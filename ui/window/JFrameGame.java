package ui.window;

import javax.swing.JFrame;

import util.FrameUtil;
import config.FrameConfig;
import config.GameConfig;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	public  JFrameGame(JPanelGame panelGame){
		//获取游戏配置
		FrameConfig fCfg=GameConfig.getFrameConfig();
		//设置标题
		this.setTitle(fCfg.getTitle());
	    //设置默认关闭属性（程序结束）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //设置窗口大小
		this.setSize(fCfg.getWidth(),fCfg.getHeight());
		//不允许用户改变窗口大小
		this.setResizable(false);
		//居中
		FrameUtil.setFrameCenter(this);
		
        //设置默认Panels
        this.setContentPane(panelGame);
	    //默认该窗口为显示
        this.setVisible(true);
	}
}
