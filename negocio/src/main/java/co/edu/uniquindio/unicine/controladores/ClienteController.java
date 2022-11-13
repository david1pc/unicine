package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import co.edu.uniquindio.unicine.servicios.*;
import co.edu.uniquindio.unicine.util.EncriptacionUtil;
import co.edu.uniquindio.unicine.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EmailServicio emailServicio;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUserDetailsServicio jwtUserDetailsServicio;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AdminServicio adminServicio;

    @Autowired
    ClienteRepo clienteRepo;

    @Value("${dev.route}")
    String devRoute;

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
            if(imagen.isEmpty()){
                imagen = null;
            }
            Cliente nuevo = this.clienteServicio.registrarCliente(cliente_nuevo, imagen);
            String username_enc = encriptarUsername(nuevo.getUsername());
            String route = this.devRoute.concat("auth/activacion/"+username_enc);
            this.emailServicio.enviarEmail("Registro de cuenta en Unicine", route, nuevo.getCorreo());
            response.put("cliente", nuevo);
            response.put("mensaje", "El cliente " + nuevo.getPrimerNombre() + ", ha sido registrado con exito!. Se le ha enviado a su correo un enlace para activar la cuenta");
        } catch (Exception e) {
            response.put("mensaje", "Error al intentar registrarse");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    private String encriptarUsername(String username){
        String username_enc = EncriptacionUtil.encrypt(username);
        return username_enc;
    }

    @PutMapping(value = "/actualizacion/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> actualizarCliente(@RequestParam(name = "imagen") @Size(min = 1) MultipartFile imagen, @RequestPart(name = "cliente") String cliente) {
        Map<String, Object> response = new HashMap<>();

        Cliente cliente_nuevo = null;

        try {
            cliente_nuevo = new ObjectMapper().readValue(cliente, Cliente.class);
        } catch (JsonProcessingException e) {
            response.put("mensaje", "Error al actualizar el cliente");
            response.put("error", "No se reconocen los datos del cliente");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            Cliente clienteActual = this.clienteServicio.actualizarCliente(cliente_nuevo, imagen);
            response.put("mensaje", "El cliente ha sido actualizado con éxito!");
            response.put("cliente", clienteActual);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/actualizacion-data/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> actualizarCliente(@RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();

        try {
            Cliente clienteActual = this.clienteServicio.actualizarCliente(cliente, null);
            response.put("mensaje", "El cliente ha sido actualizado con éxito!");
            response.put("cliente", clienteActual);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
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

    @PreAuthorize("hasRole('CLIENTE')")
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

    @GetMapping("/activacion-cuenta/{str}")
    public ResponseEntity<?> activarCuenta(@PathVariable String str){
        Map<String, Object> response = new HashMap<>();
        try{
            String username = EncriptacionUtil.decrypt(str);
            Optional<Cliente> cliente = this.clienteRepo.findByUsername(username);
            cliente.get().setEstado(true);
            this.clienteRepo.save(cliente.get());
            response.put("mensaje", "Su cuenta ha sido activada con exito");
            response.put("estado", true);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("mensaje", "Error en la activacion de la cuenta");
            response.put("error", "No se ha encontrado el cliente");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/login/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody Login login){
        Map<String, Object> response = new HashMap<>();

        try {
            final UserDetails userDetails = jwtUserDetailsServicio.loadUserByUsername(login.getUsername());
            String rol_user = "";
            for(GrantedAuthority e: userDetails.getAuthorities()){
                rol_user = e.getAuthority();
            }
            Boolean estadoCliente = this.clienteServicio.verificarEstadoCliente(userDetails.getUsername(),rol_user);
            if(!estadoCliente){
                response.put("mensaje", "Error al iniciar sesion");
                response.put("error", "El cliente esta inactivo. Active la cuenta abriendo el enlace enviado a su correo");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
            }
            autenticarUsuario(login.getUsername(), login.getPassword());
            final String token = jwtTokenUtil.generateToken(userDetails);
            LoginRespuesta loginRespuesta = new LoginRespuesta(userDetails.getUsername(), EncriptacionUtil.encrypt(token), rol_user);
            response.put("login", loginRespuesta);
            response.put("mensaje", "El usuario ha sido iniciado sesion con exito");
        } catch (Exception e) {
            response.put("mensaje", "Error al iniciar sesion");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    private void autenticarUsuario(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch(DisabledException e){
            throw new Exception("Usuario deshabilitado");
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("El username o contraseña es incorrecto");
        }
    }
}
