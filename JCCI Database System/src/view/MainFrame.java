package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.MainController;


public class MainFrame extends JFrame{

	private static MainFrame instance;
	
	public static final int WIDTH = 900, HEIGHT = 700;
	
	public MainFrame(){
		super("JCCI Database System");
		addComponents();
		setFrame();
		new MainController(this);
	}
	
	private void addComponents() {
		getContentPane().add(AddApplicantPanel.getInstance());
	}

	private void setFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
		try{
			UIManager.setLookAndFeel(looks[3].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch(Exception e){}
		setSize(WIDTH+6, HEIGHT+29);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static MainFrame getInstance() {
		return instance;
	}

}
