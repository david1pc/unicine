package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.repo.AdministradorRepo;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @GetMapping("/peliculas/")
    public ResponseEntity<?> obtenerPeliculas(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Pelicula> peliculas = this.adminServicio.listarPeliculas();
            response.put("peliculas", peliculas);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar las peliculas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/peliculas/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula pelicula){
        Map<String, Object> response = new HashMap<>();

        logger.info("Agregando pelicula... " + pelicula);

        try {
            Pelicula pelicula_nueva = this.adminServicio.crearPelicula(pelicula);
            response.put("pelicula", pelicula);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la pelicula");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/peliculas/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> actualizarPelicula(@RequestBody Pelicula pelicula){
        Map<String, Object> response = new HashMap<>();

        logger.info("Actualizando pelicula... " + pelicula);

        try {
            Pelicula pelicula_nueva = this.adminServicio.actualizarPelicula(pelicula);
            response.put("pelicula", pelicula);
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
                this.adminServicio.eliminarPelicula(id);
            } catch (Exception e) {
                response.put("mensaje", "Error al crear la pelicula");
                response.put("error", e.getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }

        response.put("mensaje", "Se ha eliminado las peliculas con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
