package a21200800isec.cmcticket2.Assets;

/**
 * Created by red_f on 28/12/2016.
 */

public class Ticket {
    private String location;
    private String data;
    private String image;
    private String description;

    public Ticket(String location, String data, String image, String description) {
        this.location = location;
        this.data = data;
        this.image = image;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}