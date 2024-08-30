package 期末報告_11102Programming_v2;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.sql.*;
import java.util.List;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;

public class Item extends JFrame{

	private JFrame frame;
	private JTextField priceText;
	private JTextField IDText;
	private JTextField nameText;
	private JTextField conditionText;
	private Book book;
	private File file_path;
	private byte[] imageBytes;
	private JPanel infoPanel;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_1;
	private JComboBox comboBox;
	private JButton btnNewButton_2;
	private JButton btnNewButton_2_1;
	private JLabel lblNewLabel_3_1;
	private JLabel imageLabel;
	private JPanel buttonPanel;
	private JButton generalModelBtn;
	private JButton bookModelBtn;
	
	//連結資料庫跟SQL
	private List<SalesData> salesDataList;
	private Sales sales = new Sales();
	
	//擷取使用者的ID
	private int userAccount;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Item window = new Item();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public Item(int userAccount) throws SQLException, IOException {
		this.userAccount = userAccount;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()throws SQLException, IOException  {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		/*frame.setExtendedState(JFrame.MAXIMIZED_BOTH);*/
		frame.setBounds(199, 190, 1091, 657);
		
		infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBackground(new Color(240, 240, 240));
		infoPanel.setBounds(0, 41, 1083, 585);
		frame.getContentPane().add(infoPanel);
		
		lblNewLabel = new JLabel("商品名稱");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel.setBounds(48, 69, 122, 21);
		infoPanel.add(lblNewLabel);
		
		lblNewLabel_2 = new JLabel("使用狀況");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(48, 258, 114, 34);
		infoPanel.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("聯絡資訊");
		lblNewLabel_4.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_4.setBounds(48, 473, 114, 43);
		infoPanel.add(lblNewLabel_4);
		
		lblNewLabel_1 = new JLabel("價格");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(48, 159, 122, 30);
		infoPanel.add(lblNewLabel_1);
		
		priceText = new JTextField();
		priceText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		priceText.setColumns(10);
		priceText.setBounds(254, 159, 358, 40);
		infoPanel.add(priceText);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"服飾", "生活用品", "電子產品", "其他"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(254, 361, 122, 43);
		infoPanel.add(comboBox);
		
		btnNewButton_2 = new JButton("上傳圖片");
		btnNewButton_2.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnNewButton_2) {
					JFileChooser file_upload=new JFileChooser();
					// Create a file filter that only allows image files
				    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
				    file_upload.setFileFilter(filter);

					int res=file_upload.showSaveDialog(null);

					if(res==JFileChooser.APPROVE_OPTION) {
						file_path=new File(file_upload.getSelectedFile().getAbsolutePath());
					    try {
							imageBytes = Files.readAllBytes(file_path.toPath());
							ImageIcon icon = new ImageIcon(imageBytes);
							Image scaledImage = icon.getImage().getScaledInstance(243, 270, Image.SCALE_SMOOTH);
							JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
							imageLabel.setBounds(762, 79, 243, 270);
							infoPanel.add(imageLabel);
							infoPanel.revalidate();
						    infoPanel.repaint();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		});
	
		btnNewButton_2.setBackground(new Color(240, 240, 240));
		btnNewButton_2.setBounds(803, 384, 167, 43);
		infoPanel.add(btnNewButton_2);
		
		btnNewButton_2_1 = new JButton("完成上架");
		btnNewButton_2_1.setForeground(new Color(255, 255, 255));
		btnNewButton_2_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(nameText.getText().isEmpty()||imageBytes.length==0||priceText.getText().isEmpty()||conditionText.getText().isEmpty()
						||IDText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "欄位不可空白", "錯誤", JOptionPane.ERROR_MESSAGE);
				}else {
					//統整後
					int studentID = userAccount; 
					String name = nameText.getText();
					byte[] graph = imageBytes;
					int price = Integer.parseInt(priceText.getText());
					String condition = conditionText.getText();
					String type = (String)comboBox.getSelectedItem();
					String lineID = IDText.getText();
					
					SalesData uploadProduct = new SalesData(studentID, name, graph, price, condition, type, lineID);
					sales.uploadProduct(uploadProduct);
					
					//檢查上架是否成功
	                boolean success = ((Sales) sales).uploadProduct(uploadProduct);
	                if (!success) {
	                	JOptionPane.showMessageDialog(null, "請輸入完整訊息", "失敗", JOptionPane.ERROR_MESSAGE);
	                }else {
	                	JOptionPane.showMessageDialog(null, "上架成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
	                	frame.dispose();
	                }
				}
			}
		});
		btnNewButton_2_1.setBackground(new Color(64, 73, 105));
		btnNewButton_2_1.setBounds(951, 525, 122, 50);
		infoPanel.add(btnNewButton_2_1);
		
		lblNewLabel_3_1 = new JLabel("商品種類");
		lblNewLabel_3_1.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_3_1.setBounds(48, 361, 114, 43);
		infoPanel.add(lblNewLabel_3_1);
		
		IDText = new JTextField();
		IDText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		IDText.setText("LINE ID");
		IDText.setColumns(10);
		IDText.setBounds(259, 473, 353, 38);
		infoPanel.add(IDText);
		
		nameText = new JTextField();
		nameText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		nameText.setColumns(10);
		nameText.setBounds(254, 69, 358, 40);
		infoPanel.add(nameText);
		
		conditionText = new JTextField();
		conditionText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		conditionText.setColumns(10);
		conditionText.setBounds(254, 258, 358, 40);
		infoPanel.add(conditionText);
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		imageLabel.setBounds(762, 79, 243, 270);
		infoPanel.add(imageLabel);
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 0, 285, 42);
		frame.getContentPane().add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1,2));
		
		generalModelBtn = new JButton("一般模板");
		buttonPanel.add(generalModelBtn);
		generalModelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		generalModelBtn.setForeground(Color.BLACK);
		generalModelBtn.setBackground(new Color(230, 230, 230));
		
		
		bookModelBtn = new JButton("書籍模板");
		buttonPanel.add(bookModelBtn);
		bookModelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//更換書籍模板
				frame.dispose();

				try {
					book=new Book(userAccount);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				book.getFrame().setVisible(true);
			}
		});
		bookModelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		bookModelBtn.setBackground(new Color(230, 230, 230));
	}
	public JFrame getFrame() {
	    return frame;
	}
}
