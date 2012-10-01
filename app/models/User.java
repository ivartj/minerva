package models;

import play.db.*;
import java.sql.*;
import org.mindrot.BCrypt;

public class User {

	private String username;
	private String email;
	private String fullname;

	private User() {}

	public User(String username, String password, String email, String fullname) throws Exception {
		this.username = username;
		String passhash = BCrypt.hashpw(password, BCrypt.gensalt(12));
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

	// Returns null when there is no user by the username
	public static User getByUsername(String username) throws Exception {
		Connection conn = DB.getConnection();
		PreparedStatement stmt = conn.prepareStatement("select email, fullname from user where username = ?"); 
		stmt.setString(1, username);
		ResultSet result = stmt.executeQuery();
		if(result.next() == false)
			return null;
		User retval = new User();
		retval.username = username;
		retval.email = result.getString(1);
		retval.fullname = result.getString(2);
		return retval;
	}
}
