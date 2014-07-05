package view;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import model.AccessLayer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;

public class AddApplicantPanel extends JPanel{
	
	/*public String values[] = new String[] {"", "Web Developer", "Data Processing Assistant", "Technical Writers", "Technical Editors", "Publication Planners", "Desktop Publishers", "Localization Administrator", "Usability Specialist", "Cloud Support Engineer", "DevOps Engineer", "Global Support Services Engineer", "Software Level 3 Support", "Mobile Software Engineer", "Mechanical Design Engineer", "Software Engineer (Tools Developer)", "Software Engineer (Java)", "Firmware Development Engineer", "Software Solutions Engineer (C/C++)", "Software Quality Assurance Engineer", "Firmware Test Engineer", "IT Project Manager", "HRIS Analyst", "Finance and Accounting Manager", "Analyst Accountants", "Junior Accountants", "Senior Accountants", "Interface Accounting Process Experts", "Accounts Receivable Specialists", "Finance Sales Analysts", "Quality Management Specialists", "Accounting Officer", "Rebates Team Lead", "Senior Buyer", "Senior Order to Cash Manager", "Auditor", "Customer Service Representative", "Technical Support Representative (Cable TV/Internet)", "Pre-process Trainer", "Voice Interaction Coach", "Transitions Manager", "Training Manager", "Communications Trainor", "Spanish Sales Representative", "Human Resources Director", "Human Resources Manager", "Human Resources Officer", "Human Resources Consultants", "Sourcing Associates", "Safety and Security Manager", "Manufacturing Section Manager", "Process Engineer", "Technical Services Auditor", "Service Engineer", "Area Business Manager", "Marketing Manager", "Assistant Marketing Manager", "Business Proposal Coordinator", "Junior Sales Associate", "Assistant Product Manager", "Freight Sales Head", "AVP for Sales and Marketing", "Merchandising Head", "Merchandising Development", "Executive Assistants"},
				classification[] = new String[] {"Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified",
					"Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Agent","Agent","Non-Agent","Non-Agent","Non-Agent","Non-Agent","Non-Agent","Agent","Unclassified",
					"Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified","Unclassified"};*/
	public JTextField primaryNum_TF, secNum_TF, school_TF, lastComp_TF, source_TF, specifics_TF, recruiter_TF, clientName_TF, actualRevenue_TF, cvPath_TF;
	public TextField firstName_TF, lastName_TF, schedTime_TF, reschedTime_TF, email_TF;
	public JButton saveButton, backButton;
	public JTextArea remarks_TA;
	public Path source, target;
	public ArrayList<String> strValues= new ArrayList<String>();
	public JDateChooser dateSourcedDC, dateProcessedDC, schedDateDC, reschedDateDC ;
	
	public int from=1;
	
	@SuppressWarnings("rawtypes")
	public JList occupationList;
	@SuppressWarnings("rawtypes")
	public DefaultListModel listModel;
	@SuppressWarnings("rawtypes")
	public DefaultComboBoxModel lastPosModel, posEndorsedModel;
	
	private static AddApplicantPanel instance;
	private JLabel sourcedLbl, processedLbl, fullNameLbl, schoolLbl, lastPosLbl, emailLbl, sourceLbl, specificsLbl, posEndorsedLbl, statusLbl,
			schedTimeLbl, schedOutcomeLbl;
	
	@SuppressWarnings("rawtypes")
	public JComboBox statusCB, schedOutcomeCB, jobClassCB, reschedOutcomeCB, clientResultCB, lastPosCB, posEndorsedCB ;
	@SuppressWarnings("rawtypes")
	public Vector lastPoscomboBoxItems = new Vector(), posEndorsedcomboBoxItems = new Vector();
	
	JFileChooser chooser = new JFileChooser();
	private JButton removeButton;
	
	public static AddApplicantPanel getInstance(){
		if(instance == null)
			instance = new AddApplicantPanel();
		return instance;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddApplicantPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setFocusable(true);
				setRequestFocusEnabled(true);
				grabFocus();
			}
		});
		setBounds(new Rectangle(0, 0, 900, 700));
		setLayout(null);
		
		chooser.setMultiSelectionEnabled(false);
		chooser.addChoosableFileFilter(new ExtensionFileFilter(new String[] { ".docx" }, "Microsoft Word Documents (*.docx)"));
		chooser.addChoosableFileFilter(new ExtensionFileFilter(new String[] { ".pdf" }, "PDF Documents (*.pdf)"));
		chooser.addChoosableFileFilter(new ExtensionFileFilter(new String[] { ".zip" }, "Compressed Files (*.zip)"));
		chooser.addChoosableFileFilter(new ExtensionFileFilter(null, null));
		chooser.setAcceptAllFileFilterUsed(false); // Turn off 'All Files' capability of file chooser, so only our custom filter is used.
		
		ResultSet rs = AccessLayer.getInstance().getAllOccupations();
		try {
			while(rs.next()){
				lastPoscomboBoxItems.add(rs.getString(1));
				posEndorsedcomboBoxItems.add(rs.getString(1));
				strValues.add(rs.getString(2));
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	/*	for(int i=0; i<values.length; i++){
			comboBoxItems.add(values[i]);
			/*try {
				AccessLayer.getInstance().addOccupationToDatabase(insertBackSlash(values[i]), insertBackSlash(classification[i]));
			} catch (SQLException e1) {
			}
		}*/
		lastPosModel = new DefaultComboBoxModel(lastPoscomboBoxItems);
		posEndorsedModel = new DefaultComboBoxModel(posEndorsedcomboBoxItems);
		
		sourcedLbl = new JLabel("Date Sourced");
		sourcedLbl.setBounds(10, 11, 70, 14);
		add(sourcedLbl);
		
		processedLbl = new JLabel("Date Processed");
		processedLbl.setBounds(10, 69, 90, 14);
		add(processedLbl);
		
		fullNameLbl = new JLabel("Full Name");
		fullNameLbl.setBounds(224, 16, 70, 14);
		add(fullNameLbl);
		
		firstName_TF = new TextField("First Name");
		firstName_TF.setBounds(279, 8, 166, 24);
		add(firstName_TF);
		
		lastName_TF = new TextField("Last Name");
		lastName_TF.setBounds(279, 41, 166, 24);
		add(lastName_TF);
		
		primaryNum_TF = new JTextField();
		primaryNum_TF.setMinimumSize(new Dimension(20, 20));
		primaryNum_TF.setColumns(10);
		primaryNum_TF.setBounds(566, 11, 154, 24);
		add(primaryNum_TF);
		
		JLabel primNumLbl = new JLabel("Prim. #");
		primNumLbl.setBounds(518, 11, 41, 14);
		add(primNumLbl);
		
		secNum_TF = new JTextField();
		secNum_TF.setMinimumSize(new Dimension(20, 20));
		secNum_TF.setColumns(10);
		secNum_TF.setBounds(262, 69, 154, 24);
		add(secNum_TF);
		
		JLabel secNumLbl = new JLabel("Sec. #");
		secNumLbl.setBounds(224, 69, 41, 14);
		add(secNumLbl);
		
		school_TF = new JTextField();
		school_TF.setMinimumSize(new Dimension(20, 20));
		school_TF.setColumns(10);
		school_TF.setBounds(480, 46, 195, 24);
		add(school_TF);
		
		schoolLbl = new JLabel("School");
		schoolLbl.setBounds(480, 28, 41, 14);
		add(schoolLbl);
		
		JLabel jobClassLbl = new JLabel("Job Classification");
		jobClassLbl.setBounds(10, 133, 82, 14);
		add(jobClassLbl);
		
		jobClassCB = new JComboBox();
		jobClassCB.setModel(new DefaultComboBoxModel(new String[] {"Unclassified", "Agent", "Non-Agent"}));
		jobClassCB.setBounds(102, 128, 94, 24);
		add(jobClassCB);
		
		lastPosLbl = new JLabel("C/L Pos");
		lastPosLbl.setBounds(728, 28, 41, 14);
		add(lastPosLbl);
		
		lastPosCB = new JComboBox(lastPosModel);
		lastPosCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //
                // Get the source of the component, which is our combo
                // box.
                //
                JComboBox lastPosCB = (JComboBox) event.getSource();
                int selected = lastPosCB.getSelectedIndex();
                for(int i=0; i<listModel.size(); i++)
                	if(selected==i)
                		jobClassCB.setSelectedItem(strValues.get(i));

            }
        });
		lastPosCB.setBounds(703, 46, 187, 24);
		add(lastPosCB);
		
		lastComp_TF = new JTextField();
		lastComp_TF.setMinimumSize(new Dimension(20, 20));
		lastComp_TF.setColumns(10);
		lastComp_TF.setBounds(294, 98, 187, 24);
		add(lastComp_TF);
		
		JLabel lastCompLbl = new JLabel("C/L Company");
		lastCompLbl.setBounds(220, 98, 64, 14);
		add(lastCompLbl);
		
		emailLbl = new JLabel("Email");
		emailLbl.setBounds(491, 98, 41, 14);
		add(emailLbl);
		
		email_TF = new TextField("sample@yahoo.com");
		email_TF.setBounds(519, 87, 166, 30);
		add(email_TF);
		
		source_TF = new JTextField();
		source_TF.setMinimumSize(new Dimension(20, 20));
		source_TF.setColumns(10);
		source_TF.setBounds(736, 93, 150, 24);
		add(source_TF);
		
		sourceLbl = new JLabel("Source");
		sourceLbl.setBounds(695, 98, 41, 14);
		add(sourceLbl);
		
		specifics_TF = new JTextField();
		specifics_TF.setMinimumSize(new Dimension(20, 20));
		specifics_TF.setColumns(10);
		specifics_TF.setBounds(304, 128, 154, 24);
		add(specifics_TF);
		
		specificsLbl = new JLabel("Source Specifics");
		specificsLbl.setBounds(224, 133, 82, 14);
		add(specificsLbl);
		
		posEndorsedCB = new JComboBox(posEndorsedModel);
		posEndorsedCB.setBounds(528, 125, 166, 30);
		add(posEndorsedCB);
		
		posEndorsedLbl = new JLabel("Position End.");
		posEndorsedLbl.setBounds(462, 133, 70, 14);
		add(posEndorsedLbl);
		
		statusLbl = new JLabel("Status");
		statusLbl.setBounds(705, 133, 64, 14);
		add(statusLbl);
		
		statusCB = new JComboBox();
		statusCB.setModel(new DefaultComboBoxModel(new String[] {"Not Interested", "Already Tapped", "Can't Contact", "Declined", "Failed", "Scheduled", "Call Back - SMS Sent", "No Answer - SMS Sent"}));
		statusCB.setBounds(746, 128, 144, 24);
		add(statusCB);
		
		JLabel schedDateLbl = new JLabel("Sched. Date");
		schedDateLbl.setBounds(10, 168, 70, 14);
		add(schedDateLbl);
		
		schedTimeLbl = new JLabel("Sched. Time");
		schedTimeLbl.setBounds(224, 168, 82, 14);
		add(schedTimeLbl);
		
		schedTime_TF = new TextField("eg. 8:30 am");
		schedTime_TF.setBounds(291, 163, 70, 24);
		add(schedTime_TF);
		
		schedOutcomeCB = new JComboBox();
		schedOutcomeCB.setModel(new DefaultComboBoxModel(new String[] {"", "No show - No answer", "No show - Call declined", "No show - Can't contact", "Endorsed", "Rescheduled", "Failed IDI", "Failed EP Interview", "Withdrew Application", "Failed VERSANT", "Declined Offer", "Active File"}));
		schedOutcomeCB.setBounds(455, 163, 144, 24);
		add(schedOutcomeCB);
		
		schedOutcomeLbl = new JLabel("Sched. Outcome");
		schedOutcomeLbl.setBounds(371, 168, 87, 14);
		add(schedOutcomeLbl);
		
		JLabel reschedDateLbl = new JLabel("Resched. date");
		reschedDateLbl.setBounds(10, 227, 70, 14);
		add(reschedDateLbl);
		
		JLabel reschedTimeLbl = new JLabel("Resched. Time");
		reschedTimeLbl.setBounds(609, 168, 82, 14);
		add(reschedTimeLbl);
		
		reschedTime_TF = new TextField("eg. 8:30 am");
		reschedTime_TF.setBounds(686, 165, 70, 24);
		add(reschedTime_TF);
		
		JLabel reschedOutcomeLbl = new JLabel("Resched. Outcome");
		reschedOutcomeLbl.setBounds(224, 203, 94, 14);
		add(reschedOutcomeLbl);
		
		reschedOutcomeCB = new JComboBox();
		reschedOutcomeCB.setModel(new DefaultComboBoxModel(new String[] {"", "No show - No answer", "No show - Call declined", "No show - Can't contact", "Endorsed", "Rescheduled", "Failed IDI", "Failed EP Interview", "Withdrew Application", "Failed VERSANT", "Declined Offer", "Active File"}));
		reschedOutcomeCB.setBounds(323, 198, 144, 24);
		add(reschedOutcomeCB);
		
		recruiter_TF = new JTextField();
		recruiter_TF.setMinimumSize(new Dimension(20, 20));
		recruiter_TF.setColumns(10);
		recruiter_TF.setBounds(518, 198, 166, 24);
		add(recruiter_TF);
		
		JLabel recruiterLbl = new JLabel("Recruiter");
		recruiterLbl.setBounds(474, 203, 50, 14);
		add(recruiterLbl);
		
		clientName_TF = new JTextField();
		clientName_TF.setMinimumSize(new Dimension(20, 20));
		clientName_TF.setColumns(10);
		clientName_TF.setBounds(756, 198, 134, 24);
		add(clientName_TF);
		
		JLabel clientNameLbl = new JLabel("Client Name");
		clientNameLbl.setBounds(696, 203, 70, 14);
		add(clientNameLbl);
		
		clientResultCB = new JComboBox();
		clientResultCB.setModel(new DefaultComboBoxModel(new String[] {"", "Failed Client Interview", "SP", "Declined Offer", "Withdrawn"}));
		clientResultCB.setBounds(294, 239, 144, 24);
		add(clientResultCB);
		
		JLabel clientResultLbl = new JLabel("Client Result");
		clientResultLbl.setBounds(224, 244, 70, 14);
		add(clientResultLbl);
		
		JLabel remarksLbl = new JLabel("Remarks");
		remarksLbl.setBounds(10, 298, 46, 14);
		add(remarksLbl);
		
		remarks_TA = new JTextArea();
		remarks_TA.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		remarks_TA.setBounds(10, 319, 205, 89);
		add(remarks_TA);
		
		actualRevenue_TF = new JTextField();
		actualRevenue_TF.setMinimumSize(new Dimension(20, 20));
		actualRevenue_TF.setColumns(10);
		actualRevenue_TF.setBounds(553, 244, 166, 24);
		add(actualRevenue_TF);
		
		JLabel actualRevenueLbl = new JLabel("Actual Revenue");
		actualRevenueLbl.setBounds(474, 249, 82, 14);
		add(actualRevenueLbl);
		
		cvPath_TF = new JTextField();
		cvPath_TF.setMinimumSize(new Dimension(20, 20));
		cvPath_TF.setColumns(10);
		cvPath_TF.setBounds(262, 319, 166, 24);
		add(cvPath_TF);
		
		JLabel cvPathLbl = new JLabel("CV Path");
		cvPathLbl.setBounds(260, 298, 46, 14);
		add(cvPathLbl);
		
		JButton cvButton = new JButton("Import CV");
		cvButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = chooser.showSaveDialog(null);
				if(choice == JFileChooser.APPROVE_OPTION){
					try {
					    File file = chooser.getSelectedFile();
					    File newFile = new File("Resumes/"+file.getName()+"");
					    source = Paths.get(file+"");
					    target = Paths.get(newFile+"");
					    
					    cvPath_TF.setText(target+"");
					} catch (HeadlessException e1) { e1.printStackTrace(); }
				}
			}
		});
		cvButton.setBounds(262, 351, 89, 23);
		add(cvButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(757, 618, 133, 30);
		add(saveButton);
		
		backButton = new JButton("Back");
		backButton.setBounds(757, 659, 133, 30);
		add(backButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 536, 205, 78);
		add(scrollPane);
		listModel = new DefaultListModel();
		for(int i =0; i<lastPoscomboBoxItems.size(); i++)
			listModel.addElement(lastPoscomboBoxItems.elementAt(i));
		occupationList = new JList(listModel);
		scrollPane.setViewportView(occupationList);
		
		JButton addWorkButton = new JButton("Add Occupation Name");
		addWorkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField field1 = new JTextField();  
				JComboBox field2 = new JComboBox(new String[]{"Agent", "Non-Agent", "Unclassified"});
				
				Object[] message = { "Occupation Name:", field1,  "Job Classification:", field2};
				int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);  
				if (option == JOptionPane.OK_OPTION){
					String input1 = field1.getText();  
				    String input2 = field2.getSelectedItem().toString();
				    
					boolean no=true;
					if(input1==null || input2==null)
						return;
					
					if(!input1.trim().isEmpty()){
						for(int i=0; i<listModel.size(); i++){
							if(input1.equalsIgnoreCase(""+listModel.getElementAt(i)))
								no=false;
						}
						if(no==true){
							try {
								AccessLayer.getInstance().addOccupationToDatabase(input1.trim(), input2.trim());
								listModel.addElement(input1.trim());
								strValues.add(input2.trim());
								lastPosCB.addItem(input1.trim());
								posEndorsedCB.addItem(input1.trim());
								System.out.println(lastPosCB.getItemAt(lastPosCB.getItemCount()-1));
								System.out.println(lastPosCB.getItemAt(lastPosCB.getItemCount()-2));
								System.out.println(lastPosCB.getItemCount());
						//		((DefaultListCellRenderer)(lastPosCB.getRenderer())).repaint();
						//		((DefaultListCellRenderer)(lastPosCB.getRenderer())).validate();
							} catch (SQLException e1) {	}
						}
						else
							JOptionPane.showMessageDialog(null, "Information already exist!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Fill up all information!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addWorkButton.setBounds(32, 621, 166, 24);
		add(addWorkButton);
		
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0, indeces[] = occupationList.getSelectedIndices(); i<indeces.length; i++){
					AccessLayer.getInstance().removeOccupationFromDatabase(insertBackSlash(listModel.getElementAt(indeces[i]-i).toString()));
					listModel.removeElementAt(indeces[i]-i);
					lastPosModel.removeElementAt(indeces[i]-i);
					posEndorsedModel.removeElementAt(indeces[i]-i);
				}
			}
		});
		removeButton.setBounds(32, 651, 166, 24);
		add(removeButton);
		
		dateSourcedDC = new JDateChooser();
		dateSourcedDC.setDateFormatString("MMM d, yyyy");
		dateSourcedDC.setBounds(10, 94, 186, 20);
		add(dateSourcedDC);
		
		dateProcessedDC = new JDateChooser();
		dateProcessedDC.setDateFormatString("MMM d, yyyy");
		dateProcessedDC.setBounds(10, 36, 186, 20);
		add(dateProcessedDC);
		
		schedDateDC = new JDateChooser();
		schedDateDC.setDateFormatString("MMM d, yyyy");
		schedDateDC.setBounds(10, 196, 186, 20);
		add(schedDateDC);
		
		reschedDateDC = new JDateChooser();
		reschedDateDC.setDateFormatString("MMM d, yyyy");
		reschedDateDC.setBounds(10, 248, 186, 20);
		add(reschedDateDC);
	}
	
	public void clearFields(){
		dateProcessedDC.setDate(null);
		dateSourcedDC.setDate(null);
		firstName_TF.setText("");
		lastName_TF.setText("");
		primaryNum_TF.setText("");
		secNum_TF.setText("");
		school_TF.setText("");
		lastPosCB.setSelectedIndex(0);
		jobClassCB.setSelectedIndex(0);
		lastComp_TF.setText("");
		email_TF.setText("");
		source_TF.setText("");
		specifics_TF.setText("");
		posEndorsedCB.setSelectedIndex(0);
		statusCB.setSelectedIndex(0);
		schedDateDC.setDate(null);
		schedTime_TF.setText("");
		schedOutcomeCB.setSelectedIndex(0);
		reschedDateDC.setDate(null);
		reschedTime_TF.setText("");
		reschedOutcomeCB.setSelectedIndex(0);
		recruiter_TF.setText("");
		clientName_TF.setText("");
		clientResultCB.setSelectedIndex(0);
		remarks_TA.setText("");
		actualRevenue_TF.setText("");
		cvPath_TF.setText("");
	}
	
	/**
	 * Inherited FileFilter class to facilitate reuse when
	 * multiple file filter selections are required. For example
	 * purposes, I used a static nested class, which is defined
	 * as below as a member of our original FileChooserExample
	 * class.
	 */
	static class ExtensionFileFilter 
	    extends javax.swing.filechooser.FileFilter {

	    private java.util.List<String> extensions;
	    private String description;

	    public ExtensionFileFilter(String[] exts, String desc) {
	        if (exts != null) {
	            extensions = new java.util.ArrayList<String>();

	            for (String ext : exts) {

	                // Clean array of extensions to remove "."
	                // and transform to lowercase.
	                extensions.add(
	                    ext.replace(".", "").trim().toLowerCase()
	                );
	            }
	        } // No else need; null extensions handled below.

	        // Using inline if syntax, use input from desc or use
	        // a default value.
	        // Wrap with an if statement to default as well as
	        // avoid NullPointerException when using trim().
	        description = (desc != null) ? desc.trim() : "Custom File List";
	    }

	    // Handles which files are allowed by filter.
	    @Override
	    public boolean accept(File f) {
	    
	        // Allow directories to be seen.
	        if (f.isDirectory()) return true;

	        // exit if no extensions exist.
	        if (extensions == null) return false;
			
	        // Allows files with extensions specified to be seen.
	        for (String ext : extensions) {
	            if (f.getName().toLowerCase().endsWith("." + ext))
	                return true;
	        }

	        // Otherwise file is not shown.
	        return false;
	    }

	    // 'Files of Type' description
	    @Override
	    public String getDescription() {
	        return description;
	    }
	}
	
	public Path getSource(){
		return source;
	}
	
	public Path getTarget(){
		return target;
	}
	
	public void showPanel(int from) {
		setVisible(true);
	}
	
	public String insertBackSlash(String str) {
		String res = "";
		for(int i = 0; i < str.length();i++)
			if(str.charAt(i)=='\'' || str.charAt(i) == '\\')
				res+="\\"+str.charAt(i);
			else
				res+=str.charAt(i);
		return res;
	}
}
