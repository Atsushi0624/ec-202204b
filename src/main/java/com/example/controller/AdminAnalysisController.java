package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/adminAnalysis")
public class AdminAnalysisController {

	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	public String displayPieChart() {
		return "pie_chart";
	}
}
