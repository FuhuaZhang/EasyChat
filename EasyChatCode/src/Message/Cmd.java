package Message;

/*
 * 系统中要用到的命令字，敞亮
 */
public class Cmd {

	//命令字
	public static final int CMD_LOGIN=1001;//登陆通知
	public static final int CMD_SEND=1002;//发送消息
	public static final int CMD_HIDDEN=1003;//隐身通知
	public static final int CMD_BUYS=1004;//忙碌通知
	public static final int CMD_LEVEL=1005;//下线通知
	public static final int CMD_SHAKE=1006;//抖动
	public static final int CMD_ADDFRIEND=1007;//添加好友
	public static final int CMD_AGREE=1008;//同意添加为好友
	public static final int CMD_DELFRIEND=1009;//删除好友
	
	public static final int CMD_SENDF = 1010;//发送文件
	public static final int CMD_FILESUC = 1011;//成功接收
	public static final int CMD_FILEFAIL = 1012;//拒绝接收
	
	public static final String STATUS_ONLINE="在线";
	public static final String STATUS_LEVEL="离线";
	public static final String STATUS_HIDDEN="隐身";
	public static final String STATUS_BUYS="忙碌";
	public static final String QQSTATUS[]={
		"在线",
		"离线",
		"隐身",
		"忙碌"
	};
	
	//常量
	public static final String TYPE_FRIEND="好友";
	public static final String TYPE_FAMILY="家人";
	public static final String TYPE_CLASSMATE="同学";
	public static final String TYPE_BLACK="黑名单";
	
	public static final String READ_NO="未读";
	public static final String READ_YES="已读";
}
