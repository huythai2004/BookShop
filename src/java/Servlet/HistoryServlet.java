/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Servlet;

import dao.PaymentDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.AccountModel;
import model.PaymentModel;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name="HistoryServlet", urlPatterns={"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            AccountModel u = (AccountModel) session.getAttribute("user");
            PaymentDao dao = new PaymentDao();
            if (u != null) {
                if (action == null) {
                    if (u.getRole() == 0) {
                        String userId = u.getUserId();
                        ArrayList<PaymentModel> listPayment = dao.getPaymentByUserId(userId);
                        if (!listPayment.isEmpty()) {
                            request.setAttribute("listPayment", listPayment);
                        } else {
                            request.setAttribute("message", "Don't have history of payment");
                        }
                    } else {
                        request.setAttribute("listPayment", dao.getAllPayment());
                    }
                request.getRequestDispatcher("history.jsp").forward(request, response);
                } else if (action.equals("detail")) {
                    if (u.getRole() == 0) {
                        String userId = u.getUserId();
                        request.setAttribute("listPayment", dao.getPaymentByUserId(userId));
                    } else {
                        request.setAttribute("listPayment", dao.getAllPayment());
                    }
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("listDetail", dao.getPaymentDetail(id));
                request.getRequestDispatcher("history.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "login first");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
