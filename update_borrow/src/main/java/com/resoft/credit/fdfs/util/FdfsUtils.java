package com.resoft.credit.fdfs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.csource.common.MyException;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.resoft.credit.fdfs.Constant;

public class FdfsUtils extends Constant {

	private static final Logger logger = LoggerFactory.getLogger(FdfsUtils.class);
	
	private static boolean initialized = false;
	private static boolean initializedPDF = false;
	//private static boolean initializedContractPDF = false;

	private static TrackerClient trackerClient;
	
	private static TrackerClient trackerClientPDF;
	
	//private static TrackerClient trackerClientContractPDF;

	private static List<TrackerServer> serverPool = Collections.synchronizedList(new ArrayList<TrackerServer>());
	
	private static List<TrackerServer> serverPoolPDF = Collections.synchronizedList(new ArrayList<TrackerServer>());
	
	//private static List<TrackerServer> serverPoolContractPDF = Collections.synchronizedList(new ArrayList<TrackerServer>());

	public static synchronized void init() throws IOException {
		if (!initialized) {
			IniFileReader.init(fdfsConfFile);
			trackerClient = new TrackerClient();
			initialized = true;
		}
	}
	
	public static synchronized void initPDF() throws IOException {
		if (!initializedPDF) {
			IniFileReader.init(fdfsConfFilePDF);
			trackerClientPDF = new TrackerClient();
			initializedPDF = true;
		}
	}
	
	
	/*public static synchronized void initContractPDF() throws IOException {
		if (!initializedContractPDF) {
			IniFileReader.init(fdfsConfContractFilePDF);
			trackerClientContractPDF = new TrackerClient();
			initializedContractPDF = true;
		}
	}*/
	
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
			result = new StorageClient(server, null).upload_file(content, FileUtils.getExtNameFromFileName(file.getName()), null);
			return result[0] + '/' + result[1];
		} catch (Exception e) {
			logger.error("上传影像服务器失败",e);
			return null;
		}
		finally {
			FileUtils.closeIOSource(is);
			closeServer(server);
		}
	}

	//对应upload的文件上传
	public static String upload1(MultipartFile file) throws IOException {
		init();
		//FileInputStream is = null;
		InputStream is = null;
		TrackerServer server = null;
		try {
			//is = (FileInputStream) file.getInputStream();
			is =file.getInputStream();
			byte[] content = new byte[is.available()];
			is.read(content);
			server = getServer();
			String[] result;
			try {
				result = new StorageClient(server, null).upload_file(content, FileUtils.getExtNameFromFileName(file.getOriginalFilename()), null);
				return result[0] + '/' + result[1];
			} catch (MyException e) {
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
	
	public static byte[] downloadPDF(String storagePath) throws IOException {
		initPDF();
		TrackerServer server = null;
		try {
			server = getServerPDF();
			String[] splitPath = splitStoragePath(storagePath);
			try {
				return new StorageClient(server, null).download_file(splitPath[0], splitPath[1]);
			} catch (MyException e) {
				return new byte[0];
			}
		} finally {
			closeServerPDF(server);
		}
	}
	
	/*public static byte[] downloadContractPDF(String storagePath) throws IOException {
		initContractPDF();
		TrackerServer server = null;
		try {
			server = getServerContractPDF();
			String[] splitPath = splitStoragePath(storagePath);
			try {
				return new StorageClient(server, null).download_file(splitPath[0], splitPath[1]);
			} catch (MyException e) {
				return new byte[0];
			}
		} finally {
			closeServerContractPDF(server);
		}
	}*/

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

	public static boolean delete(String storagePath) throws IOException {
		init();
		TrackerServer server = null;
		try {
			server = getServer();
			String[] splitPath = splitStoragePath(storagePath);
			try {
				return 0 == new StorageClient(server, null).delete_file(splitPath[0], splitPath[1]);
			} catch (MyException e) {
				return false;
			}
		} finally {
			closeServer(server);
		}
	}

	private static synchronized TrackerServer getServer() throws IOException {
		if (serverPool.isEmpty()) {
			return trackerClient.getConnection();
		} else {
			TrackerServer server = serverPool.remove(serverPool.size()-1);
			if (server==null){
				return trackerClient.getConnection();
			}
			if (server.getSocket() == null || !ProtoCommon.activeTest(server.getSocket())) {
				server.close();
				return trackerClient.getConnection();
			} else {
				return server;
			}
		}
	}

	private static synchronized TrackerServer getServerPDF() throws IOException {
		if (serverPoolPDF.isEmpty()) {
			return trackerClientPDF.getConnection();
		} else {
			TrackerServer server = serverPoolPDF.remove(serverPoolPDF.size()-1);
			if (server==null){
				return trackerClientPDF.getConnection();
			}
			if (server.getSocket() == null || !ProtoCommon.activeTest(server.getSocket())) {
				server.close();
				return trackerClientPDF.getConnection();
			} else {
				return server;
			}
		}
	}
	
	
	
	/*private static synchronized TrackerServer getServerContractPDF() throws IOException {
		if (serverPoolContractPDF.isEmpty()) {
			return trackerClientContractPDF.getConnection();
		} else {
			TrackerServer server = serverPoolContractPDF.remove(serverPoolContractPDF.size()-1);
			if (server==null){
				return trackerClientContractPDF.getConnection();
			}
			if (server.getSocket() == null || !ProtoCommon.activeTest(server.getSocket())) {
				server.close();
				return trackerClientContractPDF.getConnection();
			} else {
				return server;
			}
		}
	}*/
	
	private static synchronized void closeServer(TrackerServer server) throws IOException {
		if (serverPool.size() < 10) {
			serverPool.add(server);
		} else {
			FileUtils.closeIOSource(server);
		}
	}
	
	private static synchronized void closeServerPDF(TrackerServer server) throws IOException {
		if (serverPoolPDF.size() < 10) {
			serverPoolPDF.add(server);
		} else {
			FileUtils.closeIOSource(server);
		}
	}
	
	/*private static synchronized void closeServerContractPDF(TrackerServer server) throws IOException {
		if (serverPoolContractPDF.size() < 10) {
			serverPoolContractPDF.add(server);
		} else {
			FileUtils.closeIOSource(server);
		}
	}*/

	private static String[] splitStoragePath(String storagePath) {
		int index = storagePath.indexOf("/");
		if (index == 0) {
			return null;
		} else {
			return new String[]{
					storagePath.substring(0, index),
					storagePath.substring(index + 1)
			};
		}
	}
	
	
/*	public static String myUpload(MultipartFile mf) throws IOException {
		String savePath = Global.getUserfilesBaseDir();
		String fileName = mf.getOriginalFilename();
		InputStream in = mf.getInputStream();
		FileOutputStream out = new FileOutputStream(savePath + File.separator + fileName);
		byte buffer[] = new byte[1024];
		// 判断输入流中的数据是否已经读完的标识
		int len = 0;
		// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		return "MyOnefile/MySecFile/"+fileName;
	}*/

}
