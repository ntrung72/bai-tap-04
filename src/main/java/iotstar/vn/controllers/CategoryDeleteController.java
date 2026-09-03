package iotstar.vn.controllers;

import java.io.IOException;
import iotstar.vn.models.User;
import iotstar.vn.services.CategoryService;
import iotstar.vn.services.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns={"/admin/category/delete"})
public class CategoryDeleteController extends HttpServlet{
    private static final long serialVersionUID=1L;
    private final CategoryService cateService=new CategoryServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if(!checkAdmin(req, resp)){
            return;
        }
        HttpSession session=req.getSession();
        String id=req.getParameter("id");
        if(id==null || id.isBlank()){
            session.setAttribute("categoryDeleteError", "Danh mục không hợp lệ!");
            resp.sendRedirect(req.getContextPath()+"/admin/category/list");
            return;
        }
        try{
            cateService.delete(Integer.parseInt(id));
        }catch(NumberFormatException e){
            session.setAttribute("categoryDeleteError", "Danh mục không hợp lệ!");
        }catch(IllegalStateException e){
            session.setAttribute("categoryDeleteError", e.getMessage());
        }catch(RuntimeException e){
            session.setAttribute("categoryDeleteError", "Không thể xóa danh mục. Vui lòng thử lại!");
        }
        resp.sendRedirect(req.getContextPath()+"/admin/category/list");
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