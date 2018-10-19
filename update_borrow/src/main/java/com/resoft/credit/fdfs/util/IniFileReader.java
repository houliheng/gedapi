package com.resoft.credit.fdfs.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;

public class IniFileReader {
	private Hashtable<String, Object> paramTable;
	private String conf_filename;

	/**
	 * @param conf_filename
	 *            config filename
	 */
	public IniFileReader(String conf_filename) throws FileNotFoundException,
			IOException {
		this.conf_filename = conf_filename;
		loadFromFile(conf_filename);
	}

	/**
	 * get the config filename
	 * 
	 * @return config filename
	 */
	public String getConfFilename() {
		return this.conf_filename;
	}

	/**
	 * get string value from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @return string value
	 */
	public String getStrValue(String name) {
		Object obj;
		obj = this.paramTable.get(name);
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		return (String) ((ArrayList<?>) obj).get(0);
	}

	/**
	 * get int value from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @param default_value
	 *            the default value
	 * @return int value
	 */
	public int getIntValue(String name, int default_value) {
		String szValue = this.getStrValue(name);
		if (szValue == null) {
			return default_value;
		}

		return Integer.parseInt(szValue);
	}

	/**
	 * get boolean value from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @param default_value
	 *            the default value
	 * @return boolean value
	 */
	public boolean getBoolValue(String name, boolean default_value) {
		String szValue = this.getStrValue(name);
		if (szValue == null) {
			return default_value;
		}

		return szValue.equalsIgnoreCase("yes")
				|| szValue.equalsIgnoreCase("on")
				|| szValue.equalsIgnoreCase("true") || szValue.equals("1");
	}

	/**
	 * get all values from config file
	 * 
	 * @param name
	 *            item name in config file
	 * @return string values (array)
	 */
	public String[] getValues(String name) {
		Object obj;
		String[] values;

		obj = this.paramTable.get(name);
		if (obj == null) {
			return null;
		}

		if (obj instanceof String) {
			values = new String[1];
			values[0] = (String) obj;
			return values;
		}

		Object[] objs = ((ArrayList<?>) obj).toArray();
		values = new String[objs.length];
		System.arraycopy(objs, 0, values, 0, objs.length);
		return values;
	}

	@SuppressWarnings("unchecked")
	private void loadFromFile(String conf_filename)
			throws FileNotFoundException, IOException {
		BufferedReader buffReader;
		String line;
		String[] parts;
		String name;
		String value;
		Object obj;
		ArrayList<Object> valueList;

		buffReader =
			new BufferedReader(
				new InputStreamReader(
					this.getClass().getResourceAsStream(conf_filename)));

		this.paramTable = new Hashtable<String, Object>();

		try {
			while ((line = buffReader.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0 || line.charAt(0) == '#') {
					continue;
				}

				parts = line.split("=", 2);
				if (parts.length != 2) {
					continue;
				}

				name = parts[0].trim();
				value = parts[1].trim();

				obj = this.paramTable.get(name);
				if (obj == null) {
					this.paramTable.put(name, value);
				} else if (obj instanceof String) {
					valueList = new ArrayList<Object>();
					valueList.add(obj);
					valueList.add(value);
					this.paramTable.put(name, valueList);
				} else {
					if (obj instanceof ArrayList) {
						valueList = (ArrayList<Object>) obj;
						valueList.add(value);
					}
				}
			}
		} finally {
			buffReader.close();
		}
	}

	public static void init(String conf_filename) throws IOException {
		IniFileReader iniReader;
		String[] szTrackerServers;
		String[] parts;

		iniReader = new IniFileReader(conf_filename);

		ClientGlobal.g_connect_timeout = iniReader.getIntValue(
				"connect_timeout", ClientGlobal.DEFAULT_CONNECT_TIMEOUT);
		if (ClientGlobal.g_connect_timeout < 0) {
			ClientGlobal.g_connect_timeout = ClientGlobal.DEFAULT_CONNECT_TIMEOUT;
		}
		ClientGlobal.g_connect_timeout *= 1000; // millisecond

		ClientGlobal.g_network_timeout = iniReader.getIntValue(
				"network_timeout", ClientGlobal.DEFAULT_NETWORK_TIMEOUT);
		if (ClientGlobal.g_network_timeout < 0) {
			ClientGlobal.g_network_timeout = ClientGlobal.DEFAULT_NETWORK_TIMEOUT;
		}
		ClientGlobal.g_network_timeout *= 1000; // millisecond

		ClientGlobal.g_charset = iniReader.getStrValue("charset");
		if (ClientGlobal.g_charset == null
				|| ClientGlobal.g_charset.length() == 0) {
			ClientGlobal.g_charset = "ISO8859-1";
		}

		szTrackerServers = iniReader.getValues("tracker_server");
		if (szTrackerServers == null) {
			throw new IllegalArgumentException("item \"tracker_server\" in " + conf_filename
					+ " not found");
		}

		InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
		for (int i = 0; i < szTrackerServers.length; i++) {
			parts = szTrackerServers[i].split("\\:", 2);
			if (parts.length != 2) {
				throw new IllegalArgumentException(
						"the value of item \"tracker_server\" is invalid, the correct format is host:port");
			}

			tracker_servers[i] = new InetSocketAddress(parts[0].trim(),
					Integer.parseInt(parts[1].trim()));
		}
		ClientGlobal.g_tracker_group = new TrackerGroup(tracker_servers);

		ClientGlobal.g_tracker_http_port = iniReader.getIntValue(
				"http.tracker_http_port", 80);
		ClientGlobal.g_anti_steal_token = iniReader.getBoolValue(
				"http.anti_steal_token", false);
		if (ClientGlobal.g_anti_steal_token) {
			ClientGlobal.g_secret_key = iniReader
					.getStrValue("http.secret_key");
		}
	}
}
