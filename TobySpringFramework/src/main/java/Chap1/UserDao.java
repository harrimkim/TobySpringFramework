package Chap1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class UserDao {
//	private ConnectionMaker connectionMaker;
	private DataSource dataSource;
	private Connection c;
	private User user;

//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
//		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c
				.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.execute();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
//		this.c = connectionMaker.makeConnection();

		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return this.user;
	}
}
