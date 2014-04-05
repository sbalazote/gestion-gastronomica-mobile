package com.fiuba.diner.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.ProductDTO;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.ProductService;
import com.fiuba.diner.service.SubcategoryService;

@Controller
public class ProductController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct(ModelMap modelMap) throws Exception {
		modelMap.put("categories", this.categoryService.getAll());
		return "addProduct";
	}

	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public @ResponseBody
	void saveProduct(@RequestBody ProductDTO productDTO) throws Exception {
		this.subcategoryService.save(this.getSubcategory(productDTO));
	}

	private Subcategory getSubcategory(ProductDTO productDTO) {
		Product product = new Product();

		product.setDescription(productDTO.getDescription());
		product.setActive(productDTO.isActive());
		product.setPrice(productDTO.getPrice());

		Subcategory subcategory = this.subcategoryService.get(productDTO.getSubcategoryId());
		if (productDTO.getId() != null) {
			product.setId(productDTO.getId());
		}

		if (productDTO.getSubcategoryId().equals(productDTO.getOldSubcategoryId())) {
			for (Product prod : subcategory.getProducts()) {
				if (prod.getId().equals(productDTO.getId())) {
					prod.setDescription(productDTO.getDescription());
					prod.setActive(productDTO.isActive());
					prod.setPrice(productDTO.getPrice());
				}
			}
		} else {
			subcategory.getProducts().add(product);
		}

		return subcategory;
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public @ResponseBody
	void deleteSubcategory(@RequestParam Integer subcategoryId, Integer productId) throws Exception {
		Subcategory subcategory = this.subcategoryService.get(subcategoryId);
		Product productToDelete = this.productService.get(productId);

		subcategory.getProducts().remove(productToDelete);

		this.subcategoryService.save(subcategory);
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.GET)
	public String updateProduct(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));
		Integer categoryId = Integer.valueOf(parameters.get("categoryId"));
		Integer subcategoryId = Integer.valueOf(parameters.get("subcategoryId"));

		modelMap.put("categories", this.categoryService.getAll());
		modelMap.put("subcategories", this.subcategoryService.getAll());

		Product product = this.productService.get(id);

		modelMap.put("id", product.getId());
		modelMap.put("description", product.getDescription());
		modelMap.put("categoryId", categoryId);
		modelMap.put("subcategoryId", subcategoryId);
		modelMap.put("price", product.getPrice());
		modelMap.put("active", product.getActive());

		return "updateProduct";
	}
}
