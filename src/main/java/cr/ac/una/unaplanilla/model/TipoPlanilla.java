package cr.ac.una.unaplanilla.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 *
 * @author Bradley
 */
@Entity
@Table(name = "PLAM_TIPOPLANILLAS")
@NamedQueries({
    @NamedQuery(name = "TipoPlanilla.findAll", query = "SELECT t FROM TipoPlanilla t"),
    @NamedQuery(name = "TipoPlanilla.findById", query = "SELECT t FROM TipoPlanilla t WHERE t.id = :id"),
    /*@NamedQuery(name = "TipoPlanilla.findByTplaCodigo", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaCodigo = :tplaCodigo"),
    @NamedQuery(name = "TipoPlanilla.findByTplaDescripcion", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaDescripcion = :tplaDescripcion"),
    @NamedQuery(name = "TipoPlanilla.findByTplaPlaxmes", query = "SELECT t FROM TipoPlanilla t WHERE t.planillasMensuales = :planillasMensuales"),
    @NamedQuery(name = "TipoPlanilla.findByTplaAnoultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.annioUltimaPlanilla = :annioUltimaPlanilla"),
    @NamedQuery(name = "TipoPlanilla.findByTplaMesultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.mesUltimaPlanilla = :mesUltimaPlanilla"),
    @NamedQuery(name = "TipoPlanilla.findByTplaNumultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.numUltimaPlanilla = :numUltimaPlanilla"),
    @NamedQuery(name = "TipoPlanilla.findByTplaEstado", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaEstado = :tplaEstado"),
    @NamedQuery(name = "TipoPlanilla.findByTplaVersion", query = "SELECT t FROM TipoPlanilla t WHERE t.plaVersion = :plaVersion")/*/})
public class TipoPlanilla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PLAM_TIPOPLANILLAS_TPLA_ID_GENERATOR", sequenceName = "una.PLAM_TIPOPLANILLAS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_TIPOPLANILLAS_TPLA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "TPLA_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "TPLA_CODIGO")
    private String plaCodigo;
    @Basic(optional = false)
    @Column(name = "TPLA_DESCRIPCION")
    private String plaDescripcion;
    @Basic(optional = false)
    @Column(name = "TPLA_PLAXMES")
    private Integer planillasMensuales;
    @Column(name = "TPLA_ANOULTPLA")
    private Integer annioUltimaPlanilla;
    @Column(name = "TPLA_MESULTPLA")
    private Integer mesUltimaPlanilla;
    @Column(name = "TPLA_NUMULTPLA")
    private Integer numUltimaPlanilla;
    @Basic(optional = false)
    @Column(name = "TPLA_ESTADO")
    private String estado;
    @Version
    @Column(name = "TPLA_VERSION")
    private Long version;
    @JoinTable(name = "PLAM_EMPLEADOSPLANILLA", joinColumns = {
        @JoinColumn(name = "EXP_IDTPLA", referencedColumnName = "TPLA_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EXP_IDEMP", referencedColumnName = "EMP_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Empleado> empleados;

    public TipoPlanilla() {
    }

    public TipoPlanilla(Long tplaId) {
        this.id = tplaId;
    }

    public TipoPlanilla(Long tplaId, String tplaCodigo, String tplaDescripcion, Integer planillasMensuales, String tplaEstado, Long plaVersion) {
        this.id = tplaId;
        this.plaCodigo = tplaCodigo;
        this.plaDescripcion = tplaDescripcion;
        this.planillasMensuales = planillasMensuales;
        this.estado = tplaEstado;
        this.version = plaVersion;
    }
    
    public TipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        this.id = tipoPlanillaDto.getId();
        actualizar(tipoPlanillaDto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return plaCodigo;
    }

    public void setCodigo(String plaCodigo) {
        this.plaCodigo = plaCodigo;
    }

    public String getDescripcion() {
        return plaDescripcion;
    }

    public void setDescripcion(String plaDescripcion) {
        this.plaDescripcion = plaDescripcion;
    }

    public Integer getPlanillasMensuales() {
        return planillasMensuales;
    }

    public void setPlanillasMensuales(Integer planillasMensuales) {
        this.planillasMensuales = planillasMensuales;
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
        return estado;
    }

    public void setEstado(String plaEstado) {
        this.estado = plaEstado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long plaVersion) {
        this.version = plaVersion;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }    
    
     public void actualizar(TipoPlanillaDto tipoPlanillaDto) {
        this.plaCodigo = tipoPlanillaDto.getCodigo();
        this.plaDescripcion = tipoPlanillaDto.getDescripcion();
        this.planillasMensuales = tipoPlanillaDto.getPlanillasMensuales();
        this.estado = tipoPlanillaDto.getEstado();
        this.version = tipoPlanillaDto.getVersion();
     }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlanilla)) {
            return false;
        }
        TipoPlanilla other = (TipoPlanilla) object;
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
