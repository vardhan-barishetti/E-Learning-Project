package com.elearn.app;

import com.elearn.app.config.security.JwtUtil;
import com.elearn.app.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ELearningBackendApplicationTests {

	@Autowired
	private CategoryService categoryService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCategoryCourseRelation(){
		categoryService.addCourseToCategory("6ec51613-ed57-43ff-84ac-0be4b6014e73", "168fa40e-bbca-4846-9d58-198cb50599ae");
	}


	@Autowired
	private JwtUtil jwtUtil;

	@Test
	public void testJwt(){
		System.out.println("testing jwt");
		String token = jwtUtil.generateToken("Ayush");
		System.out.println(token);

		System.out.println(jwtUtil.validateToken(token, "Ayush"));

		System.out.println(jwtUtil.extractUsername(token));
	}

}
