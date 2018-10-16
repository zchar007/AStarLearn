package com.astar.util;

import java.util.HashMap;
import java.util.Iterator;

import com.astar.info.AStar;
import com.astar.logic.NodeItem;

public class AStarUtil {

	/**
	 * ���鷴ת����Ӧͼ��int����Ĳ�ͬ
	 * 
	 * @param array
	 * @return
	 * @throws AStarException
	 */
	public static int[][] mapReint(int[][] array) throws AStarException {
		if (null == array || array.length == 0) {
			throw new AStarException("AStarUtil.mapReint:����ΪNULL��");
		}
		int[][] array_temp = new int[array[0].length][array.length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array_temp[j][i] = array[i][j];
			}
		}
		return array_temp;
	}

	/**
	 * �������㷨���������
	 * 
	 * @param a
	 * @param b
	 * @param type
	 * @return
	 */
	public static int getDistance(NodeItem a, NodeItem b) {
		int cntX = Math.abs(a.getX() - b.getX()) * 10;
		int cntY = Math.abs(a.getY() - b.getY()) * 10;
		// 1.ֱ��ֱ��
		if (AStar.MHD_TYPE == AStar.MHD_ZLZW) {
			return cntX + cntY;
		}
		// 2.��ֱ��
		else if (AStar.MHD_TYPE == AStar.MHD_BZTJ) {
			return (int) Math.sqrt(cntX * cntX + cntY * cntY);
		}
		// 3.ת���ٱ�ֱ��
		else {
			if (cntX > cntY) {
				return (int) (Math.sqrt(cntY * cntY + cntY * cntY) + (cntX - cntY));
			} else {
				return (int) (Math.sqrt(cntX * cntX + cntX * cntX) + (cntY - cntX));
			}
		}
	}

	/**
	 * 
	 * ��������.��ʼ�������ͼ�����ڼ���
	 * 
	 * @author zchar.2018��10��14������2:45:16
	 */
	public static HashMap<String, NodeItem> initGrid(int[][] map) throws AStarException {
		HashMap<String, NodeItem> grid = new HashMap<>();
		NodeItem start = null, end = null;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				NodeItem node = null;
				node = new NodeItem(i, j);
				grid.put(node.getX() + "_" + node.getY(), node);
				if (map[i][j] == AStar.CUB_OBS) {
					node.toObs();
				} else if (map[i][j] == AStar.CUB_GROUND) {
					node.toGround();
				} else if (map[i][j] == AStar.CUB_START) {
					start = node;
					node.toStart();
					grid.put("start", node);
				} else if (map[i][j] == AStar.CUB_END) {
					end = node;
					node.toEnd();
					grid.put("end", node);
				}

			}
		}

		if (!grid.containsKey("start") || null == grid.get("start") || !grid.containsKey("end")
				|| null == grid.get("end")) {
			throw new AStarException("������������յ㣡");
		}

		if (grid.get("start").getX() == grid.get("end").getX() && grid.get("start").getY() == grid.get("end").getY()) {
			throw new AStarException("����յ㲻���غϣ�");
		}

		for (NodeItem node : grid.values()) {
			node.setStart(start);
			node.setEnd(end);
		}
		return grid;
	}

}
