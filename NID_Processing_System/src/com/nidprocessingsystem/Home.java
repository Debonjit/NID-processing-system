package com.nidprocessingsystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private JPanel contentPane;
	public static String username;
	private static JButton btn_login;
	private static JLabel lbl_hellonote ;
	private static JButton btn_update_nid;
	private static  JButton btn_new_form;
	
	public static Home frame;

	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomeToNational = new JLabel("Welcome to NID Management System  ");
		lblWelcomeToNational.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWelcomeToNational.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToNational.setBounds(111, 45, 411, 33);
		contentPane.add(lblWelcomeToNational);
		
		btn_login = new JButton("Continue...");
		btn_login.setBounds(229, 116, 156, 23);
		contentPane.add(btn_login);
		
		lbl_hellonote = new JLabel("Please login first*");
		lbl_hellonote.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_hellonote.setForeground(Color.RED);
		lbl_hellonote.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_hellonote.setBackground(Color.WHITE);
		lbl_hellonote.setBounds(161, 91, 300, 14);
		contentPane.add(lbl_hellonote);
		
		btn_new_form = new JButton("Open New Form");
		btn_new_form.setVisible(false);
		btn_new_form.setBounds(157, 116, 131, 23);
		contentPane.add(btn_new_form);
		
		btn_update_nid = new JButton("Update NID Info.");
		btn_update_nid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UpdateInfo up=new 	UpdateInfo();		
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - up.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - up.getHeight()) / 2);
		
                up.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                up.setLocation(x, y);
                up.setVisible(true);
			}
		});
		btn_update_nid.setVisible(false);
		btn_update_nid.setBounds(330, 116, 131, 23);
		contentPane.add(btn_update_nid);
		
		
		
		//==================All actions=====================================================
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showCustomDialog();
			}
		});
		
		btn_new_form.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				NewForm newframe=new NewForm();			
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - newframe.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - newframe.getHeight()) / 2);
		
                newframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                newframe.setLocation(x, y);
                newframe.setVisible(true);
			}
		});
		
	}//end of constructor
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                    frame = new Home();	
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                    frame.setLocation(x, y);	
                    frame.setTitle("SMART NID SYSTEM");
                  
                    frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
    public void showCustomDialog(){
		Logindialog dialog= new Logindialog();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
		
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocation(x, y);
		
		dialog.setVisible(true);
		dialog.setTitle("Provide Your Login Credintial");
	}
	
	public static void updateUI(String id){
		lbl_hellonote.setText("Hello, "+id+" ");
		lbl_hellonote.setBackground(Color.BLACK);
		btn_login.setVisible(false);
        btn_new_form.setVisible(true);
        btn_update_nid.setVisible(true);
	}
}
