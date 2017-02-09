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
	
	private JButton btnOk=new JButton("ȷ��");
	
	private JButton btnCancel=new JButton("ȡ��");
	
	private JButton btnUser=new JButton("Ӧ��");
	
	
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
		//�����Ϸ����������
		this.gameControl=gameControl;
		//���ò��ֹ�����Ϊ���߽粼�֡�
		this.setLayout(new BorderLayout());
		this.setTitle("����");
		//��ʼ����������
		this.initKeyText();
		//��������
		this.add(this.createMainPanel(),BorderLayout.CENTER);
		//��Ӱ�ť���
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
	 * ������ť���
	 * @return
	 */
	private JPanel createButtonPanel() {
		//������ť��� ��ʽ����
		JPanel jp=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.btnOk.addActionListener(new ActionListener(){
			//��ȷ����ť�����¼�����
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
	 * ���������(ѡ����)
	 * @return
	 */
	private JTabbedPane createMainPanel() {

		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("��������", this.createControlPanel());
		jtp.addTab("Ƥ������", this.createSkinPanel());
		return jtp;
	}
	
	/**
	 * ���Ƥ�����
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createSkinPanel() {
	
		JPanel panel=new JPanel(new BorderLayout());
		//����б�����
		this.skinData.addElement("Ĭ��");
		this.skinData.addElement("Ƥ��1");
		this.skinData.addElement("Ƥ��2");
		this.skinData.addElement("Ƥ��3");
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
	 * ��ҿ����������
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
	 * д����Ϸ����
	 */
	private boolean writeConfig(){
		HashMap<Integer,String> keySet=new HashMap<Integer,String>();
		for(int i=0;i<keyText.length;i++){
			int keyCode=this.keyText[i].getKeyCode();
			if(keyCode==0) {
				this.errorMsg.setText("���󰴼�");
				return false;
				}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if(keySet.size()!=8){
			this.errorMsg.setText("�ظ�����");
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
