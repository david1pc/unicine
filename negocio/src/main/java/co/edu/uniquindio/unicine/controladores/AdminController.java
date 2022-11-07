package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.Cupon;
import co.edu.uniquindio.unicine.entidades.Imagen;
import co.edu.uniquindio.unicine.entidades.Pelicula;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
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
            Map<String, String> respuesta = cloudinaryServicio.subirImagen(imagen, "peliculas");
            Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
            Imagen img = this.imagenRepo.save(nueva_imagen);
            pelicula_nueva.setImagen(img);
            Pelicula nueva = this.adminServicio.crearPelicula(pelicula_nueva);
            response.put("pelicula", nueva);
            response.put("mensaje", "Se ha creado la pelicula con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/peliculas-data/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula pelicula){
        Map<String, Object> response = new HashMap<>();

        try {
            Pelicula nueva = this.adminServicio.crearPelicula(pelicula);
            response.put("pelicula", nueva);
            response.put("mensaje", "Se ha creado la pelicula con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

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
            if(pelicula_update.getImagen() != null){
                this.cloudinaryServicio.eliminarImagen(pelicula_update.getImagen().getImagenId());
                this.imagenRepo.eliminarById(pelicula_update.getImagen().getCodigo());
            }
            Map<String, String> respuesta = this.cloudinaryServicio.subirImagen(imagen, "peliculas");
            Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
            Imagen img = this.imagenRepo.save(nueva_imagen);
            pelicula_update.setImagen(img);
            Pelicula pelicula_actual = this.adminServicio.actualizarPelicula(pelicula_update);
            response.put("pelicula", pelicula_actual);
            response.put("mensaje", "Se ha actualizado la pelicula con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/peliculas-data/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarPelicula(@RequestBody Pelicula pelicula){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando pelicula... " + pelicula);

        try {
            Pelicula pelicula_actual = this.adminServicio.actualizarPelicula(pelicula);
            response.put("pelicula", pelicula_actual);
            response.put("mensaje", "Se ha actualizado la pelicula con exito!");
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

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
}
