/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package biblioteca.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import biblioteca.accesoadatos.AutorDAL;
import biblioteca.entidadesdenegocio.Autor;
import biblioteca.appweb.utils.*;



@WebServlet(name = "AutorServlet", urlPatterns = {"/AutorServlet"})
public class AutorServlet extends HttpServlet {
     private Autor obtenerAutor(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Autor autor = new Autor();
        if (accion.equals("create") == false) {
            autor.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }

        autor.setNombre(Utilidad.getParameter(request, "nombre", ""));
        if (accion.equals("index")) {
            autor.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            autor.setTop_aux(autor.getTop_aux() == 0 ? Integer.MAX_VALUE : autor.getTop_aux());
        }
         autor.setPais(Utilidad.getParameter(request, "pais", ""));
        if (accion.equals("index")) {
            autor.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            autor.setTop_aux(autor.getTop_aux() == 0 ? Integer.MAX_VALUE : autor.getTop_aux());
        }
         autor.setFechaNacimiento(Utilidad.getParameter(request, "fechaNacimiento", ""));
         if (accion.equals("index")) {
            autor.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            autor.setTop_aux(autor.getTop_aux() == 0 ? Integer.MAX_VALUE : autor.getTop_aux());
        }
        
        return autor;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Autor autor = new Autor();
            autor.setTop_aux(10);
            ArrayList<Autor> autores = AutorDAL.buscar(autor);
            request.setAttribute("roles", autores);
            request.setAttribute("top_aux", autor.getTop_aux());             
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Autor autor = obtenerAutor(request);
            ArrayList<Autor> autores = AutorDAL.buscar(autor);
            request.setAttribute("roles", autores);
            request.setAttribute("top_aux", autor.getTop_aux());
            request.getRequestDispatcher("Views//index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Autor/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Autor autor = obtenerAutor(request);
            int result = AutorDAL.crear(autor);
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
            Autor autor = obtenerAutor(request);
            Autor autor_result = AutorDAL.obtenerPorId(autor);
            if (autor_result.getId() > 0) {
                request.setAttribute("autor", autor_result);
            } else {
                Utilidad.enviarError("El Id:" + autor.getId() + " no existe en la tabla de Rol", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Autor/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Autor autor = obtenerAutor(request);
            int result = AutorDAL.modificar(autor);
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
        request.getRequestDispatcher("Views/Autor/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Autor/delete.jsp").forward(request, response);
    }
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Autor autor = obtenerAutor(request);
            int result = AutorDAL.eliminar(autor);
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
                case "details": request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    // </editor-fold>
     
    
      
    // </editor-fold         
    
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

    
                
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos para procesar las peticiones Get y Post">
   
                    