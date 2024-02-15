/*
s * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.apogee.gnss.angleUnit;

import com.apogee.gnss.DBConnection;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "AngleUnitController", urlPatterns = {"/AngleUnitController"})
public class AngleUnitController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2435157563839707133L;

	/**
	 * 
	 */

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AngleUnitModel model = new AngleUnitModel();

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("name") == null) {
            request.getRequestDispatcher("login_page").forward(request, response);
            return;
        }
        try {
            model.setConnection(DBConnection.getConnection1());
            String submit = request.getParameter("submitFormBtn");
            if (submit == null) {
                submit = "";
            }
            String angleunitId = request.getParameter("angleunit_id");
            if (submit.equals("Submit")) {
                String angleunit = request.getParameter("angleunit");
                String remark = request.getParameter("remark");
                String name = (String) session.getAttribute("name");
                model.saveAngleUnit(angleunit, name, remark);
            }
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgbgcolor", model.getColor());
            request.setAttribute("angleunitId", angleunitId);
            request.setAttribute("allangle", model.allAngleUnits());
            request.setAttribute("angleUnitAllData", model.getAllAngleUnits(angleunitId));
            model.closeConnection();
            request.getRequestDispatcher("angleunit").forward(request, response);
        } catch (Exception e) {

            System.out.println("com.apogee.gnss.controller.AngleUnitController.doGet()"+e);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}
