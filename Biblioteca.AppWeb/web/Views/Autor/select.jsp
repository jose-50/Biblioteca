<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bilioteca.entidadesdenegocio.Autor"%>
<%@page import="biblioteca.accesoadatos.AutorDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Autor> autores = AutorDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slAutor" name="idAutor">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Autor autor : autores) {%>
    <option <%=(id == autor.getId()) ? "selected" : ""%>  value="<%=autor.getId()%>"><%= autor.getNombre()%><%= autor.getPais()%><%= autor.getFechaNacimiento()%></option>
    <%}%>
</select>
<label for="idAutor">Autor</label>
