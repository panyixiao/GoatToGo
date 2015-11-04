package gtg_view_subsystem;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MapMapDisplayPanel extends MapDisplayPanel{
	private JPopupMenu popup;
	private JMenuItem menuItem, menuItem_1;
	private SelectedPoints selectedPoints = null;
	private Image locationImage;
	private String map;
	private Point startEndPoint;
	private MapPage parent;
	/**
	 * Create the panel.
	 * @param mapPage 
	 * @param selectedPoints 
	 */
	public MapMapDisplayPanel(MapPage parent, JScrollPane mapPanelHolder, String mapName, String mapurl, SelectedPoints selectedPoints) {
		super(mapPanelHolder, mapurl);
		this.parent = parent;
		this.map = mapName;
		this.selectedPoints = selectedPoints;

		super.loadImage(mapurl);
		this.loadLocationImage();
	    
	    this.popup = new JPopupMenu();
	    this.popup.setFont(new Font("Meiryo", Font.PLAIN, 22));
	    this.popup.setVisible(false);
	    this.menuItem = new JMenuItem(ViewStringLiterals.SET_AS_START_LOCATION);
	    this.menuItem.setFont(new Font("Meiryo", Font.PLAIN, 22));
	    this.menuItem.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		parent.sentPointToModel(startEndPoint, ViewStringLiterals.FROM, map);
	    		//selectedPoints.setStartLocation((int)startEndPoint.getX(), (int)startEndPoint.getY(), map);
	    		//parent.displayPointInTextfield(ViewStringLiterals.FROM, startEndPoint.getX(), startEndPoint.getY());
	    		//revalidate();
	    		//repaint();
	    	}
	    });

	    this.popup.add(this.menuItem);
	    this.menuItem_1 = new JMenuItem(ViewStringLiterals.SET_AS_END_LOCATION);
	    this.menuItem_1.setFont(new Font("Meiryo", Font.PLAIN, 22));
	    this.menuItem_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		parent.sentPointToModel(startEndPoint, ViewStringLiterals.TO, map);
	    		//selectedPoints.setEndLocation((int)startEndPoint.getX(), (int)startEndPoint.getY(), map);
	    		//parent.displayPointInTextfield(ViewStringLiterals.TO, startEndPoint.getX(), startEndPoint.getY());
	    		//revalidate();
	    		//repaint();
	    	}
	    });
	    this.popup.add(this.menuItem_1);
	}
	    
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
    	if(this.selectedPoints.getStartMapName() == this.map){
    		g2.drawImage(this.locationImage, (int)this.selectedPoints.getStartX() - 10, (int)this.selectedPoints.getStartY() - 25, 20, 25, null);
    	}
    	
    	if(this.selectedPoints.getEndMapName() == this.map){
    		g2.drawImage(this.locationImage, (int)this.selectedPoints.getEndX() - 10, (int)this.selectedPoints.getEndY() - 25, 20, 25, null);
    	}
	}
	
	@Override
	public void mousePressed(MouseEvent me){
		super.mousePressed(me);
		this.maybeShowPopup(me);
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		this.maybeShowPopup(me);
	}

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

	public void displayPoint() {
		// display point to user
		revalidate();
		repaint();
	}
}
