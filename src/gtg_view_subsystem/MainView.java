package gtg_view_subsystem;

import javax.swing.JPanel;

public class MainView {
	private Page page;
	private WelcomePage welcomePage = new WelcomePage(this);
	private MapPage mapPage = new MapPage(this);
	private ResultPage resultPage = new ResultPage(this);
	private LoginPage loginPage = new LoginPage(this);
	private JPanel currentPage = new JPanel();
	private AdminMapEditPage adminMapPage = new AdminMapEditPage(this);
	
	public MainView(){
		page = new Page(this);
		this.showWelcomePage();
		//page.addPage(welcomePage);
		//currentPage = welcomePage;
	}
	
	public void showMapPage(){
		page.showAdminButton();
		page.hideLogoutButton();
		page.removePage(currentPage);
		page.addPage(mapPage);
		currentPage = mapPage;
	}
	
	public void showWelcomePage(){
		page.showAdminButton();
		page.hideLogoutButton();
		page.removePage(currentPage);
		page.addPage(welcomePage);
		currentPage = welcomePage;
	}
	
	public void showAdminLoginPage(){
		page.hideAdminButton();
		page.hideLogoutButton();
		page.removePage(currentPage);
		page.addPage(loginPage);
		currentPage = loginPage;
	}
	
	public void showResultPage(){
		page.showAdminButton();
		page.hideLogoutButton();
		page.removePage(currentPage);
		page.addPage(resultPage);
		currentPage = resultPage;
	}
	
	public void showAdminMapEditPage(){
		page.showLogoutButton();
		page.removePage(currentPage);
		page.addPage(adminMapPage);
		currentPage = adminMapPage;
	}
}
