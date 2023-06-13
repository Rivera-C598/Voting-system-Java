package system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.CPasswordField;
import myTheme.Popup;
import myTheme.SigMethods;
import myTheme.Utilities;

public class StartVotingSessionPage extends Popup implements ActionListener {
	static Connection dbconn = DBConnection.connectDB();
	JFrame svsFrame;
	static CButton startBtn;
	CLabel schoolYear, prompt, statusLabel, confirmedLabel;
	CPasswordField adminPassField;
	String sy = null;

	public StartVotingSessionPage() {

		init();

	}

	public void init() {

		title.setText("Start Session Settings");

		nE.add(new Utilities().forStartSessionUtilityPanel(popupFrame, AdminDashboard.adminDashboardFrame,
				AdminDashboard.startSessionBtn));

		if (dbconn != null) {
			try {

				PreparedStatement st = (PreparedStatement) dbconn
						.prepareStatement("SELECT schoolYear, voteEnabled FROM userstable");

				ResultSet res = st.executeQuery();
				if (res.next()) {
					sy = res.getString("schoolYear");
				}
			} catch (SQLException ex) {
				Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		schoolYear = new CLabel("School year:" + sy);
		popupContentBasePanel.add(schoolYear);

		statusLabel = new CLabel();
		statusLabel.setFontSize(30);

		prompt = new CLabel("Enter admin key to start session:");

		adminPassField = new CPasswordField();

		popupContentBasePanel.add(statusLabel);
		popupContentBasePanel.add(prompt);
		popupContentBasePanel.add(adminPassField);

		startBtn = new CButton("Start Session");
		startBtn.addActionListener(this);
		startBtn.setVisible(true);

		confirmedLabel = new CLabel();
		confirmedLabel.setForeground(Color.green);
		confirmedLabel.setVisible(false);
		confirmedLabel.setFontSize(35);

		s.setPreferredSize(new Dimension(0, 80));
		s.add(startBtn);
		s.add(confirmedLabel);
	}

	String aKey;

	int currentAdminLogId;
	static String startedSessionTimeStamp;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startBtn) {
			aKey = String.valueOf(adminPassField.getPassword());
			if (aKey.isEmpty()) {
				statusLabel.setText("Is empty bro wtf");
			} else if (Main.ADMIN_KEY.equals(aKey)) {
				AdminDashboard.startSessionBtn.setSelected(true);
				
				if (dbconn != null) {
					try {

						Statement stmnt = dbconn.createStatement();
						String sql = "UPDATE userstable SET voteEnabled = true";
						stmnt.executeUpdate(sql);

						PreparedStatement st = (PreparedStatement) dbconn
								.prepareStatement("SELECT voteEnabled FROM userstable");

						ResultSet res = st.executeQuery();
						if (res.next()) {
							AdminDashboard.goSignal = res.getBoolean("voteEnabled");
							System.out.println("Go signal Status: " + AdminDashboard.goSignal);

							if (dbconn != null) {
								try {

									PreparedStatement prepStmnt = (PreparedStatement) dbconn
											.prepareStatement("INSERT INTO adminLogs1"
													+ "(adminId, sessionStartedTime)" + "VALUES (?, ?)");

									prepStmnt.setInt(1, Login.userAdminId);
									prepStmnt.setString(2, AdminDashboard.currentSessionTime);
									
									

									prepStmnt.executeUpdate(); 
									
									PreparedStatement prepStmnt2 = (PreparedStatement) dbconn
											.prepareStatement("INSERT INTO adminLogs2"
													+ "(sessionStartedTime)" + "VALUES ( ?)");

									prepStmnt2.setString(1, AdminDashboard.currentSessionTime);
									
									startedSessionTimeStamp = AdminDashboard.currentSessionTime;

									prepStmnt2.executeUpdate(); 
									
									// GET LOG ID currentAdminLogId
									extractAdminLogId();

								} catch (SQLException ex) {
									ex.printStackTrace();
								}
							}

						}

					} catch (SQLException ex) {
						Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				SigMethods.disableGlassPane(AdminDashboard.adminDashboardFrame);
				popupFrame.dispose();
				JOptionPane.showMessageDialog(popupFrame, "Voting Session Started");

				AdminDashboard.adminDashboardFrame.dispose();
				EventQueue.invokeLater(() -> {
					new AdminDashboard();
				});

			} else {
				statusLabel.setText("Sheeet, please try again");
			}
		}

	}

	public static int extractAdminLogId() {
		int logId = 0;
		if (dbconn != null) {
			try {
				Statement st = dbconn.createStatement();
				String getAdminLogIdCommand = "SELECT logId FROM adminLogs1 WHERE sessionStartedTime = '"
						+ startedSessionTimeStamp + "'";
				ResultSet res = st.executeQuery(getAdminLogIdCommand);
				if (res.next()) {
					logId = res.getInt("logId");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return logId;
	}

}
