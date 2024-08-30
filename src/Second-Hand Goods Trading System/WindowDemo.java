package 期末報告_11102Programming_v2;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WindowDemo extends JFrame{

	private JFrame homeFrame;
	private JTable jtableCommodities;
	
	//用來存取選取列的ISBN或SalesNum
	private String selectedProductNum= "";
	
	//建立各種頁面
	private GUI_UpperPanel gui_UpperPanel;
	private DetailedProductPage detailedBookPage;
	private DetailedProductPage2 detailedProductPage;
	
	private List<SalesData> bookSalesDataList;
	private List<SalesData> otherSalesDataList;
	private List<SalesData> searchingDataList;
	private Sales sales = new Sales();
	
	private String searchingNull;
	
	private int userAccount;
	private boolean loginSuccess;
	
	/*public static void main(String[] args) {
		// 呼叫HomeFrame
		WindowDemo frame = new WindowDemo();
		frame.getHomeFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}*/
	
	public WindowDemo(){
		initialize();
		loadData();//表格
	}
	
	//給搜尋過後的首頁用的
	public WindowDemo(String name, String selectedValue){
		
		initialize();
		loadData(name, selectedValue); //表格
		
	}

	private void initialize() {
		homeFrame = new JFrame();
		homeFrame.setTitle("首頁");
		homeFrame.getContentPane().setForeground(new Color(255, 255, 255));
		/*homeFrame.setBounds(100, 100, 1920, 1080);*/
		homeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 184, 1400, 597);
		homeFrame.getContentPane().add(scrollPane);
		
		jtableCommodities = new JTable();
		jtableCommodities.setBounds(41, 184, 1455, 637);
		jtableCommodities.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		jtableCommodities.getTableHeader().setFont(new Font("微軟正黑體", Font.BOLD, 18));
		scrollPane.setViewportView(jtableCommodities);
		jtableCommodities.setPreferredScrollableViewportSize(jtableCommodities.getPreferredSize());
	}
	
	private void loadData() { //表格內容
		
		//	設定 table model 並 override
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}
			public Class<?> getColumnClass(int column) {
		        if(column == 1){return ImageIcon.class;}	// 設定column為ImageIcon
		        else {return Object.class;}
			}
		};
		
		defaultTableModel.addColumn("ISBN/商品編號"); //用ISBN/商品銷售代碼比較好設置相對應的詳細商品頁面
		defaultTableModel.addColumn("照片");
		defaultTableModel.addColumn("名稱");
		defaultTableModel.addColumn("價格");
		defaultTableModel.addColumn("詳情");
		
		//統整後的
		String name = "",detail="", isbn="";
		byte[] photo;
		int row = 0, price = 0, salesNum = 0;
		
		BookManager bookManager = new BookManager();
		bookSalesDataList = sales.findAll();
        
        for(SalesData data : bookSalesDataList) {
        	row++;
        	defaultTableModel.addRow(new Object[]{
	        	isbn = data.getISBN(),
	        	photo = data.getGraph(),
	        	name = data.getName(),
	        	price = data.getPrice(),
	        	detail = data.getCondition()
        	});
        	/*if(bookManager.getBooks().size()==1) {// 第一個強制顯示
				bookManager.addBook(new Book(photo,name,price));
				defaultTableModel.addRow(new Object[]{isbn,photo,name,price,detail});
			}else {
				for(Book book : bookManager.getBooks()) {
					if(!book.getName().equals(name)) {// 不同書籍則新增row、book
						defaultTableModel.addRow(new Object[]{isbn,photo,name,price,detail});
						bookManager.addBook(new Book(photo,name,price));
					}else {//同本書則新增該書價格並更新
						book.addPrice(price);
						defaultTableModel.setValueAt(book.getPrices(), row-1, 3);
					}
				}
			}*/
        }
        
        otherSalesDataList = sales.findAllProduct();
        for(SalesData data : otherSalesDataList) {
        	row++;
        	defaultTableModel.addRow(new Object[]{
    	        data.getSalesNum(),
    	        data.getGraph(),
    	    	data.getName(),
    	    	data.getPrice(),
    	    	data.getCondition()
            });
        }
        
		jtableCommodities.setModel(defaultTableModel);
		jtableCommodities.setRowHeight(300);	//	最好和下方getScaledInstance()的第二個數相同
		//調整寬度
		jtableCommodities.getTableHeader().setReorderingAllowed(false);
		jtableCommodities.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtableCommodities.getColumnModel().getColumn(0).setPreferredWidth(200);//產品編號
		jtableCommodities.getColumnModel().getColumn(1).setPreferredWidth(400);//圖片
		jtableCommodities.getColumnModel().getColumn(2).setPreferredWidth(400);//名稱
		jtableCommodities.getColumnModel().getColumn(3).setPreferredWidth(250);//價格
		jtableCommodities.getColumnModel().getColumn(4).setPreferredWidth(150);
		//內文置中
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		for(int i=0;i<4;i++) {
			TableColumn column=jtableCommodities.getColumnModel().getColumn(i);
			render.setHorizontalAlignment(SwingConstants.CENTER);
			column.setCellRenderer(render);
		}
		//放上圖片
		class ImageRender extends DefaultTableCellRenderer{//放圖片細部class
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, 
					boolean isSelected, boolean hasFocus, int row, int column) {
				
				if (value instanceof byte[]) {
		            byte[] bytes = (byte[]) value;

		            ImageIcon imageIcon = new ImageIcon(bytes);
		            Image image = imageIcon.getImage();
		            Image scaledImage = image.getScaledInstance(300,400, Image.SCALE_SMOOTH);
		            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
		            
		            setIcon(scaledImageIcon);
		            setHorizontalAlignment(JLabel.CENTER);
		        }
		        return this;
			}
		}
		jtableCommodities.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
		//加上按鈕
		jtableCommodities.getColumnModel().getColumn(4).setCellRenderer(new ButtonCellRenderer());
		jtableCommodities.getColumnModel().getColumn(4).setCellEditor(new ButtonCellEditor());
	}
	
	private void loadData(String serchingName, String selectedValue) { // 商品展示 //給搜尋過後的頁面用的
		
		//	設定 table model 並 override
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}
			public Class<?> getColumnClass(int column) {
		        if(column == 1){return ImageIcon.class;}	// 設定column為ImageIcon
		        else {return Object.class;}
			}
		};
		
		defaultTableModel.addColumn("ISBN/商品編號"); //用ISBN/商品銷售代碼比較好設置相對應的詳細商品頁面
		defaultTableModel.addColumn("照片");
		defaultTableModel.addColumn("名稱");
		defaultTableModel.addColumn("價格");
		defaultTableModel.addColumn("詳情");
		
		//表格
		//統整後的
		String name = "", productNum="";//商品代號一律用String
		byte[] photo;
		int row = 0, price = 0;
		
		if(selectedValue.equals("書籍")) {
			searchingDataList = sales.searchingBooks(serchingName);
			if(searchingDataList==null) {
				searchingNull = null;
			}else {
				searchingNull = "getData";
				for(SalesData data : searchingDataList) {
		        	row++;
		        	defaultTableModel.addRow(new Object[]{
		        			productNum = data.getISBN(),
		    	        	photo = data.getGraph(),
		    	        	name = data.getName(),
		    	        	price = data.getPrice()
		            });
				}
			}
		}else if(selectedValue.equals("其他")) {
			searchingDataList = sales.searchingOthers(serchingName);
			if(searchingDataList==null) {
				searchingNull = null;
			}else {
				searchingNull = "getData";
				for(SalesData data : searchingDataList) {
		        	row++;
		        	defaultTableModel.addRow(new Object[]{
			        	productNum = data.getSalesNum()+"",
			        	photo = data.getGraph(),
			        	name = data.getName(),
			        	price = data.getPrice()
		        	});
				}
			}
		}
		
		jtableCommodities.setModel(defaultTableModel);
		jtableCommodities.setRowHeight(300);	//	最好和下方getScaledInstance()的第二個數相同
		//調整寬度
		jtableCommodities.getTableHeader().setReorderingAllowed(false);
		jtableCommodities.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtableCommodities.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtableCommodities.getColumnModel().getColumn(1).setPreferredWidth(400);
		jtableCommodities.getColumnModel().getColumn(2).setPreferredWidth(400);
		jtableCommodities.getColumnModel().getColumn(3).setPreferredWidth(250);
		jtableCommodities.getColumnModel().getColumn(4).setPreferredWidth(150);
		//內文置中
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		for(int i=0;i<4;i++) {
			TableColumn column=jtableCommodities.getColumnModel().getColumn(i);
			render.setHorizontalAlignment(SwingConstants.CENTER);
			column.setCellRenderer(render);
		}
		//放上圖片
		class ImageRender extends DefaultTableCellRenderer{//放圖片細部class
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, 
					boolean isSelected, boolean hasFocus, int row, int column) {
				
				if (value instanceof byte[]) {
		            byte[] bytes = (byte[]) value;

		            ImageIcon imageIcon = new ImageIcon(bytes);
		            Image image = imageIcon.getImage();
		            Image scaledImage = image.getScaledInstance(300,400, Image.SCALE_SMOOTH);
		            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
		            
		            setIcon(scaledImageIcon);
		            setHorizontalAlignment(JLabel.CENTER);
		        }
		        return this;
			}
		}
		jtableCommodities.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
		//加上按鈕
		jtableCommodities.getColumnModel().getColumn(4).setCellRenderer(new ButtonCellRenderer());
		jtableCommodities.getColumnModel().getColumn(4).setCellEditor(new ButtonCellEditor());
	}
	
	//放按鈕細部class
	private class ButtonCellRenderer extends JButton implements TableCellRenderer {
		public ButtonCellRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText("詳情"); 
		    setBackground(Color.WHITE);
		    setFont(new Font("微軟正黑體", Font.BOLD, 18));
			return this;
		}
	}

	/**
	 * Custom cell editor for the operation column (buttons)
	 */
	private class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
		private JButton button;

		public ButtonCellEditor() {
			button = new JButton("詳情");
	        button.setFont(new Font("微軟正黑體", Font.BOLD, 18));
	        button.setOpaque(true);
	        button.setBackground(Color.WHITE);
	        button.addActionListener(this);
		}

		public Object getCellEditorValue() {
			return button.getText();
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			return button;
		}

		public void actionPerformed(ActionEvent e) {
			
			int selectedRow = jtableCommodities.getSelectedRow();
		    Object value = jtableCommodities.getValueAt(selectedRow, jtableCommodities.getColumn("ISBN/商品編號").getModelIndex());
			// 按鈕的操作 ==> 跳轉至該物品的詳情頁面
			try {
				if(value instanceof String) {
					
					homeFrame.dispose();
					String isbn = (String) value;
					detailedBookPage = new DetailedProductPage(isbn);
					detailedBookPage.setUpperPanel(loginSuccess, userAccount);
					detailedBookPage.getFrame().setVisible(true);
				
				}else if(value instanceof Integer){
					homeFrame.dispose();
					int productNum = (int) value;
					detailedProductPage = new DetailedProductPage2(productNum);
					detailedProductPage.setUpperPanel(loginSuccess, userAccount);
					detailedProductPage.getFrame().setVisible(true);
				}	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class Book{	// 存放同ISBN多書籍的價格們
		
		public byte[] bookPhoto;
		public String bookName;
		public ArrayList<Integer> bookPrices;
		
		public Book(byte[] photo,String name,int price) {
			this.bookPhoto = photo;
			this.bookName = name;
			bookPrices = new ArrayList();
			this.bookPrices.add(Integer.valueOf(price));
		}
		public String getName() {
			return this.bookName;
		}
		public void addPrice(int price) {
			bookPrices.add(Integer.valueOf(price));
		}
		public String getPrices() {
			int max = Collections.max(bookPrices);
			int min = Collections.min(bookPrices);
			String prices = min+" ~ "+max;
			return prices;
		}
	}
	
	private class BookManager{
		public ArrayList<Book> books;
		public BookManager() {
			books = new ArrayList();
		}
		public void addBook(Book book) {
			books.add(book);
		}
		public ArrayList<Book> getBooks(){
			return this.books;
		}
		
	}
	
	//設定成已登入的upperPanel
	public void setUpperPanel(boolean loginSuccess, int userAccount) {
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.homeFrame,loginSuccess, userAccount);
		this.loginSuccess = true;
		this.userAccount = userAccount;
		
	}
	
	//設定成尚未登入的upperPanel
	public void setUpperPanel() {
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.homeFrame,false,0);
		this.loginSuccess = false;
		this.userAccount = 0;
		
	}
	
	public String getSearchingNull() {
		return searchingNull;
	}
	
	public JFrame getFrame() {
	    return homeFrame;
	}
	
	public JFrame getHomeFrame() {
		return homeFrame;
	}
}
