/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tindd.cart.CartManager;

/**
 *
 * @author Tin
 */
@WebServlet(name = "UpdateCartItemServlet", urlPatterns = {"/UpdateCartItemServlet"})
public class UpdateCartItemServlet extends HttpServlet {

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
        String proIdStr = request.getParameter("productId");
        String qtyStr = request.getParameter("txtQuantity");

        CartManager cartManager;
        int quantity;

        String url = CART_CONTROLLER;
        boolean isError = false;

        try {
            if (session != null) {
                cartManager = (CartManager) session.getAttribute("CART_MANAGER");
                if (cartManager != null && proIdStr != null && qtyStr != null) {
                    int productId = Integer.parseInt(proIdStr);

                    try {
                        //Try to parse quantity
                        quantity = Integer.parseInt(qtyStr);
                    } catch (NumberFormatException ex) {
                        isError = true;
                        String quantityError = "Quantity must be numberic!";
                        request.setAttribute("QUANTITY_ERROR", quantityError);
                        return;
                    }

                    boolean isUpdated = cartManager.updateItemCart(productId, quantity);

                    if (isUpdated) {
                        session.setAttribute("CART_MANAGER", cartManager);
                    }
                }
            }
        } finally {
            if (!isError) {
                response.sendRedirect(url);
            } else {
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
