
package biblioteca.entidadesdenegocio;

import java.util.ArrayList;

public class Autor {
    
     private int id;
    private String nombre;
    private String pais;
    private String fechaNacimiento;
    private int top_aux;
    private ArrayList<Libro> libros;

    public Autor() {
    }
public Autor(int id, String nombre,String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais= pais;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    

