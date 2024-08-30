package 期末報告_11102Programming_v2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

public class GUI_Membership2 {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private List<SalesData> salesDataList;
	private List<SalesData> DataListForBookWishlist;
	private List<SalesData> DataListForOtherWishlist;
	private DefaultTableModel model;
	private String condition = "";
	
	// 連結不同class的資料
	private Sales sales = new Sales();
	
	//擷取使用者的ID
	private int userAccount;
	private boolean loginSuccess;
	
	//建立上方頁面
	GUI_UpperPanel gui_UpperPanel;
	
	// 用來切換頁面
	public JComboBox<String> comboBox;
	
	public GUI_Membership2() {
		createFrame();
		createJLabel();
	}
	
	public void run() {
		
		remove();
		createJCombo();
		createJTable();
		
	}
	
	// Create the frame.
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Membership2 window = new GUI_Membership2();//測試
					window.setUpperPanel(true, 111306018);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public void createFrame() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setTitle("會員介面-買家");
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1600, 800);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	public void createJLabel() {
		JLabel browser = new JLabel("瀏覽介面：");
		browser.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		browser.setBounds(20, 177, 99, 39);
		frame.getContentPane().add(browser);
		
		JLabel infoLabel = new JLabel("未售出:商品仍在架上 / 已售出:商品已下架 / 暫停上架: 賣家暫停上架該商品，日後可能會恢復上架");
		infoLabel.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		infoLabel.setBounds(781, 206, 625, 15);
		frame.getContentPane().add(infoLabel);
	}
	
	public void switchToGUI_Membership() {
	    frame.dispose();
	    GUI_Membership window = new GUI_Membership();
	    window.setUpperPanel(true,userAccount);
	    window.run();
	    window.getFrame().setVisible(true);
	}
	
	public void createJCombo() {
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"我的販賣清單", "我的收藏清單"}));
		comboBox.setBounds(116, 186, 140, 31);
		frame.getContentPane().add(comboBox);
		
		 // 設定事件處理程式
		comboBox.addActionListener(new ActionListener() {
           
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String selectedOption = (String) comboBox.getSelectedItem();
                if (selectedOption.equals("我的販賣清單")) {
                    // 轉換到賣家介面
                	switchToGUI_Membership();
                } else if (selectedOption.equals("我的收藏清單")) {
                    // 執行介面轉換到買家介面的程式碼
                    JOptionPane.showMessageDialog(frame, "此介面即為收藏清單！", "買家介面", JOptionPane.WARNING_MESSAGE);
                   
                }
				
			}
        });
	}
	
	public void remove() {
		JButton remove = new JButton("移出我的收藏");
		remove.setForeground(new Color(255, 255, 255));
		remove.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		remove.setBackground(new Color(84, 185, 191));
		remove.setBounds(266, 188, 119, 23);
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
            int option = JOptionPane.showConfirmDialog(frame, "確定要將商品移出收藏嗎？", "移出收藏", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // 從最後一行開始刪除，以避免索引錯誤
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int selectedRow = selectedRows[i];
                    
                    // 獲取選取行的資料//已修改
                    //先透過isbn, 登入者ID跟販售者的ID(用LineID找)去BookWishlist找這筆資料
                    int salerID = 0;
                    boolean success = false;
                    Object productNum = model.getValueAt(selectedRow, 1);
                    
                    if(productNum instanceof String) {   
                        salesDataList = sales.findBookForBookWishlist(this.userAccount);
                        for(SalesData data : salesDataList) {
                            salerID = data.getSalerID();
                            break;
                        }
                        // 創建 SalesData 物件
                        SalesData removedData = new SalesData(salerID, this.userAccount, productNum.toString());
                        // 呼叫 removeData() 方法刪除資料庫中的資料
                        success = ((Sales) sales).removeDataFromBookWishlist(removedData);
                        // 從表格中刪除行
                        model.removeRow(selectedRow); 
                    }else if(productNum instanceof Integer){
                    	salesDataList = sales.findProductForOtherWishlist(this.userAccount);
                        for(SalesData data : salesDataList) {
                            salerID = data.getSalerID();
                            break;
                        }
                        // 創建 SalesData 物件
                        SalesData removedData = new SalesData(salerID, this.userAccount, productNum.toString());
                        // 呼叫 removeData() 方法刪除資料庫中的資料
                        success = ((Sales) sales).removeDataFromOtherWishlist(removedData);
                        // 從表格中刪除行
                        model.removeRow(selectedRow);
                    }
                    if (success==false) {
                        // 處理刪除資料庫資料失敗的情況
                    	JOptionPane.showMessageDialog(null, "此商品無法移出收藏！","Wrong", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "請先選取要移出收藏的商品！", "移出收藏", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	public void createJTable() {
		model = new DefaultTableModel() {
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class; // 第一列的資料類型為Boolean，其餘列為String
		    }
		    public boolean isCellEditable(int row, int column) {
		    	return column == 0; // 僅允許編輯第一列的勾選欄
		    }
		};
		String[]columnNames = {"勾選欄","ISBN/商品編號","商品名稱","價格","商品使用情況","商品上架情況","商家聯絡資訊"};//移除圖片
        model.setColumnIdentifiers(columnNames);
		
        DataListForBookWishlist = sales.findBookForBookWishlist(this.userAccount);
        for(SalesData data : DataListForBookWishlist) {
            model.addRow(new Object[]{
            		false, // 勾選欄的初始狀態為未選中
            		data.getISBN(),
            		/*data.getGraph(),*/
            		data.getName(),
            		data.getPrice(),
            		data.getBookCondition(),
            		data.getCondition(),
            		data.getConnection()	
            });
        }
        
        DataListForOtherWishlist = sales.findProductForOtherWishlist(this.userAccount);
        for(SalesData data : DataListForOtherWishlist) {
            model.addRow(new Object[]{
            		false, // 勾選欄的初始狀態為未選中
            		data.getSalesNum(),
            		/*data.getGraph(),*/
            		data.getName(),
            		data.getPrice(),
            		data.getProductCondition(),
            		data.getCondition(),
            		data.getConnection()
            });
        }
        
        table = new JTable(model);
        table.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
        table.setForeground(new Color(0, 0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("微軟正黑體", Font.BOLD, 18));
        /*table.getColumnModel().getColumn(2).setCellRenderer(new ImageRender());*/
        table.setRowHeight(70);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // 遍历表格的除第一列的所有列，并将单元格渲染器设置为居中对齐
        for (int i = 1; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        scrollPane = new JScrollPane();
		scrollPane.setBounds(110, 237, 1300, 536);
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
	
	public void setUpperPanel(boolean loginSuccess, int userAccount) {//設定成已登入的upperPanel
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.frame,loginSuccess,userAccount);
		this.loginSuccess = true;
		this.userAccount = userAccount;
		
	}
	
	public JFrame getFrame() {
	    return frame;
	}
}
