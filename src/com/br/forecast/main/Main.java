package com.br.forecast.main;

import com.br.forecast.core.ForecastTable;
import com.br.forecast.user_interface.ConsoleInterface;

public class Main {

	public static void main(String[] args) {

		ForecastTable table = ForecastTable.getInstance();
		//ForecastTable.Config.sazional = 3;
		//ForecastTable.Config.alpha 	  = 0.3f;
		//ForecastTable.Config.beta	  =	0.3f;
		//ForecastTable.Config.gama     = 0.3f;
		ConsoleInterface.loadConfig();
		table.start();
		ConsoleInterface.showResult(table);
	}

}
