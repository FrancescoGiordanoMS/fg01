package francesco.giordano.fg01.model;

public class J04Users {
	private int codiceUser;
	private String username;
	private String email;
	private String password;
	private int hash;
	
	
	public int getCodiceUser() {
		return codiceUser;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public int getHash() {
		return hash;
	}
	public void setCodiceUser(int codiceUser) {
		this.codiceUser = codiceUser;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setHash(int hash) {
		this.hash = hash;
	}
	
	

}
