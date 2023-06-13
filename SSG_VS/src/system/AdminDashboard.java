package system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.CPanel;
import myTheme.CTextField;
import myTheme.Colors;
import myTheme.CustomFont;
import myTheme.SigMethods;
import myTheme.Utilities;
import tableModels.GetElectionStatistics;
import tableModels.MyTableModel;
import tableModels.NumberedTableModel;
import tableModels.TFLimit;

public class AdminDashboard implements ActionListener, Runnable {

	Font myFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(myFont);
	Connection dbconn = DBConnection.connectDB();

	// Variables Declaration
	// Local Variables
	protected JPanel hcpN, hcpL, hcpR, hcpC, backToLoginPanel, accN, accL, accR, accC, leftNavIconsPanel, homeIconPanel,
			accIconPanel, lgoutPanel, hcpNorthBasePanel, hcpNW, hcpNE, clockPanel, acNorth, acSouth, acEast, acWest,
			acCenter, addCandidatePanel, hcpS, idPanel, lastNamePanel, firstNamePanel, coursePanel, yearNSecPanel,
			startSessionPanel;
	protected CLabel backToLoginBtn, accDetailsLabel, schoolIdLabel, emailLabel, lName, fName, courseLbl, yrNSecLabel,
			userTypeLabel, homeIcon, accIcon, lgoutIcon, timeLabel, dayLabel, dateLabel, addCandidateLabel, idLabel,
			courseLabel, yearNSecLabel, firstNameLabel, lastNameLabel, addCPic, onlineStatusIcon, offlineStatusIcon;
	protected CButton addCandidateBtn, registerCandidateBtn, viewCandidatesBtn, elecStatsBtn, manageUsersBtn, resetBtn,
			cMButton;
	protected JToggleButton menuIcon;
	protected CPanel addCandidatesBasePanel, elecStatsBasePanel, cMBasePanel, manageUsersBasePanel, resetPanel,
			voteSessionHistoryPanel;
	protected CTextField idTf, yearNSecTf, firstNameTf, lastNameTf;
	protected static JComboBox<String> partyList;
	protected JComboBox<String> posChoices, course;
	protected JFileChooser fileChooser;

	// For Jtable (Voting Session History Panel)
	protected JTable votingSessionHistoryTable;
	protected JScrollPane scrollPane;
	protected DefaultTableModel model;

	// For Add candidate panel
	String imgPathString = null;

	BufferedImage bufferedImage;

	// Global and static variables
	public static JFrame adminDashboardFrame;
	protected static JPanel hcpCSubN, hcpBasePanel, leftNavPanel, accBasePanel;
	protected static CLabel welcomeText, voteSessionStarted, voteSessionDuration, voteSessionEnded;
	public static JToggleButton startSessionBtn;

	// For Clock
	static SimpleDateFormat timeFormat, dayFormat, dateFormat;
	static String time, day, date, toolTipDate;
	static boolean hasLoggedOut, goSignal;

	static int second, minute, hour;

	static String currentSessionTime, startSession_EndTime, startSession_Duration;

	static Date timerStartedTime;
	static String timerStartedTimeString;

	// When a new AdminDashboard is called:
	public AdminDashboard() {

		// Initializes all components
		initComponents();
		// START DIGITAL CLOCK
		Thread thread = new Thread(this);
		thread.start();
	}

	public void initComponents() {
		// CREATE THE BASE FRAME FOR THE ADMIN DASHBOARD
		setAdminDashboardFrame();

		// CREATE THE ACCOUNT PANEL FOR THE LEFT NAVBAR FOR THE ADMIN DASHBOARD
		setAccountPanel();

		// CREATE THE LEFT NAVBAR
		setLeftNavbar();

		// CREATE THE MAIN INTERFACE FOR THE ADMIN DASHBOARD
		setHomePanel();

		// INITIALIZE ADMIN DASHBOARD BUTTONS
		setAdminDashboardButtons();

		// CREATE ADMIN DASHBOARD PANELS
		setAddCandidatesPanel();
		setElectionStatisticsPanel();
		setForCampaignManagersPanel();
		setManageUsersPanel();
		setResetPanel();
		setSessionHistoryPanel();

		// ADD THE BUTTONS TO THE ADMIN DASHBOARD (ADD ONLY)
		addButtonsToAdminDashboard();

		// ADD THE PANELS TO THE ADMIN DASHBOARD (ADD ONLY)
		addPanelsToAdminDashboard();

		// FINAL TOUCHES
		compileBasePanels();
	}

	public void compileBasePanels() {
		adminDashboardFrame.add(accBasePanel);
		adminDashboardFrame.add(leftNavPanel, BorderLayout.WEST);
		adminDashboardFrame.add(hcpBasePanel, BorderLayout.EAST);

		adminDashboardFrame.setVisible(true);
	}

	public void addPanelsToAdminDashboard() {
		// ADD CPANELS
		adminDashboardFrame.add(newPage(addCandidatesBasePanel, "Add Candidate"));
		adminDashboardFrame.add(newPage(elecStatsBasePanel, "Election Statistics"));
		adminDashboardFrame.add(newPage(cMBasePanel, "For Campaign Managers"));
		adminDashboardFrame.add(newPage(manageUsersBasePanel, "Manage Users"));
		adminDashboardFrame.add(newPage(resetPanel, "Reset"));
		adminDashboardFrame.add(newPage(voteSessionHistoryPanel, "Voting Session history"));
	}

	public void addButtonsToAdminDashboard() {
		// ADDING OF BUTTONS TO THE ADMIN DASHBOARD
		hcpCSubN.add(addCandidatePanel);
		hcpCSubN.add(elecStatsBtn);
		hcpCSubN.add(cMButton);
		hcpCSubN.add(manageUsersBtn);
		hcpCSubN.add(resetBtn);
	}

	public void setResetPanel() {
		resetPanel = new CPanel();
		JPanel root = new JPanel(new BorderLayout());
		root.setBackground(Colors.LIGHT);
		root.setPreferredSize(new Dimension(610, 60));
		CLabel label = new CLabel("Select reset configuration");
		CButton resetAll = new CButton("Reset all");
		resetAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int response = JOptionPane.showConfirmDialog(adminDashboardFrame,
						"THIS BUTTON RESETS THE SYSTEM"
								+ "\nUsers may have to register again, this account will be deleted"
								+ "\nWould you like to continue?",
						"All data deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == 0) {
					String userInput = JOptionPane.showInputDialog(adminDashboardFrame,
							"Are you sure you want to delete all data? if so, \nPlease Enter the admin key to continue:");
					if (userInput.equals(Main.ADMIN_KEY)) {
						try {
							Statement st = dbconn.createStatement();
							ResultSet res = st.executeQuery("SELECT tableName FROM userstable WHERE isAdmin = 0");
							Statement st2 = dbconn.createStatement();
							while (res.next()) {
								st2.execute("DROP TABLE " + res.getString("tableName") + "");
							}
							st2.execute("DELETE FROM userstable");
							st2.execute("DELETE FROM admins");
							st2.execute("DELETE FROM candidates");
							st2.execute("DELETE FROM partialvotedcandidates");
							st2.execute("DELETE FROM partials");
							st2.execute("DELETE FROM uservoted");
							st2.execute("DELETE FROM adminlogs1");
							st2.execute("DELETE FROM adminlogs2");
							st2.execute("DELETE FROM partylists");

							adminDashboardFrame.dispose();

							EventQueue.invokeLater(new Runnable() {
								public void run() {

									new Login();
								}
							});

						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					} else
						JOptionPane.showMessageDialog(adminDashboardFrame, "Incorrect Admin Key, please try again");
				}
			}

		});
		CButton resetVoting = new CButton("Reset Votings");
		resetVoting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(adminDashboardFrame,
						"This button only resets voting data, "
								+ "\nYou may have to add candidates and partylists again to the voting pool "
								+ "\nWould you like to continue?",
						"Votings data deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == 0) {
					String userInput = JOptionPane.showInputDialog(adminDashboardFrame,
							"Are you sure you want to delete all voting data? if so, \nPlease Enter the admin key to continue:");
					if (userInput.equals(Main.ADMIN_KEY)) {
						try {
							Statement stmnt = dbconn.createStatement();
							stmnt.executeUpdate(
									"UPDATE userstable SET isCandidate = 0, hasVoted = 0, partialRegistered = 0, transactionId = null");

							Statement st = dbconn.createStatement();
							ResultSet res = st.executeQuery("SELECT tableName FROM userstable WHERE isAdmin = 0");
							Statement st2 = dbconn.createStatement();
							while (res.next()) {
								st2.execute("DELETE FROM " + res.getString("tableName") + "");
							}
							st2.execute("DELETE FROM candidates");
							st2.execute("DELETE FROM partialvotedcandidates");
							st2.execute("DELETE FROM partials");
							st2.execute("DELETE FROM uservoted");
							st2.execute("DELETE FROM adminlogs1");
							st2.execute("DELETE FROM adminlogs2");
							st2.execute("DELETE FROM partylists");

							refresh();
							JOptionPane.showMessageDialog(adminDashboardFrame, "Voting Data deleted!");

						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					} else
						JOptionPane.showMessageDialog(adminDashboardFrame, "Incorrect Admin Key, please try again");
				} else {
					System.out.println("no");
				}

			}

		});
		root.add(resetAll, BorderLayout.WEST);
		root.add(resetVoting, BorderLayout.EAST);
		resetPanel.contentBasePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 300, 5));
		resetPanel.contentBasePanel.add(label);
		resetPanel.contentBasePanel.add(root);

	}

	public void setManageUsersPanel() {
		manageUsersBasePanel = new CPanel();
		manageUsersBasePanel.contentBasePanel.setBackground(Colors.LIGHT);
		manageUsersBasePanel.contentBasePanel.add(showAllUsersTableWithScrollPane(), BorderLayout.CENTER);
	}

	public void setForCampaignManagersPanel() {
		cMBasePanel = new CPanel();
		cMBasePanel.contentBasePanel.add(showTransactionsTable(), BorderLayout.CENTER);
	}

	// SHOW ALL USERS TABLE
	private JScrollPane showTransactionsTable() {
		JTable transactionsTable = new JTable();
		transactionsTable.setDefaultEditor(Object.class, null);
		transactionsTable.setBackground(Colors.LIGHT);
		transactionsTable.setFont(myFont);
		transactionsTable.setForeground(Colors.DARK);
		transactionsTable.setFillsViewportHeight(true);
		transactionsTable.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		JTableHeader header = transactionsTable.getTableHeader();
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);
		header.setFont(new Font(fontString, 0, 22));

		DefaultTableModel model = new DefaultTableModel();
		String[] columnNames = { "Transaction Id", "Voted on", "School Id" };
		model.setColumnIdentifiers(columnNames);
		transactionsTable.setModel(model);

		JScrollPane scrollPane = new JScrollPane(transactionsTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// load table data
		if (dbconn != null) {
			try {

				String getData = "SELECT transactionId, dateVoted, schoolId FROM userVoted";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getData);
				while (res.next()) {
					String transactionId = res.getString("transactionId");
					Timestamp dateVoted = res.getTimestamp("dateVoted");
					String schoolId = res.getString("schoolId");
					model.addRow(new Object[] { transactionId, dateVoted, schoolId });

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		adjustTableSizeAccordingToContent(transactionsTable);
		return scrollPane;
	}

	public void setElectionStatisticsPanel() {
		UIManager.put("TabbedPane.selected", Colors.DARK);
		JTabbedPane rootTabbedPane = new JTabbedPane() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Color getForegroundAt(int index) {
				if (getSelectedIndex() == index)
					return Colors.LIGHT;
				return Colors.DARK;
			}
		};
		rootTabbedPane.setBackground(Colors.LIGHT);
		rootTabbedPane.setFont(new Font(fontString, 0, 22));
		rootTabbedPane.add("Presidents", showPresidentStatsTableData());
		rootTabbedPane.add("Vice-Presidents", showVicePresidentStatsTableData());
		rootTabbedPane.add("Secretarys", showSecretaryStatsTableData());
		rootTabbedPane.add("Auditors", showAuditorStatsTableData());
		rootTabbedPane.add("Treasurers", showTreasurerStatsTableData());

		elecStatsBasePanel = new CPanel();
		/*
		 * JSplitPane splitPanel = new JSplitPane(SwingConstants.VERTICAL,
		 * rootTabbedPane, showPresidentStatsTableData());
		 * splitPanel.setOrientation(SwingConstants.VERTICAL);
		 */
		elecStatsBasePanel.contentBasePanel.add(rootTabbedPane, BorderLayout.CENTER);
	}

	private ArrayList<GetElectionStatistics> getPresidentVotingStats() {
		ArrayList<GetElectionStatistics> list = new ArrayList<GetElectionStatistics>();
		if (dbconn != null) {
			try {
				GetElectionStatistics stats;

				String getStats = "SELECT candidates.img, userstable.lastName, userstable.firstName, candidates.partylist, candidates.votes\r\n"
						+ "FROM userstable JOIN candidates WHERE userstable.schoolId = candidates.users_schoolId AND candidates.runningFor = 'President' ORDER BY votes DESC";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getStats);

				while (res.next()) {
					stats = new GetElectionStatistics(
							res.getBytes("candidates.img"), "" + res.getString("userstable.lastName") + ", "
									+ res.getString("userstable.firstName") + "",
							res.getString("candidates.partyList"), res.getInt("candidates.votes"));
					list.add(stats);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetElectionStatistics> getVicePresidentVotingStats() {
		ArrayList<GetElectionStatistics> list = new ArrayList<GetElectionStatistics>();
		if (dbconn != null) {
			try {
				GetElectionStatistics stats;

				String getStats = "SELECT candidates.img, userstable.lastName, userstable.firstName, candidates.partylist, candidates.votes\r\n"
						+ "FROM userstable JOIN candidates WHERE userstable.schoolId = candidates.users_schoolId AND candidates.runningFor = 'Vice-President' ORDER BY votes DESC";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getStats);

				while (res.next()) {
					stats = new GetElectionStatistics(
							res.getBytes("candidates.img"), "" + res.getString("userstable.lastName") + ", "
									+ res.getString("userstable.firstName") + "",
							res.getString("candidates.partyList"), res.getInt("candidates.votes"));
					list.add(stats);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetElectionStatistics> getSecretaryVotingStats() {
		ArrayList<GetElectionStatistics> list = new ArrayList<GetElectionStatistics>();
		if (dbconn != null) {
			try {
				GetElectionStatistics stats;

				String getStats = "SELECT candidates.img, userstable.lastName, userstable.firstName, candidates.partylist, candidates.votes\r\n"
						+ "FROM userstable JOIN candidates WHERE userstable.schoolId = candidates.users_schoolId AND candidates.runningFor = 'Secretary' ORDER BY votes DESC";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getStats);

				while (res.next()) {
					stats = new GetElectionStatistics(
							res.getBytes("candidates.img"), "" + res.getString("userstable.lastName") + ", "
									+ res.getString("userstable.firstName") + "",
							res.getString("candidates.partyList"), res.getInt("candidates.votes"));
					list.add(stats);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetElectionStatistics> getAuditorVotingStats() {
		ArrayList<GetElectionStatistics> list = new ArrayList<GetElectionStatistics>();
		if (dbconn != null) {
			try {
				GetElectionStatistics stats;

				String getStats = "SELECT candidates.img, userstable.lastName, userstable.firstName, candidates.partylist, candidates.votes\r\n"
						+ "FROM userstable JOIN candidates WHERE userstable.schoolId = candidates.users_schoolId AND candidates.runningFor = 'Auditor' ORDER BY votes DESC";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getStats);

				while (res.next()) {
					stats = new GetElectionStatistics(
							res.getBytes("candidates.img"), "" + res.getString("userstable.lastName") + ", "
									+ res.getString("userstable.firstName") + "",
							res.getString("candidates.partyList"), res.getInt("candidates.votes"));
					list.add(stats);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetElectionStatistics> getTreasurertVotingStats() {
		ArrayList<GetElectionStatistics> list = new ArrayList<GetElectionStatistics>();
		if (dbconn != null) {
			try {
				GetElectionStatistics stats;

				String getStats = "SELECT candidates.img, userstable.lastName, userstable.firstName, candidates.partylist, candidates.votes\r\n"
						+ "FROM userstable JOIN candidates WHERE userstable.schoolId = candidates.users_schoolId AND candidates.runningFor = 'Treasurer' ORDER BY votes DESC";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getStats);

				while (res.next()) {
					stats = new GetElectionStatistics(
							res.getBytes("candidates.img"), "" + res.getString("userstable.lastName") + ", "
									+ res.getString("userstable.firstName") + "",
							res.getString("candidates.partyList"), res.getInt("candidates.votes"));
					list.add(stats);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public JScrollPane showPresidentStatsTableData() {

		Dimension minSize = new Dimension(300, 0);

		JTable presStats = new JTable();
		presStats.setDefaultEditor(Object.class, null);
		presStats.setFillsViewportHeight(true);
		presStats.setBackground(Colors.LIGHT);
		presStats.setFont(myFont);
		presStats.setForeground(Colors.DARK);

		presStats.setRowSelectionAllowed(true);
		presStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = presStats.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetElectionStatistics> list = getPresidentVotingStats();
		String[] columnNames = { "Img", "Name", "PartyList", "Votes" };
		Object[][] rows = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImg() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImg()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandPartyList();
			rows[i][3] = list.get(i).getVotes();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);

		presStats.setModel(new NumberedTableModel(model) {
			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "Rank";
				return myNumberedModel.getColumnName(columnIndex - 1);

			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {

				if (columnIndex == 0) {

					return "#" + Integer.valueOf(rowIndex + 1);
				}
				return myNumberedModel.getValueAt(rowIndex, columnIndex - 1);
			}

		});
		adjustTable(presStats);

		JScrollPane scrollPane = new JScrollPane(presStats);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		scrollPane.setMinimumSize(minSize);

		return scrollPane;

	}

	public JScrollPane showVicePresidentStatsTableData() {

		Dimension minSize = new Dimension(300, 0);

		JTable vicePresStats = new JTable();
		vicePresStats.setDefaultEditor(Object.class, null);
		vicePresStats.setFillsViewportHeight(true);
		vicePresStats.setBackground(Colors.LIGHT);
		vicePresStats.setFont(myFont);
		vicePresStats.setForeground(Colors.DARK);

		vicePresStats.setRowSelectionAllowed(true);
		vicePresStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = vicePresStats.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetElectionStatistics> list = getVicePresidentVotingStats();
		String[] columnNames = { "Img", "Name", "PartyList", "Votes" };
		Object[][] rows = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImg() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImg()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandPartyList();
			rows[i][3] = list.get(i).getVotes();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);

		vicePresStats.setModel(new NumberedTableModel(model) {
			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "Rank";
				return myNumberedModel.getColumnName(columnIndex - 1);

			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {

				if (columnIndex == 0) {

					return "#" + Integer.valueOf(rowIndex + 1);
				}
				return myNumberedModel.getValueAt(rowIndex, columnIndex - 1);
			}

		});
		adjustTable(vicePresStats);

		JScrollPane scrollPane = new JScrollPane(vicePresStats);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		scrollPane.setMinimumSize(minSize);

		return scrollPane;

	}

	public JScrollPane showSecretaryStatsTableData() {

		Dimension minSize = new Dimension(300, 0);

		JTable secStats = new JTable();
		secStats.setDefaultEditor(Object.class, null);
		secStats.setFillsViewportHeight(true);
		secStats.setBackground(Colors.LIGHT);
		secStats.setFont(myFont);
		secStats.setForeground(Colors.DARK);

		secStats.setRowSelectionAllowed(true);
		secStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = secStats.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetElectionStatistics> list = getSecretaryVotingStats();
		String[] columnNames = { "Img", "Name", "PartyList", "Votes" };
		Object[][] rows = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImg() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImg()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandPartyList();
			rows[i][3] = list.get(i).getVotes();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);

		secStats.setModel(new NumberedTableModel(model) {
			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "Rank";
				return myNumberedModel.getColumnName(columnIndex - 1);

			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {

				if (columnIndex == 0) {

					return "#" + Integer.valueOf(rowIndex + 1);
				}
				return myNumberedModel.getValueAt(rowIndex, columnIndex - 1);
			}

		});
		adjustTable(secStats);

		JScrollPane scrollPane = new JScrollPane(secStats);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		scrollPane.setMinimumSize(minSize);

		return scrollPane;

	}

	public JScrollPane showAuditorStatsTableData() {

		Dimension minSize = new Dimension(300, 0);

		JTable auditorStats = new JTable();
		auditorStats.setDefaultEditor(Object.class, null);
		auditorStats.setFillsViewportHeight(true);
		auditorStats.setBackground(Colors.LIGHT);
		auditorStats.setFont(myFont);
		auditorStats.setForeground(Colors.DARK);

		auditorStats.setRowSelectionAllowed(true);
		auditorStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = auditorStats.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetElectionStatistics> list = getAuditorVotingStats();
		String[] columnNames = { "Img", "Name", "PartyList", "Votes" };
		Object[][] rows = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImg() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImg()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandPartyList();
			rows[i][3] = list.get(i).getVotes();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);

		auditorStats.setModel(new NumberedTableModel(model) {
			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "Rank";
				return myNumberedModel.getColumnName(columnIndex - 1);

			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {

				if (columnIndex == 0) {

					return "#" + Integer.valueOf(rowIndex + 1);
				}
				return myNumberedModel.getValueAt(rowIndex, columnIndex - 1);
			}

		});
		adjustTable(auditorStats);

		JScrollPane scrollPane = new JScrollPane(auditorStats);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		scrollPane.setMinimumSize(minSize);

		return scrollPane;

	}

	public JScrollPane showTreasurerStatsTableData() {

		Dimension minSize = new Dimension(300, 0);

		JTable treasurerStats = new JTable();
		treasurerStats.setDefaultEditor(Object.class, null);
		treasurerStats.setFillsViewportHeight(true);
		treasurerStats.setBackground(Colors.LIGHT);
		treasurerStats.setFont(myFont);
		treasurerStats.setForeground(Colors.DARK);

		treasurerStats.setRowSelectionAllowed(true);
		treasurerStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = treasurerStats.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetElectionStatistics> list = getTreasurertVotingStats();
		String[] columnNames = { "Img", "Name", "PartyList", "Votes" };
		Object[][] rows = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImg() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImg()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandPartyList();
			rows[i][3] = list.get(i).getVotes();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);

		treasurerStats.setModel(new NumberedTableModel(model) {
			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "Rank";
				return myNumberedModel.getColumnName(columnIndex - 1);

			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {

				if (columnIndex == 0) {

					return "#" + Integer.valueOf(rowIndex + 1);
				}
				return myNumberedModel.getValueAt(rowIndex, columnIndex - 1);
			}

		});
		adjustTable(treasurerStats);

		JScrollPane scrollPane = new JScrollPane(treasurerStats);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		scrollPane.setMinimumSize(minSize);

		return scrollPane;

	}

	public void setAdminDashboardFrame() {
		adminDashboardFrame = new JFrame();
		ImageIcon JFrameLogo = new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/SSGLogo.png"));
		SigMethods.draggable(adminDashboardFrame);
		adminDashboardFrame.setIconImage(JFrameLogo.getImage());
		adminDashboardFrame.setSize(1100, 750);
		adminDashboardFrame.setLocationRelativeTo(null);
		adminDashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminDashboardFrame.setUndecorated(true);
		adminDashboardFrame.setLayout(new BorderLayout());
	}

	public void setAdminDashboardButtons() {
		// Admin Dashboard Buttons
		hcpCSubN.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
		addCandidatePanel = new JPanel();
		addCandidatePanel.setBackground(Colors.LIGHT);

		addCandidateBtn = new CButton();
		addCandidateLabel = new CLabel("Add Candidate");
		addCandidateLabel.setFontSize(30);
		addCandidateLabel.setForeground(Colors.DARK);

		ImageIcon addIcon = new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/iconAdd1_50px.png"));
		addCandidateBtn.setPreferredSize(new Dimension(60, 60));
		addCandidateBtn.setIcon(addIcon);
		addCandidateBtn.addActionListener(this);
		addCandidateBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				addCandidateBtn
						.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/iconAdd2.png")));
			}

			public void mouseExited(MouseEvent evt) {
				addCandidateBtn.setIcon(
						new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/iconAdd1_50px.png")));
			}
		});
		addCandidatePanel.add(addCandidateBtn);
		addCandidatePanel.add(addCandidateLabel);

		// FOR VIEW ELECTION STATISTICS BUTTON
		elecStatsBtn = new CButton("Election Statistics");
		elecStatsBtn.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/analytics1_50px.png")));
		elecStatsBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				elecStatsBtn.setIcon(
						new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/analytics_50px.png")));
			}

			public void mouseExited(MouseEvent evt) {
				elecStatsBtn.setIcon(
						new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/analytics1_50px.png")));
			}
		});
		elecStatsBtn.setPreferredSize(new Dimension(300, 60));
		elecStatsBtn.setFontSize(20);
		elecStatsBtn.addActionListener(this);

		// FOR CAMPAIGN MANAGER
		cMButton = new CButton("For Campaign Managers");
		cMButton.setFontSize(20);
		cMButton.addActionListener(this);

		// FOR MANAGE USERS
		manageUsersBtn = new CButton("Manage Users");
		manageUsersBtn.addActionListener(this);

		// FOR VIEW PARTYLIST
		resetBtn = new CButton("Reset");
		resetBtn.addActionListener(this);

		setStartSessionButton();
	}

	public void setStartSessionButton() {
		// startSessionPanel
		startSessionPanel = new JPanel();
		startSessionPanel.setPreferredSize(new Dimension(550, 80));
		startSessionPanel.setBackground(Colors.LIGHT);

		onlineStatusIcon = new CLabel();
		onlineStatusIcon.setToolTipText("Status: ONLINE");
		onlineStatusIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		onlineStatusIcon
				.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/online_icon_50px.png")));
		onlineStatusIcon.setVisible(false);
		onlineStatusIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelSwitch(voteSessionHistoryPanel);
			}

		});

		offlineStatusIcon = new CLabel();
		offlineStatusIcon.setToolTipText("Status: OFFLINE");
		offlineStatusIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		offlineStatusIcon
				.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/offline_icon_50px.png")));
		offlineStatusIcon.setVisible(false);
		offlineStatusIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelSwitch(voteSessionHistoryPanel);
			}

		});

		startSessionBtn = new JToggleButton();
		startSessionBtn.setPreferredSize(new Dimension(400, 60));
		startSessionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		startSessionBtn.setRolloverEnabled(false);
		startSessionBtn.setBackground(Colors.DARK);
		startSessionBtn.setFocusable(false);
		startSessionBtn.setForeground(Colors.LIGHT);
		startSessionBtn.setFont(new Font(fontString, 0, 30));

		checkGoSignalStatus();

		if (goSignal == true) {
			startSessionBtn.setSelected(true);
			startSessionBtn.setText("Stop Voting Session");
			startSessionBtn.setForeground(Colors.LIGHT);
			startSessionBtn.setBorder(BorderFactory.createLineBorder(Color.red));
			startSessionBtn.setContentAreaFilled(false);
			startSessionBtn.setOpaque(true);
			startSessionBtn.setBackground(Colors.DARKEST);
			onlineStatusIcon.setVisible(true);
			offlineStatusIcon.setVisible(false);
		} else {
			startSessionBtn.setSelected(false);
			startSessionBtn.setText("Start Voting Session");
			offlineStatusIcon.setVisible(true);
			onlineStatusIcon.setVisible(false);
		}

		startSessionBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startSessionBtn.isSelected()) {
					new StartVotingSessionPage();
					refresh();
					SigMethods.enableGlassPane(AdminDashboard.adminDashboardFrame);

				} else {
					refresh();

					String pass = JOptionPane.showInputDialog(null, "Please enter admin key to stop voting session: ");
					if (pass.equals(Main.ADMIN_KEY)) {
						JOptionPane.showMessageDialog(null, "Voting Session Stopped", "confirmation message",
								JOptionPane.INFORMATION_MESSAGE);
						if (dbconn != null) {
							try {
								Statement stmnt = dbconn.createStatement();
								String sql = "UPDATE userstable SET voteEnabled = false";
								stmnt.executeUpdate(sql);
								PreparedStatement st = (PreparedStatement) dbconn
										.prepareStatement("SELECT voteEnabled FROM userstable");

								ResultSet res = st.executeQuery();
								if (res.next()) {
									goSignal = res.getBoolean("voteEnabled");

									// GET SESSION ENDED TIME AND DURATION;

									String getLastSessionTimeCommand = "SELECT adminLogs1.sessionStartedTime FROM adminLogs1 JOIN adminLogs2 WHERE adminLogs1.sessionStartedTime = adminLogs2.sessionStartedTime ORDER BY logId DESC LIMIT 1";

									Statement getSessionTimeStmnt = dbconn.createStatement();
									ResultSet rs = getSessionTimeStmnt.executeQuery(getLastSessionTimeCommand);

									String lastSessionTime = null;
									if (rs.next()) {
										lastSessionTime = rs.getString("sessionStartedTime");

										try {
											String updateSessionEndedTimeCommand = "UPDATE adminLogs2 SET adminId = "
													+ Login.userAdminId + ", sessionEndedTime = '" + currentSessionTime
													+ "'" + "WHERE sessionStartedTime = '" + lastSessionTime + "'";

											Statement updateStmnt = dbconn.createStatement();
											updateStmnt.executeUpdate(updateSessionEndedTimeCommand);

										} catch (SQLException ex) {
											ex.printStackTrace();
										}
									}

								}
							} catch (SQLException ex) {
								Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
							}
						}
						refresh();
					} else if (pass.isEmpty()) {
						JOptionPane.showMessageDialog(null, "PLease Enter a value", "Admin Key Confirmation Message",
								JOptionPane.ERROR_MESSAGE);
						refresh();
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect Admin Key", "Admin Key Confirmation Message",
								JOptionPane.ERROR_MESSAGE);
						refresh();
					}
				}
			}
		});
		startSessionPanel.add(onlineStatusIcon);
		startSessionPanel.add(offlineStatusIcon);
		startSessionPanel.add(startSessionBtn);
		hcpS.add(startSessionPanel);
	}

	String currentSchoolYear = "2022-2023";
	String schoolYear;
	private CButton addNewPartyListBtn;

	public void setSessionHistoryPanel() {
		schoolYear = null;
		if (dbconn != null) {
			try {
				String query = "SELECT schoolYear FROM userstable WHERE schoolYear = '" + currentSchoolYear + "'";
				Statement stmnt = dbconn.createStatement();
				ResultSet res = stmnt.executeQuery(query);
				if (res.next()) {
					schoolYear = res.getString("schoolYear");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		voteSessionHistoryPanel = new CPanel();

		JPanel vsNorthPanel = new JPanel();
		vsNorthPanel.setBackground(Colors.DARK);
		vsNorthPanel.setPreferredSize(new Dimension(0, 60));
		vsNorthPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		CLabel displaySchoolYearLabel = new CLabel("Voting Session For S.Y " + schoolYear);
		displaySchoolYearLabel.setFontSize(30);
		displaySchoolYearLabel.setForeground(Colors.LIGHT);
		displaySchoolYearLabel.fixJLabelBug(displaySchoolYearLabel);
		vsNorthPanel.add(displaySchoolYearLabel);

		voteSessionHistoryPanel.contentBasePanel.add(vsNorthPanel, BorderLayout.NORTH);

		votingSessionHistoryTable = new JTable();
		votingSessionHistoryTable.setDefaultEditor(Object.class, null);
		votingSessionHistoryTable.setBackground(Colors.LIGHT);
		votingSessionHistoryTable.setFont(myFont);
		votingSessionHistoryTable.setForeground(Colors.DARK);
		votingSessionHistoryTable.setFillsViewportHeight(true);
		votingSessionHistoryTable.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		JTableHeader header = votingSessionHistoryTable.getTableHeader();
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);
		header.setFont(new Font(fontString, 0, 22));

		model = new DefaultTableModel();
		String[] columnNames = { "log Id", "adminId", "Voting Session Started Time", "adminId",
				"Voting Session Ended Time" };
		model.setColumnIdentifiers(columnNames);
		votingSessionHistoryTable.setModel(model);

		scrollPane = new JScrollPane(votingSessionHistoryTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		loadTableData();
		adjustTableSizeAccordingToContent(votingSessionHistoryTable);

		voteSessionHistoryPanel.contentBasePanel.add(scrollPane, BorderLayout.CENTER);

	}

	public void setAddCandidatesPanel() {
		// ADD CANDIDATES BASE PANEL
		addCandidatesBasePanel = new CPanel();
		// SETTING MARGINS
		addCandidatesBasePanel.e.setPreferredSize(new Dimension(50, 0));
		addCandidatesBasePanel.w.setPreferredSize(new Dimension(50, 0));
		addCandidatesBasePanel.contentBasePanel.setLayout(new BorderLayout());
		// ADD CANDIDATE BASE PANEL TOP PANEL
		acNorth = new JPanel();
		acNorth.setPreferredSize(new Dimension(0, 70));
		acNorth.setBackground(Colors.DARK);
		acNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		acNorth.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Colors.DARK));
		addCandidatesBasePanel.contentBasePanel.add(acNorth, BorderLayout.NORTH);

		JPanel positionChoicesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
		positionChoicesPanel.setBackground(Colors.DARK);
		positionChoicesPanel.setPreferredSize(new Dimension(130, 48));

		CLabel acChoiceSelectionLabel = new CLabel("Position");
		acChoiceSelectionLabel.setHorizontalTextPosition(JLabel.CENTER);
		acChoiceSelectionLabel.setForeground(Colors.LIGHT);
		acChoiceSelectionLabel.setFontSize(30);
		positionChoicesPanel.add(acChoiceSelectionLabel);

		String positionChoices[] = { "President", "Vice-President", "Secretary", "Treasurer", "Auditor" };

		posChoices = new JComboBox<String>(positionChoices);
		posChoices.setFont(new Font(fontString, 0, 24));
		posChoices.setCursor(new Cursor(Cursor.HAND_CURSOR));
		posChoices.setPreferredSize(new Dimension(210, 40));

		acNorth.add(positionChoicesPanel);
		acNorth.add(posChoices);

		// SETTING PANEL AREAS
		// WEST
		acWest = new JPanel();
		acWest.setPreferredSize(new Dimension(230, 0));
		acWest.setBorder(new EmptyBorder(50, 20, 0, 0));
		acWest.setBackground(Colors.LIGHT);
		acWest.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		addCandidatesBasePanel.contentBasePanel.add(acWest, BorderLayout.WEST);

		// EAST
		acEast = new JPanel();
		acEast.setPreferredSize(new Dimension(358, 0));
		acEast.setBorder(new EmptyBorder(50, 20, 0, 20));
		acEast.setBackground(Colors.LIGHT);
		acEast.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		addCandidatesBasePanel.contentBasePanel.add(acEast, BorderLayout.EAST);

		// CENTER
		acCenter = new JPanel();
		acCenter.setBorder(new EmptyBorder(50, 20, 0, 20));
		acCenter.setBackground(Colors.LIGHT);
		acCenter.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		addCandidatesBasePanel.contentBasePanel.add(acCenter, BorderLayout.CENTER);

		acSouth = new JPanel();
		acSouth.setPreferredSize(new Dimension(0, 150));
		acSouth.setBackground(Colors.LIGHT);
		acSouth.setBorder(new EmptyBorder(30, 0, 0, 0));
		addCandidatesBasePanel.contentBasePanel.add(acSouth, BorderLayout.SOUTH);

		// LEFT SIDE / WEST
		JPanel addCPicPanel = new JPanel();
		addCPicPanel.setPreferredSize(new Dimension(200, 300));
		addCPicPanel.setBackground(Colors.LIGHT);

		// SHOW CANDIDATE SELECTED IMAGE
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "png", "jpg", "gif");
		fileChooser.addChoosableFileFilter(filter);

		addCPic = new CLabel();
		addCPic.setIcon(new ImageIcon(getClass().getResource("/image_assets/Rgstr_Imgs/addNew190px.png")));
		addCPic.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addCPic.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				int result = fileChooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String imgPath = selectedFile.getAbsolutePath();
					addCPic.setIcon(resizeImage(imgPath, addCPic));
					System.out.println(imgPath);
					imgPathString = imgPath;
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No Image");
					addCPic.setIcon(new ImageIcon(getClass().getResource("/image_assets/Rgstr_Imgs/addNew190px.png")));
				}
			}

		});
		CLabel uploadPicLabel = new CLabel("Upload picture");
		addCPicPanel.add(addCPic);
		addCPicPanel.add(uploadPicLabel);
		acWest.add(addCPicPanel);

		// ID PANEL
		idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		idPanel.setPreferredSize(new Dimension(384, 90));
		idPanel.setBackground(Colors.LIGHT);

		idLabel = new CLabel("School ID");

		idTf = new CTextField();
		idTf.setPreferredSize(new Dimension(310, 40));
		idTf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				idIfKeyTyped(evt);

			}
		});
		idTf.setDocument(new TFLimit(7));

		idPanel.add(idLabel);
		idPanel.add(idTf);
		acCenter.add(idPanel);

		// LAST NAME PANEL
		lastNamePanel = new JPanel();
		lastNamePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		lastNamePanel.setPreferredSize(new Dimension(384, 90));
		lastNamePanel.setBackground(Colors.LIGHT);

		lastNameLabel = new CLabel("Last Name");

		lastNameTf = new CTextField();
		lastNameTf.setPreferredSize(new Dimension(310, 40));
		lastNameTf.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				char character = e.getKeyChar();

				if (Character.isLetter(character) || Character.isWhitespace(character)
						|| Character.isISOControl(character)) {
					lastNameTf.setEditable(true);
				} else {
					lastNameTf.setEditable(false);
				}
			}
		});

		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameTf);
		acCenter.add(lastNamePanel);

		// COURSE PANEL
		coursePanel = new JPanel();
		coursePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		coursePanel.setPreferredSize(new Dimension(384, 90));
		courseLabel = new CLabel("Course");
		coursePanel.setBackground(Colors.LIGHT);

		String crsChoices[] = { "BSME", "BSIE", "BSEE", "BSCE", "BSCpE", "BEEd", "BSEd", "BTLEd", "BSIT", "BIT", "BSHM",
				"BSTM" };
		course = new JComboBox<String>(crsChoices);
		course.setFont(myFont);
		course.setPreferredSize(new Dimension(330, 40));
		course.setCursor(new Cursor(Cursor.HAND_CURSOR));

		coursePanel.add(courseLabel);
		coursePanel.add(course);
		acCenter.add(coursePanel);

		// EAST PANEL
		// ADD CANDIDATE PANEL
		JPanel partyListPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		partyListPanel.setPreferredSize(new Dimension(384, 90));
		partyListPanel.setBackground(Colors.LIGHT);

		CLabel partyListChoiceLabel = new CLabel("Partylist");
		partyListPanel.add(partyListChoiceLabel);

		JPanel comboWithBtnPanel = new JPanel(new BorderLayout());
		comboWithBtnPanel.setBackground(Colors.LIGHT);
		comboWithBtnPanel.setPreferredSize(new Dimension(310, 42));

		partyList = new JComboBox<String>();
		partyList.setPreferredSize(new Dimension(268, 40));
		partyList.setFont(new Font(fontString, 0, 24));
		partyList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		try {
			String getPartylists = "SELECT partyListName from partyLists";

			Statement st = dbconn.createStatement();
			ResultSet res = st.executeQuery(getPartylists);
			while (res.next()) {
				partyList.addItem(res.getString("partyListName"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		addNewPartyListBtn = new CButton();
		addNewPartyListBtn.setPreferredSize(new Dimension(42, 40));
		addNewPartyListBtn.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/paddd.png")));
		addNewPartyListBtn.setToolTipText("Add a new partyList");
		addNewPartyListBtn.addActionListener(this);
		addNewPartyListBtn.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent evt) {
				addNewPartyListBtn
						.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/pad35.png")));
			}

			public void mouseExited(MouseEvent evt) {
				addNewPartyListBtn
						.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/paddd.png")));
			}
		});

		comboWithBtnPanel.add(partyList, BorderLayout.WEST);
		comboWithBtnPanel.add(addNewPartyListBtn, BorderLayout.EAST);
		partyListPanel.add(comboWithBtnPanel);
		acEast.add(partyListPanel);

		// FIRST NAME PANEL
		firstNamePanel = new JPanel();
		firstNamePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		firstNamePanel.setPreferredSize(new Dimension(384, 90));
		firstNameLabel = new CLabel("First Name");
		firstNamePanel.setBackground(Colors.LIGHT);

		firstNameTf = new CTextField();
		firstNameTf.setPreferredSize(new Dimension(310, 40));
		firstNameTf.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				char character = e.getKeyChar();

				if (Character.isLetter(character) || Character.isWhitespace(character)
						|| Character.isISOControl(character)) {
					firstNameTf.setEditable(true);
				} else {
					firstNameTf.setEditable(false);
				}
			}
		});

		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(firstNameTf);
		acEast.add(firstNamePanel);

		// YEAR AND SECTION PANEL
		yearNSecPanel = new JPanel();
		yearNSecPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		yearNSecPanel.setPreferredSize(new Dimension(384, 90));
		yearNSecLabel = new CLabel("Year and Section");
		yearNSecPanel.setBackground(Colors.LIGHT);

		yearNSecTf = new CTextField();
		yearNSecTf.setPreferredSize(new Dimension(310, 40));
		yearNSecTf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if (yearNSecTf.getText().length() >= 2
						&& !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
					adminDashboardFrame.getToolkit().beep();
					evt.consume();
				}
			}
		});

		yearNSecPanel.add(yearNSecLabel);
		yearNSecPanel.add(yearNSecTf);
		acEast.add(yearNSecPanel);

		// registerCandidateBtn
		registerCandidateBtn = new CButton("Add candidate");
		registerCandidateBtn.addActionListener(this);
		acSouth.add(registerCandidateBtn);

		addCandidatesBasePanel.contentBasePanel.setBorder(BorderFactory.createLineBorder(Colors.DARK, 3));

		viewCandidatesBtn = new CButton();

		viewCandidatesBtn.setText("View Candidates");
		viewCandidatesBtn.addActionListener(this);

	}

	public void setLeftNavbar() {
		// LEFT NAVBAR
		leftNavPanel = new JPanel();
		leftNavPanel.setPreferredSize(new Dimension(60, 0));
		leftNavPanel.setBackground(Colors.DARK);
		leftNavPanel.setLayout(new BorderLayout());

		leftNavIconsPanel = new JPanel();
		leftNavIconsPanel.setBackground(Colors.DARK);

		menuIcon = new JToggleButton();
		menuIcon.setIcon(
				new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_expand_arrow_50px_1.png")));
		menuIcon.setSelectedIcon(
				new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_collapse_arrow_50px_1.png")));
		menuIcon.setRolloverEnabled(false);
		menuIcon.setFocusable(false);
		menuIcon.setContentAreaFilled(false);
		menuIcon.setOpaque(true);
		menuIcon.setBackground(Colors.DARK);
		menuIcon.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		menuIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menuIcon.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					accIconPanel.setVisible(true);
					lgoutPanel.setVisible(true);
				} else {
					accIconPanel.setVisible(false);
					lgoutPanel.setVisible(false);
				}

			}
		});

		homeIconPanel = new JPanel();
		homeIconPanel.setPreferredSize(new Dimension(60, 80));
		homeIconPanel.setBackground(Colors.DARK);
		homeIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		homeIconPanel.setBorder(new MatteBorder(2, 0, 0, 0, Colors.LIGHT));

		CLabel homeLabel = new CLabel("home");
		homeLabel.setFontSize(10);
		homeLabel.setForeground(Colors.LIGHT);

		homeIcon = new CLabel();
		homeIcon.setIcon(
				new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_home_page_50px_4.png")));
		homeIconPanel.add(homeIcon);
		homeIconPanel.add(homeLabel);

		homeIconPanel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				homeIconPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				homeIconPanel.setBackground(Colors.LIGHT);
				homeIcon.setIcon(new ImageIcon(
						getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_home_page_50px_2.png")));
				homeLabel.setForeground(Colors.DARK);
			}

			public void mouseExited(MouseEvent evt) {
				homeIconPanel.setBackground(Colors.DARK);
				homeIcon.setIcon(new ImageIcon(
						getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_home_page_50px_4.png")));
				homeLabel.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {
				hcpBasePanel.setVisible(true);
			}
		});

		accIconPanel = new JPanel();
		accIconPanel.setBackground(Colors.DARK);
		accIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		accIconPanel.setPreferredSize(new Dimension(60, 80));

		CLabel accIconLabel = new CLabel("Account");
		accIconLabel.setFontSize(10);
		accIconLabel.setForeground(Colors.LIGHT);

		accIcon = new CLabel();
		accIcon.setIcon(
				new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_test_account_50px_1.png")));
		accIconPanel.add(accIcon);
		accIconPanel.add(accIconLabel);
		accIconPanel.setVisible(false);
		accIconPanel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				accIconPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				accIconPanel.setBackground(Colors.LIGHT);
				accIcon.setIcon(new ImageIcon(
						getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_test_account_50px_2.png")));
				accIconLabel.setForeground(Colors.DARK);
			}

			public void mouseExited(MouseEvent evt) {
				accIconPanel.setBackground(Colors.DARK);
				accIcon.setIcon(new ImageIcon(
						getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_test_account_50px_1.png")));
				accIconLabel.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				leftNavPanel.setVisible(false);
				hcpBasePanel.setVisible(false);
				accBasePanel.setVisible(true);
			}
		});

		// LGOUT PANEL
		lgoutPanel = new JPanel();
		lgoutPanel.setPreferredSize(new Dimension(60, 80));
		lgoutPanel.setBackground(Colors.DARK);

		CLabel lgoutLabel = new CLabel("Log Out");
		lgoutLabel.setFontSize(10);
		lgoutLabel.setForeground(Colors.LIGHT);

		lgoutIcon = new CLabel();
		lgoutIcon.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/logout1_50px.png")));
		lgoutPanel.add(lgoutIcon);
		lgoutPanel.add(lgoutLabel);
		lgoutPanel.setVisible(false);
		lgoutPanel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				lgoutPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				lgoutPanel.setBackground(Colors.LIGHT);
				lgoutIcon
						.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/logout2_50px.png")));
				lgoutLabel.setForeground(Colors.DARK);
			}

			public void mouseExited(MouseEvent evt) {
				lgoutPanel.setBackground(Colors.DARK);
				lgoutIcon
						.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/logout1_50px.png")));
				lgoutLabel.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				int response = 1;

				response = JOptionPane.showConfirmDialog(null, "Confirm Logout?", "Confirm Message",
						JOptionPane.YES_NO_OPTION);
				if (response == 0) {
					response = 1;
					hasLoggedOut = true;
					// Logging out...
					EventQueue.invokeLater(() -> {
						adminDashboardFrame.dispose();
						new Login();
					});
				}

			}
		});

		// CLOCK
		timeFormat = new SimpleDateFormat("hh:mm:ss a");
		dayFormat = new SimpleDateFormat("EEEE");
		dateFormat = new SimpleDateFormat("d MMM yyyy");

		timeLabel = new CLabel();
		timeLabel.setFontSize(10);
		timeLabel.setForeground(Colors.LIGHT);

		dayLabel = new CLabel();
		dayLabel.setFontSize(10);
		dayLabel.setForeground(Colors.LIGHT);

		dateLabel = new CLabel();
		dateLabel.setFontSize(10);
		dateLabel.setForeground(Colors.LIGHT);

		clockPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Point getToolTipLocation(MouseEvent e) {
				return new Point(60, 20);
			}
		};
		clockPanel.setPreferredSize(new Dimension(0, 50));
		clockPanel.setBackground(Colors.DARK);

		clockPanel.add(timeLabel);
		// clockPanel.add(dayLabel);
		clockPanel.add(dateLabel);

		clockPanel.setToolTipText(toolTipDate);

		leftNavIconsPanel.add(menuIcon);
		leftNavIconsPanel.add(accIconPanel);
		leftNavIconsPanel.add(lgoutPanel);
		leftNavIconsPanel.add(homeIconPanel);

		leftNavPanel.add(leftNavIconsPanel, BorderLayout.CENTER);
		leftNavPanel.add(clockPanel, BorderLayout.SOUTH);
	}

	public void setHomePanel() {
		// HOME PANEL
		hcpBasePanel = new JPanel();
		hcpBasePanel.setPreferredSize(new Dimension(1040, 0));
		hcpBasePanel.setLayout(new BorderLayout());

		// CENTER PANEL
		String raw = Login.userEmail;
		String arr[] = raw.split("@", 2);
		String name = arr[0];
		welcomeText = new CLabel("Welcome, " + name);

		welcomeText.setForeground(Colors.DARK);
		welcomeText.setFontSize(50);

		hcpN = new JPanel();
		hcpN.setBackground(Colors.LIGHT);
		hcpN.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
		hcpN.add(welcomeText);

		hcpNorthBasePanel = new JPanel();
		hcpNorthBasePanel.setPreferredSize(new Dimension(0, 250));
		hcpNorthBasePanel.setBackground(Colors.LIGHT);
		hcpNorthBasePanel.setLayout(new BorderLayout());
		hcpNorthBasePanel.add(hcpN, BorderLayout.CENTER);

		hcpNW = new JPanel();
		hcpNW.setPreferredSize(new Dimension(90, 0));
		hcpNW.setBackground(Colors.LIGHT);
		hcpNorthBasePanel.add(hcpNW, BorderLayout.WEST);

		hcpNE = new JPanel();
		hcpNE.setPreferredSize(new Dimension(90, 0));
		hcpNE.setBackground(Colors.LIGHT);
		hcpNE.setLayout(new FlowLayout(FlowLayout.TRAILING, 0, 0));
		hcpNE.add(new Utilities().darkUtilityPanel(adminDashboardFrame));
		hcpNorthBasePanel.add(hcpNE, BorderLayout.EAST);

		hcpL = new JPanel();
		hcpL.setPreferredSize(new Dimension(60, 0));
		hcpL.setBackground(Colors.LIGHT);

		hcpR = new JPanel();
		hcpR.setPreferredSize(new Dimension(60, 0));
		hcpR.setBackground(Colors.LIGHT);

		hcpC = new JPanel();
		hcpC.setBackground(Colors.LIGHT);
		hcpC.setBorder(new MatteBorder(4, 0, 0, 0, Colors.DARK));
		hcpC.setLayout(new BorderLayout());

		hcpS = new JPanel();
		hcpS.setPreferredSize(new Dimension(0, 140));
		hcpS.setBackground(Colors.LIGHT);

		hcpCSubN = new JPanel();
		hcpCSubN.setPreferredSize(new Dimension(0, 350));
		hcpCSubN.setBackground(Colors.LIGHT);

		hcpC.add(hcpCSubN, BorderLayout.NORTH);
		hcpC.add(hcpS, BorderLayout.SOUTH);

		hcpBasePanel.add(hcpNorthBasePanel, BorderLayout.NORTH);
		hcpBasePanel.add(hcpL, BorderLayout.WEST);
		hcpBasePanel.add(hcpR, BorderLayout.EAST);
		hcpBasePanel.add(hcpC, BorderLayout.CENTER);
	}

	public void setAccountPanel() {
		// ACC SETTINGS PANEL
		accBasePanel = new JPanel();
		accBasePanel.setBounds(0, 0, 1100, 750);
		accBasePanel.setBackground(Colors.LIGHT);
		accBasePanel.setLayout(new BorderLayout());

		accN = new JPanel();
		accN.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		accN.setPreferredSize(new Dimension(0, 60));
		accN.setBackground(Colors.DARK);
		accBasePanel.add(accN, BorderLayout.NORTH);

		accBasePanel.setVisible(false);

		backToLoginPanel = new JPanel();
		backToLoginPanel.setBackground(Colors.DARK);

		backToLoginBtn = new CLabel();

		accDetailsLabel = new CLabel("Admin account Details");
		accDetailsLabel.setForeground(Colors.LIGHT);
		accDetailsLabel.setFontSize(20);

		backToLoginBtn
				.setIcon(new ImageIcon(getClass().getResource("/image_assets/Rgstr_Imgs/icons8_back_to_50px.png")));
		backToLoginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToLoginBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				accBasePanel.setVisible(false);
				hcpBasePanel.setVisible(true);
				leftNavPanel.setVisible(true);
			}
		});
		backToLoginPanel.add(backToLoginBtn);
		backToLoginPanel.add(accDetailsLabel);

		accN.add(backToLoginPanel);

		accL = new JPanel();
		accL.setPreferredSize(new Dimension(350, 0));
		accL.setBackground(Colors.LIGHT);
		accBasePanel.add(accL, BorderLayout.WEST);

		accR = new JPanel();
		accR.setPreferredSize(new Dimension(350, 0));
		accR.setBackground(Colors.LIGHT);
		accBasePanel.add(accR, BorderLayout.EAST);

		accC = new JPanel();
		accC.setBackground(Colors.LIGHT);
		accBasePanel.add(accC, BorderLayout.CENTER);

		JPanel accInfoBasePanel = new JPanel();
		accInfoBasePanel.setPreferredSize(new Dimension(300, 500));
		accInfoBasePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		accInfoBasePanel.setBackground(Colors.LIGHT);
		accC.add(accInfoBasePanel);

		schoolIdLabel = new CLabel("School Id: " + Login.userSchoolId);
		emailLabel = new CLabel("Email: " + Login.userEmail);
		fName = new CLabel("First Name: ");
		lName = new CLabel("last Name: ");
		courseLbl = new CLabel("Course: " + Login.userCourse);
		yrNSecLabel = new CLabel("Year and Section: ");
		userTypeLabel = new CLabel("is an Admin: " + Login.userType);

		accInfoBasePanel.add(schoolIdLabel);
		accInfoBasePanel.add(emailLabel);
		accInfoBasePanel.add(fName);
		accInfoBasePanel.add(lName);
		accInfoBasePanel.add(courseLbl);
		accInfoBasePanel.add(yrNSecLabel);
		accInfoBasePanel.add(userTypeLabel);
	}

	//
	public void checkGoSignalStatus() {
		if (dbconn != null) {
			Statement stmnt;
			try {
				stmnt = dbconn.createStatement();
				String query = "SELECT voteEnabled FROM userstable";
				ResultSet res = stmnt.executeQuery(query);

				if (res.next()) {

					goSignal = res.getBoolean("voteEnabled");
					System.out.println("Admindashboard go signal status: " + goSignal);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
	}

	//
	private CPanel newPage(CPanel panel, String panelTitle) {

		panel.setCustomPanelTitle(panelTitle);
		panel.customFunc(accBasePanel, hcpBasePanel, leftNavPanel, panel);
		panel.setVisible(false);

		return panel;
	}

	String candSchoolId, candEmail, candLastName, candFirstName, candCourse, candYearNSection, candPartyList,
			candPosition;
	InputStream candImgFile;

	int candidateId;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addCandidateBtn) {
			panelSwitch(addCandidatesBasePanel);
		} else if (e.getSource() == elecStatsBtn) {
			panelSwitch(elecStatsBasePanel);
		} else if (e.getSource() == cMButton) {
			panelSwitch(cMBasePanel);
		} else if (e.getSource() == manageUsersBtn) {
			panelSwitch(manageUsersBasePanel);
		} else if (e.getSource() == resetBtn) {

			panelSwitch(resetPanel);
		} else if (e.getSource() == addNewPartyListBtn) {
			SigMethods.enableGlassPane(adminDashboardFrame);
			EventQueue.invokeLater(() -> {
				new AddPartylist();
			});
			adminDashboardFrame.revalidate();

		}

		else if (e.getSource() == startSessionBtn) {
			EventQueue.invokeLater(() -> {
				new StartVotingSessionPage();
			});

		} else if (e.getSource() == registerCandidateBtn) {

			// GET CANDIDATE INFO
			candSchoolId = idTf.getText();
			candLastName = lastNameTf.getText();
			candFirstName = firstNameTf.getText();
			candCourse = course.getSelectedItem().toString();
			candYearNSection = yearNSecTf.getText();
			candPartyList = partyList.getSelectedItem().toString();
			candPosition = posChoices.getSelectedItem().toString();

			if (imgPathString == null) {
				JOptionPane.showMessageDialog(adminDashboardFrame, "Please select an image for this candidate :(");
			} else if (candSchoolId.trim().isBlank() || candLastName.trim().isBlank() || candFirstName.trim().isBlank()
					|| candCourse.trim().isBlank() || candPartyList.trim().isBlank()
					|| candYearNSection.trim().isBlank() || imgPathString.isBlank() || imgPathString == null) {
				JOptionPane.showMessageDialog(adminDashboardFrame, "Some fields are empty", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				String key = "root";
				String pass = JOptionPane.showInputDialog(null, "Please enter admin key to register a candidate: ");
				if (pass.equals(key)) {
					// CHECK IF CANDIDATE SCHOOL ID EXISTS (CHECK IF CANDIDATE ALREADY HAS AN
					// ACCOUNT)

					try {
						candImgFile = new FileInputStream(new File(imgPathString));

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();

					}

					if (dbconn != null) {
						try {
							String findExistingRecordCommand = "SELECT * FROM userstable WHERE schoolId = '"
									+ candSchoolId + "'";
							Statement st = dbconn.createStatement();
							ResultSet res = st.executeQuery(findExistingRecordCommand);
							if (res.next()) {

								if (checkIfUserIsCandidate_equalsTrue(candSchoolId)) {
									JOptionPane.showMessageDialog(adminDashboardFrame,
											"Candidate Already Registered (candidate already has an account)", "Error",
											JOptionPane.ERROR_MESSAGE);
								} else {
									String insertCandidateToDatabase = "INSERT INTO candidates (users_schoolId, runningFor, partyList, img, votes)"
											+ "VALUES (?,?,?,?,?)";
									PreparedStatement prepStmnt = (PreparedStatement) dbconn
											.prepareStatement(insertCandidateToDatabase);
									prepStmnt.setString(1, candSchoolId);
									prepStmnt.setString(2, candPosition);
									prepStmnt.setString(3, candPartyList);
									prepStmnt.setBlob(4, candImgFile);
									prepStmnt.setInt(5, 0);

									prepStmnt.executeUpdate();

									String updateIsCandidateStatusCommand = "UPDATE userstable SET isCandidate = true, yearNSection = '"
											+ candYearNSection + "', hasVoted = false, firstName = '" + candFirstName
											+ "', lastName = '" + candLastName + "' WHERE schoolId = '" + candSchoolId
											+ "'";
									Statement st1 = dbconn.createStatement();
									st1.executeUpdate(updateIsCandidateStatusCommand);

									String getCandIdCommand = "SELECT candidateId FROM candidates WHERE users_schoolId = '"
											+ candSchoolId + "'";
									Statement st2 = dbconn.createStatement();
									ResultSet rs = st2.executeQuery(getCandIdCommand);
									if (rs.next()) {
										candidateId = rs.getInt("candidateId");

									}
									adminDashboardFrame.dispose();
									new AdminDashboard();
									JOptionPane.showMessageDialog(adminDashboardFrame, "Candidate Registered",
											"Success", JOptionPane.INFORMATION_MESSAGE);

								}
							} else {
								// IF CANDIDATE SCHOOLID DOESNT EXIST, IT MEANS THIS CANDIDATE HAS NO ACC, THE
								// FOLLOWING CODE WILL AUTOMATICALLY CREATE AND REGISTER AN ACCOUNT FOR THE
								// CANDIDATE
								// WITH THE DEFAULT PASSWORD OF THE CANDIDATE'S SCHOOL ID.

								// INSERTING TO USER TABLE

								boolean isCandidateDefault = false;
								// create date instance for the accDateCreated
								Date date = new Date();
								Timestamp dateCreated = new Timestamp(date.getTime());

								String registerUserFirstToDatabase = "INSERT INTO userstable (schoolId, lastName, firstName, course, password, accCreated, yearNSection, isCandidate, hasVoted, isAdmin)"
										+ "VALUES (?, ? ,? ,? ,? ,? ,?,?,?,?)";
								PreparedStatement prepSt = (PreparedStatement) dbconn
										.prepareStatement(registerUserFirstToDatabase);
								prepSt.setString(1, candSchoolId);
								prepSt.setString(2, candLastName);
								prepSt.setString(3, candFirstName);
								prepSt.setString(4, candCourse);
								prepSt.setString(5, candSchoolId);
								prepSt.setTimestamp(6, dateCreated);
								prepSt.setString(7, candYearNSection);
								prepSt.setBoolean(8, isCandidateDefault);
								prepSt.setBoolean(9, false);
								prepSt.setBoolean(10, false);

								prepSt.executeUpdate();

								if (checkIfUserIsCandidate_equalsTrue(candSchoolId)) {
									JOptionPane.showMessageDialog(adminDashboardFrame, "Candidate Already Registered",
											"Error", JOptionPane.ERROR_MESSAGE);
								} else {
									// INSERTING TO CANDIDATES TABLE
									String insertCandidateToDatabase = "INSERT INTO candidates (users_schoolId, runningFor, partyList, img, votes)"
											+ "VALUES (?,?,?,?,?)";
									PreparedStatement prepStmnt = (PreparedStatement) dbconn
											.prepareStatement(insertCandidateToDatabase);
									prepStmnt.setString(1, candSchoolId);
									prepStmnt.setString(2, candPosition);
									prepStmnt.setString(3, candPartyList);
									prepStmnt.setBlob(4, candImgFile);
									prepStmnt.setInt(5, 0);

									prepStmnt.executeUpdate();

									String updateIsCandidateStatusCommand = "UPDATE userstable SET isCandidate = true, hasVoted = false WHERE schoolId = '"
											+ candSchoolId + "'";
									Statement st1 = dbconn.createStatement();
									st1.executeUpdate(updateIsCandidateStatusCommand);

									int max = 500;
									int min = 1;
									int random = (int) (Math.random() * (max - min + 1) + min);
									String randomNum = String.valueOf(random);

									String tableName = "user_" + candSchoolId + "_" + randomNum + "_table";

									createUserTable(dbconn, tableName, candSchoolId);

									adminDashboardFrame.dispose();
									new AdminDashboard();
									JOptionPane.showMessageDialog(adminDashboardFrame,
											"Candidate Account created and Candidate Registered", "Success",
											JOptionPane.INFORMATION_MESSAGE);

								}

							}

						}

						catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(adminDashboardFrame, "Incorrect Admin Key", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	public void panelSwitch(CPanel chosenPanel) {
		leftNavPanel.setVisible(false);
		hcpBasePanel.setVisible(false);
		accBasePanel.setVisible(false);
		addCandidatesBasePanel.setVisible(false);
		elecStatsBasePanel.setVisible(false);

		chosenPanel.setVisible(true);
	}

	public void refresh() {

		adminDashboardFrame.dispose();
		new AdminDashboard();
	}

	private void setTableRowHeights(JTable table) {
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = table.getRowHeight();

			for (int column = 0; column < table.getColumnCount(); column++) {
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight + 2, comp.getPreferredSize().height);
			}

			table.setRowHeight(row, rowHeight);
		}
	}

	public void adjustTableSizeAccordingToContent(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		TableCellRenderer renderer;
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
				table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
			}
			if (width > 300)
				width = 300;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		setTableRowHeights(table);
	}

	public void adjustTable(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
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
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
			}
			if (width > 300)
				width = 300;
			columnModel.getColumn(column).setPreferredWidth(width);

		}
		table.setRowHeight(105);
	}

	// SHOW ALL USERS TABLE
	private JScrollPane showAllUsersTableWithScrollPane() {
		JTable allUsersTable = new JTable();
		allUsersTable.setDefaultEditor(Object.class, null);
		allUsersTable.setBackground(Colors.LIGHT);
		allUsersTable.setFont(myFont);
		allUsersTable.setForeground(Colors.DARK);
		allUsersTable.setFillsViewportHeight(true);
		allUsersTable.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		JTableHeader header = allUsersTable.getTableHeader();
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);
		header.setFont(new Font(fontString, 0, 22));

		DefaultTableModel model = new DefaultTableModel();
		String[] columnNames = { "School Id", "CTU Email", "Last Name", "First Name", "Department", "Course",
				"Year and Section", "Account Created" };
		model.setColumnIdentifiers(columnNames);
		allUsersTable.setModel(model);

		JScrollPane scrollPane = new JScrollPane(allUsersTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// load table data
		if (dbconn != null) {
			try {

				String getAllUsers = "SELECT schoolId, ctuEmail, lastName, firstName, department, course, yearNSection, accCreated FROM userstable";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getAllUsers);
				while (res.next()) {
					String schoolId = res.getString("schoolId");
					String ctuEmail = res.getString("ctuEmail");
					String lastName = res.getString("lastName");
					String firstName = res.getString("firstName");
					String department = res.getString("department");
					String course = res.getString("course");
					String yearNSection = res.getString("yearNSection");
					String accCreated = res.getString("accCreated");
					model.addRow(new Object[] { schoolId, ctuEmail, lastName, firstName, department, course,
							yearNSection, accCreated });

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		adjustTableSizeAccordingToContent(allUsersTable);
		return scrollPane;
	}

	// LOAD JTABLE VOTING SESSION HISTORY PANEL
	void loadTableData() {

		if (dbconn != null) {
			try {

				String getVotingSessionHistoryTableCommand = "SELECT adminLogs1.logId, adminLogs1.adminId, adminLogs1.sessionStartedTime, adminLogs2.adminId, adminLogs2.sessionEndedTime\r\n"
						+ "FROM adminLogs1\r\n"
						+ "JOIN adminLogs2 WHERE adminLogs1.sessionStartedTime = adminLogs2.sessionStartedTime;";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getVotingSessionHistoryTableCommand);
				while (res.next()) {
					int logId = res.getInt("adminLogs1.logId");
					int adminId = res.getInt("adminLogs1.adminId");
					String sessionStartedTime = res.getString("adminLogs1.sessionStartedTime");
					int adminId2 = res.getInt("adminLogs2.adminId");
					String sessionEndedTime = res.getString("adminLogs2.sessionEndedTime");
					model.addRow(new Object[] { logId, adminId, sessionStartedTime, adminId2, sessionEndedTime });

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Method To Resize The ImageIcon
	public ImageIcon resizeImage(String imgPath, JLabel targetLabel) {
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(targetLabel.getWidth(), targetLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		return image;
	}

	public boolean checkIfSchoolIDExists(String schoolId) {
		boolean schoolIDExists = false;

		if (dbconn != null) {
			try {

				PreparedStatement st = (PreparedStatement) dbconn
						.prepareStatement("SELECT * FROM userstable WHERE schoolId = ?");

				st.setString(1, schoolId);

				ResultSet res = st.executeQuery();
				if (res.next()) {
					schoolIDExists = true;
					JOptionPane.showMessageDialog(null, "This ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException ex) {
				Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return schoolIDExists;
	}

	public boolean checkIfUserIsCandidate_equalsTrue(String schoolId) {
		boolean isCandidate = false;

		if (dbconn != null) {
			try {

				Statement st = dbconn.createStatement();

				ResultSet res = st
						.executeQuery("SELECT isCandidate FROM userstable WHERE schoolId = '" + schoolId + "'");
				if (res.next()) {
					isCandidate = res.getBoolean("isCandidate");
					System.out.println(isCandidate);

				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return isCandidate;
	}

	public static void createUserTable(Connection conn, String tableName, String id) throws SQLException {
		if (tableExist(conn, tableName)) {
		} else {
			if (conn != null) {
				try {
					String sql = "CREATE table " + tableName + "(\r\n" + "	img LONGBLOB,\r\n"
							+ "    candLastName VARCHAR(45),\r\n" + "    candFirstName VARCHAR(45),\r\n"
							+ "    course VARCHAR(15),\r\n" + "    yearNSection VARCHAR(10),\r\n"
							+ "    runningFor VARCHAR(45),\r\n" + "    partyList VARCHAR(45)\r\n" + ")";
					Statement st = conn.createStatement();

					st.executeUpdate(sql);
					st.executeUpdate(
							"UPDATE userstable SET tableName = '" + tableName + "' WHERE schoolId = '" + id + "'");
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

	}

	// CHECK IF TABLE EXISTS
	private static boolean tableExist(Connection conn, String tableName) throws SQLException {
		String compareTable = tableName.toUpperCase();
		boolean tExists = false;
		if (conn != null) {
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(
					"SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + compareTable + "'");
			if (res.next()) {

				tExists = true;
			} else {
			}
		}
		return tExists;
	}

	// method to only allow digits in the school id input
	private void idIfKeyTyped(KeyEvent evt) {
		if (!Character.isDigit(evt.getKeyChar())) {
			evt.consume();
		}
	}

	// THIS SETS THE CLOCK AND RUNS IT ON A DIFFERENT THREAD
	@Override
	public void run() {
		while (true) {
			time = timeFormat.format(Calendar.getInstance().getTime());

			timeLabel.setText(time);

			day = dayFormat.format(Calendar.getInstance().getTime());
			dayLabel.setText(day);

			date = dateFormat.format(Calendar.getInstance().getTime());
			dateLabel.setText(date);

			currentSessionTime = date + " at " + time;

			timerStartedTime = Calendar.getInstance().getTime();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
