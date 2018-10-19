package com.resoft.common.utils;


import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtil {

    private static String account;    //登录用户名
    private static String pass;        //登录密码
    private static String host;        //服务器地址（邮件服务器）
    private static String port;        //端口
    private static String protocol; //协议
    /**
     * Created by wrh on 2018/6/25.
     */
    static{
        Properties prop = new Properties();
//        InputStream instream = ClassLoader.getSystemResourceAsStream("email.properties");//测试环境
        try {
//            prop.load(instream);//测试环境
            prop = PropertiesLoaderUtils.loadAllProperties("email.properties");//生产环境
        } catch (IOException e) {
            System.out.println("加载属性文件失败");
        }
        account = prop.getProperty("e.account");
        pass = prop.getProperty("e.pass");
        host = prop.getProperty("e.host");
        port = prop.getProperty("e.port");
        protocol = prop.getProperty("e.protocol");
    }

    static class MyAuthenricator extends Authenticator{
        String u = null;
        String p = null;
        public MyAuthenricator(String u,String p){
            this.u=u;
            this.p=p;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u,p);
        }
    }

    private String to;    //收件人
    private String subject;    //主题
    private String content;    //内容
    private String fileStr;    //附件路径
    private String fileStr2;  //附件路径
    private String fileStr3;  //附件路径
    private String fileStr4;  //附件路径
    private String fileStr5;  //附件路径

    public SendEmailUtil(String to, String subject, String content, String fileStr,String fileStr2,String fileStr3,String fileStr4,String fileStr5) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.fileStr = fileStr;
        this.fileStr2 = fileStr2;
        this.fileStr3 = fileStr3;
        this.fileStr4 = fileStr4;
        this.fileStr5 = fileStr5;
    }

    public SendEmailUtil(String to, String subject, String content, String fileStr) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.fileStr = fileStr;
    }



    public boolean send(String url){

        boolean flag =false;
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", protocol);
        //服务器
        prop.setProperty("mail.smtp.host", host);
        //端口
        prop.setProperty("mail.smtp.port", port);
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            //发件人
            //mimeMessage.setFrom(new InternetAddress(account,"XXX"));        //可以设置发件人的别名
            mimeMessage.setFrom(new InternetAddress(account));    //如果不需要就省略
            //收件人
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //主题
            mimeMessage.setSubject(subject);
            //时间
            mimeMessage.setSentDate(new Date());
            //容器类，可以包含多个MimeBodyPart对象
            MimeMultipart mp = new MimeMultipart();

            //MimeBodyPart可以包装文本，图片，附件
            MimeBodyPart body = new MimeBodyPart();
            //HTML正文
            body.setContent(content, "text/html; charset=UTF-8");
            MimeBodyPart image = new MimeBodyPart();
            File file=new File(url);
            image.setDataHandler(new DataHandler(new FileDataSource(file)));
            image.setContentID("guane.jpg");
            mp.addBodyPart(body);
            mp.addBodyPart(image);
            mp.setSubType("related");

            if(StringUtils.isNotBlank(fileStr)) {
                URLDataSource ur = new URLDataSource(new URL(fileStr));
                //添加图片&附件
                body = new MimeBodyPart();
                DataHandler dh = new DataHandler(ur);
                body.setDataHandler(dh);
                body.setFileName(MimeUtility.encodeText("法人身份证正面.jpg"));
                mp.addBodyPart(body);
            }

            if(StringUtils.isNotBlank(fileStr2)) {
                URLDataSource ur2 = new URLDataSource(new URL(fileStr2));
                //添加图片&附件
                body = new MimeBodyPart();
                //DataHandler dh2 = new DataHandler("邮件","text/plain;charset=UTF-8");
                DataHandler dh2 = new DataHandler(ur2);
                body.setDataHandler(dh2);
                body.setFileName(MimeUtility.encodeText("法人身份证背面.jpg"));
                mp.addBodyPart(body);
            }

            if(StringUtils.isNotBlank(fileStr3)) {
                URLDataSource ur3 = new URLDataSource(new URL(fileStr3));
                //添加图片&附件
                body = new MimeBodyPart();
                // DataHandler dh3 = new DataHandler("邮件","text/plain;charset=UTF-8");
                DataHandler dh3 = new DataHandler(ur3);
                body.setDataHandler(dh3);
                body.setFileName(MimeUtility.encodeText("法人手持身份证照片.jpg"));
                mp.addBodyPart(body);
            }

            if(StringUtils.isNotBlank(fileStr4)) {
                URLDataSource ur4 = new URLDataSource(new URL(fileStr4));
                //添加图片&附件
                body = new MimeBodyPart();
                // DataHandler dh4 = new DataHandler("邮件","text/plain;charset=UTF-8");
                DataHandler dh4 = new DataHandler(ur4);
                body.setDataHandler(dh4);
                body.setFileName(MimeUtility.encodeText("企业营业执照.jpg"));
                mp.addBodyPart(body);
            }

           if(StringUtils.isNotBlank(fileStr5)){
               URLDataSource ur5 = new URLDataSource(new URL(fileStr5));
               //添加图片&附件
               body = new MimeBodyPart();
               DataHandler dh5 = new DataHandler(ur5);
               body.setDataHandler(dh5);
               body.setFileName(MimeUtility.encodeText("开户许可证.jpg"));
               mp.addBodyPart(body);
           }

            //设置邮件内容
            mimeMessage.setContent(mp);

            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
            flag=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


}
