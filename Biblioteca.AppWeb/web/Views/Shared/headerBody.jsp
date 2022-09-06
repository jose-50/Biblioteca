<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.appweb.utils.*"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>

<nav>
    <div class="nav-wrapper blue">
        <a href="Home" class="brand-logo">BIBLIOTECA</a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>       
        <ul class="right hide-on-med-and-down">  
            <% if (SessionUser.isAuth(request)) {  %>
            <li><a href="Home">Inicio</a></li>
            <li><a href="Autor">Autores</a></li>
            <li><a href="Libro">Libros</a></li>
            <li><a href="Categoria">Categorias</a></li>
            <li><a href="Editorial">Editoriales</a></li>
            <li><a href="Lector">Lectores</a></li>
            <li><a href="PrestamoLibro">PrestamoLibros</a></li>
            <li><a href="Usuario">Usuarios</a></li>
            <li><a href="Rol">Roles</a></li>
            <li><a href="Usuario?accion=cambiarpass">Cambiar password</a></li>
            <li><a href="Usuario?accion=login">Cerrar sesión</a></li>
            <%}%>
        </ul>
    </div>
</nav>

<ul class="sidenav" id="mobile-demo">
     <% if (SessionUser.isAuth(request)) {  %>
     <li><a href="Home">Inicio</a></li>
    
            <li><a href="Usuario">Usuarios</a></li>
            <li><a href="Autor">Autores</a></li>
            <li><a href="Libro">Libros</a></li>
            <li><a href="Categoria">Categorias</a></li>
            <li><a href="Editorial">Editoriales</a></li>
            <li><a href="Lector">Lectores</a></li>
            <li><a href="PrestamoLibro">PrestamoLibros</a></li>
             <li><a href="Rol">Roles</a></li>
             <li><a href="Usuario?accion=cambiarpass">Cambiar password</a></li>
                <li><a href="Usuario?accion=login">Cerrar sesión</a></li>
              <%}%>
</ul>
