package ����ԭ��ʵ��һ;

public class ReservedWord {
	String mnemonicCode;//���Ƿ�
	String innerCodeValue;//����ֵ
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
