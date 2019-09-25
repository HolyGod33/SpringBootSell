package com.imooc.util;

import com.by_syk.graphiccr.core.GraphicCTranslator;
import com.imooc.config.CourseUrlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 14:18 2019/9/12
 */
@Slf4j
public class HttpRequestUtil {
    @Autowired
    public static  CourseUrlConfig courseUrlConfig;

    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 23:30 2019/9/12
     * @return: 验证码识别结果
     * create with IDEA
     *=============================
     */
    public static String verifyCodeRecognize(String dest) {
        String result = GraphicCTranslator.translate(dest,GraphicCTranslator.TYPE_7);

        return result;
    }
    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 0:01 2019/9/13
     * @param userAccount
     * @param userPassword
     * @return:
     * create with IDEA
     *=============================
     */
    public static String getparam(String userAccount,String userPassword,String verifyCode) {

        //构建encoded
        String encoded = Base64.getEncoder().encodeToString(userAccount.getBytes())+"%%%"+Base64.getEncoder().encodeToString(userPassword.getBytes());
        //serAccount=2017004459&userPassword=&RANDOMCODE=bmmq&encoded=MjAxNzAwNDQ1OQ%3D%3D%25%25%25YTExNzA3MTI0NjI%3D
        String result = "userAccount="+userAccount+"&userPassword="+userPassword+"&RANDOMCODE="+verifyCode+"&encoded="+encoded;

        return result;
    }
    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 23:30 2019/9/12
     * @param param body的参数,需要base64编码
     * @param cookie cookie作为身份验证
     * @return: String格式的网页代码,需要转换
     * create with IDEA
     *=============================
     */
    public static String sendPost(String param,String cookie) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(courseUrlConfig.getCommitUrl());
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Cookie", "JSESSIONID=" + cookie);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("[发送post表单异常],message={}",e.getMessage());
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 23:46 2019/9/12
     * @return: cookie
     * @Description 获取cookie以及验证码
     * create with IDEA
     *=============================
     */
    public static String getCookieAndImg(String src,String dest) throws IOException {
        //get image using the cookie
        HttpURLConnection imgConn = (HttpURLConnection) ( new URL(src)).openConnection();

        //get the cookie
        String setCookie = imgConn.getHeaderField("Set-Cookie");
        String cookie = setCookie.substring( setCookie.indexOf('=') + 1, setCookie.indexOf(';') );

        //new input from network( 'imgConn' )
        try( BufferedInputStream imgInputStream = new BufferedInputStream( imgConn.getInputStream() ) ){
            //new output to local file system
            try(BufferedOutputStream imgOutputStream = new BufferedOutputStream( new FileOutputStream(dest))){
                byte[] buf = new byte[1024];
                while( -1 != ( imgInputStream.read(buf) ) )
                    imgOutputStream.write(buf);
            } catch(IOException e) { e.printStackTrace();  }
        } catch(IOException e) { e.printStackTrace();  }
        return cookie;
    }
}
