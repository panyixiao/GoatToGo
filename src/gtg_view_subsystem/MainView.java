package gtg_view_subsystem;

import java.awt.Point;
import java.util.ArrayList;

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
		this.mainController.setTaskPnt(startEndPoint, selectedPointType, mapName);
		//this.mapPage.setPoint();
	}

	public void getPathResult() {
		// TODO Auto-generated method stub
		//PathData path = this.createDummyDataForResult();
		PathData path = mainController.getPathData();
		showResultPage();
		this.resultPage.displayPath(path);
	}

	public void deleteSelectedPoint(String selectedPointType) {
		System.out.println("Point is deleted" + selectedPointType);
		// TODO Auto-generated method stub
		//this.mainController.deleteSelectedPoint(selectedPointType);
		this.mapPage.deletePoint(selectedPointType);
		
	}
	
	public PathData createDummyDataForResult(){
		PathData path = new PathData();
		path.setStartPoint(new Point(112, 381));
		path.setEndPoint(new Point(863, 842));
		
		ArrayList<String> tempMapNames = new ArrayList<String>();
		tempMapNames.add("BH_BASEMENT");
		path.setArrayOfMapNames(tempMapNames);
		
		ArrayList<Point> tempPoints = new ArrayList<Point>();
		tempPoints.add(new Point(112, 381));
		tempPoints.add(new Point(147, 364));
		tempPoints.add(new Point(147, 364));
		tempPoints.add(new Point(246, 364));
		tempPoints.add(new Point(295, 363));
		tempPoints.add(new Point(361, 365));
		tempPoints.add(new Point(389, 493));
		tempPoints.add(new Point(491, 529));
		tempPoints.add(new Point(658, 542));
		tempPoints.add(new Point(777, 542));
		tempPoints.add(new Point(857, 538));
		tempPoints.add(new Point(863, 842));
		
		path.setWayPoints(tempPoints);
		
		return path;
	}
}
