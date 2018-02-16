package 编译原理实验一;
/*
 * 主要的思想是 通过建立两个字符串的队列 然后
 * 通过一个while（字符的长度）{
 * 然后不断调用recognize
 * 然后返回两个数组
 * 显示即可
 */
import java.util.ArrayList;

public class LexicalParser {
	char[] list;
	ArrayList<Item> all=new ArrayList<>();
	char s;
	ReservedWordsTable table=new ReservedWordsTable();
	boolean er=true;
	int size=0;
	String token;
	ArrayList<Item> reservedWordsList=new ArrayList<>();//保留字表
	ArrayList<Item> identifierList=new ArrayList<>();//标识符表
	ArrayList<Item> numberList=new ArrayList<>();//常数表
	ArrayList<Item> othersList=new ArrayList<>();
	public LexicalParser(char[]list){
		this.list=list;
		while(size!=list.length&&er){
			Recognize();
		}
	}
	public void Recognize(){
		token="";
		s=getchar();
		getbe(s);
		switch(s){
			case'a':
			case'b':
			case'c':
			case'd':
			case'e':
			case'f':
			case'g':
			case'h':
			case'i':
			case'j':
			case'k':
			case'l':
			case'm':
			case'n':
			case'o':
			case'p':
			case'q':
			case'r':
			case's':
			case't':
			case'u':
			case'v':
			case'w':
			case'x':
			case'y':
			case'z':
				while(letter(s)||digit(s)){
					token=token+s;
					s=getchar();
				}
				if(s=='#'){
					int c=reserve();
					if(c==0){
						createIdentifierList(true);
					}else
						createIdentifierList(false);
					er=false;
				}
				else{
					size=size-1;
					int c=reserve();
					if(c==0){
						createIdentifierList(true);
					}else
						createIdentifierList(false);
				}
				break;
			case'0':
			case'1':
			case'2':
			case'3':
			case'4':
			case'5':
			case'6':
			case'7':
			case'8':
			case'9':
				while(digit(s)){
					token=token+s;
					s=getchar();
				}
				if(s=='#'){
					createNumberList();
					break;
				}else{
					size=size-1;
					createNumberList();
					break;
				}	
			case'+':
				createOthersList("+");
				break;
			case'-':
				createOthersList("-");
				break;
			case'*':
				createOthersList("*");
				break;
			case'<':
				s=getchar();
				if(s=='='){
					createOthersList("<=");
				}
				else{
					size=size-1;
					createOthersList("=");
				}
				break;
			case'=':
				s=getchar();
				if(s=='='){
					createOthersList("==");
				}
				else{
					size=size-1;
					createOthersList("=");
				}
				break;
			case';':
				createOthersList(";");
		}			
	}

	private boolean digit(char c){
		if(c>=48&&c<=57){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	private boolean letter(char c){
		
		if(c>=97&&c<=122){
			return true;
		}
		else{
			return false;
		}
		
	}
	private void error() {
		System.out.println("有错误");
		er=false;
	}
	private void createNumberList() {
		numberList.add(new Item(token,String.valueOf(numberList.size())));
		all.add(new Item(token,String.valueOf(numberList.size())));
	}
	private void createOthersList(String c) {
		othersList.add(new Item(c,String.valueOf(othersList.size())));
		all.add(new Item(c,String.valueOf(othersList.size())));
	}
	private void createIdentifierList(boolean flag) {
		if(flag){
			identifierList.add(new Item(token,String.valueOf(identifierList.size())));
			all.add(new Item(token,String.valueOf(identifierList.size())));
		}
		else{
			all.add(new Item(token,"_"));
			
		}
	}
	private int reserve() {//判断是否为保留字
		for(int i=0;i<table.list.size();i++){
			if(table.list.get(i).getMnemonicCode().equals(token)){
				return 1;
			}
		}
		return 0;
	}
	private void getbe(char temp) {
		if(temp==32){
			s=list[size];
			size=size+1;
			getbe(s);
		}else
			return;
	}

	private char getchar() {
		if(size==list.length){
			return '#';
		}
			char temp= list[size];
			size=size+1;
			return temp;	
	}
	public ArrayList<Item> getIdentifierList() {
		return identifierList;
	}
	public void setIdentifierList(ArrayList<Item> idList) {
		identifierList = idList;
	}
	public ArrayList<Item> getNumberList() {
		return numberList;
	}
	public void setNumberList(ArrayList<Item> numlist) {
		numberList = numlist;
	}
	public ArrayList<Item> getOthersList() {
		return othersList;
	}
	public void setOthersList(ArrayList<Item> otherslist) {
		othersList = otherslist;
	}
	public ArrayList<Item> getReservedWordsList() {
		return reservedWordsList;
	}
	public void setReservedWordsList(ArrayList<Item> reservedWordlist) {
		this.reservedWordsList = reservedWordlist;
	}
}
