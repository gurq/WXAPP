package com.gutongxue.wxapp.util;



import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.gutongxue.wxapp.domain.ImageDO;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Shadow on 2017/2/13.
 * 谷若琪个人所用,通用工具类,以后我写的每个项目都放进去
 */
public class GRQUtil {

    public static void main(String[] args) {
//        printApiController("/sxr",0);
//        String sql="insert `jhyt`.`jh_base_article_info`(`gmt_create`,`gmt_modified`,`title`,`author`,`editor`,`publish_time`,`big_pic_flag`,`pic1`,`pic2`,`pic3`,`description`,`type`,`file_id`,`link_id`,`column_id`,`status_flag`,`pending_flag`) values('2017-06-29 10:04:34','2017-07-06 10:04:36','1','1','1','2017-07-06 10:04:41',1,'1','1','1','1',1,1,1,1,1,1);";
//        System.out.println(replaceAliyunAutoCreateSQLToMySQL(sql));
        getResultsStr(new ImageDO().getClass());
    }

    /**
     * 1.用于获取结果集的映射关系
     */
    public static String getResultsStr(Class origin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Results({\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            //映射关系：对象属性(驼峰)->数据库字段(下划线)
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
            stringBuilder.append(String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
        }
        stringBuilder.append("})");
        return stringBuilder.toString();
    }

    private static void printApiController(String mapping,int type){
        String jspName=mapping.replace("/","_");
        if (jspName.charAt(0)=='_'){
            jspName=jspName.substring(1);
        }
        StringBuffer functionName=new StringBuffer();
        String[] arrs=jspName.split("_");
        for (int i=0;i<arrs.length;i++){
            String item=arrs[i];
            if (i==0){
                functionName.append(item);
            }else {
                char[] cs=item.toCharArray();
                cs[0]-=32;//根据ascii码转字母大小写
                functionName.append(String.valueOf(cs));
            }
        }
        String content;
        if (type==0){
            //api
            content="@RequestMapping(\""+mapping+"\")\n" +
                    "    public ResponseEntity<String> "+functionName.toString()+"(HttpServletRequest request){\n" +
                    "        HttpHeaders responseHeaders = new HttpHeaders();\n" +
                    "        responseHeaders.set(\"Access-Control-Allow-Origin\",\"*\");\n" +
                    "        responseHeaders.set(\"Content-Type\",\"application/json;charset=UTF-8\");\n" +
                    "        String jsonString;\n" +
                    "        try {\n" +
                    "\n" +
                    "            jsonString = GRQUtil.returnJsonString(\"请求成功\", true, null);\n" +
                    "        } catch (Exception e) {\n" +
                    "            jsonString = GRQUtil.returnJsonString(\"请求失败\", false, e.getMessage());\n" +
                    "        }\n" +
                    "        return new ResponseEntity<String>(jsonString, responseHeaders, HttpStatus.OK);\n" +
                    "    }";
        }else {
            content="@RequestMapping(\""+mapping+"\")\n" +
                    "    public String "+functionName.toString()+"(){\n" +
                    "        return \""+jspName+"\";\n" +
                    "    }";
        }
        System.out.println(content);
    }

//    /**
//     * 对返回前台的数据格式化
//     *
//     * @param message
//     * @param status
//     * @param result
//     * @return
//     */
//    public static String returnJsonString(String message, boolean status, Object result) {
//        JSONObject item = new JSONObject();
//        item.put("message", message);
//        item.put("status", status);
//        item.put("result", result);
//        return JsonToString(item);
//    }
//
//    public static String JsonToString(JSONObject jsonObject){
//        return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
//    }
//
//    public static String JsonToString(JSONArray jsonArray){
//        return JSON.toJSONString(jsonArray, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
//    }

    public static boolean checkNull(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if ("".equals((String) o)) {
                return true;
            }
            return false;
        }
        if (o instanceof StringBuffer) {
            if (((StringBuffer) o).length() == 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static int getRequestInteger(HttpServletRequest request, String param, int defaultNum) {
        String numString = request.getParameter(param);
        int num;
        try {
            num = Integer.valueOf(numString);
        } catch (Exception e) {
            num = defaultNum;
        }
        return num;
    }

    public static String getTodayByFormat(String timeFormat) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }

    public static String getYesterdayByFormat(String timeFormat) {
        return LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern(timeFormat));
    }

//    public static String downloadFromUrl(String url, String filePath) {
//        try {
//            URL httpUrl = new URL(url);
//            File f = new File(filePath);
//            FileUtils.copyURLToFile(httpUrl, f);
//            return filePath;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String getFileNameFromUrl(String url){
        try {
            String fileName = url.split("/")[url.split("/").length - 1];
            //如果不这么切割的话,形如这样的网址就没法下载了
            //http://cms-bucket.nosdn.127.net/73be74f2bc8a47c39e300fec9da7908020170307162629.png?imageView&thumbnail=550x0
            fileName = fileName.split("\\?")[0];
            if (fileName.split("\\.").length>0){
                fileName = fileName.replace("."+fileName.split("\\.")[fileName.split("\\.").length-1],"");
            }
            URL httpUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) httpUrl.openConnection();
            urlConnection.connect();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            String suffix=HttpURLConnection.guessContentTypeFromStream(bis);
            suffix=suffix.split("/")[suffix.split("/").length-1];
            fileName=fileName+"."+suffix;
            return fileName;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 将字符串装换为MD5
     * @param str
     * @return
     */
    public static String strToMd5(String str,int length) {
        String md5Str = null;
        if (str != null && str.length() != 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte b[] = md.digest();

                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                if (length==16){
                    //16位
                    md5Str = buf.toString().substring(8, 24);
                }else {
                    //32位
                    md5Str = buf.toString();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return md5Str;
    }

    private static String replaceAliyunAutoCreateSQLToMySQL(String sql){
        sql=sql.replace("insert","insert into");
        sql=sql.replace("`","");
        String result=sql.split("values")[0];
        sql=sql.split("values")[1];
        result+="values (";
        for (int i=0;i<sql.split(",").length;i++){
            result+="?,";
        }
        result=result.substring(0,result.length()-1);
        result+=")";
        result=" String sql = \""+result+"\";";
        return result;
    }

    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
