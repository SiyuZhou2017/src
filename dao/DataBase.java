package dao;

import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataBase implements Data{

	
	private final String dbUrl;
	
	private final String dbUser;
	
	private final String dbPwd;
	
	private static String LOAD_SQL="SELECT TOP 5 user_name point FROM¡¡user_point ORDER BY point";
	


	public DataBase(HashMap<String,String> param){
		this.dbUrl=param.get("dbUrl");
		this.dbUser=param.get("dbUser");
		this.dbPwd=param.get("dbPwd");
		try {
			Class.forName(param.get("driver"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public List<Player> loadData() {
		
		return null;
	}

	@Override
	public void saveData(Player player) {
		
		
	}
	
	
	
	
//	public List<Player> loadData() {
//
//		PreparedStatement stmt=null;
//		ResultSet rs=null;
//        Connection conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PWD);
//		stmt=conn.prepareStatement(LOAD_SQL);
//		rs=stmt.executeQuery();
//	    while(rs.next()){
//		    rs.getString(1);
//		  
//		  
//	¡¡¡¡  } 
//		
//		return null;
//	}
//
//	@Override
//	public void saveData(List<Player> players) {
//		
//		
//	}

}
