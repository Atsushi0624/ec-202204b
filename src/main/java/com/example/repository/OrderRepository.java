package com.example.repository;

import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author atsushi.kikuchi
 *
 */
@Repository
public class OrderRepository {

	private NamedParameterJdbcTemplate template;
	
	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = null;
//			(rs) -> {
//		List<Article> articleList = new ArrayList<>();
//
//		List<Comment> commentList = null;
//		
//		int preId = 0;
//		while (rs.next()) {
//			int id = rs.getInt("a_id");
//
//			if (id != preId) {
//				Article article = new Article();
//				article.setId(rs.getInt("a_id"));
//				article.setName(rs.getString("a_name"));
//				article.setContent(rs.getString("a_content"));
//
//				commentList = new ArrayList<Comment>();
//				article.setCommentList(commentList);
//
//				articleList.add(article);
//			}
//
//			if (rs.getInt("c_id") != 0) {
//				Comment comment = new Comment();
//				comment.setId(rs.getInt("c_id"));
//				comment.setName(rs.getString("c_name"));
//				comment.setContent(rs.getString("c_content"));
//				comment.setArticleId(rs.getInt("article_id"));
//				
//				commentList.add(comment);
//			}
//
//			preId = id;
//		}
//        
//        return articleList;
//	};
	
	/**
	 * 注文情報を挿入し、採番された番号を返す.
	 * 
	 * @param order 注文情報
	 * @return オーダーID
	 */
	public Integer insert(Order order) {
		return null;
	}
	
	/**
	 * オーダーIDから注文情報を取得.
	 * 
	 * @param orderId オーダーID
	 * @return 注文情報
	 */
	public Order load(Integer orderId) {	
		return null;		
	}
	
	/**
	 * ステータスと顧客IDからオーダーIDを取得.
	 * 
	 * @param status 注文状態
	 * @param customerId 顧客ID
	 * @return オーダーID
	 */
	public Integer findOrderIdByStatusAndUserId(String status, Integer customerId) {
		return null;
	}
	
	/**
	 * ダミーIDを正規のIDに書き換える.
	 * 
	 * @param dummyCustomerId ダミー顧客ID
	 * @param customerId 正規の顧客ID
	 */
	public void updateUserId(Integer dummyCustomerId, Integer customerId) {
	}
}
