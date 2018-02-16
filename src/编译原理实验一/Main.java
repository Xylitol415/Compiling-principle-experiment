package 编译原理实验一;

import java.util.Scanner;

public class Main {
	public static void main(String args[]){
		System.out.println("请输入要分析的程序，输入start开始分析：");
		String temp="";
		StringBuffer content=new StringBuffer("");
		Scanner scanner = new Scanner(System.in);
		temp = scanner.nextLine();
		do{
			content=content.append(temp);
			temp = scanner.nextLine();
		}while(!temp.equals("start"));
		scanner.close();
		char[] list=content.toString().toCharArray();
		
		LexicalParser construct=new LexicalParser(list);
		construct.getIdentifierList(); //标识符表
		construct.getOthersList(); //其他符号表
		construct.getNumberList();  //常数表
		construct.getReservedWordsList();  //保留字表

		for(int i=0;i<construct.all.size();i++){
			System.out.println("("+construct.all.get(i).getName()+","+construct.all.get(i).getLocation()+")");
		}
	}
}
