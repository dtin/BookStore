/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import tindd.tblOrderDetails.TblOrderDetailsDAO;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblProducts.TblProductsCheckoutError;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(CheckoutServlet.class);

    private final String ORDER_RESULT_PAGE = "orderResult.jsp";
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

        CartManager cartManager;
        Map<Integer, Integer> cartProducts;
        TblProductsDAO productsDAO;
        TblOrdersDAO ordersDAO;
        TblOrderDetailsDAO orderDetailsDAO;
        TblDiscountDTO discountDTO;
        TblUsersDTO usersDTO;

        TblProductsCheckoutError error = new TblProductsCheckoutError();

        String url = CART_CONTROLLER;
        boolean isError = false;
        String userId;
        String discountCode = null;
        Integer prodOutOfStock = null;
        int currentQuantity = -1;
        int buyQuantity;
        int resultQuantity;
        long currentPrice;
        long totalPrice = 0;
        Map<Integer, Long> productsPrice = new HashMap<>();

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    userId = usersDTO.getUserId();

                    cartManager = (CartManager) session.getAttribute("CART_MANAGER");
                    if (cartManager != null) {
                        productsDAO = new TblProductsDAO();

                        cartProducts = cartManager.getCartManager();
                        Set<Integer> productIdKeys = cartProducts.keySet();

                        //Map of ProductId - Quantity
                        for (Integer productId : productIdKeys) {
                            //Checking quantity before checkout
                            currentQuantity = productsDAO.getQuantity(productId);
                            buyQuantity = cartProducts.get(productId);
                            resultQuantity = currentQuantity - buyQuantity;

                            if (resultQuantity < 0) {
                                prodOutOfStock = productId;
                                break;
                            }

                            //Get current price for checkout
                            currentPrice = productsDAO.getPrice(productId);
                            totalPrice += currentPrice * buyQuantity;

                            //Map of ProductId - currentPrice
                            productsPrice.put(productId, currentPrice);
                        }

                        //If there is an out of stock product
                        if (prodOutOfStock != null) {
                            isError = true;
                        } else {
                            discountDTO = (TblDiscountDTO) session.getAttribute("DISCOUNT");
                            if (discountDTO != null) {
                                discountCode = discountDTO.getDiscountCode();
                            }

                            ordersDAO = new TblOrdersDAO();
                            Integer orderId = ordersDAO.createOrder(userId, totalPrice, discountCode);

                            if (orderId != null) {
                                orderDetailsDAO = new TblOrderDetailsDAO();

                                boolean orderDetailAdded = false;
                                boolean isDecrease;

                                url = ORDER_RESULT_PAGE;

                                for (Integer productId : productIdKeys) {
                                    buyQuantity = cartProducts.get(productId);
                                    currentPrice = productsPrice.get(productId);

                                    isDecrease = productsDAO.setCheckoutQuantity(productId, buyQuantity);
                                    if (isDecrease) {
                                        orderDetailAdded = orderDetailsDAO.createOrderDetails(orderId, productId, buyQuantity, currentPrice);

                                        if (!orderDetailAdded) {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }

                                }

                                if (orderDetailAdded) {
                                    request.setAttribute("ORDER_ID", orderId);
                                    session.removeAttribute("DISCOUNT");
                                    session.removeAttribute("CART_MANAGER");
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                error.setProductOutOfStock("Only " + currentQuantity + " item(s) left!");
                request.setAttribute("PRODUCT_ID_ERROR", prodOutOfStock);
                request.setAttribute("QUANTITY_CHECKOUT_ERROR", error);
            }

            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
