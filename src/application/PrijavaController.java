package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//import com.mysql.cj.jdbc.CallableStatement;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class PrijavaController {
	@FXML
	private AnchorPane PanePrijava;
	@FXML
	private ImageView ImagePrijava;
	@FXML
	private Label LabelMail;
	@FXML
	private TextField TextFieldMail;
	@FXML
	private Label LabelLozinka;
	@FXML
	private TextField TextFieldLozinka;
	@FXML
	private Button ButtonPotvrda;
	
	public static int idKupca=0;
	
	  @FXML
	    void Potvrda(ActionEvent event) throws SQLException {
		  
		  Connection c = null;
		  java.sql.CallableStatement cs = null;
			try {
				
				c = ConnectionPool.getInstance().checkOut();
				
				if(!TextFieldMail.getText().equals("") && !TextFieldLozinka.getText().equals("")) {
					cs = c.prepareCall("call provjeri_korisnika(?, ?, ?)");
					cs.setString(1, TextFieldMail.getText());
					cs.setString(2, TextFieldLozinka.getText());
					cs.registerOutParameter(3, java.sql.Types.INTEGER);
					cs.executeUpdate();
					
					if((Integer) cs.getObject(3) != 0) {
						idKupca=(Integer) cs.getObject(3);
						ButtonPotvrda.getScene().getWindow().hide();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("KategorijeProizvoda.fxml"));
						Parent root = loader.load();
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Proizvodi");
						stage.setResizable(false);
						stage.show();
					}else{
						Alert a = new Alert(AlertType.CONFIRMATION,"Niste unijeli ispravne podatke",ButtonType.OK);
						a.show();
					}
				}else {
					Alert a = new Alert(AlertType.CONFIRMATION,"Nepotpuni podaci",ButtonType.OK);
					a.show();
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }

}
