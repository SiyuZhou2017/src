package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	/**
	 * �������λ��
	 */
	private static final int POINT_BIT=5;
	
	/**
	 * ��������
	 */
	private static final int LEVEL_UP=GameConfig.getSystemConfig().getLevelUp();
	
	/**
	 * ����Y����
	 */
	private  final int rmLineY;
	/**
	 * ����ֵY����
	 */
	private final int expY;

	/**
	 * ����X����	
	 */
    private  final int pointX; 
    
    
	public LayerPoint(int x,int y,int w,int h){
		super(x,y,w,h);
		//��ʼ��������ʾ��X����
		this.pointX=this.w-IMG_NUMBER_W*POINT_BIT-PADDING;
		//��ʼ������Y����
		this.rmLineY=Img.RMLINE.getHeight(null)+(PADDING<<1);
		//��ʼ������ֵY����
		this.expY=this.rmLineY+Img.RMLINE.getHeight(null)+PADDING;
		
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		//���ڱ���(����)
		g.drawImage(Img.POINT, this.x+PADDING, this.y+PADDING,null);
		
		this.drawNumberLeftPad(pointX, PADDING, this.dto.getNowPoint(), POINT_BIT, g);
		//���ڱ���(����)
		g.drawImage(Img.RMLINE, this.x+PADDING, this.y+rmLineY,null);
		//��ʾ����
		this.drawNumberLeftPad(pointX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
	    //����ֵ��
		drawRect(expY,"��һ��",null,(double)this.dto.getNowRemoveLine()%LEVEL_UP/(double)LEVEL_UP,g);
		
	}
	
	
	
}
