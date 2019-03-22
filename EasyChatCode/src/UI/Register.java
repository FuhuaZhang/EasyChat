package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Database.Account;
import Database.Operate;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;

	private int qqCode;
	private JLabel welcomeJL;
	private JLabel registerJL;
	private JLabel nickNameJL;
	private JTextField nickNameJTF;
	private JLabel passwordJL;
	private JPasswordField passwordJPF;
	private JLabel confPwdJL;
	private JPasswordField confPwdJPF;
	private JLabel genderJL;
	private JRadioButton male;
	private JRadioButton female;
	private JLabel birthdayJL;
	private JComboBox<String> yearJCB;
	private String[] years = new String[] { "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009",
			"2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999" };
	private JComboBox<String> monthJCB;
	private String[] months = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	private JComboBox<String> dateJCB;
	private String[] dates = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	private JLabel placeJL;
	private JTextField placeJTF;
	private JLabel phoneNumJL;
	private JTextField phoneNumJTF;
	private JButton registerJB;
	private JCheckBox qqZoneJCB;
	private JCheckBox agreementJCB;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Register();
	}

	public Register() {
		init();
	}

	public void init() {

		setLayout(null);
		setBounds(400, 100, 700, 900);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);

		// ����Ԫ�ص��趨
		Font f = new Font("����", 1, 20);

		ImageIcon img2 = new ImageIcon("background/register3.png");
		registerJL = new JLabel(img2);
		registerJL.setBounds(0, 80, 700, 60);
		add(registerJL);
		ImageIcon img = new ImageIcon("background/register2.png");
		welcomeJL = new JLabel(img);
		welcomeJL.setBounds(0, 0, 700, 110);
		add(welcomeJL);

		nickNameJL = new JLabel("�ǳ�");
		nickNameJL.setBounds(170, 200, 80, 30);
		nickNameJL.setFont(f);
		nickNameJL.setForeground(Color.blue);
		add(nickNameJL);
		nickNameJTF = new JTextField();
		add(nickNameJTF);
		nickNameJTF.setBounds(270, 200, 200, 30);

		passwordJL = new JLabel("����");
		passwordJL.setBounds(170, 250, 80, 30);
		passwordJL.setFont(f);
		passwordJL.setForeground(Color.blue);
		add(passwordJL);
		passwordJPF = new JPasswordField();
		add(passwordJPF);
		passwordJPF.setBounds(270, 250, 200, 30);

		confPwdJL = new JLabel("ȷ������");
		confPwdJL.setBounds(150, 300, 100, 30);
		confPwdJL.setFont(f);
		confPwdJL.setForeground(Color.blue);
		add(confPwdJL);
		confPwdJPF = new JPasswordField();
		add(confPwdJPF);
		confPwdJPF.setBounds(270, 300, 200, 30);

		genderJL = new JLabel("�Ա�");
		genderJL.setBounds(170, 350, 80, 30);
		genderJL.setFont(f);
		genderJL.setForeground(Color.blue);
		add(genderJL);
		male = new JRadioButton("��", true);
		male.setBounds(270, 350, 50, 30);
		female = new JRadioButton("Ů", false);
		female.setBounds(270, 400, 50, 30);
		ButtonGroup bg = new ButtonGroup();
		bg.add(male);
		bg.add(female);
		JPanel checkPanel = new JPanel();
		checkPanel.add(male);
		checkPanel.add(female);
		checkPanel.setBounds(270, 350, 100, 30);
		add(checkPanel);

		birthdayJL = new JLabel("����");
		birthdayJL.setBounds(170, 400, 80, 30);
		birthdayJL.setFont(f);
		birthdayJL.setForeground(Color.blue);
		add(birthdayJL);
		yearJCB = new JComboBox<>(years);
		yearJCB.setBounds(270, 400, 80, 30);
		add(yearJCB);
		monthJCB = new JComboBox<>(months);
		monthJCB.setBounds(350, 400, 80, 30);
		add(monthJCB);
		dateJCB = new JComboBox<>(dates);
		dateJCB.setBounds(430, 400, 80, 30);
		add(dateJCB);

		placeJL = new JLabel("���ڵ�");
		placeJL.setBounds(160, 450, 80, 30);
		placeJL.setFont(f);
		placeJL.setForeground(Color.blue);
		add(placeJL);
		placeJTF = new JTextField();
		add(placeJTF);
		placeJTF.setBounds(270, 450, 200, 30);

		phoneNumJL = new JLabel("�ֻ���");
		phoneNumJL.setBounds(160, 500, 80, 30);
		phoneNumJL.setFont(f);
		phoneNumJL.setForeground(Color.blue);
		add(phoneNumJL);
		phoneNumJTF = new JTextField();
		add(phoneNumJTF);
		phoneNumJTF.setBounds(270, 500, 200, 30);

		Icon bgimg = new ImageIcon("background/register4.png");
		registerJB = new JButton(bgimg);
		registerJB.setBounds(220, 600, 300, 50);
		add(registerJB);
		registerJB.addActionListener(new RegisterListener());// ��������¼�

		qqZoneJCB = new JCheckBox("ͬʱ��ͨQQ�ռ�", true);
		qqZoneJCB.setBounds(220, 665, 200, 30);
		add(qqZoneJCB);
		agreementJCB = new JCheckBox("�����Ķ���ͬ����ط����������˽����", true);
		agreementJCB.setBounds(220, 700, 250, 30);
		add(agreementJCB);

		setVisible(true);
	}

	class RegisterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			//�Խ���Ԫ�صĿ���
			if (nickNameJTF.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "������Ӣ���ǳ�");
				return;
			}
			String sPasswordJPF = new String(passwordJPF.getPassword());
			String sConfPwdJPF = new String(confPwdJPF.getPassword());
			
			if (sPasswordJPF.length() == 0) {
				JOptionPane.showMessageDialog(null, "����������");
				return;
			}
			if (!sPasswordJPF.equals(sConfPwdJPF)) {
				JOptionPane.showMessageDialog(null, "��¼�����ȷ�����벻һ��");
				return;
			}

			// ������Ԫ�ش洢��Account��
			Account account = new Account();
			// qq��Ϊ�����������ظ�����ģ����Կ��Բ��жϣ��������Ҫ��ʾ����Ϊ�����ظ���ʧ�ܵģ����ǵ��жϺͼ��
			qqCode = 3000000 + (int) (Math.random() * 1000000);
			account.setQqCode(qqCode);
			account.setNickName(nickNameJTF.getText().trim());
			account.setPassword(String.valueOf(passwordJPF.getPassword()));
			// Attention ��Ҫ������һ����ѡ���˵Ľ�����޸����ݿ��е�����������if���
			if (male.isSelected())
				account.setGender("boy");
			else
				account.setGender("girl");
			account.setBirthday(years[yearJCB.getSelectedIndex()] +"-"+ months[monthJCB.getSelectedIndex()]
					+"-"+ dates[dateJCB.getSelectedIndex()]);
			account.setPlace(placeJTF.getText().trim());
			account.setPhoneNum(phoneNumJTF.getText().trim());
			int n = (int)(Math.random() * 10);
			account.setFaceImage("face/"+n+".png");
			//�Զ���ȡip
			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				account.setIP(ip);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int port = (int) (Math.random() * 10000);
			Operate db = new Operate();
			while (true) {
				if (db.isPort(port)) {
					port = (int) (Math.random() * 10000);
					System.out.println(port);
				} else
					break;
			}
			account.setPort(port);
			account.setStatus(0);
			account.setSign(null);

			System.out.println("д�����ݿ�");
			// ��account�û������ݼ��뵽���ݿ�account����
			boolean b = db.addUser(account);
			if (b) {
				String str = "��ϲ�㣬ע��ɹ�,����¼��\n" + qqCode;
				JOptionPane.showMessageDialog(null, str);
				dispose();// �رմ���
				new Login();
			} else {
				JOptionPane.showMessageDialog(null, "ע��ʧ��");
			}
			// ������һ��Ҫ�鿴���ݿ⣬�鿴�Լ���ע�������������ݺʹ洢�����ݿ�������Ƿ�һ��
		}
	}
}
