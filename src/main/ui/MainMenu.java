package ui;

import model.EventLog;
import model.Event;
import model.Party;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.Iterator;

import javax.swing.*;
//Party Manager adds a new party to model and to ui

//Further Improvements: Fix new theme bug, Implement attention/loss in UI
//      What I think is going on is the themes before haven't properly loaded,
//      so any clicked theme loads the furthest unloaded rather than its own index

//Represents the main GUI level
public class MainMenu extends JFrame implements WindowListener {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private PartyManager partyManager;
    private JDesktopPane window;
    private JInternalFrame partyNameFrame;
    private JTextField partyNameField;
    private JInternalFrame partyListFrame;
    private JInternalFrame persistenceFrame;
    private boolean onSplash;
    private JPanel imagePanel;
    public static final Font NAMEFONT = new Font(Font.SERIF, Font.BOLD, 20);


    //EFFECTS: Creates ui window and splash screen
    public MainMenu() {
        addWindowListener(this);
        window = new JDesktopPane();
        window.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        setContentPane(window);
        setTitle("CPSC 210: City of Mist Manager");
        setSize(WIDTH, HEIGHT);
        imageSetup();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Creates the needed panels and frames for the main menu
    private void buildMenu() {
        partyManager = new PartyManager(this);

        partyNameFrame = new JInternalFrame("Add New Party");
        partyNameFrame.setLayout(new BorderLayout());
        partyNameFrame.setLocation(WIDTH - WIDTH / 3, HEIGHT / 4);
        partyListFrame = new JInternalFrame("Party List");
        partyListFrame.setLayout(new BorderLayout());
        partyListFrame.add(partyManager);

        persistenceFrame = new JInternalFrame("Save and Load");
        persistenceFrame.setLocation(WIDTH - WIDTH / 3, HEIGHT - HEIGHT / 4);

        addPartyConstructor();
        addSaveLoadPanel();

        partyListFrame.setSize(WIDTH / 2, HEIGHT - 37);
        partyListFrame.setBackground(new Color(5, 5, 125));
        partyListFrame.setVisible(true);
        partyNameFrame.pack();
        partyNameFrame.setVisible(true);
        persistenceFrame.pack();
        persistenceFrame.setVisible(true);
        window.add(partyListFrame);
        window.add(partyNameFrame);
        window.add(persistenceFrame);
    }

    //MODIFIES: this
    //EFFECTS: Adds needed fields to the main menu to be able to add parties
    private void addPartyConstructor() {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        partyNameField = new JTextField(15);
        partyNameField.addActionListener(new AddPartyAction());
        pane.add(partyNameField, BorderLayout.CENTER);
        partyNameFrame.add(pane, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: Adds needed fields to the main menu to save and load data
    private void addSaveLoadPanel() {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        JButton saveButton = new JButton("Save Parties");
        saveButton.addActionListener(new SaveAction());
        JButton loadButton = new JButton("Load Parties");
        loadButton.addActionListener(new LoadAction());
        pane.add(saveButton, BorderLayout.WEST);
        pane.add(loadButton, BorderLayout.EAST);
        persistenceFrame.add(pane);
    }

    //MODIFIES: this
    //EFFECTS: Sets up splash screen image and listener to close splash screen
    private void imageSetup() {
        onSplash = true;
        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon("images/Title.jpg");
        JLabel imageLabel = new JLabel(image);
        imageLabel.addMouseListener(new SplashListener());
        imagePanel.add(imageLabel);
        window.add(imagePanel);
        imagePanel.setVisible(true);
        imagePanel.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Event Log:");
        Iterator<Event> events = EventLog.getInstance().iterator();
        while (events.hasNext()) {
            System.out.println(events.next());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("Event Log:");
        Iterator<Event> events = EventLog.getInstance().iterator();
        while (events.hasNext()) {
            System.out.println(events.next());
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    //Represents a listener to move from splash screen to main menu
    private class SplashListener implements MouseListener {

        //MODIFIES: MainMenu
        //EFFECTS: Hides splash screen, presents main menu
        @Override
        public void mouseClicked(MouseEvent e) {
            if (onSplash) {
                onSplash = false;
                buildMenu();
                imagePanel.setVisible(false);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    //Represents an action to save parties
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save Parties");
        }

        //EFFECTS: Saves all parties
        @Override
        public void actionPerformed(ActionEvent e) {
            partyManager.save();
        }

    }

    //Represents an action to load parties
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Parties");
        }

        //EFFECTS: Loads all parties
        @Override
        public void actionPerformed(ActionEvent e) {
            partyManager.load();
        }

    }

    //Represents an action to add a party
    private class AddPartyAction extends AbstractAction {

        AddPartyAction() {
            super("Add Party");
        }

        //MODIFIES: partyManager
        //EFFECTS: Adds a party to party manager
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = partyNameField.getText();
            partyManager.addParty(name);
        }
    }

    //EFFECTS: Adds a partyUI for the given party to the main menu
    public void openParty(Party party) {
        JInternalFrame partyFrame = new JInternalFrame();
        partyFrame.setClosable(true);
        partyFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        partyFrame.setLayout(new GridLayout(1, 2));
        PartyUI partyUI = new PartyUI(this, party, partyFrame);
        partyFrame.add(partyUI);
        add(partyFrame);
        try {
            partyFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
            //Need to look at what this exception is
        }
        partyFrame.setVisible(true);
    }


    public static void main(String[] args) {
        new MainMenu();
    }
}
