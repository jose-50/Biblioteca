<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Libro</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Libro </h5>
            <form action="Libro" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
                        <la<bel for="txtNombre">Nombre</label>
                    </div>  
                     <div class="input-field col l4 s12">
                        <input  id="txtEdicion" type="text" name="edicion" required class="validate" maxlength="30">
                        <la<bel for="txtEdicion">Edicion</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <jsp:include page="/Views/Autor/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                    </div>  
                    
                    <div class="input-field col l4 s12">
                        <jsp:include page="/Views/Categoria/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                    </div>  
                     <div class="input-field col l4 s12">
                        <jsp:include page="/Views/Editorial/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                    </div>   
                     <div class="input-field col l4 s12">
                        <input  id="txtFechaEdicion" type="text" name="fechaedicion" required class="validate" maxlength="30">
                        <la<bel for="txtFechaEdicion">FechaEdicion</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Libro" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

