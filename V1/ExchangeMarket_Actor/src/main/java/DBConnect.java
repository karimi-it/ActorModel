import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DBConnect() 
   {
	   try 
	   {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbank?autoReconnect=true&useSSL=false","root","!QAZ2wsx");
	 st = con.createStatement();
	   }
	   catch(Exception ex) 
	   {
		   System.out.println("Error :"+ex);
	   }
   }

	public void getData()
	{
	try 
	{
		String query = "select * from tbltest";
		rs = st.executeQuery(query);
		System.out.println("Records from Database");
		while(rs.next())
		{
			String id=rs.getString("id");
			String name = rs.getString("name");
			System.out.println("Id = "+id +" Name = "+name);
		}
	}
	catch(Exception ex)
	{
		System.out.println("Error : "+ex);
	}
	}
}
