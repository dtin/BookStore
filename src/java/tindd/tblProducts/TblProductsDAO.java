/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblProducts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblProductsDAO implements Serializable {

    public TblProductsDTO getCartProductById(int productId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblProductsDTO productsDTO = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productName, price "
                        + "FROM tblProducts "
                        + "WHERE productId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("productName");
                    long price = rs.getLong("price");

                    productsDTO = new TblProductsDTO(productId, productName, price);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return productsDTO;
    }

    //Get first 10 new books
    public List<TblProductsDTO> loadHomeTenBooks()
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblProductsDTO> list = null;
        TblProductsDTO productsDTO;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productId, productName, image, price "
                        + "FROM tblProducts "
                        + "WHERE status=? "
                        + "ORDER BY productId DESC";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, true);

                rs = stm.executeQuery();

                if (rs.next()) {
                    list = new ArrayList<>();

                    do {
                        int id = rs.getInt("productId");
                        String name = rs.getString("productName");
                        String image = rs.getString("image");
                        long price = rs.getLong("price");

                        productsDTO = new TblProductsDTO(id, name, image, price);
                        list.add(productsDTO);
                    } while (rs.next());
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public List<TblProductsDTO> searchBooks(String searchValue)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductsDTO productsDTO;
        List<TblProductsDTO> list = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productId, productName, image, description, price, author, categoryId "
                        + "FROM tblProducts "
                        + "WHERE productName LIKE ? AND status=? AND quantity > 0";

                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setBoolean(2, true);

                rs = stm.executeQuery();

                if (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    do {
                        int productId = rs.getInt("productId");
                        String title = rs.getString("productName");
                        String image = rs.getString("image");
                        String description = rs.getString("description");
                        long price = rs.getLong("price");
                        String author = rs.getString("author");
                        int categoryId = rs.getInt("categoryId");

                        productsDTO = new TblProductsDTO(productId, title, categoryId, image, description, price, author);
                        list.add(productsDTO);
                    } while (rs.next());
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public List<TblProductsDTO> filterBooks(String searchValue, Integer categoryIdPar, long minPrice, long maxPrice)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductsDTO productsDTO;
        List<TblProductsDTO> list = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                if (categoryIdPar != null) {
                    String sql = "SELECT productId, productName, image, description, price, author, categoryId "
                            + "FROM tblProducts "
                            + "WHERE productName LIKE ? AND categoryId=? AND price >= ? AND price <= ? AND status=? AND quantity > 0";

                    stm = conn.prepareStatement(sql);
                    stm.setString(1, "%" + searchValue + "%");
                    stm.setInt(2, categoryIdPar);
                    stm.setLong(3, minPrice);
                    stm.setLong(4, maxPrice);
                    stm.setBoolean(5, true);
                } else {
                    String sql = "SELECT productId, productName, image, description, price, author, categoryId "
                            + "FROM tblProducts "
                            + "WHERE productName LIKE ? AND price >= ? AND price <= ? AND status=? AND quantity > 0";

                    stm = conn.prepareStatement(sql);
                    stm.setString(1, "%" + searchValue + "%");
                    stm.setLong(2, minPrice);
                    stm.setLong(3, maxPrice);
                    stm.setBoolean(4, true);
                }

                rs = stm.executeQuery();

                if (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    do {
                        int productId = rs.getInt("productId");
                        String title = rs.getString("productName");
                        String image = rs.getString("image");
                        String description = rs.getString("description");
                        long price = rs.getLong("price");
                        String author = rs.getString("author");
                        int categoryId = rs.getInt("categoryId");

                        productsDTO = new TblProductsDTO(productId, title, categoryId, image, description, price, author);
                        list.add(productsDTO);
                    } while (rs.next());
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    //Get quantity for checkout
    public int getQuantity(int productId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int currentQuantity = -1;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT quantity "
                        + "FROM tblProducts "
                        + "WHERE productId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    currentQuantity = rs.getInt("quantity");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return currentQuantity;
    }

    //Get price of product
    public long getPrice(int productId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        long currentPrice = -1;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT price "
                        + "FROM tblProducts "
                        + "WHERE productId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    currentPrice = rs.getLong("price");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return currentPrice;
    }

    public boolean setCheckoutQuantity(int productId, int decrease)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblProducts "
                        + "SET quantity = quantity - ? "
                        + "WHERE productId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, decrease);
                stm.setInt(2, productId);

                int rowCount = stm.executeUpdate();
                if (rowCount > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    //Get products for admin section
    public List<TblProductsDTO> loadAdminTenBooks()
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblProductsDTO> list = null;
        TblProductsDTO productsDTO;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productId, productName, image, description, price, author, categoryId "
                        + "FROM tblProducts "
                        + "WHERE status=? "
                        + "ORDER BY productId DESC";
                stm = conn.prepareStatement(sql);

                stm.setBoolean(1, true);
                rs = stm.executeQuery();

                if (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    do {
                        int productId = rs.getInt("productId");
                        String title = rs.getString("productName");
                        String image = rs.getString("image");
                        String description = rs.getString("description");
                        long price = rs.getLong("price");
                        String author = rs.getString("author");
                        int categoryId = rs.getInt("categoryId");

                        productsDTO = new TblProductsDTO(productId, title, categoryId, image, description, price, author);
                        list.add(productsDTO);
                    } while (rs.next());
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public boolean removeBook(int productId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblProducts "
                        + "SET status=? "
                        + "WHERE productId=?";

                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setInt(2, productId);

                int rowCount = stm.executeUpdate();
                if (rowCount > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean addBook(TblProductsDTO productsDTO)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblProducts(productName, categoryId, image, description, price, author, quantity, createdAt, status) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                stm = conn.prepareStatement(sql);
                stm.setString(1, productsDTO.getProductName());
                stm.setInt(2, productsDTO.getCategoryId());
                stm.setString(3, productsDTO.getImage());
                stm.setString(4, productsDTO.getDescription());
                stm.setLong(5, productsDTO.getPrice());
                stm.setString(6, productsDTO.getAuthor());
                stm.setInt(7, productsDTO.getQuantity());
                stm.setDate(8, Date.valueOf(LocalDate.now()));
                stm.setBoolean(9, true);

                int rowCount = stm.executeUpdate();

                if (rowCount > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public TblProductsDTO getProductInfos(int productId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblProductsDTO productsDTO = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productName, categoryId, image, description, price, author, quantity, createdAt "
                        + "FROM tblProducts "
                        + "WHERE productId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                if (rs.next()) {

                    String productName = rs.getString("productName");
                    int categoryId = rs.getInt("categoryId");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    long price = rs.getLong("price");
                    String author = rs.getString("author");
                    int quantity = rs.getInt("quantity");
                    Date createdAt = rs.getDate("createdAt");

                    productsDTO = new TblProductsDTO(productId, productName, categoryId, image, description, price, author, quantity, createdAt);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return productsDTO;
    }

    public boolean updateProductInfos(TblProductsDTO updateBook)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {

                String image = updateBook.getImage();
                if (image == null) {
                    String sql = "UPDATE tblProducts "
                            + "SET productName=?, categoryId=?, description=?, price=?, author=?, quantity=?, createdAt=? "
                            + "WHERE productId=?";

                    stm = conn.prepareStatement(sql);

                    stm.setString(1, updateBook.getProductName());
                    stm.setInt(2, updateBook.getCategoryId());
                    stm.setString(3, updateBook.getDescription());
                    stm.setLong(4, updateBook.getPrice());
                    stm.setString(5, updateBook.getAuthor());
                    stm.setInt(6, updateBook.getQuantity());
                    stm.setDate(7, updateBook.getCreatedAt());
                    stm.setInt(8, updateBook.getProductId());
                } else {
                    String sql = "UPDATE tblProducts "
                            + "SET productName=?, categoryId=?, image=?, description=?, price=?, author=?, quantity=?, createdAt=? "
                            + "WHERE productId=?";

                    stm = conn.prepareStatement(sql);

                    stm.setString(1, updateBook.getProductName());
                    stm.setInt(2, updateBook.getCategoryId());
                    stm.setString(3, image);
                    stm.setString(4, updateBook.getDescription());
                    stm.setLong(5, updateBook.getPrice());
                    stm.setString(6, updateBook.getAuthor());
                    stm.setInt(7, updateBook.getQuantity());
                    stm.setDate(8, updateBook.getCreatedAt());
                    stm.setInt(9, updateBook.getProductId());
                }

                int rowCount = stm.executeUpdate();
                if (rowCount > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public String getProductName(int productId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String productName = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productName "
                        + "FROM tblProducts "
                        + "WHERE productId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    productName = rs.getString("productName");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return productName;
    }
}
