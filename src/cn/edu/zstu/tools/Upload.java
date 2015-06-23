package cn.edu.zstu.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Upload {

	private final String URL_PATH;
	private ArrayList<String> params;
	private String avator;
	private final String CHARSET = "UTF-8";
	private String userId;
	private Map<String, String> message = new HashMap<String, String>();

	public Upload(ArrayList<String> params, String url_path, String userId) {
		// TODO Auto-generated constructor stub
		this.params = params;
		this.URL_PATH = url_path + "/servlet/UploadAction";
		this.userId = userId;
	}

	public Upload(String params, String url_path, String userId) {
		this.avator = params;
		this.URL_PATH = url_path + "/servlet/ChangeAvator";
		this.userId = userId;
	}

	public String changeAvator() {
		System.out.println("开始连接服务器...准备上传头像");
		String result = "";
		String BOUNDARY = UUID.randomUUID().toString();
		String PREFIX = "--";
		String LINEEND = "\r\n";
		String CONTENTTYPE = "multipart/form-data";
		try {
			URL url = new URL(URL_PATH);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10 * 10000000);
			connection.setReadTimeout(10 * 10000000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", CHARSET);
			connection.setRequestProperty("connection", "keep-alive");
			connection.setRequestProperty("Content-Type", CONTENTTYPE + ";boundary=" + BOUNDARY);

			/**
			 * 注意格式
			 */
			StringBuffer sb = new StringBuffer();

			sb.append(PREFIX + BOUNDARY + LINEEND);
			sb.append("Content-Disposition: form-data;name=\"userId" + "\"" + LINEEND + LINEEND);
			sb.append(userId);
			sb.append(LINEEND);

			/**
			 * 得到输出流 写入数据
			 */
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.write(sb.toString().getBytes());

			File file = new File(avator);
			StringBuffer buffer = new StringBuffer();
			buffer.append(PREFIX + BOUNDARY + LINEEND);
			buffer.append("Content-Disposition: form-data; name=\"img\"; filename=\""
					+ file.getName() + "\"" + LINEEND);
			// application/octet-stream 表示文件类型为二进制文件
			buffer.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEEND
					+ LINEEND);
			outputStream.write(buffer.toString().getBytes());
			InputStream inputStream = new FileInputStream(file);
			byte[] data = new byte[10 * 1024];
			int len = 0;
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			inputStream.close();
			outputStream.write(LINEEND.getBytes());
			outputStream.write((PREFIX + BOUNDARY + PREFIX + LINEEND).getBytes());
			outputStream.flush();
			System.out.println("头像上传完毕  准备取得返回值...");
			/**
			 * 处理服务器的返回值
			 */
			int code = connection.getResponseCode();
			System.out.println("--code--" + code);
			if (code == 200) {
				System.out.println("收取结束  得到返回值");
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				// System.out.println("--result--"+reader.readLine());
				result = reader.readLine();
			}

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
		return result;
	}

	public String uploadTask() {
		System.out.println("开始连接服务器...");
		String result = "";
		String BOUNDARY = UUID.randomUUID().toString();
		String PREFIX = "--";
		String LINEEND = "\r\n";
		String CONTENTTYPE = "multipart/form-data";
		try {
			URL url = new URL(URL_PATH);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10 * 10000000);
			connection.setReadTimeout(10 * 10000000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", CHARSET);
			connection.setRequestProperty("connection", "keep-alive");
			connection.setRequestProperty("Content-Type", CONTENTTYPE + ";boundary=" + BOUNDARY);
			message.put("message", params.get(0));
			message.put("Category", params.get(1));
			params.remove(0);
			params.remove(0);
			/**
			 * 注意格式
			 */
			StringBuffer sb = new StringBuffer();

			sb.append(PREFIX + BOUNDARY + LINEEND);
			sb.append("Content-Disposition: form-data;name=\"userId" + "\"" + LINEEND + LINEEND);
			sb.append(userId);
			sb.append(LINEEND);

			sb.append(PREFIX + BOUNDARY + LINEEND);
			sb.append("Content-Disposition: form-data;name=\"message" + "\"" + LINEEND + LINEEND);
			sb.append(message.get("message"));
			sb.append(LINEEND);

			sb.append(PREFIX + BOUNDARY + LINEEND);
			sb.append("Content-Disposition: form-data;name=\"message" + "\"" + LINEEND + LINEEND);
			sb.append(message.get("Category"));
			sb.append(LINEEND);

			/**
			 * 得到输出流 写入数据
			 */
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.write(sb.toString().getBytes());

			for (int i = 0; i < params.size(); i++) {
				File file = new File(params.get(i));
				StringBuffer buffer = new StringBuffer();
				buffer.append(PREFIX + BOUNDARY + LINEEND);
				buffer.append("Content-Disposition: form-data; name=\"img\"; filename=\""
						+ file.getName() + "\"" + LINEEND);
				// application/octet-stream 表示文件类型为二进制文件
				buffer.append("Content-Type: application/octet-stream; charset=" + CHARSET
						+ LINEEND + LINEEND);
				outputStream.write(buffer.toString().getBytes());
				InputStream inputStream = new FileInputStream(file);
				byte[] data = new byte[10 * 1024];
				int len = 0;
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				inputStream.close();
				outputStream.write(LINEEND.getBytes());
			}
			outputStream.write((PREFIX + BOUNDARY + PREFIX + LINEEND).getBytes());
			outputStream.flush();

			System.out.println("数据读入完毕  准备取得返回值...");
			/**
			 * 处理服务器的返回值
			 */
			int code = connection.getResponseCode();
			System.out.println("--code--" + code);
			if (code == 200) {
				System.out.println("收取结束  得到返回值");
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				// System.out.println("--result--"+reader.readLine());
				result = reader.readLine();
			}

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
		return result;
	}

}
