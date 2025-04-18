/*
- Final Project
- Sultan Lodi, Jeremiah Registre~
- (Optional) Any other notes for the TA
*/

abstract class Person {
    public abstract void print();
}

class Student extends Person {
    private String fullName;
    private String id;
    private double gpa;
    private int creditHours;

    public Student() {
        this.fullName = "Unknown Student";
        this.id = "xx0000";
        this.gpa = 0.0;
        this.creditHours = 0;
    }

    public Student(String fullName, String id, double gpa, int creditHours) {
        setFullName(fullName);
        setId(id);
        setGpa(gpa);
        setCreditHours(creditHours);
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

    public double getGpa() {
        return gpa;
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
        System.out.println("Total payment (after discount): $" + calculateTuition());
        System.out.println("-----------------------------------------------------------------\n\n");
    }
}