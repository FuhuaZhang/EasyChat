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

	//������Ϣ��������Ҫ���͵���Ϣ��װ���ֽ����У�����ŵ�DatagramSocket�У�Ȼ����DatagramSocket���Ͳ���
	public void send(SendMsg msg){
		try {
			
			//����һ���ֽ����������
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//�Ѷ���������ֽ�������
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(msg);
			//��Ҫ���͵�����ת��Ϊ�ֽ�����
			byte b[]=bos.toByteArray();
			
			//���Ͳ���
			//����Socket
			DatagramSocket socket = new DatagramSocket();
			//��ȡ���ѵ�IP��ַ
			InetAddress addr = InetAddress.getByName(msg.friendAccount.getIP());
			//��ȡ���ѵĽ��ն˿�
			int port = msg.friendAccount.getPort();
			//���ɷ��ͱ�
			DatagramPacket pack = new DatagramPacket(b,0,b.length,addr,port);
			socket.send(pack);//����
			System.out.println(msg.cmd +"==������Ϣ");
			socket.close();
			oos.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
