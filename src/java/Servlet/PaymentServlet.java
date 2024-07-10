/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Servlet;

import dao.BookDao;
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
import model.BookModel;
import model.CartItemModel;
import model.PaymentDetailsModel;
import model.PaymentModel;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name="PaymentServlet", urlPatterns={"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            AccountModel u = (AccountModel) session.getAttribute("user");
            if (u != null && u.getRole() == 0) {
                String action = request.getParameter("action");
                if (action == null) {
                ArrayList<CartItemModel> cart = (ArrayList<CartItemModel>) session.getAttribute("cart");
                    if (cart == null || cart.isEmpty()) {
                        request.setAttribute("message", "Your cart is empty");
                    }
                } else if (action.equals("buy")) {
                    ArrayList<CartItemModel> cart = (ArrayList<CartItemModel>) session.getAttribute("cart");
                    if (cart != null && !cart.isEmpty()) {
                        double tmp = (double) session.getAttribute("totalPayment");
                        float totalPayment = (float) tmp;
                        String address = request.getParameter("address");
                        String phoneNumber = request.getParameter("phoneNumber");
                        String firstDigit = "";
                        if (!phoneNumber.isEmpty()) {
                           firstDigit = Character.toString(phoneNumber.trim().charAt(0)); 
                        }
                        PaymentDao paymentDao = new PaymentDao();
                        BookDao bookDao = new BookDao();

                        String[] InputMes = {"", ""};
                        boolean validate = true;
                        if (address.trim().length() < 6 || address.isEmpty()) {
                            InputMes[0] = "Address must more than 6 characters";
                            validate = false;
                        }
                        if (phoneNumber.trim().length() != 10 || phoneNumber.isEmpty() ||
                                !firstDigit.equals("0") || firstDigit.equals("")) {
                            InputMes[1] = "Phone Number must have 10 digit and begin with 0";
                            validate = false;
                        }
                        if (validate == false) {
                            request.setAttribute("InputMes", InputMes);
                        } else {
                            String userId = u.getUserId();
                            PaymentModel payment = new PaymentModel(totalPayment, userId, address, phoneNumber);
                            if (paymentDao.storePayment(payment) == true) {
                                int paymentId = paymentDao.getPaymentId(payment);
                                for (CartItemModel i : cart) {
                                    PaymentDetailsModel detail = new PaymentDetailsModel(paymentId, i.getBook(),
                                            i.getQuantity(), (i.getQuantity() * i.getBook().getPrice()));
                                    paymentDao.storePaymentDetail(detail);
                                    BookModel book = bookDao.getBookById(i.getBook().getBookId());
                                    book.setQuantity(book.getQuantity() - i.getQuantity());
                                    bookDao.update(book);
                                }
                                request.setAttribute("message", "Buy successful");
                                session.removeAttribute("cart");
                            } else {
                                request.setAttribute("message", "Buy failed");
                            }
                        }
                    } else {
                        request.setAttribute("message", "Your cart is empty");
                    }
                }
                request.getRequestDispatcher("payment.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Login first.");
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
