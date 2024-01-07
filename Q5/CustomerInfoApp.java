package fifth_test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CustomerInfoApp {
    private Map<String, Customer> customerMap = new HashMap<>();
    private Map<Integer, Item> itemMap = new HashMap<>();

    private JTextField mobileNumberField;
    private JTextArea displayArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CustomerInfoApp().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Customer Information System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        createInputPanel(frame);
        createDisplayPanel(frame);

        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }

    private void createInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel mobileNumberLabel = new JLabel("Mobile Number:");
        mobileNumberField = new JTextField();

        JButton checkCustomerButton = new JButton("Check Customer");
        checkCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCustomerButtonClicked();
            }
        });

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemButtonClicked(frame);
            }
        });

        inputPanel.add(mobileNumberLabel);
        inputPanel.add(mobileNumberField);
        inputPanel.add(checkCustomerButton);
        inputPanel.add(addItemButton);

        frame.add(inputPanel);
    }

    private void createDisplayPanel(JFrame frame) {
        JPanel displayPanel = new JPanel();
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayPanel.add(scrollPane);
        frame.add(displayPanel);
    }

    private void checkCustomerButtonClicked() {
        String mobileNumber = mobileNumberField.getText();
        if (customerMap.containsKey(mobileNumber)) {
            Customer customer = customerMap.get(mobileNumber);
            JOptionPane.showMessageDialog(null, "Customer found!\nCustomer ID: " + customer.getCustomerId() + "\nCustomer Name: " + customer.getName(), "Customer Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            addCustomerDetails(mobileNumber);
        }
    }

    private void addCustomerDetails(String mobileNumber) {
        String name = JOptionPane.showInputDialog(null, "Enter customer name:");
        if (name != null) {
            Customer newCustomer = new Customer(name, mobileNumber);
            customerMap.put(mobileNumber, newCustomer);
            JOptionPane.showMessageDialog(null, "Customer added successfully!\nCustomer ID: " + newCustomer.getCustomerId() + "\nCustomer Name: " + newCustomer.getName(), "Customer Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addItemButtonClicked(JFrame frame) {
        try {
            int itemId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter item ID:"));
            String itemName = JOptionPane.showInputDialog(null, "Enter item name:");
            double itemPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter item price:"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter quantity purchased:"));

            Item newItem = new Item(itemName, itemPrice, quantity);
            itemMap.put(itemId, newItem);

            double totalCost = newItem.getTotalCost();
            double discount = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter discount (if any):"));
            totalCost -= discount;

            JOptionPane.showMessageDialog(frame, "Item Information:\nItem Name: " + newItem.getItemName() + "\nTotal Cost: " + totalCost, "Item Information", JOptionPane.INFORMATION_MESSAGE);

            // Display the updated item details in the JTextArea
            displayArea.append("Item ID: " + newItem.getItemId() + "\nItem Name: " + newItem.getItemName() +
                    "\nItem Price: " + newItem.getItemPrice() + "\nQuantity: " + newItem.getQuantity() +
                    "\nTotal Cost: " + newItem.getTotalCost() + "\nDiscount: " + discount + "\n----------------\n");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values for item ID, price, and quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
