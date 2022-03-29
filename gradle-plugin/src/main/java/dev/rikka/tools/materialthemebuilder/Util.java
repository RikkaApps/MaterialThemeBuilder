package dev.rikka.tools.materialthemebuilder;

public class Util {

    public static String capitalize(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public static String decapitalize(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toLowerCase(arr[0]);
        return new String(arr);
    }
}
