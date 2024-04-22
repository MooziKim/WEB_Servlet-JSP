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
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/edit_product.do")
public class AdminEditProductFormController implements BaseController {
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoriesForProductsService cfpService = new CategoriesForProductsServiceImpl(new CategoriesForProductsRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        Product beforeProduct = productService.getProductById(productId);

        String productImage = handleProductImage(req, beforeProduct.getProductImage());

        String modelNumber = req.getParameter("modelNumber");
        String modelName = req.getParameter("modelName");

        updateCategoriesForProduct(req, productId);

        int unitCost = Integer.parseInt(req.getParameter("unitCost"));
        String description = req.getParameter("description");
        int viewCount = Integer.parseInt(req.getParameter("viewCount"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Product updateProduct = new Product(productId, modelNumber, modelName, productImage, unitCost, description, viewCount, quantity);
        productService.updateProduct(updateProduct);

        DbConnectionThreadLocal.reset();
        return "redirect:/admin/index.do";
    }

    private String handleProductImage(HttpServletRequest req, String beforeProductImageName) throws IOException, ServletException {
        String uploadPath = req.getServletContext().getRealPath("/resources");
        File beforeProductImage = new File(uploadPath + File.separator + beforeProductImageName);

        String productImage = beforeProductImageName;

        if (beforeProductImage.exists() && Objects.nonNull(req.getPart("productImage")) && !extractFileName(req.getPart("productImage").getHeader(CONTENT_DISPOSITION)).isEmpty()) {
            if (!productImage.equals("no-image.png")) {
                beforeProductImage.delete();
            }
            productImage = saveProductImage(req, uploadPath);
        }

        return productImage;
    }

    private String saveProductImage(HttpServletRequest req, String uploadPath) throws IOException, ServletException {
        Part productImagePart = req.getPart("productImage");
        String contentDisposition = productImagePart.getHeader(CONTENT_DISPOSITION);
        String productImage = extractFileName(contentDisposition);

        if (contentDisposition.contains("filename=")) {
            if (productImagePart.getSize() > 0) {
                productImagePart.write(uploadPath + File.separator + productImage);
                productImagePart.delete();
            }
        } else {
            String formValue = req.getParameter(productImagePart.getName());
            log.error("{}={}", productImagePart.getName(), formValue);
        }

        return productImage;
    }

    private void updateCategoriesForProduct(HttpServletRequest req, int productId) {
        String[] categoryIds = req.getParameterValues("categoryIds");
        cfpService.deleteCategoriesForProductsByProductId(productId);

        for (String categoryId : categoryIds) {
            int intCategoryId = Integer.parseInt(categoryId);
            cfpService.saveCategoriesForProducts(intCategoryId, productId);
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
