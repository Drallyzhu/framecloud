package com.framecloud.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Date 2019/5/17 17:02
 * @Description
 */
public class RequestUtils {

    //将请求参数转换成Map
    public static Map<String, Object> getUrlParams(HttpServletRequest request) {
        String param = "";
        Map<String, Object> result = new HashMap<>();
        try {
            String queryString = request.getQueryString();
            if (StringUtils.isBlank(queryString)) {
                return result;
            }
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] params = param.split("&");
        for (String s : params) {
            Integer index = s.indexOf("=");
            result.put(s.substring(0, index), s.substring(index + 1));
        }
        return result;
    }

    /**
     * 方法用途: 对所有传入参数按照字段名的Unicode码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写
     *                   true:key转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, Object> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, Object> tmpMap = paraMap;
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue() + "";
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return buff;
    }


    public static String getUrlOrderParams(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("get")) {
            if (request.getContentType().contains("json")) {
                Map<String, Object> map = JsonParseUtil.jsonFormatter(request);
                return formatUrlMap(map, false, false);
            } else {
                Map<String, String[]> parameterMap = request.getParameterMap();
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    map.put(entry.getKey(), entry.getValue()[0]);
                }
                return formatUrlMap(map, false, false);
            }
        } else {
            Map<String, Object> map = getUrlParams(request);
            return formatUrlMap(map, false, false);
        }
    }


    public static Map<String, String> postRequest(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        ServletInputStream in = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        if (sb.toString().length() <= 0) {
            return null;
        } else {
            System.out.println("===================" + sb.toString() + "==================");
            System.out.println("===================" + JSONObject.parseObject(sb.toString()).getInnerMap() + "==================");
            return JsonUtils.jsonToStringMap(sb.toString());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        //字典序列排序
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("total_fee", "200");
        paraMap.put("appid", "wxd678efh567hg6787");
        paraMap.put("body", "腾讯充值中心-QQ会员充值");
        paraMap.put("out_trade_no", "20150806125346");
        String url = formatUrlMap(paraMap, false, false);
        System.out.println(url);

//        Map<String, Object> map = getUrlParams(request);
//        return formatUrlMap(map, false, false);
      /*  for (int j = 0; j < 10; j++) {
            String bodyContent = "/action/get_guess_words" + "&key=" + "zhqyd0ic?gReoBP%9";
            byte[] totpContent = TotpUtil.generateTotp("015a66ec3a177451126a0382818cdb27");
//            byte[] tokenBytes = Arrays.copyOf(bodyContent, bodyContent.length + totpContent.length);
            byte[] reulstContent = (bodyContent + TotpUtil.simpleTotp("015a66ec3a177451126a0382818cdb27")).getBytes();
            String localToken = DigestUtils.md5DigestAsHex(reulstContent).toUpperCase();
            System.out.println(localToken);
            Thread.sleep(1000);
        }*/

    }

}
