package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.TableDTO;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.service.TableService;

@Controller
public class TableAdministrationController {
	@Autowired
	private TableService tableService;

	@RequestMapping(value = "/addTable", method = RequestMethod.GET)
	public String addTable(ModelMap modelMap) throws Exception {
		return "addTable";
	}

	@RequestMapping(value = "/saveTable", method = RequestMethod.POST)
	public @ResponseBody
	void saveTable(@RequestBody TableDTO tableDTO) throws Exception {
		this.tableService.save(this.buildModel(tableDTO));
	}

	private Table buildModel(TableDTO tableDTO) {
		Table table = new Table();
		if (tableDTO.getId() != null) {
			table.setId(tableDTO.getId());
		}
		table.setLocked(Boolean.FALSE);
		table.setState(TableStateHelper.AVAILABLE.getState());
		table.setActive(tableDTO.isActive());
		return table;
	}

	@RequestMapping(value = "/getAllTables", method = RequestMethod.GET)
	@ResponseBody
	public List<Table> getAllTables() throws IOException {
		return this.tableService.getAllTables();
	}

	@RequestMapping(value = "/deleteTable", method = RequestMethod.POST)
	public @ResponseBody
	void deleteTable(@RequestParam Integer tableId) throws Exception {
		this.tableService.delete(tableId);
	}

	@RequestMapping(value = "/updateTable", method = RequestMethod.GET)
	public String updateTable(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));

		Table table = this.tableService.get(id);
		modelMap.put("id", table.getId());
		modelMap.put("active", table.getActive());
		return "updateTable";
	}

}
