package UtilityClasses;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler extends Throwable {
    public static void HandleExc(Throwable e,Class x, Level Lvl){
        Logger.getLogger(x.getName()).log(Lvl,e.getMessage()+" \n"+e.fillInStackTrace().toString());
    }

}

