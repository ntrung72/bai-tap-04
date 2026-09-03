package iotstar.vn.controllers;

import java.io.IOException;
import java.util.List;
import iotstar.vn.models.Category;
import iotstar.vn.models.User;
import iotstar.vn.services.CategoryService;
import iotstar.vn.services.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/admin/category/list"})
public class CategoryListController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    CategoryService cateService = new CategoryServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("account");
        if (user.getRoleid() != 1){
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }
        List<Category> cateList = cateService.getAll();
        req.setAttribute("cateList", cateList);
        req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
    }
}