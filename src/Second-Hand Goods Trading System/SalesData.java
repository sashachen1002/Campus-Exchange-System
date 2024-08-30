package 期末報告_11102Programming_v2;


public class SalesData {
	private byte[] graph;
	private String name = "";
	private int price;
	private String connection = "";
	private String condition;
	private String bookCondition;
	private String productCondition;
	private String course;
	private String category;
	private int salerID;
	private String isbn;
	private String lesson;
	private int account;
	private String userName;
	private String password;
	private int salesNum;
	
	public SalesData(byte[] graph, String name, int price, String connection, String condition, String bookCondition, String course, String category, int salerID) {
		this.graph = graph;
		this.name = name;
		this.price = price;
		this.connection = connection;
		this.condition = condition;
		this.bookCondition = bookCondition;
		this.course = course;
		this.category = category;
		this.salerID = salerID;
	}
	
	//OtherSalse專用
	public SalesData(int salerID,int salesNum, String name, byte[] graph,int price, String productCondition, String category, String connection) {
		this.salerID = salerID;
		this.salesNum = salesNum;
		this.name = name;
		this.graph = graph;
		this.price = price;
		this.productCondition = productCondition;
		this.category = category;
		this.connection = connection;
	}
	
	public SalesData(byte[] graph, String name, int price, String connection, String condition) {
		this.graph = graph;
		this.name = name;
		this.price = price;
		this.connection = connection;
		this.condition = condition;
	}
	
	//收藏-書籍
	public SalesData(int salerID, int account, String isbn) {
		this.salerID = salerID;
		this.account = account;
		this.isbn = isbn;
	}
	//收藏-其他商品
	public SalesData(int salerID, int account, int salesNum) {
		this.salerID = salerID;
		this.account = account;
		this.salesNum = salesNum;
	}
	
	public SalesData(byte[] graph, int price, String bookCondition, String connection) {
		this.graph = graph;
		this.price = price;
		this.bookCondition = bookCondition;
		this.connection = connection;
	}
	
	//Item模板用
	public SalesData(int studentID, String name, byte[] graph, int price, String condition, String type, String connection ) {
		this.salerID = studentID;
		this.name = name;
		this.graph = graph;
		this.price = price;
		this.productCondition = condition;
		this.category = type;
		this.connection = connection;
	}
	//Book模板用
	public SalesData(int studentID, String isbn, String name,byte[] graph, int price, String condition, String type,String lesson, String connection ) {
		this.salerID = studentID;
		this.isbn = isbn;
		this.name = name;
		this.graph = graph;
		this.price = price;
		this.bookCondition = condition;
		this.category = type;
		this.lesson = lesson;
		this.connection = connection;
	}
	//UserSubmit用 //註冊
	public SalesData(int account, String userName, String password) {
			this.account = account;
			this.userName = userName;
			this.password = password;
	}
	//下架用-書籍
	public SalesData(int account, String isbn) {
		this.account = account;
		this.isbn = isbn;
	}
	//下架用-其他商品
	public SalesData(int account, int salesNum) {
		this.account = account;
		this.salesNum = salesNum;
	}
	
	public SalesData() {
		
	}
	public byte[] getGraph() {
		return graph;
	}
	public void setGraph(byte[] graph) {
		this.graph = graph;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getBookCondition() {
		return bookCondition;
	}
	public void setBookCondition(String bookCondition) {
		this.bookCondition = bookCondition;
	}
	public String getProductCondition() {
		return productCondition;
	}
	public void setProductCondition(String productCondition) {
		this.productCondition = productCondition;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getSalerID() {
		return salerID;
	}
	public void setSalerID(int salerID) {
		this.salerID = salerID;
	}
	public String getISBN() {
		return isbn;
	}
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}
	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(int salesNum) {
		this.salesNum = salesNum;
	}
	
}
