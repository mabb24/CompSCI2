package edu.uwp.cs.csci242.assignments.a03.stringhandler;


import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 * This class handles Hexadecimal Strings. It will implement the methods from StringHandler and turn the char's
 * taken in from the String that is in StringParser. Those char's will be dealt with accordingly if it is
 * a digit, letter, or special character. The result of processing the char's will be give in Base 10 (Decimal).
 * The class will also implement the method from Validator to determine if the String being run through this
 * class is a valid Hexadecimal.
 */
public class HexStringHandler implements StringHandler, Validator {
    //Flag for a valid hexadecimal
    private boolean validHex;
    //Integer holding the decimal value of the hexadecimal
    private int number;
    //Integer counter for the power that will be used in the equation
    private int power = 0;
    //Constant for an invalid number
    final private int INVALID_NUMBER = -1;
    //Constant for number system being used
    final private int NUMBER_SYSTEM = 16;
    final private int NUMBER_LETTER_MIN = 10;
    final private int NUMBER_LETTER_MAX = 16;

    /**
     * Constructor that has no argument
     */
    public HexStringHandler() {
        number = 0;
        validHex = true;
    }

    /**
     * getter to return the Base 10 version of the hexadecimal that is run through this class
     *
     * @return returns an int that holds the value of Base 10 version of the hexadecimal
     */
    public int getNumber() {
        if (isValid()) {
            return number;
        } else {
            return INVALID_NUMBER;
        }
    }


    /**
     * This method is one implementation of a method in StringHandler. It checks to see if the char is a digit, then
     * it sets the boolean validHex to true, and finally, does the math to turn the char hexadecimal to Base 10.
     *
     * @param digit char value of a digit
     * @throws IllegalArgumentException
     */
    @Override
    public void processDigit(char digit) throws IllegalArgumentException {
        //Check to make sure char is between 0-9
        if (digit >= 48 && digit <= 57) {
            //Creates an Integer value of the char
            int hexDigit = Integer.parseInt(String.valueOf(digit));
            //Takes new Integer and multiplies it by 16 to the power of its postion
            number += hexDigit * (int) (Math.pow(NUMBER_LETTER_MAX, power));
            power++;
        } else {
            throw new IllegalArgumentException("Not a digit!");
        }
    }

    /**
     * This method is one implementation of a method in StringHandler. It checks to see if the char is a letter,
     * then it sets the validHex to true, and finally, does the math to turn the char hexadecimal to base 10.
     *
     * @param letter char value of a letter
     * @throws IllegalArgumentException
     */
    @Override
    public void processLetter(char letter) throws IllegalArgumentException {
        //Check to make sure char is between A-F
        if (letter >= 65 && letter <= 70) {
            //Creates an Integer with the value of the hexadecimal letter in base 10
            int hexLetter = Integer.parseInt(String.valueOf(letter), NUMBER_SYSTEM);
            number += hexLetter * ((int) (Math.pow(NUMBER_LETTER_MAX, power)));
            power++;
        } else {
            validHex = false;
            throw new IllegalArgumentException("Not a Hexadecimal letter!");
        }

    }

    /**
     * This method is one implementation of a method in StringHandler. It checks to see if the char is a special
     * character and then sets validHex to false.
     *
     * @param other char value of a special character.
     * @throws IllegalArgumentException
     */
    @Override
    public void processOther(char other) throws IllegalArgumentException {
        //Check to make sure there are not special characters
        if (!isDigit(other) || !isLetter(other)) {
            validHex = false;
        } else {
            throw new IllegalArgumentException("Not a special character!");
        }

    }

    /**
     * This method is an implementation of a method in Validator. It returns a boolean of validHex.
     * @return returns validHex
     */

    @Override
    public boolean isValid() {

        return validHex;

    }
}