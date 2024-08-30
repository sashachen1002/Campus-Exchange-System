package 期末報告_11102Programming_v2;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import javax.mail.*;  
import javax.mail.internet.*;
import java.awt.Color; 


public class UserLogin {

	private JFrame loginFrame;
	private JTextField accountTextField;
	private JPasswordField pwTextField;
	
	private User user;
	
	private List<SalesData> loginList;
	private Sales sales = new Sales();
	
	private boolean loginSuccess = false;
	
	private WindowDemo homepage;
	
	
	JFrame getLoginFrame() {
		return this.loginFrame;
	}
	
	/**
	 * Create the application.
	 */
	public UserLogin() {
		initialize();
		loginFrame.setDefaultCloseOperation(loginFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loginFrame = new JFrame();
		loginFrame.setBounds(100, 100, 450, 300);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(null);
		JLabel userLoginLabel = new JLabel("使用者登入");
		userLoginLabel.setForeground(new Color(0, 0, 0));
		userLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLoginLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		userLoginLabel.setBounds(163, 37, 102, 33);
		loginFrame.getContentPane().add(userLoginLabel);
		
		JLabel accountLabel = new JLabel("帳號");
		accountLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		accountLabel.setBounds(58, 95, 47, 22);
		loginFrame.getContentPane().add(accountLabel);
		
		JLabel pwLabel = new JLabel("密碼");
		pwLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		pwLabel.setBounds(58, 132, 47, 22);
		loginFrame.getContentPane().add(pwLabel);
		
		accountTextField = new JTextField();
		accountTextField.setBounds(102, 95, 132, 21);
		loginFrame.getContentPane().add(accountTextField);
		accountTextField.setColumns(10);
		
		pwTextField = new JPasswordField();
		pwTextField.setColumns(10);
		pwTextField.setBounds(102, 133, 250, 21);
		loginFrame.getContentPane().add(pwTextField);
		
		JLabel accountEndLabel = new JLabel("@nccu.edu.tw");
		accountEndLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		accountEndLabel.setBounds(243, 94, 109, 17);
		loginFrame.getContentPane().add(accountEndLabel);
		
		JButton btnLogin = new JButton("確認登入");
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setOpaque(true);
		btnLogin.setOpaque(true);
		btnLogin.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {//登入
					String account = accountTextField.getText();
					char[] passwordChars = pwTextField.getPassword();
					String pw = new String(passwordChars);
					checkType(account,pw);
					checkUserExist(account);
					checkPassword(account,pw);
					user = new User(Integer.parseInt(account));//登入成功才建立User物件//用不到了
					loginSuccess = true;
					loginFrame.setVisible(false);
					homepage = new WindowDemo();
					homepage.setUpperPanel(true, Integer.parseInt(account));//設定為已登入的UpperPanel
					homepage.getFrame().setVisible(true);
				}
				catch(UserError exception) {//Account不是8字
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(PasswordError exception) {//密碼為空
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setBounds(44, 178, 109, 33);
		loginFrame.getContentPane().add(btnLogin);
		
		JButton btnForgetPw = new JButton("忘記密碼");
		btnForgetPw.setBackground(new Color(255, 255, 255));
		btnForgetPw.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btnForgetPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {//寄回密碼
					
					String account = accountTextField.getText();
					if(account.equals("")) {
						throw new UserError("請輸入帳號，按下「忘記密碼」按鈕後程式會寄出密碼至您的學校郵件!");
					}
					checkUserExist(account);
					String to = account + "@nccu.edu.tw";
					String pw = getPassword(account);
					sendEmailTo(to,"忘記密碼回報","您好，以下是您當初註冊此系統時的密碼："+pw+"\n歡迎繼續使用本系統");
					
				}
				catch(UserError exception) {//Account(ID)錯誤
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(PasswordError exception) {//密碼錯誤
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnForgetPw.setBounds(163, 178, 109, 33);
		loginFrame.getContentPane().add(btnForgetPw);
		
		JButton btnToSubmit = new JButton("註冊帳號");
		btnToSubmit.setBackground(new Color(255, 255, 255));
		btnToSubmit.setOpaque(true);
		btnToSubmit.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btnToSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame.setVisible(false);
				try {
					UserSubmit window = new UserSubmit();
					window.getSubmitFrame().setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnToSubmit.setBounds(282, 178, 109, 33);
		loginFrame.getContentPane().add(btnToSubmit);
		
		
	}
	
	public void sendEmailTo(String to,String subject,String text) {
		/*String from = "alice16172425@gmail.com";  
	    String host = "localhost";//or IP address
		
	    //Get the session object 
	    Properties properties = System.getProperties();  
	    properties.setProperty("mail.smtp.host", host);  
	    Session session = Session.getDefaultInstance(properties);
	    
	    //compose the message
	    try{  
	    	MimeMessage message = new MimeMessage(session);  
	    	message.setFrom(new InternetAddress(from));  
	    	message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	    	message.setSubject("【政大二手物交流平台】"+subject);  
	    	message.setText(text);  
	  
	    	// Send message  
	    	Transport.send(message); 
	  
	    	}catch (MessagingException mex) {mex.printStackTrace();} */
		//0613更新
		String user = "alice16172425@gmail.com";  
	    String host = "smtp.gmail.com";//or IP address
	    String password="dhsnypiapzfgrmwk";
	    
	    //Get the session object 
	    Properties properties = new Properties();
	    properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

	    Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
	    	protected PasswordAuthentication getPasswordAuthentication() {
	    		return new PasswordAuthentication(user,password);
	    	}
	    });
	    
	    //compose the message
	    try{  
	    	MimeMessage message = new MimeMessage(session);  
	    	message.setFrom(new InternetAddress(user));  
	    	message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	    	message.setSubject("【政大二手物交流平台】"+subject);  
	    	message.setText(text);  
	  
	    	// Send message  
	    	Transport.send(message); 
	    	JOptionPane.showMessageDialog(null,"Email已成功寄出!，請至信箱查看!");
	    	}catch (MessagingException mex) {mex.printStackTrace();} 
	   	
	}
	
	public void checkType(String account,String pw) throws UserError,PasswordError{
		if (account.length() != 9) throw new UserError("學號為9碼");
		if (pw.length() == 0) throw new PasswordError("密碼不得為空");
	}

	public void checkUserExist(String id) throws UserError {
		
		//統整過後
		boolean flag = false;
		
		int account = 0;
		
		loginList = sales.login();
	    for(SalesData data : loginList) {
	    	account = data.getAccount();
			if((id.equals(Integer.toString(account)))) {
				flag = true;
				break;
			}
	    }
		if(!flag) {
			throw new UserError("您尚未註冊。");
		}
		
	}

	
	public void checkPassword(String id,String pw) throws UserError,PasswordError{
		//連接資料庫 確認密碼正確
		
		//統整過後
		boolean flag = false;
		
		int account = 0;
		String password = "";
		
		loginList = sales.login();
	    for(SalesData data : loginList) {
	    	account = data.getAccount();
	    	password = data.getPassword();
	    	if((id.equals(Integer.toString(account)))&&(pw.equals(password))) {
				flag = true;
				break;
			}
	    }
		
		if(!flag) {
			throw new PasswordError("密碼錯誤，麻煩再嘗試一次或使用忘記密碼功能。");
		}
	}
	
	public String getPassword(String id) throws PasswordError{
		
		//統整過後
		int account = 0;
		String password = "";
		
		loginList = sales.login();
	    for(SalesData data : loginList) {
	    	account = data.getAccount();
	    	password = data.getPassword();
	    	
			if((id.equals(Integer.toString(account)))) {
				return password;
			}
	    }
	    
	    throw new PasswordError("找不到相應的密碼");
		
	}
	
	//回傳是否成功登入
	public boolean getLoginSuccess() {
	    return loginSuccess;
	}
	
	//更新是否成功登入
	public void setLoginSuccess(boolean loginSuccess) {
	    this.loginSuccess = loginSuccess;
	}
	
	//回傳使用者(附帶帳號 密碼 名稱)
	public int getUserAccount() {
		
		return user.getUserID();
		
	}
	
	public JFrame getFrame() {
	    return loginFrame;
	}
	
}

/*class UserError extends Exception {
	public UserError(String Error){
		super(Error);
	}
}

class PasswordError extends Exception {
	public PasswordError(String Error){
		super(Error);
	}
}*/
