/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import dao.BookDao;
import dao.CategoryDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.AccountModel;
import model.BookModel;
import model.CategoryModel;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/CategoryServlet"})
public class CategoryServlet extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String userPage = "user.jsp";
    private final String adminPage = "admin.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null) {
                AccountModel a = (AccountModel) session.getAttribute("user");
                String cid = request.getParameter("cid");
                BookDao bookDao = new BookDao();
                CategoryDao categoryDao = new CategoryDao();
                List<BookModel> listBook = bookDao.getBookByCId(cid);
                List<CategoryModel> listCategory = categoryDao.getAllCategory();

                request.setAttribute("listBook", listBook);
                request.setAttribute("listCategory", listCategory);
                request.setAttribute("tag", cid);
                if (a.getRole() == 1) {
                    request.getRequestDispatcher(adminPage).forward(request, response);
                } else {
                    request.getRequestDispatcher(userPage).forward(request, response);
                }
            } else {
                request.setAttribute("message", "login first!");
                request.getRequestDispatcher(loginPage).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
