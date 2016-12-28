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
    //socket set

    public User(String userName, String authToken, String Email, String PhoneNumber) {
        this.userName = userName;
        this.authToken = authToken;
        this.requestJSON = "";
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Location = "";
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public void sendRequest(){}


}