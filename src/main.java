import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


import com.renren.api.client.*;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.services.StatusService;
import com.renren.api.client.utils.HttpURLUtils;

public class main {
	public static void main( String[] args) throws IOException{
		
		RenrenApiClient client = RenrenApiClient.getInstance( );
		System.out.print("ok");
		String url = "https://graph.renren.com/oauth/authorize?client_id="+RenrenApiConfig.renrenApiKey+"&redirect_uri=http://graph.renren.com/oauth/login_success.html&response_type=code&scope=read_user_album+read_user_feed+status_update+read_user_status";
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
		String code = strin.readLine();
		
//		URL url = new URL( "https://graph.renren.com/oauth/authorize?client_id="+RenrenApiConfig.renrenApiKey+"&redirect_uri=http://graph.renren.com/oauth/login_success.html&response_type=code&scope=read_user_album+read_user_feed+status_update");
//		URLConnection conn = url.openConnection();
//		conn.setDoOutput(true);
//		BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream(), "utf-8" ));
//		String result = null, tmp = null;
//		while( ( tmp = reader.readLine() ) != null )
//			result += tmp + "\n";
//		System.out.print(result);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client_id",RenrenApiConfig.renrenApiKey);
		parameters.put("client_secret",RenrenApiConfig.renrenApiSecret);
		//��ȡcodeʱ�õ�redirect_uri�������õ�һ��
		 parameters.put("redirect_uri","http://graph.renren.com/oauth/login_success.html");
		parameters.put("grant_type","authorization_code");
		parameters.put("code",code);

		String content = HttpURLUtils.doPost("https://graph.renren.com/oauth/token",parameters);
		String[] t1 = content.split("access_token\":\"");
		String[] t2 = t1[1].split("\"");
		String[] t3 = content.split(",\"name\":\"");
		System.out.println(t3[0]);
		String[] t4 = t3[0].split("id\":");
		String id = t4[1];
		String[] t5 = t3[1].split("\",\"avatar");
		String name = t5[0];
		System.out.print(content);
		AccessToken access_token = new AccessToken( t2[0] );
		StatusService ss = client.getStatusService();
		//ss.setStatus("test", access_token);
		System.out.print(ss.getStatuses(Integer.parseInt(id), 1, 20, access_token).get(0));
	}
}
