/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.cart.CartManager;
import tindd.tblDiscount.TblDiscountDTO;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblProducts.TblProductsDTO;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(CartServlet.class);

    private final String LOGIN_CONTROLLER = "DispatchController?btnAction=Login";
    private final String CART_PAGE = "cart.jsp";

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

        TblRolesDAO rolesDAO;
        TblProductsDAO productsDAO;
        TblDiscountDTO discountDTO;
        ArrayList<TblProductsDTO> productsList;
        long totalPrice = 0;

        String url = LOGIN_CONTROLLER;
        boolean isRedirect = true;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    //Get user full name
                    String name = usersDTO.getName();
                    request.setAttribute("FULL_NAME", name);

                    //Get user role
                    rolesDAO = new TblRolesDAO();
                    int roleId = usersDTO.getRoleId();
                    String roleName = rolesDAO.getRoleName(roleId);

                    if (!roleName.equalsIgnoreCase("admin")) {
                        CartManager cartManager = (CartManager) session.getAttribute("CART_MANAGER");
                        //Create a list having all information for cart page
                        if (cartManager != null) {
                            Set<Integer> productIdKeys = cartManager.getCartManager().keySet();
                            if (!productIdKeys.isEmpty()) {
                                productsList = new ArrayList<>();
                                productsDAO = new TblProductsDAO();

                                for (Integer productId : productIdKeys) {
                                    int quantity = cartManager.getCartManager().get(productId);
                                    TblProductsDTO productsDTO = productsDAO.getCartProductById(productId);
                                    productsDTO.setQuantity(quantity);
                                    //Update total price
                                    totalPrice += productsDTO.getPrice() * quantity;
                                    //Add product to list
                                    productsList.add(productsDTO);
                                }

                                discountDTO = (TblDiscountDTO) session.getAttribute("DISCOUNT");
                                if (discountDTO != null) {
                                    String discountCode = discountDTO.getDiscountCode();
                                    float discountPercent = discountDTO.getDiscountPercent();

                                    request.setAttribute("DISCOUNT_PERCENT", discountPercent);
                                    request.setAttribute("DISCOUNT_CODE", discountCode);
                                    totalPrice *= discountPercent / 100;
                                }

                                request.setAttribute("PRODUCTS_LIST", productsList);
                                request.setAttribute("TOTAL_PRICE", totalPrice);
                            }
                        }
                        url = CART_PAGE;
                        isRedirect = false;
                    }
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
