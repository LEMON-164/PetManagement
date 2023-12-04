/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Admin
 */
public class Customers {

    private String cusID;
    private String cusname;
    private String cusphone;

    public Customers() {
    }

    public Customers(String cusID, String cusname, String cusphone) {
        this.cusID = cusID;
        this.cusname = cusname;
        this.cusphone = cusphone;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getCusphone() {
        return cusphone;
    }

    public void setCusphone(String cusphone) {
        this.cusphone = cusphone;
    }

    @Override
    public String toString() {
        return "Customer's ID: " + cusID + "\n"
                + "Customer' name: " + cusname + "\n"
                + "Customer's phone: " + cusphone + "\n";
    }

}
