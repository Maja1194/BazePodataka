package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class PregledProizvodaController implements Initializable{
	@FXML
    private Button ButtonDodajUKorpu;

    @FXML
    private Button ButtonDodajUKorpu1;

    @FXML
    private Button ButtonDodajUKorpu3;

    @FXML
    private Text label1;

    @FXML
    private Text label2;

    @FXML
    private Text label3;

    @FXML
    private Text label4;

    @FXML
    private Button ButtonKorpa;

    @FXML
    private Button ButtonNazadPP;

    @FXML
    private Button ButtonUKorpu2;

    @FXML
    private ImageView ImageViewKorpa;

    @FXML
    private AnchorPane PanePregled;
    
    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;
    
    public ArrayList<ImageView> slike = new ArrayList<>();
    
    public ArrayList<Text> labele = new ArrayList<>();
    
    public static int idProizvoda = 0;
    
  
	
    @FXML
    void NazadNaKategorije(ActionEvent event) {
	try {
		KategorijeProizvodaController.sifre.clear();
		image1.imageProperty().set(null);
		image2.imageProperty().set(null);
		image3.imageProperty().set(null);
		image4.imageProperty().set(null);
		label1.textProperty().set(null);
		label2.textProperty().set(null);
		label3.textProperty().set(null);
		label4.textProperty().set(null);
		ButtonNazadPP.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("KategorijeProizvoda.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("KategorijeProizvoda");
		stage.setResizable(true);
		stage.show();
	} 
	catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    @FXML
    void PrikaziKorpu(ActionEvent event) {
    	try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Korpa.fxml"));
    		Parent root = loader.load();
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.setTitle("Korpa");
    		stage.setResizable(true);
    		stage.show();
    	} 
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void postaviProizvod(ImageView slika, String s, Text l, String info) {
		try{ 
			
			slika.setImage(new Image(new FileInputStream(s)));
			l.setText(info);
	    	
	    }catch(FileNotFoundException e) {
	    	e.printStackTrace();
	    }
	}
    
    
		

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		slike.add(image1);
		slike.add(image2);
		slike.add(image3);
		slike.add(image4);
		labele.add(label1);
		labele.add(label2);
		labele.add(label3);
		labele.add(label4);	
			Connection c = null;
			  ResultSet rs = null;
			  java.sql.CallableStatement cs = null;
			  
			  try {
				  c = ConnectionPool.getInstance().checkOut();
				  for(int i=0; i< KategorijeProizvodaController.sifre.size(); i++) {
					cs = c.prepareCall("call dohvati_proizvod(?)");
					cs.setInt(1,KategorijeProizvodaController.sifre.get(i));		
					rs = cs.executeQuery();
					if(rs.next()) {
						String s =rs.getString(2) + "\nKolicina: "+ rs.getInt(8) +"\nCijena: "+ rs.getDouble(9);
						postaviProizvod(slike.get(i),rs.getString(3),labele.get(i),s);
						
					}
					rs.close();
					cs.close();
				  }
			  }catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		if(PrijavaController.idKupca==0) {
			ButtonDodajUKorpu.setVisible(false);
			ButtonKorpa.setVisible(false);
			ButtonDodajUKorpu1.setVisible(false);
			ButtonDodajUKorpu3.setVisible(false);
			ButtonUKorpu2.setVisible(false);
		}
		
		  
 }
	
	  @FXML
	    void dodajUKorpu1(ActionEvent event) {
		  try {
	    		idProizvoda = KategorijeProizvodaController.sifre.get(0);
	    		ButtonDodajUKorpu1.getScene().getWindow().hide();
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("DodajUKorpu.fxml"));
	    		Parent root = loader.load();
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setScene(scene);
	    		stage.setTitle("Dodaj u korpu");
	    		stage.setResizable(true);
	    		stage.show();
	    	} 
	    	catch (IOException e) {
	    		e.printStackTrace();
	    	}

	    }

	    @FXML
	    void dodajUKorpu2(ActionEvent event) {
	    	try {
	    		idProizvoda = KategorijeProizvodaController.sifre.get(1);
	    		ButtonUKorpu2.getScene().getWindow().hide();
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("DodajUKorpu.fxml"));
	    		Parent root = loader.load();
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setScene(scene);
	    		stage.setTitle("Dodaj u korpu");
	    		stage.setResizable(true);
	    		stage.show();
	    	} 
	    	catch (IOException e) {
	    		e.printStackTrace();
	    	}

	    }

	    @FXML
	    void dodajUKorpu3(ActionEvent event) {
	    	try {
	    		idProizvoda = KategorijeProizvodaController.sifre.get(2);
	    		ButtonDodajUKorpu3.getScene().getWindow().hide();
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("DodajUKorpu.fxml"));
	    		Parent root = loader.load();
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setScene(scene);
	    		stage.setTitle("Korpa");
	    		stage.setResizable(true);
	    		stage.show();
	    	} 
	    	catch (IOException e) {
	    		e.printStackTrace();
	    	}

	    }

	    @FXML
	    void dodajUKorpu4(ActionEvent event) {
	    	try {
	    		idProizvoda = KategorijeProizvodaController.sifre.get(3);
	    		ButtonDodajUKorpu.getScene().getWindow().hide();
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("DodajUKorpu.fxml"));
	    		Parent root = loader.load();
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setScene(scene);
	    		stage.setTitle("Dodaj u korpu");
	    		stage.setResizable(true);
	    		stage.show();
	    	} 
	    	catch (IOException e) {
	    		e.printStackTrace();
	    	}

	    }
	
	
	
	
}
    



