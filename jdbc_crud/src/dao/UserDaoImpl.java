package dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import model.User;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Links to the pages with answers to the faced dificulties:
 * 1. <a href="https://www.mkyong.com/jdbc/how-to-connect-to-mysql-with-jdbc-driver-java/">
 * JDBC connection example</a>
 * 2. <a href="http://stackoverflow.com/questions/34189756/warning-about-ssl-connection-when-connecting-to-mysql-database">
 * Warning about SSL connection when connecting to MySQL database</a>
 */

public class UserDaoImpl implements UserDao {

	private void initDB() {
		// connection to DB
		try {
			// this line of code check if Dirver exists, initialize it and regitster in the DriverManager
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException ignored) {
			System.out.println("Can't Find MySQL JDBC Driver!");
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
	}

	private MysqlDataSource getMySQLDataSource() {
		final MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/crud");
		mysqlDataSource.setUser("root");
		mysqlDataSource.setPassword("root");
		return mysqlDataSource;
	}

	private PreparedStatement prepareSelectStatement(final Connection connection, final int userId) throws SQLException {
		final String queryString = "SELECT * FROM Test WHERE id = ?";
		final PreparedStatement ps = connection.prepareStatement(queryString);
		ps.setInt(1, userId);
		return ps;
	}

	@Override
	public void addUser(final User user) {
		initDB();

		final String queryString = "INSERT INTO Test VALUES (NULL, ?, ?, ?, ?)";

		try (final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud?verifyServerCertificate=false&useSSL=true", "root", "root");
		     final PreparedStatement addUser = connection.prepareStatement(queryString)) {

			System.out.println("Connected!!!");

			final String userName = user.getName();
			final int age = user.getAge();
			final boolean admin = user.isAdmin();
			final long createdDate = user.getCreatedDate().getTime();
			final Timestamp timestamp = new Timestamp(createdDate);

			// create and execute sql statement to add new user to DB
			addUser.setString(1, userName);
			addUser.setInt(2, age);
			addUser.setBoolean(3, admin);
			addUser.setTimestamp(4, timestamp);

			addUser.executeUpdate();

			System.out.println("New user added: " + user);
		} catch (final SQLException ignored) {
			System.out.println("Connection Failed!");
		}
	}

	@Override
	public boolean updateUser(final User user) {
		initDB();

		final MysqlDataSource mysqlDataSource = getMySQLDataSource();
		final String queryString = "UPDATE Test SET name = ?, age = ?, isAdmin = ?, createdDate = ? WHERE id = ?";

		try (final Connection connection = mysqlDataSource.getConnection();
		     final PreparedStatement updateUser = connection.prepareStatement(queryString)) {

			final int id = user.getId();
			final String userName = user.getName();
			final int age = user.getAge();
			final boolean admin = user.isAdmin();
			final long createDate = user.getCreatedDate().getTime();
			final Timestamp timestamp = new Timestamp(createDate);

			//create and execute sql statement to add new user to DB
			updateUser.setString(1, userName);
			updateUser.setInt(2, age);
			updateUser.setBoolean(3, admin);
			updateUser.setTimestamp(4, timestamp);
			updateUser.setInt(5, id);

			if (updateUser.executeUpdate() > 0) {
				System.out.println("Connected!!!");
				System.out.println("User with id = " + id + " was updated!");
				return true;
			}

			return false;

		} catch (final SQLException ignored) {
			System.out.println("Connection Failed!");
			return false;
		}
	}

	@Override
	public boolean removeUser(final int userId) {
		initDB();

		final MysqlDataSource mysqlDataSource = getMySQLDataSource();
		final String queryString = "DELETE FROM Test WHERE id = ?";

		try (final Connection connection = mysqlDataSource.getConnection();
		     final PreparedStatement deleteUser = connection.prepareStatement(queryString)) {

			deleteUser.setInt(1, userId);

			if (deleteUser.executeUpdate() > 0) {
				System.out.println("Connected!!!");
				System.out.println("User with id = " + userId + " was deleted!");
				return true;
			}

			return false;

		} catch (final SQLException ignored) {
			System.out.println("Connection Failed!");
			return false;
		}
	}

	@Override
	public User getUserById(final int userId) {
		initDB();

		User user = null;

		final MysqlDataSource mysqlDataSource = getMySQLDataSource();

		try (final Connection connection = mysqlDataSource.getConnection();
		     final PreparedStatement selectUser = prepareSelectStatement(connection, userId);
		     final ResultSet resultSet = selectUser.executeQuery()) {

			while (resultSet.next()) {
				final String userName = resultSet.getString(2);
				final int age = resultSet.getInt(3);
				final boolean admin = resultSet.getBoolean(4);
				final Date createdDate = resultSet.getTimestamp(5);

				user = new User(userId, userName, age, admin, createdDate);
			}
		} catch (final SQLException ignored) {
			System.out.println("Connection Failed!");
		}

		return user;
	}

	@Override
	public List<User> getAllUsers() {
		initDB();

		final MysqlDataSource mysqlDataSource = getMySQLDataSource();
		final String queryString = "SELECT * FROM Test";

		final List<User> allUsers = new ArrayList<>();

		try (final Connection connection = mysqlDataSource.getConnection();
		     final PreparedStatement selectUsers = connection.prepareStatement(queryString);
		     final ResultSet resultSet = selectUsers.executeQuery()) {

			while (resultSet.next()) {
				final int userId = resultSet.getInt(1);
				final String userName = resultSet.getString(2);
				final int age = resultSet.getInt(3);
				final boolean admin = resultSet.getBoolean(4);
				final Date createdDate = resultSet.getTimestamp(5);

				allUsers.add(new User(userId, userName, age, admin, createdDate));
			}

		} catch (final SQLException ignored) {
			System.out.println("Connection failed!");
		}
		return allUsers;
	}
}
