package gtg_view_subsystem;

import java.awt.Point;

import javax.swing.JPanel;

import gtg_control_subsystem.MainController;

public class MainView {
	private Page page;
	private WelcomePage welcomePage = new WelcomePage(this);
	private MapPage mapPage = new MapPage(this);
	private ResultPage resultPage = new ResultPage(this);
	private LoginPage loginPage = new LoginPage(this);
	private JPanel currentPage = new JPanel();
	private AdminMapEditPage adminMapPage = new AdminMapEditPage(this);
	private MainController mainController;
	
	public MainView(MainController mainController){
		this.mainController = mainController;
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

	public void sentPointToModel(Point startEndPoint, String selectedPointType, String mapName) {
		//send the point to controller
		System.out.println("Selected Point is" + startEndPoint);
		System.out.println("Selected Point type" + selectedPointType);
		System.out.println("Selected Map " + mapName);
		//this.mainController.setTaskPnt((int) startEndPoint.getX(), (int) startEndPoint.getY(), selectedPointType, mapName)
		this.mapPage.setPoint();
	}

	public void getPathResult() {
		// TODO Auto-generated method stub
		showResultPage();
	}

	public void deleteSelectedPoint(String selectedPointType) {
		System.out.println("Point is deleted" + selectedPointType);
		// TODO Auto-generated method stub
		//this.mainController.deleteSelectedPoint(selectedPointType);
		this.mapPage.deletePoint(selectedPointType);
		
	}
}
