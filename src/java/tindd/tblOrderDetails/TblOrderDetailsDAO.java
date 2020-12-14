/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrderDetails;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblOrderDetailsDAO implements Serializable {

    public boolean createOrderDetails(int orderId, int productId, int quantity, long price)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        
        boolean isCreated = false;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblOrderDetails(orderId, productId, price, quantity)"
                        + "VALUES(?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);
                
                stm.setInt(1, orderId);
                stm.setInt(2, productId);
                stm.setLong(3, price);
                stm.setInt(4, quantity);
                
                int rowCount = stm.executeUpdate();
                
                if (rowCount > 0) {
                    isCreated = true;
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
        
        return isCreated;
    }
    
    public List<TblOrderDetailsDTO> getOrder(int orderId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        TblOrderDetailsDTO orderDetailsDTO;
        List<TblOrderDetailsDTO> orderList = null;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT productId, price, quantity "
                        + "FROM tblOrderDetails "
                        + "WHERE orderId=?";
                
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderId);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    orderList = new ArrayList<>();
                    
                    do {
                        int productId = rs.getInt("productId");
                        long price = rs.getLong("price");
                        int quantity = rs.getInt("quantity");
                        
                        orderDetailsDTO = new TblOrderDetailsDTO(orderId, productId, price, quantity);
                        orderList.add(orderDetailsDTO);
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
}
