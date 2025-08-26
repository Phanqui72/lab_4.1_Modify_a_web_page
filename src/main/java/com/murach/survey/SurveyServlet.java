package com.murach.survey;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Properties;

// Thêm các import cần thiết cho việc gửi mail
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class SurveyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy dữ liệu từ form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // 2. Gửi email xác nhận
        sendConfirmationEmail(email, firstName);

        // 3. Chuyển hướng đến trang cảm ơn
        response.sendRedirect("thankyou.html");
    }

    private void sendConfirmationEmail(String toEmail, String firstName) {
        // --- THAY THẾ THÔNG TIN CỦA BẠN VÀO ĐÂY ---
        final String fromEmail = "cuoikyiot.sangthu6@gmail.com"; // Email bạn dùng để gửi
        final String password = "ktts dvxu dxje vawm"; // Mật khẩu ứng dụng 16 ký tự
        // ------------------------------------------

        // Cấu hình các thuộc tính cho SMTP server của Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // Bật xác thực
        props.put("mail.smtp.starttls.enable", "true"); // Bật STARTTLS

        // Tạo một đối tượng Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        // Tạo một session mail
        Session session = Session.getInstance(props, auth);

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Người gửi
            message.setFrom(new InternetAddress(fromEmail));

            // Người nhận
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Tiêu đề Email
            message.setSubject("Thank you for completing the survey!");

            // Nội dung Email (dạng HTML)
            String emailBody = "<h1>Hello " + firstName + ",</h1>"
                    + "<p>We have received your survey information. Thank you for your time!</p>"
                    + "<br>"
                    + "<p>My name is PHAN TRONG QUI and Tho`s student,</p>"
                    + "<p>Trân trọng,</p>"
                    + "<p><b>Murach's Java Servlets Team</b></p>";
            message.setContent(emailBody, "text/html; charset=UTF-8");

            // Gửi email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            // In ra lỗi nếu có sự cố
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển tiếp yêu cầu GET đến doPost để xử lý
        doPost(request, response);
    }
}