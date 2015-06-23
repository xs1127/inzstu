package cn.edu.zstu.DB;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionService {

	private String requeString;
	private StringBuffer responseString = new StringBuffer();
	private String url_path;

	public ConnectionService(String requestString, String url_path) {
		// TODO Auto-generated constructor stub
		this.requeString = requestString;
		this.url_path = url_path;
	}

	public String connect() {
		try {
			URL url = new URL(url_path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// connection.setConnectTimeout(3*1000);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Charset", "UTF-8");
			/**
			 * connection connected:false->true
			 */
			OutputStream os = connection.getOutputStream();
			DataOutputStream outputStream = new DataOutputStream(os);
			// os.write(requeString.getBytes("UTF-8"));
			connection.connect();
			outputStream.write(requeString.getBytes("UTF-8"));
			outputStream.flush();
			os.close();
			outputStream.close();
			int code = connection.getResponseCode();

			if (code == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), "UTF-8"));
				String temp;
				while ((temp = br.readLine()) != null) {
					responseString.append(temp);
				}
			} else {
				System.out.println("code:" + code);
				responseString.append("");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("responseCode", 400);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			responseString.append(String.valueOf(jsonObject));
			e.printStackTrace();
		}
		return responseString.toString();
	}

}
