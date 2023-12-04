/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Service.PetService;

/**
 *
 * @author HP
 */
public class OrderDetails {

    private String petId;
    private int quantity;
    private double petCost;

    public String getPetId() {
        return petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderDetails(String petId, int quantity) {
        this.petId = petId;
        this.quantity = quantity;
    }

    public double getpetCost() {
        petCost = PetService.getInstance().getPetbyID(petId).getPrice();
        return petCost * this.quantity;
    }

}
