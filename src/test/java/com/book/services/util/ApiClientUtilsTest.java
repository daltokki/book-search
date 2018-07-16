package com.book.services.util;

import com.book.interfaces.book.model.BookSearchForm;
import com.book.services.application.book.value.BookSearchType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiClientUtilsTest {
	private BookSearchForm bookSearchForm;

	@Before
	public void setUp() {
		bookSearchForm = new BookSearchForm();
		bookSearchForm.setQuery("Spring");
		bookSearchForm.setTarget(BookSearchType.TITLE.name());
	}

	@Test
	public void toUrlTest() {
		String url = ApiClientUtils.toUrl(bookSearchForm);
		Assert.assertNotNull(url);
		Assert.assertEquals(url, "page={page}&size={size}&query={query}&target={target}");
	}

	@Test
	public void buildValueMapTest() {
		Map<String, Object> buildValueMap = ApiClientUtils.buildValueMap(bookSearchForm);
		Assert.assertEquals(buildValueMap.get("query"), bookSearchForm.getQuery());
	}
}