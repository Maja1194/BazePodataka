package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class PocetnaController {
	@FXML
	private AnchorPane PanePocetna;
	@FXML
	private ImageView ImageSlika;
	@FXML
	private Label LabelNaslov;
	@FXML
	private Button ButtonPregledProizvoda;
	@FXML
	private Button ButtonPrijava;
	@FXML
	private Button ButtonRegistracija;
	
	@FXML
    void OtvoriPrijavu(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Prijava.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Prijava");
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
	
    @FXML
    void OtvoriRegistraciju(ActionEvent event) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Registracija.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Registracija");
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    
    @FXML
    void OtvoriPrgledProizvoda(ActionEvent event) {

    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("KategorijeProizvoda.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("KategorijeProizvoda");
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
