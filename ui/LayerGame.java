package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {

	/**
	 * ��λ��
	 */
	private static final int SIZE_ROL=GameConfig.getFrameConfig().getSizeRol();
	
	private static final int LEFT_SIDE=0;
	
	private static final int RIGHT_SIDE=GameConfig.getSystemConfig().getMaxX();
	
	private static final int LOSE_IDX=GameConfig.getFrameConfig().getLoseIdx();
	
	public LayerGame(int x,int y,int w,int h){
		super(x,y,w,h);
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		GameAct act=this.dto.getGameAct();
		if(act!=null){
		// ��÷������鼯��
		Point[] points = act.getActPoints();
		// ������Ӱ
		this.drawShadow(points, g);
		// ���ƻ����
		this.drawMainAct(points, g);
		}		
		// ������Ϸ��ͼ
		this.drawMap(g);
		//��ͣ
		if(this.dto.isPause()){
			this.drawImageAtCenter(Img.PAUSE, g);
			
		}
			
	}
	
	/**
     * ���ƻ����
	 * @param points 
     * @param g
     */
	private void drawMainAct(Point[] points, Graphics g) {
		//��÷������ͱ��(0-6)
		int typeCode=this.dto.getGameAct().getTypeCode();
		//���Ʒ���
		for(int i=0;i<points.length;i++){
			drawActByPoint(points[i].x,points[i].y,typeCode+1,g);	
		}

	}
	/**
	 * ���Ƶ�ͼ
	 * @param g
	 */
	private void drawMap(Graphics g) {

		//��ӡ��ͼ
		boolean[][] map=this.dto.getGameMap();
		//���㵱ǰ�ѻ���ɫ
		int lv=this.dto.getNowlevel();
		int imgIdx=lv==0?0:(lv-1)%7+1;
		for(int x=0;x<map.length;x++){
			for(int y=0;y<map[x].length;y++){
				if(map[x][y]){
					drawActByPoint(x,y,this.dto.isStart()?imgIdx:LOSE_IDX,g);
				}
			}
		}
	}
    

	/**
	 * ������Ӱ
	 * @param points
	 * @param b
	 */
	private void drawShadow(Point[] points,Graphics g) {
		
		if(!this.dto.isShowShadow()) return;
		int leftX=RIGHT_SIDE;
		int rightX=LEFT_SIDE;
		for(Point p:points){
			leftX=p.x<leftX?p.x:leftX;
			rightX=p.x>rightX?p.x:rightX;
		}
		g.drawImage(Img.SHADOW,this.x+BORDER+(leftX<<SIZE_ROL),
				   this.y+BORDER,
				   (rightX-leftX+1)<<SIZE_ROL,
				   this.h-(BORDER<<1),null);
		
	}

	/**
	 * ���������ο�
	 */
	private void drawActByPoint(int x,int y,int imgIdx,Graphics g){
		imgIdx=this.dto.isStart()?imgIdx:LOSE_IDX;
		g.drawImage(Img.ACT,
    			this.x+BORDER+(x<<SIZE_ROL),
    			this.y+BORDER+(y<<SIZE_ROL),
    			this.x+BORDER+(x+1<<SIZE_ROL),
    			this.y+BORDER+(y+1<<SIZE_ROL),
    			imgIdx<<SIZE_ROL,0,((imgIdx+1)<<SIZE_ROL),1<<SIZE_ROL,null);
		
	}
}
