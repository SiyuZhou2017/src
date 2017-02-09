package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {

	/**
	 * 窗口剧中
	 * @param jf
	 * @return 
	 */
	public static void setFrameCenter(JFrame jf){
		
		//居中属性  不同用户分辨率不同  首先获得用户的显示器参数
        Toolkit toolkit=Toolkit.getDefaultToolkit(); 
		Dimension screen=toolkit.getScreenSize();
		int x=screen.width-jf.getWidth()>>1;
		int y=(screen.height-jf.getHeight()>>1)-32;
		jf.setLocation(x,y);
	}
}
