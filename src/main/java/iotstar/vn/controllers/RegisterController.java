package iotstar.vn.controllers;

import java.io.IOException;
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
@WebServlet(urlPatterns="/register")
public class RegisterController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session=req.getSession(false);
        if(session!=null && session.getAttribute("account")!=null){
            resp.sendRedirect(req.getContextPath()+"/waiting");
            return;
        }
        req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String repassword=req.getParameter("repassword");
        String email=req.getParameter("email");
        String fullname=req.getParameter("fullname");
        String phone=req.getParameter("phone");
        UserService service=new UserServiceImpl();
        if(username==null || username.isBlank() || password==null || password.isBlank() || email==null || email.isBlank() || fullname==null || fullname.isBlank() || phone==null || phone.isBlank()){
            req.setAttribute("alert", "Vui lòng nhập đầy đủ thông tin!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        username=username.trim();
        email=email.trim();
        fullname=fullname.trim();
        phone=phone.trim();
        if(!password.equals(repassword)){
            req.setAttribute("alert", "Mật khẩu nhập lại không đúng!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        if(service.checkExistEmail(email)){
            req.setAttribute("alert", "Email đã tồn tại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        if(service.checkExistUsername(username)){
            req.setAttribute("alert", "Tài khoản đã tồn tại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        if(service.checkExistPhone(phone)){
            req.setAttribute("alert", "Số điện thoại đã tồn tại!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        String otp=OtpUtil.generateOtp();
        if(!EmailUtil.sendOtp(email, otp, "kích hoạt tài khoản")){
            req.setAttribute("alert", "Không thể gửi OTP. Hãy kiểm tra cấu hình email của project!");
            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
            return;
        }
        HttpSession session=req.getSession(true);
        session.setAttribute("registerUsername", username);
        session.setAttribute("registerPassword", password);
        session.setAttribute("registerEmail", email);
        session.setAttribute("registerFullname", fullname);
        session.setAttribute("registerPhone", phone);
        session.setAttribute("registerOtp", otp);
        session.setAttribute("registerOtpExpired", System.currentTimeMillis()+Constant.OTP_EXPIRE_MILLIS);
        resp.sendRedirect(req.getContextPath()+"/verify-register");
    }
}