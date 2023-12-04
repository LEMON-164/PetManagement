/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Entities.Customers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Admin
 */
public class AccountService {

    private static AccountService instance = new AccountService();

    public static AccountService getInstance() {
        return instance;
    }

    private ArrayList<User> userList = new ArrayList<>();

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
    //int pos;

//++++++++++++++++++++ HAM PHU +++++++++++++++++++++++
    private User checkACC(String id, String pass) {
        if (id != null && pass != null) {
            for (User user : userList) {
                if (id.equalsIgnoreCase(user.getID()) && pass.equals(user.getPass())) {
                    return user;
                }
            }
        }
        return null;
    }

    public void readDatafromfile() {
        try {
            File f = new File("userList.txt");
            if (!f.exists()) {
                System.out.println("file does not exist.");
                System.out.println("==================");
                return;
            } else {
                FileReader fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);
                String detail;
                while ((detail = bf.readLine()) != null) {
                    StringTokenizer stk = new StringTokenizer(detail, ",");
                    String ID = stk.nextToken();
                    String username = stk.nextToken();
                    String pass = stk.nextToken();
                    Role role = Role.valueOf(stk.nextToken().
                            trim().toUpperCase());
                    userList.add(new User(ID, username, pass, role));
                }
                bf.close();
                fr.close();
            }
        } catch (Exception e) {
            System.out.println("Read fail.");
        }
    }

//++++++++++++++++++++ HAM CHINH +++++++++++++++++++++++
    public boolean Login() {
        if (userList.isEmpty()) {
            System.out.println("Please create your account.");
            return false;
        } else {
            String id;
            String pass;
            id = Utils.input.inputString("User's ID: ",
                    "ID cannot be blank.");
            pass = Utils.input.inputString("Password: ",
                    "Pass can not be blank.");
            currentUser = checkACC(id, pass);
            if (currentUser == null) {
                System.out.println("User'ID or Password invalid.");
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkCurrentUserRole(Role role) {
        return currentUser.checkRole(role);
    }
}
