package 期末報告_11102Programming_v2;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sales {
	List<SalesData> sales = new ArrayList<SalesData>();
	
	//簡單商品頁面 //"待售中(暫停)"跟"已售出"不能顯示 //還沒加timeStamp的限制
	public List<SalesData> findAll(){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM BookSales WHERE TCondition = '未售出'");
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalerID(rs.getInt("StudentID"));
				salesData.setISBN(rs.getString("ISBN"));
				salesData.setName(rs.getString("BookName"));
				salesData.setGraph(rs.getBytes("BookImage"));
				salesData.setPrice(rs.getInt("BookPrice"));
				salesData.setBookCondition(rs.getString("BookCondition"));
				salesData.setCategory(rs.getString("Category"));
				salesData.setCourse(rs.getString("Course"));
				salesData.setConnection(rs.getString("LineID"));
				salesData.setCondition(rs.getString("TCondition"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//簡單商品頁面 //"待售中(暫停)"跟"已售出"不能顯示 //還沒加timeStamp的限制
	public List<SalesData> findAllProduct(){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM OtherSales WHERE TCondition = '未售出'");
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalesNum(rs.getInt("SalesNum"));
				salesData.setSalerID(rs.getInt("StudentID"));
				salesData.setName(rs.getString("ProductName"));
				salesData.setGraph(rs.getBytes("Image"));
				salesData.setPrice(rs.getInt("Price"));
				salesData.setCondition(rs.getString("ProductCondition"));
				salesData.setCategory(rs.getString("type"));
				salesData.setConnection(rs.getString("LineID"));
				salesData.setCondition(rs.getString("TCondition"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//會員介面-收藏清單-Table用-書籍
	public List<SalesData> findBookForBookWishlist(int account){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM BookSales AS b, BookWishlist AS bw "
							+ "WHERE b.ISBN = bw.ISBN AND b.StudentID = bw.SellerStudentID AND bw.BuyerStudentID = ?");
			pre.setInt(1, account);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalerID(rs.getInt("b.StudentID"));
				salesData.setISBN(rs.getString("b.ISBN"));
				salesData.setName(rs.getString("b.BookName"));
				salesData.setGraph(rs.getBytes("b.BookImage"));
				salesData.setPrice(rs.getInt("b.BookPrice"));
				salesData.setBookCondition(rs.getString("b.BookCondition"));
				salesData.setCategory(rs.getString("b.Category"));
				salesData.setCourse(rs.getString("b.Course"));
				salesData.setConnection(rs.getString("b.LineID"));
				salesData.setCondition(rs.getString("b.TCondition"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//會員介面-收藏清單-Table用-其他商品
	public List<SalesData> findProductForOtherWishlist(int account){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM OtherSales AS o, OtherWishlist AS ow "
							+ "WHERE o.SalesNum = ow.SalesNum AND o.StudentID = ow.SellerStudentID AND ow.BuyerStudentID = ?");
			pre.setInt(1, account);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalesNum(rs.getInt("o.SalesNum"));
				salesData.setSalerID(rs.getInt("o.StudentID"));
				salesData.setName(rs.getString("o.ProductName"));
				salesData.setGraph(rs.getBytes("o.Image"));
				salesData.setPrice(rs.getInt("o.Price"));
				salesData.setProductCondition(rs.getString("o.ProductCondition"));
				salesData.setCategory(rs.getString("o.type"));
				salesData.setConnection(rs.getString("o.LineID"));
				salesData.setCondition(rs.getString("o.TCondition"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//會員介面-賣家-Table用-書籍
	public List<SalesData> findBookWithSalerID(int account){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM BookSales WHERE StudentID = ?");
			pre.setInt(1, account);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalerID(rs.getInt("StudentID"));
				salesData.setISBN(rs.getString("ISBN"));
				salesData.setName(rs.getString("BookName"));
				salesData.setGraph(rs.getBytes("BookImage"));
				salesData.setPrice(rs.getInt("BookPrice"));
				salesData.setBookCondition(rs.getString("BookCondition"));
				salesData.setCategory(rs.getString("Category"));
				salesData.setCourse(rs.getString("Course"));
				salesData.setConnection(rs.getString("LineID"));
				salesData.setCondition(rs.getString("TCondition"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			System.out.print("找不到數據");//測試用
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//會員介面-賣家-Table用-其他商品
	public List<SalesData> findOtherWithSalerID(int account){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM OtherSales WHERE StudentID = ?");
			pre.setInt(1, account);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalesNum(rs.getInt("SalesNum"));
				salesData.setSalerID(rs.getInt("StudentID"));
				salesData.setName(rs.getString("ProductName"));
				salesData.setGraph(rs.getBytes("Image"));
				salesData.setPrice(rs.getInt("Price"));
				salesData.setProductCondition(rs.getString("ProductCondition"));
				salesData.setCategory(rs.getString("type"));
				salesData.setConnection(rs.getString("LineID"));
				salesData.setCondition(rs.getString("TCondition"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//會員介面-賣家-下架
	/*public boolean removeData(SalesData removeData){
		try {
			PreparedStatement pre = ConnectDB.getCon()
	                .prepareStatement("DELETE FROM BookSales "
	                        + "WHERE BookImage = ? AND BookName = ? AND BookPrice = ? "
	                        + "AND LineID = ? AND TCondition = ?");
	        pre.setBytes(1, removeData.getGraph());
	        pre.setString(2, removeData.getName());
	        pre.setInt(3, removeData.getPrice());
	        pre.setString(4, removeData.getConnection());
	        pre.setString(5, removeData.getCondition());
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}*/
	//會員介面-賣家-下架-書籍
	public boolean removeDataFromBookSales(SalesData removeData){
		try {
			PreparedStatement pre = ConnectDB.getCon()
		                .prepareStatement("DELETE FROM BookSales WHERE StudentID = ? AND ISBN = ?");
		    pre.setInt(1, removeData.getAccount());
		    pre.setString(2, removeData.getISBN());
		    return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	//會員介面-賣家-下架-其他商品
	public boolean removeDataFromOtherSales(SalesData removeData){
		try {
			PreparedStatement pre = ConnectDB.getCon()
		                .prepareStatement("DELETE FROM OtherSales WHERE StudentID = ? AND SalesNum = ?");
		    pre.setInt(1, removeData.getAccount());
		    pre.setInt(2, removeData.getSalesNum());
		    return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//會員介面-賣家-暫停-書籍
	public boolean pauseBookSales(String isbn, int userAccount, String tCondition){
		try {
			PreparedStatement pre = ConnectDB.getCon()
		                .prepareStatement("UPDATE BookSales SET TCondition = ? WHERE ISBN = ? AND StudentID = ?");
			pre.setString(1, tCondition);
			pre.setString(2, isbn);
			pre.setInt(3, userAccount);
		    return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//會員介面-賣家-暫停-其他商品
	public boolean pauseOtherSales(String salesNum, int userAccount, String tCondition){
		try {
			PreparedStatement pre = ConnectDB.getCon()
			                .prepareStatement("UPDATE OtherSales SET TCondition = ? WHERE SalesNum = ? AND StudentID = ?");
			pre.setString(1, tCondition);
			pre.setInt(2, Integer.parseInt(salesNum));
			pre.setInt(3, userAccount);
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
				return false;
		}
	}
	
	//會員介面-買家-移出收藏-書籍
	public boolean removeDataFromBookWishlist(SalesData removeData){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
	                .prepareStatement("DELETE FROM BookWishlist WHERE SellerStudentID = ? AND BuyerStudentID = ? AND ISBN = ? ");
	        pre.setInt(1, removeData.getSalerID());
	        pre.setInt(2, removeData.getAccount());
	        pre.setString(3, removeData.getISBN());
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//會員介面-買家-移出收藏-其他商品
	public boolean removeDataFromOtherWishlist(SalesData removeData){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
	                .prepareStatement("DELETE FROM OtherWishlist WHERE SellerStudentID = ? AND BuyerStudentID = ? AND SalesNum = ? ");
	        pre.setInt(1, removeData.getSalerID());
	        pre.setInt(2, removeData.getAccount());
	        pre.setInt(3, Integer.parseInt(removeData.getISBN()));
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//複雜商品頁面-表格內容&商品資訊 //要輸入ISBN才可以找 //"暫停"跟"下架的"不能顯示
	public List<SalesData> findBookWithISBN(String isbn) throws IOException{
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM BookSales WHERE ISBN = ? AND TCondition = '未售出'");
			pre.setString(1, isbn);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setGraph(rs.getBytes("BookImage"));
				salesData.setName(rs.getString("BookName"));
				salesData.setPrice(rs.getInt("BookPrice"));
				salesData.setConnection(rs.getString("LineID"));
				salesData.setBookCondition(rs.getString("BookCondition"));
				salesData.setCourse(rs.getString("Course"));
				salesData.setCategory(rs.getString("Category"));
				salesData.setSalerID(rs.getInt("StudentID"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	//複雜商品頁面-表格內容&商品資訊 //要輸入SalesNum才可以找 //"暫停"跟"下架的"不能顯示
	public List<SalesData> findProductWithSalesNum(int salesNum) throws IOException{
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM OtherSales WHERE SalesNum = ? AND TCondition = '未售出'");
			pre.setInt(1, salesNum);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalerID(rs.getInt("StudentID"));
				salesData.setName(rs.getString("ProductName"));
				salesData.setGraph(rs.getBytes("Image"));
				salesData.setPrice(rs.getInt("Price"));
				salesData.setProductCondition(rs.getString("ProductCondition"));
				salesData.setCategory(rs.getString("type"));
				salesData.setConnection(rs.getString("LineID"));
				this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	//複雜商品頁面-商品資訊-總和 //要輸入ISBN才可以找 //"暫停"跟"下架的"不能算
	public int countBookWithISBN(String isbn){
		int count = 0;
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT COUNT(*) FROM BookSales WHERE ISBN = ? AND TCondition = '未售出'");
			pre.setString(1, isbn);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				count = rs.getInt(1); // 將 count 更新為結果集中的計數值
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	//複雜商品頁面-商品資訊-所有課程 //要輸入ISBN才可以找 //"暫停"跟"下架的"不能算
	public String getCoursesWithISBN(String isbn){
		String courses = "";
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT DISTINCT Course FROM BookSales WHERE ISBN = ? AND TCondition = '未售出'");
			pre.setString(1, isbn);
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
	            String course = rs.getString("Course");
	            if (!courses.isEmpty()) {//如果非空，就先加一個分隔符號
	                courses += " | ";
	            }
	            courses += course;//再疊加課堂
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	//複雜商品頁面-商品資訊-加入收藏清單-書籍-加入清單前要確認有無重複收藏
	public boolean checkBookForBookWishlist(int account, String isbn, int salerID){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM BookWishlist WHERE BuyerStudentID = ? AND ISBN = ? AND SellerStudentID = ? ");
			pre.setInt(1, account);
			pre.setString(2, isbn);
			pre.setInt(3, salerID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				return true; // 如果找到符合條件的資料，回傳 true
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;// 迴圈結束後仍未找到符合條件的資料，回傳 false
	}
	
	//複雜商品頁面-商品資訊-加入收藏清單-其他商品-加入清單前要確認有無重複收藏
	public boolean checkOtherForOtherWishlist(int account, int salesNum, int salerID){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM OtherWishlist WHERE BuyerStudentID = ? AND SalesNum = ? AND SellerStudentID = ? ");
			pre.setInt(1, account);
			pre.setInt(2, salesNum);
			pre.setInt(3, salerID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				return true; // 如果找到符合條件的資料，回傳 true
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;// 迴圈結束後仍未找到符合條件的資料，回傳 false
	}
	
	//複雜商品頁面-商品資訊-加入收藏清單-書籍
	public boolean addBookWishlist(SalesData addData){//這邊的SQL語法要改
	try {
		PreparedStatement pre = ConnectDB.getCon()
				.prepareStatement("INSERT INTO BookWishlist(SellerStudentID,BuyerStudentID,ISBN) VALUES (?,?,?)");
		pre.setInt(1, addData.getSalerID());
		pre.setInt(2, addData.getAccount()); //登入者自己
		pre.setString(3, addData.getISBN());
		return pre.executeUpdate() > 0;
	} catch (SQLException e) {
		return false;
	}
	}
	
	//複雜商品頁面-商品資訊-加入收藏清單-其他商品
	public boolean addOtherWishlist(SalesData addData){//這邊的SQL語法要改
	try {
		PreparedStatement pre = ConnectDB.getCon()
				.prepareStatement("INSERT INTO OtherWishlist(SellerStudentID,BuyerStudentID,SalesNum) VALUES (?,?,?)");
		pre.setInt(1, addData.getSalerID());
		pre.setInt(2, addData.getAccount()); //登入者自己
		pre.setInt(3, addData.getSalesNum());
		return pre.executeUpdate() > 0;
	} catch (SQLException e) {
		return false;
	}
	}
	
	//Item模板
	public boolean uploadProduct(SalesData uploadProduct){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("INSERT INTO OtherSales (StudentID,Productname,Image,price,ProductCondition,type,LineID) VALUES (?,?, ?, ?, ?, ?, ?)");
			pre.setInt(1,uploadProduct.getSalerID()); //User待修正 //已修正
			pre.setString(2,uploadProduct.getName());
			pre.setBytes(3,uploadProduct.getGraph());
			pre.setInt(4,uploadProduct.getPrice());
			pre.setString(5,uploadProduct.getProductCondition());
			pre.setString(6,uploadProduct.getCategory());
			pre.setString(7,uploadProduct.getConnection());
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//Book模板
	public boolean uploadBook(SalesData uploadBook){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("INSERT INTO BookSales (StudentID,ISBN,BookName,BookImage,BookPrice,BookCondition, Category , Course, LineID) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)");
			pre.setInt(1,uploadBook.getSalerID()); //User待修正 //已修正
			pre.setString(2,uploadBook.getISBN());
			pre.setString(3,uploadBook.getName());
			pre.setBytes(4,uploadBook.getGraph());
			pre.setInt(5,uploadBook.getPrice());
			pre.setString(6,uploadBook.getBookCondition());
			pre.setString(7,uploadBook.getCategory());
			pre.setString(8,uploadBook.getLesson());
			pre.setString(9,uploadBook.getConnection());
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//UserSubmit //註冊
	public boolean registration(SalesData registration){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("INSERT INTO User VALUES(?,?,?)");
			pre.setInt(1,registration.getAccount()); //StudentID
			pre.setString(2,registration.getName()); //UserName
			pre.setString(3,registration.getPassword()); //Password
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//UserLogin+UserSubmit //登入 //帳號+密碼
	public List<SalesData> login(){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT StudentID,Password FROM User");
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
			SalesData salesData = new SalesData();
			salesData.setAccount(rs.getInt("StudentID"));
			salesData.setPassword(rs.getString("Password"));
			this.sales.add(salesData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.sales;
	}
	
	//upperPanel //搜尋書籍 //"暫停"跟"下架的"不能顯示
	public List<SalesData> searchingBooks(String bookName){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
						.prepareStatement("SELECT ISBN, BookImage, BookName, BookPrice FROM BookSales WHERE BookName LIKE ? AND TCondition = '未售出'");
			pre.setString(1, "%" + bookName+ "%");
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setISBN(rs.getString("ISBN"));
				salesData.setName(rs.getString("BookName"));
				salesData.setGraph(rs.getBytes("BookImage"));
				salesData.setPrice(rs.getInt("BookPrice"));
				this.sales.add(salesData);
			}
			return this.sales;
		} catch (SQLException e) {//什麼都沒搜到的話就回覆空值
			e.printStackTrace();
			return null;
		}
		
	}
	
	//upperPanel //搜尋其他商品 //"暫停"跟"下架的"不能顯示
	public List<SalesData> searchingOthers(String productName){
		this.sales.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
						.prepareStatement("SELECT * FROM OtherSales WHERE ProductName LIKE ? AND TCondition = '未售出'");
			pre.setString(1, "%" +productName+ "%");
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				SalesData salesData = new SalesData();
				salesData.setSalesNum(rs.getInt("SalesNum"));
				salesData.setName(rs.getString("ProductName"));
				salesData.setGraph(rs.getBytes("Image"));
				salesData.setPrice(rs.getInt("Price"));
				this.sales.add(salesData);
			}
			
		} catch (SQLException e) {//什麼都沒搜到的話就回覆空值
			e.printStackTrace();
			return null;
		}
		return this.sales;
		
	}
	
}
