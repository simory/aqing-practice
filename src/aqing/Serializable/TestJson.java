package aqing.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @Description:测试json序列化
 * @Author: fuqi
 * @Date: 2019/9/20 下午1:35
 */
public class TestJson {

    /**
     * Gson
     * 转换类型：1.Java对象->JSON数据  2.JSON数据->Java对象
     * 解析(序列化)方式：
     *      1.为空值的不会解析；即属性名也不存在；
     *      2.属性名称不会改变，也就是驼峰和下划线等
     *      3.序列化取值是通过属性名
     * 性能：快速高效、代码量少、面向对象、数据传输解析方便
     * @param obj
     * @return
     */
    public static String toJsonByGson(Object obj){
        String re=null;
        Gson gson=new Gson();
        re = gson.toJson(obj);
        return  re;
    }

    public static Object fromJsonByGson(String json){
//        List<String> ls = gson.fromJson(aaa,new TypeToken<List<String>>(){}.getType());
        Gson gson=new Gson();
        return gson.fromJson(json, TestVo.class);
    }

    /**
     * Jackson
     * 转换类型：1.Java->son对象/xml文档 2.json/xml->Java
     * 解析(序列化)方式：
     *      1.Java bean必须把Json数据里的所有key都对应，如果无值解析后值为null;不存在gson的部分解析
     *      2.属性名称不会改变，也就是驼峰和下划线等
     *      3.序列化方式是通过读取getXXX
     * 性能：速度和效率高于Gson，数据量大的情况优势尤为明显、占用内存少
     * 适用场景：需要处理超大json文档、不需要对json文档按需解析全解析的方式、性能要求较高
     */
    public static String toJsonByJackson(Object obj){
        String re=null;
        //对象映射
        ObjectMapper objectMapper=new ObjectMapper();
        //设置时间格式
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        try {
            re=objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

    /**
     * FastJson
     * 转换类型：1.Java对象->JSON数据  2.JSON数据->Java对象
     * 解析(序列化)方式：
     *       1.为空值的不会解析，即属性名也不存在在json串；
     *       2.属性名称不会改变，也就是驼峰和下划线等
     *       3.序列化取值是通过setXXX
     * @param obj
     * @return
     */
    public static String toJsonByFastJson(Object obj){
        String re = JSONObject.toJSONString(obj);
        return re;
    }

    public static void main(String[] args){
        TestVo vo = new TestVo("name","age","female");
        TestVo vo1 = new TestVo("value-testHash","value-tesh_hash");
        TestVo vo2 = new TestVo("name","age","female","value-testHash","value-test_hash");
        TestVo vo3 = new TestVo("name",null,"");
        System.out.println("gson    "+toJsonByGson(vo));
        System.out.println("gson    "+toJsonByGson(vo1));
        System.out.println("gson    "+toJsonByGson(vo2));
        System.out.println("gson    "+toJsonByGson(vo3));

        System.out.println("========================================");

        System.out.println("Jackson "+toJsonByJackson(vo));
        System.out.println("Jackson "+toJsonByJackson(vo1));
        System.out.println("Jackson "+toJsonByJackson(vo2));
        System.out.println("Jackson "+toJsonByJackson(vo3));
        System.out.println("========================================");

        System.out.println("Fastjson"+toJsonByFastJson(vo));
        System.out.println("Fastjson"+toJsonByFastJson(vo1));
        System.out.println("Fastjson"+toJsonByFastJson(vo2));
        System.out.println("Fastjson"+toJsonByFastJson(vo3));

        /**
         * {"name":"name","age":"age","gender":"female"}
         * {"testHash":"value-testHash","test_hash":"value-tesh_hash"}
         * {"name":"name","age":"age","gender":"female","testHash":"value-testHash","test_hash":"value-test_hash"}
         * {"name":"name","gender":""}
         * ========================================
         * {"age":"age","gender":"female","testHash":null,"test_hash":null}
         * {"age":null,"gender":null,"testHash":"value-testHash","test_hash":"value-tesh_hash"}
         * {"age":"age","gender":"female","testHash":"value-testHash","test_hash":"value-test_hash"}
         * {"age":null,"gender":"","testHash":null,"test_hash":null}
         * ========================================
         * {"age":"age","gender":"female"}
         * {"testHash":"value-testHash","test_hash":"value-tesh_hash"}
         * {"age":"age","gender":"female","testHash":"value-testHash","test_hash":"value-test_hash"}
         * {"gender":""}
         */
    }

    static class TestVo{
        private String name;
        private String age;
        private String gender;

        private String testHash;
        private String test_hash;

        public TestVo(){}

        public TestVo(String name, String age, String gender){
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public TestVo(String name, String age, String gender,String testHash, String test_hash){
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.testHash = testHash;
            this.test_hash = test_hash;
        }

        public TestVo(String testHash, String test_hash){
            this.testHash = testHash;
            this.test_hash = test_hash;
        }

        public String getName() {
            return name;
        }

//        public void setName(String name) {
//            this.name = name;
//        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getTestHash() {
            return testHash;
        }

        public void setTestHash(String testHash) {
            this.testHash = testHash;
        }

        public String getTest_hash() {
            return test_hash;
        }

        public void setTest_hash(String test_hash) {
            this.test_hash = test_hash;
        }
    }


}
