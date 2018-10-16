package com.astar.logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.astar.info.AStar;
import com.astar.util.AStarException;

public class AStarFindWay {
	/**
	 * 暂时允许重复走路径，但现在导致如果真走了原路径，会造成链表闭锁，陷入死循环
	 * 
	 * @param nodes
	 * @return
	 * @throws AStarException
	 */
	public static NodeItem FindWay(HashMap<String, NodeItem> nodes) throws AStarException {
		NodeItem currentNode = nodes.get("start");
		System.out.println(currentNode);

		currentNode.addFrequency();
		// 循环查找下一步
		while (!currentNode.isEnd()) {
			NodeItem currentNodeTemp = AStarFindWay.getNext(currentNode, nodes);
			if (null == currentNodeTemp) {
				throw new AStarException("无解，请尝试增大【寻路最大重复次数】！");
			}
			currentNodeTemp.addFrequency();

			// 设置上下关系
			currentNode.setNext(currentNodeTemp);
			currentNode = currentNodeTemp;
		}

		NodeItem endNode = nodes.get("end");
		ArrayList<NodeItem> arrayList = new ArrayList<>();

		currentNode = endNode;
		arrayList.add(currentNode);
		int k = 0;
		while (!currentNode.isStart()) {
			System.out.println(currentNode);
			NodeItem nowNode = currentNode;
			if (null != nowNode.getFather()) {
				arrayList.add(nowNode.getFather());
				currentNode = nowNode.getFather();
			} else {
				throw new AStarException("无解！");
			}
		}

		for (int i = arrayList.size() - 1; i > 0; i--) {
			arrayList.get(i).setNext(arrayList.get(i - 1));
		}

		return nodes.get("start");
	}

	/**
	 * 获取某节点的下一步选择
	 * 
	 * @param currentNode
	 * @param nodes
	 * @return
	 * @throws AStarException
	 */
	private static NodeItem getNext(NodeItem currentNode, HashMap<String, NodeItem> nodes) throws AStarException {
		// 获取currentNode的上下左右节点
		HashMap<Integer, NodeItem> childNodes = currentNode.getChildNodes(nodes);
		ArrayList<NodeItem> childNodes_Temp = new ArrayList<>();
		NodeItem currentNode_Temp = null;

		for (NodeItem nodeItem : childNodes.values()) {

			// 是终点直接返回
			if (nodeItem.isEnd()) {
				nodeItem.setFather(currentNode);
				return nodeItem;
			}
			// 是墙，开始点，路径点，终止本次循环
			if (nodeItem.isObs() || nodeItem.isStart() || nodeItem.isHisWay()) {
				continue;
			}

			// 循环次数多余给定值则废弃该点
			if (nodeItem.getFrequency() > AStar.MAX_REPEAT) {
				return null;
			}
			if (!nodeItem.isPath() && !nodeItem.isHisWay()) {
				nodeItem.setFather(currentNode);
				nodeItem.toHisWay();
			}
			childNodes_Temp.add(nodeItem);
		}
		if (childNodes_Temp.size() <= 0) {
			throw new AStarException("无解，请尝试更改【寻路路径类型】！");
		}
		currentNode_Temp = childNodes_Temp.get(0);
		int cost0 = currentNode_Temp.getHCost() + currentNode_Temp.getGCost();

		for (int i = 1; i < childNodes_Temp.size(); i++) {
			NodeItem nowNode = currentNode_Temp;
			NodeItem nodeItem = childNodes_Temp.get(i);

			// 有路走尽量不走
			// 循环次数多余给定值则废弃该点
			if (nodeItem.getFrequency() > AStar.MAX_REPEAT) {
				// return null;
			}

			// 先不看花销，次数少的优先
			if (currentNode_Temp.getFrequency() > nodeItem.getFrequency()) {
				nowNode = nodeItem;
				continue;
			}

			int costN = nodeItem.getHCost() + nodeItem.getGCost();
			// 本循环的这条花销更小
			if (cost0 > costN) {
				nowNode = nodeItem;
				cost0 = costN;
			} else if (cost0 < costN) {

			} else {
				// 花销相同则看循环次数，次数少的更合适
				if (nowNode.getFrequency() > nodeItem.getFrequency()) {
					nowNode = nodeItem;
					cost0 = costN;
				}
			}

			// 倾向于其子节点有未走路径，未走路径较多，未选路径，有路径的点
			HashMap<Integer, NodeItem> childNodes1 = currentNode_Temp.getChildNodes(nodes);
			HashMap<Integer, NodeItem> childNodes2 = nodeItem.getChildNodes(nodes);
			
			int path_n_1 = 0,path_n_2 = 0,f_1 = 0,f_2 = 0;
			for (NodeItem n_t : childNodes1.values()) {
				if(n_t.isPath()){
					path_n_1++;
				}
				f_1+=n_t.getFrequency();
			}
			for (NodeItem n_t : childNodes2.values()) {
				if(n_t.isPath()){
					path_n_2++;
				}
				f_2+=n_t.getFrequency();
			}
			if(path_n_1 > path_n_2){
				nowNode = currentNode_Temp;
			}else if(path_n_1 < path_n_2){
				nowNode = nodeItem;
			}else{
				if(f_2 > f_1){
					nowNode = currentNode_Temp;
				}else if(f_2 < f_1){
					nowNode = nodeItem;
				}
			}

			currentNode_Temp = nowNode;
		}
		currentNode_Temp.toPath();
		return currentNode_Temp;
	}

	/**
	 * 以下是路径优化，具体如何需要具体分析
	 * 
	 * @param nodes
	 * @return
	 */
	public static NodeItem aStarOptimize_1(NodeItem node) {
		return null;
	}

	public static NodeItem aStarOptimize_2(NodeItem node) {
		return null;
	}

	public static NodeItem aStarOptimize_3(NodeItem node) {
		return null;
	}

	public static NodeItem aStarOptimize_4(NodeItem node) {
		return null;
	}

	public static NodeItem aStarOptimize_5(NodeItem node) {
		return null;
	}

	public static NodeItem aStarOptimize_6(NodeItem node) {
		return null;
	}
}
