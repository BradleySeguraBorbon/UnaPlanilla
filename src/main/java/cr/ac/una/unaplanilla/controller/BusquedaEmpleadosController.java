/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Bradley
 */
public class BusquedaEmpleadosController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtPrimerApellido;
    @FXML
    private MFXTextField txtSegundoApellido;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private TableView<EmpleadoDto> tbvEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcCedula;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcNombre;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcPrimerApellido;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcSegundoApellido;
    @FXML
    private MFXButton btnAceptar;

    private Long resultado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tbcCedula.setCellValueFactory(cd -> cd.getValue().cedula);
        this.tbcNombre.setCellValueFactory(cd -> cd.getValue().nombre);
        this.tbcPrimerApellido.setCellValueFactory(cd -> cd.getValue().primerApellido);
        this.tbcSegundoApellido.setCellValueFactory(cd -> cd.getValue().segundoApellido);
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onMousePressedTbvEmpleados(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            onActionBtnAceptar(null);
        }
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        buscarEmpleados();
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        EmpleadoDto empleadoSeleccionado = this.tbvEmpleados.getSelectionModel().getSelectedItem();
        this.resultado = empleadoSeleccionado.getId();
        getStage().close();
    }

    public Long getResultado() {
        return this.resultado;
    }

    private void buscarEmpleados() {
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleadoByParameters(txtCedula.getText(), txtNombre.getText(), txtPrimerApellido.getText(), txtSegundoApellido.getText());

            if (respuesta.getEstado()) {
                this.tbvEmpleados.getItems().clear();
                this.tbvEmpleados.setItems(FXCollections.observableArrayList((ArrayList<EmpleadoDto>) respuesta.getResultado("Empleados")));
                this.tbvEmpleados.refresh();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Busqueda Empleados", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error buscando los empleados", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "BusquedaEmpleados", getStage(), "Ocurrio un error buscando los empleados.");
        }

    }

}
