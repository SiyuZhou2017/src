package ui;

import java.awt.Graphics;

public class LayerAbout extends Layer {
	
	

	public LayerAbout(int x,int y,int w,int h){
		super(x,y,w,h);
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		int imgW=Img.SIGN.getWidth(null);
		int imgH=Img.SIGN.getHeight(null);
		
		g.drawImage(Img.SIGN, this.x, this.y+6,this.w+this.x,this.y+this.h,
				    0,0,imgW,imgH,
				null);
		
	}
}
