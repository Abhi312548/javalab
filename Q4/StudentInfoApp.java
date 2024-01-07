package fourth_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class StudentInfoApp {
    private Map<Integer, Student> studentMap = new HashMap<>();

    private JFrame frame;
    private JTextField nameField, usnField, ageField, sgpa1Field, sgpa2Field, sgpa3Field, sgpa4Field, categoryField;
    private JTextArea displayArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentInfoApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Student Information App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createInputPanel();
        createDisplayPanel();

        frame.setLayout(new GridLayout(2, 1));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel usnLabel = new JLabel("USN:");
        usnField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();

        JLabel sgpa1Label = new JLabel("SGPA Semester 1:");
        sgpa1Field = new JTextField();

        JLabel sgpa2Label = new JLabel("SGPA Semester 2:");
        sgpa2Field = new JTextField();

        JLabel sgpa3Label = new JLabel("SGPA Semester 3:");
        sgpa3Field = new JTextField();

        JLabel sgpa4Label = new JLabel("SGPA Semester 4:");
        sgpa4Field = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField();

        JButton computeButton = new JButton("Compute");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeCGPA();
            }
        });

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentDetails();
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(usnLabel);
        inputPanel.add(usnField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(sgpa1Label);
        inputPanel.add(sgpa1Field);
        inputPanel.add(sgpa2Label);
        inputPanel.add(sgpa2Field);
        inputPanel.add(sgpa3Label);
        inputPanel.add(sgpa3Field);
        inputPanel.add(sgpa4Label);
        inputPanel.add(sgpa4Field);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(computeButton);
        inputPanel.add(doneButton);

        frame.add(inputPanel);
    }

    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStudentDetails();
            }
        });

        displayPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.add(displayButton, BorderLayout.SOUTH);

        frame.add(displayPanel);
    }

    private void computeCGPA() {
        try {
            int age = Integer.parseInt(ageField.getText());
            double sgpa1 = Double.parseDouble(sgpa1Field.getText());
            double sgpa2 = Double.parseDouble(sgpa2Field.getText());
            double sgpa3 = Double.parseDouble(sgpa3Field.getText());
            double sgpa4 = Double.parseDouble(sgpa4Field.getText());

            if (age <= 18 || age > 30 || sgpa1 < 0 || sgpa1 > 10 || sgpa2 < 0 || sgpa2 > 10 || sgpa3 < 0 || sgpa3 > 10 || sgpa4 < 0 || sgpa4 > 10) {
                JOptionPane.showMessageDialog(frame, "Invalid entries. Please check age and SGPA values.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Compute CGPA and display in a message dialog
                double cgpa = (sgpa1 + sgpa2 + sgpa3 + sgpa4) / 4.0;
                JOptionPane.showMessageDialog(frame, "CGPA: " + cgpa, "CGPA Calculation", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid entries. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudentDetails() {
        try {
            int age = Integer.parseInt(ageField.getText());

            if (age <= 18 || age > 30) {
                JOptionPane.showMessageDialog(frame, "Invalid age. Age must be between 19 and 30.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = nameField.getText();
                String usn = usnField.getText();
                String category = categoryField.getText();

                // Generate a unique student ID
                int studentId = studentMap.size() + 1;

                // Create a Student object with entered details
                Student student = new Student(studentId, name, usn, age, category);
                student.setSgpa1(Double.parseDouble(sgpa1Field.getText()));
                student.setSgpa2(Double.parseDouble(sgpa2Field.getText()));
                student.setSgpa3(Double.parseDouble(sgpa3Field.getText()));
                student.setSgpa4(Double.parseDouble(sgpa4Field.getText()));

                // Add student details to the HashMap
                studentMap.put(studentId, student);

                JOptionPane.showMessageDialog(frame, "Student details added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the input fields
                clearInputFields();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid age. Please enter a valid numeric age.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayStudentDetails() {
        displayArea.setText("");
        for (Student student : studentMap.values()) {
            displayArea.append("Student ID: " + student.getStudentId() + "\n");
            displayArea.append("Name: " + student.getName() + "\n");
            displayArea.append("USN: " + student.getUsn() + "\n");
            displayArea.append("Age: " + student.getAge() + "\n");
            displayArea.append("Category: " + student.getCategory() + "\n");
            displayArea.append("SGPA Semester 1: " + student.getSgpa1() + "\n");
            displayArea.append("SGPA Semester 2: " + student.getSgpa2() + "\n");
            displayArea.append("SGPA Semester 3: " + student.getSgpa3() + "\n");
            displayArea.append("SGPA Semester 4: " + student.getSgpa4() + "\n");
            displayArea.append("----------------------------\n");
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        usnField.setText("");
        ageField.setText("");
        sgpa1Field.setText("");
        sgpa2Field.setText("");
        sgpa3Field.setText("");
        sgpa4Field.setText("");
        categoryField.setText("");
    }
}
