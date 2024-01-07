package sec_test;

import java.util.Scanner;

public class TelephoneSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Telephone telephone = new Telephone();

        // Simulate missed calls based on user input
        System.out.print("Enter the number of missed calls to simulate: ");
        int numMissedCalls = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numMissedCalls; i++) {
            System.out.print("Enter phone number for missed call " + (i + 1) + ": ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter caller name for missed call " + (i + 1) + " (or press Enter for private caller): ");
            String callerName = scanner.nextLine();

            telephone.recordMissedCall(phoneNumber, callerName.isEmpty() ? null : callerName);
        }

        // Display missed calls
        telephone.displayMissedCalls();

        // Display call details based on user input
        System.out.print("Enter the phone number to display details: ");
        String displayPhoneNumber = scanner.nextLine();
        telephone.displayCallDetails(displayPhoneNumber);

        // Delete a missed call based on user input
        System.out.print("Enter the phone number to delete missed call: ");
        String deletePhoneNumber = scanner.nextLine();
        telephone.deleteMissedCall(deletePhoneNumber);

        // Display missed calls after deletion
        telephone.displayMissedCalls();

        // Display phone numbers with names starting with 'A'
        telephone.displayPhoneNumbersWithNameStartingWithA();
    }
}
