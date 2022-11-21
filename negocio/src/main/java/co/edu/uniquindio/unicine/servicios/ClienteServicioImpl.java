package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private ClienteRepo clienteRepo;
    private AdministradorRepo adminRepo;
    private AdministradorTeatroRepo adminTeatroRepo;
    private CompraRepo compraRepo;
    private CuponRepo cuponRepo;
    private FuncionRepo funcionRepo;
    private CiudadRepo ciudadRepo;
    private TeatroRepo teatroRepo;
    private CompraConfiteriaRepo compraConfiteriaRepo;
    private CompraComboRepo compraComboRepo;
    private EntradaRepo entradaRepo;
    private CuponClienteRepo cuponClienteRepo;
    private PeliculaRepo peliculaRepo;


    @Autowired
    AdminTeatroServicio adminTeatroServicio;

    @Autowired
    CloudinaryServicio cloudinaryServicio;

    @Autowired
    ImagenRepo imagenRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ClienteServicioImpl(ClienteRepo clienteRepo, CompraRepo compraRepo, CuponRepo cuponRepo, AdministradorTeatroRepo adminTeatroRepo, AdministradorRepo adminRepo, FuncionRepo funcionRepo, TeatroRepo teatroRepo, CiudadRepo ciudadRepo, CompraComboRepo compraComboRepo, CompraConfiteriaRepo compraConfiteriaRepo, EntradaRepo entradaRepo, CuponClienteRepo cuponClienteRepo, PeliculaRepo peliculaRepo){
        this.clienteRepo = clienteRepo;
        this.compraRepo = compraRepo;
        this.cuponRepo = cuponRepo;
        this.adminTeatroRepo = adminTeatroRepo;
        this.adminRepo = adminRepo;
        this.funcionRepo = funcionRepo;
        this.ciudadRepo = ciudadRepo;
        this.teatroRepo = teatroRepo;
        this.compraComboRepo = compraComboRepo;
        this.compraConfiteriaRepo = compraConfiteriaRepo;
        this.entradaRepo = entradaRepo;
        this.cuponClienteRepo = cuponClienteRepo;
        this.peliculaRepo = peliculaRepo;
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
        verificarCredencialesActualizar(cliente.getCorreo(), cliente.getUsername(), cliente.getCodigo());

        cliente.setCompras(guardado.get().getCompras());
        cliente.setRol(guardado.get().getRol());
        cliente.setCuponClientes(guardado.get().getCuponClientes());

        if(imagen != null){
            Imagen img = new Imagen();
            if(cliente.getImagen() == null){
                img = this.cloudinaryServicio.actualizarImagen(imagen, null,"clientes");
            }else{
                img = this.cloudinaryServicio.actualizarImagen(imagen, cliente.getImagen(),"clientes");
            }
            cliente.setImagen(img);
        }
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

    public Boolean verificarEstadoCliente(String username, String rol){
        if(rol.equals("ROLE_CLIENTE")){
            Optional<Cliente> cliente = this.clienteRepo.findByUsername(username);
            logger.info(username + " " + rol + " " + cliente.get().getEstado());
            if(cliente.get().getEstado()){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
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
        boolean isPasswd = cliente.getPassword().equals(passwdEnc);
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

        Compra compra_actualizada = null;

        try{
            compra_actualizada = calcularCostoTotalVenta(compra);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

        if(compra_actualizada.getEntradas().size() > 0) {
            for(int i = 0; i<compra_actualizada.getEntradas().size(); i++){
                compra_actualizada.getEntradas().get(i).setCompra(compra_actualizada);
            }
        }

        if(compra_actualizada.getCompraCombos().size() > 0) {
            for(int i = 0; i<compra_actualizada.getCompraCombos().size(); i++){
                compra_actualizada.getCompraCombos().get(i).setCompra(compra_actualizada);
            }
        }

        if(compra_actualizada.getCompraCombos().size() > 0) {
            for(int i = 0; i<compra_actualizada.getCompraConfiterias().size(); i++){
                compra_actualizada.getCompraConfiterias().get(i).setCompra(compra_actualizada);
            }
        }

        if(compra_actualizada.getCuponCliente() != null){
            compra_actualizada.getCuponCliente().setCliente(compra_actualizada.getCliente());
        }

        try{
            crearDetallesCompra(compra_actualizada);
            this.adminTeatroServicio.actualizarDistribucionSillas(compra.getFuncion().getSala().getDistribucionSillas());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

        return compraRepo.save(compra_actualizada);
    }

    private Compra crearDetallesCompra(Compra compra) throws Exception {
        List<Entrada> entradas = new ArrayList<>();

        if(compra.getEntradas() != null && !compra.getEntradas().isEmpty()){
            for(Entrada entrada : compra.getEntradas()){
                Entrada entradaNew = crearEntrada(entrada);
                entradas.add(entradaNew);
            }
            compra.setEntradas(entradas);
        }

        List<CompraCombo> comprasCombos = new ArrayList<>();

        if(!compra.getCompraCombos().isEmpty() && compra.getCompraCombos() != null){
            for(CompraCombo compraCombo : compra.getCompraCombos()){
                CompraCombo compraCon = crearCompraCombo(compraCombo);
                comprasCombos.add(compraCon);
            }
            compra.setCompraCombos(comprasCombos);
        }

        List<CompraConfiteria> comprasConfiterias = new ArrayList<>();

        if(!compra.getCompraConfiterias().isEmpty() && compra.getCompraConfiterias() != null){
            for(CompraConfiteria compraConfiteria : compra.getCompraConfiterias()){
                CompraConfiteria compraConf = crearCompraConfiteria(compraConfiteria);
                comprasConfiterias.add(compraConf);
            }
            compra.setCompraConfiterias(comprasConfiterias);
        }

        return compra;
    }

    private Compra calcularCostoTotalVenta(Compra compra) throws Exception {
        logger.info("Compra: " + compra.toString());
       try{
           this.adminTeatroServicio.obtenerFuncion(compra.getFuncion().getCodigo());
       }catch(Exception e){
           throw new Exception(e.getMessage());
       }

       double precioTotalEntradas = 0;

       if(compra.getEntradas().size() > 0){
           for(int i = 0; i<compra.getEntradas().size(); i++){
               precioTotalEntradas+= compra.getEntradas().get(i).getPrecio().doubleValue();
           }
       }

       Double precioCombos = 0.0;

       if(compra.getCompraCombos().size() > 0){
           for(CompraCombo compraCombo : compra.getCompraCombos()){
                precioCombos+=compraCombo.getPrecio() * compraCombo.getCantidad();
           }
       }

       Double precioConfiteria = 0.0;

       if(compra.getCompraConfiterias().size() > 0){
           for(CompraConfiteria compraConfiteria : compra.getCompraConfiterias()){
               precioConfiteria += compraConfiteria.getPrecio() * compraConfiteria.getCantidad();
           }
       }

       Optional<Cupon> cupon = null;

       if(compra.getCuponCliente() != null){
           cupon = redimirCupon(compra.getCuponCliente().getCupon().getCodigo());
       }

       Double precio = (precioTotalEntradas + precioCombos + precioConfiteria);
       Double valor_total = precio;

       if(cupon == null || cupon.isEmpty()){
           compra.setValor_total(valor_total);
           return this.compraRepo.save(compra);
       }

       List<String> criteriosValidos = new ArrayList<>();
       criteriosValidos.add("G");
       criteriosValidos.add("E");
       criteriosValidos.add("C");
       criteriosValidos.add("CO");

       List<String> criteriosCupon = List.of(cupon.get().getCriterio().split(" "));
       Double totalSinDescuentoComprasConfiteria = 0.0;
       Double totalSinDescuentoComprasCombos = 0.0;
       Double totalSinDescuentoEntradas = 0.0;
       Double totalSinDescuentoCompra = 0.0;

       for(int i = 0; i < criteriosCupon.size(); i++){
           for(int j = 0; j < criteriosValidos.size(); j++){
               if (criteriosCupon.get(i).equals(criteriosValidos.get(j))) {
                   if (criteriosCupon.get(i).equals("C")) {
                       totalSinDescuentoComprasConfiteria = precioConfiteria;
                       precioConfiteria = precioConfiteria - (precioConfiteria * (cupon.get().getDescuento().doubleValue() / 100));
                       valor_total -= totalSinDescuentoComprasConfiteria;
                       valor_total += precioConfiteria;
                   } else if (criteriosCupon.get(i).equals("E")) {
                       totalSinDescuentoEntradas = precioTotalEntradas;
                       precioTotalEntradas = precioTotalEntradas - (precioTotalEntradas * (cupon.get().getDescuento().doubleValue() / 100));
                       valor_total -= totalSinDescuentoEntradas;
                       valor_total += precioTotalEntradas;
                   } else if (criteriosCupon.get(i).equals("G")) {
                       totalSinDescuentoComprasConfiteria = precioConfiteria;
                       Double calculo = precioConfiteria - (precioConfiteria * (cupon.get().getDescuento().doubleValue() / 100));
                       precioConfiteria = precioConfiteria - (precioConfiteria * (cupon.get().getDescuento().doubleValue() / 100));
                       totalSinDescuentoEntradas = precioTotalEntradas;
                       precioTotalEntradas = precioTotalEntradas - (precioTotalEntradas * (cupon.get().getDescuento().doubleValue() / 100));
                       totalSinDescuentoComprasCombos = precioCombos;
                       precioCombos = precioCombos - (precioCombos * (cupon.get().getDescuento().doubleValue() / 100));
                       totalSinDescuentoCompra = valor_total;
                       valor_total = precioConfiteria + precioCombos + precioTotalEntradas;
                   } else if (criteriosCupon.get(i).equals("CO")) {
                       totalSinDescuentoComprasCombos = precioCombos;
                       precioCombos = precioCombos - (precioCombos * (cupon.get().getDescuento().doubleValue() / 100));
                       valor_total -= totalSinDescuentoComprasCombos;
                       valor_total += precioCombos;
                   }
                   break;
               }
           }
       }

       if(compra.getValor_total().equals(valor_total)){

           compra.setValor_total(valor_total);

           CuponCliente cuponCli = crearCuponCliente(compra.getCuponCliente());
           compra.setCuponCliente(cuponCli);

           return this.compraRepo.save(compra);
       }else{
           logger.info("17. " + valor_total);
           throw new Exception("Los datos enviados no son validos.");
       }
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

    @Override
    public List<Funcion> obtener_funciones(Integer idCiudad, Integer idTeatro) throws Exception {
        if(idCiudad == null || idTeatro == null){
            throw new Exception("El id de la ciudad o el teatro no puede estar vacio");
        }

        return this.funcionRepo.listarFuncionesPorCiudadTeatro(idCiudad, idTeatro);
    }

    @Override
    public List<Funcion> obtener_funciones_ciudad(Integer idCiudad) throws Exception {
        if(idCiudad == null){
            throw new Exception("El id de la ciudad no puede estar vacio");
        }

        return this.funcionRepo.listarFuncionesPorCiudad(idCiudad);
    }

    @Override
    public List<Funcion> obtener_funciones_pelicula(Integer idCiudad, Integer idPelicula, LocalDate fecha, String dia) throws Exception {
        if(idCiudad == null || idPelicula == null){
            throw new Exception("El id de la ciudad o pelicula, no puede estar vacio");
        }

        List<Funcion> funciones = this.funcionRepo.listarFuncionesPorPeliculaCiudad(idCiudad, idPelicula, fecha);
        List<Funcion> funcionesDisponibles = new ArrayList<>();
        Boolean esFuncionDisponible = false;

        for(Funcion funcion : funciones){
            String[] dias = funcion.getHorario().getDia().split(" ");
            for(String diaS : dias){
                if(diaS.equals(dia)){
                    esFuncionDisponible = true;
                    break;
                }else{
                    esFuncionDisponible = false;
                }
            }
            if(esFuncionDisponible){
                funcionesDisponibles.add(funcion);
            }
        }

        return funcionesDisponibles;
    }

    @Override
    public List<Pelicula> obtener_peliculas(Integer idCiudad, Integer idPelicula) throws Exception {
        if(idCiudad == null || idPelicula == null){
            throw new Exception("El id de la ciudad o pelicula, no puede estar vacio");
        }

        return this.peliculaRepo.obtener_peliculas(idCiudad, idPelicula);
    }

    @Override
    public List<Pelicula> obtener_peliculas_ciudad(Integer idCiudad) throws Exception {
        if(idCiudad == null){
            throw new Exception("El id de la ciudad no puede estar vacio");
        }

        return this.peliculaRepo.obtener_peliculas_ciudad(idCiudad);
    }

    @Override
    public CompraConfiteria crearCompraConfiteria(CompraConfiteria compraConfiteria) throws Exception {
        try{
            return this.compraConfiteriaRepo.save(compraConfiteria);
        }catch(Exception e){
            throw new Exception("No se pudo crear la compra de la confiteria");
        }
    }

    @Override
    public CompraCombo crearCompraCombo(CompraCombo compraCombo) throws Exception {
        try{
            return this.compraComboRepo.save(compraCombo);
        }catch(Exception e){
            throw new Exception("No se pudo crear la compra del combo");
        }
    }

    @Override
    public Entrada crearEntrada(Entrada entrada) throws Exception {
        try{
            return this.entradaRepo.save(entrada);
        }catch(Exception e){
            throw new Exception("No se pudo crear la entrada");
        }
    }

    @Override
    public CuponCliente crearCuponCliente(CuponCliente cuponCliente) throws Exception {
        try{
            return this.cuponClienteRepo.save(cuponCliente);
        }catch(Exception e){
            throw new Exception("No se pudo crear el cupon cliente");
        }
    }

}
