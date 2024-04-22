
<%@ page contentType="text/html;charset=UTF-8" session="true" language="java" %>
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
    <title>포인트 사용내역 조회</title>
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
            <div class="grid grid-cols-1 gap-4">
                <c:forEach var="order" items="${orderList}">
                    <div class="bg-white border border-gray-300 p-4 rounded-md">
                        <div class="mb-2 font-bold">제품 아이디: ${order.productId}</div>
                        <div class="mb-2">수량: ${order.quantity}</div>
                        <div class="mb-2">단위 가격: ${order.unitCost} 원</div>
                        <div>
                            <a href="/mypage/orderDetail.do?orderId=${order.orderId}" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-3 rounded">상세보기</a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="mt-4">
                <nav class="flex justify-center">
                    <ul class="pagination">
                        <li class="page-item">
                            <a href="/mypage/order.do?page=${currentPage - 1}" class="page-link" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach var="i" begin="1" end="${pageCount}">
                            <li class="page-item">
                                <a href="/mypage/order.do?page=${i}"
                                   class="page-link ${currentPage eq i ? 'bg-blue-500 text-white' : 'hover:bg-blue-500 hover:text-white'}">${i}</a>
                            </li>
                        </c:forEach>

                        <li class="page-item">
                            <a href="/mypage/order.do?page=${currentPage + 1}" class="page-link" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </main>
</div>
</body>
</html>
