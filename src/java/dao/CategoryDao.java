/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.CategoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author FPTSHOP
 */
public class CategoryDao {

    private Connection con = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }

    }

    public CategoryModel getCategoryById(String id) throws Exception {
        CategoryModel result = null;

        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select *"
                        + " from Category where categoryID=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("categoryName");

                    result = new CategoryModel(id, name);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<CategoryModel> getAllCategory() throws Exception {
        List<CategoryModel> list = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Category";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("categoryID");
                    String name = rs.getString("categoryName");
                    CategoryModel dto = new CategoryModel(id, name);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
