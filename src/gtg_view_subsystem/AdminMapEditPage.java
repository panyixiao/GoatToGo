package gtg_view_subsystem;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class AdminMapEditPage extends JPanel {
	private JPanel leftPanel, rightPanel;
	private JButton zoomInBtn, zoomOutBtn, clearAllBtn, saveBtn;
	private JRadioButton pointBtn, pathBtn, nbrBtn;
	private ButtonGroup modeBtns;
	private JTextField buiding, floor;
	private JLabel buildingLabel, floorLabel;

	private ImageIcon zoomInBtnImage, zoomOutBtnImage, clearAllBtnImage, saveBtnImage;
	private JLabel dropDownLabel;
	private AdminMapDisplayPanel adminMapDisplayPanel;
	private JScrollPane mapPanelHolder;
	private JLayeredPane layeredPane;
	private JComboBox comboBox;
	private ActionListener changeMode; 
	private double MAX_ZOOM_IN = 2.0;
	private double MAX_ZOOM_OUT = 1.0;
	private double currentZoomValue = 1.0;
	private double zoomFactor = 0.1;
	private MainView parent;
	private SelectedPoints selectedPoints = new SelectedPoints();
	
	/**
	 * Create the panel.
	 * @param mainView 
	 */
	public AdminMapEditPage(MainView mainView) {
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
		this.zoomInBtn.setBounds(895, 5, 50, 50);
		this.zoomInBtn.setContentAreaFilled(false);
		this.zoomInBtn.setBorder(null);
		this.zoomInBtnImage = new ImageIcon(ImageURLS.ZOOM_IN_BUTTON);
		this.zoomInBtn.setIcon(this.zoomInBtnImage);
		this.zoomInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentZoomValue + zoomFactor <= MAX_ZOOM_IN){
					currentZoomValue = currentZoomValue + zoomFactor;
					adminMapDisplayPanel.setScale(currentZoomValue);
				}
			}
		});
		this.layeredPane.add(this.zoomInBtn, new Integer(1));

		this.zoomOutBtn = new JButton();
		this.zoomOutBtn.setBounds(895, 60, 51, 50);
		this.zoomOutBtn.setContentAreaFilled(false);
		this.zoomOutBtn.setBorder(null);
		this.zoomOutBtnImage = new ImageIcon(ImageURLS.ZOOM_OUT_BUTTON);
		this.zoomOutBtn.setIcon(this.zoomOutBtnImage);
		this.zoomOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(currentZoomValue - zoomFactor >= MAX_ZOOM_OUT){
						currentZoomValue = currentZoomValue - zoomFactor;
						adminMapDisplayPanel.setScale(currentZoomValue);
					}
			}
		});
		this.layeredPane.add(this.zoomOutBtn, new Integer(1));
		
		this.mapPanelHolder = new JScrollPane();
		this.mapPanelHolder.setBounds(0, 0, 950, 650);
		this.mapPanelHolder.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.mapPanelHolder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.layeredPane.add(this.mapPanelHolder, new Integer(0));
		this.adminMapDisplayPanel = new AdminMapDisplayPanel(this.mapPanelHolder, ImageURLS.BH_BASEMENT);
		this.mapPanelHolder.setViewportView(adminMapDisplayPanel);

		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		this.mapPanelHolder.setViewportBorder(border);
		this.mapPanelHolder.setBorder(border);

		this.rightPanel = new JPanel();
		this.rightPanel.setBounds(955, 5, 405, 650);
		this.rightPanel.setLayout(null);
		this.rightPanel.setBackground(null);
		this.add(this.rightPanel);

		this.dropDownLabel = new JLabel(ViewStringLiterals.SELECT_MAP + " :");
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
		
		
		this.buildingLabel = new JLabel("Building :");
		this.buildingLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.buildingLabel.setBounds(50, 165, 150, 50);
		this.buildingLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.buildingLabel);
		
		this.buiding = new JTextField();
		this.buiding.setBounds(50, 210, 307, 53);
		this.buiding.setBackground(null);
		this.buiding.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.buiding.setBorder(BorderFactory.createLineBorder(new Color(0x5b1010),3));
		this.buiding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JTextField b = (JTextField)ae.getSource();
			     String building = (String)b.getText();
			     adminMapDisplayPanel.setBuilding(building);
			}
		});
		this.rightPanel.add(buiding);
		
		
		this.floorLabel = new JLabel("Floor :");
		this.floorLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.floorLabel.setBounds(50, 265, 150, 50);
		this.floorLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.floorLabel);
		
		this.floor = new JTextField();
		this.floor.setBounds(50, 310, 307, 53);
		this.floor.setBackground(null);
		this.floor.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.floor.setBorder(BorderFactory.createLineBorder(new Color(0x5b1010),3));
		this.floor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JTextField f = (JTextField)ae.getSource();
			     String floor = (String)f.getText();
			     adminMapDisplayPanel.setFloor(floor);
			}
		});
		this.rightPanel.add(floor);
	
		
		this.changeMode = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JRadioButton cb = (JRadioButton)ae.getSource();
			     String mode = cb.getText();
			     setMode(mode);
			}
		};
			
		this.pointBtn =new JRadioButton();
		this.pointBtn.setText("Create Points");
		this.pointBtn.setBounds(55, 425, 253, 25);
		this.pointBtn.setFont(new Font("Meiryo", Font.BOLD, 24));
		this.pointBtn.setBackground(null);
		this.pointBtn.addActionListener(this.changeMode);
	
		this.pathBtn =new JRadioButton();
		this.pathBtn.setText("Create Path");
		this.pathBtn.setBounds(55, 475, 253, 25);
		this.pathBtn.setFont(new Font("Meiryo", Font.BOLD, 24));
		this.pathBtn.setBackground(null);
		this.pathBtn.addActionListener(changeMode);
		
		this.nbrBtn =new JRadioButton();
		this.nbrBtn.setText("Select Neighbors");
		this.nbrBtn.setBounds(55, 525, 253, 25);
		this.nbrBtn.setFont(new Font("Meiryo", Font.BOLD, 24));
		this.nbrBtn.setBackground(null);
		this.nbrBtn.addActionListener(changeMode);
		this.modeBtns = new ButtonGroup();
		this.modeBtns.add(pointBtn);
		this.modeBtns.add(pathBtn);
		this.modeBtns.add(nbrBtn);
		
		this.rightPanel.add(this.pointBtn);
		this.rightPanel.add(this.pathBtn);
		this.rightPanel.add(this.nbrBtn);

		this.clearAllBtn = new JButton();
		this.clearAllBtn.setContentAreaFilled(false);
		this.clearAllBtn.setBorder(null);
		this.clearAllBtn.setBounds(109, 569, 80, 42);
		this.clearAllBtnImage = new ImageIcon(ImageURLS.CLEAR_ALL_BUTTON);
		this.clearAllBtn.setIcon(this.clearAllBtnImage);
		this.rightPanel.add(this.clearAllBtn);

		this.saveBtn = new JButton();
		this.saveBtn.setBounds(234, 569, 80, 42);
		this.saveBtn.setContentAreaFilled(false);
		this.saveBtn.setBorder(null);
		this.saveBtnImage = new ImageIcon(ImageURLS.SAVE_BUTTON);
		this.saveBtn.setIcon(this.saveBtnImage);
		this.rightPanel.add(this.saveBtn);
		
		System.out.println(this.modeBtns.getSelection());
	}
	
	private void setMode(String mode){
		this.adminMapDisplayPanel.setMode(mode);
	}
	private void changeMap(String mapName){
	//	this.adminMapDisplayPanel = null;
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
//		this.adminMapDisplayPanel = new AdminMapDisplayPanel(this,this.mapPanelHolder, mapName, mapurl, selectedPoints);
//		this.mapPanelHolder.setViewportView(adminMapDisplayPanel);
		this.currentZoomValue = 1.0;

	}

}
