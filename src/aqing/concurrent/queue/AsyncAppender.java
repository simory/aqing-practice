package aqing.concurrent.queue;

//import com.alibaba.tissot.common.Tissot;
//import com.alibaba.tissot.common.util.Monitor;
//import com.alibaba.tissot.common.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @Description: 描述
 * @author fuqi@meizu.com
 * @date 2018年5月17日 下午1:46:16
 * @version V1.0
 */
public class AsyncAppender {


//	/**
//	 * 异步提交日志，避免影响主线程 主要使用了一个无锁的缓存数组，存在提交上来的日志 异步append
//	 *
//	 * @author tianshi
//	 */
//	public class AsyncAppender {

		/**
		 * 默认的消费者唤醒阈值，这个值需要让消费者能较持续的有事情做， 这个值设置过小，会导致生产者频繁唤起消费者；
		 * 设置过大，可能导致生产者速度过快导致队列满丢日志的问题。
		 */
		private static final int	DEFAULT_NOTIFY_THRESHOLD	= 512;

		/**
		 * RingBuffer 实现，size 必须为 2 的 n 次方
		 *
		 */
		private final String[]		entries;
		/**
		 * 无锁队列最大长度，用于控制当生产者快于消费者时，瞬间压力太大
		 */
		private final int			queueSize;
		/**
		 * 队列下标掩码
		 */
		private final int			indexMask;
		/**
		 * 用于控制，当队列中数据堆积数据达到一定程度时，唤醒消费者
		 */
		private final int			notifyThreshold;

		/**
		 * 控制消费者，唤醒消费者
		 */
		private final ReentrantLock	lock;
		private final Condition		notEmpty;

		// 下一个写的位置，一直递增
		private AtomicLong			putIndex;
		/**
		 *最近丢弃的日志条数
	 	 */
		private AtomicLong			discardCount;
		/**
		 * 下一个读的位置，一直递增，不能大于 putIndex
		 */

		private AtomicLong			takeIndex;

		private TissotAppender		appender;

		private String				workerName;

		private Thread				worker;
		private AtomicBoolean		running;
//		public Monitor				monitor =  new Monitor(this);

		public AsyncAppender(int queueSize) {
			checkQueueSize(queueSize);
			this.entries = new String[queueSize];
			this.queueSize = queueSize;
			this.indexMask = queueSize - 1;
			this.notifyThreshold = queueSize >= DEFAULT_NOTIFY_THRESHOLD ? DEFAULT_NOTIFY_THRESHOLD : queueSize;

			this.putIndex = new AtomicLong(0L);
			this.discardCount = new AtomicLong(0L);
			this.takeIndex = new AtomicLong(0L);

			this.running = new AtomicBoolean(false);

			this.lock = new ReentrantLock(false);
			this.notEmpty = lock.newCondition();
		}

		/**
		 * 启动异步日志
		 * @param appender
		 * @param workerName
		 * @param isMonitor：是否开启统计日志
		 */
		public void start(TissotAppender appender, String workerName, boolean isMonitor) {
			this.appender = appender;
			this.workerName = workerName;
			this.worker = new Thread(new AsyncRunnable(), "Tissot-AsyncAppender-Thread-" + workerName);
			this.worker.setDaemon(true);
			this.worker.start();
			if(isMonitor){
//				monitor.setStarted(true);
			}
			Tissot.selfLog(String.format("AsyncAppender=%s started succ,isMotor=%s",workerName,isMonitor));
		}

		/**
		 * 本来就是在系统关闭尝试做到事前，就是有异常，也不用管了
		 */
		public void ShutdownHook() {
			try {
				this.flush();
//				Monitor.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private static void checkQueueSize(int n) {
			if (n <= 0) {
				throw new IllegalArgumentException("queueSize cant less than ");
			}
			if (!((n & (n - 1)) == 0)) {
				throw new IllegalArgumentException("queueSize must be N power of 2");
			}
		}

		int size() {
			return (int) (putIndex.get() - takeIndex.get());
		}

		/**
		 * 队列满时直接丢弃日志，不阻塞业务线程，返回日志是否被接受
		 */
		public boolean append(String log) {
			if (log == null) {
				return true;
			}
			int qsize = queueSize;
			for (;;) {
				long put = putIndex.get();
				long size = put - takeIndex.get();
				if (size >= qsize) {
					discardCount.incrementAndGet();
					return false;
				}
				if (putIndex.compareAndSet(put, put + 1)) {
					entries[(int) put & indexMask] = log;
					// 仅仅在队列的日志数超过阈值，且消费者不在运行，且获得锁，才唤醒消费者
					// 这个做法能保证只有必要时才立即通知消费者，减少上下文切换的开销                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
					if (size >= notifyThreshold && !running.get() && lock.tryLock()) {
						try {
							notEmpty.signal();
						} catch (Exception e) {
							Tissot.selfLog("[ERROR] fail to signal notEmpty", e);
						} finally {
							lock.unlock();
						}
					}
					return true;
				}
			}
		}

		public void flush() throws IOException {
			// 最多等待刷新的时间，避免数据一直在写导致无法返回
			long end = System.currentTimeMillis() + 500;
			while (size() > 0 && System.currentTimeMillis() <= end) {
				if (running.get()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						break;
					}
				} else {
					if (lock.tryLock()) {
						try {
							notEmpty.signal();
						} catch (Exception e) {
							Tissot.selfLog("[ERROR] fail to signal notEmpty", e);
						} finally {
							lock.unlock();
						}
					}
				}
			}
//			this.appender.flush();
		}

		public static void main(String[] args) {
			long max = Long.MAX_VALUE;
			long d = 24 * 3600 * 1000 * 365;
			System.out.println(max + "  " + d);
			System.out.println(max / d);
		}

		class AsyncRunnable implements Runnable {
			public void run() {
				final AsyncAppender parent = AsyncAppender.this;
				final int indexMask = parent.indexMask;
				final String workerName = parent.workerName;
				final String[] entries = parent.entries;
				final AtomicLong putIndex = parent.putIndex;
				final AtomicLong takeIndex = parent.takeIndex;
				final AtomicBoolean running = parent.running;
				final ReentrantLock lock = parent.lock;
				final Condition notEmpty = parent.notEmpty;
				for (;;) {
					try {
						running.set(true);
						long take = takeIndex.get();
						long size = putIndex.get() - take;
						if (size > 0) {
							do {
								final int idx = (int) take & indexMask;
								String log = entries[idx];
								while (log == null) {
									Thread.yield();
									log = entries[idx];
								}
								entries[idx] = null;
								parent.appender.append(log );
								takeIndex.set(++take);
								--size;
//								monitor.count();
							} while (size > 0);

						} else {
							parent.appender.flush();
							if (lock.tryLock()) {
								try {
									running.set(false);
									notEmpty.await(1, TimeUnit.SECONDS);
								} finally {
									lock.unlock();
								}
							}
						}
					} catch (InterruptedException e) {
						Tissot.selfLog("[WARN] " + workerName + " async thread is iterrupted");
						running.set(false);
					} catch (Exception e) {
						Tissot.selfLog("[ERROR] Fail to async write log", e);
					}
				}
			}
		}

		public AtomicLong getDiscardCount() {
			return discardCount;
		}

		public int getQueueSize() {
			return queueSize;
		}

		public String getWorkerName() {
			return workerName;
		}
		public long getUserdSize(){
			return putIndex.get() - takeIndex.get();
		}
}
