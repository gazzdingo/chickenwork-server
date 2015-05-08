/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.dms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author guylangford-lee
 */
@WebServlet(urlPatterns = {"/getlocation"})
public class GetLocation extends HttpServlet {

  
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         ResultSet rs = null;
         JSONArray jsonArray = new JSONArray();
        try {
            DataBase db = new DataBase();
            rs = db.query("select * from LOCATIONS");
            
            while(rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("lat", rs.getString(1));
                 obj.put("lon", rs.getString(2));
                
                jsonArray.put(obj);
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(GetLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GetLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GetLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetLocation.class.getName()).log(Level.SEVERE, null, ex);
        }
         response.getOutputStream().print(jsonArray.toString());
      
    }

   
}
