package com.book.services.application.category;

import com.book.repository.CategoryRepository;
import com.book.repository.entity.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@Transactional
	public void initCategory() {
		TypeReference<List<Category>> typeReference = new TypeReference<List<Category>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/static/json/categoryInfo.json");
		try {
			List<Category> categoryInfo = mapper.readValue(inputStream,typeReference);
			categoryRepository.saveAll(categoryInfo);
			log.info("categoryInfo load completed.");
		} catch (IOException e){
			log.error("Unable to load categoryInfo: " + e.getMessage());
		}
	}

	public Map<String, List<Category>> getCategoryMap() {
		List<Category> allCategories = categoryRepository.findAll();
		return allCategories.stream().collect(Collectors.groupingBy(Category::getMainCategory));
	}

	public Category findCategory(Integer categoryId) {
		return categoryRepository.findByCategoryIdEquals(categoryId);
	}
}
