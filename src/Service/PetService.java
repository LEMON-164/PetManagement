/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Order;
import Entities.Pet;
import Entities.PetCategory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class PetService {

    private class CompareASCOrderById implements Comparator<Pet> {

        @Override
        public int compare(Pet o1, Pet o2) {
            return o1.getPid().compareTo(o2.getPid());
        }
    }
    private static PetService instance = new PetService();

    public static PetService getInstance() {
        return instance;
    }
    private Map<PetCategory, List<Pet>> petMap;
    private List<Pet> catList;
    private List<Pet> dogList;
    private List<Pet> parrotList;

    public PetService() {
        petMap = new HashMap();
        catList = new ArrayList<>();
        dogList = new ArrayList<>();
        parrotList = new ArrayList<>();
        petMap.put(PetCategory.CAT, catList);
        petMap.put(PetCategory.DOG, dogList);
        petMap.put(PetCategory.PARROT, parrotList);
    }

    public Map<PetCategory, List<Pet>> getPetMap() {
        return petMap;
    }

//************************* HAM PHU ****************************
    public Pet getPetbyID(String id) {
        if (petMap.isEmpty()) {
            return null;
        } else {
            for (List<Pet> petList : petMap.values()) {
                for (Pet pet : petList) {
                    if (id.equalsIgnoreCase(pet.getPid())) {
                        return pet;
                    }
                }
            }
        }
        return null;
    }

    private String nextId(PetCategory category) {
        String prex = "";
        List<Pet> petList = petMap.get(category);
        if (petList == null) {
            petList = new ArrayList();
            petMap.put(PetCategory.CAT, petList);
        }

        switch (category) {
            case CAT:
                prex = "C";
                break;
            case DOG:
                prex = "D";
                break;
            case PARROT:
                prex = "P";
                break;
        }
        String maxId = null;
        if (petList.isEmpty()) {
            return String.format("%s%03d", prex, Integer.valueOf(000));
        } else {
            maxId = petList.get(petList.size() - 1).getPid().substring(1);
            return String.format("%s%03d", prex, Integer.valueOf(maxId) + 1);
        }
    }

    private List<Pet> getSortedListById() {
        return petMap.values().stream().flatMap(e -> e.stream())
                .sorted(new CompareASCOrderById()).collect(Collectors.toList());
    }

    public void readDatafromfilePet() {
        try {
            File f = new File("Pets.txt");
            if (!f.exists()) {
                System.out.println("file does not exist.");
                System.out.println("=========================");
                return;
            } else {
                FileReader fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);
                String detail;
                while ((detail = bf.readLine()) != null) {
                    StringTokenizer stk = new StringTokenizer(detail, ";");
                    String pid = stk.nextToken();
                    String des = stk.nextToken();
                    Date date = Utils.input.ParseDate(stk.nextToken());
                    Double price = Double.parseDouble(stk.nextToken());
                    int quantity = Integer.parseInt(stk.nextToken());
                    String cate = stk.nextToken();

                    if (getPetbyID(pid) == null) {
                        Pet pet = new Pet(pid, des, date, price, quantity,
                                PetCategory.valueOf(cate.trim().toUpperCase()));
                        PetCategory key = PetCategory.valueOf(cate.trim().toUpperCase());
                        List<Pet> PetList = petMap.get(key);
                        if (PetList == null) {
                            PetList = new ArrayList();
                            petMap.put(key, PetList);
                        }
                        PetList.add(pet);

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

//************************* HAM CHINH **************************
    public void addPet() {
        System.out.println("=========================");

        PetCategory category = null;
        do {
            try {
                String cate = Utils.input.inputString(
                        "Category(CAT/DOG/PARROT): ",
                        "Category can not be blank.");
                category = PetCategory.valueOf(cate.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                System.out.println("Input CAT/DOG/PARROT.");
            }
        } while (category == null);

        String id = nextId(category);

        String des = Utils.input.inputString(
                "Description: ",
                "Description name can not be blank.");

        Date date = Utils.input.inputDate("Date in: ");

        double price = Utils.input.inputdouble("Price: $", 0);

        int quantity = Utils.input.inputInt("Quantity: ", 0);

        Pet pet = new Pet(id, des, date, price, quantity, category);

        petMap.get(category).add(pet);
        System.out.println("Pet has been added.");
    }

    public void searchPet() {
        System.out.println("=========================");
        if (petMap.isEmpty()) {
            System.out.println("Map is empty.");
            System.out.println("=========================");
            return;
        } else {
            String id = Utils.input.inputString("Pet's ID((D/C/P + ***)): ",
                    "ID cannot be blank.");

            if (getPetbyID(id) != null) {
                System.out.println("=========================");
                System.out.println(getPetbyID(id));
                System.out.println("=========================");
            } else {
                System.out.println("=========================");
                System.out.println("this ID does not exist");
                System.out.println("Search fail.");
                System.out.println("=========================");
            }
        }
    }

    public void searchPetList() {
        System.out.println("=========================");
        if (petMap.isEmpty()) {
            System.out.println("Map is empty.");
            System.out.println("=========================");
            return;
        } else {
            PetCategory category = null;
            do {
                try {
                    String cate = Utils.input.inputString(
                            "Category(CAT/DOG/PARROT)",
                            false);
                    category = PetCategory.valueOf(cate.trim().toUpperCase());
                } catch (IllegalArgumentException ex) {
                    System.out.println("Input CAT/DOG/PARROT.");
                }
            } while (category == null);

            List<Pet> petList = petMap.get(category);
            if (petList != null) {
                for (Pet pet : petList) {
                    System.out.println("*********************");
                    System.out.println(pet);

                }
                System.out.println("=========================");
            } else {
                System.out.println("=========================");
                System.out.println("this List does not exist or empty.");
                System.out.println("Search fail.");
                System.out.println("=========================");
            }
        }
    }

    public void updatePet() {
        System.out.println("=========================");
        Scanner sc = new Scanner(System.in);
        String id;
        if (petMap.get(PetCategory.CAT).isEmpty()) {
            System.out.println("CatList is empty.");
            System.out.println("=========================");
        }
        if (petMap.get(PetCategory.DOG).isEmpty()) {
            System.out.println("DogList is empty.");
            System.out.println("=========================");
        }
        if (petMap.get(PetCategory.PARROT).isEmpty()) {
            System.out.println("ParrotList is empty.");
            System.out.println("=========================");
        }
        if (petMap.isEmpty()) {
            return;
        } else {
            for (Pet pet : getSortedListById()) {
                System.out.println("*********************");
                System.out.println(pet);

            }
            System.out.println("=========================");
            do {
                id = Utils.input.inputString("Pet's ID((D/C/P + ***)): ",
                        "ID cannot be blank.");

                if (getPetbyID(id) == null) {
                    System.out.println("This ID has not found.");
                }
            } while (getPetbyID(id) == null);
            Pet pet = getPetbyID(id);

            PetCategory category = null;
            do {
                try {
                    String cate = Utils.input.inputString(
                            "Category(CAT/DOG/PARROT)",
                            true);
                    if (cate.isEmpty()) {
                        break;
                    }
                    category = PetCategory.valueOf(cate.trim().toUpperCase());
                } catch (IllegalArgumentException ex) {
                    System.out.println("Input CAT/DOG/PARROT.");
                }
            } while (category == null);

            if (category == null) {
                System.out.println("-------------------------");
                System.out.println("Category is not changed.");
                System.out.println("-------------------------");
            } else {
                petMap.get(pet.getCate()).remove(pet);
                pet.setCate(category);
                pet.setPid(nextId(category));
                petMap.get(pet.getCate()).add(pet);
                System.out.println("-------------------------");
                System.out.println("Category update successful.");
                System.out.println("contemporaneous Id updated successfully");
                System.out.println("-------------------------");
            }

            String des = Utils.input.inputString("Change description", true);

            if (des.trim().isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Description is not changed.");
                System.out.println("-------------------------");
            } else {
                pet.setDes(des);
                System.out.println("-------------------------");
                System.out.println("Description update successful.");
                System.out.println("-------------------------");
            }

            Date date = Utils.input.inputDateAlowEmty("Date change: ");

            if (date == null) {
                System.out.println("-------------------------");
                System.out.println("Date is not changed.");
                System.out.println("-------------------------");
            } else {
                pet.setDate(date);
                System.out.println("-------------------------");
                System.out.println("Date update successful.");
                System.out.println("-------------------------");
            }

            String price = Utils.input.inputStringwithrexAlowEmty(
                    "Change price to: $",
                    "^(?:-(?:[1-9](?:\\d{0,2}(?:,\\d{3})+|\\d*))|(?:0|(?:[1-9](?:\\d{0,2}(?:,\\d{3})+|\\d*))))(?:.\\d+|)$",
                    "Input number please!");

            if (price.trim().isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Price is not changed");
                System.out.println("-------------------------");
            } else {
                double pr = Float.parseFloat(price);
                pet.setPrice(pr);
                System.out.println("-------------------------");
                System.out.println("Price update successful.");
                System.out.println("-------------------------");
            }

            String quantity = Utils.input.inputStringwithrexAlowEmty(
                    "Change quantity to: ", "^\\d+$", "Input number please!");
            if (quantity.trim().isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("Quantity is not changed");
                System.out.println("-------------------------");
            } else {
                int quan = Integer.parseInt(quantity);
                pet.setQuantity(quan);
                System.out.println("-------------------------");
                System.out.println("Quantity update successful.");
                System.out.println("-------------------------");
            }
        }
    }

    public void deletePet() {
        System.out.println("=========================");
        String id;
        if (petMap.get(PetCategory.CAT).isEmpty()) {
            System.out.println("CatList is empty.");
            System.out.println("=========================");
        }
        if (petMap.get(PetCategory.DOG).isEmpty()) {
            System.out.println("DogList is empty.");
            System.out.println("=========================");
        }
        if (petMap.get(PetCategory.PARROT).isEmpty()) {
            System.out.println("ParrotList is empty.");
            System.out.println("=========================");
        }
        if (petMap.isEmpty()) {
            return;
        } else {
            for (Pet pet : getSortedListById()) {
                System.out.println("*********************");
                System.out.println(pet);

            }
            System.out.println("=========================");
            id = Utils.input.inputString("Pet's ID((D/C/P + ***)): ", "ID cannot be blank.");

            Pet pet = getPetbyID(id);
            if (pet != null) {
                petMap.get(pet.getCate()).remove(pet);
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

    public void savetoFilePet() {
        System.out.println("=========================");
        try {
            File f = new File("Pets.txt");
            FileWriter fw = new FileWriter(f);
            PrintWriter bw = new PrintWriter(fw);
            for (Pet pet : getSortedListById()) {
                bw.println(pet.getPid()
                        + ";" + pet.getDes()
                        + ";" + Utils.input.toString(pet.getDate())
                        + ";" + pet.getPrice()
                        + ";" + pet.getQuantity()
                        + ";" + pet.getCate());
            }

            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.print("Not ");

        }
        System.out.println("Done save to file.");
        System.out.println("=========================");
    }

    public void displayPet() {
        if (petMap.get(PetCategory.CAT).isEmpty()) {
            System.out.println("CatList is empty.");
            System.out.println("=========================");
        }
        if (petMap.get(PetCategory.DOG).isEmpty()) {
            System.out.println("DogList is empty.");
            System.out.println("=========================");
        }
        if (petMap.get(PetCategory.PARROT).isEmpty()) {
            System.out.println("ParrotList is empty.");
            System.out.println("=========================");
        }
        if (petMap.isEmpty()) {
            System.out.println("=========================");
            System.out.println("map is empty.");
            System.out.println("=========================");
            return;
        } else {
            for (Pet pet : getSortedListById()) {
                System.out.println("*********************");
                System.out.println(pet);

            }
            System.out.println("=========================");
        }
    }
}
