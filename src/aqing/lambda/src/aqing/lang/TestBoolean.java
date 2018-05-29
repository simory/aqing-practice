package aqing.lambda.src.aqing.lang;

/**  
 * @Description: 描述
 * @author fuqi@meizu.com 
 * @date 2018年5月2日 下午2:09:54 
 * @version V1.0   
 */
public class TestBoolean {
	/**
	 * 连续两个boolean属性会各占一个字节，然后接着会补齐6个字节，凑齐8字节，boolean[]是按数组长度的8字节倍数占用字节数，长度在1~8之间的boolean[]占24字节，长度在9到16之间的占32字节，开启UseCompressedOops;
关闭UseCompressedOops，boolean属性占8字节，boolean[]在1-8之间的占32字节，9-16之间的占40字节
	 */
	public boolean b;
    public boolean b2;
    public boolean b3;
    public boolean b4;
    public boolean b5;
    public boolean b6;
    public boolean b7;
    public boolean b8;
    public boolean[] bs=new boolean[8];
    public boolean[] bs2=new boolean[4];
    public boolean[] bs3=new boolean[1];
    public boolean[] bs4=new boolean[9];
    public boolean[] bs5=new boolean[16];
}
