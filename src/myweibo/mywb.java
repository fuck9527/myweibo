package myweibo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONArray;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;
import com.renren.api.client.services.FeedService;
import com.renren.api.client.services.StatusService;
import com.renren.api.client.services.UserService;
import com.renren.api.client.utils.HttpURLUtils;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.BareBonesBrowserLaunch;

public class mywb extends JFrame{
	public JTextArea text = new JTextArea();
	public JButton submit = new JButton("submit");
	public JTextArea content = new JTextArea();
	public JButton refresh = new JButton("refresh");

	public JTextArea c1 = new JTextArea();
	public JButton confirm = new JButton("confirm");
	public Map<String, String> auth = new HashMap();
	public JButton sina = new JButton("add sina");
	
	public JTextArea c2 = new JTextArea();
	public JButton renren = new JButton("add RenRen");
	
	public Weibo weibo = new Weibo();
	public Oauth oauth = new Oauth();
	
	public RenrenApiClient client = RenrenApiClient.getInstance();
	
	public FileInputStream fis;
	
    public String sinaat = null;
    public String renrenat = null;
	
	public mywb() throws IOException {
		setTitle("o!weibo");
		setResizable(false);
		setVisible(true);
		setBounds(100, 50, 705, 565);
		setLayout(null);
		
		JLabel l1 = new JLabel("type your status here :)");
		l1.setBounds(20, 10, 300, 30);
		
		text.setLineWrap(true);
		content.setLineWrap(true);
		
		JScrollPane js = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setBounds(20, 40, 200, 70);
		
		submit.setBounds(240, 60, 80, 30);
		
		JLabel l2 = new JLabel("get new information here :P");
		l2.setBounds(110, 135, 200, 30);
		
		JScrollPane js1 = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js1.setBounds(20, 170, 660, 350);
		
		refresh.setBounds(20, 130, 80, 30);
		
		add(l1);
		add(l2);
		add(js);
		add(submit);
		add(js1);
		add(refresh);
		
		content.setFocusable(false);
		
		
		JLabel l3 = new JLabel("type your code to get authority!");
		
		l3.setBounds(350, 10, 200, 20);
		add(l3);
		sina.setBounds(350, 40, 90, 20);
		add(sina);
		c1.setBounds(450, 40, 220, 20);
		add(c1);
		
		renren.setBounds(350, 70, 90, 20);
		add(renren);
		c2.setBounds(450, 70, 220, 20);
		add(c2);
		
		confirm.setBounds(450, 130, 90, 30);
		add(confirm);
		
		FileInputStream fis = new FileInputStream("account.txt");
		byte[] data = new byte[1024]; //数据存储的数组
        int i = 0; //当前下标
        //读取流中的第一个字节数据
        int n = fis.read();
        //依次读取后续的数据
        while(n != -1){ //未到达流的末尾
        	//将有效数据存储到数组中
        	data[i] = (byte)n;
        	//下标增加
        	i++;
        	//读取下一个字节的数据
        	n = fis.read();
        }
        fis.close();
       
        //解析数据
        String s = new String(data,0,i);
        System.out.print(s);
        try {
        	sinaat = ((s.split("\n"))[0].split(","))[1];
        	weibo.setToken(sinaat);
        	c1.setText("added!");
        } catch (Exception e) {}
        try {
        	renrenat = ((s.split("\n"))[1].split(","))[1];  
        	c2.setText("added!");
        } catch (Exception e) {}
        
        
		sina.addMouseListener(new listener1());
		renren.addMouseListener(new listener1_2());
		confirm.addMouseListener(new listener2());
		refresh.addMouseListener(new listener3());
		submit.addMouseListener(new listener4());
		
	}
	
	public class listener1 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			try {
				BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
			} catch (WeiboException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				content.append("something wrong ! \n\n");
			}
			
		}
	}
	
	public class listener1_2 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			try {
				String url = "https://graph.renren.com/oauth/authorize?client_id="+RenrenApiConfig.renrenApiKey+"&redirect_uri=http://graph.renren.com/oauth/login_success.html&response_type=code&scope=read_user_album+read_user_feed+status_update+read_user_status";
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				content.append("something wrong ! \n\n");
			}
			
		}
	}
	
	public class listener2 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
			weibo4j.http.AccessToken access_token;
			if (!c1.getText().equals("added!"))
			{
				try {
					weibo.setToken(null);
//					auth.remove("sina");
					access_token = oauth.getAccessTokenByCode(c1.getText());
					String at = access_token.getAccessToken();
					System.out.print(at);
					sinaat = at;
//					auth.put("sina", at);
					content.append("sina added successfully!\n\n");
					weibo.setToken(at);
					c1.setText("added!");
				} catch (WeiboException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					content.append("sina: something wrong ! \n\n");
				}
			} else {
				content.append("sina: already added! \n\n");
			}
			
			if (!c2.getText().equals("added!"))
			{
				try {
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("client_id",RenrenApiConfig.renrenApiKey);
					parameters.put("client_secret",RenrenApiConfig.renrenApiSecret);
					parameters.put("redirect_uri","http://graph.renren.com/oauth/login_success.html");
					parameters.put("grant_type","authorization_code");
					parameters.put("code",c2.getText());
	
					String con = HttpURLUtils.doPost("https://graph.renren.com/oauth/token",parameters);
					System.out.print(con);
					String[] t1 = con.split("access_token\":\"");
					String[] t2 = t1[1].split("\"");
					String[] t3 = con.split(",\"name\":\"");
					System.out.println(t3[0]);
					String[] t4 = t3[0].split("id\":");
					String id = t4[1];
					String[] t5 = t3[1].split("\",\"avatar");
					String name = t5[0];
					com.renren.api.client.param.impl.AccessToken access_token2 = new com.renren.api.client.param.impl.AccessToken( t2[0] );
					renrenat = access_token2.getValue();
					c2.setText("added!");
					
					content.append("renren added successfully !\n\n");
				} catch(Exception e1) {
					content.append("renren: something !\n\n");
				}
			} else {
				content.append("renren: already added !\n\n");
			}
			
			byte[] b = ("sina," + sinaat + "\n" + "renren," + renrenat).getBytes();
			
			FileOutputStream fis;
			try {
				fis = new FileOutputStream("account.txt");
				fis.write(b);
				fis.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
	}
	
	public class listener3 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Timeline tm = new Timeline();
			try {
				StatusWapper status = tm.getFriendsTimeline();
				List<Status> statuslist= status.getStatuses();
//				content.setText("");
				for(int j = statuslist.size()-1; j >= 0; j--)
				{
					content.append("【sina】【" + statuslist.get(j).getUser().getName().toString() + "】 : " + statuslist.get(j).getText() + "\n\n");
				}
			} catch (Exception e2) {
				content.append("something wrong ! \n\n");
			}
			FeedService fs = client.getFeedService();
			UserService us = client.getUserService();
			JSONArray ja = fs.getFeed("10", 0, 1, 20, new com.renren.api.client.param.impl.AccessToken(renrenat));
			for (int j = ja.size()-1; j>=0; j--)
			{
				String status = ja.get(j).toString();
				System.out.print(status);
				String id = status.split("\"actor_id\":")[1].split(",")[0];
				String user = us.getInfo(id, "name", new com.renren.api.client.param.impl.AccessToken(renrenat)).toString();
				String[] userarray = user.split("\"");
				String name = userarray[userarray.length-2];
				String prefix = status.split("\",\"title\":\"")[1].split("\",\"")[0];
				content.append("【renren】【" + name + "】: " + prefix + "\n\n");
			}
		}

	}
	
	public class listener4 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Timeline tm = new Timeline();
			try {
				tm.UpdateStatus(text.getText());
				content.append("sina submit successfully ! \n\n");
			} catch (Exception e2) {
				content.append("sina something wrong ! \n\n");
			}
			try {
				StatusService ss = client.getStatusService();
				ss.setStatus(text.getText(), new com.renren.api.client.param.impl.AccessToken(renrenat));
				content.append("renren submit successfully ! \n\n");
			} catch (Exception e2) {
				content.append("renren something wrong ! \n\n");
			}
		}
	}
	
	public static void main(String [] args) throws IOException {
		mywb x = new mywb();
//		Oauth oauth = new Oauth();
//		BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
//		System.out.println(oauth.getAccessTokenByCode(code));

	}

}
