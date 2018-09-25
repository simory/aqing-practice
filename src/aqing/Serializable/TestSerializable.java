package aqing.Serializable;

import java.io.*;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/9/25 上午6:36
 */
public class TestSerializable implements Serializable {

    private Long id;
    private AttributeSerialzable attr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttributeSerialzable getAttr() {
        return attr;
    }

    public void setAttr(AttributeSerialzable attr) {
        this.attr = attr;
    }

    public static void main(String[] args){
//        serialization();
        //test exception of no serializable data
        TestSerializable t = deserialization(serialization());
//        t.getAttr().getTestId();
    }

    public static TestSerializable deserialization(byte[] bytes){
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois  = new ObjectInputStream(bais);
//            FileInputStream stream = new FileInputStream(new File("123.txt"));
//            ObjectInputStream ois  = new ObjectInputStream(stream);
            TestSerializable test = (TestSerializable)ois.readObject();
            return test;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] serialization(){
        try {
//            File file = new File("123.txt");
//            file.createNewFile();
//            FileOutputStream stream = new FileOutputStream(file);
//            ObjectOutputStream oos = new ObjectOutputStream(stream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = null;
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            TestSerializable t = new TestSerializable();
            t.setId(90l);
            AttributeSerialzable attr = new AttributeSerialzable();
            attr.setTestId(2);
            attr.setTestStr("");
            t.setAttr(attr);
            oos.writeObject(t);
            oos.flush();
            return  baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // /Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/bin/java
    // /Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/jre/lib/libinstrument.dylib.
}
