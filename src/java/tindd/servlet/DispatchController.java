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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@MultipartConfig
public class DispatchController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DispatchController.class);

    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final String ADMIN_CONTROLLER = "AdminServlet";
    private final String NEW_BOOK_CONTROLLER = "AddBookServlet";
    private final String UPDATE_BOOK_CONTROLLER = "UpdateBookServlet";
    private final String VIEW_BOOK_CONTROLLER = "ViewBookServlet";
    private final String DELETE_BOOK_CONTROLLER = "DeleteBookServlet";
    private final String ADD_DISCOUNT_CONTROLLER = "AddDiscountServlet";
    private final String VIEW_DISCOUNT_CONTROLLER = "ViewDiscountServlet";
    private final String HISTORY_CONTROLLER = "HistoryServlet";
    private final String VIEW_ORDER_DETAIL_CONTROLLER = "ViewOrderDetailServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet";
    private final String DELETE_CART_ITEM_CONTROLLER = "DeleteCartItemServlet";
    private final String UPDATE_CART_ITEM_CONTROLLER = "UpdateCartItemServlet";
    private final String DISCOUNT_CART_CONTROLLER = "DiscountCartServlet";
    private final String CART_CONTROLLER = "CartServlet";
    private final String CHECKOUT_CONTROLLER = "CheckoutServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";

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

        String url = HOME_CONTROLLER;
        boolean isRedirect = true;
        String roleName = null;
        int roleId;

        String btnAction = request.getParameter("btnAction");

        try {
            //Authorize
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    roleId = usersDTO.getRoleId();
                    //Get role name
                    rolesDAO = new TblRolesDAO();
                    roleName = rolesDAO.getRoleName(roleId).toLowerCase();
                }
            }

            if (btnAction != null) {
                if (roleName == null) {
                    //Guest user, anyone can use
                    switch (btnAction) {
                        case "Login":
                            url = LOGIN_CONTROLLER;
                            isRedirect = false;
                            break;
                        case "Search":
                            url = SEARCH_CONTROLLER;
                            isRedirect = false;
                            break;
                        case "Filter":
                            url = SEARCH_CONTROLLER;
                            isRedirect = false;
                            break;
                        case "Cart":
                            url = CART_CONTROLLER;
                            isRedirect = false;
                            break;
                        case "Add To Cart":
                            url = ADD_TO_CART_CONTROLLER;
                            isRedirect = false;
                            break;

                        case "History":
                            url = HISTORY_CONTROLLER;
                            break;
                    }
                } else {
                    if (roleName.equals("admin")) {
                        //Admin user
                        switch (btnAction) {
                            case "Logout":
                                url = LOGOUT_CONTROLLER;
                                break;
                            case "Search":
                                url = SEARCH_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Filter":
                                url = SEARCH_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Delete":
                                url = DELETE_BOOK_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Update":
                                url = VIEW_BOOK_CONTROLLER + "?type=Update";
                                isRedirect = false;
                                break;
                            case "Update Book":
                                url = UPDATE_BOOK_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Add":
                                url = VIEW_BOOK_CONTROLLER + "?type=Add";
                                isRedirect = false;
                                break;
                            case "Create Book":
                                url = NEW_BOOK_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Discount":
                                url = VIEW_DISCOUNT_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Add Discount":
                                url = ADD_DISCOUNT_CONTROLLER;
                                isRedirect = false;
                                break;
                            default:
                                url = ADMIN_CONTROLLER;
                                break;
                        }
                    } else {
                        //Normal user
                        switch (btnAction) {
                            case "Cart":
                                url = CART_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Search":
                                url = SEARCH_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Filter":
                                url = SEARCH_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Add To Cart":
                                url = ADD_TO_CART_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Delete Cart Item":
                                url = DELETE_CART_ITEM_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Update":
                                url = UPDATE_CART_ITEM_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Apply":
                                url = DISCOUNT_CART_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Checkout":
                                url = CHECKOUT_CONTROLLER;
                                break;
                            case "History":
                                url = HISTORY_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Filter Order":
                                url = HISTORY_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "View Order":
                                url = VIEW_ORDER_DETAIL_CONTROLLER;
                                isRedirect = false;
                                break;
                            case "Logout":
                                url = LOGOUT_CONTROLLER;
                                break;
                        }
                    }
                }
            } else {
                //Admin only, because other roles has default value url
                if (roleName != null && roleName.equals("admin")) {
                    url = ADMIN_CONTROLLER;

                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isRedirect) {
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
