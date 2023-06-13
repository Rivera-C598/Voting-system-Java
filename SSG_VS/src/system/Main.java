package system;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	private final static String USERS = "userstable";
	private final static String CANDIDATES = "candidates";
	private final static String ADMINS = "admins";
	private final static String PARTYLISTS = "partylists";
	private final static String ADMIN_LOGS_1 = "adminlogs1";
	
	private final static String ADMIN_LOGS_2 = "adminlogs2";
	private final static String PARTIALS = "partials";
	private final static String PARTIAL_VOTED_CANDIDATES = "partialvotedcandidates";
	private final static String USER_VOTED_CANDIDATES_TABLE = "uservoted";
	
	final protected static String ADMIN_KEY = "root";

	public static void main(String[] args) throws SQLException {
		// get Database Connection
		Connection conn = DBConnection.connectDB();
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}

		initializeDatabase(conn, USERS, CANDIDATES, ADMINS, PARTYLISTS, ADMIN_LOGS_1, ADMIN_LOGS_2, PARTIALS, PARTIAL_VOTED_CANDIDATES, USER_VOTED_CANDIDATES_TABLE);
		launchApp();

	}

	public static void initializeDatabase(Connection conn, String users, String candidates, String admins, String partylists, String adminLogs1, String adminLogs2, String partials, String partialVotedCandidates, String userVotedCandidatesTable) throws SQLException {
		if (tableExist(conn, users)) {
		} else {
			if (conn != null) {
				try {
					String sql = "CREATE TABLE "+users+" (\r\n"
							+ "  `schoolId` varchar(10) NOT NULL,\r\n"
							+ "  `ctuEmail` varchar(255) DEFAULT NULL,\r\n"
							+ "  `lastName` varchar(45) DEFAULT NULL,\r\n"
							+ "  `firstName` varchar(45) DEFAULT NULL,\r\n"
							+ "  `department` varchar(45) DEFAULT NULL,\r\n"
							+ "  `course` varchar(45) DEFAULT NULL,\r\n"
							+ "  `password` varchar(45) DEFAULT NULL,\r\n"
							+ "  `accCreated` timestamp NULL DEFAULT NULL,\r\n"
							+ "  `isAdmin` tinyint DEFAULT NULL,\r\n"
							+ "  `voteEnabled` tinyint DEFAULT NULL,\r\n"
							+ "  `hasVoted` tinyint DEFAULT NULL,\r\n"
							+ "  `schoolYear` varchar(45) DEFAULT NULL,\r\n"
							+ "  `isCandidate` tinyint DEFAULT NULL,\r\n"
							+ "  `yearNSection` varchar(10) DEFAULT NULL,\r\n"
							+ "  `partialRegistered` tinyint DEFAULT NULL,\r\n"
							+ "  `transactionId` varchar(255) DEFAULT NULL,\r\n"
							+ "  `tableName` varchar(69) DEFAULT NULL,\r\n"
							+ "  PRIMARY KEY (`schoolId`)\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		if (tableExist(conn, candidates)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+candidates+" (\r\n"
							+ "  `candidateId` IDENTITY NOT NULL,\r\n"
							+ "  `users_schoolId` varchar(10) NOT NULL,\r\n"
							+ "  `runningFor` varchar(45) DEFAULT NULL,\r\n"
							+ "  `partylist` varchar(45) DEFAULT NULL,\r\n"
							+ "  `votes` int DEFAULT NULL,\r\n"
							+ "  `img` longblob,\r\n"
							+ "  PRIMARY KEY (`candidateId`)\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, admins)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+admins+" (\r\n"
							+ "  `adminId` IDENTITY NOT NULL,\r\n"
							+ "  `users_schoolId` varchar(10) NOT NULL,\r\n"
							+ "  PRIMARY KEY (`adminId`)\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, partylists)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+partylists+" (\r\n"
							+ "  `partyListName` varchar(45) DEFAULT NULL\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, adminLogs1)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+adminLogs1+" (\r\n"
							+ "  `logId` IDENTITY NOT NULL,\r\n"
							+ "  `adminId` int DEFAULT NULL,\r\n"
							+ "  `sessionStartedTime` varchar(30) DEFAULT NULL,\r\n"
							+ "  PRIMARY KEY (`logId`)\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, adminLogs2)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+adminLogs2+" (\r\n"
							+ "  `adminId` int DEFAULT NULL,\r\n"
							+ "  `sessionStartedTime` varchar(30) DEFAULT NULL,\r\n"
							+ "  `sessionEndedTime` varchar(45) DEFAULT NULL\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, partials)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+partials+" (\r\n"
							+ "  `candidateId` int DEFAULT NULL,\r\n"
							+ "  `img` longblob,\r\n"
							+ "  `lastName` varchar(45) DEFAULT NULL,\r\n"
							+ "  `firstName` varchar(45) DEFAULT NULL,\r\n"
							+ "  `course` varchar(45) DEFAULT NULL,\r\n"
							+ "  `yearNSection` varchar(10) DEFAULT NULL,\r\n"
							+ "  `runningFor` varchar(45) DEFAULT NULL,\r\n"
							+ "  `partylist` varchar(45) DEFAULT NULL,\r\n"
							+ "  `schoolId` varchar(10) DEFAULT NULL\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, partialVotedCandidates)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+partialVotedCandidates+" (\r\n"
							+ "  `users_schoolId` varchar(10) DEFAULT NULL,\r\n"
							+ "  `partialVotedPres` int DEFAULT NULL,\r\n"
							+ "  `partialVotedVicePres` int DEFAULT NULL,\r\n"
							+ "  `partialVotedSecretary` int DEFAULT NULL,\r\n"
							+ "  `partialVotedAuditor` int DEFAULT NULL,\r\n"
							+ "  `partialVotedTreasurer` int DEFAULT NULL\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
					st.close();

				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		if (tableExist(conn, userVotedCandidatesTable)) {
		} else {
			if (conn != null) {
				try {

					String sql = "CREATE TABLE "+userVotedCandidatesTable+" (\r\n"
							+ "  `transactionId` varchar(255) DEFAULT NULL,\r\n"
							+ "  `dateVoted` timestamp NULL DEFAULT NULL,\r\n"
							+ "  `schoolId` int DEFAULT NULL,\r\n"
							+ "  `votedPres` int DEFAULT NULL,\r\n"
							+ "  `votedVice` int DEFAULT NULL,\r\n"
							+ "  `votedSec` int DEFAULT NULL,\r\n"
							+ "  `votedAud` int DEFAULT NULL,\r\n"
							+ "  `votedTreasu` int DEFAULT NULL\r\n"
							+ ")";
					Statement st = conn.createStatement();
					st.executeUpdate(sql);
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

	private static void launchApp() {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            	new Login();
            }
        });
	}

}
