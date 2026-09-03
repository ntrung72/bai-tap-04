package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.models.User;
import iotstar.vn.services.UserService;
import iotstar.vn.services.UserServiceImpl;
import iotstar.vn.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns="/login")
public class LoginController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session=req.getSession(false);
        if(session!=null && session.getAttribute("account")!=null){
            resp.sendRedirect(req.getContextPath()+"/waiting");
            return;
        }
        Cookie[] cookies=req.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(Constant.COOKIE_REMEMBER.equals(cookie.getName())){
                    req.setAttribute("rememberedUsername", cookie.getValue());
                    req.setAttribute("rememberChecked", true);
                    break;
                }
            }
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        boolean isRememberMe="on".equals(req.getParameter("remember"));
        if(username==null || username.isBlank() || password==null || password.isBlank()){
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
            req.setAttribute("rememberedUsername", username);
            req.setAttribute("rememberChecked", isRememberMe);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }
        username=username.trim();
        UserService service=new UserServiceImpl();
        User user=service.login(username, password);
        if(user!=null){
            HttpSession session=req.getSession(true);
            session.setAttribute("account", user);
            if(isRememberMe){
                saveRememberMe(req, resp, username);
            }else{
                deleteRememberMe(req, resp);
            }
            resp.sendRedirect(req.getContextPath()+"/waiting");
            return;
        }
        req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
        req.setAttribute("rememberedUsername", username);
        req.setAttribute("rememberChecked", isRememberMe);
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }
    private void saveRememberMe(HttpServletRequest req, HttpServletResponse resp, String username){
        Cookie cookie=new Cookie(Constant.COOKIE_REMEMBER, username);
        cookie.setMaxAge(30*60);
        cookie.setHttpOnly(true);
        cookie.setSecure(req.isSecure());
        cookie.setPath(getCookiePath(req));
        resp.addCookie(cookie);
    }
    private void deleteRememberMe(HttpServletRequest req, HttpServletResponse resp){
        Cookie cookie=new Cookie(Constant.COOKIE_REMEMBER, "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(req.isSecure());
        cookie.setPath(getCookiePath(req));
        resp.addCookie(cookie);
    }
    private String getCookiePath(HttpServletRequest req){
        String contextPath=req.getContextPath();
        return contextPath==null || contextPath.isEmpty()?"/":contextPath;
    }
}