/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Entities.Customers;
import Entities.Order;
import Login.AccountService;
import Login.User;
import Menu.Menu;
import Menu.MenuItem;
import Service.CustomerService;
import Service.PetService;
import Service.OrderService;

/**
 *
 * @author lemon
 */
public class PetShop {

    private PetShop() {
    }

    private void addOder() {
        Order or = new Order();
        or = OrderService.getInstance().addOrder();
        if (CustomerService.getInstance().getCustomerByName(or.getCusname()) == null) {
            Customers customer = CustomerService.getInstance().addCustomer();
            if (!or.getCusname().equalsIgnoreCase(customer.getCusname())) {
                or.setCusname(customer.getCusname());
            }
        }
        CustomerService.getInstance().savetoFileCustomer();
    }

    private void run() {
        Menu menu = new Menu();
        MenuItem userChoice;
        do {
            userChoice = menu.getUserchoice();
            switch (userChoice) {
                case ADD_PET:
                    PetService.getInstance().addPet();
                    break;
                case ADD_CUSTOMER:
                    CustomerService.getInstance().addCustomer();
                    break;
                case ADD_ORDER:
                    addOder();
                    break;
                case DELETE_PET:
                    PetService.getInstance().deletePet();
                    break;
                case DELETE_CUSTOMER:
                    CustomerService.getInstance().deleteCustomer();
                    break;
                case UPDATE_PET:
                    PetService.getInstance().updatePet();
                    break;
                case UPDATE_CUSTOMER:
                    CustomerService.getInstance().updateCustomer();
                    break;
                case SEARCH_PET:
                    PetService.getInstance().searchPet();
                    break;
                case SEARCH_PETLIST:
                    PetService.getInstance().searchPetList();
                    break;
                case SEARCH_CUSTOMER:
                    CustomerService.getInstance().searchCustomer();
                    break;
                case SEARCH_ORDERLIST:
                    OrderService.getInstance().searchListOrderByYearMonth();
                    break;
                case SEARCH_CUSTOMERORDER:
                    OrderService.getInstance().searchCustomerOrder();
                    break;
                case SETTING_ORDER:
                    OrderService.getInstance().settingOrder();
                    break;
                case SAVETOFILE_PET:
                    PetService.getInstance().savetoFilePet();
                    break;
                case SAVETOFILE_CUSTOMER:
                    CustomerService.getInstance().savetoFileCustomer();
                    break;
                case SAVETOFILE_ORDER:
                    OrderService.getInstance().savetoFileOrder();
                    break;
                case DISPLAY_PET:
                    PetService.getInstance().displayPet();
                    break;
                case DISPLAY_CUSTOMER:
                    CustomerService.getInstance().displayCustomer();
                    break;
                case DISPLAY_ORDER:
                    OrderService.getInstance().displayOrder();
                    break;
                case DISPLAY_SORT_ORDER:
                    OrderService.getInstance().displaySortOrder();
                    break;
                case EXIT:
                    System.out.println("Logout.");
                    break;
                default:
                    break;
            }
        } while (userChoice != MenuItem.EXIT);
    }

    private void prepareData() {

        PetService.getInstance().readDatafromfilePet();
        CustomerService.getInstance().readDatafromfileCustomer();
        OrderService.getInstance().readDatafromfileOrder();
        System.out.println("=========================");
    }

    private void start() {
        int check = 3;
        AccountService.getInstance().readDatafromfile();

        do {
            System.out.println("Login");
            if (AccountService.getInstance().Login()) {
                System.out.println(User.getInstance().getUsername());
                prepareData();
                run();
                System.out.println("=========================");
                String choice = Utils.input.inputString(
                        "do you want to continue?(yes/no): ",
                        "this can not be blank");
                if (choice.equalsIgnoreCase("no")) {
                    return;
                } else {
                    check = 3;
                }
            } else {
                check--;
                System.out.println("Login fail");
                System.out.println("Number of try left: " + check);
                if (check == 0) {
                    return;
                }
            }
        } while (check != 0);
    }

    public static void main(String[] args) {
        new PetShop().start();
    }
}
