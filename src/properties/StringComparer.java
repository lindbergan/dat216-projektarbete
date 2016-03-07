package properties;

import java.util.regex.Pattern;

public class StringComparer {

    //Matches the string to all letters and spaces. Returns true if String only contains letters or spaces.
    public static boolean onlyContainsLetter(String str){


        for (int i = 0; i < str.length(); i++){

            if(!Pattern.matches("[a-zA-Z åäöÅÄÖ]+", str)){
                return false;
            }
        }
        return true;
    }

    //Matches the string to all numbers. Returns true if String only contains numbers.
    public static boolean onlyContainsNumbers(String str){

        for (int i = 0; i < str.length(); i++){

            if(!Pattern.matches("[0-9]+",str)){
                return false;
            }
        }
        return true;
    }
}
