import gtg_view_subsystem.MainView;
import gtg_control_subsystem.MainController;
import gtg_model_subsystem.MainModel;

public class GTGApplication {
	public static void main(String[] args) {
<<<<<<< HEAD
		
=======
>>>>>>> 01835e2c6e34f9da4bb6851d323882f1336aaff1
		MainModel mainModel = new MainModel();

		MainController mainController = new MainController(mainModel);

<<<<<<< HEAD
		MainView mainView= new MainView();
=======
		MainView mainView= new MainView(mainController);

>>>>>>> 01835e2c6e34f9da4bb6851d323882f1336aaff1
	}

}
