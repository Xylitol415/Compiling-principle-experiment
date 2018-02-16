package ����ԭ��ʵ����;

import java.util.LinkedList;

public class SLR_1AnalysisMain {
	
	//��Ƶ������ɾ����ʹ��������������ٶȣ�
	/*״̬ջ*/
	private LinkedList<Integer> stateStack = new LinkedList<Integer>();
	/*����ջ*/
	private LinkedList<String> symbolStack = new LinkedList<String>();
	/*���봮*/
	private String inputStr = "i+i*i#";
	/*�������̵Ľ����*/
	private LinkedList<LinkedList<String>> analysisResult = new LinkedList<LinkedList<String>>();
	/*������������*/
	private LinkedList<String> resultRow = null;
	private SLR_1AnalysisTable slr1AnalysisTable = new SLR_1AnalysisTable();
	private int step = 0;//����;
	private int statusIndex = 0;//״̬ջ�е�����(0,i)
	private String operate = "";
	
	public static void main(String[] args) {
		SLR_1AnalysisMain slr1Anilays = new SLR_1AnalysisMain();
		System.out.println("����SLR1������");
		slr1Anilays.slr1AnalysisTable.printTable();
		System.out.println("\n�����������\n"+slr1Anilays.inputStr);
		
		System.out.println("\n������������̣�");
		slr1Anilays.run();
		for (LinkedList<String> result : slr1Anilays.analysisResult) {
			for (int i = 0; i < result.size(); i++) {
				if (i == 0) {
					System.out.printf("%-8s", result.get(i));
				}else if (i == 3|| i == 4) {
					System.out.printf("%-12s", result.get(i));
				}else {
					System.out.printf("%-20s", result.get(i));
				}
			}
			System.out.println();
		}
	}
	
	public void run() {
		System.out.printf("%-3s", "����");
		System.out.printf("%-6s", "״̬ջ");
		System.out.printf("%-10s", "����ջ");
		System.out.printf("%-5s", "���봮");
		System.out.printf("%-8s", "ACTION");
		System.out.printf("%-20s\n", "GOTO");
		String temp = "";
		stateStack.addFirst(0);//״̬ջ�ĳ�ʼֵ
		symbolStack.addFirst("#");//����ջ�г�ʼֵ
		for (int i = 0; i <= inputStr.length();) {//���봮
			if (inputStr.length() == 0) {
				break;
			}
			temp = inputStr.substring(0, 1);
			statusIndex = stateStack.getLast();
			if (statusIndex < 0) {
				stateStack.removeLast();
				statusIndex = stateStack.getLast();
			}
			//action����goto������Si����ri��
			operate = slr1AnalysisTable.getSTATUS().get(statusIndex).get(temp);
			if (slr1AnalysisTable.formatMoveIn(operate) >= 0) {
				moveIn(temp);
				 i++;
			} else if (slr1AnalysisTable.formatMoveOutState(operate) > 0)  {//��Լ
				i --;
				while(true) {
					if ((stateStack.getLast() <= 0 || slr1AnalysisTable.formatMoveIn(operate) >= 0) || (slr1AnalysisTable.formatMoveOutState(operate) < 0)) {//ѭ������
						moveIn(temp);
						break;		
					}
					moveOut();
					operate = slr1AnalysisTable.getSTATUS().get(stateStack.getLast()).get(temp);
				}
			}
		}
	}
	
	/**
	 * �ƽ�����
	 */
	public void moveIn(String temp) {
		resultRow = new LinkedList<String>();
		
		resultRow.add(String.valueOf(++step));//����
		resultRow.add(stateStack.toString());//״̬��
		resultRow.add(symbolStack.toString().replace(',', ' '));//������
		resultRow.add(inputStr);//���봮
		resultRow.add(operate);//ACTION����
		resultRow.add("");//GOTO����
		
		stateStack.add(slr1AnalysisTable.formatMoveIn(operate));//״̬ջ��ջ
		inputStr = inputStr.substring(1);//��ջ֮���ַ����еĵ�һ��ȥ��
		symbolStack.add(temp);//���Ž�ջ
		
		analysisResult.add(resultRow);
	}
	
	/**
	 * ��Լ����
	 */
	public void moveOut() {
		resultRow = new LinkedList<String>();
		
		resultRow.add(stateStack.toString());//״̬��
		String prodRight = slr1AnalysisTable.getGRow().get(operate);//����ʽ�Ҳ�
		String prodLeft = slr1AnalysisTable.getG().get(prodRight);//����ʽ��
		String strSymbolStacke = symbolStack.toString().replace(',', ' ');//����ջ�д��ڵ�
		for (int j = 0; j < prodRight.length(); j++) {//�����ջ
			symbolStack.removeLast();//����ջ�����һ����ջ
			stateStack.removeLast();//״̬ջ�����һ����ջ
		}
		symbolStack.add(prodLeft);//��Լ�����ջ
		//GOTO
		statusIndex = stateStack.getLast();
		String str_goto = slr1AnalysisTable.getSTATUS().get(statusIndex).get(prodLeft);
		//statusIndex = slr1AnilaysTable.formatMoveOutState(str_goto);//GOTO�������޸�״̬���
		stateStack.add(Integer.valueOf(str_goto));//GOTO֮��״̬��ջ
		
		resultRow.addFirst(String.valueOf(++step));//����
		//resultRow.add(stateStack.toString());//״̬��
		resultRow.add(strSymbolStacke);//������
		resultRow.add(inputStr);//���봮
		resultRow.add(operate);//ACTION����
		resultRow.add(String.valueOf(Integer.valueOf(str_goto)));//GOTO����
		
		analysisResult.add(resultRow);
	}
	
}
