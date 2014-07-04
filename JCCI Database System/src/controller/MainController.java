package controller;

import view.*;

public class MainController {
	public MainFrame frame;
	
	public AddApplicantPanel addApplicantView;
	
	public MainController(MainFrame frame) {
		
		this.frame = frame;

		addApplicantView = AddApplicantPanel.getInstance();
		
		addListeners();
		
	}
	
	public void addListeners() {
		
		addApplicantView.backButton.addActionListener(new AddApplicantListener(this));
		addApplicantView.saveButton.addActionListener(new AddApplicantListener(this));
		
	}
}
