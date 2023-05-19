package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import com.mysql.cj.jdbc.CallableStatement;

import app.connection.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class KategorijeProizvodaController implements Initializable {
	@FXML
	private AnchorPane PaneKategorijeProizvoda;
	
	@FXML
	private Label LabelOdabirProizvoda;
	
	@FXML
	private Button ButtonPotvrda;
	
	@FXML
    private ImageView Image1;
	
	
	@FXML
	private ChoiceBox<String> Choice1;
	@FXML
	private ChoiceBox<String> Choice2;
	@FXML
	private ChoiceBox<String> Choice3;
	
	private String[] vrste= {"satovi","mindjuse","narukvice","prstenje","lancici","privjesci"};
	private String[] pol= {"muski","zenski"};
	private String[] materijal= {"srebro","zlato"};
	
	public static ArrayList<Integer> sifre = new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Choice1.getItems().addAll(vrste);
		Choice2.getItems().addAll(pol);
		Choice3.getItems().addAll(materijal);
		
		
	}
	
	  @FXML
	    void Potvrdi(ActionEvent event) throws SQLException  {
		  
		  Connection c = null;
		  ResultSet rs = null;
		  java.sql.CallableStatement cs = null;
		  
		  try {
			  c = ConnectionPool.getInstance().checkOut();
			  cs = c.prepareCall("call id_kategorija(?)");
				cs.setString(1, Choice1.getValue());
				rs = cs.executeQuery();
				rs.next();
				int id_kat= rs.getInt(1);
				rs.close();
				cs.close();
				
				
				
				cs=c.prepareCall("call id_pol(?)");
				cs.setString(1,Choice2.getValue());
				rs = cs.executeQuery();
				rs.next();
				int id_pol= rs.getInt(1);
				rs.close();
				cs.close();
				
				
				cs=c.prepareCall("call id_materijal(?)");
				cs.setString(1, Choice3.getValue());
				rs = cs.executeQuery();
				rs.next();
				int id_materijal= rs.getInt(1);
				rs.close();
				cs.close();
				
				
				cs=c.prepareCall("call pronadji_proizvod(?, ?, ?)");
				cs.setInt(1, id_kat);
				cs.setInt(2, id_pol);
				cs.setInt(3, id_materijal);
				rs = cs.executeQuery();
				while(rs.next())
					sifre.add(rs.getInt(1));
				rs.close();
				cs.close();
				
				ButtonPotvrda.getScene().getWindow().hide();    
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
