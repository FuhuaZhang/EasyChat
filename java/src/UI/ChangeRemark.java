package UI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChangeRemark extends JFrame {
	private JLabel remarkJL;
	private JTextField remarkJTF;
	private JButton sureJB, cancelJB;

	public ChangeRemark() {

		setSize(330, 160);
		setLayout(null);

		remarkJL = new JLabel("�����뱸ע������");
		remarkJL.setBounds(10, 10, 110, 30);
		add(remarkJL);

		remarkJTF = new JTextField();
		remarkJTF.setBounds(10, 45, 290, 30);
		add(remarkJTF);

		sureJB = new JButton("ȷ��");
		sureJB.setBounds(155, 80, 60, 30);
		add(sureJB);

		cancelJB = new JButton("ȡ��");
		cancelJB.setBounds(230, 80, 60, 30);
		add(cancelJB);
		
		setVisible(true);
	}

	public String getRemark(){
		return remarkJTF.getText();
	}
	
	public static void main(String[] args) {
		new ChangeRemark();
	}
}