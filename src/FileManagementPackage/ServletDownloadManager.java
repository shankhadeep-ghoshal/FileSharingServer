package FileManagementPackage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletDownloadManager")
public class ServletDownloadManager extends HttpServlet {
    private FileInputStream fileInputStream;
    private PrintWriter writer;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        manageDownloads(request,response);
    }
    private void manageDownloads(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName");
        String userName = request.getSession().getAttribute("userLogged").toString();
        String pathName = "/users/" + userName+"/";
        File file = new File(pathName,fileName);
        writer = response.getWriter();
        if (file.exists()){
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename=\"" + file.getName() + "\"");
            fileInputStream = new FileInputStream(file);
            int i;
            while ((i=fileInputStream.read()) != -1) writer.write(i);
        }else writer.print("File not found");
        response.sendRedirect(request.getContextPath()+"/users/"+userName);
    }

    @Override
    public void destroy() {
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
    }
}
