package UtilityClasses;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnectorClass {

    public static Connection getConn(){
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/AWS_usr_det_acc");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
