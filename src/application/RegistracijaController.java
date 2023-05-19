package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class RegistracijaController {
	@FXML
	private AnchorPane PaneRegistracija;
	@FXML
	private ImageView ImageRegistracija;
	@FXML
	private Label LabelIme;
	@FXML
	private TextField TextFieldIme;
	@FXML
	private Label LabelMail;
	@FXML
	private TextField TextFieldMail;
	@FXML
	private Label LabelBroj;
	@FXML
	private TextField TextFieldBroj;
	@FXML
	private Label LabelLozinka;
	@FXML
	private TextField TextFieldLozinka;
	@FXML
	private Button ButtonRegistrujse;

	// Event Listener on Button[#ButtonRegistrujse].onAction
	@FXML
	/*public void RegistrujSe(ActionEvent event) {
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
	}*/
	
void RegistrujSe(ActionEvent event) throws SQLException {
		
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		//CallableStatement cs = null;
		
		try {
			
			if(!TextFieldIme.getText().equals("") && !TextFieldMail.getText().equals("") && !TextFieldBroj.getText().equals("") && !TextFieldLozinka.getText().equals("")) {
				
				c = ConnectionPool.getInstance().checkOut();
				ps = c.prepareStatement("insert into kupac (ime,mail, lozinka, brojTelefona) values (?, ?, ?, ?)");
				ps.setString(1, TextFieldIme.getText());
				ps.setString(2, TextFieldMail.getText());
				ps.setString(3, TextFieldLozinka.getText());
				ps.setString(4, TextFieldBroj.getText());
				ps.executeUpdate();
				ps.close();
				
				ButtonRegistrujse.getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("KategorijeProizvoda.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Proizvodi");
				stage.setResizable(false);
				stage.show();
			}else {
				//ubaciti
			}
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			
			if(c!=null) {
				ConnectionPool.getInstance().checkIn(c);
			}
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}

    }
}
