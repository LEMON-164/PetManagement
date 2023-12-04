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
public enum Role {
    ADMIN(0),
    USER(1),
    INVALID(Integer.MAX_VALUE);

    private final int role;

    public static Role valueOf(int role) {
        if (role < 0 || role >= Role.values().length) {
            return Role.INVALID;
        }
        return Role.values()[role];
    }

    public int intValue() {
        return role;
    }

    private Role(int role) {
        this.role = role;
    }

}
