package com.softwarelabs.product;

import com.softwarelabs.config.BaseIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
public class CrudProductServiceIntegrationTest extends BaseIntegrationTest {

	@Autowired
	private CrudProductService crudProductService;

	@Test
	public void returnProductName_ifProductSavedBefore() {
		String productName = "product001";
		BigDecimal price = BigDecimal.TEN;
		Product product = new ProductPort.ProductRequest(productName, price);

		crudProductService.saveProduct(product);
		Product actualProduct = crudProductService.getProduct(product);
		Assert.assertNotNull(actualProduct);
		Assert.assertEquals(productName, actualProduct.name());
	}

	@Test(expected = RuntimeException.class)
	public void throwRunTimeException_ifProductNotExist() {
		String productName = "product002";
		BigDecimal price = BigDecimal.TEN;
		Product product = new ProductPort.ProductRequest(productName, price);

		crudProductService.getProduct(product);
	}
}