package com.imooc.Service;

import com.by_syk.graphiccr.core.GraphicCTranslator;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.imooc.config.CourseUrlConfig;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 22:50 2019/9/11
 */
@Component
@Slf4j
public class CourseService {

    @Autowired
    private CourseUrlConfig courseUrlConfig;

    public String getCourseTable(String userAccount,String userPassword){
        return null;
    }

//    //存放cookie的集合
//    private static final Map<String ,String> cookies = new HashMap<>();
//
//    //cookie name
//    private final static String sessionName = "JSESSIONID";
//
//    //cookie value
//    private static String sessionValue = "";
//
//    //验证码
//    private static String verifyCode = "";
//
//    private static Map<String, String> headData = new HashMap<>();
//
//    private static String base64encode = "";
//
//
//    public void  search(String userAccount,String userPassword) throws IOException {
//        verifyCode = downloadImgReturnCookie(courseUrlConfig.getVerifyCodeUrl(), "C:\\Users\\CXY\\Desktop\\verifyCodeImg.jpg");
//
//        //print cookie
//        System.out.println(cookies);
//
//        //base64编码
//        base64encode = Base64.getEncoder().encodeToString((userAccount+"%%%"+userPassword).getBytes());
//
//        log.info(base64encode);
//
//        //login using cookie prepared.
//        Connection loginConn = Jsoup.connect(courseUrlConfig.getCommitUrl());
//
//        loginConn.header("Content-Type","application/x-www-form-urlencoded");
//
//        //请求体加入
//        loginConn.requestBody("userAccount="+userAccount+"&userPassword=&RANDOMCODE="+verifyCode+"&encoded="+base64encode);
//
//        //login using cookies
//        loginConn.header("cookie","cookies");
//
//        //try to login
//        Document document = loginConn.post();
//
//        System.out.println( document.body());
//    }
//
//    private static String  downloadImgReturnCookie(String srcUrl, String dest) throws MalformedURLException, IOException{
//
//        //get image using the cookie
//        HttpURLConnection imgConn = (HttpURLConnection) ( new URL(srcUrl) ).openConnection();
//
//        //get the cookie
//        String cookie = imgConn.getHeaderField("Set-Cookie");
//        sessionValue = cookie.substring( cookie.indexOf('=') + 1, cookie.indexOf(';') );
//        cookies.put( sessionName,  sessionValue );
//
//        //new input from network( 'imgConn' )
//        try( BufferedInputStream imgInputStream = new BufferedInputStream( imgConn.getInputStream() ) ){
//            //new output to local file system
//            try(BufferedOutputStream imgOutputStream = new BufferedOutputStream( new FileOutputStream(dest) );  ){
//                byte[] buf = new byte[1024];
//                while( -1 != ( imgInputStream.read(buf) ) )
//                    imgOutputStream.write(buf);
//            } catch(IOException e) { e.printStackTrace();  }
//        } catch(IOException e) { e.printStackTrace();  }
//
//        String result = GraphicCTranslator.translate(new File(dest),GraphicCTranslator.TYPE_7);
//
//        return result;
//
//    }
}
