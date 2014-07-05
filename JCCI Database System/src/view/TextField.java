package view;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class TextField extends JTextField implements FocusListener{
	
	String strLbl;
	
	public TextField(String strLbl){
		addFocusListener(this);
		this.strLbl = strLbl;
		setText(strLbl);
		setForeground(Color.LIGHT_GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(getForeground().equals(Color.LIGHT_GRAY)){
			setText("");
			setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(getText().trim().isEmpty()){
			setForeground(Color.LIGHT_GRAY);
			setText(strLbl);
		}
	}

}
