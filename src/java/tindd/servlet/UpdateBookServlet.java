/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;
import tindd.tblProducts.TblProductsDAO;
import tindd.tblProducts.TblProductsDTO;
import tindd.tblProducts.TblProductsUpdateError;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "UpdateBookServlet", urlPatterns = {"/UpdateBookServlet"})
public class UpdateBookServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(UpdateBookServlet.class);

    private final String VIEW_BOOK_CONTROLLER = "DispatchController?btnAction=Update";
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
        ServletContext context = request.getServletContext();

        //Params
        String productIdStr = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String categoryIdStr = request.getParameter("categoryId");
        Part image = request.getPart("image");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String author = request.getParameter("author");
        String quantityStr = request.getParameter("quantity");
        String createdAtStr = request.getParameter("createdAt");

        //Declare
        TblUsersDTO usersDTO;
        TblRolesDAO rolesDAO;
        TblProductsDAO productsDAO;
        TblProductsDTO productsDTO;

        TblProductsUpdateError error = new TblProductsUpdateError();

        InputStream imageStream = null;
        OutputStream outputImage = null;
        boolean isError = false;
        int roleId;
        long price;
        int productId;
        int categoryId;
        int quantity;
        String url = LOGIN_PAGE;
        String roleName;
        String userName;
        String fileName = null;
        Date createdAt = null;

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
                        //Check product id
                        if (productIdStr == null) {
                            isError = true;
                            error.setEmptyProductId("ProductId must not be empty!");
                            return;
                        } else {
                            try {
                                productId = Integer.parseInt(productIdStr);
                            } catch (NumberFormatException ex) {
                                isError = true;
                                error.setNotValidProductId("ProductId must be numberic!");
                                return;
                            }
                        }

                        url = VIEW_BOOK_CONTROLLER + "&productId=" + productId;

                        //Get name of user
                        userName = usersDTO.getName();
                        request.setAttribute("FULL_NAME", userName);

                        //Check params
                        if (productName == null || productName.trim().isEmpty()) {
                            isError = true;
                            error.setEmptyProductName("Book name must not be empty!");
                            return;
                        }

                        //Check category
                        if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                            isError = true;
                            error.setEmptyCategoryId("Category must not be empty!");
                            return;
                        } else {
                            try {
                                categoryId = Integer.parseInt(categoryIdStr);
                            } catch (NumberFormatException ex) {
                                isError = true;
                                error.setNotValidCategoryId("Category must be numberic!");
                                return;
                            }
                        }

                        //Check description
                        if (description == null || description.trim().isEmpty()) {
                            isError = true;
                            error.setEmptyDescription("Description must not be empty!");
                            return;
                        }

                        //Check price
                        if (priceStr == null || priceStr.trim().isEmpty()) {
                            isError = true;
                            error.setEmptyPrice("Price must not be empty!");
                            return;
                        } else {
                            try {
                                price = Long.parseLong(priceStr);
                            } catch (NumberFormatException ex) {
                                isError = true;
                                error.setNotValidPrice("Price must be numberic!");
                                return;
                            }
                        }

                        //Check author
                        if (author == null || author.trim().isEmpty()) {
                            isError = true;
                            error.setEmptyAuthor("Author must not be empty!");
                            return;
                        }

                        //Check quantity
                        if (quantityStr == null || quantityStr.trim().isEmpty()) {
                            isError = true;
                            error.setEmptyQuantity("Quantity must not be empty!");
                            return;
                        } else {
                            try {
                                quantity = Integer.parseInt(quantityStr);
                            } catch (NumberFormatException ex) {
                                isError = true;
                                error.setNotValidQuantity("Quantity must be numberic");
                                return;
                            }
                        }

                        //Check createdAt
                        if (createdAtStr != null) {
                            createdAt = Date.valueOf(createdAtStr);
                        }

                        if (image.getSize() > 0) {
                            //Get upload filename
                            String fullPath = image.getSubmittedFileName();
                            int indexLastDivider = fullPath.lastIndexOf("\\");
                            fileName = System.currentTimeMillis() + "_" + fullPath.substring(indexLastDivider + 1);

                            //Get input stream of upload file
                            imageStream = image.getInputStream();

                            //Set where to store file
                            String localPath = context.getRealPath("/");
                            File file = new File(localPath + "/assets/images/" + fileName);

                            //Read until end of file (== -1)
                            int read;
                            if (file.createNewFile()) {
                                outputImage = new FileOutputStream(file);
                                while ((read = imageStream.read()) != -1) {
                                    outputImage.write(read);
                                }
                            }
                        }
                        
                        //Update book
                        productsDAO = new TblProductsDAO();
                        productsDTO = new TblProductsDTO(productId, productName, categoryId, fileName, description, price, author, quantity, createdAt);
                        productsDAO.updateProductInfos(productsDTO);
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (outputImage != null) {
                outputImage.close();
            }

            if (imageStream != null) {
                imageStream.close();
            }

            if (!isError) {
                response.sendRedirect(url);
            } else {
                request.setAttribute("ERROR", error);
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
