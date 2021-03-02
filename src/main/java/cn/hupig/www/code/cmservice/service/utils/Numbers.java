package cn.hupig.www.code.cmservice.service.utils;

public class Numbers {
	
	/**
	 * 获取任意长度的随机数，最小1位，最大17位
	 * @param length 要获取的随机数的长度
	 * @return 随机数，String类型
	 */
	public static String getRandom(Integer length) {
		String code = "0";
		while (code.substring(0,1).equals("0")) {
			code = String.valueOf(Math.random()).substring(2,length+2);
		}
		return code;
	}
	
}
