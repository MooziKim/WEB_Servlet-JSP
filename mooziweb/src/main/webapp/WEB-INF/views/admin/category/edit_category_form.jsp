<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Category</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>

<body>
<form action="/admin/edit_category.do" method="post" class="mt-4">
    <input type="hidden" name="categoryId" value="${category.categoryId}">

    <label class="block text-sm font-medium text-gray-700">카테고리 이름</label>
    <input type="text" name="categoryName" class="mt-1 p-2 border rounded-md w-full" value="${category.categoryName}" required>

    <div class="flex mt-4">
        <button type="submit" class="mr-2 p-2 bg-blue-500 text-white rounded-md">수정 완료</button>
        <a href="/admin/category_processing.do" class="p-2 bg-gray-300 text-gray-700 rounded-md">취소</a>
    </div>
</form>
</body>
</html>

