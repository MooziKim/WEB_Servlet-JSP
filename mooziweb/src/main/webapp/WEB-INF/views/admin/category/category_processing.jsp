<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>카테고리 처리 페이지</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body>
<div class="container mx-auto">
  <h1 class="text-2xl font-bold mb-4">카테고리 처리 페이지</h1>

  <table class="min-w-full border w-full">
    <thead>
    <tr>
      <th class="border p-3">ID</th>
      <th class="border p-3">Category Name</th>
      <th class="border p-3">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="category" items="${categoryList}">
      <tr>
        <td class="border p-3">${category.categoryId}</td>
        <td class="border p-3">${category.categoryName}</td>
        <td class="border p-3">
          <a href="/admin/edit_category.do?id=${category.categoryId}" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">수정</a>
          <a href="/admin/delete_category.do?id=${category.categoryId}" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded ml-2">삭제</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <form method="get" action="/admin/add_category.do">
    <button type="submit" class="mt-2 p-2 bg-blue-500 text-white rounded-md">카테고리 추가</button>
  </form>
</div>

</body>
</html>

