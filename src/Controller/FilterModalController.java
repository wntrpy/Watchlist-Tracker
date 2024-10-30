package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Query.Query;
import Validate.Validate;

public class FilterModalController extends MainController{
    @FXML private Button WatchingBtn;
    @FXML private Button CompletedBtn;
    @FXML private Button DroppedBtn;
    @FXML private Button OnHoldBtn;
    @FXML private Button PlanToWatchBtn;

    
    public static Stage overlayStage;
    boolean close = false;
    boolean allOrNot = false;
    
    @FXML
    private void handleButtonAction(ActionEvent e) {

    	close = false;
    	if(e.getSource()==WatchingBtn) {
        	if(Query.tempChoice == 0) { //all
        		Query.setItems(Validate.queryBuilder("Watching"));
        	}
        	else { // not all
        		System.out.println("QUERY: " + Validate.queryBuilder(getSeriesType, "Watching"));
        		Query.setItems(Validate.queryBuilder(getSeriesType, "Watching"));
        		close=true;
        	} 
    	}
    	else if(e.getSource()==CompletedBtn) {
    		
    		close=true;
    	}
    	else if(e.getSource()==DroppedBtn) {
    		
    		close=true;
    	}
    	else if(e.getSource()==OnHoldBtn) {
    		
    		close=true;
    	}
    	else if(e.getSource()==PlanToWatchBtn) {
    		
    		close=true;
    	}
    	
    	if(close) {
    		overlayStage.close();
    	}
    	allOrNot = false;
    }
    
    
    
}
