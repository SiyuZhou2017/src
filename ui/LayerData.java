package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer{
	/**
	 * 最大数据行
	 */
	private static final int MAX_ROW=GameConfig.getDataConfig().getMaxRow();
	/**
	 * 起始Y坐标
	 */
	private static int START_Y=0;
	
	/**
	 * 间距
	 */
	private static int SPA=0;
	
	/**
	 * 值槽外径
	 */
	private static final int RECT_H=IMG_RECT_H+4;

	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		// 计算记录绘制间距
		SPA = (this.h - RECT_H * MAX_ROW - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
		// 计算起始Y坐标
		START_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}

	/**
	 * 绘制所有值槽
	 * @param imgTitle
	 * @param players
	 * @param g
	 */
	public void showData(Image imgTitle,List<Player> players,Graphics g){
		//绘制标题
				g.drawImage(imgTitle, this.x+PADDING, this.y+PADDING,null);
				//获得数据对象
	//			List<Player> players=this.dto.getDbRecord();
		 		//获得当前分数
				int nowPoint=this.dto.getNowPoint();
		 		//循环绘制记录		
				for(int i=0;i<MAX_ROW;i++){
				    //获取一条玩家记录
					Player player = players.get(i);
					//获取玩家分数
					int recordPoint = player.getPoint();
					//计算当前分数和记录分数比值
					double percent = (double) nowPoint / recordPoint;
					//破纪录比值设为1
					percent = percent > 1 ? 1.0 : percent;
		            //绘制单条记录
					String strPoint=recordPoint==0?null:Integer.toString(recordPoint);
					this.drawRect(START_Y + i * (RECT_H + SPA), player.getName(),
							strPoint, percent, g);
				
				
				}
				
			}
		

	@Override
	abstract public void paint(Graphics g);

}
