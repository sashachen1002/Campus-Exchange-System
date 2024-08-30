package 期末報告_11102Programming_v2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Image;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Canvas;
import javax.swing.JPanel;
import java.awt.Label;
import java.awt.Button;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.ScrollPane;
import javax.swing.JCheckBox;

public class GUI_Membership extends JFrame{
	
	// 建立表格
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	
	//建立模板物件
	private Book book;
	private Item item;
	
	//擷取使用者的ID
	private int userAccount = 0;
	private boolean loginSuccess;
	
	//建立上方頁面
	private GUI_UpperPanel gui_UpperPanel;
	
	// 連結不同class的資料
	private Sales sales = new Sales();
	private List<SalesData> BookDataList;
	private List<SalesData> ProductDataList;
	
	// 用來切換頁面
	private JComboBox<String> comboBox;
	
	// 調整商品狀態至字串
	private String condition = "";
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Membership window = new GUI_Membership();//測試
					window.setUpperPanel(true, 111306018);
					window.run();
					window.frame.setVisible(true);
					System.out.println("第一個"+window.getUserAccount());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	public GUI_Membership() {
		
		createFrame();
	}
	
	//設定成已登入的upperPanel
	public void setUpperPanel(boolean loginSuccess, int userAccount) {
		
		this.loginSuccess = loginSuccess;
		this.userAccount = userAccount;
		this.gui_UpperPanel = new GUI_UpperPanel(this.frame,loginSuccess,userAccount);
		
	}
	
	public void run() {
		createJLabel();
		createJButton();
		createJCombo();
		createJTable();
	}
	
	// Create the frame.

	public void createFrame() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setTitle("會員介面-賣家");
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		/*frame.setBounds(100, 100, 1920, 1080);*/
		/*frame.setBounds(100, 100, 1600, 800);*/
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void createJLabel() {
		
		JLabel browser = new JLabel("瀏覽介面：");
		browser.setBounds(20, 177, 99, 39);
		browser.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		frame.getContentPane().add(browser);
		
	}
	
	public void createJButton() {
		// 連結至一般商品模板介面
		JButton generalPost = new JButton("刊登商品");
		generalPost.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					item = new Item(userAccount);
					item.getFrame().setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		generalPost.setBounds(488, 176, 118, 40);
		generalPost.setForeground(new Color(255, 255, 255));
		generalPost.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		generalPost.setBackground(new Color(84, 185, 191));
		frame.getContentPane().add(generalPost);
		
		
		// 連結至書籍模板介面
		JButton bookPost = new JButton("刊登書籍");
		bookPost.setBounds(616, 176, 118, 40);
		bookPost.setForeground(new Color(255, 255, 255));
		bookPost.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		bookPost.setBackground(new Color(255, 137, 29));
		bookPost.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					book = new Book(userAccount);
					book.getFrame().setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		frame.getContentPane().add(bookPost);
		stop();
		remove();
	}
	
	public void stop() {
		JButton stop = new JButton("暫停/取消暫停");
		stop.setBounds(270, 184, 120, 31);
		stop.setForeground(Color.WHITE);
		stop.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		stop.setBackground(new Color(121, 133, 135));
		frame.getContentPane().add(stop);
		
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				stopSelectedData();
			}
		});
	}
	
	public void stopSelectedData() {
		//首先要取得最後一欄的值(知道目前是暫停還是還沒)
		//暫停:如果最後一欄的值是"未售出"(還沒按暫停)(也還沒按下架/下架會直接刪除)那就是要更新資料庫的TCondition為"暫停上架"
		//取消暫停:如果最後一欄的值是"暫停上架"(已按暫停)(也還沒按下架/下架會直接刪除)那就是要更新資料庫的TCondition為"未售出"
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();

	    for (int i = 0; i < rowCount; i++) {
	        Boolean checked = (Boolean) model.getValueAt(i, 0); 
	        if (checked != null && checked) {
	        	Object productNum = model.getValueAt(i, 1);//商品編號
	        	String condition = (String) model.getValueAt(i, 5);//商品編號
	        	String changedCondition = "";
	        	
	        	//單純更改表格欄位
	        	if(condition.equals("未售出")) {
	        		changedCondition = "暫停上架";
	        		model.setValueAt("暫停上架", i, 5); // 假設要修改的數值在第二欄 //更改欄位
	        		model.setValueAt(false, i, 0);//收藏完畢就不能再勾選了
	        	}else if(condition.equals("暫停上架")) {
	        		changedCondition = "未售出";
	        		model.setValueAt("未售出", i, 5); // 假設要修改的數值在第二欄 //更改欄位
	        		model.setValueAt(false, i, 0);//收藏完畢就不能再勾選了
	        	}
	        	
	        	//記得更新資料庫  
        		if(productNum instanceof String) {//Book的參數是"ISBN"+"userAccount/SalerID"
        			sales.pauseBookSales(productNum.toString(),this.userAccount,changedCondition);
        		}else if(productNum instanceof Integer) {//Other的參數是"SalesNum"+"userAccount/SalerID"
        			sales.pauseOtherSales(productNum.toString(),this.userAccount,changedCondition);
        		}
	        }
	    }	
	}
	
	public void remove() {
		JButton remove = new JButton("下架");
		remove.setBounds(400, 184, 58, 31);
		remove.setForeground(Color.WHITE);
		remove.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		remove.setBackground(new Color(255, 137, 29));
		frame.getContentPane().add(remove);
		
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				removeSelectedData();
			}
		});
	}
	
	public void removeSelectedData() {
		int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length > 0) {
            int option = JOptionPane.showConfirmDialog(frame, "確定要下架此商品嗎？", "下架商品", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // 從最後一行開始刪除，以避免索引錯誤
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int selectedRow = selectedRows[i];
                    
                    //修改
                    //先從欄位取得ISBN跟登入者帳號
                    //用這兩筆資料找同一筆交易
                    String productNum = model.getValueAt(selectedRow, 1).toString();
                    boolean success = false;
                    
                 // 創建 SalesData 物件
                    if(productNum.length()==10) {
                    	BookDataList = sales.findBookWithSalerID(this.userAccount);
                        for(SalesData data : BookDataList) {
                            if(data.getISBN().equals(productNum)) {
                            	// 創建 SalesData 物件
                                SalesData removedData = new SalesData(this.userAccount, productNum);

                                // 從表格中刪除行
                                model.removeRow(selectedRow);

                                // 呼叫 removeData() 方法刪除資料庫中的資料
                                success = ((Sales) sales).removeDataFromBookSales(removedData);
                            }
                        }
                    }else {
                    	ProductDataList = sales.findOtherWithSalerID(this.userAccount);
                        for(SalesData data : ProductDataList) {
                            if(data.getSalesNum()==Integer.parseInt(productNum)) {
                            	 // 創建 SalesData 物件
                                SalesData removedData = new SalesData(this.userAccount, Integer.parseInt(productNum));

                                // 從表格中刪除行
                                model.removeRow(selectedRow);

                                // 呼叫 removeData() 方法刪除資料庫中的資料
                               success = ((Sales) sales).removeDataFromOtherSales(removedData);
                            } 
                        }
                    }
                    if (!success) {
                        // 處理刪除資料庫資料失敗的情況
                    	JOptionPane.showMessageDialog(null, "此商品無法下架！","Wrong", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "請先選取要下架的商品！", "下架商品", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	public void switchToGUI_Membership2() {
	    frame.dispose();
	    GUI_Membership2 window2 = new GUI_Membership2();
	    window2.setUpperPanel(true,userAccount);
	    window2.run();
	    window2.getFrame().setVisible(true);
	}
	
	public void createJCombo() {
		comboBox = new JComboBox<String>();
		comboBox.setBounds(116, 186, 140, 31);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"我的販賣清單", "我的收藏清單"}));
		frame.getContentPane().add(comboBox);
		
		 // 設定事件處理程式
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String selectedOption = (String) comboBox.getSelectedItem();
                if (selectedOption.equals("我的販賣清單")) {
                    // 停留在賣家介面並跳出錯誤訊息
                    JOptionPane.showMessageDialog(frame, "此介面即為販賣清單！", "賣家介面", JOptionPane.WARNING_MESSAGE);
                } else if (selectedOption.equals("我的收藏清單")) {
                    // 執行介面轉換到買家介面的程式碼
                	switchToGUI_Membership2();
                }				
			}
		});
	}
		
	public void createJTable() {
		model = new DefaultTableModel() {
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class; // 第一列的資料類型為Boolean，其餘列為String*/
		    }
		    public boolean isCellEditable(int row, int column) {
		    	/*return true;*/
		    	return column == 0; // 僅允許編輯第一列的勾選欄
		    }
		};
		String[]columnNames = {"勾選欄","ISBN/商品編號","商品名稱","價格","商品使用情況","商品上架狀態"};//移除圖片
		model.setColumnCount(columnNames.length);//新增的
		model.setColumnIdentifiers(columnNames);
        
        BookDataList = sales.findBookWithSalerID(this.userAccount);
        for(SalesData data : BookDataList) {
            model.addRow(new Object[]{
            		false, // 勾選欄的初始狀態為未選中
            		data.getISBN(),
            		/*data.getGraph(),//圖片在第2行*/
            		data.getName(),
            		data.getPrice(),
            		data.getBookCondition(),
            		data.getCondition()
            });
        }
        
        ProductDataList = sales.findOtherWithSalerID(this.userAccount);
        for(SalesData data : ProductDataList) {
            model.addRow(new Object[]{
            		false, // 勾選欄的初始狀態為未選中
            		data.getSalesNum(),
            		/*data.getGraph(),*/
            		data.getName(),
            		data.getPrice(),
            		data.getProductCondition(),
            		data.getCondition()
            });
        }
        
        table = new JTable(model);
        table.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
        table.setForeground(new Color(0, 0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("微軟正黑體", Font.BOLD, 18));
        
        /*table.getColumnModel().getColumn(2).setCellRenderer(new ImageRender());*/
        table.setRowHeight(150);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // 遍历表格的除第一列的所有列，并将单元格渲染器设置为居中对齐
        for (int i = 1; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(170, 237, 1189, 536);
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane);
	}
	
	/*public class ImageRender extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent(JTable table, Object value, 
				boolean isSelected, boolean hasFocus, int row, int column) {
			
			if (value instanceof byte[]) {
	            byte[] bytes = (byte[]) value;

	            ImageIcon imageIcon = new ImageIcon(bytes);
	            Image image = imageIcon.getImage();
	            Image scaledImage = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
	            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
	            
	            setIcon(scaledImageIcon);
	            setHorizontalAlignment(JLabel.CENTER);
	        } else {
	            setText(value != null ? value.toString() : "");
	            setHorizontalAlignment(SwingConstants.CENTER);
	        }
	        
	        return this;
		}
	}*/
	
	public JFrame getFrame() {
	    return frame;
	}
	
	public int getUserAccount() {//測試
		return userAccount;
	}

}
