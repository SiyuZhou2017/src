package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	/**
	 * 分数最大位数
	 */
	private static final int POINT_BIT=5;
	
	/**
	 * 升级行数
	 */
	private static final int LEVEL_UP=GameConfig.getSystemConfig().getLevelUp();
	
	/**
	 * 消行Y坐标
	 */
	private  final int rmLineY;
	/**
	 * 经验值Y坐标
	 */
	private final int expY;

	/**
	 * 分数X坐标	
	 */
    private  final int pointX; 
    
    
	public LayerPoint(int x,int y,int w,int h){
		super(x,y,w,h);
		//初始化分数显示的X坐标
		this.pointX=this.w-IMG_NUMBER_W*POINT_BIT-PADDING;
		//初始化消行Y坐标
		this.rmLineY=Img.RMLINE.getHeight(null)+(PADDING<<1);
		//初始化经验值Y坐标
		this.expY=this.rmLineY+Img.RMLINE.getHeight(null)+PADDING;
		
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		//窗口标题(分数)
		g.drawImage(Img.POINT, this.x+PADDING, this.y+PADDING,null);
		
		this.drawNumberLeftPad(pointX, PADDING, this.dto.getNowPoint(), POINT_BIT, g);
		//窗口标题(消行)
		g.drawImage(Img.RMLINE, this.x+PADDING, this.y+rmLineY,null);
		//显示消行
		this.drawNumberLeftPad(pointX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
	    //绘制值槽
		drawRect(expY,"下一级",null,(double)this.dto.getNowRemoveLine()%LEVEL_UP/(double)LEVEL_UP,g);
		
	}
	
	
	
}
