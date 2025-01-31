/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author FPTSHOP
 */
public class PaymentModel {

    private int paymentId = 0;
    private Date dateCreate;
    private float totalPayment;
    private String userId;
    private String address;
    private String phoneNumber;
    private int status = 0;//0 la dang xu li //1 la that bai//2 la thanh cong

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PaymentModel() {
    }

    public PaymentModel(float totalPayment, String userId, String address, String phoneNumber) {
        this.totalPayment = totalPayment;
        this.userId = userId;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public PaymentModel(int paymentId, String userId, String address, String phoneNumber) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public PaymentModel(int paymentId, Date dateCreate, float totalPayment, String userId, String address, String phoneNumber, int status) {
        this.paymentId = paymentId;
        this.dateCreate = dateCreate;
        this.totalPayment = totalPayment;
        this.userId = userId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

}
