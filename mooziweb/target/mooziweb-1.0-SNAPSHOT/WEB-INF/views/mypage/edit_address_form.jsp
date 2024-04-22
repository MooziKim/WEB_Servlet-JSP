<%--
  Created by IntelliJ IDEA.
  User: parksangwon
  Date: 24. 1. 22.
  Time: 오전 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;1,200;1,300;1,400;1,500;1,600;1,700;1,800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
  <style>
    body {
      font-family: 'Plus Jakarta Sans', sans-serif;
    }
  </style>
  <title>마이페이지 - 주소 수정</title>
</head>
<body>
<div class="bg-white w-full flex flex-col gap-5 px-3 md:px-16 lg:px-28 md:flex-row text-[#161931]">
  <aside class="hidden py-4 md:w-1/3 lg:w-1/4 md:block">
    <div class="sticky flex flex-col gap-2 p-4 text-sm border-r border-indigo-100 top-12">

      <h2 class="pl-3 mb-4 text-2xl font-semibold">Settings</h2>

      <a href="/mypage/index.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
        회원 정보 수정
      </a>
      <a href="/mypage/change_password.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
        비밀번호 변경
      </a>
      <a href="/mypage/management_address.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
        주소 관리
      </a>
      <a href="/mypage/pointInfo.do" class="flex items-center px-3 py-2.5 font-semibold hover:text-indigo-900 hover:border hover:rounded-full  ">
        포인트 사용내역 조회
      </a>
      <a href="/mypage/order.do" class="flex items-center px-3 py-2.5 font-semibold hover:text-indigo-900 hover:border hover:rounded-full  ">
        주문 명세 조회
      </a>
    </div>
  </aside>
  <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
    <div class="container mx-auto mt-8 p-4">
      <h2 class="text-2xl font-bold mb-4">주소 수정</h2>

      <form method="post" action="/mypage/editAddress.do">
        <input type="hidden" name="addressId" value="${address.addressId}">

        <div class="mb-2 sm:mb-6">
          <label for="name" class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">이름</label>
          <input type="text" id="name" name="name"
                 class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5"
                 placeholder="이름" value="${address.name}" required>
        </div>

        <div class="mb-2 sm:mb-6">
          <label for="zipcode" class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">우편번호</label>
          <input type="text" id="zipcode" name="zipcode"
                 class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5"
                 placeholder="우편번호" value="${address.zipcode}" required>
        </div>

        <div class="mb-2 sm:mb-6">
          <label for="address" class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">주소</label>
          <input type="text" id="address" name="address"
                 class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5"
                 placeholder="주소" value="${address.address}" required>
        </div>

        <div class="mb-2 sm:mb-6">
          <label for="addressDetail" class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">상세주소</label>
          <input type="text" id="addressDetail" name="addressDetail"
                 class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5"
                 placeholder="상세주소" value="${address.addressDetail}" required>
        </div>

        <div class="mb-2 sm:mb-6">
          <label for="phoneNumber" class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">전화번호</label>
          <input type="text" id="phoneNumber" name="phoneNumber"
                 class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5"
                 placeholder="전화번호" value="${address.phoneNumber}" required>
        </div>

        <div class="flex justify-end">
          <button type="submit"
                  class="text-white bg-indigo-700 hover:bg-indigo-800 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
            저장
          </button>
        </div>
      </form>
    </div>
  </main>
</div>
</body>
</html>

