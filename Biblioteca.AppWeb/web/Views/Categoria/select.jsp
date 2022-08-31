<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bilioteca.entidadesdenegocio.Categoria"%>
<%@page import="biblioteca.accesoadatos.CategoriaDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Categoria> categorias = CategoriaDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slAutor" name="idAutor">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Categoria categoria : categorias) {%>
    <option <%=(id == categoria.getId()) ? "selected" : ""%>  value="<%=categoria.getId()%>"><%= categoria.getNombre()%>
        
    <%}%>
</select>
<label for="idCategoria">Categoria</label>
