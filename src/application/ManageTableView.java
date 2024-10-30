package application;

import java.sql.SQLException;

import Alert.ShowError;
import Controller.MainController;
import Query.Query;
import Validate.Validate;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ManageTableView extends MainController{
	static int trap = 0;
    private MainController controller;


	public ManageTableView(MainController controller) {
		  this.controller = controller;
	}
	
	 public void showSeries(String query) {
	        TableView<Series> tableView = controller.getSeriesListTableView();

		 
	        ObservableList<Series> list = Query.getSeriesList(query);
	
	        try {
	        controller.TitleCol.setCellValueFactory(new PropertyValueFactory<Series, String>("SeriesName"));
	        controller.GenreCol.setCellValueFactory(new PropertyValueFactory<Series, String>("Genre"));
	        controller.EpisodeCol.setCellValueFactory(new PropertyValueFactory<Series, Integer>("Episodes"));
	        controller.LastEpCol.setCellValueFactory(new PropertyValueFactory<Series, Integer>("LastEpisode"));
	        controller.SeasonCol.setCellValueFactory(new PropertyValueFactory<Series, Integer>("Seasons"));
	        controller.StatusCol.setCellValueFactory(new PropertyValueFactory<Series, String>("Status"));
	        controller.TypeCol.setCellValueFactory(new PropertyValueFactory<Series, String>("Type"));
	        controller.DateWatchedCol.setCellValueFactory(new PropertyValueFactory<Series, String>("DateWatched"));

	        controller.ButtonCol.setCellFactory(param -> new TableCell<>() {
	            private final Button addButton = new Button("+");
	            private final Button minusButton = new Button("-");
	            private final HBox container = new HBox(5, addButton, minusButton);

	            {
	                addButton.setOnAction(event -> {
	                    Series selectedSeries = getTableView().getItems().get(getIndex());
	                    if (selectedSeries != null) {
	                    	try {
								updateEpisode(selectedSeries, 0);
								System.out.println("ADD");
							} catch (SQLException e) {
								e.printStackTrace();
							}
	                    }
	                });

	                minusButton.setOnAction(event -> {
	                    Series selectedSeries = getTableView().getItems().get(getIndex());
	                    if (selectedSeries != null) {
	                    	try {
							 	updateEpisode(selectedSeries, 1);
								System.out.println("MINUS");

							} catch (SQLException e) {
								e.printStackTrace();
							}
	                    }
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty) {
	                    setGraphic(null);
	                } else {
	                    setGraphic(container);
	                }
	            }
	        });
	        tableView.setItems(list);

	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        	ShowError.errorAlert("Error in loading data in table :<");
	        }

	    }
	 
	 
	 
	 public void updateEpisode(Series selectedItem, int addOrMinus) throws SQLException {
			int tempEp = 0;
			
			if(addOrMinus == 0) { //add
				if(trap == 0) {
					tempEp = Integer.valueOf(Query.getCurrEp(selectedItem)) + 1;
				}
				
				if(Validate.isEpisodeExceed(selectedItem, tempEp)) {//true
					trap = 1;
					tempEp = selectedItem.getLastEpisode();
					ShowError.errorAlert("Can't exceed last episode dumb ahh!");
				}

			}
			else if(addOrMinus == 1) { //minus
				if(trap == 0) {
				tempEp = Integer.valueOf(Query.getCurrEp(selectedItem)) - 1;
				}
				
				if(Validate.isEpisodeExceed(selectedItem, tempEp)) {
					trap = 1;
					tempEp = 0;
					ShowError.errorAlert("There is no episode -1 dumb lookin ahh!");
				}

			}
			
			Query.updateCurrentEpisodeInDatabase(String.valueOf(tempEp), String.valueOf(selectedItem.getSeriesID()));
			showSeries(listOfQueries[Query.tempChoice]); //refresher
			trap = 0;
		}

}
