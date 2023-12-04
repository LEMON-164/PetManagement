/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Pet implements Comparator<Pet> {

    private String pid;
    private String des;
    private Date date;
    private double price;
    private int quantity;
    private PetCategory cate;

    public Pet() {
    }

    public Pet(String pid, String des, Date date, double price, int quantity, PetCategory cate) {
        this.pid = pid;
        this.des = des;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.cate = cate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PetCategory getCate() {
        return cate;
    }

    public void setCate(PetCategory cate) {
        this.cate = cate;
    }

    @Override
    public String toString() {
        return "Pet'ID: " + pid + "\n"
                + "Description: " + des + "\n"
                + "Date: " + Utils.input.toString(date) + "\n"
                + "Price: $" + price + "\n"
                + "Quantity: " + quantity + "\n"
                + "Category: " + cate;
    }

    @Override
    public int compare(Pet o1, Pet o2) {
        return o1.pid.compareTo(o2.pid);
    }

}
