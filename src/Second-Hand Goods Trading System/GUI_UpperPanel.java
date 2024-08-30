package 期末報告_11102Programming_v2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUI_UpperPanel {
	
	private JPanel upperPanel;
	private JPanel searchingPanel;
	private JTextField searchingTextField;
	private JButton btnNewButton;
	private JPanel membershipPanel;
	private JLabel registrationLabel;
	private JLabel systemNameLabel;
	private JLabel loginLabel;
	private JComboBox comboBox;
	private String selectedValue;
	
	//建立各種頁面
	private UserLogin userLogin;
	private UserSubmit userSubmit;
	private WindowDemo homepage;
	private GUI_Membership gui_Membership;
	
	private int userAccount = 0;
	private boolean loginSuccess = false;
	
	public GUI_UpperPanel(JFrame frame, boolean loginSuccess, int userAccount) {//要先偵測到底有沒有登入
		
		this.loginSuccess = loginSuccess;
		this.userAccount = userAccount;
		
		createUpperPanel(frame);
		
		if(loginSuccess ==true) {
			createLoginStatus(frame);
		}else {
			createLogoutStatus(frame);
		}
		
	}
	
	public void createUpperPanel(JFrame frame) {
		
		upperPanel = new JPanel();
		upperPanel.setBackground(new Color(64, 73, 105));
		upperPanel.setForeground(new Color(255, 255, 255));
		upperPanel.setBounds(0, 0, 1920, 160);
		upperPanel.setLayout(null);
		frame.getContentPane().add(upperPanel);
		
		searchingPanel = new JPanel();
		searchingPanel.setBounds(453, 65, 764, 41);
		upperPanel.add(searchingPanel);
		searchingPanel.setLayout(null);
		
		searchingTextField = new JTextField();
		searchingTextField.setBounds(91, 0, 581, 41);
		searchingPanel.add(searchingTextField);
		searchingTextField.setFont(new Font("微軟正黑體", Font.BOLD, 18));
		searchingTextField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(143, 143, 143));
		comboBox.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"書籍", "其他"}));
		comboBox.setBounds(0, 0, 93, 41);
		searchingPanel.add(comboBox);
		
		btnNewButton = new JButton("搜尋");
		frame.getContentPane().add(upperPanel);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//按下搜尋的時候 要用下拉式選單的值跟搜尋欄的字去創建首頁
				selectedValue = (String) comboBox.getSelectedItem();//取得下拉式選單的值
				homepage = new WindowDemo(searchingTextField.getText(),selectedValue);
				homepage.setUpperPanel(loginSuccess, userAccount);
				if(homepage.getSearchingNull()==null) {//有bug但可以待會處理
					JOptionPane.showMessageDialog(null, "目前無人販賣此商品","查無此商品", JOptionPane.INFORMATION_MESSAGE);
				}else {
					frame.dispose();
					homepage.getFrame().setVisible(true);
				}
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(143, 143, 143));
		btnNewButton.setOpaque(true);
		btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnNewButton.setBounds(671, 0, 93, 41);
		searchingPanel.add(btnNewButton);
		
		membershipPanel = new JPanel();
		membershipPanel.setBounds(1294, 52, 206, 61);
		upperPanel.add(membershipPanel);
		membershipPanel.setLayout(null);
		
		registrationLabel = new JLabel("註冊");
		registrationLabel.setBounds(0, 0, 126, 61);
		membershipPanel.add(registrationLabel);
		registrationLabel.setForeground(new Color(255, 255, 255));
		registrationLabel.setOpaque(true);
		registrationLabel.setBackground(new Color(64, 73, 105));
		registrationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registrationLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		loginLabel = new JLabel("登入");
		loginLabel.setBounds(125, 0, 81, 61);
		membershipPanel.add(loginLabel);
		loginLabel.setForeground(new Color(255, 255, 255));
		loginLabel.setOpaque(true);
		loginLabel.setBackground(new Color(64, 73, 105));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 25));
		
		systemNameLabel = new JLabel("政大二手物交流平台");
		systemNameLabel.setForeground(new Color(255, 255, 255));
		systemNameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				homepage = new WindowDemo();
				homepage.setUpperPanel(loginSuccess, userAccount);
				homepage.getFrame().setVisible(true);
			}
		});
		systemNameLabel.setBounds(26, 52, 385, 61);
		upperPanel.add(systemNameLabel);
		systemNameLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 40));
		systemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	public void createLoginStatus(JFrame frame) {//已登入的狀態
		
		loginLabel.setText("登出");
		loginLabel.addMouseListener(new MouseAdapter() {//這個Label可以同時為"登入"跟"登出"
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "確定要登出嗎？","即將登出", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
				userAccount = 0;//將使用者帳號重設為0
				homepage = new WindowDemo();//跳回尚未登入的首頁
				homepage.setUpperPanel();
				homepage.getFrame().setVisible(true);
			}
		});
		
		registrationLabel.setText("會員中心");
		registrationLabel.addMouseListener(new MouseAdapter() {//這個Label可以同時為"註冊"跟"會員中心"
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				gui_Membership = new GUI_Membership();//跳入會員中心//假設login那邊就會先有使用者帳號
				gui_Membership.setUpperPanel(loginSuccess,userAccount);
				gui_Membership.run();
				gui_Membership.getFrame().setVisible(true);
			}
		});
		
	}
	
	public void createLogoutStatus(JFrame frame) {//未登入的狀態
		
		loginLabel.setText("登入");
		loginLabel.addMouseListener(new MouseAdapter() {//這個Label可以同時為"登入"跟"登出"
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				userLogin = new UserLogin();
				userLogin.getFrame().setVisible(true);
			}
		});
		
		registrationLabel.setText("註冊");
		registrationLabel.addMouseListener(new MouseAdapter() {//這個Label可以同時為"註冊"跟"會員中心"
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				userSubmit = new UserSubmit();
				userSubmit.getFrame().setVisible(true);
			}
		});
		
	}
}
