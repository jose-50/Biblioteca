
package biblioteca.entidadesdenegocio;
import java.util.ArrayList;


public class Editorial {
    private int id;
    private String nombre;
    private String idioma;
    private String pais;
    private int top_aux;
    private ArrayList<Libro> libros;
    
    
public Editorial(){}



    public Editorial(int id, String nombre, String idioma, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.idioma = idioma;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

   }  
