package com.fiuba.diner.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.service.TableService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class TableServiceTest {

	@Autowired
	private TableService tableService;

	@Test
	public void save() {
		Table table = new Table();
		table.setId(21);
		Integer sizeBefore = this.tableService.getAll().size();
		table.setActive(Boolean.FALSE);
		table.setLocked(Boolean.FALSE);
		table.setState(TableStateHelper.AVAILABLE.getState());

		this.tableService.save(table);
		System.out.println(sizeBefore);

		Assert.isTrue(this.tableService.getAll().size() == (sizeBefore + 1));
		System.out.println(this.tableService.getAll().size());
	}
}
