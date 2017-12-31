package mif50.com.onlinequizapp.model;

/**
 *
 * this class model that hold data (username,password,email) from firebase and set it to object json Users
 *  used this model class to add user and also used to login
 */

public class User {
    private String username;
    private String password;
    private String email;

    public User(){

    }

    public User(String username,String password,String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
