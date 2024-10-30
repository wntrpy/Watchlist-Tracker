package Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Controller.MainController;
import application.Main;
import application.Series;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import Validate.Validate;

public class Query extends MainController{
	public static int tempChoice = 0;
	public static int indexStatusBox = 0;
    private static MainController controller;
    
	public Query(MainController controller){
		  this.controller = controller;

	}
	
	public void insertSeriesToWaitlist() {
		
	}

	public static void insertSeries(String SeriesName, String Genre, String Episode, String LastEpisode, String Season, String Status, String SeriesType, String Type) throws SQLException{
		 String query = "INSERT INTO series (SeriesID, SeriesName, Genre, Episodes, LastEpisode, Seasons, Status, SeriesType, Type, DateWatched) "
                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, getDate())";
		PreparedStatement insertSeries = Main.local.getConnection().prepareStatement(query);
	
		System.out.println("AUTO ID: " +  generateSeriesID() 
		+ SeriesName + " " + Genre + " " + Episode + " " + LastEpisode + " " + Season
		+ " " + Status + " " + SeriesType + " " + Type
		
		);
		
			  insertSeries.setInt(1, generateSeriesID()); // Assuming you have a method to generate a unique SeriesID
	          insertSeries.setString(2, SeriesName);
	          insertSeries.setString(3, Genre);
	          insertSeries.setInt(4, Integer.parseInt(Episode));
	          insertSeries.setInt(5, Integer.parseInt(LastEpisode));
	          insertSeries.setInt(6, Integer.parseInt(Season));
	          insertSeries.setString(7, Status);
	          insertSeries.setString(8, SeriesType);
	          insertSeries.setString(9, Type);
	          insertSeries.executeUpdate();
		  

	}
	
	private static int generateSeriesID() throws SQLException {
		PreparedStatement insertSeries = Main.local.getConnection().prepareStatement("select count(*) as Count from series");
		ResultSet rs = insertSeries.executeQuery();
		rs.next();

		return rs.getInt("Count") + 1;
	}
	
	 public static void updateCurrentEpisodeInDatabase(String episode, String seriesID) throws SQLException {
	        PreparedStatement statement = Main.local.getConnection().prepareStatement("Update series set episodes = ?, DateWatched = getdate() where seriesid = ?");
	        statement.setString(1, episode);
	        statement.setString(2, seriesID);
	        statement.executeUpdate();
	 }
	 
	 
	 
		public static String getCurrEp(Series selectedSeries) throws SQLException {
			PreparedStatement  statement = Main.local.getConnection().prepareStatement("select * from series where seriesid = ?");
			statement.setLong(1, selectedSeries.getSeriesID());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			
			System.out.println("Latest ep: " + resultSet.getInt("Episodes"));
			
			return String.valueOf( resultSet.getInt("Episodes"));
		}
		
		
		public static void setItems(int choice) {
	        TableView<Series> tableView = controller.getSeriesListTableView();

	    	ObservableList<Series> list = Query.getSeriesList(listOfQueries[choice]);
    		controller.getSeriesListTableView().setItems(list);
    		tempChoice=choice;
		}
		
		public static void setItems(String querry) {	
			try {
		        TableView<Series> tableView = controller.getSeriesListTableView();
		        System.out.println("QUERY TEST: " + querry);
		          ObservableList<Series> list = Query.getSeriesList(querry);
		        controller.getSeriesListTableView().setItems(list);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}
		
		
		public static ObservableList<Series> getSeriesList(String query){
		    ObservableList<Series> list = FXCollections.observableArrayList();
		    try {
		    	Statement statement = Main.local.getConnection().createStatement();
		    	ResultSet rs = statement.executeQuery(query);
		    	
		    	  while (rs.next()){
		    		  	Series series = new Series(
		    			rs.getInt(colNameId), 
		    			rs.getString("SeriesName"), 
		    			rs.getString("Genre"), 
		    			rs.getInt("Episodes"),
		    			rs.getInt("Seasons"),
		    			rs.getString("Status"),
		    			rs.getString("SeriesType"),
		    			rs.getString(colNameDate),
		    			rs.getInt("LastEpisode"),
		    			rs.getString("Type")
		    			);
		    		list.add(series);
		    	  }
		    }
		    catch(Exception e) {
		    	e.printStackTrace();
		    }	
			return list;	
		}
		
		
		
		
		
		//pasa ung na build na query then change ung data sa table
		public static void changeTableBasedOnStatus(String queryy) {
			System.out.println("BOX QUERY: " + queryy);
			ObservableList<Series> list = getSeriesList(queryy);
			
			  TableView<Series> tableView = controller.getSeriesListTableView();

	    		controller.getSeriesListTableView().setItems(list);

		}
		
		
		//GETTERS PARA SA MAIN FRAME
		public static String getSeriesCount(String query) throws SQLException{
			Statement statement = Main.local.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			return rs.getString("Count");
		}
		
		
		
}
