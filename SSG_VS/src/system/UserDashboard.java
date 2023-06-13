package system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.CPanel;
import myTheme.Colors;
import myTheme.CustomFont;
import myTheme.SigMethods;
import myTheme.Utilities;
import tableModels.GetCandidates;
import tableModels.GetVotedCandidates;
import tableModels.MyTableModel;
import tableModels.NumberedTableModel;

public class UserDashboard implements ActionListener, Runnable {

	Font myFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(myFont);

	public static JFrame userDashboardFrame;
	Connection dbconn = DBConnection.connectDB();

	public static CPanel votingBasePanel;
	private JPanel voteBtnPanel, changeThemePanel, accN, accL, accR, accC, backToLoginPanel, hcpCSubN, lgoutPanel,
			hcpNorthBasePanel, hcpNW, hcpNE, hcpN, hcpL, hcpR, hcpC, leftNavIconsPanel, accIconPanel, homeIconPanel,
			clockPanel;
	private CLabel welcomeText, showTransactionId, backToLoginBtn, accDetailsLabel, schoolIdLabel, emailLabel,
			firstNameLabel, lastNameLabel, courseLabel, yearNSecLabel, userTypeLabel, homeIcon, accIcon, lgoutIcon,
			timeLabel, dayLabel, dateLabel;
	protected CButton changeThemeBtn;
	JPanel hcpBasePanel, leftNavPanel, accBasePanel;
	JToggleButton menuIcon;
	public static CButton testVoteButton;
	static CButton voteBtn;
	CLabel stopWatchLabel;

	SimpleDateFormat timeFormat, dayFormat, dateFormat;

	String time, day, date, toolTipDate;

	JPanel presPanel, vPresPanel, secPanel, audiPanel, treasuPanel;

	CLabel presPic, presName, presCourseYrSec, vPresPic, vPresName, vPresCourseYrSec, secPic, secName, secCourseYrSec,
			audPic, audName, audCourseYrSec, treasuPic, treasuName, treasuCourseYrSec;

	CLabel presId, vPresId, secId, audId, treasuId;

	static boolean hasLoggedOut = false;
	boolean goSignal;
	private boolean hasVoted;
	private CButton viewVotedCandidatesBtn;
	private CPanel votedCandidatesBasePanel;
	private JTable votedCandidatesTable;
	private CButton printBtn;

	public UserDashboard() {

		initComponents();

		Thread thread = new Thread(this);
		thread.start();

	}

	public void initComponents() {
		// JFrame
		userDashboardFrame = new JFrame();
		ImageIcon JFrameLogo = new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/SSGLogo.png"));
		userDashboardFrame.setIconImage(JFrameLogo.getImage());
		userDashboardFrame.setSize(1100, 750);
		userDashboardFrame.setLocationRelativeTo(null);
		userDashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userDashboardFrame.setUndecorated(true);
		userDashboardFrame.setLayout(new BorderLayout());
		SigMethods.draggable(userDashboardFrame);

		// ACC SETTINGS PANEL
		accBasePanel = new JPanel();
		accBasePanel.setBounds(0, 0, 1100, 750);
		accBasePanel.setBackground(Colors.LIGHT);
		accBasePanel.setLayout(new BorderLayout());

		// ADDING OF THE ACC BASE PANEL TO THE JFRAME IS AT THE END
		accN = new JPanel();
		accN.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		accN.setPreferredSize(new Dimension(0, 60));
		accN.setBackground(Colors.DARK);
		accBasePanel.add(accN, BorderLayout.NORTH);

		// SET VISIBLE FALSE TO BE USED LATER
		accBasePanel.setVisible(false);

		backToLoginPanel = new JPanel();
		backToLoginPanel.setBackground(Colors.DARK);

		backToLoginBtn = new CLabel();

		accDetailsLabel = new CLabel("Account details");
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
		firstNameLabel = new CLabel("First Name: ");
		lastNameLabel = new CLabel("last Name: ");
		courseLabel = new CLabel("Course: " + Login.userCourse);
		yearNSecLabel = new CLabel("Year and Section: ");
		userTypeLabel = new CLabel("is an Admin: " + Login.userType);

		accInfoBasePanel.add(schoolIdLabel);
		accInfoBasePanel.add(emailLabel);
		accInfoBasePanel.add(firstNameLabel);
		accInfoBasePanel.add(lastNameLabel);
		accInfoBasePanel.add(courseLabel);
		accInfoBasePanel.add(yearNSecLabel);
		accInfoBasePanel.add(userTypeLabel);
		//

		// HOME PANEL
		hcpBasePanel = new JPanel();
		hcpBasePanel.setPreferredSize(new Dimension(1040, 0));
		hcpBasePanel.setLayout(new BorderLayout());

		welcomeText = new CLabel();

		welcomeText.setText("Welcome! null");

		try {
			String getUserFirstNameCommand = "SELECT firstName FROM userstable WHERE schoolId = '" + Login.userSchoolId
					+ "'";
			Statement statement = dbconn.createStatement();
			ResultSet result = statement.executeQuery(getUserFirstNameCommand);
			if (result.next()) {
				if (result.getString("firstName") == null) {
					String raw = Login.userEmail;
					String arr[] = raw.split("@", 2);
					String name = arr[0];
					welcomeText.setText("Welcome! " + name);
				} else {
					welcomeText.setText("Welcome! " + result.getString("firstName"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		welcomeText.setForeground(Colors.DARK);
		welcomeText.setFontSize(50);
		welcomeText.fixJLabelBug(welcomeText);

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
		hcpNW.setPreferredSize(new Dimension(80, 0));
		hcpNW.setBackground(Colors.LIGHT);
		hcpNorthBasePanel.add(hcpNW, BorderLayout.WEST);

		
		CLabel indicator = new CLabel();
		indicator.setForeground(Colors.LIGHT);
		indicator.setOpaque(true);
		indicator.setHorizontalTextPosition(JLabel.CENTER);
		indicator.setPreferredSize(new Dimension(56, 26));
		indicator.setBackground(Colors.DARK);
		
		boolean isCandidate = false;
		try {
			Statement st = dbconn.createStatement();
			ResultSet res = st
					.executeQuery("SELECT isCandidate FROM userstable WHERE schoolId = '" + Login.userSchoolId + "'");
			if (res.next()) {
				isCandidate = res.getBoolean("isCandidate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(isCandidate == true) {
			indicator.setText("Candidate");
			hcpNW.setPreferredSize(new Dimension(110, 0));
			indicator.setPreferredSize(new Dimension(100, 26));
			
		}else {
			indicator.setText("Voter");
			hcpNW.setPreferredSize(new Dimension(80, 0));
			indicator.setPreferredSize(new Dimension(56, 26));
		}
		
		hcpNW.add(indicator);

		hcpNE = new JPanel();
		hcpNE.setPreferredSize(new Dimension(90, 0));
		hcpNE.setBackground(Colors.LIGHT);
		hcpNE.setLayout(new FlowLayout(FlowLayout.TRAILING, 0, 0));
		hcpNE.add(new Utilities().darkUtilityPanelForUserDashboard(userDashboardFrame));
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

		hcpCSubN = new JPanel();
		hcpCSubN.setPreferredSize(new Dimension(0, 350));
		hcpCSubN.setBackground(Colors.LIGHT);

		hcpC.add(hcpCSubN, BorderLayout.NORTH);

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
		menuIcon.setBorder(BorderFactory.createLineBorder(Colors.DARK));
		menuIcon.setBackground(Colors.DARK);
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
		accIconPanel.setVisible(false);

		// LGOUT PANEL
		lgoutPanel = new JPanel();
		lgoutPanel.setPreferredSize(new Dimension(60, 80));
		lgoutPanel.setBackground(Colors.DARK);

		CLabel lgoutLabel = new CLabel("Log Out");
		lgoutLabel.setFontSize(10);
		lgoutLabel.setForeground(Colors.LIGHT);

		lgoutIcon = new CLabel();
		lgoutIcon.setIcon(new ImageIcon(getClass().getResource("/image_assets/admnDbrd_Imgs/logout1_50px.png")));
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
					if (hasLoggedOut == true) {
						System.out.println("Login: hasLoggedout :" + UserDashboard.hasLoggedOut);
					}
					// Logging out...
					EventQueue.invokeLater(() -> {
						userDashboardFrame.dispose();
						new Login();
					});
				}

			}
		});

		lgoutPanel.add(lgoutIcon);
		lgoutPanel.add(lgoutLabel);
		lgoutPanel.setVisible(false);

		// VIEW VOTED CANDIDATES PANEL
		votedCandidatesBasePanel = new CPanel();

		votedCandidatesBasePanel.contentBasePanel.add(showVotedCandidatesTable());
		printBtn = new CButton("Print");
		printBtn.setPreferredSize(new Dimension(250, 50));
		printBtn.addActionListener(this);
		votedCandidatesBasePanel.s.add(printBtn);

		// VOTE PANEL

		votingBasePanel = new CPanel();
		votingBasePanel.contentBasePanel.setLayout(new BorderLayout());
		votingBasePanel.contentBasePanel.setBackground(Colors.LIGHT);

		// add the tables
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
		rootTabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rootTabbedPane.add("Presidents", showPresTableData());
		rootTabbedPane.add("Vice-Presidents", showVicePresTableData());
		rootTabbedPane.add("Secretaries", showSecretariesTableData());
		rootTabbedPane.add("Auditors", showAuditorsTableData());
		rootTabbedPane.add("Treasurers", showTreasurersTableData());
		votingBasePanel.contentBasePanel.add(rootTabbedPane, BorderLayout.CENTER);

		// SELECTED CANDIDATES BASE PANEL/////////////////////////////////////////
		JPanel selectedCandidatesBasePanel = new JPanel(new BorderLayout());
		selectedCandidatesBasePanel.setPreferredSize(new Dimension(340, 0));
		selectedCandidatesBasePanel.setBackground(Colors.LIGHT);

		TitledBorder title = new TitledBorder(BorderFactory.createLineBorder(Colors.DARK, 2), "Selected Candidates");
		title.setTitleFont(myFont);
		title.setTitleColor(Colors.DARK);
		selectedCandidatesBasePanel.setBorder(title);

		JPanel root = new JPanel(new GridLayout(5, 1));
		root.setBackground(Colors.LIGHT);

		///////// PRES PANEL//////////////
		presPanel = new JPanel(new BorderLayout());
		presPanel.setBackground(Colors.LIGHT);

		JPanel titlePres = new JPanel(new BorderLayout());
		titlePres.setPreferredSize(new Dimension(0, 24));
		titlePres.setBackground(Colors.DARK);
		CLabel textPres = new CLabel("President");
		textPres.setForeground(Colors.LIGHT);
		titlePres.add(textPres, BorderLayout.CENTER);
		CLabel deletePres = new CLabel("X");
		deletePres.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deletePres.setPreferredSize(new Dimension(15, 0));
		deletePres.setForeground(Colors.LIGHT);

		titlePres.add(deletePres, BorderLayout.EAST);
		presPanel.add(titlePres, BorderLayout.NORTH);

		presPic = new CLabel();
		presPic.setPreferredSize(new Dimension(80, 80));
		presPanel.add(presPic, BorderLayout.WEST);

		JPanel presCenterRootPanel = new JPanel(new GridLayout(3, 1, 2, 2));
		presCenterRootPanel.setBackground(Colors.LIGHT);

		presId = new CLabel();
		presId.setText(null);
		presId.setPreferredSize(new Dimension(50, 20));
		presId.setBackground(Colors.LIGHT);
		presId.setBorder(null);
		presId.setFont(new Font(fontString, 0, 20));
		presCenterRootPanel.add(presId);

		presName = new CLabel();
		presCenterRootPanel.add(presName);

		presCourseYrSec = new CLabel();
		presCenterRootPanel.add(presCourseYrSec);

		presPanel.add(presCenterRootPanel, BorderLayout.CENTER);

		deletePres.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deletePres.setForeground(Color.red);
			}

			public void mouseExited(MouseEvent evt) {
				deletePres.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				if (dbconn != null) {
					try {

						String sql = "UPDATE partialvotedcandidates SET partialVotedPres = null WHERE users_schoolId = '"
								+ Login.userSchoolId + "'";
						Statement st = dbconn.createStatement();
						st.executeUpdate(sql);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				presId.setText(null);
				presPic.setIcon(null);
				presName.setText(null);
				presPanel.setVisible(false);

			}
		});
		presPanel.setVisible(false);
		root.add(presPanel);

		////////////////////////////////////

		// VICE PRES PANEL///////////////////
		vPresPanel = new JPanel(new BorderLayout());
		vPresPanel.setBackground(Colors.LIGHT);

		JPanel titleVicePres = new JPanel(new BorderLayout());
		titleVicePres.setPreferredSize(new Dimension(0, 24));
		titleVicePres.setBackground(Colors.DARK);
		CLabel textVicePres = new CLabel("Vice-President");
		textVicePres.setForeground(Colors.LIGHT);
		titleVicePres.add(textVicePres, BorderLayout.CENTER);
		CLabel deleteVicePres = new CLabel("X");
		deleteVicePres.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteVicePres.setPreferredSize(new Dimension(15, 0));
		deleteVicePres.setForeground(Colors.LIGHT);

		titleVicePres.add(deleteVicePres, BorderLayout.EAST);
		vPresPanel.add(titleVicePres, BorderLayout.NORTH);

		vPresPic = new CLabel();
		vPresPic.setPreferredSize(new Dimension(80, 80));
		vPresPanel.add(vPresPic, BorderLayout.WEST);

		JPanel vPresCenterRootPanel = new JPanel(new GridLayout(3, 1, 2, 2));
		vPresCenterRootPanel.setBackground(Colors.LIGHT);

		vPresId = new CLabel();
		vPresId.setText(null);
		vPresId.setPreferredSize(new Dimension(50, 20));
		vPresId.setBackground(Colors.LIGHT);
		vPresId.setBorder(null);
		vPresId.setFont(new Font(fontString, 0, 20));
		vPresCenterRootPanel.add(vPresId);

		vPresName = new CLabel();
		vPresCenterRootPanel.add(vPresName);

		vPresCourseYrSec = new CLabel();
		vPresCenterRootPanel.add(vPresCourseYrSec);

		vPresPanel.add(vPresCenterRootPanel, BorderLayout.CENTER);

		deleteVicePres.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deleteVicePres.setForeground(Color.red);
			}

			public void mouseExited(MouseEvent evt) {
				deleteVicePres.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				if (dbconn != null) {
					try {

						String sql = "UPDATE partialvotedcandidates SET partialVotedVicePres = null WHERE users_schoolId = '"
								+ Login.userSchoolId + "'";
						Statement st = dbconn.createStatement();
						st.executeUpdate(sql);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				vPresId.setText(null);
				vPresPic.setIcon(null);
				vPresName.setText(null);
				vPresPanel.setVisible(false);

			}
		});

		root.add(vPresPanel);
		vPresPanel.setVisible(false);
		///////////////////////////////////

		// SECRETARY PANEL //////////////////
		secPanel = new JPanel(new BorderLayout());
		secPanel.setBackground(Colors.LIGHT);

		JPanel titleSec = new JPanel(new BorderLayout());
		titleSec.setPreferredSize(new Dimension(0, 24));
		titleSec.setBackground(Colors.DARK);
		CLabel textSec = new CLabel("Secretary");
		textSec.setForeground(Colors.LIGHT);
		titleSec.add(textSec, BorderLayout.CENTER);
		CLabel deleteSec = new CLabel("X");
		deleteSec.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteSec.setPreferredSize(new Dimension(15, 0));
		deleteSec.setForeground(Colors.LIGHT);

		titleSec.add(deleteSec, BorderLayout.EAST);
		secPanel.add(titleSec, BorderLayout.NORTH);

		secPic = new CLabel();
		secPic.setPreferredSize(new Dimension(80, 80));
		secPanel.add(secPic, BorderLayout.WEST);

		JPanel secCenterRootPanel = new JPanel(new GridLayout(3, 1, 2, 2));
		secCenterRootPanel.setBackground(Colors.LIGHT);

		secId = new CLabel();
		secId.setText(null);
		secId.setPreferredSize(new Dimension(50, 20));
		secId.setBackground(Colors.LIGHT);
		secId.setBorder(null);
		secId.setFont(new Font(fontString, 0, 20));
		secCenterRootPanel.add(secId);

		secName = new CLabel();
		secCenterRootPanel.add(secName);

		secCourseYrSec = new CLabel();
		secCenterRootPanel.add(secCourseYrSec);

		secPanel.add(secCenterRootPanel, BorderLayout.CENTER);

		deleteSec.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deleteSec.setForeground(Color.red);
			}

			public void mouseExited(MouseEvent evt) {
				deleteSec.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				if (dbconn != null) {
					try {

						String sql = "UPDATE partialvotedcandidates SET partialVotedSecretary = null WHERE users_schoolId = '"
								+ Login.userSchoolId + "'";
						Statement st = dbconn.createStatement();
						st.executeUpdate(sql);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				secPic.setText(null);
				secPic.setIcon(null);
				secName.setText(null);
				secPanel.setVisible(false);

			}
		});

		root.add(secPanel);
		secPanel.setVisible(false);
		/////////////////////////////////////

		// AUDITOR////////////////////////////
		audiPanel = new JPanel(new BorderLayout());
		audiPanel.setBackground(Colors.LIGHT);

		JPanel titleAudi = new JPanel(new BorderLayout());
		titleAudi.setPreferredSize(new Dimension(0, 24));
		titleAudi.setBackground(Colors.DARK);
		CLabel textAudi = new CLabel("Auditor");
		textAudi.setForeground(Colors.LIGHT);
		titleAudi.add(textAudi, BorderLayout.CENTER);
		CLabel deleteAudi = new CLabel("X");
		deleteAudi.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteAudi.setPreferredSize(new Dimension(15, 0));
		deleteAudi.setForeground(Colors.LIGHT);

		titleAudi.add(deleteAudi, BorderLayout.EAST);
		audiPanel.add(titleAudi, BorderLayout.NORTH);

		audPic = new CLabel();
		audPic.setPreferredSize(new Dimension(80, 80));
		audiPanel.add(audPic, BorderLayout.WEST);

		JPanel audiCenterRootPanel = new JPanel(new GridLayout(3, 1, 2, 2));
		audiCenterRootPanel.setBackground(Colors.LIGHT);

		audId = new CLabel();
		audId.setText(null);
		audId.setPreferredSize(new Dimension(50, 20));
		audId.setBackground(Colors.LIGHT);
		audId.setBorder(null);
		audId.setFont(new Font(fontString, 0, 20));
		audiCenterRootPanel.add(audId);

		audName = new CLabel();
		audiCenterRootPanel.add(audName);

		audCourseYrSec = new CLabel();
		audiCenterRootPanel.add(audCourseYrSec);

		audiPanel.add(audiCenterRootPanel, BorderLayout.CENTER);

		deleteAudi.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deleteAudi.setForeground(Color.red);
			}

			public void mouseExited(MouseEvent evt) {
				deleteAudi.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				if (dbconn != null) {
					try {

						String sql = "UPDATE partialvotedcandidates SET partialVotedAuditor = null WHERE users_schoolId = '"
								+ Login.userSchoolId + "'";
						Statement st = dbconn.createStatement();
						st.executeUpdate(sql);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				audPic.setText(null);
				audPic.setIcon(null);
				audName.setText(null);
				audiPanel.setVisible(false);

			}
		});

		root.add(audiPanel);
		audiPanel.setVisible(false);
		////////////////////////////////////

		/// TREASURER//////////////////////////
		treasuPanel = new JPanel(new BorderLayout());
		treasuPanel.setBackground(Colors.LIGHT);

		JPanel titleTreasu = new JPanel(new BorderLayout());
		titleTreasu.setPreferredSize(new Dimension(0, 24));
		titleTreasu.setBackground(Colors.DARK);
		CLabel textTreasu = new CLabel("treasurer");
		textTreasu.setForeground(Colors.LIGHT);
		titleTreasu.add(textTreasu, BorderLayout.CENTER);
		CLabel deleteTreasu = new CLabel("X");
		deleteTreasu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteTreasu.setPreferredSize(new Dimension(15, 0));
		deleteTreasu.setForeground(Colors.LIGHT);

		titleTreasu.add(deleteTreasu, BorderLayout.EAST);
		treasuPanel.add(titleTreasu, BorderLayout.NORTH);

		treasuPic = new CLabel();
		treasuPic.setPreferredSize(new Dimension(80, 80));
		treasuPanel.add(treasuPic, BorderLayout.WEST);

		JPanel treasuCenterRootPanel = new JPanel(new GridLayout(3, 1, 2, 2));
		treasuCenterRootPanel.setBackground(Colors.LIGHT);

		treasuId = new CLabel();
		treasuId.setText(null);
		treasuId.setPreferredSize(new Dimension(50, 20));
		treasuId.setBackground(Colors.LIGHT);
		treasuId.setBorder(null);
		treasuId.setFont(new Font(fontString, 0, 20));
		treasuCenterRootPanel.add(treasuId);

		treasuName = new CLabel();
		treasuCenterRootPanel.add(treasuName);

		treasuCourseYrSec = new CLabel();
		treasuCenterRootPanel.add(treasuCourseYrSec);

		treasuPanel.add(treasuCenterRootPanel, BorderLayout.CENTER);

		deleteTreasu.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deleteTreasu.setForeground(Color.red);
			}

			public void mouseExited(MouseEvent evt) {
				deleteTreasu.setForeground(Colors.LIGHT);
			}

			public void mouseClicked(MouseEvent evt) {

				if (dbconn != null) {
					try {

						String sql = "UPDATE partialvotedcandidates SET partialVotedTreasurer = null WHERE users_schoolId = '"
								+ Login.userSchoolId + "'";
						Statement st = dbconn.createStatement();
						st.executeUpdate(sql);

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				treasuId.setText(null);
				treasuPic.setIcon(null);
				treasuName.setText(null);
				treasuPanel.setVisible(false);

			}
		});

		root.add(treasuPanel);
		treasuPanel.setVisible(false);
		/////////////////////////////////////

		JScrollPane scrollPane = new JScrollPane(root);
		selectedCandidatesBasePanel.add(scrollPane, BorderLayout.CENTER);

		votingBasePanel.contentBasePanel.add(selectedCandidatesBasePanel, BorderLayout.EAST);
		testVoteButton = new CButton("Vote");
		testVoteButton.addActionListener(this);
		selectedCandidatesBasePanel.add(testVoteButton, BorderLayout.SOUTH);
		////////////////////////////////////////////////////////////////////////
		JPanel northFillerPanel = new JPanel();
		northFillerPanel.setBackground(Colors.LIGHT);
		northFillerPanel.setPreferredSize(new Dimension(0, 20));
		votingBasePanel.contentBasePanel.add(northFillerPanel, BorderLayout.NORTH);

		JPanel southFillerPanel = new JPanel();
		southFillerPanel.setBackground(Colors.LIGHT);
		southFillerPanel.setPreferredSize(new Dimension(0, 40));

		showTransactionId = new CLabel();
		southFillerPanel.add(showTransactionId);
		votingBasePanel.contentBasePanel.add(southFillerPanel, BorderLayout.SOUTH);

		voteBtnPanel = new JPanel();
		voteBtnPanel.setBackground(Colors.LIGHT);
		voteBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		hcpCSubN.add(voteBtnPanel);

		hasVoted = false;
		if (dbconn != null) {
			try {
				String getHasVotedStatus = "SELECT hasVoted FROM userstable WHERE schoolId = '" + Login.userSchoolId
						+ "'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getHasVotedStatus);
				if (res.next()) {
					hasVoted = res.getBoolean("hasVoted");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		viewVotedCandidatesBtn = new CButton("View your candidates");
		viewVotedCandidatesBtn.setPreferredSize(new Dimension(420, 60));
		viewVotedCandidatesBtn.addActionListener(this);
		viewVotedCandidatesBtn.setVisible(false);

		voteBtn = new CButton("VOTE!");

		voteBtn.setPreferredSize(new Dimension(420, 60));
		voteBtn.setIcon(
				new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_elections_50px_1.png")));
		voteBtn.addActionListener(this);

		// CHECK IF HAS USER HAS VOTED

		voteBtn.setVisible(false);

		if (checkGoSignalStatus() == true) {
			voteBtn.setVisible(true);
			if (hasVoted == true) {
				voteBtn.setVisible(false);
				viewVotedCandidatesBtn.setVisible(true);
			} else {
				voteBtn.setVisible(true);
				viewVotedCandidatesBtn.setVisible(false);
			}
		} else {
			viewVotedCandidatesBtn.setVisible(true);
			voteBtn.setVisible(false);
		}
		voteBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				voteBtn.setIcon(
						new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_elections_50px.png")));
			}

			public void mouseExited(MouseEvent evt) {
				voteBtn.setIcon(new ImageIcon(
						getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_elections_50px_1.png")));
			}
		});

		changeThemePanel = new JPanel();
		changeThemePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		changeThemeBtn = new CButton("Change Theme");
		changeThemeBtn.setPreferredSize(new Dimension(420, 60));
		changeThemeBtn.setIcon(
				new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_elections_50px_1.png")));
		changeThemeBtn.addActionListener(this);
		changeThemeBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				changeThemeBtn.setIcon(
						new ImageIcon(getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_elections_50px.png")));
			}

			public void mouseExited(MouseEvent evt) {
				changeThemeBtn.setIcon(new ImageIcon(
						getClass().getResource("/image_assets/usrDbrd_Imgs/icons8_elections_50px_1.png")));
			}
		});

		voteBtnPanel.add(voteBtn);
		voteBtnPanel.add(viewVotedCandidatesBtn);
		changeThemePanel.add(changeThemeBtn);

		// CLOCK

		timeFormat = new SimpleDateFormat("hh:mm a");
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

		// ADDING OF COMPONENTS IN ORDER
		homeIconPanel.add(homeIcon);
		homeIconPanel.add(homeLabel);

		accIconPanel.add(accIcon);
		accIconPanel.add(accIconLabel);

		leftNavIconsPanel.add(menuIcon);
		leftNavIconsPanel.add(accIconPanel);
		leftNavIconsPanel.add(lgoutPanel);
		leftNavIconsPanel.add(homeIconPanel);

		leftNavPanel.add(leftNavIconsPanel, BorderLayout.CENTER);
		leftNavPanel.add(clockPanel, BorderLayout.SOUTH);

		hcpBasePanel.add(hcpNorthBasePanel, BorderLayout.NORTH);
		hcpBasePanel.add(hcpL, BorderLayout.WEST);
		hcpBasePanel.add(hcpR, BorderLayout.EAST);
		hcpBasePanel.add(hcpC, BorderLayout.CENTER);

		userDashboardFrame.add(newPage(votingBasePanel, "Vote madafaka"));
		userDashboardFrame.add(newPage(votedCandidatesBasePanel, "View your candidates"));

		userDashboardFrame.add(accBasePanel);
		userDashboardFrame.add(leftNavPanel, BorderLayout.WEST);
		userDashboardFrame.add(hcpBasePanel, BorderLayout.EAST);

		userDashboardFrame.setVisible(true);
	}

	private CPanel newPage(CPanel panel, String panelTitle) {

		panel.setCustomPanelTitle(panelTitle);
		panel.customFunc(accBasePanel, hcpBasePanel, leftNavPanel, panel);
		panel.setVisible(false);

		return panel;
	}

	private ArrayList<GetVotedCandidates> getVotedCandidatesData() {
		ArrayList<GetVotedCandidates> list = new ArrayList<GetVotedCandidates>();
		if (dbconn != null) {
			try {
				String tableName = null;
				Statement st = dbconn.createStatement();
				ResultSet res = st
						.executeQuery("SELECT tableName FROM userstable WHERE schoolId = '" + Login.userSchoolId + "'");
				if (res.next()) {
					tableName = res.getString("tableName");
				}
				GetVotedCandidates candidates;

				String getVotedCandidatesTable = "SELECT * FROM " + tableName + "";
				System.out.println(getVotedCandidatesTable);
				Statement st2 = dbconn.createStatement();
				ResultSet rs = st2.executeQuery(getVotedCandidatesTable);

				while (rs.next()) {
					candidates = new GetVotedCandidates(rs.getBytes("img"),
							"" + rs.getString("candFirstName") + " " + rs.getString("candLastName") + "",
							rs.getString("course"), rs.getString("yearNSection"), rs.getString("runningFor"),
							rs.getString("partyList"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public JScrollPane showVotedCandidatesTable() {

		votedCandidatesTable = new JTable();
		votedCandidatesTable.setDefaultEditor(Object.class, null);
		votedCandidatesTable.setFillsViewportHeight(true);
		votedCandidatesTable.setBackground(Colors.LIGHT);
		votedCandidatesTable.setFont(myFont);
		votedCandidatesTable.setForeground(Colors.DARK);

		votedCandidatesTable.setRowSelectionAllowed(true);
		votedCandidatesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = votedCandidatesTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetVotedCandidates> list = getVotedCandidatesData();
		String[] columnNames = { "Img", "Name", "Course", "Yr/Section", "Position", "Partylist" };
		Object[][] rows = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getImg() != null) {
				ImageIcon image = new ImageIcon(
						new ImageIcon(list.get(i).getImg()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getName();
			rows[i][2] = list.get(i).getCourse();
			rows[i][3] = list.get(i).getYrSection();
			rows[i][4] = list.get(i).getPosition();
			rows[i][5] = list.get(i).getpList();
		}

		MyTableModel model = new MyTableModel(rows, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return Icon.class;
				} else {
					return getValueAt(0, column).getClass();
				}
			}
		};
		votedCandidatesTable.setModel(model);
		adjustTable(votedCandidatesTable);

		JScrollPane scrollPane = new JScrollPane(votedCandidatesTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		return scrollPane;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == voteBtn) {

			if (dbconn != null) {
				try {
					String checkIfUserAlreadyRegisteredToPartialVotesCommand = "SELECT partialRegistered FROM userstable WHERE schoolId = "
							+ Login.userSchoolId + "";
					Statement stmnt = dbconn.createStatement();
					ResultSet res = stmnt.executeQuery(checkIfUserAlreadyRegisteredToPartialVotesCommand);

					if (res.next()) {
						boolean registeredToPartial = res.getBoolean("partialRegistered");
						if (registeredToPartial == false) {

							try {
								String registerToPartialVotes = "INSERT INTO partialvotedcandidates(users_schoolId) VALUES ("
										+ Login.userSchoolId + ")";
								Statement st = dbconn.createStatement();
								st.executeUpdate(registerToPartialVotes);

								String setUserRegisteredToPartialVotesCommand = "UPDATE userstable SET partialRegistered = true WHERE schoolId = '"
										+ Login.userSchoolId + "'";
								st.executeUpdate(setUserRegisteredToPartialVotesCommand);

							} catch (SQLException e2) {
								e2.printStackTrace();
							}

						}
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

			panelSwitch(votingBasePanel);

		} else if (e.getSource() == changeThemeBtn) {
			Colors.setTheme("Cameo Pink");
			userDashboardFrame.dispose();
			new UserDashboard();
		}

		else if (e.getSource() == testVoteButton) {
			boolean hasVoted = false;

			if (dbconn != null) {
				try {
					Statement st = dbconn.createStatement();
					ResultSet res = st.executeQuery(
							"SELECT hasVoted FROM userstable WHERE schoolId = '" + Login.userSchoolId + "'");
					if (res.next()) {
						hasVoted = res.getBoolean("hasVoted");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

			if (hasVoted == true) {
				JOptionPane.showMessageDialog(null, "YOU HAVE ALREADY VOTED!");
			} else {

				String checkForPres = presId.getText();
				String checkForVicePres = vPresId.getText();
				String checkForSec = secId.getText();
				String checkForAuditor = audId.getText();
				String checkforTreasu = treasuId.getText();

				if (checkForPres == null && checkForVicePres == null && checkForSec == null && checkForAuditor == null
						&& checkforTreasu == null) {
					JOptionPane.showMessageDialog(null, "Please select atleast one(1) candidate", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// INSERT INITIAL DATA FOR USER PARTIAL VOTED CANDIDATES REVIEW
					insertPartialCandidates(checkForPres, checkForVicePres, checkForSec, checkForAuditor,
							checkforTreasu);

					new ConfirmVotedCandidatesDialog();
					SigMethods.enableGlassPane(userDashboardFrame);

				}
			}

		} else if (e.getSource() == viewVotedCandidatesBtn) {
			panelSwitch(votedCandidatesBasePanel);
		} else if (e.getSource() == printBtn) {
			String transacId = null;
			String dateVoted = null;
			try {
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(
						"SELECT transactionId, dateVoted FROM userVoted WHERE schoolId = '" + Login.userSchoolId + "'");
				if (res.next()) {
					transacId = res.getString("transactionId");
					dateVoted = res.getString("dateVoted");
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			MessageFormat header = new MessageFormat("Id: " + transacId + " on " + dateVoted);
			MessageFormat footer = new MessageFormat("@Group3");
			try {
				votedCandidatesTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
			} catch (java.awt.print.PrinterAbortException e1) {
			} catch (PrinterException ex) {
				System.out.println("noo");
			}
		}

	}

	void insertPartialCandidates(String pres, String vPres, String sec, String auditor, String treasurer) {

		if (pres != null) {
			if (dbconn != null) {
				try {
					String sql = "INSERT INTO partials " + "SELECT partialvotedcandidates.partialVotedPres, "
							+ "candidates.img, " + "userstable.lastName, " + "userstable.firstName, "
							+ "userstable.course, " + "userstable.yearNSection, " + "candidates.runningFor, "
							+ "candidates.partylist, partialvotedcandidates.users_schoolId\r\n"
							+ "FROM partialvotedcandidates INNER JOIN candidates ON partialvotedcandidates.partialVotedPres = candidates.candidateId \r\n"
							+ "INNER JOIN userstable ON candidates.users_schoolId = userstable.schoolId "
							+ "WHERE partialvotedcandidates.users_schoolId = '" + Login.userSchoolId + "'";
					Statement st = dbconn.createStatement();
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (vPres != null) {
			if (dbconn != null) {
				try {
					String sql = "INSERT INTO partials " + "SELECT partialvotedcandidates.partialVotedVicePres, "
							+ "candidates.img, " + "userstable.lastName, " + "userstable.firstName, "
							+ "userstable.course, " + "userstable.yearNSection, " + "candidates.runningFor, "
							+ "candidates.partylist, partialvotedcandidates.users_schoolId\r\n"
							+ "FROM partialvotedcandidates INNER JOIN candidates ON partialvotedcandidates.partialVotedVicePres = candidates.candidateId \r\n"
							+ "INNER JOIN userstable ON candidates.users_schoolId = userstable.schoolId "
							+ "WHERE partialvotedcandidates.users_schoolId = '" + Login.userSchoolId + "'";
					Statement st = dbconn.createStatement();
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (sec != null) {
			if (dbconn != null) {
				try {
					String sql = "INSERT INTO partials " + "SELECT partialvotedcandidates.partialVotedSecretary, "
							+ "candidates.img, " + "userstable.lastName, " + "userstable.firstName, "
							+ "userstable.course, " + "userstable.yearNSection, " + "candidates.runningFor, "
							+ "candidates.partylist, partialvotedcandidates.users_schoolId\r\n"
							+ "FROM partialvotedcandidates INNER JOIN candidates ON partialvotedcandidates.partialVotedSecretary = candidates.candidateId \r\n"
							+ "INNER JOIN userstable ON candidates.users_schoolId = userstable.schoolId "
							+ "WHERE partialvotedcandidates.users_schoolId = '" + Login.userSchoolId + "'";
					Statement st = dbconn.createStatement();
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (auditor != null) {
			if (dbconn != null) {
				try {
					String sql = "INSERT INTO partials " + "SELECT partialvotedcandidates.partialVotedAuditor, "
							+ "candidates.img, " + "userstable.lastName, " + "userstable.firstName, "
							+ "userstable.course, " + "userstable.yearNSection, " + "candidates.runningFor, "
							+ "candidates.partylist, partialvotedcandidates.users_schoolId\r\n"
							+ "FROM partialvotedcandidates INNER JOIN candidates ON partialvotedcandidates.partialVotedAuditor = candidates.candidateId \r\n"
							+ "INNER JOIN userstable ON candidates.users_schoolId = userstable.schoolId "
							+ "WHERE partialvotedcandidates.users_schoolId = '" + Login.userSchoolId + "'";
					Statement st = dbconn.createStatement();
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (treasurer != null) {
			if (dbconn != null) {
				try {
					String sql = "INSERT INTO partials " + "SELECT partialvotedcandidates.partialVotedTreasurer, "
							+ "candidates.img, " + "userstable.lastName, " + "userstable.firstName, "
							+ "userstable.course, " + "userstable.yearNSection, " + "candidates.runningFor, "
							+ "candidates.partylist, partialvotedcandidates.users_schoolId\r\n"
							+ "FROM partialvotedcandidates INNER JOIN candidates ON partialvotedcandidates.partialVotedTreasurer = candidates.candidateId \r\n"
							+ "INNER JOIN userstable ON candidates.users_schoolId = userstable.schoolId "
							+ "WHERE partialvotedcandidates.users_schoolId = '" + Login.userSchoolId + "'";
					Statement st = dbconn.createStatement();
					st.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public boolean checkGoSignalStatus() {

		if (dbconn != null) {
			Statement stmnt;
			try {
				stmnt = dbconn.createStatement();
				String query = "SELECT voteEnabled FROM userstable";
				ResultSet res = stmnt.executeQuery(query);

				if (res.next()) {
					goSignal = res.getBoolean("voteEnabled");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		return goSignal;
	}

	private ArrayList<GetCandidates> getPresCandidatesData() {
		ArrayList<GetCandidates> list = new ArrayList<GetCandidates>();
		if (dbconn != null) {
			try {
				GetCandidates candidates;

				String getCandidatesForPresidentsCommand = "SELECT candidates.candidateId, candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.partyList\r\n"
						+ "FROM candidates JOIN userstable WHERE candidates.users_schoolId = userstable.schoolId AND candidates.runningFor = 'President'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getCandidatesForPresidentsCommand);

				while (res.next()) {
					candidates = new GetCandidates(res.getInt("candidates.candidateId"), res.getBytes("candidates.img"),
							"" + res.getString("userstable.lastName") + ", " + res.getString("userstable.firstName")
									+ "",
							res.getString("userstable.course"), res.getString("userstable.yearNSection"),
							res.getString("candidates.partyList"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetCandidates> getVicePresCandidatesData() {
		ArrayList<GetCandidates> list = new ArrayList<GetCandidates>();
		if (dbconn != null) {
			try {
				GetCandidates candidates;

				String getCandidatesForVicePresidentsCommand = "SELECT candidates.candidateId, candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.partyList\r\n"
						+ "FROM candidates JOIN userstable WHERE candidates.users_schoolId = userstable.schoolId AND candidates.runningFor = 'Vice-President'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getCandidatesForVicePresidentsCommand);

				while (res.next()) {
					candidates = new GetCandidates(res.getInt("candidates.candidateId"), res.getBytes("candidates.img"),
							"" + res.getString("userstable.lastName") + ", " + res.getString("userstable.firstName")
									+ "",
							res.getString("userstable.course"), res.getString("userstable.yearNSection"),
							res.getString("candidates.partyList"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetCandidates> getSecretaryCandidatesData() {
		ArrayList<GetCandidates> list = new ArrayList<GetCandidates>();
		if (dbconn != null) {
			try {
				GetCandidates candidates;

				String getCandidatesForSecretariesCommand = "SELECT candidates.candidateId, candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.partyList\r\n"
						+ "FROM candidates JOIN userstable WHERE candidates.users_schoolId = userstable.schoolId AND candidates.runningFor = 'Secretary'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getCandidatesForSecretariesCommand);

				while (res.next()) {
					candidates = new GetCandidates(res.getInt("candidates.candidateId"), res.getBytes("candidates.img"),
							"" + res.getString("userstable.lastName") + ", " + res.getString("userstable.firstName")
									+ "",
							res.getString("userstable.course"), res.getString("userstable.yearNSection"),
							res.getString("candidates.partyList"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetCandidates> getAuditorCandidatesData() {
		ArrayList<GetCandidates> list = new ArrayList<GetCandidates>();
		if (dbconn != null) {
			try {
				GetCandidates candidates;

				String getCandidatesForAuditorCommand = "SELECT candidates.candidateId, candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.partyList\r\n"
						+ "FROM candidates JOIN userstable WHERE candidates.users_schoolId = userstable.schoolId AND candidates.runningFor = 'Auditor'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getCandidatesForAuditorCommand);

				while (res.next()) {
					candidates = new GetCandidates(res.getInt("candidates.candidateId"), res.getBytes("candidates.img"),
							"" + res.getString("userstable.lastName") + ", " + res.getString("userstable.firstName")
									+ "",
							res.getString("userstable.course"), res.getString("userstable.yearNSection"),
							res.getString("candidates.partyList"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private ArrayList<GetCandidates> getTreasurerCandidatesData() {
		ArrayList<GetCandidates> list = new ArrayList<GetCandidates>();
		if (dbconn != null) {
			try {
				GetCandidates candidates;

				String getCandidatesForTreasurerCommand = "SELECT candidates.candidateId, candidates.img, userstable.lastName, userstable.firstName, userstable.course, userstable.yearNSection, candidates.partyList\r\n"
						+ "FROM candidates JOIN userstable WHERE candidates.users_schoolId = userstable.schoolId AND candidates.runningFor = 'Treasurer'";
				Statement st = dbconn.createStatement();
				ResultSet res = st.executeQuery(getCandidatesForTreasurerCommand);

				while (res.next()) {
					candidates = new GetCandidates(res.getInt("candidates.candidateId"), res.getBytes("candidates.img"),
							"" + res.getString("userstable.lastName") + ", " + res.getString("userstable.firstName")
									+ "",
							res.getString("userstable.course"), res.getString("userstable.yearNSection"),
							res.getString("candidates.partyList"));
					list.add(candidates);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public JScrollPane showPresTableData() {

		JTable presidentsTable = new JTable();
		presidentsTable.setDefaultEditor(Object.class, null);
		presidentsTable.setFillsViewportHeight(true);
		presidentsTable.setBackground(Colors.LIGHT);
		presidentsTable.setFont(myFont);
		presidentsTable.setForeground(Colors.DARK);

		presidentsTable.setRowSelectionAllowed(true);
		presidentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = presidentsTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetCandidates> list = getPresCandidatesData();
		String[] columnNames = { "Img", "Name", "Course", "Yr/Section", "PartyList", "id" };
		Object[][] rows = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImage() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImage()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandCourse();
			rows[i][3] = list.get(i).getCandYearAndSection();
			rows[i][4] = list.get(i).getCandPartyList();
			rows[i][5] = list.get(i).getCandId();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);
		presidentsTable.setModel(new NumberedTableModel(model));
		adjustTableSizeAccordingToContent(presidentsTable);

		TableColumnModel tcm = presidentsTable.getColumnModel();
		tcm.removeColumn(tcm.getColumn(1));
		tcm.removeColumn(tcm.getColumn(5));

		presidentsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = presidentsTable.getSelectedRow();

					if (presidentsTable.getModel().getValueAt(index, 1) != null) {
						presPanel.setVisible(true);
						ImageIcon imageIcon = (ImageIcon) presidentsTable.getModel().getValueAt(index, 1);
						Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
						ImageIcon finalImage = new ImageIcon(image);
						presPic.setIcon(finalImage);

						String selectedName = (String) presidentsTable.getValueAt(index, 1);
						presName.setText(selectedName);

						String course = (String) presidentsTable.getValueAt(index, 2);
						String yrSec = (String) presidentsTable.getValueAt(index, 3);
						String courseYrSec = course + " " + yrSec;

						presCourseYrSec.setText(courseYrSec);

						int selectedId = (int) presidentsTable.getModel().getValueAt(index, 6);

						presId.setText(String.valueOf(selectedId));

						if (dbconn != null) {
							try {

								String sql = "UPDATE partialvotedcandidates SET partialVotedPres = " + selectedId
										+ " WHERE users_schoolId = '" + Login.userSchoolId + "'";
								Statement st = dbconn.createStatement();
								st.executeUpdate(sql);

							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

					} else {
						System.out.println("No Image");
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(presidentsTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// additional methods for accomodating table data widths

		return scrollPane;

	}

	public JScrollPane showVicePresTableData() {

		JTable vPresidentsTable = new JTable();
		vPresidentsTable.setDefaultEditor(Object.class, null);
		vPresidentsTable.setFillsViewportHeight(true);
		vPresidentsTable.setBackground(Colors.LIGHT);
		vPresidentsTable.setFont(myFont);
		vPresidentsTable.setForeground(Colors.DARK);

		vPresidentsTable.setRowSelectionAllowed(true);
		vPresidentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = vPresidentsTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetCandidates> list = getVicePresCandidatesData();
		String[] columnNames = { "Img", "Name", "Course", "Yr/Section", "PartyList", "id" };
		Object[][] rows = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImage() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImage()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandCourse();
			rows[i][3] = list.get(i).getCandYearAndSection();
			rows[i][4] = list.get(i).getCandPartyList();
			rows[i][5] = list.get(i).getCandId();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);
		vPresidentsTable.setModel(new NumberedTableModel(model));
		adjustTableSizeAccordingToContent(vPresidentsTable);

		TableColumnModel tcm = vPresidentsTable.getColumnModel();
		tcm.removeColumn(tcm.getColumn(1));
		tcm.removeColumn(tcm.getColumn(5));
		vPresidentsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = vPresidentsTable.getSelectedRow();

					if (vPresidentsTable.getModel().getValueAt(index, 1) != null) {
						vPresPanel.setVisible(true);
						ImageIcon imageIcon = (ImageIcon) vPresidentsTable.getModel().getValueAt(index, 1);
						Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
						ImageIcon finalImage = new ImageIcon(image);
						vPresPic.setIcon(finalImage);

						String selectedName = (String) vPresidentsTable.getValueAt(index, 1);
						vPresName.setText(selectedName);

						String course = (String) vPresidentsTable.getValueAt(index, 2);
						String yrSec = (String) vPresidentsTable.getValueAt(index, 3);
						String courseYrSec = course + " " + yrSec;

						vPresCourseYrSec.setText(courseYrSec);

						int selectedId = (int) vPresidentsTable.getModel().getValueAt(index, 6);

						vPresId.setText(String.valueOf(selectedId));

						if (dbconn != null) {
							try {

								String sql = "UPDATE partialvotedcandidates SET partialVotedVicePres = " + selectedId
										+ " WHERE users_schoolId = '" + Login.userSchoolId + "'";
								Statement st = dbconn.createStatement();
								st.executeUpdate(sql);

							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					} else {
						System.out.println("No Image");
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(vPresidentsTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// additional methods for accomodating table data widths

		return scrollPane;

	}

	public JScrollPane showSecretariesTableData() {

		JTable secretaries = new JTable();
		secretaries.setDefaultEditor(Object.class, null);
		secretaries.setFillsViewportHeight(true);
		secretaries.setBackground(Colors.LIGHT);
		secretaries.setFont(myFont);
		secretaries.setForeground(Colors.DARK);

		secretaries.setRowSelectionAllowed(true);
		secretaries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = secretaries.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetCandidates> list = getSecretaryCandidatesData();
		String[] columnNames = { "Img", "Name", "Course", "Yr/Section", "PartyList", "id" };
		Object[][] rows = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImage() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImage()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandCourse();
			rows[i][3] = list.get(i).getCandYearAndSection();
			rows[i][4] = list.get(i).getCandPartyList();
			rows[i][5] = list.get(i).getCandId();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);
		secretaries.setModel(new NumberedTableModel(model));
		adjustTableSizeAccordingToContent(secretaries);

		TableColumnModel tcm = secretaries.getColumnModel();
		tcm.removeColumn(tcm.getColumn(1));
		tcm.removeColumn(tcm.getColumn(5));

		secretaries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = secretaries.getSelectedRow();

					if (secretaries.getModel().getValueAt(index, 1) != null) {
						secPanel.setVisible(true);
						ImageIcon imageIcon = (ImageIcon) secretaries.getModel().getValueAt(index, 1);
						Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
						ImageIcon finalImage = new ImageIcon(image);
						secPic.setIcon(finalImage);

						String selectedName = (String) secretaries.getValueAt(index, 1);
						secName.setText(selectedName);

						String course = (String) secretaries.getValueAt(index, 2);
						String yrSec = (String) secretaries.getValueAt(index, 3);
						String courseYrSec = course + " " + yrSec;

						secCourseYrSec.setText(courseYrSec);

						int selectedId = (int) secretaries.getModel().getValueAt(index, 6);

						secId.setText(String.valueOf(selectedId));

						if (dbconn != null) {
							try {

								String sql = "UPDATE partialvotedcandidates SET partialVotedSecretary = " + selectedId
										+ " WHERE users_schoolId = '" + Login.userSchoolId + "'";
								Statement st = dbconn.createStatement();
								st.executeUpdate(sql);

							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

					} else {
						System.out.println("No Image");
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(secretaries);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// additional methods for accomodating table data widths

		return scrollPane;

	}

	public JScrollPane showAuditorsTableData() {

		JTable auditorsTable = new JTable();
		auditorsTable.setDefaultEditor(Object.class, null);
		auditorsTable.setFillsViewportHeight(true);
		auditorsTable.setBackground(Colors.LIGHT);
		auditorsTable.setFont(myFont);
		auditorsTable.setForeground(Colors.DARK);

		auditorsTable.setRowSelectionAllowed(true);
		auditorsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = auditorsTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetCandidates> list = getAuditorCandidatesData();
		String[] columnNames = { "Img", "Name", "Course", "Yr/Section", "PartyList", "id" };
		Object[][] rows = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImage() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImage()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandCourse();
			rows[i][3] = list.get(i).getCandYearAndSection();
			rows[i][4] = list.get(i).getCandPartyList();
			rows[i][5] = list.get(i).getCandId();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);
		auditorsTable.setModel(new NumberedTableModel(model));
		adjustTableSizeAccordingToContent(auditorsTable);

		TableColumnModel tcm = auditorsTable.getColumnModel();
		tcm.removeColumn(tcm.getColumn(1));
		tcm.removeColumn(tcm.getColumn(5));

		auditorsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = auditorsTable.getSelectedRow();

					if (auditorsTable.getModel().getValueAt(index, 1) != null) {
						audiPanel.setVisible(true);
						ImageIcon imageIcon = (ImageIcon) auditorsTable.getModel().getValueAt(index, 1);
						Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
						ImageIcon finalImage = new ImageIcon(image);
						audPic.setIcon(finalImage);

						String selectedName = (String) auditorsTable.getValueAt(index, 1);
						audName.setText(selectedName);

						String course = (String) auditorsTable.getValueAt(index, 2);
						String yrSec = (String) auditorsTable.getValueAt(index, 3);
						String courseYrSec = course + " " + yrSec;

						audCourseYrSec.setText(courseYrSec);

						int selectedId = (int) auditorsTable.getModel().getValueAt(index, 6);

						audId.setText(String.valueOf(selectedId));

						if (dbconn != null) {
							try {

								String sql = "UPDATE partialvotedcandidates SET partialVotedAuditor = " + selectedId
										+ " WHERE users_schoolId = '" + Login.userSchoolId + "'";
								Statement st = dbconn.createStatement();
								st.executeUpdate(sql);

							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

					} else {
						System.out.println("No Image");
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(auditorsTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// additional methods for accomodating table data widths

		return scrollPane;

	}

	public JScrollPane showTreasurersTableData() {

		JTable treasurersTable = new JTable();
		treasurersTable.setDefaultEditor(Object.class, null);
		treasurersTable.setFillsViewportHeight(true);
		treasurersTable.setBackground(Colors.LIGHT);
		treasurersTable.setFont(myFont);
		treasurersTable.setForeground(Colors.DARK);

		treasurersTable.setRowSelectionAllowed(true);
		treasurersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = treasurersTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setPreferredSize(new Dimension(header.getWidth(), 40));
		header.setFont(new Font(fontString, 0, 18));
		header.setBackground(Colors.DARK);
		header.setForeground(Colors.LIGHT);

		ArrayList<GetCandidates> list = getTreasurerCandidatesData();
		String[] columnNames = { "Img", "Name", "Course", "Yr/Section", "PartyList", "id" };
		Object[][] rows = new Object[list.size()][6];
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getCandImage() != null) {
				ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getCandImage()).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				rows[i][0] = image;
			} else {
				rows[i][0] = null;
			}
			rows[i][1] = list.get(i).getCandName();
			rows[i][2] = list.get(i).getCandCourse();
			rows[i][3] = list.get(i).getCandYearAndSection();
			rows[i][4] = list.get(i).getCandPartyList();
			rows[i][5] = list.get(i).getCandId();
		}

		MyTableModel model = new MyTableModel(rows, columnNames);
		treasurersTable.setModel(new NumberedTableModel(model));
		adjustTableSizeAccordingToContent(treasurersTable);

		TableColumnModel tcm = treasurersTable.getColumnModel();
		tcm.removeColumn(tcm.getColumn(1));
		tcm.removeColumn(tcm.getColumn(5));

		treasurersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = treasurersTable.getSelectedRow();

					if (treasurersTable.getModel().getValueAt(index, 1) != null) {
						treasuPanel.setVisible(true);
						ImageIcon imageIcon = (ImageIcon) treasurersTable.getModel().getValueAt(index, 1);
						Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
						ImageIcon finalImage = new ImageIcon(image);
						treasuPic.setIcon(finalImage);

						String selectedName = (String) treasurersTable.getValueAt(index, 1);
						treasuName.setText(selectedName);

						String course = (String) treasurersTable.getValueAt(index, 2);
						String yrSec = (String) treasurersTable.getValueAt(index, 3);
						String courseYrSec = course + " " + yrSec;

						treasuCourseYrSec.setText(courseYrSec);

						int selectedId = (int) treasurersTable.getModel().getValueAt(index, 6);

						treasuId.setText(String.valueOf(selectedId));

						if (dbconn != null) {
							try {

								String sql = "UPDATE partialvotedcandidates SET partialVotedTreasurer = " + selectedId
										+ " WHERE users_schoolId = '" + Login.userSchoolId + "'";
								Statement st = dbconn.createStatement();
								st.executeUpdate(sql);

							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

					} else {
						System.out.println("No Image");
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(treasurersTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));

		// additional methods for accomodating table data widths

		return scrollPane;

	}

	/*
	 * private JScrollPane showPresidentsVotingTable() { JTable presidentsTable =
	 * new JTable(); presidentsTable.setDefaultEditor(Object.class, null);
	 * presidentsTable.setFillsViewportHeight(true);
	 * presidentsTable.setBackground(Colors.LIGHT); presidentsTable.setFont(myFont);
	 * presidentsTable.setForeground(Colors.DARK);
	 * 
	 * presidentsTable.setRowSelectionAllowed(true);
	 * presidentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 * 
	 * JTableHeader header = presidentsTable.getTableHeader();
	 * header.setResizingAllowed(false); header.setReorderingAllowed(false);
	 * header.setPreferredSize(new Dimension(header.getWidth(), 40));
	 * header.setFont(new Font(fontString, 0, 18));
	 * header.setBackground(Colors.DARK); header.setForeground(Colors.LIGHT);
	 * 
	 * JScrollPane scrollPane = new JScrollPane(presidentsTable);
	 * scrollPane.setHorizontalScrollBarPolicy(JScrollPane.
	 * HORIZONTAL_SCROLLBAR_AS_NEEDED);
	 * scrollPane.setVerticalScrollBarPolicy(JScrollPane.
	 * VERTICAL_SCROLLBAR_AS_NEEDED);
	 * scrollPane.setBorder(BorderFactory.createLineBorder(Colors.DARK));
	 * 
	 * // load data from database to table String[] columnNames = { "Name",
	 * "Course", "Year and Section", "Partylist" }; DefaultTableModel model = new
	 * DefaultTableModel(); model.setColumnIdentifiers(columnNames); if (dbconn !=
	 * null) { try {
	 * 
	 * String getCandidatesForPresidentsCommand =
	 * "SELECT candidates.candidateId, users.lastName, users.firstName, users.course, users.yearNSection, candidates.partyList\r\n"
	 * +
	 * "FROM candidates JOIN users WHERE candidates.users_schoolId = users.schoolId AND candidates.runningFor = 'President'"
	 * ; Statement st = dbconn.createStatement(); ResultSet res =
	 * st.executeQuery(getCandidatesForPresidentsCommand);
	 * 
	 * while (res.next()) { int candId = res.getInt("candidates.candidateId");
	 * String candFullName = "" + res.getString("users.lastName") + ", " +
	 * res.getString("users.firstName") + ""; String candCourse =
	 * res.getString("users.course"); String candYearNSection =
	 * res.getString("users.yearNSection"); String candPartyList =
	 * res.getString("candidates.partyList");
	 * 
	 * model.addRow(new Object[] { candFullName, candCourse, candYearNSection,
	 * candPartyList }); } presidentsTable.setModel(new NumberedTableModel(model));
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * adjustTableSizeAccordingToContent(presidentsTable);
	 * 
	 * return scrollPane;
	 * 
	 * }
	 */

	// TABLE RENDERES (BEAUTIFIERS)
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
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
			}
			if (width > 200)
				width = 200;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		table.setRowHeight(40);
	}

	public void adjustTable(JTable table) {
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
				table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
			}
			if (width > 200)
				width = 200;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		table.setRowHeight(120);
	}

	public String toString(char arr[]) {
		String string = new String(arr);

		return string;
	}

	public void panelSwitch(JPanel chosenPanel) {
		leftNavPanel.setVisible(false);
		hcpBasePanel.setVisible(false);
		accBasePanel.setVisible(false);
		votingBasePanel.setVisible(false);

		chosenPanel.setVisible(true);
	}

	// REFRESH
	public void refresh() {

		userDashboardFrame.dispose();
		new UserDashboard();
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

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
