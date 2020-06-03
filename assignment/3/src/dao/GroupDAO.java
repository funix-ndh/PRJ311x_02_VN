package dao;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.Group;

public class GroupDAO {

    //load all groups from the file group in to a list
    public List<Group> loadGroup(String fileName) throws Exception {
        final List<Group> groups = new ArrayList<>();
        try (final LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(fileName))) {
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    groups.add(new Group(line));
                }
            }
        }
        return groups;
    }

    //save all groups from a given list to a text file
    public void saveGroupToFile(List<Group> list, String fileName) throws Exception {
        try (final Writer bw = new BufferedWriter(new FileWriter(fileName))) {
            for (final Group contact : list) {
                bw.write(contact.toString());
            }
        }
    }

    //return the first position of a given contact group in the list
    //otherwise return -1
    public int indexOf(List<Group> list, Group group) {
        for (int index = 0; index < list.size(); index++) {
            final Group g = list.get(index);
            if (g.getName().equalsIgnoreCase(group.getName())) {
                return index;
            }
        }
        return -1;
    }

    //save a group to a current list
    public void saveGroupToList(List<Group> list, Group group) {
        list.add(group);
    }

    //return a list of Contact who information matched given searchKey word
    public List<Group> search(List<Group> list, String searchKey) {
        return list.stream()
                     .filter(group -> group.toString().toLowerCase().contains(searchKey.toLowerCase()))
                     .collect(Collectors.toList());
    }
}
