package gtg_view_subsystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 */
public class Page extends JFrame {
	private JPanel mainPanel, headerPanel, dragpanel;
	private JLabel wpiLogoHolder, lblGoattogo;
	private ImageIcon wpiLogoImage, minimizeBtnImage, closeBtnImage,maximizeBtnImage;
	private JButton minimizeBtn, closeBtn, adminBtn, logoutBtn,maximizeBtn;
	private MainView parent;
	private int pX, pY;
	/**
	 * Create the frame.
	 * @param mainView 
	 */
	public Page(MainView mainView) {
		this.parent = mainView;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1366, 728);
		//this.setBounds(500, 200, 1057, 601);
		this.setUndecorated(true);
		this.getContentPane().setLayout(null);
		
		this.headerPanel = new JPanel();
		this.headerPanel.setBounds(0, 0, 1366, 67);
		this.headerPanel.setBackground(new Color(0xc30e2d));
		this.headerPanel.setLayout(null);
		this.getContentPane().add(this.headerPanel);

		this.wpiLogoHolder = new JLabel();
		this.wpiLogoHolder.setBounds(10, 6, 64, 56);
		this.wpiLogoImage = new ImageIcon(ImageURLS.WPI_SMALL_LOGO);
		this.wpiLogoHolder.setIcon(this.wpiLogoImage);
		this.headerPanel.add(this.wpiLogoHolder);
		
		this.lblGoattogo = new JLabel("GOAT-TO-GO");
		this.lblGoattogo.setFont(new Font("Meiryo", Font.PLAIN, 36));
		this.lblGoattogo.setForeground(Color.WHITE);
		this.lblGoattogo.setBounds(89, 16, 277, 46);
		this.headerPanel.add(this.lblGoattogo);

		this.minimizeBtn = new JButton();
		this.minimizeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(Frame.ICONIFIED);
			}
		});
		this.minimizeBtn.setBounds(1285, 45, 22, 5);
		this.minimizeBtn.setContentAreaFilled(false);
		this.minimizeBtn.setBorder(null);
		this.minimizeBtnImage = new ImageIcon(ImageURLS.MINIMIZE_BUTTON);
		this.minimizeBtn.setIcon(this.minimizeBtnImage);
		this.headerPanel.add(this.minimizeBtn);

		/*this.maximizeBtn = new JButton();
		this.maximizeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getExtendedState() != Frame.MAXIMIZED_BOTH) 
				setExtendedState(Frame.MAXIMIZED_BOTH); 
				else 
				setExtendedState(Frame.NORMAL);
			}
		});
		this.maximizeBtn.setBounds(1285, 15, 22, 50);
		this.maximizeBtn.setContentAreaFilled(false);
		this.maximizeBtn.setBorder(null);
		this.maximizeBtnImage = new ImageIcon(ImageURLS.MAXIMIZE_BUTTON);
		this.maximizeBtn.setIcon(this.maximizeBtnImage);
		this.headerPanel.add(this.maximizeBtn);
		*/
		
		
		this.closeBtn = new JButton();
		this.closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		this.closeBtn.setBounds(1322, 22, 29, 27);
		this.closeBtn.setContentAreaFilled(false);
		this.closeBtn.setBorder(null);
		this.closeBtnImage = new ImageIcon(ImageURLS.CLOSE_BUTTON);
		this.closeBtn.setIcon(this.closeBtnImage);
		this.headerPanel.add(this.closeBtn);
		
		String adminText = "<html><u>" + ViewStringLiterals.ADMIN +"</u></html>";
		this.adminBtn = new JButton(adminText);
		this.adminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showAdminLoginPage();
			}
		});
		this.adminBtn.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.adminBtn.setBackground(null);
		this.adminBtn.setForeground(Color.WHITE);
		this.adminBtn.setBounds(1185, 18, 70, 44);
		this.adminBtn.setBorder(null);
		this.adminBtn.setFocusPainted(false);
		headerPanel.add(this.adminBtn);
		
		String logoutText = "<html><u>" + ViewStringLiterals.LOGOUT +"</u></html>";
		this.logoutBtn = new JButton(logoutText);
		this.logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showWelcomePage();
			}
		});
		this.logoutBtn.setFont(new Font("Meiryo", Font.PLAIN, 20));
		this.logoutBtn.setBackground(null);
		this.logoutBtn.setForeground(Color.WHITE);
		this.logoutBtn.setBounds(1185, 18, 70, 44);
		this.logoutBtn.setBorder(null);
		this.logoutBtn.setFocusPainted(false);
		this.logoutBtn.setVisible(false);
		headerPanel.add(this.logoutBtn);
		
		this.dragpanel = new JPanel();
		this.dragpanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent mouseEvent) {
				setLocation(getLocation().x+mouseEvent.getX()-pX,getLocation().y+mouseEvent.getY()-pY);
			}
		});
		this.dragpanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				pX=mouseEvent.getX();
                pY=mouseEvent.getY();
			}
		});
		this.dragpanel.setBounds(0, 0, 1178, 67);
		this.dragpanel.setBackground(null);
		headerPanel.add(this.dragpanel);

		this.mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		this.mainPanel.setBounds(0, 67, 1366, 661);
		this.getContentPane().add(mainPanel);

		this.setVisible(true);
	}
	
	/**
	 * Method addPage.
	 * @param page JPanel
	 */
	public void addPage(JPanel page){
		this.mainPanel.setLayout(new java.awt.GridLayout());
		this.mainPanel.add(page);
		this.mainPanel.revalidate();
	}
	
	/**
	 * Method removePage.
	 * @param page JPanel
	 */
	public void removePage(JPanel page){
		this.mainPanel.remove(page);
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}
	
	public void hideAdminButton(){
		this.adminBtn.setVisible(false);
	}
	
	public void showAdminButton(){
		this.adminBtn.setVisible(true);
	}
	
	public void showLogoutButton(){
		this.logoutBtn.setVisible(true);
	}
	
	public void hideLogoutButton(){
		this.logoutBtn.setVisible(false);
	}
}
