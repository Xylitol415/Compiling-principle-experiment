package 编译原理实验一;

import java.util.ArrayList;

public class ReservedWordsTable {
	public ArrayList<ReservedWord> list=new ArrayList<>();
	public ReservedWordsTable(){
		ReservedWord rw1=new ReservedWord("while", "_");
		ReservedWord rw2=new ReservedWord("if", "_");
		ReservedWord rw3=new ReservedWord("else", "_");
		ReservedWord rw4=new ReservedWord("switch", "_");
		ReservedWord rw5=new ReservedWord("case", "_");
		list.add(rw1);
		list.add(rw2);
		list.add(rw3);
		list.add(rw4);
		list.add(rw5);
	}
	public ArrayList<ReservedWord> getList(){
		return list;
	}
//	public static void main(String[] args) {
//		ReservedWordsTable rwt = new ReservedWordsTable();
//		ArrayList<ReservedWord> list = rwt.getList();
//		for(int i=0;i<list.size();i++){
//			System.out.println("("+list.get(i).name+","+list.get(i).decription+")");
//		}		
//	}
}
