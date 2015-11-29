package gtg_view_subsystem;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 */
public class MapMapDisplayPanel extends MapDisplayPanel{
	private JPopupMenu popup;
	private JMenuItem menuItem, menuItem_1;
	private SelectedPoints selectedPoints = null;
	private Image locationImage, locationEndImage;
	private String map;
	private Point startEndPoint;
	private MapPage parent;
	private ArrayList<Point> graphPoints = new ArrayList<Point>();
	private Boolean showLocations = false;
	private int circleWidthHeight = 10;
	/**
	 * Create the panel.
	
	 * @param selectedPoints 
	 * @param parent MapPage
	 * @param mapPanelHolder JScrollPane
	 * @param mapName String
	 * @param mapurl String
	 */
	public MapMapDisplayPanel(MapPage parent, JScrollPane mapPanelHolder, String mapName, String mapurl, SelectedPoints selectedPoints) {
		super(mapPanelHolder, mapurl);
		this.parent = parent;
		this.map = mapName;
		this.selectedPoints = selectedPoints;

		super.loadImage(mapurl);
		this.loadLocationImage();
		this.loadLocationEndImage();
	    
	    this.popup = new JPopupMenu();
	    this.popup.setFont(new Font("Meiryo", Font.PLAIN, 22));
	    this.popup.setVisible(false);
	    this.menuItem = new JMenuItem(ViewStringLiterals.SET_AS_START_LOCATION);
	    this.menuItem.setFont(new Font("Meiryo", Font.PLAIN, 22));
	    this.menuItem.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		startEndPoint = parent.sentPointToModel(startEndPoint, ViewStringLiterals.FROM, map);
	    		selectedPoints.setStartLocation((int)startEndPoint.getX(), (int)startEndPoint.getY(), map);
	    		parent.displayPointInTextfield(ViewStringLiterals.FROM, startEndPoint.getX(), startEndPoint.getY());
	    		revalidate();
	    		repaint();
	    	}
	    });

	    this.popup.add(this.menuItem);
	    this.menuItem_1 = new JMenuItem(ViewStringLiterals.SET_AS_END_LOCATION);
	    this.menuItem_1.setFont(new Font("Meiryo", Font.PLAIN, 22));
	    this.menuItem_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		startEndPoint = parent.sentPointToModel(startEndPoint, ViewStringLiterals.TO, map);
	    		selectedPoints.setEndLocation((int)startEndPoint.getX(), (int)startEndPoint.getY(), map);
	    		parent.displayPointInTextfield(ViewStringLiterals.TO, startEndPoint.getX(), startEndPoint.getY());
	    		revalidate();
	    		repaint();
	    	}
	    });
	    this.popup.add(this.menuItem_1);
	}
	    
	/**
	 * Method paintComponent.
	 * @param g Graphics
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
    	if(this.selectedPoints.getStartMapName() == this.map){
    		g2.drawImage(this.locationImage, (int)this.selectedPoints.getStartX() - 10, (int)this.selectedPoints.getStartY() - 25, 20, 25, null);
    	}
    	
    	if(this.selectedPoints.getEndMapName() == this.map){
    		g2.drawImage(this.locationEndImage, (int)this.selectedPoints.getEndX() - 10, (int)this.selectedPoints.getEndY() - 25, 20, 25, null);
    	}
    	
    	if(this.showLocations == true){
    		for(int i = 0; i < this.graphPoints.size(); i++){
    			Point p = this.graphPoints.get(i);
    			Ellipse2D.Double circle = new Ellipse2D.Double(p.getX() - (circleWidthHeight * super.getScale() / 2),
    					p.getY() - (circleWidthHeight * super.getScale() / 2), circleWidthHeight * super.getScale(),
    					circleWidthHeight * super.getScale());
    			g2.fill(circle);
    		}
    	}
	}
	
	/**
	 * Method mousePressed.
	 * @param me MouseEvent
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent me){
		super.mousePressed(me);
		this.maybeShowPopup(me);
	}

	/**
	 * Method mouseReleased.
	 * @param me MouseEvent
	 * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		this.maybeShowPopup(me);
	}

	/**
	 * Method loadLocationImage.
	 * @param none
	 * Loads the image used for displaying start location on the map image
	 */
	public void loadLocationImage() {
        try {
            this.locationImage = ImageIO.read(new File(ImageURLS.LOCATION_IMAGE));
        }
        catch(MalformedURLException mue) {
            System.out.println("URL trouble: " + mue.getMessage());
        }
        catch(IOException ioe) {
        	System.out.println("read trouble: " + ioe.getMessage());
        }
	}
	
	/**
	 * Method loadLocationEndImage.
	 * @param none
	 * Loads the image used for displaying end location on the map image
	 */
	public void loadLocationEndImage() {
        try {
            this.locationEndImage = ImageIO.read(new File(ImageURLS.LOCATION_END_ICON));
        }
        catch(MalformedURLException mue) {
            System.out.println("URL trouble: " + mue.getMessage());
        }
        catch(IOException ioe) {
        	System.out.println("read trouble: " + ioe.getMessage());
        }
	}
	
	/**
	 * Method maybeShowPopup.
	 * @param me MouseEvent
	 */
	private void maybeShowPopup(MouseEvent me) {
        if (me.isPopupTrigger()) {
            this.popup.show(me.getComponent(), me.getX(), me.getY());
            
            double scale = super.getScale();
            if(scale > 1.0){
            	 this.startEndPoint= new Point((int)(me.getX() / scale), (int)(me.getY() / scale));
			} else {
				 this.startEndPoint= new Point(me.getX(), me.getY());
			}
        }
    }

	/**
	 * Method deletePoint.
	 * @param location String
	 */
	public void deletePoint(String location) {
		if(location == ViewStringLiterals.FROM){
			if(this.selectedPoints.getStartMapName() == this.map){
				this.selectedPoints.resetStart();
				this.revalidate();
				this.repaint();
			} else {
				this.selectedPoints.resetStart();
			}
		} else if(location == ViewStringLiterals.TO){
			if(this.selectedPoints.getEndMapName() == this.map){
				this.selectedPoints.resetEnd();
				this.revalidate();
				this.repaint();
			} else {
				this.selectedPoints.resetEnd();
			}
		}
	}

	/**
	 * Method displayPoint.
	 * @param none
	 * Whenever user sets a selected point this method is called to update the map view to show the selected point.
	 */
	public void displayPoint() {
		// display point to user
		revalidate();
		repaint();
	}
	
	/**
	 * Method totalGraphPoints.
	 * @param none
	 * Returns the total number of graph points created for the map.
	 */
	public int totalGraphPoints(){
		return this.graphPoints.size();
	}
	
	/**
	 * Method showLocations.
	 * @param none
	 * Displays all the graph points
	 */
	public void showLocations(){
		this.showLocations = true;
		revalidate();
		repaint();
	}
	
	/**
	 * Method hideLocations.
	 * @param none
	 * Hides all the graph points
	 */
	public void hideLocations(){
		this.showLocations = false;
		revalidate();
		repaint();
	}
	
	/**
	 * Method addGraphPoints.
	 * @param graphPoints 
	 * @param none
	 * Adding graph points to Array
	 */
	public void addGraphPoints(ArrayList<Point> graphPoints){
		this.graphPoints = graphPoints;
	}
}
