package MyAddress;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Pattern;

public class MyAddressbook extends JFrame implements MouseListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_name;
	private JTextField textField_birth;
	private JTextField textField_number;
	private JTextField textField_mail;
	private JTextField textField_other;
	private JButton btnNewButton_xinjian;
	private JButton btnNewButton_xiugai;
	private JButton btnNewButton_baocun;
	private JButton btnNewButton_shanchu;
	private JButton btnNewButton_tuichu;

	//手机号正则表达式
	public static final String REGEX_MOBILE = 
			"^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	//邮箱正则表达式
	//public static final String REGEX_EMAIL = 
			//"^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	//出生日期正则表达式
	public static final String REGEX_BIRTH = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	
	private String username;
	private String userbirth;
	private String usernumber;
	private String usermail;
	private String userother;
	
	public static JList list = new JList();
	public static Vector <String> v = new Vector <String>();
	public int mode = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyAddressbook frame = new MyAddressbook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MyAddressbook() {
		
		setTitle("我的通讯录");
		Liulan.liulan();
		initUI();
		init();
	}
	//程序初始化
	private void init() {
		setLocationRelativeTo(null);
		list.addMouseListener(this);
		btnNewButton_xinjian.addMouseListener(this);
		btnNewButton_baocun.addMouseListener(this);
		btnNewButton_xiugai.addMouseListener(this);
		btnNewButton_shanchu.addMouseListener(this);
		btnNewButton_tuichu.addMouseListener(this);
		setMode(mode);
		try {
			MySqlStatement.MySql();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	//设置模式：浏览0、新建1、修改2
	public void setMode(int mode) {
		switch(mode) {
		case 0:
			btnNewButton_xinjian.setEnabled(true);
			btnNewButton_xiugai.setEnabled(true);
			btnNewButton_baocun.setEnabled(false);
			btnNewButton_shanchu.setEnabled(true);
			textField_name.setEditable(false);
			textField_birth.setEditable(false);
			textField_number.setEditable(false);
			textField_mail.setEditable(false);
			textField_other.setEditable(false);
			break;
		case 1:
			btnNewButton_xinjian.setEnabled(false);
			btnNewButton_xiugai.setEnabled(false);
			btnNewButton_baocun.setEnabled(true);
			btnNewButton_shanchu.setEnabled(false);
			textField_name.setEditable(true);
			textField_birth.setEditable(true);
			textField_number.setEditable(true);
			textField_mail.setEditable(true);
			textField_other.setEditable(true);
			clearAll();
			break;
		case 2:	
			btnNewButton_xinjian.setEnabled(false);
			btnNewButton_xiugai.setEnabled(false);
			btnNewButton_baocun.setEnabled(true);
			btnNewButton_shanchu.setEnabled(false);
			textField_name.setEditable(false);
			textField_birth.setEditable(true);
			textField_number.setEditable(true);
			textField_mail.setEditable(true);
			textField_other.setEditable(true);
			break;
		}
	}
	//保存按钮实现方法
	public void mysave() {
		
		boolean j = false;
			try {
				
				String sql1 = "select * from adressbook where name ='"+username+"'";
				ResultSet rs = MySqlStatement.stmt.executeQuery(sql1);
				while(rs.next()) {
					j =true;
				}
				if(j) {
					JOptionPane.showMessageDialog(this, "姓名重复，请重新输入！");
				}else {
				String sql = "insert into adressbook(name,birth,number,mail,other) "
				+ "values('"+username+"','"+userbirth+"','"+usernumber+"',"
						+ "'"+usermail+"','"+userother+"')";
				int i = MySqlStatement.stmt.executeUpdate(sql);
				if(i == 1) {
					JOptionPane.showMessageDialog(this, "保存成功！");
					Liulan.liulan();
					clearAll();
					}else {
						JOptionPane.showMessageDialog(this, "保存失败！");
						}
				}
				} catch (SQLException e) {
						e.printStackTrace();
						}
	}
	//修改按钮实现方法
	public void mychange() {
		username = textField_name.getText();
		userbirth = textField_birth.getText();
		usernumber = textField_number.getText();
		usermail = textField_mail.getText();
		userother = textField_other.getText();
		
			try {
				String sql = "update adressbook set birth='"+userbirth+"',"
						+ "number='"+usernumber+"',"+"mail='"+usermail+"',"
								+ "other='"+userother+"' where name ='"+username+"'";
				int i = MySqlStatement.stmt.executeUpdate(sql);
				if(i == 1) {
					JOptionPane.showMessageDialog(this, "修改成功！");
					//Liulan.liulan();
					clearAll();
					}else {
						JOptionPane.showMessageDialog(this, "修改失败！");
						}
				} catch (SQLException e) {
						e.printStackTrace();
						}
		}
	//删除按钮实现方法
	public void mydelete() {
		username = textField_name.getText();
		try {
			//MySqlStatement.MySql();
			String sql = "delete from adressbook where name ='"+username+"'";
			int i = MySqlStatement.stmt.executeUpdate(sql);
			
			if(i == 1) {
				JOptionPane.showMessageDialog(this, "删除成功！");
				Liulan.liulan();
				clearAll();
			}else {
				JOptionPane.showMessageDialog(this, "删除失败！");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//退出按钮实现方法
	public void myexit() {
		int i = JOptionPane.showConfirmDialog(this, "你确定要退出吗？");
		if(i == JOptionPane.YES_OPTION) {
			Liulan.liulan();
			System.exit(0);
		}
	}
	//正则表达式检验姓名是否为空、出生日期是否符合xxxx-xx-xx格式、
	//电子邮件是否符合xxxx@xxxx.xxx格式
	public boolean verify() {
		
		if(Pattern.matches("REGEX_MOBILE", "usernumber")
				/*Pattern.matches("REGEX_BIRTH", "userbirth")*/)
		return true;
		else 
			return false;
	}
	//清空所有文本框
	public void clearAll() {
		textField_name.setText("");
		textField_birth.setText("");
		textField_number.setText("");
		textField_mail.setText("");
		textField_other.setText("");
	}
	//界面UI
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 623);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u4E2A\u4EBA\u4FE1\u606F");
		label.setBounds(14, 27, 72, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u59D3\u540D ");
		label_1.setBounds(24, 58, 38, 18);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("\u51FA\u751F\u65E5\u671F");
		lblNewLabel.setBounds(24, 103, 72, 18);
		contentPane.add(lblNewLabel);
		//姓名文本框
		textField_name = new JTextField();
		textField_name.setBounds(109, 55, 287, 24);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		
		JLabel label_2 = new JLabel("\u624B\u673A");
		label_2.setBounds(24, 145, 44, 18);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u7535\u5B50\u90AE\u4EF6");
		label_3.setBounds(24, 189, 72, 18);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u5907\u6CE8");
		label_4.setBounds(24, 229, 49, 18);
		contentPane.add(label_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 296, 428, 249);
		contentPane.add(scrollPane);
		//显示列表
		scrollPane.setViewportView(list);
		//list = new JList<String>(v);
		//新建
		btnNewButton_xinjian = new JButton("\u65B0\u5EFA");
		btnNewButton_xinjian.setBounds(499, 294, 209, 27);
		contentPane.add(btnNewButton_xinjian);
		//修改
		btnNewButton_xiugai = new JButton("\u4FEE\u6539");
		btnNewButton_xiugai.setBounds(499, 343, 209, 27);
		contentPane.add(btnNewButton_xiugai);
		//保存
		btnNewButton_baocun = new JButton("\u4FDD\u5B58");
		btnNewButton_baocun.setBounds(499, 404, 209, 27);
		contentPane.add(btnNewButton_baocun);
		//删除
		btnNewButton_shanchu = new JButton("\u5220\u9664");
		btnNewButton_shanchu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_shanchu.setBounds(499, 464, 209, 27);
		contentPane.add(btnNewButton_shanchu);
		//退出
		btnNewButton_tuichu = new JButton("\u9000\u51FA");
		btnNewButton_tuichu.setBounds(499, 518, 209, 27);
		contentPane.add(btnNewButton_tuichu);
		//出生日期文本框
		textField_birth = new JTextField();
		textField_birth.setBounds(109, 100, 287, 21);
		contentPane.add(textField_birth);
		textField_birth.setColumns(10);
		//电话号码文本框
		textField_number = new JTextField();
		textField_number.setBounds(109, 142, 287, 24);
		contentPane.add(textField_number);
		textField_number.setColumns(10);
		//邮件文本框
		textField_mail = new JTextField();
		textField_mail.setBounds(109, 186, 287, 24);
		contentPane.add(textField_mail);
		textField_mail.setColumns(10);
		
		textField_other = new JTextField();
		textField_other.setBounds(109, 226, 287, 24);
		contentPane.add(textField_other);
		textField_other.setColumns(10);
		
		JLabel label_5 = new JLabel("\u59D3\u540D\u4E0D\u80FD\u4E3A\u7A7A\uFF0C\u4E0D\u80FD\u91CD\u590D\uFF0C\u5FC5\u987B\u552F\u4E00");
		label_5.setFont(new Font("微软雅黑", Font.BOLD, 15));
		label_5.setBounds(448, 58, 260, 18);
		contentPane.add(label_5);
		
		JLabel lblxxxxxxxx = new JLabel("\u65E5\u671F\u683C\u5F0F\uFF1Axxxx-xx-xx");
		lblxxxxxxxx.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblxxxxxxxx.setBounds(448, 103, 260, 18);
		contentPane.add(lblxxxxxxxx);
		
		JLabel lblxxxxxxxxxxx = new JLabel("\u90AE\u4EF6\u683C\u5F0F\uFF1Axxxx@xxxx.xxx");
		lblxxxxxxxxxxx.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblxxxxxxxxxxx.setBounds(448, 189, 260, 18);
		contentPane.add(lblxxxxxxxxxxx);
	}
	//鼠标监听
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource().equals(list)) {
			String mysql_name = (String) list.getSelectedValue();
			String sql = "SELECT * FROM adressbook WHERE name='" + mysql_name + "';";
			try {
				ResultSet rs = MySqlStatement.stmt.executeQuery(sql);
				rs.next();
				this.textField_name.setText(rs.getString(1));
				this.textField_birth.setText(rs.getString(2));
				this.textField_number.setText(rs.getString(3));
				this.textField_mail.setText(rs.getString(4));
				this.textField_other.setText(rs.getString(5));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(arg0.getSource().equals(btnNewButton_xinjian)) {
			mode = 1;
			setMode(mode);
		}
		if(arg0.getSource().equals(btnNewButton_baocun)) {
			if(mode == 1) {
				username = textField_name.getText();
				userbirth = textField_birth.getText();
				usernumber = textField_number.getText();
				usermail = textField_mail.getText();
				userother = textField_other.getText();
				boolean j = false;
				/*if(!verify()) {
					JOptionPane.showMessageDialog(this, "格式错误！请重新输入！");
				}else {*/
				if(username.equals("")||userbirth.equals("")||usernumber.equals("")
						||usermail.equals("")||userother.equals("")) {
					JOptionPane.showMessageDialog(this, "姓名不能为空！请输入！");
				}else {
				mysave();
				}
			}else {
				mychange();
			}
			mode = 0;
			setMode(mode);
		}
		if(arg0.getSource().equals(btnNewButton_xiugai)) {
			mode = 2;
			setMode(mode);
		}
		
		if(arg0.getSource().equals(btnNewButton_shanchu)) {
			mydelete();
		}
		
		if(arg0.getSource().equals(btnNewButton_tuichu)) {
			myexit();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
