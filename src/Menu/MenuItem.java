/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Login.Role;

/**
 *
 * @author Admin
 */
public enum MenuItem {
    BACK("Back.", Role.USER),
    EXIT("Exit", Role.USER),
    
    MAIN("PETSHOP MANAGEMENT", Role.USER),
    
    
    PET("PET.", Role.USER),
    CUSTOMER("Customer.", Role.USER),
    ORDER("Order.", Role.USER),
    
    ADD_PET("Add Pet.", Role.ADMIN),
    ADD_CUSTOMER("Add Customer.", Role.ADMIN),
    ADD_ORDER("Add Order.", Role.ADMIN),
    
    DELETE_PET("Delete Pet.", Role.ADMIN),
    DELETE_CUSTOMER("Delete Customer.", Role.ADMIN),
    
    UPDATE_PET("Update Pet.", Role.ADMIN),
    UPDATE_CUSTOMER("Update Customer.", Role.ADMIN),
    SETTING_ORDER("Setting Order.", Role.ADMIN),
    
    SEARCH_PET("Search Pet.", Role.USER),
    SEARCH_PETLIST("Search Pet List.", Role.USER),
    SEARCH_CUSTOMER("Search Customer.", Role.USER),
    SEARCH_CUSTOMERORDER("Search Customer order.", Role.USER),
    SEARCH_ORDERLIST("Search Order List by Year month.", Role.ADMIN),
    
    SAVETOFILE_PET("Save to file Pet.", Role.ADMIN),
    SAVETOFILE_CUSTOMER("Save to file Customer.", Role.ADMIN),
    SAVETOFILE_ORDER("Save to file Order.", Role.ADMIN),
    
    DISPLAY_PET("Display Pet.", Role.USER),
    DISPLAY_CUSTOMER("Display Customer.", Role.ADMIN),
    DISPLAY_ORDER("Display Order.", Role.ADMIN),
    DISPLAY_SORT_ORDER("Display sort Order.", Role.ADMIN);
    
    private final String label;
    private final Role role;

    private MenuItem(String label, Role role) {
        this.label = label;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getLabel() {
        return label;
    }

}
