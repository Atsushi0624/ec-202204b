package com.example.form;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 注文フォーム
 * 
 * @author ryosuke.moritani
 *
 */
public class ExecOrderForm {
  /** id */
  private Integer id;
  /** 顧客id */
  private Integer customerId;
  /** 顧客の状態 */
  private Integer status;
  /** 合計金額 */
  private Integer totalPrice;
  /** 注文日 */
  private Date orderDate;
  /** 宛先氏名 */
  private String destinationName;
  /** 宛先Eメール */
  private String destinationEmail;
  /** 宛先郵便番号 */
  private String destinationZipcode;
  /** 宛先住所 */
  private String destinationAddress;
  /** 宛先電話番号 */
  private String destinationTel;
  /** 配達時間 */
  private Timestamp deliveryTime;
  /** 支払い方法 */
  private Integer paymentMethod;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getDestinationName() {
    return destinationName;
  }

  public void setDestinationName(String destinationName) {
    this.destinationName = destinationName;
  }

  public String getDestinationEmail() {
    return destinationEmail;
  }

  public void setDestinationEmail(String destinationEmail) {
    this.destinationEmail = destinationEmail;
  }

  public String getDestinationZipcode() {
    return destinationZipcode;
  }

  public void setDestinationZipcode(String destinationZipcode) {
    this.destinationZipcode = destinationZipcode;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public void setDestinationAddress(String destinationAddress) {
    this.destinationAddress = destinationAddress;
  }

  public String getDestinationTel() {
    return destinationTel;
  }

  public void setDestinationTel(String destinationTel) {
    this.destinationTel = destinationTel;
  }

  public Timestamp getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(Timestamp deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public Integer getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(Integer paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  @Override
  public String toString() {
    return "ExecOrderForm [customerId=" + customerId + ", deliveryTime=" + deliveryTime + ", destinationAddress="
        + destinationAddress + ", destinationEmail=" + destinationEmail + ", destinationName=" + destinationName
        + ", destinationTel=" + destinationTel + ", destinationZipcode=" + destinationZipcode + ", id=" + id
        + ", orderDate=" + orderDate + ", paymentMethod=" + paymentMethod + ", status=" + status + ", totalPrice="
        + totalPrice + "]";
  }

}
