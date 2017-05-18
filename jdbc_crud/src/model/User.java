package model;

import java.util.Date;

public class User {
	private int id;
	private String name;
	private int age;
	private boolean admin;
	private Date createdDate;

	public User(final String name, final int age, final boolean admin, final Date createdDate) {
		this.name = name;
		this.age = age;
		this.admin = admin;
		this.createdDate = createdDate;
	}

	public User(final int id, final String name, final int age, final boolean admin, final Date createdDate) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.admin = admin;
		this.createdDate = createdDate;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(final boolean admin) {
		this.admin = admin;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", admin=" + admin +
				", createdDate=" + createdDate +
				'}';
	}
}
