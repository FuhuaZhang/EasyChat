package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BqUI extends JFrame implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	JLabel [] bqicon;
	String iconlist[];
//	String imageName;
	Chat chat;
	
	public BqUI(Chat chat) {
		this.chat = chat;
		setResizable(false);
		setAlwaysOnTop(true);
		Container con = getContentPane();
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		con.setBackground(Color.WHITE);
		File file = new File("bq");//bq文件夹中存放着所有的表情
		iconlist = file.list();
		bqicon = new JLabel[iconlist.length];
		for(int i=0;i<iconlist.length;i++){
			bqicon[i]= new JLabel(new ImageIcon("bq/"+iconlist[i]));
			bqicon[i].addMouseListener(this);
			add(bqicon[i]);
		}
		
		setSize(300, 270);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public static void main(String[] args) {
//		new BqUI();
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2){
			for(int i=0;i<iconlist.length;i++){
				if(e.getSource()==bqicon[i]){
					chat.txtSend.insertIcon(bqicon[i].getIcon());
					dispose();
					break;
				}
			}
		}	
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
