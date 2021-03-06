package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {

	/**
	 * 图片路径
	 */
	private static final String GRAPHICS_PATH = "graphics/";
	
	private static final String DEFAULT_PATH = "default/";
	
	private Img() {
	}

	public static void setSkin(String path) {
		String skinPath = GRAPHICS_PATH  + path;
		// 个人签名
		SIGN = new ImageIcon(skinPath + "string/sign.png").getImage();

		// 窗口图片

		WINDOW = new ImageIcon(skinPath + "window/Window.png").getImage();

		// 数字图片 260,36

		NUMBER = new ImageIcon(skinPath + "string/num.png").getImage();

		// 矩形值槽

		RECT = new ImageIcon(skinPath + "window/rect.png").getImage();

		// 数据库窗口标题

		DB = new ImageIcon(skinPath + "string/db.png").getImage();

		// 本地记录窗口标题

		DISK = new ImageIcon(skinPath + "string/disk.png").getImage();

		// 方块图片

		ACT = new ImageIcon(skinPath + "game/rect.png").getImage();

		// 等级标题

		LEVEL = new ImageIcon(skinPath + "string/level.png").getImage();

		// 窗口标题 分数

		POINT = new ImageIcon(skinPath + "string/point.png").getImage();

		// 窗口标题 消行

		RMLINE = new ImageIcon(skinPath + "string/rmline.png").getImage();

		// 阴影

		SHADOW = new ImageIcon(skinPath + "game/shadow.png").getImage();

		// 开始按钮

		BTN_START = new ImageIcon(skinPath + "string/start.png");

		// 设置按钮

		BTN_CONFIG = new ImageIcon(skinPath + "string/config.png");

		PAUSE = new ImageIcon(skinPath + "string/pause.png").getImage();

		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig()
				.size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon(skinPath + "game/" + i + ".png")
					.getImage();
		}
		File dir = new File( GRAPHICS_PATH+DEFAULT_PATH + "background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File file : files) {
			if (file.isDirectory())
				continue;
			BG_LIST.add(new ImageIcon(file.getPath()).getImage());

		}

	}

	/**
	 * 个人签名
	 */
	public static Image SIGN = null;
	/**
	 * 窗口图片
	 */
	public static Image WINDOW = null;
	/**
	 * 数字图片 260,36
	 */
	public static Image NUMBER = null;
	/**
	 * 矩形值槽
	 */
	public static Image RECT = null;
	/**
	 * 数据库窗口标题
	 */
	public static Image DB = null;
	/**
	 * 本地记录窗口标题
	 */
	public static Image DISK = null;
	/**
	 * 方块图片
	 */
	public static Image ACT = null;
	/**
	 * 等级标题
	 */
	public static Image LEVEL = null;
	/**
	 * 窗口标题 分数
	 */
	public static Image POINT = null;
	/**
	 * 窗口标题 消行
	 */
	public static Image RMLINE = null;
	/**
	 * 阴影
	 */
	public static Image SHADOW = null;
	/**
	 * 开始按钮
	 */
	public static ImageIcon BTN_START = null;
	/**
	 * 设置按钮
	 */
	public static ImageIcon BTN_CONFIG = null;

	public static Image PAUSE = null;

	/**
	 * 下一个图片数组
	 */
	public static Image[] NEXT_ACT = null;

	/**
	 * 背景数组
	 */
	public static List<Image> BG_LIST = null;
	
	/**
	 * 初始化图片
	 */
	static {
		setSkin(DEFAULT_PATH);
	}


}
