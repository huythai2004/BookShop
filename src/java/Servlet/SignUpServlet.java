/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import model.AccountModel;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignUpServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("signUpUsername");
            String password = request.getParameter("signUpPassword");
            String confirm = request.getParameter("confirmPass");
            AccountDao dao = new AccountDao();
            AccountModel acc = new AccountModel(username, password, fullname, 0);

            String[] ErrMsg = {
                "Full name must longer than 3 characters",
                "Please choose a unique username",
                "Password must longer than 6 characters",
                "Confirm password is not same as password"
            };

            boolean bIsValidate = true;
            bIsValidate = IsValidate(fullname, username, password,
                    confirm, dao, ErrMsg);

            /* if (dao.getAccountById(username) == null) {
                if (password.equals(confirm)) {*/
            if (bIsValidate) {
                if (dao.signUp(acc) != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", acc);
                    response.sendRedirect("UserServlet");
                }
            } else {
                request.setAttribute("ErrSignUp", ErrMsg);
            }
            request.setAttribute("context", "SignUpServlet");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean IsValidate(String fullname, String username, String password,
            String confirm, AccountDao dao, String[] outErrMsg)
            throws ClassNotFoundException, SQLException {

        boolean bCheck = true;

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty()
                || confirm.isEmpty()) {
            return false;
        }

        int iIndex = 0;
        if (fullname.length() < 3) {
            bCheck = false;
        } else {
            outErrMsg[iIndex] = null;
        }

        iIndex++;
        if (dao.getAccountById(username) != null) {
            bCheck = false;
        } else {
            outErrMsg[iIndex] = null;
        }

        iIndex++;
        if (password.length() < 6) {
            bCheck = false;
        } else {
            outErrMsg[iIndex] = null;
        }

        iIndex++;
        if (!confirm.equals(password)) {
            bCheck = false;
        } else {
            outErrMsg[iIndex] = null;
        }

        return bCheck;
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
