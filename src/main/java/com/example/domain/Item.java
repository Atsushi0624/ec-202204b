package com.example.domain;

import java.util.List;

import lombok.Data;

/**
 * 
 * 商品を表すドメイン
 * 
 * @author ryosuke.moritani
 *
 */
@Data
public class Item {
	/** 商品ID */
	private Integer id;
	/** 商品名 */
	private String name;
	/** 商品詳細 */
	private String description;
	/** Mサイズ料金 */
	private Integer priceM;
	/** Lサイズ料金 */
	private Integer priceL;
	/** 画像パス */
	private String imagePath;
	/** 削除 */
	private Boolean deleted;
	/** トッピングリスト */
	private List<Topping> toppingList;
}
