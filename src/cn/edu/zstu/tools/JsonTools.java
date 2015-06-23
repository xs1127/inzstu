package cn.edu.zstu.tools;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

public class JsonTools {

	public JsonTools() {
		// TODO Auto-generated constructor stub

	}

	public static JSONObject getJsonObject(String first, String second, String type) {
		JSONObject object = new JSONObject();
		try {
			if (type.equals("detail")) {
				object.put("type", type);
				object.put("userid", first);
				object.put("postid", second);
			} else if (type.equals("login")) {
				object.put("type", type);
				object.put("username", first);
				object.put("passwd", second);

			} else if (type.equals("buy")) {
				object.put("type", type);
				object.put("userid", first);
				object.put("postid", second);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public static JSONObject getJsonObject(String first, String second, String third, String type) {
		JSONObject object = new JSONObject();
		try {
			if (type.equals("buy")) {
				object.put("type", type);
				object.put("userid", first);
				object.put("postid", second);
				object.put("customerid", third);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public static JSONObject getJsonObject(String type, int currentIndex) {
		JSONObject object = new JSONObject();
		try {
			object.put("type", type);
			object.put("currentIndex", currentIndex);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	public static JSONObject getJsonObject(String type, String category) {
		JSONObject object = new JSONObject();
		try {
			object.put("type", type);
			object.put("currentIndex", 0);
			object.put("category", category);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	public static JSONObject getJsonObject(String type, int postid, String message) {
		JSONObject object = new JSONObject();
		try {
			object.put("type", type);
			object.put("postid", postid);
			object.put("newmessage", message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object;
	}

}
