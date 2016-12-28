package car;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

class Consumer extends JFrame implements ActionListener{
   Font  font = new Font("휴먼편지체", Font.PLAIN, 40);
    JPanel panel,contentPane, info, in;
    JButton b1,b2,b3;
    JTextField pay; JTextField t1,t2,t3,t4,t5;
    JLabel L,pa;  
    JLabel picture;
    JScrollPane scrollPane;
    Connection con = makeConnection();   
    String s1="select *from list order by price";
    String picPath, Filename;
    String name[]={"자동차 이름","시 세","연 봉"};
    String car_table[] = new String[3];
    DefaultTableModel model = new DefaultTableModel(name,0); 
    JTable table = new JTable(model);

    ///////////////////소비자 클래스 시작-------------------------------------------------------
    
 public Consumer(){     
	 
       setBounds(200,100,1060,600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setTitle("Consumer");
       Font  font = new Font("휴먼편지체", Font.PLAIN, 40);
       panel=new JPanel();
       panel.setBounds(80, 10, 540, 150);
       panel.setBorder(new TitledBorder(null, "연봉 입력", TitledBorder.LEADING, TitledBorder.TOP, null, null));
       panel.setLayout(null);
       
       pa= new JLabel("연봉을 입력해주세요");
       panel.add(pa);
       pa.setBounds(150, 28, 120, 35);
       pay = new JTextField(10);
       panel.add(pay);
       pay.setBounds(300, 38, 57, 15);
       L= new JLabel("만원");
       panel.add(L);
       L.setBounds(370, 38, 57, 15);
       b1 = new JButton("입력");
       panel.add(b1);
       b1.setBounds(150, 90, 80, 30);
       b1.addActionListener(this);
       
       b2 = new JButton("초기화");
       panel.add(b2);
       b2.setBounds(310, 90, 80, 30);
       b2.addActionListener(this);
       b2.setEnabled(false);
       b3=new JButton("홈으로");
       b3.addActionListener(this);
       contentPane = new JPanel();
       
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       setContentPane(contentPane);
       contentPane.setLayout(null);
       contentPane.add(panel);
       scrollPane = new JScrollPane(table);
       scrollPane.setBounds(80, 180, 540, 271);
       scrollPane.setBorder(new TitledBorder(null, "전체차량", TitledBorder.LEADING, TitledBorder.TOP, null, null));
       contentPane.add(scrollPane);
       this.add(b3);
       b3.setBounds(360, 460, 300, 30);
       
       
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
              
      
       
       JLabel carname = new JLabel("차 이름");
       JLabel carprice = new JLabel("차량 가격");
       JLabel ratio= new JLabel("차량 종류");
       JLabel l1= new JLabel("배기량(CC)");
       JLabel l2= new JLabel("연비(km/l)");
       t1 = new JTextField(10);
       t2 = new JTextField(10);
       t3 = new JTextField(10);
       t4 = new JTextField(10);
       t5 = new JTextField(10);
       t1.setEditable(false);
       t2.setEditable(false);
       t3.setEditable(false);
       t4.setEditable(false);
       t5.setEditable(false);
       info=new JPanel();
       info.setBorder(new TitledBorder(null, "차량 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
       info.setLayout(null);
       info.add(carname);
       carname.setBounds(10, 28, 70, 15);
       info.add(t1);
       t1.setBounds(90, 28, 70, 15);
       info.add(carprice);
       carprice.setBounds(10, 56, 70, 15);
       info.add(t2);
       t2.setBounds(90, 56, 70, 15);
       info.add(ratio);
       ratio.setBounds(10, 84, 70, 15);
       info.add(t3);
       t3.setBounds(90, 84, 70, 15);
       info.add(l1);
       l1.setBounds(10, 112, 70, 15);
       info.add(t4);
       t4.setBounds(90, 112, 70, 15);
       info.add(l2);
       l2.setBounds(10, 140, 70, 15);
       info.add(t5);
       t5.setBounds(90, 140, 70, 15);
       picture = new JLabel("사  진");
       picture.setHorizontalAlignment(SwingConstants.CENTER);
       picture.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        
        ImageIcon icon = new ImageIcon("C:\\삼육.jpg");          
        picture.setText("");
        picture.setIcon(icon);
       info.add(picture);
       picture.setBounds(10,180,300,200);
       
       
       contentPane.add(info);
       info.setBounds(650, 18, 320, 432);
     
       
       setVisible(true);       
    }
    // 연봉입력후  테이블 정렬 메소드 ------------------
    public void search(){
       
       model.getDataVector().removeAllElements();//기존 테이블 초기화
         revalidate();                           //기존 테이블 초기화
        
       try{     
          if(Integer.parseInt(pay.getText())*10000 < 20000000){
             showMsg("구입 할 수 있는 차량이 없습니다.");
             car_table[0]= "-";
              car_table[1] ="-";
              car_table[2] ="-";                
              model.addRow(car_table);               
             return;
          }
          String s2="select *from list  where pay <= "+Integer.parseInt(pay.getText())*10000+" order by price"+"";
          Statement stmt = con.createStatement();           
          ResultSet rs = stmt.executeQuery(s2);
               
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
    }
    //연봉에맞는 검색후 처음 테이블로 초기화시키는 메소드 
    public void first(){
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
    }
    
    public void actionPerformed(ActionEvent e){
      if(e.getSource()==b1){
         
         if(pay.getText().trim().equals("")){
            showMsg("연봉을 입력하세요.");
            return;
         }
         search();
         b2.setEnabled(true); //연봉검색을 하면 초기화버튼 활성화
        
      }
      else if(e.getSource()==b2){
         first();
         
      }
      else if(e.getSource()==b3){
    	  setVisible(false);
          new Login();
          
       }       
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
 public void showMsg(String msg){
    JOptionPane.showMessageDialog(getParent(), msg);  
 }
 public void status(int row){  
   
    
   String good = table.getValueAt(row,0).toString();
    t1.setText(good);         
    String A = "select * from list where name= ?";        
    try {           
       PreparedStatement pstmt;
       ResultSet rs;                        
       pstmt=con.prepareStatement(A);         
       pstmt.setString(1, good);                                                
       rs=pstmt.executeQuery();   
       rs.next();  
       Filename=rs.getString("name");
       t3.setText(rs.getString("kind"));
       t2.setText(String.valueOf(rs.getInt("price")));
       t4.setText(String.valueOf(rs.getInt("CC")));
       t5.setText(rs.getString("km"));
       
       
      
             
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
