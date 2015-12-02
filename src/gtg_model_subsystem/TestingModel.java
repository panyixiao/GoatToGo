package gtg_model_subsystem;
//import gtg_control_subsystem.TestController;
import java.awt.Point;
import java.io.IOException;

/**
 * Separate Main process to test Model subsystem, will be changed later to hook into Controller and view API
 * To run and see what is there so far right click main model -> Run As -> Java Application
 * @author Joshua
 *
 * @version $Revision: 1.0 $
 */
public class TestingModel {
	/**
	 * Method main.
	 * @param args String[]
	 */
	public static void main(String args[])
	{
		MainModel test = new MainModel();
		//remind alyssa and neha that we need relative path and not absolute
		
		//TEST CASE 1
		test.loadFiles();
		//test.printNodes("BH_Basement");
		//test.setStartEndPathPoint(new Point(605,90), "TO", "BoyntonHall_1");
		//test.setStartEndPathPoint(new Point(422,615), "FROM", "BoyntonHall_3");
		Node toNode = test.validatePoint("BoyntonHall_1",605, 90, " ");

		Node fromNode = test.validatePoint("BoyntonHall_3",422, 615, " ");
		test.multiPathCalculate(fromNode, toNode);
		//test.printNodes(test.getMapPaths().get("BoyntonHall_1").getWayPoints());
		/**System.out.println(test.getPath().getStartPoint().getID());
		System.out.println(test.getPath().getEndPoint().getID());
		test.testDij("BH_Basement");
		Point point = test.validatePoint("BH_Basement", 125, 400);
		System.out.println("Point x: " + point.x + " Point Y: " + point.y);
		TestController testController = new TestController(test);*/
		
	}
}