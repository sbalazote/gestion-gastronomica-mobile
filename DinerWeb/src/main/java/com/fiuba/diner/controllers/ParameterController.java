package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.ParameterDTO;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.service.ParameterService;

@Controller
public class ParameterController {
	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = "/saveParameter", method = RequestMethod.POST)
	public @ResponseBody
	void saveParameter(@RequestBody ParameterDTO parameterDTO) throws Exception {
		this.parameterService.save(this.buildModel(parameterDTO));
	}

	private Parameter buildModel(ParameterDTO parameterDTO) {
		Parameter parameter = new Parameter();
		if (parameterDTO.getId() != null) {
			parameter.setId(parameterDTO.getId());
		}

		parameter.setRestaurantName(parameterDTO.getRestaurantName());
		parameter.setAddress(parameterDTO.getAddress());
		parameter.setDinnerServicePrice(parameterDTO.getDinnerServicePrice());
		parameter.setDinnerServiceActive(parameterDTO.getDinnerServiceActive());
		return parameter;
	}

	@RequestMapping(value = "/updateParameter", method = RequestMethod.GET)
	public String updateParameter(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = 1; // Integer.valueOf(parameters.get("id"));
		Parameter parameter = this.parameterService.get(id);

		modelMap.put("id", parameter.getId());
		modelMap.put("restaurantName", parameter.getRestaurantName());
		modelMap.put("address", parameter.getAddress());
		modelMap.put("dinnerServicePrice", parameter.getDinnerServicePrice());
		modelMap.put("dinnerServiceActive", parameter.getDinnerServiceActive());

		return "updateParameter";
	}

	@RequestMapping(value = "/getParameters", method = RequestMethod.GET)
	@ResponseBody
	public Parameter getParameters() throws IOException {
		return this.parameterService.get(1);
	}
}
