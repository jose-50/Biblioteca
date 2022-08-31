<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="biblioteca.entidadesdenegocio.PrestamoLibro"%>
<%@page import="biblioteca.entidadesdenegocio.Lector"%>
<%@page import="biblioteca.entidadesdenegocio.Libro"%>

<% ArrayList<PrestamoLibro> prestamoLibros = (ArrayList<PrestamoLibro>) request.getAttribute("prestamoLibros");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (libros == null) {
        libros = new ArrayList();
    } else if (libros.size() > numReg) {
        double divNumPage = (double) libros.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Buscar Libro</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Libro</h5>
            <form action="Libro" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
             <div class="input-field col l4 s12">
                        <jsp:include page="/Views/IdLibro/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                    </div>  
                    
                    
                    <div class="input-field col l4 s12">
                        <jsp:include page="/Views/IdLector/select.jsp">                           
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
                    <div class="input-field col l3 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Libro?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>
                             
                                     <th>IdLibro</th>
                                    <th>IdLector</th>   
                                    <th>FechaEntrega</th>
                                    <th>FechaDevolucion</th>
                                    <th>Acciones</th>
                              </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Libro libros: s) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">
                                    <td><%=libro.getIdLibro()%>
                                     <td><%=libro.getIdLector()%>
                                     <td><%=libro.getFechaEntrega()%>
                                     <td><%=libro.getFechaDevolucion()%>
                                    

                                    </td>                                       
                                    <td>
                                        <div style="display:flex">
                                            <a href="PrestamoLibro?accion=edit&id=<%=prestamolibro.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                                <i class="material-icons">edit</i>
                                            </a>
                                            <a href="PrestamoLibro?accion=details&id=<%=prestamoLibro.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                                <i class="material-icons">description</i>
                                            </a>
                                            <a href="PrestamoLibro?accion=delete&id=<%=prestamoLibro.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                                <i class="material-icons">delete</i>
                                            </a>     
                                        </div>
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />        
    </body>
</html>
