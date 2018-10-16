package com.astar.util;

import java.util.HashMap;
import java.util.Iterator;

import com.astar.info.AStar;
import com.astar.logic.NodeItem;

public class AStarUtil {

	/**
	 * 数组反转以适应图和int数组的不同
	 * 
	 * @param array
	 * @return
	 * @throws AStarException
	 */
	public static int[][] mapReint(int[][] array) throws AStarException {
		if (null == array || array.length == 0) {
			throw new AStarException("AStarUtil.mapReint:数组为NULL！");
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
	 * 曼哈顿算法求两点距离
	 * 
	 * @param a
	 * @param b
	 * @param type
	 * @return
	 */
	public static int getDistance(NodeItem a, NodeItem b) {
		int cntX = Math.abs(a.getX() - b.getX()) * 10;
		int cntY = Math.abs(a.getY() - b.getY()) * 10;
		// 1.直来直往
		if (AStar.MHD_TYPE == AStar.MHD_ZLZW) {
			return cntX + cntY;
		}
		// 2.笔直走
		else if (AStar.MHD_TYPE == AStar.MHD_BZTJ) {
			return (int) Math.sqrt(cntX * cntX + cntY * cntY);
		}
		// 3.转弯再笔直走
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
	 * 方法描述.初始化网格地图，用于计算
	 * 
	 * @author zchar.2018年10月14日下午2:45:16
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
			throw new AStarException("必须包含起点和终点！");
		}

		if (grid.get("start").getX() == grid.get("end").getX() && grid.get("start").getY() == grid.get("end").getY()) {
			throw new AStarException("起点终点不能重合！");
		}

		for (NodeItem node : grid.values()) {
			node.setStart(start);
			node.setEnd(end);
		}
		return grid;
	}

}
