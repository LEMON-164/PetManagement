/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Order;
import Entities.OrderDetails;
import Entities.PetCategory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class OrderService {

    private class CompareASCOrderById implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o1.getOrid().compareTo(o2.getOrid());
        }
    }

    private class CompareDESCOrderById implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o2.getOrid().compareTo(o1.getOrid());
        }
    }

    private class CompareASCOrderByCustomerName implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o1.getCusname().compareTo(o2.getCusname());
        }
    }

    private class CompareDESCOrderByCustomerName implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o2.getCusname().compareTo(o1.getCusname());
        }
    }

    private class CompareASCOrderByDate implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o1.getOrdate().compareTo(o2.getOrdate());
        }
    }

    private class CompareDESCOrderByDate implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o2.getOrdate().compareTo(o1.getOrdate());
        }
    }

    Scanner sc = new Scanner(System.in);
    private static OrderService instance = new OrderService();

    public static OrderService getInstance() {
        return instance;
    }

    private HashMap<YearMonth, List<Order>> orderMap;

    public HashMap<YearMonth, List<Order>> getOrderMap() {
        return orderMap;
    }

    public OrderService() {
        this.orderMap = new HashMap();
    }

//+++++++++++++++++++++++++++ HAM PHU +++++++++++++++++++++++++++++
    public Order getOrderbyID(String id) {
        for (List<Order> orList : orderMap.values()) {
            for (Order or : orList) {
                if (id.equalsIgnoreCase(or.getOrid())) {
                    return or;
                }
            }
        }
        return null;
    }

    public Order getOrderbyCustomerID(String id) {
        for (List<Order> orList : orderMap.values()) {
            for (Order or : orList) {
                if (CustomerService.getInstance().getCustomerByID(id).getCusname()
                        .equalsIgnoreCase(or.getCusname())) {
                    return or;
                }
            }
        }
        return null;
    }

    private String nextId(YearMonth ym) {
        List<Order> orList = getSortedListById(SortType.SORT_ASC_BY_ID);
        if (orList == null) {
            orList = new ArrayList();
            orderMap.put(ym, orList);
        }
        String maxId = null;
        if (orList.isEmpty()) {
            return String.format("%04d", 0);
        } else {
            maxId = orList.get(orList.size() - 1).getOrid().substring(1);
            return String.format("%04d", Integer.valueOf(maxId) + 1);
        }
    }

    private boolean addtoMap(Order order, YearMonth ym) {
        List<Order> orderList = this.orderMap.get(ym);
        if (orderList == null) {
            orderList = new ArrayList();
            this.orderMap.put(ym, orderList);
        }
        return orderList.add(order);
    }

    private List<Order> getSortedListById(SortType type) {
        switch (type) {
            case SORT_ASC_BY_ID:
                return orderMap.values().stream().flatMap(e -> e.stream())
                        .sorted(new CompareASCOrderById()).collect(Collectors.toList());
            case SORT_DESC_BY_ID:
                return orderMap.values().stream().flatMap(e -> e.stream())
                        .sorted(new CompareDESCOrderById()).collect(Collectors.toList());
            case SORT_ASC_BY_CUSTOMER_NAME:
                return orderMap.values().stream().flatMap(e -> e.stream())
                        .sorted(new CompareASCOrderByCustomerName()).collect(Collectors.toList());
            case SORT_DESC_BY_CUSTOMER_NAME:
                return orderMap.values().stream().flatMap(e -> e.stream())
                        .sorted(new CompareDESCOrderByCustomerName()).collect(Collectors.toList());
            case SORT_ASC_BY_DATE:
                return orderMap.values().stream().flatMap(e -> e.stream())
                        .sorted(new CompareASCOrderByDate()).collect(Collectors.toList());
            case SORT_DESC_BY_DATE:
                return orderMap.values().stream().flatMap(e -> e.stream())
                        .sorted(new CompareDESCOrderByDate()).collect(Collectors.toList());
        }
        return orderMap.values().stream().flatMap(e -> e.stream())
                .sorted().collect(Collectors.toList());
    }

    public void readDatafromfileOrder() {
        try {
            File f = new File("Orders.csv");
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
                    String oid = stk.nextToken();
                    Date date = Utils.input.ParseDate(stk.nextToken());
                    String cusname = stk.nextToken();
                    String pid = stk.nextToken();
                    int quantity = Integer.parseInt(stk.nextToken());

                    Order order = getOrderbyID(oid);
                    if (order == null) {
                        order = new Order(oid, date, cusname,
                                new OrderDetails(pid, quantity));
                        YearMonth ym = Utils.input.convertDateToYearMonth(date);
                        List<Order> orList = orderMap.get(ym);
                        if (orList == null) {
                            orList = new ArrayList();
                            orderMap.put(ym, orList);
                        }
                        orList.add(order);
                    } else {
                        order.getPetList().add(new OrderDetails(pid, quantity));
                    }
                }
                bf.close();
                fr.close();
                System.out.println("Read successfull.");
            }
        } catch (Exception e) {
            System.out.println("Read fail.");
        }
    }

    private int getPetCount() {
        int petcount = 0;
        for (List<Order> orList : orderMap.values()) {
            for (Order or : orList) {
                for (OrderDetails ord : or.getPetList()) {
                    petcount += ord.getQuantity();
                }
            }
        }
        return petcount;
    }

    private int getTotal() {
        int Total = 0;
        for (List<Order> orList : orderMap.values()) {
            for (Order or : orList) {
                for (OrderDetails ord : or.getPetList()) {
                    Total += ord.getpetCost();
                }
            }
        }
        return Total;
    }
//+++++++++++++++++++++++++++ HAM CHINH +++++++++++++++++++++++++++  

    public Order addOrder() {
        System.out.println("=========================");

        Date date = Utils.input.inputDate("Order's date");
        YearMonth ym = Utils.input.convertDateToYearMonth(date);
        String oid = nextId(ym);

        String cid;
        String name = null;
        do {
            cid = Utils.input.inputStringwithrex("Customer's ID: ",
                    "C\\d{4}$",
                    "ID cannot be blank.",
                    "please input true format(C****).");
            if (CustomerService.getInstance().getCustomerByID(cid) == null) {
                System.out.println("This ID has not found.");
            } else {
                name = CustomerService.getInstance().
                        getCustomerByID(cid).getCusname();
            }
        } while (CustomerService.getInstance().getCustomerByID(cid) == null);

        ArrayList<OrderDetails> petList = new ArrayList();

        System.out.println("==============================="
                + "\nEnter to End input Products !!! "
                + "\n===============================");
        String pid;
        do {
            pid = Utils.input.inputString("Pet's ID((D/C/P + ***)): ", true);
            if (PetService.getInstance().getPetbyID(pid) != null) {
                int quantity = Utils.input.inputintlimit("Pet's quantity: ",
                        0, PetService.getInstance().getPetbyID(pid).getQuantity());
                petList.add(new OrderDetails(pid, quantity));
            }
        } while (!pid.trim().isEmpty() || petList.isEmpty());

        Order or = new Order(oid, date, name, petList);
        if (addtoMap(or, ym)) {
            System.out.println("Order has been added.");
        }
        return or;
    }

    private void updateOrder() {
        System.out.println("=========================");
        Scanner sc = new Scanner(System.in);
        if (PetService.getInstance().getPetMap().get(PetCategory.CAT).isEmpty()) {
            System.out.println("CatList is empty.");
            System.out.println("=========================");
        }
        if (PetService.getInstance().getPetMap().get(PetCategory.DOG).isEmpty()) {
            System.out.println("DogList is empty.");
            System.out.println("=========================");
        }
        if (PetService.getInstance().getPetMap().get(PetCategory.PARROT).isEmpty()) {
            System.out.println("ParrotList is empty.");
            System.out.println("=========================");
        }
        if (PetService.getInstance().getPetMap().isEmpty()) {
            System.out.println("Map is empty.");
            return;
        } else {
            for (Order order : getSortedListById(SortType.SORT_ASC_BY_ID)) {
                System.out.println("*********************");
                System.out.println(order);
            }
            System.out.println("=========================");

            boolean check = true;
            String oid;
            do {
                oid = Utils.input.inputStringwithrex("Order's ID: ",
                        "\\d{4}$", "ID can not be blank.",
                        "please input true format(****).");
                if (getOrderbyID(oid) == null) {
                    System.out.println("Order does not exist.");
                }
            } while (getOrderbyID(oid) == null);
            Order or = getOrderbyID(oid);

            check = true;
            String cid;
            do {
                cid = Utils.input.inputStringwithrexAlowEmty("Customer's ID: ",
                        "C\\d{3}$",
                        "please input true format(C****).");

                if (cid.trim().isEmpty()) {
                    check = false;
                } else if (CustomerService.getInstance().getCustomerByID(cid) == null) {
                    System.out.println("This ID has not found.");
                } else {
                    check = false;
                }
            } while (check);

            if (cid.trim().isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Customer's ID is not changed.");
                System.out.println("-------------------------");
            } else {
                or.setCusname(CustomerService.getInstance()
                        .getCustomerByID(cid)
                        .getCusname());
                System.out.println("-------------------------");
                System.out.println("Customer's ID update successful.");
                System.out.println("-------------------------");
            }

            ArrayList<OrderDetails> proList = new ArrayList();
            System.out.println("==============================="
                    + "\nEnter to End input Products !!! "
                    + "\n===============================");

            ArrayList<OrderDetails> petList = new ArrayList();

            String pid;
            do {
                System.out.println("Pet's ID: ");
                pid = sc.nextLine();
                if (pid.trim().isEmpty()) {
                    break;
                } else if (PetService.getInstance().getPetbyID(pid) == null) {
                    System.out.println("This ID has not found.");
                    pid = null;
                } else {
                    int quantity = Utils.input.inputintlimit("Pet's quantity: ", 0,
                            PetService.getInstance().getPetbyID(pid).getQuantity());
                    petList.add(new OrderDetails(pid, quantity));
                }
                System.out.println("==============================="
                        + "\nEnter to End input Pet !!! "
                        + "\n===============================");
            } while (true);

            if (proList.isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Product's ID is not changed.");
                System.out.println("-------------------------");
            } else {
                or.setPetList(proList);
                System.out.println("-------------------------");
                System.out.println("Product's ID update successful.");
                System.out.println("-------------------------");
            }

            Date date = Utils.input.inputDateAlowEmty("Order's date: ");

            if (date == null) {
                System.out.println("-------------------------");
                System.out.println("Date is not changed.");
                System.out.println("-------------------------");
            } else {
                orderMap.get(Utils.input.convertDateToYearMonth(or.getOrdate())).remove(or);
                or.setOrdate(date);
                orderMap.get(Utils.input.convertDateToYearMonth(or.getOrdate())).add(or);
                System.out.println("-------------------------");
                System.out.println("Date update successful.");
                System.out.println("-------------------------");
            }
        }
    }

    private void deleteOrder() {
        System.out.println("=========================");
        if (orderMap.isEmpty()) {
            System.out.println("Map is empty.");
            return;
        } else {
            for (Order order : getSortedListById(SortType.SORT_ASC_BY_ID)) {
                System.out.println("*********************");
                System.out.println(order);
            }
            System.out.println("=========================");
            String oid = Utils.input.inputStringwithrex("Order's ID: ",
                    "\\d{4}$", "ID can not be blank.",
                    "please input true format(****).");
            Order or = getOrderbyID(oid);
            if (getOrderbyID(oid) != null) {
                orderMap.get(Utils.input.convertDateToYearMonth(or.getOrdate())).remove(getOrderbyID(oid));
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

    public void settingOrder() {
        System.out.println("=========================");
        int choice;
        do {
            System.out.println("(1) -> Update Order.");
            System.out.println("(2) -> Delete Order.");
            System.out.println("(0) -> Back.");
            choice = Utils.input.inputintlimit("Your choice: ", 0, 2);
            switch (choice) {
                case 1:
                    updateOrder();
                    break;
                case 2:
                    deleteOrder();
                    break;
                default:
                    break;
            }
        } while (choice > 0 && choice < 2);

    }

    public void searchCustomerOrder() {
        System.out.println("=========================");
        String cid = Utils.input.inputStringwithrex("Customer's ID: ",
                "C\\d{4}$",
                "ID cannot be blank.",
                "please input true format(C****).");

        if (CustomerService.getInstance().getCustomerByID(cid) == null) {
            System.out.println("=========================");
            System.out.println("This ID has not found.");
            System.out.println("=========================");
            return;
        } else {
            System.out.println("=========================");
            for (OrderDetails ord : getOrderbyCustomerID(cid).getPetList()) {
                System.out.println(getOrderbyCustomerID(cid));
            }
            System.out.println("=========================");
        }
    }

    public void searchListOrderByYearMonth() {
        YearMonth ym = Utils.input.inputYearMonth("input Date to Search");
        List<Order> ordList = orderMap.get(ym);
        if (ordList == null) {
            return;
        } else {

            System.out.println("No." + "\t"
                    + "Order Id" + "\t"
                    + "Order Date" + "\t"
                    + "Customer" + "\t"
                    + "Pet id" + "\t"
                    + "\t" + "Pet Count" + "\t"
                    + "Order total");

            int i = 0;
            for (Order order : ordList) {
                System.out.println(i + "\t" + order);
                i++;
            }
            System.out.println("=========================");
        }
    }

    public void savetoFileOrder() {
        System.out.println("=========================");
        try {
            File f = new File("Orders.csv");
            FileWriter fw = new FileWriter(f, false);
            PrintWriter bw = new PrintWriter(fw, false);
            for (Order or : getSortedListById(SortType.SORT_ASC_BY_ID)) {
                for (OrderDetails ordetail : or.getPetList()) {
                    bw.println(or.getOrid()
                            + "," + Utils.input.toString(or.getOrdate())
                            + "," + or.getCusname()
                            + "," + ordetail.getPetId()
                            + "," + ordetail.getQuantity());
                }
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.print("Not ");

        }
        System.out.println("Done save to file.");
        System.out.println("=========================");
    }

    public void displayOrder() {
        System.out.println("=========================");
        if (orderMap.isEmpty()) {
            System.out.println("Order map is empty.");
            System.out.println("=========================");
            return;
        } else {
            System.out.println("No." + "|"
                    + "Order Id" + "|"
                    + "Order Date" + "|"
                    + "Customer" + "|"
                    + "Pet id" + "|"
                    + "Pet Count" + "|"
                    + "Order total");

            int i = 0;
            for (Order order : getSortedListById(SortType.SORT_ASC_BY_ID)) {
                System.out.println("*********************");
                System.out.println(i + "" + order);
                i++;
            }
            System.out.println("PetCount: " + getPetCount());
            System.out.println("ToTal: " + getTotal());
            System.out.println("=========================");
        }
    }

    public void displaySortOrder() {
        System.out.println("=========================");
        if (orderMap.isEmpty()) {
            System.out.println("Order map is empty.");
            System.out.println("=========================");
            return;
        } else {
            SortType type = Utils.input.inputSortType();
            System.out.println("No." + "|"
                    + "Order Id" + "|"
                    + "Order Date" + "|"
                    + "Customer" + "|"
                    + "Pet id" + "|"
                    + "Pet Count" + "|"
                    + "Order total");
            int i = 0;
            for (Order order : getSortedListById(type)) {
                System.out.println("*********************");
                System.out.println(i + "" + order);
                i++;
            }
            System.out.println("PetCount: " + getPetCount());
            System.out.println("ToTal: " + getTotal());
            System.out.println("=========================");
        }
    }
}
