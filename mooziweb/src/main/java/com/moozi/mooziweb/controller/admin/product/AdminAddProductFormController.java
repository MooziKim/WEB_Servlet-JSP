package com.moozi.mooziweb.controller.admin.product;

import com.moozi.mooziweb.common.mvc.annotation.RequestMapping;
import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.mvc.transaction.DbConnectionThreadLocal;
import com.moozi.mooziweb.product.domain.Product;
import com.moozi.mooziweb.product.repository.impl.CategoriesForProductsRepositoryImpl;
import com.moozi.mooziweb.product.repository.impl.ProductRepositoryImpl;
import com.moozi.mooziweb.product.service.CategoriesForProductsService;
import com.moozi.mooziweb.product.service.ProductService;
import com.moozi.mooziweb.product.service.impl.CategoriesForProductsServiceImpl;
import com.moozi.mooziweb.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/add_product.do")
public class AdminAddProductFormController implements BaseController {
    private static final String NO_IMAGE_FILE = "no-image.png";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoriesForProductsService cfpService = new CategoriesForProductsServiceImpl(new CategoriesForProductsRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String modelNumber = req.getParameter("modelNumber");
        String modelName = req.getParameter("modelName");

        Part productImagePart = req.getPart("productImage");
        String productImage = saveProductImage(req, productImagePart);

        int unitCost = Integer.parseInt(req.getParameter("unitCost"));
        String description = req.getParameter("description");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Product saveProduct = new Product(0, modelNumber, modelName, productImage, unitCost, description, 0, quantity);
        int saveProductId = productService.saveProduct(saveProduct);

        saveCategoriesForProduct(req, saveProductId);

        DbConnectionThreadLocal.reset();
        return "redirect:/admin/index.do";
    }

    private String saveProductImage(HttpServletRequest req, Part productImagePart) throws IOException {
        String contentDisposition = productImagePart.getHeader(CONTENT_DISPOSITION);
        String productImage = NO_IMAGE_FILE;

        if (contentDisposition.contains("filename=")) {
            String uploadPath = req.getServletContext().getRealPath("/resources");
            productImage = extractFileName(contentDisposition);

            if (productImagePart.getSize() > 0) {
                productImagePart.write(uploadPath + File.separator + productImage);
                productImagePart.delete();
            }
        } else {
            String formValue = req.getParameter(productImagePart.getName());
            log.error("{}={}", productImagePart.getName(), formValue);
        }

        return productImage.isEmpty() ? NO_IMAGE_FILE : productImage;
    }

    private void saveCategoriesForProduct(HttpServletRequest req, int saveProductId) {
        String[] categoryIds = req.getParameterValues("categoryIds");
        for (String categoryId : categoryIds) {
            int intCategoryId = Integer.parseInt(categoryId);
            cfpService.saveCategoriesForProducts(intCategoryId, saveProductId);
        }
    }

    private String extractFileName(String contentDisposition) {
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
}
