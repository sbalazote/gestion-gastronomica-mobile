package com.fiuba.diner.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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

import com.fiuba.diner.dto.CategorySubcategoryDTO;
import com.fiuba.diner.dto.ProductRankingReportDTO;
import com.fiuba.diner.dto.ProductRankingReportFiltersDTO;
import com.fiuba.diner.dto.SalesReportDTO;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Subcategory;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.CouponService;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ProductService;
import com.fiuba.diner.service.SubcategoryService;

@Controller
public class AdministrationController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "/categoryAdministration", method = RequestMethod.GET)
	public String categoryAdministration(ModelMap modelMap) throws Exception {
		return "categoryAdministration";
	}

	@RequestMapping(value = "/entitySaved", method = RequestMethod.GET)
	public String error(ModelMap modelMap) throws Exception {
		return "entitySaved";
	}

	@RequestMapping(value = "/deleteConfirmation", method = RequestMethod.GET)
	public String deleteConfirmation(ModelMap modelMap) throws Exception {
		return "deleteConfirmation";
	}

	@RequestMapping(value = "/subcategoryAdministration", method = RequestMethod.GET)
	public String subcategoryAdministration(ModelMap modelMap) throws Exception {
		return "subcategoryAdministration";
	}

	@RequestMapping(value = "/productAdministration", method = RequestMethod.GET)
	public String productAdministration(ModelMap modelMap) throws Exception {
		return "productAdministration";
	}

	@RequestMapping(value = "/tableAdministration", method = RequestMethod.GET)
	public String tableAdministration(ModelMap modelMap) throws Exception {
		return "tableAdministration";
	}

	@RequestMapping(value = "/reportsAdministration", method = RequestMethod.GET)
	public String reportsAdministration(ModelMap modelMap) throws Exception {
		List<CategorySubcategoryDTO> lista = new ArrayList<CategorySubcategoryDTO>();
		List<Category> categories = this.categoryService.getAll();
		modelMap.put("categories", categories);

		Iterator<Category> it = categories.iterator();
		while (it.hasNext()) {
			Category category = it.next();
			List<Subcategory> subcategories = category.getSubcategories();
			Iterator<Subcategory> subcategoriesIt = subcategories.iterator();
			while (subcategoriesIt.hasNext()) {
				Subcategory subcategory = subcategoriesIt.next();
				CategorySubcategoryDTO dto = new CategorySubcategoryDTO();
				dto.setCategoryId(category.getId());
				dto.setSubcategoryId(subcategory.getId());
				dto.setCategoryDescription(category.getDescription());
				dto.setSubcategoryDescription(subcategory.getDescription());
				lista.add(dto);
			}
		}
		modelMap.put("categoriesSubcategories", lista);
		return "reportsAdministration";
	}

	@RequestMapping(value = "/couponAdministration", method = RequestMethod.GET)
	public String couponAdministration(ModelMap modelMap) throws Exception {
		return "couponAdministration";
	}

	@RequestMapping(value = "/getBilledOrdersBetweenDates", method = RequestMethod.POST)
	public @ResponseBody
	List<SalesReportDTO> getBilledOrdersBetweenDates(@RequestParam String from, @RequestParam String to) throws Exception {
		return this.orderService.getBilledOrdersBetweenDates(from, to);
	}

	@RequestMapping(value = "/getRankedProductsBetweenDates", method = RequestMethod.POST)
	public @ResponseBody
	List<ProductRankingReportDTO> getRankedProductsBetweenDates(@RequestBody ProductRankingReportFiltersDTO categorySubcategoryFilter) throws Exception {
		Map<String, String> categorySubcategoryFilterMap = new HashMap<String, String>();

		for (String i : categorySubcategoryFilter.getCategorySubcategoryFilter()) {
			String[] splitted = i.split("-");
			String categoryDescription = this.categoryService.get(Integer.parseInt(splitted[0])).getDescription();
			String subcategoryDescription;
			if (splitted.length == 2) {
				subcategoryDescription = this.subcategoryService.get(Integer.parseInt(splitted[1])).getDescription();
			} else {
				subcategoryDescription = "TODAS";
			}
			categorySubcategoryFilterMap.put(categoryDescription, subcategoryDescription);
		}

		Map<String, Integer> productRankingMap = new HashMap<String, Integer>();
		List<ProductRankingReportDTO> productRankingReportDTOList = new ArrayList<ProductRankingReportDTO>();
		List<Order> orders = this.orderService.getOrdersBetweenDates(categorySubcategoryFilter.getFrom(), categorySubcategoryFilter.getTo());
		Iterator<Order> it = orders.iterator();
		while (it.hasNext()) {
			Order order = it.next();
			Iterator<OrderDetail> it2 = order.getDetails().iterator();
			while (it2.hasNext()) {
				OrderDetail orderDetail = it2.next();
				Integer amount = productRankingMap.get(orderDetail.getProduct().getDescription());
				if (amount != null) {
					amount += orderDetail.getAmount();
					productRankingMap.put(orderDetail.getProduct().getDescription(), amount);
				} else {
					productRankingMap.put(orderDetail.getProduct().getDescription(), orderDetail.getAmount());
				}
			}
		}
		Iterator<String> it3 = productRankingMap.keySet().iterator();
		while (it3.hasNext()) {
			String key = it3.next();
			ProductRankingReportDTO prrDTO = this.productService.getCategorySubcategoryFromProduct(key);
			prrDTO.setProductDescription(key);
			prrDTO.setNumberOfTimesServed(productRankingMap.get(key));
			if (categorySubcategoryFilterMap.containsKey(prrDTO.getCategory())) {
				if ((categorySubcategoryFilterMap.get(prrDTO.getCategory()).equals("TODAS"))
						|| (categorySubcategoryFilterMap.get(prrDTO.getCategory()).equals(prrDTO.getSubcategory()))) {
					productRankingReportDTOList.add(prrDTO);
				}
			}
		}
		Collections.sort(productRankingReportDTOList, new Comparator<ProductRankingReportDTO>() {
			@Override
			public int compare(ProductRankingReportDTO r1, ProductRankingReportDTO r2) {
				if (r1.getNumberOfTimesServed() == null) {
					return 1;
				}
				if (r2.getNumberOfTimesServed() == null) {
					return -1;
				}
				return -(r1.getNumberOfTimesServed().compareTo(r2.getNumberOfTimesServed()));
			}
		});
		return productRankingReportDTOList;
	}

}