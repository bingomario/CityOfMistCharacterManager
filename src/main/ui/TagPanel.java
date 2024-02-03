package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//Represents a panel of tags that can be added to or removed from
public class TagPanel extends JPanel implements ListSelectionListener {
    private List<String> tags;
    private JList tagList;
    private DefaultListModel listModel;
    private JTextField tagField;
    private JButton deleteButton;

    //EFFECTS: Creates a new TagPanel with the given list of tags
    public TagPanel(List<String> tags) {
        super();
        setLayout(new BorderLayout());
        this.tags = tags;
        setupList();
        loadTags();
        JScrollPane listScrollPane = new JScrollPane(tagList);
        listScrollPane.setVisible(true);

        JPanel modifyPane = new JPanel();
        setupPane(modifyPane);
        modifyPane.setVisible(true);

        add(listScrollPane, BorderLayout.CENTER);
        add(modifyPane, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: Sets up ui list for tags, adding required elements
    private void setupList() {
        listModel = new DefaultListModel();
        tagList = new JList(listModel);
        tagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tagList.setSelectedIndex(-1);
        tagList.addListSelectionListener(this);
        tagList.setVisibleRowCount(0);
        tagList.setBackground(new Color(5, 5, 125));
        tagList.setForeground(new Color(223, 223, 223));
        tagList.setFont(MainMenu.NAMEFONT);
    }

    //MODIFIES: this
    //EFFECTS: Adds existing tags into UI
    private void loadTags() {
        for (String tag : tags) {
            listModel.addElement(tag);
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets up text field and button for adding & deleting tags
    private void setupPane(JPanel pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        tagField = new JTextField(15);
        tagField.addActionListener(new AddTagAction());

        deleteButton = new JButton("Delete Tag");
        deleteButton.addActionListener(new DeleteTagAction());

        pane.add(tagField);
        pane.add(Box.createHorizontalGlue());
        pane.add(deleteButton);
    }

    //MODIFIES: this
    //EFFECTS: enables or disables delete button depending on if we have selected a value
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (tagList.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
            }
        }
    }

    //Represents an action to add a tag
    private class AddTagAction implements ActionListener {

        //MODIFIES: this
        //EFFECTS: if a tag name is provided, adds a new tag, otherwise does nothing
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!tagField.getText().equals("")) {
                String tag = tagField.getText();
                tags.add(tag);
                listModel.addElement(tag);
            }
        }
    }

    //Represents an action to delete a tag
    private class DeleteTagAction implements ActionListener {

        //If a tag is selected, deletes it
        public void actionPerformed(ActionEvent e) {
            int index = tagList.getSelectedIndex();
            if (index >= 0 && index < tags.size()) {
                //System.out.println("Deleting tag " + tags.get(index));
                tags.remove(index);
                listModel.remove(index);
                tagList.setSelectedIndex(-1);
            }
        }
    }

}
