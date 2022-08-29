<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.Autor"%>
<% Autor autor = (Autor) request.getAttribute("autor");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalles del Autor</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle del Autor</h5>
            <div class="row">
                <div class="input-field col l4 s12">
                    <input disabled  id="txtNombre" type="text" value="<%=autor.getNombre()%>">
                    <label for="txtNombre">Nombre</label>
                </div>   
                    <div class="input-field col l4 s12">
                    <input disabled  id="txtPais" type="text" value="<%=autor.getPais()%>">
                    <label for="txtPais">Pais</label>
                </div> 
                    
                 <div class="input-field col l4 s12">
                    <input disabled  id="txtFechaNacimiento" type="text" value="<%=autor.getFechaNacimiento()%>">
                    <label for="txtFechaNacimiento">FechaNacimiento</label>
                </div>    
                    
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <a href="Autor?accion=edit&id=<%=autor.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>                        
                    <a href="Autor" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                </div>
            </div>         
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

