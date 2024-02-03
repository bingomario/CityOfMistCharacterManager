package ui;

import model.Party;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

//Represents a UI Panel to store, select, and delete Parties
public class PartyManager extends JPanel implements ListSelectionListener {
    private MainMenu container;
    private List<Party> parties;
    private JList partyList;
    private DefaultListModel listModel;
    private JButton selectButton;
    private JButton deleteButton;

    //EFFECTS: Creates a new party manager panel with no parties
    public PartyManager(MainMenu container) {
        super(new BorderLayout());
        this.container = container;
        listSetup();
        JScrollPane listScrollPane = new JScrollPane(partyList);

        selectButton = new JButton("Select Party");
        selectButton.addActionListener(new SelectListener());
        selectButton.setEnabled(false);

        deleteButton = new JButton("Delete Party");
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);

        JPanel buttonPane = new JPanel();
        buttonPane.add(selectButton);
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(deleteButton);


        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);

    }

    //MODIFIES: this
    //EFFECTS: Helper method for constructor, sets up basic list elements
    private void listSetup() {
        parties = new ArrayList<>();
        listModel = new DefaultListModel();
        partyList = new JList(listModel);
        partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partyList.setSelectedIndex(-1);
        partyList.addListSelectionListener(this);
        partyList.setVisibleRowCount(0);
        partyList.setBackground(new Color(5, 5, 125));
        partyList.setForeground(new Color(223, 223, 223));
        partyList.setFont(MainMenu.NAMEFONT);
    }

    //MODIFIES: this
    //EFFECTS: Adds a new party with the given name to parties
    //and lists that party in the UI
    public void addParty(String name) {
        if (name.equals("")) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        //System.out.println("Adding Party " + name);
        Party party = new Party(name);
        parties.add(party);
        listModel.addElement(name);
        partyList.ensureIndexIsVisible(partyList.getLastVisibleIndex() + 1);
    }

    //EFFECTS: saves parties to file
    public void save() {
        JsonWriter writer = new JsonWriter();
        try {
            writer.open();
            writer.writeToFile(parties);
            writer.close();
            //System.out.println("Save complete!");
        } catch (FileNotFoundException e) {
            System.out.println("File Error, failed to save parties.");
        }
    }

    //EFFECTS: loads parties from file
    public void load() {
        JsonReader reader = new JsonReader();
        try {
            parties = reader.readFile();
            //System.out.println("File load complete!");
        } catch (IOException e) {
            System.out.println("File Read Error, failed to load file.");
        }
        listModel.removeAllElements();
        for (Party party : parties) {
            listModel.addElement(party.getName());
        }
    }

    //Represents a listener for selecting a party
    private class SelectListener implements ActionListener {

        //EFFECTS: Opens the currently selected party
        public void actionPerformed(ActionEvent e) {
            int index = partyList.getSelectedIndex();
            //System.out.println("Selecting Party " + parties.get(index).getName());
            container.openParty(parties.get(index));
        }
    }

    //Represents a listener for deleting an empty party
    private class DeleteListener implements ActionListener {

        //MODIFIES: PartyManager
        //EFFECTS: If selected party is empty, deletes it, otherwise does nothing
        public void actionPerformed(ActionEvent e) {
            int index = partyList.getSelectedIndex();
            if (parties.get(index).getNumMembers() == 0) {
                //System.out.println("Deleting Party " + parties.get(index).getName());
                parties.remove(index);
                listModel.remove(index);
            }
            partyList.setSelectedIndex(-1);
        }
    }





    //MODIFIES: this
    //EFFECTS: Changes availability of buttons depending on selection
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (partyList.getSelectedIndex() == -1) {
                selectButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                selectButton.setEnabled(true);
                if (parties.get(partyList.getSelectedIndex()).getNumMembers() == 0) {
                    deleteButton.setEnabled(true);
                } else {
                    deleteButton.setEnabled(false);
                }
            }
        }
    }
}
