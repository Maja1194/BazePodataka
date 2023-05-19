package application;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class NarudzbaController {
	@FXML
	private TextField TextFieldIme;
	@FXML
	private TextField TextFieldGrad;
	@FXML
	private TextField TextFieldAdresa;
    @FXML
    private TextField TextFieldbr;
	@FXML
	private MenuButton ButtonPlacanje;
	@FXML
	private MenuButton ButtonPosta;
	@FXML
	private Button ButtonPotvrdi;
	@FXML
	private Button ButtonOtkazi;
	@FXML
	private MenuItem MenuItemE;
	@FXML
	private MenuItem MenuItemP;
	@FXML
	private MenuItem MenuItemX;
	@FXML
	private ImageView ImageNarudzba;
	
	String posta;
	
    @FXML
    void meniItemE(ActionEvent event) {
    	posta="EuroExpress";

    }

    @FXML
    void menuItemP(ActionEvent event) {
    	posta="PosteSrpske";

    }

    @FXML
    void menuItemX(ActionEvent event) {
    	posta="XExpress";

    }
	
	@FXML
    void Otkazi(ActionEvent event) {
		 try {
			    ButtonOtkazi.getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Korpa.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Korpa");
				stage.setResizable(true);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }
    

    @FXML
    void Potvrdi(ActionEvent event) throws IOException {
    	
    	PreparedStatement ps = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection c = null;
		int pomBroj=0;
		int pomAdresa=0;
		int pomDostava=0;
		int pomNarudzbe=0;
		
		if(!TextFieldIme.getText().equals("") && !TextFieldAdresa.getText().equals("") && !TextFieldGrad.getText().equals("")) {
			try {
				c = ConnectionPool.getInstance().checkOut();
				cs = c.prepareCall("call postanski_broj_grad(?)");
				cs.setString(1, TextFieldGrad.getText());
				rs = cs.executeQuery();
					if(!rs.next()) {
						
						Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("U ovaj grad nije moguce isporuciti proizvod!");
						alert.showAndWait();
						return;
					}
						pomBroj=rs.getInt(1);
						rs.close();
						cs.close();
						
				
				ps = c.prepareStatement("insert into adresa(ulica, broj, Grad_postanskiBroj) values (?, ?, ?)");
				ps.setString(1, TextFieldAdresa.getText());
				ps.setInt(2, Integer.valueOf(TextFieldbr.getText()));
				ps.setInt(3, pomBroj);
				ps.executeUpdate();
				ps.close();
				
				cs = c.prepareCall("call dohvati_adresu(?)");
				cs.setString(1, TextFieldAdresa.getText());
				rs = cs.executeQuery();
				if(rs.next())
					pomAdresa=rs.getInt(1);
				rs.close();
				cs.close();
				
				cs = c.prepareCall("call dohvati_dostavu(?)");
				cs.setString(1,posta);
				rs = cs.executeQuery();
				if(rs.next())
					pomDostava=rs.getInt(1);
				rs.close();
				cs.close();
				
				ps = c.prepareStatement("insert into narudzba(datum, Kupac_idKupca, Adresa_idAdresa, StanjeNaruzbe_id, Dostava_idDostava, NačinPlaćanja_idplaćanja, ukupnaCijena) values (?, ?, ?, ?, ?, ?, ?)");
				ps.setDate(1, Date.valueOf(LocalDate.now()));
				ps.setInt(2, PrijavaController.idKupca);
				ps.setInt(3, pomAdresa);
				ps.setInt(4, 2);
				ps.setInt(5, pomDostava);
				ps.setInt(6,1);
				ps.setInt(7,0);
				ps.executeUpdate();
				ps.close();
				
				
				
				cs = c.prepareCall("call dohvati_narudzbu(?, ?, ?)");
				cs.setInt(1, PrijavaController.idKupca);
				cs.setDate(2, Date.valueOf(LocalDate.now()));
				cs.setInt(3, pomAdresa);
				rs = cs.executeQuery();
				if(rs.next())
					pomNarudzbe=rs.getInt(1);
				rs.close();
				cs.close();
				
				double ukupnaCijena=0;
				for(Integer i : DodajUKorpuController.korpa.keySet()) {
					cs=c.prepareCall("call dodaj_instancu(?, ?, ?)");
					cs.setInt(1, DodajUKorpuController.korpa.get(i));
					cs.setInt(2,i);
					cs.setInt(3, pomNarudzbe);
					rs=cs.executeQuery();
					if(rs.next())
						ukupnaCijena+=rs.getDouble(1);
				}
				cs.close();
				rs.close();
				DodajUKorpuController.korpa.clear();
				
				cs = c.prepareCall("call azuriraj_cijenu(?,?)");
				cs.setInt(1,pomNarudzbe);
				cs.setDouble(2, ukupnaCijena);
				cs.executeUpdate();
				cs.close();
				
				String ispis = "Uspjesno izvrsena narudzba. \nUkupna cijena: " + ukupnaCijena;
				
				Alert a = new Alert(AlertType.CONFIRMATION, ispis, ButtonType.OK);
				a.show();
				
				
				

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if (c != null)
					ConnectionPool.getInstance().checkIn(c);
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		    	}
				if (cs != null) {
					try {
						cs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		    	}
			}
		}
    	}
    
}
