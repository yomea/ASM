package com.asm_core.test;

import java.util.Date;

/**
 * 需要进行代理的类
 * @author Administrator
 *
 */
public class SayHello {
	
	
	public void sayHello(String str_1, String str_2, int age, String[] args) {
		
		System.out.println(str_1 + " " + str_2 + "嘿嘿：" + age);
		
	}
	
	private String l() {
		System.out.println("private String l() {");
		return "";
		
	}
	
	public int[][] tt(int age, long l, double d) {
		System.out.println("public int[][] tt(int age) {");
		return new int[][]{};
	}
	
	public String[][] hh(long k, double d, Double dd) {
		System.out.println("public String[][] hh(long k, double d, Double dd) {");
		return null;
	}
	
	
	public String[][] hh(short age, byte[] arg, int a, float f, char c, long l, double d, int[][] ii, String str, String[][] ss, Date date) {
		System.out.println("public String[][] hh(short age, byte[] arg, int a, float f, char c, long l, double d, int[][] ii, String str, String[][] ss, Date date) {");
		return null;
	}
	
	/*public String[][] hh(Long l, Double d) {
		System.out.println("public String[][] hh(short age, byte[] arg, double d) {");
		return null;
	}*/


	
	
}
