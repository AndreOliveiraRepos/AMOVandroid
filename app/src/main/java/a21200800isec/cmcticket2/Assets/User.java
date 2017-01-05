package a21200800isec.cmcticket2.Assets;

/**
 * Created by red_f on 28/12/2016.
 */

public class User {
    private String userName;
    private String authToken;
    private String requestJSON;
    private String Email;
    private String PhoneNumber;
    private String Location;
    private boolean login;
    private String password;
    //socket set

    public User() {
        this.userName = "";
        this.authToken = "";
        this.requestJSON = "";
        this.Email = "";
        this.Location = "";
        this.login = false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRequestJSON() {
        return requestJSON;
    }

    public void setRequestJSON(String requestJSON) {
        this.requestJSON = requestJSON;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public void setLogin(boolean b){this.login = b;}

    public boolean isLogin(){return this.login;}

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String p){this.password = p;}

}