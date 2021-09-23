package cn.hupig.www.code.cmservice.service.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

import javax.imageio.ImageIO;

import cn.hupig.www.code.cmservice.web.rest.errors.FileOperationException;
import net.coobird.thumbnailator.Thumbnails;

public class FileOperation {

	/**
	 * 开发临时使用
	 * fileAddress本机文件存放位置
	 * saveAddress服务器的文件访问路径
	 */
	private static String fileAddress = "/home/apache-tomcat-10.0.0-M10/webapps/ROOT/";
	// 开发机windows的项目上传位置
//	private static String fileAddress = "C:/Users/lixin/Desktop/CMService/target/classes/static/content/images/";
	private static String saveAddress = "/webfile/";

	/**
	 * 删除文件，传入文件名+地址（本机文件存放地址）
	 * @param address
	 */
	public static void deleteFile(String address, String type) {
		if (address.contains("author.svg")) return;
		address = address.substring(address.lastIndexOf("/") + 1);
		address = type.equals("image")? "image/" + address : address;
		address = type.equals("user")? "user/" + address : address;
		File file = new File(fileAddress + address);
		if (file.exists() && file.isFile()) {// 文件存在且是个文件
			file.delete();
			if (type.equals("image")) { // 删除缓存文件
				File thumbnailFile = new File(fileAddress + "thumbnail/" + address.substring(address.lastIndexOf("/") + 1));
				thumbnailFile.delete();
			}
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
	public static String save(byte[] binary, String name, String type) {
		try {
			name = fileName(name, type);
			File file = new File(fileAddress + name);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(binary, 0, binary.length);
			fileOutputStream.flush();
			fileOutputStream.close();
			if (type.equals("image")) {
				thumbnail(name);
			}
			return saveAddress + name;
		} catch (Exception e) {
			throw new FileOperationException();
		}
	}

	/**
	 * 生成缩略图
	 */
	private static void thumbnail(String name) {
		name = name.substring(name.lastIndexOf("/") + 1);
		try {
			File iniFile = new File(fileAddress + "image/" + name);
			File rarFile = new File(fileAddress + "thumbnail/" + name);
			if (!rarFile.getParentFile().exists()) {
				rarFile.getParentFile().mkdirs();
			}
			BufferedImage bufferedImage = ImageIO.read(new FileInputStream(iniFile));
			Integer girth = bufferedImage.getWidth() + bufferedImage.getHeight();
			Double rarRatio = girth > 10000 ? 0.1 : 
				girth > 8000 ? 0.2 :
				girth > 6000 ? 0.3 : 
				girth > 4000 ? 0.4 : 
				girth > 2000 ? 0.5 : 0.8; // 计算压缩比
			Thumbnails.of(iniFile)
			.scale(rarRatio)
			.outputQuality(0.5f)
		    .toFile(rarFile);
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
	public static String fileName(String fileName, String type) {
		fileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		String name = String.valueOf(Instant.now().toEpochMilli());
		for (int i = 0; i < 4; i++) {
			name += String.valueOf((char) (((int) (Math.random() * 26)) + 65));
		}
		name = type.equals("image")? "image/" + name : name;
		name = type.equals("article")? "article/" + name : name;
		name = type.equals("user")? "user/" + name : name;
		return name + fileName.substring(fileName.lastIndexOf("."),fileName.length());
	}
	
	public static String getCacheAddress(String address) {
		return saveAddress + "thumbnail/" +
				address.substring(address.lastIndexOf("/") + 1);
	}

}
