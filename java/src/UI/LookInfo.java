package UI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Database.Account;

public class LookInfo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel faceJL;	
	private JLabel nickNameJL;
	private JLabel signJL;
	
	private JLabel qqCodeJL;
	private JLabel remarkJL;
	//private JTextField remarkJTF;
	private JLabel genderJL;
	private JLabel birthdayJL;
	private JLabel placeJL;
	private JLabel phoneNumJL;
	
	public LookInfo(Account acc){
		setLayout(null);
		setBounds(400,100,450, 650);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);

		// ����Ԫ�ص��趨	
		Font f = new Font("����", 1, 20);
		setFont(f);
		
		JLabel upJL = new JLabel();
		upJL.setBounds(0,0,450, 190);
		upJL.setForeground(Color.blue);
		
		ImageIcon img2 = new ImageIcon(acc.getFaceImage());
		faceJL = new JLabel(img2);
		faceJL.setBounds(10, 90, 100, 100);
		upJL.add(faceJL);

		nickNameJL = new JLabel(acc.getNickName());
		nickNameJL.setBounds(120, 90, 80, 30);
		upJL.add(nickNameJL);
		
		signJL = new JLabel(acc.getSign());
		signJL.setBounds(120, 130, 100, 30);
		upJL.add(signJL);
		
		add(upJL);
		
		qqCodeJL = new JLabel("��    ��   "+acc.getQqCode()+"");
		qqCodeJL.setBounds(20, 200, 100, 30);
		add(qqCodeJL);
		
		nickNameJL = new JLabel("��    ��    "+acc.getNickName());
		nickNameJL.setBounds(20, 240, 100, 30);
		add(nickNameJL);
		
		remarkJL = new JLabel("��    ע   "+acc.getNickName());
		remarkJL.setBounds(20, 280, 100, 30);
		add(remarkJL);
		
		genderJL = new JLabel("��     ��   "+acc.getGender());
		genderJL.setBounds(20, 320, 100, 30);
		add(genderJL);
		
		birthdayJL = new JLabel("��     ��   "+acc.getBirthday());
		birthdayJL.setBounds(20, 360, 100, 30);
		add(birthdayJL);
		
		placeJL = new JLabel("���ڵ�    "+acc.getPlace());
		placeJL.setBounds(20, 400, 100, 30);
		add(placeJL);
		
		phoneNumJL = new JLabel("�ֻ���     "+acc.getPhoneNum());
		phoneNumJL.setBounds(20, 440, 100, 30);
		add(phoneNumJL);
		
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new LookInfo();
	}
}

