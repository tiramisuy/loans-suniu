package com.rongdu.loans.linkface;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.rongdu.common.utils.FileUtils;

public class LinkfaceDemo {

	private static final String api_id = "36a195fb34f54ddf84c9b4081d03a2be";
	private static final String api_secret = "a9e236b6c555488fac373f3ddf9a7126";

	public static final String filepath = "D:/tmp/test4.jpg";// 图片路径
	public static final String selfie_idnumber_verification_url = "https://cloudapi.linkface.cn/identity/selfie_idnumber_verification";//人脸识别接口
//	public static final String username = "栾健";// 姓名  test2.jpg
//	public static final String id_number = "320829198305230016";// 身份证号
	
	public static final String username = "蒋丹丹";// 姓名  test4.jpg
	public static final String id_number = "342626199206100187";// 身份证号

	public static void selfie_idnumber_verification() throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(selfie_idnumber_verification_url);
		StringBody id = new StringBody(api_id);
		StringBody secret = new StringBody(api_secret);
		StringBody name = new StringBody(username, Charset.forName("UTF-8"));
		StringBody number = new StringBody(id_number);
		FileBody fileBody = new FileBody(new File(filepath));
		MultipartEntity entity = new MultipartEntity();

		entity.addPart("api_id", id);
		entity.addPart("api_secret", secret);
		entity.addPart("name", name);
		entity.addPart("id_number", number);
		entity.addPart("selfie_file", fileBody);
		System.out.println(username + name);
		post.setEntity(entity);

		HttpResponse response = httpclient.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entitys = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entitys.getContent()));
			String line = reader.readLine();
			System.out.println(line);
		} else {
			HttpEntity r_entity = response.getEntity();
			String responseString = EntityUtils.toString(r_entity);
			System.out.println("错误码是：" + response.getStatusLine().getStatusCode() + "  "
					+ response.getStatusLine().getReasonPhrase());
			System.out.println("出错原因是：" + responseString);
			// 你需要根据出错的原因判断错误信息，并修改
		}

		httpclient.getConnectionManager().shutdown();
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		selfie_idnumber_verification();
		//FileUtils.downLoadFromUrl("http://192.168.1.222/img/face_verify/2018/06/13/85ded330611947088be34e95562cb73f.jpg");
	}
}
