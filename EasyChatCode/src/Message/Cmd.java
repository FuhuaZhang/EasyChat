package Message;

/*
 * ϵͳ��Ҫ�õ��������֣�����
 */
public class Cmd {

	//������
	public static final int CMD_LOGIN=1001;//��½֪ͨ
	public static final int CMD_SEND=1002;//������Ϣ
	public static final int CMD_HIDDEN=1003;//����֪ͨ
	public static final int CMD_BUYS=1004;//æµ֪ͨ
	public static final int CMD_LEVEL=1005;//����֪ͨ
	public static final int CMD_SHAKE=1006;//����
	public static final int CMD_ADDFRIEND=1007;//��Ӻ���
	public static final int CMD_AGREE=1008;//ͬ�����Ϊ����
	public static final int CMD_DELFRIEND=1009;//ɾ������
	
	public static final int CMD_SENDF = 1010;//�����ļ�
	public static final int CMD_FILESUC = 1011;//�ɹ�����
	public static final int CMD_FILEFAIL = 1012;//�ܾ�����
	
	public static final String STATUS_ONLINE="����";
	public static final String STATUS_LEVEL="����";
	public static final String STATUS_HIDDEN="����";
	public static final String STATUS_BUYS="æµ";
	public static final String QQSTATUS[]={
		"����",
		"����",
		"����",
		"æµ"
	};
	
	//����
	public static final String TYPE_FRIEND="����";
	public static final String TYPE_FAMILY="����";
	public static final String TYPE_CLASSMATE="ͬѧ";
	public static final String TYPE_BLACK="������";
	
	public static final String READ_NO="δ��";
	public static final String READ_YES="�Ѷ�";
}
