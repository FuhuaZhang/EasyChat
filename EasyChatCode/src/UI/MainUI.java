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
	// ���Ժܷ�����ڴ��ڷ��ö����ǩҳ��ÿ����ǩҳ�൱�ڻ����һ�����ⲿ����������ͬ��С������ڷ�����
	private JList<Account> friendJL, groupJL, talkJL, sign2JL, currentJL;
	// List���ϴ���һ��Ԫ��������ظ��ļ��ϣ�������ÿ��Ԫ�ض������Ӧ��˳��������
	private JPanel searchJP;
	private JLabel searchJL;
	private JTextField searchJTF;
	private JButton searchJB;
	private Vector<Account> friendV;
	private Vector groupV, talkV, sign2V, allInfoV;
	// Vector��ʵ���Ͼ���һ������Ŀ��Զ�̬���������飬��Ϊ�������Ӻ�ɾ�����ѵĲ�����������Vector�����������
	private JPopupMenu rightJPM;// �Ҽ�����
	private JMenu menuJM;
	private JMenuItem sendMsgJMI, lookInfoJMI, setRemarkJMI, delJMI;
	// ����һ�����ѵ��˺����������һһ��Ӧ�Ĺ�ϣ����ʵ���յ���Ϣʱ�Ѿ��½����Ĵ��ڲ������±��½����ǵ���
	Hashtable<Integer, Chat> ht_chat = new Hashtable<Integer, Chat>();

	public MainUI() {

	}

	public MainUI(Account acc) {
		super("QQ2017");
		this.acc = acc;

		// �ܽ���
		setBounds(700, 0, 280, 700);
		getContentPane().setLayout(null);
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setForeground(new Color(255, 255, 255));
		Image icon = new ImageIcon(acc.getFaceImage()).getImage();
		setIconImage(icon);

		// �ϲ�Ԫ����ʾͷ���ǳƺ�QQ�Ż��и���ǩ��
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
		// JLabel jl = new(str, new IamgeIcon(""),JLabel.RIGHT);��ͼƬ�����ַŵ�һ���Ч��

		// searchJP��һ������Ӻ��ѵĲ���
		searchJP = new JPanel();
		searchJP.setLayout(null);
		searchJL = new JLabel("��������Ҫ������qq�ţ�");
		searchJL.setBounds(10, 20, 200, 30);
		searchJP.add(searchJL);
		searchJTF = new JTextField();
		searchJTF.setBounds(10, 60, 200, 30);
		searchJP.add(searchJTF);
		searchJB = new JButton("����");
		searchJB.setBounds(215, 60, 55, 30);
		searchJB.addActionListener(this);
		searchJP.add(searchJB);

		// ҳ���ǩ�����ҳ��
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

		tabJTP.add("����", friendJL);
		tabJTP.add("Ⱥ��", groupJL);
		tabJTP.add("�Ự", talkJL);
		tabJTP.add("����ǩ��", signJL);
		tabJTP.add("���Һ���", searchJP);
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

		// �����߳�
		new ReceiveThread(acc).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// �˵��Ͱ�ť�÷�һ��
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
				System.out.println("�޸ı�ע�ɹ�");
			} else {
				System.out.println("�޸ı�עʧ��");
			}
		} else if (e.getSource() == delJMI) {

		} else if (e.getSource() == searchJB) {
			Operate db = new Operate();
			Account friend = db.searchFriend(Integer.parseInt(searchJTF.getText()));
			new Chat(acc, friend);
		}
	}

	// ����������Ϊ��ʵ����list�д��ͼƬ
	// ��Ҫ�ı�JList�е�CellRender��֧�ֶ�ͼƬ����ʾ��Ĭ�ϵ�CellRenderֻ����ʾ�ַ�����
	// ����listModel��������JList�б�����
	class listModel extends AbstractListModel {
		Vector dats;
		public listModel(Vector dats) {
			this.dats = dats;
		}
		// ��ȡָ��λ�õĶ���
		public Object getElementAt(int index) {
			Account user = (Account) dats.get(index);
			return user.getNickName().trim() + "��" + user.getQqCode() + "��";
		}
		// ��ȡ����
		public int getSize() {
			return dats.size();
		}
	}
	// ����ÿһ��listModel�е�Ԫ�ض���һ��Vector����
	// myfind��̳�DefaultListCellRenderer,��DefaultListCellRenderer�̳���JLabel������ֻҪ��
	// ͼƬ��ֵ��JLabel�Ϳ��Ը�����ʾ��ʽ��Ҫ�ﵽ������ͼƬ���Ҳ��Ҫ����JLabel��TextPosition;
	class myfind extends DefaultListCellRenderer {
		Vector datas;
		public myfind(Vector datas) {
			this.datas = datas;
		}
		// Component��������ĸ���
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (index >= 0 && index < datas.size()) {
				Account user = (Account) datas.get(index);
				setIcon(new ImageIcon(user.getFaceImage()));
				setText(user.getNickName().trim() + "(" + user.getQqCode() + ")");
			}
			// ����������ɫ
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

	// ˢ�½���
	public void refresh() {
		friendV.clear();
		// �����ݿ���ȡ���������Ϸŵ�friendV��
		friendV = new Operate().getAllFriendsInfo(acc);
		System.out.println(friendV.size() + "friendV");
		// �������ŵ�List�ؼ��У�����ʾ��������
		friendJL.setModel(new listModel(friendV));// ��ʾ����
		friendJL.setCellRenderer(new myfind(friendV));// ��ʾͷ��
	}

	// �����˵�

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == friendJL) {
			// ˫��
			System.out.println(friendJL.getSelectedIndex() + "friendJL");
			friendAccount = (Account) friendV.get(friendJL.getSelectedIndex());
			if (arg0.getClickCount() == 2) {
				// JOptionPane.showMessageDialog(null, "you have clicked
				// double");
				// new ChatUI(acc, acc);
			}
			// �Ҽ�
			if (arg0.getButton() == 3) {// ѡ���˲��ܳ����Ҽ��˵���
				if (friendJL.getSelectedIndex() >= 0) {
					rightJPM.show(friendJL, arg0.getX(), arg0.getY());// �Ҽ������show
				}
			}
		} else if (arg0.getSource() == groupJL) {

		}
	}

	// ����¼�
	class MouseHandle extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == friendJL) {
				currentJL = friendJL;
				if (e.getClickCount() == 2) {// ˫��
					popChat();
				} else if (e.getButton() == 3) {// �Ҽ�
					rightJPM.show(friendJL, e.getX(), e.getY());
				}
			} else if (e.getSource() == groupJL) {
				currentJL = groupJL;
				if (e.getClickCount() == 2) {// ˫��
					popChat();
				} else if (e.getButton() == 3) {// �Ҽ�
					rightJPM.show(groupJL, e.getX(), e.getY());
				}
			} else if (e.getSource() == talkJL) {
				currentJL = talkJL;
				if (e.getClickCount() == 2) {// ˫��
					popChat();
				} else if (e.getButton() == 3) {// �Ҽ�
					rightJPM.show(talkJL, e.getX(), e.getY());
				}
			} else if (e.getSource() == sign2JL) {
				currentJL = sign2JL;
				if (e.getClickCount() == 2) {// ˫��
					popChat();
				} else if (e.getButton() == 3) {// �Ҽ�
					rightJPM.show(sign2JL, e.getX(), e.getY());
				}
			}
		}
	}

	// �������Ҽ�ʱ��ȡ��������
	public boolean getFriendInfo() {
		int index = currentJL.getSelectedIndex();// ��ȡ��ѡ��ǩҳ
		if (index < 0)
			return false;

		if (currentJL == friendJL) {
			friendAccount = friendV.get(index);
		}
		return true;
	}

	// ���Ҵ����Ƿ���ڣ������ڣ��򵯳������������½�
	public Chat findWin(int qqCode, SendMsg msg) {
		Chat chat = null;
		// ���Ҵ����Ƿ����
		chat = ht_chat.get(qqCode);
		if (chat == null) {
			if (msg == null) {// ˫�������Ҽ��򿪴���
				new Chat(acc, friendAccount);// ����friendAccount��ô������Ϊ��ѡ�е��Ǹ�������
				ht_chat.put(qqCode, chat);
			} else {
				new Chat(msg.friendAccount, msg.myAccount);
			}

		}
		chat.show();

		return chat;
	}

	// ��Ϣ�����߳�
	// ��Ϊ��֪��ʲôʱ������˷�����Ϣ���Լ������Բ����߳���ѭ���ȴ�����Ϣ����ܣ�����Ϣ��ȴ�
	class ReceiveThread extends Thread {
		Account myAccount;

		public ReceiveThread(Account myAccount) {
			this.myAccount = myAccount;
			System.out.println("�����߳�....");
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// ���������Ϣ��Socket;
			DatagramSocket socket = null;
			try {
				System.out.println(myAccount.getPort());
				socket = new DatagramSocket(myAccount.getPort());// �ظ�����ͻᱨ���˿��Ѿ�����һ�ε�����ռ����
				while (true) {
					byte b[] = new byte[1024 * 64];
					DatagramPacket p = new DatagramPacket(b, b.length);

					// ������Ϣ
					System.out.println("׼�����յ���Ϣ��������");
					socket.receive(p);
					System.out.println("���յ���Ϣ��������");
					ByteArrayInputStream bis = new ByteArrayInputStream(p.getData());
					// �õ����������
					ObjectInputStream ois = new ObjectInputStream(bis);
					SendMsg msg = (SendMsg) ois.readObject();// ��ԭ��sendMsg����ô���ͣ���ô����
					System.out.println("msg=" + msg);

					// ���ű���ش��������Ϣ
					switch (msg.cmd) {
					case Cmd.CMD_SEND: // ����������Ϣ
						Chat chat = findWin(friendAccount.getQqCode(), msg);
						// �ѽ��յ�����Ϣ��ʾ�����촰�ڵĽ��տ�
						chat.appendView(msg.myAccount.getNickName(), msg.msg);
						break;
					case Cmd.CMD_SENDF:
						String str = msg.myAccount.getNickName() + "������" + msg.fileName + "�ļ�����";
						int cmd = Cmd.CMD_FILESUC;
						if (JOptionPane.showConfirmDialog(null, str, "�����ļ�",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							FileDialog dlg = new FileDialog(MainUI.this, "����", FileDialog.SAVE);
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
						JOptionPane.showMessageDialog(null, "�ɹ�����");
						break;
					case Cmd.CMD_FILEFAIL:// �ظ�����ʧ��
						JOptionPane.showMessageDialog(null, "�ܾ�����");
						break;
					case Cmd.CMD_SHAKE:
						chat = ht_chat.get(msg.myAccount.getQqCode());
						if (chat == null) {// ��һ������
							chat = new Chat(msg.friendAccount, msg.myAccount);
							// �����촰�ڱ��浽hashtable��
							ht_chat.put(msg.myAccount.getQqCode(), chat);
						}
						chat.show();
						chat.shake();// ����
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

	// �����˵�
	public void createMenu() {
		rightJPM = new JPopupMenu();
		sendMsgJMI = new JMenuItem("���ͼ�ʱ��Ϣ");
		lookInfoJMI = new JMenuItem("�鿴����");
		lookInfoJMI.addActionListener(this);
		setRemarkJMI = new JMenuItem("�޸��ǳ�");
		delJMI = new JMenuItem("ɾ������");
		rightJPM.add(sendMsgJMI);
		rightJPM.add(lookInfoJMI);
		rightJPM.add(setRemarkJMI);
		rightJPM.add(delJMI);
	}
	
	// �������촰��
		public void popChat() {
			if (!getFriendInfo())// ���ؼٱ�ʾû��ѡ���������
				return;
			// ��hashtable�л�ȡ���촰��,����������򷵻�null
			Chat chat = ht_chat.get(friendAccount.getQqCode());
			if (chat == null) {// ��һ������
				chat = new Chat(acc, friendAccount);
				// �����촰�ڱ��浽hashtable��
				ht_chat.put(friendAccount.getQqCode(), chat);
			}
			System.out.println("my port=" + acc.getPort() + ";friend port=" + friendAccount.getPort());
			chat.show();// ��ʾ����
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
