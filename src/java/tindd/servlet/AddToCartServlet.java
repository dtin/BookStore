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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.cart.CartManager;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AddToCartServlet.class);

    private final String LOGIN_CONTROLLER = "DispatchController?btnAction=Login";
    private final String DISPATCH_CONTROLLER = "DispatchController";

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

        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;
        CartManager cartManager;

        String pageBefore = request.getParameter("pageBefore");
        String searchValue = request.getParameter("searchValue");
        String proIdStr = request.getParameter("productId");

        String url = LOGIN_CONTROLLER;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    url = DISPATCH_CONTROLLER;

                    int roleId = usersDTO.getRoleId();

                    rolesDAO = new TblRolesDAO();
                    String roleName = rolesDAO.getRoleName(roleId);

                    //Only not admin can use this func
                    if (!roleName.equals("Admin")) {
                        if (pageBefore != null) {
                            switch (pageBefore) {
                                case "Search":
                                    //Redirect to search page
                                    url += "?btnAction=Search";
                                    if (searchValue != null) {
                                        //Add search value to parameter
                                        url += "&txtSearch=" + searchValue;
                                    }
                                    break;
                            }
                        }

                        if (proIdStr != null) {
                            int productId = Integer.parseInt(proIdStr);
                            //Get cart from session scope
                            cartManager = (CartManager) session.getAttribute("CART_MANAGER");

                            //Add product to cart
                            if (cartManager != null) {
                                cartManager.addToCart(productId);
                            } else {
                                cartManager = new CartManager();
                                cartManager.addToCart(productId);
                            }

                            //Update new cart to session scope
                            session.setAttribute("CART_MANAGER", cartManager);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
