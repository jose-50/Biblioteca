
package biblioteca.entidadesdenegocio;

/**
 *
 * @author DELL
 */
public class Libro {
    
    
    private int id;
    private String nombre;
    private String edicion;
    private int idAutor;
    private int idCategoria;
    private int idEditorial;
    private String FechaEdicion;
    private int top_aux;
    private Autor autor;
    private Categoria categoria;
    private Editorial editorial;

    public Libro() {
    }

    public Libro(int id, String nombre, String edicion, int idAutor, int idCategoria, int idEditorial, String FechaEdicion) {
        this.id = id;
        this.nombre = nombre;
        this.edicion = edicion;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
        this.idEditorial = idEditorial;
        this.FechaEdicion = FechaEdicion;
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

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public String getFechaEdicion() {
        return FechaEdicion;
    }

    public void setFechaEdicion(String FechaEdicion) {
        this.FechaEdicion = FechaEdicion;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

   
}
