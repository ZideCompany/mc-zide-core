package fr.nkw.zidemcore.Lib;

import java.util.TreeMap;

public class RomanNumber {
    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public static String toRoman(int number) {
        int l =  map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }

    public static int fromRoman(String roman) {
        int result = 0;
        String remaining = roman;
        for (Integer key : map.descendingKeySet()) {
            String symbol = map.get(key);
            while (remaining.startsWith(symbol)) {
                result += key;
                remaining = remaining.substring(symbol.length());
            }
        }
        if (!remaining.isEmpty()) {
            throw new IllegalArgumentException("Invalid Roman numeral: " + roman);
        }
        return result;
    }
}
