package com.astar.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JPanel;

import com.astar.info.AStar;
import com.astar.logic.NodeItem;

public class AStarDrawJPanel extends JPanel {

	private int drawType_out;
	private int drawType_inside;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Graphics2D g;
	private Image offScreenImage; // 图形缓存
	private DrawCanvas canvas;

	private int S_X = -1, S_Y = -1, E_X = -1, E_Y = -1;
	private int[][] map;

	private NodeItem nowPathNode;

	/**
	 * Create the panel.
	 */
	public AStarDrawJPanel() {
		init();
	}

	// 初始化
	public void init() {
		this.removeAll();
		this.setLayout(null);

		drawType_out = AStar.DRAW_TYPE_OUT;
		drawType_inside = AStar.CUB_PATH;
		image = new BufferedImage(AStar.MAP_SIZE, AStar.MAP_SIZE, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		offScreenImage = null; // 图形缓存
		canvas = new DrawCanvas();
		S_X = -1;
		S_Y = -1;
		E_X = -1;
		E_Y = -1;
		map = new int[AStar.MAP_SIZE / AStar.CUB_SIZE][AStar.MAP_SIZE / AStar.CUB_SIZE];
		nowPathNode = null;

		this.setPreferredSize(new Dimension(AStar.MAP_SIZE, AStar.MAP_SIZE));
		canvas.setBounds(0, 0, AStar.MAP_SIZE, AStar.MAP_SIZE);
		this.add(canvas);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				drawForOut(x, y);
			}
		});
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				drawForOut(x, y);
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println(this.getClass().getName() + ":" + x + "," + y);
			}
		});
		g.setColor(AStar.COLOR_LINE);
		g.fillRect(0, 0, AStar.MAP_SIZE, AStar.MAP_SIZE);
		g.setColor(AStar.COLOR_GROUND);

		for (int i = 0; i < AStar.MAP_SIZE / AStar.CUB_SIZE; i++) {
			for (int j = 0; j < AStar.MAP_SIZE / AStar.CUB_SIZE; j++) {
				g.fillRect(i * AStar.CUB_SIZE, j * AStar.CUB_SIZE, AStar.CUB_SIZE - 1, AStar.CUB_SIZE - 1);
			}
		}
		canvas.repaint();
		// canvas.validate();
		//
		// this.repaint();
		// this.validate();
	}

	/**
	 * 画路径
	 * 
	 * @param node
	 */
	public void drawPath(NodeItem node) {
		NodeItem currentNode = node;
		this.nowPathNode = node;
		while (null != currentNode && !currentNode.isEnd()) {
			try {
				Thread.sleep(AStar.DRAW_PATH_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.drawType_inside = AStar.CUB_PATH;
			drawForInside(currentNode.getX(), currentNode.getY());
			currentNode = currentNode.getNext();
		}
	}

	/**
	 * 清除路径
	 */
	public void clearPath() {
		if (null != this.nowPathNode) {
			this.drawType_inside = AStar.CUB_GROUND;
			NodeItem currentNode = this.nowPathNode;
			while (null != currentNode && !currentNode.isEnd()) {
				try {
					Thread.sleep(AStar.DRAW_PATH_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("消除：" + currentNode);
				if (!currentNode.isStart()) {
					drawForInside(currentNode.getX(), currentNode.getY());
				}
				currentNode = currentNode.getNext();
			}
		}
		this.nowPathNode = null;
	}

	/**
	 * 外部画图用,因此传入的x,y是像素位置，所以除以AStar.CUB_SIZE在调用
	 * 
	 * @param x
	 * @param y
	 */
	public boolean drawForOut(int x, int y) {
		return drawPoint(x / AStar.CUB_SIZE, y / AStar.CUB_SIZE, drawType_out);
	}

	/**
	 * 内部画图用，传入x,y是int数组的坐标，
	 * 
	 * @param x
	 * @param y
	 */
	public boolean drawForInside(int x, int y) {
		return drawPoint(x, y, drawType_inside);
	}

	/**
	 * 画图
	 * 
	 * @param x
	 * @param y
	 * @param drawType
	 * @return
	 */
	private boolean drawPoint(int x, int y, int drawType) {
		if (x < 0 || y < 0 || x >= AStar.MAP_SIZE / AStar.CUB_SIZE || y >= AStar.MAP_SIZE / AStar.CUB_SIZE) {
			return false;
		}

		switch (drawType) {
			case AStar.CUB_START: {
				if (S_X >= 0 && S_Y >= 0 && S_X < map.length && S_Y < map.length) {
					map[S_X][S_Y] = AStar.CUB_GROUND;
					g.setColor(AStar.COLOR_GROUND);
					g.fillRect(S_X * AStar.CUB_SIZE, S_Y * AStar.CUB_SIZE, AStar.CUB_SIZE - 1, AStar.CUB_SIZE - 1);
				}
				S_X = x;
				S_Y = y;
				map[S_X][S_Y] = AStar.CUB_START;
				g.setColor(AStar.COLOR_START);
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						System.out.print("[" + map[i][j] + "] ,");
					}
					System.out.println();
				}
			}
				break;
			case AStar.CUB_END: {
				if (E_X >= 0 && E_Y >= 0 && E_X < map.length && E_Y < map.length) {
					map[E_X][E_Y] = AStar.CUB_GROUND;
					g.setColor(AStar.COLOR_GROUND);
					g.fillRect(E_X * AStar.CUB_SIZE, E_Y * AStar.CUB_SIZE, AStar.CUB_SIZE - 1, AStar.CUB_SIZE - 1);
				}
				E_X = x;
				E_Y = y;
				map[E_X][E_Y] = AStar.CUB_END;

				g.setColor(AStar.COLOR_END);
			}
				break;
			case AStar.CUB_OBS: {
				if (map[x][y] != AStar.CUB_GROUND) {
					return false;
				}
				map[x][y] = AStar.CUB_OBS;
				g.setColor(AStar.COLOR_OBS);
			}
				break;
			case AStar.CUB_GROUND: {
				if (map[x][y] == AStar.CUB_END) {
					E_X = -1;
					E_Y = -1;
				}
				if (map[x][y] == AStar.CUB_START) {
					S_X = -1;
					S_Y = -1;
				}
				map[x][y] = AStar.CUB_GROUND;
				g.setColor(AStar.COLOR_GROUND);
			}
				break;
			case AStar.CUB_PATH: {
				if (map[x][y] != AStar.CUB_GROUND) {
					return false;
				}
				g.setColor(AStar.COLOR_PATH);
			}
				break;
			case AStar.CUB_HIS_WAY: {
				if (map[x][y] != AStar.CUB_GROUND) {
					return false;
				}
				g.setColor(AStar.COLOR_HIS_WAY);
			}
				break;
			default:
				break;
		}
		g.fillRect(x * AStar.CUB_SIZE, y * AStar.CUB_SIZE, AStar.CUB_SIZE - 1, AStar.CUB_SIZE - 1);
		canvas.repaint();
		return true;
	}

	public void drawTXT(HashMap<String, NodeItem> hmNodes) {

		for (NodeItem nodeItem : hmNodes.values()) {
			int x = nodeItem.getX();
			int y = nodeItem.getY();
			g.setColor(Color.BLACK);
			Font font1 = new Font("Times New Roman", Font.BOLD, 14);
			Font font2 = new Font("SansSerif", Font.ITALIC, AStar.CUB_SIZE / 4);
			Font font3 = new Font("Serif", Font.PLAIN, 60);
			g.setFont(font2);
			g.drawString((nodeItem.getHCost() + nodeItem.getGCost()) + "", x * AStar.CUB_SIZE,
					y * AStar.CUB_SIZE + AStar.CUB_SIZE / 4);
			g.drawString(nodeItem.getHCost() + "", x * AStar.CUB_SIZE, y * AStar.CUB_SIZE + AStar.CUB_SIZE / 4 * 2);
			g.drawString(nodeItem.getGCost() + "", x * AStar.CUB_SIZE, y * AStar.CUB_SIZE + AStar.CUB_SIZE / 4 * 3);

		}
		canvas.repaint();
	}

	public int getDrawType_out() {
		return drawType_out;
	}

	public void setDrawType_out(int drawType_out) {
		this.drawType_out = drawType_out;
	}

	public int getDrawType_inside() {
		return drawType_inside;
	}

	public void setDrawType_inside(int drawType_inside) {
		this.drawType_inside = drawType_inside;
	}

	public int[][] getMap() {
		return this.map;
	}

	class DrawCanvas extends Canvas {
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 0, 0, AStar.MAP_SIZE, AStar.MAP_SIZE, null);
		}

		@Override
		public void update(Graphics g) {
			if (offScreenImage == null)
				offScreenImage = this.createImage(AStar.MAP_SIZE, AStar.MAP_SIZE); // 新建一个图像缓存空间,这里图像大小为800*600
			Graphics gImage = offScreenImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
			paint(gImage); // 将要画的东西画到图像缓存空间去
			g.drawImage(offScreenImage, 0, 0, AStar.MAP_SIZE, AStar.MAP_SIZE, null); // 然后一次性显示出来
		}
	}
}
