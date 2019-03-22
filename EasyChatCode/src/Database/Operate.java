package Database;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Operate {

		// ע���û�
		public boolean addUser(Account acc)// ��RegUsers�а����е����޶��ŵ���Account��
		{
			boolean bok = false;
			Connection conn = new Connecte().getConn();// ����DBConn���������
			try {
				// ����һ��PreparedStatement����
				String sql = "insert into account values(?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				// ˳��Ҫ�����ݿ���˳��Ҫһһ��Ӧ��
				pstmt.setInt(i++, acc.getQqCode());
				pstmt.setString(i++, acc.getNickName());
				pstmt.setString(i++, acc.getPassword());
				pstmt.setString(i++, acc.getGender());
				pstmt.setString(i++, acc.getBirthday());
				pstmt.setString(i++, acc.getPlace());
				pstmt.setString(i++, acc.getPhoneNum());
				pstmt.setString(i++, acc.getFaceImage());
				pstmt.setString(i++, acc.getIP());
				pstmt.setInt(i++, acc.getPort());
				pstmt.setInt(i++, acc.getStatus());
				pstmt.setString(i++, acc.getSign());

				int n = pstmt.executeUpdate();// ִ��ʧ���ˣ�����
				if (n > 0)
					bok = true;
				System.out.println(bok + "�ɹ������ݼ��뵽���ݿ���");
				pstmt.close();
				conn.close();// ��д���ӣ�д�����Ӿ�д�ر�
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bok;
		}

		// �ж϶˿��Ƿ񱻱���ռ��,trueΪ�ѱ�ռ�ã�falseΪδ��ռ��
		public boolean isPort(int port) {
			boolean bok = false;
			Connection conn = new Connecte().getConn();// ����DBConn���������
			try {
				String sql = "select port from account where port=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				pstmt.setInt(i++, port);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())// �ѱ�ռ��
				{
					bok = true;
				}
				pstmt.close();
				conn.close();
				return bok;
			} catch (Exception e) {
				InetAddress theAddress;
				try {
					theAddress = InetAddress.getByName("127.0.0.1");
					try {
						Socket socket = new Socket(theAddress, port);
						bok = true;
					} catch (IOException ex) {

					}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				return bok;
			}
		}
		//�ж��Ƿ�ϵͳ����������ռ�ö˿�
		public static boolean isPortUsing(String host, int port) throws UnknownHostException {
			boolean flag = false;
			InetAddress theAddress = InetAddress.getByName(host);
			try {
				Socket socket = new Socket(theAddress, port);
				flag = true;
			} catch (IOException e) {

			}
			return flag;
		}

		// ��¼��������¼����뽫���û���������Ϣ��ȡ�����ͷ���
		public Account login(Account acc) {
			Connection conn = new Connecte().getConn();// ����DBConn���������
			try {
				String sql = "select * from account where qqCode=? and password=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				pstmt.setInt(i++, acc.getQqCode());
				pstmt.setString(i++, acc.getPassword());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())// ��¼�ɹ�
				{
					System.out.println("�ɹ���½...");
					// �޸�״̬Ϊ����״̬(1)//����set����ֻ���޸���acc�е�״̬��������Ҫ�޸����ݿ��е�״̬���������º�������ʵ��
					modifySatus(acc.getQqCode(), 1);
					// qq�ź����벻��Ҫ��ȡ�ˣ���Ϊ�ڵ�¼��������
					acc.setNickName(rs.getString("nickName").trim());
					acc.setGender(rs.getString("gender").trim());
					acc.setBirthday(rs.getString("birthday").trim());
					acc.setPlace(rs.getString("place".trim()));
					acc.setPhoneNum(rs.getString("phoneNum").trim());
					acc.setFaceImage(rs.getString("faceImage"));
					acc.setIP(rs.getString("IP").trim());
					// System.out.println("IP" + rs.getString("IP"));
					acc.setPort(rs.getInt("port"));
					acc.setStatus(1);
					modifySatus(acc.getQqCode(), 1);
					acc.setSign("sign");
				} else {
					JOptionPane.showMessageDialog(null, "����������˺ŵ���ȷ��");
				}
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return acc;
		}

		// �޸�״̬ status(0�����ߣ� 1�����ߣ�2������4��æµ)
		public boolean modifySatus(int qqcode, int status)// ��RegUsers�а����е����޶��ŵ���Account��
		{
			boolean bok = false;
			Connection conn = new Connecte().getConn();// ����DBConn���������
			try {
				String sql = "update account set status=? where qqcode=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				// ˳��Ҫ�����ݿ���˳��Ҫһһ��Ӧ��
				pstmt.setInt(i++, status);
				pstmt.setInt(i++, qqcode);

				if ((pstmt.executeUpdate()) > 0)
					bok = true;
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bok;
		}

		// �޸ı�ע
		public boolean changeRemark(Account self, Account friend, String remark) {
			boolean bRet = false;
			try {
				// ��ȡ���ݿ�����
				Connection conn = new Connecte().getConn();
				Statement stmt = conn.createStatement();
				String sql = "update friend set remark='";
				sql += remark + "' where selfAccount=";
				sql += self.getQqCode() + "and friendAccount =";
				sql += friend.getQqCode();
				System.out.println(sql);
				if (stmt.executeUpdate(sql) > 0)
					bRet = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return bRet;
		}

		//���øú�����ȡ�����˺ŵĺ�����Ϣ
		public Vector<Account> getAllFriendsInfo(Account acc) {
			Connection conn = new Connecte().getConn();// ����DBConn���������
			Vector<Account> allInfo = new Vector<>();
			try {
				//String sql = "select * from account where qqCode in(select friendAccount from friend where selfAccount=?)";
				String sql = "select * from account";
				//PreparedStatement pstmt = conn.prepareStatement(sql);
				Statement pstmt = conn.createStatement();

				//int i = 1;
				//pstmt.setInt(i++, acc.getQqCode());
				ResultSet rs = pstmt.executeQuery(sql);
				System.out.println(rs.next()+"rs.next()");
				while (rs.next())
				{
					Account a = new Account();// һ����¼����һ��Account����
					
					a.setQqCode(rs.getInt("qqCode"));
					a.setPassword(rs.getString("password"));
					a.setNickName(rs.getString("nickName").trim());
					a.setGender(rs.getString("gender").trim());
					a.setBirthday(rs.getString("birthday").trim());
					a.setPlace(rs.getString("place".trim()));
					a.setPhoneNum(rs.getString("phoneNum").trim());
					a.setFaceImage(rs.getString("faceImage"));
					a.setIP(rs.getString("IP").trim());
					a.setPort(rs.getInt("port"));
					a.setStatus(rs.getInt("status"));
					a.setSign(rs.getString("sign"));
					
					allInfo.add(a);//��������Account������뵽Ҫ���ص�������
				}
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return allInfo;
		}

		// ���Һ���
		public Account searchFriend(int qqCode) {
			try {
				// ��ȡ���ݿ�����
				Connection conn = new Connecte().getConn();
				Statement stmt = conn.createStatement();
				String sql = "select * from account where qqCode =";
				sql += qqCode;
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					Account a = new Account();// һ����¼����һ��Account����
					a.setQqCode(rs.getInt("qqCode"));
					a.setPassword(rs.getString("password"));
					a.setNickName(rs.getString("nickName").trim());
					a.setGender(rs.getString("gender").trim());
					a.setBirthday(rs.getString("birthday").trim());
					a.setPlace(rs.getString("place".trim()));
					a.setPhoneNum(rs.getString("phoneNum").trim());
					a.setFaceImage(rs.getString("faceImage"));
					a.setIP(rs.getString("IP").trim());
					a.setPort(rs.getInt("port"));
					a.setStatus(rs.getInt("status"));
					a.setSign(rs.getString("sign"));
					return a;
				} else {
					JOptionPane.showMessageDialog(null, "û�и�qq�õ���Ϣ");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
}
