package model;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Date extends JPanel {
	
	public JComboBox<String> month;
	public JComboBox<String> day;
	public JComboBox<String> year;
	
	
	public Date() {
		
		setLayout(null);
		
		String[] months = {"mm","01","02","03","04","05","06","07","08","09","10","11","12"};
		month = new JComboBox<String>(months);
		String[] days = {"dd","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		day = new JComboBox<String>(days);
		String[] years = {"Year"};
		year = new JComboBox<String>(years);
		for(int i = 1900; i <= 2030; i++)
			year.addItem(""+i);
		year.setEditable(true);
		
		month.setBounds(0, 0, month.getPreferredSize().width, month.getPreferredSize().height);
		day.setBounds(75, 0, month.getPreferredSize().width, month.getPreferredSize().height);
		year.setBounds(150, 0, year.getPreferredSize().width, year.getPreferredSize().height);
		
		add(month);
		add(day);
		add(year);
		
		month.addItemListener(new MonthHandler());
		year.addItemListener(new YearHandler());
		
		setPreferredSize(new Dimension(225, month.getPreferredSize().height));
		setOpaque(false);
	}
	
	public void reset() {
		month.setSelectedIndex(0);
		day.setSelectedIndex(0);
		year.setSelectedIndex(0);
	}
	
	private class MonthHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			
			int selectedYear;
			
			if(year.getSelectedIndex() == 0)
				selectedYear = 1888;
			else
				selectedYear = Integer.parseInt(year.getSelectedItem().toString());
			
			if(month.getSelectedIndex() == 1) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}else if(month.getSelectedIndex() == 2) {
				if((selectedYear-1888) % 4 == 0)
					while(day.getItemCount() != 30) {
						if(day.getItemCount() < 30)
							day.addItem(""+(day.getItemCount()));
						else
							day.removeItemAt(day.getItemCount()-1);
					}
				else
					while(day.getItemCount() != 29) {
						if(day.getItemCount() < 29)
							day.addItem(""+(day.getItemCount()));
						else
							day.removeItemAt(day.getItemCount()-1);
					}
			}else if(month.getSelectedIndex() == 3) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}else if(month.getSelectedIndex() == 4) {
				while(day.getItemCount() != 31) {
					if(day.getItemCount() < 31)
						day.addItem(""+(day.getItemCount()));
					else 	
						day.removeItemAt(day.getItemCount()-1);
				}
			}else if(month.getSelectedIndex() == 5) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}else if(month.getSelectedIndex() == 6) {
				while(day.getItemCount() != 31) {
					if(day.getItemCount() < 31)
						day.addItem(""+(day.getItemCount()));
					else
						day.removeItemAt(day.getItemCount()-1);
				}
			}else if(month.getSelectedIndex() == 7) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}else if(month.getSelectedIndex() == 8) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}else if(month.getSelectedIndex() == 9) {
				while(day.getItemCount() != 31) {
					if(day.getItemCount() < 31)
						day.addItem(""+(day.getItemCount()));
					else
						day.removeItemAt(day.getItemCount()-1);
				}
			}else if(month.getSelectedIndex() == 10) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}else if(month.getSelectedIndex() == 11) {
				while(day.getItemCount() != 31) {
					if(day.getItemCount() < 31)
						day.addItem(""+(day.getItemCount()));
					else
						day.removeItemAt(day.getItemCount()-1);
				}
			}else if(month.getSelectedIndex() == 12) {
				while(day.getItemCount() != 32) {
					if(day.getItemCount() < 32)
						day.addItem(""+(day.getItemCount()));
				}
			}
		}
	}
	
	private class YearHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			try{
				int selectedYear = 0;
				if(year.getSelectedIndex() == 0)
					selectedYear = 1888;
				else
					selectedYear = Integer.parseInt(year.getSelectedItem().toString());
				
				if(month.getSelectedIndex() == 2) {
					if((selectedYear-1888) % 4 == 0)
						while(day.getItemCount() != 30) {
							if(day.getItemCount() < 30)
								day.addItem(""+(day.getItemCount()));
							else
								day.removeItemAt(day.getItemCount()-1);
						}
					else
						while(day.getItemCount() != 29) {
							if(day.getItemCount() < 29)
								day.addItem(""+(day.getItemCount()));
							else
								day.removeItemAt(day.getItemCount()-1);
						}
				}
			}catch(Exception e){
				
			}
		}
		
	}
	public String getDate(){
		
		String d = year.getEditor().getItem().toString()+"-"+month.getSelectedItem().toString()+"-"+day.getSelectedItem().toString();
		return d;
	}
	
	public boolean isValidDate(){
		String date = getDate();
		if(date.length()<10 || (!(!isValidNum(date.substring(0, 4)) && !isValidNum(date.substring(5,7)) && !isValidNum(date.substring(8,10))) && (!isValidNum(date.substring(0, 4)) || !isValidNum(date.substring(5,7)) || !isValidNum(date.substring(8,10)))))
			return false;
		return true;
	}

	public static boolean isValidNum(String year) {
		System.out.println(year);
		try{
			int num = Integer.parseInt(year);
			if(num<0)
				throw new Exception();
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public void setDate(String birthDate) {
		System.out.println(birthDate);
		String nums[] = birthDate.split("/");
		month.setSelectedItem(nums[0]);
		day.setSelectedItem(nums[1]);
		year.setSelectedItem(nums[2]);
	}
}