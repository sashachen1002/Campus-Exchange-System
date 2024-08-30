package 期末報告_11102Programming_v2;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class UserSubmit extends JFrame{

	private JFrame submitFrame;
	private JTextField accountTextField;
	private JPasswordField pwTextField;
	private JTextField userNameTextField;
	private JTextField vCodeTextField;
	private String vertifyCode;
	
	//連結資料庫跟SQL
	private List<SalesData> loginList;
	private Sales sales = new Sales();
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserSubmit window = new UserSubmit();
					window.submitFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/


	public JFrame getSubmitFrame() {
		return this.submitFrame;
	}
	/**
	 * Create the application.
	 */
	public UserSubmit() {
		initialize();
		submitFrame.setDefaultCloseOperation(submitFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		submitFrame = new JFrame();
		submitFrame.setBounds(100, 100, 450, 300);
		submitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		submitFrame.getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("註冊帳號");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		titleLabel.setBounds(161, 23, 102, 33);
		submitFrame.getContentPane().add(titleLabel);
		
		JLabel userNameLabel = new JLabel("使用者名稱");
		userNameLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		userNameLabel.setBounds(28, 78, 109, 19);
		submitFrame.getContentPane().add(userNameLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setColumns(10);
		userNameTextField.setBounds(121, 77, 250, 22);
		submitFrame.getContentPane().add(userNameTextField);
		
		JLabel accountLabel = new JLabel("帳號");
		accountLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		accountLabel.setBounds(77, 109, 47, 22);
		submitFrame.getContentPane().add(accountLabel);
		
		accountTextField = new JTextField();
		accountTextField.setColumns(10);
		accountTextField.setBounds(121, 109, 109, 24);
		submitFrame.getContentPane().add(accountTextField);
		
		JLabel lblNewLabel_2 = new JLabel("@nccu.edu.tw");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(238, 113, 109, 17);
		submitFrame.getContentPane().add(lblNewLabel_2);
		
		JLabel pwLabel = new JLabel("密碼");
		pwLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		pwLabel.setBounds(77, 140, 47, 22);
		submitFrame.getContentPane().add(pwLabel);
		
		pwTextField = new JPasswordField();
		pwTextField.setColumns(10);
		pwTextField.setBounds(121, 141, 250, 24);
		submitFrame.getContentPane().add(pwTextField);

		JLabel vCodeLabel = new JLabel("驗證碼");
		vCodeLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		vCodeLabel.setBounds(62, 173, 62, 22);
		submitFrame.getContentPane().add(vCodeLabel);
		
		vCodeTextField = new JTextField();
		vCodeTextField.setColumns(10);
		vCodeTextField.setBounds(121, 176, 109, 22);
		submitFrame.getContentPane().add(vCodeTextField);
		
		JButton btnGetVCode = new JButton("獲取");
		btnGetVCode.setBackground(new Color(255, 255, 255));
		btnGetVCode.setOpaque(true);
		btnGetVCode.setOpaque(true);
		btnGetVCode.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		btnGetVCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String to = accountTextField.getText();
					//checkUserExist(to);
					vertifyCode = createVertifyCode();
					if(to.equals("")) {
						throw new UserError("請輸入學號，按下「獲取」按鈕後程式會寄出驗證碼至您的學校郵件!將驗證碼打在驗證碼輸入框後送出方可註冊完成。");
					}else {
						to += "@nccu.edu.tw";
						sendEmailTo(to,"註冊驗證碼","以下為您的註冊驗證碼"+vertifyCode);
					}
				}catch(UserError exception) {//Account不是8字
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnGetVCode.setBounds(229, 172, 75, 26);
		submitFrame.getContentPane().add(btnGetVCode);
		
		JButton btnGetVCodeAgain = new JButton("重傳");
		btnGetVCodeAgain.setBackground(new Color(255, 255, 255));
		btnGetVCodeAgain.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		btnGetVCodeAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(vertifyCode.equals("")) {
						throw new UserError("尚未獲取過驗證碼，按下「獲取」按鈕後程式會寄出驗證碼至您的學校郵件!將驗證碼打在驗證碼輸入框後送出方可註冊完成。");
					}
					String to = accountTextField.getText();
					if(to.equals("")) {
						throw new UserError("請輸入學號，按下「獲取」按鈕後程式會寄出驗證碼至您的學校郵件!將驗證碼打在驗證碼輸入框後送出方可註冊完成。");
					}else {
						checkUserExist(to);
						to += "@nccu.edu.tw";
						sendEmailTo(to,"註冊驗證碼","用戶 "+userNameLabel.getText()+" 您好，以下為您的註冊驗證碼 : "+vertifyCode);
					}
				}catch(UserError exception) {//Account不是8字
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGetVCodeAgain.setBounds(303, 172, 68, 26);
		submitFrame.getContentPane().add(btnGetVCodeAgain);
		
		
		JButton btnSubmit = new JButton("確認送出");
		btnSubmit.setBackground(new Color(255, 255, 255));
		btnSubmit.setOpaque(true);
		btnSubmit.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					/*checkType(userNameTextField.getText(),accountTextField.getText(),pwTextField.getText(),vCodeTextField.getText());
					checkUserExist(accountTextField.getText());
					if(vertifyCode.equals(vCodeLabel.getText())) {// 確認驗證碼
						
						//統整過後
						int account = Integer.parseInt(accountLabel.getText());
						String userName = userNameLabel.getText();
						String password = pwLabel.getText();
						
						SalesData registration = new SalesData(account, userName, password);
						sales.registration(registration);
						
						//檢查是否註冊成功
		                boolean success = ((Sales) sales).uploadBook(registration);
		                if (success) {
		                	JOptionPane.showMessageDialog(null,"已完成註冊，將自動跳至登入頁面。請輸入剛註冊的密碼並登入使用系統。");
		                	submitFrame.setVisible(false);
							callLoginFrame();
							submitFrame.dispose();
		                }
					}*/
					
					//0612修改
					checkType(userNameTextField.getText(),accountTextField.getText(),pwTextField.getText(),vCodeTextField.getText());
					checkUserExist(accountTextField.getText());
					if(vertifyCode.equals(vCodeTextField.getText())) {// 確認驗證碼
						
						int account = Integer.parseInt(accountTextField.getText());
						String userName = userNameTextField.getText();
						char[] passwordChars = pwTextField.getPassword();
						String password = new String(passwordChars);
						
						SalesData registration = new SalesData(account, userName, password);
						sales.registration(registration);
						
						//檢查是否註冊成功
		                boolean success = ((Sales) sales).uploadBook(registration);
		                if (success) {
		                	JOptionPane.showMessageDialog(null,"已完成註冊，將自動跳至登入頁面。請輸入剛註冊的密碼並登入使用系統。");
		                	submitFrame.setVisible(false);
							callLoginFrame();
							submitFrame.dispose();
		                }else {
							JOptionPane.showMessageDialog(null,"尚未註冊成功，請重試一次。");
						}
					}else {
						JOptionPane.showMessageDialog(null,"請確認輸入正確的驗證碼!");
					}
					
				}catch(UserError | PasswordError exception) {//Account不是8字
					JOptionPane.showMessageDialog(null,exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSubmit.setBounds(154, 208, 109, 33);
		submitFrame.getContentPane().add(btnSubmit);
		
		
	}
	
	public String createVertifyCode() {	//產生四碼亂數
		String vertifyCode = "";
		int code = 0;
		for(int i=0;i<4;i++) {
			code = (int)(Math.random()*10);
			vertifyCode += Integer.toString(code);
		}
		return vertifyCode;
	}
	
	public void callLoginFrame() { //呼叫登入介面
		try {
			UserLogin window = new UserLogin();
			window.getLoginFrame().setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void sendEmailTo(String to,String subject,String text) { // 寄email
		
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
	
	//確認沒輸入不為空/格式錯誤
	public void checkType(String user,String account,String pw,String vCode) throws UserError,PasswordError{
		if (user.length() == 0) throw new PasswordError("使用者名稱不得為空");
		if (account.length() != 9) throw new UserError("學號為9碼");
		if (pw.length() == 0) throw new PasswordError("密碼不得為空");
		if (vCode.length() == 0) throw new PasswordError("驗證碼不得為空。請輸入學號，按下「獲取」按鈕後程式會寄出驗證碼至您的學校郵件!將驗證碼打在驗證碼輸入框後送出方可註冊完成。");
	}
	
	public void checkUserExist(String id) throws UserError {//確認沒有此會員
		//統整過後
		boolean flag = true;
		loginList = sales.login();
		int account=0;
	    for(SalesData data : loginList) {
	    	account = data.getAccount();
	    	if(id.equals(Integer.toString(account))) {
				flag = false;
				break;
			}
	    }
	    if(!flag) {
	    	submitFrame.dispose();
	    	UserLogin window = new UserLogin();
	    	window.getLoginFrame().setVisible(true);
	    	throw new UserError("您已經註冊了，請回到登入介面!");
		}	
	}
	
	public JFrame getFrame() {
	    return submitFrame;
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