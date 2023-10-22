package edu.uwp.cs.csci242.assignments.a03.stringhandler;

import java.util.Scanner;

/**
 * This program will read in a text file and as it walks through the file it will determine whether
 * the String it is reading is going to be a password, hexadecimal, telephone, or email address. The
 * program will then create the appropriate object for the respective String and parse the String by using the methods
 * in the StringHandler Interface, which are implemented in the appropriate classes. After those classes do their work
 * the results will be printed.
 *
 *  * @author Mark Barsky
 *   @edu.uwp.cs.242.course CSCI 242 - Computer Science II
 *  * @edu.uwp.cs.242.section 001
 *  * @edu.uwp.cs.242.assignment 3
 *  * @bugs None
 */
public class StringHandlerApp {

    public static void main(String[] args) {
        String fileName = "data.txt";
        FileInOut passwordFile = new FileInOut(fileName, "", true);
        Scanner fileReader = passwordFile.getInFile();
        readPasswords(fileReader);
    }

    /**
     * This method will do the work of reading through the text file. It will also determine whether the String it is
     * going to read in next is either a hexadecimal, password, telephone number, or email address.
     * Once determined, a new Object will be created, it will be parsed and then the results will be printed.
     * @param read takes a scanner as an argument to be able to walk through a file.
     */
    public static void readPasswords(Scanner read) {
        //Loop to walk through entire text file
            do {
                //Variable to hold a String that will determine the type of Object to create
                String passwordType = read.next();
                //If statement to make sure there is a next line to read in.
                if (read.hasNextLine()) {
                    //Switch statement to determine which object to make and parse
                    switch (passwordType.toUpperCase()) {
                        //Case for hexadecimals
                        case "H": {
                            //Variable to hold next text in the text file
                            String hexString = read.next();
                            //Creates new instance of a HexStringHandler
                                HexStringHandler hexPass = new HexStringHandler();
                            //Creates a new instance of StringParser with a StringHandler being passed through
                                StringParser hexCharReader = new StringParser(hexPass);
                            //Call the parse method to parse the text from the text file
                                hexCharReader.parse(hexString);
                            //Print statement to print the original text with the number after parsing
                                System.out.println(hexString + " = " + hexPass.getNumber());

                        }
                        break;
                        //Case for passwords
                        case "P":{
                            //Variable to hold next text in the text file
                                String password = read.next();
                            //Creates new instance of a PasswordSecurityHandler
                                PasswordSecurityHandler securePass = new PasswordSecurityHandler();
                            //Creates a new instance of StringParser with a StringHandler being passed through
                                StringParser secureCharReader = new StringParser(securePass);
                            //Call the parse method to parse the text from the text file
                                secureCharReader.parse(password);
                            //Variable to hold the String returned from calling securityLevel
                                String level = securePass.securityLevel();
                            //Print statement to print the passwords security level
                                System.out.println(password + "'s"+ " security is: " + level);
                        }
                        break;
                        //Case for telephone numbers
                        case "T": {
                            //Variable to hold next text in the text file
                            String usTelephone = read.next();
                            //Creates new instance of UsTelephoneHandler
                            UsTelephoneHandler newTelephone = new UsTelephoneHandler();
                            //Creates a new instance of StringParser with a StringHandler being passed through
                            StringParser telephoneParser = new StringParser(newTelephone);
                            //Call the parse method to parse the text from the text file
                            telephoneParser.parse(usTelephone);
                            //Print statement to print telephone numbers and if they are valid
                            System.out.println(newTelephone.checkTelephone(newTelephone.getTelephone()));
                        }
                        break;
                        //Default case
                        default:
                            if (read.hasNextLine()) {
                                read.nextLine();
                            }
                    }
                }
            }
            while (read.hasNext());
    }
}