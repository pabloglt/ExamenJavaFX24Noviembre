package org.example.javafxx.CONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.javafxx.MODELS.Persona;

public class MainController {

    @FXML
    private Button btnAnadir;

    @FXML
    private TableView<Persona> tvPersonas;
    @FXML
    private TableColumn<Persona, String> colEmail;
    @FXML
    private TableColumn<Persona, String> colPlataforma;
    @FXML
    private TableColumn<Persona, Boolean> colAdministrador;



    @FXML
    private TextField tfEmail;
    @FXML
    private ComboBox<String> cbPlataforma;
    @FXML
    private CheckBox cbAdministrador;


    public static ObservableList<Persona> listaPersonas;
    @FXML
    private Spinner spVersion;
    @FXML
    private Button btnvaciar;


    @FXML
    public void initialize() {
        if (listaPersonas == null) {
            listaPersonas = FXCollections.observableArrayList();
        }

        cbPlataforma.setItems(FXCollections.observableArrayList("Windows", "macOS", "Linux"));

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));


        colAdministrador.setCellValueFactory(new PropertyValueFactory<>("administrador"));
        colAdministrador.setCellFactory(column -> new TableCell<Persona, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Sí" : "No");
                }
            }
        });

        tvPersonas.setItems(listaPersonas);


        tvPersonas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        mostrarDetallePersona(newValue);
                    }
                }
        );
        // 3. Configurar el Spinner para Edad (ejemplo de 1 a 100)
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 2);
        spVersion.setValueFactory(valueFactory);
    }



    @FXML
    public void anadir(ActionEvent actionEvent) {

        String email = tfEmail.getText();
        String plataforma = cbPlataforma.getValue();
        boolean administrador = cbAdministrador.isSelected();
        Double version = Double.valueOf(spVersion.getValue().toString());

        if (email.isEmpty() || plataforma == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Campos incompletos",
                    "Por favor, rellena el email y selecciona una plataforma.");
            return;
        }

        Persona nuevaPersona = new Persona(email, plataforma, administrador, version, java.time.LocalDateTime.now());

        listaPersonas.add(nuevaPersona);

        limpiarCampos();
    }


    private void limpiarCampos() {
        tfEmail.clear();
        cbPlataforma.setValue(null);
        cbAdministrador.setSelected(false);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


    private void mostrarDetallePersona(Persona p) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Ficha de Usuario");

        // 🔑 CORRECCIÓN 6: Usar la propiedad booleana correcta
        String esAdmin = p.getAdministrador() ? "Sí" : "No";

        alerta.setHeaderText("Email: " + p.getEmail());
        alerta.setContentText("Plataforma: " + p.getPlataforma() +
                "\nAdministrador: " + esAdmin+ " (Hora de creación del usuario: "+p.getFechaCreacion().toString()+")"+ " VERSIÓN: " +p.getVersion());
        alerta.showAndWait();
    }

    @FXML
    public void VaciarTabla(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Eliminación");
        confirm.setHeaderText("¿Estás seguro de que deseas eliminar TODOS los usuarios?");
        confirm.setContentText("Esta acción eliminará todos los registros de la tabla.");

        ButtonType result = confirm.showAndWait().orElse(ButtonType.CANCEL);


        if (result == ButtonType.OK) {

            listaPersonas.clear();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Tabla Vaciada",
                    "Todos los usuarios han sido eliminados de la lista.");
        }
    }
}