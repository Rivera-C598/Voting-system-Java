package system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import myTheme.CButton;
import myTheme.CLabel;
import myTheme.CPasswordField;
import myTheme.CTextField;
import myTheme.Colors;
import myTheme.CustomFont;
import myTheme.SigMethods;
import myTheme.Utilities;
import tableModels.TFLimit;

public class Login implements ActionListener {

	static JFrame loginFrame;

	Font forTitleFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(forTitleFont);
	Font modifiedTitleFont = new CustomFont().setFontModifications(forTitleFont, 50);

	Connection dbconn = DBConnection.connectDB();

	ImageIcon JFrameLogo;

	private JPanel leftPanel, lTopPanel, lLeftMargin, lRightMargin, lCenterPanel, rightPanel, rTopPanel, rLeftMargin,
			rRightMargin, rCenterPanel, rBottomPanel;
	private CLabel title1, title2, title3, ssgLogoLabel, loginTitleLabel, idLabel, passLabel, idTextLabel;

	private CTextField idTf;
	private CPasswordField passInput;
	private CButton loginButton;

	public Login() {

		/*
		 * custom package cheat sheets:
		 * 
		 * CPanel [BorderLayout] : inherits the JPanel class, intended for adding
		 * JPanels with uniform properties. tips: contentBasePanel.add() - add
		 * components here components here methods: 1.
		 * setCustomPanelTitle("title String"); - Panel title- 2.
		 * customFunc(accBasePanel, hcpBasePanel, leftNavPanel); - makes the back to
		 * home button work
		 * 
		 * CLabels: inherits the JLabel Class, intended for removing the tedious process
		 * of setting JLabel fonts one by one. Current fonts in use:
		 * 
		 * 
		 * 
		 * Color Palette (hex): 1. EABE7C - orange 2. 311E10 - dark 3. ECE4B7 - light
		 */

		// -38 height

		initComponents();

	}

	void initComponents() {
		// JFrame
		loginFrame = new JFrame();

		SigMethods.draggable(loginFrame);
		ImageIcon JFrameLogo = new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/SSGLogo.png"));
		loginFrame.setIconImage(JFrameLogo.getImage());
		loginFrame.setSize(1100, 750);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setUndecorated(true);
		loginFrame.setLayout(new BorderLayout());

		Colors.setTheme("Default");
		// ---------- LEFT PANEL CONTENTS -----------
		// LEFT JPANEL ITSELF
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(500, 750));
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(new Color(0x153243));

		// LOGO PLACEMENT
		lTopPanel = new JPanel();
		lTopPanel.setPreferredSize(new Dimension(0, 334));
		lTopPanel.setBorder(new EmptyBorder(100, 0, 0, 0));
		lTopPanel.setBackground(Colors.DARK);
		ssgLogoLabel = new CLabel();
		ssgLogoLabel.setIcon(new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/SSGLogo.png")));
		leftPanel.add(lTopPanel, BorderLayout.NORTH);

		lTopPanel.add(ssgLogoLabel);

		// MARGINS
		// LEFT
		lLeftMargin = new JPanel();
		lLeftMargin.setPreferredSize(new Dimension(30, 0));
		lLeftMargin.setBackground(Colors.DARK);
		leftPanel.add(lLeftMargin, BorderLayout.WEST);
		// RIGHT
		lRightMargin = new JPanel();
		lRightMargin.setPreferredSize(new Dimension(30, 0));
		lRightMargin.setBackground(Colors.DARK);
		leftPanel.add(lRightMargin, BorderLayout.EAST);

		// CENTER PANEL
		lCenterPanel = new JPanel();
		lCenterPanel.setBackground(Colors.DARK);
		lCenterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		title1 = new CLabel("Supreme Student ");
		title2 = new CLabel("Government ");
		title3 = new CLabel("Voting System");

		title1.setForeground(Colors.LIGHT);
		title1.setNewFont(modifiedTitleFont);
		lCenterPanel.add(title1);

		title2.setForeground(Colors.LIGHT);
		title2.setNewFont(modifiedTitleFont);
		lCenterPanel.add(title2);

		title3.setForeground(Colors.LIGHT);
		title3.setNewFont(modifiedTitleFont);
		lCenterPanel.add(title3);
		title1.fixJLabelBug(title1);
		title1.fixJLabelBug(title2);
		title1.fixJLabelBug(title3);
		leftPanel.add(lCenterPanel, BorderLayout.CENTER);

		// ---------- RIGHT PANEL CONTENTS -----------
		// RIGHT PANEL ITSELF
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(600, 750));
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(Colors.LIGHT);

		// FOR LOGIN TITLE
		loginTitleLabel = new CLabel("Welcome");
		loginTitleLabel.setForeground(Colors.DARK);
		loginTitleLabel.setFontSize(40);

		rTopPanel = new JPanel();
		rTopPanel.setBackground(Colors.ORANGE);
		rTopPanel.setLayout(new BorderLayout());

		JPanel rTopNorth = new JPanel();
		rTopNorth.setPreferredSize(new Dimension(0, 120));
		rTopNorth.setBackground(Colors.LIGHT);
		rTopNorth.setLayout(new BorderLayout());

		JPanel rTopRight = new JPanel();
		rTopRight.setPreferredSize(new Dimension(70, 0));
		rTopRight.setLayout(new FlowLayout());
		rTopRight.setBackground(Colors.LIGHT);

		rTopRight.add(new Utilities().darkUtilityPanel(loginFrame));

		JPanel rTopLeft = new JPanel();
		rTopLeft.setPreferredSize(new Dimension(70, 0));
		rTopLeft.setBackground(Colors.LIGHT);

		rTopNorth.add(rTopRight, BorderLayout.EAST);
		rTopNorth.add(rTopLeft, BorderLayout.WEST);
		rTopPanel.add(rTopNorth, BorderLayout.NORTH);

		JPanel rTopCenter = new JPanel();
		rTopCenter.setBackground(Colors.LIGHT);
		rTopCenter.add(loginTitleLabel);

		rTopPanel.add(rTopCenter, BorderLayout.CENTER);

		rightPanel.add(rTopPanel, BorderLayout.NORTH);

		// MARGINS
		// LEFT
		rLeftMargin = new JPanel();
		rLeftMargin.setPreferredSize(new Dimension(70, 0));
		rLeftMargin.setBackground(Colors.LIGHT);
		rightPanel.add(rLeftMargin, BorderLayout.WEST);
		// RIGHT
		rRightMargin = new JPanel();
		rRightMargin.setPreferredSize(new Dimension(70, 0));
		rRightMargin.setBackground(Colors.LIGHT);
		rightPanel.add(rRightMargin, BorderLayout.EAST);

		// CENTER PANEL
		rCenterPanel = new JPanel();
		BoxLayout box = new BoxLayout(rCenterPanel, BoxLayout.PAGE_AXIS);
		rCenterPanel.setLayout(box);

		rCenterPanel.setBackground(Colors.LIGHT);
		rCenterPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

		// ID USERNAME AREA
		idTextLabel = new CLabel("School Id");
		idTextLabel.setForeground(Colors.DARK);
		idTextLabel.setFontSize(16);
		idTextLabel.setBackground(Colors.LIGHT);
		idTextLabel.setOpaque(true);

		JPanel p1 = new JPanel();
		p1.setPreferredSize(new Dimension(0, 22));
		p1.setMaximumSize(new Dimension(Integer.MAX_VALUE, p1.getMinimumSize().height));
		p1.setBackground(Colors.LIGHT);
		p1.setLayout(new FlowLayout(FlowLayout.LEADING, 115, 0));
		p1.add(idTextLabel);
		rCenterPanel.add(p1);

		JPanel p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 68));
		p2.setMaximumSize(new Dimension(Integer.MAX_VALUE, p2.getMinimumSize().height));
		p2.setBackground(Colors.LIGHT);
		p2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		idLabel = new CLabel();
		idLabel.setIcon(new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/icons8_name_68px.png")));

		idTf = new CTextField();
		idTf.setPreferredSize(new Dimension(305, 40));
		idTf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				idIfKeyTyped(evt);

			}
		});
		idTf.setDocument(new TFLimit(7));

		p2.add(idLabel);
		p2.add(idTf);
		rCenterPanel.add(p2);
		//

		// PASSWORD AREA
		JPanel p2_5 = new JPanel();
		p2_5.setPreferredSize(new Dimension(0, 22));
		p2_5.setMaximumSize(new Dimension(Integer.MAX_VALUE, p2_5.getMinimumSize().height));
		p2_5.setBackground(Colors.LIGHT);
		p2_5.setLayout(new FlowLayout(FlowLayout.LEADING, 115, 0));

		CLabel passTextLabel = new CLabel("Password");
		passTextLabel.setForeground(Colors.DARK);
		passTextLabel.setFontSize(16);
		p2_5.add(passTextLabel);

		CLabel forgotPassLabel = new CLabel("Forgot Password?");
		forgotPassLabel.setForeground(Colors.ORANGE);
		forgotPassLabel.setFontSize(14);
		forgotPassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		forgotPassLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				forgotPassLabel.setForeground(Colors.DARK);
			}

			public void mouseExited(MouseEvent evt) {
				forgotPassLabel.setForeground(Colors.ORANGE);
			}

			public void mouseClicked(MouseEvent evt) {

				EventQueue.invokeLater(() -> {
					new ForgotPassPage();
				});

			}
		});

		rCenterPanel.add(p2_5);

		JPanel p3 = new JPanel();

		p3.setPreferredSize(new Dimension(0, 58));
		p3.setMaximumSize(new Dimension(Integer.MAX_VALUE, p3.getMinimumSize().height));
		p3.setBackground(Colors.LIGHT);
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		passLabel = new CLabel();
		passLabel.setIcon(new ImageIcon(getClass().getResource("/image_assets/Lgn_Imgs/icons8_password_68px.png")));

		passInput = new CPasswordField();
		passInput.setPreferredSize(new Dimension(305, 40));
		p3.add(passLabel);
		p3.add(passInput);
		rCenterPanel.add(p3);

		JPanel p3_5 = new JPanel();
		p3_5.setPreferredSize(new Dimension(0, 108));
		p3_5.setMaximumSize(new Dimension(Integer.MAX_VALUE, p3_5.getMinimumSize().height));
		p3_5.setLayout(new BorderLayout());
		p3_5.setBorder(new EmptyBorder(0, 110, 0, 35));
		// p3_5.setLayout(new FlowLayout(FlowLayout.LEADING, 110, 0));
		p3_5.setBackground(Colors.LIGHT);

		JCheckBox showPass = new JCheckBox("show password");
		showPass.setBackground(Colors.LIGHT);
		showPass.setFont(new Font(fontString, 0, 14));
		showPass.setFocusable(false);
		showPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showPass.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					passInput.setEchoChar((char) 0);
				} else {
					passInput.setEchoChar('*');
				}
			}
		});

		p3_5.add(showPass, BorderLayout.WEST);
		p3_5.add(forgotPassLabel, BorderLayout.EAST);
		rCenterPanel.add(p3_5);
		//
		// LOGIN BUTTON AREA
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		p4.setBackground(Colors.LIGHT);
		p4.setBorder(new EmptyBorder(60, 0, 0, 0));
		p4.setMaximumSize(new Dimension(Integer.MAX_VALUE, p4.getMinimumSize().height));

		// p4.setBackground(Colors.LIGHT);

		loginButton = new CButton("Log in");
		loginButton.addActionListener(this);
		loginButton.setPreferredSize(new Dimension(358, 60));

		p4.add(loginButton);

		rCenterPanel.add(p4);

		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		p4.setPreferredSize(new Dimension(0, 400));
		p4.setMaximumSize(new Dimension(Integer.MAX_VALUE, p4.getMinimumSize().height));
		p5.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.DARK));
		p5.setBackground(Colors.LIGHT);

		CLabel registerPrompt = new CLabel("Don't have an account? ");
		registerPrompt.setForeground(Colors.DARK);
		registerPrompt.setFontSize(18);

		CLabel registerButton = new CLabel("Register here!");
		registerButton.setFont(new Font(fontString, 2, 15));
		registerButton.setForeground(Colors.ORANGE);
		registerButton.setFontSize(18);

		registerButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				registerButton.setForeground(Colors.DARK);
			}

			public void mouseExited(MouseEvent evt) {
				registerButton.setForeground(Colors.ORANGE);
			}

			public void mouseClicked(MouseEvent evt) {
				loginFrame.dispose();
				new Register();
			}
		});
		p5.add(registerPrompt);
		p5.add(registerButton);
		rCenterPanel.add(p5);

		//
		rBottomPanel = new JPanel();
		rBottomPanel.setPreferredSize(new Dimension(0, 90));
		rBottomPanel.setBackground(Colors.LIGHT);

		rightPanel.add(rCenterPanel, BorderLayout.CENTER);
		rightPanel.add(rBottomPanel, BorderLayout.SOUTH);

		loginFrame.add(leftPanel, BorderLayout.WEST);
		loginFrame.add(rightPanel, BorderLayout.EAST);
		loginFrame.setVisible(true);
	}

	String idEmail, password;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			// DATABASE
			// VERIFICATION
			// GETTING/GATHERING INPUTS

			idEmail = idTf.getText();
			password = String.valueOf(passInput.getPassword());

			if (idEmail.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(loginFrame, "Fields should not be empty", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (verifyUser(idEmail)) {
				if (verifyPass(password)) {

					userLogin(idEmail, password);

					loginFrame.dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "School ID not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public boolean verifyUser(String idEmail) {

		boolean userMatch = false;

		if (idEmail.matches(".*[a-z].*")) {
			
			if (dbconn != null) {
				try {
					PreparedStatement st = (PreparedStatement) dbconn
							.prepareStatement("SELECT * FROM userstable WHERE ctuEmail = ?");

					st.setString(1, idEmail);

					ResultSet res = st.executeQuery();

					if (res.next()) {
						// if there is a match
						userMatch = true;
					}

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} else {
			
			if (dbconn != null) {
				try {
					PreparedStatement st = (PreparedStatement) dbconn
							.prepareStatement("SELECT * FROM userstable WHERE schoolId = ?");

					st.setString(1, idEmail);

					ResultSet res = st.executeQuery();

					if (res.next()) {
						// if there is a match
						userMatch = true;
					}

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		return userMatch;
	}

	public boolean verifyPass(String password) {
		boolean passMatch = false;

		if (dbconn != null) {
			try {

				PreparedStatement st = (PreparedStatement) dbconn
						.prepareStatement("SELECT * FROM userstable WHERE password = ?");

				st.setString(1, password);

				ResultSet res = st.executeQuery();

				if (res.next()) {
					// if there is a match
					passMatch = true;
				}
			} catch (SQLException ex) {
				Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return passMatch;
	}

	// VARIABLES FOR STORING LOGGED IN USER INFORMATION

	public static int userAdminId = 0;
	public static String userSchoolId, userEmail, userCourse, userPassword, userTheme, userFirstName, userLastName;
	public static boolean userType;

	private void userLogin(String schoolId, String password) {
		if (dbconn != null) {
			try {

				PreparedStatement st = (PreparedStatement) dbconn
						.prepareStatement("SELECT * FROM userstable WHERE schoolId = ? AND password = ?");

				st.setString(1, schoolId);
				st.setString(2, password);

				ResultSet res = st.executeQuery();

				if (res.next()) {

					// METHOD TO GET LOGGED IN USER INFO TO BE USED BY THE ENTIRE SYSTEM
					extractUserInfo(res);

					// CHECKING USER STATUS
					boolean userType = res.getBoolean("isAdmin");

					if (userType == true) {

						// CHECK CHOSEN USER THEME

						// GET CURRENT ADMINID
						userAdminId = extractAdminId();

						EventQueue.invokeLater(() -> {
							new AdminDashboard();
						});

					} else {

						EventQueue.invokeLater(() -> {
							new UserDashboard();
						});

					}

				} else {
					JOptionPane.showMessageDialog(null, "Username / password not found", "Error",
							JOptionPane.ERROR_MESSAGE);
					EventQueue.invokeLater(() -> {
						new Login();
					});

				}
			} catch (SQLException ex) {
				Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			System.out.println("The connection not available.");
		}
	}

	private void extractUserInfo(ResultSet result) throws SQLException {
		userSchoolId = result.getString("schoolId");
		userEmail = result.getString("ctuEmail");
		userCourse = result.getString("course");
		userPassword = result.getString("password");
		userType = result.getBoolean("isAdmin");
		userFirstName = result.getString("firstName");
		userLastName = result.getString("lastName");
	}

	private int extractAdminId() {
		int adminId = 0;
		if (dbconn != null) {
			try {
				Statement st = dbconn.createStatement();
				String getAdminIdCommand = "SELECT adminId from admins WHERE users_schoolId = '" + userSchoolId + "'";
				ResultSet res = st.executeQuery(getAdminIdCommand);
				if (res.next()) {
					adminId = res.getInt("adminId");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return adminId;
	}

	// method to only allow digits in the school id input
	private void idIfKeyTyped(KeyEvent evt) {
		if (!Character.isDigit(evt.getKeyChar())) {
			evt.consume();
		}
	}

}
