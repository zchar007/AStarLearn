package com.astar.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.astar.info.AStar;
import com.astar.logic.OperatePool;
import com.astar.util.Alert;
import java.awt.GridLayout;

public class AStarJFrame extends JFrame implements ActionListener, ItemListener, FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JSplitPane splitPane;
	private AStarDrawJPanel astarPanel;
	private JScrollPane scrollPane_show;
	private static int HEAD_HEIGHT = 20;
	private HashMap<JTextField, Integer> hmJTF = new HashMap<>();
	private JTextField jtf_map_size;
	private JTextField jtf_cub_size;
	private JTextField jtf_max_repeat;
	private JTextField jtf_draw_path_interval;
	private static final int JTF_MAP_SIZE_P = 1;
	private static final int JTF_CUB_SIZE_P = 2;
	private static final int JTF_MAX_REPEAT_P = 3;
	private static final int JTF_DRAW_PATH_INTERVAL_P = 4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// windows���
					AStarJFrame frame = new AStarJFrame();
					frame.setVisible(true);
					frame.splitPane.setDividerLocation(0.3);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AStarJFrame() {
		this.setTitle("�Զ�Ѱ·ģ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		// �˵�����
		createJMenu();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		splitPane = new JSplitPane();
		splitPane.setOpaque(false);
		splitPane.setDividerSize(3);

		contentPane.add(splitPane, BorderLayout.CENTER);

		// ������崴��
		createConfigureJPanel();
		// չʾ��崴��
		createShowJPanel();
	}

	/**
	 * �����˵�
	 */
	private void createJMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu_txt = new JMenu("�ļ�");
		menuBar.add(menu_txt);

		JMenuItem menuItem_open = new JMenuItem("��");
		menu_txt.add(menuItem_open);

		JMenu menu_svae = new JMenu("����");
		menu_txt.add(menu_svae);

		JMenuItem menuItem_savemap = new JMenuItem("�����ͼ");
		menu_svae.add(menuItem_savemap);

		JMenuItem menuItem_saveall = new JMenuItem("����ȫ��(����ͼ)");
		menu_svae.add(menuItem_saveall);

		JMenuItem menuItem_re_save = new JMenuItem("���Ϊ");
		menu_txt.add(menuItem_re_save);
	}

	/**
	 * ����������
	 */
	private void createConfigureJPanel() {
		JPanel panel_configure = new JPanel();
		splitPane.setLeftComponent(panel_configure);
		panel_configure.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_configure = new JScrollPane();
		panel_configure.add(scrollPane_configure, BorderLayout.CENTER);

		JPanel panel_configure_configure = new JPanel();
		scrollPane_configure.setViewportView(panel_configure_configure);
		panel_configure_configure.setLayout(null);

		int HEIGHT = 0;
		/**
		 * �������
		 */
		JPanel cell_panel_operate = new JPanel();
		cell_panel_operate.setBorder(BorderFactory.createTitledBorder("����"));
		cell_panel_operate.setBounds(0, HEIGHT, 215, 80 + AStarJFrame.HEAD_HEIGHT);
		panel_configure_configure.add(cell_panel_operate);
		cell_panel_operate.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btn_find = new JButton("Ѱ·");
		btn_find.addActionListener(this);
		cell_panel_operate.add(btn_find);

		JButton btn_clear_path = new JButton("���·��");
		btn_clear_path.addActionListener(this);
		cell_panel_operate.add(btn_clear_path);

		JButton btn_clear_all = new JButton("�������");
		btn_clear_all.addActionListener(this);
		cell_panel_operate.add(btn_clear_all);

		HEIGHT += 80 + AStarJFrame.HEAD_HEIGHT + 5;

		/**
		 * ��������
		 */
		JPanel cell_panel_drawType = new JPanel();
		cell_panel_drawType.setLayout(null);
		cell_panel_drawType.setBorder(BorderFactory.createTitledBorder("��������"));
		cell_panel_drawType.setBounds(0, HEIGHT, 215, 65 + AStarJFrame.HEAD_HEIGHT);

		ButtonGroup bg_drow_type = new ButtonGroup();

		JRadioButton jrb_start = new JRadioButton("���");
		jrb_start.setBounds(5, 25, 100, 25);
		cell_panel_drawType.add(jrb_start);
		bg_drow_type.add(jrb_start);
		jrb_start.addActionListener(this);

		JRadioButton jrb_end = new JRadioButton("�յ�");
		jrb_end.setBounds(110, 25, 100, 25);
		cell_panel_drawType.add(jrb_end);
		bg_drow_type.add(jrb_end);
		jrb_end.addActionListener(this);

		JRadioButton jrb_obs = new JRadioButton("�ϰ���");
		jrb_obs.setBounds(5, 55, 100, 25);
		cell_panel_drawType.add(jrb_obs);
		bg_drow_type.add(jrb_obs);
		jrb_obs.addActionListener(this);

		JRadioButton jrb_ground = new JRadioButton("��Ƥ��");
		jrb_ground.setBounds(110, 55, 100, 25);
		cell_panel_drawType.add(jrb_ground);
		bg_drow_type.add(jrb_ground);
		jrb_ground.addActionListener(this);

		switch (AStar.DRAW_TYPE_OUT) {
			case AStar.CUB_START: {
				jrb_start.setSelected(true);
			}
				break;
			case AStar.CUB_END: {
				jrb_end.setSelected(true);
			}
				break;
			case AStar.CUB_OBS: {
				jrb_obs.setSelected(true);
			}
				break;
			case AStar.CUB_GROUND: {
				jrb_ground.setSelected(true);
			}
				break;
			default:
				break;
		}

		panel_configure_configure.add(cell_panel_drawType);
		HEIGHT += 65 + AStarJFrame.HEAD_HEIGHT + 5;
		/**
		 * �������㷨
		 */
		JPanel cell_panel_mhd = new JPanel();
		cell_panel_mhd.setLayout(null);
		cell_panel_mhd.setBorder(BorderFactory.createTitledBorder("�������㷨"));
		cell_panel_mhd.setBounds(0, HEIGHT, 215, 65 + AStarJFrame.HEAD_HEIGHT);

		ButtonGroup bg_mhd = new ButtonGroup();

		JRadioButton jrb_zlzw = new JRadioButton("ֱ��ֱ��");
		jrb_zlzw.setBounds(5, 25, 100, 25);
		cell_panel_mhd.add(jrb_zlzw);
		bg_mhd.add(jrb_zlzw);
		jrb_zlzw.addActionListener(this);

		JRadioButton jrb_zxxj = new JRadioButton("ֱб���");
		jrb_zxxj.setBounds(110, 25, 100, 25);
		cell_panel_mhd.add(jrb_zxxj);
		bg_mhd.add(jrb_zxxj);
		jrb_zxxj.addActionListener(this);

		JRadioButton jrb_bztj = new JRadioButton("��ֱǰ��");
		jrb_bztj.setBounds(5, 55, 100, 25);
		cell_panel_mhd.add(jrb_bztj);
		bg_mhd.add(jrb_bztj);
		jrb_bztj.addActionListener(this);

		switch (AStar.MHD_TYPE) {
			case AStar.MHD_ZLZW: {
				jrb_zlzw.setSelected(true);
			}
				break;
			case AStar.MHD_ZXXJ: {
				jrb_zxxj.setSelected(true);
			}
				break;
			case AStar.MHD_BZTJ: {
				jrb_bztj.setSelected(true);
			}
				break;
			default:
				break;
		}

		panel_configure_configure.add(cell_panel_mhd);
		HEIGHT += 65 + AStarJFrame.HEAD_HEIGHT + 5;

		/**
		 * Ѱ··������
		 */
		JPanel cell_panel_wayType = new JPanel();
		cell_panel_wayType.setLayout(null);
		cell_panel_wayType.setBorder(BorderFactory.createTitledBorder("Ѱ··������"));
		cell_panel_wayType.setBounds(0, HEIGHT, 215, 35 + AStarJFrame.HEAD_HEIGHT);

		JCheckBox jcb_stright = new JCheckBox("�ݺ᷽��");
		jcb_stright.setBounds(5, 25, 100, 25);
		cell_panel_wayType.add(jcb_stright);
		jcb_stright.setSelected(AStar.STRAIGHT);
		jcb_stright.addItemListener(this);

		JCheckBox jcb_skew = new JCheckBox("б��");
		jcb_skew.setBounds(110, 25, 100, 25);
		cell_panel_wayType.add(jcb_skew);
		jcb_skew.setSelected(AStar.SKEW);
		jcb_skew.addItemListener(this);

		panel_configure_configure.add(cell_panel_wayType);
		HEIGHT += 35 + AStarJFrame.HEAD_HEIGHT + 5;

		/**
		 * �Ż��ȼ�
		 */
		JPanel cell_panel_optimize = new JPanel();
		cell_panel_optimize.setLayout(null);
		cell_panel_optimize.setBorder(BorderFactory.createTitledBorder("�Ż��ȼ�"));
		cell_panel_optimize.setBounds(0, HEIGHT, 215, 95 + AStarJFrame.HEAD_HEIGHT);

		JCheckBox jcb_optimize1 = new JCheckBox("һ���Ż�");
		jcb_optimize1.setBounds(5, 25, 100, 25);
		cell_panel_optimize.add(jcb_optimize1);
		jcb_optimize1.setSelected(AStar.OPTIMIZE_1);
		jcb_optimize1.addItemListener(this);

		JCheckBox jcb_optimize2 = new JCheckBox("�����Ż�");
		jcb_optimize2.setBounds(110, 25, 100, 25);
		cell_panel_optimize.add(jcb_optimize2);
		jcb_optimize2.setSelected(AStar.OPTIMIZE_2);
		jcb_optimize2.addItemListener(this);

		JCheckBox jcb_optimize3 = new JCheckBox("�����Ż�");
		jcb_optimize3.setBounds(5, 55, 100, 25);
		cell_panel_optimize.add(jcb_optimize3);
		jcb_optimize3.setSelected(AStar.OPTIMIZE_3);
		jcb_optimize3.addItemListener(this);

		JCheckBox jcb_optimize4 = new JCheckBox("�ļ��Ż�");
		jcb_optimize4.setBounds(110, 55, 100, 25);
		cell_panel_optimize.add(jcb_optimize4);
		jcb_optimize4.setSelected(AStar.OPTIMIZE_4);
		jcb_optimize4.addItemListener(this);

		JCheckBox jcb_optimize5 = new JCheckBox("�弶�Ż�");
		jcb_optimize5.setBounds(5, 85, 100, 25);
		cell_panel_optimize.add(jcb_optimize5);
		jcb_optimize5.setSelected(AStar.OPTIMIZE_5);
		jcb_optimize5.addItemListener(this);

		JCheckBox jcb_optimize6 = new JCheckBox("�����Ż�");
		jcb_optimize6.setBounds(110, 85, 100, 25);
		cell_panel_optimize.add(jcb_optimize6);
		jcb_optimize6.setSelected(AStar.OPTIMIZE_6);
		jcb_optimize6.addItemListener(this);

		panel_configure_configure.add(cell_panel_optimize);

		HEIGHT += 95 + AStarJFrame.HEAD_HEIGHT + 5;

		/**
		 * �����������
		 */
		JPanel cell_panel_size = new JPanel();
		cell_panel_size.setBorder(BorderFactory.createTitledBorder("��������С����"));
		cell_panel_size.setBounds(0, HEIGHT, 215, 125 + AStarJFrame.HEAD_HEIGHT);
		cell_panel_size.setLayout(null);
		panel_configure_configure.add(cell_panel_size);

		JLabel jl_map_size = new JLabel("��ͼ��С");
		jl_map_size.setBounds(5, 25, 100, 25);
		cell_panel_size.add(jl_map_size);
		jl_map_size.setHorizontalAlignment(JLabel.RIGHT);

		jtf_map_size = new JTextField();
		jtf_map_size.setBounds(110, 25, 95, 25);
		cell_panel_size.add(jtf_map_size);
		jtf_map_size.setColumns(10);
		hmJTF.put(jtf_map_size, AStarJFrame.JTF_MAP_SIZE_P);
		jtf_map_size.setText(AStar.MAP_SIZE + "");
		jtf_map_size.addFocusListener(this);

		JLabel jl_cub_size = new JLabel("�����С");
		jl_cub_size.setBounds(5, 55, 100, 25);
		cell_panel_size.add(jl_cub_size);
		jl_cub_size.setHorizontalAlignment(JLabel.RIGHT);

		jtf_cub_size = new JTextField();
		jtf_cub_size.setBounds(110, 55, 95, 25);
		cell_panel_size.add(jtf_cub_size);
		jtf_cub_size.setColumns(10);
		hmJTF.put(jtf_cub_size, AStarJFrame.JTF_CUB_SIZE_P);
		jtf_cub_size.setText(AStar.CUB_SIZE + "");
		jtf_cub_size.addFocusListener(this);

		JLabel jl_find_max = new JLabel("Ѱ·����ظ�����");
		jl_find_max.setBounds(5, 85, 100, 25);
		cell_panel_size.add(jl_find_max);
		jl_find_max.setHorizontalAlignment(JLabel.RIGHT);

		jtf_max_repeat = new JTextField();
		jtf_max_repeat.setBounds(110, 85, 95, 25);
		cell_panel_size.add(jtf_max_repeat);
		jtf_max_repeat.setColumns(10);
		hmJTF.put(jtf_max_repeat, AStarJFrame.JTF_MAX_REPEAT_P);
		jtf_max_repeat.setText(AStar.MAX_REPEAT + "");
		jtf_max_repeat.addFocusListener(this);

		JLabel jl_draw_path_interval = new JLabel("����·��ʱ����");
		jl_draw_path_interval.setBounds(5, 115, 100, 25);
		cell_panel_size.add(jl_draw_path_interval);
		jl_draw_path_interval.setHorizontalAlignment(JLabel.RIGHT);

		jtf_draw_path_interval = new JTextField();
		jtf_draw_path_interval.setBounds(110, 115, 95, 25);
		cell_panel_size.add(jtf_draw_path_interval);
		jtf_draw_path_interval.setColumns(10);
		hmJTF.put(jtf_draw_path_interval, AStarJFrame.JTF_DRAW_PATH_INTERVAL_P);
		jtf_draw_path_interval.setText(AStar.DRAW_PATH_INTERVAL + "");
		jtf_draw_path_interval.addFocusListener(this);

		HEIGHT += 125 + AStarJFrame.HEAD_HEIGHT + 5;

		panel_configure_configure.setPreferredSize(new Dimension(230, HEIGHT));

	}

	/**
	 * �м�չʾ���
	 */
	private void createShowJPanel() {
		JPanel panel_show = new JPanel();
		splitPane.setRightComponent(panel_show);
		panel_show.setLayout(new BorderLayout(0, 0));

		scrollPane_show = new JScrollPane();
		panel_show.add(scrollPane_show, BorderLayout.CENTER);

		astarPanel = new AStarDrawJPanel();
		scrollPane_show.setViewportView(astarPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("���".equals(e.getActionCommand())) {
			AStar.DRAW_TYPE_OUT = AStar.CUB_START;
			if (null != astarPanel) {
				astarPanel.setDrawType_out(AStar.DRAW_TYPE_OUT);
			}
		} else if ("�յ�".equals(e.getActionCommand())) {
			AStar.DRAW_TYPE_OUT = AStar.CUB_END;
			if (null != astarPanel) {
				astarPanel.setDrawType_out(AStar.DRAW_TYPE_OUT);
			}
		} else if ("�ϰ���".equals(e.getActionCommand())) {
			AStar.DRAW_TYPE_OUT = AStar.CUB_OBS;
			if (null != astarPanel) {
				astarPanel.setDrawType_out(AStar.DRAW_TYPE_OUT);
			}
		} else if ("��Ƥ��".equals(e.getActionCommand())) {
			AStar.DRAW_TYPE_OUT = AStar.CUB_GROUND;
			if (null != astarPanel) {
				astarPanel.setDrawType_out(AStar.DRAW_TYPE_OUT);
			}
		} else if ("ֱ��ֱ��".equals(e.getActionCommand())) {
			AStar.MHD_TYPE = AStar.MHD_ZLZW;
		} else if ("ֱб���".equals(e.getActionCommand())) {
			AStar.MHD_TYPE = AStar.MHD_ZXXJ;
		} else if ("��ֱǰ��".equals(e.getActionCommand())) {
			AStar.MHD_TYPE = AStar.MHD_BZTJ;
		} else if ("Ѱ·".equals(e.getActionCommand())) {
			if (null != astarPanel) {
				astarPanel.clearPath();
				OperatePool.findWay(astarPanel);
			}
		} else if ("���·��".equals(e.getActionCommand())) {
			if (null != astarPanel) {
				astarPanel.clearPath();
			}
		} else if ("�������".equals(e.getActionCommand())) {
			scrollPane_show.remove(astarPanel);
			astarPanel = null;
			astarPanel = new AStarDrawJPanel();
			scrollPane_show.setViewportView(astarPanel);
			//scrollPane_show.repaint();
			//scrollPane_show.revalidate();
		} else {
			System.out.println(e.getActionCommand());
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object item = e.getItem();
		if (item instanceof JCheckBox) {
			JCheckBox jcbItem = (JCheckBox) item;
			if ("�ݺ᷽��".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.STRAIGHT = true;
				} else {
					AStar.STRAIGHT = false;
				}
			} else if ("б��".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.SKEW = true;
				} else {
					AStar.SKEW = false;
				}
			} else if ("һ���Ż�".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.OPTIMIZE_1 = true;
				} else {
					AStar.OPTIMIZE_1 = false;
				}
			} else if ("�����Ż�".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.OPTIMIZE_2 = true;
				} else {
					AStar.OPTIMIZE_2 = false;
				}
			} else if ("�����Ż�".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.OPTIMIZE_3 = true;
				} else {
					AStar.OPTIMIZE_3 = false;
				}
			} else if ("�ļ��Ż�".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.OPTIMIZE_4 = true;
				} else {
					AStar.OPTIMIZE_4 = false;
				}
			} else if ("�弶�Ż�".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.OPTIMIZE_5 = true;
				} else {
					AStar.OPTIMIZE_5 = false;
				}
			} else if ("�����Ż�".equals(jcbItem.getText())) {
				if (e.getStateChange() == 1) {
					AStar.OPTIMIZE_6 = true;
				} else {
					AStar.OPTIMIZE_6 = false;
				}
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// System.out.println("focusGained");
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object object = e.getSource();
		if (object instanceof JTextField) {
			JTextField jTextField = (JTextField) object;
			String txt = jTextField.getText();
			int type = hmJTF.get(jTextField);
			switch (type) {
				case AStarJFrame.JTF_MAP_SIZE_P: {
					try {
						int number = Integer.parseInt(txt);
						if (AStar.MAP_SIZE == number) {
							return;
						}
						if (number <= 1) {
							jTextField.setFocusable(true);
							jTextField.requestFocus();
							Alert.alertError("������Ϸ����֣�");
							return;
						}
						if (number % AStar.CUB_SIZE != 0) {
							jTextField.setFocusable(true);
							jTextField.requestFocus();
							Alert.alertError("��ͼ��С����Ϊ�����С����������");
							return;
						}
						AStar.MAP_SIZE = number;
						scrollPane_show.remove(astarPanel);
						astarPanel = new AStarDrawJPanel();
						scrollPane_show.setViewportView(astarPanel);
//						scrollPane_show.repaint();
//						scrollPane_show.revalidate();

						astarPanel.setPreferredSize(new Dimension(AStar.MAP_SIZE, AStar.MAP_SIZE));

					} catch (Exception e2) {
						jTextField.setFocusable(true);
						jTextField.requestFocus();
						Alert.alertError("������Ϸ����֣�");
					}
				}
					break;
				case AStarJFrame.JTF_CUB_SIZE_P: {
					try {
						int number = Integer.parseInt(txt);
						if (AStar.CUB_SIZE == number) {
							return;
						}
						if (number <= 1) {
							jTextField.setFocusable(true);
							jTextField.requestFocus();
							Alert.alertError("������Ϸ����֣�");
							return;
						}
						if (AStar.MAP_SIZE % number != 0) {
							jTextField.setFocusable(true);
							jTextField.requestFocus();
							Alert.alertError("��ͼ��С����Ϊ�����С����������");
							return;
						}
						AStar.CUB_SIZE = number;
						scrollPane_show.remove(astarPanel);
						astarPanel = new AStarDrawJPanel();
						scrollPane_show.setViewportView(astarPanel);
						scrollPane_show.repaint();
						scrollPane_show.revalidate();

					} catch (Exception e2) {
						jTextField.setFocusable(true);
						jTextField.requestFocus();
						Alert.alertError("������Ϸ����֣�");
					}
				}

					break;
				case AStarJFrame.JTF_MAX_REPEAT_P: {
					try {
						int number = Integer.parseInt(txt);
						if (AStar.CUB_SIZE == number) {
							return;
						}
						if (number < 1) {
							jTextField.setFocusable(true);
							jTextField.requestFocus();
							Alert.alertError("������Ϸ����֣�");
							return;
						}
						AStar.MAX_REPEAT = number;
					} catch (Exception e2) {
						jTextField.setFocusable(true);
						jTextField.requestFocus();
						Alert.alertError("������Ϸ����֣�");
					}

				}
					break;
				case AStarJFrame.JTF_DRAW_PATH_INTERVAL_P: {
					try {
						int number = Integer.parseInt(txt);
						if (AStar.DRAW_PATH_INTERVAL == number) {
							return;
						}
						if (number <= 1) {
							Alert.alertError("������Ϸ����֣�");
							jTextField.setFocusable(true);
							jTextField.requestFocus();
							return;
						}
						AStar.DRAW_PATH_INTERVAL = number;
					} catch (Exception e2) {
						Alert.alertError("������Ϸ����֣�");
					}

				}
					break;
				default:
					break;
			}
		}
	}
}
