package 编译原理实验三;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SLR(1) 分析表
 */
public class SLR_1AnalysisTable {

	/*action*/
	private List<String> ACTION = new ArrayList<String>();
	/*goto*/
	private List<String> GOTO = new ArrayList<String>();
	/*状态*/
	private List<Map<String, String>> STATUS = new ArrayList<Map<String,String>>();
	/*文法*/
	private Map<String, String> G = new HashMap<String,String>();
	/*文法中各个产生式对应的索引*/
	private Map<String, String> GRow = new HashMap<String, String>();
	
	public SLR_1AnalysisTable() {
		initAction();
		initGoto();
		initGrammar();
		initStatus();
	}
	
	/**
	 * 初始化文法和各个产生式对应的索引
	 */
	public void initGrammar(){
		/*初始化文法*/
		//G.put("E", "S'");
		G.put("E+T", "E");
		G.put("T", "E");
		G.put("T*F", "T");
		G.put("F", "T");
		G.put("(E)", "F");
		G.put("i", "F");
		/*初始化各个产生式对应的索引*/
		//GRow.put("E", 0);
		GRow.put("r1","E+T");
		GRow.put("r2","T");
		GRow.put("r3","T*F");
		GRow.put("r4","F");
		GRow.put("r5","(E)");
		GRow.put("r6","i");
	}
	
	/**
	 * 初始化action
	 */
	public void initAction(){
		ACTION.add("i");
		ACTION.add("+");
		ACTION.add("*");
		ACTION.add("(");
		ACTION.add(")");
		ACTION.add("#");
	}
	
	/**
	 * 初始化goto
	 */
	public void initGoto(){
		GOTO.add("E");
		GOTO.add("T");
		GOTO.add("F");
	}
	
	/**
	 * 初始化每个状态下的操作
	 */
	public void initStatus(){
		Map<String, String> map = new HashMap<String, String>();
		/*=0=*/
		map.put(ACTION.get(0), "S5");
		map.put(ACTION.get(1), "    ");
		map.put(ACTION.get(2), "    ");
		map.put(ACTION.get(3), "S4");
		map.put(ACTION.get(4), "    ");
		map.put(ACTION.get(5), "    ");
		map.put(GOTO.get(0), "1");
		map.put(GOTO.get(1), "2");
		map.put(GOTO.get(2), "3");
		STATUS.add(map);
		/*=1=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "S6");
		map.put(ACTION.get(2), "    ");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "    ");
		map.put(ACTION.get(5), "acc");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=2=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "r2");
		map.put(ACTION.get(2), "S7");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "r2");
		map.put(ACTION.get(5), "r2");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=3=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "r4");
		map.put(ACTION.get(2), "r4");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "r4");
		map.put(ACTION.get(5), "r4");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=4=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "S5");
		map.put(ACTION.get(1), "    ");
		map.put(ACTION.get(2), "    ");
		map.put(ACTION.get(3), "S4");
		map.put(ACTION.get(4), "    ");
		map.put(ACTION.get(5), "    ");
		map.put(GOTO.get(0), "8");
		map.put(GOTO.get(1), "2");
		map.put(GOTO.get(2), "3");
		STATUS.add(map);
		/*=5=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "r6");
		map.put(ACTION.get(2), "r6");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "r6");
		map.put(ACTION.get(5), "r6");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=6=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "S5");
		map.put(ACTION.get(1), "    ");
		map.put(ACTION.get(2), "    ");
		map.put(ACTION.get(3), "S4");
		map.put(ACTION.get(4), "    ");
		map.put(ACTION.get(5), "    ");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "9");
		map.put(GOTO.get(2), "3");
		STATUS.add(map);
		/*=7=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "S5");
		map.put(ACTION.get(1), "    ");
		map.put(ACTION.get(2), "    ");
		map.put(ACTION.get(3), "S4");
		map.put(ACTION.get(4), "    ");
		map.put(ACTION.get(5), "    ");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "10");
		STATUS.add(map);
		/*=8=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "S6");
		map.put(ACTION.get(2), "    ");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "S11");
		map.put(ACTION.get(5), "    ");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=9=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "r1");
		map.put(ACTION.get(2), "S7");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "r1");
		map.put(ACTION.get(5), "r1");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=10=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "r3");
		map.put(ACTION.get(2), "r3");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "r3");
		map.put(ACTION.get(5), "r3");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
		/*=11=*/
		map = new HashMap<String, String>();
		map.put(ACTION.get(0), "    ");
		map.put(ACTION.get(1), "r5");
		map.put(ACTION.get(2), "r5");
		map.put(ACTION.get(3), "    ");
		map.put(ACTION.get(4), "r5");
		map.put(ACTION.get(5), "r5");
		map.put(GOTO.get(0), "    ");
		map.put(GOTO.get(1), "    ");
		map.put(GOTO.get(2), "    ");
		STATUS.add(map);
	}
	
	/**
	 * 格式化移进状态，将字符串转换成整数
	 */
	public int formatMoveIn(String state) {
		int format = 0;
		switch (state) {
		case "S0":
			format = 0;
			break;
		case "S1":
			format = 1;
			break;
		case "S2":
			format = 2;
			break;
		case "S3":
			format = 3;
			break;
		case "S4":
			format = 4;
			break;
		case "S5":
			format = 5;
			break;
		case "S6":
			format = 6;
			break;
		case "S7":
			format = 7;
			break;
		case "S8":
			format = 8;
			break;
		case "S9":
			format = 9;
			break;
		case "S10":
			format = 10;
			break;
		case "S11":
			format = 11;
			break;
		default:
			format = -1;
			break;
		}
		return format;
	}
	
	/**
	 * 格式化规约状态，将字符串转换成整数
	 */
	public int formatMoveOutState(String state) {
		int format = 0;
		switch (state) {
		case "r1":
			format = 1;
			break;
		case "r2":
			format = 2;
			break;
		case "r3":
			format = 3;
			break;
		case "r4":
			format = 4;
			break;
		case "r5":
			format = 5;
			break;
		case "r6":
			format = 6;
			break;

		default:
			format = -1;
			break;
		}
		return format;
	}
	

	public List<String> getACTION() {
		return ACTION;
	}

	public List<String> getGOTO() {
		return GOTO;
	}

	public List<Map<String, String>> getSTATUS() {
		return STATUS;
	}

	public Map<String, String> getG() {
		return G;
	}

	public Map<String, String> getGRow() {
		return GRow;
	}
	
	public void printTable(){
		System.out.printf("%-10s","状态");
		System.out.printf("%-30s","ACTION");
		System.out.printf("%-10s","GOTO");
		System.out.println();
		System.out.printf("%-10s","");
		System.out.printf("%-5s","i");
		System.out.printf("%-7s","+");
		System.out.printf("%-8s","*");
		System.out.printf("%-8s","(");
		System.out.printf("%-7s",")");
		System.out.printf("%-7s","#");
		System.out.printf("%-7s","E");
		System.out.printf("%-6s","T");
		System.out.printf("%-4s","F");
		System.out.println();
		for (int i = 0; i < STATUS.size(); i++) {
			Map<String, String> map = STATUS.get(i);
			System.out.printf("%-7s",i);
			System.out.printf("%-7s",map.get("i"));
			System.out.printf("%-7s",map.get("+"));
			System.out.printf("%-7s",map.get("*"));
			System.out.printf("%-7s",map.get("("));
			System.out.printf("%-7s",map.get(")"));
			System.out.printf("%-10s",map.get("#"));
			System.out.printf("%-7s",map.get("E"));
			System.out.printf("%-7s",map.get("T"));
			System.out.printf("%-7s\n",map.get("F"));
		}
	}
}
