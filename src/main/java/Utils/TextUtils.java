/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author Ellian
 */
public class TextUtils {
    public static double parseDouble(String input) {
        double value =0;
        int strLength = input.length();
        if (strLength >3) {
            input = input.replaceAll("[,.]+","");
            strLength = input.length();
            String firstHalf = input.substring(0,strLength-2);
            String lastHalf = input.substring(strLength-2);
            input = firstHalf+"."+lastHalf;
        } else {
            input = input.replace(",",".");
        }
        value = Double.parseDouble(input);
        return value;
    }
}
