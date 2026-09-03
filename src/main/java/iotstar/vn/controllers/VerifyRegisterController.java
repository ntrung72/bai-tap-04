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
@WebServlet(urlPatterns="/verify-register")
public class VerifyRegisterController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("registerEmail")==null || session.getAttribute("registerOtp")==null){
            resp.sendRedirect(req.getContextPath()+"/register");
            return;
        }
        req.setAttribute("email", session.getAttribute("registerEmail"));
        req.getRequestDispatcher(Constant.Path.VERIFY_REGISTER).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("registerOtp")==null){
            resp.sendRedirect(req.getContextPath()+"/register");
            return;
        }
        String otp=req.getParameter("otp");
        String savedOtp=(String)session.getAttribute("registerOtp");
        Long expired=(Long)session.getAttribute("registerOtpExpired");
        if(expired==null || System.currentTimeMillis()>expired){
            clearRegisterSession(session);
            req.setAttribute("alert", "OTP đã hết hạn. Vui lòng đăng ký lại để nhận mã mới!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        if(otp==null || !savedOtp.equals(otp.trim())){
            req.setAttribute("alert", "Mã OTP không đúng!");
            req.setAttribute("email", session.getAttribute("registerEmail"));
            req.getRequestDispatcher(Constant.Path.VERIFY_REGISTER).forward(req, resp);
            return;
        }
        String username=(String)session.getAttribute("registerUsername");
        String password=(String)session.getAttribute("registerPassword");
        String email=(String)session.getAttribute("registerEmail");
        String fullname=(String)session.getAttribute("registerFullname");
        String phone=(String)session.getAttribute("registerPhone");
        UserService service=new UserServiceImpl();
        boolean success=service.register(username, password, email, fullname, phone);
        if(!success){
            clearRegisterSession(session);
            req.setAttribute("alert", "Không thể tạo tài khoản. Thông tin có thể đã tồn tại hoặc cơ sở dữ liệu đang gặp lỗi!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        clearRegisterSession(session);
        session.setAttribute("success", "Kích hoạt tài khoản thành công. Bạn có thể đăng nhập ngay!");
        resp.sendRedirect(req.getContextPath()+"/login");
    }
    private void clearRegisterSession(HttpSession session){
        session.removeAttribute("registerUsername");
        session.removeAttribute("registerPassword");
        session.removeAttribute("registerEmail");
        session.removeAttribute("registerFullname");
        session.removeAttribute("registerPhone");
        session.removeAttribute("registerOtp");
        session.removeAttribute("registerOtpExpired");
    }
}