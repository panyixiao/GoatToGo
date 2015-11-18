package gtg_view_subsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddDeleteMapPage extends JPanel{
	private JButton addBtn, deleteBtn, editBtn, previewBtn, browseFileBtn;
	private ImageIcon addBtnImage, deleteBtnImage, editBtnImage, previewBtnImage, browseFileBtnImage;
	private JLabel availableMapListLabel, mapPreviewLabel, mapPreviewHolder, enterMapNameLabel, selectImageLabel, mapListEmpty, isCampusLabel;
	private MainView parent;
	private JScrollPane mapListscrollPane;
	private JList mapList;
	private ArrayList<String> mapListArray;
	private JPanel addDialogPanel;
	private JTextField mapNameTextField, mapURLTextField;
	private String addMapURLPath = "";
	private JRadioButton yesBtn, noBtn;
	private ButtonGroup group;
	private String mapType = ViewStringLiterals.FLOOR;
	/*
	 * Initialize the contents of the page.
	 */
	public AddDeleteMapPage(MainView mainView) {
		this.parent = mainView;
		this.setBounds(0, 67, 1366, 661);
		this.setBackground(new Color(0xf0e6e6));
		this.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 5));
		this.setLayout(null);

		this.availableMapListLabel = new JLabel(ViewStringLiterals.AVAILABLE_MAP_LIST);
		this.availableMapListLabel.setBounds(50, 40, 271, 30);
		this.availableMapListLabel.setFont(new Font("Meiryo", Font.PLAIN, 25));
		this.availableMapListLabel.setForeground(new Color(0xc30e2d));
		this.add(this.availableMapListLabel);

		this.addBtn = new JButton();
		
		// Handles the add button functionality.
		this.addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMapURLPath = "";
				mapNameTextField.setText("");
				mapURLTextField.setText("");
				int result = JOptionPane.showConfirmDialog(((JButton)e.getSource()).getParent(), addDialogPanel, "Add new map",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					if(mapNameTextField.getText().equals("")){
						JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), ViewStringLiterals.MAP_NAME_EMPTY, "INVALID", JOptionPane.ERROR_MESSAGE);
					} else if(mapURLTextField.getText().equals("")){
						JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), ViewStringLiterals.MAP_URL_EMPTY, "INVALID", JOptionPane.ERROR_MESSAGE);
					} else {
						parent.sendAddMapData(mapNameTextField.getText(), addMapURLPath, mapType);
					}
				}
			}
		});
		this.addBtn.setContentAreaFilled(false);
		this.addBtn.setBorder(null);
		this.addBtn.setBounds(536, 194, 72, 42);
		this.addBtnImage = new ImageIcon(ImageURLS.ADD_BUTTON);
		this.addBtn.setIcon(this.addBtnImage);
		this.add(addBtn);

		this.deleteBtn = new JButton();
		
		// Handles the delete button functionality.
		this.deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mapList.getSelectedIndex() != -1){
					parent.deleteMap(mapList.getSelectedValue().toString());
				} else {
					JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), ViewStringLiterals.MAP_NOT_SELECTED, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.deleteBtn.setContentAreaFilled(false);
		this.deleteBtn.setBorder(null);
		this.deleteBtn.setBounds(526, 273, 90, 42);
		this.deleteBtnImage = new ImageIcon(ImageURLS.DELETE_BUTTON);
		this.deleteBtn.setIcon(this.deleteBtnImage);
		this.add(deleteBtn);
		
		this.editBtn = new JButton();
		
		// Handles the edit button functionality.
		this.editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mapList.getSelectedIndex() != -1){
					parent.showAdminMapEditPage(mapList.getSelectedValue().toString());
				} else {
					JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), ViewStringLiterals.MAP_NOT_SELECTED, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.editBtn.setContentAreaFilled(false);
		this.editBtn.setBorder(null);
		this.editBtn.setBounds(536, 354, 68, 42);
		this.editBtnImage = new ImageIcon(ImageURLS.EDIT_BUTTON);
		this.editBtn.setIcon(this.editBtnImage);
		this.add(editBtn);
		
		this.previewBtn = new JButton();
		
		// Handles the preview button functionality.
		this.previewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mapList.getSelectedIndex() != -1){
					parent.fetchMapURLAdmin(mapList.getSelectedValue().toString());
				} else {
					JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), ViewStringLiterals.MAP_NOT_SELECTED, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.previewBtn.setContentAreaFilled(false);
		this.previewBtn.setBorder(null);
		this.previewBtn.setBounds(521, 433, 100, 42);
		this.previewBtnImage = new ImageIcon(ImageURLS.PREVIEW_BUTTON);
		this.previewBtn.setIcon(this.previewBtnImage);
		this.add(previewBtn);

		this.mapPreviewLabel = new JLabel(ViewStringLiterals.MAP_PREVIEW);
		this.mapPreviewLabel.setBounds(689, 40, 159, 30);
		this.mapPreviewLabel.setFont(new Font("Meiryo", Font.PLAIN, 25));
		this.mapPreviewLabel.setForeground(new Color(0xc30e2d));
		this.add(this.mapPreviewLabel);
		
		this.mapListEmpty = new JLabel(ViewStringLiterals.MAP_LIST_EMPTY);
		this.mapListEmpty.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.mapListEmpty.setBounds(90, 325, 337, 20);
		this.mapListEmpty.setVisible(false);
		this.add(this.mapListEmpty);

		this.mapList = new JList();
		this.mapList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.mapList.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.mapList.setForeground(new Color(0xc30e2d));

		this.mapListscrollPane = new JScrollPane(mapList);
		this.mapListscrollPane.setBounds(50, 86, 401, 539);
		this.mapListscrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 2));
		this.add(this.mapListscrollPane);
		
		this.mapPreviewHolder = new JLabel();
		this.mapPreviewHolder.setBounds(689, 86, 642, 539);
		this.mapPreviewHolder.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d), 2));
		this.add(this.mapPreviewHolder);
		
		this.addDialogPanel = new JPanel();
		this.addDialogPanel.setLayout(null);
		this.addDialogPanel.setPreferredSize(new Dimension(500,200));
		
		this.enterMapNameLabel = new JLabel(ViewStringLiterals.ENTER_MAP_NAME);
		this.enterMapNameLabel.setBounds(10, 20, 185, 30);
		this.enterMapNameLabel.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.enterMapNameLabel.setForeground(new Color(0xc30e2d));
		
		this.mapNameTextField = new JTextField();
		this.mapNameTextField.setBounds(200, 15, 200, 41);
		this.mapNameTextField.setColumns(10);
		this.mapNameTextField.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.mapNameTextField.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d),3));
		this.mapNameTextField.setForeground(new Color(0xc30e2d));

		this.selectImageLabel = new JLabel(ViewStringLiterals.SELECT_MAP_IMAGE);
		this.selectImageLabel.setBounds(10, 80, 185, 30);
		this.selectImageLabel.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.selectImageLabel.setForeground(new Color(0xc30e2d));
		
		this.mapURLTextField = new JTextField();
		this.mapURLTextField.setBounds(200, 75, 200, 41);
		this.mapURLTextField.setColumns(10);
		this.mapURLTextField.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.mapURLTextField.setBorder(BorderFactory.createLineBorder(new Color(0xc30e2d),3));
		this.mapURLTextField.setForeground(new Color(0xc30e2d));

		this.browseFileBtn = new JButton();
		
		// Handles the browse file button functionality.
		this.browseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser fileChooser = new JFileChooser();
				 FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "PNG & JPG Images", "jpg", "png");
				 fileChooser.setFileFilter(filter);
				 int returnValue = fileChooser.showOpenDialog(null);
				 if (returnValue == JFileChooser.APPROVE_OPTION) {
			          File selectedFile = fileChooser.getSelectedFile();
			          addMapURLPath = selectedFile.getAbsolutePath();
			          mapURLTextField.setText(selectedFile.getName());
				 }
			}
		});
		this.browseFileBtn.setContentAreaFilled(false);
		this.browseFileBtn.setBorder(null);
		this.browseFileBtn.setBounds(430, 60, 50, 50);
		this.browseFileBtnImage = new ImageIcon(ImageURLS.BROWSE_FILE_BUTTON);
		this.browseFileBtn.setIcon(this.browseFileBtnImage);
		
		this.isCampusLabel = new JLabel(ViewStringLiterals.IS_CAMPUS_MAP);
		this.isCampusLabel.setBounds(10, 150, 250, 30);
		this.isCampusLabel.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.isCampusLabel.setForeground(new Color(0xc30e2d));
	
		this.yesBtn = new JRadioButton();
		this.yesBtn.setText(ViewStringLiterals.YES);
		this.yesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mapType = ViewStringLiterals.CAMPUS;
			}
		});
		this.yesBtn.setBounds(250, 153, 70, 25);
		this.yesBtn.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.yesBtn.setForeground(new Color(0xc30e2d));
		this.yesBtn.setBackground(null);

		this.noBtn = new JRadioButton();
		this.noBtn.setText(ViewStringLiterals.NO);
		this.noBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mapType = ViewStringLiterals.FLOOR;
			}
		});
		this.noBtn.setBounds(350, 153, 70, 25);
		this.noBtn.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.noBtn.setForeground(new Color(0xc30e2d));
		this.noBtn.setBackground(null);
		this.noBtn.setSelected(true);

		this.group = new ButtonGroup();
	    this.group.add(yesBtn);
	    this.group.add(noBtn);

		this.addDialogPanel.add(this.enterMapNameLabel);
		this.addDialogPanel.add(this.selectImageLabel);
		this.addDialogPanel.add(this.browseFileBtn);
		this.addDialogPanel.add(this.mapNameTextField);
		this.addDialogPanel.add(this.mapURLTextField);
		this.addDialogPanel.add(this.isCampusLabel);
		this.addDialogPanel.add(this.yesBtn);
		this.addDialogPanel.add(this.noBtn);
	}
	
	/*
	 * Displays the list of map names.
	 * input: ArrayList of mapnames.
	 */
	public void showMapList(ArrayList<String> listofMaps){
		if(listofMaps.size() == 0){
			this.mapListEmpty.setVisible(true);
		} else {
			this.mapListEmpty.setVisible(false);
		}

		this.mapListArray = listofMaps;
		this.mapList.setListData(listofMaps.toArray());
		this.mapListscrollPane.revalidate();
		this.mapListscrollPane.repaint();
	}

	/*
	 * Displays the map in the map preview holder.
	 * input: Map url.
	 */
	public void showMapImage(String mapurl) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(mapurl));
			Image scaled = img.getScaledInstance(this.mapPreviewHolder.getWidth(), this.mapPreviewHolder.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon mapImage = new ImageIcon(scaled);
			this.mapPreviewHolder.setIcon(mapImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Displays error message if map cannot be added to the .txt file.
	 */
	public void showAddMapError() {
		JOptionPane.showMessageDialog(this, ViewStringLiterals.MAP_CANNOT_ADDED, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	/*
	 * Displays error message if map cannot be delete from the .txt file.
	 */
	public void showDeleteMapError() {
		JOptionPane.showMessageDialog(this, ViewStringLiterals.MAP_CANNOT_DELETE, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
