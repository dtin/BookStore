/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblRoles;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblRolesDAO implements Serializable {

    public String getRoleName(int roleId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String roleName = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT roleName "
                        + "FROM tblRoles "
                        + "WHERE roleId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, roleId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    roleName = rs.getString("roleName");
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

        return roleName;
    }
}
