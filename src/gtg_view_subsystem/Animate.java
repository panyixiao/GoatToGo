package gtg_view_subsystem;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Animate {
    private JPanel panel;
    private int from;
    private int to;
    private MapPage parent;

    private long startTime;

    public Animate() {
    }

    public void setAnimationPanel(MapPage parent, JPanel panel, int from, int to){
    	this.parent = parent;
    	this.panel = panel;
    	this.from = from;
    	this.to = to;
    	
    }

    public void startAnimationLeft() {
    	parent.animationStarted();
    	Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	from = from - 1;
            	panel.setLocation(from, 0);
               if(from <= to){
            	   ((Timer)e.getSource()).stop();
               		parent.animationEnd();
               }
            }
        });
        timer.start();
       
    }
    
    public void startAnimationRight() {
    	parent.animationStarted();
    	Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	from = from + 1;
            	panel.setLocation(from, 0);
               if(from >= to){
            	   ((Timer)e.getSource()).stop();
            	   parent.animationEnd();
               }
            }
        });
        timer.start();
       
    }
}
