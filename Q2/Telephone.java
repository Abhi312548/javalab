package sec_test;

import java.util.*;

class Telephone {
    private Map<String, String> callersList = new HashMap<>();
    private List<String> missedCalls = new ArrayList<>();

    public void recordMissedCall(String phoneNumber, String callerName) {
        callersList.put(phoneNumber, callerName);
        missedCalls.add(phoneNumber);
        System.out.println("Missed call recorded: " + phoneNumber);
    }

    public void displayMissedCalls() {
        if (missedCalls.isEmpty()) {
            System.out.println("No missed calls.");
        } else {
            System.out.println("Missed Calls:");
            for (String phoneNumber : missedCalls) {
                System.out.println(phoneNumber);
            }
        }
    }

    public void displayCallDetails(String phoneNumber) {
        if (callersList.containsKey(phoneNumber)) {
            String callerName = callersList.get(phoneNumber);
            System.out.println("Phone Number: " + phoneNumber + ", Caller Name: " +
                    (callerName != null ? callerName : "Private Caller"));
        } else {
            System.out.println("Missed call not found: " + phoneNumber);
        }
    }

    public void deleteMissedCall(String phoneNumber) {
        if (missedCalls.contains(phoneNumber)) {
            missedCalls.remove(phoneNumber);
            callersList.remove(phoneNumber);
            System.out.println("Missed call deleted: " + phoneNumber);
        } else {
            System.out.println("Missed call not found: " + phoneNumber);
        }
    }

    public void displayPhoneNumbersWithNameStartingWithA() {
        System.out.println("Phone Numbers with Names Starting with 'A':");
        for (Map.Entry<String, String> entry : callersList.entrySet()) {
            String phoneNumber = entry.getKey();
            String callerName = entry.getValue();
            if (callerName != null && callerName.toUpperCase().startsWith("A")) {
                System.out.println("Phone Number: " + phoneNumber + ", Caller Name: " + callerName);
            }
        }
    }
}

