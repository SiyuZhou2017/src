package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerBackground extends Layer {
	
	public LayerBackground(int x,int y,int w,int h){
		super(x,y,w,h);
	}

	@Override
	public void paint(Graphics g) {
		
		g.drawImage(Img.BG_LIST.get(this.dto.getNowlevel()%Img.BG_LIST.size()), 0,0,1162,800,null);
		

	}

}
