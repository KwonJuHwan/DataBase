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

			/*명령어 인터페이스(CLI, Command Line Interface)*/
			System.out.println("========================================");
			System.out.println("(0) 종료");
			System.out.println("(1) 릴레이션 생성 및 데이터 추가");
			System.out.println("(2) 제목을 이용한 검색");
			System.out.println("(3) 관객수를 이용한 검색");
			System.out.println("(4) 개봉일을 이용한 검색");
			System.out.println("========================================");
			System.out.println("원하는 번호를 입력 하시오");
			int num = sc.nextInt();
			sc.nextLine();
			
			stmt = conn.createStatement();
			if(num == 0) System.out.println("프로그램종료");
			if(num == 1) { /*실습 2 - 2 릴레이션 생성 및 데이터 추가*/
							
			
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
			
		if(num == 2) {/*실습 2 - 3 제목을 이용한 검색*/

			String input = sc.nextLine();  
			
			String query = "SELECT * FROM movie WHERE title LIKE '%" + input + "%';";
			rs = stmt.executeQuery(query);
			
			 while(rs.next()) {// 출력
		        	System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + 
		        			"|" + rs.getDate(4) + "|" + rs.getString(5) + "|" + rs.getInt(6) + 
		        			"|" + rs.getDouble(7) + "|" + rs.getInt(8) + "|" + rs.getString(9));
		        }
			
		}
		
		if(num == 3) {/*실습 2 - 4 관객 수를 이용한 검색*/
			
			int customer = sc.nextInt();
			
			String query = "SELECT * FROM movie WHERE totalnum > " + customer + ";";
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {// 출력
	        	System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + 
	        			"|" + rs.getDate(4) + "|" + rs.getString(5) + "|" + rs.getInt(6) + 
	        			"|" + rs.getDouble(7) + "|" + rs.getInt(8) + "|" + rs.getString(9));
	        }
			
		}
		
		if(num == 4) {/*실습  2 - 5 개봉 일를 이용한 검색*/
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