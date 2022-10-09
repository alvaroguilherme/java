package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.dao.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLLoginController implements Initializable {

   
    @FXML
    private TextField textFieldUsuarioEmail;

    @FXML
    private PasswordField passwordFieldUsuarioSenha;

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonAutenticar;
   
    private UsuarioDAO usuarioDAO;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
    }

    @FXML
    public void cancelar() {
        System.exit(0);
    }

    @FXML
    public void autenticar() throws IOException {

        if (usuarioDAO.verify(textFieldUsuarioEmail.getText(), passwordFieldUsuarioSenha.getText())) {
            
            Stage stageMenu = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLMenu.fxml"));
        
            Scene scene = new Scene(root);
        
            stageMenu.setScene(scene);
            stageMenu.setTitle("Sistema de Controle");
            stageMenu.setResizable(false);        
            stageMenu.show();  
            
            buttonAutenticar.getScene().getWindow().hide();
            
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Usuário ou senha inválido!");
            alert.show();
        }
    }
}
