
package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.dao.CategoriaDAO;
import br.com.atividadepratica2b.model.dao.ProdutoDAO;
import br.com.atividadepratica2b.model.entity.Categoria;
import br.com.atividadepratica2b.model.entity.Pessoa;
import br.com.atividadepratica2b.model.entity.Produto;
import br.com.atividadepratica2b.utility.EstadoFormulario;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLProdutoController implements Initializable {
    @FXML
    private TextField textFieldDescricao;

    @FXML
    private TextField textFieldValor;
    
    @FXML
    private TextField textFieldQtd;
    
    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    @FXML
    private TableView<Produto> tableViewProduto;

    @FXML
    private TableColumn<Produto, String> tableColumnDescricao;
    
    @FXML
    private TableColumn<Produto, String> tableColumnValor;
    
    @FXML
    private TableColumn<Produto, String> tableColumnQtd;
    
    @FXML
    private TableColumn<Pessoa, ComboBox> tableColumnCategoria;

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

    private ObservableList<Produto> observableListProduto;
    private Produto produto;
    private EstadoFormulario estadoFormulario;
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;
    private ObservableList<Categoria> observableListCategoria;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        produtoDAO = new ProdutoDAO();
        categoriaDAO = new CategoriaDAO();

        controlarComponentes(EstadoFormulario.Salvando);

        tableViewProduto.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tableColumnQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        observableListProduto = FXCollections.observableArrayList();
        tableViewProduto.setItems(observableListProduto);
        
        observableListCategoria = FXCollections.observableArrayList();
        comboBoxCategoria.setItems(observableListCategoria);

        carregarTableView();
        carregarComboBoxCategoria();
    }

    private void controlarComponentes(EstadoFormulario estado) {

        this.estadoFormulario = estado;

        buttonSalvar.setDisable(estado == EstadoFormulario.Visualizando);

        buttonEditar.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Editando);

        buttonRemover.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Visualizando);

        buttonCancelar.setDisable(estado == EstadoFormulario.Salvando);

        tableViewProduto.setDisable(estado == EstadoFormulario.Editando);

        textFieldDescricao.setDisable(estado == EstadoFormulario.Visualizando);
        textFieldValor.setDisable(estado == EstadoFormulario.Visualizando);
        textFieldQtd.setDisable(estado == EstadoFormulario.Visualizando);
        comboBoxCategoria.setDisable(estado == EstadoFormulario.Visualizando);

    }

    private void carregarTableView() {
        observableListProduto.clear();
        observableListProduto.addAll(produtoDAO.findAll());
    }
    
    private void carregarComboBoxCategoria() {
        observableListCategoria.clear();
        observableListCategoria.addAll(categoriaDAO.findAll());
    }    

    public void selecionarItemTableView(Produto produto) {
        if (produto != null) {
            textFieldDescricao.setText(produto.getDescricao());
            textFieldValor.setText(produto.getValor().toString());
            textFieldQtd.setText(Integer.toString(produto.getQtd()));  
            comboBoxCategoria.getSelectionModel().select(produto.getCategoria());
            
            controlarComponentes(EstadoFormulario.Visualizando);
            
        }
    }

    private void limparComponentes() {
        textFieldDescricao.clear();
        textFieldValor.clear();
        textFieldQtd.clear();
        tableViewProduto.getSelectionModel().clearSelection();
        carregarComboBoxCategoria();
    }

    @FXML
    public void salvar() {

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            produto = new Produto();
        }

        produto.setDescricao(textFieldDescricao.getText());
        produto.setValor(new BigDecimal(textFieldValor.getText()));
        produto.setQtd(Integer.parseInt(textFieldQtd.getText()));
        produto.setCategoria(comboBoxCategoria.getSelectionModel().getSelectedItem());

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            produtoDAO.insert(produto);
        } else if (this.estadoFormulario == EstadoFormulario.Editando) {
            produtoDAO.update(produto);
        }

        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void cancelar() {
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void editar() {
        controlarComponentes(EstadoFormulario.Editando);
        produto = tableViewProduto.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void remover() {
        produto = tableViewProduto.getSelectionModel().getSelectedItem();
        produtoDAO.delete(produto.getId());
        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }
    
    @FXML
    public void fechar(){
        buttonFechar.getScene().getWindow().hide();
    }    
}
