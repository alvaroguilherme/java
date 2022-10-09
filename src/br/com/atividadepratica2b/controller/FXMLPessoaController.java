package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.dao.PessoaDAO;
import br.com.atividadepratica2b.model.entity.Pessoa;
import br.com.atividadepratica2b.utility.Datas;
import br.com.atividadepratica2b.utility.EstadoFormulario;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLPessoaController implements Initializable{
    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private TableView<Pessoa> tableViewPessoa;
    
    @FXML
    private ComboBox<String> comboBoxSexo;
    
    @FXML
    private DatePicker datePickerNascimento;

    @FXML
    private TableColumn<Pessoa, String> tableColumnNome;
    
//    @FXML
//    private TableColumn<Pessoa, String> tableColumnSexo;
    
    @FXML
    private TableColumn<Pessoa, String> tableColumnCpf;
    
    @FXML
    private TableColumn<Pessoa, LocalDate> tableColumnNascimento;

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

    private ObservableList<Pessoa> observableListPessoa;
   // private ObservableList<String> observableListSexo;
    private Pessoa pessoa;
    private EstadoFormulario estadoFormulario;
    private PessoaDAO pessoaDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pessoaDAO = new PessoaDAO();

        controlarComponentes(EstadoFormulario.Salvando);

        tableViewPessoa.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("dtnasc"));
       // tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        observableListPessoa = FXCollections.observableArrayList();
        tableViewPessoa.setItems(observableListPessoa);
     //   observableListSexo = FXCollections.observableArrayList();
        
     //   comboBoxSexo.setItems(observableListSexo);

        carregarTableView();
       // carregarComboBoxSexo();
    }

    private void controlarComponentes(EstadoFormulario estado) {

        this.estadoFormulario = estado;

        buttonSalvar.setDisable(estado == EstadoFormulario.Visualizando);

        buttonEditar.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Editando);

        buttonRemover.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Visualizando);

        buttonCancelar.setDisable(estado == EstadoFormulario.Salvando);

        tableViewPessoa.setDisable(estado == EstadoFormulario.Editando);

        textFieldNome.setDisable(estado == EstadoFormulario.Visualizando);
        textFieldCpf.setDisable(estado == EstadoFormulario.Visualizando);
        datePickerNascimento.setDisable(estado == EstadoFormulario.Visualizando);
        comboBoxSexo.setDisable(estado == EstadoFormulario.Visualizando);

    }

    private void carregarTableView() {
        observableListPessoa.clear();
        observableListPessoa.addAll(pessoaDAO.findAll());
    }
    
//    private void carregarComboBoxSexo() {
//        observableListSexo.clear();
//        observableListSexo.addAll("Masculino" , "Feminino");
//    }    

    public void selecionarItemTableView(Pessoa pessoa) {
        if (pessoa != null) {
            textFieldNome.setText(pessoa.getNome());
            textFieldCpf.setText(pessoa.getCpf());
            datePickerNascimento.setValue(Datas.toLocalDate(pessoa.getDtnasc()));
           // comboBoxSexo.getSelectionModel().select(pessoa.getSexo());
            controlarComponentes(EstadoFormulario.Visualizando);
        }
    }

    private void limparComponentes() {
        textFieldNome.clear();
        textFieldCpf.clear();
        datePickerNascimento.setValue(null);
        //carregarComboBoxSexo();
        tableViewPessoa.getSelectionModel().clearSelection();
    }

    @FXML
    public void salvar() {

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            pessoa = new Pessoa();
        }

        pessoa.setNome(textFieldNome.getText());
        pessoa.setCpf(textFieldCpf.getText());
        pessoa.setDtnasc(Datas.toDate(datePickerNascimento));
       // pessoa.setSexo(comboBoxSexo.getSelectionModel().getSelectedItem());

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            pessoaDAO.insert(pessoa);
        } else if (this.estadoFormulario == EstadoFormulario.Editando) {
            pessoaDAO.update(pessoa);
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
        pessoa = tableViewPessoa.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void remover() {
        pessoa = tableViewPessoa.getSelectionModel().getSelectedItem();
        pessoaDAO.delete(pessoa.getId());
        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }
    
    @FXML
    public void fechar(){
        buttonFechar.getScene().getWindow().hide();
    }    
}