package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import application.Main;
import application.Series;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Query.Query;
import Validate.Validate;
import Alert.ShowError;
import application.ManageTableView;


public class MainController implements Initializable{
	//COUNT
    @FXML private Label AllCountLbl;
    @FXML private Label AnimeCountLbl;
    @FXML private Label KDramaCountLbl;
    @FXML private Label OthersCountLbl;
    @FXML private Label WaitinCountLbl;

	
    @FXML private Button AddSeriesBtn;
    @FXML private Button CloseBtn;
    @FXML private Button MinimizeBtn;
    @FXML private Button UpdateButton;
    @FXML private ComboBox<String> FilterStatusBox;
    @FXML private TextField SearchBarTF;

    
    //NAVS
    @FXML private Button AllBtn;
    @FXML private Button AnimeBtn;
    @FXML private Button KDramaBtn;
    @FXML private Button OthersBtn;
    @FXML private Button WaitingBtn;

    //TABLE VIEWS
    @FXML private  TableView<Series> SeriesListTableView;
    @FXML public  TableColumn<Series, String> TitleCol;
    @FXML public  TableColumn<Series, String> GenreCol;
    @FXML public  TableColumn<Series, Integer> EpisodeCol;
    @FXML public  TableColumn<Series, Integer> LastEpCol;
    @FXML public  TableColumn<Series, Integer> SeasonCol;
    @FXML public  TableColumn<Series, String> StatusCol;
    @FXML public  TableColumn<Series, String> TypeCol;
    @FXML public  TableColumn<Series, String> DateWatchedCol;
    @FXML public  TableColumn<Series, Void> ButtonCol;
    
    private String query = "SELECT * from series where status != 'Plan to Watch' order by DateWatched desc";
    public static String colNameDate = "DateWatched";
    public static String colNameId = "SeriesID";
	boolean isWaitingClicked = false;
	public static String[] listOfQueries = {
			"SELECT * from series where status != 'Plan to Watch' order by DateWatched desc",
			"select * from series where seriestype = 'Anime' order by DateWatched desc",
			"select * from series where seriestype = 'KDrama' order by DateWatched desc",
			"select * from series where seriestype = 'Others' order by DateWatched desc",
			"select * from Waitlist_New_Season"
	};
	private String[] FilterStatusItems = {"Watching", "Completed", "Dropped", "On-Hold", "Plan to Watch"};
	
    private static String[] listOfSeriesType = {"All","Anime", "KDrama", "Others"};
    
    public static String getSeriesType = null;
    private int tempSType = 0;

	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showCountLabels(); //show yung nirereturn na count based sa Navs
		FilterStatusBox.getItems().addAll(FilterStatusItems);
		
		ManageTableView helper = new ManageTableView(this);
		helper.showSeries(query);
		
		Query q = new Query(this);
		
	}

	
    @FXML
    private void handleButtonAction(ActionEvent e) throws Exception{
    	ObservableList<Series> list = null;
    	isWaitingClicked = false;
    	if(!isWaitingClicked) {
    		DateWatchedCol.setText("Date Watched");
    		colNameDate = "DateWatched";
    		colNameId = "SeriesID";
    	}
    	
    	if(e.getSource()==AllBtn) {//0
    		Query.setItems(0);
    		getSeriesType = "All";
    	}
    	else if(e.getSource()==AnimeBtn) { //1
    		Query.setItems(1);
    		getSeriesType = "Anime";
    		tempSType = 1;
    	}
    	else if(e.getSource()==KDramaBtn) { //2
    		Query.setItems(2);
    		getSeriesType = "KDrama";
    		tempSType = 2;
    	}
    	else if(e.getSource()==OthersBtn) {//3
    		Query.setItems(3);
    		getSeriesType = "Others";
    		tempSType = 3;
    	}
    	else if(e.getSource()==WaitingBtn) {//4
    		isWaitingClicked= true;
    		colNameDate = "ReleaseDate";
    		colNameId = "waitlistID";
    		DateWatchedCol.setText("Release Date");
    		Query.setItems(4);
    	}
    	else if(e.getSource()==AddSeriesBtn){
    		System.out.println("ADDSERIESOPEN!");
    		openSeriesAddForm(Main.stage);
    	}
    	else if(e.getSource()==CloseBtn) {
    		System.out.println("CLOSE MAIN STAGE");
    		Main.stage.close();
    	}
    	else if(e.getSource()==MinimizeBtn) {
    		Main.stage.setIconified(true);
    	}
    	else if(e.getSource() == UpdateButton) {
    		
    	}
    }
    
   

	private void showCountLabels() {
		try {
			AllCountLbl.setText(Query.getSeriesCount("select count(*) as 'Count' from series where status != 'Plan to Watch'"));
			AnimeCountLbl.setText(Query.getSeriesCount("select count(*) as 'Count' from series where seriestype = 'Anime' and status != 'Plan to Watch'"));
			KDramaCountLbl.setText(Query.getSeriesCount("select count(*) as 'Count' from series where seriestype = 'Kdrama' and status != 'Plan to Watch'"));
			OthersCountLbl.setText(Query.getSeriesCount("select count(*) as 'Count' from series where seriestype = 'Others' and status != 'Plan to Watch'"));
			WaitinCountLbl.setText(Query.getSeriesCount("select count(*) as 'Count' from Waitlist_New_Season"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@FXML
	private void openSeriesAddForm(Stage stage) {
		 try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SeriesAddForm.fxml"));
	            Stage overlayStage = new Stage();
	            SeriesAddController.overlayStage = overlayStage;

	           // SeriesAddController.setEmployeesController(this); //pass ung instance ng employees controller sa employees add, para ma refresh ko yung table view pag cinlick yung cloe            
	            Parent overlayContent = loader.load();

	            overlayStage.initModality(Modality.APPLICATION_MODAL);
	            overlayStage.initOwner(Main.stage);
	            overlayStage.initStyle(StageStyle.UNDECORATED);
	            overlayStage.setScene(new Scene(overlayContent));
	            overlayStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	
	
	//GETTER FOR NODES
	public TableView<Series> getSeriesListTableView() {
        return SeriesListTableView;
    }
	
	
	//COMBO BOX FUNCTIONS
	@FXML
	public void handleComboBox() {
	    String selectedStatus = FilterStatusBox.getValue(); //pasa ung clicked value sa var
	    
	    if (Validate.isAll()) { //if all
	        Query.setItems(Validate.queryBuilder(selectedStatus));
	    } else { //if may series type
	        String seriesType = getSeriesType(tempSType);
	        Query.setItems(Validate.queryBuilder(seriesType, selectedStatus));
	    }
	}

	private String getSeriesType(int tempSType) {
	    switch (tempSType) {
	        case 1:
	            return "Anime";
	        case 2:
	            return "KDrama";
	        case 3:
	            return "Others";
	        default:
	            return "";
	    }
	}

	
	
	
	
}
