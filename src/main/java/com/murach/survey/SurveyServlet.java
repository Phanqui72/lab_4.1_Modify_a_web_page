package com.murach.survey;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SurveyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy tất cả các tham số từ form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String heardFrom = request.getParameter("heardFrom");
        String wantsUpdates = request.getParameter("wantsUpdates"); // Sẽ là "on" hoặc null
        String emailOK = request.getParameter("emailOK");           // Sẽ là "on" hoặc null
        String contactVia = request.getParameter("contactVia");

        // 2. Xử lý dữ liệu (ở đây chúng ta chỉ chuẩn hóa checkbox)
        // Nếu checkbox được tích, giá trị sẽ là "on". Nếu không, nó sẽ là null.
        String wantsUpdatesDisplay = (wantsUpdates != null) ? "Yes" : "No";
        String emailOKDisplay = (emailOK != null) ? "Yes" : "No";

        // 3. Thiết lập kiểu nội dung và mã hóa ký tự cho response
        response.setContentType("text/html;charset=UTF-8");

        // 4. Tạo trang HTML để trả về cho người dùng
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Survey Submission</title>");
            // Sử dụng lại CSS của bạn để trang kết quả trông đẹp hơn!
            out.println("<link rel='stylesheet' href='styles/survey.css'>");
            out.println("</head>");
            out.println("<body>");

            // Bọc nội dung trong một thẻ form để áp dụng style
            out.println("<form>");
            out.println("<h1>Thank You For Your Submission!</h1>");
            out.println("<p>Here is the information you entered:</p>");

            out.println("<h2>Your Information</h2>");
            out.println("<ul>");
            out.println("<li><b>First Name:</b> " + firstName + "</li>");
            out.println("<li><b>Last Name:</b> " + lastName + "</li>");
            out.println("<li><b>Email:</b> " + email + "</li>");
            out.println("<li><b>Date of Birth:</b> " + dateOfBirth + "</li>");
            out.println("</ul>");

            out.println("<h2>Preferences</h2>");
            out.println("<ul>");
            out.println("<li><b>How you heard about us:</b> " + heardFrom + "</li>");
            out.println("<li><b>Receive announcements:</b> " + wantsUpdatesDisplay + "</li>");
            out.println("<li><b>Receive email announcements:</b> " + emailOKDisplay + "</li>");
            out.println("<li><b>Contact method:</b> " + contactVia + "</li>");
            out.println("</ul>");

            out.println("<p><a href='index.html'>Back to Survey</a></p>");

            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}