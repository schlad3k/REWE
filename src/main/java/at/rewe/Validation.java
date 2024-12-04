package at.rewe;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private String error;
    private Map<String,String> map;

    public Validation(Map<String,String> map) {
        this.map = map;
    }

    public enum Regex {
        NUMWERTE4("^\\d+(\\.\\d{1,4})?$"),
        NUMWERTE("^\\d+$"),
        CHECKBOX("^[xXjJyY]$"),
        DATE("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$"),
        GTIN("^\\d{1,14}$"),
        GLN("^\\d{13}$"),
        KEINE("^.*$"),
        LENGTH70(),
        LENGTH40();

        private String regex;

        Regex() {
            this.regex = "length";
        }
        Regex(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }

    }

    public String validate() {
        List<Regex> list = Arrays.asList(Regex.LENGTH70, Regex.GLN, Regex.DATE, Regex.NUMWERTE, Regex.KEINE,
                Regex.LENGTH40, Regex.KEINE, Regex.KEINE, Regex.NUMWERTE, Regex.KEINE, Regex.KEINE, Regex.NUMWERTE4, Regex.KEINE,
                Regex.NUMWERTE, Regex.NUMWERTE, Regex.KEINE, Regex.KEINE, Regex.NUMWERTE, Regex.GTIN, Regex.GTIN,Regex.GTIN,Regex.GTIN,
                Regex.NUMWERTE,Regex.NUMWERTE,Regex.NUMWERTE, Regex.NUMWERTE4, Regex.NUMWERTE,
                Regex.CHECKBOX, Regex.CHECKBOX, Regex.KEINE, Regex.CHECKBOX,Regex.CHECKBOX,Regex.CHECKBOX,
                Regex.CHECKBOX, Regex.KEINE, Regex.NUMWERTE4,Regex.NUMWERTE4,Regex.NUMWERTE4, Regex.CHECKBOX);
-
        Iterator<String> iterator = map.keySet().iterator();
        for(int i = 0; i < list.size(); i++) {
            if(iterator.hasNext()) {
                if(list.get(i).equals(Regex.LENGTH70)) {
                    if(iterator.next().length() > 70) {
                        System.out.println("Error");
                        error = "Longer than 70";
                    }
                }
                if(iterator.next().equals(Regex.LENGTH40)) {
                    if(iterator.next().length() > 40) {
                        System.out.println("Error");
                        error = "Longer than 40";
                    }
                }
                Pattern pattern = Pattern.compile(list.get(i).getRegex(), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(iterator.next());
                boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println("No Error");
                }
            }
        }
        return error;
    }
}
