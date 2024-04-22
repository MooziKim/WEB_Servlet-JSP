<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>고객 처리 페이지</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body>
<div class="container mx-auto">
    <h1 class="text-2xl font-bold mb-4">고객 처리 페이지</h1>

    <h2 class="text-xl font-bold mb-2">관리자 목록</h2>
    <table class="min-w-full border w-full mb-4">
        <thead>
        <tr>
            <th class="border p-3">ID</th>
            <th class="border p-3">User Name</th>
            <th class="border p-3">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${adminUserList}">
            <tr>
                <td class="border p-3">${user.userId}</td>
                <td class="border p-3">${user.userName}</td>
                <td class="border p-3">
                    <a href="/admin/user_detail.do?id=${user.userId}" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">상세보기</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="mt-4">
        <nav class="flex justify-center">
            <ul class="pagination">
                <li class="page-item">
                    <a href="/admin/user_processing.do?adminPage=${adminCurrentPage - 1}&rolePage=${roleCurrentPage}" class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach var="i" begin="1" end="${adminPageCount}">
                    <li class="page-item">
                        <a href="/admin/user_processing.do?adminPage=${i}&rolePage=${roleCurrentPage}" class="page-link ${adminCurrentPage eq i ? 'bg-blue-500 text-white' : 'hover:bg-blue-500 hover:text-white'}">${i}</a>
                    </li>
                </c:forEach>

                <li class="page-item">
                    <a href="/admin/user_processing.do?adminPage=${adminCurrentPage + 1}&rolePage=${roleCurrentPage}" class="page-link" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>

    <h2 class="text-xl font-bold mb-2 mt-6">일반 고객 목록</h2>
    <table class="min-w-full border w-full">
        <thead>
        <tr>
            <th class="border p-3">ID</th>
            <th class="border p-3">User Name</th>
            <th class="border p-3">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${roleUserList}">
            <tr>
                <td class="border p-3">${user.userId}</td>
                <td class="border p-3">${user.userName}</td>
                <td class="border p-3">
                    <a href="/admin/user_detail.do?id=${user.userId}" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">상세보기</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="mt-4">
        <nav class="flex justify-center">
            <ul class="pagination">
                <li class="page-item">
                    <a href="/admin/user_processing.do?rolePage=${roleCurrentPage - 1}&adminPage=${adminCurrentPage}" class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach var="i" begin="1" end="${rolePageCount}">
                    <li class="page-item">
                        <a href="/admin/user_processing.do?rolePage=${i}&adminPage=${adminCurrentPage}" class="page-link ${roleCurrentPage eq i ? 'bg-blue-500 text-white' : 'hover:bg-blue-500 hover:text-white'}">${i}</a>
                    </li>
                </c:forEach>

                <li class="page-item">
                    <a href="/admin/user_processing.do?rolePage=${roleCurrentPage + 1}&adminPage=${adminCurrentPage}" class="page-link" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>

</body>
</html>
