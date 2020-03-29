package zad2.controllers;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import zad2.Service;

import java.util.Locale;

public class ApplicationController {

    @FXML
    private ChoiceBox<String> countriesCBox;
    @FXML
    private Button walutaBtn;
    @FXML
    private AnchorPane pogodaPn;
    @FXML
    private AnchorPane walutaPn;
    @FXML
    private Button pogodaBtn;
    @FXML
    private Label selectedCtry;
    @FXML
    private Button panstwoButton;
    @FXML
    private Button wikiBtn;
    @FXML
    private AnchorPane panstwoPn;
    @FXML
    private AnchorPane wikiPn;
    @FXML
    private WebView webView;
    @FXML
    private Label przelicznikLbl;
    @FXML
    private TextField miastoFld;
    @FXML
    private Label pogodaLbl;
    @FXML
    private Button pogodaZatwierdzBtn;


    public void initialize() {
        populateChoiceBox();
        try {
            setActions();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void populateChoiceBox() {
        ObservableList list = FXCollections.observableArrayList();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            list.add(l.getDisplayCountry());
        }
        countriesCBox.getItems().addAll(list);
    }

    public void setActions() throws Exception {
        pogodaZatwierdzBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    pogodaLbl.setText(new Service(countriesCBox.getSelectionModel().getSelectedItem().toString()).getWeather(miastoFld.getText()));
                } catch (Exception ex) {
                    System.err.println("Nie ma pogody dla tego miasta w API openweather.");
                }
            }
        });

        countriesCBox.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                selectedCtry.setText(countriesCBox.getSelectionModel().getSelectedItem());
                webView.getEngine().load("https://en.wikipedia.org/wiki/"+countriesCBox.getSelectionModel().getSelectedItem());
                try {
                    przelicznikLbl.setText(new Service(countriesCBox.getSelectionModel().getSelectedItem()).getNBPRate().toString());
                } catch (Exception e) {
                    System.err.println("Państwo prawdopodobnie nie ma określonej waluty w API NBP. Proszę spróbować jakieś bardziej popularne.");
                }
            }
        });



        panstwoButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                panstwoPn.setVisible(true);
                wikiPn.setVisible(false);
                walutaPn.setVisible(false);
                pogodaPn.setVisible(false);
            }
        });

        wikiBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                panstwoPn.setVisible(false);
                wikiPn.setVisible(true);
                walutaPn.setVisible(false);
                pogodaPn.setVisible(false);
            }
        });
        walutaBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                panstwoPn.setVisible(false);
                wikiPn.setVisible(false);
                walutaPn.setVisible(true);
                pogodaPn.setVisible(false);
            }
        });
        pogodaBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                panstwoPn.setVisible(false);
                wikiPn.setVisible(false);
                walutaPn.setVisible(false);
                pogodaPn.setVisible(true);
            }
        });



    }

}

