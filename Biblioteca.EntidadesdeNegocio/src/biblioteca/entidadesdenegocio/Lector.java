/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.entidadesdenegocio;

import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class Lector {
    private int id;
    private String nombre;
    private String apellido;
    private String dui;
    private int top_aux;
     private ArrayList<PrestamoLibro> prestamoLibros;

    public Lector(int id, String nombre, String apellido, String dui) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = dui;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<PrestamoLibro> getPrestamoLibros() {
        return prestamoLibros;
    }

    public void setPrestamoLibros(ArrayList<PrestamoLibro> prestamoLibros) {
        this.prestamoLibros = prestamoLibros;
    }
    
}
