package ui;

import model.Character;
import model.Party;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

//Represents a Party in UI
public class PartyUI extends JPanel {
    private MainMenu container;
    private Party party;
    private JInternalFrame frame;
    private JInternalFrame characterNameFrame;
    private JTextField characterNameField;
    private JInternalFrame characterListFrame;
    private CharacterManager characterManager;

    //REQUIRES: party is not null
    public PartyUI(MainMenu container, Party party, JInternalFrame frame) {
        this.container = container;
        this.party = party;
        this.frame = frame;
        characterManager = new CharacterManager(this, party);
        characterNameFrame = new JInternalFrame();
        characterNameFrame.setLayout(new BorderLayout());
        characterListFrame = new JInternalFrame("Character List");
        characterListFrame.setLayout(new BorderLayout());
        characterListFrame.add(characterManager, BorderLayout.WEST);
        characterManager.setVisible(true);

        addCharacterConstructor();
        characterListFrame.setBackground(new Color(5, 5, 125));
        characterListFrame.setVisible(true);
        characterNameFrame.pack();
        characterNameFrame.setVisible(true);


        add(characterListFrame);
        add(characterNameFrame);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Adds needed fields to the main menu to be able to add parties
    private void addCharacterConstructor() {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        characterNameField = new JTextField(15);
        characterNameField.addActionListener(new AddCharacterAction());
        pane.add(characterNameField, BorderLayout.CENTER);
        characterNameFrame.add(pane, BorderLayout.CENTER);
    }

    //Represents an action to add a character
    private class AddCharacterAction extends AbstractAction {

        AddCharacterAction() {
            super("Add Character");
        }

        //MODIFIES: characterManager
        //EFFECTS: adds a character with the name field's name to the character manager
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = characterNameField.getText();
            characterManager.addCharacter(name);
        }
    }

    //EFFECTS: Opens the given character as a new UI panel
    public void openCharacter(Character character) {
        CharacterFrame characterFrame = new CharacterFrame(character);
        characterFrame.setClosable(true);
        characterFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        container.add(characterFrame);
        try {
            characterFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
            //Need to look at what this exception is
        }
        characterFrame.setVisible(true);
    }



}
