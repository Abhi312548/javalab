package sixth_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeInfoApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new EmployeeInfoApp().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Employee Information System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        createInputPanel(frame);

        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }

    private void createInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Employee Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Employee Age:");
        JTextField ageField = new JTextField();

        JLabel payscaleLabel = new JLabel("Employee Payscale:");
        String[] payscaleOptions = {"Select", "Payscale 1", "Payscale 2", "Payscale 3"};
        JComboBox<String> payscaleComboBox = new JComboBox<>(payscaleOptions);

        JLabel familyMembersLabel = new JLabel("Number of Family Members:");
        JTextField familyMembersField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        JRadioButton maleRadioButton = new JRadioButton("Male");
        JRadioButton femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButtonClicked(frame, nameField.getText(), ageField.getText(), payscaleComboBox.getSelectedItem().toString(),
                        familyMembersField.getText(), addressField.getText(),
                        maleRadioButton.isSelected() ? "Male" : "Female");
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(payscaleLabel);
        inputPanel.add(payscaleComboBox);
        inputPanel.add(familyMembersLabel);
        inputPanel.add(familyMembersField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(genderLabel);
        inputPanel.add(maleRadioButton);
        inputPanel.add(new JLabel());  // Empty label for spacing
        inputPanel.add(femaleRadioButton);
        inputPanel.add(new JLabel());  // Empty label for spacing
        inputPanel.add(submitButton);

        frame.add(inputPanel);
    }

    private void submitButtonClicked(JFrame frame, String name, String age, String payscale, String familyMembers, String address, String gender) {
        try {
            int ageValue = Integer.parseInt(age);

            if (ageValue > 22 && ageValue < 60) {
                String message = "Employee Information:\nName: " + name + "\nAge: " + age +
                        "\nPayscale: " + payscale + "\nFamily Members: " + familyMembers +
                        "\nAddress: " + address + "\nGender: " + gender;
                JOptionPane.showMessageDialog(frame, message, "Employee Data", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid age! Please enter age between 22 and 60.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
