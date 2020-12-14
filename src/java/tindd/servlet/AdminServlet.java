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
import tindd.product.ProductSearch;
import tindd.tblCategory.TblCategoryDAO;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblProducts.TblProductsDTO;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AdminServlet.class);

    private final String ADMIN_PAGE = "adminPanel.jsp";
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

        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;
        TblProductsDAO productsDAO;
        TblCategoryDAO categoryDAO;
        ProductSearch productSearch;

        String url = LOGIN_PAGE;
        boolean isRedirect = true;
        int roleId;
        int categoryId;
        String roleName;
        String userName;
        String categoryName;
        List<TblProductsDTO> listBooks;
        List<ProductSearch> productList;

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
                        url = ADMIN_PAGE;
                        isRedirect = false;

                        //Get name of user
                        userName = usersDTO.getName();
                        request.setAttribute("FULL_NAME", userName);

                        productsDAO = new TblProductsDAO();
                        listBooks = productsDAO.loadAdminTenBooks();

                        if (listBooks != null) {
                            productList = new ArrayList<>();
                            categoryDAO = new TblCategoryDAO();

                            for (TblProductsDTO listBook : listBooks) {
                                //Get category name from category id
                                categoryId = listBook.getCategoryId();
                                categoryName = categoryDAO.getCategoryNameById(categoryId);

                                productSearch = new ProductSearch(listBook, categoryName);

                                //Add to list show
                                productList.add(productSearch);
                            }
                            request.setAttribute("PRODUCT_LIST", productList);
                        }
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
