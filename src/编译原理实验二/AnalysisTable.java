package 编译原理实验二;
/**********************************************
 * 创建表格对象，行名为非终结符，列名为终结符号名。内容为文法表达式
 * */
public class AnalysisTable {
	private String nT;
	private char yT;
	private String exp;
	public AnalysisTable(String nt,char c,String exp){
		this.yT=c;
		this.nT=nt;
		this.exp=exp;
	}
	public boolean isEquals(AnalysisTable t){//判断两个表格元素是否完全相同
		if(t.nT.equals(this.nT)&&t.yT==this.yT&&t.exp.equals(this.exp))
			return true;
		return false;
	}
	public boolean cmpTnT(String nT,char yT){//查看表格元素是否已经创建。
		if(this.yT==yT&&this.nT.equals(nT))
			return true;
		else 
			return false;
	}
	public void print(){//打印当前的表格对象
		System.out.println("M["+this.nT+","+this.yT+"] :"+this.exp);
	}
	public String getExp(){//获取表格对象的内容,文法表达式。
		return this.exp;
	}
	public String getNT() {//获得非终结符。
		
		return this.nT;
	}
	public Character getYT() {//获得非终结符。
		
		return this.yT;
	}
}
