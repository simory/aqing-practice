package aqing.interview;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/9/14 上午6:12
 */
public class GetPaymentType {

    static final Long ALLOW_LONGEST_WAIT_TIME = 200l;//ms
    static CountDownLatch countDown = new CountDownLatch(PaymentTypes.values().length);
    static Boolean[] result = new Boolean[PaymentTypes.values().length];

    public static Map<String, Object> getAvailablePaymentTypes(){
        for(PaymentTypes payment: PaymentTypes.values()){
            FutureTask<String> r = new FutureTask<>(new Handle(countDown, payment,result));
            new Thread(r).start();
        }
        try {
            countDown.await(ALLOW_LONGEST_WAIT_TIME, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            System.out.println("wait-exception---"+e.getMessage());
        }
        System.out.println("wait-exception---============================================");
        for(boolean rr : result){
            System.out.println("============================================"+rr);
        }
        return null;
    }

    public static void main(String[] args){
        getAvailablePaymentTypes();
    }

    static class Handle implements Callable<String> {
        private final CountDownLatch countDown;
        private final PaymentTypes payment;
        private final Boolean[] result;
        public Handle(CountDownLatch countDown,PaymentTypes payment,Boolean[] result){
            this.countDown = countDown;
            this.payment = payment;
            this.result = result;
        }

        public String call(){
            try{
                for(int i = 0 ;i<10;i++){
                    String r = "";
                    if(r!=null){
                        //result[payment.getIndex()]=Boolean.TRUE;
                        System.out.println("running---"+payment.getIndex());
                        if(payment.getIndex() == PaymentTypes.VOUCHER.getIndex()) {
                            System.out.println("countDown---"+payment.getIndex());
                            result[payment.getIndex()]=Boolean.TRUE;
                            countDown.countDown();
                            break;
                        }
                    }
                }
            }catch(Exception e){
                result[payment.getIndex()]=Boolean.FALSE;
                System.out.println("call exception---"+e.getMessage());
            }
            return "";
        }
    }
}


