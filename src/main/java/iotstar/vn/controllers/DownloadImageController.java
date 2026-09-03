package iotstar.vn.controllers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import iotstar.vn.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet(urlPatterns="/image")
public class DownloadImageController extends HttpServlet {
    private static final long serialVersionUID=1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName=req.getParameter("fname");
        if(fileName==null || fileName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String normalized=fileName.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        if(new File(normalized).isAbsolute()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        File rootDirectory=new File(Constant.DIR).getCanonicalFile();
        File file=new File(rootDirectory, normalized).getCanonicalFile();
        Path rootPath=rootDirectory.toPath();
        Path filePath=file.toPath();
        if(!filePath.startsWith(rootPath)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Path relativePath=rootPath.relativize(filePath);
        if(relativePath.getNameCount()<2) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String firstFolder=relativePath.getName(0).toString();
        if(!"product".equals(firstFolder) && !"category".equals(firstFolder) && !"avatar".equals(firstFolder)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if(!file.exists() || !file.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String mimeType=getServletContext().getMimeType(file.getName());
        if(mimeType==null || !mimeType.startsWith("image/")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }
        resp.setContentType(mimeType);
        resp.setContentLengthLong(file.length());
        try(FileInputStream input=new FileInputStream(file); ServletOutputStream output=resp.getOutputStream()) {
            byte[] buffer=new byte[4096];
            int length;
            while((length=input.read(buffer))!=-1) {
                output.write(buffer, 0, length);
            }
        }
    }
}