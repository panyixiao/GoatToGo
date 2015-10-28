package gtg_view_subsystem;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResultPage extends JPanel {
	private JTextField fromTextField, toTextField, currentMapName, totalMaps;
	private JPanel leftPanel, rightPanel;
	private JButton zoomInBtn, zoomOutBtn, newSearchBtn, nextBtn ,previousBtn;
	private ImageIcon zoomInBtnImage, zoomOutBtnImage, newSearchBtnImage, nextBtnImage, previousBtnImage;
	private JLabel fromLabel, toLabel;
	private MainView parent;
	/**
	 * Create the panel.
	 * @param mainView 
	 */
	public ResultPage(MainView mainView) {
		this.parent = mainView;
		this.setBounds(0, 67, 1057, 534);
		this.setBackground(new Color(0xf0e6e6));
		this.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 5));
		this.setLayout(null);

		this.leftPanel = new JPanel();
		this.leftPanel.setBounds(5, 5, 671, 524);
		this.leftPanel.setLayout(null);
		this.leftPanel.setBackground(new Color(0xe0dede));
		this.add(this.leftPanel);

		this.zoomInBtn = new JButton();
		this.zoomInBtn.setBounds(615, 5, 50, 50);
		this.zoomInBtn.setContentAreaFilled(false);
		this.zoomInBtn.setBorder(null);
		this.zoomInBtnImage = new ImageIcon(ImageURLS.ZOOM_IN_BUTTON);
		this.zoomInBtn.setIcon(this.zoomInBtnImage);
		this.leftPanel.add(this.zoomInBtn);

		this.zoomOutBtn = new JButton();
		this.zoomOutBtn.setBounds(615, 60, 51, 50);
		this.zoomOutBtn.setContentAreaFilled(false);
		this.zoomOutBtn.setBorder(null);
		this.zoomOutBtnImage = new ImageIcon(ImageURLS.ZOOM_OUT_BUTTON);
		this.zoomOutBtn.setIcon(this.zoomOutBtnImage);
		this.leftPanel.add(this.zoomOutBtn);

		this.rightPanel = new JPanel();
		this.rightPanel.setBounds(677, 5, 375, 524);
		this.rightPanel.setLayout(null);
		this.rightPanel.setBackground(null);
		this.add(this.rightPanel);

		this.fromLabel = new JLabel(ViewStringLiterals.FROM + " :");
		this.fromLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.fromLabel.setBounds(15, 105, 98, 25);
		this.fromLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.fromLabel);

		this.toLabel = new JLabel(ViewStringLiterals.TO + " :");
		this.toLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.toLabel.setBounds(45, 342, 57, 25);
		this.toLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.toLabel);

		this.newSearchBtn = new JButton();
		this.newSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showMapPage();
			}
		});
		this.newSearchBtn.setBounds(110, 471, 173, 42);
		this.newSearchBtn.setContentAreaFilled(false);
		this.newSearchBtn.setBorder(null);
		this.newSearchBtnImage = new ImageIcon(ImageURLS.NEW_SEARCH_BUTTON);
		this.newSearchBtn.setIcon(this.newSearchBtnImage);
		this.rightPanel.add(this.newSearchBtn);
		
		this.fromTextField = new JTextField();
		this.fromTextField.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.fromTextField.setEditable(false);
		this.fromTextField.setBounds(106, 94, 254, 47);
		this.fromTextField.setColumns(10);
		this.fromTextField.setForeground(new Color(0x5b1010));
		this.fromTextField.setBorder(null);
		this.rightPanel.add(this.fromTextField);

		this.nextBtn = new JButton();
		this.nextBtn.setBounds(304, 181, 56, 90);
		this.nextBtn.setContentAreaFilled(false);
		this.nextBtn.setBorder(null);
		this.nextBtnImage = new ImageIcon(ImageURLS.NEXT_BUTTON);
		this.nextBtn.setIcon(this.nextBtnImage);
		this.rightPanel.add(this.nextBtn);

		this.toTextField = new JTextField();
		this.toTextField.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.toTextField.setEditable(false);
		this.toTextField.setColumns(10);
		this.toTextField.setBounds(105, 331, 255, 47);
		this.toTextField.setForeground(new Color(0x5b1010));
		this.toTextField.setBorder(null);
		this.rightPanel.add(this.toTextField);

		this.previousBtn = new JButton();
		this.previousBtn.setBounds(15, 181, 58, 90);
		this.previousBtn.setContentAreaFilled(false);
		this.previousBtn.setBorder(null);
		this.previousBtnImage = new ImageIcon(ImageURLS.PREVIOUS_BUTTON);
		this.previousBtn.setIcon(this.previousBtnImage);
		this.rightPanel.add(this.previousBtn);
		
		this.currentMapName = new JTextField();
		this.currentMapName.setEditable(false);
		this.currentMapName.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.currentMapName.setBounds(88, 203, 204, 37);
		this.currentMapName.setForeground(new Color(0x5b1010));
		this.currentMapName.setBorder(null);
		this.rightPanel.add(this.currentMapName);
		this.currentMapName.setColumns(10);
		
		this.totalMaps = new JTextField();
		this.totalMaps.setEditable(false);
		this.totalMaps.setFont(new Font("Meiryo", Font.PLAIN, 16));
		this.totalMaps.setForeground(new Color(0x5b1010));
		this.totalMaps.setBounds(145, 245, 98, 26);
		this.totalMaps.setBorder(null);
		this.rightPanel.add(this.totalMaps);
		this.totalMaps.setColumns(10);
	}

}
