package cn.hupig.www.code.cmservice.service.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;

import cn.hupig.www.code.cmservice.web.rest.errors.FileOperationException;

public class FileOperation {

	/**
	 * 开发临时使用
	 * fileAddress本机文件存放位置
	 * saveAddress服务器的文件访问路径
	 */
	private static String fileAddress = "C:/Users/lixin/Desktop/CMService/target/classes/static/content/images/";
	private static String saveAddress = "content/images/";
	
	/**
	 * 删除文件，传入文件名+地址（本机文件存放地址）
	 * @param address
	 */
	public static void deleteFile(String address) {
		if (address.contains("author.svg")) return;
		address = address.substring(address.lastIndexOf("/") + 1);
		File file = new File(fileAddress + address);
		if (file.exists() && file.isFile()) {//文件存在且是个文件
			file.delete();
		}
	}
	
	/**
	 * 保存文件 传入文件的二进制binary,和文件的储存地址(包含文件路径、文件名)。
	 * binary是文件的二进制
	 * address是服务器文件的访问地址
	 * name是原始文件名，用来提取后缀名的
	 * @param binary
	 * @param address
	 */
	public static String save(byte[] binary, String name) {
		try {
			name = fileName(name);
			FileOutputStream file = new FileOutputStream(new File(fileAddress + name));
			file.write(binary, 0, binary.length);
			file.flush();
			file.close();
			return saveAddress + name;
		} catch (Exception e) {
			throw new FileOperationException();
		}
	}

	/**
	 * 生成文件名，随机文件名，根据时间
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static String fileName(String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		String name = String.valueOf(Instant.now().toEpochMilli());
		for (int i = 0; i < 4; i++) {
			name += String.valueOf((char) (((int) (Math.random() * 26)) + 65));
		}
		return name + fileName.substring(fileName.lastIndexOf("."),fileName.length());
	}

}
