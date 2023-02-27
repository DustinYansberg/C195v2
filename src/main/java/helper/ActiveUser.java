package helper;

import Model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ActiveUser {

    User activeUser;
    ArrayList<Integer> loginAttempts;
    ArrayList<LocalDateTime> loginDates;

    public ActiveUser(User user){
        this.activeUser = user;
    }

    public User getActiveUser(){
        return this.activeUser;
    }

    public void setActiveUser(User user){
        this.activeUser = user;
    }

    public void logInAttemptToLog(){
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("\"C:\\Users\\drfla\\IdeaProjects\\C195v1\\login_activity.txt\"");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info(this.activeUser.getUserName() + " attempted to log in at " + LocalDateTime.now());

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

//        logger.info("Hi How r u?");

    }
}
