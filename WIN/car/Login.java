package car;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



class Login extends JFrame implements ActionListener{
	JPanel title,center, center1, center2, south;
	JLabel t,la_id, la_passwd;
	JTextField t_id;
	JPasswordField t_passwd;
	JButton login,n;
	 
	String url="jdbc:mysql://localhost/car";
	String uid="root";
	String upasswd="ckdwn123";
	String driver="com.mysql.jdbc.Driver";
	 
	Connection con;
	PreparedStatement pstmt, pstmt1;
	ResultSet rs,rs1; 
	 
	
	public Login(){	
		
		setTitle("오빠차");
		title=new JPanel();
		center = new JPanel();
		center1 = new JPanel();
		center2 = new JPanel();
		south = new JPanel();
		n=new JButton("회원가입");
		n.addActionListener(this);
		t= new JLabel("오빠차에 오신것을 환영합니다.");
		la_id = new JLabel("I   D  ");
		la_passwd= new JLabel("PW  ");
		t_id= new JTextField(20);
		t_passwd = new JPasswordField(20);
		login = new JButton("Login");
	  
	 
	  
		center.setLayout(new GridLayout(2,1));
	    title.add(t);
	    add(title, BorderLayout.NORTH);
	    center1.add(la_id);
	    center1.add(t_id);
	    center2.add(la_passwd);
	    center2.add(t_passwd);
	  
	    center.add(center1);
	    center.add(center2);    
	    add(center,BorderLayout.CENTER);
	  
	    south.add(login);
	    south.add(n);
	    add(south, BorderLayout.SOUTH);
	    
	    login.addActionListener(this);
	    setSize(1000,900);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(200,100,500,300); 
	    setVisible(true);
	  
	 
	    if(connect()){		 		   
	    	t_id.requestFocus();
	    }else{
	    	showMsg("접속실패");
	    }
	  
	}
	 
	 
	public boolean connect() {
		boolean result=false;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,uid,upasswd);
			if(con!=null){
				result= true;
			}else{
				result= false;
			}
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;  
	}
	 		 
	public void loginCheck(){
		if(t_id.getText().length()==0){
			showMsg("아이디를 입력해주세요");
			t_id.requestFocus();
			return; 
		}
		char[] ch=t_passwd.getPassword();
		String passStr = new String(ch);
		if(passStr.length()==0){
			showMsg("비밀번호를 정확히 입력해주세요");
			t_passwd.requestFocus();
			return;
		}
		String sql="select * from manager where id=? and pw=?";
		String sql1="select * from consumer where id=? and pw=?";
		try{
		  
			pstmt=con.prepareStatement(sql);
			pstmt1=con.prepareStatement(sql1);
		   
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, passStr);
		   
			pstmt1.setString(1, t_id.getText());
			pstmt1.setString(2, passStr);
		   
			rs=pstmt.executeQuery();
			rs1=pstmt1.executeQuery();
		   
			if(rs.next()){
				showMsg("로그인되었습니다.");
			    this.setVisible(false);
			    
			    new Manager();
			   				  
			}
		   
			else if(rs1.next()){
				showMsg("로그인되었습니다.");
			    this.setVisible(false);
			  
			    new Consumer();
			  
			}
			else{
				showMsg("로그인 정보가 올바르지 않습니다.");
			}
		  		
		}catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	public void closeDB(){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {  
				e.printStackTrace();
			}   
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	 
	public void showMsg(String msg){
		JOptionPane.showMessageDialog(getParent(), msg);  
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login){
			loginCheck();
		}
		if(e.getSource()==n){
			 new NewId();
		}
	}
	 		
}