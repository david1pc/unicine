package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import co.edu.uniquindio.unicine.servicios.*;
import co.edu.uniquindio.unicine.util.EncriptacionUtil;
import co.edu.uniquindio.unicine.util.JwtTokenUtil;
import co.edu.uniquindio.unicine.util.QrUtil;
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
import java.time.LocalDate;
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
    AdminTeatroServicio adminTeatroServicio;


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

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{username}")
    public ResponseEntity<?> obtener_cliente(@PathVariable String username) {
        Optional<Cliente> cliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = this.clienteRepo.findByUsername(username);
            response.put("cliente", cliente);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", "No se pudo encontrar al cliente con username: " + username);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/funciones/{idCiudad}/{idTeatro}")
    public ResponseEntity<?> buscarFunciones(@PathVariable Long idCiudad, @PathVariable Long idTeatro) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Funcion> funciones = this.clienteServicio.obtener_funciones(idCiudad.intValue(), idTeatro.intValue());
            response.put("funciones", funciones);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar las funciones");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/peliculas/{idCiudad}/{idTeatro}")
    public ResponseEntity<?> buscarPeliculas(@PathVariable Long idCiudad, @PathVariable Long idTeatro) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Pelicula> peliculas = this.clienteServicio.obtener_peliculas(idCiudad.intValue(), idTeatro.intValue());
            response.put("peliculas", peliculas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar las peliculas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/peliculas/{idCiudad}")
    public ResponseEntity<?> buscarPeliculasCiudad(@PathVariable Long idCiudad) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Pelicula> peliculas = this.clienteServicio.obtener_peliculas_ciudad(idCiudad.intValue());
            response.put("peliculas", peliculas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar las peliculas");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/funcion/{idCiudad}/{idPelicula}")
    public ResponseEntity<?> buscarFuncionesPelicula(@PathVariable Long idCiudad, @PathVariable Long idPelicula, @RequestBody Horario horario) {
        Map<String, Object> response = new HashMap<>();
        try {
            logger.info("Horario" + horario);
            List<Funcion> funciones = this.clienteServicio.obtener_funciones_pelicula(idCiudad.intValue(), idPelicula.intValue(), horario.getFecha_inicio(), horario.getDia());
            response.put("funciones", funciones);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar las funciones");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/funciones/{idCiudad}")
    public ResponseEntity<?> buscarFuncionesCiudad(@PathVariable Long idCiudad) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Funcion> funciones = this.clienteServicio.obtener_funciones_ciudad(idCiudad.intValue());
            response.put("funciones", funciones);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar las funciones");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ciudades/")
    public ResponseEntity<?> buscarCiudades() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Ciudad> ciudades = this.adminServicio.obtener_ciudades();
            response.put("ciudades", ciudades);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar las ciudades");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/teatros/")
    public ResponseEntity<?> buscarTeatros() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Teatro> teatros = this.adminTeatroServicio.listarTeatros();
            response.put("teatros", teatros);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e) {
            response.put("mensaje", "Error al encontrar los teatros");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/confiteria")
    @PreAuthorize("hasRole('CLIENTE')")
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

    @GetMapping("/combos")
    @PreAuthorize("hasRole('CLIENTE')")
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

    @PreAuthorize("hasRole('CLIENTE')")
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
            if(imagen.isEmpty()){
                imagen = null;
            }
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

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/compras/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> crearCompra(@RequestBody NuevaCompra compra){
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Cliente> cliente = this.clienteRepo.findByUsername(compra.getUsername());
            Compra nueva = new Compra(MedioPago.valueOf(compra.getMedioPago()), compra.getFecha_compra(), compra.getValor_total(), compra.getFuncion(), compra.getCompraCombos(), compra.getCompraConfiterias(), compra.getEntradas(), cliente.get(), compra.getCuponCliente());
            nueva.setCompraCombos(compra.getCompraCombos());
            nueva.setCuponCliente(compra.getCuponCliente());
            nueva.getCuponCliente().setCupon(compra.getCuponCliente().getCupon());
            nueva.setCompraConfiterias(compra.getCompraConfiterias());
            nueva.setEntradas(compra.getEntradas());
            Compra compra_registrada = this.clienteServicio.registrarCompra(nueva);
            this.emailServicio.enviarEmail("Nuevo registro de compra en unicine", compra.getContenido(), cliente.get().getCorreo());
            Optional<Cliente> cliente_update = this.clienteRepo.findByUsername(compra.getUsername());
            if(cliente_update.get().getCompras().size() == 1){
                LocalDate fechaVencimiento = LocalDate.now();
                LocalDate nuevaFecha = fechaVencimiento.plusDays(15);
                Cupon nuevo_cupon = new Cupon(0, "PRIMERA COMPRA EN UNICINE", 10, "G", nuevaFecha);
                Cupon cupon = this.adminServicio.crearCupon(nuevo_cupon);
                this.emailServicio.enviarEmail("Cupon de primera compra en Unicine", "Gracias por comprar con nosotros, te regalamos un cupon para compras generales(confiteria, entradas y combos) para redimirlo en nuestro sitio web.\n Codigo del cupon: " + cupon.getCodigo(), cliente.get().getCorreo());
            }
            response.put("compra", compra_registrada);
            response.put("mensaje", "La compra ha sido registrada con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al registrar la compra");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cupon/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> redimirCupon(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        logger.info("Redimiendo cupon... " + id);

        try {
            Optional<Cupon> cupon = this.clienteServicio.redimirCupon(id.intValue());
            response.put("cupon", cupon);
            response.put("mensaje", "Cupon redimido con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al redimir el cupon");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/entradas/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> generarQREntradas(@RequestBody EntradaQR entradaQR[]){
        Map<String, Object> response = new HashMap<>();
        List<byte[]> barcode = new ArrayList<>();

        logger.info("Generando codigo QR... " + entradaQR);

        try {
            for(EntradaQR ent : entradaQR){
                byte[] barcodeg = QrUtil.generateByteQRCode(ent.getInd() + ent.getColumna().toString() + ent.getFila().toString(), 250,250);
                barcode.add(barcodeg);
            }
            response.put("barcode", barcode);
            response.put("mensaje", "QR generado con exito!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al generar el codigo QR");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
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
            LocalDate fechaVencimiento = LocalDate.now();
            LocalDate nuevaFecha = fechaVencimiento.plusDays(15);
            Cupon nuevo_cupon = new Cupon(0, "BIENVENIDA A UNICINE", 10, "G", nuevaFecha);
            Cupon cupon = this.adminServicio.crearCupon(nuevo_cupon);
            this.emailServicio.enviarEmail("Cupon de bienvenida a Unicine", "Gracias por registrarte en unicine, te regalamos un cupon de bienvenida para compras generales(confiteria, combos y entradas) para redimirlo en nuestro sitio web.\n Codigo del cupon: " + cupon.getCodigo(), cliente.get().getCorreo());
            response.put("mensaje", "Su cuenta ha sido activada con exito");
            response.put("estado", true);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("mensaje", "Error en la activacion de la cuenta");
            response.put("error", "No se ha encontrado el cliente");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recuperar-cuenta/{str}")
    public ResponseEntity<?> recuperarCuenta(@PathVariable String str){
        Map<String, Object> response = new HashMap<>();
        try{
            String correo = EncriptacionUtil.encrypt(str);
            this.emailServicio.enviarEmail("Recuperacion de cuenta en Unicine", correo, str);
            response.put("mensaje", "Si el correo pertenece a una cuenta registrada en Unicine, le llegara un correo para recuperar la contraseña");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("mensaje", "Error en la recuperacion de la cuenta");
            response.put("error", "Vuelva a intentar recuperar la cuenta");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/verificar-cuenta/{str}")
    public ResponseEntity<?> verificarCuenta(@PathVariable String str){
        Map<String, Object> response = new HashMap<>();
        try{
            String correo = EncriptacionUtil.decrypt(str);
            if(correo != null){
                Optional<Cliente> cliente = this.clienteRepo.findByCorreo(correo);
                response.put("mensaje", "La cuenta ha sido verificada");
                response.put("cliente", cliente);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }else{
                response.put("mensaje", "Error en la verificacion de la cuenta");
                response.put("error", "No se ha encontrado el cliente");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            response.put("mensaje", "Error en la verificacion de la cuenta");
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
