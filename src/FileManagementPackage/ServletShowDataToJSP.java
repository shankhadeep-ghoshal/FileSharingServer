package FileManagementPackage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.*;

@WebServlet(name = "ServletShowDataToJSP")
public class ServletShowDataToJSP extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        show_tables(request,response);
    }

    private void show_tables(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        String session_name = request.getSession().getAttribute("userLogged").toString();
        String path_name = "/users/"+session_name+"/";

        ArrayList<String> path_locations = new ArrayList<>();
        /* TODO : Some error message here */
        stream(Objects.requireNonNull(new File(path_name).listFiles())).forEach(temp -> {
            if (temp.isFile()) {
                path_locations.add(temp.getPath());
            } else {
                /* TODO : Some error message here */

            }
        });
        request.setAttribute("urlData",path_locations);
        response.sendRedirect(request.getContextPath()+"/users/"+request.getSession().getAttribute("userLogged").toString());
    }
}
