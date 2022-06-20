package com.example.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * OrderItemを表すドメイン.
 * 
 * @author atsushi.kikuchi
 *
 */
@Data
public class OrderItem {
	
	/** ID */
	private Integer id;
	/** 顧客ID */
	private Integer customerId;
	/** 注文状態 */
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
	/** 宛先TEL */
	private String destinationTel;
	/** 配達時間 */
	private Timestamp deriveryTime;
	/** 支払い方法 */
	private Integer paymentMethod;
	/** 顧客情報 */
	private Customer customer;
	/** 支払い方法 */
	private List<OrderItem> orderItemList;
	
	
}
