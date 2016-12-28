package car;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class NewId extends JFrame implements ActionListener{
      JPanel p1;
      JLabel l1,l2,l3,l4;
      JTextField t1,t3,t4;
      JPasswordField t2;
      JButton b1,b2;
      Connection con = makeConnection();
      
      public NewId(){
         p1=new JPanel();
         p1.setLayout(null);
         l1=new JLabel("Id:");
         l2=new JLabel("Pw:");
         l3=new JLabel("이름:");
         l4=new JLabel("연봉:");
         t1=new JTextField(20);
         t2=new JPasswordField(20);
         t3=new JTextField(20);
         t4=new JTextField(20);
         b1=new JButton("중복검사");
         b1.addActionListener(this);
         b2=new JButton("가입하기");
         b2.addActionListener(this);
         b2.setEnabled(false);
         p1.add(l1);
         l1.setBounds(12, 28, 57, 15);
         p1.add(t1);
         t1.setBounds(66, 22, 116, 21);
         p1.add(b1);
         b1.setBounds(190, 22, 90, 21);
         p1.add(l2);
         l2.setBounds(12, 53, 57, 15);
         p1.add(t2);
         t2.setBounds(66, 47, 116, 21);
         p1.add(l3);
         l3.setBounds(12, 77, 57, 15);
         p1.add(t3);
         t3.setBounds(66, 77, 116, 21);
         p1.add(l4);
         l4.setBounds(12, 103, 57, 15);
         p1.add(t4);
         t4.setBounds(66, 103, 116, 21);
         p1.add(b2);
         b2.setBounds(12, 129, 270, 50);
         add(p1);
         
         setBounds(200,100,350,300); 
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setTitle("회원가입");
         setVisible(true);
      }
      public void actionPerformed(ActionEvent e) {
			if(e.getSource()==b1){	
				if(t1.getText().trim().equals("")){
					showMsg("id를 입력해주세요");
				}else{
					IdCheck();
				}
				}
			if(e.getSource()==b2){
					Addid();
			    }
		}
      private void IdCheck(){
         try {
         PreparedStatement pstmt;
         ResultSet rs;
         
         String s1="select id from consumer where id= ?";            
         pstmt=con.prepareStatement(s1);         
         pstmt.setString(1, t1.getText());                                                
         rs=pstmt.executeQuery();
      
         
         if(rs.next()){
            showMsg("중복된 id가 있습니다.");
            t1.requestFocus();
            return;
         }
         else{
            showMsg("중복된 id가 없습니다.");
            b2.setEnabled(true);
         }
         }catch (SQLException e) {
            showMsg(e.getMessage());
            System.exit(0);
            }
      }
      private void Addid() {         
         try {            
            Statement stmt = con.createStatement();
                                             
            String s = "INSERT INTO consumer (id, pw, name, income) VALUES ";
            s += "('" + t1.getText() + "','" + t2.getText() + "', '"+t3.getText()+"',"+Integer.parseInt(t4.getText())+")";                        
            int i = stmt.executeUpdate(s);
            
            if (i == 1){
               
               showMsg("회원가입 성공");
               this.setVisible(false);
            }
            else
               showMsg("회원가입 실패");
            }catch (SQLException e) {
               showMsg(e.getMessage());
               System.exit(0);
            }
      }
      public void showMsg(String msg){
         JOptionPane.showMessageDialog(getParent(), msg);  
          }
      public Connection makeConnection() {
         String url = "jdbc:mysql://localhost/car";
         String id = "root";
         String password = "ckdwn123";
         Connection con = null;
         try {
            Class.forName("com.mysql.jdbc.Driver");            
            con = DriverManager.getConnection(url, id, password);            
         } catch (ClassNotFoundException e) {
            showMsg("드라이버를 찾을 수 없습니다.");
         } catch (SQLException e) {
            showMsg("연결에 실패하였습니다.");
         }
         return con;
      }
   }