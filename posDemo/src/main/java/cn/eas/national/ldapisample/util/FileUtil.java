package cn.eas.national.ldapisample.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件操作工具函数
 * @author baoxl
 *
 */
public final class FileUtil {
	private FileUtil() {}
	
	public static String read(String absoluteFileName) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(absoluteFileName));
			return reader.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	public static String readLine(String filePath) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			return line == null ? "" : line;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	public static void write(File file, String content) {
		if (file == null) {
			return;
		}
		FileOutputStream fos = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deleteFile(File file) {
		if (file != null && file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}
}
