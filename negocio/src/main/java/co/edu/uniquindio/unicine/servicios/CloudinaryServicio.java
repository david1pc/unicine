package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Imagen;
import co.edu.uniquindio.unicine.repo.ImagenRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServicio {

    private Cloudinary cloudinary;
    private Map<String, String> config;
    @Autowired
    ImagenRepo imagenRepo;

    public CloudinaryServicio(){
        config = new HashMap<>();
        config.put("cloud_name", "dbshgpkhp");
        config.put("api_key", "933959221189739");
        config.put("api_secret", "AyiAu7y2Sd3j9j_lbWUwY5ePYOU");

        cloudinary = new Cloudinary(config);
    }

    public Imagen guardarImagen(MultipartFile imagen, String carpeta) throws IOException {
        Map<String, String> respuesta = subirImagen(imagen, carpeta);
        Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
        Imagen img = this.imagenRepo.save(nueva_imagen);
        return img;
    }

    public Map<String, String> subirImagen(MultipartFile multipartFile, String carpeta) throws IOException {
        File file = convertir(multipartFile);
        Map<String, String> respuesta = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", carpeta));
        return respuesta;
    }

    public Map<String, String> eliminarImagen(String idImagen) throws IOException {
        Map<String, String> respuesta = cloudinary.uploader().destroy(idImagen, ObjectUtils.emptyMap());
        return respuesta;
    }

    private File convertir(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

    public Imagen actualizarImagen(MultipartFile imagen, Imagen img, String carpeta) throws IOException {
        if(img == null){
            return guardarImagen(imagen, carpeta);
        }
        Optional<Imagen> guardada = this.imagenRepo.findById(img.getCodigo());
        eliminarImagen(img.getImagenId());
        Imagen img_update = updateImagen(imagen, carpeta);
        img_update.setCodigo(guardada.get().getCodigo());
        return this.imagenRepo.save(img_update);
    }

    public Imagen updateImagen(MultipartFile imagen, String carpeta) throws IOException {
        Map<String, String> respuesta = subirImagen(imagen, carpeta);
        Imagen nueva_imagen = new Imagen((String)respuesta.get("original_filename"), (String)respuesta.get("url"), (String)respuesta.get("public_id"));
        return nueva_imagen;
    }
}
