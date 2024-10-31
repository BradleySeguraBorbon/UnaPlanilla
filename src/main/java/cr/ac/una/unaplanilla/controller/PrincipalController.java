/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bradley
 */
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private ImageView imvUnaBackground;
    @FXML
    private ImageView imvUserIcon;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblName;
    @FXML
    private MFXButton btnCerrarSesion;
    @FXML
    private MFXButton btnSalir;

    private EmpleadoDto empleadoDto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.empleadoDto = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        setInformacionUsuario();
    }

    @Override
    public void initialize() {

    }

    @FXML
    public void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadosView");
    }

    @FXML
    public void onActionBtnTiposPlanilla(ActionEvent event) {
        FlowController.getInstance().goView("TiposPlanillaView");
    }

    @FXML
    public void onActionBtnCerrarSesion(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("LogInView");
        onActionBtnSalir(null);
    }

    @FXML
    public void onActionBtnSalir(ActionEvent event) {
        getStage().close();
    }

    public void setInformacionUsuario() {
        this.lblUser.setText(this.empleadoDto.getUsuario());
        this.lblName.setText(this.empleadoDto.getNombre() + " " + this.empleadoDto.getPrimerApellido());
    }

}
