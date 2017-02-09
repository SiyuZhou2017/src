package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.GameFunction;
import config.GameConfig;
import entity.GameAct;

public class GameDto {

	
	public static final int GAMEZONE_W=GameConfig.getSystemConfig().getMaxX()+1;
	
	public static final int GAMEZONE_H=GameConfig.getSystemConfig().getMaxY()+1;
	
	private List<Player> dbRecord;
	
	private List<Player> diskRecord;
	
	private boolean[][] gameMap;
	/**
	 * 下落方块
	 */
	private GameAct gameAct;
	/**
	 * 下一个方块
	 */
	private int next;
	/**
	 * 等级
	 */
	private int nowlevel;
	
	/**
	 * 分数
	 */
	private int nowPoint;
	
	/**
	 * 线程睡眠时间
	 */
	private long sleepTime;
	
	/**
	 * 游戏是否开始
	 */
	private boolean start;
	
	/**
	 * 暂停
	 */
	private boolean pause;
	
	/**
	 * 是否显示阴影 
	 */
	private boolean showShadow;
	
	/**
	 * 消行
	 */
	private int nowRemoveLine;
	/**
	 * 构造函数
	 */
	public GameDto(){
		dtoInit();
		
	}
	/**
	 * dto初始化
	 */
	public void dtoInit(){
		
		this.gameMap=new boolean[GAMEZONE_W][GAMEZONE_H];
		this.nowRemoveLine=0;
		this.nowPoint=0;
		this.nowlevel=1;
		this.pause=false;
		this.sleepTime=GameFunction.getSleepTimeByLevel(this.nowlevel);
		
	}

	public int getNowlevel() {
		return nowlevel;
	}

	public void setNowlevel(int nowlevel) {
		this.nowlevel = nowlevel;
		//计算线程睡眠时间
		this.sleepTime=GameFunction.getSleepTimeByLevel(this.nowlevel);
	}

	public List<Player> getDbRecord() {
		return dbRecord;
	}

	public void setDbRecord(List<Player> dbRecord) {
		this.dbRecord=this.fillRecord(dbRecord);		
	}

	public List<Player> getDiskRecord() {
		return diskRecord;
	}

	public void setDiskRecord(List<Player> diskRecord) {
		this.diskRecord=this.fillRecord(diskRecord);
	}
	
	private List<Player> fillRecord(List<Player> players){
		
		if(players==null){
			players=new ArrayList<Player>();
	    }
		 while(players.size()<5){
			 players.add(new Player("No Data",0));
	    }
		 Collections.sort(players);
		 return players;
	}

	public boolean isPause() {
		return pause;
	}
	public void changePause() {
		this.pause = !this.pause;
	}
	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public long getSleepTime() {
		return sleepTime;
	}
	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}
	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}



	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}
	public boolean isShowShadow() {
		return this.showShadow;
	}
	

	
}
