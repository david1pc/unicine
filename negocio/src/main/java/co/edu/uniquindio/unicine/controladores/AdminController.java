package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.AdministradorRepo;
import co.edu.uniquindio.unicine.repo.ImagenRepo;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import co.edu.uniquindio.unicine.servicios.CloudinaryServicio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/peliculas/")
    public ResponseEntity<?> obtenerPeliculas(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Pelicula> peliculas = this.adminServicio.listarPeliculas();
            logger.info("Enviando peliculas: " + peliculas);
            response.put("peliculas", peliculas);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar las peliculas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/peliculas/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearPelicula(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "pelicula") String pelicula){
        Map<String, Object> response = new HashMap<>();
        Pelicula pelicula_nueva = null;

        try {
            pelicula_nueva = new ObjectMapper().readValue(pelicula, Pelicula.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al crear la pelicula");
            response.put("error", "No se reconocen los datos de la pelicula");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            if(imagen.isEmpty()){
                imagen = null;
            }
            Pelicula nueva = this.adminServicio.crearPelicula(pelicula_nueva, imagen);
            response.put("pelicula", nueva);
            response.put("mensaje", "Se ha creado la pelicula con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/peliculas/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarPelicula(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "pelicula") String pelicula){
        Map<String, Object> response = new HashMap<>();
        Pelicula pelicula_update = null;

        try {
            pelicula_update = new ObjectMapper().readValue(pelicula, Pelicula.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al actualizar la pelicula");
            response.put("error", "No se reconocen los datos de la pelicula");
        }

        logger.info("Actualizando pelicula... " + pelicula_update);

        try {
            if(imagen.isEmpty()){
                imagen = null;
            }
            Pelicula pelicula_actual = this.adminServicio.actualizarPelicula(pelicula_update, imagen);
            response.put("pelicula", pelicula_actual);
            response.put("mensaje", "Se ha actualizado la pelicula con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/peliculas/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarPelicula(@RequestBody Integer peliculas_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando peliculas... " + peliculas_ids);

        for (int i = 0; i < peliculas_ids.length; i++){
            Integer id = peliculas_ids[i].intValue();
            try {
                Pelicula pelicula = this.adminServicio.obtenerPelicula(id);
                if(pelicula.getImagen() != null){
                    this.cloudinaryServicio.eliminarImagen(pelicula.getImagen().getImagenId());
                    this.adminServicio.eliminarPelicula(id);
                    this.imagenRepo.deleteById(pelicula.getImagen().getCodigo());
                }else{
                    this.adminServicio.eliminarPelicula(id);
                }
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar la pelicula ");
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        response.put("mensaje", "Se ha eliminado las peliculas con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cupones/")
    public ResponseEntity<?> obtenerCupones(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Cupon> cupones = this.adminServicio.listarCupones();
            response.put("cupones", cupones);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar los cupones");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cupones/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearCupones(@RequestBody Cupon cupon){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando cupon... " + cupon);

        try {
            Cupon cupon_nuevo = this.adminServicio.crearCupon(cupon);
            response.put("cupon", cupon_nuevo);
            response.put("mensaje", "El cupon ha sido creado con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el cupon");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/cupones/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarCupon(@RequestBody Cupon cupon){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando cupon... " + cupon);

        try {
            Cupon nuevo_cupon = this.adminServicio.actualizarCupon(cupon);
            response.put("cupon", nuevo_cupon);
            response.put("mensaje", "El cupon ha sido actualizado con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el cupon");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cupones/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarCupon(@RequestBody Integer cupones_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando cupones... " + cupones_ids);

        for (int i = 0; i < cupones_ids.length; i++){
            Integer id = cupones_ids[i].intValue();
            try {
                this.adminServicio.eliminarCupon(id);
                response.put("mensaje", "El cupon ha sido eliminado con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar el cupon " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        response.put("mensaje", "Se han eliminado los cupones con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/teatro")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMIN_TEATRO')")
    public ResponseEntity<?> obtener_admins_teatro(){
        Map<String, Object> response = new HashMap<>();

        try{
            List<AdministradorTeatro> adminsTeatros = this.adminServicio.listarAdministradoresTeatro();
            response.put("admins_teatro", adminsTeatros);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("mensaje", "Error al obtener los administradores de teatro");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/teatro")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearAdminTeatro(@RequestBody AdministradorTeatro administradorTeatro){
        Map<String, Object> response = new HashMap<>();

        try{
            AdministradorTeatro  nuevoAdministradorTeatro = this.adminServicio.crearAdministradorTeatro(administradorTeatro);
            response.put("mensaje", "El administrador de teatro ha sido creado con exito");
            response.put("admin_teatro", nuevoAdministradorTeatro);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }catch(Exception e){
            response.put("mensaje", "Error al crear un administrador de teatro");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/teatro")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> actualizarAdminTeatro(@RequestBody AdministradorTeatro administradorTeatro){
        Map<String, Object> response = new HashMap<>();

        try{
            AdministradorTeatro nuevoAdminTeatro = this.adminServicio.actualizarAdministradorTeatro(administradorTeatro);
            response.put("mensaje", "El administrador de teatro ha sido actualizado con exito");
            response.put("admin_teatro", nuevoAdminTeatro);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }catch(Exception e){
            response.put("mensaje", "Error al actualizar un administrador de teatro");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/teatro/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarAdminTeatro(@RequestBody Integer adminTeatro_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando admins teatro... " + adminTeatro_ids);

        for (int i = 0; i < adminTeatro_ids.length; i++){
            Integer id = adminTeatro_ids[i].intValue();
            try {
                this.adminServicio.eliminarAdministradorTeatro(id);
                response.put("mensaje", "El administrador teatro se ha eliminado con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar el administrador teatro " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(adminTeatro_ids.length > 1){
            response.put("mensaje", "Se han eliminado los administradores de teatro con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/confiteria/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearConfiteria(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "confiteria") String confiteria){
        Map<String, Object> response = new HashMap<>();
        Confiteria confiteria_nueva = null;

        try {
            confiteria_nueva = new ObjectMapper().readValue(confiteria, Confiteria.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al crear la confiteria");
            response.put("error", "No se reconocen los datos de la confiteria");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            if(imagen.isEmpty()){
                imagen = null;
            }
            Confiteria nueva = this.adminServicio.crearConfiteria(confiteria_nueva, imagen);
            response.put("confiteria", nueva);
            response.put("mensaje", "Se ha creado la confiteria con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la confiteria");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/confiteria/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarConfiteria(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "confiteria") String confiteria){
        Map<String, Object> response = new HashMap<>();
        Confiteria confiteria_update = null;

        try {
            confiteria_update = new ObjectMapper().readValue(confiteria, Confiteria.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al actualizar la confiteria");
            response.put("error", "No se reconocen los datos de la confiteria");
        }

        logger.info("Actualizando confiteria... " + confiteria_update);

        try {
            if(imagen.isEmpty()){
               imagen = null;
            }
            Confiteria confiteria_nueva = this.adminServicio.actualizarConfiteria(confiteria_update, imagen);
            response.put("confiteria", confiteria_nueva);
            response.put("mensaje", "Se ha actualizado la confiteria con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la confiteria");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/confiteria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obtener_confiterias(){
        Map<String, Object> response = new HashMap<>();

        try{
            List<Confiteria> confiterias = this.adminServicio.listarConfiteria();
            response.put("confiterias", confiterias);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("mensaje", "Error al obtener las confiterias");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/confiteria/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarConfiteria(@RequestBody Integer confiteria_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando confiterias_ids... " + confiteria_ids);

        for (int i = 0; i < confiteria_ids.length; i++){
            Integer id = confiteria_ids[i].intValue();
            try {
                this.adminServicio.eliminarConfiteria(id);
                response.put("mensaje", "La confiteria se ha eliminado con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar la confiteria con id: " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(confiteria_ids.length > 1){
            response.put("mensaje", "Se han eliminado las confiterias con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/combos/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearCombo(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "combo") String combo){
        Map<String, Object> response = new HashMap<>();
        Combo combo_nuevo = null;

        try {
            combo_nuevo = new ObjectMapper().readValue(combo, Combo.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al crear la confiteria");
            response.put("error", "No se reconocen los datos de la confiteria");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            if(imagen.isEmpty()){
                imagen = null;
            }
            Combo nuevo = this.adminServicio.crearCombo(combo_nuevo, imagen);
            response.put("combo", nuevo);
            response.put("mensaje", "Se ha creado el combo con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el combo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/combos/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarCombo(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "combo") String combo){
        Map<String, Object> response = new HashMap<>();
        Combo combo_update = null;

        try {
            combo_update = new ObjectMapper().readValue(combo, Combo.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al actualizar el combo");
            response.put("error", "No se reconocen los datos del combo");
        }

        logger.info("Actualizando combo... " + combo_update);

        try {
            if(imagen.isEmpty()){
                imagen = null;
            }
            Combo confiteria_nueva = this.adminServicio.actualizarCombo(combo_update, imagen);
            response.put("combo", confiteria_nueva);
            response.put("mensaje", "Se ha actualizado el combo con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el combo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/combos/eliminar")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> eliminarCombo(@RequestBody Integer combos_ids []){
        Map<String, Object> response = new HashMap<>();
        logger.info("Eliminando combos_ids... " + combos_ids);

        for (int i = 0; i < combos_ids.length; i++){
            Integer id = combos_ids[i].intValue();
            try {
                this.adminServicio.eliminarCombo(id);
                response.put("mensaje", "El combo se ha eliminado con exito!");
            } catch (Exception e) {
                response.put("mensaje", "Error al eliminar el combo con id: " + id);
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        if(combos_ids.length > 1){
            response.put("mensaje", "Se han eliminado los combos con exito");
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/combos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obtener_combos(){
        Map<String, Object> response = new HashMap<>();

        try{
            List<Combo> combos = this.adminServicio.listarCombos();
            response.put("combos", combos);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("mensaje", "Error al obtener los combos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }
}
