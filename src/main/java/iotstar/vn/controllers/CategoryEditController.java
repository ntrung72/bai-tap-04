package iotstar.vn.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import iotstar.vn.models.Category;
import iotstar.vn.models.User;
import iotstar.vn.services.CategoryService;
import iotstar.vn.services.CategoryServiceImpl;
import iotstar.vn.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/admin/category/edit"})
@MultipartConfig
public class CategoryEditController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private final CategoryService cateService = new CategoryServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (!checkAdmin(req, resp)){
            return;
        }
        String id = req.getParameter("id");
        if (id == null || id.isBlank()){
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }
        Category category;
        try{
            category = cateService.get(Integer.parseInt(id));
        } catch (NumberFormatException e){
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }
        if (category == null){
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }
        req.setAttribute("category", category);
        req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (!checkAdmin(req, resp)){
            return;
        }
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        int id;
        try{
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e){
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            return;
        }
        String name = req.getParameter("name");
        Part iconPart = req.getPart("icon");
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        String newIcon = saveImage(iconPart);
        category.setIcon(newIcon);
        cateService.edit(category);
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
    
    private String saveImage(Part iconPart) throws IOException{
        if (iconPart == null || iconPart.getSize() <= 0){
            return null;
        }
        String originalFileName = iconPart.getSubmittedFileName();
        if (originalFileName == null || originalFileName.isBlank()){
            return null;
        }
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0){
            extension = originalFileName.substring(dotIndex);
        }
        String fileName = System.currentTimeMillis() + extension;
        File categoryDirectory = new File(Constant.DIR, "category");
        if (!categoryDirectory.exists()){
            categoryDirectory.mkdirs();
        }
        File destination = new File(categoryDirectory, fileName);
        Files.copy(iconPart.getInputStream(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return "category/" + fileName;
    }
    
    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        User user = (User) session.getAttribute("account");
        if (user.getRoleid() != 1){
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return false;
        }
        return true;
    }
}