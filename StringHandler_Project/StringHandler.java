package edu.uwp.cs.csci242.assignments.a03.stringhandler;

/**
 * This is the StringHandler Interface. It holds three empty-bodied methods that other classes will implement.
 * The purpose of these methods will be to determine what do with either a digit, letter, or special character
 * when working with different types of Strings such as, hexadecimal Strings, a String that represents a password,
 * telephone number String, and an email address String.
 */
public interface StringHandler {
    /**
     * This is the processDigit method. It will take a char as a parameter and will be implemented
     * in a  different class.
     * @param digit char value of a digit
     */
        public void processDigit(char digit);

    /**
     * This is the proceesLetter method. It will take a char as a parameter and will be implemented
     * in a different class
     * @param letter char value of a letter
     */
         public void processLetter(char letter);

    /**
     * This is the processOther method. It will take a char as a parameter and will be implemented
     * in a different class.
     * @param other char value of a special character.
     */
    public void processOther(char other);
}
