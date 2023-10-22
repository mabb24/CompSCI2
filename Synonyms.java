

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This project is a collection of URLs that we parse through. With user defined words we find the semantic similarity
 * between the words the user gives. The first word the program asks from the user is the main word that is being
 * looked against. The user then can enter a number of choices (in one line) and the program will check the
 * semantic similarity of the main word against all the others and choose the word that has the greatest semantic
 * similarity.
 *
 * @author Mark Barsky
 * @edu.uwp.cs.242.course CSCI 242 - Computer Science II
 * @edu.uwp.cs.242.section 001
 * @edu.uwp.cs.242.assignment 5
 * @bugs None
 */

public class Synonyms {
    //Class level variable holding the main HashMap with the user defined Keys
    private static HashMap<String, HashMap<String,Integer>> corpusMap;
    //Class level variable that holds the first word the user inputs
    private static String input;
    //Class level variable that is an array holding the other choices the user inputs
    private static String [] options;
    //Class level variable that is an array holding all the URLs
    private static URL[] corpus;
    static {
        //Try-Catch block that once a URL is trying to be opened it will throw an exception if
        //the URL is not correct
        try {
            corpus = new URL[]{
                    // Pride and Prejudice, by Jane Austen
                    new URL( "https://www.gutenberg.org/files/1342/1342-0.txt" ),
                    // The Adventures of Sherlock Holmes, by A. Conan Doyle
                    new URL ( "http://www.gutenberg.org/cache/epub/1661/pg1661.txt" ),
                    // A Tale of Two Cities, by Charles Dickens
                    new URL ( "https://www.gutenberg.org/files/98/98-0.txt" ),
                    // Alice's Adventures In Wonderland, by Lewis Carroll
                    new URL ( "https://www.gutenberg.org/files/11/11-0.txt" ),
                    // Moby Dick; or The Whale, by Herman Melville
                    new URL ( "https://www.gutenberg.org/files/2701/2701-0.txt" ),
                    // War and Peace, by Leo Tolstoy
                    new URL ( "https://www.gutenberg.org/files/2600/2600-0.txt" ),
                    // The Importance of Being Earnest, by Oscar Wilde
                    new URL ( "http://www.gutenberg.org/cache/epub/844/pg844.txt" ),
                    // The Wisdom of Father Brown, by G.K. Chesterton
                    new URL ( "https://www.gutenberg.org/files/223/223-0.txt" ),
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This is the main of the program. It instantiates a new HashMap that will hold the cosine similarities of the
     * main input word with the choices the user defines. Afterwards, there is a Try-Catch block that will try and
     * have the user input a word and then the other choices they choose to find the semantic similarity with. The
     * choices are split into an Array to be accessed. The next step is calling the constructor which instantiates
     * the class level corpusMap HashMap and then calls parseCorpus. After the code runs, the HashMaps should be filled
     * and then the cosine similarities can be found. Each choice is then printed with their cosine similarity to
     * the first input word and the word with the greatest similarity is printed. The code will then ask the user again
     * for a word and if they enter nothing the code will end.
     * @param args
     * @throws MalformedURLException exception for incorrect URL
     */
    public static void main(String[] args) throws MalformedURLException {
        //HashMap to hold the cosine similarities
        HashMap<String, Double> cosines = new HashMap<>();
        int count = 1;

        //Try-Catch block that creates a scanner to read a user input and run the code afterwards
        try {
            //Creates a scanner to read user input
            Scanner user = new Scanner(System.in);
            // Loop to run as long as the user enters a word. Entering nothing will result in the program finishing
            for (int i = 0; i < count; i++) {
                System.out.println("Please enter a word:");

                //User input using class level variable for the main word
                input = user.nextLine();
                if (input.equals("")) {
                    return;
                } else {
                    System.out.println("Please enter the choices:");

                    //Line of choices that are user defined
                    String choices = user.nextLine();

                    //Splits the line into an array of choices. The choices are split are the white spaces. Class level
                    //array options is used.
                    options = choices.split("\\s+");

                    //Calls the constructor with the corpus array as a parameter
                    new Synonyms(corpus);

                    //For each loop that runs through each String in the options array
                    for (String choice : options) {
                        //variable that will hold the cosine similarity between the input word and every word in options
                        double cosineSim = similarity(input, choice);
                        System.out.println(choice + ": " + cosineSim);
                        //Puts all the Strings in options into the cosines HashMap with their cosine similarity to the
                        //input word
                        cosines.put(choice, cosineSim);
                    }
                    System.out.println(getKey(cosines, maxEntryValue(cosines)));
                    corpusMap.clear();
                    cosines.clear();
                    //Hold a count for the loop
                    count++;

                }

            }

        }
        //Catch's any exception
        catch(Exception e){
                e.printStackTrace();
            }

    }


    /**
     * This method will parse through the array of URLs and open them one at a time. At the start of the method
     * a new HashMap is instantiated that will be the Value of the corpusMap HashMap. The corpusMap HashMap is then
     * filled with Keys that were defined by the user in the main (input, and all Strings in options). As each URL is
     * opened a Scanner is used to hold on sentence at a time. The program then does multiple checks to see if any of
     * the Keys in CorpusMap are in the sentence. If they are, then the sentence will be split and the innerMap
     * HashMap will be filled with Keys and Values.
     * @param corpus takes an array of URLs as a parameter
     */
    public static void parseCorpus(URL[] corpus) {
            //Instantiates the HashMap that is the Value for the corpusMap HashMap
            HashMap<String, Integer> innerMap = new HashMap<>();

            //Puts the input into the corpusMap Key and instantiates a new HashMap for that Key
            corpusMap.put(input, new HashMap<>());
            //For each loop that put all the Strings in options into the corpusMap Keys and instantiates
            //a new HashMap for those Keys
            for(String choices: options) {
                corpusMap.put(choices, new HashMap<>());
            }

            //For each loop to open all URLs in the corpus
            for (URL url : corpus)
                //Try-Catch block to try and open the URLs and parse through them
                try {
                    //Creates a new Scanner
                    Scanner reader = new Scanner(url.openStream());

                    //Sets a delimiter for the Scanner to take a line up to a ".", "?", "!", or end of line
                    reader.useDelimiter("[\\.\\?\\!]|\\Z");

                    while (reader.hasNext()) {
                        //Variable holding a sentence and turning it all lowercase, takes away punctuations, and trims
                        //white spaces at the end and beginning of the sentence
                        String sentence = reader.next().toLowerCase().replaceAll("\\W+", " ").trim();

                        //For each loop to walk through all Keys in the corpusMap keySet
                        for(String keyString: corpusMap.keySet()){
                            //Check to see if the sentence contains the Key
                            if(sentence.contains(keyString)){
                                //Sets the innerMap HashMap equal to the Key it is looking at
                                innerMap = corpusMap.get(keyString);
                                //Splits the sentence into an array of Strings
                                String [] word = sentence.split("\\s+");
                                //For each loop to walk through each String in the array of the split sentence
                                for(String text: word){
                                    //Check to make sure the Key doesnt get added to the innerMap
                                    if(!text.equals(keyString)){
                                        //Check to make sure the String is not in the innerMap and the adds it to the
                                        //innerMap with a value of 1
                                        if(!innerMap.containsKey(text)){
                                            innerMap.put(text,1);

                                        //If the String is in the innerMap then it will pull the value and increment it
                                        }else{
                                            int count = innerMap.get(text);
                                            innerMap.put(text,(count+1));

                                        }
                                    }
                                }
                            }
                        }
                    }
                    //Catch's exceptions
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    /**
     * Constructor for Synonyms
     * @param corpus takes an array of URLs as a parameter
     * @throws MalformedURLException exception for an incorrect URL
     */
    public Synonyms(URL []corpus) throws MalformedURLException {
            //Instantiates the corpusMap class level variable
            corpusMap = new HashMap<>();
            //Call parseCorpus
            parseCorpus(corpus);
        }

    /**
     * This method calls two other methods to calculate the cosine similarity and returns the similarity as a double
     * @param firstWord takes a String as a parameter (will be the input word)
     * @param secondWord takes a String as a parameter (will be the choices in the options array)
     * @return returns the cosine similarity
     */
    public static double similarity(String firstWord, String secondWord){
            //if the size of the value of the first or second string is 0 then it will return -1
            if(corpusMap.get(firstWord).size()==0 || corpusMap.get(secondWord).size()==0){
                return -1.0;
            }
            //Calls dotProduct and magnitude to return values to divide
            return dotProduct(firstWord,secondWord)/magnitude(firstWord,secondWord);
        }

    /**
     * This method takes the dotProdcut of the two vectors. The dotProduct is the sum of all the words that are similar
     * in the vectors multiplied together
     * @param firstWord takes a String as a parameter (will be the input word)
     * @param secondWord takes a String as a parameter (will be the choices in the options array)
     * @return returns a double of the dot product
     */
        private static double dotProduct(String firstWord, String secondWord){
            //Variable to hold the sum of the firstWords values
            int firstSum;
            //Variable to hold the sum of the secondWords values
            int secondSum;
            //Variable to hold the multiplication of the other two variables
            double dot = 0.0;

            //Creates a set of the Keys in the corpusMap
            Set<String> keys = corpusMap.get(firstWord).keySet();
            //For each loop to walk through all the Keys
            for(String key: keys){
                //Checks to see if innerMap of the secondWord contains any of the Keys in the firstWord
                if(corpusMap.get(secondWord).containsKey(key)){
                    //Overrides the value of firstSum with the value of the innerMap of the for the firstWord
                    firstSum = corpusMap.get(firstWord).get(key);
                    //Overrides the value of firstSum with the value of the innerMap of the for the secondWord
                    secondSum = corpusMap.get(secondWord).get(key);
                    //Multiplies firstSum and secondSum and keeps a count for all similar keys
                    dot += firstSum * secondSum;
                }
            }
            return dot;
        }

    /**
     * This method takes the magnitude of two vectors. The magnitude is the sum of all the values in the vectors and
     * then multiply the values together. In case of any negative numbers, each value is squared and then the square
     * root is taken from the whole value.
     * @param firstWord takes a String as a parameter (will be the input word)
     * @param secondWord takes a String as a parameter (will be the choices in the options array)
     * @return returns a double of the magnitude
     */
        private static double magnitude(String firstWord,String secondWord){
            //Variable holding the magnitude
            double mag = 0.0;
            //Variable holding the firstWords sums
            int firstNum = 0;
            //Variable holding the secondWords sums
            int secondNum = 0;

            //For each loop to get each Key in the firstWords keySet
            for(String key: corpusMap.get(firstWord).keySet()){
                //Adds all the values of the innerMap for the firstWord and squares them
                firstNum += (int)Math.pow(corpusMap.get(firstWord).get(key),2);
            }

            //For each loop to get each Key in the secondWords keySet
            for(String key: corpusMap.get(secondWord).keySet()){
                //Adds all the values of the innerMap for the secondWord and squares them
                secondNum += (int)Math.pow(corpusMap.get(secondWord).get(key),2);
            }

            //Square roots the firstSum and secondSum and then multiplies them together to get the magnitude
            mag = Math.sqrt(firstNum) * Math.sqrt(secondNum);
            return mag;
        }

    /**
     * This method returns the largest cosine similarity
     * @param cosSimilarity takes a HashMap as a parameter
     * @return returns a Double of the cosine similarity
     */
    public static Double maxEntryValue(HashMap<String, Double> cosSimilarity){
            //Instantiates a Map of Entries
            Map.Entry<String, Double> max = null;
            //For each look at each Map/Entry from the entrySet of the passed in HashMap
            for(Map.Entry<String, Double> set: cosSimilarity.entrySet()){
                //Check to see if max is null or when the sets value is compared with max's value if the returned number
                //is greater than 0 (meaning set is larger than max)
                if(max == null || set.getValue().compareTo(max.getValue())> 0){
                    max = set;
                }
            }

            assert max != null;
            return max.getValue();
        }

    /**
     * This method returns the Key given a value
     * @param myMap takes a HashMao as a parameter
     * @param value takes Double as a parameter
     * @return returns a String of the Key
     */
    public static String getKey(HashMap<String,Double> myMap, Double value){
        //For each loop to look at each Key in the passed in HashMap
            for(String key: myMap.keySet()){
                //Checks to see if the value is equal to the Key and returns it
                if(value.equals(myMap.get(key))){
                    return key;
                }
            }
            return null;
        }
    }
