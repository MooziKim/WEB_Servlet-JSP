<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입 페이지</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body class="bg-gray-100">
<div style="margin: auto; width: 400px; height: 1000px" class="py-40">
    <div class="p-2 bg-white max-w-md mx-auto rounded-md shadow-md">
        <form method="post" action="/signupAction.do" class="p-4">

            <h1 class="text-2xl font-bold mb-3 text-center">Please sign up</h1>

            <div class="mb-4">
                <label for="user_id" class="block text-gray-700 text-sm font-bold mb-2">회원 아이디</label>
                <input type="text" name="user_id" id="user_id" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300" placeholder="회원 아이디" required>
            </div>

            <div class="mb-4">
                <label for="user_password" class="block text-gray-700 text-sm font-bold mb-2">비밀번호</label>
                <input type="password" name="user_password" id="user_password" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300" placeholder="비밀번호" required>
            </div>

            <div class="mb-4">
                <label for="user_password_check" class="block text-gray-700 text-sm font-bold mb-2">비밀번호 확인</label>
                <input type="password" name="user_password_check" id="user_password_check" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300" placeholder="비밀번호 확인" required>
            </div>

            <div class="mb-4">
                <label for="user_name" class="block text-gray-700 text-sm font-bold mb-2">이름</label>
                <input type="text" name="user_name" id="user_name" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300" placeholder="이름" required>
            </div>

            <div class="mb-4">
                <label for="user_birth" class="block text-gray-700 text-sm font-bold mb-2">생년월일</label>
                <input type="text" name="user_birth" id="user_birth" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300" placeholder="생년월일" required>
            </div>

            <button class="w-full bg-gray-500 text-white py-2 rounded-md hover:bg-gray-800 focus:outline-none focus:ring focus:border-gray-300" type="submit">Sign up</button>

            <p class="mt-5 mb-3 text-center text-gray-500">© 2022-2024</p>

        </form>
    </div>
</div>
</body>