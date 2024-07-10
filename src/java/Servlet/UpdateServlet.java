/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import dao.BookDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AccountModel;
import model.BookModel;
import model.CategoryModel;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class UpdateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String UPLOAD_DIR = "photo";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession();
            AccountModel u = (AccountModel) session.getAttribute("user");
            if (u != null && u.getRole() == 1) {
                String image = uploadFile(request);
                String bookId = request.getParameter("id");
                String bookName = request.getParameter("name");
                String description = request.getParameter("description");
                String author = request.getParameter("author");
                String Year = request.getParameter("year");
                String price = request.getParameter("price");
                String quantity = request.getParameter("quantity");
                String categoryID = request.getParameter("category");
                BookDao dao = new BookDao();
                CategoryModel category = new CategoryModel(categoryID);

                if (bookName.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    bookName = book.getBookName();
                }
                if (description.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    description = book.getDescription();
                }
                if (author.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    author = book.getAuthor();
                }
                if (Year.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    Year = String.valueOf(book.getYear());
                }
                if (quantity.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    quantity = String.valueOf(book.getQuantity());
                }
                if (price.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    price = String.valueOf(book.getPrice());
                }
                if (image.isEmpty()) {
                    BookModel book = dao.getBookById(bookId);
                    image = book.getImg();
                }

                BookModel book = new BookModel(bookId, description, Float.parseFloat(price), bookName, author, Integer.parseInt(Year), Integer.parseInt(quantity), image, category);
                dao.update(book);
                int i = Integer.parseInt(request.getParameter("i"));
                response.sendRedirect("AdminServlet?i=" + i);
            } else {
                request.setAttribute("message", "Login first.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("photo");
            fileName = (String) getFileName(filePart);
            int lengh = fileName.indexOf(".");
            for (int iIndex = lengh; iIndex >= 0; iIndex--) {
                if (fileName.charAt(iIndex) == 92) {
                    fileName = fileName.substring(iIndex + 1);
                    break;
                }
            }

            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
