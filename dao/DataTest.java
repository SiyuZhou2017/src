package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataTest implements Data{
	
	public DataTest(HashMap<String,String> param){
		
	}
	

	@Override
	public List<Player> loadData() {
		
		List<Player> players=new ArrayList<Player>();
		players.add(new Player("С��",123));
		players.add(new Player("С��",10));
		players.add(new Player("С��",200));
		players.add(new Player("С��",3000));
		players.add(new Player("С��",10000));
		return players;
	}

	@Override
	public void saveData(Player player) {
		
		
	}

}
