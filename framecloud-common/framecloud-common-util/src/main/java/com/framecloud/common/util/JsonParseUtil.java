package com.framecloud.common.util;


import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 多层嵌套json数据转换为单层，同时规格化
 **/
public class JsonParseUtil {
    /**
     * 把拍平后的json进行格式化处理，输出标准的json格式
     *
     * @param request
     * @return
     */
    public static Map<String, Object> jsonFormatter(HttpServletRequest request) {
        String uglyJSONString = "";
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            uglyJSONString = sb.toString();
        } catch (Exception e) {

        }
        Map<String, Object> map = new HashMap<>();
        parseJson2Map(map, uglyJSONString, null);

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        return map;
    }

    public static void parseJson2Map(Map map, JsonObject jsonObject, String parentKey) {
        for (Map.Entry<String, JsonElement> object : jsonObject.entrySet()) {
            String key = object.getKey();
            JsonElement value = object.getValue();
            String fullkey = (null == parentKey || parentKey.trim().equals("")) ? key : parentKey.trim() + "." + key;
            //判断对象的类型，如果是空类型则安装空类型处理
            if (value.isJsonNull()) {
                map.put(fullkey, null);
                continue;
                //如果是JsonObject对象则递归处理
            } else if (value.isJsonObject()) {
                parseJson2Map(map, value.getAsJsonObject(), fullkey);
                //如果是JsonArray数组则迭代，然后进行递归
            } else if (value.isJsonArray()) {
                JsonArray jsonArray = value.getAsJsonArray();
                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonElement jsonElement1 = iterator.next();
                    if (jsonElement1.isJsonPrimitive()) {
//                        parseJson2Map(map, jsonElement1.getAsJsonArray(), fullkey);
                        getJsonPrimitive(map, jsonElement1, fullkey);
                        continue;
                    }
                    parseJson2Map(map, jsonElement1.getAsJsonObject(), fullkey);
                }
                continue;
                // 如果是JsonPrimitive对象则获取当中的值,则还需要再次进行判断一下
            } else if (value.isJsonPrimitive()) {
                getJsonPrimitive(map, value, fullkey);
            }
        }
    }

    private static void getJsonPrimitive(Map map, JsonElement value, String fullkey) {
        try {
            JsonElement element = new JsonParser().parse(value.getAsString());
            if (element.isJsonNull()) {
                map.put(fullkey, value.getAsString());
            } else if (element.isJsonObject()) {
                parseJson2Map(map, element.getAsJsonObject(), fullkey);
            } else if (element.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();

                if (jsonPrimitive.isNumber()) {
//                    map.put(fullkey, jsonPrimitive.getAsNumber());
                    handerMap(map, value, fullkey);
                } else {
//                    map.put(fullkey, jsonPrimitive.getAsString());
                    Object val = map.get(fullkey);
                    if (val != null) {
                        map.put(fullkey,val + jsonPrimitive.getAsString());
                    }else {
                        map.put(fullkey, value.getAsString());
                    }
                }
            } else if (element.isJsonArray()) {
                JsonArray jsonArray = element.getAsJsonArray();
                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    parseJson2Map(map, iterator.next().getAsJsonObject(), fullkey);
                }
            }
        } catch (Exception e) {
            handerMap(map, value, fullkey);
        }
    }

    private static void handerMap(Map map, JsonElement value, String fullkey) {
        Object val = map.get(fullkey);
        if (val != null) {
            map.put(fullkey,val + value.getAsString());
        }else {
            map.put(fullkey, value.getAsString());
        }
    }

    /**
     * 使用Gson拍平json字符串，即当有多层json嵌套时，可以把多层的json拍平为一层
     *
     * @param map
     * @param json
     * @param parentKey
     */
    public static void parseJson2Map(Map map, String json, String parentKey) {
        JsonElement jsonElement = new JsonParser().parse(json);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            parseJson2Map(map, jsonObject, parentKey);
            //传入的还是一个json数组
        } else if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Iterator<JsonElement> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JsonElement next = iterator.next();
                if (next.isJsonPrimitive()) {
                    handerMap(map, next, "array");
                    continue;
                }
                parseJson2Map(map, next.getAsJsonObject(), parentKey);
            }
        } else if (jsonElement.isJsonPrimitive()) {
            System.out.println("please check the json format!");
        } else if (jsonElement.isJsonNull()) {

        }
    }

    public static void main(String[] args) {

        String json = "{\"code\":200, \"message\":\"ok\", \"data\":\"{\\\"id\\\":131,\\\"appId\\\":6,\\\"versionCode\\\":6014000}\"}";

        String test = "{" + "\"hello\": \"sweetzcc\"," +
                "\"topic\": \"gjs\"," +
                "\"Id\": \"180605Ceb8NB\"," +
                "\"Type\": \"REG\"," +
                "\"Time\": \"2018-06-05 10:02:24\"," +
                "\"sweetzcc\": \"{\\\"needUpdate\\\":true,\\\"Info\\\":\\\"{\\\\\\\"apple\\\\\\\":\\\\\\\"BB199DA64A7692E927722BFD1CA\\\\\\\",\\\\\\\"token\\\\\\\":null,\\\\\\\"uniqueId\\\\\\\":\\\\\\\"868387\\\\\\\",\\\\\\\"pushSweetToken\\\\\\\":\\\\\\\"a968\\\\\\\",\\\\\\\"device\\\\\\\":\\\\\\\"android\\\\\\\",\\\\\\\"systemName\\\\\\\":\\\\\\\"Re\\\\\\\",\\\\\\\"systemV\\\\\\\":\\\\\\\"7.0\\\\\\\",\\\\\\\"pVersion\\\\\\\":\\\\\\\"4.9\\\\\\\",\\\\\\\"key\\\\\\\":\\\\\\\"63e78ea58\\\\\\\",\\\\\\\"chan\\\\\\\":\\\\\\\"net\\\\\\\",\\\\\\\"push\\\\\\\":\\\\\\\"4\\\\\\\",\\\\\\\"userName\\\\\\\":null,\\\\\\\"product\\\\\\\":\\\\\\\"sweet\\\\\\\",\\\\\\\"crime\\\\\\\":1528,\\\\\\\"update1\\\\\\\":15281}\\\"}\"" +
                "}";
//        String array = "{'name':'111','child':[{'child':[{'name':'333'}]},{'name':'2221'}]}";
        String array = "{\n" +
                "                \"id\":181123174140846,\n" +
                "                \"channelId\":7,\n" +
                "                \"tagId\":2,\n" +
                "                \"tag\":\"别墅\",\n" +
                "                \"announcerType\":2,\n" +
                "                \"releaseTime\":1542966092000,\n" +
                "                \"longitude\":104.92820989999996,\n" +
                "                \"latitude\":11.5563738,\n" +
                "                \"isCertification\":0,\n" +
                "                \"provinceId\":0,\n" +
                "                \"memberId\":3470,\n" +
                "                \"memberName\":\"官方生活资讯发布账号\",\n" +
                "                \"gravatar\":\"http://refile.tnaot.com/image/2018/10/29/ee9f378be5614ecaba5d0aea35e367b5.png?x-oss-process=image/resize,m_fill,h_180,w_180/quality,Q_70\",\n" +
                "                \"nickName\":\"官方发布\",\n" +
                "                \"mediaStatus\":0,\n" +
                "                \"releaseArea\":\"\",\n" +
                "                \"price\":5500,\n" +
                "                \"summary\":\"别墅出租在钻石岛 金边 柬埔寨\",\n" +
                "                \"floorage\":-1,\n" +
                "                \"thumbs\":[\n" +
                "                    \"http://refile.tnaot.com/image/2018/11/23/2f37e7a0167849699a5355f257354920.jpg?x-oss-process=image/crop,x_0,y_-16,w_240,h_180/resize,m_fixed,limit_0,w_240,h_180/quality,Q_80\",\n" +
                "                    \"http://refile.tnaot.com/image/2018/11/23/2527d57d0ad44a8aad845e95ad89cfc0.jpg?x-oss-process=image/crop,x_0,y_0,w_574,h_431/resize,m_fixed,limit_0,w_240,h_180/quality,Q_80\",\n" +
                "                    \"http://refile.tnaot.com/image/2018/11/23/21c02d9243b34f4d8f104a9520d512c8.jpg?x-oss-process=image/crop,x_0,y_0,w_603,h_453/resize,m_fixed,limit_0,w_240,h_180/quality,Q_80\"\n" +
                "                ],\n" +
                "                \"saleMethod\":\"出租\",\n" +
                "                \"saleMethodId\":2,\n" +
                "                \"estateArea\":\"金边\",\n" +
                "                \"estateAddress\":\"柬埔寨金边\",\n" +
                "                \"leaveMessageCount\":0,\n" +
                "                \"averagePrice\":-1\n" +
                "            }";//"{'name':'111','child':['child','name']}";

        String array1 = "[\n" +
                "  180824180156247,\n" +
                "  180720155531888\n" +
                "]";

        String array2 = "{\n" +
                "    \"orderDetailList\":[\n" +
                "        {\n" +
                "            \"productId\":1,\n" +
                "            \"rechargePhoneNum\":\"0101234567\",\n" +
                "            \"count\":1,\n" +
                "            \"aaa\":1\n" +
                "        },\n" +
                "        {\n" +
                "            \"productId\":1,\n" +
                "            \"rechargePhoneNum\":\"0101234567\",\n" +
                "            \"count\":1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"paymentType\":2\n" +
                "}";

        String array3 = "[\n" +
                "    {\n" +
                "        \"productId\":1,\n" +
                "        \"rechargePhoneNum\":\"0101234567\",\n" +
                "        \"count\":1,\n" +
                "        \"aaa\":1\n" +
                "    },\n" +
                "    {\n" +
                "        \"productId\":1,\n" +
                "        \"rechargePhoneNum\":\"0101234567\",\n" +
                "        \"count\":1\n" +
                "    }\n" +
                "]";

     /*   System.out.println(JSONObject.toJSONString(array3));
        Long start = System.currentTimeMillis();
        Map<String, Object> map = jsonFormatter(null);
        String s = RequestUtils.formatUrlMap(map, false, false);
        Long end = System.currentTimeMillis();
        System.out.println((end - start));
        System.out.println(s);*/
    }

}
