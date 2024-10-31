package cr.ac.una.unaplanilla.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bradley
 */
public class TipoPlanillaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    public SimpleStringProperty id;
    public SimpleStringProperty codigo;
    public SimpleStringProperty descripcion;
    public SimpleStringProperty planillasMensuales;
    public Integer annioUltimaPlanilla;
    public Integer mesUltimaPlanilla;
    public Integer numUltimaPlanilla;
    public SimpleBooleanProperty estado;
    public Long version;
    public ObservableList<EmpleadoDto> empleados;
    private List<EmpleadoDto> empleadosEliminados;
    public Boolean modificado;

    public TipoPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillasMensuales = new SimpleStringProperty("");
        this.annioUltimaPlanilla = 0;
        this.mesUltimaPlanilla = 0;
        this.numUltimaPlanilla = 0;
        this.estado = new SimpleBooleanProperty(true);
        this.modificado = false;
        this.empleados = FXCollections.observableArrayList();
        this.empleadosEliminados = new ArrayList<>();
    }

    public TipoPlanillaDto(TipoPlanilla tipoPlanilla) {
        this();
        this.id.set(tipoPlanilla.getId().toString());
        this.codigo.set(tipoPlanilla.getCodigo());
        this.descripcion.set(tipoPlanilla.getDescripcion());
        this.planillasMensuales.set(tipoPlanilla.getPlanillasMensuales().toString());
        this.annioUltimaPlanilla = tipoPlanilla.getAnnioUltimaPlanilla();
        this.mesUltimaPlanilla = tipoPlanilla.getMesUltimaPlanilla();
        this.numUltimaPlanilla = tipoPlanilla.getNumUltimaPlanilla();
        this.estado.set(tipoPlanilla.getEstado().equals("A"));
        this.version = tipoPlanilla.getVersion();
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public Integer getPlanillasMensuales() {
        return Integer.parseInt(planillasMensuales.get());
    }

    public void setPlanillasMensuales(Integer planillasMensuales) {
        this.planillasMensuales.set(planillasMensuales.toString());
    }

    public Integer getAnnioUltimaPlanilla() {
        return annioUltimaPlanilla;
    }

    public void setAnnioUltimaPlanilla(Integer annioUltimaPlanilla) {
        this.annioUltimaPlanilla = annioUltimaPlanilla;
    }

    public Integer getMesUltimaPlanilla() {
        return mesUltimaPlanilla;
    }

    public void setMesUltimaPlanilla(Integer mesUltimaPlanilla) {
        this.mesUltimaPlanilla = mesUltimaPlanilla;
    }

    public Integer getNumUltimaPlanilla() {
        return numUltimaPlanilla;
    }

    public void setNumUltimaPlanilla(Integer numUltimaPlanilla) {
        this.numUltimaPlanilla = numUltimaPlanilla;
    }

    public String getEstado() {
        return estado.get() ? "A" : "I";
    }

    public void setEstado(String estado) {
        this.estado.set(estado.equalsIgnoreCase("A"));
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ObservableList<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ObservableList<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = empleadosEliminados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlanillaDto)) {
            return false;
        }
        TipoPlanillaDto other = (TipoPlanillaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.TipoPlanilla[ tplaId=" + id + " ]";
    }

}
