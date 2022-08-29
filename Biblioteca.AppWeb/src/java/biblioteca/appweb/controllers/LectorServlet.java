
package biblioteca.appweb.controllers;

import java.util.ArrayList;
import biblioteca.accesoadatos.LectorDAL;
import biblioteca.entidadesdenegocio.Lector;
import biblioteca.appweb.utils.*;

@WebServlet(name = "LectorServlet", urlPatterns = {"/LectorServlet"})
public class LectorServlet extends HttpServlet {
    private Lector obtenerLector(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Lector lector = new Lector();
        if (accion.equals("create") == false) {
            lector.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }

        lector.setNombre(Utilidad.getParameter(request, "nombre", ""));
        if (accion.equals("index")) {
            lector.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            lector.setTop_aux(lector.getTop_aux() == 0 ? Integer.MAX_VALUE : lector.getTop_aux());
        }
        lector.setApellido(Utilidad.getParameter(request, "apellido", ""));
        if (accion.equals("index")) {
            lector.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            lector.setTop_aux(lector.getTop_aux() == 0 ? Integer.MAX_VALUE : lector.getTop_aux());
        }
        
        lector.setDui(Utilidad.getParameter(request, "dui", ""));
        if (accion.equals("index")) {
            lector.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            lector.setTop_aux(lector.getTop_aux() == 0 ? Integer.MAX_VALUE : lector.getTop_aux());
        }
        return lector;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Lector lector = new Lector();
            lector.setTop_aux(10);
            ArrayList<Lector> lectores = LectorDAL.buscar(lector);
            request.setAttribute("lectores", lectores);
            request.setAttribute("top_aux", lector.getTop_aux());             
            request.getRequestDispatcher("Views/Lector/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Lector lector = obtenerLector(request);
            ArrayList<Lector> lectores = LectorDAL.buscar(lector);
            request.setAttribute("lectores", lectores);
            request.setAttribute("top_aux", lector.getTop_aux());
            request.getRequestDispatcher("Views/Lector/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Lector/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Lector lector = obtenerLector(request);
            int result = LectorDAL.crear(lector);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Lector lector = obtenerLector(request);
            Lector lector_result = LectorDAL.obtenerPorId(lector);
            if (lector_result.getId() > 0) {
                request.setAttribute("lector", lector_result);
            } else {
                Utilidad.enviarError("El Id:" + lector.getId() + " no existe en la tabla de Lector", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Lector/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Lector lector = obtenerLector(request);
            int result = LectorDAL.modificar(lector);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Lector/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Lector/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Lector lector = obtenerLector(request);
            int result = LectorDAL.eliminar(lector);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
   
                    
                    
    
    //</editor-fold>
}
