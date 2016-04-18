package com.kingdee.eas.fdc.contract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JS数据交换格式
 * @version v0.1
 * @author longHP
 */
public class JsDataTools {
    /**
     * 获取js数据
     * @param 元数据map
     * @return js数据
     */
    public synchronized static String getJsData(Object obj) {
        return createJsData(obj);
    }
    
    /**
     * 生成js数据
     * @param 元数据map
     * @return js数据
     */
    public static String createJsData(Object obj) {
        if (null == obj) {
            return "\"\"";
        }
        StringBuffer resultStr = new StringBuffer();
        if (obj instanceof Collection) {    // 集合List、Set等类型
            resultStr.append("[");
            Collection collection = (Collection) obj;
            if (collection.size() > 0) {
                Object[] collectionObj = collection.toArray();
                for (int i = 0; i < collectionObj.length; i++) {
                    resultStr.append(createJsData(collectionObj[i]) + ",");
                }
                resultStr.deleteCharAt(resultStr.lastIndexOf(","));
            }
            resultStr.append("]");
        } else if (obj instanceof Map) {    // Map类型
            resultStr.append("{");
            Map map = (Map) obj;
            if (map.size() > 0) {
                Iterator iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Entry) iter.next();
                    String key = entry.getKey().toString();
                    resultStr.append("\"" + key + "\":");    // jquery1.4以上要求双引号包裹
                    Object tempObj = entry.getValue();
                    resultStr.append(createJsData(tempObj) + ",");
                }
                resultStr.deleteCharAt(resultStr.lastIndexOf(","));
            }
            resultStr.append("}");
        } else {    // 基本类型
            try {
                int arrLength = Array.getLength(obj);
                resultStr.append("[");
                if (arrLength > 0) {
                    for (int i = 0; i < arrLength; i++) {
                        resultStr.append(createJsData(Array.get(obj, i)) + ",");
                    }
                    resultStr.deleteCharAt(resultStr.lastIndexOf(","));
                }
                resultStr.append("]");
            } catch (IllegalArgumentException e) {    // 不是数组，是最基本的数据
                resultStr.append("\"" + stringToJson(obj + "") + "\"");
            }
        }
        return resultStr.toString();
    }
    
    /**
     * 处理特殊字符
     * @param json字符串
     * @return 处理过的字符串
     */
    public static String stringToJson(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
//        String obj = null;
//        if (Arrays.asList(obj).get(0) == null) {
//            System.out.println(Arrays.asList(obj).get(0));
//        }
//        String str = "fds";
//        java.lang.reflect.Array.getLength(str);
        Map map = new HashMap();
        map.put("a", "1");
        map.put("b", "2");
        int[] intArr = {1,2,3};
//        int[] intArr = new int[0];
//        System.out.println(intArr.length);
//        System.out.println(Arrays.asList(intArr).get(0));
//        java.lang.reflect.Array.getLength(intArr);
        map.put("intArr", intArr);
        Map childMap = new HashMap();
        childMap.put(3, 5);
        childMap.put(7, new HashMap());
        childMap.put(new Object(), intArr);
        map.put("map", childMap);
        List childList = new ArrayList();
        childList.add(true);
        childList.add(new ArrayList());
        childList.add(intArr);
        map.put("list1", childList);
        System.out.println(JsDataTools.getJsData(map));
    }
}