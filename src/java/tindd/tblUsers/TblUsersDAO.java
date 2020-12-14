/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblUsers;

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
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String email, String password)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblUsersDTO dto = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT name, roleId "
                        + "FROM tblUsers "
                        + "WHERE userId=? AND password=? AND status=?";

                stm = conn.prepareStatement(sql);

                stm.setString(1, email);
                stm.setString(2, password);
                stm.setBoolean(3, true);

                rs = stm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    int roleId = rs.getInt("roleId");

                    //Set dto from ResultSet
                    dto = new TblUsersDTO(email, password, name, true, roleId);
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

        return dto;
    }
}
