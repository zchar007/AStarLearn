package com.astar.logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.astar.info.AStar;
import com.astar.util.AStarException;

public class AStarFindWay {
	/**
	 * ��ʱ�����ظ���·���������ڵ������������ԭ·������������������������ѭ��
	 * 
	 * @param nodes
	 * @return
	 * @throws AStarException
	 */
	public static NodeItem FindWay(HashMap<String, NodeItem> nodes) throws AStarException {
		NodeItem currentNode = nodes.get("start");
		System.out.println(currentNode);

		currentNode.addFrequency();
		// ѭ��������һ��
		while (!currentNode.isEnd()) {
			NodeItem currentNodeTemp = AStarFindWay.getNext(currentNode, nodes);
			if (null == currentNodeTemp) {
				throw new AStarException("�޽⣬�볢������Ѱ·����ظ���������");
			}
			currentNodeTemp.addFrequency();

			// �������¹�ϵ
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
				throw new AStarException("�޽⣡");
			}
		}

		for (int i = arrayList.size() - 1; i > 0; i--) {
			arrayList.get(i).setNext(arrayList.get(i - 1));
		}

		return nodes.get("start");
	}

	/**
	 * ��ȡĳ�ڵ����һ��ѡ��
	 * 
	 * @param currentNode
	 * @param nodes
	 * @return
	 * @throws AStarException
	 */
	private static NodeItem getNext(NodeItem currentNode, HashMap<String, NodeItem> nodes) throws AStarException {
		// ��ȡcurrentNode���������ҽڵ�
		HashMap<Integer, NodeItem> childNodes = currentNode.getChildNodes(nodes);
		ArrayList<NodeItem> childNodes_Temp = new ArrayList<>();
		NodeItem currentNode_Temp = null;

		for (NodeItem nodeItem : childNodes.values()) {

			// ���յ�ֱ�ӷ���
			if (nodeItem.isEnd()) {
				nodeItem.setFather(currentNode);
				return nodeItem;
			}
			// ��ǽ����ʼ�㣬·���㣬��ֹ����ѭ��
			if (nodeItem.isObs() || nodeItem.isStart() || nodeItem.isHisWay()) {
				continue;
			}

			// ѭ�������������ֵ������õ�
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
			throw new AStarException("�޽⣬�볢�Ը��ġ�Ѱ··�����͡���");
		}
		currentNode_Temp = childNodes_Temp.get(0);
		int cost0 = currentNode_Temp.getHCost() + currentNode_Temp.getGCost();

		for (int i = 1; i < childNodes_Temp.size(); i++) {
			NodeItem nowNode = currentNode_Temp;
			NodeItem nodeItem = childNodes_Temp.get(i);

			// ��·�߾�������
			// ѭ�������������ֵ������õ�
			if (nodeItem.getFrequency() > AStar.MAX_REPEAT) {
				// return null;
			}

			// �Ȳ��������������ٵ�����
			if (currentNode_Temp.getFrequency() > nodeItem.getFrequency()) {
				nowNode = nodeItem;
				continue;
			}

			int costN = nodeItem.getHCost() + nodeItem.getGCost();
			// ��ѭ��������������С
			if (cost0 > costN) {
				nowNode = nodeItem;
				cost0 = costN;
			} else if (cost0 < costN) {

			} else {
				// ������ͬ��ѭ�������������ٵĸ�����
				if (nowNode.getFrequency() > nodeItem.getFrequency()) {
					nowNode = nodeItem;
					cost0 = costN;
				}
			}

			// ���������ӽڵ���δ��·����δ��·���϶࣬δѡ·������·���ĵ�
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
	 * ������·���Ż������������Ҫ�������
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
