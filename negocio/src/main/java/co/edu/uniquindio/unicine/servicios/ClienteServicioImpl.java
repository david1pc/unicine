package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    public Auth login2(String correo, String password) throws Exception{
        /*
        Cliente cliente = null;
        Administrador administrador = null;
        AdministradorTeatro administradorTeatro = null;
        logger.info("Realizando login...");

        cliente = clienteRepo.comprobarAutenticacion(correo, password).orElse(null);

        if(cliente == null){
            administrador = adminRepo.comprobarAutenticacion(correo, password).orElse(null);
        }

        if(cliente == null && administrador == null){
            administradorTeatro = adminTeatroRepo.comprobarAutenticacion(correo, password).orElse(null);
        }

        Auth auth = asignarAuth(cliente, administrador, administradorTeatro);
        */


        return null;
    }
/*
    @Override
    public Auth asignarAuth(Cliente cliente, Administrador administrador, AdministradorTeatro administradorTeatro) throws Exception {
        Auth auth = new Auth();
        if(cliente != null && cliente.getEstado() == true){
            auth.setCodigo(cliente.getCodigo());
            auth.setCorreo(cliente.getCorreo());
            auth.setRol(Rol.CLIENTE);
        }else if(administrador != null){
            auth.setCodigo(administrador.getCodigo());
            auth.setCorreo(administrador.getCorreo());
            auth.setRol(Rol.ADMINISTRADOR);
        }else if(administradorTeatro != null){
            auth.setCodigo(administradorTeatro.getCodigo());
            auth.setCorreo(administradorTeatro.getCorreo());
            auth.setRol(Rol.ADMINISTRADOR_TEATRO);
        }else{
            throw new Exception("El correo o la contraseña son incorrectas");
        }
        return auth;
    }
*/
    @Override
    public Cliente registrarCliente(Cliente cliente) throws Exception {
        Boolean correoExiste = esCorreoRepetido(cliente.getCorreo());
        Boolean cedulaExiste = esCedulaRepetida(cliente.getCedula());

        if(correoExiste || cedulaExiste){
            throw new Exception("El cliente ya existe");
        }

        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

        return clienteRepo.save(cliente);
    }

    private Boolean esCorreoRepetido(String correo){
        return clienteRepo.findByCorreo(correo).orElse(null) != null;
    }

    private Boolean esCedulaRepetida(String cedula){
        return clienteRepo.findByCedula(cedula).orElse(null) != null;
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) throws Exception {
        Optional<Cliente> guardado = clienteRepo.findById(cliente.getCodigo());

        if(guardado.isEmpty()){
            throw new Exception("El cliente no existe");
        }

        return clienteRepo.save(cliente);
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
