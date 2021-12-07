package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cap cac phuong thuc gui request len server va nhan du lieu tra ve
 * Date: 07/12/2021
 * @author nguyenlm
 * @version 1.0
 */
public class API {

	/**
	 * Thuoc tinh nay giup format ngay thang nam theo dinh dang
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Thuoc tinh nay giup log ra thong tin o console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Phuong thuc goi cac API dang GET
	 * @param url duong dan toi server can request
	 * @param token doan ma bam cung cap de xac dinh thong tin nguoi dung
	 * @return response cua server (o dang String)
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		// Setup
		HttpURLConnection conn = getHttpURLConnection(url, "GET", token);
		// Doc du lieu cua server tra ve
		return readResponse(conn);
	}

	/**
	 * Phuong thuc chuyen response cua server thanh dang String
	 * @param conn connection to server
	 * @return response in string type
	 * @throws IOException
	 */
	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder(); // using StringBuilder for the sake of memory and performance
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		response.append(null + "\n");
		in.close();
		LOGGER.info("Respone Info: " + response.substring(0, response.length() - 1).toString());
		return response.substring(0, response.length() - 1).toString();
	}

	/**
	 * Phuong thuoc ket noi voi server
	 * @param url dia chi URl cua server
	 * @param method phuong thuc muon ket noi
	 * @param token ma bam cua nguoi dung
	 * @return HttpURLConnection toi server
	 * @throws IOException
	 */
	private static HttpURLConnection getHttpURLConnection(String url, String method, String token) throws IOException {
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}

	/**
	 * Phuong thuc goi cac API dang POST
	 * @param url duong dan toi server can request
	 * @param data du lieu dua len server de xu ly (dang JSON)
	 * @return response cua server (dang String)
	 * @throws IOException
	 */
	public static String post(String url, String data, String token) throws IOException {
		allowMethods("PATCH");
		// setup
		HttpURLConnection conn = getHttpURLConnection(url, "PATCH", token);

		// gui du lieu
		String payload = data;
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(payload);
		writer.close();

		// doc du lieu tu server tra ve
		return readResponse(conn);
	}

	/**
	 * Phuong thuc cho phep goi cac loai giao thuc API khac nhau nhu PATCH, PUT, ... (chi hoat dong voi Java 11)
	 * @deprecated Chi hoat dong voi Java 11+
	 * @param methods giao thuc can cho phep ho tro
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
