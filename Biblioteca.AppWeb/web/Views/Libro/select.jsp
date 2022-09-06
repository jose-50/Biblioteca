<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.Libro"%>
<%@page import="biblioteca.accesoadatos.LibroDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Libro> libros = LibroDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slLibro" name="idLibro">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Libro libro : libros) {%>
    <option <%=(id == libro.getId()) ? "selected" : ""%>  value="<%=libro.getId()%>"><%= libro.getNombre()%>
        <%= libro.getEdicion()%><%= libro.getIdAutor()%><%= libro.getIdCategoria()%><%= libro.getIdEditorial()%>
        <%= libro.getFechaEdicion()%>
    <%}%>
</select>
<label for="idLibro">Libro</label>
