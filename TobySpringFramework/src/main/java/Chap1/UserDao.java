package Chap1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	private DataSource dataSource;
	private User user;
	private JdbcContext jdbcContext;
	
	public void setJdbcContext(JdbcContext jdbcContext){
		this.jdbcContext = jdbcContext;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(final User user) throws ClassNotFoundException, SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");

				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				return ps;
			}
		});
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("select * from users where id = ?");

			ps.setString(1, id);
			rs = ps.executeQuery();
			this.user = null;

			if (rs.next()) {
				this.user = new User();
				this.user.setId(rs.getString("id"));
				this.user.setName(rs.getString("name"));
				this.user.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {

				}
			}
		}

		if (this.user == null)
			throw new EmptyResultDataAccessException(1);

		return this.user;
	}


	public void deleteAll() throws SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("delete from users");
				return ps;
			}
		});
	}

	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();

			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {

				}
			}
		}

	}
}
