<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.Lector"%>
<% Lector lector = (Lector) request.getAttribute("lector");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalles del Lector</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle del Lector</h5>
            <div class="row">
                <div class="input-field col l4 s12">
                    <input disabled  id="txtNombre" type="text" value="<%lector.getNombre()%>">
                    <label for="txtNombre">Nombre</label>
                
                    
                 <div class="input-field col l4 s12">
                    <input disabled  id="txtApellido" type="text" value="<%=lector.getApellido()%>">
                    <label for="txtApellido">Apellido
                    </label>
                </div> 
                    </div>   
                    <div class="input-field col l4 s12">
                    <input disabled  id="txtDui" type="text" value="<%=lector.getDui()%>">
                    <label for="txtDui">Dui</label>
                </div> 
                    
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <a href="Lector?accion=edit&id=<%=lector.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>                        
                    <a href="Lector" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                </div>
            </div>         
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>


