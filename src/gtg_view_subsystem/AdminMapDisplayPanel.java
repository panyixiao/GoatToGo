package gtg_view_subsystem;

import gtg_view_subsystem.AdminMapEditPage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.Dialog;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

//import gtg_control_subsystem.MainController;

/**
 */
public class AdminMapDisplayPanel extends MapDisplayPanel {
	private Point2D newPoint, newStart, newEnd;
	private int circleWidthHeight = 10;
	private ImageIcon icon;
	private JPanel imputPopup;
	private String currentMap, mode, building, floor;
	private AdminMapEditPage adminViewPageHandle;

	/**
	 * Create the panel.
	 * 
	 * @param mapPanelHolder
	 *            JScrollPane
	 * @param mapurl
	 *            String
	 * @param adminViewPage
	 *            AdminMapEditPage
	 */
	public AdminMapDisplayPanel(JScrollPane mapPanelHolder, String mapurl, AdminMapEditPage adminViewPage) {
		super(mapPanelHolder, mapurl);
		this.currentMap = mapurl;
		this.mode = "Create Points";
		adminViewPageHandle = adminViewPage;
		this.newStart = new Point2D.Double(0, 0);
		this.newEnd = new Point2D.Double(0, 0);

		this.building = adminViewPage.getBuilding();
		this.floor = adminViewPage.getFloor();
	}

	/**
	 * Method paintComponent.
	 * 
	 * @param g
	 *            Graphics
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Here, need to get points, which have been created and stored in
		// controller;

		for (int i = 0; i < adminViewPageHandle.pointPositions.size(); i++) {
			Point2D p = adminViewPageHandle.pointPositions.get(i);
			Ellipse2D.Double circle = new Ellipse2D.Double(p.getX() - (circleWidthHeight * super.getScale() / 2),
					p.getY() - (circleWidthHeight * super.getScale() / 2), circleWidthHeight * super.getScale(),
					circleWidthHeight * super.getScale());
			// System.out.println("Will Draw at:"+p.getX()+","+p.getY());
			g2.fill(circle);
		}

		// Here, need to get edges, which have been created and stored in
		// controller;
		for (int i = 0; i < adminViewPageHandle.pointNeighbors.size() - 1; i += 2) {
			Point2D p1 = adminViewPageHandle.pointNeighbors.get(i);
			Point2D p2 = adminViewPageHandle.pointNeighbors.get(i + 1);
			g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}
	}

	/**
	 * Method mouseClicked.
	 * 
	 * @param me
	 *            MouseEvent
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		double scale = super.getScale();
		String newEnterenceIdString, newType, newDescription, newBuilding;
		int newEnterenceId;
		int floorNum;
		JTextField description = new JTextField();
		JTextField entranceId = new JTextField();
		JTextField building = new JTextField();
		building.setText(this.building);
		if (this.building.equals("Campus")) {
			building.setEditable(true);
		} else {
			building.setEditable(false);
		}
		JTextField floor = new JTextField();
		floor.setText(this.floor);
		if (this.floor.equals("Campus")) {
			floor.setEditable(true);
		} else {
			floor.setEditable(false);
		}
		JPanel panel = new JPanel(new GridLayout(0, 2));
		String[] listPointTypes = { "Classroom", "Office", "Elevator", "Stairs", "Building", "Parking Lot",
				"Men's Restroom", "Woman's Restroom", "Cafe", "Vending", "Water fountian", "Waypoint" };

		JComboBox pointType = new JComboBox(listPointTypes);

		entranceId.setText("0");
		panel.add(new JLabel("Building:"));
		panel.add(building);

		panel.add(new JLabel("Floor:"));
		panel.add(floor);

		panel.add(new JLabel("Entrance ID:"));
		panel.add(entranceId);

		panel.add(new JLabel("Type:"));
		panel.add(pointType);

		panel.add(new JLabel("Description:"));
		panel.add(description);
		if (me.getButton() == MouseEvent.BUTTON1) {
			switch (this.mode) {
			case "Create Points":
				int result = JOptionPane.showConfirmDialog(null, panel, "Please Describe Point",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (scale > 1.0) {
						newPoint = new Point2D.Double(me.getX() / scale, me.getY() / scale);

					} else {
						newPoint = new Point2D.Double(me.getX(), me.getY());

					}
					
					newEnterenceIdString = entranceId.getText();

					newDescription = description.getText();
					if (floor.getText().isEmpty()) {
						floorNum = 1;

					} else {
						try {
							floorNum = Integer.parseInt(floor.getText());
						} catch (NumberFormatException e) {
							floorNum = 0;
						}
					}
					if (building.getText().isEmpty()) {
						newBuilding = "Null";

					} else {
						newBuilding = building.getText();
					}

					newType = (String) pointType.getSelectedItem();
					if (newEnterenceIdString.isEmpty()) {
						newEnterenceIdString = "0";
					}
					newEnterenceId = Integer.parseInt(newEnterenceIdString);

					if (newDescription == null) {
						newDescription = "Null";
					}
					adminViewPageHandle.CreatePoint(newPoint, 
													floorNum, 
													newEnterenceId, 
													newBuilding, 
													newType,
													newDescription);

					System.out.println("Building: " + this.building);
					System.out.println("Floor: " + this.floor);
					System.out.println("Description: " + description.getText());
					System.out.println("Exit ID: " + entranceId.getText());
					System.out.println("Type: " + newType);
				}
				break;

			case "Create Path":

				Point2D checkResult = adminViewPageHandle.returnLastPointInList();

				if (checkResult.getX() != 0) {
					this.addStart(checkResult);
					newPoint = new Point2D.Double(me.getX() / scale, me.getY() / scale);
					createPathPoint(newPoint);
					this.addNeighbors(me.getPoint());
				}

				else {
					newPoint = new Point2D.Double(me.getX() / scale, me.getY() / scale);
					createPathPoint(newPoint);
				}
				break;

			case "Select Neighbors":

				this.newPoint = new Point2D.Double(me.getX() / scale, me.getY() / scale);

				JPopupMenu selectAction = new JPopupMenu();
				JMenuItem selectPoint, selectNeighbor;
				selectPoint = new JMenuItem("From");
				selectPoint.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addStart(me.getPoint());
					}
				});

				selectNeighbor = new JMenuItem("To");
				selectNeighbor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addNeighbors(me.getPoint());
					}
				});

				selectAction.add(selectPoint);
				selectAction.add(selectNeighbor);
				selectAction.show(this, (int) newPoint.getX(), (int) newPoint.getY());

				// System.out.println("Start: "+ this.newStart);
				// System.out.println("End: "+ this.newEnd);
				// System.out.println("Paths: "+
				// this.adminViewPageHandle.pointNeighbors);

				break;

			default:
				System.out.println("No mode selected, Please select a mode");
			}

		} else if (me.getButton() == MouseEvent.BUTTON3) {
			Point2D point2bDeleted = new Point2D.Double(me.getX() / scale, me.getY() / scale);

			// Try to Delete Edge first
			adminViewPageHandle.DeleteEdge(point2bDeleted);
			// Try to Delete Point Second
			adminViewPageHandle.deletePoint(point2bDeleted);

			/*
			 * switch (this.mode) { case "Create Points": // Check back in the
			 * controller list, delete it if the point exist. //
			 * checkIfPointIsDrawn(me.getX(), me.getY(), scale);
			 * adminViewPageHandle.deletePoint(point2bDeleted); case
			 * "Select Neighbors": //check if point is part of an edge
			 * adminViewPageHandle.DeleteEdge(point2bDeleted); }
			 */

		}

		revalidate();
		repaint();
	}

	/**
	 * Method addNeighbors.
	 * 
	 * @param p
	 *            Point2D
	 */
	public void addNeighbors(Point2D p) {
		Point2D result = adminViewPageHandle.checkPoint(p);
		if (result.getX() != 0) {
			this.newEnd = result;
			if (newStart.getX() != 0) {
				this.adminViewPageHandle.CreateEdge(newStart, newEnd);
				// this.adminViewPageHandle.pointNeighbors.add(this.newStart);
				// this.adminViewPageHandle.pointNeighbors.add(this.newEnd);
				this.newStart = new Point2D.Double(0, 0);
				this.newEnd = new Point2D.Double(0, 0);
			} else {
				System.out.println("Please select a start Point first!");
			}
		}
		revalidate();
		repaint();
	}

	/**
	 * Method addStart.
	 * 
	 * @param p
	 *            Point2D
	 */
	public void addStart(Point2D p) {
		Point2D result = adminViewPageHandle.checkPoint(p);
		if (result.getX() != 0) {
			this.newStart = result;
			System.out.println("New start" + this.newStart);
		}
	}

	/**
	 * Method checkIfPointIsDrawn.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param scale
	 *            double
	 */
	public void checkIfPointIsDrawn(int x, int y, double scale) {
		for (int i = 0; i < adminViewPageHandle.pointPositions.size(); i++) {
			Point2D p = adminViewPageHandle.pointPositions.get(i);
			if (isInCircle(p.getX(), p.getY(), circleWidthHeight / 2, x / scale, y / scale, scale)) {
				adminViewPageHandle.pointPositions.remove(i);
				revalidate();
				repaint();
			}
		}
	}

	/**
	 * Method isInCircle.
	 * 
	 * @param circleX
	 *            double
	 * @param circleY
	 *            double
	 * @param r
	 *            int
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @param scale
	 *            double
	 * @return boolean
	 */
	public boolean isInCircle(double circleX, double circleY, int r, double x, double y, double scale) {
		double d = Math.sqrt(Math.pow(circleX - x, 2) + Math.pow(circleY - y, 2));
		return d <= r * scale;
	}

	/**
	 * Method setMode.
	 * 
	 * @param m
	 *            String
	 */
	public void setMode(String m) {
		this.mode = m;
	}

	/**
	 * Method setBuilding.
	 * 
	 * @param b
	 *            String
	 */
	public void setBuilding(String b) {
		this.building = b;
	}

	/**
	 * Method setFloor.
	 * 
	 * @param f
	 *            String
	 */
	public void setFloor(String f) {
		this.floor = f;
	}

	public void clearAll() {
		adminViewPageHandle.pointNeighbors.clear();
		adminViewPageHandle.pointPositions.clear();
		revalidate();
		repaint();

	}

	public void createPathPoint(Point2D p) {
		int floorNum;
		String newBuilding;
		if (this.floor.isEmpty()) {
			floorNum = 1;

		} else {
			try {
				floorNum = Integer.parseInt(this.floor);
			} catch (NumberFormatException e) {
				floorNum = 0;
			}

		}
		if (this.building.isEmpty()) {
			newBuilding = "Null";

		} else {
			newBuilding = this.building;
		}

		adminViewPageHandle.CreatePoint(p, floorNum, 0, newBuilding, "Waypoint", "NULL");

	}
}
