import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class EmpTableDisplay implements ActionListener
{ 
 TextField t1,t2,t3;
 Button b1,b2,b3,b4,b5;
 Frame f; 
 ResultSet rs; Connection con; Statement st;
 EmpTableDisplay()
  {
		f=new Frame("Dept NutShell");
		f.setVisible(true);
		f.setSize(250,300);
		f.setBackground(Color.yellow);
		f.setLayout(new FlowLayout());
		
	    t1=new TextField(18); //t1.addTextListener(this);
	 	t2=new TextField(18); //t2.addTextListener(this);
		t3=new TextField(18); //t3.addTextListener(this);
		
		f.add(new Label("DeptNo :")); f.add(t1);
		f.add(new Label("DName:")); f.add(t2); 
		f.add(new Label("   Dloc:  ")); f.add(t3);
		
		b1=new Button("First "); b1.addActionListener(this);
		b2=new Button("Previous"); b2.addActionListener(this);
		b3=new Button("Next"); b3.addActionListener(this);
		b4=new Button("Last"); b4.addActionListener(this);
		f.add(b1); f.add(b2); f.add(b3); f.add(b4);
		b5=new Button("Clear"); b5.addActionListener(this);
		f.add(b5);
	    
	f.addWindowListener(new WindowAdapter()
                                { 
                                   public void windowClosing(WindowEvent w)
                                   { 
                                     System.exit(1); 
                                     try
		                      {
		                        if(con!=null) con.close();
		                        if(st!=null)  st.close();
	                              }
                                      catch(Exception e3)
      	                              {  e3.printStackTrace();  System.out.println("In addWindowListener \n caught_3"); }
                                   }//windowClosing
                                 }//WindowAdapter
                            );
                 
    }//sanju()
	
public static void main(String...n)
{  
     EmpTableDisplay N=new EmpTableDisplay();
      try
      { 
           Class.forName("org.postgresql.Driver");
	   N.con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/sanju","postgres","root");
	   N.st=N.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
           N.rs=N.st.executeQuery("select * from \"myDB\".\"dept\"");
      }
      catch(Exception e1)
      { e1.printStackTrace(); System.out.println("In Main \n caught_1"); }	 
}//main
  /*public void textValueChanged(TextEvent a){  }*/
 public void actionPerformed(ActionEvent b)
 { 
 	 try
	 {
           if(b.getActionCommand().equals("First "))
	    {rs.first();
		if(rs.isFirst()) { t1.setText(rs.getString(1)); t2.setText(rs.getString(2)); t3.setText(rs.getString(3)); }
	    }
           else if(b.getActionCommand().equals("Previous"))
	    {
		  if(rs.previous()) { t1.setText(rs.getString(1)); t2.setText(rs.getString(2)); t3.setText(rs.getString(3)); }
                   else   rs.next();
	    }
           else if(b.getActionCommand().equals("Next"))
	    {
		  if(rs.next()) { t1.setText(rs.getString(1)); t2.setText(rs.getString(2)); t3.setText(rs.getString(3)); }
                   else  rs.previous();
            }
	   else if(b.getActionCommand().equals("Last"))
	    {  rs.last();
		  if(rs.isLast()) { t1.setText(rs.getString(1)); t2.setText(rs.getString(2)); t3.setText(rs.getString(3)); }
	    }
           else if(b.getActionCommand().equals("Clear"))
	    {
		  t1.setText(""); t2.setText(""); t3.setText(""); rs.beforeFirst();
	    }
	 }//try
	 catch(Exception e2)
	 { 
                e2.printStackTrace();	System.out.println("In actionPerformed \n caught_2");
	 }//catch
 }//actionPerformed
}