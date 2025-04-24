package com.make_profile.dto.user;



public class MakeProfileUserDto {

	private Long id;

	private String name;

	private String mobileNumber;

	private String email;

	private String signInAccess;

	private String password;

	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSignInAccess() {
		return signInAccess;
	}

	public void setSignInAccess(String signInAccess) {
		this.signInAccess = signInAccess;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
