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

		// 界面元素的设定
		Font f = new Font("隶书", 1, 20);

		ImageIcon img2 = new ImageIcon("background/register3.png");
		registerJL = new JLabel(img2);
		registerJL.setBounds(0, 80, 700, 60);
		add(registerJL);
		ImageIcon img = new ImageIcon("background/register2.png");
		welcomeJL = new JLabel(img);
		welcomeJL.setBounds(0, 0, 700, 110);
		add(welcomeJL);

		nickNameJL = new JLabel("昵称");
		nickNameJL.setBounds(170, 200, 80, 30);
		nickNameJL.setFont(f);
		nickNameJL.setForeground(Color.blue);
		add(nickNameJL);
		nickNameJTF = new JTextField();
		add(nickNameJTF);
		nickNameJTF.setBounds(270, 200, 200, 30);

		passwordJL = new JLabel("密码");
		passwordJL.setBounds(170, 250, 80, 30);
		passwordJL.setFont(f);
		passwordJL.setForeground(Color.blue);
		add(passwordJL);
		passwordJPF = new JPasswordField();
		add(passwordJPF);
		passwordJPF.setBounds(270, 250, 200, 30);

		confPwdJL = new JLabel("确认密码");
		confPwdJL.setBounds(150, 300, 100, 30);
		confPwdJL.setFont(f);
		confPwdJL.setForeground(Color.blue);
		add(confPwdJL);
		confPwdJPF = new JPasswordField();
		add(confPwdJPF);
		confPwdJPF.setBounds(270, 300, 200, 30);

		genderJL = new JLabel("性别");
		genderJL.setBounds(170, 350, 80, 30);
		genderJL.setFont(f);
		genderJL.setForeground(Color.blue);
		add(genderJL);
		male = new JRadioButton("男", true);
		male.setBounds(270, 350, 50, 30);
		female = new JRadioButton("女", false);
		female.setBounds(270, 400, 50, 30);
		ButtonGroup bg = new ButtonGroup();
		bg.add(male);
		bg.add(female);
		JPanel checkPanel = new JPanel();
		checkPanel.add(male);
		checkPanel.add(female);
		checkPanel.setBounds(270, 350, 100, 30);
		add(checkPanel);

		birthdayJL = new JLabel("生日");
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

		placeJL = new JLabel("所在地");
		placeJL.setBounds(160, 450, 80, 30);
		placeJL.setFont(f);
		placeJL.setForeground(Color.blue);
		add(placeJL);
		placeJTF = new JTextField();
		add(placeJTF);
		placeJTF.setBounds(270, 450, 200, 30);

		phoneNumJL = new JLabel("手机号");
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
		registerJB.addActionListener(new RegisterListener());// 加入监听事件

		qqZoneJCB = new JCheckBox("同时开通QQ空间", true);
		qqZoneJCB.setBounds(220, 665, 200, 30);
		add(qqZoneJCB);
		agreementJCB = new JCheckBox("我已阅读并同意相关服务条款和隐私条件", true);
		agreementJCB.setBounds(220, 700, 250, 30);
		add(agreementJCB);

		setVisible(true);
	}

	class RegisterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			//对界面元素的控制
			if (nickNameJTF.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入英文昵称");
				return;
			}
			String sPasswordJPF = new String(passwordJPF.getPassword());
			String sConfPwdJPF = new String(confPwdJPF.getPassword());
			
			if (sPasswordJPF.length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入密码");
				return;
			}
			if (!sPasswordJPF.equals(sConfPwdJPF)) {
				JOptionPane.showMessageDialog(null, "登录密码和确认密码不一致");
				return;
			}

			// 将界面元素存储到Account中
			Account account = new Account();
			// qq号为主键，不会重复插入的，所以可以不判断，但是如果要提示是因为密码重复而失败的，还是得判断和检查
			qqCode = 3000000 + (int) (Math.random() * 1000000);
			account.setQqCode(qqCode);
			account.setNickName(nickNameJTF.getText().trim());
			account.setPassword(String.valueOf(passwordJPF.getPassword()));
			// Attention 需要根据哪一个被选中了的结果来修改数据库中的量，所以用if语句
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
			//自动获取ip
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

			System.out.println("写入数据库");
			// 将account用户的数据加入到数据库account表中
			boolean b = db.addUser(account);
			if (b) {
				String str = "恭喜你，注册成功,快快登录吧\n" + qqCode;
				JOptionPane.showMessageDialog(null, str);
				dispose();// 关闭窗口
				new Login();
			} else {
				JOptionPane.showMessageDialog(null, "注册失败");
			}
			// 操作完一定要查看数据库，查看自己在注册界面输入的数据和存储到数据库的数据是否一致
		}
	}
}
