package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/verify-forgot-otp")
public class VerifyForgotOtpController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("forgotEmail") == null || session.getAttribute("forgotOtp") == null){
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        req.setAttribute("email", session.getAttribute("forgotEmail"));
        req.getRequestDispatcher(Constant.Path.VERIFY_FORGOT_OTP).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("forgotOtp") == null){
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        String otp = req.getParameter("otp");
        String savedOtp = (String) session.getAttribute("forgotOtp");
        Long expired = (Long) session.getAttribute("forgotOtpExpired");
        if(expired == null || System.currentTimeMillis() > expired){
            clearForgotSession(session);
            req.setAttribute("alert", "OTP đã hết hạn. Vui lòng gửi yêu cầu quên mật khẩu lại!");
            req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
            return;
        }
        if(otp == null || !savedOtp.equals(otp.trim())){
            req.setAttribute("alert", "Mã OTP không đúng!");
            req.setAttribute("email", session.getAttribute("forgotEmail"));
            req.getRequestDispatcher(Constant.Path.VERIFY_FORGOT_OTP).forward(req, resp);
            return;
        }
        session.setAttribute("forgotOtpVerified", true);
        session.removeAttribute("forgotOtp");
        session.removeAttribute("forgotOtpExpired");
        resp.sendRedirect(req.getContextPath() + "/reset-password");
    }
    private void clearForgotSession(HttpSession session){
        session.removeAttribute("forgotEmail");
        session.removeAttribute("forgotOtp");
        session.removeAttribute("forgotOtpExpired");
        session.removeAttribute("forgotOtpVerified");
    }
}