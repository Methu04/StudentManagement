//creating the module class
public class Module {
    //declaring the variable
    private int marks;

    //creating a constructor for the module class
    public Module(int marks){
        //this keyword refers to the current object
        this.marks = marks;
    }

    //this is the getter for the marks variable
    //this gives the access to the marks variable
    public int getMarks(){
        return marks;
    }

    //this is the setter for the marks variable
    //this method modify the marks variable
    public void setMarks(int marks){
        this.marks = marks;
    }
}
