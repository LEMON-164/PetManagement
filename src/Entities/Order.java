/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Order {

    private String orid;
    private Date ordate;
    private String cusname;
    private OrderDetails cart;
    private ArrayList<OrderDetails> petList;

    public Order() {
        this.petList = new ArrayList();
    }

    public Order(String orid, Date ordate, String cusname, OrderDetails cart) {
        this.petList = new ArrayList();
        this.orid = orid;
        this.ordate = ordate;
        this.cusname = cusname;
        this.cart = cart;
        this.petList.add(cart);
    }

    public Order(String orid, Date ordate, String cusname, ArrayList<OrderDetails> petList) {
        this.petList = new ArrayList();
        this.orid = orid;
        this.ordate = ordate;
        this.cusname = cusname;
        this.setPetList(petList);
    }

    public OrderDetails getPet() {
        return cart;
    }

    public String getOrid() {
        return orid;
    }

    public void setOrid(String orid) {
        this.orid = orid;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public ArrayList<OrderDetails> getPetList() {
        return petList;
    }

    public final void setPetList(ArrayList<OrderDetails> petList) {
        if (petList != null) {
            this.petList.addAll(petList);
        }
    }

    public Date getOrdate() {
        return ordate;
    }

    public void setOrdate(Date ordate) {
        this.ordate = ordate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OrderDetails PetDetail : petList) {
            sb.append("\t").append(System.lineSeparator());
//            sb.append(System.lineSeparator());
            sb.append(this.orid);
            sb.append(",").append(Utils.input.toString(this.ordate));
            sb.append(",").append(this.cusname);
            sb.append(",").append(PetDetail.getPetId());
            sb.append(",").append(PetDetail.getQuantity());
            sb.append(",").append(PetDetail.getpetCost());
        }
//        return sb.length() <= 0 ? "" : sb.toString().substring(1);
        return sb.toString().substring(System.lineSeparator().length());
    }
}
