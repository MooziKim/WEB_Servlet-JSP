<%--
  Created by IntelliJ IDEA.
  User: parksangwon
  Date: 24. 1. 16.
  Time: 오후 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Product</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body>
<form action="/admin/edit_product.do" method="post" class="mt-4" enctype="multipart/form-data">
    <input type="hidden" name="productId" value="${product.productId}">

    <label class="block text-sm font-medium text-gray-700">제품 번호</label>
    <input type="text" name="modelNumber" class="mt-1 p-2 border rounded-md w-full" value="${product.modelNumber}" required>

    <label class="block text-sm font-medium text-gray-700">제품 이름</label>
    <input type="text" name="modelName" class="mt-1 p-2 border rounded-md w-full" value="${product.modelName}" required>

    <label class="block text-sm font-medium text-gray-700">카테고리</label>
    <div class="flex flex-wrap mt-1">
        <c:forEach var="category" items="${categoryList}">
            <input type="checkbox" name="categoryIds" value="${category.categoryId}" id="category-${category.categoryId}" class="mr-2"
            <c:if test="${productCategoryList.contains(category)}">
                   checked
            </c:if>
            >
            <label for="category-${category.categoryId}">${category.categoryName}</label>
        </c:forEach>
    </div>

    <label class="block text-sm font-medium text-gray-700">제품 이미지</label>
    <input type="file" name="productImage" class="mt-1 p-2 border rounded-md w-full" accept="image/*">

    <label class="block text-sm font-medium text-gray-700">제품 가격</label>
    <input type="number" name="unitCost" class="mt-1 p-2 border rounded-md w-full" value="${product.unitCost}" required>

    <label class="block text-sm font-medium text-gray-700">제품 설명</label>
    <textarea name="description" class="mt-1 p-2 border rounded-md w-full" >${product.description}</textarea>

    <input type="hidden" name="viewCount" value="${product.viewCount}">

    <label class="block text-sm font-medium text-gray-700">수량</label>
    <input type="number" name="quantity" class="mt-1 p-2 border rounded-md w-full" value="${product.quantity}" required>

    <div class="flex mt-4">
        <button type="submit" class="mr-2 p-2 bg-blue-500 text-white rounded-md">수정 완료</button>
        <a href="/admin/index.do" class="p-2 bg-gray-300 text-gray-700 rounded-md">취소</a>
    </div>
</form>
</body>
</html>

