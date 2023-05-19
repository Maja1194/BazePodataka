package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class KorpaController implements Initializable {
	@FXML
	private AnchorPane PaneKorpa;
	@FXML
	private Button ButtonZavrsiKupovinu;
	@FXML
	private ListView<String> ListViewKorpa;
	
	 @FXML
	    void ZavrsiKupovinu(ActionEvent event) {
		 
		 try {
			    ButtonZavrsiKupovinu.getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Narudzba.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Narudzba");
				stage.setResizable(true);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
			Connection c = null;
		  	ResultSet rs = null;
		  	java.sql.CallableStatement cs = null;
		
		for(Integer i : DodajUKorpuController.korpa.keySet()) {
			 try {
	  			  c = ConnectionPool.getInstance().checkOut();
	  			  cs = c.prepareCall("call naziv_proizvoda(?)");
	  				cs.setInt(1, i);
	  				rs = cs.executeQuery();
	  				rs.next();
	  				String naziv=rs.getString(1);
	  				rs.close();
	  				cs.close();
	  				
	  			    ListViewKorpa.getItems().add(naziv + " - " + DodajUKorpuController.korpa.get(i));
	  				
	  		  } catch (SQLException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
		}
		
	}

	    }


