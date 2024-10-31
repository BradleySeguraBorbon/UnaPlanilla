package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.Empleado;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanilla;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.util.EntityManagerHelper;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bradley
 */
public class TipoPlanillaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getTipoPlanilla(Long id) {
        try {
            Query qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findById", TipoPlanilla.class);
            qryTipoPlanilla.setParameter("id", id);
            TipoPlanilla tipoPlanilla = (TipoPlanilla) qryTipoPlanilla.getSingleResult();
            TipoPlanillaDto tipoPlanillaDto = new TipoPlanillaDto(tipoPlanilla);
            for (Empleado emp : tipoPlanilla.getEmpleados()) {
                tipoPlanillaDto.getEmpleados().add(new EmpleadoDto(emp));
            }
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con las credenciales ingresadas.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el tipoPlanilla.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el tipoPlanilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipoPlanilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            et = em.getTransaction();
            et.begin();
            TipoPlanilla tipoPlanilla;
            if (tipoPlanillaDto.getId() != null && tipoPlanillaDto.getId() > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, tipoPlanillaDto.getId());
                if (tipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró el tipoPlanilla a guardar", "guardarTipoPlanilla notResultException");
                }
                tipoPlanilla.actualizar(tipoPlanillaDto);
                for (EmpleadoDto emp : tipoPlanillaDto.getEmpleadosEliminados()) {
                    tipoPlanilla.getEmpleados().remove(new Empleado(emp.getId()));
                }
                if (!tipoPlanillaDto.getEmpleados().isEmpty()) {
                    for (EmpleadoDto emp : tipoPlanillaDto.getEmpleados()) {
                        if (emp.getModificado()) {
                            Empleado empleado = em.find(Empleado.class, emp.getId());
                            empleado.getTipoPlanillaList().add(tipoPlanilla);
                            tipoPlanilla.getEmpleados().add(empleado);
                        }
                    }
                }
                em.merge(tipoPlanilla);
            } else {
                tipoPlanilla = new TipoPlanilla(tipoPlanillaDto);
                em.persist(tipoPlanilla);
            }
            et.commit();
            return new Respuesta(true, "", "", "TipoPlanilla", new TipoPlanillaDto(tipoPlanilla));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando el tipoPlanilla", ex);
            return new Respuesta(false, "Error guardando el tipoPlanilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            TipoPlanilla tipoPlanilla;
            if (id != null && id > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, id);
                if (tipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró el tipoPlanilla a eliminar", "eliminarTipoPlanilla notResultException");
                }
                em.remove(tipoPlanilla);
            } else {
                return new Respuesta(false, "Favor consultar el tipoPlanilla a eliminar", "eliminarTipoPlanilla NoResultException");
            }
            et.commit();
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error eliminando el tipoPlanilla", ex);
            return new Respuesta(false, "Error eliminando el tipoPlanilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }

}
