package com.br.forecast.filereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.br.forecast.core.ForecastUnit;

public class CSVFileHandle {
	private static CSVFileHandle instance;
	private static final DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
	private File file;
	private File result_file;
	public static CSVFileHandle getInstance(String file_path){
		instance = (instance == null)?new CSVFileHandle(file_path):instance;
		return instance;
	}
	public CSVFileHandle(String file_path){
		file = new File(file_path);
	}
	public void writeResult(StringBuffer sb) throws IOException{
		result_file =  new File("result_"+df.format(new Date())+".csv");
		if(!result_file.exists()){result_file.createNewFile();}
		FileWriter writer = null;
		try {
			writer = new FileWriter(result_file);
			writer.write("sep=;\n"+sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			writer.flush();
			writer.close();
			result_file = null;
		}
		
	}
	
	public void loadTable(UnitHandler handler) throws IOException{
		FileInputStream fis = null;
		Scanner s = null;
		try {
			fis = new FileInputStream(file);
			s = new Scanner(fis);
			int index = 0;
			while(s.hasNextLine()){
				String[] values = s.nextLine().split(";");
				handler.handleUnit(
						new ForecastUnit(
								++index
								, Float.parseFloat(values[0].replace(",","."))
								)
						);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			s.close();
			fis.close();
		}
	}
	
	public interface UnitHandler{
		void handleUnit(ForecastUnit unit);
	}
}