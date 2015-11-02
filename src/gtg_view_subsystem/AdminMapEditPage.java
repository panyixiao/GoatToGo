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
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;


public class AdminMapEditPage extends JPanel {
	private JPanel leftPanel, rightPanel;
	private JButton zoomInBtn, zoomOutBtn, clearAllBtn, saveBtn;
	private ImageIcon zoomInBtnImage, zoomOutBtnImage, clearAllBtnImage, saveBtnImage;
	private JLabel dropDownLabel;
	private AdminMapDisplayPanel adminMapDisplayPanel;
	private JScrollPane mapPanelHolder;
	private JLayeredPane layeredPane;
	private double MAX_ZOOM_IN = 2.0;
	private double MAX_ZOOM_OUT = 1.0;
	private double currentZoomValue = 1.0;
	private double zoomFactor = 0.1;
	private MainView parent;
	
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
		this.dropDownLabel.setBounds(15, 16, 253, 25);
		this.dropDownLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.dropDownLabel);

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
	}

}
