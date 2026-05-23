package com.fruitshop.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * File: EmailUtil.java
 * Description: Utility class for sending emails using SMTP.
 */
public class EmailUtil {

    private static String host;
    private static String port;
    private static String username;
    private static String password;
    private static String fromName;

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = EmailUtil.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                props.load(input);
                host = props.getProperty("mail.host");
                port = props.getProperty("mail.port");
                username = props.getProperty("mail.username");
                password = props.getProperty("mail.password");
                fromName = props.getProperty("mail.from.name", "FruitStore");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send an email with the given subject and body.
     */
    public static boolean sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, fromName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Send password reset email.
     */
    public static boolean sendPasswordResetEmail(String to, String resetLink) {
        String subject = "Yêu cầu đặt lại mật khẩu - FruitStore";
        String body = String.format("""
            <html>
            <body>
                <h2>Xin chào!</h2>
                <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>
                <p>Nhấn vào link sau để đặt lại mật khẩu:</p>
                <a href="%s">Đặt lại mật khẩu</a>
                <p>Link sẽ hết hạn sau 1 giờ.</p>
                <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>
                <br>
                <p>Trân trọng,<br>FruitStore Team</p>
            </body>
            </html>
            """, resetLink);
        return sendEmail(to, subject, body);
    }

    /**
     * Send order confirmation email.
     */
    public static boolean sendOrderConfirmation(String to, String orderId, String details) {
        String subject = String.format("Xác nhận đơn hàng #%s - FruitStore", orderId);
        String body = String.format("""
            <html>
            <body>
                <h2>Cảm ơn bạn đã đặt hàng!</h2>
                <p>Mã đơn hàng: <strong>%s</strong></p>
                %s
                <p>Chúng tôi sẽ gửi email cập nhật khi đơn hàng được xử lý.</p>
                <br>
                <p>Trân trọng,<br>FruitStore Team</p>
            </body>
            </html>
            """, orderId, details);
        return sendEmail(to, subject, body);
    }
}
