package car;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Manager extends JFrame implements ActionListener{
      JPanel panel,contentPane;
      JLabel title1,title2,label,l1,l2,l3,l4,l5,picture,l6;
      JButton b1,b2,home_b,btnRemovePic,modify,b6,b7;
      JTextField t_name,t_price,t1,t2,t3,t4,t5,t6;
      JScrollPane scrollPane;
      String picPath, Filename; 
      JFileChooser fc = new JFileChooser();
      Connection con = makeConnection();      
      String car_table[] = new String[3];
      String s1="select *from list order by price";
      String name[]={"자동차 이름","시 세","연 봉"};
      DefaultTableModel model = new DefaultTableModel(name,0);   
      JTable table = new JTable(model);
      
      public Manager(){
    	  
         setBounds(200,100,985,380); 
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setTitle("Manager");
         try{                               
             Statement stmt = con.createStatement();           
              ResultSet rs = stmt.executeQuery(s1);
             
              while(rs.next()){               
                 car_table[0]= rs.getString("name");
                 car_table[1] = rs.getString("price");
                 car_table[2] = rs.getString("pay");
                
                 model.addRow(car_table);                
               }
              table.addMouseListener(new MouseAdapter(){
                  public void mouseClicked(MouseEvent m){
                     int rowIndex = table.getSelectedRow();
                     
                     if(rowIndex != -1){
                        status(rowIndex);
                        picPath=Img(Filename);
                        
                        ImageIcon icon = new ImageIcon(picPath);          
                        picture.setText("");
                        picture.setIcon(icon);
                     }
                  }                     
               });         
              }
           catch(SQLException e){
              showMsg(e.getMessage());
              System.exit(0);
          }
         panel=new JPanel();
         contentPane=new JPanel();
         title1=new JLabel("관리자 모드");
         title2=new JLabel("레코드 삭제시 이름만 입력.");
         label=new JLabel("안녕하세요");
         Border border = BorderFactory.createTitledBorder("관리자 모드");
       panel.setBorder(border);
         l1=new JLabel("차 이름");
         l2=new JLabel("가격");
         l3=new JLabel("차종");
         l4=new JLabel("배기량");
         l5=new JLabel("연비");
         l6=new JLabel("차량 이름을 입력하세요:");
         b6=new JButton("검색");
         b7=new JButton("초기화");
         b7.addActionListener(this);
         b1=new JButton("차량 추가");
         b2=new JButton("차량 삭제");
         home_b=new JButton("홈으로");
         modify=new JButton("차량 수정");
         modify.addActionListener(this);
         b1.addActionListener(this);
         b2.addActionListener(this);
         t_name=new JTextField(20);
         t_price=new JTextField(20);
         t3=new JTextField(20);
         t4=new JTextField(20);
         t5=new JTextField(20);
         t6=new JTextField(20);
         panel.add(t6);
         panel.add(b6);
         panel.add(l6);
         panel.add(b7);
         l6.setBounds(12, 230, 150, 25);
         t6.setBounds(160,230, 165, 25);
         b6.setBounds(340,230, 90, 25);
         b7.setBounds(435, 230, 90, 25);
         b6.addActionListener(this);
         panel.setLayout(null);
         panel.add(title1);
         panel.add(title2);
         panel.add(l1);
         l1.setBounds(12, 28, 57, 15);
         panel.add(t_name);
         t_name.setBounds(66, 22, 140, 21);
         panel.add(l2);
         l2.setBounds(12, 53, 57, 15);
         panel.add(t_price);
         t_price.setBounds(66, 47, 140, 21);
         panel.add(l3);
         l3.setBounds(12, 78, 57, 15);
         panel.add(t3);
         t3.setBounds(66, 72, 140, 21);
         panel.add(l4);
         l4.setBounds(12, 103, 57, 15);
         panel.add(t4);
         t4.setBounds(66, 97, 140, 21);
         panel.add(l5);
         l5.setBounds(12, 128, 57, 15);
         panel.add(t5);
         t5.setBounds(66, 123, 140, 21);
         panel.add(b1);
         b1.setBounds(12, 153, 90, 25);
         panel.add(b2);
         b2.setBounds(115, 153, 90, 25);
         panel.add(modify);
         modify.setBounds(12, 195, 90, 25);
         panel.add(home_b);
         home_b.setBounds(115, 195, 90, 25);
         contentPane = new JPanel();
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        panel.setBounds(12, 10, 550, 271);
         contentPane.add(panel);
         home_b.addActionListener(this);
         scrollPane = new JScrollPane(table);
         scrollPane.setBounds(580, 8, 358, 271);
       scrollPane.setBorder(new TitledBorder(null, "전체차량", TitledBorder.LEADING, TitledBorder.TOP, null, null));
       contentPane.add(scrollPane);
         /*------------------------------------*/
         picture = new JLabel("사  진");
       picture.setHorizontalAlignment(SwingConstants.CENTER);
       picture.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
       picture.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseReleased(MouseEvent arg0) {
             int ret = fc.showOpenDialog(Manager.this);
             if(ret == JFileChooser.APPROVE_OPTION) {
                picPath = fc.getSelectedFile().toString();
                System.out.println(picPath);
                ImageIcon icon = new ImageIcon(picPath);
                picture.setText("");
                picture.setIcon(icon);
             }
          }
       });
       picture.setBounds(225 , 22, 300, 200);
       panel.add(picture);
      
       /*--------------------------------------------*/
         setVisible(true);
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
      private void AddCar(String name, int price) {         
         try {
            int pay = 0;
            Statement stmt = con.createStatement();
            PreparedStatement pstmt;
            ResultSet rs;
            
            String s1="select name from list where name= ?";            
            pstmt=con.prepareStatement(s1);         
            pstmt.setString(1, name);                                                
            rs=pstmt.executeQuery();
         
            
            if(rs.next()){
               showMsg("중복된 차종이 있습니다.");
               return;
            }
            pay=price*2;
            String s = "INSERT INTO list (name, price,pay,kind,CC,km) VALUES ";
            s += "('" + name + "','" + price + "',"+pay+",'"+t3.getText()+"',"+t4.getText()+",'"+t5.getText()+"')";                        
            int i = stmt.executeUpdate(s);
            
            if (i == 1)
               showMsg("레코드 추가 성공");
            else
               showMsg("레코드 추가 실패");
            }catch (SQLException e) {
            showMsg(e.getMessage());
            System.exit(0);
            }
         
         model.getDataVector().removeAllElements();
         revalidate();
       try{                               
             Statement stmt = con.createStatement();           
              ResultSet rs = stmt.executeQuery(s1);
              
              while(rs.next()){   
                
                 car_table[0]= rs.getString("name");
                 car_table[1] = rs.getString("price");
                 car_table[2] = rs.getString("pay");                
                 model.addRow(car_table);                
               }            
              }
           catch(SQLException e){
              showMsg(e.getMessage());
              System.exit(0);
          }  
         t_name.setText("");
         t_price.setText("");
         t3.setText("");
         t4.setText("");
         t5.setText("");
      }
      private void DropCar(String name) {         
         try {
                         
            Statement stmt = con.createStatement();
            String s = "delete FROM list where name=";
            s += "'" + name + "'";
            
            int i = stmt.executeUpdate(s);
            if (i == 1)
               showMsg("레코드 삭제 성공");
            else
               showMsg("레코드 삭제 실패");
            }catch (SQLException e) {
            showMsg(e.getMessage());
            System.exit(0);
            }
         
         model.getDataVector().removeAllElements();
         revalidate();
       try{                               
             Statement stmt = con.createStatement();           
              ResultSet rs = stmt.executeQuery(s1);
              
              while(rs.next()){   
                
                 car_table[0]= rs.getString("name");
                 car_table[1] = rs.getString("price");
                 car_table[2] = rs.getString("pay");                
                 model.addRow(car_table);                
               }            
              }
           catch(SQLException e){
              showMsg(e.getMessage());
              System.exit(0);
          }  
         t_name.setText("");
         t_price.setText("");
         t3.setText("");
         t4.setText("");
         t5.setText("");
      }
      public void showMsg(String msg){
         JOptionPane.showMessageDialog(getParent(), msg);  
          }
      public void actionPerformed(ActionEvent e) {
         if(e.getSource()==b1){ 
            if(t_name.getText().trim().equals("")){
             showMsg("차량이름을 입력하지 않았습니다.");
             return;
            }
            if(t_price.getText().trim().equals("")){
              showMsg("차량가격을 입력하지 않았습니다.");
              return;
             }
            if(t3.getText().trim().equals("")){
              showMsg("차종을 입력하지 않았습니다.");
              return;
             }
            if(t4.getText().trim().equals("")){
              showMsg("배기량 입력하지 않았습니다.");
              return;
             }
            if(t5.getText().trim().equals("")){
              showMsg("연비를 입력하지 않았습니다.");
              return;
             }
            AddCar(t_name.getText(),Integer.parseInt(t_price.getText()));
            }
         if(e.getSource()==b2){
            DropCar(t_name.getText());
             }
         if(e.getSource()==home_b){
            setVisible(false);
            new Login();
         }
         if(e.getSource()==modify){
        	 Modify();
          }
         if(e.getSource()==b6){
        	 String s1="select *from list where name='"+t6.getText()+"'";
        	 model.getDataVector().removeAllElements();
             revalidate();
        	 try{                               
                 Statement stmt = con.createStatement();           
                  ResultSet rs = stmt.executeQuery(s1);
                 
                  while(rs.next()){               
                     car_table[0]= rs.getString("name");
                     car_table[1] = rs.getString("price");
                     car_table[2] = rs.getString("pay");
                    
                     model.addRow(car_table);                
                   }
        	 }catch(SQLException ea){
        		 showMsg(ea.getMessage());
                 System.exit(0);  
                   }
        	 
                 
                       
          }
         if(e.getSource()==b7){
        	 String s1="select *from list";
        	 model.getDataVector().removeAllElements();
             revalidate();
        	 try{                               
                 Statement stmt = con.createStatement();           
                  ResultSet rs = stmt.executeQuery(s1);
                 
                  while(rs.next()){               
                     car_table[0]= rs.getString("name");
                     car_table[1] = rs.getString("price");
                     car_table[2] = rs.getString("pay");
                    
                     model.addRow(car_table);                
                   }
        	 }catch(SQLException ea){
        		 showMsg(ea.getMessage());
                 System.exit(0);  
                   }
         }
      }
public void Modify(){
	 try {
         
         Statement stmt = con.createStatement();
         String s = "delete FROM list where name=";
         s += "'" + t_name.getText() + "'";
         
         stmt.executeUpdate(s);
        
         }catch (SQLException e) {
         showMsg(e.getMessage());
         System.exit(0);
         }
	 
	 try{	
	 int pay = 0;
     Statement stmt = con.createStatement();
     PreparedStatement pstmt;
     ResultSet rs;
     
     String s1="select name from list where name= ?";            
     pstmt=con.prepareStatement(s1);         
     pstmt.setString(1, t_name.getText());                                                
     rs=pstmt.executeQuery();
  
     
     if(rs.next()){
        showMsg("중복된 차종이 있습니다.");
        return;
     }
     pay=Integer.parseInt(t_price.getText())*2;
     String s = "INSERT INTO list (name, price,pay,kind,CC,km) VALUES ";
     s += "('" + t_name.getText() + "','" + Integer.parseInt(t_price.getText()) + "',"+pay+",'"+t3.getText()+"',"+Integer.parseInt(t4.getText())+",'"+t5.getText()+"')";                        
     stmt.executeUpdate(s);
     
     
     }catch (SQLException e) {
     showMsg(e.getMessage());
     System.exit(0);
     }
	 model.getDataVector().removeAllElements();
     revalidate();
   try{                               
         Statement stmt = con.createStatement();           
          ResultSet rs = stmt.executeQuery(s1);
          
          while(rs.next()){   
            
             car_table[0]= rs.getString("name");
             car_table[1] = rs.getString("price");
             car_table[2] = rs.getString("pay");                
             model.addRow(car_table);                
           }            
          }
       catch(SQLException e){
          showMsg(e.getMessage());
          System.exit(0);
      }  
   showMsg("수정되었습니다.");
   t_name.setText("");
   t_price.setText("");
   t3.setText("");
   t4.setText("");
   t5.setText("");
   
}
public void status(int row){  
   
    String good = table.getValueAt(row,0).toString();
          
    String A = "select * from list where name= ?";        
    try {           
       PreparedStatement pstmt;
       ResultSet rs;                        
       pstmt=con.prepareStatement(A);         
       pstmt.setString(1, good);                                                
       rs=pstmt.executeQuery();   
       rs.next();  
       Filename=rs.getString("name");
       this.t_name.setText(rs.getString("name"));
       this.t3.setText(rs.getString("kind"));
       this.t_price.setText(String.valueOf(rs.getInt("price")));
       this.t4.setText(String.valueOf(rs.getInt("CC")));
       this.t5.setText(rs.getString("km"));
       
       
      
             
 } catch (SQLException e) {
    // TODO 자동 생성된 catch 블록
    e.printStackTrace();
 }          
 }
 //사진파일 경로 입력
 public String Img(String s){
    String path="C:\\Users\\Lee\\Desktop\\Java\\사진\\"+s+".jpg";
    return path;
 }
 }

