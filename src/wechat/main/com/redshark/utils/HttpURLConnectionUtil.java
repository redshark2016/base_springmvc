package com.redshark.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

/**
 * @Description:HttpURLConnectionUtil
 * @author ximing.fu
 * @time:2015年11月13日 上午10:58:57
 *
 */
public class HttpURLConnectionUtil {

	private String action;
	private String content;
	private String filePath;

	public HttpURLConnectionUtil() {
	}

	public HttpURLConnectionUtil(String action) {
		this.action = action;
	}

	public HttpURLConnectionUtil(String action, String content) {
		this.action = action;
		this.content = content;
	}

	public HttpURLConnectionUtil(String action, String content, String filePath) {
		this.action = action;
		this.content = content;
		this.filePath = filePath;
	}

	public String execute() throws Exception {
		URL url = new URL(action);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();

		http.setRequestMethod("POST");
		http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		http.setDoOutput(true);
		http.setDoInput(true);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

		http.connect();
		OutputStream os = http.getOutputStream();
		if (!StringUtil.isBlank(content)) {
			os.write(content.getBytes("UTF-8"));// 传入参数
		}
		os.flush();
		os.close();

		InputStream is = http.getInputStream();
		int size = is.available();
		byte[] jsonBytes = new byte[size];
		is.read(jsonBytes);
		String message = new String(jsonBytes, "UTF-8");
		System.out.println("【==============HttpURLConnectionUtil execute result开始==============】");
		System.out.println("【请求时间：】"+DateUtil.getDateString());
		System.out.println("【请求url：】"+action);
		System.out.println("【请求参数：】"+content);
		System.out.println("【反馈结果：】"+message);
		System.out.println("【==============HttpURLConnectionUtil execute result结束==============】");

		is.close();
		http.disconnect();
		return message;

	}

	public String executeForFile() throws Exception {
		
		System.out.println("【HttpURLConnectionUtil executeForFile from === 】"+action);

		String result = null;

		System.out.println(filePath);
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			//throw new IOException("文件不存在");
			return "";
		}

		/**
		 * 第一部分
		 */
		URL urlObj = new URL(action);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		/**
		 * 设置关键值
		 */
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		// 请求正文信息

		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}

		}

		System.out.println(result);
		JSONObject jsonObj = JSONObject.fromObject(result);
		String re_url = jsonObj.getString("media_id");
		System.out.println("【==============HttpURLConnectionUtil executeForFile result开始==============】");
		System.out.println("【请求时间：】"+DateUtil.getDateString());
		System.out.println("【请求url：】"+action);
		System.out.println("【请求参数：】"+filePath);
		System.out.println("【请求结果：】"+result);
		System.out.println("【反馈media_id：】"+re_url);
		System.out.println("【==============HttpURLConnectionUtil executeForFile result结束==============】");
		return re_url;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
