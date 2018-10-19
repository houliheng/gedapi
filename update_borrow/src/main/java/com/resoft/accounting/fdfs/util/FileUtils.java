package com.resoft.accounting.fdfs.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.csource.fastdfs.TrackerServer;

public class FileUtils {

	public static String getExtNameFromFileName(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex == -1) {
			return null;
		} else {
			return fileName.substring(dotIndex + 1);
		}
	}
	
	public static void closeIOSource(Object o) throws IOException{
		if (o != null) {
			if (o instanceof Reader){
				((Reader) o).close();
			} else if (o instanceof Writer){
				((Writer) o).close();
			} else if (o instanceof InputStream){
				((InputStream) o).close();
			} else if (o instanceof OutputStream){
				((OutputStream) o).close();
			} else if (o instanceof TrackerServer){
				((TrackerServer) o).close();
			} else {
				throw new IllegalArgumentException("Unsupportable type: " + o.getClass().getCanonicalName());
			}
		}
	}
	
	public static void delete(File dir) {
		if (dir.isDirectory()) {
			for (File sub : dir.listFiles()) {
				delete(sub);
			}
		}
		dir.delete();
	}

	public static TreeMap<String, TreeSet<String>> listFile(File dir) {
		TreeMap<String, TreeSet<String>> fileMap = new TreeMap<String, TreeSet<String>>();
		listFile(dir, fileMap, "/");
		return fileMap;
	}
	
	private static void listFile(File dir, TreeMap<String, TreeSet<String>> fileMap, String path) {
		fileMap.put(path, null);
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				if (fileMap.get(path) == null) {
					fileMap.put(path, new TreeSet<String>());
				}
				fileMap.get(path).add(f.getName());
			} else {
				listFile(f, fileMap, path + f.getName()+ '/' );
			}
		}
	}
	
	public static void forceMkdir(File directory) throws IOException {
		if (directory.exists()) {
			if (!directory.isDirectory()) {
				String message = "File " + directory + " exists and is "
						+ "not a directory. Unable to create directory.";
				throw new IOException(message);
			}
		} else if (!directory.mkdirs()) {
			if (!directory.isDirectory()) {
				String message = "Unable to create directory " + directory;
				throw new IOException(message);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(listFile(new File("C:/download/fastdfs_deploy/fastdfs-client-java-master/target")));
	}
	
}
