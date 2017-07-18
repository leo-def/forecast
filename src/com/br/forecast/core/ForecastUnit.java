package com.br.forecast.core;


public class ForecastUnit {
	public ForecastUnit(int index, float val) {
		this.index = index;
		this.val = val;
	}
	public ForecastUnit(int index, float val, String label){
		this(index,val);
		this.label =label;
	}
	public ForecastUnit(){
		
	}
	int index;
	String label;
	float val;
	float L;
	float b;
	float S;
	float F;
	float e;
	float e_per_cent;
	
	public StringBuffer getStream(){
		StringBuffer sb = new StringBuffer();
		sb.append(columnString(index));
		sb.append(columnString(label));
		sb.append(columnString(val));
		sb.append(columnString(L));
		sb.append(columnString(b));
		sb.append(columnString(S));
		sb.append(columnString(F));
		sb.append(columnString(e));
		sb.append(columnPerCentString(e_per_cent));
		sb.append("\n");
		return sb;
	}
	public String columnPerCentString(float s){
		if(s == 0){return columnString("");}
		return columnString(String.format("%.1f",s*100).toString()+"%");
	}
	public String columnString(int s){
		if(s == 0){return columnString("");}
		return columnString(Integer.toString(s));
	}
	public String columnString(float number){
		if(number == 0){return columnString("");}
		return columnString(String.format("%.3f",number).toString());
	}
	public String columnString(String s_number){
		if(s_number == null){return "          ;";}
		
		for(int i = s_number.length(); i<=10; i++){
			if(i%2 == 0){
				s_number = " "+s_number;
			}else{
				s_number = s_number+" ";
			}
		}
		return s_number+";";
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public float getVal() {
		return val;
	}
	public void setVal(float val) {
		this.val = val;
	}
	public float getL() {
		return L;
	}
	public void setL(float l) {
		L = l;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	public float getS() {
		return S;
	}
	public void setS(float s) {
		S = s;
	}
	public float getF() {
		return F;
	}
	public void setF(float f) {
		F = f;
	}
	public float getE() {
		return e;
	}
	public void setE(float e) {
		this.e = e;
	}
	public float getE_per_cent() {
		return e_per_cent;
	}
	public void setE_per_cent(float e_per_cent) {
		this.e_per_cent = e_per_cent;
	}
}
