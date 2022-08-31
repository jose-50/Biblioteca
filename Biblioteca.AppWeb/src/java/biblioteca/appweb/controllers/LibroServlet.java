
package biblioteca.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import biblioteca.accesoadatos.CategoriaDAL;
import biblioteca.accesoadatos.EditorialDAL;
import biblioteca.accesoadatos.AutorDAL;


import biblioteca.accesoadatos.LibroDAL;
import biblioteca.appweb.utils.*;
import biblioteca.entidadesdenegocio.Libro;
import biblioteca.entidadesdenegocio.Categoria;
import biblioteca.entidadesdenegocio.Editorial;
import biblioteca.entidadesdenegocio.Autor;

/**
 *
 * @author Alumno
 */
@WebServlet(name = "LibroServlet", urlPatterns = {"/LibroServlet"})
public class LibroServlet extends HttpServlet {

    
    private Libro obtenerLibro(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Libro libro = new Libro();
        libro.setNombre(Utilidad.getParameter(request, "nombre", ""));
                libro.setEdicion(Utilidad.getParameter(request, "edicion", ""));
        libro.setIdAutor(Integer.parseInt(Utilidad.getParameter(request, "idAutor", "0")));
        libro.setIdCategoria(Integer.parseInt(Utilidad.getParameter(request, "idCategoria", "0")));
        libro.setIdEditorial(Integer.parseInt(Utilidad.getParameter(request, "idEditorial", "0")));
        libro.setFechaEdicion(Utilidad.getParameter(request, "fechaEdicion", ""));
return libro;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = new Libro();
            libro.setTop_aux(10);
            ArrayList<Libro> libros = LibroDAL.buscarIncluirACE(libro);
            request.setAttribute("libros", libros);
            request.setAttribute("top_aux", libro.getTop_aux());
            request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }


    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro= obtenerLibro(request);
            ArrayList<Libro> libros = LibroDAL.buscarIncluirACE(libro);
            request.setAttribute("libros", libros);
            request.setAttribute("top_aux", libro.getTop_aux());
            request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Libro/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = obtenerLibro(request);
            int result = LibroDAL.crear(libro);
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
            Libro libro = obtenerLibro(request);
            Libro libro_result = LibroDAL.obtenerPorId(libro);

            if (libro_result.getId() > 0) {
                Autor autor = new Autor();
                autor.setId(libro_result.getIdAutor());
                libro_result.setAutor(AutorDAL.obtenerPorId(autor));
                    
                Categoria categoria = new Categoria();
                categoria.setId(libro_result.getIdCategoria());
                libro_result.setCategoria(CategoriaDAL.obtenerPorId(categoria));
                
                Editorial editorial = new Editorial();
                editorial.setId(libro_result.getIdEditorial());
                libro_result.setEditorial(EditorialDAL.obtenerPorId(editorial));
                
                request.setAttribute("libro", libro_result);
                
                
 
            } else {
                Utilidad.enviarError("El Id:" + libro_result.getId() + " no existe en la tabla de Libro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Libro/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = obtenerLibro(request);
            int result = LibroDAL.modificar(libro);
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
        request.getRequestDispatcher("Views/Libro/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Libro/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = obtenerLibro(request);
            int result = LibroDAL.eliminar(libro);
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