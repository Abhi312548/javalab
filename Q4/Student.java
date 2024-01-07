package fourth_test;

public class Student {
    private int studentId;
    private String name;
    private String usn;
    private int age;
    private String category;
    private double sgpa1;
    private double sgpa2;
    private double sgpa3;
    private double sgpa4;

    public Student(int studentId, String name, String usn, int age, String category) {
        this.studentId = studentId;
        this.name = name;
        this.usn = usn;
        this.age = age;
        this.category = category;
    }

    // Getters and setters for other fields

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getUsn() {
        return usn;
    }

    public int getAge() {
        return age;
    }

    public String getCategory() {
        return category;
    }

    public double getSgpa1() {
        return sgpa1;
    }

    public void setSgpa1(double sgpa1) {
        this.sgpa1 = sgpa1;
    }

    public double getSgpa2() {
        return sgpa2;
    }

    public void setSgpa2(double sgpa2) {
        this.sgpa2 = sgpa2;
    }

    public double getSgpa3() {
        return sgpa3;
    }

    public void setSgpa3(double sgpa3) {
        this.sgpa3 = sgpa3;
    }

    public double getSgpa4() {
        return sgpa4;
    }

    public void setSgpa4(double sgpa4) {
        this.sgpa4 = sgpa4;
    }
}
