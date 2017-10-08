package FileManagementPackage;

import UtilityClasses.ExceptionHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.logging.Level;

import static java.util.Objects.requireNonNull;

@WebServlet(name = "FileManagementPackage.ServletFileUploadManager")
public class ServletFileUploadManager extends HttpServlet {
    private static final String[] extensionList = {"pdf","doc","rtf","docx","rrt","pptx","xls","xlsx","jpg","jpeg"
        ,"png"};
    private static final String message = "File/s not uploaded because of wrong file type";
    private static final String message2 = "Directory couldn't be created";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        manageUploads(request,response);
    }


    private void manageUploads(HttpServletRequest request,HttpServletResponse response){
        ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
        File demoDir = new File("/users/"+request.getSession().getAttribute("userLogged"));
        try {
            List<FileItem> files = fileUpload.parseRequest(request);
            for(FileItem file : files){
                String nnm = file.getName();
                if(nnm!=null){
                    nnm = FilenameUtils.getName(nnm);
                }
                String[] extName = requireNonNull(nnm).split("\\.");
                if(checkValidity(extName[1])){
                    boolean idDirExists = demoDir.exists() || demoDir.mkdirs();
                    if(idDirExists) writeFile(demoDir,nnm,file);
                    else request.setAttribute("errorMessage",message2);
                }else{
                    request.setAttribute("errorMessage",message);
                }
            }
            response.sendRedirect(request.getContextPath()+"/user/"+request.getSession().getAttribute("userLogged"));
        } catch (Exception e) {
            ExceptionHandler.HandleExc(e,this.getClass(), Level.SEVERE);
        }
    }

    private void writeFile(File demoDir,String nnm,FileItem fil) throws Exception {
        String path = demoDir.getPath()+"/";
        File tempDir  = new File(path+nnm);
        fil.write(tempDir);
        System.out.println("Success");
    }

    private boolean checkValidity(String extension){
        for(String itr : extensionList){
            if(itr.contentEquals(extension))return true;
        }
        return false;
    }

}
