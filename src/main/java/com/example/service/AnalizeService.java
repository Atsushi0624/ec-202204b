package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.AnalizeData;
import com.example.repository.AnalizeRepository;
import com.example.repository.ItemRepository;

/**
 * おすすめ商品特定用の分析データを操作するサービス.
 * 
 * @author nao.yamada
 *
 */
@Service
public class AnalizeService {

	@Autowired
	private AnalizeRepository analizeRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 分析データを取得します.
	 * 
	 * @return
	 */
	public  List<AnalizeData> getAnalizeData() {
		int itemNum = itemRepository.getItemNum();
		analizeRepository.setItemNum(itemNum);
		return analizeRepository.getdata();
	}
	
}
