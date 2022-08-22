
package biblioteca.accesoadatos;
import biblioteca.entidadesdenegocio.*;
import java.util.*; // Utilizar la utileria de java https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html
import java.sql.*;


public class LectorDAL {
    static String obtenerCampos() {
        return "l.Id,l.Nombre,l.Apellido,l.Dui";
        
        }  
    private static String obtenerSelect(Lector pLector) {
        String sql;
        sql = "SELECT ";
        if (pLector.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y "getTop_aux" es mayor a cero
            sql += "TOP " + pLector.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Lector l"); // Agregar los campos de la tabla de Rol mas el FROM a la tabla Rol con su alias "r"
        return sql;
    } 
    
    private static String agregarOrderBy(Lector pLector) {
        String sql = " ORDER BY l.Id DESC";
        if (pLector.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Rol en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pLector.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Lector pLector) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Lector(Nombre,Apellido,Dui) VALUES(?,?,?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pLector.getNombre()); 
                ps.setString(2, pLector.getApellido());
                 ps.setString(3,pLector.getDui());// Agregar el parametro a la consulta donde estan el simbolo ? #1  
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
    public static int modificar(Lector pLector) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Lector SET Nombre=?,Apellido=?,Dui=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pLector.getNombre()); 
              ps.setString(2, pLector.getApellido()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
              ps.setString(3, pLector.getDui());
                ps.setInt(4, pLector.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #2  
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
    
    
    
    
    
public static int eliminar(Lector pLector) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Lector WHERE Id=?";  // Definir la consulta DELETE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pLector.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
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

static int asignarDatosResultSet(Lector pLector, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Lector
        pIndex++;
        pLector.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pLector.setNombre(pResultSet.getString(pIndex)); 
          pIndex++;// index 2
       pLector.setApellido(pResultSet.getString(pIndex));
       pIndex++;
       pLector.setDui(pResultSet.getString(pIndex));
              

        return pIndex;
    }
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Lector> pLectores) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Rol
                Lector lector = new Lector(); 
                asignarDatosResultSet(lector, resultSet, 0); // Llenar las propiedaddes de la Entidad Rol con los datos obtenidos de la fila en el ResultSet
                pLectores.add(lector); // Agregar la entidad Rol al ArrayList de Rol
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    public static Lector obtenerPorId(Lector pLector) throws Exception {
        Lector lector= new Lector();
        ArrayList<Lector> lectores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pLector); // Obtener la consulta SELECT de la tabla Rol
            sql += " WHERE l.Id=?"; // Concatenar a la consulta SELECT de la tabla Rol el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pLector.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, lectores); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (lectores.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            lector = lectores.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }       return lector ; 
    }   
    
    
        public static ArrayList<Lector> obtenerTodos() throws Exception {
        ArrayList<Lector> lectores;
        lectores = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Lector());  // Obtener la consulta SELECT de la tabla Rol
            sql += agregarOrderBy(new Lector());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, lectores); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return lectores; // Devolver el ArrayList de Rol
    }
        
        static void querySelect(Lector pLector, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pLector.getId() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Rol
            pUtilQuery.AgregarWhereAnd(" l.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Rol
                statement.setInt(pUtilQuery.getNumWhere(), pLector.getId()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Rol
        if (pLector.getNombre() != null && pLector.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" l.Nombre LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLector.getNombre() + "%"); 
            }
        }
        if (pLector.getApellido()!= null && pLector.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" l.Apellido LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLector.getApellido() + "%"); 
            }
        }
        if (pLector.getDui
        ()!= null && pLector.getDui().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" l.Dui LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pLector.getDui()+ "%"); 
            }
        }
        
    }
        
        
        
        
        
    
    public static ArrayList<Lector> buscar(Lector pLector) throws Exception {
        ArrayList<Lector> lectores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pLector); // Obtener la consulta SELECT de la tabla Rol
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pLector, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Rol 
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pLector); // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pLector, utilQuery);  // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Rol
                obtenerDatos(ps, lectores); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return lectores; // Devolver el ArrayList de Rol
    }

    
}

    



    
        