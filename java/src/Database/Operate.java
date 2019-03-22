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

		// 注册用户
		public boolean addUser(Account acc)// 在RegUsers中把所有的呢绒都放到了Account中
		{
			boolean bok = false;
			Connection conn = new Connecte().getConn();// 勿忘DBConn后面的括号
			try {
				// 创建一个PreparedStatement对象；
				String sql = "insert into account values(?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				// 顺序要跟数据库中顺序要一一对应；
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

				int n = pstmt.executeUpdate();// 执行失败了？？？
				if (n > 0)
					bok = true;
				System.out.println(bok + "成功将数据加入到数据库中");
				pstmt.close();
				conn.close();// 先写连接，写完连接就写关闭
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bok;
		}

		// 判断端口是否被别人占用,true为已被占用，false为未被占用
		public boolean isPort(int port) {
			boolean bok = false;
			Connection conn = new Connecte().getConn();// 勿忘DBConn后面的括号
			try {
				String sql = "select port from account where port=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				pstmt.setInt(i++, port);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())// 已被占用
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
		//判断是否被系统中其他进程占用端口
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

		// 登录！！！登录后必须将该用户的所有信息读取出来和返回
		public Account login(Account acc) {
			Connection conn = new Connecte().getConn();// 勿忘DBConn后面的括号
			try {
				String sql = "select * from account where qqCode=? and password=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				pstmt.setInt(i++, acc.getQqCode());
				pstmt.setString(i++, acc.getPassword());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())// 登录成功
				{
					System.out.println("成功登陆...");
					// 修改状态为在线状态(1)//若用set，则只是修改了acc中的状态，但是需要修改数据库中的状态，所以如下函数可以实现
					modifySatus(acc.getQqCode(), 1);
					// qq号和密码不需要在取了，因为在登录界面里有
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
					JOptionPane.showMessageDialog(null, "请检查密码和账号的正确性");
				}
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return acc;
		}

		// 修改状态 status(0：离线； 1：在线；2：隐身；4：忙碌)
		public boolean modifySatus(int qqcode, int status)// 在RegUsers中把所有的呢绒都放到了Account中
		{
			boolean bok = false;
			Connection conn = new Connecte().getConn();// 勿忘DBConn后面的括号
			try {
				String sql = "update account set status=? where qqcode=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int i = 1;
				// 顺序要跟数据库中顺序要一一对应；
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

		// 修改备注
		public boolean changeRemark(Account self, Account friend, String remark) {
			boolean bRet = false;
			try {
				// 获取数据库连接
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

		//调用该函数获取传入账号的好友信息
		public Vector<Account> getAllFriendsInfo(Account acc) {
			Connection conn = new Connecte().getConn();// 勿忘DBConn后面的括号
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
					Account a = new Account();// 一条记录创建一个Account对象
					
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
					
					allInfo.add(a);//将创建的Account对象加入到要返回的数组中
				}
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return allInfo;
		}

		// 查找好友
		public Account searchFriend(int qqCode) {
			try {
				// 获取数据库连接
				Connection conn = new Connecte().getConn();
				Statement stmt = conn.createStatement();
				String sql = "select * from account where qqCode =";
				sql += qqCode;
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					Account a = new Account();// 一条记录创建一个Account对象
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
					JOptionPane.showMessageDialog(null, "没有该qq好的信息");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
}
