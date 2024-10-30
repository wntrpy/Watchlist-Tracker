package Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Query.Query;

public class SeriesAddController implements Initializable{

    @FXML private TextField SeriesNameTF;
    @FXML private TextField GenreTF;
    @FXML private TextField EpisodeTF;
    @FXML private TextField LastEpisodeTF;
    @FXML private TextField SeasonTF;
    @FXML public ComboBox<String> StatusCB;
    @FXML private ComboBox<String> SeriesTypeCB;
    @FXML private ComboBox<String> TypeCB;
    @FXML private Button CancelBtn;
    @FXML private Button SaveBtn;
    @FXML private CheckBox yesCheckBox;
    @FXML private CheckBox noCheckBox;
    
    private String[] StatusChoices = {"Watching", "Completed", "Dropped", "Plan to watch", "On-hold"};
    private String[] SeriesTypeChoices = {"Anime", "KDrama", "Others"};
    private String[] TypeChoices = {"TV", "Movie"};
    private String yesOrNo = null;
    
	 public static javafx.stage.Stage overlayStage;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		StatusCB.getItems().addAll(StatusChoices);
		SeriesTypeCB.getItems().addAll(SeriesTypeChoices);
		TypeCB.getItems().addAll(TypeChoices);
		
	 yesCheckBox.setOnAction(event -> handleCheckBox());
	       noCheckBox.setOnAction(event -> handleCheckBox());
		}

	    @FXML
	    private void handleCheckBox() {
	        if (noCheckBox.isSelected()) {
	        	yesOrNo = "No";
	            yesCheckBox.setSelected(false);
	            System.out.println("No is selected");
	        } else {
	            System.out.println("No is deselected");
	        }
	        
	        if (yesCheckBox.isSelected()) {
	            noCheckBox.setSelected(false);
	            System.out.println("Yes is selected");
	            yesOrNo = "Yes";
	        } else {
	            System.out.println("Yes is deselected");
	        }
	    }
	
	@FXML
	void handleButtonAction(ActionEvent event) throws SQLException {
		if(event.getSource()==SaveBtn) {
			//get text from textfield
			//insert it in database
			getText();
			overlayStage.close();
		}
		else if(event.getSource()==CancelBtn) {
			overlayStage.close();
		}
	}
	
	private void getText() throws SQLException {		
		if(yesOrNo.equals("Yes")) {
			//iinsert to sa waitlist table
		}
		else if(yesOrNo.equals("No")) {
			//iinsert sa series table\
			System.out.println("INSERT SA SERIES");
			Query.insertSeries(
					SeriesNameTF.getText(),
					GenreTF.getText(),
					EpisodeTF.getText(),
					LastEpisodeTF.getText(),
					SeasonTF.getText(),
					 StatusCB.getValue(),
					 SeriesTypeCB.getValue(),
					 TypeCB.getValue()
					);
		}
	}

	private boolean isComboBoxNull(){
		if(StatusCB.getValue() == null ||  SeriesTypeCB.getValue() == null || TypeCB.getValue()==null) {
			return true;
		}
		return false;
	}
	
    
}
