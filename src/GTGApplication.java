import gtg_view_subsystem.MainView;
import gtg_control_subsystem.MainController;
import gtg_model_subsystem.MainModel;

/**
 */
public class GTGApplication {
	/**
	 * Method main.
	 * @param args String[]
	 */
	public static void main(String[] args) {
		MainModel mainModel = new MainModel();
		
		MainController mainController = new MainController(mainModel);

		MainView mainView= new MainView(mainController);

	}

}
