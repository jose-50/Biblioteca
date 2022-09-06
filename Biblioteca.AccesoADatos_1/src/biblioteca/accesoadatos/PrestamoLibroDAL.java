
package biblioteca.accesoadatos;
import static biblioteca.accesoadatos.LibroDAL.asignarDatosResultSet;
        import java.util.*; // Utilizar la utileria de java https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html
import java.sql.*;
import biblioteca.entidadesdenegocio.*;

/**
 *
 * @author DELL
 */
public class PrestamoLibroDAL {
    
    static String obtenerCampos() {
        return "p.Id,p.IdLibro, p.IdLector,  p.FechaEntrega, p.FechaDevolucion";
    }
    private static String obtenerSelect(PrestamoLibro pPrestamoLibro) {
        String sql;
        sql = "SELECT ";
        if (pPrestamoLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pPrestamoLibro.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM PrestamoLibro p");
        return sql;
    }   
    
       private static String agregarOrderBy(PrestamoLibro pPrestamoLibro) {
        String sql = " ORDER BY p.Id DESC";
        if (pPrestamoLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pPrestamoLibro.getTop_aux() + " ";
        }
        return sql;
    } 
    
    
    
    
    
    
    
 public static int crear(PrestamoLibro pPrestamoLibro) throws Exception {
        int result = 0;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO PrestamoLibros(IdLibro,IdLector,FechaEntrega,FechaDevolucion) VALUES(?,?,?,?,)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pPrestamoLibro.getIdLibro()); 
                ps.setInt(2, pPrestamoLibro.getIdLector());
                ps.setString(4, pPrestamoLibro.getFechaEntrega()); 
                ps.setString(5, pPrestamoLibro.getFechaDevolucion()); 
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

    
public static int modificar(PrestamoLibro pPrestamoLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE prestamoLibros SET IdLibro=? ,IdLector=? , FechaEntrega=?,FechaDevolucion=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pPrestamoLibro.getIdLibro()); 
                ps.setInt(2, pPrestamoLibro.getIdLector());
                ps.setString(4, pPrestamoLibro.getFechaEntrega()); 
                ps.setString(5, pPrestamoLibro.getFechaDevolucion()); 
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
public static int eliminar(PrestamoLibro pPrestamoLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM PrestamoLibros WHERE Id=?"; //definir la consulta DELETE a la tabla de Usuario utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pPrestamoLibro.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
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



static int asignarDatosResultSet(PrestamoLibro pPrestamoLibro, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.Id(indice 1), u.IdRol(indice 2), u.Nombre(indice 3), u.Apellido(indice 4), u.Login(indice 5), 
        // u.Estatus(indice 6), u.FechaRegistro(indice 7) * FROM Usuario
        pIndex++;
        pPrestamoLibro.setIdLibro(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pPrestamoLibro.setIdLector(pResultSet.getInt(pIndex)); // index 3
        pIndex++;
        pPrestamoLibro.setFechaEntrega(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pPrestamoLibro.setFechaDevolucion(pResultSet.getString(pIndex)); // index 2
        pIndex++;
       
        
         // index 7
        return pIndex;
    }
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<PrestamoLibro> pPrestamoLibros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario
                PrestamoLibro prestamoLibro = new PrestamoLibro();
                // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(prestamoLibro, resultSet, 0);
                pPrestamoLibros.add(prestamoLibro); // agregar la entidad Usuario al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    } 
    
    
    public static PrestamoLibro obtenerPorId(PrestamoLibro pPrestamoLibro) throws Exception {
        PrestamoLibro prestamoLibro = new PrestamoLibro();
        ArrayList<PrestamoLibro> prestamoLibros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pPrestamoLibro); // Obtener la consulta SELECT de la tabla Rol
            sql += " WHERE p.Id=?"; // Concatenar a la consulta SELECT de la tabla Rol el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pPrestamoLibro.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, prestamoLibros); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (prestamoLibros.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            prestamoLibro = prestamoLibros.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }
        return prestamoLibro; // Devolver el rol encontrado por Id 
    }
    
    public static ArrayList<PrestamoLibro> obtenerTodos() throws Exception {
        ArrayList<PrestamoLibro> prestamoLibros;
        prestamoLibros = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new PrestamoLibro());  // Obtener la consulta SELECT de la tabla Rol
            sql += agregarOrderBy(new PrestamoLibro());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, prestamoLibros); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return prestamoLibros; // Devolver el ArrayList de Rol
    }
    
    
    
    private static void obtenerDatosIncluirACE(PreparedStatement pPS, ArrayList<PrestamoLibro> pPrestamoLibros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            HashMap<Integer, Libro> libroMap = new HashMap();
            HashMap<Integer, Lector> lectorMap = new HashMap();
           
             while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario JOIN a la tabla de Rol
                PrestamoLibro prestamoLibro = new PrestamoLibro();
                 // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                int index = asignarDatosResultSet(prestamoLibro, resultSet, 0);
                if (libroMap.containsKey(prestamoLibro.getIdLibro()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                   Libro libro = new Libro();
                    // en el caso que el Rol no este en el HashMap se asignara
                    LibroDAL.asignarDatosResultSet(libro, resultSet, index);
                    libroMap.put(libro.getId(), libro); // agregar el Rol al  HashMap
                    prestamoLibro.setLibro(libro); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    prestamoLibro.setLibro(libroMap.get(prestamoLibro.getIdLibro())); 
                }
                
                
                if (lectorMap.containsKey(prestamoLibro.getIdLector()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Lector lector = new Lector();
                    // en el caso que el Rol no este en el HashMap se asignara
                    LectorDAL.asignarDatosResultSet(lector, resultSet, index);
                    lectorMap.put(lector.getId(), lector); // agregar el Rol al  HashMap
                    prestamoLibro.setLector(lector); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    prestamoLibro.setLector(lectorMap.get(prestamoLibro.getIdLector())); 
                }
                
                
                pPrestamoLibros.add(prestamoLibro); // Agregar el Usuario de la fila actual al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }

    
   
    
       public static ArrayList<PrestamoLibro> buscar(PrestamoLibro pPrestamoLibro) throws Exception {
        ArrayList<PrestamoLibro> prestamoLibros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pPrestamoLibro); // obtener la consulta SELECT de la tabla Usuario
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pPrestamoLibro, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPrestamoLibro); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPrestamoLibro, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatos(ps, prestamoLibros); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return prestamoLibros; // Devolver el ArrayList de Usuario
    }
    
       static void querySelect(PrestamoLibro pPrestamoLibro, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pPrestamoLibro.getId() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Rol
            pUtilQuery.AgregarWhereAnd(" p.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Rol
                statement.setInt(pUtilQuery.getNumWhere(), pPrestamoLibro.getId()); 
            }
        }
        
        
        
        if ( (int)pPrestamoLibro.getIdLibro() > 0) {
            pUtilQuery.AgregarWhereAnd(" p.IdLibro=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), (int) pPrestamoLibro.getIdLibro());
            }
        }
        
        
        if (pPrestamoLibro.getIdLector()> 0) {
            pUtilQuery.AgregarWhereAnd(" p.IdLector=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pPrestamoLibro.getIdLector());
            }
        }
        
        
        
        
        if (pPrestamoLibro.getFechaEntrega()!= null && pPrestamoLibro.getFechaEntrega().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" p.FechaEntrega LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPrestamoLibro.getFechaEntrega()+ "%"); 
            }
        }
        if (pPrestamoLibro.getFechaDevolucion()!= null && pPrestamoLibro.getFechaDevolucion().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" p.FechaDevolucion LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPrestamoLibro.getFechaDevolucion()+ "%"); 
            }
        }
        
    }
       
       public static ArrayList<PrestamoLibro> buscarIncluirACE(PrestamoLibro pPrestamoLibro) throws Exception {
        ArrayList<PrestamoLibro> prestamoLibros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = "SELECT "; // Iniciar la variables para el String de la consulta SELECT
            if (pPrestamoLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pPrestamoLibro.getTop_aux() + " "; // Agregar el TOP en el caso que se este utilizando SQL SERVER
            }
            sql += obtenerCampos(); // Obtener los campos de la tabla de Usuario que iran en el SELECT
            sql += ",";
            sql += LibroDAL.obtenerCampos(); 
            sql += ",";
            sql += LectorDAL.obtenerCampos(); 
           
            sql += " FROM PrestamoLibro p";
            sql += " JOIN Libro l on (p.IdLibro=l.Id)";
            sql += " JOIN Lector m on (p.IdLector=m.Id)";
            
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pPrestamoLibro, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPrestamoLibro); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPrestamoLibro, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatosIncluirACE(ps, prestamoLibros);
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al e
    
        }
        return prestamoLibros;
    }
 }      
 }

    
    
    
    

