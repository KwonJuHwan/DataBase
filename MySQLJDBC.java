package MySQL_JAVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class MySQLJDBC {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		try{

			
			Class.forName("com.mysql.jdbc.Driver");

			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
														"root", "3502");

			/*��ɾ� �������̽�(CLI, Command Line Interface)*/
			System.out.println("========================================");
			System.out.println("(0) ����");
			System.out.println("(1) �����̼� ���� �� ������ �߰�");
			System.out.println("(2) ������ �̿��� �˻�");
			System.out.println("(3) �������� �̿��� �˻�");
			System.out.println("(4) �������� �̿��� �˻�");
			System.out.println("========================================");
			System.out.println("���ϴ� ��ȣ�� �Է� �Ͻÿ�");
			int num = sc.nextInt();
			sc.nextLine();
			
			stmt = conn.createStatement();
			if(num == 0) System.out.println("���α׷�����");
			if(num == 1) { /*�ǽ� 2 - 2 �����̼� ���� �� ������ �߰�*/
							
			
			StringBuilder sb = new StringBuilder();
            String sql = sb.append("create table movie (")
                    .append("id char(3),")
                    .append("title  varchar (100),")
                    .append("company  varchar (50),")
                    .append("releasedate  date,")
                    .append("country  varchar (10),")
                    .append("totalscreen  int,")
                    .append("profit  numeric (15,2),")
                    .append("totalnum  int,")
                    .append("grade     varchar (50),")
                    .append("primary key (id));").toString();

           		stmt.execute(sql);
            		
            		
            		File file = new File("movie_data.txt");
            		FileReader filereader = new FileReader(file); 
            		BufferedReader bufReader = new BufferedReader(filereader);
            		String line;
            		
      
            		
            		String prefix = "INSERT INTO movie VALUES( '";
          
            		while((line = bufReader.readLine()) != null){
            			//System.out.println(line);
            			String str2 = "";
            			int comMacnt = 0;
            			for(int i = 1; i < line.length(); i++) {
            				
            				if(line.charAt(i) == '|') {
            						comMacnt++;
            					if(comMacnt < 5) {
            						str2 = str2 + "' , '";
            					
            					}
            					else if(comMacnt == 5) {
            						str2 = str2 + "' , "; 
            					
            					}
            					else if(comMacnt > 5 && comMacnt < 8) {
            						str2 = str2 + " , "; 
            					}
            					else if(comMacnt == 8) {
            						str2 = str2 + " , '"; 
            					}
            				}
            	
            				else str2 = str2 + line.charAt(i);
            			}
            		    	str2 =prefix + str2 + "' );";
            		    	stmt.execute(str2); 
            	}
          
      		rs = stmt.executeQuery("SELECT * FROM movie");	
      		
          while(rs.next()) {
        	System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + 
        			"|" + rs.getDate(4) + "|" + rs.getString(5) + "|" + rs.getInt(6) + 
        			"|" + rs.getDouble(7) + "|" + rs.getInt(8) + "|" + rs.getString(9));
        }
          
            
	}
			
		if(num == 2) {/*�ǽ� 2 - 3 ������ �̿��� �˻�*/

			String input = sc.nextLine();  
			
			String query = "SELECT * FROM movie WHERE title LIKE '%" + input + "%';";
			rs = stmt.executeQuery(query);
			
			 while(rs.next()) {// ���
		        	System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + 
		        			"|" + rs.getDate(4) + "|" + rs.getString(5) + "|" + rs.getInt(6) + 
		        			"|" + rs.getDouble(7) + "|" + rs.getInt(8) + "|" + rs.getString(9));
		        }
			
		}
		
		if(num == 3) {/*�ǽ� 2 - 4 ���� ���� �̿��� �˻�*/
			
			int customer = sc.nextInt();
			
			String query = "SELECT * FROM movie WHERE totalnum > " + customer + ";";
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {// ���
	        	System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + 
	        			"|" + rs.getDate(4) + "|" + rs.getString(5) + "|" + rs.getInt(6) + 
	        			"|" + rs.getDouble(7) + "|" + rs.getInt(8) + "|" + rs.getString(9));
	        }
			
		}
		
		if(num == 4) {/*�ǽ�  2 - 5 ���� �ϸ� �̿��� �˻�*/
			String pre_date = sc.nextLine();
			String post_date = sc.nextLine();
			String query = "SELECT * FROM movie WHERE releasedate BETWEEN '" + pre_date + "' AND '" + post_date + "';";
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
	        	System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + 
	        			"|" + rs.getDate(4) + "|" + rs.getString(5) + "|" + rs.getInt(6) + 
	        			"|" + rs.getDouble(7) + "|" + rs.getInt(8) + "|" + rs.getString(9));
	        }
		}
		
		 stmt.close();
         conn.close();
         sc.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}