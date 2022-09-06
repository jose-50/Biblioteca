<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear PrestamoLibro</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear PrestamoLibro </h5>
            <form action="PrestamoLibro" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                     
                    <div class="input-field col l4 s12">
                        <jsp:include page="/Views/Libro/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                    </div>  
                    
                    
                    <div class="input-field col l4 s12">
                        <jsp:include page="/Views/Lector/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtFechaEntrega" type="text" name="fechaentrega" required class="validate" maxlength="30">
                        <la<bel for="txtFechaEntrega">FechaEntrega</label>
                    </div> 
                  <div class="input-field col l4 s12">
                        <input  id="txtFechaDevolucion" type="text" name="fechadevolucion" required class="validate" maxlength="30">
                        <la<bel for="txtFechaDevolucion">FechaDevolucion</label>
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="PrestamoLibro" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

