package gtg_view_subsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

/**
 */
public class MapPage extends JPanel {
	private JTextField fromTextField, toTextField;
	private JPanel leftPanel, rightPanel, slidePanel;
	private JButton zoomInBtn, zoomOutBtn, getDirectionsBtn, fromClearBtn, toClearBtn, showHideLocationsBtn;
	private JButton cafeBtn, classroomBtn, elevatorBtn, mensRestroomBtn, womensRestroomBtn, officeBtn, vendingBtn, parkingLotBtn, doneBtn;
	private ImageIcon zoomInBtnImage, zoomOutBtnImage, getDirectionsBtnImage, fromClearBtnImage, toClearBtnImage;
	private ImageIcon cafeBtnImage, classroomBtnImage, elevatorBtnImage, mensRestroomBtnImage, womensRestroomBtnImage, officeBtnImage, vendingBtnImage, parkingLotBtnImage;
	private JLabel dropDownLabel, fromLabel, toLabel, noImageLabel;
	private MapMapDisplayPanel mapMapDisplayPanel;
	private JScrollPane mapPanelHolder, filterScrollPane;
	private JLayeredPane layeredPane, rightlayeredPane;
	private JComboBox comboBox;
	private double MAX_ZOOM_IN = 2.0;
	private double MAX_ZOOM_OUT = 1.0;
	private double currentZoomValue = 1.0;
	private double zoomFactor = 0.1;
	private SelectedPoints selectedPoints = new SelectedPoints();
	private Animate animate = new Animate();
	private MainView parent;
	private Boolean animationStarted = false;
	private Boolean slidePanelIsOpen = false;
	private String currentDisplayedMap = "";
	private String mapURL = "";
	private Boolean isCampusMap = false;
	/**
	 * Create the panel.
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

		this.noImageLabel = new JLabel(ViewStringLiterals.MAP_NOT_AVAILABLE);
		this.noImageLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.noImageLabel.setForeground(new Color(0x5b1010));
		this.noImageLabel.setBounds(350, 310, 250, 30);
		this.noImageLabel.setVisible(false);
		this.layeredPane.add(this.noImageLabel, new Integer(1));

		this.zoomInBtn = new JButton();
		zoomInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mapMapDisplayPanel != null){
					if(currentZoomValue + zoomFactor <= MAX_ZOOM_IN){
						currentZoomValue = currentZoomValue + zoomFactor;
						mapMapDisplayPanel.setScale(currentZoomValue);
					}
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
				if(mapMapDisplayPanel != null){
					if(currentZoomValue - zoomFactor >= MAX_ZOOM_OUT){
						currentZoomValue = currentZoomValue - zoomFactor;
						mapMapDisplayPanel.setScale(currentZoomValue);
					}
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

		this.rightlayeredPane = new JLayeredPane();
		this.rightlayeredPane.setBounds(955, 5, 405, 650);
		this.add(this.rightlayeredPane);

		this.slidePanel = new JPanel();
		this.slidePanel.setBounds(359, 0, 405, 650);
		this.slidePanel.setBackground(new Color(0xf0e6e6));
		this.slidePanel.setLayout(null);
		this.rightlayeredPane.add(this.slidePanel, new Integer(1));

		this.createSlidePanelUI();

		this.rightPanel = new JPanel();
		this.rightPanel.setBounds(0, 0, 405, 650);
		this.rightPanel.setLayout(null);
		this.rightPanel.setBackground(null);
		this.rightlayeredPane.add(this.rightPanel, new Integer(0));
		
		this.dropDownLabel = new JLabel(ViewStringLiterals.SELECT_BUILDING + ": ");
		this.dropDownLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.dropDownLabel.setBounds(15, 54, 253, 25);
		this.dropDownLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.dropDownLabel);

		this.comboBox = new JComboBox();
		comboBox.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				 JComboBox cb = (JComboBox)ae.getSource();
			     updateMapList((String)cb.getSelectedItem());
			}
		});
		this.comboBox.setBackground(null);
		this.comboBox.setBorder(BorderFactory.createLineBorder(new Color(0x5b1010),3));
		this.comboBox.setBounds(15, 96, 271, 53);
		this.rightPanel.add(this.comboBox);
		
		this.fromLabel = new JLabel(ViewStringLiterals.FROM + " :");
		this.fromLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.fromLabel.setBounds(25, 222, 98, 25);
		this.fromLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.fromLabel);

		this.toLabel = new JLabel(ViewStringLiterals.TO + " :");
		this.toLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.toLabel.setBounds(25, 343, 57, 25);
		this.toLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.toLabel);

		this.getDirectionsBtn = new JButton();
		getDirectionsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//parent.getPathResult();
				if(selectedPoints.arePointsSelected() == true){
					parent.getPathResult(currentDisplayedMap);
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
				}
			}
		});
		this.getDirectionsBtn.setContentAreaFilled(false);
		this.getDirectionsBtn.setBorder(null);
		this.getDirectionsBtn.setBounds(122, 495, 173, 42);
		this.getDirectionsBtnImage = new ImageIcon(ImageURLS.GET_DIRECTIONS_BUTTON);
		this.getDirectionsBtn.setIcon(this.getDirectionsBtnImage);
		this.rightPanel.add(this.getDirectionsBtn);
		
		this.showHideLocationsBtn = new JButton(ViewStringLiterals.SHOW_LOCATIONS);
		this.showHideLocationsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JButton)e.getSource()).getText().equals(ViewStringLiterals.SHOW_LOCATIONS)){
					if(mapMapDisplayPanel != null){
						if(mapMapDisplayPanel.totalGraphPoints() != 0){
							mapMapDisplayPanel.showLocations();
							((JButton)e.getSource()).setText(ViewStringLiterals.HIDE_LOCATIONS);
						} else {
							JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), ViewStringLiterals.NO_LOCATIONS_CREATED, "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					((JButton)e.getSource()).setText(ViewStringLiterals.SHOW_LOCATIONS);
					mapMapDisplayPanel.hideLocations();
				}
			}
		});
		this.showHideLocationsBtn.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 3));
		this.showHideLocationsBtn.setBackground(null);
		this.showHideLocationsBtn.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.showHideLocationsBtn.setForeground(new Color(204, 0, 0));
		this.showHideLocationsBtn.setFocusPainted(false);
		this.showHideLocationsBtn.setBounds(105, 550, 210, 42);
		this.rightPanel.add(this.showHideLocationsBtn);

		this.fromTextField = new JTextField();
		this.fromTextField.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.fromTextField.setEditable(false);
		this.fromTextField.setBounds(25, 263, 261, 47);
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
		this.fromClearBtn.setBounds(295, 275, 20, 20);
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
		this.toTextField.setBounds(25, 384, 261, 47);
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
		this.toClearBtn.setBounds(295, 395, 20, 20);
		this.toClearBtn.setContentAreaFilled(false);
		this.toClearBtn.setBorder(null);
		this.toClearBtnImage = new ImageIcon(ImageURLS.CLEAR_BUTTON);
		this.toClearBtn.setIcon(this.toClearBtnImage);
		this.rightPanel.add(this.toClearBtn);
	}
	
	/**
	 * Method createSlidePanelUI.
	 * This method creates the slide panel UI component for the filters.
	 */
	private void createSlidePanelUI() {
		this.cafeBtn = new JButton();
		cafeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showFilterPanel();
			}
		});
		this.cafeBtn.setBounds(0, 10, 50, 50);
		this.cafeBtn.setContentAreaFilled(false);
		this.cafeBtn.setBorder(null);
		this.cafeBtnImage = new ImageIcon(ImageURLS.CAFE_BUTTON);
		this.cafeBtn.setIcon(this.cafeBtnImage);
		this.slidePanel.add(this.cafeBtn);
		
		this.classroomBtn = new JButton();
		classroomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showFilterPanel();
			}
		});
		this.classroomBtn.setBounds(0, 80, 50, 50);
		this.classroomBtn.setContentAreaFilled(false);
		this.classroomBtn.setBorder(null);
		this.classroomBtnImage = new ImageIcon(ImageURLS.CLASSROOM_BUTTON);
		this.classroomBtn.setIcon(this.classroomBtnImage);
		this.slidePanel.add(this.classroomBtn);
		
		this.elevatorBtn = new JButton();
		elevatorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterPanel();
			}
		});
		this.elevatorBtn.setBounds(0, 150, 50, 50);
		this.elevatorBtn.setContentAreaFilled(false);
		this.elevatorBtn.setBorder(null);
		this.elevatorBtnImage = new ImageIcon(ImageURLS.ELEVATOR_BUTTON);
		this.elevatorBtn.setIcon(this.elevatorBtnImage);
		this.slidePanel.add(this.elevatorBtn);
		
		this.mensRestroomBtn = new JButton();
		mensRestroomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterPanel();
			}
		});
		this.mensRestroomBtn.setBounds(0, 220, 50, 50);
		this.mensRestroomBtn.setContentAreaFilled(false);
		this.mensRestroomBtn.setBorder(null);
		this.mensRestroomBtnImage = new ImageIcon(ImageURLS.MENS_RESTROOM_BUTTON);
		this.mensRestroomBtn.setIcon(this.mensRestroomBtnImage);
		this.slidePanel.add(this.mensRestroomBtn);
		
		this.womensRestroomBtn = new JButton();
		womensRestroomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterPanel();
			}
		});
		this.womensRestroomBtn.setBounds(0, 290, 50, 50);
		this.womensRestroomBtn.setContentAreaFilled(false);
		this.womensRestroomBtn.setBorder(null);
		this.womensRestroomBtnImage = new ImageIcon(ImageURLS.WOMENS_RESTROOM_BUTTON);
		this.womensRestroomBtn.setIcon(this.womensRestroomBtnImage);
		this.slidePanel.add(this.womensRestroomBtn);
		
		this.officeBtn = new JButton();
		officeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterPanel();
			}
		});
		this.officeBtn.setBounds(0, 360, 50, 50);
		this.officeBtn.setContentAreaFilled(false);
		this.officeBtn.setBorder(null);
		this.officeBtnImage = new ImageIcon(ImageURLS.OFFICE_BUTTON);
		this.officeBtn.setIcon(this.officeBtnImage);
		this.slidePanel.add(this.officeBtn);
		
		this.vendingBtn = new JButton();
		vendingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterPanel();
			}
		});
		this.vendingBtn.setBounds(0, 430, 50, 50);
		this.vendingBtn.setContentAreaFilled(false);
		this.vendingBtn.setBorder(null);
		this.vendingBtnImage = new ImageIcon(ImageURLS.VENDING_BUTTON);
		this.vendingBtn.setIcon(this.vendingBtnImage);
		this.slidePanel.add(this.vendingBtn);
		
		this.parkingLotBtn = new JButton();
		parkingLotBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterPanel();
			}
		});
		this.parkingLotBtn.setBounds(0, 500, 50, 50);
		this.parkingLotBtn.setContentAreaFilled(false);
		this.parkingLotBtn.setBorder(null);
		this.parkingLotBtnImage = new ImageIcon(ImageURLS.PARKING_LOT_BUTTON);
		this.parkingLotBtn.setIcon(this.parkingLotBtnImage);
		this.slidePanel.add(this.parkingLotBtn);
		
		this.doneBtn = new JButton(ViewStringLiterals.DONE);
		this.doneBtn.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 3));
		this.doneBtn.setBackground(Color.WHITE);
		this.doneBtn.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.doneBtn.setForeground(new Color(204, 0, 0));
		this.doneBtn.setFocusPainted(false);
		this.doneBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				hideFilterPanel();
			}
		});
		this.doneBtn.setBounds(160, 580, 100, 40);
		this.slidePanel.add(this.doneBtn);
		
		this.filterScrollPane = new JScrollPane();
		this.filterScrollPane.setBounds(70, 10, 310, 450);
		this.filterScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 2));
		this.slidePanel.add(this.filterScrollPane);
	}

	/**
	 * Method changeMap.
	 * @param mapName String
	 */
	public void changeMapImage(String mapURL){
		this.mapURL = mapURL;
		this.showHideLocationsBtn.setText(ViewStringLiterals.SHOW_LOCATIONS);
		if(mapURL.equals("")){
			this.noImageLabel.setVisible(true);
			this.mapPanelHolder.setVisible(false);
		} else {
			this.noImageLabel.setVisible(false);
			this.mapMapDisplayPanel = new MapMapDisplayPanel(this,this.mapPanelHolder, this.currentDisplayedMap, mapURL, selectedPoints);
			this.mapPanelHolder.setViewportView(mapMapDisplayPanel);
			this.mapPanelHolder.setVisible(true);
			this.currentZoomValue = 1.0;
		}
	}

	/**
	 * Method displayDropDownList.
	 * @param mapList ArrayList<String>
	 * This method updates the mapList when a building is selected in the campus map.
	 */
	public void displayDropDownList(ArrayList<String> mapList){
		this.currentDisplayedMap = mapList.get(0);
		
		int pos = this.currentDisplayedMap.toLowerCase().indexOf(("campus").toLowerCase());
		if(pos != -1){
			this.isCampusMap = true;
			this.dropDownLabel.setText(ViewStringLiterals.SELECT_BUILDING + ": ");
		} else {
			this.isCampusMap = false;
			this.dropDownLabel.setText(ViewStringLiterals.SELECT_FLOOR + ": ");
		}

		DefaultComboBoxModel model = new DefaultComboBoxModel(mapList.toArray());
		this.comboBox.setModel(model);
	}
	/**
	 * Method displayPointInTextfield.
	 * @param locationType String
	 * @param x double
	 * @param y double
	 */
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
	
	/**
	 * Method sentPointToModel.
	 * @param startEndPoint Point
	 * @param selectedPointType String
	 * @param mapName String
	 * @return Point
	 */
	public Point  sentPointToModel(Point startEndPoint, String selectedPointType, String mapName) {
		Point pntOnGraph = startEndPoint;
		pntOnGraph = parent.sentPointToModel(startEndPoint, selectedPointType, mapName);
		return pntOnGraph;
	}

	public void setPoint() {
		this.mapMapDisplayPanel.displayPoint();
	}
	
	/**
	 * Method deletePoint.
	 * @param selectedPointType String
	 */
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

	/**
	 * Method reset.
	 * Resets the value of the attributes when the map page is displayed again.
	 */
	public void reset() {
		// TODO Auto-generated method stub
		fromTextField.setText("");
		toTextField.setText("");
		this.selectedPoints.resetEnd();
		this.selectedPoints.resetStart();
		this.animationStarted = false;
		this.slidePanelIsOpen = false;
		this.currentDisplayedMap = "";
		this.mapURL = "";
		this.isCampusMap = false;
		this.slidePanel.setLocation(359, 0);
		this.showHideLocationsBtn.setText(ViewStringLiterals.SHOW_LOCATIONS);
		/*if(this.mapMapDisplayPanel != null){
			this.mapMapDisplayPanel.displayPoint();
		}*/
	}
	
	/**
	 * Method animationStarted.
	 * Method called from the animate class, so that when the slide animation is in progress
	 * no buttons on the UI are enabled.
	 */
	public void animationStarted(){
		this.animationStarted = true;
	}
	
	/**
	 * Method animationEnd.
	 * Method called from the animate class, so that when the slide animation is over
	 * buttons on the UI are enabled.
	 */
	public void animationEnd(){
		this.animationStarted = false;
	}
	
	/**
	 * Method showFilterPanel.
	 * Executes the slide animation of the filter panel to display it.
	 */
	public void showFilterPanel(){
		if(!this.mapURL.equals("")){
			if(this.animationStarted == false && slidePanelIsOpen == false){
				animate.setAnimationPanel(this, slidePanel, 355, 10);
				animate.startAnimationLeft();
				slidePanelIsOpen = true;
			}
		}
	}
	
	/**
	 * Method hideFilterPanel.
	 * Executes the slide animation of the filter panel to hide it.
	 */
	public void hideFilterPanel(){
		if(animationStarted == false){
			animate.setAnimationPanel(this, slidePanel, 10, 355);
			animate.startAnimationRight();
			slidePanelIsOpen = false;
		}
	}
	
	/**
	 * Method updateMapList.
	 * @param String mapName
	 * Updates the dropdown list values depending upon the type of the selected map.
	 * If mapName = campus, display list of buildings + campus map name
	 * If mapName = building, display list of floors + campus map name
	 */
	public void updateMapList(String mapName){
		if(this.isCampusMap == true){
			this.parent.getListOfFloors(mapName);
		} else {
			int pos = mapName.toLowerCase().indexOf(("campus").toLowerCase());
			if(pos != -1){
				this.parent.getListOfBuildings();
			} else {
				this.currentDisplayedMap = mapName;
				this.parent.getMapURL(mapName);
			}
		}
	}

	public void addGraphPoints(ArrayList<Point> graphPoints) {
		if(this.mapMapDisplayPanel != null){
			this.mapMapDisplayPanel.addGraphPoints(graphPoints);
		}
	}
}
