import gtg_view_subsystem.MainView;
import gtg_control_subsystem.MainController;
import gtg_model_subsystem.MainModel;

public class GTGApplication {
	public static void main(String[] args) {
		MainView mainView= new MainView();
		MainController mainController = new MainController();
		MainModel mainModel = new MainModel();
	}

}
