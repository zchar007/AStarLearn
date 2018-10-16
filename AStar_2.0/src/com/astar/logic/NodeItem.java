package com.astar.logic;

import java.util.HashMap;

import com.astar.info.AStar;
import com.astar.util.AStarException;
import com.astar.util.AStarUtil;

public class NodeItem {
	/**
	 * 静态值 值勿动，牵扯到运算逻辑
	 */
	public static int CHILD_WAY_UP = 1;
	public static int CHILD_WAY_DOWN = 2;
	public static int CHILD_WAY_RIGHT = 3;
	public static int CHILD_WAY_LEFT = 4;
	public static int CHILD_WAY_RIGHT_UP = 31;
	public static int CHILD_WAY_LEFT_UP = 41;
	public static int CHILD_WAY_RIGHT_DOWN = 32;
	public static int CHILD_WAY_LEFT_DOWN = 42;

	/**
	 * 固定属性
	 */
	private final int x;// x轴
	private final int y;// y轴
	private NodeItem start;// 下一个
	private NodeItem end;// 下一个
	/**
	 * 可变属性
	 */
	private NodeItem father;// 上一个
	private NodeItem next;// 上一个

	private int frequency = 0;// 路过次数
	private boolean isObs;// 是不是墙
	private boolean isEnd; // 是不是终点
	private boolean isStart;// 是不是起点
	private boolean isPath;// 是不是路径
	private boolean isGround;// 是不是地面
	private boolean isHisWay;// 是不是历史路径

	private HashMap<Integer, NodeItem> childNodes;
	private int gCost;
	private int hCost = -1;// 路径上的顺序
	
	private boolean isFindWay = false;

	public NodeItem(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.toGround();
	}

	public int getGCost() {
		if (this.gCost <= 0) {
			this.gCost = AStarUtil.getDistance(this, this.end);
		}
		return this.gCost;
	}

	// 获取距离起始节点的移动量
	public int getHCost() {
		return this.hCost;
	}

	public HashMap<Integer, NodeItem> getChildNodes(HashMap<String, NodeItem> hm) {
		if (null == childNodes || childNodes.size() == 0) {

			if (AStar.STRAIGHT) {
				childNodes = new HashMap<>();
				// 上
				NodeItem n_up = hm.get(this.x + "_" + (this.y - 1));
				// 下
				NodeItem n_down = hm.get(this.x + "_" + (this.y + 1));
				// 左
				NodeItem n_left = hm.get((this.x - 1) + "_" + this.y);
				// 右
				NodeItem n_right = hm.get((this.x + 1) + "_" + this.y);

				if (null != n_up) {
					childNodes.put(NodeItem.CHILD_WAY_UP, n_up);
				}
				if (null != n_down) {
					childNodes.put(NodeItem.CHILD_WAY_DOWN, n_down);
				}
				if (null != n_left) {
					childNodes.put(NodeItem.CHILD_WAY_LEFT, n_left);
				}
				if (null != n_right) {
					childNodes.put(NodeItem.CHILD_WAY_RIGHT, n_right);
				}
			}

			if (AStar.SKEW) {
				// 左上
				NodeItem n_left_up = hm.get((this.x - 1) + "_" + (this.y - 1));
				// 左下
				NodeItem n_left_down = hm.get((this.x - 1) + "_" + (this.y + 1));
				// 右上
				NodeItem n_right_up = hm.get((this.x + 1) + "_" + (this.y - 1));
				// 右下
				NodeItem n_right_down = hm.get((this.x + 1) + "_" + (this.y + 1));

				if (null != n_left_up) {
					childNodes.put(NodeItem.CHILD_WAY_LEFT_UP, n_left_up);
				}
				if (null != n_left_down) {
					childNodes.put(NodeItem.CHILD_WAY_LEFT_DOWN, n_left_down);
				}
				if (null != n_right_up) {
					childNodes.put(NodeItem.CHILD_WAY_RIGHT_UP, n_right_up);
				}
				if (null != n_right_down) {
					childNodes.put(NodeItem.CHILD_WAY_RIGHT_DOWN, n_right_down);
				}
			}
		}
		return childNodes;
	}

	public NodeItem getFather() {
		return this.father;
	}

	public void setFather(NodeItem father) {
		this.father = father;
	}

	public NodeItem getNext() {
		return next;
	}

	public void setNext(NodeItem next) throws AStarException {
		this.next = next;
		//this.next.father = this;

		int p_t = this.next.getPosition(this);
		if (p_t == NodeItem.CHILD_WAY_DOWN || p_t == NodeItem.CHILD_WAY_UP || p_t == NodeItem.CHILD_WAY_RIGHT
				|| p_t == NodeItem.CHILD_WAY_LEFT) {
			this.next.hCost = this.getHCost() + 10;
		} else if (p_t == NodeItem.CHILD_WAY_RIGHT_DOWN || p_t == NodeItem.CHILD_WAY_RIGHT_UP
				|| p_t == NodeItem.CHILD_WAY_LEFT_DOWN || p_t == NodeItem.CHILD_WAY_LEFT_UP) {
			this.next.hCost = this.getHCost() + 14;
		} else {
			throw new AStarException("NodeItem.getPosition：" + p_t);
		}
		if (!this.next.isEnd() && !this.next.isStart()) {
			this.next.toPath();
		}
	}

	public int getFrequency() {
		return frequency;
	}

	public void addFrequency() {
		this.frequency++;
	}

	public void reduceFrequency() {
		this.frequency--;
	}

	public void resetFrequency() {
		this.frequency = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isObs() {
		return isObs;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public boolean isStart() {
		return isStart;
	}

	public boolean isPath() {
		return isPath;
	}

	public boolean isGround() {
		return isGround;
	}

	public boolean isHisWay() {
		return isHisWay;
	}

	public void toObs() {
		this.isObs = true;
		this.isEnd = false;
		this.isStart = false;
		this.isPath = false;
		this.isGround = false;
		this.isHisWay = false;
	}

	public void toEnd() {
		this.isObs = false;
		this.isEnd = true;
		this.isStart = false;
		this.isPath = false;
		this.isGround = false;
		this.isHisWay = false;
	}

	public void toStart() {
		this.isObs = false;
		this.isEnd = false;
		this.isStart = true;
		this.isPath = false;
		this.isGround = false;
		this.isHisWay = false;
	}

	public void toPath() {
		this.isObs = false;
		this.isEnd = false;
		this.isStart = false;
		this.isPath = true;
		this.isGround = false;
		this.isHisWay = false;
	}

	public void toGround() {
		this.isObs = false;
		this.isEnd = false;
		this.isStart = false;
		this.isPath = false;
		this.isGround = true;
		this.isHisWay = false;
	}

	public void toHisWay() {
		this.isObs = false;
		this.isEnd = false;
		this.isStart = false;
		this.isPath = false;
		this.isGround = false;
		this.isHisWay = true;
	}

	public NodeItem getStart() {
		return start;
	}

	public void setStart(NodeItem start) {
		this.start = start;
		if (this.start.equals(this)) {
			this.hCost = 0;
		}
	}

	public NodeItem getEnd() {
		return end;
	}

	public void setEnd(NodeItem end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "NodeItem [x=" + x + ", y=" + y + ", frequency=" + frequency + ", isObs=" + isObs + ", isEnd=" + isEnd
				+ ", isStart=" + isStart + ", isPath=" + isPath + ", isGround=" + isGround + ", isHisWay=" + isHisWay
				+ ", hCost=" + hCost + "]";
	}

	/**
	 * 判断此点是在传入点哪个位置
	 * 
	 * @return
	 */
	private int getPosition(NodeItem node) {
		int x1 = this.getX();
		int y1 = this.getY();

		int x2 = node.getX();
		int y2 = node.getY();

		int h_t = 0, v_t = 0;
		if (x1 > x2) {
			h_t = NodeItem.CHILD_WAY_RIGHT;
		} else if (x1 < x2) {
			h_t = NodeItem.CHILD_WAY_LEFT;
		}

		if (y1 > y2) {
			v_t = NodeItem.CHILD_WAY_DOWN;
		} else if (y1 < y2) {
			v_t = NodeItem.CHILD_WAY_UP;
		}
		int p_t = h_t * 10 + v_t;
		return p_t % 10 == 0 ? h_t : p_t;
	}

	public boolean isFindWay() {
		return isFindWay;
	}

	public void setFindWay(boolean isFindWay) {
		this.isFindWay = isFindWay;
	}

}
