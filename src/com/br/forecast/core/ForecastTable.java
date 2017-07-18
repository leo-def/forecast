package com.br.forecast.core;

import java.io.IOException;
import java.util.HashMap;

import com.br.forecast.filereader.CSVFileHandle;

public class ForecastTable {
	final HashMap<Integer,ForecastUnit> units = new HashMap<Integer, ForecastUnit>();
	static ForecastTable instance = new ForecastTable();
	CSVFileHandle handle;
	private ForecastTable(){
		handle = CSVFileHandle.getInstance(Config.file);
		units.clear();
	}
	
	public static ForecastTable getInstance(){
		return instance;
	}
	public void loadFile(){
			try {
				handle.loadTable(new CSVFileHandle.UnitHandler() {
					
					@Override
					public void handleUnit(ForecastUnit unit) {
						units.put(unit.index, unit);
						
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void printResult(){
		try {
			handle.writeResult(this.getStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void start(){
		loadFile();config();run();printResult();
	}
	
	public void config(){
		HashMap<Integer,ForecastUnit> first_period = getPeriod(1);
		
		float average = average(first_period);
		int max = 0;
		for(Integer key: first_period.keySet()){
			ForecastUnit unit = first_period.get(key);
			unit.S = unit.val/average;
			if(max < key){max = key;}
		}
		ForecastUnit last = first_period.get(max);
		last.b = extremeAverage();
		last.L = average;
	}
	
	public void run(){
		int start_index = periodIndex(2);
		for(int i = start_index; i <= units.size(); i++){
			ForecastUnit unit = units.get(i);
			ForecastUnit last_unit = units.get(i-1);
			ForecastUnit prev_period_unit = units.get(i-Config.sazional);

			// D    E   F   G   H   I   J			
			//val	L	b	S	F	e	e%
						
			//L = $M$3*(D6/G3)+(1-$M$3)*(E5+F5)
			unit.L = (Config.alpha 
					* (unit.val/prev_period_unit.S)
					+ (1-Config.alpha)
					* (last_unit.L + last_unit.b)
					);
			
			//b = $M$4*(E6-E5)+(1-$M$4)*F5
			unit.b = (Config.beta
					* (unit.L - last_unit.L)
					+ (1-Config.beta)
					* last_unit.b
					);
			
			//S = $M$5*(D6/E6)+(1-$M$5)*(G3)
			unit.S = (Config.gama 
					* (unit.val/unit.L)
					+ (1-Config.beta)
					* (prev_period_unit.S)
					);
			
			//F = (E6+1*F6)*G3
			unit.F = ((unit.L+1*unit.b)
					* prev_period_unit.S
					);
			//e = ABS(D6-H6)
			unit.e = Math.abs(unit.val-unit.F);
			
			//e%= ABS(I6/D6)			
			unit.e_per_cent = Math.abs(unit.e/unit.val);
		}
	}
	
	public float extremeAverage(){
		float average = 0;
		for(int i = 1; i <= Config.sazional;i++){
			if(units.containsKey(i+Config.sazional) && units.containsKey(i)){
				ForecastUnit unit1 = units.get(i);
				ForecastUnit unit2 = units.get(i+Config.sazional);
				average += (unit2.val - unit1.val)/Config.sazional;
			}
		}
		return average/Config.sazional;
	}
	
	public float average(HashMap<Integer,ForecastUnit> period){
		float sum = 0;
		for(Integer key: period.keySet()){
			sum += period.get(key).val;
		}
		return sum/period.size();
	}
	public int periodIndex(int n){
		return (n*Config.sazional)-Config.sazional+1;
	}
	
	public HashMap<Integer,ForecastUnit> getPeriod(int n){
		HashMap<Integer, ForecastUnit> period = new HashMap<Integer,ForecastUnit>();
		int start_index = periodIndex(n);
		for(int i = start_index; i < start_index+Config.sazional; i++){
			period.put(i,units.get(i));
		}
		
		return period;
	}
	
	public StringBuffer getStream(){
		StringBuffer sb = new StringBuffer();
		sb.append(
				 "    index  "
				+";   name   "
				+";     val   "
				+";      L    "
				+";      b    "
				+";      S    "
				+";      F    "
				+";      e    "
				+";     e%    "
				+";\n"
				);
		for(Integer key : units.keySet()){
			sb.append(units.get(key).getStream());
		}
		return sb;
	}
	
	public static class Config{
		public static String file = "forecast.txt";
		public static int sazional = 3;
		public static float alpha = 0.3f;
		public static float beta = 0.3f;
		public static float gama = 0.3f;
	}
}
