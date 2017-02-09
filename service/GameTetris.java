package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService{
	/**
	 * ����
	 */
	private GameDto dto;
	/**
	 * �����������
	 */
	private Random random=new Random();
	/**
	 * ��������
	 */
	private static final int LEVEL_UP=GameConfig.getSystemConfig().getLevelUp();
	/**
	 * �������з�����
	 */
	private static final Map<Integer,Integer> PLUS_POINT=GameConfig.getSystemConfig().getPlusPoint();
	/**
	 * �����������
	 */
	private static final int MAX_TYPE=GameConfig.getSystemConfig().getTypeConfig().size();
	
	public GameTetris(GameDto dto){
		this.dto=dto;
	}
	
	
	/**
     * ����������� ��
	 * @return 
     */
	public boolean keyUp() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
		this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
		
	}
    /**
     * ������� ��
     */
	public boolean keyDown() {
		if(this.dto.isPause()){
			return true;
		}
		//���������ƶ������ж��Ƿ��ƶ��ɹ�
		synchronized(this.dto){
		if (this.dto.getGameAct().move(0, 1,this.dto.getGameMap())) {
			return false;
		}
		// �����Ϸ��ͼ����
		boolean[][] map = this.dto.getGameMap();
		// ��ȡ�������
		Point[] act = this.dto.getGameAct().getActPoints();
		// ������ѻ�����ͼ����
		for (int i = 0; i < act.length; i++) {
			map[act[i].x][act[i].y] = true;
		}
		//�����þ���ֵ
		int exp=this.plusExp();
		//���Ӿ���ֵ
		if(exp>0){
		this.plusPoint(exp);
		}
		// ������һ�����鲢��������һ���K
		this.dto.getGameAct().init(this.dto.getNext());	
		// ���������һ������
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//����Ϸ�Ƿ�ʧ��
		if(this.isLose()){
			//������Ϸ
			this.dto.setStart(false);
		}		
		return true;
	}
		}



	/**
	 * ����Ϸ�Ƿ�ʧ��
	 */
	private boolean isLose() {
		//��õ�ǰ�����
		Point[] actPoints=this.dto.getGameAct().getActPoints();
		//���������Ϸ��ͼ
		boolean[][] map=this.dto.getGameMap();
		for(int i=0;i<actPoints.length;i++){
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
	}


	/**
	 * ��������
	 * @param exp
	 */
	private void plusPoint(int plusExp) {
		int lv=this.dto.getNowlevel();
		int rmLine=this.dto.getNowRemoveLine();
		int point=this.dto.getNowPoint();
		if(rmLine%LEVEL_UP+plusExp>=LEVEL_UP){
			this.dto.setNowlevel(++lv);
		}
		this.dto.setNowRemoveLine(rmLine+plusExp);
		
		this.dto.setNowPoint(point+PLUS_POINT.get(plusExp));
	
	}


	/**
	 * ����
	 */
	private int plusExp() {
		//�����Ϸ��ͼ
		boolean[][] map=this.dto.getGameMap();
		int exp=0;
		//ɨ����Ϸ��ͼ �鿴�Ƿ�������
		for(int y=0;y<GameDto.GAMEZONE_H;y++){
			if(isCanRemoveLine(y,map)){
				this.removeLine(y,map);
				exp++;
			}
		}
		return exp;
	}
	/**
	 * ���д���
	 * @param y
	 * @param map
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for(int x=0;x<GameDto.GAMEZONE_W;x++){
			for(int y=rowNumber;y>0;y--){
				map[x][y]=map[x][y-1];
			}
			map[x][0]=false;
			
		}
		
	}


	/**
	 * �ж�ĳһ���Ƿ������
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y,boolean[][] map){
		for(int x=0;x<GameDto.GAMEZONE_W;x++){
			if(!map[x][y]){
				//�����һ��Ϊfalseֱ������һ��
				return false;	
			}
		}
		return true;
	}


	/**
	 * ������� ��
	 * @return 
	 */
	public boolean keyLeft() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
		this.dto.getGameAct().move(-1, 0,this.dto.getGameMap());
		return true;
	}
	}
	/**
	 * ������� ��
	 * @return 
	 */
	public boolean keyRight() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
		this.dto.getGameAct().move(1, 0,this.dto.getGameMap());
		return true;
	 }
	}
@Override
public boolean keyFunUp() {
	if(this.dto.isPause()){
		return true;
	}
	//���׼�
	    this.plusPoint(4);
		return true;
	
}

@Override
public boolean keyFunDown() {
	if(this.dto.isPause()){
		return true;
	}
	//˲������
	while(!this.keyDown());
	return true;
}


@Override
public boolean keyFunLeft() {
	if(this.dto.isPause()){
		return true;
	}
	//��Ӱ����
    this.dto.changeShowShadow();
	return true;
}


@Override
public boolean keyFunRight() {
	//��ͣ
	if(this.dto.isStart()){
	this.dto.changePause();
	}
	return true;
	
}
@Override
public void startGame() {
	//���������һ������
	this.dto.setNext(random.nextInt(MAX_TYPE));
	//������ɵ�ǰ����
	this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
	//����Ϸ��Ϊ��ʼ
	this.dto.setStart(true);
	//dto��ʼ��
	this.dto.dtoInit();
}

public void mainAction() {
	this.keyDown();
}

}
