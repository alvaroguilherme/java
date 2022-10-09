package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.dao.ItemVendaDAO;
import br.com.atividadepratica2b.model.dao.PessoaDAO;
import br.com.atividadepratica2b.model.dao.ProdutoDAO;
import br.com.atividadepratica2b.model.dao.VendaDAO;
import br.com.atividadepratica2b.model.entity.ItemVenda;
import br.com.atividadepratica2b.model.entity.Pessoa;
import br.com.atividadepratica2b.model.entity.Produto;
import br.com.atividadepratica2b.model.entity.Venda;
import br.com.atividadepratica2b.utility.Datas;
import br.com.atividadepratica2b.utility.EstadoFormulario;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLItemVendaController implements Initializable {

    @FXML
    private DatePicker datePickerData;

    @FXML
    private ComboBox<Pessoa> comboBoxPessoa;

    @FXML
    private ComboBox<Produto> comboBoxProduto;

    @FXML
    private TextField textFieldQtd;

    @FXML
    private TextField textFieldValor;

    @FXML
    private TableView<ItemVenda> tableViewItemVenda;

    @FXML
    private TableColumn<ItemVenda, String> tableColumnQtd;

    @FXML
    private TableColumn<Produto, String> tableColumnProduto;

    @FXML
    private TableColumn<ItemVenda, String> tableColumnValor;

    @FXML
    private Button buttonSalvar;

    @FXML
    private Button buttonAdicionar;

    @FXML
    private Button buttonRemover;

    @FXML
    private Button buttonFechar;

    private ObservableList<ItemVenda> observableListItemVenda;
    private ItemVenda itemVenda;
    private VendaDAO vendaDAO;
    private Venda venda;
    private EstadoFormulario estadoFormulario;
    private ItemVendaDAO itemVendaDAO;
    private PessoaDAO pessoaDAO;
    private ObservableList<Pessoa> observableListPessoa;
    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> observableListProduto;
    static ArrayList<ItemVenda> listvenda = new ArrayList();
    private Produto produto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        itemVendaDAO = new ItemVendaDAO();
        pessoaDAO = new PessoaDAO();
        produtoDAO = new ProdutoDAO();
        controlarComponentes(EstadoFormulario.Salvando);

        tableViewItemVenda.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

        tableColumnQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));

        observableListItemVenda = FXCollections.observableArrayList();
        tableViewItemVenda.setItems(observableListItemVenda);

        observableListProduto = FXCollections.observableArrayList();
        comboBoxProduto.setItems(observableListProduto);

        observableListPessoa = FXCollections.observableArrayList();
        comboBoxPessoa.setItems(observableListPessoa);

        carregarTableView();
        carregarComboBoxProduto();
        carregarComboBoxPessoa();

    }

    private void controlarComponentes(EstadoFormulario estado) {

        this.estadoFormulario = estado;

        buttonSalvar.setDisable(estado == EstadoFormulario.Visualizando);
        buttonAdicionar.setDisable(estado == EstadoFormulario.Visualizando);
        buttonRemover.setDisable(estado == EstadoFormulario.Editando || estado == EstadoFormulario.Salvando);

//        buttonEditar.setDisable(estado == EstadoFormulario.Salvando
//                || estado == EstadoFormulario.Editando);
        //tableViewItemVenda.setDisable(estado == EstadoFormulario.Editando);
        datePickerData.setDisable(estado == EstadoFormulario.Visualizando);
        textFieldQtd.setDisable(estado == EstadoFormulario.Visualizando);
        textFieldValor.setDisable(estado == EstadoFormulario.Visualizando);
        comboBoxPessoa.setDisable(estado == EstadoFormulario.Visualizando);
        comboBoxProduto.setDisable(estado == EstadoFormulario.Visualizando);

    }

    private void carregarTableView() {
        observableListItemVenda.clear();
        observableListItemVenda.addAll(listvenda);
    }

    private void carregarComboBoxPessoa() {
        observableListPessoa.clear();
        observableListPessoa.addAll(pessoaDAO.findAll());
    }

    private void carregarComboBoxProduto() {
        observableListProduto.clear();
        observableListProduto.addAll(produtoDAO.findAll());
    }

    public void selecionarItemTableView(ItemVenda itemVenda) {
        if (itemVenda != null) {
            textFieldQtd.setText(Integer.toString(itemVenda.getQtd()));
            textFieldValor.setText(itemVenda.getValor().toString());
            comboBoxProduto.getSelectionModel().select(itemVenda.getProduto());
            controlarComponentes(EstadoFormulario.Visualizando);

        }
    }

    private void limparComponentes() {
        textFieldQtd.clear();
        textFieldValor.clear();
        tableViewItemVenda.getSelectionModel().clearSelection();
        carregarComboBoxProduto();
    }

    @FXML
    public void selecionarComboBox() {
        itemVenda = new ItemVenda();
        itemVenda.setProduto(comboBoxProduto.getSelectionModel().getSelectedItem());
        if (comboBoxProduto.getSelectionModel().getSelectedItem() != null) {
            Double valor = itemVenda.getProduto().getValor().doubleValue();
            textFieldValor.setText(valor.toString());
        }
    }

    @FXML
    public void salvar() throws IOException {
        int n = listvenda.size();
        venda = new Venda();
        vendaDAO = new VendaDAO();

        for (int i = 0; i < n; i++) {
            if (listvenda.get(i) != null) {
                 if (this.estadoFormulario == EstadoFormulario.Salvando) {
                    venda.setPessoa(comboBoxPessoa.getValue());
                    venda.setData(Datas.toDate(datePickerData));
                    vendaDAO.insert(venda);
                }
                produto = new Produto();
                produtoDAO = new ProdutoDAO();
                itemVenda = new ItemVenda();
                itemVendaDAO = new ItemVendaDAO();
                produto = listvenda.get(i).getProduto();
                itemVenda.setVenda(venda);
                itemVenda.setQtd(listvenda.get(i).getQtd());
                itemVenda.setProduto(listvenda.get(i).getProduto());
                itemVenda.setValor(listvenda.get(i).getValor());
                itemVendaDAO.insert(itemVenda);
                produto.setQtd(produto.getQtd() - listvenda.get(i).getQtd());
                produtoDAO.update(produto);
            }
        }
        listvenda.clear();
        carregarComboBoxPessoa();
        datePickerData.setValue(null);
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
        Stage stageMenu = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLVenda.fxml"));

        Scene scene = new Scene(root);

        stageMenu.setScene(scene);
        stageMenu.setTitle("Sistema de Venda");
        stageMenu.setResizable(false);
        stageMenu.show();

        buttonSalvar.getScene().getWindow().hide();

    }
//    @FXML
//    public void salvar() throws IOException {
//        int n = listvenda.size();
//        venda = new Venda();
//        vendaDAO = new VendaDAO();
//        if (this.estadoFormulario == EstadoFormulario.Salvando) {
//            venda.setPessoa(comboBoxPessoa.getValue());
//            venda.setData(Datas.toDate(datePickerData));
//            vendaDAO.insert(venda);
//        }
//        for (int i = 0; i < n; i++) {
//            if (listvenda.get(i) != null) {
//                produto = new Produto();
//                produtoDAO = new ProdutoDAO();
//                itemVenda = new ItemVenda();
//                itemVendaDAO = new ItemVendaDAO();
//                produto = listvenda.get(i).getProduto();
//                produto.setQtd(produto.getQtd() - listvenda.get(i).getQtd());
//                produtoDAO.update(produto);
//                for (int j = 0; j < 10; j++) {
//                    if (listvenda.get(j).getProduto().equals(produto)) {
//                        listvenda.get(j).setQtd(produto.getQtd());
//                    }
//                }
//                itemVenda.setVenda(venda);
//                itemVenda.setQtd(listvenda.get(i).getQtd());
//                itemVenda.setProduto(listvenda.get(i).getProduto());
//                itemVenda.setValor(listvenda.get(i).getValor());
//                itemVendaDAO.insert(itemVenda);
//            }
//        }
//        listvenda.clear();
//        carregarComboBoxPessoa();
//        datePickerData.setValue(null);
//        limparComponentes();
//        controlarComponentes(EstadoFormulario.Salvando);
//        Stage stageMenu = new Stage();
//        Parent root= FXMLLoader.load(getClass().getResource("/br/com/atividadepratica2b/view/FXMLVenda.fxml"));
//Scene scene = new Scene(root);
//        stageMenu.setScene(scene);
//        stageMenu.setTitle("Sistema de Venda");
//        stageMenu.setResizable(false);
//        stageMenu.show();
//        buttonSalvar.getScene().getWindow().hide();
//    }

    @FXML
    public void adicionar() {
        itemVenda = new ItemVenda();
        itemVenda.setProduto(comboBoxProduto.getSelectionModel().getSelectedItem());
        // if (comboBoxProduto.getSelectionModel().getSelectedItem() != null) {
        if (itemVenda.getProduto().getQtd() >= Integer.parseInt(textFieldQtd.getText())) {
            itemVenda.setQtd(Integer.parseInt(textFieldQtd.getText()));
            itemVenda.setValor(new BigDecimal(Double.parseDouble(textFieldValor.getText())));
            listvenda.add(itemVenda);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Problemas na escolha do produto!");
            alert.setContentText("Não existe a quantidade de produtos disponíveis no estoque!");
            alert.show();
        }
        // }
//        if (this.estadoFormulario == EstadoFormulario.Salvando) {
//            itemVendaDAO.insert(itemVenda);
//        } else if (this.estadoFormulario == EstadoFormulario.Editando) {
//            itemVendaDAO.update(itemVenda);
//       }
        carregarTableView();
        limparComponentes();
        //controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void cancelar() {
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    //  @FXML
    //  public void editar() {
    //      controlarComponentes(EstadoFormulario.Editando);
    //     itemVenda = tableViewItemVenda.getSelectionModel().getSelectedItem();
    // }
    @FXML
    public void remover() {
        itemVenda = new ItemVenda();
        produto = new Produto();
        produtoDAO = new ProdutoDAO();
        itemVenda.setProduto(tableViewItemVenda.getSelectionModel().getSelectedItem().getProduto());

        int n = listvenda.size();
        for (int i = 0; i < n; i++) {
            if (tableViewItemVenda.getSelectionModel().getSelectedItem().getProduto().equals(listvenda.get(i).getProduto())
                    && tableViewItemVenda.getSelectionModel().getSelectedItem().getQtd() == listvenda.get(i).getQtd()) {
                listvenda.remove(i);
            }
        }
        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }

    @FXML
    public void fechar() {
        listvenda.clear();
        buttonFechar.getScene().getWindow().hide();
    }

}
