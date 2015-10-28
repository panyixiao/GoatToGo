package gtg_view_subsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminMapEditPage extends JPanel {
	private JPanel leftPanel, rightPanel;
	private JButton zoomInBtn, zoomOutBtn, clearAllBtn, saveBtn;
	private ImageIcon zoomInBtnImage, zoomOutBtnImage, clearAllBtnImage, saveBtnImage;
	private JLabel dropDownLabel;
	private MainView parent;
	/**
	 * Create the panel.
	 */
	public AdminMapEditPage(MainView mainView) {
		this.parent = mainView;
		this.setBounds(0, 67, 1057, 534);
		this.setBackground(new Color(0xf0e6e6));
		this.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 5));
		this.setLayout(null);

		this.leftPanel = new JPanel();
		this.leftPanel.setBounds(5, 5, 671, 524);
		this.leftPanel.setLayout(null);
		this.leftPanel.setBackground(new Color(0xe0dede));
		this.add(leftPanel);

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
		
		this.dropDownLabel = new JLabel(ViewStringLiterals.SELECT_MAP + " :");
		this.dropDownLabel.setFont(new Font("Meiryo", Font.PLAIN, 24));
		this.dropDownLabel.setBounds(15, 16, 253, 25);
		this.dropDownLabel.setForeground(new Color(0x5b1010));
		this.rightPanel.add(this.dropDownLabel);

		this.clearAllBtn = new JButton();
		this.clearAllBtn.setContentAreaFilled(false);
		this.clearAllBtn.setBorder(null);
		this.clearAllBtn.setBounds(92, 422, 80, 42);
		this.clearAllBtnImage = new ImageIcon(ImageURLS.CLEAR_ALL_BUTTON);
		this.clearAllBtn.setIcon(this.clearAllBtnImage);
		this.rightPanel.add(this.clearAllBtn);

		this.saveBtn = new JButton();
		this.saveBtn.setBounds(217, 422, 80, 42);
		this.saveBtn.setContentAreaFilled(false);
		this.saveBtn.setBorder(null);
		this.saveBtnImage = new ImageIcon(ImageURLS.SAVE_BUTTON);
		this.saveBtn.setIcon(this.saveBtnImage);
		this.rightPanel.add(this.saveBtn);
	}

}
