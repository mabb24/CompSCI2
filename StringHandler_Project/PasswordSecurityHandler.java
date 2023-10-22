package edu.uwp.cs.csci242.assignments.a03.stringhandler;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 * This class takes care of processing a string that will be checked for its security strength.
 * It will implement the methods from StringHandler and check the char's
 * taken in from the String that is in StringParser. Those char's will be dealt with accordingly if it is
 * a digit, letter, or special character. The result of processing the char's will determine the passwords
 * security strength. If the password has less than 8 characters it is weak, if it has 8 or more characters and there
 * is a number then it is medium, and if it has eight or more with numbers and a special character it is strong.
 */
public class PasswordSecurityHandler implements StringHandler {
    //Class level variable to know how long the password is
    private int length;
    //Class level variable to flag for a digit in the password
    private boolean digit;
    //Class level variable to flag if there is a special character
    private boolean otherCharacter;
    //Constants holding Strings for password strength: weak, medium, and strong
    final private String SECURITY_LEVEL_WEAK = "weak";
    final private String SECURITY_LEVEL_MEDIUM = "medium";
    final private String SECURITY_LEVEL_STRONG = "strong";

    /**
     * Constructor that has no argument
     */
    public PasswordSecurityHandler() {
        length = 0;
        digit = false;
        otherCharacter = false;
    }

    /**
     * Method to check passwords length, flag for digit, and flag for special character to determine
     * the strength of the password
     * @return returns string constant of either weak, medium, or strong.
     */
        public String securityLevel () {
            //If the length is less than eight, return weak
            if(length<8){
                return SECURITY_LEVEL_WEAK;
            }
            //If the length is more than eight, and it has a number, and special character, return strong
            else if((digit && otherCharacter)) {
                return SECURITY_LEVEL_STRONG;
            }
            //Else its level is medium
            else {
                return SECURITY_LEVEL_MEDIUM;
            }

        }

    /**
     * This method is an implementation of a method in StringHandler. It checks to see if a digit is passed through
     * then turns the flag to true.
     * @param digit char value of a digit
     * @throws IllegalArgumentException
     */
        @Override
        public void processDigit (char digit) throws IllegalArgumentException {
            //Check to see if the char is a digit
            if (isDigit(digit)) {
                //Set flag to true
                this.digit = true;
                //Increases length variable
                length++;
            }
            else{
                throw new IllegalArgumentException("Not a number");
            }
        }

    /**
     * This method is an implementation of a method in StringHandler. It checks to see if the char is a letter
     * and then it increases the length variable.
     * @param letter char value of a letter
     * @throws IllegalArgumentException
     */
    @Override
        public void processLetter (char letter) throws IllegalArgumentException{
        //Check to see if the char is a letter
        if(isLetter(letter)) {
            //Increase length variable
            length++;
        }
        else{
            throw new IllegalArgumentException("Not a letter");
        }

        }

    /**
     * This method is an implementation of a method in StringHandler.It checks to see if the char is a special
     * character who's ASCII value is more greater than or equal to 33
     * @param other char value of a special character.
     * @throws IllegalArgumentException
     */
        @Override
        public void processOther (char other) throws IllegalArgumentException{
            //Check to see if the char is not a digit or letter and its ASCII value is larger than 33
            if(!isDigit(other) && !isLetter(other) && other >= 33) {
                //Sets special character flag to true
                otherCharacter = true;
                //Increase length variable
                length++;
            }
            else{
                throw new IllegalArgumentException("Not a special character");
            }
        }
    }
