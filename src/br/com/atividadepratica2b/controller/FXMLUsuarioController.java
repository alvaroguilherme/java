package br.com.atividadepratica2b.controller;

import br.com.atividadepratica2b.model.dao.UsuarioDAO;
import br.com.atividadepratica2b.model.entity.Usuario;
import br.com.atividadepratica2b.utility.EstadoFormulario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLUsuarioController implements Initializable {

    @FXML
    private TextField textFieldUsuarioEmail;

    @FXML
    private PasswordField passwordFieldUsuarioSenha;

    @FXML
    private TableView<Usuario> tableViewUsuario;

    @FXML
    private TableColumn<Usuario, String> tableColumnUsuarioEmail;

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

    private ObservableList<Usuario> observableListUsuario;
    private Usuario usuario;
    private EstadoFormulario estadoFormulario;
    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuarioDAO = new UsuarioDAO();

        controlarComponentes(EstadoFormulario.Salvando);

        tableViewUsuario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

        tableColumnUsuarioEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        observableListUsuario = FXCollections.observableArrayList();
        tableViewUsuario.setItems(observableListUsuario);

        carregarTableView();
    }

    private void controlarComponentes(EstadoFormulario estado) {

        this.estadoFormulario = estado;

        buttonSalvar.setDisable(estado == EstadoFormulario.Visualizando);

        buttonEditar.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Editando);

        buttonRemover.setDisable(estado == EstadoFormulario.Salvando
                || estado == EstadoFormulario.Visualizando);

        buttonCancelar.setDisable(estado == EstadoFormulario.Salvando);

        tableViewUsuario.setDisable(estado == EstadoFormulario.Editando);

        textFieldUsuarioEmail.setDisable(estado == EstadoFormulario.Visualizando);
        passwordFieldUsuarioSenha.setDisable(estado == EstadoFormulario.Visualizando);

    }

    private void carregarTableView() {
        observableListUsuario.clear();
        observableListUsuario.addAll(usuarioDAO.findAll());
    }

    public void selecionarItemTableView(Usuario usuario) {
        if (usuario != null) {
            textFieldUsuarioEmail.setText(usuario.getEmail());
            passwordFieldUsuarioSenha.setText(usuario.getSenha());
            controlarComponentes(EstadoFormulario.Visualizando);
        }
    }

    private void limparComponentes() {
        textFieldUsuarioEmail.clear();
        passwordFieldUsuarioSenha.clear();
        tableViewUsuario.getSelectionModel().clearSelection();
    }

    @FXML
    public void salvar() {

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            usuario = new Usuario();
        }

        usuario.setEmail(textFieldUsuarioEmail.getText());
        usuario.setSenha(passwordFieldUsuarioSenha.getText());

        if (this.estadoFormulario == EstadoFormulario.Salvando) {
            usuarioDAO.insert(usuario);
        } else if (this.estadoFormulario == EstadoFormulario.Editando) {
            usuarioDAO.update(usuario);
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
        usuario = tableViewUsuario.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void remover() {
        usuario = tableViewUsuario.getSelectionModel().getSelectedItem();
        usuarioDAO.delete(usuario.getId());
        carregarTableView();
        limparComponentes();
        controlarComponentes(EstadoFormulario.Salvando);
    }
    
    @FXML
    public void fechar(){
        buttonFechar.getScene().getWindow().hide();
    }    
}
