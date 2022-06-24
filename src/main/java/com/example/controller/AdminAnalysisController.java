package com.example.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author nao.yamada
 *
 */

import com.example.domain.Item;
import com.example.service.AdminAnalysisService;
import com.example.service.ItemListService;


@RequestMapping("/adminAnalysis")
@Controller
public class AdminAnalysisController {
	
	
	/** 顧客登録時に選択できる年代の数 */
	private static final int AGE_NUM = 6;
	
	@Autowired
	private AdminAnalysisService adminAnalysisService;

	@Autowired
	private ItemListService itemListService;
	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	@RequestMapping("/pieChart")
	public String displayPieChart(Integer itemId, Model model) {
		List<Item> allItemList = itemListService.findAll();
		model.addAttribute("itemList", allItemList);
		if(itemId == null) {
			itemId = 1;
		}
		model.addAttribute("selectedItemId", itemId);

		List<String> agePercentList = new ArrayList<>(2);
		List<String> genderPercentList = new ArrayList<>(AGE_NUM);
		
		
		// Map<male, 男性の数> Map<age20, 20代の数>のように値が格納されている
		Map<String, Integer> ageGenderCountMap = adminAnalysisService.getAgeGenderMapByItem(itemId);
		if (ageGenderCountMap == null ) {
		}else {
			// mapから「数」のみのListに変換する
			List<Integer> ageCountList = new ArrayList<>(Arrays.asList(ageGenderCountMap.get("male"),ageGenderCountMap.get("female")));
			List<Integer> genderCountList = new ArrayList<>();
			for (int i = 1; i <= AGE_NUM ; i++) {
				String keyName = "age" + String.valueOf(i*10) ;
				genderCountList.add(ageGenderCountMap.get(keyName));
			}
			
			// Listの要素を数から%に変換する
			agePercentList = toPercent(ageCountList);
			genderPercentList = toPercent(genderCountList);
			
			model.addAttribute("agePercentList", agePercentList);
			model.addAttribute("genderPercentList", genderPercentList);
		}

		return "pie_chart";
	}
	
	/**
	 * Listの要素の値を、その値がList要素合計に占める割合（%）に変換します.
	 * 
	 * @param list
	 */
	public List<String> toPercent(List<Integer> list) {
		List<String> percentList = new ArrayList<>();
		int total = 0;
		for (int elem : list) {
			total += elem;
		}
		for (int i = 0; i < list.size(); i++ ) {
			percentList.add(String.valueOf( (int)(((double)list.get(i)/total) * 100)));
		}
		
		return percentList;
	}
}
