
// Final Project
// Names: Sultan Lodi, Jeremiah Registre, Ali Amar Abid

import java.util.*;

abstract class Person {
    protected String fullName;
    protected String id;

    public Person(String fullName, String id) {
        this.fullName = fullName;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void print();
}
//Shared between faculty and staff
abstract class Employee extends Person {
    protected String department;

    public Employee(String fullName, String id, String department) {
        super(fullName, id);
        this.department = department;
    }
}

//Student class with tuition
class Student extends Person implements Comparable<Student> {
    private double gpa;
    public int creditHours;

    public Student(String fullName, String id, double gpa, int creditHours) {
        super(fullName, id);
        this.gpa = gpa;
        this.creditHours = creditHours;
    }

    public double getGpa() {
        return gpa;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.length() != 6) {
            throw new IllegalArgumentException("The ID must be six characters long. (ab1234)");
        }

        if (!Character.isLetter(id.charAt(0)) || !Character.isLetter(id.charAt(1))) {
            throw new IllegalArgumentException("First two characters must be a letter.");
        }

        for (int i = 2; i < 6; i++) {
            if (!Character.isDigit(id.charAt(i))) {
                throw new IllegalArgumentException("Last four characters must be digits.");
            }
        }
        this.id = id;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public double calculateTuition() {
        double tuition = (this.creditHours * 236.45) + 52;
        if (this.gpa >= 3.85) {
            tuition *= 0.75;
        }

        return tuition;
    }

    @Override
    public void print() {
        System.out.println("Tuition invoice for " + this.fullName);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(this.fullName + "\t" + this.id);
        System.out.println("Credit Hours: " + this.creditHours + " ($236.45/credit hour)");
        System.out.println("Fees: $52");
        System.out.printf("Total payment (after discount): $%.2f\n", calculateTuition());
        System.out.println("-----------------------------------------------------------------\n\n");
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa); // sort by GPA descending
    }
}

// Faculty class
class Faculty extends Employee {
    public String rank;

    public Faculty(String fullName, String id, String department, String rank) {
        super(fullName, id, department);
        this.rank = rank;
    }

    @Override
    public void print() {
        System.out.printf("%s\nID: %s\n%s, %s\n", fullName, id, rank, department);
    }

    public String getDepartment() {
        return department;
    }
}

// Staff class
class Staff extends Employee {
    private String status;

    public Staff(String fullName, String id, String department, String status) {
        super(fullName, id, department);
        setStatus(status);
    }

    public void setStatus(String status) {
        status = status.trim().toLowerCase();
        if (!status.equals("full-time") && !status.equals("part-time")) {
            throw new IllegalArgumentException("Status must be either 'Full-time' or 'Part-time'.");
        }
        this.status = capitalize(status);
    }

    public String getStatus() {
        return status;
    }

    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    @Override
    public void print() {
        System.out.println(fullName);
        System.out.println("ID: " + id);
        System.out.println(department + ", " + status);
    }
}


//Main program and the menu
public class FinalProject {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Person> personnel = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            // Menu
            System.out.println("\nChoose one of the options:");
            System.out.println("1- Enter the information of a faculty");
            System.out.println("2- Enter the information of a student");
            System.out.println("3- Print tuition invoice for a student");
            System.out.println("4- Print faculty information");
            System.out.println("5- Enter the information of a staff member");
            System.out.println("6- Print the information of a staff member");
            System.out.println("7- Delete a person");
            System.out.println("8- Exit Program");

            System.out.print("Enter your selection: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": enterFaculty(); break;
                case "2": enterStudent(); break;
                case "3": printStudentInvoice(); break;
                case "4": printFaculty(); break;
                case "5": enterStaff(); break;
                case "6": printStaff(); break;
                //case "7": deletePerson(); break;
                //case "8": exitProgram(); return;
                default: System.out.println("Invalid selection.");
            }
        }
    }

    // Checks if the ID matches
    static boolean validateId(String id) {
        return id.matches("(?i)^[a-z]{2}\\d{4}$");
    }

    // Checks for Dupes
    static boolean idExists(String id) {
        return personnel.stream().anyMatch(p -> p.getId().equalsIgnoreCase(id));
    }
    //Formatting names
    static String formatName(String rawName) {
        String[] parts = rawName.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                sb.append(Character.toUpperCase(part.charAt(0)));
                sb.append(part.substring(1));
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
    

    // Add new student
    static void enterStudent() {
        for (int attempt = 0; attempt < 3; attempt++) {
            System.out.print("Name: ");
            String name = formatName(sc.nextLine());
            System.out.print("ID: ");
            String id = sc.nextLine();
    
            if (!validateId(id)) {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
                continue;
            }
    
            if (idExists(id)) {
                System.out.println("ID already exists.");
                return;
            }
    
            try {
                System.out.print("GPA: ");
                double gpa = Double.parseDouble(sc.nextLine());
                System.out.print("Credit hours: ");
                int hours = Integer.parseInt(sc.nextLine());
                personnel.add(new Student(name, id, gpa, hours));
                System.out.println("Student added!");
                return;
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }
    

    // Add new faculty
    static void enterFaculty() {
        System.out.print("Name: ");
        String name = formatName(sc.nextLine());
        System.out.print("ID: ");
        String id = sc.nextLine();

        if (!validateId(id) || idExists(id)) {
            System.out.println("Invalid or duplicate ID.");
            return;
        }

        System.out.print("Department: ");
        String dept = sc.nextLine();
        System.out.print("Rank (Professor/Adjunct): ");
        String rank = sc.nextLine();
        personnel.add(new Faculty(name, id, dept, rank));
        System.out.println("Faculty added!");
    }

    
    //Prints invoice if Student exists
    static void printStudentInvoice() {
        System.out.print("Enter the student’s ID: ");
        String id = sc.nextLine();
        for (Person p : personnel) {
            if (p instanceof Student && p.getId().equalsIgnoreCase(id)) {
                p.print();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    //Searches for faculty member if found 
    static void printFaculty() {
        System.out.print("Enter the faculty ID: ");
        String id = sc.nextLine();
        for (Person p : personnel) {
            if (p instanceof Faculty && p.getId().equalsIgnoreCase(id)) {
                p.print();
                return;
            }
        }
        System.out.println("Sorry, no faculty with ID = " + id);
    }

    // Add new staff
static void enterStaff() {
    for (int attempt = 0; attempt < 3; attempt++) {
        try {
            System.out.print("Name: ");
            String name = formatName(sc.nextLine());

            System.out.print("ID: ");
            String id = sc.nextLine();

            if (!validateId(id)) {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
                continue;
            }

            if (idExists(id)) {
                System.out.println("ID already exists.");
                return;
            }

            System.out.print("Department (Mathematics, Engineering, English): ");
            String dept = sc.nextLine();

            System.out.print("Status (Full-time or Part-time): ");
            String status = sc.nextLine();

            personnel.add(new Staff(name, id, dept, status));
            System.out.println("Staff added!");
            return;
        } catch (Exception e) {
            System.out.println("Invalid input. Try again.");
        }
    }
}

// Print staff info
static void printStaff() {
    for (int attempt = 0; attempt < 3; attempt++) {
        System.out.print("Enter the staff ID: ");
        String id = sc.nextLine();

        for (Person p : personnel) {
            if (p instanceof Staff && p.getId().equalsIgnoreCase(id)) {
                System.out.println();
                p.print();
                return;
            }
        }

        System.out.println("Sorry, no staff with ID = " + id);
    }
}

}
    

