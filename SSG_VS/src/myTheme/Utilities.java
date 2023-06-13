package myTheme;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import system.AdminDashboard;
import system.DBConnection;
import system.Login;

public class Utilities {
	// for Classes that extends the JFrame class only
	public static JPanel basePanel;
	CLabel minimize, close;

	Connection dbconn = DBConnection.connectDB();

	public JPanel darkUtilityPanel(JFrame frame) {
		basePanel = new JPanel();
		basePanel.setBackground(Colors.LIGHT);
		basePanel.setPreferredSize(new Dimension(70, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		minimize = new CLabel();
		minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minD.png")));
		minimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minimize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
				minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minGray.png")));
			}

			public void mouseExited(MouseEvent e) {
				minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minD.png")));
			}
		});

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);

			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
			}

		});

		basePanel.add(minimize);
		basePanel.add(close);

		return basePanel;
	}

	public JPanel darkUtilityPanelForUserDashboard(JFrame frame) {
		basePanel = new JPanel();
		basePanel.setBackground(Colors.LIGHT);
		basePanel.setPreferredSize(new Dimension(70, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		minimize = new CLabel();
		minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minD.png")));
		minimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minimize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
				minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minGray.png")));
			}

			public void mouseExited(MouseEvent e) {
				minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minD.png")));
			}
		});

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				System.exit(0);

			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
			}

		});

		basePanel.add(minimize);
		basePanel.add(close);

		return basePanel;
	}
	
	public JPanel forPrompts(JFrame frame_to_be_disposed, JFrame frame_to_be_enabled) {
		basePanel = new JPanel();
		basePanel.setBackground(Colors.LIGHT);
		basePanel.setPreferredSize(new Dimension(35, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				SigMethods.disableGlassPane(frame_to_be_enabled);
				frame_to_be_enabled.setEnabled(true);
				frame_to_be_disposed.dispose();
			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
			}

		});

		basePanel.add(close);

		return basePanel;
	}

	public JPanel lightUtilityPanel(JFrame frame) {
		basePanel = new JPanel();
		basePanel.setBackground(Colors.DARK);
		basePanel.setPreferredSize(new Dimension(70, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		minimize = new CLabel();
		minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minL.png")));
		minimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minimize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
				minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minGray.png")));
			}

			public void mouseExited(MouseEvent e) {
				minimize.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/minL.png")));
			}
		});

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.dispose();

			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
			}

		});

		basePanel.add(minimize);
		basePanel.add(close);

		return basePanel;
	}

	public JPanel darkUtilityCloseOnlyPanel(JFrame frame_to_be_disposed, JFrame frame_to_be_enabled) {
		basePanel = new JPanel();
		basePanel.setBackground(Colors.LIGHT);
		basePanel.setPreferredSize(new Dimension(35, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame_to_be_enabled.setEnabled(true);
				frame_to_be_disposed.dispose();
			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeD.png")));
			}

		});

		basePanel.add(close);

		return basePanel;
	}

	public JPanel forStartSessionUtilityPanel(JFrame frame_to_be_disposed, JFrame frame_to_be_enabled,
			JToggleButton toggleBtn) {

		basePanel = new JPanel();
		basePanel.setBackground(Colors.DARK);
		basePanel.setPreferredSize(new Dimension(35, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				SigMethods.disableGlassPane(frame_to_be_enabled);
				AdminDashboard.adminDashboardFrame.dispose();
				new AdminDashboard();
				toggleBtn.setVisible(true);
				toggleBtn.setSelected(false);

				frame_to_be_disposed.dispose();

			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
			}

		});

		basePanel.add(close);

		return basePanel;
	}

	public JPanel forConfirmSelectedCandidatesUtilityPanel(JFrame frame_to_be_disposed, JFrame frame_to_be_enabled) {

		basePanel = new JPanel();
		basePanel.setBackground(Colors.DARK);
		basePanel.setPreferredSize(new Dimension(35, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				SigMethods.disableGlassPane(frame_to_be_enabled);

				frame_to_be_disposed.dispose();

				if (dbconn != null) {
					try {
						String refreshPartialVotedCandidatesData = "DELETE FROM partials WHERE schoolId = '"
								+ Login.userSchoolId + "'";
						Statement st = dbconn.createStatement();
						st.executeUpdate(refreshPartialVotedCandidatesData);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}

			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
			}

		});

		basePanel.add(close);

		return basePanel;
	}

	public JPanel lightUtilityCloseOnlyPanel(JFrame frame_to_be_disposed, JFrame frame_to_be_enabled) {

		SigMethods.enableGlassPane(frame_to_be_enabled);
		basePanel = new JPanel();
		basePanel.setBackground(Colors.DARK);
		basePanel.setPreferredSize(new Dimension(35, 35));
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		close = new CLabel();
		close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				SigMethods.disableGlassPane(frame_to_be_enabled);
				frame_to_be_disposed.dispose();
			}

			public void mouseEntered(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeRed.png")));
			}

			public void mouseExited(MouseEvent e) {
				close.setIcon(new ImageIcon(getClass().getResource("/image_assets/utility_Imgs/closeL.png")));
			}

		});

		basePanel.add(close);

		return basePanel;
	}

}
