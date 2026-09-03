package iotstar.vn.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import iotstar.vn.models.Category;
import iotstar.vn.models.Product;
import iotstar.vn.models.User;
import iotstar.vn.services.CategoryService;
import iotstar.vn.services.CategoryServiceImpl;
import iotstar.vn.services.ProductService;
import iotstar.vn.services.ProductServiceImpl;
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
@WebServlet(urlPatterns={"/admin/product/add"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50)
public class ProductAddController extends HttpServlet{
    private static final long serialVersionUID=1L;
    private final ProductService productService=new ProductServiceImpl();
    private final CategoryService categoryService=new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if(!checkAdmin(req, resp)){
            return;
        }
        req.setAttribute("cateList", categoryService.getAll());
        req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if(!checkAdmin(req, resp)){
            return;
        }
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name=req.getParameter("name");
        String description=req.getParameter("description");
        BigDecimal price;
        int categoryId;
        try{
            price=new BigDecimal(req.getParameter("price"));
            categoryId=Integer.parseInt(req.getParameter("categoryId"));
        }catch(Exception e){
            forwardWithError(req, resp, "Giá hoặc danh mục không hợp lệ!");
            return;
        }
        if(name==null || name.isBlank()){
            forwardWithError(req, resp, "Tên sản phẩm không được để trống!");
            return;
        }
        if(price.compareTo(BigDecimal.ZERO)<0){
            forwardWithError(req, resp, "Giá sản phẩm không được nhỏ hơn 0!");
            return;
        }
        Category category=categoryService.get(categoryId);
        if(category==null){
            forwardWithError(req, resp, "Danh mục không tồn tại!");
            return;
        }
        Part imagePart;
        try{
            imagePart=req.getPart("image");
        }catch(IllegalStateException e){
            forwardWithError(req, resp, "Ảnh vượt quá kích thước cho phép 10 MB!");
            return;
        }
        if(imagePart!=null && imagePart.getSize()>0){
            String contentType=imagePart.getContentType();
            if(contentType==null || !contentType.startsWith("image/")){
                forwardWithError(req, resp, "File tải lên phải là hình ảnh!");
                return;
            }
        }
        String image=saveImage(imagePart);
        Product product=new Product();
        product.setName(name.trim());
        product.setImage(image);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);
        productService.insert(product);
        resp.sendRedirect(req.getContextPath()+"/admin/product/list");
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException{
        req.setAttribute("error", error);
        req.setAttribute("cateList", categoryService.getAll());
        req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
    }

    private String saveImage(Part imagePart) throws IOException{
        if(imagePart==null || imagePart.getSize()<=0){
            return null;
        }
        String originalFileName=imagePart.getSubmittedFileName();
        if(originalFileName==null || originalFileName.isBlank()){
            return null;
        }
        originalFileName=new File(originalFileName).getName();
        String extension="";
        int dotIndex=originalFileName.lastIndexOf('.');
        if(dotIndex>=0){
            extension=originalFileName.substring(dotIndex);
        }
        String fileName=System.currentTimeMillis()+extension;
        File productDirectory=new File(Constant.DIR, "product");
        if(!productDirectory.exists()){
            productDirectory.mkdirs();
        }
        File destination=new File(productDirectory, fileName);
        imagePart.write(destination.getAbsolutePath());
        return "product/"+fileName;
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