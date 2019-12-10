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
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

public class NewForm extends JFrame {
	
	String name,fname,mname,address,dob="",blood,nid_num;
	private JPanel contentPane;
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
	
	public static NewForm frame;
	private Connection con;
	JLabel lbl_note;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new NewForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public NewForm() {
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
		tf_fname.setBounds(135, 102, 159, 20);
		contentPane.add(tf_fname);
		
		JLabel lblFName = new JLabel("Father Name");
		lblFName.setBounds(42, 100, 73, 24);
		contentPane.add(lblFName);
		
		tf_mname = new JTextField();
		tf_mname.setColumns(10);
		tf_mname.setBounds(135, 137, 159, 20);
		contentPane.add(tf_mname);
		
		JLabel lblMName = new JLabel("Mother Name");
		lblMName.setBounds(42, 135, 83, 24);
		contentPane.add(lblMName);
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(348, 92, 151, 30);
		datePanel.setBounds(221, 102, 242, 147);
		contentPane.add(datePicker);
		
		Date selectedDate=(Date) datePicker.getModel().getValue();
		System.out.println(selectedDate);
		
		JLabel lblNidNum = new JLabel("NID Number");
		lblNidNum.setBounds(42, 220, 83, 24);
		contentPane.add(lblNidNum);
		
		tf_num = new JTextField();
		tf_num.setColumns(10);
		tf_num.setBounds(146, 222, 353, 20);
		contentPane.add(tf_num);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(348, 68, 73, 14);
		contentPane.add(lblDateOfBirth);
		
		
		cb=new JComboBox(bloodgroup);
		cb.setBounds(426,135, 73, 24);
		contentPane.add(cb);
		
		JLabel lblPermanentAddr = new JLabel("Permanent Addr. ");
		lblPermanentAddr.setBounds(42, 185, 115, 24);
		contentPane.add(lblPermanentAddr);
		
		tf_address = new JTextField();
		tf_address.setColumns(10);
		tf_address.setBounds(148, 187, 353, 20);
		contentPane.add(tf_address);
		
		JLabel lblBloodGroup = new JLabel("Blood Group");
		lblBloodGroup.setBounds(348, 140, 73, 14);
		contentPane.add(lblBloodGroup);
		
		tf_name = new JTextField();
		tf_name.setColumns(10);
		tf_name.setBounds(135, 65, 159, 20);
		contentPane.add(tf_name);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(42, 68, 73, 24);
		contentPane.add(lblFullName);
		
		JButton btnSave = new JButton("Save");
		
		btnSave.setBounds(348, 266, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnRefresh = new JButton("Reload");

		btnRefresh.setBounds(229, 266, 89, 23);
		contentPane.add(btnRefresh);
		
		JButton btnExit = new JButton("Back");

		btnExit.setBounds(111, 266, 89, 23);
		contentPane.add(btnExit);
		
		lbl_note = new JLabel("Fillup all fields");lbl_note.setVisible(false);
		lbl_note.setForeground(Color.RED);
		lbl_note.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbl_note.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_note.setBounds(184, 300, 237, 14);
		contentPane.add(lbl_note);
		
		
		btnSave.addActionListener(new ActionListener() {
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
                	lbl_note.setVisible(true); 
                }else{
                    try{
        			      con=ConnectDB.connect2DB();
        			      final String  query = "insert into nidinfo(name,fname,mname,address,nid,dob,bloodgroup)"+
        		          "values(?,?,?,?,?,?,?)";
        		          PreparedStatement stt;
        		          
        					  stt = con.prepareStatement(query);
        					  stt.setString(1,name);
        			          stt.setString(2,fname);
        			          stt.setString(3,mname);
        			          stt.setString(4,address);
        			          stt.setString(5,nid_num);
        			          stt.setString(6,dob);
        			          stt.setString(7,blood);
        			          
        						stt.execute();
        						stt.close();
        				        con.close();
        				        lbl_note.setVisible(false);
                      }catch(Exception e){
                      	System.out.println("sql error="+e);
                      }
                }      
		        
			}
		});
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_name.setText("");
				tf_fname.setText("");
				tf_mname.setText("");
				tf_address.setText("");
				tf_num.setText("");
				
				cb.setSelectedIndex(0); 
				datePicker.getModel().setValue(null);
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/*Home  home=new 	Home();		
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - home.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - home.getHeight()) / 2);
		
                home.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                home.setLocation(x, y);
                home.setVisible(true);*/
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

