package edu.uwp.cs.csci242.assignments.a03.stringhandler;

/**
 * This class has an instance of StringHandler. This class contains a constructor that will take
 * a StringHandler Object as a parameter and a method that will take a String as parameter. This method
 * will walk through the entire String and determine with method of StringHandler will be used.
 */
public class StringParser {
    private StringHandler handler;

    /**
     * Constructor for making a StringParser
     * @param passText takes a StringHandler Object as a parameter
     */
    public StringParser(StringHandler passText) {
        handler = passText;
    }

    /**
     * This method will walk through a String and determine which method of StringHandler will be used/implemented.
     * @param s takes a String as an argument to be walked through.
     */
    public void parse(String s) throws IllegalArgumentException{
        //Check to see if the StringHandler is not an instance of UsTelephoneHandler
        if(!(handler instanceof UsTelephoneHandler))
        //Loop to walk through string and call the correct method to process certain characters
        for (int i = s.length()-1; i >= 0; i--) {
            //This check is for letter A-Z and a-z
            if (s.charAt(i) >= 65 && s.charAt(i) <= 90 || s.charAt(i) >= 97 && s.charAt(i) <= 122) {
                handler.processLetter(s.charAt(i));
            //This check is for 0-9
            } else if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
                handler.processDigit(s.charAt(i));
            //This check is for special characters with ASCII values between 33-37
            } else if (s.charAt(i) >= 33 && s.charAt(i) <= 37) {
                handler.processOther(s.charAt(i));
            //This check is for special characters with ASCII values between 58-64
            } else if (s.charAt(i) >= 58 && s.charAt(i) <= 64) {
                handler.processOther(s.charAt(i));
            //This check is for special characters with ASCII values between 91-96
            } else if (s.charAt(i) >= 91 && s.charAt(i) <= 96) {
                handler.processOther(s.charAt(i));
            //This check is for special characters with ASCII values between 123-126
            } else if (s.charAt(i) >= 123 && s.charAt(i) <= 126) {
                handler.processOther(s.charAt(i));
            //Throw statement for any case that isn't allowed
            } else {
                throw new IllegalArgumentException("Your text contains a character that is unrecognizable!");
            }
        }
        //Checks to see if the StringHandler is an instance of UsTelephoneHandler
        if(handler instanceof UsTelephoneHandler){
            //Loop to walk through string and call correct methods to deal with certain characters
            for(int i=0; i<s.length();i++){
                //This check is for 0-9
                if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
                    handler.processDigit(s.charAt(i));
                //This check is for the characters "(", ")", and "-"
                } else if (s.charAt(i) >= 40 && s.charAt(i) <= 41 || s.charAt(i)==45) {
                    handler.processOther(s.charAt(i));
                }
                //Throw statement for any case that isn't allowed
                else {
                    throw new IllegalArgumentException("Your text contains a character that is unrecognizable!");
                }
            }
        }
    }
}

