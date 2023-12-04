package Menu;

import Login.AccountService;

public class Menu {

    private final MenuItem[] mainOption = {
        MenuItem.EXIT,
        MenuItem.PET,
        MenuItem.CUSTOMER,
        MenuItem.ORDER
    };
    private final MenuItem[] petOption = {
        MenuItem.BACK,
        MenuItem.ADD_PET,
        MenuItem.UPDATE_PET,
        MenuItem.DELETE_PET,
        MenuItem.SEARCH_PET,
        MenuItem.SEARCH_PETLIST,
        MenuItem.SAVETOFILE_PET,
        MenuItem.DISPLAY_PET
    };
    private final MenuItem[] customerOption = {
        MenuItem.BACK,
        MenuItem.ADD_CUSTOMER,
        MenuItem.UPDATE_CUSTOMER,
        MenuItem.DELETE_CUSTOMER,
        MenuItem.SEARCH_CUSTOMER,
        MenuItem.SAVETOFILE_CUSTOMER,
        MenuItem.DISPLAY_CUSTOMER
    };
    private final MenuItem[] orderOption = {
        MenuItem.BACK,
        MenuItem.ADD_ORDER,
        MenuItem.SETTING_ORDER,
        MenuItem.SEARCH_CUSTOMERORDER,
        MenuItem.SEARCH_ORDERLIST,
        MenuItem.SAVETOFILE_ORDER,
        MenuItem.DISPLAY_ORDER,
        MenuItem.DISPLAY_SORT_ORDER
    };

    private MenuItem mainChoice = null;
    private MenuItem subChoice = null;

    public Menu() {
        this.mainChoice = MenuItem.EXIT;
        this.subChoice = MenuItem.BACK;
    }

    private MenuItem[] getOptionList(MenuItem option) {
        MenuItem[] optionList = null;
        switch (option) {
            case PET:
                optionList = petOption;
                break;
            case CUSTOMER:
                optionList = customerOption;
                break;
            case ORDER:
                optionList = orderOption;
                break;
            default:
                optionList = mainOption;
        };
        return optionList;
    }

    private int showOptionMenu(String menuCaption, MenuItem[] optionList) {
        int numItems = 1;
        System.out.println(menuCaption);
        AccountService accService = AccountService.getInstance();
        for (int i = 1; i < optionList.length; i++) {
            if (accService.checkCurrentUserRole(optionList[i].getRole())) {
                System.out.printf("(%d) -> %s\n", numItems, optionList[i].getLabel());
                numItems++;
            }
        }
        System.out.printf("(0) -> %s\n", optionList[0].getLabel());
        return numItems;
    }

    private MenuItem getChoice(MenuItem option) {
        MenuItem[] optionList = getOptionList(option);
        int numItems = showOptionMenu(option.getLabel(), optionList);
        System.out.println("+++++++++++++++++++++++++");
        int choice = Utils.input.inputintlimit("Inter your choice: ", 0, numItems - 1);
        AccountService accService = AccountService.getInstance();
        for (MenuItem menuItem : optionList) {
            if (accService.checkCurrentUserRole(menuItem.getRole())) {
                if (choice == 0) {
                    return menuItem;
                }
                choice--;
            }
        }
        return optionList[0];
    }

    public MenuItem getUserchoice() {
        do {
            if (subChoice == MenuItem.BACK) {
                mainChoice = getChoice(MenuItem.MAIN);
            }
            if (mainChoice != MenuItem.EXIT && !isRepeatAct()) {
                subChoice = getChoice(mainChoice);
            }
        } while (mainChoice != MenuItem.EXIT && subChoice == MenuItem.BACK);
        return mainChoice.equals(MenuItem.EXIT) ? MenuItem.EXIT : subChoice;
    }

    private boolean isRepeatAct() {
        boolean show = true;
        String choice;
        switch (subChoice) {
            case DISPLAY_SORT_ORDER:
            case ADD_CUSTOMER:
            case ADD_PET:
            case ADD_ORDER:
                choice = Utils.input.inputString(
                        "do you want to continue?(Yes/No): ",
                        "this can not be blank");
                return choice.trim().toUpperCase().startsWith("Y");
        }
        return false;
    }
}
