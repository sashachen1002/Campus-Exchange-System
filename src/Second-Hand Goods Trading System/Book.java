package 期末報告_11102Programming_v2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.sql.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Book extends JFrame{

	private JFrame frame;
	private JTextField lessonText;
	private JTextField priceText;
	private JTextField IDText;
	private JTextField ISBNText;
	private JTextField nameText;
	private JTextField conditionText;
	private JComboBox comboBox ;
	private File file_path;
	private byte[] imageBytes;
	private Item item;
	private JPanel InfoPanel;
	private JLabel imageLabel;
	private JPanel buttonPanel;
	private JButton bookModelBtn;
	private JButton generalModelBtn;
	private JLabel lblNewLabel_3_1;
	private JButton btnNewButton_2_1;
	private JButton btnNewButton_2;
	
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
					Book window = new Book(108305091);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public Book(int userAccount) throws SQLException, IOException {
		this.userAccount = userAccount;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize()throws SQLException, IOException {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		/*frame.setExtendedState(JFrame.MAXIMIZED_BOTH);*/
		frame.setBounds(199, 190, 1091, 657);
		
		InfoPanel = new JPanel();
		InfoPanel.setLayout(null);
		InfoPanel.setBackground(new Color(240, 240, 240));
		InfoPanel.setBounds(0, 41, 1083, 585);
		frame.getContentPane().add(InfoPanel);
		
		lessonText = new JTextField();
		lessonText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		lessonText.setText("課程");
		lessonText.setColumns(10);
		lessonText.setBounds(438, 383, 174, 43);
		InfoPanel.add(lessonText);
		
		JLabel lblNewLabel = new JLabel("商品名稱");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel.setBounds(48, 54, 122, 21);
		InfoPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("使用狀況");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(48, 213, 114, 34);
		InfoPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("ISBN");
		lblNewLabel_3.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_3.setBounds(48, 301, 114, 30);
		InfoPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("聯絡資訊");
		lblNewLabel_4.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_4.setBounds(48, 482, 114, 43);
		InfoPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_1 = new JLabel("價格");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(48, 129, 122, 30);
		InfoPanel.add(lblNewLabel_1);
		
		priceText = new JTextField();
		priceText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		priceText.setColumns(10);
		priceText.setBounds(254, 132, 358, 40);
		InfoPanel.add(priceText);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"全部", "文學院", "理學院", "社科院", "法學院", "商學院", "外院", "傳院", "國際事務學院", "教育學院", "創新國際學院", "資訊學院", "國際金融學院"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(254, 383, 174, 43);
		InfoPanel.add(comboBox);
		
		btnNewButton_2 = new JButton("上傳書籍圖片");
		btnNewButton_2.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnNewButton_2) {
					JFileChooser file_upload=new JFileChooser();
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
							InfoPanel.add(imageLabel);
							InfoPanel.revalidate();
						    InfoPanel.repaint();
							
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
		InfoPanel.add(btnNewButton_2);
		
		
		 
		btnNewButton_2_1 = new JButton("完成上架");
		btnNewButton_2_1.setForeground(new Color(255, 255, 255));
		btnNewButton_2_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(ISBNText.getText().isEmpty()||nameText.getText().isEmpty()||imageBytes.length==0||priceText.getText().isEmpty()
						||conditionText.getText().isEmpty()||lessonText.getText().isEmpty()||IDText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "欄位不可空白", "錯誤", JOptionPane.ERROR_MESSAGE);
				}else {
					//統整後
					int studentID = userAccount;
					String isbn = ISBNText.getText();
					String name = nameText.getText();
					byte[] graph = imageBytes;
					int price = Integer.parseInt(priceText.getText());
					String condition = conditionText.getText();
					String type = (String)comboBox.getSelectedItem();
					String lesson = lessonText.getText();
					String lineID = IDText.getText();
					
				    // 確認 ISBN 長度為十碼
				    if (isbn.length() != 10) {
				        JOptionPane.showMessageDialog(null, "ISBN 必須為十碼", "錯誤", JOptionPane.ERROR_MESSAGE);
				        return; // 停止執行插入資料的程式碼
				    }
					
					SalesData uploadBook = new SalesData(studentID,isbn,name, graph, price,condition,type,lesson,lineID);
					sales.uploadProduct(uploadBook);
					
					//檢查上架是否成功
	                boolean success = ((Sales) sales).uploadBook(uploadBook);
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
		InfoPanel.add(btnNewButton_2_1);
		
		lblNewLabel_3_1 = new JLabel("使用學院/課程");
		lblNewLabel_3_1.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		lblNewLabel_3_1.setBounds(48, 385, 204, 43);
		InfoPanel.add(lblNewLabel_3_1);
		
		IDText = new JTextField();
		IDText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		IDText.setText("LINE ID");
		IDText.setColumns(10);
		IDText.setBounds(259, 487, 353, 38);
		InfoPanel.add(IDText);
		
		ISBNText = new JTextField();
		ISBNText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		ISBNText.setColumns(10);
		ISBNText.setBounds(254, 304, 358, 40);
		InfoPanel.add(ISBNText);
		
		nameText = new JTextField();
		nameText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		nameText.setColumns(10);
		nameText.setBounds(254, 52, 358, 40);
		InfoPanel.add(nameText);
		
		conditionText = new JTextField();
		conditionText.setFont(new Font("微軟正黑體", Font.PLAIN, 22));
		conditionText.setColumns(10);
		conditionText.setBounds(254, 218, 358, 40);
		InfoPanel.add(conditionText);
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		imageLabel.setBounds(762, 79, 243, 270);
		InfoPanel.add(imageLabel);
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 0, 285, 42);
		frame.getContentPane().add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		bookModelBtn = new JButton("書籍模板");
		buttonPanel.add(bookModelBtn);
		bookModelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		bookModelBtn.setBackground(new Color(230, 230, 230));
		
		generalModelBtn = new JButton("一般模板");
		buttonPanel.add(generalModelBtn);
		generalModelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//更換ㄧ般模板
				frame.dispose();
				try {
					item=new Item(userAccount);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				item.getFrame().setVisible(true);
				
			}
		});
		generalModelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		generalModelBtn.setForeground(Color.BLACK);
		generalModelBtn.setBackground(new Color(230, 230, 230));
	}
	public JFrame getFrame() {
	    return frame;
	}
	
	public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
