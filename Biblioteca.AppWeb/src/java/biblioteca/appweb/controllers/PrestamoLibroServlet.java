package biblioteca.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import biblioteca.accesoadatos.LibroDAL;
import biblioteca.accesoadatos.LectorDAL;
import biblioteca.accesoadatos.PrestamoLibroDAL;
import biblioteca.entidadesdenegocio.Libro;
import biblioteca.entidadesdenegocio.Lector;
import biblioteca.entidadesdenegocio.PrestamoLibro;
import jakarta.servlet.http.HttpServletRequest;
import biblioteca.appweb.utils.*;


@WebServlet(name = "PrestamoLibroServlet", urlPatterns = {"/PrestamoLibro"})
public class PrestamoLibroServlet extends HttpServlet {


    
    private PrestamoLibro obtenerPrestamoLibro(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        PrestamoLibro prestamoLibro = new PrestamoLibro();
        prestamoLibro.setIdLibro(Integer.parseInt(Utilidad.getParameter(request, "idLibro", "0")));
        prestamoLibro.setIdLector(Integer.parseInt(Utilidad.getParameter(request, "idLector", "0")));
        prestamoLibro.setFechaEntrega(Utilidad.getParameter(request, "fechaEntrega", ""));
        prestamoLibro.setFechaDevolucion(Utilidad.getParameter(request, "fechaDevolucion", ""));
return prestamoLibro;

    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrestamoLibro prestamoLibro = new PrestamoLibro();
            prestamoLibro.setTop_aux(10);
            ArrayList<PrestamoLibro> prestamoLibros = PrestamoLibroDAL.buscarIncluirACE(prestamoLibro);
            request.setAttribute("prestamoLibros", prestamoLibros);
            request.setAttribute("top_aux", prestamoLibro.getTop_aux());
            request.getRequestDispatcher("Views/PrestamoLibro/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrestamoLibro prestamoLibro= obtenerPrestamoLibro(request);
            ArrayList<PrestamoLibro> prestamoLibros = PrestamoLibroDAL.buscarIncluirACE(prestamoLibro);
            request.setAttribute("prestamoLibros", prestamoLibros);
            request.setAttribute("top_aux", prestamoLibro.getTop_aux());
            request.getRequestDispatcher("Views/PrestamoLibro/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/PrestamoLibro/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrestamoLibro prestamoLibro = obtenerPrestamoLibro(request);
            int result = PrestamoLibroDAL.crear(prestamoLibro);
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
            PrestamoLibro prestamoLibro = obtenerPrestamoLibro(request);
             PrestamoLibro prestamoLibro_result = PrestamoLibroDAL.obtenerPorId(prestamoLibro);

            if (prestamoLibro_result.getId() > 0) {
                Libro libro = new Libro();
                prestamoLibro.setId(prestamoLibro_result.getIdLibro());
                prestamoLibro_result.setLibro(LibroDAL.obtenerPorId(libro));
                    
                Lector lector = new Lector();
                lector.setId(prestamoLibro_result.getIdLector());
                prestamoLibro_result.setLector(LectorDAL.obtenerPorId(lector));
                
                request.setAttribute("Prestamolibro", prestamoLibro_result);
                
                
 
            } else {
                Utilidad.enviarError("El Id:" + prestamoLibro_result.getId() + " no existe en la tabla de PrestamoLibro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/PrestamoLibro/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrestamoLibro prestamoLibro = obtenerPrestamoLibro(request);
            int result = PrestamoLibroDAL.modificar(prestamoLibro);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/PrestamoLibro/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/PrestamoLibro/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrestamoLibro prestamoLibro = obtenerPrestamoLibro(request);
            int result = PrestamoLibroDAL.eliminar(prestamoLibro);
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

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    //</editor-fold>
}