package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.models.User;
import iotstar.vn.services.ProductService;
import iotstar.vn.services.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns={"/manager/home"})
public class ManagerHomeController extends HttpServlet{
    private static final long serialVersionUID=1L;
    private final ProductService productService=new ProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("account")==null){
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        User user=(User)session.getAttribute("account");
        if(user.getRoleid()!=2){
            resp.sendRedirect(req.getContextPath()+"/home");
            return;
        }
        req.setAttribute("latestProducts", productService.getLatest(10));
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}