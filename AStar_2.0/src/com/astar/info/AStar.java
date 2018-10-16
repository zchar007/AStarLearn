package com.astar.info;

import java.awt.Color;

public abstract class AStar {
	/**
	 * �����ǳ���
	 */
	// 1.�������㷨���
	public static final int MHD_ZLZW = 0;// ֱ��ֱ��
	public static final int MHD_ZXXJ = 1;// ֱб���
	public static final int MHD_BZTJ = 2;// ��ֱ�ƽ�

	// 2. ��ͼ����������
	public static final int CUB_GROUND = 0;// �յ�
	public static final int CUB_START = 1;// ��ʼ��
	public static final int CUB_END = 2;// ������
	public static final int CUB_OBS = 3;// �ϰ���
	public static final int CUB_PATH = 4;// ·��
	public static final int CUB_HIS_WAY = 5;// ·��

	// �쳣����
	public static final int EXCEPTION_WARN = 0;// ·��
	public static final int EXCEPTION_INFO = 1;// ·��
	public static final int EXCEPTION_ERROR = 2;// ·��
	public static final int EXCEPTION_QUESTION = 3;// ·��

	/**
	 * �����ǳ���
	 */

	public static int MHD_TYPE = AStar.MHD_BZTJ;// �������㷨���Ĭ�ϱ�ֱ�ƽ�

	public static boolean OPTIMIZE_1 = true;// �Ƿ�һ���Ż����
	public static boolean OPTIMIZE_2 = true;// �Ƿ�����Ż����
	public static boolean OPTIMIZE_3 = true;// �Ƿ������Ż����
	public static boolean OPTIMIZE_4 = true;// �Ƿ��ļ��Ż����
	public static boolean OPTIMIZE_5 = true;// �Ƿ��弶�Ż����
	public static boolean OPTIMIZE_6 = true;// �Ƿ������Ż����

	public static boolean STRAIGHT = true;// �Ƿ���ֱ����
	public static boolean SKEW = true;// �Ƿ���б����

	public static int MAX_REPEAT = 5;// ����ظ��ߵĴ�����Խ��Խ�Ժ�ӵ���Ļ���������

	public static int MAP_SIZE = 800;// ��ͼ��С
	public static int CUB_SIZE = 20;// ��ͼ��ÿ����λ��С

	public static int RANDOM_OBS_NUM = 1000;// ���ʱ�ϰ�������
	public static int RANDOM_OBS_SPREAD = 10;// ���ʱ�ϰ�����ɢ�ȣ�����һ�ԣ�ֵԽ����ɢ�̶�ԽС��
	public static long DRAW_RANDOM_INTERVAL = 500;// �������ʱ��ÿһ�ε�ʱ������Ĭ��500����

	public static long DRAW_PATH_INTERVAL = 20;// ����·������ʱ��ʱ������Ĭ��20����

	public static int DRAW_TYPE_OUT = AStar.CUB_START;// Ĭ���ֹ���ͼʱ�ĵ�����

	public static Color COLOR_LINE = Color.WHITE;
	public static Color COLOR_GROUND = Color.RED;
	public static Color COLOR_START = Color.GREEN;
	public static Color COLOR_END = Color.BLUE;
	public static Color COLOR_OBS = Color.BLACK;
	public static Color COLOR_PATH = Color.YELLOW;
	public static Color COLOR_HIS_WAY = Color.GRAY;

}
