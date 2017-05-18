package dao;

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
	public static void main(final String[] args) {
		new UserDaoImpl().addUser(new User("Andrey", 34, true, new Date()));
	}

	@Override
	public void addUser(final User user) {
		// connection to DB
		try {
			// this line of code check if Dirver exists, initialize it and regitster in the DriverManager
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException ignored) {
			System.out.println("Can't Find MySQL JDBC Driver!");
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");

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
	public void updateUser(final User user) {

	}

	@Override
	public void removeUser(final int userId) {

	}

	@Override
	public User getUserById(final int userId) {
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return null;
	}
}
