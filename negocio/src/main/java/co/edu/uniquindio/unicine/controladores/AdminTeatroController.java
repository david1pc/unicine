package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.AdministradorRepo;
import co.edu.uniquindio.unicine.repo.CiudadRepo;
import co.edu.uniquindio.unicine.repo.ImagenRepo;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import co.edu.uniquindio.unicine.servicios.CloudinaryServicio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin-teatro")
public class AdminTeatroController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdministradorRepo adminRepo;

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Autowired
    CloudinaryServicio cloudinaryServicio;

    @Autowired
    ImagenRepo imagenRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    @GetMapping("/ciudades/")
    public ResponseEntity<?> obtenerCiudades(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Ciudad> ciudades = ciudadRepo.findAll();
            logger.info("Enviando ciudades: " + ciudades);
            response.put("ciudades", ciudades);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar las ciudades");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ciudades/")
    public ResponseEntity<?> buscarCiudad(@RequestBody Ciudad ciudad){
        Map<String, Object> response = new HashMap<>();

        try {
            String str = "%" + ciudad.getNombre() + "%";
            List<Ciudad> ciudades = ciudadRepo.findByNombre(str);
            logger.info("Enviando ciudades: " + ciudades);
            response.put("ciudades", ciudades);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar la ciudad");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/teatro/")
    public ResponseEntity<?> obtenerTeatros(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Teatro> teatros = this.adminTeatroServicio.listarTeatros();
            teatros.forEach(teatro -> teatro.setSalas(null));
            logger.info("Enviando teatros: " + teatros);
            response.put("teatros", teatros);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar los teatros");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/teatro/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearTeatro(@RequestBody Teatro teatro){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando teatro... " + teatro);

        try {
            Teatro teatro_nuevo = this.adminTeatroServicio.crearTeatro(teatro);
            response.put("teatro", teatro_nuevo);
            response.put("mensaje", "El teatro ha sido creado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el teatro");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PutMapping("/teatro/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarTeatro(@RequestBody Teatro teatro){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando cupon... " + teatro);

        try {
            Teatro nuevo_teatro = this.adminTeatroServicio.actualizarTeatro(teatro);
            response.put("teatro", nuevo_teatro);
            response.put("mensaje", "El teatro ha sido actualizado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el teatro");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/teatro/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarTeatro(@RequestBody Integer teatros_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando teatros... " + teatros_ids);

        for (int i = 0; i < teatros_ids.length; i++){
            Integer id = teatros_ids[i].intValue();
            try {
                this.adminTeatroServicio.eliminarTeatro(id);
                response.put("mensaje", "El teatro ha sido eliminado con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar el teatro " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(teatros_ids.length > 1){
            response.put("mensaje", "Se han eliminado los teatros con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/distribucion/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearDistribucion(@RequestBody DistribucionSillas distribucionSillas){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando distribucion de sillas... " + distribucionSillas);

        try {
            DistribucionSillas nueva = this.adminTeatroServicio.crearDistribucionSillas(distribucionSillas);
            response.put("distribucion", nueva);
            response.put("mensaje", "La distribucion de sillas ha sido creado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la distribucion de sillas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PutMapping("/distribucion/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarDistribucionSillas(@RequestBody DistribucionSillas distribucionSillas){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando distribucion de sillas... " + distribucionSillas);

        try {
            DistribucionSillas nueva = this.adminTeatroServicio.actualizarDistribucionSillas(distribucionSillas);
            response.put("distribucion", nueva);
            response.put("mensaje", "La distribucion de sillas ha sido actualizada con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la distribucion de sillas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/distribucion/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarDistribucionSillas(@RequestBody Integer distribucion_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando distribuciones de sillas... " + distribucion_ids);

        for (int i = 0; i < distribucion_ids.length; i++){
            Integer id = distribucion_ids[i].intValue();
            try {
                this.adminTeatroServicio.eliminarDistribucionSillas(id);
                response.put("mensaje", "La distribucion de sillas ha sido eliminada con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar la distribucion de sillas " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(distribucion_ids.length > 1){
            response.put("mensaje", "Se han eliminado las distribuciones de sillas con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/distribucion/")
    public ResponseEntity<?> obtenerDistribucionesSillas(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<DistribucionSillas> distribucionSillas = this.adminTeatroServicio.listarDistribucionSillas();
            logger.info("Enviando las distribuciones de sillas: " + distribucionSillas);
            response.put("distribucion", distribucionSillas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar las distribuciones de sillas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/salas/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearSala(@RequestBody Sala sala){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando sala... " + sala);

        try {
            Sala nueva = this.adminTeatroServicio.crearSala(sala);
            response.put("sala", nueva);
            response.put("mensaje", "La sala ha sido creado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la sala");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PutMapping("/salas/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarSalas(@RequestBody Sala sala){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando sala... " + sala);

        try {
            Sala nueva = this.adminTeatroServicio.actualizarSala(sala);
            response.put("sala", nueva);
            response.put("mensaje", "La sala ha sido actualizada con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la sala");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/salas/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarSalas(@RequestBody Integer salas_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando salas... " + salas_ids);

        for (int i = 0; i < salas_ids.length; i++){
            Integer id = salas_ids[i].intValue();
            try {
                this.adminTeatroServicio.eliminarSala(id);
                response.put("mensaje", "La sala ha sido eliminada con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar la sala " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(salas_ids.length > 1){
            response.put("mensaje", "Se han eliminado las salas con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/salas/")
    public ResponseEntity<?> obtenerSalas(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Sala> salas = this.adminTeatroServicio.listarSalas();
            logger.info("Enviando las salas: " + salas);
            response.put("salas", salas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar las salas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/horario/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearHorario(@RequestBody Horario horario){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando horario... " + horario);

        try {
            Horario nuevo = this.adminTeatroServicio.crearHorario(horario);
            response.put("horario", nuevo);
            response.put("mensaje", "El horario ha sido creado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el horario");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PutMapping("/horario/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarHorario(@RequestBody Horario horario){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando horario... " + horario);

        try {
            Horario nuevo = this.adminTeatroServicio.actualizarHorario(horario);
            response.put("horario", nuevo);
            response.put("mensaje", "El horario ha sido actualizado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la sala");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/horario/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarHorarios(@RequestBody Integer horarios_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando horarios... " + horarios_ids);

        for (int i = 0; i < horarios_ids.length; i++){
            Integer id = horarios_ids[i].intValue();
            try {
                this.adminTeatroServicio.eliminarHorario(id);
                response.put("mensaje", "El horario ha sido eliminada con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar el horario " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(horarios_ids.length > 1){
            response.put("mensaje", "Se han eliminado los horarios con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/horario/")
    public ResponseEntity<?> obtenerHorarios(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Horario> horarios = this.adminTeatroServicio.listarHorarios();
            logger.info("Enviando los horarios: " + horarios);
            response.put("horarios", horarios);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar los horarios");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/funcion/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearFuncion(@RequestBody Funcion funcion){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando funcion... " + funcion);

        try {
            Funcion nueva = this.adminTeatroServicio.crearFuncion(funcion);
            response.put("funcion", nueva);
            response.put("mensaje", "La funcion ha sido creada con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la funcion");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PutMapping("/funcion/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarFuncion(@RequestBody Funcion funcion){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando funcion... " + funcion);

        try {
            Funcion nueva = this.adminTeatroServicio.actualizarFuncion(funcion);
            response.put("funcion", nueva);
            response.put("mensaje", "La funcion ha sido actualizada con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la funcion");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN_TEATRO')")
    @PostMapping("/funcion/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarFunciones(@RequestBody Integer funciones_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando funciones... " + funciones_ids);

        for (int i = 0; i < funciones_ids.length; i++){
            Integer id = funciones_ids[i].intValue();
            try {
                this.adminTeatroServicio.eliminarFuncion(id);
                response.put("mensaje", "La funcion ha sido eliminada con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar la funcion " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(funciones_ids.length > 1){
            response.put("mensaje", "Se han eliminado las funciones con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/funcion/")
    public ResponseEntity<?> obtenerFunciones(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Funcion> funciones = this.adminTeatroServicio.listarFunciones();
            logger.info("Enviando las funciones: " + funciones);
            response.put("funciones", funciones);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar las funciones");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }
}
