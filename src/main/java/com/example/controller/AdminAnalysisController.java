package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.AdminAnalysisService;

/**
 * @author nao.yamada
 *
 */
@RequestMapping("/adminAnalysis")
@Controller
public class AdminAnalysisController {
	
	
	/** 顧客登録時に選択できる年代の数 */
	private static final int AGE_NUM = 6;
	
	@Autowired
	private AdminAnalysisService adminAnalysisService;

	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	@RequestMapping("/pieChart")
	public String displayPieChart(Integer itemId, Model model) {
		if (itemId == null) {
			itemId = 1;
		}
//		Integer[] agePercentArray;
//		Integer[] genderPercentArray;
		List<Integer> agePercentList = new ArrayList<>(2);
		List<Integer> genderPercentList = new ArrayList<>(AGE_NUM);
		
		
		// Map<male, 男性の数> Map<age20, 20代の数>のように値が格納されている
		Map<String, Integer> ageGenderCountMap = adminAnalysisService.getAgeGenderMapByItem(itemId);
		if (ageGenderCountMap == null ) {
//			agePercentArray = new Integer[AGE_NUM];
//			genderPercentArray = new Integer[AGE_NUM];
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
			
//			agePercentArray = (Integer[])agePercentList.toArray();
//			genderPercentArray = (Integer[])genderPercentList.toArray();
			
			System.out.println(agePercentList.toString());
			System.out.println(genderPercentList.toString());
			
		}
		model.addAttribute("agePercentArray", agePercentList);
		model.addAttribute("genderPercentArray", genderPercentList);
		return "pie_chart";
	}
	
	/**
	 * Listの要素の値を、その値がList要素合計に占める割合（%）に変換します.
	 * 
	 * @param list
	 */
	public List<Integer> toPercent(List<Integer> list) {
		List<Integer> percentList = new ArrayList<>(list.size());
		System.out.println(list);
		int total = 0;
		for (int elem : list) {
			total += elem;
		}
		for (int i = 0; i < list.size(); i++ ) {
			percentList.add((int)((list.get(i)/total) * 100));
		}
		System.out.println(percentList);
		return percentList;
	}
}
