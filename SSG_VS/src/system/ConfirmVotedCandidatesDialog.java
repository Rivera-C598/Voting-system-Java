package system;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.Colors;
import myTheme.CustomFont;
import myTheme.Popup;
import myTheme.SigMethods;
import myTheme.Utilities;
import tableModels.GetPartialCandidates;
import tableModels.MyTableModel;

public class ConfirmVotedCandidatesDialog extends Popup implements ActionListener {

	Connection dbconn = DBConnection.connectDB();

	Font myFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(myFont);

	CButton finalVoteBtn;

	JPanel presPanel, vPresPanel, secPanel, audiPanel, treasuPanel;

	CLabel presPic, presName, presCourseYrSec, vPresPic, vPresName, vPresCourseYrSec, secPic, secName, secCourseYrSec,
			audPic, audName, audCourseYrSec, treasuPic, treasuName, treasuCourseYrSec;

	CLabel presId, vPresId, secId, audId, treasuId;

	public ConfirmVotedCandidatesDialog() {
		initComponents();
	}

	void initComponents() {

		title.setText("Review Selected Candidates");

		popupFrame.setSize(810, 710);
		popupFrame.setLocationRelativeTo(null);

		nE.add(new Utilities().forConfirmSelectedCandidatesUtilityPanel(popupFrame, UserDashboard.userDashboardFrame));

		popupContentBasePanel.setLayout(new GridLayout(1, 1));
		popupContentBasePanel.add(showPartialVotedCandidatesTable());

		finalVoteBtn = new CButton("Submit Vote");
		finalVoteBtn.addActionListener(this);
		finalVoteBtn.setVisible(true);

		s.setPreferredSize(new Dimension(0, 80));
		s.add(finalVoteBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == finalVoteBtn) {
			if (dbconn != null) {

				try {

					String castPresVote = "UPDATE candidates SET votes = votes + 1 WHERE candidateId = (SELECT candidateId FROM partials WHERE runningFor = 'President' AND partials.schoolId = '"
							+ Login.userSchoolId + "')";
					String castVPresVote = "UPDATE candidates SET votes = votes + 1 WHERE candidateId = (SELECT candidateId FROM partials WHERE runningFor = 'Vice-President' AND partials.schoolId = '"
							+ Login.userSchoolId + "')";
					String castSecVote = "UPDATE candidates SET votes = votes + 1 WHERE candidateId = (SELECT candidateId FROM partials WHERE runningFor = 'Secretary' AND partials.schoolId = '"
							+ Login.userSchoolId + "')";
					String castAudVote = "UPDATE candidates SET votes = votes + 1 WHERE candidateId = (SELECT candidateId FROM partials WHERE runningFor = 'Auditor' AND partials.schoolId = '"
							+ Login.userSchoolId + "')";
					String castTreasuVote = "UPDATE candidates SET votes = votes + 1 WHERE candidateId = (SELECT candidateId FROM partials WHERE runningFor = 'Treasurer' AND partials.schoolId = '"
							+ Login.userSchoolId + "')";

					Statement st = dbconn.createStatement();
					st.executeUpdate(castPresVote);
					st.executeUpdate(castVPresVote);
					st.executeUpdate(castSecVote);
					st.executeUpdate(castAudVote);
					st.executeUpdate(castTreasuVote);

					String setHasVotedTrue = "Update userstable SET hasVoted = true WHERE schoolId = '" + Login.userSchoolId
							+ "'";
					st.executeUpdate(setHasVotedTrue);

					UUID testID = UUID.randomUUID();
					String str = testID.toString();
					char arr[] = new char[str.length()];
					for (int i = 0; i < str.length(); i++) {
						arr[i] = str.charAt(i);
					}

					Date date = new Date();
					Timestamp timeClicked = new Timestamp(date.getTime());
					String string = toString(arr);

					st.executeUpdate("UPDATE userstable SET transactionId = '" + string + "' WHERE schoolId = '"
							+ Login.userSchoolId + "'");

					PreparedStatement prepStmnt = (PreparedStatement) dbconn.prepareStatement(
							"INSERT INTO userVoted(transactionId, schoolId, dateVoted) VALUES (?, ?, ?)");
					prepStmnt.setString(1, string);
					prepStmnt.setString(2, Login.userSchoolId);
					prepStmnt.setTimestamp(3, timeClicked);

					prepStmnt.executeUpdate();

					st.executeUpdate(
							"UPDATE userVoted SET votedPres = (SELECT candidateId FROM partials WHERE schoolId = '"
									+ Login.userSchoolId + "' AND runningFor = 'President') WHERE transactionId = '"
									+ string + "' AND schoolId = '" + Login.userSchoolId + "'");
					st.executeUpdate(
							"UPDATE userVoted SET votedVice = (SELECT candidateId FROM partials WHERE schoolId = '"
									+ Login.userSchoolId + "' AND runningFor = 'Vice-President') WHERE transactionId = '"
									+ string + "' AND schoolId = '" + Login.userSchoolId + "'");
					st.executeUpdate(
							"UPDATE userVoted SET votedSec = (SELECT candidateId FROM partials WHERE schoolId = '"
									+ Login.userSchoolId + "' AND runningFor = 'Secretary') WHERE transactionId = '"
									+ string + "' AND schoolId = '" + Login.userSchoolId + "'");
					st.executeUpdate(
							"UPDATE userVoted SET votedAud = (SELECT candidateId FROM partials WHERE schoolId = '"
									+ Login.userSchoolId + "' AND runningFor = 'Auditor') WHERE transactionId = '"
									+ string + "' AND schoolId = '" + Login.userSchoolId + "'");
					st.executeUpdate(
							"UPDATE userVoted SET votedTreasu = (SELECT candidateId FROM partials WHERE schoolId = '"
									+ Login.userSchoolId + "' AND runningFor = 'Treasurer') WHERE transactionId = '"
									+ string + "' AND schoolId = '" + Login.userSchoolId + "'");
					
					Statement st2 = dbconn.createStatement();
					String tableName = null;
					ResultSet res = st2.executeQuery("SELECT tableName FROM userstable WHERE schoolID = '"+Login.userSchoolId+"'");
					if(res.next()) {
						tableName = res.getString("tableName");
					}
					
					st2.executeUpdate("INSERT INTO "+tableName+" SELECT candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.runningFor ,candidates.partylist\r\n"
							+ "FROM userstable INNER JOIN candidates ON userstable.schoolId = candidates.users_schoolId \r\n"
							+ "INNER JOIN uservoted ON uservoted.votedPres = candidates.candidateId \r\n"
							+ "WHERE uservoted.schoolId = '"+Login.userSchoolId+"'");
					
					st2.executeUpdate("INSERT INTO "+tableName+" SELECT candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.runningFor ,candidates.partylist\r\n"
							+ "FROM userstable INNER JOIN candidates ON userstable.schoolId = candidates.users_schoolId \r\n"
							+ "INNER JOIN uservoted ON uservoted.votedVice = candidates.candidateId \r\n"
							+ "WHERE uservoted.schoolId = '"+Login.userSchoolId+"'");
					
					st2.executeUpdate("INSERT INTO "+tableName+" SELECT candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.runningFor ,candidates.partylist\r\n"
							+ "FROM userstable INNER JOIN candidates ON userstable.schoolId = candidates.users_schoolId \r\n"
							+ "INNER JOIN uservoted ON uservoted.votedSec = candidates.candidateId \r\n"
							+ "WHERE uservoted.schoolId = '"+Login.userSchoolId+"'");
					st2.executeUpdate("INSERT INTO "+tableName+" SELECT candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.runningFor ,candidates.partylist\r\n"
							+ "FROM userstable INNER JOIN candidates ON userstable.schoolId = candidates.users_schoolId \r\n"
							+ "INNER JOIN uservoted ON uservoted.votedAud = candidates.candidateId \r\n"
							+ "WHERE uservoted.schoolId = '"+Login.userSchoolId+"'");
					st2.executeUpdate("INSERT INTO "+tableName+" SELECT candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.runningFor ,candidates.partylist\r\n"
							+ "FROM userstable INNER JOIN candidates ON userstable.schoolId = candidates.users_schoolId \r\n"
							+ "INNER JOIN uservoted ON uservoted.votedTreasu = candidates.candidateId \r\n"
							+ "WHERE uservoted.schoolId = '"+Login.userSchoolId+"'");

					

				} catch (SQLException e2) {
					e2.printStackTrace();
				}

				SigMethods.disableGlassPane(UserDashboard.userDashboardFrame);
				popupFrame.dispose();
				JOptionPane.showMessageDialog(popupFrame, "Vote Submitted!");

				UserDashboard.userDashboardFrame.dispose();
				EventQueue.invokeLater(() -> {
					new UserDashboard();
				});

			}
		}

	}

	private ArrayList<GetPartialCandidates> getPartialCandidatesData() {

		ArrayList<GetPartialCandidates> list = new ArrayList<GetPartialCandidates>();

		if (dbconn != null) {
			try {
				GetPartialCandidates candidates;

				String getAllPartialCandidatesCommand = "SELECT * FROM partials WHERE schoolId = '" + Login.userSchoolId
						+ "'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getAllPartialCandidatesCommand);

				while (res.next()) {

					candidates = new GetPartialCandidates(res.getInt("candidateId"), res.getBytes("img"),
							"" + res.getString("lastName") + ", " + res.getString("firstName") + "",
							res.getString("course"), res.getString("yearNSection"), res.getString("runningFor"),
							res.getString("partylist"), res.getString("schoolId"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public JScrollPane showPartialVotedCandidatesTable() {

		JTable partialTable = new JTable();
		partialTable.setDefaultEditor(Object.class, null);
		partialTable.setFillsViewportHeight(true);
		partialTable.setBackground(Colors.LIGHT);
		partialTable.setFont(myFont);
		partialTable.setForeground(Colors.DARK);

		partialTable.setRowSelectionAllowed(true);
		partialTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = partialTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetPartialCandidates> list = getPartialCandidatesData();
		String[] columnNames = { "Id", "Position", "Img", "Name", "Course", "Yr/Section", "PartyList" };
		Object[][] rows = new Object[list.size()][7];
		for (int i = 0; i < list.size(); i++) {
			rows[i][0] = list.get(i).getCandId();
			rows[i][1] = list.get(i).getCandPosition();
			if (list.get(i).getCandImage() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImage()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][2] = image;
			} else {
				rows[i][2] = null;
			}
			rows[i][3] = list.get(i).getCandName();
			rows[i][4] = list.get(i).getCandCourse();
			rows[i][5] = list.get(i).getCandYearAndSection();
			rows[i][6] = list.get(i).getCandPartyList();
		}

		MyTableModel model = new MyTableModel(rows, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 2) {
					return Icon.class;
				} else {
					return getValueAt(0, column).getClass();
				}
			}
		};
		partialTable.setModel(model);

		adjustTableSizeAccordingToContent(partialTable);

		JScrollPane scrollPane = new JScrollPane(partialTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		return scrollPane;

	}

	public void adjustTableSizeAccordingToContent(JTable table) {
		TableColumnModel columnModel = table.getColumnModel();
		TableCellRenderer renderer;
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
			}
			if (width > 200)
				width = 200;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		table.setRowHeight(100);
	}

	public String toString(char arr[]) {
		String string = new String(arr);

		return string;
	}

}
