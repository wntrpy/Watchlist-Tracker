package Alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowError {
	 static Alert alert = new Alert(null);

      public static void errorAlert(String errorMessage) {
    	  alert = new Alert(AlertType.ERROR);    
    	  alert.setTitle("Error!");
          alert.setHeaderText(errorMessage);
          alert.setContentText("Please check the details and try again.");
          alert.showAndWait();
      }
      
    
}
