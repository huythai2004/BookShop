/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author FPTSHOP
 */
public class PaymentDetailsModel {
     private int paymentId;
    private BookModel book;
    private int quantity;
    private float subTotal;

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public PaymentDetailsModel() {
    }

    public PaymentDetailsModel(int paymentId, BookModel book, int quantity, float subTotal) {
        this.paymentId = paymentId;
        this.book = book;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}
