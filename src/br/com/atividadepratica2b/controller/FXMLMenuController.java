package br.com.atividadepratica2b.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLMenuController implements Initializable {
    
    @FXML
    private MenuItem menuItemCadastrosCategoria;

    @FXML
    private MenuItem menuItemCadastrosUsuario;
    
    @FXML
    private MenuItem menuItemCadastrosPessoa;
    
    @FXML
    private MenuItem menuItemCadastrosProduto;
    
    @FXML
    private MenuItem menuItemCadastrosVenda;
    
//    @FXML
//    private MenuItem menuItemCadastrosItemVenda;
     

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void abrirMenuItemCadastrosCategoria() throws IOException {
        /*
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLCategoria.fxml"));
        anchorPane.getChildren().setAll(a);
        */
        
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLCategoria.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cadastros - Categoria");
        stage.setResizable(false);        
        stage.show();
        
    }

    @FXML
    public void abrirMenuItemCadastrosUsuario() throws IOException {
        /*
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLUsuario.fxml"));
        anchorPane.getChildren().setAll(a);
        */
        
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLUsuario.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cadastros - Usuário");
        stage.setResizable(false);        
        stage.show();        
    }
    
    @FXML
    public void abrirMenuItemCadastrosPessoa() throws IOException {
        /*
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLPessoa.fxml"));
        anchorPane.getChildren().setAll(a);
        */
        
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLPessoa.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cadastros - Pessoa");
        stage.setResizable(false);        
        stage.show();        
    }
    
    @FXML
    public void abrirMenuItemCadastrosProduto() throws IOException {
        /*
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLProduto.fxml"));
        anchorPane.getChildren().setAll(a);
        */
        
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLProduto.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cadastros - Produto");
        stage.setResizable(false);        
        stage.show();        
    }
    
    @FXML
    public void abrirMenuItemCadastrosVenda() throws IOException {
        /*
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLVenda.fxml"));
        anchorPane.getChildren().setAll(a);
        */
        
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLVenda.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cadastros - Venda");
        stage.setResizable(false);        
        stage.show();        
    }
    
//    @FXML
//    public void abrirMenuItemCadastrosItemVenda() throws IOException {
//        /*
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLItemVenda.fxml"));
//        anchorPane.getChildren().setAll(a);
//        */
//        
//        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLItemVenda.fxml"));
//        
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Cadastros - Item Venda");
//        stage.setResizable(false);        
//        stage.show();        
//    }
    
    
}
