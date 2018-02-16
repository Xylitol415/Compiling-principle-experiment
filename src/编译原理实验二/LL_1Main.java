package 编译原理实验二;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LL_1Main {
	private static List<String> prt = new ArrayList<String>(); // 获取的是进行分割后的集合
	private static List<First> first = new ArrayList<First>(); // 获取的first集合
	private static List<First> follow = new ArrayList<First>(); // 获取的follow集合
	private static List<AnalysisTable> table = new ArrayList<AnalysisTable>(); // 表格的形式

	public void read() {// 读取文件函数
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(".\\G(E).txt"));
			//System.out.println("读入的文法是：");
			while ((line = br.readLine()) != null) {
			//	System.out.println(line);
				pretreatment(line); // 调用预处理方法
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			if (br != null) {
				br.close();
				br = null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void pretreatment(String line) {// 对输入字符进行预处理。分割 形如
		// A->B|a的表达式为A->B和A->a
		Pattern p = Pattern.compile("([A-Z]?[']?)([-]?[>]?)([\\w\\W &&[^|]]*)([|]?)");// 重复问题。
		Matcher m = p.matcher(line);
		String emp = "";
		boolean flag = false;

		do {
			flag = false;
			// boolean b=m.find();
			// System.out.println("find "+b);
			if (m.find()) {
				String str = "";
				if (m.group(1).length() > 0 && m.group(2).length() > 0) {
					// System.out.println("补货的1"+m.group(1)+" "+m.group(3));
					emp = m.group(1);
					str = emp + "->" + m.group(3);// 解决正则表达式问题。第二次寻找时组1和组3有交叉的问题
				} else
					str = emp + "->" + m.group(1) + m.group(2) + m.group(3);

				// System.out.println(m.group(3)+" "+m.group(4)+"
				// "+m.group(3).length());
				prt.add(str);

				if (m.group(4).length() > 0)
					flag = true;
				// System.out.println("flag "+flag);
			}

		} while (flag);

	}

	public void printP() {// 打印全部的文法表达式
		Iterator<String> it = prt.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void printF(List<First> first) {// 打印全部first或follow集
		Iterator<First> it = first.iterator();
		while (it.hasNext()) {
			First ft = it.next();
			ft.print();
		}
	}

	public void FindFirstP() {// 进行找first集的第一步操作。
		Iterator<String> it = prt.iterator();
		Pattern p = Pattern.compile("([A-Z][']?)(->)([\\w\\W&&[^A-Z]]*)");
		while (it.hasNext()) {
			Matcher m = p.matcher(it.next());
			// System.out.println("是否存在4"+m.group(4));
			if (m.find() && m.group(3).length() > 0) {
				// System.out.println(m.group(1));
				if (!cmpAdd(m.group(1), m.group(3), first)) { // 将获取的是first集合
																// 如果有该集合则直接添加序列，否则就新建一个序列.
					First fc = new First(m.group(1));
					// System.out.println(m.group(1));
					fc.addf(m.group(3));
					first.add(fc);
					// System.out.println("此时的first集合为"+m.group(3));
				}

			}
		}
	}

	public void FindFirstS() {// 进行查找first集的进一步操作。

		Iterator<String> it = prt.iterator();
		while (it.hasNext()) {
			String str = it.next();
			Pattern p = Pattern.compile("([A-Z][']?)(->)([A-Z][']?)");
			Matcher m = p.matcher(str);
			if (str.matches("[A-Z][']?->[[A-Z][']?]+")) {// 如果表达式符合Y->Y1Y2Y3......Yk的形式则执行
				p = Pattern.compile("([A-Z]?[']?)([-]?[>]?)([A-Z][']?)");
				m = p.matcher(str);
				String emp = "";
				boolean flag;
				do {
					flag = false;
					if (m.find()) {
						First fc, ff;
						if (m.group(1).length() > 0) {
							emp = m.group(1);
						}
						ff = findE(emp, first); // 查找到该标记的first集合
						if (m.group(3).length() > 0) {
							fc = findE(m.group(3), first); // 查找到该标记的first集合
							// if (fc.conteinZero()) {//
							// 如果Yi的first集中含有ε，直接将Yi的first集中的内容加到ff中
							// flag = true;
							ff.addf(fc);
							// }
							// else {//
							// 如果Yi的first集中不含有ε，直接将Yi的first集中的内容加到ff中，并且取出加入的ε
							// ff.addf(fc);
							// ff.reMove('ε');// 因为只要前面的都为有ε即可，最后取到一个可以不含ε
							//
							// }
						}
					}
				} while (flag);
			}
		}
	}

	private First findE(String name, List<First> first) {// 在找到name的first/follow集中的集合。
		Iterator<First> it = first.iterator();
		First ft = null;
		while (it.hasNext()) {
			ft = it.next();
			if (ft.getName().equals(name)) {
				return ft;
			}
		}
		ft = new First(name);
		first.add(ft);
		return ft;
	}

	private boolean cmpAdd(String name, String str, List<First> first) {// 把新找到的A的First/Follow集中元素加入到其相应的集中。
		Iterator<First> it = first.iterator(); // 加入成功返回true,否则返回false;
		while (it.hasNext()) {
			First ft = it.next();
			if (ft.getName().equals(name)) {
				ft.addf(str);
				return true;
			}
		}
		return false;
	}

	private void findFollowP() {// 创建follow集
		Pattern p = Pattern.compile("([A-Z]?[']?)([-]?[>]?)([\\w\\W &&[^A-Z]]*)([[A-Z][']?]*)([\\w\\W &&[^A-Z]]*)");// 符合的模式为：非终结符->多个终结符
																													// 多个非终结符
																													// 多个终结符
		Iterator<String> it = prt.iterator();
		Matcher m = null;
		boolean IsFirst = true;
		while (it.hasNext()) {
			String wf = it.next();
			m = p.matcher(wf);
			boolean match = false;

			String ename = "";
			String emp = "";
			do {
				match = m.find();
				if (match) {
					emp = emp + m.group();// 获取到现在为止获取到的wf中的字符。
					// System.out.println("此时的"+emp);
					if (m.group(1).length() > 0) {
						ename = m.group(1);
					}
					// System.out.println(emp+" "+wf.length()+" "+emp.length()+"
					// "+m.group(4));
					First fc = findE(ename, follow); // 查找到了follow集合
					if (IsFirst) {
						fc.addf('#');// 文法开始符处理与其他非终结符处理的不同之处。
						IsFirst = false;
					}
					if (wf.length() == emp.length()) { // 将所取到的字符相加得到总字符长度与原字符长度比较即可
						if (m.group(5).length() > 0) {
							// System.out.println(m.group(5));
							String emp5 = m.group(5); // 获取的是后面的终结符字符；
							String en = "";
							Pattern p1 = Pattern.compile("([A-Z][']?)"); // 非终结符
							Matcher m1 = p1.matcher(m.group(4));// 第四个的模式范围
							while (m1.find()) {
								en = m1.group(); // 获取的非终结符的最后一个字符
							}
							if (en.length() > 0) {
								First f = findE(en, follow);
								f.addf(emp5); // 此时跟着的是终结符 所以直接添加到follow集合中；
							}

						} else if (m.group(4).length() > 0) {
							Pattern p1 = Pattern.compile("([A-Z][']?)"); // 第四个的模式是终结符还是非终结符
							Matcher m1 = p1.matcher(m.group(4));
							List<String> lT1 = new ArrayList<String>();
							List<String> lT2 = new ArrayList<String>();
							while (m1.find()) {
								// System.out.println(emp+" "+m.group(4)+"
								// "+m1.group());
								lT1.add(m1.group()); // 将第四个位置的名称添加进去
								lT2.add(m1.group());
							}
							Iterator<String> it1 = lT1.iterator();
							while (it1.hasNext()) {
								String name1 = it1.next();
								// System.out.println(" "+name1);
								String name2 = "";
								Iterator<String> it2 = lT2.iterator();

								while (it2.hasNext()) {
									name2 = it2.next();
									// System.out.println(name2+" "+name1);
									if (name2.equals(name1))
										break;
								}
								First flw = findE(name1, follow);
								boolean flage = true;
								while (it2.hasNext()) {// 必须是紧紧跟随在E之后的终结符才算follow集中
									name2 = it2.next();
									// System.out.println(name2+" "+name1);
									First fst = findE(name2, first);
									flw.addf(fst.exceptZero()); // 添加的是该字符的后面的非终结符的除了空字符的first集合
									if (!fst.containZero()) { // 判断是否含有空字符
										flage = false;
										break;// 重要，否则，将会导致程序失败。使如A->BCD形式的表达式，
										// 含ε时，仍旧将D的first集中的内容加入到了B的follow集中
									}
								}
								if (flage) {// 完成A->aBb形式，将A的follow集加到B的follow集中。
									First main = findE(ename, follow);
									flw.addf(main);
								}
							}
						}
						match = false;
					}
					// else if (m.group(5).length() > 0) {
					// // System.out.println(m.group(5));
					// String emp5 = m.group(5);
					// String en = "";
					// Pattern p1 = Pattern.compile("([A-Z][']?)");
					// Matcher m1 = p1.matcher(m.group(4));
					// while (m1.find()) {
					// en = m1.group();
					// }
					// if (en.length() > 0) {
					// First f = findE(en, follow);
					// f.addf(emp5);
					// }
					// }

				}

			} while (match);

		}
	}

	private void createTable() { // 创建ll(1)表
		Iterator<String> it = prt.iterator(); // 获取的是所有的表达式；
		Pattern p = Pattern.compile("([A-Z][']?)(->)([\\w\\W&&[^A-Z]]?)"); // 直接是终结符的表达式
		Matcher m = null;
		while (it.hasNext()) {
			String exp = it.next();
			m = p.matcher(exp); // 进行匹配
			boolean b = m.find(); // 是否匹配到
			// System.out.println(b);
			if (b && m.group(1).length() > 0) {
				// System.out.println(m.group(1) + m.group(2));
				First ft = this.findE(m.group(1), first);
				if (IsSingle(m.group(1) + m.group(2))) {// 在分解的表中 是否只有一个表达式
					// System.out.println(m.group(1));
					String collect = ft.exceptZero().getCollect();// 获取的是取出空字符的集合
					int length = collect.length();
					for (int i = 0; i < length; i++) {
						AnalysisTable t = new AnalysisTable(m.group(1), collect.charAt(i), exp);
						if (!isHas(t))
							table.add(t);
					} // 将直接的first结合中的非空字符对应的结合添加进去
				} else if (m.group(3).length() > 0) { // 如何既还有多个表达式，并且有终结符
					// System.out.println(m.group(1)+" "+m.group(3));
					// First ft=this.findE(m.group(1), first);
					if (m.group(3).charAt(0) != 'ε') {
						// System.out.println(m.group(1));
						if (ft.containChar(m.group(3).charAt(0))) {
							// System.out.println(m.group(1));
							AnalysisTable t = new AnalysisTable(m.group(1), m.group(3).charAt(0), exp);
							if (!isHas(t))
								table.add(t);
						}
					}
				}

				if (ft.containZero()) {// 判断的是first结合中是否还有空字符
					First ff = this.findE(m.group(1), follow); // 如果有空字符
																// 就查找到该集合的follow集合
					String collect = ff.exceptZero().getCollect();
					int length = collect.length();
					for (int i = 0; i < length; i++) {
						AnalysisTable t = new AnalysisTable(m.group(1), collect.charAt(i), m.group(1) + "->ε");
						if (!isHas(t))
							table.add(t);
					}
				}

			}
		}
	}

	private boolean isHas(AnalysisTable t) {// 判断在表中是不是已经存在了M[A,a],
		Iterator<AnalysisTable> it = table.iterator();
		while (it.hasNext()) {
			if (it.next().isEquals(t))
				return true;
		}
		return false;
	}

	private boolean IsSingle(String str) {// 判断表达式 A->a是不是唯一表达式。
		Iterator<String> it = prt.iterator();
		Pattern p = Pattern.compile(str);
		int sign = 0;
		while (it.hasNext()) {
			Matcher m = p.matcher(it.next());
			if (m.find()) {
				sign++;
			}
			if (sign > 1)
				return false;
		}
		return true;
	}

	private void printT() {// 打印整张表格
		Iterator<AnalysisTable> it = table.iterator();
		System.out.println("创建的LL(1)分析表：");
		String emp = "";
		while (it.hasNext()) {
			AnalysisTable t = it.next();
			String current = t.getNT();
			if (current.equals(emp))
				t.print();
			else {
				System.out.println();
				t.print();
				emp = current;
			}
		}
	}

	public static void main(String[] args) {
		LL_1Main l = new LL_1Main();
		l.read();// 从文件读取文法表达式并且对读到的表达式进行预处理
	//	System.out.println("读入文法分析分解后为：");
	//	l.printP();
		l.FindFirstP();
		/*******************************************************
		 * 应该加入次数控制，判定，全部first集已经不再发生变化，停*
		 * 止调用FindFirstS()函数！*****************************
		 *****************************************************/
		l.FindFirstS();
		l.FindFirstS();
		/*******************************************************/
		l.findFollowP();
		l.findFollowP();
		/*******************************************************/
		// System.out.println("first集：");
		// l.printF(first);
		// System.out.println("follow集：");
		// l.printF(follow);
		/*******************************************************/
		l.createTable();// 创建LL(1)表
		l.printT();// 打印表

		/******************************************************
		 * 
		 * 接下来进行的是LL(1)文法
		 */

		Character current;
		ArrayList<String> end1;
		int number = 0;
		String[] end;
		boolean flag;
		System.out.println("\n请输入输入串，以#结束：");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner.close();
		Stack<String> a = new Stack<>();
		a.push("#");
		a.push("E");
		current = input.charAt(0);
		while (!(a.peek().equals(String.valueOf(current))&&current.equals("#"))) {
		//	System.out.println("此时的" + a.peek() + "并且" + current);
			if(a.peek().equals("#")){
				System.out.println("符号栈：" + a.toString() + "   当前输入符号：" + current + "   输入串："+"  说明：匹配，分析成功");
				System.out.println("");
				break;
			}
			Iterator<AnalysisTable> it = table.iterator();
			if (!(current.equals(new Character(a.peek().charAt(0))))) {
				flag = true;
				while (it.hasNext() && flag == true) {
					AnalysisTable t = it.next();
					String a1 = t.getNT();
					if (a1.equals(a.peek()) && current.equals(t.getYT())) {
						end1 = new ArrayList<>();
						char[] temp = t.getExp().substring(t.getExp().indexOf(">") + 1).toCharArray();
						end = new String[temp.length];
						for (int i = 0; i < end.length; i++) {
							end[i] = String.valueOf(temp[temp.length - i - 1]);
						}
						for (int i = 0; i < end.length; i++) {
							if (end[i].equals("\'")) {
								end[i + 1] = end[i + 1] + "\'";
							}
						}
						for (int i = 0; i < end.length; i++) {
							if (end[i].equals("'")) {
								continue;
							}
							end1.add(end[i]);
						}
						if(end1.get(0).equals("ε")){
							System.out.println("符号栈：" + a.toString() + "    当前输入符号：" + current + "   输入串："
									+ input.substring(number + 1, input.length()) + "   说明：弹出栈顶符号" + a.peek()+"，因为下一个为空，故不压栈");
							System.out.println("");
							a.pop();
						}else{
							System.out.println("符号栈：" + a.toString() + "   当前输入符号：" + current + "  输入串："
									+ input.substring(number + 1, input.length()) + "  说明：弹出栈顶符号" + a.peek() + "，将" + end1
									+ "压栈");
							System.out.println("");
							a.pop();
							for (int i = 0; i < end1.size(); i++) {
								a.push(end1.get(i));
							}
						}
						flag = false;
					}
				}
			} else {
				System.out.println("符号栈：" + a.toString() + "   当前输入符号：" + current + "   输入串："
						+ input.substring(number + 1, input.length()) + "  说明：匹配，弹出栈顶符号" + a.peek() + "并读出输入串的下一个输入符号"
						+ input.charAt(number + 1));
				System.out.println("");
				number++;
				current = input.charAt(number);
				a.pop();
			}
		}
	}
}
