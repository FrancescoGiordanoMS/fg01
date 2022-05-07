package francesco.giordano.fg01.model;

public class J04Users {
	private int codiceuser;
	private String username;
	private String email;
	private String password;
	private int hash;
	
	
	public int getCodiceuser() {
		return codiceuser;
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
	public void setCodiceuser(int codiceUser) {
		this.codiceuser = codiceUser;
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
