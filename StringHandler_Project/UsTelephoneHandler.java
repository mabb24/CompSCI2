package edu.uwp.cs.csci242.assignments.a03.stringhandler;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 * This class takes care of processing a string that is a telephone number.It will implement the methods
 * from StringHandler and check the char's taken in from the String that is in StringParser.
 * Those char's will be dealt with accordingly if it is a digit or special character.
 * The result of processing the char's will determine the if the telephone number is a valid number.
 * If the telephone number does not look like one of these, (847)-111-0234, (847)111-0234, (847)1110234,
 * 847-111-0234, or 8471110234 then it is invalid for this instance.
 */
public class UsTelephoneHandler implements Validator, StringHandler{
    //Flag for valid telephone number
    private boolean validTele;
    //Counter for amount of digits
    private int numCount;
    //Count for total amount of characters
    private int totalCount;
    //Class level variable to hold the telephone number being parsed
    private String telephone;

    //Constructor with no argument
    public UsTelephoneHandler(){
        validTele = true;
        telephone = "";
    }
    //Getter to return telephone number
    public String getTelephone(){

            return telephone;

    }

    /**
     * This method will check and make sure that the telephone number is a valid format or not. It will walk through
     * the string and check for special character cases.
     * @param phoneNumber takes a string as an argument that will be a telephone number
     * @return returns a string holding the telephone number and wheter it is valid or not.
     */
    public String checkTelephone(String phoneNumber) {
        //Loop to walk through string
            for (int i = 0; i < phoneNumber.length() - 1; i++) {
                //Switch case for special characters
                switch(phoneNumber.charAt(i)){
                    //Case for "("
                    case '(':{
                        if(phoneNumber.charAt(4) != 41){
                            validTele = false;
                        }
                    }
                    break;
                    //Case for ")"
                    case ')':{
                        if(phoneNumber.charAt(0) !=40){
                            validTele =false;
                        }
                    }
                    break;
                    //Case for "-"
                    case '-':{
                        if(totalCount == 14){
                            if(phoneNumber.contains("(") && phoneNumber.contains(")") &&
                               phoneNumber.charAt(5) !=45 && phoneNumber.charAt(9) !=45){
                                validTele = false;
                            }
                        }
                        else if(totalCount == 13){
                            if(phoneNumber.contains("(") && phoneNumber.contains(")") &&
                               phoneNumber.charAt(8) !=45){
                                validTele = false;
                            }
                        }
                        else if(totalCount == 12){
                            if(phoneNumber.charAt(3) !=45 && phoneNumber.charAt(7) !=45){
                                validTele = false;
                            }
                        }
                        else{
                            validTele = false;
                        }
                    }
                    break;
                    default:
                        if(isDigit(phoneNumber.charAt(i)))
                            break;
                }
            }
        if(validTele){
            return phoneNumber + " is valid";
        }
        else{
            return phoneNumber + " is invalid";
        }
    }

    /**
     * This method is an implementation of a method in Validator. It returns the boolean flag for a valid telephone
     * @return boolean holding flag for valid telephone number
     */
    @Override
    public boolean isValid() {
        return validTele;
    }

    /**
     * This method is an implementation of a method in StringHandler. It checks if a char is a digit and
     * increases the counter for amount of numbers and counter for total character count. Sets valid telephone
     * flag to true if the number count is equal to 10 and finally adds the char to the instance string called
     * telephone.
     * @param digit char value of a digit
     */
    @Override
    public void processDigit(char digit) {
        //Check to see if the char is a digit
        if(isDigit(digit)) {
            //Increase number counter
            numCount++;
            //Increase total character counter
            totalCount++;
            //Sets valid telephone flag true if the number count is 10
            validTele = numCount == 10;
            //Adds the char to the telephone string
            telephone += digit;
        }

    }

    /**
     * This method is an implementation of a method in StringHandler. It checks to see if the char is a letter.
     * Telephone numbers do not contain letters so the valid telephone flag will be set to false.
     * @param letter char value of a letter
     */
    @Override
    public void processLetter(char letter) {
        //Check to see if the char is a letter. If so then the valid telephone flag will be set to false.
        if(isLetter(letter)) validTele = false;
    }

    /**
     * This method is an implementation of a method in StringHandler. It checks to see if the char is a
     * special character. It only allows the special characters that are "(", ")", and "-". Then it adds it to
     * the telephone string.
     * @param other char value of a special character.
     */
    @Override
    public void processOther(char other) {
        //Check to see if the char has an ASCII value of 40, 41, or 45
        if(other ==40 || other ==41 || other ==45){
            //Adds the special character to the telephone string
            telephone += other;
            //Increase the total count
            totalCount++;
        }
        else{
            validTele = false;
        }
    }

}
