//creating the student class
public class Student {
    //declaring private final instance for the variable stdId, stdName and modules of type Module[]
    private final String stdId;
    private String stdName;
    private final Module[] modules;

    //constructor of the student class
    public Student(String stdId) {
        //assigning the value to the parameter stdId
        this.stdId = stdId;
        //initializing the modules array to size 3
        //modules array will hold three Module objects
        this.modules = new Module[3];
        for (int i = 0; i < 3; i++) {
            //initializing each element of the module array to zero
            this.modules[i] = new Module(0);
        }
    }

    //this is the getter for the student id;stdId variable
    // which gives access to the stdId variable
    public String getStdId(){
        return stdId;
    }

    //this is the getter for the student name;stdName variable
    // which gives access to the stdName variable
    public String getStdName(){
        return stdName;
    }

    //this is the setter of the stdName variable
    //this modifies the stdName variable
    public void setStdName(String stdName){
        this.stdName = stdName;
    }

    //this is the getter of the modules array
    public Module[] getModules(){
        return modules;
    }

    //method to calculate the average
    public double getAvgMarks(){
        int total = 0;
        //looping through the module array
        for(Module module : modules){
            //adding the marks of the current module to the total
            total += module.getMarks();
        }
        //calculating the average marks
        return total / (double) 3;
    }

    //method to calculate the grade
    public String getGrade(){
        //calling the getAvgMarks to get the calculated average inside this method
        double avg = getAvgMarks();
        //checking whether the average is greater than 0 and less than 100
        if(avg <= 100 && avg >= 0){
            //deciding the grade according to the conditions
            if(avg >= 80){
                return "Distinction";
            }else if(avg >= 70){
                return "Merit";
            }else if(avg >= 40){
                return "Pass";
            }else{
                return "Fail";
            }
        }
        //if the average is either less than 0 or greater than 100,
        //the program will return an empty string
        return "";
    }
}

