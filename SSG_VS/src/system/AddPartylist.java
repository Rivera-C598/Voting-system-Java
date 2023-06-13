package system;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.CTextField;
import myTheme.CustomFont;
import myTheme.Popup;
import myTheme.SigMethods;
import myTheme.Utilities;

public class AddPartylist extends Popup implements ActionListener {

	Connection dbconn = DBConnection.connectDB();
	Font myFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(myFont);

	CButton submitBtn;
	CTextField partyListTf;
	CLabel promptLabel;

	public AddPartylist() {
		title.setText("Add new Partylist");

		nE.add(new Utilities().forPrompts(popupFrame, AdminDashboard.adminDashboardFrame));

		popupContentBasePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		submitBtn = new CButton("Add Partylist");
		submitBtn.addActionListener(this);
		promptLabel = new CLabel("Type in a new Partylist");
		popupContentBasePanel.add(promptLabel);

		partyListTf = new CTextField();
		partyListTf.setPreferredSize(new Dimension(305, 40));
		partyListTf.setFont(new Font(fontString, 0, 20));
		partyListTf.setCursor(new Cursor(Cursor.HAND_CURSOR));


		popupContentBasePanel.add(partyListTf);
		popupContentBasePanel.add(submitBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String partyListString = null;
		partyListString = partyListTf.getText();

		if (partyListString.isEmpty()) {
			JOptionPane.showMessageDialog(popupFrame, "Please enter a value", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			String userInput = null;
			userInput = JOptionPane.showInputDialog(popupFrame, "Please enter Admin key");
			if (userInput.equals(Main.ADMIN_KEY)) {
				if (dbconn != null) {
					try {
						String addPartylistsCommand = "INSERT INTO partyLists(partyListName) VALUES (?)";
						PreparedStatement prepStmnt = (PreparedStatement) dbconn.prepareStatement(addPartylistsCommand);
						prepStmnt.setString(1, partyListString);
						prepStmnt.executeUpdate();
						
						SigMethods.disableGlassPane(AdminDashboard.adminDashboardFrame);
						popupFrame.dispose();
						JOptionPane.showMessageDialog(popupFrame, "New Partylist Added", "Success", JOptionPane.INFORMATION_MESSAGE);
						AdminDashboard.adminDashboardFrame.dispose();
						new AdminDashboard();

					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(popupFrame, "Incorrect admin key, please try again", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}
