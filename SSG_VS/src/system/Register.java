package system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.CPanel;
import myTheme.CPasswordField;
import myTheme.CTextField;
import myTheme.Colors;
import myTheme.CustomFont;
import myTheme.SigMethods;
import myTheme.Utilities;

public class Register implements ActionListener {

	public static JFrame registerFrame;
	Font myFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(myFont);

	Connection dbconn = DBConnection.connectDB();

	private CPanel registerBasePanel;
	private JPanel w, e, idPanel, departmentPanel, emailPanel, regUserTypePanel, passWrdPanel,
			confrmPassPanel, idValidatorPanel, emailValidatorPanel, passValidatorPanel, confrmPassValidatorPanel,
			crsPanel;
	private CTextField idTf, emailTf;
	private CPasswordField passWrdPassFld, confrmPassFld;
	private CLabel idLabel,  departmentLabel, emailLabel, regUserTypeLabel, passWrdLabel,
			confrmPassLabel, idDoesntExistLabel, emailDoesntExistLabel, passErrPromptLabel, confrmPassErrPromptLabel,
			crsLabel;
	private JComboBox<String> deptComboBox, crsComboBox;
	private JCheckBox showPassIcon;
	private JRadioButton regAsUserBtn, regAsAdminBtn;
	private CButton registerBtn;
	private ButtonGroup userAdminBtnGroup;

	/*
	 * ImageIcon checkIcon = new
	 * ImageIcon("image_assets/Rgstr_Imgs/icons8_Done_15px.png"); ImageIcon
	 * defaultIcon = new ImageIcon("image_assets/Rgstr_Imgs/showPassIcon");
	 * ImageIcon selectedRadioBtnIcon = new
	 * ImageIcon("image_assets/Rgstr_Imgs/showPassIcon"); ImageIcon
	 * selectedRadioBtnIcon = new ImageIcon("image_assets/Rgstr_Imgs/showPassIcon");
	 * ImageIcon selectedRadioBtnIcon = new
	 * ImageIcon("image_assets/Rgstr_Imgs/showPassIcon");
	 */

	final int PMIN = 40;

	Register() {
		// JFrame

		initComponents();

	}

	void initComponents() {
		registerFrame = new JFrame();
		ImageIcon JFrameLogo = new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/SSGLogo.png"));
		SigMethods.draggable(registerFrame);
		registerFrame.setIconImage(JFrameLogo.getImage());
		registerFrame.setSize(1100, 750);
		registerFrame.setLocationRelativeTo(null);
		registerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		registerFrame.setUndecorated(true);
		registerFrame.setLayout(null);

		registerBasePanel = new CPanel();
		registerBasePanel.setCustomPanelTitle("Registration page");
		registerBasePanel.customFunc(registerFrame);
		registerFrame.add(registerBasePanel);

		JPanel n = new JPanel();
		n.setPreferredSize(new Dimension(0, 70));
		n.setBackground(Colors.LIGHT);
		registerBasePanel.contentBasePanel.add(n, BorderLayout.NORTH);

		// JPanel wN = new JPanel(); wN.setPreferredSize(new Dimension(0, 70));

		registerBasePanel.eastFillerPanel.add(new Utilities().lightUtilityPanel(registerFrame));
		registerBasePanel.w.setLayout(new BorderLayout());
		registerBasePanel.w.setPreferredSize(new Dimension(120, 0));
		registerBasePanel.w.setBackground(Colors.LIGHT);
		// registerBasePanel.w.add(wN, BorderLayout.NORTH);

		/*
		 * JPanel eN = new JPanel(); eN.setPreferredSize(new Dimension(0, 70));
		 */

		registerBasePanel.e.setLayout(new BorderLayout());
		registerBasePanel.e.setPreferredSize(new Dimension(120, 0));
		registerBasePanel.e.setBackground(Colors.LIGHT);

		// registerBasePanel.e.add(eN, BorderLayout.NORTH);

		// WEST PANEL OF THE CONTENTBASEPANEL FROM CPANEL
		w = new JPanel();
		w.setBackground(Colors.LIGHT);
		w.setPreferredSize(new Dimension(420, 0));
		w.setLayout(new BoxLayout(w, BoxLayout.Y_AXIS));
		w.setBorder(new EmptyBorder(0, 90, 0, 20));

		idPanel = new JPanel();
		idPanel.setPreferredSize(new Dimension(0, PMIN));
		idPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		idPanel.setBackground(Colors.LIGHT);
		idPanel.setBorder(new EmptyBorder(0, 0, 0, 50));
		idLabel = new CLabel("School ID");
		idPanel.add(idLabel);

		idTf = new CTextField();
		idTf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				idIfKeyTyped(evt);
				if (idTf.getText().length() >= 7
						&& !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
					registerFrame.getToolkit().beep();
					evt.consume();
				}
			}
		});

		idPanel.add(idTf);

		idValidatorPanel = new JPanel();
		idValidatorPanel.setPreferredSize(new Dimension(305, 20));
		idValidatorPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		idValidatorPanel.setBackground(Colors.LIGHT);

		idDoesntExistLabel = new CLabel();
		idDoesntExistLabel.setFont(new Font(fontString, 2, 12));
		idDoesntExistLabel.setForeground(Color.red);

		idValidatorPanel.add(idDoesntExistLabel);
		idPanel.add(idValidatorPanel);
		w.add(idPanel);

		/*
		 * lnPanel = new JPanel(); lnPanel.setPreferredSize(new Dimension(0, PMIN));
		 * lnPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		 * lnPanel.setBackground(Colors.LIGHT); lastNameLabel = new CLabel("Last Name");
		 * lnPanel.add(lastNameLabel);
		 * 
		 * lastNameTf = new CTextField();
		 * 
		 * lastNameTf.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent
		 * evt) { char c = evt.getKeyChar(); if (!(Character.isAlphabetic(c) || (c ==
		 * KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) evt.consume(); } });
		 * 
		 * 
		 * lnPanel.add(lastNameTf); w.add(lnPanel);
		 */

		// DROPDOWN LIST AREA
		departmentPanel = new JPanel();
		departmentPanel.setPreferredSize(new Dimension(0, PMIN));
		departmentPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		departmentPanel.setBackground(Colors.LIGHT);
		departmentLabel = new CLabel("Department");
		departmentPanel.add(departmentLabel);

		String departmentChoices[] = { "COT", "CEAS", "CME", "COE" };

		deptComboBox = new JComboBox<String>(departmentChoices);
		deptComboBox.setFont(myFont);

		deptComboBox.setPreferredSize(new Dimension(305, 40));
		departmentPanel.add(deptComboBox);
		w.add(departmentPanel);

		passWrdPanel = new JPanel();
		passWrdPanel.setPreferredSize(new Dimension(305, PMIN));
		passWrdPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		passWrdPanel.setBackground(Colors.LIGHT);

		passWrdLabel = new CLabel("Password");
		passWrdPassFld = new CPasswordField();

		passWrdPanel.add(passWrdLabel);
		passWrdPanel.add(passWrdPassFld);

		// PASS VALIDATOR PANEL
		// 0x1E8F19 - Green Color Icon
		// 0xD00704 - Red Color Icon

		passValidatorPanel = new JPanel();
		passValidatorPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		passValidatorPanel.setPreferredSize(new Dimension(305, 20));
		passValidatorPanel.setBackground(Colors.LIGHT);

		passErrPromptLabel = new CLabel();
		passErrPromptLabel.setFont(new Font(fontString, 2, 12));
		passErrPromptLabel.setForeground(Color.red);

		passValidatorPanel.add(passErrPromptLabel);

		passWrdPanel.add(passValidatorPanel);
		w.add(passWrdPanel);

		// EAST PANEL OF THE CONTENTBASEPANEL FROM CPANEL
		e = new JPanel();
		e.setBackground(Colors.LIGHT);
		e.setPreferredSize(new Dimension(420, 0));
		e.setLayout(new BoxLayout(e, BoxLayout.Y_AXIS));
		e.setBorder(new EmptyBorder(0, 20, 0, 90));

		// EMAIL PANEL
		emailPanel = new JPanel();
		emailPanel.setPreferredSize(new Dimension(0, PMIN));
		emailPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		emailPanel.setBackground(Colors.LIGHT);

		emailLabel = new CLabel("CTU Email");
		emailPanel.add(emailLabel);

		emailTf = new CTextField();
		emailTf.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				int pos = emailTf.getCaretPosition();
				emailTf.setText(emailTf.getText().toLowerCase());
				emailTf.setCaretPosition(pos);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		emailPanel.add(emailTf);

		emailValidatorPanel = new JPanel();
		emailValidatorPanel.setPreferredSize(new Dimension(305, 20));
		emailValidatorPanel.setBackground(Colors.LIGHT);
		emailValidatorPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		emailDoesntExistLabel = new CLabel();
		emailDoesntExistLabel.setFont(new Font(fontString, 2, 12));
		emailDoesntExistLabel.setForeground(Color.red);

		emailValidatorPanel.add(emailDoesntExistLabel);
		emailPanel.add(emailValidatorPanel);
		e.add(emailPanel);

		/*
		 * // FIRST NAME PANEL firstNamePanel = new JPanel();
		 * firstNamePanel.setPreferredSize(new Dimension(0, PMIN));
		 * firstNamePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		 * firstNamePanel.setBackground(Colors.LIGHT);
		 * 
		 * firstNameLabel = new CLabel("First Name");
		 * firstNamePanel.add(firstNameLabel);
		 * 
		 * firstNameTf = new CTextField(); firstNamePanel.add(firstNameTf);
		 * 
		 * e.add(firstNamePanel);
		 */

		crsPanel = new JPanel();
		crsPanel.setPreferredSize(new Dimension(0, PMIN));
		crsPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		crsPanel.setBackground(Colors.LIGHT);
		crsLabel = new CLabel("Course");
		crsPanel.add(crsLabel);

		String courseChoices[] = { "BSME", "BSIE", "BSEE", "BSCE", "BSCpE", "BEEd", "BSEd", "BTLEd", "BSIT", "BIT", "BSHM", "BSTM" };

		crsComboBox = new JComboBox<String>(courseChoices);
		crsComboBox.setFont(myFont);

		crsComboBox.setPreferredSize(new Dimension(305, 40));
		crsPanel.add(crsComboBox);
		e.add(crsPanel);

		/*
		 * yrNSecPanel = new JPanel(); yrNSecPanel.setPreferredSize(new Dimension(0,
		 * PMIN)); yrNSecPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		 * yrNSecPanel.setBackground(Colors.LIGHT);
		 * 
		 * yrNSecLabel = new CLabel("Year and Section"); yrNSecPanel.add(yrNSecLabel);
		 * 
		 * yrNSecTf = new CTextField(); yrNSecTf.addKeyListener(new KeyAdapter() {
		 * public void keyTyped(KeyEvent evt) { if (yrNSecTf.getText().length() >= 2 &&
		 * !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() ==
		 * KeyEvent.VK_BACK_SPACE)) { getToolkit().beep(); evt.consume(); } } });
		 * yrNSecPanel.add(yrNSecTf);
		 * 
		 * e.add(yrNSecPanel);
		 */

		confrmPassPanel = new JPanel();
		confrmPassPanel.setPreferredSize(new Dimension(305, PMIN));
		confrmPassPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		confrmPassPanel.setBackground(Colors.LIGHT);

		confrmPassLabel = new CLabel("Confirm Password");
		confrmPassFld = new CPasswordField();

		confrmPassPanel.add(confrmPassLabel);
		confrmPassPanel.add(confrmPassFld);

		// CONFIRM PASS ERR MESSAGE
		confrmPassValidatorPanel = new JPanel();
		confrmPassValidatorPanel.setBackground(Colors.LIGHT);
		confrmPassValidatorPanel.setLayout(new BorderLayout());

		confrmPassErrPromptLabel = new CLabel();
		confrmPassErrPromptLabel.setFont(new Font(fontString, 2, 12));
		confrmPassErrPromptLabel.setForeground(Color.red);

		JPanel confrmEP = new JPanel();
		confrmEP.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		confrmEP.setPreferredSize(new Dimension(155, 25));
		confrmEP.setBackground(Colors.LIGHT);
		confrmEP.add(confrmPassErrPromptLabel);

		JPanel showAllPassP = new JPanel();
		showAllPassP.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 0));
		showAllPassP.setPreferredSize(new Dimension(155, 0));
		showAllPassP.setBackground(Colors.LIGHT);

		showPassIcon = new JCheckBox("Show Password");
		showPassIcon.setIcon(new ImageIcon("image_assets/Rgstr_Imgs/hidePassIcon.png"));
		showPassIcon.setRolloverEnabled(false);
		showPassIcon.setSelectedIcon(new ImageIcon("image_assets/Rgstr_Imgs/showPassIcon.png"));
		showPassIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showPassIcon.setFocusable(false);
		showPassIcon.setBackground(Colors.LIGHT);
		showPassIcon.setFont(new Font(fontString, 0, 12));
		showPassIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showPassIcon.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					passWrdPassFld.setEchoChar((char) 0);
					confrmPassFld.setEchoChar((char) 0);
				} else {
					passWrdPassFld.setEchoChar('*');
					confrmPassFld.setEchoChar('*');
				}
			}
		});

		showAllPassP.add(showPassIcon);

		confrmPassValidatorPanel.add(confrmEP, BorderLayout.WEST);
		confrmPassValidatorPanel.add(showAllPassP, BorderLayout.EAST);

		confrmPassPanel.add(confrmPassValidatorPanel);

		e.add(confrmPassPanel);

		JPanel nSP = new JPanel();
		nSP.setLayout(new FlowLayout(FlowLayout.CENTER));
		nSP.setBackground(Colors.LIGHT);

		regUserTypePanel = new JPanel();
		regUserTypePanel.setLayout(new GridLayout(2, 1));
		regUserTypePanel.setBackground(Colors.LIGHT);

		regUserTypeLabel = new CLabel("Register as");
		regUserTypePanel.add(regUserTypeLabel);

		regAsUserBtn = new JRadioButton("User");
		regAsUserBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		regAsUserBtn.setFont(new Font(fontString, 0, 15));
		regAsUserBtn.setFocusable(false);
		regAsUserBtn.setBackground(Colors.LIGHT);
		regAsUserBtn.setIconTextGap(5);

		regAsAdminBtn = new JRadioButton("Admin");
		regAsAdminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		regAsAdminBtn.setFont(new Font(fontString, 0, 15));
		regAsAdminBtn.setFocusable(false);
		regAsAdminBtn.setBackground(Colors.LIGHT);
		regAsAdminBtn.setIconTextGap(5);
		userAdminBtnGroup = new ButtonGroup();

		userAdminBtnGroup.add(regAsUserBtn);
		userAdminBtnGroup.add(regAsAdminBtn);

		JPanel btnGrpPanel = new JPanel();
		btnGrpPanel.setLayout(new GridLayout(1, 2));
		btnGrpPanel.setBackground(Colors.LIGHT);
		btnGrpPanel.add(regAsUserBtn);
		btnGrpPanel.add(regAsAdminBtn);

		regUserTypePanel.add(btnGrpPanel);

		nSP.add(regUserTypePanel);

		registerBasePanel.s.setPreferredSize(new Dimension(0, 250));
		registerBasePanel.s.setLayout(new BorderLayout());
		registerBasePanel.s.add(nSP, BorderLayout.NORTH);

		registerBtn = new CButton("Register");
		registerBtn.addActionListener(this);

		JPanel sC = new JPanel();
		sC.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		sC.setBackground(Colors.LIGHT);
		sC.setBorder(new EmptyBorder(25, 0, 0, 0));
		sC.add(registerBtn);
		/*
		 * registerBasePanel.s.add(sW, BorderLayout.WEST); registerBasePanel.s.add(sE,
		 * BorderLayout.EAST);
		 */
		registerBasePanel.s.add(sC, BorderLayout.CENTER);

		registerBasePanel.contentBasePanel.add(w, BorderLayout.WEST);
		registerBasePanel.contentBasePanel.add(e, BorderLayout.EAST);
		registerFrame.setVisible(true);

	}

	// method to only allow digits in the school id input
	private void idIfKeyTyped(KeyEvent evt) {
		if (!Character.isDigit(evt.getKeyChar())) {
			evt.consume();
		}
	}

	String schoolId, email, department, course, yearNSec, password, confirmPass;
	boolean isAdmin;;

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == registerBtn) {
			// ENTER REGISTRATION DETAILS TO DATABASE HERE
			schoolId = idTf.getText();
			email = emailTf.getText();
			/*
			 * lastName = lastNameTf.getText(); firstName = firstNameTf.getText();
			 */
			course = crsComboBox.getSelectedItem().toString();
			department = deptComboBox.getSelectedItem().toString();
			password = String.valueOf(passWrdPassFld.getPassword());
			confirmPass = String.valueOf(confrmPassFld.getPassword());
			if (regAsAdminBtn.isSelected()) {
				isAdmin = true;
			} else if (regAsUserBtn.isSelected()) {
				isAdmin = false;
			}

			Date date = new Date();
			Timestamp dateCreated = new Timestamp(date.getTime());

			// VERIFICATION

			if (schoolId.trim().isBlank() || email.isBlank() || password.isBlank() || confirmPass.isBlank()
					|| !regAsAdminBtn.isSelected() && !regAsUserBtn.isSelected()) {
				JOptionPane.showMessageDialog(registerFrame, "Some fields are empty", "Error",
						JOptionPane.ERROR_MESSAGE);
				passErrPromptLabel.setText("");
				emailDoesntExistLabel.setText("");

			} else if (isValidEmail(email) == false) {
				emailDoesntExistLabel.setText("Invalid CTU Email");
				passErrPromptLabel.setText("");
			} else if (password.length() < 4 || confirmPass.length() < 4) {
				passErrPromptLabel.setText("Password should be atleast 4 characters long");
			} else if (password == null ? confirmPass != null : !password.equals(confirmPass)) {
				passErrPromptLabel.setText("");
				passErrPromptLabel.setText("Passwords do not match");
			} else if (!checkEmailExists(email) && !checkSchoolIDExists(schoolId)) {
				passErrPromptLabel.setText("");
				emailDoesntExistLabel.setText("");
				userRegisterToDatabase(schoolId, email, department, course, password, isAdmin, dateCreated);
			}

		}
	}

	public boolean isValidEmail(String email) {

		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@ctu.edu.ph";
		;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public boolean checkEmailExists(String email) {
		boolean emailExists = false;

		if (dbconn != null) {
			try {
				PreparedStatement st = (PreparedStatement) dbconn
						.prepareStatement("SELECT * FROM userstable WHERE ctuEmail = ?");

				st.setString(1, email);

				ResultSet res = st.executeQuery();
				if (res.next()) {
					emailExists = true;
					JOptionPane.showMessageDialog(null, "This Email is already taken", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException ex) {
				Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return emailExists;
	}

	public boolean checkSchoolIDExists(String schoolId) {
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

	public void userRegisterToDatabase(String schoolId, String email, String department, String course, String password,
			boolean userType, Timestamp dateCreated) {

		String schoolYear = "2022-2023";
		boolean isCandidateDefault = false;
		if (dbconn != null) {
			try {

				PreparedStatement prepStatement = (PreparedStatement) dbconn
						.prepareStatement("INSERT INTO userstable " + "(schoolId, ctuEmail, department, course, password,  "
								+ "isAdmin, accCreated, schoolYear, isCandidate, hasVoted, partialRegistered)VALUES(?,?,?,?,?,?,?,?,?,?,?)");

				prepStatement.setString(1, schoolId);
				prepStatement.setString(2, email);
				prepStatement.setString(3, department);
				prepStatement.setString(4, course);
				prepStatement.setString(5, password);
				prepStatement.setBoolean(6, userType);
				prepStatement.setTimestamp(7, dateCreated);
				prepStatement.setString(8, schoolYear);
				prepStatement.setBoolean(9, isCandidateDefault);
				prepStatement.setBoolean(10, false);
				prepStatement.setBoolean(11, false);
				
				
				

				if (userType == true) {
					
					String keyEntered = JOptionPane.showInputDialog(null, "Please Enter an Admin Key");
					if (keyEntered.equals(Main.ADMIN_KEY)) {
						JOptionPane.showMessageDialog(null, "Admin Key Confirmed", "Admin Key Confirmation Message",
								JOptionPane.INFORMATION_MESSAGE);

						prepStatement.executeUpdate();
						adminRegisterToDatabase(schoolId);

						


						JOptionPane.showMessageDialog(registerFrame, "Admin Account Registered", "Success",
								JOptionPane.INFORMATION_MESSAGE);

						idTf.setText("");
						emailTf.setText("");
						passWrdPassFld.setText("");
						confrmPassFld.setText("");
					} else {
						JOptionPane.showMessageDialog(registerFrame, "Incorrect Admin Key",
								"Admin Key Confirmation Message", JOptionPane.ERROR_MESSAGE);

					}
				} else if (userType == false) {

					prepStatement.executeUpdate();
					JOptionPane.showMessageDialog(registerFrame, "User Account Registered", "Success",
							JOptionPane.INFORMATION_MESSAGE);
					
					
					int max = 500;
					int min = 1;
					int random = (int) (Math.random() * (max - min + 1) + min);
					String randomNum = String.valueOf(random);
					
					
					String tableName = "user_"+schoolId+"_"+randomNum+"_table";
					
					
					createUserTable(dbconn, tableName, schoolId);
					
					idTf.setText("");
					emailTf.setText("");
					passWrdPassFld.setText("");
					confrmPassFld.setText("");
				}
			} catch (SQLException ex) {
				Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			System.out.println("no connection...");
		}

	}

	void adminRegisterToDatabase(String schoolId) {
		if (dbconn != null) {
			try {

				Statement stmnt = dbconn.createStatement();
				String updateUserAccessStatusSQLCommand = "UPDATE userstable SET isAdmin = true WHERE schoolId = '"
						+ schoolId + "'";

				stmnt.executeUpdate(updateUserAccessStatusSQLCommand);

				PreparedStatement prepStmnt = (PreparedStatement) dbconn
						.prepareStatement("INSERT INTO admins( users_schoolId)" + "VALUES (?)");

				prepStmnt.setString(1, schoolId);
				prepStmnt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String toString(char arr[]) {
		String string = new String(arr);

		return string;
	}
	
	public static void createUserTable(Connection conn, String tableName, String id) throws SQLException {
		if (tableExist(conn, tableName)) {
		} else {
			if (conn != null) {
				try {
					String sql = "CREATE table "+tableName+"(\r\n"
							+ "	img LONGBLOB,\r\n"
							+ "    candLastName VARCHAR(45),\r\n"
							+ "    candFirstName VARCHAR(45),\r\n"
							+ "    course VARCHAR(15),\r\n"
							+ "    yearNSection VARCHAR(10),\r\n"
							+ "    runningFor VARCHAR(45),\r\n"
							+ "    partyList VARCHAR(45)\r\n"
							+ ")";
					Statement st = conn.createStatement();
					
					st.executeUpdate(sql);
					st.executeUpdate("UPDATE userstable SET tableName = '"+tableName+"' WHERE schoolId = '"+id+"'");
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		
	}

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

}
