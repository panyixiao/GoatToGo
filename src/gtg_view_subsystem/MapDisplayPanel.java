package gtg_view_subsystem;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class MapDisplayPanel extends JPanel implements MouseListener, MouseMotionListener{
	private Image img;
	private Point origin;
	private JScrollPane mapPanelHolder;
	/**
	 * Create the panel.
	 */
	public MapDisplayPanel(JScrollPane mapPanelHolder, String mapurl) {
		this.mapPanelHolder = mapPanelHolder;
		addMouseListener(this);
		addMouseMotionListener(this);
		this.img = new ImageIcon(mapurl).getImage();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1024,1024));
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0 ,null);
		g.drawLine(10, 10, 100, 100);
	}
	
	@Override
	public void mousePressed(MouseEvent me){
		origin = new Point(me.getPoint());
		//System.out.println(origin);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		 if (origin != null) {
             JViewport viewPort = this.mapPanelHolder.getViewport();
             if (viewPort != null) {
                 int deltaX = origin.x - me.getX();
                 int deltaY = origin.y - me.getY();

                 Rectangle view = viewPort.getViewRect();
                 view.x += deltaX;
                 view.y += deltaY;

                 this.scrollRectToVisible(view);
             }
         }
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
