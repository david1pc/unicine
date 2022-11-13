package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import co.edu.uniquindio.unicine.util.EncriptacionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminServicioImpl implements AdminServicio  {

     private CiudadRepo ciudadRepo;
     private PeliculaRepo peliculaRepo;
     private CuponRepo cuponRepo;
     private ConfiteriaRepo confiteriaRepo;
     private ComboRepo comboRepo;
     private AdministradorTeatroRepo administradorTeatroRepo;
     private ClienteRepo clienteRepo;
     private AdministradorRepo adminRepo;
     private ImagenRepo imagenRepo;
     @Autowired
     CloudinaryServicio cloudinaryServicio;


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

     private ClienteServicio clienteServicio;

    public AdminServicioImpl(CiudadRepo ciudadRepo, PeliculaRepo peliculaRepo, CuponRepo cuponRepo, ConfiteriaRepo confiteriaRepo, ComboRepo comboRepo, AdministradorTeatroRepo administradorTeatroRepo, ClienteServicio clienteServicio, ClienteRepo clienteRepo, AdministradorRepo administradorRepo, ImagenRepo imagenRepo) {
        this.ciudadRepo = ciudadRepo;
        this.peliculaRepo = peliculaRepo;
        this.cuponRepo = cuponRepo;
        this.confiteriaRepo = confiteriaRepo;
        this.comboRepo = comboRepo;
        this.administradorTeatroRepo = administradorTeatroRepo;
        this.clienteServicio = clienteServicio;
        this.clienteRepo = clienteRepo;
        this.adminRepo = administradorRepo;
        this.imagenRepo = imagenRepo;
    }
    // gestionar ciudades

    @Override
    public Ciudad crearCiudad(Ciudad ciudad) throws Exception{
        boolean ciudadExiste = ciudadExiste(ciudad.getCodigo());

        if (ciudadExiste){
            throw new Exception("La ciudad ya existe");
        }
        return ciudadRepo.save(ciudad);
    }

    private Boolean ciudadExiste (Integer codigo){
        return ciudadRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Ciudad obtenerCiudad(Integer codigo) throws Exception {
        Optional<Ciudad> ciudad = ciudadRepo.findById(codigo);

        if(ciudad.isEmpty()){
            throw new Exception("La ciudad no existe");
        }

        return ciudad.get();
    }

    @Override
    public Pelicula crearPelicula(Pelicula pelicula, MultipartFile imagen) throws Exception {
        if (pelicula == null){
            throw new Exception("La pelicula no tiene datos");
        }

        if(imagen != null){
            Imagen img = cloudinaryServicio.guardarImagen(imagen, "peliculas");
            pelicula.setImagen(img);
        }

        return peliculaRepo.save(pelicula);
    }

    private Boolean peliculaExiste (Integer codigo){
        return peliculaRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula, MultipartFile imagen) throws Exception {
        boolean peliculaExiste = peliculaExiste(pelicula.getCodigo());
        if (!peliculaExiste){
            throw new Exception("La película no existe");
        }

        if(imagen != null){
            Imagen img = new Imagen();
            if(pelicula.getImagen() == null){
                img = this.cloudinaryServicio.actualizarImagen(imagen, null,"peliculas");
            }else{
                img = this.cloudinaryServicio.actualizarImagen(imagen, pelicula.getImagen(),"peliculas");
            }
            pelicula.setImagen(img);
        }

        return peliculaRepo.save(pelicula);
    }

    @Override
    public void eliminarPelicula(Integer codigo) throws Exception {
        Optional<Pelicula> pelicula = peliculaRepo.findById(codigo);

        if(pelicula.isEmpty()){
            throw new Exception("La pelicula no existe");
        }

        try{
            peliculaRepo.delete(pelicula.get());
        }catch(Exception e){
            throw new Exception("La pelicula con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con otra entidad");
        }

    }

    @Override
    public List<Pelicula> listarPeliculas() {
        return peliculaRepo.findAll();
    }

    @Override
    public Pelicula obtenerPelicula(Integer codigo) throws Exception {
        Optional<Pelicula> pelicula = peliculaRepo.findById(codigo);
        if(pelicula.isEmpty()){
            throw new Exception("La pelicula no existe");
        }
        return pelicula.get();
    }

    // Gestionar cupones
    @Override
    public Cupon crearCupon(Cupon cupon) throws Exception {
        if (cupon == null){
            throw new Exception("El cupon no tiene datos");
        }
        return cuponRepo.save(cupon);
    }

    private Boolean cuponExiste (Integer codigo){
        return cuponRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Cupon actualizarCupon(Cupon cupon) throws Exception {
        boolean cuponExiste = cuponExiste(cupon.getCodigo());
        if (!cuponExiste){
            throw new Exception("El cupón no existe");
        }
        return cuponRepo.save(cupon);
    }

    @Override
    public void eliminarCupon(Integer codigo) throws Exception {
        Optional<Cupon> cupon = cuponRepo.findById(codigo);

        if(cupon.isEmpty()){
            throw new Exception("El cupón no existe");
        }

        try{
            cuponRepo.delete(cupon.get());
        }catch(Exception e) {
            throw new Exception("El cupon con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con uno o más cupones de clientes");
        }
    }

    @Override
    public List<Cupon> listarCupones() {
        return cuponRepo.findAll();
    }

    @Override
    public Cupon obtenerCupon(Integer codigo) throws Exception {
        Optional<Cupon> cupon = cuponRepo.findById(codigo);
        if(cupon.isEmpty()){
            throw new Exception("El cupón no existe");
        }
        return cupon.get();
    }


    // confitería
    @Override
    public Confiteria crearConfiteria(Confiteria confiteria, MultipartFile imagen) throws Exception {
        boolean confiteriaExiste = confiteriaExiste(confiteria.getCodigo());
        if (confiteriaExiste){
            throw new Exception("La confitería ya existe");
        }

        if(imagen != null){
            Imagen img = cloudinaryServicio.guardarImagen(imagen, "confiteria");
            confiteria.setImagen(img);
        }

        return confiteriaRepo.save(confiteria);
    }

    private Boolean confiteriaExiste (Integer codigo){
        return confiteriaRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Confiteria actualizarConfiteria(Confiteria confiteria, MultipartFile imagen) throws Exception {
        boolean confiteriaExiste = confiteriaExiste(confiteria.getCodigo());
        if (!confiteriaExiste){
            throw new Exception("La confitería no existe");
        }

        if(imagen != null){
            Imagen img = new Imagen();
            if(confiteria.getImagen() == null){
                img = this.cloudinaryServicio.actualizarImagen(imagen, null,"confiteria");
            }else{
                img = this.cloudinaryServicio.actualizarImagen(imagen, confiteria.getImagen(),"confiteria");
            }
            confiteria.setImagen(img);
        }

        return confiteriaRepo.save(confiteria);
    }

    @Override
    public void eliminarConfiteria(Integer codigo) throws Exception {
        Optional<Confiteria> confiteria = confiteriaRepo.findById(codigo);

        if(confiteria.isEmpty()){
            throw new Exception("la confitería no existe");
        }

        try{
            confiteriaRepo.delete(confiteria.get());
        }catch(Exception e){
            throw new Exception("La confiteria con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o más compras");
        }
    }

    @Override
    public List<Confiteria> listarConfiteria() {
        return confiteriaRepo.findAll();
    }

    @Override
    public Confiteria obtenerConfiteria(Integer codigo) throws Exception {
        Optional<Confiteria> confiteria = confiteriaRepo.findById(codigo);

        if(confiteria.isEmpty()){
            throw new Exception("La confitería no existe");
        }

        return confiteria.get();
    }



    // combos

    @Override
    public Combo crearCombo(Combo combo) throws Exception {
        boolean comboExiste = comboExiste(combo.getCodigo());
        if (comboExiste){
            throw new Exception("El combo ya existe");
        }
        return comboRepo.save(combo);
    }

    private Boolean comboExiste (Integer codigo){
        return comboRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Combo actualizarCombo(Combo combo) throws Exception {
        boolean comboExiste = comboExiste(combo.getCodigo());
        if (!comboExiste){
            throw new Exception("El combo no existe");
        }
        return comboRepo.save(combo);
    }

    @Override
    public void eliminarCombo(Integer codigo) throws Exception {
        Optional<Combo> combo = comboRepo.findById(codigo);

        if(combo.isEmpty()){
            throw new Exception("El combo no existe");
        }

        try{
            comboRepo.delete(combo.get());
        }catch(Exception e) {
            throw new Exception("El combo con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o más compras");
        }
    }

    @Override
    public List<Combo> listarCombos() {
        return comboRepo.findAll();
    }

    @Override
    public Combo obtenerCombo(Integer codigo) throws Exception {
        Optional<Combo> combo = comboRepo.findById(codigo);

        if(combo.isEmpty()){
            throw new Exception("El combo no existe");
        }

        return combo.get();
    }


    // Administradores de teatros
    @Override
    public AdministradorTeatro crearAdministradorTeatro(AdministradorTeatro administradorTeatro) throws Exception {
        verificarCredenciales(administradorTeatro.getCorreo(), administradorTeatro.getUsername(), null);
        try{
            administradorTeatro.setPassword(passwordEncoder.encode(administradorTeatro.getPassword()));
            AdministradorTeatro nuevo = administradorTeatroRepo.save(administradorTeatro);
            return nuevo;
        }catch (Exception e){
            throw new Exception("No se ha logrado crear un administrador teatro, intentelo de nuevo.");
        }
    }

    private Boolean administradorTeatroExiste (Integer codigo){
        return administradorTeatroRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public AdministradorTeatro actualizarAdministradorTeatro(AdministradorTeatro administradorTeatro) throws Exception {
        Optional<AdministradorTeatro> guardado = this.administradorTeatroRepo.findById(administradorTeatro.getCodigo());
        if (guardado.isEmpty()){
            throw new Exception("El administrador de teatro no existe");
        }

        verificarCredenciales(administradorTeatro.getCorreo(), administradorTeatro.getUsername(), administradorTeatro.getCodigo());

        return actualizarAdminTeatroVerificado(administradorTeatro, guardado.get().getPassword());
    }

    private void verificarCredenciales(String correo, String username, Integer codigo) throws Exception {
        if(correo != null){
            Boolean correoExiste = esCorreoRepetido(correo, codigo);

            if(correoExiste){
                throw new Exception("El correo ya se encuentra en uso");
            }
        }

        if(username != null){
            Boolean usernameExiste = esUsernameRepetido(username, codigo);

            if(usernameExiste){
                throw new Exception("El username ya se encuentra en uso");
            }
        }
    }

    private Boolean esUsernameRepetido(String username, Integer codigo){
        boolean estadoCliente;
        boolean estadoAdmin;
        boolean estadoAdminTeatro;

        if(codigo == null){
            estadoCliente = clienteRepo.findByUsername(username).orElse(null) != null;
            estadoAdmin = adminRepo.findByUsername(username).orElse(null) != null;
            estadoAdminTeatro = administradorTeatroRepo.findByUsername(username).orElse(null) != null;
        }else{
            estadoCliente = clienteRepo.findByUsername(username).orElse(null) != null;
            estadoAdmin = adminRepo.findByUsername(username).orElse(null) != null;
            estadoAdminTeatro = administradorTeatroRepo.findByUsernameActualizar(username, codigo).orElse(null) != null;
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
            estadoCliente = this.clienteRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdmin = adminRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdminTeatro = administradorTeatroRepo.findByCorreo(correo).orElse(null) != null;
        }else{
            estadoCliente = clienteRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdmin = adminRepo.findByCorreo(correo).orElse(null) != null;
            estadoAdminTeatro = administradorTeatroRepo.findByCorreoActualizar(correo, codigo).orElse(null) != null;
        }

        if(estadoCliente || estadoAdmin || estadoAdminTeatro){
            return true;
        }else{
            return false;
        }
    }

    private AdministradorTeatro actualizarAdminTeatroVerificado(AdministradorTeatro adminTeatro, String passwdEnc){
        boolean isPasswd = passwordEncoder.matches(adminTeatro.getPassword(), passwdEnc);
        if(!isPasswd){
            adminTeatro.setPassword(passwordEncoder.encode(adminTeatro.getPassword()));
            return administradorTeatroRepo.save(adminTeatro);
        }else{
            return administradorTeatroRepo.save(adminTeatro);
        }
    }

    @Override
    public void eliminarAdministradorTeatro(Integer codigo) throws Exception {
        Optional<AdministradorTeatro> administradorTeatro = administradorTeatroRepo.findById(codigo);

        if(administradorTeatro.isEmpty()){
            throw new Exception("El administrador teatro no existe");
        }

        try{
            administradorTeatroRepo.delete(administradorTeatro.get());
        }catch(Exception e){
            throw new Exception("El administrador de teatro con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con uno o más teatros");
        }
    }

    @Override
    public List<AdministradorTeatro> listarAdministradoresTeatro() throws Exception {
        List<AdministradorTeatro> adminsTeatros = administradorTeatroRepo.findAll();

        if(adminsTeatros.isEmpty()){
            throw new Exception("No se han encontrado administradores de teatros");
        }

        return adminsTeatros;
    }

    @Override
    public AdministradorTeatro obtenerAdministradorTeatro(Integer codigo) throws Exception {
        Optional<AdministradorTeatro> administradorTeatro = administradorTeatroRepo.findById(codigo);

        if(administradorTeatro.isEmpty()){
            throw new Exception("El administrador teatro no existe");
        }

        return administradorTeatro.get();
    }
}
