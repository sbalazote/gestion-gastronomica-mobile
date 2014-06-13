package com.fiuba.diner.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.CouponDTO;
import com.fiuba.diner.helper.CipherHelper;
import com.fiuba.diner.model.Coupon;
import com.fiuba.diner.qr.QRCodeGenerator;
import com.fiuba.diner.service.CouponService;

@Controller
public class CouponController {
	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "/addCoupon", method = RequestMethod.GET)
	public String addCoupon(ModelMap modelMap) throws Exception {
		return "addCoupon";
	}

	@RequestMapping(value = "/saveCoupon", method = RequestMethod.POST)
	public @ResponseBody
	void saveCoupon(@RequestBody CouponDTO couponDTO) throws Exception {
		Coupon coupon = this.buildModel(couponDTO);
		this.couponService.save(coupon);
		this.generateCoupon(coupon);
	}

	private Coupon buildModel(CouponDTO couponDTO) {
		Coupon coupon = new Coupon();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

		if (couponDTO.getId() != null) {
			coupon.setId(couponDTO.getId());
		}

		coupon.setDescription(couponDTO.getDescription());
		coupon.setPercentage(couponDTO.getPercentage());
		try {
			coupon.setStartingDate(dateFormatter.parse(couponDTO.getStartingDate()));
			coupon.setExpirationDate(dateFormatter.parse(couponDTO.getExpirationDate()));
		} catch (ParseException e) {
		}
		return coupon;
	}

	private void generateCoupon(Coupon coupon) throws Exception {
		String encryptedQRCodeText = CipherHelper.encrypt(String.valueOf(coupon.getId()));
		String filePath = "C:/coupons/coupon_" + coupon.getId() + ".png";
		int size = 125;
		String fileType = "png";
		File qrFile = new File(filePath);
		QRCodeGenerator.createQRImage(qrFile, encryptedQRCodeText, size, fileType);
	}

	@RequestMapping(value = "/retrieveCouponImage", method = RequestMethod.POST)
	public @ResponseBody
	String retrieveCouponImage(@RequestParam Integer couponId) throws Exception {
		File file = new File("C:/coupons/coupon_" + couponId + ".png");
		FileInputStream imageInFile = new FileInputStream(file);
		byte imageData[] = new byte[(int) file.length()];
		imageInFile.read(imageData);

		// Converting Image byte array into Base64 String
		String imageDataString = Base64.encodeBase64String(imageData);

		imageInFile.close();

		return imageDataString;
	}

	@RequestMapping(value = "/getCoupons", method = RequestMethod.GET)
	@ResponseBody
	public List<Coupon> getCoupons() throws IOException {
		return this.couponService.getAll();
	}

	@RequestMapping(value = "/deleteCoupon", method = RequestMethod.POST)
	public @ResponseBody
	void deleteCoupon(@RequestParam Integer couponId) throws Exception {
		this.couponService.delete(couponId);
	}

}
