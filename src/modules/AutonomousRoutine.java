/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Darvin
 */
public class AutonomousRoutine extends StaticWidget{
    
    public static final DataType[] TYPES = { DataType.NUMBER };
    int routine_num = 0;
    
    public void setValue(Object value){
        routine_num = (Integer) value;
    }
    
    public void init(){
        JLabel routine = new JLabel("Routine");
        final JTextField text = new JTextField("" + NetworkTable.getTable("SmartDashboard").getNumber("Autonomous Routine"));
        
        text.setSize(100, text.getHeight());
        text.setEditable(true);
        text.setEnabled(true);
        
        text.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                NetworkTable.getTable("SmartDashboard").putValue("Autonomous Routine", new Double(text.getText()));
            }
        });
        
        add(routine);
        add(text);
    }
    
    public void propertyChanged(Property p){
        
    }
}
