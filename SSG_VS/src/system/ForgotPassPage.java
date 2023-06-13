package system;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import myTheme.CButton;
import myTheme.Popup;
import myTheme.SigMethods;
import myTheme.Utilities;

public class ForgotPassPage extends Popup implements ActionListener{
	protected CButton submitBtn;
	
	public ForgotPassPage() {
		title.setText("Forgotten Password");
		nE.add(new Utilities().lightUtilityCloseOnlyPanel(popupFrame, Login.loginFrame));
		
		
		
		s.setPreferredSize(new Dimension(0, 80));
		submitBtn = new CButton("Submit");
		submitBtn.addActionListener(this);
		s.add(submitBtn);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitBtn) {
			JOptionPane.showMessageDialog(popupFrame, "Sorry, this feature is not available yet");
			popupFrame.dispose();
			SigMethods.disableGlassPane(Login.loginFrame);
			Login.loginFrame.dispose();
			new Login();
		}

	}
}
