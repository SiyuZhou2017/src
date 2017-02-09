package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 * 绘制窗口
 * @author siyu.Z
 *
 */
abstract public class Layer {
	/**
	 * 边框宽度
	 */
	protected static final int BORDER;
	/**
	 * 内边距
	 */
	protected static final int PADDING;
	static{
		//获得游戏配置
		FrameConfig fCfg=GameConfig.getFrameConfig();
		BORDER=fCfg.getBorder();
		PADDING=fCfg.getPadding();
	}
    /**
	* 窗口左上角x坐标
    */
	protected int x;
	/**
	 * 窗口左上角y坐标
	 */
	protected int y;
	/**
	 * 窗口宽度
	 */
	protected int w;
	/**
	 * 窗口高度
	 */
	protected int h;
	/**
	 * 游戏数据
	 */
	protected GameDto dto=null;
	
	private static int WINDOW_W=Img.WINDOW.getWidth(null);
	private static int WINDOW_H=Img.WINDOW.getHeight(null);
	
	/**
	 * 数字切片宽度
	 */
	protected static final int IMG_NUMBER_W=Img.NUMBER.getWidth(null)/10;
	/**
	 * 数字切片高度
	 */
	private static final int IMG_NUMBER_H=Img.NUMBER.getHeight(null);
	
	
	
	/**
	 * 矩形值槽高度
	 */
	protected static final int IMG_RECT_H=Img.RECT.getHeight(null);
	
	/**
	 * 矩形值槽图片宽度
	 */
	private static final int IMG_RECT_W=Img.RECT.getWidth(null);
	
	/**
	 * 经验槽宽度
	 */
	private   int rectW;	
	
	/**
	 * 默认字体
	 */
	private static final Font DEF_FONT=new Font("黑体",Font.BOLD,20);

	protected Layer(int x,int y,int w,int h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;	
		this.rectW=this.w-(PADDING<<1);
	}
	/**
	 * 绘制窗口
	 */
	protected void createWindow(Graphics g){
		//左上
				g.drawImage(Img.WINDOW,x,y,x+BORDER,y+BORDER,0,0,BORDER,BORDER,null);
				//中上
				g.drawImage(Img.WINDOW,x+BORDER,y,x+w-BORDER,y+BORDER,BORDER,0,WINDOW_W-BORDER,BORDER,null);
				//右上
				g.drawImage(Img.WINDOW,x+w-BORDER,y,x+w,y+BORDER,WINDOW_W-BORDER,0,WINDOW_W,BORDER,null);
				//左中
				g.drawImage(Img.WINDOW,x,y+BORDER,x+BORDER,y+h-BORDER,0,BORDER,BORDER,WINDOW_H-BORDER,null);
				//中中
				g.drawImage(Img.WINDOW,x+BORDER,y+BORDER,x+w-BORDER,y+h-BORDER,BORDER,BORDER,WINDOW_W-BORDER,WINDOW_H-BORDER,null);
				//右中
				g.drawImage(Img.WINDOW,x+w-BORDER,y+BORDER,x+w,y+h-BORDER,WINDOW_W-BORDER,BORDER,WINDOW_W,WINDOW_H-BORDER,null);
				//左下
				g.drawImage(Img.WINDOW,x,y+h-BORDER,x+BORDER,y+h,0,WINDOW_H-BORDER,BORDER,WINDOW_H,null);
				//中下	
				g.drawImage(Img.WINDOW,x+BORDER,y+h-BORDER,x+w-BORDER,y+h,BORDER,WINDOW_H-BORDER,WINDOW_W-BORDER,WINDOW_H,null);
				//右下
				g.drawImage(Img.WINDOW,x+w-BORDER,y+h-BORDER,x+w,y+h,WINDOW_W-BORDER,WINDOW_H-BORDER,WINDOW_W,WINDOW_H,null);
				
			
	}
	
	
	public void  setDto(GameDto dto) {
		this.dto=dto;
	}
	
	/**
	 * 刷新游戏具体内容
	 * @param g 画笔
	 */
	abstract public void paint(Graphics g);
	
	/**
	 * 显示等级数字
	 * @param x   左上角x坐标
	 * @param y   左上角y坐标
	 * @param num 显示的数字
	 * @param g   画笔对象
	 */
	protected void drawNumberLeftPad(int x,int y,int num,int maxBit,Graphics g){
		//要打印的数字转成字符串
		String strNum=Integer.toString(num);
		//循环绘制数字 右对齐
		for(int i=0;i<maxBit;i++){
			//判断是否满足绘制条件
			if(maxBit-i<=strNum.length()){
				//获得数字在字符串中下标
				int idx=i-maxBit+strNum.length();
				//取出数字中每一位
				int bit=strNum.charAt(idx)-'0';
				g.drawImage(Img.NUMBER,
						this.x+x+IMG_NUMBER_W*i,this.y+y,
						this.x+x+IMG_NUMBER_W*(i+1),this.y+y+IMG_NUMBER_H,
						bit*IMG_NUMBER_W,0,
						(bit+1)*IMG_NUMBER_W,IMG_NUMBER_H,null);
			}
			
		}
		
		
	}
	/**
	 * 绘制值槽
	 * @param title
	 * @param number
	 * @param value
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int y,String title,String number,double percent,Graphics g){
		
		//各种值初始化
		int rect_X=this.x+PADDING;
		int rect_Y=this.y+y;
		//绘制背景
		g.setColor(Color.BLACK);
		g.fillRect(rect_X, rect_Y, this.rectW, IMG_RECT_H+4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_X+1, rect_Y+1, this.rectW-2, IMG_RECT_H+2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_X+2, rect_Y+2, this.rectW-4, IMG_RECT_H);
		//绘制值槽
	
		//求出宽度
		int w=(int)( percent*(this.rectW-4));
		//求出颜色
		int subIdx=(int)(percent*IMG_RECT_W)-1;
		
		g.drawImage(Img.RECT, 
				rect_X+2, rect_Y+2,
				rect_X+2+w, rect_Y+2+IMG_RECT_H,
				subIdx, 0,subIdx+1, IMG_RECT_H,
				null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_X+4, rect_Y+24);
		if(number!=null){
			//绘制数值
		g.drawString(number, rect_X+275-11*number.length(), rect_Y+24);
		}
		
		
	}
	/**
	 * 正中绘图
	 */
	protected void drawImageAtCenter(Image img,Graphics g){
		int imgW=img.getWidth(null);
		int imgH=img.getHeight(null);
		g.drawImage(img, this.x+(this.w-imgW>>1), this.y+(this.h-imgH>>1), null);
		
	}
	
}







