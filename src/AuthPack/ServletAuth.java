package AuthPack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import UtilityClasses.CryptoPackage.EncryptClass;
import UtilityClasses.ExceptionHandler;
import UtilityClasses.JDBCConnectorClass;
import org.apache.commons.lang3.StringUtils;

/**
 * This class is has the role of verifying <a href="/SignUp.jsp">SignUp.jsp</a> input fields<br>
 *     and querying the data to the database after hashing the necessary details.
 */
@WebServlet(name = "AuthPack.ServletAuth")
public class ServletAuth extends HttpServlet  {
    private static final String propCred = "Please insert proper credentials."+" The fields must contain characters in" +
            " the range of 8 to 32."+ " Please refrain from trying XSS and SQL Injections. "+"You'll fail miserably.";
    private static final String wentWrong = "Something went wrong. Please try again now or later.";

    private String usr,pass,cnfPass;
    private static final String[] redList = {"<",">","'","\""};

    private Connection c;
    private PreparedStatement pst;

    @Override
    public void init() throws ServletException {
        c = JDBCConnectorClass.getConn();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
             IOException {
        queryToDb(request,response);
    }

    /**
     * Method for filtering out elements that could be used to create tags
     * and sequences which may open up <b>XSS</b> and <b>SQL Injection</b>
     * attacks.
     * The method also checks for the size of the
     * @param input which must be more than 7 characters but less than 33 characters
     * @return true - by default and false if the conditions are met (<i>See Source Code)</i>
     *
     */
    private boolean filterString(String input){
        for(String str:redList){
            if((StringUtils.containsIgnoreCase(input,str)) || (input.length()<=7 || input.length()>=33))return false;
        }
        return true;
    }

    /**
     * This method sets the global parameters for this class<br>
     * If the conditions are not met then the user is redirected back<br>
     * to the <a href="/SignUp.jsp">SignUp.jsp</a> page<br>
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     * @return <b>true</b> if username, password and confirmPassword is sanitised<br>
     *     else return <b>false</b>
     */
    private boolean setParams(HttpServletRequest request,HttpServletResponse response) throws IOException,
            ServletException {
        String un,pa,cp;
        un = request.getParameter("user_name");
        pa = request.getParameter("pass");
        cp = request.getParameter("passConf");
        if(filterString(un) && filterString(pa) && filterString(cp) && pa.contentEquals(cp)){
            this.usr = un;   this.pass = pa;    this.cnfPass = cp;
            return true;
        }else{
            request.setAttribute("msg2",propCred);
            //request.getRequestDispatcher(request.getContextPath()+"/SignUp.jsp").include(request,response);
            return false;
        }
    }

    private void queryToDb(HttpServletRequest request, HttpServletResponse response)  {
        try {
            if(setParams(request, response)){
                String query = "INSERT INTO `usr_det_log_table` VALUES(default,?,?,?);";
                EncryptClass aClass = new EncryptClass(2048,256,100000);
                String key = aClass.hashPassword(cnfPass);
                byte[] bt = aClass.getMC();
                this.pst = c.prepareStatement(query);
                this.pst.setString(1,usr);this.pst.setString(2,key);this.pst.setBytes(3,bt);
                pst.executeUpdate();
                //HttpSession session = request.getSession();
                //session.invalidate();
                request.getRequestDispatcher("/index.jsp").forward(request,response);
            } else {
                request.setAttribute("msg", wentWrong);
                request.getRequestDispatcher("/SignUp.jsp").include(request,response);
            }
        } catch (IOException | ServletException | NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            e.printStackTrace();
            ExceptionHandler.HandleExc(e,this.getClass(),Level.SEVERE);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,e.getCause().getMessage());
        }
    }


    /**
     * close the database connection
     */
    @Override
    public void destroy() {
        try {
            if(c!=null)c.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getSQLState()+" \n"+e.getNextException().toString()+" \n"+e.getErrorCode()+" \n"+e.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        c.close();
    }
}
