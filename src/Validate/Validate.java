package Validate;

import Alert.ShowError;
import application.Series;
import Query.Query;

public class Validate {
	public static String[] listOfStatusFilters = {"Watching", "Completed", "Dropped", "On-hold", "Plan to watch"};


	
	public static boolean isEpisodeExceed(Series selectedItem, int tempEp) {
		if(tempEp > selectedItem.getLastEpisode() || tempEp < 0) return true;
		return false;
	}
	
	public static String queryBuilder(String seriesType, String status) {
		return "select * from series where seriestype = " + "'"+ seriesType+"'" + " and status = " + "'"+status+"'";
	}
	
	public static String queryBuilder(String status) {
		return "select * from series where status = '" + status + "'";
	}
	

	public static boolean isAll() {
		if(Query.tempChoice == 0) {
			return true;
		}
		return false;
	}
}
