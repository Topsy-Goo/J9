package ru.gb.antonov;

public class Library {

    public static final String LIST_DELIMITER = ", ";

    public static boolean isStringValid (String s) {
        return s != null && !s.isBlank();
    }

    public static void lnprint (Object s) { System.out.print ("\n"+s);  }
    public static void print (Object s) { System.out.print (s);  }
    public static void println (Object s) { System.out.print (s+"\n");  }
}
