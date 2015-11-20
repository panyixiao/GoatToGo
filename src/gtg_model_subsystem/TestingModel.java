package gtg_model_subsystem;
import gtg_control_subsystem.TestController;
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
		try {
			test.saveNewMap("BH_Basement", "images\\BH_Basement.png", "Floor");
			test.printMaps();
			//test.createMapGraph("BH_Basement");
			//test.createMapGraph("BH_LAME");
			//test.deleteMap("BH_Basement");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TEST CASE 1
		/**test.loadFiles("BH_Basement");
		test.printNodes("BH_Basement");
		test.setStartEndPathPoint(new Point(590,346), "FROM", "BH_Basement");
		test.setStartEndPathPoint(new Point(632,189), "TO", "BH_Basement");
		System.out.println(test.getPath().getStartPoint().getID());
		System.out.println(test.getPath().getEndPoint().getID());
		test.testDij("BH_Basement");
		Point point = test.validatePoint("BH_Basement", 125, 400);
		System.out.println("Point x: " + point.x + " Point Y: " + point.y);
		TestController testController = new TestController(test);
		**/
	}
}