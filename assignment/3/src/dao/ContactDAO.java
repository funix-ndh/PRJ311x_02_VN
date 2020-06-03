package dao;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.Contact;

public class ContactDAO {

    //load all Contacts from the file Contact in to a list
    public List<Contact> loadContact(String fileName) throws Exception {
        final List<Contact> contacts = new ArrayList<>();
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(fileName))) {
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    final String[] contactFields = line.split(":");
                    contacts.add(new Contact(
                            contactFields[0].trim(),
                            contactFields[1].trim(),
                            contactFields[2].trim(),
                            contactFields[3].trim(),
                            contactFields[4].trim(),
                            contactFields[5].trim()
                    ));
                }
            }
        }
        return contacts;
    }

    //save all Contacts from a given list to a text file
    public void saveToFile(List<Contact> list, String fileName) throws Exception {
        try (final Writer bw = new BufferedWriter(new FileWriter(fileName))) {
            for (final Contact contact : list) {
                bw.write(contact.toString());
            }
        }
    }

    //return the first position of a given contact in the list
    //otherwise return -1
    public int indexOf(List<Contact> list, Contact contact) {
        for (int index = 0; index < list.size(); index++) {
            final Contact c = list.get(index);
            if (c.getFirstName().equalsIgnoreCase(contact.getFirstName()) &&
                c.getLastName().equalsIgnoreCase(contact.getLastName())) {
                return index;
            }
        }
        return -1;
    }

    //save a Contact to a current list
    public void saveToList(List<Contact> list, Contact contact) {
        list.add(contact);
    }

    //update information of a contact at position i in the list
    public void updateContact(List<Contact> list, int index, Contact c) {
        final Contact contact = list.get(index);
        contact.setDob(c.getDob());
        contact.setEmail(c.getEmail());
        contact.setFirstName(c.getFirstName());
        contact.setLastName(c.getLastName());
        contact.setGroup(c.getGroup());
        contact.setPhone(c.getPhone());
    }

    //return a list of Contact who information matched given search word
    public List<Contact> search(List<Contact> list, String groupName, String searchKey) {
        final String groupSearchKey = "ALL".equalsIgnoreCase(groupName) ? "" : groupName;
        return list.stream()
                   .filter(contact -> contact.getGroup().toLowerCase().contains(groupSearchKey.toLowerCase())
                                      && contact.toString().toLowerCase().contains(searchKey.toLowerCase()))
                   .collect(Collectors.toList());
    }

    //return a list of Contact who is in a given group
    public List<Contact> contactByGroup(List<Contact> list, String groupName) {
        final String groupSearchKey = "ALL".equalsIgnoreCase(groupName) ? "" : groupName;
        return list.stream()
                   .filter(contact -> contact.getGroup().contains(groupSearchKey))
                   .collect(Collectors.toList());
    }
}
