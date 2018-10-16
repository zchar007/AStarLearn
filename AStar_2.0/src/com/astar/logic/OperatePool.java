package com.astar.logic;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.astar.ui.AStarDrawJPanel;
import com.astar.util.AStarException;
import com.astar.util.AStarUtil;
import com.astar.util.Alert;

public class OperatePool {
	private static ExecutorService operatePool = Executors.newFixedThreadPool(1000);

	public static void shutdownNow() {
		while (!operatePool.isShutdown()) {
			operatePool.shutdownNow();
		}
	}

	public static void findWay(AStarDrawJPanel drawPanel) {
		operatePool.execute(new Runnable() {
			public void run() {
				int[][] map = drawPanel.getMap();
				try {
					HashMap<String, NodeItem> nodesMap = AStarUtil.initGrid(map);
					// NodeItem start = FindWay.getWay(nodesMap);
					try {
						AStarFindWay.FindWay(nodesMap);
					} catch (Exception e) {
						e.printStackTrace();
						NodeItem currentNode = nodesMap.get("start");
						while(null != currentNode && !currentNode.isEnd()){
							System.out.println("½á¹û¡ª¡ª¡ª¡ª£º"+currentNode);
							currentNode = currentNode.getNext();
						}
					}
					NodeItem start = nodesMap.get("start");

					drawPanel.drawPath(start);
					drawPanel.drawTXT(nodesMap);
				} catch (AStarException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

}
