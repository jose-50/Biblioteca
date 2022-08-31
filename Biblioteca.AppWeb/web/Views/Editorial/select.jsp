<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bilioteca.entidadesdenegocio.Editorial"%>
<%@page import="biblioteca.accesoadatos.EditorialDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Editorial> editoriales = EditorialDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slEditorial" name="idEditorial">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Editorial editorial : editoriales) {%>
    <option <%=(id == editorial.getId()) ? "selected" : ""%>  value="<%=editorial.getId()%>"><%= editorial.getNombre()%>
        <%= editorial.getIdioma()%><%= editorial.getPais()%>
        
    <%}%>
</select>
<label for="idEditorial">Editorial</label>
