package email;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lidu
 * @CreateDate 2018/9/12
 * @Description 邮件发送工具类
 */
public class MailUtils {
    private static final String H_USERNAME = "hmas@workway.com.cn";
    private static final String H_PASSWORD = "WorkWay135";
    private static final String H_HOST = "smtp.qiye.163.com";//smtp.workway.com.cn
    private static final String H_FROM = "和美服务<hmas@workway.com.cn>";
    private static final Integer H_PORT = 465 ;
    private static final String CHARSET = "UTF-8";

    /**
     * @author lidu
     * @createDate 2015/7/20 9:30
     * @description 邮件发送方法
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param receivers 接收人
     * @param ccReceivers 抄送人
     * @param files 附件
     */
    public static void sendMail(String subject, String content, List<String> receivers, List<String> ccReceivers, List<File> files) throws EmailException {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(H_HOST);
        email.setSmtpPort(H_PORT);
        email.setCharset(CHARSET);
        email.setSSLOnConnect(true);
        email.setAuthentication(H_USERNAME, H_PASSWORD);
        {
            email.setFrom(H_FROM, "和美服务");
            for (String receive : receivers) {
                email.addTo(receive);
            }
            if (ccReceivers != null && ccReceivers.size() > 0) {
                for (String reveive : ccReceivers) {
                    email.addCc(reveive);
                }
            }
            email.setSubject(subject);
            email.setMsg(content);
            if (files != null && files.size()>0) {
                for(int i=0; i<files.size(); i++){
                    EmailAttachment att = new EmailAttachment();
                    att.setName(files.get(i).getName());
                    att.setPath(files.get(i).getPath());
                    att.setDescription(files.get(i).getName());
                    email.attach(att);
                }
            }
            email.send();
        }

    }

    /**
     * @Author lidu
     * @CreateDate 2018/9/12 14:23
     * @Description 判断输入的字符串是否是规范的邮件地址
     */
    public static boolean isEmail(String email){
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        String[] mails = email.split(";");
        String regex = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";
        Pattern pt = Pattern.compile(regex);
        for (String mail : mails) {
            Matcher mat = pt.matcher(mail);
            if (!mat.matches()) {
                return false;
            }
        }
        return true;
    }
}
