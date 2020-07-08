package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Artisti;
import it.polito.tdp.artsmia.model.Collegamento;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	if(collegamenti.size()==0) {
    		this.txtResult.appendText("NESSUN COLLEGAMENTO TROVATO! \n");
    		return;
    	}
    	Collections.sort(collegamenti,new ComparatorePerPeso());
    	for(Collegamento c:collegamenti) {
    		this.txtResult.appendText(c.getA1()+" -> "+c.getA2()+" con peso "+c.getPeso()+"\n");
    	}

    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	if(!this.isNumeric(this.txtArtista.getText())) {
    		this.txtResult.appendText("INSERISCI UN NUMERO INTERO \n");
    		return;
    	}
    	int artista=Integer.parseInt(this.txtArtista.getText());
    	if(!model.okay(artista)) {
    		this.txtResult.setText("inserisci un artista di questo ruolo");
    		return;
    	}
    	ArrayList<Artisti> cammino=model.cercaCammino(artista);
    	if(cammino!=null) {
    		for(int i=0;i<cammino.size()-1;i++) {
    			this.txtResult.appendText((i+1)+") "+cammino.get(i)+" -> "+cammino.get(i+1)+"\n");
    		}
    	}

    }
    
    public static boolean isNumeric(String str) { 
    	  try {  
    	    Integer.parseInt(str);  
    	    return true;
    	  } catch(NumberFormatException e){  
    	    return false;  
    	  }  
    	}
    
    private ArrayList<Collegamento> collegamenti;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String s=this.boxRuolo.getValue();
    	if(s==null) {
    		this.txtResult.setText("SELEZIONA UN RUOLO!");
    		return;
    	} else {
    		this.txtResult.setText("Creazione grafo... \n");
    		collegamenti=model.creaGrafo(s);
    	}
    	this.btnCalcolaPercorso.setDisable(false);

    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxRuolo.getItems().clear();
		this.boxRuolo.getItems().addAll(model.getAllRuoli());
		this.btnCalcolaPercorso.setDisable(true);
	}
}

