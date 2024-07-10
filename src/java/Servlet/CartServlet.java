/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import dao.BookDao;
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

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {
    private static final String CART_ATTRIBUTE = "cart";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ACTION_PARAM = "action";
    private static final String ID_PARAM = "id";
    private static final String MESSAGE_ATTRIBUTE = "message";
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
        response.setContentType("text/html;charset=UTF-8");
        try {
           HttpSession session = request.getSession();
            AccountModel u = (AccountModel) session.getAttribute("user");
            if (u != null && u.getRole() == 0) {
                String action = request.getParameter("action");
                if (action == null) {
                    displayCart(request, response);
                } else if (action.equalsIgnoreCase("add")) {
                    addItem(request, response);
                } else if (action.equalsIgnoreCase("sub")) {
                    subItem(request, response);
                } else if (action.equalsIgnoreCase("empty")) {
                    emptyCart(request, response);
                } else if (action.equalsIgnoreCase("remove")) {
                    removeItems(request, response);
                }
            } else {
                request.setAttribute("message", "Login first.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //
    protected void displayCart(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<CartItemModel> cart = (ArrayList<CartItemModel>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()){
            request.setAttribute("message","Your cart is emty now");
        }
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
    
    protected void subItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<CartItemModel> cart = (ArrayList<CartItemModel>) session.getAttribute("cart");
        int index = isExisting(request.getParameter("id"), cart);
        int quantity = cart.get(index).getQuantity() - 1;
        if (quantity > 0) {
            cart.get(index).setQuantity(quantity);
        } else {
            cart.remove(index);
        }
        session.setAttribute("cart", cart);
        response.sendRedirect("CartServlet");
    }
    
    protected void removeItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            ArrayList<CartItemModel> cart = (ArrayList<CartItemModel>) session.getAttribute("cart");
            for (String selected : request.getParameterValues("select")) {
                int index = isExisting(selected, cart);
                cart.remove(index);
            }
            session.setAttribute("cart", cart);
            response.sendRedirect("CartServlet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void emptyCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("cart");
        response.sendRedirect("CartServlet");
    }

    protected void addItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookDao dao = new BookDao();
        HttpSession session = request.getSession();
        try {
            BookModel b = dao.getBookById(request.getParameter("id"));
            if (b.getQuantity() > 0) {
                if (session.getAttribute("cart") == null) {
                    ArrayList<CartItemModel> cart = new ArrayList<>();
                    CartItemModel item = new CartItemModel(b, 1);
                    cart.add(item);
                    session.setAttribute("cart", cart);
                } else {
                    ArrayList<CartItemModel> cart = (ArrayList<CartItemModel>) session.getAttribute("cart");
                    int index = isExisting(request.getParameter("id"), cart);
                    if (index == -1) {
                        CartItemModel item = new CartItemModel(b, 1);
                        cart.add(item);
                    } else {
                        int quantity = cart.get(index).getQuantity() + 1;
                        cart.get(index).setQuantity(quantity);
                    }
                    session.setAttribute("cart", cart);
                }
                response.sendRedirect("CartServlet");
            } else {
                String message = "Out of stock";
                response.sendRedirect("UserServlet?message=" + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int isExisting(String id, ArrayList<CartItemModel> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getBook().getBookId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
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
