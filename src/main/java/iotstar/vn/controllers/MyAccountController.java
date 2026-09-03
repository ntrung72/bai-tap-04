package iotstar.vn.controllers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;
import iotstar.vn.models.User;
import iotstar.vn.services.UserService;
import iotstar.vn.services.UserServiceImpl;
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
@WebServlet(urlPatterns="/member/myaccount")
@MultipartConfig(
    fileSizeThreshold=1024*1024,
    maxFileSize=5*1024*1024,
    maxRequestSize=6*1024*1024
)
public class MyAccountController extends HttpServlet {
    private static final long serialVersionUID=1L;
    private final UserService userService=new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("account")==null) {
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        User sessionUser=(User)session.getAttribute("account");
        User user=userService.getById(sessionUser.getId());
        if(user==null) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        session.setAttribute("account", user);
        req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("account")==null) {
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        User currentUser=(User)session.getAttribute("account");
        String fullname=trimToNull(req.getParameter("fullname"));
        String phone=trimToNull(req.getParameter("phone"));
        if(fullname==null) {
            req.setAttribute("alert", "Họ tên không được để trống.");
            req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
            return;
        }
        if(phone==null) {
            req.setAttribute("alert", "Số điện thoại không được để trống.");
            req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
            return;
        }
        if(!phone.matches("^[0-9]{9,11}$")) {
            req.setAttribute("alert", "Số điện thoại chỉ gồm 9 đến 11 chữ số.");
            req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
            return;
        }
        if(userService.checkExistPhone(phone, currentUser.getId())) {
            req.setAttribute("alert", "Số điện thoại này đã được sử dụng bởi tài khoản khác.");
            req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
            return;
        }
        Part imagePart=req.getPart("image");
        String oldAvatar=currentUser.getAvatar();
        String newAvatar=oldAvatar;
        String uploadedAvatar=null;
        try {
            if(imagePart!=null && imagePart.getSize()>0) {
                uploadedAvatar=saveAvatar(imagePart);
                newAvatar=uploadedAvatar;
            }
            User updatedUser=userService.updateProfile(currentUser.getId(), fullname, phone, newAvatar);
            if(updatedUser==null) {
                deleteAvatar(uploadedAvatar);
                req.setAttribute("alert", "Cập nhật thông tin thất bại. Vui lòng thử lại.");
                req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
                return;
            }
            if(uploadedAvatar!=null && oldAvatar!=null && !oldAvatar.isBlank()) {
                deleteAvatar(oldAvatar);
            }
            session.setAttribute("account", updatedUser);
            session.setAttribute("profileSuccess", "Cập nhật profile thành công.");
            resp.sendRedirect(req.getContextPath()+"/member/myaccount");
        } catch(IllegalArgumentException e) {
            deleteAvatar(uploadedAvatar);
            req.setAttribute("alert", e.getMessage());
            req.getRequestDispatcher("/views/myaccount.jsp").forward(req, resp);
        }
    }
    private String saveAvatar(Part imagePart) throws IOException {
        String originalFileName=imagePart.getSubmittedFileName();
        if(originalFileName==null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("File ảnh không hợp lệ.");
        }
        String contentType=imagePart.getContentType();
        if(contentType==null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("Chỉ được upload file hình ảnh.");
        }
        String extension=getExtension(originalFileName).toLowerCase(Locale.ROOT);
        if(!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png") && !extension.equals(".gif") && !extension.equals(".webp")) {
            throw new IllegalArgumentException("Chỉ chấp nhận ảnh JPG, JPEG, PNG, GIF hoặc WEBP.");
        }
        String fileName=UUID.randomUUID()+extension;
        File avatarDirectory=new File(Constant.DIR, "avatar");
        if(!avatarDirectory.exists() && !avatarDirectory.mkdirs()) {
            throw new IOException("Không thể tạo thư mục lưu avatar.");
        }
        File destination=new File(avatarDirectory, fileName);
        try(InputStream input=imagePart.getInputStream()) {
            Files.copy(input, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return "avatar/"+fileName;
    }
    private void deleteAvatar(String relativePath) {
        if(relativePath==null || relativePath.isBlank() || !relativePath.startsWith("avatar/")) {
            return;
        }
        try {
            File root=new File(Constant.DIR).getCanonicalFile();
            File file=new File(root, relativePath).getCanonicalFile();
            if(file.toPath().startsWith(root.toPath())) {
                Files.deleteIfExists(file.toPath());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private String getExtension(String fileName) {
        int dotIndex=fileName.lastIndexOf('.');
        return dotIndex>=0 ? fileName.substring(dotIndex) : "";
    }
    private String trimToNull(String value) {
        if(value==null) {
            return null;
        }
        String trimmed=value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}