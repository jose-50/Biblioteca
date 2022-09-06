<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.Lector"%>
<%@page import="biblioteca.accesoadatos.LectorDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Lector> lectores = LectorDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slLector" name="idLector">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Lector lector : lectores) {%>
    <option <%=(id == lector.getId()) ? "selected" : ""%>  value="<%=lector.getId()%>"><%= lector.getNombre()%>
        <%= lector.getApellido()%><%= lector.getDui()%>
        
    <%}%>
</select>
<label for="idLector">Lector</label>
