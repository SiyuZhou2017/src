package dao;

import java.util.List;

import dto.Player;

/**
 * ���ݳ־ò�ӿ�
 * @author admin
 *
 */
public interface Data {

	/**
	 * �������
	 * @return
	 */
	List<Player> loadData();
	/**
	 * ��������
	 */
	void saveData(Player player);
	
	
}
