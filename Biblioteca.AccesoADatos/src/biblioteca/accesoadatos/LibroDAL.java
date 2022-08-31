
package biblioteca.accesoadatos;
import  biblioteca.entidadesdenegocio.*;
import java.util.*; // Utilizar la utileria de java https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html
import java.sql.*;


public class LibroDAL {
    
    static String obtenerCampos() {
        return "l.Id,l.Nombre, l.Edicion,l.IdAutor, l.IdCategoria, l.IdEditorial, l.FechaRegistro";
    }
    private static String obtenerSelect(Libro pLibro) {
        String sql;
        sql = "SELECT ";
        if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pLibro.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Libros l");
        return sql;
    }   
    
       private static String agregarOrderBy(Libro pLibro) {
        String sql = " ORDER BY l.Id DESC";
        if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pLibro.getTop_aux() + " ";
        }
        return sql;
    } 
    
    
    
    
    
    
    
 public static int crear(Libro pLibro) throws Exception {
        int result = 0;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Libros(Nombre,IdAutor,IdCategoria,IdEditorial,FechaRegistro) VALUES(?,?,?,?,?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pLibro.getNombre()); 
                ps.setString(2, pLibro.getEdicion());

                ps.setInt(3, pLibro.getIdAutor());
                ps.setInt(4, pLibro.getIdCategoria()); 
                ps.setInt(5, pLibro.getIdEditorial());
                ps.setString(6, pLibro.getFechaEdicion()); 
                result = ps.executeUpdate(); // Ejecutar la consulta INSERT en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }

    
public static int modificar(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Libros SET Nombre=? ,IdAutor=? , IdCategoria=?,IdEditorial=?,FechaRegistro=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pLibro.getNombre()); 
                ps.setString(2, pLibro.getEdicion());

                ps.setInt(3, pLibro.getIdAutor());
                ps.setInt(4, pLibro.getIdCategoria()); 
                ps.setInt(5, pLibro.getIdEditorial());
                ps.setString(6, pLibro.getFechaEdicion());   
                result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
       return result; // Retorna
    } 
public static int eliminar(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Libros WHERE Id=?"; //definir la consulta DELETE a la tabla de Usuario utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pLibro.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate(); // ejecutar la consulta DELETE en la base de datos
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex;  // enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }



static int asignarDatosResultSet(Libro pLibro, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.Id(indice 1), u.IdRol(indice 2), u.Nombre(indice 3), u.Apellido(indice 4), u.Login(indice 5), 
        // u.Estatus(indice 6), u.FechaRegistro(indice 7) * FROM Usuario
        pIndex++;
        pLibro.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pLibro.setNombre(pResultSet.getString(pIndex)); // index 3
        pIndex++;
        pLibro.setEdicion(pResultSet.getString(pIndex)); // index 3
        pIndex++;
        pLibro.setIdAutor(pResultSet.getInt(pIndex)); // index 2
        pIndex++;
        pLibro.setIdCategoria(pResultSet.getInt(pIndex)); // index 2
        pIndex++;
        pLibro.setIdEditorial(pResultSet.getInt(pIndex)); // index 2
        pIndex++;
        pLibro.setFechaEdicion(pResultSet.getString(pIndex)); // index 3
        
         // index 7
        return pIndex;
    }
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Libro> pLibros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario
                Libro libro = new Libro();
                // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(libro, resultSet, 0);
                pLibros.add(libro); // agregar la entidad Usuario al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    } 
    
    
    public static Libro obtenerPorId(Libro pLibro) throws Exception {
        Libro libro = new Libro();
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pLibro); // Obtener la consulta SELECT de la tabla Rol
            sql += " WHERE l.Id=?"; // Concatenar a la consulta SELECT de la tabla Rol el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pLibro.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, libros); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (libros.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            libro = libros.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }
        return libro; // Devolver el rol encontrado por Id 
    }
    
    public static ArrayList<Libro> obtenerTodos() throws Exception {
        ArrayList<Libro> libros;
        libros = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Libro());  // Obtener la consulta SELECT de la tabla Rol
            sql += agregarOrderBy(new Libro());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, libros); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return libros; // Devolver el ArrayList de Rol
    }
    
    
    
    private static void obtenerDatosIncluirACE(PreparedStatement pPS, ArrayList<Libro> pLibros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            HashMap<Integer, Autor> autorMap = new HashMap();
            HashMap<Integer, Categoria> categoriaMap = new HashMap();
             HashMap<Integer, Editorial> editorialMap = new HashMap();          //crear un HashMap para automatizar la creacion de instancias de la clase Rol
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario JOIN a la tabla de Rol
                Libro libro = new Libro();
                 // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                int index = asignarDatosResultSet(libro, resultSet, 0);
                if (autorMap.containsKey(libro.getIdAutor()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Autor autor = new Autor();
                    // en el caso que el Rol no este en el HashMap se asignara
                    AutorDAL.asignarDatosResultSet(autor, resultSet, index);
                    autorMap.put(autor.getId(), autor); // agregar el Rol al  HashMap
                    libro.setAutor(autor); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    libro.setAutor(autorMap.get(libro.getIdAutor())); 
                }
                
                
                if (categoriaMap.containsKey(libro.getIdCategoria()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Categoria categoria = new Categoria();
                    // en el caso que el Rol no este en el HashMap se asignara
                    CategoriaDAL.asignarDatosResultSet(categoria, resultSet, index);
                    categoriaMap.put(categoria.getId(), categoria); // agregar el Rol al  HashMap
                    libro.setCategoria(categoria); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    libro.setCategoria(categoriaMap.get(libro.getIdCategoria())); 
                }
                if (editorialMap.containsKey(libro.getIdEditorial()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Editorial editorial = new Editorial();
                    // en el caso que el Rol no este en el HashMap se asignara
                    EditorialDAL.asignarDatosResultSet(editorial, resultSet, index);
                    editorialMap.put(editorial.getId(), editorial); // agregar el Rol al  HashMap
                    libro.setEditorial(editorial); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    libro.setCategoria(categoriaMap.get(libro.getIdCategoria())); 
                }
                
                pLibros.add(libro); // Agregar el Usuario de la fila actual al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }

    
   
    
       public static ArrayList<Libro> buscar(Libro pLibro) throws Exception {
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pLibro); // obtener la consulta SELECT de la tabla Usuario
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pLibro, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pLibro); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pLibro, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatos(ps, libros); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return libros; // Devolver el ArrayList de Usuario
    }
    
       static void querySelect(Libro pLibro, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pLibro.getId() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Rol
            pUtilQuery.AgregarWhereAnd(" l.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Rol
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getId()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Rol
        if (pLibro.getNombre() != null && pLibro.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" l.Nombre LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLibro.getNombre() + "%"); 
            }
        }
        if (pLibro.getNombre() != null && pLibro.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" l.Edicion LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLibro.getEdicion() + "%"); 
            }
        }
        
        
        if ( (int)pLibro.getIdAutor() > 0) {
            pUtilQuery.AgregarWhereAnd(" l.IdAutor=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), (int) pLibro.getIdAutor());
            }
        }
        
        
        if (pLibro.getIdCategoria()> 0) {
            pUtilQuery.AgregarWhereAnd(" l.IdCategoria=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getIdCategoria());
            }
        }
        
        
        if (pLibro.getIdEditorial()> 0) {
            pUtilQuery.AgregarWhereAnd(" l.IdEditorial=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pLibro.getIdEditorial());
            }
        }
        
        
        
        if (pLibro.getFechaEdicion()!= null && pLibro.getFechaEdicion().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" l.FechaRegistro LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLibro.getFechaEdicion()+ "%"); 
            }
        }
        
    }
       
       public static ArrayList<Libro> buscarIncluirACE(Libro pLibro) throws Exception {
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = "SELECT "; // Iniciar la variables para el String de la consulta SELECT
            if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pLibro.getTop_aux() + " "; // Agregar el TOP en el caso que se este utilizando SQL SERVER
            }
            sql += obtenerCampos(); // Obtener los campos de la tabla de Usuario que iran en el SELECT
            sql += ",";
            sql += AutorDAL.obtenerCampos(); 
            sql += CategoriaDAL.obtenerCampos(); 
            sql += EditorialDAL.obtenerCampos(); 
           
            sql += " FROM Libros l";
            sql += " JOIN Autor a on (l.IdAutor=a.Id)";
            sql += " JOIN Categoria c on (l.IdCategoria=c.Id)";
            sql += " JOIN Editorial e on (l.IdEditorial=e.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pLibro, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pLibro); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pLibro, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatosIncluirACE(ps, libros);
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al e
    
        }
        return libros;
    }
 }      
 }
