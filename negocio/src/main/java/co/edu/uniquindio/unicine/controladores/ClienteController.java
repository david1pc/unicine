package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.AdministradorRepo;
import co.edu.uniquindio.unicine.repo.ImagenRepo;
import co.edu.uniquindio.unicine.servicios.*;
import co.edu.uniquindio.unicine.util.Encriptacion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/clientes")
public class ClienteController {

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
    private CloudinaryServicio cloudinaryServicio;

    @Autowired
    private ImagenRepo imagenRepo;

    @Autowired
    private EmailServicio emailServicio;

    @GetMapping("/")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        List<Cliente> clientes = this.clienteServicio.listarClientes();

        response.put("clientes", clientes);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener_cliente(@PathVariable Long id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = this.clienteServicio.obtenerCliente(id.intValue());
        }catch(Exception e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(cliente == null) {
            response.put("mensaje", "El cliente con el ID ".concat(id.toString().concat(" no éxiste en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cliente", cliente);

        System.out.println(cliente);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/registro/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearCliente(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "cliente") String cliente){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente_nuevo = null;

        try {
            cliente_nuevo = new ObjectMapper().readValue(cliente, Cliente.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al registrar el cliente");
            response.put("error", "No se reconocen los datos del cliente");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            Map<String, String> respuesta = cloudinaryServicio.subirImagen(imagen, "clientes");
            Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
            Imagen img = this.imagenRepo.save(nueva_imagen);
            cliente_nuevo.setImagen(img);
            Cliente nuevo = this.clienteServicio.registrarCliente(cliente_nuevo);
            //this.emailServicio.enviarEmail("");
            response.put("cliente", nuevo);
            response.put("mensaje", "El cliente " + nuevo.getPrimerNombre() + ", ha sido registrado con exito!. Se le ha enviado a su correo un enlace para activar la cuenta");
        } catch (Exception e) {
            response.put("mensaje", "Error al intentar registrarse");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registro-data/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        Cliente clienteNuevo = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteNuevo = this.clienteServicio.registrarCliente(cliente);
        }catch(Exception e) {
            response.put("mensaje", "Error al realizar el insert a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente ha sido creado con éxito!");
        response.put("cliente", clienteNuevo);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> actualizarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActual = null;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteActual = this.clienteServicio.actualizarCliente(cliente);
        } catch (Exception e) {
            response.put("mensaje", "No se pudo editar, el cliente con ID ".concat(cliente.getCodigo().toString()));
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(clienteActual == null) {
            response.put("mensaje", "No se pudo editar, el cliente con ID ".concat(cliente.getCodigo().toString().concat(" no éxiste en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente ha sido actualizado con éxito!");
        response.put("cliente", clienteActual);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            this.clienteServicio.eliminarCliente(id.intValue());
        }catch(Exception e) {
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente ha sido eliminado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<?> verCompras(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Compra> compras = this.clienteServicio.listarHistorialCompras(id.intValue());
            response.put("compras", compras);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar el historial de compras");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/login/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody Login login){
        Map<String, Object> response = new HashMap<>();
        try {
            Auth auth = this.clienteServicio.login2(login.getCorreo(), login.getPassword());
            this.logger.info("Enviando auth: " + auth.getCorreo() + " " + auth.getCodigo() + " " + auth.getRol());
            response.put("auth", auth);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar el usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/verificar-usuario/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> verificarUsuario(@RequestBody Auth auth){
        Map<String, Object> response = new HashMap<>();
        Auth autha = new Auth();
        Cliente cliente = null;
        AdministradorTeatro administradorTeatro = null;
        Optional<Administrador>  administrador = null;
        try {
            this.logger.info("Realizando verificacion de usuario " + auth.getRol());

            if(auth.getRol().equals(Rol.CLIENTE)){
                cliente = this.clienteServicio.obtenerCliente(auth.getCodigo());
                auth.setCorreo(cliente.getCorreo());
                auth.setCodigo(cliente.getCodigo());
                auth.setRol(Rol.CLIENTE);
            }else if(auth.getRol().equals(Rol.ADMINISTRADOR)){
                administrador = this.adminRepo.findById(auth.getCodigo());
                auth.setCorreo(administrador.get().getCorreo());
                auth.setCodigo(administrador.get().getCodigo());
                auth.setRol(Rol.ADMINISTRADOR);
            }else if(auth.getRol().equals(Rol.ADMINISTRADOR_TEATRO)){
                administradorTeatro = this.adminServicio.obtenerAdministradorTeatro(auth.getCodigo());
                auth.setCorreo(administradorTeatro.getCorreo());
                auth.setCodigo(administradorTeatro.getCodigo());
                auth.setRol(Rol.ADMINISTRADOR_TEATRO);
            }

            this.logger.info("Enviando auth verificado: " + auth.getCorreo() + " " + auth.getCodigo() + " " + auth.getRol());
            response.put("auth", auth);
        } catch (Exception e) {
            response.put("mensaje", "Error al buscar el usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
