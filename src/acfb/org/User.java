package acfb.org;

public class User {
	private int user_id;
	private String user_name;
	private String user_username;
	private String user_email;
	private String user_password;
	private int user_role;
	private String user_time;
	
	public User() {}
	
	public User(String user_username, String user_password, int user_role) {
		setUser_username(user_username);
		setUser_password(user_password);
		setUser_role(user_role);
	}
	
	public User(String user_name, String user_username, String user_email, String user_password, String user_time) {
		setUser_username(user_username);
		setUser_password(user_password);
		setUser_name(user_name);
		setUser_email(user_email);
		setUser_time(user_time);
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public int getUser_role() {
		return user_role;
	}

	public void setUser_role(int user_role) {
		this.user_role = user_role;
	}

	public String getUser_time() {
		return user_time;
	}

	public void setUser_time(String user_time) {
		this.user_time = user_time;
	}
}

