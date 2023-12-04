package Utils;

import Service.SortType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class input {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String YEARMONTH_FORMAT = "yyyy-MM";

    public static String inputString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String inputString(String message, boolean allowBlankOrEmpty) {
        String inputString;
        Scanner sc = new Scanner(System.in);
        if (allowBlankOrEmpty) {
            message += ": ";
        } else {
            message += " \n(not blank or empty): ";
        }
        do {
            System.out.print(message);
            inputString = sc.nextLine();
        } while (!allowBlankOrEmpty && inputString.trim().isEmpty());
        return inputString;
    }

    public static SortType inputSortType() {
        StringBuilder sb = new StringBuilder();
        int type = 0;

        for (SortType value : SortType.values()) {
            sb.append("\n\t" + type++ + ": " + value);
        }

        type = Utils.input.inputintlimit("Input type (" + sb.toString() + "): ", 0, SortType.values().length - 1);
        return SortType.values()[type];
    }

    public static String inputStringwithrex(String welcome, String pattern,
            String msg, String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String inputStringwithrexAlowEmty(String welcome, String pattern,
            String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                check = false;
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static int inputInt(String welcome, int min) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be larger than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static int inputintlimit(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be larger than " + min
                            + "and smaller than " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min || number > max);
        return number;
    }

    public static float inputfloat(String welcome, int min) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be larger than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static float inputfloatlimit(String welcome, float min, float max) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be larger than " + min
                            + "and smaller than " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min || number > max);
        return number;
    }

    public static double inputdouble(String welcome, int min) {
        boolean check = true;
        double number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Double.parseDouble(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be larger than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static double inputdoublelimit(String welcome, double min, double max) {
        boolean check = true;
        double number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Double.parseDouble(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be larger than " + min
                            + "and smaller than " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min || number > max);
        return number;
    }

    public static Date ParseDate(String strDate) throws ParseException {
        if (strDate == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        return df.parse(strDate);
    }

    public static String toString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    public static Date inputDate(String message) {
        Scanner sc = new Scanner(System.in);
        Date date = null;
        do {
            try {
                System.out.print(message + "(" + DATE_FORMAT + "): ");
                date = ParseDate(sc.nextLine());

            } catch (ParseException ex) {
                System.out.println("Input Date!!!");
//                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, exU001);
            }
        } while (date == null);
        return date;
    }

    public static YearMonth inputYearMonth(String message) {
        Scanner sc = new Scanner(System.in);
        YearMonth ym = null;
        do {
            try {
                System.out.print(message + "(" + YEARMONTH_FORMAT + "): ");
                ym = ym.parse(sc.nextLine());

            } catch (Exception ex) {
                System.out.println("Input Date!!!");
//                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, exU001);
            }
        } while (ym == null);
        return ym;
    }

    public static Date inputDateAlowEmty(String message) {
        Scanner sc = new Scanner(System.in);
        Date date = null;
        String sDate = null;
        do {
            try {
                System.out.print(message + "(" + DATE_FORMAT + "): ");
                sDate = sc.nextLine().trim();
                if (sDate.isEmpty()) {
                    return null;
                } else {
                    date = ParseDate(sDate);
                }
            } catch (ParseException ex) {
                System.out.println("Input Date!!!");
//                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, exU001);
            }
        } while (date == null);
        return date;
    }

    public static YearMonth convertDateToYearMonth(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        YearMonth yearMonth = YearMonth.from(localDate);
        return yearMonth;
    }
}
