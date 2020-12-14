/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import tindd.tblCategory.TblCategoryDAO;
import tindd.tblCategory.TblCategoryDTO;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblProducts.TblProductsDTO;
import tindd.tblProducts.TblProductsUpdateError;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "ViewBookServlet", urlPatterns = {"/ViewBookServlet"})
public class ViewBookServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(ViewBookServlet.class);

    private final String VIEW_BOOK_PAGE = "viewBook.jsp";
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

        //Params
        String type = request.getParameter("type");
        String productIdStr = request.getParameter("productId");

        //Declare
        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;
        TblProductsDAO productsDAO;
        TblProductsDTO productsDTO;
        TblCategoryDAO categoryDAO;

        int roleId;
        int productId = -1;
        String url = LOGIN_PAGE;
        String roleName;
        String userName;
        boolean isError = false;
        boolean isRedirect = true;
        boolean isNew = false;
        List<TblCategoryDTO> categoryList;

        TblProductsUpdateError error = new TblProductsUpdateError();

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
                        url = VIEW_BOOK_PAGE;
                        isRedirect = false;

                        //Get name of user
                        userName = usersDTO.getName();
                        request.setAttribute("FULL_NAME", userName);

                        //Get category list
                        categoryDAO = new TblCategoryDAO();
                        categoryList = categoryDAO.getAllCategory();

                        if (categoryList != null) {
                            request.setAttribute("CATEGORY_LIST", categoryList);
                        }

                        //If user demand Update
                        if (type.equals("Update")) {
                            //Check product id
                            if (productIdStr != null) {
                                try {
                                    productId = Integer.parseInt(productIdStr);
                                } catch (NumberFormatException ex) {
                                    isError = true;
                                    error.setNotValidProductId("ProductId must be numberic!");
                                    return;
                                }
                            } else {
                                isError = true;
                                error.setEmptyProductId("ProductId must not be empty!");
                            }

                            productsDAO = new TblProductsDAO();
                            productsDTO = productsDAO.getProductInfos(productId);

                            if (productsDTO != null) {
                                request.setAttribute("PRODUCT", productsDTO);
                            } else {
                                isError = true;
                                error.setNotFoundBook("Can not found book!");
                            }
                        } else { //User request for new book
                            isNew = true;
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
                if (isError) {
                    request.setAttribute("ERROR", error);
                }

                //Set attribute to determine this request is create new or update
                request.setAttribute("IS_NEW", isNew);
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
