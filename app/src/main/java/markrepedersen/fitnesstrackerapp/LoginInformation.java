package markrepedersen.fitnesstrackerapp;

/**
 * Created by mark on 16-08-31.
 */
public class LoginInformation {
    private int id;
    private String username;
    private String hashPass;
    private String salt;
    private int weight;
    private int height;

    public LoginInformation() {
    }

    //constructor
    public LoginInformation(int id, String username, String hashPass, String salt, int height, int weight) {
        this.id = id;
        this.username = username;
        this.hashPass = hashPass;
        this.salt = salt;
        this.height = height;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashPass() {
        return hashPass;
    }

    public int getWeight() { return weight; }

    public int getHeight() {return height; }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String s) {
        this.salt = s;
    }




}
