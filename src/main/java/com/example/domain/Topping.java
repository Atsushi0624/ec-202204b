package com.example.domain;

import lombok.Data;

/**
 * トッピングを表すドメイン
 * 
 * @author ryosuke.moritani
 *
 */
@Data
public class Topping {
	/** トッピングID */
	private Integer id;
	/** トッピング名 */
	private String name;
	/** Mサイズのトッピング料金 */
	private Integer priceM;
	/** Lサイズのトッピング料金 */
	private Integer priceL;
}
