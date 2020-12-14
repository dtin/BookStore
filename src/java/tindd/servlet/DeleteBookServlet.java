/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblProducts.TblProductsDeleteError;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DeleteBookServlet", urlPatterns = {"/DeleteBookServlet"})
public class DeleteBookServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DeleteBookServlet.class);

    private final String ADMIN_CONTROLLER = "DispatchController";
    private final String LOGIN_PAGE = "DispatchController?btnAction=Login";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        String productIdStr = request.getParameter("productId");

        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;
        TblProductsDAO productsDAO;

        TblProductsDeleteError error = new TblProductsDeleteError();

        String url = LOGIN_PAGE;
        boolean isError = false;
        int productId = -1;
        int roleId;
        String roleName;
        String userName;

        try {
            //Check if user is valid
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    roleId = usersDTO.getRoleId();

                    //Get role name
                    rolesDAO = new TblRolesDAO();
                    roleName = rolesDAO.getRoleName(roleId);

                    //If valid admin
                    if (roleName.equalsIgnoreCase("admin")) {
                        url = ADMIN_CONTROLLER;
                        //Get name of user
                        userName = usersDTO.getName();
                        request.setAttribute("FULL_NAME", userName);

                        productsDAO = new TblProductsDAO();

                        try {
                            productId = Integer.parseInt(productIdStr);
                        } catch (NumberFormatException ex) {
                            isError = true;
                            error.setNotFoundBook("Not found book you want to remove!");
                        }

                        boolean isRemoved = productsDAO.removeBook(productId);

                        if (!isRemoved) {
                            isError = true;
                            error.setNotFoundBook("Not found book you want to remove!");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (!isError) {
                response.sendRedirect(url);
            } else {
                request.setAttribute("ERROR", error);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            out.close();
        }
    }

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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
