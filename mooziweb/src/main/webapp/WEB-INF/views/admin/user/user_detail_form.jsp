<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>고객 정보</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body class="bg-gray-100 p-4">

<div class="max-w-2xl mx-auto bg-white p-8 my-8 rounded-md shadow-md">
  <h1 class="text-2xl font-bold mb-4">고객 정보</h1>

  <c:if test="${not empty userInfo}">
    <table class="w-full mb-4">
      <tr>
        <th class="text-left">User ID</th>
        <td class="text-left">${userInfo.userId}</td>
      </tr>
      <tr>
        <th class="text-left">User Name</th>
        <td class="text-left">${userInfo.userName}</td>
      </tr>
      <tr>
        <th class="text-left">User Birth</th>
        <td class="text-left">${userInfo.userBirth}</td>
      </tr>
      <tr>
        <th class="text-left">User Auth</th>
        <td class="text-left">${userInfo.userAuth}</td>
      </tr>
      <tr>
        <th class="text-left">User Created At</th>
        <td class="text-left">${userInfo.createdAt}</td>
      </tr>
      <tr>
        <th class="text-left">User Latest Login At</th>
        <td class="text-left">${userInfo.latestLoginAt}</td>
      </tr>
    </table>
  </c:if>

  <a href="/admin/user_processing.do" class="mt-4 block text-blue-500 hover:underline">유저 목록</a>
</div>

</body>
</html>

