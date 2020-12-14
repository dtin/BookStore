/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblCategory;

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
public class TblCategoryDAO implements Serializable {

    //Get name category by id
    public String getCategoryNameById(int categoryId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String categoryName = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT categoryName "
                        + "FROM tblCategory "
                        + "WHERE categoryId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, categoryId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    categoryName = rs.getString("categoryName");
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

        return categoryName;
    }

    //Get all category for search page filter
    public List<TblCategoryDTO> getAllCategory()
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblCategoryDTO> list = null;
        TblCategoryDTO categoryDTO;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT categoryId, categoryName FROM tblCategory";

                stm = conn.prepareStatement(sql);

                rs = stm.executeQuery();
                if (rs.next()) {
                    list = new ArrayList<>();

                    do {
                        int categoryId = rs.getInt("categoryId");
                        String categoryName = rs.getString("categoryName");

                        categoryDTO = new TblCategoryDTO(categoryId, categoryName);
                        list.add(categoryDTO);
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
}
