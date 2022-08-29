
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.Lector"%>
<% Lector lector= (Lector) request.getAttribute("lector");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar  Lector</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Lector</h5>
            <form action="Lector" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=lector.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=lector.getNombre()%>" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    
                        <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="apellido" value="<%=lector.getApellido()%>" required class="validate" maxlength="30">
                        <label for="txtApellido">Apellido</label>
                        
                        </div>   
                       <div class="input-field col l4 s12">
                        <input  id="txtDui" type="text" name="dui" value="<%=lector.getDui()%>" required class="validate" maxlength="30">
                        <label for="txtDui">Dui</label>
                    </div>    
                        
                    </div>   
                        
                        
                        
                        
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Lector" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
