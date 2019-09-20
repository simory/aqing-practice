package aqing.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/10/14 下午12:22
 */
public class ReallyTestHashMap {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>(4);
        System.out.println("size:"+map.size());
        map.put("","");
        map.put("1","");
        map.put("2","");
        map.put("3","");
        map.put("4","");
        System.out.println("size:"+map.size());
        ConcurrentHashMap<String, Object> cmap = new ConcurrentHashMap<String, Object>(3);
        System.out.println("size:"+cmap.size());
        cmap.put("","");
        cmap.put("1","");
        cmap.put("2","");
        for(int i=0;i<100000;i++){
            cmap.put(""+i,"");
        }
        System.out.println("size:"+cmap.size());

    }
}
