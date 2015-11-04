package gtg_model_subsystem;
import gtg_model_subsystem.TestingDijkstra;


/**
 * Separate Main process to test Model subsystem, will be changed later to hook into Controller and view API
 * To run and see what is there so far right click main model -> Run As -> Java Application
 * @author Joshua
 *
 */
public class MainModel {
	public static void main(String args[])
	{
		TestingDijkstra test = new TestingDijkstra();
		test.testDij(1,7);
	}
}
