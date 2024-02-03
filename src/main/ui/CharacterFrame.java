package ui;

import model.Character;
import model.Logos;
import model.Mythos;
import model.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents an internal frame that contains all character related information
public class CharacterFrame extends JInternalFrame {
    private Character character;
    private JPanel basicInfo;
    private JTabbedPane themePanel;
    private JPanel buttonPanel;
    private JTextField themeName;
    private JTextField themeIdentity;
    private JButton themeType;

    //EFFECTS: Makes a new character frame for the given character
    public CharacterFrame(Character character) {
        super();
        this.character = character;
        this.setTitle(character.getName());
        this.setLayout(new BorderLayout());
        basicInfo = new JPanel();
        setupInfo();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        setupButtons();
        createThemePanel();
        loadThemes();
        character.logSelect();

        add(basicInfo, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        basicInfo.setVisible(true);
        //setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: builds the panel that shows basic character info
    private void setupInfo() {
        basicInfo.setLayout(new BoxLayout(basicInfo, BoxLayout.LINE_AXIS));
        JLabel name = new JLabel("Name: " + character.getName());
        name.setFont(MainMenu.NAMEFONT);
        JLabel buildUp = new JLabel("Build Up: " + character.getBuildUp());
        buildUp.setFont(MainMenu.NAMEFONT);
        JLabel improvements = new JLabel("Evolutions: " + character.getNumEvolutions());
        improvements.setFont(MainMenu.NAMEFONT);
        basicInfo.add(name);
        basicInfo.add(Box.createHorizontalGlue());
        basicInfo.add(buildUp);
        basicInfo.add(Box.createHorizontalStrut(10));
        basicInfo.add(improvements);
        basicInfo.add(Box.createHorizontalStrut(10));
    }

    //MODIFIES: this
    //EFFECTS: Sets up buttons and text areas needed for character
    private void setupButtons() {
        JLabel nameLabel = new JLabel("Theme Name: ");
        JLabel identityLabel = new JLabel("Theme Identity: ");
        themeName = new JTextField(10);
        themeIdentity = new JTextField(20);
        themeType = new JButton();
        themeType.setText("Logos");
        themeType.addActionListener(new ActionSwitchType());
        JButton addThemeButton = new JButton("Add Theme");
        addThemeButton.addActionListener(new CreateThemeListener());

        buttonPanel.add(nameLabel);
        buttonPanel.add(themeName);
        buttonPanel.add(themeType);
        buttonPanel.add(identityLabel);
        buttonPanel.add(themeIdentity);
        buttonPanel.add(addThemeButton);

    }

    //MODIFIES: this
    //EFFECTS: creates an empty tabbed pane for themes
    private void createThemePanel() {
        themePanel = new JTabbedPane();
        add(themePanel, BorderLayout.CENTER);
        themePanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: loads themes into theme panel
    private void loadThemes() {
        for (Theme theme : character.getThemes()) {
            addTheme(theme);
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a tab for the given theme to the tabbed pane of themes
    private void addTheme(Theme theme) {
        ThemeUI themeTab = new ThemeUI(theme);
        themePanel.addTab(theme.getName(), themeTab);
        themeTab.setVisible(true);

    }

    //Represents an action to switch theme type button from Logos to Mythos
    private class ActionSwitchType implements ActionListener {

        //MODIFIES: themeType
        //EFFECTS: Switches theme type between Logos and Mythos
        @Override
        public void actionPerformed(ActionEvent e) {
            if (themeType.getText().equals("Logos")) {
                themeType.setText("Mythos");
            } else if (themeType.getText().equals("Mythos")) {
                themeType.setText("Logos");
            }
        }
    }

    //Represents an action to create a new theme
    private class CreateThemeListener implements ActionListener {

        //MODIFIES: CharacterFrame
        //EFFECTS: If a name and identity are provided, creates a new theme, otherwise does nothing
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = themeName.getText();
            String identity = themeIdentity.getText();
            if (!(name.equals("") && !identity.equals(""))) {
                if (themePanel == null) {
                    createThemePanel();
                }
                if (themeType.getText().equals("Logos")) {
                    Theme theme = new Logos(name, identity);
                    //System.out.println("Adding Logos Theme " + theme.getName());
                    addTheme(theme);
                    character.addTheme(theme);
                } else if (themeType.getText().equals("Mythos")) {
                    Theme theme = new Mythos(name, identity);
                    //System.out.println("Adding Mythos Theme " + theme.getName());
                    addTheme(theme);
                    character.addTheme(theme);
                }
            }
        }
    }
}
