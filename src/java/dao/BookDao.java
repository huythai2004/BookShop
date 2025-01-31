/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.BookModel;
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
public class BookDao {
    private Connection con = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;
    
    private void closeConnection() throws SQLException {
        if (rs!= null){
            rs.close();
        } 
        if (stm != null){
            stm.close();
        }
        if (con != null){
            con.close();
        }
    }
    
    public List<BookModel> getAllBook() throws Exception {
        List<BookModel> list = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Book";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("bookId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String name = rs.getString("bookName");
                    String author = rs.getString("author");
                    int year = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    String img = rs.getString("bookImgURL");
                    String categoryID = rs.getString("categoryID");
                    CategoryDao dao = new CategoryDao();
                    CategoryModel category = dao.getCategoryById(categoryID);
                    BookModel dto = new BookModel(id, description, price, name, author, year, quantity, img, category);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    public List<BookModel> getBookByName(String searchValue) throws SQLException, ClassNotFoundException,Exception {
        List<BookModel> list = null;
        try {
            con = DBUtils.makeConnection();   
            if (con != null) {
                String sql = "Select * From Book where bookName like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("bookId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String name = rs.getString("bookName");
                    String author = rs.getString("author");
                    int year = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    String img = rs.getString("bookImgURL");
                    String categoryID = rs.getString("categoryID");
                    CategoryDao dao = new CategoryDao();
                    CategoryModel category = dao.getCategoryById(categoryID);
                    BookModel dto = new BookModel(id, description, price, name, author, year, quantity, img, category);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public List<BookModel> getBookByPrice(float p1, float p2) throws SQLException, ClassNotFoundException,Exception {
        List<BookModel> list = null;
        if (p1 > p2) {
            float temp = p1;
            p1 = p2;
            p2 = temp;
        }
        try {
            con = DBUtils.makeConnection();   
            if (con != null) {
                String sql = "Select * From Book where (price >= ? and price <= ?)";
                stm = con.prepareStatement(sql);
                stm.setFloat(1, p1);
                stm.setFloat(2, p2);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("bookId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String name = rs.getString("bookName");
                    String author = rs.getString("author");
                    int year = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    String img = rs.getString("bookImgURL");
                    String categoryID = rs.getString("categoryID");
                    CategoryDao dao = new CategoryDao();
                    CategoryModel category = dao.getCategoryById(categoryID);
                    BookModel dto = new BookModel(id, description, price, name, author, year, quantity, img, category);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean update(BookModel book) throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update Book set description=?, price=? ,bookName=? ,author=? ,yearOfProduction=? ,quantity=? ,bookImgURL=? ,categoryID=? where bookId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, book.getDescription());
                stm.setFloat(2, book.getPrice());
                stm.setString(3, book.getBookName());
                stm.setString(4, book.getAuthor());
                stm.setInt(5, book.getYear());
                stm.setInt(6, book.getQuantity());
                stm.setString(7, book.getImg());
                stm.setString(8, book.getCategoryID().getId());
                stm.setString(9, book.getBookId());
                stm.executeUpdate();
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean delete(String id) throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Delete From Book where bookId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

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
    
      public boolean insert(BookModel book) throws SQLException, ClassNotFoundException{
        try{
            con = DBUtils.makeConnection();
            if(con != null){
            String sql = "Insert into Book(bookId, description, price, bookName, author, yearOfProduction, quantity,bookImgURL,categoryID) "
                    + "Values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            stm = con.prepareStatement(sql);
            stm.setString(1, book.getBookId());
            stm.setString(2, book.getDescription());
            stm.setFloat(3, book.getPrice());
            stm.setString(4, book.getBookName());
            stm.setString(5, book.getAuthor());
            stm.setInt(6, book.getYear());
            stm.setInt(7, book.getQuantity());
            stm.setString(8,book.getImg());
            stm.setString(9, book.getCategoryID().getId());
            
            int row = stm.executeUpdate();
                if(row > 0){
                    return true;
                }
            
            }
        }finally{
            closeConnection();
        }    
        return false;
    }
      
      public BookModel getBookById(String id) throws Exception{
          try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * From Book where bookId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id.toUpperCase());
                rs = stm.executeQuery();
                while (rs.next()) {                    
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String name = rs.getString("bookName");
                    String author = rs.getString("author");
                    int year = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    String img = rs.getString("bookImgURL");
                    String categoryID = rs.getString("categoryID");
                    CategoryDao dao = new CategoryDao();
                    CategoryModel category = dao.getCategoryById(categoryID);
                    BookModel dto = new BookModel(id, description, price, name, author, year, quantity, img, category);
                    return dto;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
      }
      
      public List<BookModel> getBookByCId(String cid) throws Exception{
           List<BookModel> list = null;
          try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * From Book where categoryID=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, cid);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("bookId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String name = rs.getString("bookName");
                    String author = rs.getString("author");
                    int year = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    String img = rs.getString("bookImgURL");
                    
                    CategoryDao dao = new CategoryDao();
                    CategoryModel category = dao.getCategoryById(cid);
                    BookModel dto = new BookModel(id, description, price, name, author, year, quantity, img, category);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return list;
      }
      
      public List<BookModel> getTop3Book(int count) throws Exception{
           List<BookModel> list = null;
          try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select * from (select ROW_NUMBER() over (order by bookId asc) as rn, * from Book) as b\n" +
                            "where rn >= (?*3-2) and rn <= (?*3)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, count);
                stm.setInt(2, count);
                rs = stm.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("bookId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String name = rs.getString("bookName");
                    String author = rs.getString("author");
                    int year = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    String img = rs.getString("bookImgURL");
                    String categoryID = rs.getString("categoryID");
                    
                    CategoryDao dao = new CategoryDao();
                    CategoryModel category = dao.getCategoryById(categoryID);
                    BookModel dto = new BookModel(id, description, price, name, author, year, quantity, img, category);
                    list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
          return list;
      }
  
    public int count() throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "SELECT count(*) From Book";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            closeConnection();
        }
        return 0;
    }
}
