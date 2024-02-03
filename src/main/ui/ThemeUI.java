package ui;

import model.Theme;

import javax.swing.*;
import java.awt.*;

//THINGS TO DO:
//ADD ATTENTION/LOSS FUNCTIONALITY
//FIX SOME FORMATTING
//ADD LABELS FOR POWER VS WEAKNESS

//Represents a Theme in UI, presenting all theme information and tags
public class ThemeUI extends JPanel {
    private Theme theme;
    private JPanel basicInfo;
    private JPanel growthInfo;
    private JPanel powerTags;
    private JPanel weaknessTags;

    //Creates a new theme panel for the given theme
    public ThemeUI(Theme theme) {
        super();
        this.setLayout(new BorderLayout());
        this.theme = theme;
        basicInfo = new JPanel();
        growthInfo = new JPanel();
        setupBasicInfo();
        setupGrowthInfo();

        powerTags = new TagPanel(theme.getTags());
        weaknessTags = new TagPanel(theme.getWeaknesses());



        add(basicInfo, BorderLayout.NORTH);
        add(growthInfo, BorderLayout.CENTER);
        add(powerTags, BorderLayout.WEST);
        add(weaknessTags, BorderLayout.EAST);
    }

    //MODIFIES: this
    //EFFECTS: Sets the required labels and elements for basic theme info
    private void setupBasicInfo() {
        basicInfo.setLayout(new BoxLayout(basicInfo, BoxLayout.LINE_AXIS));
        JLabel name = new JLabel("Name: " + theme.getName());
        name.setFont(MainMenu.NAMEFONT);
        JLabel core = new JLabel("Core: " + theme.getCoreStatement());
        core.setFont(MainMenu.NAMEFONT);
        basicInfo.add(name);
        basicInfo.add(Box.createHorizontalGlue());
        basicInfo.add(core);
    }

    //MODIFIES: this
    //EFFECTS: Sets the required labels ad elements for theme growth
    private void setupGrowthInfo() {
        growthInfo.setLayout(new BoxLayout(growthInfo, BoxLayout.PAGE_AXIS));
        JLabel attention = new JLabel("Attention: " + theme.getAttention() + "/3");
        attention.setFont(MainMenu.NAMEFONT);
        JButton markAttention = new JButton("Mark Attention");

        JLabel improvements = new JLabel("Number of Improvements: " + theme.getNumImprovements());
        improvements.setFont(MainMenu.NAMEFONT);

        JLabel loss = new JLabel("Loss: " + theme.getLoss() + "/3");
        loss.setFont(MainMenu.NAMEFONT);

        JButton markLoss = new JButton("Mark Loss");

        growthInfo.add(attention);
        growthInfo.add(markAttention);
        growthInfo.add(improvements);
        growthInfo.add(loss);
        growthInfo.add(markLoss);
    }
}
