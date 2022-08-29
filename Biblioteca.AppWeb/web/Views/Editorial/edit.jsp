<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.Editorial"%>
<% Editorial editorial= (Editorial) request.getAttribute("editorial");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar  Editorial</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Editorial</h5>
            <form action="Editorial" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=editorial.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=editorial.getNombre()%>" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    
                        <div class="input-field col l4 s12">
                        <input  id="txtIdioma" type="text" name="idioma" value="<%=editorial.getIdioma()%>" required class="validate" maxlength="30">
                        <label for="txtIdioma">Idioma</label>
                        
                        </div>   
                       <div class="input-field col l4 s12">
                        <input  id="txtPais" type="text" name="pais" value="<%=editorial.getPais()%>" required class="validate" maxlength="30">
                        <label for="txtPais">Pais</label>
                    </div>    
                        
                    </div>   
                        
                        
                        
                        
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Editorial" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

