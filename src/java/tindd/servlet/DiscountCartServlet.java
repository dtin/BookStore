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
import tindd.tblDiscount.TblDiscountDAO;
import tindd.tblDiscount.TblDiscountDTO;
import tindd.tblDiscount.TblDiscountError;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DiscountCartServlet", urlPatterns = {"/DiscountCartServlet"})
public class DiscountCartServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DiscountCartServlet.class);

    private final String CART_CONTROLLER = "DispatchController?btnAction=Cart";

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

        String discountStr = request.getParameter("discountCode");

        //Change discount code to uppercase
        String discountCode = discountStr.toUpperCase();

        TblUsersDTO usersDTO;
        TblDiscountDAO discountDAO;
        TblOrdersDAO ordersDAO;
        TblDiscountDTO discountDTO;
        String userId;
        TblDiscountError error = new TblDiscountError();

        String url = CART_CONTROLLER;
        boolean isError = false;
        boolean isUsedBefore;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    if (discountCode != null && !discountCode.trim().isEmpty()) {
                        discountDAO = new TblDiscountDAO();
                        discountDTO = discountDAO.getDiscount(discountCode);
                        if (discountDTO != null) {
                            //Check if user was already use this discount before
                            ordersDAO = new TblOrdersDAO();
                            userId = usersDTO.getUserId();
                            isUsedBefore = ordersDAO.isDiscountBefore(userId, discountCode);

                            if (!isUsedBefore) {
                                session.setAttribute("DISCOUNT", discountDTO);
                            } else {
                                error.setAlreadyUse("You already used this discount code before!");
                                isError = true;
                            }
                        } else {
                            error.setDiscountNotValid("Discount code '" + discountStr + "' is not valid!");
                            isError = true;
                        }
                    } else {
                        error.setEmptyDiscount("Can not apply empty code!");
                        isError = true;
                    }
                }
            }

        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                request.setAttribute("ERROR", error);

                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
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
