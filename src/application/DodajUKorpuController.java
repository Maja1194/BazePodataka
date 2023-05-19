package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DodajUKorpuController{
	
	@FXML
    private Button ButtonNazadDodajUKorpu;

    @FXML
    private Button ButtonZavrsi;

    @FXML
    private ImageView ImageKolicina;

    @FXML
    private Label LabelKolicina;

    @FXML
    private AnchorPane PaneDodajUKorpu;

    @FXML
    private TextField TextFieldKolicina;
    
    public static HashMap<Integer, Integer> korpa = new HashMap<>();
	
	public static int sifra;

   


	
	@FXML
    void Nazad(ActionEvent event) {
		
		try {
			ButtonNazadDodajUKorpu.getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PregledProizvoda.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("PregledProizvoda");
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
	
    @FXML
    void ZavrsiDodavanje(ActionEvent event) {
    	int nova_kolicina=0;
    	
    	if(!TextFieldKolicina.getText().isEmpty()) {
    		
    		korpa.put(PregledProizvodaController.idProizvoda, Integer.valueOf(TextFieldKolicina.getText()));
    		
    		Connection c = null;
  		  	ResultSet rs = null;
  		  	java.sql.CallableStatement cs = null;
  		  
  		  try {
  			  c = ConnectionPool.getInstance().checkOut();
  			  cs = c.prepareCall("call ukupna_kolicina(?)");
  				cs.setInt(1, PregledProizvodaController.idProizvoda);
  				rs = cs.executeQuery();
  				rs.next();
  				nova_kolicina=rs.getInt(1)-Integer.valueOf(TextFieldKolicina.getText());
  				rs.close();
  				cs.close();
  				
  				cs=c.prepareCall("call azuriraj_proizvod(? , ?)");
  				cs.setInt(1, PregledProizvodaController.idProizvoda);
  				cs.setInt(2, nova_kolicina);
  				cs.executeUpdate();
  				cs.close();
  				
  				
  				
  		  } catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    	}
    	else {
    		Alert a = new Alert(AlertType.CONFIRMATION,"Nepotpuni podaci",ButtonType.OK);
			a.show();
    	}
    	
    	try {
			ButtonZavrsi.getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PregledProizvoda.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("PregledProizvoda");
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	 

    }

    }


