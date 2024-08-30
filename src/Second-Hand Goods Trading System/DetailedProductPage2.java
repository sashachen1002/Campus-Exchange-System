package 期末報告_11102Programming_v2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.util.List;
import java.awt.Image;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.io.IOException;

public class DetailedProductPage2 {//複雜商品頁面-其他商品
	//GUI
	private JFrame detailedProductPage2;
	private JButton btnNewButton_1;
	private JLabel productPic_1;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel conditionLabel;
	private JLabel conditionLabel2;
	private JLabel connectionLabel;
	private JLabel connectionLabel2;
	private JLabel nameLabel;
	private JLabel priceLabel;
	private JLabel priceLabel2;
	
	private String productName = "";
	private String type = "";
	private String productCondition = "";
	private String connection = "";
	private int price = 0;
	
	//資料庫用
	private List<SalesData> infoDataList;
	private Sales sales = new Sales();
	
	//建立上方頁面
	GUI_UpperPanel gui_UpperPanel;
	
	//這個複雜商品頁面專屬的SalesNum
	private int mainSalesNum;
	
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
					DetailedProductPage2 window = new DetailedProductPage2(3);
					window.detailedProductPage2.setVisible(true);
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
	public DetailedProductPage2(int mainSalesNum) throws IOException {
		this.mainSalesNum = mainSalesNum;
		initialize();
		creatProductPanel();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		detailedProductPage2 = new JFrame();
		detailedProductPage2.setType(Type.UTILITY);
		/*detailedProductPage2.setBounds(100, 100, 1920, 1080);*/
		detailedProductPage2.setExtendedState(JFrame.MAXIMIZED_BOTH);
		detailedProductPage2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		detailedProductPage2.getContentPane().setLayout(null);

		btnNewButton_1 = new JButton("收藏");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userAccount==0) {//未登入
					JOptionPane.showMessageDialog(detailedProductPage2, "登入後才能收藏商品！", "尚未登入", JOptionPane.WARNING_MESSAGE);
				}else {
					try {
						addOtherWishlist(userAccount);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//將商品加進資料庫
					btnNewButton_1.setEnabled(false);//收藏過後就不能再按按鈕
					btnNewButton_1.setText("已收藏");
					btnNewButton_1.setBackground(Color.GRAY);
				}
			}
		});
        
	}
	
	public void creatProductPanel() throws IOException {
		
		panel = new JPanel();
		panel.setBounds(141, 170, 423, 38);
		detailedProductPage2.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, 423, 38);
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		panel.add(lblNewLabel);
		
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(84, 185, 191));
		btnNewButton_1.setFont(new Font("微軟正黑體", Font.BOLD, 18));
		btnNewButton_1.setBounds(485, 448, 123, 38);
		detailedProductPage2.getContentPane().add(btnNewButton_1);
		
		productPic_1 = new JLabel();
        productPic_1.setBounds(141, 218, 334, 396);
        detailedProductPage2.getContentPane().add(productPic_1);
        productPic_1.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        productPic_1.setHorizontalAlignment(SwingConstants.CENTER);
        
        nameLabel = new JLabel("商品名稱：");
        nameLabel.setFont(new Font("微軟正黑體", Font.BOLD, 30));
        nameLabel.setBounds(485, 218, 320, 39);
        detailedProductPage2.getContentPane().add(nameLabel);
        
        priceLabel = new JLabel("價格：");
        priceLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        priceLabel.setBounds(485, 283, 66, 29);
        detailedProductPage2.getContentPane().add(priceLabel);
        
        priceLabel2 = new JLabel();
        priceLabel2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        priceLabel2.setBounds(548, 283, 307, 29);
        detailedProductPage2.getContentPane().add(priceLabel2);
        
        conditionLabel = new JLabel("使用狀況：");
        conditionLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        conditionLabel.setBounds(485, 327, 100, 29);
        detailedProductPage2.getContentPane().add(conditionLabel);
        
        conditionLabel2 = new JLabel();
        conditionLabel2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        conditionLabel2.setBounds(584, 327, 307, 29);
        detailedProductPage2.getContentPane().add(conditionLabel2);
        
        connectionLabel = new JLabel("賣家聯絡資訊：");
        connectionLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        connectionLabel.setBounds(485, 374, 140, 29);
        detailedProductPage2.getContentPane().add(connectionLabel);
        
        connectionLabel2 = new JLabel();
        connectionLabel2.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        connectionLabel2.setBounds(627, 374, 307, 29);
        detailedProductPage2.getContentPane().add(connectionLabel2);

		//統整過後的
		infoDataList = sales.findProductWithSalesNum(mainSalesNum); //parameter應該要放SalesNum //這個SalesNum是要從簡單商品頁面拿的
        for(SalesData data : infoDataList) {

            ImageIcon icon = new ImageIcon(data.getGraph());
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(productPic_1.getWidth(), productPic_1.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            productPic_1.setIcon(scaledIcon);
            
            productName = data.getName();
            type = data.getCategory();
            productCondition = data.getProductCondition();
            connection = data.getConnection();
            price = data.getPrice();
        }
        nameLabel.setText(productName);
        conditionLabel2.setText(productCondition);
        connectionLabel2.setText(connection);
        priceLabel2.setText(price+"");
        lblNewLabel.setText("其他>"+type);
	}
	
	//把商品加入OtherWishlist
	public void addOtherWishlist(int userAccount) throws IOException {
		
		//可以利用SalesNum去BookSales Array裡面找salerID
        /*int salerID  = 0;//??
        
        infoDataList = sales.findProductWithSalesNum(mainSalesNum);//有錯誤!!//還是沒有找到SalerID(=0)
        for(SalesData data : infoDataList) {
            if(data.getSalesNum()==mainSalesNum) {
            	salerID = data.getSalerID();
            	break;
            }
        }*/
		
		int salerID  = 0;
        
        infoDataList = sales.findProductWithSalesNum(mainSalesNum);//有錯誤!!//還是沒有找到SalerID(=0)
        for(SalesData data : infoDataList) {
            salerID = data.getSalerID();
            break;
        }
        
        //先確認是否重複加入收藏
        if(sales.checkOtherForOtherWishlist(userAccount, mainSalesNum, salerID)==true) {//重複
        	JOptionPane.showMessageDialog(null, "您已收藏過該商品！", "重複收藏", JOptionPane.WARNING_MESSAGE);
        }else {
        	// 創建 SalesData 物件
            SalesData addData = new SalesData(salerID,userAccount, mainSalesNum);

            // 呼叫 addBookWishlist() 方法新增資料庫中的資料
            boolean success = ((Sales) sales).addOtherWishlist(addData);
            if (success==false) {
                // 如果失敗
            	JOptionPane.showMessageDialog(null, "商品無法加入收藏！","收藏失敗", JOptionPane.INFORMATION_MESSAGE);
            }else{
            	JOptionPane.showMessageDialog(null, "商品成功加入收藏！","收藏成功", JOptionPane.INFORMATION_MESSAGE);
            }
        }
	}
	
	//設定成已登入的upperPanel
	public void setUpperPanel(boolean loginSuccess, int userAccount) {
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.detailedProductPage2,loginSuccess, userAccount);
		this.loginSuccess = true;
		this.userAccount = userAccount;
		
	}
	
	//設定成尚未登入的upperPanel
	public void setUpperPanel() {
		
		this.gui_UpperPanel = new GUI_UpperPanel(this.detailedProductPage2,false,0);
		this.loginSuccess = false;
		this.userAccount = 0;
	}

	public JFrame getFrame() {
	    return detailedProductPage2;
	}
}
