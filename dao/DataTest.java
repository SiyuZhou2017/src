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
		players.add(new Player("小明",123));
		players.add(new Player("小明",10));
		players.add(new Player("小明",200));
		players.add(new Player("小明",3000));
		players.add(new Player("小明",10000));
		return players;
	}

	@Override
	public void saveData(Player player) {
		
		
	}

}
