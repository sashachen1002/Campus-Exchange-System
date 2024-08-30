package 期末報告_11102Programming_v2;

public class User {
	
	private int userID;
	private String userName;
	private String password;
	
	public User(int userID, String userName, String password) {
		
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		
	}
	
	public User(int userID) {
		
		this.userID = userID;
		
	}
	
	public int getUserID() {
		
		return userID;
		
	}

}
