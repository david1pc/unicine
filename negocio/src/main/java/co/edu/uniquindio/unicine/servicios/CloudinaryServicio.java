package co.edu.uniquindio.unicine.servicios;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServicio {

    private Cloudinary cloudinary;
    private Map<String, String> config;

    public CloudinaryServicio(){
        config = new HashMap<>();
        config.put("cloud_name", "dbshgpkhp");
        config.put("api_key", "933959221189739");
        config.put("api_secret", "AyiAu7y2Sd3j9j_lbWUwY5ePYOU");

        cloudinary = new Cloudinary(config);
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
}
