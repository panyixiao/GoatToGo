package gtg_view_subsystem;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.Dialog;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import gtg_control_subsystem.MainController;

public class AdminMapDisplayPanel extends MapDisplayPanel {
	private ArrayList<Point2D> pointPositions = new ArrayList<Point2D>();
	private int circleWidthHeight = 10;
	private ImageIcon icon;
	private JPanel imputPopup;
	private String currentMap, mode, building, floor;
private MainController adminCOntrol;
	/**
	 * Create the panel.
	 */
	public AdminMapDisplayPanel(JScrollPane mapPanelHolder, String mapurl) {
		super(mapPanelHolder, mapurl);
		this.currentMap = mapurl;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < pointPositions.size(); i++) {
			Point2D p = pointPositions.get(i);
			Ellipse2D.Double circle = new Ellipse2D.Double(p.getX() - (circleWidthHeight * super.getScale() / 2),
					p.getY() - (circleWidthHeight * super.getScale() / 2), circleWidthHeight * super.getScale(),
					circleWidthHeight * super.getScale());
			g2.fill(circle);
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		double scale = super.getScale();
		Point2D newPoint;
		String newEnterenceId;
		String newDescription;
		JTextField description = new JTextField(10);
		JTextField entranceId = new JTextField(3);
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Description:"));
		panel.add(description);
		panel.add(Box.createHorizontalStrut(5)); // a spacer
		panel.add(new JLabel("Entrance ID:"));
		panel.add(entranceId);

		if (me.getButton() == MouseEvent.BUTTON1) {
			System.out.println("Mouse Mode" + this.mode);
			switch (this.mode) {
			case "Create Points":
				int result = JOptionPane.showConfirmDialog(null, panel, "Please Describe Point",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (scale > 1.0) {
						newPoint = new Point2D.Double(me.getX() / scale, me.getY() / scale);
						pointPositions.add(newPoint);
					} else {
						newPoint = new Point2D.Double(me.getX(), me.getY());
						pointPositions.add(newPoint);
					}
					newEnterenceId = entranceId.getText();
					newDescription = description.getText();
					System.out.println("Building: " + this.building);
					System.out.println("Floor: " + this.floor);
					System.out.println("description: " + description.getText());
					System.out.println("Exit ID: " + entranceId.getText());
				}
				break;
			default:
				System.out.println("Sorry");
			}
			
			revalidate();
			repaint();
		} else if (me.getButton() == MouseEvent.BUTTON3) {
			checkIfPointIsDrawn(me.getX(), me.getY(), scale);
		}
	}

	public void checkIfPointIsDrawn(int x, int y, double scale) {
		for (int i = 0; i < pointPositions.size(); i++) {
			Point2D p = pointPositions.get(i);
			if (isInCircle(p.getX(), p.getY(), circleWidthHeight / 2, x / scale, y / scale, scale)) {
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

	public void setMode(String m) {
		this.mode = m;
	}

	public void setBuilding(String b) {
		this.building = b;
	}

	public void setFloor(String f) {
		this.floor = f;
	}
}
