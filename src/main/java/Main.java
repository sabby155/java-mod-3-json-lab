import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    //Create the list
    static List<Person> personList = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        initializeMainMenu();


    }

    private static void initializeMainMenu() throws IOException {
        System.out.println("Restored the following from person.data: ");
        System.out.println(readFromFile("person.data", true));

        //Ask the user if they want to restore the list of people from file.
        Logger.getInstance().log("Do you want to restore the list of people from file? Select 'Y' for YES, 'N' for NO, or 'X' for EXIT." );

        String inputFromUser = getUserInput();


        if (inputFromUser.equalsIgnoreCase("Y")) {

            //restore from the file you might have saved from a previous run of your program.
            System.out.println("Reading file...");
            System.out.println(readFromFile("person.data", true));


            initializeThreeOptionsMenu(personList);


        } else if (inputFromUser.equalsIgnoreCase("N")) {

            //Create brand new list
            List newPersonList = new ArrayList<>();

            initializeThreeOptionsMenu(newPersonList);

        } else if (inputFromUser.equalsIgnoreCase("X")) {
            //Save all the persons on list to the file and then exit
            exit();

        }
    }

    private static void initializeThreeOptionsMenu(List<Person> personList) throws IOException {
        //Give them 3 more options
        Logger.getInstance().log("Please press: 'A' to add a person to the list, 'P' to print a list of current persons, or 'X' to EXIT." );
        String inputFromUserAfterY = getUserInput();

        if(inputFromUserAfterY.equalsIgnoreCase("A")) {
            //Run the add person to the list method
            addPerson();
        } else if(inputFromUserAfterY.equalsIgnoreCase("P")) {
            //Run the print method
            printPeople();
        } else if(inputFromUserAfterY.equalsIgnoreCase("X")) {
            //Exit them and save
            exit();
        }

    }

    static void printPersonListAsJSON(List<Person> personList) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(personList);
        System.out.println(json);
    }

    private static void exit() throws IOException {
        System.out.println("Goodbye.");

        for (Person person : personList) {
            writeToFile("person.data", person.getName());
        }

        printPersonListAsJSON(personList);

    }

    private static void printPeople() throws IOException {
        //Print everyone in the persons list
        if(personList.size() > 0) {
            personList.forEach(person-> {
                System.out.println("Person: " + person.getName());
            });
        } else {
            System.out.println("There are no people on your list.");
        }

        //Return the user to the 3 options from before.
        initializeThreeOptionsMenu(personList);
    }

    private static void addPerson() throws IOException {

            System.out.println("Provide a name.");

            //Get name of a new person
            String userInputNewPersonName = getUserInput();


        //Create a new person with info
            Person newPerson = new Person(userInputNewPersonName);
            //Add them to original personList
            personList.add(newPerson);


        //Return the user to the 3 options from before.
        initializeThreeOptionsMenu(personList);
    }

    private static void writeToFile(String fileName, String text) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName, true);
            stringBuffer.append(text);
            fileWriter.write(stringBuffer.toString() + "\n");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    private static String readFromFile(String fileName, boolean addNewLine) throws IOException {
        String returnString = new String();
        Scanner fileReader = null;
        try {
            File myFile = new File(fileName);
            fileReader = new Scanner(myFile);
            while (fileReader.hasNextLine()) {
                returnString += fileReader.nextLine();
                if (addNewLine) {
                    returnString += "\n";
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileReader != null)
                fileReader.close();
        }

        return returnString;
    }

    private static String getUserInput() {
        try{
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            return userInput;
        } catch(InputMismatchException e) {
            System.out.println("Are you sure you selected correctly?");
            return null;
        }
    }


}
