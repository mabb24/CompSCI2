/**
 * File input/output class.
 * <p>
 * This class makes file input/output easier. The class handles the file names
 * as well as opening and closing the input Scanner and the output PrintWriter.
 * <p>
 * Filenames are stored as private Strings.  Getters and setters are available
 * to retrieve and set the filenames.  In addition, methods are available to
 * open and close the input Scanner and the output PrintWriter.  There are also
 * two meta-methods that allow for the opening and closing of both the input
 * Scanner and the output PrintWriter. Finally, two default file names are
 * provided if the user does not provide them.
 * <p>
 * A constructor provides an easy way to use the class without calling the
 * individual methods. The constructor takes three parameters but
 * will also use the default file names if the user does not provide them.
 *
 * @author Mark Barsky
 * @edu.uwp.cs.242.course CSCI 242 - Computer Science II
 * @edu.uwp.cs.242.section 001
 * @edu.uwp.cs.242.assignment 2
 * @bugs None
 */
package edu.uwp.cs.csci242.assignments.a03.stringhandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileInOut
{
    /**
     * Name of the input file.
     */
    private String inFilename;
    /**
     * Name of the output file.
     */
    private String outFilename;
    /**
     * Name of the Scanner object used to perform input.
     */
    private Scanner inFileScanner;
    /**
     * Name of the PrintWriter object that performs output.
     */
    private PrintWriter outFilePrinter;
    /**
     * Default file names.
     */
    private final String DEFAULTINFILENAME = "default_in.txt";
    private final String DEFAULTOUTFILENAME = "default_out.txt";
    /**
     * Three argument constructor to instantiate a FileInOut object.
     * <p>
     * This constructor uses the provided input and output file names to set
     * the objects internal input and output file names. The files can also
     * be opened by passing TRUE as the pOpenFlag parameter.
     *
     * @param pIn String value for the name of the input file.
     * @param pOut String value for the name of the output file.
     * @param pOpenFlag Flag that determined whether the files will be opened
     * or not. TRUE means that the files should be opened; FALSE otherwise.
     */
    public FileInOut(String pIn, String pOut, boolean pOpenFlag )
    {
        if(pIn.length() > 0) {
            setInFilename(pIn);
            setOutFilename(pOut);
        }
        else {
            inFilename = DEFAULTINFILENAME;
            outFilename = DEFAULTOUTFILENAME;
        }
        if (pOpenFlag)
            openFiles();
    }
    /**
     * Meta-method that opens both the input file and the output file.
     */
    public void openFiles()
    {
        this.openInFile();
        this.openOutFile();
    }
    /**
     * Opens the input file for input using a Scanner.
     * <p>
     * This method opens the file whose name is specified in the class level
     * variable inFilename.  The length is checked to ensure the variable has
     * content.  The file is opened via the Java Scanner class.
     */
    public void openInFile() {

        File inFile;
        inFile = new File(inFilename);

        if (inFilename.length() > 0) {
            try {
                inFileScanner = new Scanner(inFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace(System.out);
            }
        }
        else {
            inFile = new File(DEFAULTINFILENAME);
        }

    }
    /**
     * Opens the output file for output using a PrintWriter.
     * <p>
     * This method opens the file whose name is specified in the class level
     * variable outFilename.  The length is checked to ensure the variable has
     * content.  The file is opened via the Java PrintWriter class.
     */
    public void openOutFile()
    {
        File outFile;
        outFile = new File(outFilename);

        if (outFilename.length() > 0) {
            try {
                outFilePrinter = new PrintWriter(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace(System.out);
            }
        }
        else {
            outFile = new File(DEFAULTOUTFILENAME);
        }

    }
    /**
     * Meta-method to close all of the open files.
     */
    public void closeFiles()
    {
        this.closeInFile();
        this.closeOutFile();
    }
    /**
     * Closes the input file.
     */
    public void closeInFile()
    {
        inFileScanner.close();
    }
    /**
     * Closes the output file.
     */
    public void closeOutFile()
    {
        outFilePrinter.close();
    }
    /**
     * Retrieves the input file name.
     *
     * @return String value of the input file name.
     */
    public String getInFilename()
    {
        return inFilename;
    }
    /**
     * Sets the input file name.
     *
     * @param inFilename String value of the input file name.
     */
    public void setInFilename ( String inFilename )
    {
        this.inFilename = inFilename;
    }
    /**
     * Retrieves the output file name.
     *
     * @return String value of the output file name.
     */
    public String getOutFilename ( )
    {
        return outFilename;
    }
    /**
     * Sets the output file name.
     *
     * @param outFilename String value of the output file name.
     */
    public void setOutFilename ( String outFilename )
    {
        this.outFilename = outFilename;
    }
    /**
     * Retrieves the input Scanner.
     *
     * @return Scanner object for performing input on the opened file.
     */
    public Scanner getInFile()
    {
        return inFileScanner;
    }
    /**
     * Retrieves the output PrintWriter.
     *
     * @return PrintWriter object for performing output on the opened file.
     */
    public PrintWriter getOutFile()
    {
        return outFilePrinter;
    }
}