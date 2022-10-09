package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.entity.Categoria;
import br.com.atividadepratica2b.model.dao.CategoriaDAO;
import br.com.atividadepratica2b.utility.EstadoFormulario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class FXMLCategoriaController implements Initializable {

    @FXML
    private TextField textFieldDescricao;
    
    @FXML
    private TableView<Categoria> tableViewCategoria;  
    
    @FXML
    private TableColumn<Categoria, String> tableColumnCategoriaDescricao;    

    @FXML
    private Button buttonSalvar; 

    @FXML
    private Button buttonEditar; 

    @FXML
    private Button buttonRemover; 

    @FXML
    private Button buttonCancelar;   
    
    @FXML
    private Button buttonFechar;      
        
    private ObservableList<Categoria> observableListCategoria;
    private Categoria categoria;
    private EstadoFormulario estadoFormulario;
    private CategoriaDAO categoriaDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        categoriaDAO = new CategoriaDAO();
        
        controlarComponentes(EstadoFormulario.Salvando);
                       
        tableViewCategoria.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        tableColumnCategoriaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            
        observableListCategoria = FXCollections.observableArrayList();
        tableViewCategoria.setItems(observableListCategoria); 
        
        carregarTableView(); 
        
    }    
    
    private void carregarTableView(){
        observableListCategoria.clear();
        observableListCategoria.addAll( categoriaDAO.findAll());
    }   
    
    private void limparComponentes(){
        textFieldDescricao.clear();
        tableViewCategoria.getSelectionModel().clearSelection();
    }
        
    public void selecionarItemTableView(Categoria categoria) {
        if (categoria != null) {
            textFieldDescricao.setText(categoria.getDescricao());
            controlarComponentes(EstadoFormulario.Visualizando);
        }
    }
    
    private void controlarComponentes(EstadoFormulario estado){
        
        this.estadoFormulario = estado;
        
        buttonSalvar.setDisable(estado == EstadoFormulario.Visualizando); 
        
        buttonEditar.setDisable(estado == EstadoFormulario.Salvando
                                || estado == EstadoFormulario.Editando);                 
        
        buttonRemover.setDisable(estado == EstadoFormulario.Salvando
                                || estado == EstadoFormulario.Visualizando); 
        
        buttonCancelar.setDisable(estado == EstadoFormulario.Salvando); 
        
        tableViewCategoria.setDisable(estado == EstadoFormulario.Editando);
        
        textFieldDescricao.setDisable(estado == EstadoFormulario.Visualizando);
          
    }      
    
    @FXML
    public void salvar(){
      if(this.estadoFormulario == EstadoFormulario.Salvando){
            categoria = new Categoria();    
        }
        
        categoria.setDescricao(textFieldDescricao.getText());
                    
        if(this.estadoFormulario == EstadoFormulario.Salvando){
            categoriaDAO.insert(categoria);                
        }else
            if(this.estadoFormulario == EstadoFormulario.Editando){
                categoriaDAO.update(categoria);    
            }
        
        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);                    
    }
    
    @FXML
    public void cancelar(){
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }
    
    @FXML
    public void editar(){
        controlarComponentes(EstadoFormulario.Editando);
        categoria = tableViewCategoria.getSelectionModel().getSelectedItem();
    }    
    
    @FXML
    public void remover(){
        categoria = tableViewCategoria.getSelectionModel().getSelectedItem();
        categoriaDAO.delete(categoria.getId());
        carregarTableView();
        limparComponentes();       
        controlarComponentes(EstadoFormulario.Salvando);
    }  
    
    @FXML
    public void fechar(){
        buttonFechar.getScene().getWindow().hide();
    }    
}
