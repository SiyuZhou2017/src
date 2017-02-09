package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import util.FrameUtil;
import control.GameControl;


@SuppressWarnings("serial")
public class JFrameConfig extends JFrame{
	
	private JButton btnOk=new JButton("确定");
	
	private JButton btnCancel=new JButton("取消");
	
	private JButton btnUser=new JButton("应用");
	
	
	private TextCtrl[] keyText=new TextCtrl[8];
	
	private JLabel errorMsg=new JLabel();
	
	@SuppressWarnings("rawtypes")
	private JList skinList=null; 
	
	@SuppressWarnings("rawtypes")
	private DefaultListModel skinData=new DefaultListModel();
	
	private GameControl gameControl;
	
	private static final Image IMG_PSP=new ImageIcon("data/psp.jpg").getImage();
	
	private Image skinImg=new ImageIcon("graphics/view.jpg").getImage();
	
	private JPanel skinView=null;
	
	private static final String[] METHOD_NAME={
		"keyRight",
		"keyUp",
		"keyLeft",
		"keyDown",
		"keyFunLeft",
		"keyFunUp",
		"keyFunRight",
		"keyFunDown"
	};
	
	private static final String PATH="data/control.dat";

	public JFrameConfig(GameControl gameControl){
		//获得游戏控制器对象
		this.gameControl=gameControl;
		//设置布局管理器为“边界布局”
		this.setLayout(new BorderLayout());
		this.setTitle("设置");
		//初始化按键输入
		this.initKeyText();
		//添加主面板
		this.add(this.createMainPanel(),BorderLayout.CENTER);
		//添加按钮面板
		this.add(this.createButtonPanel(),BorderLayout.SOUTH);
		this.setResizable(false);
		this.setSize(1300, 900);
		FrameUtil.setFrameCenter(this);	
	}

	//20, 140, 120, 50)
	@SuppressWarnings("static-access")
	private void initKeyText() {
		int w=120;
		int x=100;
		int y=70;
		int h=50;
		for(int i=0;i<4;i++){
		keyText[i]=new TextCtrl(x,y+i*70,w,h,this.METHOD_NAME[i]);
		}
		for(int i=4;i<8;i++){
			keyText[i]=new TextCtrl(1100,y+(i-4)*70,w,h,this.METHOD_NAME[i]);
			}
		try {
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			HashMap<Integer,String> cfgSet=(HashMap)ois.readObject();
			ois.close();
			Set<Entry<Integer,String>> entrySet=cfgSet.entrySet();
			for(Entry<Integer,String> e:entrySet){
				for(TextCtrl tc:keyText){
					if(tc.getMethodName().equals(e.getValue())){
						tc.setKeyCode(e.getKey());		
					}	
				}		
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
	}

	/**
	 * 创建按钮面板
	 * @return
	 */
	private JPanel createButtonPanel() {
		//创建按钮面板 流式布局
		JPanel jp=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.btnOk.addActionListener(new ActionListener(){
			//给确定按钮增加事件监听
			public void actionPerformed(ActionEvent e) {
				if(writeConfig())
				setVisible(false);
				gameControl.setOver();
				}
			
		});
		
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		
		this.btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
				
			}	
		});
		jp.add(this.btnCancel);
		this.btnUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}	
		});
		jp.add(this.btnUser);
		return jp;
		}

	/**
	 * 创建主面板(选项卡面板)
	 * @return
	 */
	private JTabbedPane createMainPanel() {

		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("控制设置", this.createControlPanel());
		jtp.addTab("皮肤设置", this.createSkinPanel());
		return jtp;
	}
	
	/**
	 * 玩家皮肤面板
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createSkinPanel() {
	
		JPanel panel=new JPanel(new BorderLayout());
		//添加列表内容
		this.skinData.addElement("默认");
		this.skinData.addElement("皮肤1");
		this.skinData.addElement("皮肤2");
		this.skinData.addElement("皮肤3");
		this.skinList=new JList(this.skinData);
		
		this.skinView=new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				g.drawImage(skinImg,0,0,null);
				
			}
		};
		panel.add(this.skinView,BorderLayout.CENTER);
		panel.add(new JScrollPane(this.skinList),BorderLayout.WEST);
		
		
		return panel;
	}

	/**
	 * 玩家控制设置面板
	 * @return
	 */
	private JPanel createControlPanel() {
		JPanel jp=new JPanel(){
			
			{
			}	
			@Override
			public void paintComponent(Graphics g){
				g.drawImage(IMG_PSP,0,0,1300,900,null);
				
			}
		};

		jp.setLayout(null);
		for(int i=0;i<keyText.length;i++){
			jp.add(keyText[i]);	
		}
		
		return jp;
	}	
	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig(){
		HashMap<Integer,String> keySet=new HashMap<Integer,String>();
		for(int i=0;i<keyText.length;i++){
			int keyCode=this.keyText[i].getKeyCode();
			if(keyCode==0) {
				this.errorMsg.setText("错误按键");
				return false;
				}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if(keySet.size()!=8){
			this.errorMsg.setText("重复按键");
			return false;
			}
		try {
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
		    return false;
		}
		this.errorMsg.setText(null);
		return true;	
	}
		
}
