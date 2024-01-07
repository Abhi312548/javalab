package first_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class StudentManagement {
    public static void main(String[] args) {
        ArrayList<Student> studentList = new ArrayList<>();

        // Taking user input to add more students
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students you want to add: ");
        int numStudents = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numStudents; i++) {
            System.out.println("Enter details for student " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("USN: ");
            String USN = scanner.nextLine();
            System.out.print("Department: ");
            String dept = scanner.nextLine();
            System.out.print("Section: ");
            String section = scanner.nextLine();
            System.out.print("CGPA: ");
            double CGPA = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            studentList.add(new Student(name, USN, dept, section, CGPA));
        }

        // Searching for students with CGPA > 8.5
        System.out.println("\nStudents with CGPA > 8.5:");
        for (Student student : studentList) {
            if (student.CGPA > 8.5) {
                System.out.println("Name: " + student.name + ", USN: " + student.USN +
                        ", Department: " + student.dept + ", Section: " + student.section +
                        ", CGPA: " + student.CGPA);
            }
        }

        // Printing students belonging to 'ISE' department
        System.out.println("\nStudents belonging to 'ISE' department:");
        for (Student student : studentList) {
            if ("ISE".equals(student.dept)) {
                System.out.println("Name: " + student.name + ", USN: " + student.USN +
                        ", Department: " + student.dept + ", Section: " + student.section +
                        ", CGPA: " + student.CGPA);
            }
        }

        // Printing students belonging to 'C' section
        System.out.println("\nStudents belonging to 'C' section:");
        for (Student student : studentList) {
            if ("C".equals(student.section)) {
                System.out.println("Name: " + student.name + ", USN: " + student.USN +
                        ", Department: " + student.dept + ", Section: " + student.section +
                        ", CGPA: " + student.CGPA);
            }
        }

        // Using Iterator to iterate through the list
        System.out.println("\nUsing Iterator to iterate through the list:");
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            System.out.println("Name: " + student.name + ", USN: " + student.USN +
                    ", Department: " + student.dept + ", Section: " + student.section +
                    ", CGPA: " + student.CGPA);
        }
    }
}

