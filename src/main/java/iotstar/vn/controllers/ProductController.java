package iotstar.vn.controllers;

import java.io.IOException;
import java.util.List;
import iotstar.vn.models.Product;
import iotstar.vn.services.ProductService;
import iotstar.vn.services.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns={"/product"})
public class ProductController extends HttpServlet{
    private static final long serialVersionUID=1L;
    private static final int PAGE_SIZE=6;
    private final ProductService productService=new ProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int currentPage=1;
        String pageParam=req.getParameter("page");
        if(pageParam!=null && !pageParam.isBlank()){
            try{
                currentPage=Integer.parseInt(pageParam);
            }catch(NumberFormatException e){
                currentPage=1;
            }
        }
        int totalProducts=productService.count();
        int totalPages=(int)Math.ceil((double)totalProducts/PAGE_SIZE);
        if(totalPages==0){
            totalPages=1;
        }
        if(currentPage<1){
            currentPage=1;
        }
        if(currentPage>totalPages){
            currentPage=totalPages;
        }
        List<Product> products=productService.getAll(currentPage, PAGE_SIZE);
        req.setAttribute("products", products);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalProducts", totalProducts);
        req.getRequestDispatcher("/views/product.jsp").forward(req, resp);
    }
}