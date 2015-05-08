/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.dms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guylangford-lee
 */
@WebServlet(urlPatterns = {"/addlocation"})
public class AddLocation extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getParameter("lat"));
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");
        
        try {
            DataBase db = new DataBase();
            
               
            String addItem = "INSERT INTO LOCATIONS (LAT, LON) VALUES ('" +lat + "','" + lon + "')";
            
            db.execute(addItem);
  
        } catch (InstantiationException ex) {
            Logger.getLogger(AddLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddLocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.getOutputStream().print(lon);
    }



}
