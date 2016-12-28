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
   Font  font = new Font("�޸�����ü", Font.PLAIN, 40);
    JPanel panel,contentPane, info, in;
    JButton b1,b2,b3;
    JTextField pay; JTextField t1,t2,t3,t4,t5;
    JLabel L,pa;  
    JLabel picture;
    JScrollPane scrollPane;
    Connection con = makeConnection();   
    String s1="select *from list order by price";
    String picPath, Filename;
    String name[]={"�ڵ��� �̸�","�� ��","�� ��"};
    String car_table[] = new String[3];
    DefaultTableModel model = new DefaultTableModel(name,0); 
    JTable table = new JTable(model);

    ///////////////////�Һ��� Ŭ���� ����-------------------------------------------------------
    
 public Consumer(){     
	 
       setBounds(200,100,1060,600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setTitle("Consumer");
       Font  font = new Font("�޸�����ü", Font.PLAIN, 40);
       panel=new JPanel();
       panel.setBounds(80, 10, 540, 150);
       panel.setBorder(new TitledBorder(null, "���� �Է�", TitledBorder.LEADING, TitledBorder.TOP, null, null));
       panel.setLayout(null);
       
       pa= new JLabel("������ �Է����ּ���");
       panel.add(pa);
       pa.setBounds(150, 28, 120, 35);
       pay = new JTextField(10);
       panel.add(pay);
       pay.setBounds(300, 38, 57, 15);
       L= new JLabel("����");
       panel.add(L);
       L.setBounds(370, 38, 57, 15);
       b1 = new JButton("�Է�");
       panel.add(b1);
       b1.setBounds(150, 90, 80, 30);
       b1.addActionListener(this);
       
       b2 = new JButton("�ʱ�ȭ");
       panel.add(b2);
       b2.setBounds(310, 90, 80, 30);
       b2.addActionListener(this);
       b2.setEnabled(false);
       b3=new JButton("Ȩ����");
       b3.addActionListener(this);
       contentPane = new JPanel();
       
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       setContentPane(contentPane);
       contentPane.setLayout(null);
       contentPane.add(panel);
       scrollPane = new JScrollPane(table);
       scrollPane.setBounds(80, 180, 540, 271);
       scrollPane.setBorder(new TitledBorder(null, "��ü����", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
              
      
       
       JLabel carname = new JLabel("�� �̸�");
       JLabel carprice = new JLabel("���� ����");
       JLabel ratio= new JLabel("���� ����");
       JLabel l1= new JLabel("��ⷮ(CC)");
       JLabel l2= new JLabel("����(km/l)");
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
       info.setBorder(new TitledBorder(null, "���� ����", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
       picture = new JLabel("��  ��");
       picture.setHorizontalAlignment(SwingConstants.CENTER);
       picture.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        
        ImageIcon icon = new ImageIcon("C:\\����.jpg");          
        picture.setText("");
        picture.setIcon(icon);
       info.add(picture);
       picture.setBounds(10,180,300,200);
       
       
       contentPane.add(info);
       info.setBounds(650, 18, 320, 432);
     
       
       setVisible(true);       
    }
    // �����Է���  ���̺� ���� �޼ҵ� ------------------
    public void search(){
       
       model.getDataVector().removeAllElements();//���� ���̺� �ʱ�ȭ
         revalidate();                           //���� ���̺� �ʱ�ȭ
        
       try{     
          if(Integer.parseInt(pay.getText())*10000 < 20000000){
             showMsg("���� �� �� �ִ� ������ �����ϴ�.");
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
    //�������´� �˻��� ó�� ���̺�� �ʱ�ȭ��Ű�� �޼ҵ� 
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
            showMsg("������ �Է��ϼ���.");
            return;
         }
         search();
         b2.setEnabled(true); //�����˻��� �ϸ� �ʱ�ȭ��ư Ȱ��ȭ
        
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
       showMsg("����̹��� ã�� �� �����ϴ�.");
    } catch (SQLException e) {
       showMsg("���ῡ �����Ͽ����ϴ�.");
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
    // TODO �ڵ� ������ catch ���
    e.printStackTrace();
 }          
 }
 //�������� ��� �Է�
 public String Img(String s){
	String path="C:\\Users\\Lee\\Desktop\\Java\\����\\"+s+".jpg";
    return path;
 }
}
