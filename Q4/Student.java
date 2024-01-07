package fourth_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class Student {
    private static int idCounter = 1;
    private int studentId;
    private String name;
    private String usn;
    private int age;
    private String address;
    private double sgpa1;
    private double sgpa2;
    private double sgpa3;
    private double sgpa4;
    private String category;

    public Student(String name, String usn, int age, String address, double sgpa1, double sgpa2, double sgpa3, double sgpa4, String category) {
        this.studentId = idCounter++;
        this.name = name;
        this.usn = usn;
        this.age = age;
        this.address = address;
        this.sgpa1 = sgpa1;
        this.sgpa2 = sgpa2;
        this.sgpa3 = sgpa3;
        this.sgpa4 = sgpa4;
        this.category = category;
    }

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

    public String getAddress() {
        return address;
    }

    public double getSgpa1() {
        return sgpa1;
    }

    public double getSgpa2() {
        return sgpa2;
    }

    public double getSgpa3() {
        return sgpa3;
    }

    public double getSgpa4() {
        return sgpa4;
    }

    public String getCategory() {
        return category;
    }

    public double calculateCGPA() {
        return (sgpa1 + sgpa2 + sgpa3 + sgpa4) / 4.0;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + name + "\nUSN: " + usn + "\nAge: " + age +
                "\nAddress: " + address + "\nSGPA 1: " + sgpa1 + "\nSGPA 2: " + sgpa2 + "\nSGPA 3: " +
                sgpa3 + "\nSGPA 4: " + sgpa4 + "\nCategory: " + category + "\nCGPA: " + calculateCGPA();
    }
}


