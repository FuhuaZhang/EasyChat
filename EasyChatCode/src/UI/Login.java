package UI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Database.Account;
import Database.Operate;

public class Login extends JFrame implements ActionListener, MouseListener {

	// ActionListner�ǵ���ť���ı��򣬲˵������ʱ����
	// ItemListener�ǵ�ĳ�ѡ�л�ȡ��ѡ�е�ʱ�򴥷�
	// MouseListener������¼�������굥�������£��ɿ����ɿ����ȶ���ʱ�������¼�
	private static final long serialVersionUID = 1L;

	private JLabel backgroundJL;
	private JLabel faceImageJL;
	private JComboBox qqCodeJCB;// ���������˵�����ͬʱ����������setEditable();
	private JPasswordField passwordJPF;
	private JLabel registerJL, findPwdJL;
	private JCheckBox memoryPwdJCB, autoLoginJCB;// �����ItemLstener�¼�
	private JButton loginJB;

	public Login() {
		//this.setUndecorated(true);//but���ϸþ�Ͳ����ƶ��ˣ���Ҫ�Լ������ƶ��Ĵ���͹رհ�ť��
		super("QQ2017");
		setForeground(new Color(255, 255, 255));
		Image icon = new ImageIcon("face/tubiao.png").getImage();
		setIconImage(icon);
		init();
	}

	public void init() {
		// ���ý����С
		getContentPane().setLayout(null);
		setBounds(666, 333, 430, 360);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		// ���ñ߿�

		// ���ý�����
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ImageIcon img = new ImageIcon("background/login-bg.png");
		backgroundJL = new JLabel(img);
		backgroundJL.setBounds(0, -20, 430, 200);
		getContentPane().add(backgroundJL);

		Icon icon = new ImageIcon("face/tubiao.png");
		faceImageJL = new JLabel();
		faceImageJL.setIcon(icon);//ͷ�����ò��ɹ�
		//ImageIcon qq = new ImageIcon("background/qq.jpg");
		//JLabel faceImageJL = new JLabel(qq);
		faceImageJL.setBounds(30, 220, 60, 60);
		getContentPane().add(faceImageJL);

		qqCodeJCB = new JComboBox();
		qqCodeJCB.setEditable(true);
		qqCodeJCB.setBounds(120, 200, 200, 30);
		qqCodeJCB.setToolTipText("3480757");
		getContentPane().add(qqCodeJCB);

		registerJL = new JLabel("ע���˺�");
		registerJL.setBounds(330, 200, 80, 30);
		registerJL.addMouseListener(this);
		registerJL.setForeground(Color.blue);
		getContentPane().add(registerJL);

		passwordJPF = new JPasswordField();
		passwordJPF.setBounds(120, 230, 200, 30);
		//passwordJPF.setText("z");
		getContentPane().add(passwordJPF);

		findPwdJL = new JLabel("�һ�����");
		findPwdJL.setBounds(330, 230, 80, 30);
		findPwdJL.addMouseListener(this);
		registerJL.setForeground(Color.blue);
		getContentPane().add(findPwdJL);

		memoryPwdJCB = new JCheckBox("��ס����");
		memoryPwdJCB.setBounds(120, 260, 80, 25);
		getContentPane().add(memoryPwdJCB);

		autoLoginJCB = new JCheckBox("�Զ���¼");
		autoLoginJCB.setBounds(200, 260, 80, 25);
		getContentPane().add(autoLoginJCB);

		loginJB = new JButton("��ȫ��¼");
		loginJB.setBounds(120, 290, 200, 30);
		// login.setOpaque(true);
		getContentPane().add(loginJB);
		loginJB.addActionListener(this);
		// login.addItemListener(this);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// ��Ҫ�ж�qq���Ƿ���ڣ���qq�Ŵ������ж������Ƿ�һ��,��Ҫ�Զ�����ͷ��
		if (e.getSource() == loginJB) {
			String qqcode = qqCodeJCB.getSelectedItem().toString();
			String password = String.valueOf(passwordJPF.getPassword());

			Account acc = new Account();
			try {
				acc.setQqCode(Integer.parseInt(qqcode));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "���������֣�");
				return;
			}
			acc.setPassword(password);

			acc = new Operate().login(acc);
			if (acc.getStatus() == 1) {
				//JOptionPane.showMessageDialog(null, "��½�ɹ�");
				Icon icon = new ImageIcon(acc.getFaceImage());
				//System.out.println(icon);
				faceImageJL.setIcon(icon);//ͷ�����ò��ɹ�
				new MainUI(acc);
				System.out.println("�����˳���½���棬����������");
				dispose();
			}

			// JOptionPane.showMessageDialog(null, e.getActionCommand());
		}

	}


	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == registerJL) {
			new Register();
			System.out.println("�˳���½���棬����ע�����");
			dispose();
		}else if(arg0.getSource() == findPwdJL){
			JOptionPane.showMessageDialog(null, "Ŀǰ�������һ����밥");
		}

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
