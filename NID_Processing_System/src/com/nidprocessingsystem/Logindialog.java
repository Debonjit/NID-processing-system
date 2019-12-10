package com.nidprocessingsystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class Logindialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tf_userID;
	private JPasswordField tf_userpass;
	Connection connection;public JLabel lbl_warning;

	public static void main(String[] args) {
		try {
			Logindialog dialog = new Logindialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Logindialog() {
		setBounds(100, 100, 426, 235);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUsername = new JLabel("username:");
			lblUsername.setBounds(81, 42, 70, 25);
			contentPanel.add(lblUsername);
		}
		{
			tf_userID = new JTextField();
			tf_userID.setBounds(161, 44, 141, 20);
			contentPanel.add(tf_userID);
			tf_userID.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("password:");
			lblPassword.setBounds(81, 78, 70, 25);
			contentPanel.add(lblPassword);
		}
		{
			tf_userpass = new JPasswordField();
			tf_userpass.setBounds(161, 80, 141, 20);
			contentPanel.add(tf_userpass);
		}
		{
			JButton btnLogin = new JButton("login");
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					String id=tf_userID.getText();
					String pass=tf_userpass.getText();
					connection=ConnectDB.connect2DB();
					System.out.println("=>"+id+"=>"+pass);
					if(id.isEmpty() && pass.isEmpty()){
						lbl_warning.setText("Invalid Input!");
						lbl_warning.setVisible(true);
					}else{
						if(connection!=null){
						String query="SELECT * FROM `userlist` WHERE `userid`=? AND `password`=?";
						try {
							PreparedStatement ps=connection.prepareStatement(query);
							ps.setString(1,id );
							ps.setString(2, pass);
							ResultSet resultset=ps.executeQuery();
							
							if(resultset.next()){
								System.out.println("login done.");  // 
	                            Home.username=id;
	                            Home.updateUI(id);
								Logindialog.this.dispose();
								connection.close();
							}else{
								lbl_warning.setText("Wrong Credentials !");
								lbl_warning.setVisible(true);
							}
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
						

					}
					
					
				}
			});
			btnLogin.setBounds(213, 111, 89, 23);
			contentPanel.add(btnLogin);
		}
		{
			lbl_warning = new JLabel("New label");lbl_warning.setVisible(false);
			lbl_warning.setForeground(Color.RED);
			lbl_warning.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lbl_warning.setBounds(81, 115, 111, 14);
			contentPanel.add(lbl_warning);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

}
