package iotstar.vn.controllers;

import java.io.IOException;
import java.util.List;
import iotstar.vn.models.Product;
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
@WebServlet(urlPatterns={"/admin/product/list"})
public class ProductListController extends HttpServlet{
    private static final long serialVersionUID=1L;
    private final ProductService productService=new ProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if(!checkAdmin(req, resp)){
            return;
        }
        List<Product> productList=productService.getAll();
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("/views/admin/list-product.jsp").forward(req, resp);
    }
    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("account")==null){
            resp.sendRedirect(req.getContextPath()+"/login");
            return false;
        }
        User user=(User)session.getAttribute("account");
        if(user.getRoleid()!=1){
            resp.sendRedirect(req.getContextPath()+"/waiting");
            return false;
        }
        return true;
    }
}