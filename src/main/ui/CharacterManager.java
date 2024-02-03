package ui;

import model.Character;
import model.Party;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

//Represents a Panel that shows a party's characters in UI
public class CharacterManager extends JPanel implements ListSelectionListener {
    private PartyUI container;
    private Party party;
    private JList characterList;
    private DefaultListModel listModel;
    private JButton selectButton;
    private JButton deleteButton;
    private static final int FONTSIZE = 20;

    //EFFECTS: Creates a new party manager panel with no parties
    public CharacterManager(PartyUI container, Party party) {
        super(new BorderLayout());
        this.party = party;
        this.container = container;
        listSetup();
        JScrollPane listScrollPane = new JScrollPane(characterList);

        selectButton = new JButton("Select Character");
        selectButton.addActionListener(new SelectListener());
        selectButton.setEnabled(false);

        deleteButton = new JButton("Delete Character");
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);

        JPanel buttonPane = new JPanel();
        buttonPane.add(selectButton);
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(deleteButton);


        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);

    }

    //MODIFIES: this
    //EFFECTS: Helper method for constructor, sets up basic list elements
    private void listSetup() {
        listModel = new DefaultListModel();
        characterList = new JList(listModel);
        for (Character character : party.getMembers()) {
            listModel.addElement(character.getName());
        }
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        characterList.setSelectedIndex(-1);
        characterList.addListSelectionListener(this);
        characterList.setVisibleRowCount(15);
        characterList.setBackground(new Color(5, 5, 125));
        characterList.setForeground(new Color(223, 223, 223));
        characterList.setFont(new Font(Font.SERIF, Font.BOLD, FONTSIZE));
        characterList.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Adds a new character with the given name to characters
    //and lists that character in the UI
    public void addCharacter(String name) {
        if (name.equals("")) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        //System.out.println("Adding Character " + name);
        party.addMember(new Character(name));
        listModel.addElement(name);
        characterList.ensureIndexIsVisible(characterList.getLastVisibleIndex() + 1);
    }

    private class SelectListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int index = characterList.getSelectedIndex();
            //System.out.println("Selecting Character " + characters.get(index).getName());
            container.openCharacter(party.getMembers().get(index));
        }
    }

    private class DeleteListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int index = characterList.getSelectedIndex();
            //.out.println("Deleting Character " + characters.get(index).getName());
            party.removeMember(index);
            listModel.remove(index);
            characterList.setSelectedIndex(-1);
        }
    }



    //MODIFIES: this
    //EFFECTS: Changes availability of buttons depending on selection
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (characterList.getSelectedIndex() == -1) {
                selectButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                selectButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        }
    }
}
