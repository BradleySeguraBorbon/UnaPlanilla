package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.Empleado;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.EntityManagerHelper;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Bradley
 */
public class EmpleadoService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getUsuario(String usuario, String clave) {
        try {
            Query qryUsuario = em.createNamedQuery("Empleado.findByUsuarioClave", Empleado.class);
            qryUsuario.setParameter("usuario", usuario);
            qryUsuario.setParameter("clave", clave);
            EmpleadoDto empleadoDto = new EmpleadoDto((Empleado)qryUsuario.getSingleResult());
            return new Respuesta(true, "", "", "Usuario", empleadoDto); 
       } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con las credenciales ingresadas.", "getUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el usuario.", "getUsuario NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta getEmpleado(Long id) {
        try {
            Query qryEmpleado = em.createNamedQuery("Empleado.findById", Empleado.class);
            qryEmpleado.setParameter("id", id);
            EmpleadoDto empleadoDto = new EmpleadoDto((Empleado)qryEmpleado.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleadoDto); 
       } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con las credenciales ingresadas.", "getEmpleado NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el empleado.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el empleado.", "getEmpleado NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el empleado.", "getEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta getEmpleadoByParameters(String cedula, String nombre, String primerApellido, String segundoApellido) {
        try {
            Query qryUsuario = em.createNamedQuery("Empleado.findByParameters", Empleado.class);
            qryUsuario.setParameter("cedula", "%" + cedula + "%");
            qryUsuario.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            qryUsuario.setParameter("primerApellido", "%" + primerApellido.toLowerCase() + "%");
            qryUsuario.setParameter("segundoApellido", "%" + segundoApellido.toLowerCase() + "%");
            List<Empleado> empleados = (List<Empleado>)qryUsuario.getResultList();
            List<EmpleadoDto> empleadosDto = new ArrayList<>();
            for(Empleado empleado : empleados) {
                empleadosDto.add(new EmpleadoDto(empleado));
            }
            return new Respuesta(true, "", "", "Empleados", empleadosDto); 
       } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un empleado con las credenciales ingresadas.", "getEmpleadoByParameters NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el usuario.", "getUsuario NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + cedula + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta guardarEmpleado(EmpleadoDto empleadoDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Empleado empleado;
            if(empleadoDto.getId() != null && empleadoDto.getId() > 0) {
                empleado = em.find(Empleado.class, empleadoDto.getId());
                if(empleado == null) {
                    return new Respuesta(false, "No se encontró el empleado a guardar", "guardarEmpleado notResultException");
                }
                empleado.actualizar(empleadoDto);
                em.merge(empleado);
            } else {
                empleado = new Empleado(empleadoDto);
                em.persist(empleado);
            }
            et.commit();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDto(empleado));
        } catch(Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el empleado", ex);
            return new Respuesta(false, "Error guardando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarEmpleado(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            Empleado empleado;
            if(id != null && id > 0) {
                empleado = em.find(Empleado.class, id);
                if(empleado == null) {
                    return new Respuesta(false, "No se encontró el empleado a eliminar", "eliminarEmpleado notResultException");
                }
                em.remove(empleado);
            } else {
                return new Respuesta(false, "Favor consultar el empleado a eliminar", "eliminarEmpleado NoResultException");
            }
            et.commit();
            return new Respuesta(true, "", "");
        } catch(Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado", ex);
            return new Respuesta(false, "Error eliminando el empleado.", "eliminarEmpleado " + ex.getMessage());
        }
    }
}
