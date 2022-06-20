package com.example.domain;

import lombok.Data;

/**
 * OrderToppingを表すドメイン.
 * 
 * @author atsushi.kikuchi
 *
 */
@Data
public class OrderTopping {
	/** ID */
	private Integer id;
	/** トッピングID */
	private Integer toppingId;
	/** 注文アイテムID */
	private Integer orderItemId;
	/** トッピング情報 */
	private Topping topping;
}
