/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Kathryn Andrew
 */
public class LabelledSlider extends JPanel {

    JSlider slider;
    JLabel label;
    String name;
    double min;
    double max;
    int steps;
    Updatable container;
    
    public LabelledSlider(String name, double min, double max, int steps, int start, Updatable container) {
        this.setPreferredSize(new Dimension(300, 50));
        
        this.min = min;
        this.max = max;
        this.steps = steps;
        this.container = container;

        this.name = name;
        slider = new JSlider(JSlider.HORIZONTAL, 0, steps, start);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        
        label = new JLabel(name + ": " + String.format("%.2f",getValue())); 
        
        slider.addChangeListener(new ChangeListener() {
          @Override
          public void stateChanged(ChangeEvent event) {
            label.setText(name + ": " + String.format("%.2f",getValue()));
            if (container != null) {
                container.update();
            }
          }
        });
                
        
        this.add(label);
        this.add(slider);      
    }
    
    public final double getValue() {
        return min + (slider.getValue()/(double)steps * (max - min));
    } 
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setToolTipText(String s) {
        label.setToolTipText(s);
        slider.setToolTipText(s);
    }

}
