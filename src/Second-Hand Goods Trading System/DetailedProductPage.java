package 期末報告_11102Programming_v2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.Component;
import java.util.List;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetailedProductPage extends JFrame{ //複雜商品頁面-書籍

	//GUI
	private JFrame detailedProductPage;
	private JTable productTable;
	private JLabel numLabel1;
	private JPanel productIPanel;
	private JButton btnNewButton_1;
	private JPanel productPic;
	private JLabel productPic_1;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JPanel productInfoPanel;
	private JLabel isbnLabel1;
	private JLabel classLabel1;
	private JLabel numLabel2;
	private JLabel classLabel2;
	private JLabel isbnLabel2;
	private JLabel productNameLabel;
	private JPanel panel;
	private JLabel lblNewLabel;

	//資料庫用
	private List<SalesData> salesDataList;
	private List<SalesData> infoDataList;
	private int countBookWithISBN;
	private String getCoursesWithISBN;
	private Sales sales = new Sales();
	
	//建立上方頁面
	GUI_UpperPanel gui_UpperPanel;
	
	//這個複雜商品頁面專屬的ISBN
	private String mainISBN;
	
	//使用者是否已登入
	private int userAccount = 0;
	private boolean loginSuccess;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetailedProductPage window = new DetailedProductPage("1234567890");
					window.detailedProductPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public DetailedProductPage(String isbn) throws IOException {//要輸入ISBN，才會產生相對應的複雜商品頁面
		this.mainISBN = isbn;
		initialize();
		/*createUpperPanel();*/
		creatProductPanel();
		createJTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		detailedProductPage = new JFrame();
		detailedProductPage.setType(Type.UTILITY);
		detailedProductPage.setBounds(100, 100, 1920, 1080);
		detailedProductPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
		/*cdetailedProductPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		detailedProductPage.getContentPane().setLayout(null);

		btnNewButton_1 = new JButton("收藏");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userAccount==0) {//未登入
					JOptionPane.showMessageDialog(detailedProductPage, "登入後才能收藏商品！", "尚未登入", JOptionPane.WARNING_MESSAGE);
				}else {
					addBookWishlist(userAccount);//將商品加進資料庫
				}
			}
		});
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(84, 185, 191));
		btnNewButton_1.setFont(new Font("微軟正黑體", Font.BOLD, 18));
		btnNewButton_1.setBounds(1278, 418, 89, 38);
		detailedProductPage.getContentPane().add(btnNewButton_1);
	}
	
	public void creatProductPanel() throws IOException {
		
		productIPanel = new JPanel();
		productIPanel.setBounds(141, 218, 674, 230);
		detailedProductPage.getContentPane().add(productIPanel);
		productIPanel.setLayout(null);
		
		productPic = new JPanel();
		productPic.setBounds(0, 0, 180, 230);
		productIPanel.add(productPic);
		productPic.setLayout(null);
		
		productPic_1 = new JLabel();
		productPic_1.setBounds(0, 0, 180, 230);
		productPic.add(productPic_1);
		productPic_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		productPic_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		productInfoPanel = new JPanel();
		productInfoPanel.setBounds(190, 73, 484, 147);
		productIPanel.add(productInfoPanel);
		productInfoPanel.setLayout(null);
		
		isbnLabel1 = new JLabel("ISBN：");
		isbnLabel1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		isbnLabel1.setBounds(0, 10, 68, 29);
		productInfoPanel.add(isbnLabel1);
		
		classLabel1 = new JLabel("使用課堂：");
		classLabel1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		classLabel1.setBounds(0, 58, 100, 29);
		productInfoPanel.add(classLabel1);
		
		numLabel1 = new JLabel("目前數量：");
		numLabel1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		numLabel1.setBounds(0, 108, 100, 29);
		productInfoPanel.add(numLabel1);
		
		numLabel2 = new JLabel();
		numLabel2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		numLabel2.setBounds(100, 108, 307, 29);
		productInfoPanel.add(numLabel2);
		
		classLabel2 = new JLabel();
		classLabel2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		classLabel2.setBounds(100, 58, 384, 29);
		productInfoPanel.add(classLabel2);
		
		isbnLabel2 = new JLabel();
		isbnLabel2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		isbnLabel2.setBounds(70, 10, 337, 29);
		productInfoPanel.add(isbnLabel2);
		
		productNameLabel = new JLabel();
		productNameLabel.setBounds(190, 14, 484, 49);
		productIPanel.add(productNameLabel);
		productNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		productNameLabel.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		
		panel = new JPanel();
		panel.setBounds(141, 170, 423, 38);
		detailedProductPage.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, 423, 38);
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		panel.add(lblNewLabel);
		
		String bookName = "",type = "";
		
		//統整過後的
		infoDataList = sales.findBookWithISBN(this.mainISBN); //parameter應該要放ISBN //這個isbn是要從簡單商品頁面拿的
        for(SalesData data : infoDataList) {
        	ImageIcon icon = new ImageIcon(data.getGraph());
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(productPic_1.getWidth(), productPic_1.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            productPic_1.setIcon(scaledIcon);
            
            bookName = data.getName();
            type = data.getCategory();
        }
        
        countBookWithISBN = sales.countBookWithISBN(this.mainISBN);//parameter應該要放ISBN
        getCoursesWithISBN = sales.getCoursesWithISBN(this.mainISBN); //parameter應該要放ISBN
        lblNewLabel.setText("全部>書籍>教科書>"+type);
		
        isbnLabel2.setText(this.mainISBN);
        productNameLabel.setText(bookName);
        numLabel2.setText(Integer.toString(countBookWithISBN));
		classLabel2.setText(getCoursesWithISBN);
		
	}
	
	public void createJTable() throws IOException {
		
		model = new DefaultTableModel() {
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class; // 第一列的資料類型為Boolean，其餘列為String
		    }
		    public boolean isCellEditable(int row, int column) {
		    	return column == 0; // 僅允許編輯第一列的勾選欄
		    }
		};
		String[]columnNames = {"勾選欄","商品圖片","價格","商品使用情況","商家聯絡資訊"};
		model.setColumnCount(columnNames.length);
		model.setColumnIdentifiers(columnNames);
		
        salesDataList = sales.findBookWithISBN(this.mainISBN); //parameter應該要放ISBN
        // turn the int condition into String.
        for(SalesData data : salesDataList) {
            model.addRow(new Object[]{
            		false, // 勾選欄的初始狀態為未選中
            		data.getGraph(),//圖片在第1行
            		data.getPrice(),
                    data.getBookCondition(), // 將使用狀況設定為BookCondition
                    data.getConnection()
            });
        }
        
        productTable = new JTable(model);
		productTable.setFont(new Font("微軟正黑體", Font.BOLD, 18));
		productTable.getTableHeader().setFont(new Font("微軟正黑體", Font.BOLD, 18));
        productTable.setForeground(new Color(0, 0, 0));
        productTable.getTableHeader().setReorderingAllowed(false);
        
        productTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
        productTable.setRowHeight(150);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // 遍历表格的除第一列的所有列，并将单元格渲染器设置为居中对齐
        for (int i = 2; i < model.getColumnCount(); i++) {
        	productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(141, 460, 1226, 325);
		scrollPane.setViewportView(productTable);
		detailedProductPage.getContentPane().add(scrollPane);
	}
	
	//把書籍加入BookWishlist
	public void addBookWishlist(int userAccount) {
		int[] selectedRows = productTable.getSelectedRows();

        if (selectedRows.length > 0) {
            int option = JOptionPane.showConfirmDialog(detailedProductPage, "確定要收藏該商品嗎?", "加入收藏", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) productTable.getModel();

                // 從最後一行開始
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int selectedRow = selectedRows[i];

                    //可以利用LineID去BookSales Array裡面找salerID
                    int salerID  = 0;//??
                    String connection = (String) model.getValueAt(selectedRow, 4);// 獲取選取行的資料
                    String isbn = isbnLabel2.getText();
                    
                    for(SalesData data : salesDataList) {
                        if(data.getConnection().equals(connection)) {
                        	salerID = data.getSalerID();
                        	break;
                        }
                    }
                    
                    //先確認是否重複加入收藏
                    if(sales.checkBookForBookWishlist(userAccount, isbn, salerID)==true) {//重複
                    	JOptionPane.showMessageDialog(detailedProductPage, "您已收藏過該商品！", "重複收藏", JOptionPane.WARNING_MESSAGE);
                    }else {//沒有重複
                    	// 創建 SalesData 物件
                        SalesData addData = new SalesData(salerID,userAccount,isbn);

                        // 呼叫 addBookWishlist() 方法新增資料庫中的資料
                        boolean success = ((Sales) sales).addBookWishlist(addData);
                        if (success==false) {
                            // 如果失敗
                        	JOptionPane.showMessageDialog(null, "商品無法加入收藏！","收藏失敗", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                        	JOptionPane.showMessageDialog(null, "商品成功加入收藏！","收藏成功", JOptionPane.INFORMATION_MESSAGE);
                        	productTable.setValueAt(false, selectedRow, 0);//收藏完畢就不能再勾選了
                            productTable.getColumnModel().getColumn(0).setCellEditor(null);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(detailedProductPage, "請先選取要加入收藏的商品！", "加入收藏", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	//處理表格圖片
	public class ImageRender extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent(JTable table, Object value, 
				boolean isSelected, boolean hasFocus, int row, int column) {
			
			if (value instanceof byte[]) {
	            byte[] bytes = (byte[]) value;

	            ImageIcon imageIcon = new ImageIcon(bytes);
	            Image image = imageIcon.getImage();
	            Image scaledImage = image.getScaledInstance(table.getRowHeight(), table.getRowHeight(), Image.SCALE_SMOOTH);
	            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
	            
	            setIcon(scaledImageIcon);
	            setHorizontalAlignment(JLabel.CENTER);
	        } else {
	            setText(value != null ? value.toString() : "");
	            setHorizontalAlignment(SwingConstants.CENTER);
	        }
	        
	        return this;
		}
	}
	
	//設定成已登入的upperPanel
	public void setUpperPanel(boolean loginSuccess, int userAccount) {
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.detailedProductPage,loginSuccess,userAccount);
		this.loginSuccess = true;
		this.userAccount = userAccount;
		
	}
	
	//設定成尚未登入的upperPanel
	public void setUpperPanel() {
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.detailedProductPage,false,0);
		this.loginSuccess = false;
		this.userAccount = 0;
		
	}
	
	public JFrame getFrame() {
	    return detailedProductPage;
	}
	
}
