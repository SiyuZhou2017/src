package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer{
	/**
	 * ���������
	 */
	private static final int MAX_ROW=GameConfig.getDataConfig().getMaxRow();
	/**
	 * ��ʼY����
	 */
	private static int START_Y=0;
	
	/**
	 * ���
	 */
	private static int SPA=0;
	
	/**
	 * ֵ���⾶
	 */
	private static final int RECT_H=IMG_RECT_H+4;

	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		// �����¼���Ƽ��
		SPA = (this.h - RECT_H * MAX_ROW - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
		// ������ʼY����
		START_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}

	/**
	 * ��������ֵ��
	 * @param imgTitle
	 * @param players
	 * @param g
	 */
	public void showData(Image imgTitle,List<Player> players,Graphics g){
		//���Ʊ���
				g.drawImage(imgTitle, this.x+PADDING, this.y+PADDING,null);
				//������ݶ���
	//			List<Player> players=this.dto.getDbRecord();
		 		//��õ�ǰ����
				int nowPoint=this.dto.getNowPoint();
		 		//ѭ�����Ƽ�¼		
				for(int i=0;i<MAX_ROW;i++){
				    //��ȡһ����Ҽ�¼
					Player player = players.get(i);
					//��ȡ��ҷ���
					int recordPoint = player.getPoint();
					//���㵱ǰ�����ͼ�¼������ֵ
					double percent = (double) nowPoint / recordPoint;
					//�Ƽ�¼��ֵ��Ϊ1
					percent = percent > 1 ? 1.0 : percent;
		            //���Ƶ�����¼
					String strPoint=recordPoint==0?null:Integer.toString(recordPoint);
					this.drawRect(START_Y + i * (RECT_H + SPA), player.getName(),
							strPoint, percent, g);
				
				
				}
				
			}
		

	@Override
	abstract public void paint(Graphics g);

}
