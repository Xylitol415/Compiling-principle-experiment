package 编译原理实验三;

import java.util.LinkedList;

public class SLR_1AnalysisMain {
	
	//（频繁的增删操作使用链表可以提升速度）
	/*状态栈*/
	private LinkedList<Integer> stateStack = new LinkedList<Integer>();
	/*符号栈*/
	private LinkedList<String> symbolStack = new LinkedList<String>();
	/*输入串*/
	private String inputStr = "i+i*i#";
	/*分析过程的结果表*/
	private LinkedList<LinkedList<String>> analysisResult = new LinkedList<LinkedList<String>>();
	/*分析结果表的行*/
	private LinkedList<String> resultRow = null;
	private SLR_1AnalysisTable slr1AnalysisTable = new SLR_1AnalysisTable();
	private int step = 0;//步骤;
	private int statusIndex = 0;//状态栈中的索引(0,i)
	private String operate = "";
	
	public static void main(String[] args) {
		SLR_1AnalysisMain slr1Anilays = new SLR_1AnalysisMain();
		System.out.println("输入SLR1分析表：");
		slr1Anilays.slr1AnalysisTable.printTable();
		System.out.println("\n输入分析串：\n"+slr1Anilays.inputStr);
		
		System.out.println("\n输出串分析过程：");
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
		System.out.printf("%-3s", "步骤");
		System.out.printf("%-6s", "状态栈");
		System.out.printf("%-10s", "符号栈");
		System.out.printf("%-5s", "输入串");
		System.out.printf("%-8s", "ACTION");
		System.out.printf("%-20s\n", "GOTO");
		String temp = "";
		stateStack.addFirst(0);//状态栈的初始值
		symbolStack.addFirst("#");//符号栈中初始值
		for (int i = 0; i <= inputStr.length();) {//输入串
			if (inputStr.length() == 0) {
				break;
			}
			temp = inputStr.substring(0, 1);
			statusIndex = stateStack.getLast();
			if (statusIndex < 0) {
				stateStack.removeLast();
				statusIndex = stateStack.getLast();
			}
			//action或者goto操作（Si或者ri）
			operate = slr1AnalysisTable.getSTATUS().get(statusIndex).get(temp);
			if (slr1AnalysisTable.formatMoveIn(operate) >= 0) {
				moveIn(temp);
				 i++;
			} else if (slr1AnalysisTable.formatMoveOutState(operate) > 0)  {//规约
				i --;
				while(true) {
					if ((stateStack.getLast() <= 0 || slr1AnalysisTable.formatMoveIn(operate) >= 0) || (slr1AnalysisTable.formatMoveOutState(operate) < 0)) {//循环出口
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
	 * 移进操作
	 */
	public void moveIn(String temp) {
		resultRow = new LinkedList<String>();
		
		resultRow.add(String.valueOf(++step));//步骤
		resultRow.add(stateStack.toString());//状态列
		resultRow.add(symbolStack.toString().replace(',', ' '));//符号列
		resultRow.add(inputStr);//输入串
		resultRow.add(operate);//ACTION操作
		resultRow.add("");//GOTO操作
		
		stateStack.add(slr1AnalysisTable.formatMoveIn(operate));//状态栈进栈
		inputStr = inputStr.substring(1);//进栈之后将字符串中的第一个去掉
		symbolStack.add(temp);//符号进栈
		
		analysisResult.add(resultRow);
	}
	
	/**
	 * 规约操作
	 */
	public void moveOut() {
		resultRow = new LinkedList<String>();
		
		resultRow.add(stateStack.toString());//状态列
		String prodRight = slr1AnalysisTable.getGRow().get(operate);//产生式右部
		String prodLeft = slr1AnalysisTable.getG().get(prodRight);//产生式左部
		String strSymbolStacke = symbolStack.toString().replace(',', ' ');//符号栈中存在的
		for (int j = 0; j < prodRight.length(); j++) {//句柄出栈
			symbolStack.removeLast();//符号栈中最后一个出栈
			stateStack.removeLast();//状态栈中最后一个出栈
		}
		symbolStack.add(prodLeft);//规约结果入栈
		//GOTO
		statusIndex = stateStack.getLast();
		String str_goto = slr1AnalysisTable.getSTATUS().get(statusIndex).get(prodLeft);
		//statusIndex = slr1AnilaysTable.formatMoveOutState(str_goto);//GOTO后重新修改状态编号
		stateStack.add(Integer.valueOf(str_goto));//GOTO之后状态进栈
		
		resultRow.addFirst(String.valueOf(++step));//步骤
		//resultRow.add(stateStack.toString());//状态列
		resultRow.add(strSymbolStacke);//符号列
		resultRow.add(inputStr);//输入串
		resultRow.add(operate);//ACTION操作
		resultRow.add(String.valueOf(Integer.valueOf(str_goto)));//GOTO操作
		
		analysisResult.add(resultRow);
	}
	
}
