package Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Vector;

import Database.Account;

public class SendSocket {

	//发送消息函数，将要发送的信息封装到字节流中，将其放到DatagramSocket中，然后用DatagramSocket发送操作
	public void send(SendMsg msg){
		try {
			
			//创建一个字节数组输出流
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//把对象输出到字节数组中
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(msg);
			//把要发送的数据转换为字节数组
			byte b[]=bos.toByteArray();
			
			//发送操作
			//定义Socket
			DatagramSocket socket = new DatagramSocket();
			//获取好友的IP地址
			InetAddress addr = InetAddress.getByName(msg.friendAccount.getIP());
			//获取好友的接收端口
			int port = msg.friendAccount.getPort();
			//生成发送报
			DatagramPacket pack = new DatagramPacket(b,0,b.length,addr,port);
			socket.send(pack);//发送
			System.out.println(msg.cmd +"==发送消息");
			socket.close();
			oos.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
