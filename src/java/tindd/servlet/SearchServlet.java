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
import tindd.tblCategory.TblCategoryDTO;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblProducts.TblProductsDTO;
import tindd.tblProducts.TblProductsSearchError;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(SearchServlet.class);

    private final String DISPATCH_CONTROLLER = "DispatchController";
    private final String SEARCH_PAGE = "searchPage.jsp";

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

        String txtSearch = request.getParameter("txtSearch");
        String categoryIdPar = request.getParameter("categoryId");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");
        String oldMinPrice = request.getParameter("oldMinPrice");
        String oldMaxPrice = request.getParameter("oldMaxPrice");

        String searchValue;
        TblProductsDAO productsDAO;
        TblCategoryDAO categoryDAO;
        ProductSearch productSearch;
        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;

        TblProductsSearchError err = new TblProductsSearchError();

        List<TblProductsDTO> searchList;
        List<ProductSearch> productList;
        List<TblCategoryDTO> categoryList;

        long maxPrice;
        long minPrice;
        int categoryFilter;

        String url = DISPATCH_CONTROLLER;
        boolean isRedirected = true;
        boolean isError = false;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    String userName = usersDTO.getName();
                    int roleId = usersDTO.getRoleId();
                    rolesDAO = new TblRolesDAO();
                    String roleName = rolesDAO.getRoleName(roleId);
                    request.setAttribute("FULL_NAME", userName);

                    if (roleName.equalsIgnoreCase("admin")) {
                        request.setAttribute("IS_ADMIN", true);
                    }

                }
            }

            if (txtSearch != null) {
                searchValue = txtSearch.trim();
                if (!searchValue.isEmpty()) {
                    url = SEARCH_PAGE;
                    isRedirected = false;

                    productsDAO = new TblProductsDAO();

                    //Choose filter or search base on params
                    if (categoryIdPar != null && priceFrom != null && priceTo != null) {
                        try {
                            categoryFilter = Integer.parseInt(categoryIdPar);
                        } catch (NumberFormatException ex) {
                            isError = true;
                            err.setCategoryNumberic("Category must be numberic!");
                            return;
                        }

                        try {
                            minPrice = Long.parseLong(priceFrom);
                        } catch (NumberFormatException ex) {
                            isError = true;
                            err.setMinValueNumberic("Minimum price must be numberic!");
                            return;
                        }

                        try {
                            maxPrice = Long.parseLong(priceTo);
                        } catch (NumberFormatException ex) {
                            isError = true;
                            err.setMaxValueNumberic("Maximum price must be numberic!");
                            return;
                        }

                        //User select filter catelory for all type
                        if (categoryFilter != 0) {
                            searchList = productsDAO.filterBooks(searchValue, categoryFilter, minPrice, maxPrice);
                            request.setAttribute("CATEGORY_ID", categoryFilter);
                        } else {
                            searchList = productsDAO.filterBooks(searchValue, null, minPrice, maxPrice);
                        }
                    } else {
                        searchList = productsDAO.searchBooks(searchValue);
                    }

                    //Add to request scope list of category (with or without search result)
                    categoryDAO = new TblCategoryDAO();
                    categoryList = categoryDAO.getAllCategory();
                    request.setAttribute("CATEGORY_LIST", categoryList);

                    //Old search value min and max store for futher search
                    if (oldMinPrice != null && oldMaxPrice != null) {
                        request.setAttribute("OLD_MIN_PRICE", oldMinPrice);
                        request.setAttribute("OLD_MAX_PRICE", oldMaxPrice);

                        //In case no result in search list
                        request.setAttribute("MIN_PRICE", priceFrom);
                        request.setAttribute("MAX_PRICE", priceTo);
                    }
                    
                    //If having result
                    if (searchList != null) {
                        minPrice = maxPrice = 0;
                        productList = new ArrayList<>();

                        for (TblProductsDTO book : searchList) {
                            int categoryId = book.getCategoryId();
                            String categoryName = categoryDAO.getCategoryNameById(categoryId);

                            //Add result to list for show to user
                            productSearch = new ProductSearch(book, categoryName);
                            productList.add(productSearch);

                            long bookPrice = book.getPrice();

                            if (bookPrice < minPrice) {
                                minPrice = bookPrice;
                            }

                            if (bookPrice > maxPrice) {
                                maxPrice = bookPrice;
                            }
                        }

                        request.setAttribute("PRODUCT_LIST", productList);

                        //If this is not a filter action
                        if (oldMaxPrice == null && oldMinPrice == null) {
                            //Set min and max value for this search
                            request.setAttribute("MIN_PRICE", minPrice);
                            request.setAttribute("MAX_PRICE", maxPrice);
                        }

                    }

                    request.setAttribute("SEARCH_VALUE", searchValue);
                }
            }

        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isRedirected) {
                response.sendRedirect(url);
            } else {
                if (isError) {
                    request.setAttribute("ERROR", err);
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
