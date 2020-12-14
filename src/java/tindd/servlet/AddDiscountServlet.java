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
import tindd.tblDiscount.TblDiscountAddError;
import tindd.tblDiscount.TblDiscountDAO;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "AddDiscountServlet", urlPatterns = {"/AddDiscountServlet"})
public class AddDiscountServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AddDiscountServlet.class);

    private final String VIEW_DISCOUNT_CONTROLLER = "DispatchController?btnAction=Discount";
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

        String discountCode = request.getParameter("discountCode");
        String percentStr = request.getParameter("percent");

        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;
        TblDiscountDAO discountDAO;

        String url = LOGIN_PAGE;
        boolean isRedirect = true;
        int roleId;
        float percent = -1;
        String roleName;
        String userName;

        TblDiscountAddError error = new TblDiscountAddError();
        boolean isError = false;

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
                        url = VIEW_DISCOUNT_CONTROLLER;
                        isRedirect = false;

                        //Get name of user
                        userName = usersDTO.getName();
                        request.setAttribute("FULL_NAME", userName);

                        if (percentStr != null) {
                            try {
                                percent = Float.parseFloat(percentStr);
                            } catch (NumberFormatException ex) {
                                isError = true;
                                error.setNotValidPercent("Percent must be numberic!");
                                return;
                            }

                            if (percent <= 0 || percent > 100) {
                                isError = true;
                                error.setOutOfRangePercent("Percent field only allow from 0.1 to 100!");
                                return;
                            }
                        }

                        if (discountCode != null) {
                            discountCode = discountCode.trim().toUpperCase();
                            int discountLength = discountCode.length();

                            if (discountLength == 0 || discountLength > 30) {
                                isError = true;
                                error.setOutOfRangeDiscount("Discount length from 0 to 30 digits!");
                            } else {
                                discountDAO = new TblDiscountDAO();
                                discountDAO.addDiscount(discountCode, percent);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            if (ex.getMessage().contains("duplicate key")) {
                isError = true;
                error.setDuplicateDiscount("There is already a discount code name '" + discountCode + "' in the system!");
            } else {
                LOGGER.error("SQLException: " + ex.getMessage());
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isRedirect) {
                response.sendRedirect(url);
            } else {
                if (isError) {
                    request.setAttribute("ERROR", error);
                }

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
