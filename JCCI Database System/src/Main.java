import model.AccessLayer;
import view.MainFrame;

public class Main {
	public static void main(String args[]){
		AccessLayer.dbName = "JCCI_db";
		new MainFrame();
	}
}