package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Database.Account;
import Message.Cmd;
import Message.SendMsg;
import Message.SendSocket;

public class Chat extends JFrame implements ActionListener,ItemListener{
	
	JLabel lblChatInfo,lblboy,lblgril;//
	JTextPane txtReceive,txtSend;
	JButton btnFace,btnShake,btnSendFile,btnColor,btnSend,btnClose;
	JComboBox cbFont,cbSize,cbStyle, cbFace;
	
	Account myAccount,friendAccount;
	String style[] ={"正常","粗体","斜体","粗斜体"}; 
	String sfont[] = {"宋体","楷体","黑体","隶书"};
	Integer nsize[] = {10,11,12,14,16,18,20,24,28,36,48,72};

	public Chat(Account myAccount,Account friendAccount) {
		String title =friendAccount.getNickName();
		setTitle(title);
		ImageIcon ic = new ImageIcon(friendAccount.getFaceImage());
		setIconImage(ic.getImage());
		this.myAccount=myAccount;
		this.friendAccount=friendAccount;
		//右边的，放2个QQ秀
		JPanel epanel = new JPanel(new GridLayout(2,1,5,5));
		//中间的，分成2部分，上面部分是接收框，下面部分是放bpanel
		JPanel cpanel = new JPanel(new GridLayout(2,1,5,5));
		//分成3部分，上面放抖动等按钮,中间放发送框，下面放发送按钮等
		JPanel bpanel = new JPanel(new BorderLayout());
		//上面放抖动等按钮
		JPanel npanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));//上面
		//下面放发送按钮等
		JPanel npanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));//下面
		
		lblChatInfo = new JLabel(title, ic, JLabel.LEFT);
		add(lblChatInfo,BorderLayout.NORTH);
		add(cpanel);
		add(epanel,BorderLayout.EAST);
		txtReceive = new JTextPane();
		txtReceive.setEditable(false);
		cpanel.add(new JScrollPane(txtReceive));//上面部分是接收框
		cpanel.add(bpanel);//下面部分是放bpanel
		bpanel.add(npanel1,BorderLayout.NORTH);
		bpanel.add(npanel2,BorderLayout.SOUTH);
		btnFace = new JButton(new ImageIcon("face/sendFace.png"));
		btnFace.setMargin(new Insets(0, 0, 0, 0)); // 使按钮大小与图片大小一致
		btnShake = new JButton(new ImageIcon("face/zd.png"));
		btnShake.setMargin(new Insets(0, 0, 0, 0)); // 使按钮大小与图片大小一致
		cbFont = new JComboBox(sfont);
		cbSize = new JComboBox(nsize);
		cbStyle = new JComboBox(style);
		btnColor = new JButton("字体颜色");
		btnSendFile = new JButton("发送文件");
		npanel1.add(btnFace);
		npanel1.add(btnShake);
		npanel1.add(cbFont);
		npanel1.add(cbSize);
		npanel1.add(cbStyle);
		npanel1.add(btnColor);
		npanel1.add(btnSendFile);
		txtSend = new JTextPane();
		txtSend.requestFocus();
		bpanel.add(new JScrollPane(txtSend));
		btnSend = new JButton("发送(S)");
		btnSend.setMnemonic('S');
		btnClose = new JButton("关闭(C)");
		btnClose.setMnemonic('C');
		npanel2.add(btnSend);
		npanel2.add(btnClose);
		btnSend.addActionListener(this);
		btnSendFile.addActionListener(this);
		btnClose.addActionListener(this);
		btnColor.addActionListener(this);
		btnFace.addActionListener(this);
		btnShake.addActionListener(this);
		
		cbFont.addItemListener(this);
		cbSize.addItemListener(this);
		cbStyle.addItemListener(this);
		
		lblboy = new JLabel(new ImageIcon("face/boy.gif"));
		lblgril = new JLabel(new ImageIcon("face/girl.gif"));
		epanel.add(lblboy);
		epanel.add(lblgril);
		setResizable(false);
		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//隐藏窗口
		
	}
	//把发送框的内容提交到接收框，同时清除发送框的内容，，StyledDcocument可以发送各种文件
	public  void appendView(String name, StyledDocument xx)throws BadLocationException {
		//获取接收框的文档（内容）
		StyledDocument vdoc = txtReceive.getStyledDocument();//获取接收框的内容
		
		// 格式化时间
		Date date = new Date();//获取系统当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //设置显示时分秒的格式
		String time = sdf.format(date);//获取时分秒
		//创建一个属性集合,字体的大小呀颜色等
		SimpleAttributeSet as = new SimpleAttributeSet();
		// 显示谁说
		//vdoc.getLength()获取接收框中的原来内容的长度
		String s =name + "    " + time + "\n";
//		saveRecord(name,s);//保存聊天记录
		//将新发送的内容加到vdoc中
		vdoc.insertString(vdoc.getLength(), s, as);
		int end = 0;
		//处理显示的内容,
		while (end < xx.getLength()) {
			// 获得一个元素
			Element e0 = xx.getCharacterElement(end);
			//获取对应的风格，
			SimpleAttributeSet as1 = new SimpleAttributeSet();
			StyleConstants.setForeground(as1, StyleConstants.getForeground(e0.getAttributes()));
			StyleConstants.setFontSize(as1, StyleConstants.getFontSize(e0.getAttributes()));
			StyleConstants.setFontFamily(as1, StyleConstants.getFontFamily(e0.getAttributes()));
			//获取该元素的内容
			s = e0.getDocument().getText(end, e0.getEndOffset() - end);
			// 将元素加到浏览窗中，区分发送的信息是图片还是文本
			if("icon".equals(e0.getName())){
				vdoc.insertString(vdoc.getLength(), s, e0.getAttributes());
			}
			else{
				vdoc.insertString(vdoc.getLength(), s, as1);
//				saveRecord(name,s+"\n");//保存聊天记录
			}
			//getEndOffset（）函数就是获取当前元素的长度
			end = e0.getEndOffset(); 
		}
		// 输入一个换行
		vdoc.insertString(vdoc.getLength(), "\n", as);
		 
		// 设置显示视图加字符的位置与文档结尾，以便视图滚动
		txtReceive.setCaretPosition(vdoc.getLength());
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnSend){
			//生成发送类
			SendMsg msg = new SendMsg();
			msg.cmd = Cmd.CMD_SEND;
			msg.myAccount = myAccount;
			msg.friendAccount = friendAccount;
			msg.msg = txtSend.getStyledDocument();//聊天内容
			//发送消息
			new SendSocket().send(msg);
			//把发送框的内容显示到接收框
			try {
				appendView(myAccount.getNickName(), txtSend.getStyledDocument());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			//清空发送框的内容
			txtSend.removeAll();
			txtSend.setText("");
			
		}else if(e.getSource()==btnClose){
			dispose();//关闭当前窗口
		}else if(e.getSource()==btnColor){
			JColorChooser colordlg = new JColorChooser();
			Color c =colordlg.showDialog(null, "请选择颜色", Color.BLACK);
			txtSend.setForeground(c);
		}else if(e.getSource() == btnSendFile){
			//首先打开文件对话框，然后把文件的内容读取到字节数组中，再发送
			FileDialog fdlg = new FileDialog(this,"", FileDialog.LOAD);
			fdlg.show();
			String sfilename = fdlg.getDirectory()+"\\"+fdlg.getFile();
			File file = new File(sfilename);
			if(file.length() > 1024*64){
				JOptionPane.showMessageDialog(null, "文件过大");
				return;
			}
			try{
				byte b[] = new byte[(int)file.length()];
				FileInputStream fis = new FileInputStream(file);
				fis.read(b);
				fis.close();
				
				SendMsg msg = new SendMsg();
				msg.cmd = Cmd.CMD_SENDF;
				msg.myAccount = myAccount;
				msg.friendAccount = friendAccount;
				msg.fileName = fdlg.getFile();
				msg.b = b;
				new SendSocket().send(msg);
			}catch(Exception ex){
				
			}
		}
		else if(e.getSource()==btnShake){			
			SendMsg msg = new SendMsg();
			msg.cmd = Cmd.CMD_SHAKE;
			msg.myAccount = myAccount;
			msg.friendAccount = friendAccount;
			new SendSocket().send(msg);
			shake();
		}else if(e.getSource()==btnFace){
			BqUI bqui = new BqUI(this);
			bqui.show();
		}		
	}
	
	//抖动，不断更改坐标
	public void shake(){
		int left = this.getX();
		int top = this.getY();
		for(int i=0;i<20;i++){
			try {
				if(i%2==0){
					this.setLocation(left-5, top-5);
				}else{
					this.setLocation(left+5, top+5);
				}
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()== cbFont || e.getSource()==cbSize || e.getSource()==cbStyle){
			String font = sfont[cbFont.getSelectedIndex()]; 
			int size = nsize[cbSize.getSelectedIndex()];
			String st = style[cbStyle.getSelectedIndex()];
			int nstyle = Font.PLAIN;
			//{"正常","粗体","斜体","粗斜体"}; 
			if(st.equals(style[1])){
				nstyle=Font.BOLD;
			}else if(st.equals(style[2])){
				nstyle=Font.ITALIC;
			}else if(st.equals(style[3])){
				nstyle=Font.ITALIC+Font.BOLD;
			}
			Font f = new Font(font,nstyle,size);
			txtSend.setFont(f);
		}else if(e.getSource() == cbFace){
			Icon g = (Icon)cbFace.getSelectedItem();
			txtSend.insertIcon(g);
		}
	}
}
