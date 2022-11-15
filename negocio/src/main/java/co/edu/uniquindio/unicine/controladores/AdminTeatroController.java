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



}
