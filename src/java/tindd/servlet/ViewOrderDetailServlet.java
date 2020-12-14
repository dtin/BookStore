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
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.history.ProductOrderDetail;
import tindd.tblOrderDetails.TblOrderDetailsDAO;
import tindd.tblOrderDetails.TblOrderDetailsDTO;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "ViewOrderDetailServlet", urlPatterns = {"/ViewOrderDetailServlet"})
public class ViewOrderDetailServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(ViewOrderDetailServlet.class);

    private final String LOGIN_CONTROLLER = "DispatchController?btnAction=Login";
    private final String HOME_CONTROLLER = "DispatchController";
    private final String ORDER_DETAIL_PAGE = "orderDetail.jsp";

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

        String orderIdStr = request.getParameter("orderId");

        TblRolesDAO rolesDAO;
        TblOrdersDAO ordersDAO;
        TblOrderDetailsDAO orderDetailsDAO;
        TblProductsDAO productsDAO;

        int orderId;
        String url = LOGIN_CONTROLLER;
        boolean isError = false;
        boolean isRedirect = true;
        ProductOrderDetail productOrderDetail;

        List<TblOrderDetailsDTO> detailList;
        List<ProductOrderDetail> productList;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    //Get user full name
                    String name = usersDTO.getName();
                    String userId = usersDTO.getUserId();
                    request.setAttribute("FULL_NAME", name);

                    //Get user role
                    rolesDAO = new TblRolesDAO();
                    int roleId = usersDTO.getRoleId();
                    String roleName = rolesDAO.getRoleName(roleId);

                    //If not admin
                    if (!roleName.equalsIgnoreCase("admin")) {
                        if (orderIdStr != null) {
                            url = ORDER_DETAIL_PAGE;
                            isRedirect = false;

                            try {
                                orderId = Integer.parseInt(orderIdStr);
                            } catch (NumberFormatException ex) {
                                isError = true;
                                return;
                            }

                            ordersDAO = new TblOrdersDAO();
                            //Check if user is allow to see this history order detail
                            boolean isAllow = ordersDAO.isAllowHistory(orderId, userId);

                            if (isAllow) {
                                orderDetailsDAO = new TblOrderDetailsDAO();
                                detailList = orderDetailsDAO.getOrder(orderId);

                                if (detailList != null) {
                                    productList = new ArrayList<>();
                                    productsDAO = new TblProductsDAO();

                                    for (TblOrderDetailsDTO itemProduct : detailList) {
                                        int productId = itemProduct.getProductId();
                                        String productName = productsDAO.getProductName(productId);

                                        productOrderDetail = new ProductOrderDetail(itemProduct, productName);
                                        productList.add(productOrderDetail);
                                    }

                                    request.setAttribute("ORDER_LIST", productList);
                                    request.setAttribute("ORDER_ID", orderId);
                                }
                            } else {
                                isError = true;
                            }
                        } else {
                            isError = true;
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
                url = HOME_CONTROLLER;
                isRedirect = true;
            }

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
