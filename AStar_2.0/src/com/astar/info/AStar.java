package com.astar.info;

import java.awt.Color;

public abstract class AStar {
	/**
	 * 以下是常量
	 */
	// 1.曼哈顿算法类别
	public static final int MHD_ZLZW = 0;// 直来直往
	public static final int MHD_ZXXJ = 1;// 直斜相间
	public static final int MHD_BZTJ = 2;// 笔直推进

	// 2. 地图上物体种类
	public static final int CUB_GROUND = 0;// 空地
	public static final int CUB_START = 1;// 开始点
	public static final int CUB_END = 2;// 结束点
	public static final int CUB_OBS = 3;// 障碍物
	public static final int CUB_PATH = 4;// 路径
	public static final int CUB_HIS_WAY = 5;// 路径

	// 异常类型
	public static final int EXCEPTION_WARN = 0;// 路径
	public static final int EXCEPTION_INFO = 1;// 路径
	public static final int EXCEPTION_ERROR = 2;// 路径
	public static final int EXCEPTION_QUESTION = 3;// 路径

	/**
	 * 以上是常量
	 */

	public static int MHD_TYPE = AStar.MHD_BZTJ;// 曼哈顿算法类别默认笔直推进

	public static boolean OPTIMIZE_1 = true;// 是否一级优化结果
	public static boolean OPTIMIZE_2 = true;// 是否二级优化结果
	public static boolean OPTIMIZE_3 = true;// 是否三级优化结果
	public static boolean OPTIMIZE_4 = true;// 是否四级优化结果
	public static boolean OPTIMIZE_5 = true;// 是否五级优化结果
	public static boolean OPTIMIZE_6 = true;// 是否六级优化结果

	public static boolean STRAIGHT = true;// 是否能直着走
	public static boolean SKEW = true;// 是否能斜着走

	public static int MAX_REPEAT = 5;// 最大重复走的次数（越大越对很拥挤的环境有利）

	public static int MAP_SIZE = 800;// 地图大小
	public static int CUB_SIZE = 20;// 地图上每个单位大小

	public static int RANDOM_OBS_NUM = 1000;// 随机时障碍物数量
	public static int RANDOM_OBS_SPREAD = 10;// 随机时障碍物离散度（几个一丛，值越大，离散程度越小）
	public static long DRAW_RANDOM_INTERVAL = 500;// 随机绘制时，每一次的时间间隔，默认500毫秒

	public static long DRAW_PATH_INTERVAL = 20;// 绘制路径方块时的时间间隔，默认20毫秒

	public static int DRAW_TYPE_OUT = AStar.CUB_START;// 默认手工绘图时的点类型

	public static Color COLOR_LINE = Color.WHITE;
	public static Color COLOR_GROUND = Color.RED;
	public static Color COLOR_START = Color.GREEN;
	public static Color COLOR_END = Color.BLUE;
	public static Color COLOR_OBS = Color.BLACK;
	public static Color COLOR_PATH = Color.YELLOW;
	public static Color COLOR_HIS_WAY = Color.GRAY;

}
