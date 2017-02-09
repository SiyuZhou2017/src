package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author admin
 *
 */
public interface Data {

	/**
	 * 获得数据
	 * @return
	 */
	List<Player> loadData();
	/**
	 * 存入数据
	 */
	void saveData(Player player);
	
	
}
