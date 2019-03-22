package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import Database.Account;
import Database.Operate;
import Message.Cmd;
import Message.SendMsg;
import Message.SendSocket;

public class MainUI extends JFrame implements MouseListener, ActionListener {

	private Account acc, friendAccount;
	private JLabel faceJL, nickNameJL, signJL;
	private JTabbedPane tabJTP;
	// 可以很方便地在窗口放置多个标签页，每个标签页相当于获得了一个与外部容器局域相同大小的组件摆放区域
	private JList<Account> friendJL, groupJL, talkJL, sign2JL, currentJL;
	// List集合代表一个元素有序可重复的集合，集合中每个元素都有其对应的顺序索引。
	private JPanel searchJP;
	private JLabel searchJL;
	private JTextField searchJTF;
	private JButton searchJB;
	private Vector<Account> friendV;
	private Vector groupV, talkV, sign2V, allInfoV;
	// Vector类实际上就是一种特殊的可以动态增长的数组，因为会有增加和删除好友的操作，所以用Vector比数组更方便
	private JPopupMenu rightJPM;// 右键对象
	private JMenu menuJM;
	private JMenuItem sendMsgJMI, lookInfoJMI, setRemarkJMI, delJMI;
	// 定义一个朋友的账号与聊天界面一一对应的哈希表，来实现收到消息时已经新建过的窗口不再重新被新建而是弹出
	Hashtable<Integer, Chat> ht_chat = new Hashtable<Integer, Chat>();

	public MainUI() {

	}

	public MainUI(Account acc) {
		super("QQ2017");
		this.acc = acc;

		// 总界面
		setBounds(700, 0, 280, 700);
		getContentPane().setLayout(null);
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setForeground(new Color(255, 255, 255));
		Image icon = new ImageIcon(acc.getFaceImage()).getImage();
		setIconImage(icon);

		// 上部元素显示头像，昵称和QQ号还有个性签名
		Icon img = new ImageIcon(acc.getFaceImage());
		// System.out.println(acc.getFaceImage());
		faceJL = new JLabel(img);
		faceJL.setBounds(10, 10, 100, 100);
		getContentPane().add(faceJL);

		String s = acc.getNickName() + "(" + acc.getQqCode() + ")";
		nickNameJL = new JLabel(s);
		// System.out.println("nickName" + acc.getNickName());
		nickNameJL.setBounds(120, 10, 80, 30);
		getContentPane().add(nickNameJL);

		signJL = new JLabel(acc.getSign());
		// System.out.println("sign" + acc.getSign());
		signJL.setBounds(120, 70, 80, 30);
		getContentPane().add(signJL);
		// JLabel jl = new(str, new IamgeIcon(""),JLabel.RIGHT);将图片和文字放到一起的效果

		// searchJP用一放置添加好友的操作
		searchJP = new JPanel();
		searchJP.setLayout(null);
		searchJL = new JLabel("请输入您要搜索的qq号：");
		searchJL.setBounds(10, 20, 200, 30);
		searchJP.add(searchJL);
		searchJTF = new JTextField();
		searchJTF.setBounds(10, 60, 200, 30);
		searchJP.add(searchJTF);
		searchJB = new JButton("搜索");
		searchJB.setBounds(215, 60, 55, 30);
		searchJB.addActionListener(this);
		searchJP.add(searchJB);

		// 页面标签，多个页面
		tabJTP = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		friendJL = new JList<Account>();
		friendJL.addMouseListener(this);
		groupJL = new JList();
		groupJL.addMouseListener(this);
		talkJL = new JList();
		talkJL.addMouseListener(this);
		sign2JL = new JList();
		sign2JL.addMouseListener(this);

		friendV = new Vector<>();
		groupV = new Vector<>();
		talkV = new Vector<>();
		sign2V = new Vector<>();

		refresh();

		tabJTP.add("好友", friendJL);
		tabJTP.add("群组", groupJL);
		tabJTP.add("会话", talkJL);
		tabJTP.add("个性签名", signJL);
		tabJTP.add("查找好友", searchJP);
		tabJTP.setBounds(0, 110, 280, 560);
		add(tabJTP);

		createMenu();

		MouseHandle handle = new MouseHandle();
		faceJL.addMouseListener(handle);
		friendJL.addMouseListener(handle);
		groupJL.addMouseListener(handle);
		talkJL.addMouseListener(handle);
		signJL.addMouseListener(handle);

		refresh();

		setVisible(true);

		// 启动线程
		new ReceiveThread(acc).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// 菜单和按钮用法一样
		if (e.getSource() == sendMsgJMI) {
			popChat();
			// new Chat(acc, friendAccount);
		} else if (e.getSource() == lookInfoJMI) {
			if (friendAccount != null)
				new LookInfo(friendAccount);
		} else if (e.getSource() == setRemarkJMI) {
			new ChangeRemark();
			String s = new ChangeRemark().getRemark();
			Operate db = new Operate();
			boolean b = db.changeRemark(acc, friendAccount, s);
			if (b) {
				System.out.println("修改备注成功");
			} else {
				System.out.println("修改备注失败");
			}
		} else if (e.getSource() == delJMI) {

		} else if (e.getSource() == searchJB) {
			Operate db = new Operate();
			Account friend = db.searchFriend(Integer.parseInt(searchJTF.getText()));
			new Chat(acc, friend);
		}
	}

	// 以下两个类为了实现在list中存放图片
	// 需要改变JList中的CellRender来支持对图片的显示，默认的CellRender只会显示字符串。
	// 创建listModel类来设置JList列表类型
	class listModel extends AbstractListModel {
		Vector dats;
		public listModel(Vector dats) {
			this.dats = dats;
		}
		// 获取指定位置的对象
		public Object getElementAt(int index) {
			Account user = (Account) dats.get(index);
			return user.getNickName().trim() + "【" + user.getQqCode() + "】";
		}
		// 获取长度
		public int getSize() {
			return dats.size();
		}
	}
	// 现在每一个listModel中的元素都是一个Vector数组
	// myfind类继承DefaultListCellRenderer,而DefaultListCellRenderer继承于JLabel，所以只要将
	// 图片赋值给JLabel就可以更改显示样式，要达到文字在图片的右侧就要设置JLabel的TextPosition;
	class myfind extends DefaultListCellRenderer {
		Vector datas;
		public myfind(Vector datas) {
			this.datas = datas;
		}
		// Component所有组件的父类
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (index >= 0 && index < datas.size()) {
				Account user = (Account) datas.get(index);
				setIcon(new ImageIcon(user.getFaceImage()));
				setText(user.getNickName().trim() + "(" + user.getQqCode() + ")");
			}
			// 设置字体颜色
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}
	}

	// 刷新界面
	public void refresh() {
		friendV.clear();
		// 将数据库中取出来的资料放到friendV中
		friendV = new Operate().getAllFriendsInfo(acc);
		System.out.println(friendV.size() + "friendV");
		// 把向量放到List控件中，以显示到界面上
		friendJL.setModel(new listModel(friendV));// 显示资料
		friendJL.setCellRenderer(new myfind(friendV));// 显示头像
	}

	// 创建菜单

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == friendJL) {
			// 双击
			System.out.println(friendJL.getSelectedIndex() + "friendJL");
			friendAccount = (Account) friendV.get(friendJL.getSelectedIndex());
			if (arg0.getClickCount() == 2) {
				// JOptionPane.showMessageDialog(null, "you have clicked
				// double");
				// new ChatUI(acc, acc);
			}
			// 右键
			if (arg0.getButton() == 3) {// 选中了才能出现右键菜单。
				if (friendJL.getSelectedIndex() >= 0) {
					rightJPM.show(friendJL, arg0.getX(), arg0.getY());// 右键组件点show
				}
			}
		} else if (arg0.getSource() == groupJL) {

		}
	}

	// 鼠标事件
	class MouseHandle extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == friendJL) {
				currentJL = friendJL;
				if (e.getClickCount() == 2) {// 双击
					popChat();
				} else if (e.getButton() == 3) {// 右键
					rightJPM.show(friendJL, e.getX(), e.getY());
				}
			} else if (e.getSource() == groupJL) {
				currentJL = groupJL;
				if (e.getClickCount() == 2) {// 双击
					popChat();
				} else if (e.getButton() == 3) {// 右键
					rightJPM.show(groupJL, e.getX(), e.getY());
				}
			} else if (e.getSource() == talkJL) {
				currentJL = talkJL;
				if (e.getClickCount() == 2) {// 双击
					popChat();
				} else if (e.getButton() == 3) {// 右键
					rightJPM.show(talkJL, e.getX(), e.getY());
				}
			} else if (e.getSource() == sign2JL) {
				currentJL = sign2JL;
				if (e.getClickCount() == 2) {// 双击
					popChat();
				} else if (e.getButton() == 3) {// 右键
					rightJPM.show(sign2JL, e.getX(), e.getY());
				}
			}
		}
	}

	// 点击鼠标右键时获取好友资料
	public boolean getFriendInfo() {
		int index = currentJL.getSelectedIndex();// 获取所选标签页
		if (index < 0)
			return false;

		if (currentJL == friendJL) {
			friendAccount = friendV.get(index);
		}
		return true;
	}

	// 查找窗口是否存在，若存在，则弹出，不存在则新建
	public Chat findWin(int qqCode, SendMsg msg) {
		Chat chat = null;
		// 查找窗口是否存在
		chat = ht_chat.get(qqCode);
		if (chat == null) {
			if (msg == null) {// 双击或者右键打开窗口
				new Chat(acc, friendAccount);// ？？friendAccount怎么被更新为被选中的那个？？？
				ht_chat.put(qqCode, chat);
			} else {
				new Chat(msg.friendAccount, msg.myAccount);
			}

		}
		chat.show();

		return chat;
	}

	// 信息接收线程
	// 因为不知道什么时候会有人发送消息给自己，所以采用线程来循环等待有消息则接受，无消息则等待
	class ReceiveThread extends Thread {
		Account myAccount;

		public ReceiveThread(Account myAccount) {
			this.myAccount = myAccount;
			System.out.println("启动线程....");
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// 定义接收消息的Socket;
			DatagramSocket socket = null;
			try {
				System.out.println(myAccount.getPort());
				socket = new DatagramSocket(myAccount.getPort());// 重复请求就会报错，端口已经被上一次的请求占用了
				while (true) {
					byte b[] = new byte[1024 * 64];
					DatagramPacket p = new DatagramPacket(b, b.length);

					// 接收消息
					System.out.println("准备接收到消息。。。。");
					socket.receive(p);
					System.out.println("接收到消息。。。。");
					ByteArrayInputStream bis = new ByteArrayInputStream(p.getData());
					// 得到对象输出流
					ObjectInputStream ois = new ObjectInputStream(bis);
					SendMsg msg = (SendMsg) ois.readObject();// 还原成sendMsg，怎么发送，怎么接收
					System.out.println("msg=" + msg);

					// 分门别类地处理各种信息
					switch (msg.cmd) {
					case Cmd.CMD_SEND: // 接收聊天信息
						Chat chat = findWin(friendAccount.getQqCode(), msg);
						// 把接收到的信息显示到聊天窗口的接收框
						chat.appendView(msg.myAccount.getNickName(), msg.msg);
						break;
					case Cmd.CMD_SENDF:
						String str = msg.myAccount.getNickName() + "发送了" + msg.fileName + "文件给你";
						int cmd = Cmd.CMD_FILESUC;
						if (JOptionPane.showConfirmDialog(null, str, "接收文件",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							FileDialog dlg = new FileDialog(MainUI.this, "保存", FileDialog.SAVE);
							dlg.setFile(msg.fileName);
							dlg.show();
							String sfilename = dlg.getDirectory() + "\\" + dlg.getFile();
							File file = new File(sfilename);
							if (!file.exists()) {
								file.createNewFile();
							}
							FileOutputStream fos = new FileOutputStream(sfilename);
							fos.write(msg.b);
							fos.close();
						} else {
							cmd = Cmd.CMD_FILEFAIL;

						}
						SendMsg mg = new SendMsg();
						mg.cmd = cmd;
						mg.myAccount = msg.friendAccount;
						mg.friendAccount = msg.myAccount;
						new SendSocket().send(mg);
						break;

					case Cmd.CMD_FILESUC:
						JOptionPane.showMessageDialog(null, "成功接收");
						break;
					case Cmd.CMD_FILEFAIL:// 回复接收失败
						JOptionPane.showMessageDialog(null, "拒绝接收");
						break;
					case Cmd.CMD_SHAKE:
						chat = ht_chat.get(msg.myAccount.getQqCode());
						if (chat == null) {// 第一次聊天
							chat = new Chat(msg.friendAccount, msg.myAccount);
							// 把聊天窗口保存到hashtable中
							ht_chat.put(msg.myAccount.getQqCode(), chat);
						}
						chat.show();
						chat.shake();// 抖动
						break;
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			// socket.close();
		}
	}

	// 创建菜单
	public void createMenu() {
		rightJPM = new JPopupMenu();
		sendMsgJMI = new JMenuItem("发送即时消息");
		lookInfoJMI = new JMenuItem("查看资料");
		lookInfoJMI.addActionListener(this);
		setRemarkJMI = new JMenuItem("修改昵称");
		delJMI = new JMenuItem("删除好友");
		rightJPM.add(sendMsgJMI);
		rightJPM.add(lookInfoJMI);
		rightJPM.add(setRemarkJMI);
		rightJPM.add(delJMI);
	}
	
	// 弹出聊天窗口
		public void popChat() {
			if (!getFriendInfo())// 返回假表示没有选择好友资料
				return;
			// 从hashtable中获取聊天窗口,如果不存在则返回null
			Chat chat = ht_chat.get(friendAccount.getQqCode());
			if (chat == null) {// 第一次聊天
				chat = new Chat(acc, friendAccount);
				// 把聊天窗口保存到hashtable中
				ht_chat.put(friendAccount.getQqCode(), chat);
			}
			System.out.println("my port=" + acc.getPort() + ";friend port=" + friendAccount.getPort());
			chat.show();// 显示窗口
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

}
