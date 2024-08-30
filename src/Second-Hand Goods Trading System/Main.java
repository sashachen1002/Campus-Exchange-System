package 期末報告_11102Programming_v2;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// 呼叫HomeFrame
		WindowDemo frame = new WindowDemo();
		frame.setUpperPanel();
		frame.getHomeFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getHomeFrame().setVisible(true);
		
	}

}

class UserError extends Exception {
	public UserError(String Error){
		super(Error);
	}
}

class PasswordError extends Exception {
	public PasswordError(String Error){
		super(Error);
	}
}