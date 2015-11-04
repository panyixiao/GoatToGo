package gtg_view_subsystem;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.JScrollPane;

public class ResultMapDisplayPanel extends MapDisplayPanel{
	private Image locationImage;
	private String map;
	private ResultPage parent;
	/**
	 * Create the panel.
	 * @param mapPage 
	 * @param selectedPoints 
	 */
	public ResultMapDisplayPanel(ResultPage parent, JScrollPane mapPanelHolder, String mapName, String mapurl) {
		super(mapPanelHolder, mapurl);
		this.parent = parent;
		this.map = mapName;

		super.loadImage(mapurl);
		this.loadLocationImage();
	}
	    
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
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
}
