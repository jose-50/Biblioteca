
package biblioteca.accesoadatos;
import  biblioteca.entidadesdenegocio.*;
import java.util.*; // Utilizar la utileria de java https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html
import java.sql.*;



public class AutorDAL {
    
    
     static String obtenerCampos() {
        return "a.Id,a.Nombre,a.Pais,a.FechaNacimiento";
    }
   private static String obtenerSelect(Autor pAutor) {
        String sql;
        sql = "SELECT ";
        if (pAutor.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y "getTop_aux" es mayor a cero
            sql += "TOP " + pAutor.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Autor a"); // Agregar los campos de la tabla de Rol mas el FROM a la tabla Rol con su alias "r"
        return sql;
    } 
    
    private static String agregarOrderBy(Autor pAutor) {
        String sql = " ORDER BY a.Id DESC";
        if (pAutor.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Rol en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pAutor.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Autor pAutor) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Autor(Nombre,Pais,FechaNacimiento) VALUES(?,?,?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pAutor.getNombre()); 
                ps.setString(2, pAutor.getPais());
                 ps.setString(3,pAutor.getFechaNacimiento());// Agregar el parametro a la consulta donde estan el simbolo ? #1  
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
    public static int modificar(Autor pAutor) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Autor SET Nombre=?,Pais=?,FechaNacimiento=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pAutor.getNombre()); 
              ps.setString(2, pAutor.getPais()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
              ps.setString(3, pAutor.getFechaNacimiento());
                ps.setInt(4, pAutor.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #2  
                result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; 
    }
    
    
    
    
    
public static int eliminar(Autor pAutor) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Autor WHERE Id=?";  // Definir la consulta DELETE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pAutor.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate();  // Ejecutar la consulta DELETE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda 
        }
        return result; // Retor
    }

static int asignarDatosResultSet(Autor pAutor, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Rol
        pIndex++;
        pAutor.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pAutor.setNombre(pResultSet.getString(pIndex)); 
          pIndex++;// index 2
       pAutor.setPais(pResultSet.getString(pIndex));
       pIndex++;
       pAutor.setFechaNacimiento(pResultSet.getString(pIndex));
              

        return pIndex;
    }
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Autor> pAutores) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Rol
                Autor autor = new Autor(); 
                asignarDatosResultSet(autor, resultSet, 0); // Llenar las propiedaddes de la Entidad Rol con los datos obtenidos de la fila en el ResultSet
                pAutores.add(autor); // Agregar la entidad Rol al ArrayList de Rol
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    public static Autor obtenerPorId(Autor pAutor) throws Exception {
        Autor autor= new Autor();
        ArrayList<Autor> autores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pAutor); // Obtener la consulta SELECT de la tabla Rol
            sql += " WHERE a.Id=?"; // Concatenar a la consulta SELECT de la tabla Rol el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pAutor.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, autores); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (autores.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            autor = autores.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }       return autor ; 
    }   
    
    
        public static ArrayList<Autor> obtenerTodos() throws Exception {
        ArrayList<Autor> autores;
        autores = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Autor());  // Obtener la consulta SELECT de la tabla Rol
            sql += agregarOrderBy(new Autor());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, autores); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return autores; // Devolver el ArrayList de Rol
    }
        
        static void querySelect(Autor pAutor, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pAutor.getId() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Rol
            pUtilQuery.AgregarWhereAnd(" a.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Rol
                statement.setInt(pUtilQuery.getNumWhere(), pAutor.getId()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Rol
        if (pAutor.getNombre() != null && pAutor.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" a.Nombre LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAutor.getNombre() + "%"); 
            }
        }
        if (pAutor.getPais()!= null && pAutor.getPais().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" a.Pais LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAutor.getPais() + "%"); 
            }
        }
        if (pAutor.getFechaNacimiento()!= null && pAutor.getFechaNacimiento().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" a.FechaNacimiento LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAutor.getFechaNacimiento()+ "%"); 
            }
        }
        
    }
        
        
        
        
        
    
    public static ArrayList<Autor> buscar(Autor pAutor) throws Exception {
        ArrayList<Autor> autores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pAutor); // Obtener la consulta SELECT de la tabla Rol
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pAutor, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Rol 
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pAutor); // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pAutor, utilQuery);  // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Rol
                obtenerDatos(ps, autores); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return autores; // Devolver el ArrayList de Rol
    }

    
}

    

