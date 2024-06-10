import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

class ContactManager {
    private List<Contact> contacts;
    private static final String FILE_NAME = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void editContact(int index, String name, String phoneNumber, String emailAddress) {
        Contact contact = contacts.get(index);
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmailAddress(emailAddress);
        saveContacts();
    }

    private void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmailAddress());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing contacts found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
}

public class ContactManagementSystem {
    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------\n");
            System.out.println("Contact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            System.out.println("\n\n");
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println(
                            "------------------------------------------------------------------------------------------------------------\n");
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();
                    Contact contact = new Contact(name, phoneNumber, emailAddress);
                    contactManager.addContact(contact);
                    System.out.println("Contact added.");
                    break;
                case 2:
                    System.out.println(
                            "------------------------------------------------------------------------------------------------------------\n");
                    List<Contact> contacts = contactManager.getContacts();
                    for (int i = 0; i < contacts.size(); i++) {
                        System.out.println((i + 1) + ". " + contacts.get(i));
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.println(
                            "------------------------------------------------------------------------------------------------------------\n");

                    System.out.print("Enter the contact number to edit: ");
                    int index = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline
                    if (index >= 0 && index < contactManager.getContacts().size()) {
                        System.out.print("Enter new name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter new phone number: ");
                        phoneNumber = scanner.nextLine();
                        System.out.print("Enter new email address: ");
                        emailAddress = scanner.nextLine();
                        contactManager.editContact(index, name, phoneNumber, emailAddress);
                        System.out.println("Contact updated.");
                    } else {
                        System.out.println("Invalid contact number.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
