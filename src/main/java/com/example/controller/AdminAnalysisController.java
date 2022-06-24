package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemListService;

@RequestMapping("/adminAnalysis")
@Controller
public class AdminAnalysisController {

	@Autowired
	private ItemListService itemListService;
	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	@RequestMapping("/pieChart")
	public String displayPieChart(Model model, Integer itemId) {
		List<Item> allItemList = itemListService.findAll();
		model.addAttribute("itemList", allItemList);
		if(itemId == null) {
			itemId = 1;
		}
		model.addAttribute("selectedItemId", itemId);
		return "pie_chart";
	}
}
