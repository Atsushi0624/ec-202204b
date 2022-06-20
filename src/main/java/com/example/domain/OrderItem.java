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
	/** アイテムID */
	private Integer itemId;
	/** オーダーID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private String size;
	/** アイテム情報 */
	private Item item;
	/** トッピングリスト */
	private List<OrderTopping> orderTopping;
	
}
