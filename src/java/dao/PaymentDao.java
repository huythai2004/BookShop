/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.BookModel;
import model.PaymentModel;
import model.PaymentDetailsModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import utils.DBUtils;

/**
 *
 * @author FPTSHOP
 */
public class PaymentDao {
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

    public boolean storePayment(PaymentModel payment) throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert into Payment(userID, dateCreate, totalPayment, address, phoneNumber, status) "
                        + "Values(?, ?, ?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setString(1, payment.getUserId());
                stm.setDate(2,  Date.valueOf(LocalDate.now()));
                stm.setFloat(3, payment.getTotalPayment());
                stm.setString(4, payment.getAddress());
                stm.setString(5, payment.getPhoneNumber());
                stm.setInt(6, payment.getStatus());
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean storePaymentDetail(PaymentDetailsModel detail) throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert into PaymentDetail(paymentId, bookId, quantity, subTotal) "
                        + "Values(?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setInt(1, detail.getPaymentId());
                stm.setString(2, detail.getBook().getBookId());
                stm.setInt(3, detail.getQuantity());
                stm.setFloat(4, detail.getSubTotal());
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public int getPaymentId(PaymentModel payment) throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select paymentId from Payment where userID=? and dateCreate=? and totalPayment=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, payment.getUserId());
                stm.setDate(2, payment.getDateCreate());
                stm.setFloat(3, payment.getTotalPayment());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int paymentId = rs.getInt("paymentId");
                    return paymentId;
                }
            }
        } finally {
            closeConnection();
        }
        return 0;
    }

    public ArrayList<PaymentModel> getPaymentByUserId(String userId) throws Exception {
        ArrayList<PaymentModel> list = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * from Payment where userID=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    PaymentModel payment = new PaymentModel(rs.getInt(1), rs.getDate(3), rs.getFloat(4), 
                            rs.getString(2), rs.getString(5), rs.getString(6), rs.getInt(7));
                    list.add(payment);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public ArrayList<PaymentDetailsModel> getPaymentDetail(int paymentId) throws Exception {
        ArrayList<PaymentDetailsModel> list = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * from PaymentDetail where paymentId=?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, paymentId);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                BookDao dao = new BookDao();
                while (rs.next()) {
                    BookModel book = dao.getBookById(rs.getString("bookId"));
                    PaymentDetailsModel detail = new PaymentDetailsModel(paymentId, book,
                            rs.getInt("quantity"), rs.getFloat("subTotal"));
                    list.add(detail);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public ArrayList<PaymentModel> getAllPayment() throws Exception {
        ArrayList<PaymentModel> list = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * from Payment";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    PaymentModel payment = new PaymentModel(rs.getInt(1), rs.getDate(3), rs.getFloat(4), 
                            rs.getString(2), rs.getString(5), rs.getString(6), rs.getInt(7));
                    list.add(payment);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public PaymentDetailsModel checkBook(String bookId) throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * from PaymentDetail where bookId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    BookDao bookDao = new BookDao();
                    BookModel book = bookDao.getBookById(bookId);
                    PaymentDetailsModel detail = new PaymentDetailsModel(rs.getInt("paymentId"), book,
                            rs.getInt("quantity"), rs.getFloat("subTotal"));
                    return detail;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }
    

}
