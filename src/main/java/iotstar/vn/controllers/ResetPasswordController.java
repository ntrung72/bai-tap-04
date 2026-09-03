package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.services.UserService;
import iotstar.vn.services.UserServiceImpl;
import iotstar.vn.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if(!canReset(session)){
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if(!canReset(session)){
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        if(password == null || password.isBlank() || repassword == null || repassword.isBlank()){
            req.setAttribute("alert", "Vui lòng nhập đầy đủ mật khẩu mới!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }
        if(!password.equals(repassword)){
            req.setAttribute("alert", "Mật khẩu nhập lại không đúng!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }
        String email = (String) session.getAttribute("forgotEmail");
        UserService service = new UserServiceImpl();
        if(!service.updatePassword(email, password)){
            req.setAttribute("alert", "Không thể đổi mật khẩu. Vui lòng thử lại!");
            req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
            return;
        }
        clearForgotSession(session);
        session.setAttribute("success", "Đổi mật khẩu thành công. Bạn có thể đăng nhập bằng mật khẩu mới!");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
    private boolean canReset(HttpSession session){
        return session != null && session.getAttribute("forgotEmail") != null && Boolean.TRUE.equals(session.getAttribute("forgotOtpVerified"));
    }
    private void clearForgotSession(HttpSession session){
        session.removeAttribute("forgotEmail");
        session.removeAttribute("forgotOtp");
        session.removeAttribute("forgotOtpExpired");
        session.removeAttribute("forgotOtpVerified");
    }
}