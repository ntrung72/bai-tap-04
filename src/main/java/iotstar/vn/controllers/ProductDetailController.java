package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.models.Product;
import iotstar.vn.services.ProductService;
import iotstar.vn.services.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns={"/product/detail"})
public class ProductDetailController extends HttpServlet{
    private static final long serialVersionUID=1L;
    private final ProductService productService=new ProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int id;
        try{
            id=Integer.parseInt(req.getParameter("id"));
        }catch(Exception e){
            resp.sendRedirect(req.getContextPath()+"/product");
            return;
        }
        Product product=productService.get(id);
        if(product==null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("product", product);
        req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
    }
}