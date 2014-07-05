package controller;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.AccessLayer;
import model.Applicant;
import view.AddApplicantPanel;

import com.toedter.calendar.JDateChooser;

public class AddApplicantListener implements ActionListener{ 

	private MainController controller;
	
	
	public AddApplicantListener(MainController controller) {
		
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == controller.addApplicantView.saveButton) {
			try{
				JDateChooser dateS = AddApplicantPanel.getInstance().dateSourcedDC,
						dateP = AddApplicantPanel.getInstance().dateProcessedDC,
						dateRD = AddApplicantPanel.getInstance().reschedDateDC,
						dateD = AddApplicantPanel.getInstance().schedDateDC;
				Applicant applicant = new Applicant(dateS.getDate(), dateP.getDate(),
							AddApplicantPanel.getInstance().firstName_TF.getText().trim(), AddApplicantPanel.getInstance().lastName_TF.getText().trim(),
							AddApplicantPanel.getInstance().primaryNum_TF.getText().trim(), AddApplicantPanel.getInstance().secNum_TF.getText().trim(),
							AddApplicantPanel.getInstance().school_TF.getText().trim(), AddApplicantPanel.getInstance().lastPosCB.getSelectedItem().toString(),
							""+AddApplicantPanel.getInstance().jobClassCB.getSelectedItem().toString().charAt(0), AddApplicantPanel.getInstance().lastComp_TF.getText().trim(),
							AddApplicantPanel.getInstance().email_TF.getText().trim(), AddApplicantPanel.getInstance().source_TF.getText().trim(),
							AddApplicantPanel.getInstance().specifics_TF.getText().trim(), AddApplicantPanel.getInstance().posEndorsedCB.getSelectedItem().toString(),
							AddApplicantPanel.getInstance().statusCB.getSelectedItem().toString(), dateD.getDate(),
							AddApplicantPanel.getInstance().schedTime_TF.getText().trim(), AddApplicantPanel.getInstance().schedOutcomeCB.getSelectedItem().toString(),
							dateRD.getDate(), AddApplicantPanel.getInstance().reschedTime_TF.getText().trim(),
							AddApplicantPanel.getInstance().reschedOutcomeCB.getSelectedItem().toString(), AddApplicantPanel.getInstance().recruiter_TF.getText().trim(),
							AddApplicantPanel.getInstance().clientName_TF.getText().trim(), AddApplicantPanel.getInstance().clientResultCB.getSelectedItem().toString(),
							AddApplicantPanel.getInstance().remarks_TA.getText().trim(), AddApplicantPanel.getInstance().actualRevenue_TF.getText().trim(),
							AddApplicantPanel.getInstance().cvPath_TF.getText().trim());
				String dateSourced, dateProcessed, schedDate, reschedDate;
				dateSourced = ((JTextField)(dateS.getDateEditor().getUiComponent())).getText();
				dateProcessed = ((JTextField)(dateP.getDateEditor().getUiComponent())).getText();
				schedDate = ((JTextField)(dateD.getDateEditor().getUiComponent())).getText();
				reschedDate = ((JTextField)(dateRD.getDateEditor().getUiComponent())).getText();
				if((!applicant.getSchedTime().equals("eg. 8:30 am") && !isValidTime(applicant.getSchedTime())) || (!applicant.getReschedTime().equals("eg. 8:30 am") && !isValidTime(applicant.getReschedTime())) || AddApplicantPanel.getInstance().firstName_TF.getForeground().equals(Color.LIGHT_GRAY) ||
						AddApplicantPanel.getInstance().lastName_TF.getForeground().equals(Color.LIGHT_GRAY) || applicant.getPrimaryNumber().equals("") || applicant.getSchool().equals("") || applicant.getLastCompany().equals("") || AddApplicantPanel.getInstance().email_TF.getForeground().equals(Color.LIGHT_GRAY) || applicant.getSource().equals("") || applicant.getSpecifics().equals("") ||
						(!dateSourced.isEmpty() && dateS.getDate() == null) || (!dateProcessed.isEmpty() && dateP.getDate() == null) || 
						(!schedDate.isEmpty() && dateD.getDate() == null) || (!reschedDate.isEmpty() && dateRD.getDate() == null))
					throw new Exception();
				else{
					if(!applicantAlreadyExist(applicant)){
						if(AddApplicantPanel.getInstance().from == 1)
							applicant.addToDatabase();
						//else {}
						if(!applicant.getCVPath().isEmpty())
							Files.copy(AddApplicantPanel.getInstance().getSource(), AddApplicantPanel.getInstance().getTarget(), REPLACE_EXISTING);
						AddApplicantPanel.getInstance().clearFields();
					}
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error! Check all inputs for required fields/date/time.", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		
		else if(event.getSource() == controller.addApplicantView.backButton) {
		}
	}
	
	private boolean applicantAlreadyExist(Applicant applicant) {
		ResultSet result = AccessLayer.getInstance().executeQuery("select first_name from applicant_info where `last_name`='"+applicant.insertBackSlash(applicant.getLastname())+"' AND `first_name`='"+applicant.insertBackSlash(applicant.getFirstname())+"' AND `email`='"+applicant.insertBackSlash(applicant.getEmail())+"';");
		try {
			while(result.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isValidTime(String time){
		String str[] = time.split(":");
		String nxtStr[] = str[1].split(" ");
		
		if(isNum(str[0]) && str[0].length()<=2 && str[0].length()!=0 && isNum(nxtStr[0]) && nxtStr[0].length()<=2 && nxtStr[0].length()!=0 && isValid(nxtStr[1]) && nxtStr[1].length()<=2 && nxtStr[1].length()>0)
			return true;
		return false;
	}
	
	private boolean isNum(String num){
		try  
		  {  
		    int d = Integer.parseInt(num);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
	}
	
	private boolean isValid(String str){
		if(str.equalsIgnoreCase("am") || str.equalsIgnoreCase("pm") || str.equalsIgnoreCase("nn") || str.equalsIgnoreCase("mn"))
			return true;
		return false;
	}
}