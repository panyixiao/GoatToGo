package gtg_view_subsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class MapPage extends JPanel {
	private JTextField fromTextField, toTextField;
	private JPanel leftPanel, rightPanel;
	private JButton zoomInBtn, zoomOutBtn, getDirectionsBtn, fromClearBtn, toClearBtn;
	private ImageIcon zoomInBtnImage, zoomOutBtnImage, getDirectionsBtnImage, fromClearBtnImage, toClearBtnImage;
	private JLabel dropDownLabel, fromLabel, toLabel;
	private MapMapDisplayPanel mapMapDisplayPanel;
	private JScrollPane mapPanelHolder;
	private JLayeredPane layeredPane;
	private JComboBox comboBox;
	private double MAX_ZOOM_IN = 2.0;
	private double MAX_ZOOM_OUT = 1.0;
	private double currentZoomValue = 1.0;
	private double zoomFactor = 0.1;
	private SelectedPoints selectedPoints = new SelectedPoints();
	private MainView parent;
	/**
	 * Create the panel.
	 * @param mainView 
	 * @param mainView 
	 */
	public MapPage(MainView mainView) {
		this.parent = mainView;
		this.setBounds(0, 67, 1366, 661);
		this.setBackground(new Color(0xf0e6e6));
		this.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 5));
		this.setLayout(null);

		this.leftPanel = new JPanel();
		this.leftPanel.setBounds(5, 5, 950, 650);
		this.leftPanel.setLayout(null);
		this.leftPanel.setBackground(new Color(0xe0dede));
		this.add(leftPanel);

		this.layeredPane = new JLayeredPane();
		this.layeredPane.setBounds(0, 0, 950, 650);
		this.leftPanel.add(this.layeredPane);

		this.zoomInBtn = new JButton();
		zoomInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(currentZoomValue + zoomFactor <= MAX_ZOOM_IN){
					currentZoomValue = currentZoomValue + zoomFactor;
					mapMapDisplayPanel.setScale(currentZoomValue);
				}
			}
		});
		this.zoomInBtn.setBounds(895, 5, 50, 50);
		this.zoomInBtn.setContentAreaFilled(false);
		this.zoomInBtn.setBorder(null);
		this.zoomInBtnImage = new ImageIcon(ImageURLS.ZOOM_IN_BUTTON);
		this.zoomInBtn.setIcon(this.zoomInBtnImage);
		this.layeredPane.add(this.zoomInBtn, new Integer(1));

		this.zoomOutBtn = new JButton();
		zoomOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentZoomValue - zoomFactor >= MAX_ZOOM_OUT){
					currentZoomValue = currentZoomValue - zoomFactor;
					mapMapDisplayPanel.setScale(currentZoomValue);
				}
			}
		});
		this.zoomOutBtn.setBounds(895, 60, 51, 50);
		this.zoomOutBtn.setContentAreaFilled(false);
		this.zoomOutBtn.setBorder(null);
		this.zoomOutBtnImage = new ImageIcon(ImageURLS.ZOOM_OUT_BUTTON);
		this.zoomOutBtn.setIcon(this.zoomOutBtnImage);
		this.layeredPane.add(this.zoomOutBtn, new Integer(1));

		this.mapPanelHolder = new JScrollPane();
		this.mapPanelHolder.setBounds(0, 0, 950, 650);
		this.mapPanelHolder.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.mapPanelHolder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.layeredPane.add(this.mapPanelHolder, new Integer(0));

		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		this.mapPanelHolder.setViewportBorder(border);
		this.mapPanelHolder.setBorder(border);

		this.rightPanel = new JPanel();
		this.rightPanel.setBounds(955, 5, 405, 650);
		this.rightPanel.setLayout(null);
		this.rightPanel.setBackground(null);
		this.add(this.rightPanel);
		
		this.dropDownLabel = new JLabel(ViewStringLiterals.SELECT_BUILDING + ": ");
		this.dropDownLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.dropDownLabel.setBounds(50, 54, 253, 25);
		this.dropDownLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.dropDownLabel);

		String[] floorStrings = {"BH_Basement", "BH_FirstFloor", "BH_SecondFloor", "BH_ThirdFloor"};
		this.comboBox = new JComboBox(floorStrings);
		comboBox.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				 JComboBox cb = (JComboBox)ae.getSource();
			     String mapName = (String)cb.getSelectedItem();
			     changeMap(mapName);
			}
		});
		this.comboBox.setBackground(null);
		this.comboBox.setSelectedIndex(0);
		this.comboBox.setBorder(BorderFactory.createLineBorder(new Color(0x5b1010),3));
		this.comboBox.setBounds(60, 95, 307, 53);
		this.rightPanel.add(this.comboBox);
		
		this.fromLabel = new JLabel(ViewStringLiterals.FROM + " :");
		this.fromLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.fromLabel.setBounds(50, 222, 98, 25);
		this.fromLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.fromLabel);

		this.toLabel = new JLabel(ViewStringLiterals.TO + " :");
		this.toLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.toLabel.setBounds(50, 342, 57, 25);
		this.toLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.toLabel);

		this.getDirectionsBtn = new JButton();
		getDirectionsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.getPathResult();
				/*if(selectedPoints.arePointsSelected() == true){
					parent.getPathResult();
				} else {
					if(selectedPoints.isStartSelected() == false && selectedPoints.isEndSelected() == true) {
						JOptionPane.showMessageDialog(null, ViewStringLiterals.FROM_POINT_NOT_SET, "INVALID", JOptionPane.ERROR_MESSAGE);
					}
					else if(selectedPoints.isStartSelected() == true && selectedPoints.isEndSelected() == false) {
						JOptionPane.showMessageDialog(null, ViewStringLiterals.TO_POINT_NOT_SET, "INVALID", JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, ViewStringLiterals.POINTS_NOT_SET, "INVALID", JOptionPane.ERROR_MESSAGE);
					}
				}*/
			}
		});
		this.getDirectionsBtn.setContentAreaFilled(false);
		this.getDirectionsBtn.setBorder(null);
		this.getDirectionsBtn.setBounds(128, 558, 173, 42);
		this.getDirectionsBtnImage = new ImageIcon(ImageURLS.GET_DIRECTIONS_BUTTON);
		this.getDirectionsBtn.setIcon(this.getDirectionsBtnImage);
		this.rightPanel.add(this.getDirectionsBtn);
		
		this.fromTextField = new JTextField();
		this.fromTextField.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.fromTextField.setEditable(false);
		this.fromTextField.setBounds(60, 263, 292, 47);
		this.fromTextField.setColumns(10);
		this.fromTextField.setForeground(new Color(0x5b1010));
		this.fromTextField.setBorder(BorderFactory.createLineBorder(new Color(0x5b1010),3));
		this.rightPanel.add(this.fromTextField);

		this.fromClearBtn = new JButton();
		fromClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mapMapDisplayPanel != null){
					if(selectedPoints.isStartSelected() == true){
						parent.deleteSelectedPoint(ViewStringLiterals.FROM);
					}
					//mapMapDisplayPanel.deletePoint(ViewStringLiterals.FROM);
					//fromTextField.setText("");
				}
			}
		});
		this.fromClearBtn.setBounds(364, 275, 20, 20);
		this.fromClearBtn.setContentAreaFilled(false);
		this.fromClearBtn.setBorder(null);
		this.fromClearBtnImage = new ImageIcon(ImageURLS.CLEAR_BUTTON);
		this.fromClearBtn.setIcon(this.fromClearBtnImage);
		this.rightPanel.add(this.fromClearBtn);

		this.toTextField = new JTextField();
		this.toTextField.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.toTextField.setForeground(new Color(0x5b1010));
		this.toTextField.setEditable(false);
		this.toTextField.setColumns(10);
		this.toTextField.setBounds(60, 383, 292, 47);
		this.toTextField.setBorder(BorderFactory.createLineBorder(new Color(0x5b1010),3));
		this.rightPanel.add(this.toTextField);

		this.toClearBtn = new JButton();
		toClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mapMapDisplayPanel != null){
					if(selectedPoints.isEndSelected() == true){
						parent.deleteSelectedPoint(ViewStringLiterals.TO);
					}
					//mapMapDisplayPanel.deletePoint(ViewStringLiterals.TO);
					//toTextField.setText("");
				}
			}
		});
		this.toClearBtn.setBounds(364, 395, 20, 20);
		this.toClearBtn.setContentAreaFilled(false);
		this.toClearBtn.setBorder(null);
		this.toClearBtnImage = new ImageIcon(ImageURLS.CLEAR_BUTTON);
		this.toClearBtn.setIcon(this.toClearBtnImage);
		this.rightPanel.add(this.toClearBtn);
	}
	
	private void changeMap(String mapName){
		this.mapMapDisplayPanel = null;
		String mapurl = "";
		switch(mapName){
		case "BH_Basement":
			mapurl = ImageURLS.BH_BASEMENT;
			break;
			
		case "BH_FirstFloor":
			mapurl = ImageURLS.BH_FIRST_FLOOR;
			break;
			
		case "BH_SecondFloor":
			mapurl = ImageURLS.BH_SECOND_FLOOR;
			break;
			
		case "BH_ThirdFloor":
			mapurl = ImageURLS.BH_THIRD_FLOOR;
			break;
		}
		this.mapMapDisplayPanel = new MapMapDisplayPanel(this,this.mapPanelHolder, mapName, mapurl, selectedPoints);
		this.mapPanelHolder.setViewportView(mapMapDisplayPanel);
		this.currentZoomValue = 1.0;
	}

	public void displayPointInTextfield(String locationType, double x, double y) {
		switch(locationType){
		case ViewStringLiterals.FROM:
			fromTextField.setText("X = " + Math.round(x * 100) / 100 + ", " + "Y = " + Math.round(y * 100) / 100);
			break;
			
		case ViewStringLiterals.TO:
			toTextField.setText("X = " + Math.round(x * 100) / 100 + ", " + "Y = " + Math.round(y * 100) / 100);
			break;
		}
	}

	//public void sentPointToModel(Point startEndPoint, String selectedPointType, String mapName) {
	//	parent.sentPointToModel(startEndPoint, selectedPointType, mapName);
	//}
	
	public Point  sentPointToModel(Point startEndPoint, String selectedPointType, String mapName) {
		Point pntOnGraph = startEndPoint;
		pntOnGraph = parent.sentPointToModel(startEndPoint, selectedPointType, mapName);
		return pntOnGraph;
	}

	public void setPoint() {
		this.mapMapDisplayPanel.displayPoint();
		
	}
	
	public void deletePoint(String selectedPointType){
		if(selectedPointType == ViewStringLiterals.FROM){
			mapMapDisplayPanel.deletePoint(ViewStringLiterals.FROM);
			fromTextField.setText("");
		}
		
		if(selectedPointType == ViewStringLiterals.TO){
			mapMapDisplayPanel.deletePoint(ViewStringLiterals.TO);
			toTextField.setText("");
		}
	}
}
