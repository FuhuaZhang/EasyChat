package Message;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramSocket;

import javax.swing.text.StyledDocument;

import Database.Account;
//消息发送类
public class SendMsg implements Serializable{

	public int cmd;//命令字
	public Account myAccount;
	public Account friendAccount;
	public StyledDocument msg;
	//若要发送文件
	public String fileName;
	public byte []b;//发送文件，就是发送字节数组
	
}