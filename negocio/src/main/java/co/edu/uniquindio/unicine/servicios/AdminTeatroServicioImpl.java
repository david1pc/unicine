package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AdminTeatroServicioImpl implements AdminTeatroServicio {

    private HorarioRepo horarioRepo;
    private FuncionRepo funcionRepo;
    private TeatroRepo teatroRepo;
    private SalaRepo salaRepo;
    private DistribucionSillasRepo distribucionSillasRepo;

    public AdminTeatroServicioImpl(HorarioRepo horarioRepo, FuncionRepo funcionRepo, TeatroRepo teatroRepo, SalaRepo salaRepo, DistribucionSillasRepo distribucionSillasRepo) {
        this.horarioRepo = horarioRepo;
        this.funcionRepo = funcionRepo;
        this.teatroRepo = teatroRepo;
        this.salaRepo = salaRepo;
        this.distribucionSillasRepo = distribucionSillasRepo;
    }

    // horario

    @Override
    public Horario crearHorario(Horario horario) throws Exception {
        if(horario == null){
            throw new Exception("El horario no tiene datos");
        }

        boolean horarioExiste = horarioExiste(horario.getCodigo());

        if (horarioExiste){
            throw new Exception("El horario ya existe");
        }

        verificarFechasHorario(horario);

        return horarioRepo.save(horario);
    }

    private void verificarFechasHorario(Horario horario) throws Exception {
        if(horario.getFecha_fin().isBefore(horario.getFecha_inicio())){
            throw new Exception("La fecha de fin no puede ir antes que la fecha de inicio");
        }
    }

    private Boolean horarioExiste (Integer codigo){
        return horarioRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Horario actualizarHorario(Horario horario) throws Exception {
        boolean horarioExiste = horarioExiste(horario.getCodigo());

        if (!horarioExiste){
            throw new Exception("El horario no existe");
        }

        verificarFechasHorario(horario);

        return horarioRepo.save(horario);
    }

    @Override
    public void eliminarHorario(Integer codigo) throws Exception {
        Optional<Horario> horario = horarioRepo.findById(codigo);

        if(horario.isEmpty()){
            throw new Exception("El horario no existe");
        }

        try{
            horarioRepo.delete(horario.get());
        }catch(Exception e){
            throw new Exception("El horario con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o mas funciones");
        }
    }

    @Override
    public List<Horario> listarHorarios() {
        return horarioRepo.findAll();
    }

    @Override
    public Horario obtenerHorario(Integer codigo) throws Exception {
        Optional<Horario> horario = horarioRepo.findById(codigo);

        if(horario.isEmpty()){
            throw new Exception("El horario no existe");
        }

        return horario.get();

    }

    // funcion

    @Override
    public Funcion crearFuncion(Funcion funcion) throws Exception {
        boolean funcionExiste = funcionExiste(funcion.getCodigo());

        if (funcionExiste){
            throw new Exception("La función ya existe");
        }

        Boolean esFuncionOcupada = verificarFuncionHorario(funcion);

        if(esFuncionOcupada){
            throw new Exception("La función no puede crearse. La funcion se presenta a la misma hora con otra funcion en la misma sala, teatro y dia");
        }

        return funcionRepo.save(funcion);
    }

    private Boolean verificarFuncionHorario(Funcion funcion) throws Exception {
        List<Funcion> funciones = this.funcionRepo.verificarHorarioFuncion(funcion.getHorario().getHora(), funcion.getSala().getCodigo(), funcion.getSala().getTeatro().getCodigo());
        List<String> dias_nueva_fun = Arrays.asList(funcion.getHorario().getDia().split(" "));
        AtomicReference<Boolean> esFuncionOcupada = new AtomicReference<>(false);

        if(!funciones.isEmpty()){
            funciones.forEach(f -> {
                String[] dias_fun = f.getHorario().getDia().split(" ");
                for(String dia : dias_fun){
                    if(dias_nueva_fun.contains(dia)){
                        if(f.getHorario().getFecha_fin().isBefore(funcion.getHorario().getFecha_inicio())){
                            esFuncionOcupada.set(false);
                        }else{
                            esFuncionOcupada.set(true);
                            break;
                        }
                    }
                }
            });
        }

        return esFuncionOcupada.get();
    }

    private Boolean funcionExiste (Integer codigo){
        return funcionRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) throws Exception {
        boolean funcionExiste = funcionExiste(funcion.getCodigo());

        if (!funcionExiste){
            throw new Exception("La función no existe");
        }

        Boolean esFuncionOcupada = verificarFuncionHorario(funcion);

        if(esFuncionOcupada){
            throw new Exception("La funcion con id " + funcion.getCodigo() + ", no puede actualizarse. La funcion se presenta a la misma hora con otra funcion en la misma sala, teatro y dia");
        }


        return funcionRepo.save(funcion);
    }

    @Override
    public void eliminarFuncion(Integer codigo) throws Exception {
        Optional<Funcion> funcion = funcionRepo.findById(codigo);

        if(funcion.isEmpty()){
            throw new Exception("La función no existe");
        }

        try{
            funcionRepo.delete(funcion.get());
        }catch(Exception e){
            throw new Exception("La funcion con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o mas compras");
        }
    }

    @Override
    public List<Funcion> listarFunciones() {
        return funcionRepo.findAll();
    }

    @Override
    public Funcion obtenerFuncion(Integer codigo) throws Exception {
        Optional<Funcion> funcion = funcionRepo.findById(codigo);

        if(funcion.isEmpty()){
            throw new Exception("La función no existe");
        }

       return funcion.get();
    }
    // Teatros
    @Override
    public Teatro crearTeatro(Teatro teatro) throws Exception {
        if (teatro == null){
            throw new Exception("El teatro no tiene datos");
        }

        return teatroRepo.save(teatro);
    }

    private Boolean teatroExiste (Integer codigo){
        return teatroRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public Teatro actualizarTeatro(Teatro teatro) throws Exception {
        boolean teatroExiste = teatroExiste(teatro.getCodigo());

        if (!teatroExiste || teatro == null){
            throw new Exception("El teatro no existe");
        }

        return teatroRepo.save(teatro);
    }

    @Override
    public void eliminarTeatro(Integer codigo) throws Exception {
        Optional<Teatro> teatro = teatroRepo.findById(codigo);

        if(teatro.isEmpty()){
            throw new Exception("El teatro no existe");
        }

        try{
            teatroRepo.delete(teatro.get());
        }catch(Exception e){
            throw new Exception("El teatro con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o mas salas");
        }
    }

    @Override
    public List<Teatro> listarTeatros() {
        return teatroRepo.findAll();
    }

    @Override
    public Teatro obtenerTeatro(Integer codigo) throws Exception {
        Optional<Teatro> teatro = teatroRepo.findById(codigo);

        if(teatro.isEmpty()){
            throw new Exception("El teatro no existe");
        }
        return teatro.get();
    }

    @Override
    public List<Sala> listarSalas() throws Exception {
        return this.salaRepo.findAll();
    }

    @Override
    public Sala crearSala(Sala sala) throws Exception {
        if (sala == null){
            throw new Exception("La sala no tiene datos");
        }

        return salaRepo.save(sala);
    }

    @Override
    public Sala actualizarSala(Sala sala) throws Exception {
        boolean salaExiste = salaExiste(sala.getCodigo());

        if (!salaExiste || sala == null){
            throw new Exception("La sala no existe");
        }

        return salaRepo.save(sala);
    }

    private boolean salaExiste(Integer codigo) {
        return salaRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public void eliminarSala(Integer codigo) throws Exception {
        Optional<Sala> sala = salaRepo.findById(codigo);

        if(sala.isEmpty()){
            throw new Exception("La sala no existe");
        }

        try{
            salaRepo.delete(sala.get());
        }catch(Exception e){
            throw new Exception("La sala con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o mas funciones");
        }
    }

    @Override
    public List<DistribucionSillas> listarDistribucionSillas() throws Exception {
        return this.distribucionSillasRepo.findAll();
    }

    @Override
    public DistribucionSillas crearDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        if (distribucionSillas == null){
            throw new Exception("La distribucion de sillas no tiene datos");
        }

        return distribucionSillasRepo.save(distribucionSillas);
    }

    @Override
    public DistribucionSillas actualizarDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        boolean distribucionExiste = distribucionSillasExiste(distribucionSillas.getCodigo());

        if (!distribucionExiste || distribucionSillas == null){
            throw new Exception("La distribucion de sillas no existe");
        }

        return distribucionSillasRepo.save(distribucionSillas);
    }

    private boolean distribucionSillasExiste(Integer codigo) {
        return distribucionSillasRepo.findById(codigo).orElse(null) != null;
    }

    @Override
    public void eliminarDistribucionSillas(Integer codigo) throws Exception {
        Optional<DistribucionSillas> distribucionSillas = distribucionSillasRepo.findById(codigo);

        if(distribucionSillas.isEmpty()){
            throw new Exception("La distribucion de sillas no existe");
        }

        try{
            distribucionSillasRepo.delete(distribucionSillas.get());
        }catch(Exception e){
            throw new Exception("La distribucion de sillas con id: " + codigo + ", no puede eliminarse. Se encuentra relacionada con una o mas salas");
        }
    }
}
