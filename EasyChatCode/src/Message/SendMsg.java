package Message;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramSocket;

import javax.swing.text.StyledDocument;

import Database.Account;
//��Ϣ������
public class SendMsg implements Serializable{

	public int cmd;//������
	public Account myAccount;
	public Account friendAccount;
	public StyledDocument msg;
	//��Ҫ�����ļ�
	public String fileName;
	public byte []b;//�����ļ������Ƿ����ֽ�����
	
}