/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblOrdersDAO implements Serializable {
    
    public boolean isDiscountBefore(String userId, String discountCode)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderId "
                        + "FROM tblOrders "
                        + "WHERE userId=? AND discountCode=?";
                
                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, discountCode);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    result = true;
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
        
        return result;
    }
    
    public Integer createOrder(String userId, long totalPrice, String discountCode)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        Integer result = null;
        String sql;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                sql = "INSERT INTO tblOrders(userId, totalPrice, orderDate, discountCode) "
                        + "VALUES(?, ?, ?, ?)";
                
                stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, userId);
                stm.setLong(2, totalPrice);
                stm.setDate(3, Date.valueOf(LocalDate.now()));
                stm.setString(4, discountCode);
                
                int rowCount = stm.executeUpdate();
                if (rowCount != 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        result = rs.getInt(1);
                    }
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
        
        return result;
    }
    
    public List<TblOrdersDTO> getOrders(String userId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        TblOrdersDTO ordersDTO;
        List<TblOrdersDTO> orderList = null;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderId, totalPrice, orderDate, discountCode "
                        + "FROM tblOrders "
                        + "WHERE userId=?";
                
                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    orderList = new ArrayList<>();
                    
                    do {
                        int orderId = rs.getInt("orderId");
                        long totalPrice = rs.getLong("totalPrice");
                        Date orderDate = rs.getDate("orderDate");
                        String discountCode = rs.getString("discountCode");
                        
                        ordersDTO = new TblOrdersDTO(orderId, userId, totalPrice, orderDate, discountCode);
                        orderList.add(ordersDTO);
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
        
        return orderList;
    }
    
    public List<TblOrdersDTO> getOrders(String userId, String userOrderId, String userOrderDate)
            throws SQLException, NamingException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        
        TblOrdersDTO ordersDTO;
        List<TblOrdersDTO> orderList = null;
        
        StringBuilder sb;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                sb = new StringBuilder("SELECT orderId, totalPrice, orderDate, discountCode "
                        + "FROM tblOrders "
                        + "WHERE");
                
                sb.append(" userId='").append(userId).append("'");
                
                if (userOrderId != null && userOrderId.length() > 0) {
                    sb.append(" AND orderId LIKE '%").append(userOrderId).append("%'");
                }
                
                if (userOrderDate != null) {
                    sb.append(" AND orderDate='").append(userOrderDate).append("'");
                }
                
                stm = conn.createStatement();
                rs = stm.executeQuery(sb.toString());
                
                if (rs.next()) {
                    orderList = new ArrayList<>();
                    
                    do {
                        int orderId = rs.getInt("orderId");
                        long totalPrice = rs.getLong("totalPrice");
                        Date orderDate = rs.getDate("orderDate");
                        String discountCode = rs.getString("discountCode");
                        
                        ordersDTO = new TblOrdersDTO(orderId, userId, totalPrice, orderDate, discountCode);
                        orderList.add(ordersDTO);
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
        
        return orderList;
    }
    
    public boolean isAllowHistory(int orderId, String userId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderId "
                        + "FROM tblOrders "
                        + "WHERE userId=? AND orderId=?";
                
                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setInt(2, orderId);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    result = true;
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
        
        return result;
    }
}
