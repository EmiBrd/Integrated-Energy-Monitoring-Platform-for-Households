package com.assigm3Client.clientAssigment3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginDummy {

	private JFrame frame;
	private JPasswordField passwordField_password;
	private JTextField textField_username;
	private MeasureValue measureValue;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//new LoginDummy(measureValue);
					new LoginDummy();
					//LoginDummy window = new LoginDummy();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public LoginDummy() {
		initialize();
		frame.setVisible(true);
	}

	
	private void initialize() {
		ConfigurableApplicationContext ctx = SpringApplication.run(ClientAssigment3Application.class);
		measureValue = ctx.getBean(MeasureValue.class);
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 448, 345);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_username = new JLabel("Username");
		lbl_username.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_username.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_username.setBounds(10, 54, 107, 36);
		frame.getContentPane().add(lbl_username);
		
		JLabel lbl_password = new JLabel("Password");
		lbl_password.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_password.setBounds(10, 100, 107, 36);
		frame.getContentPane().add(lbl_password);
		
		passwordField_password = new JPasswordField();
		passwordField_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		passwordField_password.setBounds(136, 100, 222, 36);
		frame.getContentPane().add(passwordField_password);
		
		textField_username = new JTextField();
		textField_username.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField_username.setBounds(138, 54, 220, 36);
		frame.getContentPane().add(textField_username);
		textField_username.setColumns(10);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Login");
				String[] credentials = measureValue.loginPentruGUIClient(textField_username.getText(), passwordField_password.getText());
				System.out.println("username returnat = "+credentials[0]+", password returnat = "+credentials[1]+", id = "+credentials[2]);
				if(textField_username.getText().equals("") || passwordField_password.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "Please fill in your username and password");
				}
				if(credentials[0].equals(textField_username.getText()) && credentials[1].equals(passwordField_password.getText())) {
					JOptionPane.showMessageDialog(null, "Login successfully");
					new FrameMainDummy(Long.parseLong(credentials[2]) );
				}
				else {
					JOptionPane.showMessageDialog(null, "Username or password is incorrect");
				}
			}
		});
		btn_login.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btn_login.setBounds(137, 182, 158, 49);
		frame.getContentPane().add(btn_login);
		
		
		ctx.close();
	}
	
}

