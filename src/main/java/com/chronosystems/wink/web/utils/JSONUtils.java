package com.chronosystems.wink.web.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.chronosystems.wink.web.SocketServer;

public class JSONUtils {

	// flags to identify the kind of json response on client side
	private static final String FLAG_SELF = "self", FLAG_NEW = "new", FLAG_MESSAGE = "message", FLAG_EXIT = "exit";

	private final static SimpleDateFormat ft = new SimpleDateFormat("HH:mm");

	public JSONUtils() {
	}

	/**
	 * Json when client needs it's own session details
	 * */
	public String getClientDetailsJson(String sessionId, String message) {
		String json = null;

		try {
			JSONObject jObj = new JSONObject();
			jObj.put("flag", FLAG_SELF);
			jObj.put("sessionId", sessionId);
			jObj.put("message", message);
			jObj.put("time", getCurrentTime());

			json = jObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * Json to notify all the clients about new person joined
	 * */
	public String getNewClientJson(String sessionId, Map<String, String> params, String message, Set<Session> sessions) {
		String json = null;

		try {
			JSONObject jObj = new JSONObject();
			jObj.put("flag", FLAG_NEW);
			jObj.put("name", params.get(SocketServer.NAME));
			jObj.put("color", params.get(SocketServer.COLOR));
			jObj.put("sessionId", sessionId);
			jObj.put("message", message);
			jObj.put("time", getCurrentTime());

			jObj.put("users", getOnlineUser().toArray());
			jObj.put("onlineCount", sessions.size());

			json = jObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	private String getCurrentTime() {
		return ft.format(new Date()).replace(':', 'h');
	}
	
	private List<Map<String, String>> getOnlineUser() {
		final List<Map<String, String>> resultList = new ArrayList<Map<String,String>>(SocketServer.sessionParams.values());
		Collections.reverse(resultList);
		return resultList;
	}

	/**
	 * Json when the client exits the socket connection
	 * */
	public String getClientExitJson(String sessionId, Map<String, String> params, String message, Set<Session> sessions) {
		String json = null;

		try {
			JSONObject jObj = new JSONObject();
			jObj.put("flag", FLAG_EXIT);
			jObj.put("name", params.get(SocketServer.NAME));
			jObj.put("color", params.get(SocketServer.COLOR));
			jObj.put("sessionId", sessionId);
			jObj.put("message", message);
			jObj.put("time", getCurrentTime());

			jObj.put("users", getOnlineUser().toArray());
			jObj.put("onlineCount", sessions.size());

			json = jObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * JSON when message needs to be sent to all the clients
	 * */
	public String getSendAllMessageJson(String sessionId, Map<String, String> params, String message) {
		String json = null;

		try {
			JSONObject jObj = new JSONObject();
			jObj.put("flag", FLAG_MESSAGE);
			jObj.put("sessionId", sessionId);
			jObj.put("name", params.get(SocketServer.NAME));
			jObj.put("color", params.get(SocketServer.COLOR));
			jObj.put("message", message);
			jObj.put("time", getCurrentTime());

			json = jObj.toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
}
