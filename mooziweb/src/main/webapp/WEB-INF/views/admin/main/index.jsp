<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>제품 처리 페이지</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body>
<div class="container mx-auto">
    <h1 class="text-2xl font-bold mb-4">제품 처리 페이지</h1>

    <table class="min-w-full border w-full">
        <thead>
        <tr>
            <th class="border p-3">ID</th>
            <th class="border p-3">Model Name</th>
            <th class="border p-3">Quantity</th>
            <th class="border p-3">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${productList}">
            <tr>
                <td class="border p-3">${product.productId}</td>
                <td class="border p-3">${product.modelName}</td>
                <td class="border p-3">${product.quantity}</td>
                <td class="border p-3">
                    <a href="/admin/edit_product.do?id=${product.productId}" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">수정</a>
                    <a href="/admin/delete_product.do?id=${product.productId}" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded ml-2">삭제</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="mt-4">
        <nav class="flex justify-center">
            <ul class="pagination">
                <li class="page-item">
                    <a href="/admin/index.do?page=${currentPage - 1}" class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach var="i" begin="1" end="${pageCount}">
                    <li class="page-item">
                        <a href="/admin/index.do?page=${i}" class="page-link ${currentPage eq i ? 'bg-blue-500 text-white' : 'hover:bg-blue-500 hover:text-white'}">${i}</a>
                    </li>
                </c:forEach>

                <li class="page-item">
                    <a href="/admin/index.do?page=${currentPage + 1}" class="page-link" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>

    <form method="get" action="/admin/add_product.do">
        <button type="submit" class="mt-2 p-2 bg-blue-500 text-white rounded-md">제품 추가</button>
    </form>
</div>

</body>
</html>
