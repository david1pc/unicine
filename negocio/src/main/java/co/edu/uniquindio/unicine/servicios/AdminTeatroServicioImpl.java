package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.repo.FuncionRepo;
import co.edu.uniquindio.unicine.repo.HorarioRepo;
import co.edu.uniquindio.unicine.repo.TeatroRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminTeatroServicioImpl implements AdminTeatroServicio {

    private HorarioRepo horarioRepo;
    private FuncionRepo funcionRepo;
    private TeatroRepo teatroRepo;

    public AdminTeatroServicioImpl(HorarioRepo horarioRepo, FuncionRepo funcionRepo, TeatroRepo teatroRepo) {
        this.horarioRepo = horarioRepo;
        this.funcionRepo = funcionRepo;
        this.teatroRepo = teatroRepo;
    }

    // horario

    @Override
    public Horario crearHorario(Horario horario) throws Exception {
        boolean horarioExiste = horarioExiste(horario.getCodigo());

        if (horarioExiste){
            throw new Exception("El horario ya existe");
        }
        return horarioRepo.save(horario);
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
        return horarioRepo.save(horario);
    }

    @Override
    public void eliminarHorario(Integer codigo) throws Exception {
        Optional<Horario> horario = horarioRepo.findById(codigo);

        if(horario.isEmpty()){
            throw new Exception("El horario no existe");
        }

        horarioRepo.delete(horario.get());
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
        return funcionRepo.save(funcion);
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
        return funcionRepo.save(funcion);
    }

    @Override
    public void eliminarFuncion(Integer codigo) throws Exception {
        Optional<Funcion> funcion = funcionRepo.findById(codigo);

        if(funcion.isEmpty()){
            throw new Exception("La función no existe");
        }

        funcionRepo.delete(funcion.get());
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
}
