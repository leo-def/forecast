package com.br.forecast.user_interface;

import java.util.Scanner;

import com.br.forecast.core.ForecastTable;

public class ConsoleInterface {
	public static final Scanner s = new Scanner(System.in);
	public static void loadConfig(){
		
		//
		System.out.println("Informe o indice alfa"
				+ " (Utilize '.' no lugar de ',')"
				+ "(Valor padrao: 3)");
		try{
			ForecastTable.Config.alpha = s.nextFloat();
		}catch(RuntimeException ex){}

		//
		s.nextLine();
		System.out.println("Informe o indice beta"
			+ " (Utilize '.' no lugar de ',')"
			+ "(Valor padrao: 3)");
		try{
			ForecastTable.Config.beta = s.nextFloat();
		}catch(RuntimeException ex){}
		
		//
		s.nextLine();
		System.out.println("Informe o indice gama"
				+ " (Utilize '.' no lugar de ',')"
				+ "(Valor padrao: 3)");
		try{
			ForecastTable.Config.gama = s.nextFloat();
		}catch(RuntimeException ex){}
		
		//
		s.nextLine();
		System.out.println("Informe o indice sazional \n"
				+ "(Valor padrao: 3)");
		try{
			ForecastTable.Config.sazional = s.nextInt();
		}catch(RuntimeException ex){}
		
		//
		s.nextLine();
		System.out.println("Informe o arquivo que você deseja ler \n "
				+ "(pressione enter para ler 'forecast.txt')");
		try{
			String file = s.nextLine();
			if(!file.trim().replace("/n","").replace("/r","").isEmpty()){
				ForecastTable.Config.file = file;
			}
		}catch(RuntimeException ex){}
		
	}
	public static void showResult(ForecastTable table){
		System.out.println("Resultado Gerado:");
		System.out.println(table.getStream());
	}
}
