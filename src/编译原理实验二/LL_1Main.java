package ����ԭ��ʵ���;

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
	private static List<String> prt = new ArrayList<String>(); // ��ȡ���ǽ��зָ��ļ���
	private static List<First> first = new ArrayList<First>(); // ��ȡ��first����
	private static List<First> follow = new ArrayList<First>(); // ��ȡ��follow����
	private static List<AnalysisTable> table = new ArrayList<AnalysisTable>(); // ������ʽ

	public void read() {// ��ȡ�ļ�����
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(".\\G(E).txt"));
			//System.out.println("������ķ��ǣ�");
			while ((line = br.readLine()) != null) {
			//	System.out.println(line);
				pretreatment(line); // ����Ԥ������
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

	public void pretreatment(String line) {// �������ַ�����Ԥ�����ָ� ����
		// A->B|a�ı��ʽΪA->B��A->a
		Pattern p = Pattern.compile("([A-Z]?[']?)([-]?[>]?)([\\w\\W &&[^|]]*)([|]?)");// �ظ����⡣
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
					// System.out.println("������1"+m.group(1)+" "+m.group(3));
					emp = m.group(1);
					str = emp + "->" + m.group(3);// ���������ʽ���⡣�ڶ���Ѱ��ʱ��1����3�н��������
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

	public void printP() {// ��ӡȫ�����ķ����ʽ
		Iterator<String> it = prt.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void printF(List<First> first) {// ��ӡȫ��first��follow��
		Iterator<First> it = first.iterator();
		while (it.hasNext()) {
			First ft = it.next();
			ft.print();
		}
	}

	public void FindFirstP() {// ������first���ĵ�һ��������
		Iterator<String> it = prt.iterator();
		Pattern p = Pattern.compile("([A-Z][']?)(->)([\\w\\W&&[^A-Z]]*)");
		while (it.hasNext()) {
			Matcher m = p.matcher(it.next());
			// System.out.println("�Ƿ����4"+m.group(4));
			if (m.find() && m.group(3).length() > 0) {
				// System.out.println(m.group(1));
				if (!cmpAdd(m.group(1), m.group(3), first)) { // ����ȡ����first����
																// ����иü�����ֱ��������У�������½�һ������.
					First fc = new First(m.group(1));
					// System.out.println(m.group(1));
					fc.addf(m.group(3));
					first.add(fc);
					// System.out.println("��ʱ��first����Ϊ"+m.group(3));
				}

			}
		}
	}

	public void FindFirstS() {// ���в���first���Ľ�һ��������

		Iterator<String> it = prt.iterator();
		while (it.hasNext()) {
			String str = it.next();
			Pattern p = Pattern.compile("([A-Z][']?)(->)([A-Z][']?)");
			Matcher m = p.matcher(str);
			if (str.matches("[A-Z][']?->[[A-Z][']?]+")) {// ������ʽ����Y->Y1Y2Y3......Yk����ʽ��ִ��
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
						ff = findE(emp, first); // ���ҵ��ñ�ǵ�first����
						if (m.group(3).length() > 0) {
							fc = findE(m.group(3), first); // ���ҵ��ñ�ǵ�first����
							// if (fc.conteinZero()) {//
							// ���Yi��first���к��Цţ�ֱ�ӽ�Yi��first���е����ݼӵ�ff��
							// flag = true;
							ff.addf(fc);
							// }
							// else {//
							// ���Yi��first���в����Цţ�ֱ�ӽ�Yi��first���е����ݼӵ�ff�У�����ȡ������Ħ�
							// ff.addf(fc);
							// ff.reMove('��');// ��ΪֻҪǰ��Ķ�Ϊ�Цż��ɣ����ȡ��һ�����Բ�����
							//
							// }
						}
					}
				} while (flag);
			}
		}
	}

	private First findE(String name, List<First> first) {// ���ҵ�name��first/follow���еļ��ϡ�
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

	private boolean cmpAdd(String name, String str, List<First> first) {// �����ҵ���A��First/Follow����Ԫ�ؼ��뵽����Ӧ�ļ��С�
		Iterator<First> it = first.iterator(); // ����ɹ�����true,���򷵻�false;
		while (it.hasNext()) {
			First ft = it.next();
			if (ft.getName().equals(name)) {
				ft.addf(str);
				return true;
			}
		}
		return false;
	}

	private void findFollowP() {// ����follow��
		Pattern p = Pattern.compile("([A-Z]?[']?)([-]?[>]?)([\\w\\W &&[^A-Z]]*)([[A-Z][']?]*)([\\w\\W &&[^A-Z]]*)");// ���ϵ�ģʽΪ�����ս��->����ս��
																													// ������ս��
																													// ����ս��
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
					emp = emp + m.group();// ��ȡ������Ϊֹ��ȡ����wf�е��ַ���
					// System.out.println("��ʱ��"+emp);
					if (m.group(1).length() > 0) {
						ename = m.group(1);
					}
					// System.out.println(emp+" "+wf.length()+" "+emp.length()+"
					// "+m.group(4));
					First fc = findE(ename, follow); // ���ҵ���follow����
					if (IsFirst) {
						fc.addf('#');// �ķ���ʼ���������������ս������Ĳ�֮ͬ����
						IsFirst = false;
					}
					if (wf.length() == emp.length()) { // ����ȡ�����ַ���ӵõ����ַ�������ԭ�ַ����ȱȽϼ���
						if (m.group(5).length() > 0) {
							// System.out.println(m.group(5));
							String emp5 = m.group(5); // ��ȡ���Ǻ�����ս���ַ���
							String en = "";
							Pattern p1 = Pattern.compile("([A-Z][']?)"); // ���ս��
							Matcher m1 = p1.matcher(m.group(4));// ���ĸ���ģʽ��Χ
							while (m1.find()) {
								en = m1.group(); // ��ȡ�ķ��ս�������һ���ַ�
							}
							if (en.length() > 0) {
								First f = findE(en, follow);
								f.addf(emp5); // ��ʱ���ŵ����ս�� ����ֱ����ӵ�follow�����У�
							}

						} else if (m.group(4).length() > 0) {
							Pattern p1 = Pattern.compile("([A-Z][']?)"); // ���ĸ���ģʽ���ս�����Ƿ��ս��
							Matcher m1 = p1.matcher(m.group(4));
							List<String> lT1 = new ArrayList<String>();
							List<String> lT2 = new ArrayList<String>();
							while (m1.find()) {
								// System.out.println(emp+" "+m.group(4)+"
								// "+m1.group());
								lT1.add(m1.group()); // �����ĸ�λ�õ�������ӽ�ȥ
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
								while (it2.hasNext()) {// �����ǽ���������E֮����ս������follow����
									name2 = it2.next();
									// System.out.println(name2+" "+name1);
									First fst = findE(name2, first);
									flw.addf(fst.exceptZero()); // ��ӵ��Ǹ��ַ��ĺ���ķ��ս���ĳ��˿��ַ���first����
									if (!fst.containZero()) { // �ж��Ƿ��п��ַ�
										flage = false;
										break;// ��Ҫ�����򣬽��ᵼ�³���ʧ�ܡ�ʹ��A->BCD��ʽ�ı��ʽ��
										// ����ʱ���Ծɽ�D��first���е����ݼ��뵽��B��follow����
									}
								}
								if (flage) {// ���A->aBb��ʽ����A��follow���ӵ�B��follow���С�
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

	private void createTable() { // ����ll(1)��
		Iterator<String> it = prt.iterator(); // ��ȡ�������еı��ʽ��
		Pattern p = Pattern.compile("([A-Z][']?)(->)([\\w\\W&&[^A-Z]]?)"); // ֱ�����ս���ı��ʽ
		Matcher m = null;
		while (it.hasNext()) {
			String exp = it.next();
			m = p.matcher(exp); // ����ƥ��
			boolean b = m.find(); // �Ƿ�ƥ�䵽
			// System.out.println(b);
			if (b && m.group(1).length() > 0) {
				// System.out.println(m.group(1) + m.group(2));
				First ft = this.findE(m.group(1), first);
				if (IsSingle(m.group(1) + m.group(2))) {// �ڷֽ�ı��� �Ƿ�ֻ��һ�����ʽ
					// System.out.println(m.group(1));
					String collect = ft.exceptZero().getCollect();// ��ȡ����ȡ�����ַ��ļ���
					int length = collect.length();
					for (int i = 0; i < length; i++) {
						AnalysisTable t = new AnalysisTable(m.group(1), collect.charAt(i), exp);
						if (!isHas(t))
							table.add(t);
					} // ��ֱ�ӵ�first����еķǿ��ַ���Ӧ�Ľ����ӽ�ȥ
				} else if (m.group(3).length() > 0) { // ��μȻ��ж�����ʽ���������ս��
					// System.out.println(m.group(1)+" "+m.group(3));
					// First ft=this.findE(m.group(1), first);
					if (m.group(3).charAt(0) != '��') {
						// System.out.println(m.group(1));
						if (ft.containChar(m.group(3).charAt(0))) {
							// System.out.println(m.group(1));
							AnalysisTable t = new AnalysisTable(m.group(1), m.group(3).charAt(0), exp);
							if (!isHas(t))
								table.add(t);
						}
					}
				}

				if (ft.containZero()) {// �жϵ���first������Ƿ��п��ַ�
					First ff = this.findE(m.group(1), follow); // ����п��ַ�
																// �Ͳ��ҵ��ü��ϵ�follow����
					String collect = ff.exceptZero().getCollect();
					int length = collect.length();
					for (int i = 0; i < length; i++) {
						AnalysisTable t = new AnalysisTable(m.group(1), collect.charAt(i), m.group(1) + "->��");
						if (!isHas(t))
							table.add(t);
					}
				}

			}
		}
	}

	private boolean isHas(AnalysisTable t) {// �ж��ڱ����ǲ����Ѿ�������M[A,a],
		Iterator<AnalysisTable> it = table.iterator();
		while (it.hasNext()) {
			if (it.next().isEquals(t))
				return true;
		}
		return false;
	}

	private boolean IsSingle(String str) {// �жϱ��ʽ A->a�ǲ���Ψһ���ʽ��
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

	private void printT() {// ��ӡ���ű��
		Iterator<AnalysisTable> it = table.iterator();
		System.out.println("������LL(1)������");
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
		l.read();// ���ļ���ȡ�ķ����ʽ���ҶԶ����ı��ʽ����Ԥ����
	//	System.out.println("�����ķ������ֽ��Ϊ��");
	//	l.printP();
		l.FindFirstP();
		/*******************************************************
		 * Ӧ�ü���������ƣ��ж���ȫ��first���Ѿ����ٷ����仯��ͣ*
		 * ֹ����FindFirstS()������*****************************
		 *****************************************************/
		l.FindFirstS();
		l.FindFirstS();
		/*******************************************************/
		l.findFollowP();
		l.findFollowP();
		/*******************************************************/
		// System.out.println("first����");
		// l.printF(first);
		// System.out.println("follow����");
		// l.printF(follow);
		/*******************************************************/
		l.createTable();// ����LL(1)��
		l.printT();// ��ӡ��

		/******************************************************
		 * 
		 * ���������е���LL(1)�ķ�
		 */

		Character current;
		ArrayList<String> end1;
		int number = 0;
		String[] end;
		boolean flag;
		System.out.println("\n���������봮����#������");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner.close();
		Stack<String> a = new Stack<>();
		a.push("#");
		a.push("E");
		current = input.charAt(0);
		while (!(a.peek().equals(String.valueOf(current))&&current.equals("#"))) {
		//	System.out.println("��ʱ��" + a.peek() + "����" + current);
			if(a.peek().equals("#")){
				System.out.println("����ջ��" + a.toString() + "   ��ǰ������ţ�" + current + "   ���봮��"+"  ˵����ƥ�䣬�����ɹ�");
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
						if(end1.get(0).equals("��")){
							System.out.println("����ջ��" + a.toString() + "    ��ǰ������ţ�" + current + "   ���봮��"
									+ input.substring(number + 1, input.length()) + "   ˵��������ջ������" + a.peek()+"����Ϊ��һ��Ϊ�գ��ʲ�ѹջ");
							System.out.println("");
							a.pop();
						}else{
							System.out.println("����ջ��" + a.toString() + "   ��ǰ������ţ�" + current + "  ���봮��"
									+ input.substring(number + 1, input.length()) + "  ˵��������ջ������" + a.peek() + "����" + end1
									+ "ѹջ");
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
				System.out.println("����ջ��" + a.toString() + "   ��ǰ������ţ�" + current + "   ���봮��"
						+ input.substring(number + 1, input.length()) + "  ˵����ƥ�䣬����ջ������" + a.peek() + "���������봮����һ���������"
						+ input.charAt(number + 1));
				System.out.println("");
				number++;
				current = input.charAt(number);
				a.pop();
			}
		}
	}
}
