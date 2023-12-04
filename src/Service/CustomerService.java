/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Customers;
import Entities.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author lemon
 */
public class CustomerService {

    private static CustomerService instance = new CustomerService();

    public static CustomerService getInstance() {
        return instance;
    }

    private ArrayList<Customers> cusList = new ArrayList<>();

    public ArrayList<Customers> getCusList() {
        return cusList;
    }

//============= HAM PHU ==============
    public Customers getCustomerByID(String id) {
        if (id != null) {
            for (Customers cus : cusList) {
                if (id.equalsIgnoreCase(cus.getCusID())) {
                    return cus;
                }
            }
        }
        return null;
    }

    public Customers getCustomerByName(String name) {
        if (name != null) {
            for (Customers cus : cusList) {
                if (name.equalsIgnoreCase(cus.getCusname())) {
                    return cus;
                }
            }
        }
        return null;
    }

    private void sortList() {
        Collections.sort(cusList, new Comparator<Customers>() {
            @Override
            public int compare(Customers o1, Customers o2) {
                return o1.getCusID().compareTo(o2.getCusID());
            }
        });
    }

    public void readDatafromfileCustomer() {
        try {
            File f = new File("Customers.csv");
            if (!f.exists()) {
                System.out.println("file does not exist.");
                System.out.println("=========================");
                return;
            } else {
                FileReader fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);
                String detail;
                while ((detail = bf.readLine()) != null) {
                    StringTokenizer stk = new StringTokenizer(detail, ",");
                    String cusid = stk.nextToken();
                    String cusname = stk.nextToken();
                    String phone = stk.nextToken();
                    if (getCustomerByID(cusid) == null) {
                        Customers cus = new Customers(cusid, cusname, phone);
                        cusList.add(cus);
                    }
                }
                bf.close();
                fr.close();
                System.out.println("Read seccessfull.");
            }
        } catch (Exception e) {
            System.out.println("Read fail.");
        }
    }
//============= HAM CHINH ==============

    public Customers addCustomer() {
        Customers customer = null;
        System.out.println("=========================");

        String id = Utils.input.inputStringwithrex("Customer's ID: ",
                "C\\d{4}$", "ID can not be blank.",
                "please input true format(C****).");

        customer = getCustomerByID(id);
        if (customer != null) {
            System.out.println("This ID has already existed.");
            return customer;
        }

        String name = Utils.input.inputStringwithrex("Customer's name: ",
                "[A-Za-z\\s]{2,30}",
                "Customer's name can not be blank.",
                "please input properly.");

        String phone = Utils.input.inputStringwithrex("Customer's phone: ",
                "^\\d{10}$",
                "phone cannot be empty", "please input phone with 10 numbers");

        customer = new Customers(id, name, phone);
        cusList.add(customer);
        System.out.println("Customer has been added.");
        System.out.println("=========================");
        return customer;
    }

    public void deleteCustomer() {
        System.out.println("=========================");
        if (cusList.isEmpty()) {
            System.out.println("List is empty.");
            return;
        } else {
            sortList();
            for (Customers cus : cusList) {
                System.out.println("*********************");
                System.out.println(cus);
            }
            System.out.println("=========================");
            String id = Utils.input.inputStringwithrex("Customer's ID: ", "C\\d{4}$",
                    "ID cannot be blank.", "please input true format(C****).");

            if (getCustomerByID(id) != null) {
                cusList.remove(getCustomerByID(id));
                System.out.println("=========================");
                System.out.println("Delete successfully.");
                System.out.println("=========================");

            } else {
                System.out.println("=========================");
                System.out.println("this ID does not exist");
                System.out.println("Delete fail.");
                System.out.println("=========================");
            }
        }
    }

    public void updateCustomer() {
        System.out.println("=========================");
        Scanner sc = new Scanner(System.in);
        String id;
        if (cusList.isEmpty()) {
            System.out.println("List is empty.");
            return;
        } else {
            sortList();
            for (Customers cus : cusList) {
                System.out.println("*********************");
                System.out.println(cus);
            }
            System.out.println("=========================");
            do {
                id = Utils.input.inputStringwithrex("Customer's ID: ",
                        "C\\d{4}$",
                        "ID cannot be blank.",
                        "please input true format(C****).");

                if (getCustomerByID(id) == null) {
                    System.out.println("This ID has not found.");
                }
            } while (getCustomerByID(id) == null);
            Customers cus = getCustomerByID(id);

            String name = Utils.input.inputStringwithrexAlowEmty("Change Customer's name: ",
                    "[A-Za-z\\s]{2,30}",
                    "please input properly.");

            if (name.trim().isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Name is not changed.");
                System.out.println("-------------------------");
            } else {
                cus.setCusname(name);
                System.out.println("-------------------------");
                System.out.println("Customer's name update successful.");
                System.out.println("-------------------------");
            }

            String phone = Utils.input.inputStringwithrexAlowEmty(
                    "Change customer's phone: ",
                    "^\\d{10}$",
                    "please input phone with 10 numbers");

            if (phone.trim().isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Phone is not changed.");
                System.out.println("-------------------------");
            } else {
                cus.setCusphone(phone);
                System.out.println("-------------------------");
                System.out.println("Customer's phone update successful.");
                System.out.println("-------------------------");
            }
        }
    }

    public void searchCustomer() {
        System.out.println("=========================");
        if (cusList.isEmpty()) {
            System.out.println("List is empty.");
            System.out.println("=========================");
            return;
        } else {
            String id = Utils.input.inputStringwithrex("Customer's ID: ",
                    "C\\d{4}$",
                    "ID cannot be blank.",
                    "please input true format(C****).");

            if (getCustomerByID(id) != null) {
                System.out.println("=========================");
                System.out.println(getCustomerByID(id));
                System.out.println("=========================");
            } else {
                System.out.println("=========================");
                System.out.println("this ID does not exist");
                System.out.println("Search fail.");
                System.out.println("=========================");
            }
        }
    }

    public void savetoFileCustomer() {
        System.out.println("=========================");
        sortList();
        try {
            File f = new File("Customers.csv");
            FileWriter fw = new FileWriter(f);
            PrintWriter bw = new PrintWriter(fw);
            for (int i = 0; i < cusList.size(); i++) {
                Customers x = cusList.get(i);
                bw.println(x.getCusID() + "," + x.getCusname()
                        + "," + x.getCusphone());
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.print("Not ");

        }
        System.out.println("Done save to file.");
        System.out.println("=========================");
    }

    public void displayCustomer() {
        System.out.println("=========================");
        if (cusList.isEmpty()) {
            System.out.println("list is empty.");
            System.out.println("=========================");
            return;
        } else {
            sortList();
            for (Customers cus : cusList) {
                System.out.println("*********************");
                System.out.println(cus);
            }
            System.out.println("=========================");
        }
    }
}
