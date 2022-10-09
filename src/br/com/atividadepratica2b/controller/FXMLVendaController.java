package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.dao.ItemVendaDAO;
import br.com.atividadepratica2b.model.dao.PessoaDAO;
import br.com.atividadepratica2b.model.dao.ProdutoDAO;
import br.com.atividadepratica2b.model.dao.VendaDAO;
import br.com.atividadepratica2b.model.entity.ItemVenda;
import br.com.atividadepratica2b.model.entity.Pessoa;
import br.com.atividadepratica2b.model.entity.Produto;
import br.com.atividadepratica2b.model.entity.Venda;
import br.com.atividadepratica2b.utility.EstadoFormulario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLVendaController implements Initializable {
//    @FXML
//    private DatePicker datePickerData;
//    
//    @FXML
//    private ComboBox<Pessoa> comboBoxPessoa;

    @FXML
    private TableView<Venda> tableViewVenda;

    @FXML
    private TableColumn<Venda, String> tableColumnData;

    @FXML
    private TableColumn<Pessoa, String> tableColumnPessoa;

    @FXML
    private Button buttonSalvar;

    @FXML
    private Button buttonEditar;

    @FXML
    private Button buttonRemover;

    @FXML
    private Button buttonFechar;

    @FXML
    private Button buttonCancelar;

    private ObservableList<Venda> observableListVenda;
    private Venda venda;
    private Produto produto;
    private ItemVenda itemVenda;
    private EstadoFormulario estadoFormulario;
    private VendaDAO vendaDAO;
    private PessoaDAO pessoaDAO;
    private ProdutoDAO produtoDAO;
    private ItemVendaDAO itemVendaDAO;
    private ObservableList<Pessoa> observableListPessoa;
    private ObservableList<ItemVenda> observableListItemVenda;
    static ArrayList<ItemVenda> listItemVenda = new ArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        vendaDAO = new VendaDAO();
        pessoaDAO = new PessoaDAO();

        controlarComponentes(EstadoFormulario.Salvando);

        tableViewVenda.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnPessoa.setCellValueFactory(new PropertyValueFactory<>("pessoa"));

        observableListVenda = FXCollections.observableArrayList();
        tableViewVenda.setItems(observableListVenda);

        //observableListPessoa = FXCollections.observableArrayList();
        //comboBoxPessoa.setItems(observableListPessoa);
        carregarTableView();
        //carregarComboBoxPessoa();
    }

    private void controlarComponentes(EstadoFormulario estado) {

        this.estadoFormulario = estado;

        buttonSalvar.setDisable(estado == EstadoFormulario.Visualizando);

        buttonCancelar.setDisable(estado == EstadoFormulario.Editando || estado == EstadoFormulario.Salvando);

//        buttonEditar.setDisable(estado == EstadoFormulario.Salvando
        //               || estado == EstadoFormulario.Editando);
        buttonRemover.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Editando);

        // tableViewVenda.setDisable(estado == EstadoFormulario.Editando);
        //datePickerData.setDisable(estado == EstadoFormulario.Visualizando);
        //comboBoxPessoa.setDisable(estado == EstadoFormulario.Visualizando);
    }

    private void carregarTableView() {
        observableListVenda.clear();
        observableListVenda.addAll(vendaDAO.findAll());
    }

//    private void carregarComboBoxPessoa() {
//        observableListPessoa.clear();
//        observableListPessoa.addAll(pessoaDAO.findAll());
//    }    
    public void selecionarItemTableView(Venda venda) {
        if (venda != null) {
            //datePickerData.setValue(Datas.toLocalDate(venda.getData())); 
            //comboBoxPessoa.getSelectionModel().select(venda.getPessoa());            
            controlarComponentes(EstadoFormulario.Visualizando);

        }
    }

    private void limparComponentes() {
        //datePickerData.setValue(null);
        tableViewVenda.getSelectionModel().clearSelection();
        //carregarComboBoxPessoa();
    }

    @FXML
    public void salvar() throws IOException {

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            venda = new Venda();
        }
        Stage stageMenu = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLItemVenda.fxml"));

        Scene scene = new Scene(root);

        stageMenu.setScene(scene);
        stageMenu.setTitle("Sistema de Venda");
        stageMenu.setResizable(false);
        stageMenu.show();

        buttonSalvar.getScene().getWindow().hide();
    }

    @FXML
    public void cancelar() {
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void editar() throws IOException {
        controlarComponentes(EstadoFormulario.Editando);
        Stage stageMenu = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLItemVenda.fxml"));

        Scene scene = new Scene(root);

        stageMenu.setScene(scene);
        stageMenu.setTitle("Sistema de Venda");
        stageMenu.setResizable(false);
        stageMenu.show();

        buttonEditar.getScene().getWindow().hide();
    }

    @FXML
    public void remover() {
        itemVendaDAO = new ItemVendaDAO();
        produtoDAO = new ProdutoDAO();
        vendaDAO = new VendaDAO();
        venda = tableViewVenda.getSelectionModel().getSelectedItem();
        listItemVenda.clear();
        listItemVenda.addAll(itemVendaDAO.findAll());
        for (int i = 0; i < listItemVenda.size(); i++) {
            itemVenda = listItemVenda.get(i);
            if (itemVenda.getVenda().getId().equals(venda.getId())){
                produto = new Produto();
                produto = itemVenda.getProduto();
                produto.setQtd(produto.getQtd()+itemVenda.getQtd());
                produtoDAO.update(produto);
                itemVendaDAO.delete(itemVenda.getId());
            }
        }

        vendaDAO.delete(venda.getId());
        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void remover2() {
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void fechar() {
        buttonFechar.getScene().getWindow().hide();
    }

}
