package aqing.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/9/11 下午8:37
 */
public class Test {
    public static int achieveLongestConsecutive(int[] allNums) {
        if(allNums == null || allNums.length == 0) return 0;
        int max = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < allNums.length; i++){
            if(!map.containsKey(allNums[i])){
                map.put(allNums[i], 1);
                if(map.containsKey(allNums[i] - 1)){
                    max = Math.max(max, merge(map, allNums[i]- 1, allNums[i]));
                }
                if(map.containsKey(allNums[i] + 1)){
                    max = Math.max(max, merge(map, allNums[i], allNums[i] + 1));
                }
            }
        }
        return max;
    }

    public static int merge(Map<Integer, Integer> map, int less, int more){
        int left = less - map.get(less) + 1;
        int right = more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);

        return len;
    }

    public static void main(String[] args){
        int[] tests = new int[]{400,2,3,1,4,90,};
        System.out.println(achieveLongestConsecutive(tests));
    }
}
