package de.erdtmann.soft.haussteuerung.login.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@NamedNativeQuery(
		name="User.login",
		query="select id, benutzer, password from USER u where u.benutzer = :user and u.password = :pw",
		resultClass=UserE.class)

@Entity
@Table(name = "USER")
public class UserE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="benutzer")
	private String user;
	@Column(name="password")
	private String pw;
	
	protected UserE() { }
	
	private UserE(Builder builder) {
		setUser(builder.user);
		setPw(builder.pw);
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public static final class Builder {
		private String user;
		private String pw;

		private Builder() {
		}

		public Builder withUser(String user) {
			this.user = user;
			return this;
		}
		
		public Builder withPw(String pw) {
			this.pw = pw;
			return this;
		}

		public UserE build() {
			return new UserE(this);
		}
	}
}
