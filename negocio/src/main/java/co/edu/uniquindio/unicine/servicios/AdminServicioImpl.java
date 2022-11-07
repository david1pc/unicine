package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicioImpl implements AdminServicio  {

     private CiudadRepo ciudadRepo;
     private PeliculaRepo peliculaRepo;
     private CuponRepo cuponRepo;
     private ConfiteriaRepo confiteriaRepo;
     private ComboRepo comboRepo;
     private AdministradorTeatroRepo administradorTeatroRepo;

    public AdminServicioImpl(CiudadRepo ciudadRepo, PeliculaRepo peliculaRepo, CuponRepo cuponRepo, ConfiteriaRepo confiteriaRepo, ComboRepo comboRepo, AdministradorTeatroRepo administradorTeatroRepo) {
        this.ciudadRepo = ciudadRepo;
        this.peliculaRepo = peliculaRepo;
        this.cuponRepo = cuponRepo;
        this.confiteriaRepo = confiteriaRepo;
        this.comboRepo = comboRepo;
        this.administradorTeatroRepo = administradorTeatroRepo;
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
    public Pelicula crearPelicula(Pelicula pelicula) throws Exception {
        if (pelicula == null){
            throw new Exception("La pelicula no tiene datos");
        }
        return peliculaRepo.save(pelicula);

    }

    private Boolean peliculaExiste (Integer codigo){
        return peliculaRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula) throws Exception {
        boolean peliculaExiste = peliculaExiste(pelicula.getCodigo());
        if (!peliculaExiste){
            throw new Exception("La película no existe");
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
    public Confiteria crearConfiteria(Confiteria confiteria) throws Exception {
        boolean confiteriaExiste = confiteriaExiste(confiteria.getCodigo());
        if (confiteriaExiste){
            throw new Exception("La confitería ya existe");
        }
        return confiteriaRepo.save(confiteria);
    }

    private Boolean confiteriaExiste (Integer codigo){
        return confiteriaRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Confiteria actualizarConfiteria(Confiteria confiteria) throws Exception {
        boolean confiteriaExiste = confiteriaExiste(confiteria.getCodigo());
        if (!confiteriaExiste){
            throw new Exception("La confitería no existe");
        }
        return confiteriaRepo.save(confiteria);
    }

    @Override
    public void eliminarConfiteria(Integer codigo) throws Exception {
        Optional<Confiteria> confiteria = confiteriaRepo.findById(codigo);

        if(confiteria.isEmpty()){
            throw new Exception("la confitería no existe");
        }

        confiteriaRepo.delete(confiteria.get());
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
        boolean administradorExiste = administradorTeatroExiste(administradorTeatro.getCodigo());
        if (administradorExiste){
            throw new Exception("El administrador ya existe");
        }
        return administradorTeatroRepo.save(administradorTeatro);
    }

    private Boolean administradorTeatroExiste (Integer codigo){
        return administradorTeatroRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public AdministradorTeatro actualizarAdministradorTeatro(AdministradorTeatro administradorTeatro) throws Exception {
        boolean administradorExiste = administradorTeatroExiste(administradorTeatro.getCodigo());
        if (!administradorExiste){
            throw new Exception("El administrador no existe");
        }
        return administradorTeatroRepo.save(administradorTeatro);
    }

    @Override
    public void eliminarAdministradorTeatro(Integer codigo) throws Exception {
        Optional<AdministradorTeatro> administradorTeatro = administradorTeatroRepo.findById(codigo);

        if(administradorTeatro.isEmpty()){
            throw new Exception("El combo no existe");
        }

        administradorTeatroRepo.delete(administradorTeatro.get());
    }

    @Override
    public List<AdministradorTeatro> listarAdministradoresTeatro() {
        return administradorTeatroRepo.findAll();
    }

    @Override
    public AdministradorTeatro obtenerAdministradorTeatro(Integer codigo) throws Exception {
        Optional<AdministradorTeatro> administradorTeatro = administradorTeatroRepo.findById(codigo);

        if(administradorTeatro.isEmpty()){
            throw new Exception("El combo no existe");
        }

        return administradorTeatro.get();
    }
}
