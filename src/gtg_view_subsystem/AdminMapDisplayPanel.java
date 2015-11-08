package gtg_view_subsystem;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JScrollPane;

public class AdminMapDisplayPanel extends MapDisplayPanel{
	private ArrayList<Point2D> pointPositions = new ArrayList<Point2D>();
	private int circleWidthHeight = 10;
	/**
	 * Create the panel.
	 */
	public AdminMapDisplayPanel(JScrollPane mapPanelHolder, String mapurl) {
		super(mapPanelHolder, mapurl);
	}
	    
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
        for(int i= 0; i < pointPositions.size();i++){
        	Point2D p = pointPositions.get(i);
        	Ellipse2D.Double circle = new Ellipse2D.Double(p.getX() - (circleWidthHeight * super.getScale() / 2), p.getY()  - (circleWidthHeight * super.getScale() / 2), circleWidthHeight * super.getScale(), circleWidthHeight * super.getScale());
        	g2.fill(circle);
        }
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		double scale = super.getScale();
		if(me.getButton() == MouseEvent.BUTTON1) {
			if(scale > 1.0){
				pointPositions.add(new Point2D.Double(me.getX() / scale, me.getY() / scale));
			} 
			else {
				pointPositions.add(new Point2D.Double(me.getX(), me.getY()));
			}/**/
			
			revalidate();
			repaint();
		} else if(me.getButton() == MouseEvent.BUTTON3) {
			checkIfPointIsDrawn(me.getX(), me.getY(), scale);
	    }
	}

	public void checkIfPointIsDrawn(int x, int y, double scale){
		for(int i= 0; i < pointPositions.size();i++){
        	Point2D p = pointPositions.get(i);
        	if(isInCircle(p.getX(), p.getY(), circleWidthHeight / 2, x / scale, y / scale, scale)){
        		pointPositions.remove(i);
        		revalidate();
				repaint();
        	}
        }
	}
	
	public boolean isInCircle(double circleX, double circleY, int r, double x, double y, double scale) {
	    double d = Math.sqrt(Math.pow(circleX - x, 2) + Math.pow(circleY - y, 2));
	    return d <= r * scale;
	}

}
