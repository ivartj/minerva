package models;

import play.db.*;
import java.sql.*;
import org.mindrot.BCrypt;

public class User {

	private String username;
	private String passhash;
	private String email;
	private String fullname;

	public User(String username, String password, String email, String fullname) throws Exception {
		this.username = username;
		this.passhash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		this.email = email;
		this.fullname = fullname;

		Connection conn = DB.getConnection();

		PreparedStatement stmt = conn.prepareStatement("insert into user (username, passhash, email, fullname) values (?, ?, ?, ?)");
		stmt.setString(1, username);
		stmt.setString(2, passhash);
		stmt.setString(3, email);
		stmt.setString(4, fullname);
		stmt.execute();
	}

	public static boolean authenticate(String username, String password) throws Exception {
		Connection conn = DB.getConnection();
		PreparedStatement stmt = conn.prepareStatement("select passhash from user where username = ?");
		stmt.setString(1, username);

		ResultSet result = stmt.executeQuery();

		if(!result.next())
			return false;

		String passhash = result.getString(1);

		return BCrypt.checkpw(password, passhash);
	}

}
