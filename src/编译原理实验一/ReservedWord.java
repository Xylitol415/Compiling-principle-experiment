package 编译原理实验一;

public class ReservedWord {
	String mnemonicCode;//助记符
	String innerCodeValue;//内码值
	public ReservedWord(String mnemonicCode,String innerCodeValue){
		this.mnemonicCode=mnemonicCode;
		this.innerCodeValue=innerCodeValue;
	}
	public String getMnemonicCode() {
		return mnemonicCode;
	}
	public void setMnemonicCode(String mnemonicCode) {
		this.mnemonicCode = mnemonicCode;
	}
	public String getInnerCodeValue() {
		return innerCodeValue;
	}
	public void setInnerCodeValue(String innerCodeValue) {
		this.innerCodeValue = innerCodeValue;
	}
	
}
