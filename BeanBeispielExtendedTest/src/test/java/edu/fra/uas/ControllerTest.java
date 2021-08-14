package edu.fra.uas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.fra.uas.controller.BeanController;

@SpringBootTest
public class ControllerTest {

	@Autowired
	private BeanController beanController;

	@Test
	void testController() {
		assertThat(beanController.putMessage("Das ist ein Test")).isEqualTo("Das ist ein Test");
	}
	
}
