package models;
import play.db.*;
import java.sql.*;

public class Topic {

	String name;
	
	public Topic(String name) {
		this.name = name;
	}

	public void addUser(User user) throws Exception {
		
		Connection conn = DB.getConnection();
		PreparedStatement stmt = conn.prepareStatement("insert into interest (user, topic) values (?, ?)");
		stmt.setLong(1, user.id);
		stmt.setString(2, name);
		stmt.executeUpdate();
	}

	public boolean hasUser(User user) throws Exception {
		ResultSet result;
		int rownum = 0;
		Connection conn = DB.getConnection();
		PreparedStatement stmt = conn.prepareStatement("select topic from interest where user = ? and topic = ?");
		stmt.setLong(1, user.id);
		stmt.setString(2, name);
		result = stmt.executeQuery();
		while(result.next())
			rownum++;
		return rownum != 0;
	}
}
