//needed modules are imported
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

//main class is created
class StudentManagementMenu {
    //Initializing the main array containing all the student details and maximum is 100 slots
    static final Student[] std = new Student[100];
    static int stdCount = 0;



    //main method is created
    public static void main(String[] args) {
        //Initializing variables
        int choice = 0;
        Scanner input;
        //loading details to the system in the beginning
        loadDetails();
        //Starting of the main menu, if the user input an invalid choice the program will display the menu again and ask the user to input the choice
        //menu will display until the user input 9 - exit
        do {
            menu();
            input = new Scanner(System.in);
            try{
                choice = input.nextInt();
                //if the user input a character ,the error message will display
                //each case calls the corresponding method
                switch (choice) {
                    case 1:
                        availableSeats();
                        break;
                    case 2:
                        regStudent(input);
                        break;
                    case 3:
                        delStudent(input);
                        break;
                    case 4:
                        searchStudent(input);
                        break;
                    case 5:
                        storeDetails();
                        break;
                    case 6:
                        loadDetails();
                        break;
                    case 7:
                        viewStudents();
                        break;
                    case 8:
                        submenu(input);
                        break;
                    case 9:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice...");
                }
            }catch(InputMismatchException e){
                System.out.println("Enter a choice between 1 and 9");
            }
        } while (choice != 9);
    }

    //menu display statements
    private static void menu() {
        System.out.println("\n"+"=======Student Management System=======");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student(with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student(with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students(with name)");
        System.out.println("8. Sub menu for additional options");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    //method to check the no.of available seats after loading the details
    private static void availableSeats() {
        System.out.println("Available seats: "+(100-stdCount));
    }

    //method to register the students
    private static void regStudent(Scanner input) {
        //if the no.of available slots are exceeded, the following message will display
        if(stdCount >= std.length){
            System.out.println("No available slots");
        }else {
            String stdId;
            char firstchar;
            boolean exist;
            //The user should input the id in the format w1234567
            do {
                System.out.print("Enter student ID: ");
                stdId = input.next();
                //first character of the entered id
                firstchar = stdId.charAt(0);
                exist = false;
                //checking whether the entered id exist already as there should not be similar ids
                for (int i = 0; i < stdCount; i++) {
                    if(std[i].getStdId().equals(stdId)){
                        exist = true;
                        break;
                    }
                }
                //if the id already exist, the following error message will display
                if (exist) {
                    System.out.println("Student ID already exist !!!");
                } else if(firstchar == 'w' && stdId.length() == 8){ //checking whether the user input contains 8 characters and the first character is "w"
                    std[stdCount++] = new Student(stdId);
                    System.out.println("Registered successfully !!!");
                }else {
                    System.out.println("Student ID should be in the format w1234567");
                } // if not the program will ask the user to enter the choice again
            } while (firstchar != 'w' && stdId.length() != 8 && exist);
        }
    }

    //method to delete a student id
    private static void delStudent(Scanner input) {
        //asking the user to input an id
        System.out.print("Enter the ID of the student you want to delete: ");
        String delId = input.next().trim(); //using .trim() whitespaces can be removed
        for (int i = 0; i < stdCount; i++) {
            //finding the entered id in the main array
            if (std[i].getStdId().equals(delId)){
                //if the id was found, reducing the student count and
                std[i] = std[--stdCount];
                //emptying the element of the corresponding index
                std[stdCount] = null;
                System.out.println("The student with the ID "+delId+" is deleted successfully !!!");
                return;
            }
        }
        //if the entered id is not found, the error message will display
        System.out.println("Invalid Id !!!");
    }
    //method to search for a student using the entered student id
    private static void searchStudent(Scanner input) {
        //asking the user to enter the id
        System.out.print("Enter the student ID you want to search: ");
        String searchId = input.next().trim();
        for (int i = 0; i < stdCount; i++) {
            //checking whether the entered id exist in the main array
            if(std[i].getStdId().equals(searchId)){
                //if exists, displaying the following details
                System.out.println("Searched ID: "+ searchId);
                System.out.println("Name: "+std[i].getStdName());
                System.out.println("Module 1: "+std[i].getModules()[0].getMarks());
                System.out.println("Module 2: "+std[i].getModules()[1].getMarks());
                System.out.println("Module 3: "+std[i].getModules()[2].getMarks());
                System.out.println("Average: "+std[i].getAvgMarks());
                System.out.println("Grade: "+std[i].getGrade());
                return;
            }
        }
        //if the id doesn't exist, error message will display
        System.out.println("Searched student ID not found !!!");
    }

    //method to store details in a text file
    private static void storeDetails() {
        //using the filewriter module to create and write the details to the text file
        // detailSave is a variable name
        try(FileWriter detailSave = new FileWriter("StudentDetails.txt");){
            for (int i = 0; i < stdCount; i++) {
                //writing the id,name and module marks simultaneously to the file separating using the "|" symbol
                detailSave.write(std[i].getStdId()+","+std[i].getStdName());
                //using the enhanced for loop to access the elements of module array
                for(Module module : std[i].getModules()){
                    detailSave.write(","+module.getMarks());
                }
                detailSave.write("\n");
            }
            detailSave.close();
            System.out.println("Student details stored successfully !!!");
            //if an error occurs while creating the file, catch block will display the error message
        }catch(IOException e){
            System.out.println("An error occurred !!!");
        }
    }

    //method to load details from the text file to the system
    private static void loadDetails() {

        //using the filereader module to read the document and load the data
        try (FileReader f = new FileReader("StudentDetails.txt")) {
            //detailReader is created to read the file line by line
            Scanner detailReader = new Scanner(f);
            stdCount = 0;
            //while loop will run until there are lines to read
            while (detailReader.hasNextLine()) {
                //read lines of the file are stored in the line variable
                String line = detailReader.nextLine();
                //splits the line string into substrings separated by "|" and stores in slots array
                String[] slots = line.split(",");
                //checking whether the slots array contain 5 elements as there should be id,name,module1,module2 and module3
                if (slots.length >= 5) {
                    //getting the student id and name using the index from the slot array
                    String stdId = slots[0];
                    if(similarId(stdId)){
                        System.out.println("Similar ID found");
                        break;
                    }
                    String stdName = slots[1];
                    //creating the student object using the student id and setting the student name in the student object
                    Student student = new Student(stdId);
                    student.setStdName(stdName);
                    //looping through the rest of the elements in the slots array
                    for (int i = 0; i < 3; i++) {
                        //converting them into int and sets the mark in the student object
                        student.getModules()[i].setMarks(Integer.parseInt(slots[i + 2].trim()));
                    }
                    //adding the student object to the std array
                    std[stdCount++] = student;
                }
            }
            System.out.println("Details loaded successfully !!!");
            //if any error occurs while reading the file, the following error message will display
        } catch (IOException e) {
            System.out.println("Error occurred while loading details !!!");
        }
    }

    private static boolean similarId(String stdId) {
        for (int i = 0; i < stdCount; i++) {
            if(std[i].getStdId().equals(stdId)){
                return true;
            }
        }
        return false;
    }

    //method to view the student list based on names
    private static void viewStudents() {
        //creating the array sorted array with the size of student count
        Student[] sorted = new Student[stdCount];
        //copying student objects from the std array to the sorted array(arraycopy)
        for (int i = 0; i <stdCount ; i++) {
            sorted[i] = std[i];
        }

        //**using bubble sort to sort the names alphabetically**
        boolean exchanged = true;
        while(exchanged){
            exchanged = false;
            for (int i = 0; i < stdCount - 1; i++) {
                //if the name of the first student is greater than the second student, the names should be swapped
                if(sorted[i].getStdName().compareTo(sorted[i+1].getStdName()) > 0){
                    //the loop will run until the swapping is not necessary
                    Student temp = sorted[i];
                    sorted[i] = sorted[i+1];
                    sorted[i+1] = temp;
                    exchanged = true;
                }
            }
        }
        System.out.println("Student list: ");
        //student names are printed by iterating over the sorted array using the following enhanced for loop
        for(Student student : sorted){
            System.out.println(student.getStdName());
        }
    }

    //task 2 sub menu class is created
    //this is the method for the sub menu
    private static void submenu(Scanner input) {
        String choice;
        do {
            //displaying the sub menu and asking for choice
            //the choice should be either a,b,c or d
            System.out.println("a. Add student name");
            System.out.println("b. Add module marks 1,2 and 3");
            System.out.println("c. Generate a summary (no.of reg & total of students who scored more than 40)");
            System.out.println("d. Generate a report with all the details of students");
            System.out.print("Enter your choice: ");
            choice = input.next();
            switch (choice) {
                case "a":
                    addstdName(input);
                    break;
                case "b":
                    addmodulemarks(input);
                    break;
                case "c":
                    generateSummary();
                    break;
                case "d":
                    generateReport();
                    break;
                default:
                    System.out.println("Invalid choice");
            } //if not the program will ask for the user input again and again
        } while (!(choice.equals("a")) && !(choice.equals("b")) && !(choice.equals("c")) && !(choice.equals("d")));

    }

    //method to add the student name to the corresponding id
    private static void addstdName(Scanner input) {
        //asking the user to input the student id
        System.out.print("Enter the student ID: ");
        String nameId = input.next();
        //iterating over the array of students
        for (int i = 0; i < stdCount; i++) {
            //checking whether the entered student id matches any of the student ids at the current index
            if(std[i].getStdId().equals(nameId)){
                System.out.print("Enter the student name: ");
                String idName = input.next();
                //sets the name of the student with the corresponding student id
                std[i].setStdName(idName);
                System.out.println("Name saved successfully !!!");
                return;
            }
        }
        //if the id is not found, the error message will display
        System.out.println("Invalid student ID !!!");
    }

    //method to add the module marks
    private static void addmodulemarks(Scanner input) {
        //asking the user to input the student id
        System.out.print("Enter the student ID: ");
        String stdId = input.next();
        for (int i = 0; i < stdCount; i++) {
            if(std[i].getStdId().equals(stdId)){
                //iterating over 3 modules
                for (int j = 0; j < 3; j++) {
                    //asking the user to input the marks of each module
                    //the module number will increase by j+1
                    System.out.print("Enter the marks for module "+(j+1)+" : ");
                    int mark = input.nextInt();
                    //sets the mark of the module at the index j for the student at i to the entered mark
                    std[i].getModules()[j].setMarks(mark);
                }
                System.out.println("Module marks added successfully !!!");
                return;
            }
        }
        //if the id is not found, the error message will display
        System.out.println("Invalid student ID !!!");
    }

    //method to generate the summary
    private static void generateSummary() {
        //displaying the total number of registrations
        System.out.println("Total student registrations: "+ stdCount);

        //initializing the pass count to 0
        int m1pass = 0;
        int m2pass = 0;
        int m3pass = 0;

        //iterating over the array of students
        for (int i = 0; i < stdCount; i++) {
            //if the mark of the module at index 0 of the student at i is greater than 40
            // pass count of the module 1 increases
            if(std[i].getModules()[0].getMarks() >= 40){
                m1pass++;
            }
            //if the mark of the module at index 1 of the student at i is greater than 40
            // pass count of the module 2 increases
            if(std[i].getModules()[1].getMarks() >= 40){
                m2pass++;
            }
            //if the mark of the module at index 2 of the student at i is greater than 40
            // pass count of the module 3 increases
            if(std[i].getModules()[2].getMarks() >= 40){
                m3pass++;
            }
        }

        //displaying statements of the passing count of each module
        System.out.println("No.of students who has scored more than 40 for module 1: "+m1pass);
        System.out.println("No.of students who has scored more than 40 for module 2: "+m2pass);
        System.out.println("No.of students who has scored more than 40 for module 3: "+m3pass);
    }

    //method to generate the report
    private static void generateReport() {
        //creating the sortedAvg array with the size of student count
        Student[] sortedAvg = new Student[stdCount];

        //copying the student objects from the std array to the sortedAvg array
        for (int i = 0; i < stdCount; i++) {
            sortedAvg[i] = std[i];
        }

        //calling the bubble sort method to sort the details of the report by average
        bubbleSortAvg(sortedAvg);

        //iterating over the sortedAvg array using the following enhanced for loop
        // and displaying the details, id,name,module 1,2,3 marks,total,average and grade
        System.out.println("Complete report of students: ");
        for(Student student: sortedAvg){
            System.out.println("Student ID: "+student.getStdId()+" | Name: "+student.getStdName()+
                    " | Module 1: "+student.getModules()[0].getMarks()+
                    " | Module 2: "+student.getModules()[1].getMarks()+
                    " | Module 3: "+student.getModules()[2].getMarks()+
                    " | Total: "+(student.getModules()[0].getMarks()+
                    student.getModules()[1].getMarks()+
                    student.getModules()[2].getMarks())+
                    " | Average: "+student.getAvgMarks()+
                    " | Grade: "+student.getGrade()
            );
        }
    }

    //method to bubble sort the average
    private static void bubbleSortAvg(Student[] avglist) {
        boolean exchanged;
        do{
            exchanged = false;
            //looping through the elements of the avglist
            for (int i = 0; i < avglist.length -1; i++) {
                //checking whether the average mark of student i is less than average mark of student i+1
                //if true, then the swapping occurs
                if(avglist[i].getAvgMarks()<avglist[i+1].getAvgMarks()){
                    Student temp = avglist[i];
                    avglist[i] = avglist[i+1];
                    avglist[i+1] = temp;
                    //after the swap, exchanged is set to true
                    exchanged = true;
                }
            }
            //loop will run until the array is sorted and once the exchanged is false, the loop will terminate
        }while(exchanged);
    }

}

