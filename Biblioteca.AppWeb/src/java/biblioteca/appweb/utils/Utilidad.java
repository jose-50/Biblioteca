/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package biblioteca.appweb.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utilidad {
    public static String getParameter(HttpServletRequest request, String pKey, String pDefault) {
        String result = "";
        String value = request.getParameter(pKey);
        if (value != null && value.trim().length() > 0) {
            result = value;
        } else {
            result = pDefault;
        }
        return result;
    }
    
    public static void enviarError(String pError, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", pError); 
        request.getRequestDispatcher("Views/Shared/error.jsp").forward(request, response);
    }
    
    public static String obtenerRuta(HttpServletRequest request, String pStrRuta) {
        return request.getContextPath() + pStrRuta;
    }
}
