package util;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 05/05/15
 * Time: 02:19 PM
 * To change this template use File | Settings | File Templates.
 */
import com.pixelmed.dicom.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imgscalr.Scalr;

import static com.pixelmed.dicom.TagFromName.Manufacturer;

/**
 *
 * @author Talavera
 */
public class GestionImagen extends Scalr{

    private static BufferedImage BuferImagen;
    private static File Archivo;
    private static ImageIO FotoIO;
    private static String Carpeta;
    private static String Foto;

    /*
     * GETTERS
     */
    public static File getArchivo() {
        return Archivo;
    }

    public static BufferedImage getBuferImagen() {
        return BuferImagen;
    }

    public static String getCarpeta() {
        return Carpeta;
    }

    public static String getFoto() {
        return Foto;
    }

    public static ImageIO getFotoIO() {
        return FotoIO;
    }

    /*
     * SETTERS
     */
    public static void setArchivo(File Archivo) {
        GestionImagen.Archivo = Archivo;
    }

    public static void setBuferImagen(BufferedImage BuferImagen) {
        GestionImagen.BuferImagen = BuferImagen;
    }

    public static void setCarpeta(String Carpeta) {
        GestionImagen.Carpeta = Carpeta;
    }

    public static void setFoto(String Foto) {
        GestionImagen.Foto = Foto;
    }

    public static void setFotoIO(ImageIO FotoIO) {
        GestionImagen.FotoIO = FotoIO;
    }

    /*
     *Cambiar el formato de una imagen a formato DICOM
     *@param Origen
     *          Ruta de origen de la imagen
     *@param Destino
     *          Ruta Destino de la imagen
     *@param NombreFile
     *          Nombre de la imagen
     *@param Nombre
     *          Nombre del Paciente
     *@param IDPaciente
     *          ID del Paciente
     *@param IDEstudio
     *          ID del Estudio
     *@param NSerie
     *          Numero de Serie del Estudio
     *@param NInstancia
     *          Numero de la instancia en la serie
     */
    public static void CambiarADicom(String Origen, String Destino, String Nombre,
                                     String IDPaciente, String IDEstudio, String NSerie, String NInstancia) {
        try {
            ImageToDicom nuevo = new ImageToDicom(Origen, Destino, Nombre, IDPaciente, IDEstudio, NSerie, NInstancia);

        } catch (IOException ex) {
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DicomException ex) {
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     *Cambiar el valor de la etiqueta PatientName en una imagen con formato DICOM
     *@param nameFile
     *          Ruta de origen de la imagen
     *@param newNombre
     *          EL valor para la etiqueta
     */
    public static void CambiarDicomTagPatientName (String nameFile, String newName) throws DicomException {

        AttributeList list = new AttributeList();
        try {
            list.read(nameFile);
            list.replace(TagFromName.PatientName, newName);
            list.write(nameFile,TransferSyntax.ExplicitVRLittleEndian,true,true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DicomException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /*
     *Cambiar el valor de la etiqueta PatientID en una imagen con formato DICOM
     *@param nameFile
     *          Ruta de origen de la imagen
     *@param newNombre
     *          EL valor nuevo para la etiqueta
     */
    public static void CambiarDicomTagPatientID (String nameFile, String newName) throws DicomException {

        AttributeList list = new AttributeList();
        try {
            list.read(nameFile);
            list.replace(TagFromName.PatientID, newName);
            list.write(nameFile,TransferSyntax.ExplicitVRLittleEndian,true,true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DicomException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /*
     *Cambiar el valor de la etiqueta PatientBirthDate en una imagen con formato DICOM
     *@param nameFile
     *          Ruta de origen de la imagen
     *@param newNombre
     *          EL valor nuevo para la etiqueta
     */
    public static void CambiarDicomTagPatientBirthDate (String nameFile, String newName) throws DicomException {

        AttributeList list = new AttributeList();
        try {
            list.read(nameFile);
            list.replace(TagFromName.PatientBirthDate, newName);
            list.write(nameFile,TransferSyntax.ExplicitVRLittleEndian,true,true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DicomException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /*
    *Cambiar el valor de la etiqueta Date en una imagen con formato DICOM
    *@param nameFile
    *          Ruta de origen de la imagen
    *@param newNombre
    *          EL valor nuevo para la etiqueta
    */
    public static void CambiarDicomTagImageDate (String nameFile, String newName) throws DicomException {

        AttributeList list = new AttributeList();
        try {
            list.read(nameFile);
            list.replace(TagFromName.Date, newName);
            list.write(nameFile,TransferSyntax.ExplicitVRLittleEndian,true,true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DicomException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /*
     *Cambiar el valor de la etiqueta StudyDate en una imagen con formato DICOM
     *@param nameFile
     *          Ruta de origen de la imagen
     *@param newNombre
     *          EL valor nuevo para la etiqueta
     */
    public static void CambiarDicomTagStudyDate (String nameFile, String newName) throws DicomException {

        AttributeList list = new AttributeList();
        try {
            list.read(nameFile);
            list.replace(TagFromName.StudyDate, newName);
            list.write(nameFile,TransferSyntax.ExplicitVRLittleEndian,true,true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DicomException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /*
     *  Aplica los cambios de opciones que se le pueden hacer a una imagen
     *
     * @param arg
     *          ruta origen de la imagen
     * @param arg1
     *          ruta destino de la imagen
     * @param op
     *          Opereración BufferedImage para aplicarle a la imagen
     */
    public static void Apply(String arg, String arg1, BufferedImageOp... ops) {
        try {
            File sourceImageFile = new File(arg);
            BufferedImage img;
            BufferedImage thumbnail;
            Foto = arg1;
            img = ImageIO.read(sourceImageFile);

            thumbnail = apply(img, ops);

            thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
            ImageIO.write(thumbnail, "jpg", new File(Foto));
        } catch (IOException ex) {
            System.out.println("Fallo la carga del Archivo");
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ChangeSize(String arg, String arg1, int size, BufferedImageOp... ops) {

        ChangeSize(arg, arg1, size, size, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, ops);
    }

    public static void ChangeSize(String arg, String arg1, Scalr.Method metodo, int size, BufferedImageOp... ops) {

        ChangeSize(arg, arg1, size, size, metodo, Scalr.Mode.AUTOMATIC, ops);

    }

    public static void ChangeSize(String arg, String arg1, Scalr.Mode Modo, int Size, BufferedImageOp... ops) {

        ChangeSize(arg, arg1, Size, Size, Scalr.Method.AUTOMATIC, Modo, ops);

    }

    public static void ChangeSize(String arg, String arg1, int Size, Scalr.Method Metodo, Scalr.Mode Modo, BufferedImageOp... Ops) {

        ChangeSize(arg, arg1, Size, Size, Metodo, Modo, Ops);

    }

    public static void ChangeSize(String arg, String arg1, int Ancho, int Alto, BufferedImageOp... ops) {

        ChangeSize(arg, arg1, Ancho, Alto, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, ops);

    }

    public static void ChangeSize(String arg, String arg1, int Ancho, int Alto, Scalr.Method metodo, BufferedImageOp... ops) {

        ChangeSize(arg, arg1, Ancho, Alto, metodo, Scalr.Mode.AUTOMATIC, ops);

    }

    public static void ChangeSize(String arg, String arg1, int ancho, int alto, Scalr.Mode modo, BufferedImageOp... ops) {

        ChangeSize(arg, arg1, ancho, alto, Scalr.Method.AUTOMATIC, modo, ops);

    }

    /*
     * Función para cambiar de tamaño la imagen
     * @param arg
     *          ruta origen de la imagen
     * @param arg1
     *          ruta destino de la imagen
     * @param size
     *          tamaño a cambiar
     * @param Metodo
     *          Metodo escalar para aplicarle a la imagen
     * @param Modo
     *          Modo Escalar para aplicarle a la imagen @param op Opereración BufferedImage
     * @param op
     *          Opereración BufferedImage para aplicarle a la imagen
     */

    public static void ChangeSize(String arg, String arg1, int Ancho, int Alto, Scalr.Method Metodo, Scalr.Mode Modo, BufferedImageOp... ops) {

        try {
            File sourceImageFile = new File(arg);
            BufferedImage img = ImageIO.read(sourceImageFile);
            BufferedImage thumbnail;
            Foto = arg1;

            thumbnail = resize(img, Metodo, Modo, Ancho, Alto, ops);

            thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
            ImageIO.write(thumbnail, "jpg", new File(Foto));

        } catch (IOException ex) {
            System.out.println("Fallo la carga del Archivo");
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * sesga la imagen a un tamaño específico.
    * @param arg
    *          ruta origen de la imagen
    * @param arg1
    *          ruta destino de la imagen
    * @param ops
    *          Operacion BufferedImage para aplicarle a la imagen
    */
    public static void Crop (String arg, String arg1, int Ancho, int Alto, BufferedImageOp... ops) {

        Crop(arg,arg1,0,0,Ancho,Alto,ops);

    }

    /*
    * sesga la imagen a un tamaño específico.
    * @param arg
    *          ruta origen de la imagen
    * @param arg1
    *          ruta destino de la imagen
    * @param ops
    *          Operacion BufferedImage para aplicarle a la imagen
    */
    public static void Crop (String arg, String arg1, int x, int y, int Ancho, int Alto, BufferedImageOp... ops) {
        try {
            File sourceImageFile = new File(arg);
            BufferedImage img = ImageIO.read(sourceImageFile);
            BufferedImage thumbnail;
            Foto = arg1;

            thumbnail = crop(img, x, y, Ancho, Alto, ops);

            thumbnail.flush();
            thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
            ImageIO.write(thumbnail, "jpg", new File(Foto));
        } catch (IOException ex) {
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Pad (String arg, String arg1, int Padding, BufferedImageOp... ops) {
        Pad (arg, arg1, Padding, Color.BLACK, ops);
    }

    public static void Pad (String arg, String arg1, int Padding, Color color, BufferedImageOp... ops) {
        try {
            File sourceImageFile = new File(arg);
            BufferedImage img = ImageIO.read(sourceImageFile);
            BufferedImage thumbnail;
            Foto = arg1;

            thumbnail = pad(img, Padding, color, ops);

            thumbnail.flush();
            thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
            ImageIO.write(thumbnail, "jpg", new File(Foto));
        } catch (IOException ex) {
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     * Rota la imagen
     * @param arg
     *          ruta origen de la imagen
     * @param arg1
     *          ruta destino de la imagen
     * @param Rotar
     *          Rotación para aplicar a la imagen
     * @param ops
     *          Operacion BufferedImage para aplicarle a la imagen
     */
    public static void Rotate(String arg, String arg1, Scalr.Rotation Rotar, BufferedImageOp... ops) {
        try {
            File sourceImageFile = new File(arg);
            BufferedImage img = ImageIO.read(sourceImageFile);
            BufferedImage thumbnail;
            Foto = arg1;

            thumbnail = rotate(img, Rotar, ops);

            thumbnail.flush();
            thumbnail.createGraphics().drawImage(thumbnail, 0, 0, null);
            ImageIO.write(thumbnail, "jpg", new File(Foto));
        } catch (IOException ex) {
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        setCarpeta("");
        File sourceImageFile = new File("J:/Enrique/wuafle_new.jpg");
        System.out.println("Nombre de Imagen: "+sourceImageFile.getName());
        BufferedImage img;
        /*try {
            img = ImageIO.read(sourceImageFile);
            rotate(img, Scalr.Rotation.CW_180, Scalr.OP_ANTIALIAS, Scalr.OP_DARKER);
        } catch (IOException ex) {
            Logger.getLogger(GestionImagen.class.getName()).log(Level.SEVERE, null, ex);
        } */

        //ChangeSize("J:/Enrique/wuafle_original.jpg",
                //"J:/Enrique/IMAGEN NUEVA/wuafle_new.jpg",300,150);
        //Rotate("J:/Enrique/wuafle_original.jpg",
                //"J:/Enrique/IMAGEN NUEVA/wuafle_volteada.jpg", Scalr.Rotation.CW_180, Scalr.OP_BRIGHTER, Scalr.OP_GRAYSCALE);
        //CambiarADicom("D:/Documents and Settings/Salaprod/Mis documentos/Mis imágenes/wuafle.jpg","J:/Enrique/wuafle.jpg","Enrique","0001","001","1","1");
        //CambiarADicom("J:/Enrique/IMAGEN NUEVA/","J:/Enrique/DICOM/","wuafle_new.jpg","Enrique","0001","001","1","1");
        AttributeList list = new AttributeList();
        Attribute sex = new PersonNameAttribute(TagFromName.PatientSex);
        try {
            list.read("J:/Enrique/DICOM/001/wuafle_new.jpg");
            sex.addValue("Male");
            list.put(TagFromName.PatientSex,sex);
            list.replace(TagFromName.Manufacturer, "YO");
            System.out.println("Lista de atributos "+list);
            list.write("J:/Enrique/DICOM/001/wuafle_new.jpg");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DicomException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("Imagen cambiada");

    }
}
