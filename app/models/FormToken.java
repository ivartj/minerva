package models;

import java.sql.*;
import java.util.UUID;
import play.db.DB;
import controllers.Authenticator;

public class FormToken {

	// Returns empty string on error or if not logged in
	public static String get(String form) {
		Connection conn;
		PreparedStatement stmt;
		User user;
		String cookieIdentifier;
		String token;

		user = Authenticator.getCurrentUser();
		if(user == null)
			return "";
		try {
			token = getIfAlreadyExists(user.id, form);
			if(token != null)
				return token;
			token = UUID.randomUUID().toString();
			conn = DB.getConnection();
			stmt = conn.prepareStatement("insert into form_token (user, form, token) values (?, ?, ?)");
			stmt.setLong(1, user.id);
			stmt.setString(2, form);
			stmt.setString(3, token);
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// Just fail miserably
			e.printStackTrace();
			return "";
		}

		return token;
	}

	private static String getIfAlreadyExists(Long uid, String form) throws SQLException {
		Connection conn;
		PreparedStatement stmt;
		ResultSet result;
		String token;

		conn = DB.getConnection();
		stmt = conn.prepareStatement("select token from form_token where user = ? and form = ?");
		stmt.setLong(1, uid);
		stmt.setString(2, form);
		result = stmt.executeQuery();
		if(result.next() == false)
			return null;
		token = result.getString(1);
		conn.close();
		return token;
	}

	public static boolean check(String form, String token) {
		User user;
		String realToken;

		user = Authenticator.getCurrentUser();
		if(user == null)
			return false;
		try {
			realToken = getIfAlreadyExists(user.id, form);
		} catch(SQLException e) {
			return false;
		}
		if(realToken == null || token == null)
			return false;
		return realToken.equals(token);
	}

}
