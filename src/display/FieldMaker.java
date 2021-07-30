/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import controls.SimControl;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import simulation.SimStateFacade;

/**
 *
 * @author Kathryn Andrew
 */
public class FieldMaker extends ComponentMaker {
    
    private FieldsSelect fieldsSelect; 
    private FieldsGraphics fieldsGraphics;    
    
    JTextField nameField;
    JButton colorChooser;
    JPanel colorSwatch;
    JPanel makerPanel;
    
    
    JPanel fieldsPreview;
    LabelledSlider growthRateSlider;
    LabelledSlider densitySlider;
        

    
    public FieldMaker(SimControl simControl, SimStateFacade simFacade) {
        
        super(simControl, simFacade);
              
        makerPanel = new JPanel(new GridLayout(6, 1));
        this.add(makerPanel);
        makeNamePanel();
        makeDensityPanel();      
        makeGrowthRateSlider();
        makeColorPanel();
        makeAddComponentButton();

       
        fieldsGraphics = new FieldsGraphics(200, 200, super.getFacade().fieldsReport());
        fieldsSelect= new FieldsSelect(this);
        this.add(fieldsSelect);
        
        fieldsSelect.setup("Fields", getIDsAndLabels(), true, true);

        
        makeRemoveComponentButton();
        
        this.add(fieldsGraphics);
    }
    
    
    private void makeNamePanel() {
        JPanel namePanel = new JPanel();       
        JLabel nameLabel = new JLabel("Name: ");        
        nameField = new JTextField();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        nameField.setPreferredSize(new Dimension(100, 25));
        makerPanel.add(namePanel);                
    }
    
    private void makeDensityPanel() {    
        densitySlider = new LabelledSlider("Density", 0, 100, 100, 11);
        makerPanel.add(densitySlider);        
    }
    
    private void makeGrowthRateSlider() {
        growthRateSlider = new LabelledSlider("Growth %", 0, 10, 100, 0);       
        makerPanel.add(growthRateSlider);        
    }    
    
    private void makeColorPanel() {
        JPanel colorPanel = new JPanel();  
        
        colorSwatch = new JPanel();
        colorSwatch.setPreferredSize(new Dimension(25, 25));
        colorSwatch.setBackground(Color.BLUE);
        
        colorChooser = new JButton("Select Colour");
        colorChooser.addActionListener(this);
        
        colorPanel.add(colorSwatch);
        colorPanel.add(colorChooser);
        
        makerPanel.add(colorPanel);               
    }    

    @Override
    void makeAddComponentButton() {
        addComponentButton = new JButton("Add field");
        addComponentButton.addActionListener(this);        
        makerPanel.add(addComponentButton);               
    }  
    
    @Override
    void makeRemoveComponentButton() {
        removeComponentButton = new JButton("Remove fields");
        removeComponentButton.addActionListener(this);        
        makerPanel.add(removeComponentButton);               
    }    
    
    @Override
    public void actionPerformed(ActionEvent e){        
        if (e.getSource().equals(colorChooser)) {
            Color c=JColorChooser.showDialog(this,"Select colour",Color.GREEN);
            if (c != null)
                colorSwatch.setBackground(c);
        } else if (e.getSource().equals(addComponentButton)) {
            if (nameField.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Invalid name.");
            } else {
                Color c = colorSwatch.getBackground();
                boolean existingName = super.getControl().addField(nameField.getText().strip(), (int) densitySlider.getValue(), growthRateSlider.getValue()/100.0, c.getRed(), c.getGreen(), c.getBlue());
                if (existingName)
                    JOptionPane.showMessageDialog(null, "A field with this name already exists.");
                else {                 
                    update();
                }
            }            
        } else if (e.getSource().equals(removeComponentButton)) {
            super.getControl().removeFields(fieldsSelect.getSelected());
            update();
        }        
    }
   
    
    /**
     * Allows the FieldsSelect to update the FieldsGraphics
     * @param newActiveFields 
     */
    public void setSelected(Set<String> newActiveFields) {
        fieldsGraphics.setActiveFields(newActiveFields);
    }
    
    /**
     * Updates the whole panel.  Call after change to simulation environment.
     */
    @Override
    public void update() {
        List<Map<String, Object>> fieldsReport = super.getFacade().fieldsReport();
        fieldsSelect.setup("Fields", getIDsAndLabels(), true, false);
        fieldsGraphics.updateData(fieldsReport);
        Set<String> newSelect = fieldsSelect.getSelected();
        newSelect.add(nameField.getText().strip());
        fieldsSelect.setSelected(newSelect);
        this.repaint();
        this.revalidate();    
    }    
    
    /**
     * In this case only calls the standard update.
     */
    @Override
    public void updateAll() {
        update();
    }
    
    
    public Map<String, String> getIDsAndLabels() {
        Map<String, String> out = new HashMap<>();
        for (String entry : super.getFacade().getFields()) {
            out.put(entry, entry);
        }
        return out;
    }
        
}
