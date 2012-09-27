package models;

import org.mindrot.BCrypt;
import play.db.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {

	private int id;

	private String username;
	private String pwhash;
	private String email;
	private String fullname;

	public User(String username, String password, String email, String fullname) throws SQLException {
		this.username = username;
		this.pwhash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		this.email = email;
		this.fullname = fullname;
		
		Connection conn = DB.getConnection();

		PreparedStatement stmt = conn.prepareStatement("insert into user (username, password, email, fullname) values (?, ?, ?, ?)");
		stmt.setString(1, username);
		stmt.setString(2, this.pwhash);
		stmt.setString(3, email);
		stmt.setString(4, fullname);
		stmt.execute();
	}
}
