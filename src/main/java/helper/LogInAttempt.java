package helper;

import Model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public class LogInAttempt {

    /**
     * This method will determine whether a user logged in successfully, and it will print a line to a login activity
     * log with the ZonedDateTime of the user
     * @param user the user attmepting to log in
     * @param success whether the login was successful
     * @return true. Figured this could be updated to return false if the writing failed
     * @throws IOException in case there is an issue
     */
    public static boolean log(User user, boolean success) throws IOException {

        File file = new File("login_activity.txt");
        FileWriter fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(fw);
        ZonedDateTime zdt = ZonedDateTime.now();
        String successString = "unsuccessful";

        if(user == null){
            pw.println("Someone tried to log in with a bad username at " + zdt);
            pw.close();
            return false;
        }

        if (success){
            successString = "successful";
        }
        pw.println("User " + user.getUserId() +
                " attempted to login at " + zdt +
                " the log in attempt was " + successString);
        pw.close();
        return true;
    }
}
