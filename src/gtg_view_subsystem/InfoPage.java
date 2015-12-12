package gtg_view_subsystem;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Dimension;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;

public class InfoPage extends JPanel {
	
	private JPanel imageHolder;
	private BufferedImage image;
	private JTextArea textArea;
	
	public InfoPage(String ImageURL, String Description){
			
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        JSplitPane splitPane = new JSplitPane();
        add(splitPane);        

		loadImage(ImageURL);
		imageHolder = new JPanel(){
        	@Override
        	protected void paintComponent(Graphics g){
        		super.paintComponent(g);
        		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        	}
        	
        	@Override
        	public Dimension getPreferredSize(){
        		Dimension size = super.getPreferredSize();
        		size.width = image.getWidth();
        		size.height = image.getHeight();
        		return size;
        	}
        };

        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(image.getWidth());
        splitPane.setLeftComponent(imageHolder);

        
        textArea = new JTextArea(){
        	@Override
        	public Dimension getPreferredSize(){
        		Dimension size = super.getPreferredSize();
        		size.width = 150;
        		size.height = image.getHeight();
        		return size;
        	}
        };
        splitPane.setRightComponent(textArea);
        textArea.setText(Description);	
        textArea.setEditable(false);
        
        splitPane.setEnabled(false);        
	}
	
	
	private void loadImage(String mapurl) {
        try {
            this.image = ImageIO.read(new File(mapurl));
        }
        catch(MalformedURLException mue) {
            System.out.println("URL trouble: " + mue.getMessage());
        }
        catch(IOException ioe) {
        	System.out.println("read trouble: " + ioe.getMessage());
        }
	}

}
