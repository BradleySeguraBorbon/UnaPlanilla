/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bradley
 */
public class LogInController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imvFondo;
    @FXML
    private MFXTextField txfUsuario;
    @FXML
    private MFXPasswordField psfClave;
    @FXML
    private MFXButton btnCancelar;
    @FXML
    private MFXButton btnIngresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        ((Stage) this.btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        if (this.txfUsuario.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(), "Es necesario digitar un usuario para ingresar al sistema");
        } else if (this.psfClave.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(), "Es necesario digitar una clave para ingresar al sistema");
        } else {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getUsuario(txfUsuario.getText(), psfClave.getText());
            if (respuesta.getEstado()) {
                AppContext.getInstance().set("Usuario", respuesta.getResultado("Usuario"));
                FlowController.getInstance().goMain();
                ((Stage) this.btnCancelar.getScene().getWindow()).close();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(), respuesta.getMensaje());
            }
        }

    }

    @Override
    public void initialize() {
        this.txfUsuario.clear();
        this.psfClave.clear();
    }

}
