package mif50.com.onlinequizapp.common;

import java.util.ArrayList;
import java.util.List;

import mif50.com.onlinequizapp.model.Question;
import mif50.com.onlinequizapp.model.User;

/**
 * Created by mohamed on 11/5/17.
 */

public class Common {
    public static String categoryId,categoryName; // common variable to get categoryId and categoryName  from Questions
    public static User currentUser; // common variable to get  currentUser from category (user Login)
    public static List<Question> questionList = new ArrayList<>(); // hold data of Questions (json obj) (Question model)
    public static final String STR_PUSH="pushNotification";// key used in Notification
    public static final String MESSAGE_KEY="MESSAGE"; // key used in message Notification
}
