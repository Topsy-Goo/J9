package ru.gb.antonov;

public class Library {

    public static final String LIST_DELIMITER = ", ";

    public static boolean isStringValid (String s) {
        return s != null && !s.isBlank();
    }

    public static void lnprint (String s) { System.out.print ("\n"+s);  }
}
