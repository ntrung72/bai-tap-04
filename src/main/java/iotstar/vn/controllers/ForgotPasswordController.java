package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.models.User;
import iotstar.vn.services.UserService;
import iotstar.vn.services.UserServiceImpl;
import iotstar.vn.utils.Constant;
import iotstar.vn.utils.EmailUtil;
import iotstar.vn.utils.OtpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        if(email == null || email.isBlank()){
            req.setAttribute("alert", "Vui lòng nhập email!");
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }
        UserService service = new UserServiceImpl();
        User user = service.getByEmail(email.trim());
        if(user == null){
            req.setAttribute("alert", "Email không tồn tại trong hệ thống!");
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }
        String otp = OtpUtil.generateOtp();
        if(!EmailUtil.sendOtp(user.getEmail(), otp, "quên mật khẩu")){
            req.setAttribute("alert", "Không thể gửi OTP. Hãy kiểm tra cấu hình email của project!");
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("forgotEmail", user.getEmail());
        session.setAttribute("forgotOtp", otp);
        session.setAttribute("forgotOtpExpired", System.currentTimeMillis() + Constant.OTP_EXPIRE_MILLIS);
        session.removeAttribute("forgotOtpVerified");
        resp.sendRedirect(req.getContextPath() + "/verify-forgot-otp");
    }
}