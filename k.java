import java.sql.*;
import java.util.Scanner;
import java.io.*;
public class k{
	static void about(){
		System.out.println("+------------------------------------------------+");
		System.out.println("|Developer : Niranjan Kumar K                    |");
		System.out.println("+------------------------------------------------+");
		System.out.println("|Email     : hackerenvironment1514@gmail.com     |");
		System.out.println("+------------------------------------------------+");
		System.out.println("|Engine    : sqlite3                             |");
		System.out.println("+------------------------------------------------+");
		System.out.println("|Note      : system commandas allowed with ...   |");
		System.out.println("+------------------------------------------------+");
		System.out.println("\nEnjoy Learning and Practicing   !\n");
		System.out.println("Exit through _exit command      !\n");
		}
	static void system(String command)throws Exception{
			String os = System.getProperty("os.name").toLowerCase();
			ProcessBuilder pb;
			if(os.contains("win")){
			 		pb= new ProcessBuilder("cmd","/c",command);
				}else{
			 		pb= new ProcessBuilder("bash","-c",command);
				}
			pb.inheritIO();
			Process p = pb.start();
			p.waitFor();
			}
	static void lines(int n){
			for(int j=0;j<n;j++){
				System.out.print("+--------------------");
			}
			System.out.println("+");
			}
	public static void main(String[] args) throws Exception{
		Connection con = DriverManager.getConnection("jdbc:sqlite::memory:");
		Statement s = con.createStatement();
		ResultSet r;
		ResultSetMetaData rm;
		Scanner input = new Scanner(System.in);
		StringBuilder input_lines = new StringBuilder();
		about();
		while(true){
			System.out.print("::Ksql::$");

			while(true){
				String t_s =input.nextLine().trim();
				input_lines.append(t_s).append(" ");
				if((t_s.trim().endsWith(";")) || (t_s.startsWith("...")) || (t_s.startsWith("_exit"))){
								break;
							}
				System.out.print("::");
				}

			String q=input_lines.toString();
			input_lines.setLength(0);
			if(q.startsWith("_exit")){
					System.out.println("\nSuccessfully Exited from Ksql terminal ....  !\n");
					System.out.println("Niranjan Special Thanked, for using Terminal !");
					System.exit(0);
				}
			if(q.trim().length()==1){
					continue;
				}
			if(q.startsWith("...")){
				system(q.replace("...",""));
				continue;
				}
			try{
			Boolean b = s.execute(q);
			if(b){
					System.out.println();
					r=s.getResultSet();
					rm=r.getMetaData();
					int cols=rm.getColumnCount();
					lines(cols);
					for(int i=1;i<=cols;i++){
						System.out.printf("|"+"%-20s",rm.getColumnName(i));
					}
					System.out.println("|");
					while(r.next()){
						lines(cols);
						for(int i=1;i<=cols;i++){
							System.out.printf("|"+"%-20s",r.getString(i));
						}
					System.out.println("|");
					}
					lines(cols);
					System.out.println();
			}else{
				System.out.println("\nSuccesfully executed query !\n");
			}
			}
			catch(Exception e){
						String t = (e.getMessage()).replace("[SQLITE_ERROR] SQL error or missing database (","Error :: ");
						String error = t.replace(")","");
						System.out.println("\n"+error+"\n");
					}
		}
	}
}