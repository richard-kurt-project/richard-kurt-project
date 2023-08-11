package contactManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManager {
    // fields
    private static final Path FILE_PATH = Paths.get("contacts.txt");
    private static final String DELIMITER = ",";
    private static final Scanner scanner = new Scanner(System.in);

    /// main
    public static void main(String[] args) {
        List<Contact> contacts = loadContactsFromFile();
        int choice;
        do {
            choice = showMainMenu();
            switch (choice) {
                case 1:
                    viewContacts(contacts);
                    break;
                case 2:
                    addContact(contacts);
                    break;
                case 3:
                    searchContactByName(contacts);
                    break;
                case 4:
                    deleteContact(contacts);
                    break;
                case 5:
                    // Exit option, do nothing
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        writeContactsToFile(contacts);
    }

    //    private static List<Contact> loadContactsFromFile() {
//        List<Contact> contacts = new ArrayList<>();
//        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split(DELIMITER);
//                if (parts.length == 2) {
//                    String name = parts[0];
//                    String phoneNumber = parts[1];
//                    Contact contact = new Contact(name, phoneNumber);
//                    contacts.add(contact);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Contacts file not found. Creating a new one.");
//        }
//        return contacts;
//    }
    private static List<Contact> loadContactsFromFile() {
        List<Contact> contacts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(FILE_PATH);
            for (String line : lines) {
                String[] parts = line.split(DELIMITER);
                if (parts.length == 2) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    Contact contact = new Contact(name, phoneNumber);
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            System.out.println("Contacts file not found. Creating a new one.");
        }
        return contacts;
    }

    private static int showMainMenu() {
        System.out.println("Main Menu");
        System.out.println("1. View contacts");
        System.out.println("2. Add a new contact");
        System.out.println("3. Search a contact by name");
        System.out.println("4. Delete an existing contact");
        System.out.println("5. Exit");
        System.out.print("Enter an option (1, 2, 3, 4, or 5): ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        return userChoice;
    }

    private static void viewContacts(List<Contact> contacts) {
        System.out.println("Contacts:");
        System.out.println("Name\t\tPhone number");
        System.out.println("------------------------");
        for (Contact contact : contacts) {
            System.out.println(contact.getName() + "\t\t |" + contact.getPhoneNumber());
        }
    }

    private static void addContact(List<Contact> contacts) {
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("kurt")) {
            try {
                System.out.println("<a href=\"https://www.youtube.com/watch?v=dQw4w9WgXcQ\">Top Secret Government Files on Aliens </a>");
            } catch (Exception e) {
                // Handle the exception here if needed
                e.printStackTrace();
            }
        }

        // Check if a contact with the same name already exists
        for (Contact existingContact : contacts) {
            if (existingContact.getName().equalsIgnoreCase(name)) {
                System.out.println("Contact already exists.");
                return;
            }
        }

        System.out.print("Enter the phone number: ");
        String phoneNumberStr = scanner.nextLine();

        if (!InRangeInt(phoneNumberStr)) {
            System.out.println("Not a valid phone number.");
            return;
        }


        Contact contact = new Contact(name, phoneNumberStr);
        contacts.add(contact);
        System.out.println("Contact added successfully.");
    }


    private static void searchContactByName(List<Contact> contacts) {
//        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name to search: ");
        String searchName = scanner.nextLine();
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(searchName)) {
                System.out.println("Contact found:");
                System.out.println("Name: " + contact.getName());
                System.out.println("Phone number: " + contact.getPhoneNumber());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteContact(List<Contact> contacts) {
//        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the contact to delete: ");
        String deleteName = scanner.nextLine();
        boolean deleted = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(deleteName)) {
                contacts.remove(contact);
                System.out.println("Contact deleted successfully.");
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            System.out.println("Contact not found.");
        }
    }

    //    private static void writeContactsToFile(List<Contact> contacts) {
//        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
//            for (Contact contact : contacts) {
//                writer.println(contact.getName() + DELIMITER + contact.getPhoneNumber());
//            }
//        } catch (IOException e) {
//            System.out.println("Error writing contacts to file.");
//        }
//    }
    private static void writeContactsToFile(List<Contact> contacts) {
        try {
            List<String> lines = new ArrayList<>();
            for (Contact contact : contacts) {
                lines.add(contact.getName() + DELIMITER + contact.getPhoneNumber());
            }
            Files.write(FILE_PATH, lines);
        } catch (IOException e) {
            System.out.println("Error writing contacts to file.");
        }
    }

    public static boolean InRangeInt(String phoneNumberStr) {
        // Check if the string length is exactly 7 characters
        if (phoneNumberStr.length() != 10) {
            return false;
        }

        // Verify if all characters in the string are digits
        for (char c : phoneNumberStr.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true; // If all conditions are met
    }





}
