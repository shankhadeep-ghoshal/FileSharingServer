package FilterPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

@WebServlet(name = "ServletDataSetter")
public class ServletDataSetter extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        show_tables(request);
        request.getRequestDispatcher("/LoginLanding.jsp").forward(request,response);
    }

    private void show_tables(HttpServletRequest request) throws ServletException {
        String session_name = request.getSession().getAttribute("userLogged").toString();
        String path_name = "/users/"+session_name+"/";
        if (path_name.isEmpty()) request.setAttribute("urlData", "Files not uploaded yet");
        else {
            ArrayList<String> path_locations = new ArrayList<>();
            for(File temp : requireNonNull(new File(path_name).listFiles())) {
                if (temp.isFile()) {
                    String[] extractName = temp.getPath().split(session_name +"\\\\");
                    path_locations.add(extractName[1]);
                } else {
                    /* TODO : Some error message here */
                    throw new ServletException("File not found");
                }
            }
            request.setAttribute("urlData",path_locations);
        }
    }
}
