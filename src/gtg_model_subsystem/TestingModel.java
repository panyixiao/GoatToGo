package gtg_model_subsystem;
import gtg_control_subsystem.TestController;
import java.awt.Point;

/**
 * Separate Main process to test Model subsystem, will be changed later to hook into Controller and view API
 * To run and see what is there so far right click main model -> Run As -> Java Application
 * @author Joshua
 *
 */
public class TestingModel {
	public static void main(String args[])
	{
		MainModel test = new MainModel();
		test.loadFiles();
		test.printNodes("BH_Basement");
		test.setStartEndPathPoint(new Point(590,346), "FROM", "BH_Basement");
		test.setStartEndPathPoint(new Point(632,189), "TO", "BH_Basement");
		System.out.println(test.getPath().getStartPoint().getID());
		System.out.println(test.getPath().getEndPoint().getID());
		test.testDij("BH_Basement");
		Point point = test.validatePoint("BH_Basement", 125, 400);
		System.out.println("Point x: " + point.x + " Point Y: " + point.y);
		TestController testController = new TestController(test);
	}
}