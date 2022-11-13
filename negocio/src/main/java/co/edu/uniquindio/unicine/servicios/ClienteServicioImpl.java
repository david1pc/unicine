package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import co.edu.uniquindio.unicine.util.EncriptacionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private ClienteRepo clienteRepo;

    private AdministradorRepo adminRepo;

    private AdministradorTeatroRepo adminTeatroRepo;
    private CompraRepo compraRepo;

    private CuponRepo cuponRepo;

    @Autowired
    CloudinaryServicio cloudinaryServicio;

    @Autowired
    ImagenRepo imagenRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ClienteServicioImpl(ClienteRepo clienteRepo, CompraRepo compraRepo, CuponRepo cuponRepo, AdministradorTeatroRepo adminTeatroRepo, AdministradorRepo adminRepo){
        this.clienteRepo = clienteRepo;
        this.compraRepo = compraRepo;
        this.cuponRepo = cuponRepo;
        this.adminTeatroRepo = adminTeatroRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public Cliente obtenerCliente(Integer codigoCliente) throws Exception {
        Optional<Cliente> cliente = clienteRepo.findById(codigoCliente);

        if(cliente.isEmpty()){
            throw new Exception("El cliente no existe");
        }

        return cliente.get();
    }

    @Override
    public Cliente login(String correo, String password) throws Exception{
        Cliente cliente = clienteRepo.comprobarAutenticacion(correo, password).orElse(null);

        if(cliente == null){
            throw new Exception("El correo o la contraseña son incorrectas");
        }

        return cliente;
    }

    @Override
    public Cliente registrarCliente(Cliente cliente, MultipartFile imagen) throws Exception {
        verificarCredenciales(cliente.getCorreo(), cliente.getCedula(), cliente.getUsername());

        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

        if(imagen != null){
            Imagen img = guardarImagenCloudinary(imagen);
            cliente.setImagen(img);
        }

        return clienteRepo.save(cliente);
    }

    private Imagen guardarImagenCloudinary(MultipartFile imagen) throws IOException {
        Map<String, String> respuesta = cloudinaryServicio.subirImagen(imagen, "clientes");
        Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
        Imagen img = this.imagenRepo.save(nueva_imagen);
        return img;
    }

    public void verificarCredenciales(String correo, String cedula, String username) throws Exception {
        if(cedula != null){
            Boolean cedulaExiste = esCedulaRepetida(cedula);

            if(cedulaExiste){
                throw new Exception("La cedula ya se encuentra en uso");
            }
        }

        if(correo != null){
            Boolean correoExiste = esCorreoRepetido(correo, null);

            if(correoExiste){
                throw new Exception("El correo ya se encuentra en uso");
            }
        }

        if(username != null){
            Boolean usernameExiste = esUsernameRepetido(username, null);

            if(usernameExiste){
                throw new Exception("El username ya se encuentra en uso");
            }
        }
    }

    private Boolean esUsernameRepetido(String username, Integer codigo) {
        boolean estadoCliente;
        boolean estadoAdmin;
        boolean estadoAdminTeatro;

        if(codigo == null){
            estadoCliente = clienteRepo.findByUsername(username).orElse(null) != null;
            estadoAdmin = adminRepo.findByUsername(username).orElse(null) != null;
            estadoAdminTeatro = adminTeatroRepo.findByUsername(username).orElse(null) != null;
        }else{
            estadoCliente = clienteRepo.findByUsernameActualizar(username, codigo).orElse(null) != null;
            estadoAdmin = adminRepo.findByUsername(username).orElse(null) != null;
            estadoAdminTeatro = adminTeatroRepo.findByUsername(username).orElse(null) != null;
        }

        if(estadoCliente || estadoAdmin || estadoAdminTeatro){
            return true;
        }else{
            return false;
        }
    }

    private Boolean esCorreoRepetido(String correo, Integer codigo){
        boolean estadoAdminTeatro;
        boolean estadoAdmin;
        boolean estadoCliente;

        if(codigo == null){
            estadoCliente = clienteRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdmin = adminRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdminTeatro = adminTeatroRepo.findByCorreo(correo).orElse(null) != null;
        }else{
            estadoCliente = clienteRepo.findByCorreoActualizar(correo, codigo).orElse(null) != null;
            estadoAdmin = adminRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdminTeatro = adminTeatroRepo.findByCorreo(correo).orElse(null) != null;
        }

        if(estadoCliente || estadoAdmin || estadoAdminTeatro){
            return true;
        }else{
            return false;
        }
    }

    private Boolean esCedulaRepetida(String cedula){
        return clienteRepo.findByCedula(cedula).orElse(null) != null;
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente, MultipartFile imagen) throws Exception {
        Optional<Cliente> guardado = clienteRepo.findById(cliente.getCodigo());

        if(guardado.isEmpty()){
            throw new Exception("El cliente no existe");
        }

        cliente.setCompras(guardado.get().getCompras());
        cliente.setRol(guardado.get().getRol());
        cliente.setCuponClientes(guardado.get().getCuponClientes());

        if(imagen == null){
            cliente.setImagen(guardado.get().getImagen());
        }else{
            eliminarImagenCloudinary(cliente, imagen);
        }

        verificarCredencialesActualizar(cliente.getCorreo(), cliente.getUsername(), cliente.getCodigo());

        return actualizarClienteVerificado(cliente, guardado.get().getPassword());
    }

    private Cliente eliminarImagenCloudinary(Cliente cliente, MultipartFile imagen) throws IOException {
        this.cloudinaryServicio.eliminarImagen(cliente.getImagen().getImagenId());
        Map<String, String> respuesta = cloudinaryServicio.subirImagen(imagen, "clientes");
        Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
        Imagen img = this.imagenRepo.save(nueva_imagen);
        cliente.setImagen(img);
        return cliente;
    }

    private void verificarCredencialesActualizar(String correo, String username, Integer codigo) throws Exception {
        if(!correo.isEmpty()){
            Boolean correoExiste = esCorreoRepetido(correo, codigo);

            if(correoExiste){
                throw new Exception("El correo ya se encuentra en uso");
            }
        }

        if(!username.isEmpty()){
            Boolean usernameExiste = esUsernameRepetido(username, codigo);

            if(usernameExiste){
                throw new Exception("El username ya se encuentra en uso");
            }
        }
    }

    private Cliente actualizarClienteVerificado(Cliente cliente, String passwdEnc){
        boolean isPasswd = passwordEncoder.matches(cliente.getPassword(), passwdEnc);
        if(!isPasswd){
            cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
            return clienteRepo.save(cliente);
        }else{
            return clienteRepo.save(cliente);
        }
    }

    @Override
    public void eliminarCliente(Integer codigoCliente) throws Exception {
        Optional<Cliente> cliente = clienteRepo.findById(codigoCliente);

        if(cliente.isEmpty()){
            throw new Exception("El cliente no existe");
        }

        clienteRepo.delete(cliente.get());
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public List<Compra> listarHistorialCompras(Integer codigoCliente) throws Exception {
        Optional<Cliente> cliente = clienteRepo.findById(codigoCliente);

        if(cliente.isEmpty()){
            throw new Exception("El cliente no existe");
        }

        return clienteRepo.obtenerCompras(cliente.get().getCorreo());
    }

    @Override
    public Compra registrarCompra(Compra compra) throws Exception {
        if(compra == null){
            throw new Exception("No se ha completado la compra");
        }

        String correo = compra.getCliente().getCorreo();
        String passwd = compra.getCliente().getPassword();

        Cliente cliente = login(correo, passwd);

        if(cliente == null){
            throw new Exception("El cliente no existe");
        }

        if(!cliente.getEstado()){
            throw new Exception("El cliente no está activo");
        }

        return compraRepo.save(compra);
    }

    @Override
    public Optional<Cupon> redimirCupon(Integer codigoCupon) throws Exception {
        Optional<Cupon> cupon = cuponRepo.findById(codigoCupon);

        if(cupon.isEmpty()){
            throw new Exception("El cupon no existe");
        }

        LocalDate vencimiento = cupon.get().getVencimiento();

        if(LocalDate.now().isAfter(vencimiento)){
            throw new Exception("El cupon está vencido");
        }

        return cupon;
    }
}
