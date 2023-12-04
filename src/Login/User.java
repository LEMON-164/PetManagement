/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

/**
 *
 * @author Admin
 */
public class User {

    private static User instance = new User();

    public static User getInstance() {
        return instance;
    }

    private String ID;
    private String username;
    private String pass;
    private Role role;

    public User() {
    }

    public User(String ID, String username, String pass, Role role) {
        this.ID = ID;
        this.username = username;
        this.pass = pass;
        this.role = role;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean checkRole(Role role) {
//        return Integer.compare(this.role.intValue(), role.intValue()) <= 0;
        return this.role.intValue() <= role.intValue();
    }

}
