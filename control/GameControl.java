package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
/**
 * ������Ҽ����¼�
 * ���ƻ���
 * ������Ϸ
 * @author admin
 *
 */
public class GameControl {
	/**
	 * ���ݷ��ʽӿ�A
	 */
	private Data dataA;
	
	/**
	 * ���ݷ��ʽӿ�B
	 */
	private Data dataB;
	
	/**
	 * ��Ϸ�����
	 */
	private JPanelGame panelGame;
	
	/**
	 * ��Ϸ���ƴ���
	 */
	private JFrameConfig frameConfig;
	
	/**
	 * �����������
	 */
	private JFrameSavePoint frameSavePoint;
	
	/**
	 * ��Ϸ�߼���
	 */
	private GameService gameService;
	
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer,Method> actionList;
	
	/**
	 * ��Ϸ����Դ
	 */
	private GameDto dto=null;
	
	/**
	 * ��Ϸ�߳�
	 */
	private Thread gameThread=null;
	
	public GameControl(){

		//������Ϸ����Դ
		this.dto=new GameDto();
		//������Ϸ�߼��飨������Ϸ����Դ��
		this.gameService=new GameTetris(dto);
		//�������ݽӿ�A
		this.dataA=createDataObiject(GameConfig.getDataConfig().getDataA());  
		//�������ݿ��¼����Ϸ
		this.dto.setDbRecord(dataA.loadData());
		//�����ݽӿ�B��ñ��ش��̼�¼
		this.dataB=createDataObiject(GameConfig.getDataConfig().getDataB());
		//���ñ��ش��̼�¼����Ϸ
		this.dto.setDiskRecord(dataB.loadData());
		//������Ϸ���
		this.panelGame=new JPanelGame(this,dto);
		//��ȡ�û���������
		this.setControlConfig();
		//��ʼ���û����ô���
		this.frameConfig=new JFrameConfig(this);
		//��ʼ�������������
		this.frameSavePoint=new JFrameSavePoint(this);
		//��ʼ����Ϸ���ڣ���װ��Ϸ���
		new JFrameGame(this.panelGame);
        
		
	}
	/**
	 * ��ȡ��ҿ�������
	 */
	private void setControlConfig(){
	
		//�����������뷽������ӳ������
		this.actionList=new HashMap<Integer,Method>(); 
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			HashMap<Integer,String> cfgSet=(HashMap)ois.readObject();
			Set<Entry<Integer,String>> entrySet=cfgSet.entrySet();
			for(Entry<Integer,String> e:entrySet){
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		
		
	}
    
	/**
	 * �������ݶ���
	 * @param cfg
	 * @return
	 */
	private Data createDataObiject(DataInterfaceConfig cfg){
		try {
			//��������
			Class<?> cls=Class.forName(cfg.getClassName());
			//��ù�����
			Constructor<?> ctr =cls.getConstructor(HashMap.class);
			//��������
			return (Data)ctr.newInstance(cfg.getParam());
			
			} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
   
	/**
	 * ������ҿ��ƾ�����Ϊ
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {

		try {
			if(!this.actionList.containsKey(keyCode)){return;}
			this.actionList.get(keyCode).invoke(this.gameService);	
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		this.panelGame.repaint();
		
	}
	
	/**
	 * ��ʾ��ҿ��ƴ���
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}
	
	/**
	 * �Ӵ��ڹر�
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
		
	}
	
	
	/**
	 * ��ʼ��ť�¼�
	 */
	public void start() {
		//��尴ť����Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		//�رմ���
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		//ˢ�»���
		panelGame.repaint();
		//�����̶߳���
		this.gameThread=new MainThread();
		//�����߳�
		this.gameThread.start();
		//ˢ�½���
		this.panelGame.repaint();
	}

	/**
	 * �������
	 * @param name
	 */
	public void savePoint(String name) {
		Player pla=new Player(name,this.dto.getNowPoint());
		//�����¼�����ݿ�
		this.dataA.saveData(pla);
		//�����¼�����ش���
		this.dataB.saveData(pla);
		this.dto.setDbRecord(dataA.loadData());
		this.dto.setDiskRecord(dataB.loadData());
	    this.panelGame.repaint();
	}
	
	/**
	 * ʧ��֮��Ĵ���
	 */
	public void afterLose(){
		//��ʾ����÷ִ���
		this.frameSavePoint.show(this.dto.getNowPoint());
		//ʹ��ť���Ե��
		this.panelGame.buttonSwitch(true);
		
		
	}
	
	
	private class MainThread extends Thread{
		@Override
		public void run(){
			//ˢ�»���
			panelGame.repaint();
			while(dto.isStart()){				
				try {
					//�߳�˯��
					Thread.sleep(dto.getSleepTime());
					//�����ͣ��ִ������Ϊ
					if(dto.isPause()){
						continue;
					}
					//��������
					gameService.mainAction();
					//ˢ�»���
					panelGame.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			afterLose();
		}
	}	
}
