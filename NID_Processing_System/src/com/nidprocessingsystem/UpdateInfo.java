package com.nidprocessingsystem;

import java.awt.BorderLayout;
import java.util.*;
import java.util.logging.Handler;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import com.nidprocessingsystem.NewForm.DateLabelFormatter;

public class UpdateInfo extends JFrame {

	private JPanel contentPane;
	String name,fname,mname,address,dob="",blood,nid_num;
	private JTextField tf_fname;
	private JTextField tf_mname;
	UtilDateModel model;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker ;
	private JTextField tf_num;
	JComboBox cb;
	String bloodgroup[]={"Select","A+","A-","B+","B-","AB+","AB-","O+","O-"};  
	private JTextField tf_address;
	private JTextField tf_name;
	JLabel lbl_status;
	
	public static NewForm frame;
	static UpdateInfo upFrame;
	
	private Connection con;



	public UpdateInfo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 354);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNationalIdInformation = new JLabel("National ID Information");
		lblNationalIdInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblNationalIdInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNationalIdInformation.setBounds(151, 11, 257, 33);
		contentPane.add(lblNationalIdInformation);
		
		tf_fname = new JTextField();
		tf_fname.setColumns(10);
		tf_fname.setBounds(148, 152, 159, 20);
		contentPane.add(tf_fname);
		
		JLabel lblFName = new JLabel("Father Name");
		lblFName.setBounds(42, 150, 73, 24);
		contentPane.add(lblFName);
		
		tf_mname = new JTextField();
		tf_mname.setColumns(10);
		tf_mname.setBounds(148, 185, 159, 20);
		contentPane.add(tf_mname);
		
		JLabel lblMName = new JLabel("Mother Name");
		lblMName.setBounds(42, 183, 83, 24);
		contentPane.add(lblMName);
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(348, 138, 151, 30);
		datePanel.setBounds(221, 102, 242, 147);
		contentPane.add(datePicker);
		
		Date selectedDate=(Date) datePicker.getModel().getValue();
		System.out.println(selectedDate);
		
		JLabel lblNidNum = new JLabel("NID Number");
		lblNidNum.setBounds(42, 53, 83, 24);
		contentPane.add(lblNidNum);
		
		tf_num = new JTextField();
		tf_num.setColumns(10);
		tf_num.setBounds(146, 55, 275, 22);
		contentPane.add(tf_num);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(348, 114, 73, 14);
		contentPane.add(lblDateOfBirth);
		
		
		cb=new JComboBox(bloodgroup);
		cb.setBounds(426,181, 73, 24);
		contentPane.add(cb);
		
		JLabel lblPermanentAddr = new JLabel("Permanent Addr. ");
		lblPermanentAddr.setBounds(42, 218, 115, 24);
		contentPane.add(lblPermanentAddr);
		
		tf_address = new JTextField();
		tf_address.setColumns(10);
		tf_address.setBounds(148, 220, 353, 20);
		contentPane.add(tf_address);
		
		JLabel lblBloodGroup = new JLabel("Blood Group");
		lblBloodGroup.setBounds(348, 186, 73, 14);
		contentPane.add(lblBloodGroup);
		
		tf_name = new JTextField();
		tf_name.setColumns(10);
		tf_name.setBounds(148, 121, 159, 20);
		contentPane.add(tf_name);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(42, 115, 73, 24);
		contentPane.add(lblFullName);
		
		JButton btnUpdate = new JButton("Update");btnUpdate.setEnabled(false);
		
		btnUpdate.setBounds(348, 266, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnBack = new JButton("Exit");

		btnBack.setBounds(218, 266, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnFind = new JButton("Find");

		btnFind.setBounds(426, 54, 73, 23);
		contentPane.add(btnFind);
		
		lbl_status = new JLabel("Enter nid number to search");
		lbl_status.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_status.setBounds(148, 76, 199, 14);
		contentPane.add(lbl_status);
		
		
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con=ConnectDB.connect2DB();
				String query="SELECT * FROM `nidinfo` WHERE `nid`=?";
				try {
					PreparedStatement ps=con.prepareStatement(query);
					String nidSearch=tf_num.getText().toString();
					ps.setString(1,nidSearch );
					ResultSet resultset=ps.executeQuery();
					
					if(resultset.next()){
						lbl_status.setText("Found.");
						btnUpdate.setEnabled(true);
                        con.close();
					}else{
						lbl_status.setText("Not Found.Search Again");
					}
			}catch(Exception e1){
				
			}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				name=tf_name.getText().toString();
				fname=tf_fname.getText().toString();
				mname=tf_mname.getText().toString();
				address=tf_address.getText().toString();
				nid_num=tf_num.getText().toString();
				
				Date selectedDate=(Date) datePicker.getModel().getValue();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			    dob= formatter.format(selectedDate);
			    
			    blood=String.valueOf(cb.getItemAt(cb.getSelectedIndex()));
                if(name.isEmpty()||fname.isEmpty()||mname.isEmpty()||address.isEmpty()||nid_num.isEmpty()||dob.isEmpty()||blood.equals("select")){
                	//lbl_note.setVisible(true); 
                }else{
                    try{
        			      con=ConnectDB.connect2DB();
        			      
        			      final String  query = "UPDATE  nidinfo SET name=?, fname=?, mname=?, address=?, dob=?, bloodgroup=? where nid=?";
        			          
        			      PreparedStatement stt;
        		          
        					  stt = con.prepareStatement(query);
        					  stt.setString(1,name);
        			          stt.setString(2,fname);
        			          stt.setString(3,mname);
        			          stt.setString(4,address);
        			          stt.setString(5,dob);
        			          stt.setString(6,blood);
        			          stt.setString(7,nid_num);
        			          
        					  stt.executeUpdate();
        				      stt.close();
        				      con.close();
        				    //lbl_note.setVisible(false);
                      }catch(Exception e){
                      	e.printStackTrace();
                      }
                } 
                
                btnUpdate.setEnabled(false);
                tf_name.setText("");
				tf_fname.setText("");
				tf_mname.setText("");
				tf_address.setText("");
				tf_num.setText("");
				cb.setSelectedIndex(0); 
				datePicker.getModel().setValue(null);
				lbl_status.setText("Enter nid number to search");
		        
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
/*				Home home=new Home();
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - home.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - home.getHeight()) / 2);
		
                home.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                home.setLocation(x, y);
                home.setVisible(true);*/
				
				
				upFrame.dispose();
/*				Home  home=new 	Home();		
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - home.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - home.getHeight()) / 2);
		
                home.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                home.setLocation(x, y);
                home.setVisible(true);*/
			}
		});
		
		
	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					upFrame = new UpdateInfo();
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	                int x = (int) ((dimension.getWidth() - upFrame.getWidth()) / 2);
	                int y = (int) ((dimension.getHeight() - upFrame.getHeight()) / 2);
			
	                upFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	                upFrame.setLocation(x, y);
					upFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
	    

	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}
}
