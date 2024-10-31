package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Bradley
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtCodigo;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXTextField txtPlanillasXMes;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXTextField txtIdEmpleado;
    @FXML
    private MFXTextField txtNombreEmpleado;
    @FXML
    private TableView<EmpleadoDto> tbvListaEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcCodigo;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcNombre;
    @FXML
    private TableColumn<EmpleadoDto, Boolean> tbcEliminar;

    TipoPlanillaDto tipoPlanillaDto;
    EmpleadoDto empleadoDto;
    List<Node> requeridos = new ArrayList<>();
    @FXML
    private MFXButton btnAgregarEmpleado;
    @FXML
    private TabPane tbpTipoPlanilla;
    @FXML
    private Tab tabTipoPlanillas;
    @FXML
    private Tab tabEmpleados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(4));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(40));
        txtPlanillasXMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtPlanillasXMes.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(1));
        tipoPlanillaDto = new TipoPlanillaDto();
        this.empleadoDto = new EmpleadoDto();
        nuevoTipoPlanilla();
        indicarRequeridos();

        this.txtIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());

        this.tbcCodigo.setCellValueFactory(cd -> cd.getValue().id);
        this.tbcNombre.setCellValueFactory(cd -> cd.getValue().nombre);
        this.tbcEliminar.setCellValueFactory((TableColumn.CellDataFeatures<EmpleadoDto, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
        this.tbcEliminar.setCellFactory((TableColumn<EmpleadoDto, Boolean> p) -> new ButtonCell());
        
        this.tbvListaEmpleados.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends EmpleadoDto> observable, EmpleadoDto oldValue, EmpleadoDto newValue) -> {
            unbindEmpleado();
            if (newValue != null) {
                this.empleadoDto = newValue;
                bindEmpleado(false);
            }
        });
    }

    @Override
    public void initialize() {

    }

    private void bindTipoPlanilla(Boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(this.tipoPlanillaDto.id);
        }
        txtCodigo.textProperty().bindBidirectional(tipoPlanillaDto.codigo);
        txtDescripcion.textProperty().bindBidirectional(tipoPlanillaDto.descripcion);
        txtPlanillasXMes.textProperty().bindBidirectional(tipoPlanillaDto.planillasMensuales);
        chkActivo.selectedProperty().bindBidirectional(tipoPlanillaDto.estado);
    }

    private void unbindTipoPlanilla() {
        txtId.textProperty().unbind();
        txtCodigo.textProperty().unbind();
        txtDescripcion.textProperty().unbind();
        txtPlanillasXMes.textProperty().unbind();
        chkActivo.selectedProperty().unbindBidirectional(tipoPlanillaDto.estado);
    }

    private void bindEmpleado(Boolean nuevo) {
        if (!nuevo) {
            txtIdEmpleado.textProperty().bind(empleadoDto.id);
        }
        txtNombreEmpleado.textProperty().bindBidirectional(empleadoDto.nombre);
    }

    private void unbindEmpleado() {
        txtIdEmpleado.textProperty().unbind();
        txtNombreEmpleado.textProperty().unbindBidirectional(empleadoDto.nombre);
    }

    private void nuevoTipoPlanilla() {
        unbindTipoPlanilla();
        tipoPlanillaDto = new TipoPlanillaDto();
        bindTipoPlanilla(true);
        txtId.clear();
        txtId.requestFocus();
        nuevoEmpleado();
        cargarEmpleados();
    }

    private void nuevoEmpleado() {
        unbindEmpleado();
        tbvListaEmpleados.getSelectionModel().select(null);
        empleadoDto = new EmpleadoDto();
        bindEmpleado(true);
        txtIdEmpleado.clear();
        txtIdEmpleado.requestFocus();
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCodigo, txtDescripcion, txtPlanillasXMes));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void cargarTipoPlanilla(Long id) {
        try {
            TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
            Respuesta respuesta = tipoPlanillaService.getTipoPlanilla(id);
            if (respuesta.getEstado()) {
                unbindTipoPlanilla();
                this.tipoPlanillaDto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                bindTipoPlanilla(false);
                validarRequeridos();
                cargarEmpleados();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error consultando el tipo de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar TipoPlanilla", getStage(), "Ocurrio un error consultando el tipo de planilla.");
        }
    }

    private void cargarEmpleados() {
        this.tbvListaEmpleados.getItems().clear();
        this.tbvListaEmpleados.setItems(this.tipoPlanillaDto.getEmpleados());
        this.tbvListaEmpleados.refresh();
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank()) {
            cargarTipoPlanilla(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (this.tabEmpleados.isSelected()) {
            nuevoEmpleado();
        } else if (this.tabTipoPlanillas.isSelected()) {
            if (new Mensaje().showConfirmation("Limpiar Tipo Planilla", getStage(), "¿Está seguro que desea limpiar el registro?")) {
                nuevoTipoPlanilla();
            }
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {

    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.tipoPlanillaDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar TipoPlanilla", getStage(), "Favor consultar el Tipo de Planilla a eliminar");
            } else {
                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.eliminarTipoPlanilla(this.tipoPlanillaDto.getId());
                if (respuesta.getEstado()) {
                    nuevoTipoPlanilla();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar TipoPlanilla", getStage(), "El Tipo de Planilla se eliminó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar TipoPlanilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error eliminando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar TipoPlanilla", getStage(), "Ocurrio un error eliminando el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar TipoPlanilla", getStage(), invalidos);
            } else {
                TipoPlanillaService empleadoService = new TipoPlanillaService();
                Respuesta respuesta = empleadoService.guardarTipoPlanilla(this.tipoPlanillaDto);
                if (respuesta.getEstado()) {
                    unbindTipoPlanilla();
                    this.tipoPlanillaDto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                    bindTipoPlanilla(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar TipoPlanilla", getStage(), "El tipo de planilla se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar TipoPlanilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar TipoPlanilla", getStage(), "Ocurrio un error guardando el tipo de planilla.");
        }
    }

    @FXML
    private void onKeyPressedTxtIdEmpleado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtIdEmpleado.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtIdEmpleado.getText()));
        }
    }

    private void cargarEmpleado(Long id) {
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);
            if (respuesta.getEstado()) {
                unbindEmpleado();
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                bindEmpleado(false);
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error consultando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), "Ocurrio un error consultando el empleado.");
        }
    }

    @FXML
    private void onActionBtnAgregarEmpleado(ActionEvent event) {
        if (this.empleadoDto.getId() == null || this.empleadoDto.getNombre().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Agregar Empleado", getStage(),
                    "Es necesario cargar un empleado para agregarlo a la lista");
        } else if (this.tbvListaEmpleados.getItems() == null
                || this.tbvListaEmpleados.getItems().stream().noneMatch(e -> e.equals(this.empleadoDto))) {
            this.empleadoDto.setModificado(true);
            this.tbvListaEmpleados.getItems().add(this.empleadoDto);
            this.tbvListaEmpleados.refresh();
        }

        nuevoEmpleado();
    }

    @FXML
    private void selectionChangeTabEmp(Event event) {
        if (this.tabEmpleados.isSelected()) {
            if (this.tipoPlanillaDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Tipo Planillas", stage, "Debe cargar el tipo de planilla al que desea agregar empleados");
                tbpTipoPlanilla.getSelectionModel().select(this.tabTipoPlanillas);
            }
        }
    }

    private class ButtonCell extends TableCell<EmpleadoDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            this.cellButton.setPrefWidth(500);
            this.cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");

            this.cellButton.setOnAction((ActionEvent t) -> {
                EmpleadoDto emp = (EmpleadoDto) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                tipoPlanillaDto.getEmpleadosEliminados().add(emp);
                tbvListaEmpleados.getItems().remove(emp);
                tbvListaEmpleados.refresh();
            });

        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
}
