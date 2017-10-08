package AuthPack;

import UtilityClasses.CryptoPackage.EncryptClass;
import UtilityClasses.ExceptionHandler;
import UtilityClasses.JDBCConnectorClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

@WebServlet(name = "/Login/*")
public class ServletLogin extends HttpServlet {
    private Connection c;

    private String imiya, kyuch;
    private byte[] kodom;

    private static final String query = "SELECT user_name, `key`, `nitrate` from usr_det_log_table WHERE user_name=? " +
            ";";
    private static final String message = "Username or password not correct. Please try again.";


    @Override
    public void init() {
        c = JDBCConnectorClass.getConn();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            login_doIT(request,response);
        } catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        c.close();
    }

    @Override
    public void destroy() {
        try {
            c.close();
        } catch (SQLException e) {
            ExceptionHandler.HandleExc(e,this.getClass(),Level.SEVERE);
        }
    }

    private void login_doIT(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException, ServletException, IOException {
        String userInput = request.getParameter("user_name");
        String pass = request.getParameter("pass");
        if((userInput == null) || (pass == null) || userInput.isEmpty() || pass.isEmpty() || userInput.contentEquals
                (" ") || pass.contentEquals(" ")) {
            response.sendRedirect(request.getContextPath());
        } else{
            PreparedStatement pst = c.prepareStatement(query);
            pst.setString(1,userInput);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                imiya = rs.getString("user_name");
                kyuch = rs.getString("key");
                kodom = rs.getBytes("nitrate");
            }
            EncryptClass instance = new EncryptClass(2048,100000);
            if(instance.chkPass(pass,kyuch,kodom) && imiya.equals(userInput)){
                HttpSession session = request.getSession();
                session.setAttribute("userLogged",userInput);
                request.setAttribute("title",userInput);
                response.sendRedirect(request.getContextPath()+"/user/"+imiya);
            } else {
                request.setAttribute("message", message);
                request.getRequestDispatcher("/index.jsp").include(request,response);
            }
        }

    }
}
