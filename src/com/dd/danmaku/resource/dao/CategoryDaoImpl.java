package com.dd.danmaku.resource.dao;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dd.danmaku.common.dao.BaseDaoMongoImpl;
import com.dd.danmaku.resource.bean.Category;


@Repository
public class CategoryDaoImpl extends BaseDaoMongoImpl implements CategoryDao {

	public Category getCategoryByName(String name) {
		Criteria criteria = Criteria.where("pid").is(pid);
		Query query = new Query(criteria);
		Sort sort = new Sort(Direction.ASC, "order");
		query.with(sort);
		return (LinkedList<Category>) mongoTemplate.find(query, Category.class);
	}
	
	public LinkedList<Category> getSubCategories(String pid) {
		Criteria criteria = Criteria.where("pid").is(pid);
		Query query = new Query(criteria);
		Sort sort = new Sort(Direction.ASC, "order");
		query.with(sort);
		return (LinkedList<Category>) mongoTemplate.find(query, Category.class);
	}
	
	public LinkedHashMap<Category, List<Category>> getAllCategories() {
		LinkedHashMap<Category, List<Category>> result = new LinkedHashMap<Category, List<Category>>();
//		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
//		orderBy.put("order", "ASC");
//		List<Category> categories = getResultList(Category.class, "{ pid : '%1$s' }", orderBy, Category.ROOT);
		List<Category> categories = getSubCategories(Category.ROOT);
		for (int i = 0; i < categories.size(); i++) {
			Category root = categories.get(i);
//			List<Category> subCategories = getResultList(Category.class, "{ pid : '%1$s' }", orderBy, root.getId());
			List<Category> subCategories = getSubCategories(root.getId());
			result.put(root, subCategories);
		}
		return result;
	}

}
