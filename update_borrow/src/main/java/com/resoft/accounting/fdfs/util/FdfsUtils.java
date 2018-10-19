package com.resoft.accounting.fdfs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.csource.common.MyException;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.resoft.accounting.fdfs.Constant;

public class FdfsUtils extends Constant {

	private static boolean initialized = false;

	private static TrackerClient trackerClient;

	private static List<TrackerServer> serverPool = Collections.synchronizedList(new ArrayList<TrackerServer>());

	public static synchronized void init() throws IOException {
		if (!initialized) {
			IniFileReader.init(fdfsConfFile);
			trackerClient = new TrackerClient();
			initialized = true;
		}
	}

	public static String upload(File file) throws IOException {
		init();
		FileInputStream is = null;
		TrackerServer server = null;
		try {
			is = new FileInputStream(file);
			byte[] content = new byte[is.available()];
			is.read(content);
			server = getServer();
			String[] result;
			try {
				result = new StorageClient(server, null).upload_file(content, FileUtils.getExtNameFromFileName(file.getName()), null);
				return result[0] + '/' + result[1];
			} catch (Exception e) {
				return null;
			}
		} finally {
			FileUtils.closeIOSource(is);
			closeServer(server);
		}
	}

	public static byte[] download(String storagePath) throws IOException {
		init();
		TrackerServer server = null;
		try {
			server = getServer();
			String[] splitPath = splitStoragePath(storagePath);
			try {
				return new StorageClient(server, null).download_file(splitPath[0], splitPath[1]);
			} catch (MyException e) {
				return new byte[0];
			}
		} finally {
			closeServer(server);
		}
	}

	public static boolean download(String storagePath, String targetLocalPath) throws IOException {
		init();
		TrackerServer server = null;
		try {
			server = getServer();
			String[] splitPath = splitStoragePath(storagePath);
			try {
				return 0 == new StorageClient(server, null).download_file(splitPath[0], splitPath[1], targetLocalPath);
			} catch (MyException e) {
				return false;
			}
		} finally {
			closeServer(server);
		}
	}

	private static TrackerServer getServer() throws IOException {
		if (serverPool.isEmpty()) {
			return trackerClient.getConnection();
		} else {
			TrackerServer server = serverPool.remove(serverPool.size() - 1);
			if (server.getSocket() == null || !ProtoCommon.activeTest(server.getSocket())) {
				server.close();
				return trackerClient.getConnection();
			} else {
				return server;
			}
		}
	}

	private static void closeServer(TrackerServer server) throws IOException {
		if (serverPool.size() < fdfsConnectionPoolSize) {
			serverPool.add(server);
		} else {
			FileUtils.closeIOSource(server);
		}
	}

	private static String[] splitStoragePath(String storagePath) {
		int index = storagePath.indexOf("/");
		if (index == 0) {
			return null;
		} else {
			return new String[] { storagePath.substring(0, index), storagePath.substring(index + 1) };
		}
	}

	public static void main(String[] args) throws Exception {
		// String s = upload(new File("C:/login_logo_s.png"));
		// System.out.println(s);
		// byte[] b =
		// download("group1/M00/00/00/wKgI7FbmdfeAK1OhAAHwFT--0MM486.jar");
		// System.out.println(b.length);
		// boolean b =
		// download("group1/M00/00/00/wKgI7FbmfJCAM0rPAAAM4ilYt1k277.png",
		// "c:/nnnn.png");
		// System.out.println(b);
		// String s =upload(new
		// File("C:/WorkSpace/MyEclipse/Workspaces/jee_cre_inprogress/webapp/WEB-INF/views/test.pdf"));
		// System.out.println(s);
		boolean b = download("group1/M00/00/03/wKgI7Fc0O6mAESSmAACR1-WbwGg968.jpg", "c:/asd.jpg");
		System.out.println(b);
	}

}
