<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Autor </title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Autor</h5>
            <form action="Autor" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
                        <la<bel for="txtNombre">Nombre</label>
                    </div>  
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtPais" type="text" name="pais" required class="validate" maxlength="30">
                        <la<bel for="txtPais">Pais</label>
                    </div>  
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtFechaNacimiento" type="text" name="fechaNacimiento" required class="validate" maxlength="30">
                        <la<bel for="txtFechaNacimiento">FechaNacimiento</label>
                    </div>  
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Autor" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
