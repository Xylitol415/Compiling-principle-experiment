package ����ԭ��ʵ��һ;

import java.util.Scanner;

public class Main {
	public static void main(String args[]){
		System.out.println("������Ҫ�����ĳ�������start��ʼ������");
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
		construct.getIdentifierList(); //��ʶ����
		construct.getOthersList(); //�������ű�
		construct.getNumberList();  //������
		construct.getReservedWordsList();  //�����ֱ�

		for(int i=0;i<construct.all.size();i++){
			System.out.println("("+construct.all.get(i).getName()+","+construct.all.get(i).getLocation()+")");
		}
	}
}
