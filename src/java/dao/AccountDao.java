/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AccountModel;
import utils.DBUtils;

/**
 *
 * @author FPTSHOP
 */
public class AccountDao {
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

    public AccountModel checkLogin(String userId, String password) throws ClassNotFoundException, SQLException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Account where userID=? and password=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    AccountModel a = new AccountModel(userId, password, rs.getString("fullName"), rs.getInt("roleID"));
                    return a;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public AccountModel getAccountById(String userId) throws ClassNotFoundException, SQLException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Account where userID=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId.toUpperCase());
                rs = stm.executeQuery();
                while (rs.next()) {
                    AccountModel a = new AccountModel(userId, rs.getString("password"), rs.getString("fullName"), rs.getInt("roleID"));
                    return a;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public AccountModel signUp(AccountModel acc) throws ClassNotFoundException, SQLException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "insert into Account(userID, password, fullName, roleID)"
                        + "Values(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, acc.getUserId());
                stm.setString(2, acc.getPassword());
                stm.setString(3, acc.getFullname());
                stm.setInt(4, acc.getRole());
                int row = stm.executeUpdate();
                if (row > 0) {
                    return acc;
                }

            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean update(AccountModel acc) throws ClassNotFoundException, SQLException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "update Account set fullName=?, password=? where userID= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, acc.getFullname());
                stm.setString(2, acc.getPassword());
                stm.setString(3, acc.getUserId());
                stm.executeUpdate();
                return true;

            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
