package co.edu.uniquindio.unicine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;


public class Encriptacion {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final static String algoritmo = "AES";
    private final static String cI = "AES/CBC/PKCS5Padding";
    private static final String key="VFGTHJ5GT95265GR9H8T4";
    private static byte[] iv = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public static String encriptar(String texto) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algoritmo);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encriptado = cipher.doFinal(texto.getBytes());

        return new String(encodeBase64(encriptado));
    }

    public static String desencriptar(String encriptado) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algoritmo);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        byte[] enc = decodeBase64(encriptado);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] desencriptado = cipher.doFinal(enc);
        return new String(desencriptado);
    }
}
