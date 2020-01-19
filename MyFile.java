package com.zccp.tongyin;

import java.io.*;
import java.lang.management.GarbageCollectorMXBean;

public class MyFile {
	public static String read() throws IOException {
		File file = new File("C:\\Users\\bj_gj\\Desktop\\JavaTest.txt"); // 用File类打开本地文件
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String s = null;
		try {
			fis = new FileInputStream(file); // 实例化FileInputStream对象时，传入已打开文件的File对象
			isr = new InputStreamReader(fis); // 使用InputStream读取出来的是byte数组
			br = new BufferedReader(isr);

			char[] container = new char[1024];
			
			int ci, i;
			for (i = 0; i < 1024; i++) {
				ci = br.read();
				if (ci == -1) {
                    break;
                }
				container[i] = (char) ci;
			}
			s = new String(container, 0, i);
			System.out.println(s);
			
			
		} catch (IOException e) {
			// TODO: handle exception
		} finally {
			fis.close();
			br.close();
			isr.close();
			
		}
		return s;
	}

}