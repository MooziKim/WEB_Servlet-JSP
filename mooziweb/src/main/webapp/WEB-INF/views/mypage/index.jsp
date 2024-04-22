
<%@ page contentType="text/html;charset=UTF-8" session="true" language="java" %>
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
    <title>마이페이지</title>
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
        <div class="p-2 md:p-4">
            <div class="w-full px-6 pb-8 mt-8 sm:max-w-xl sm:rounded-lg">
                <h2 class="pl-6 text-2xl font-bold sm:text-xl">회원 정보 수정</h2>

                <div class="grid max-w-2xl mx-auto mt-8">
                    <div class="items-center mt-8 sm:mt-14 text-[#202142]">
                        <form method="post" action="/mypage/editUserAction.do">
                            <div class="flex flex-col items-center w-full mb-2 space-x-0 space-y-2 sm:flex-row sm:space-x-4 sm:space-y-0 sm:mb-6">
                                <div class="w-full">
                                    <label for="name"
                                           class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">이름</label>
                                    <input type="text" id="name" name="userName"
                                           class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 "
                                           value="${sessionScope.user.userName}" required>
                                </div>

                            </div>

                            <div class="mb-2 sm:mb-6">
                                <label for="birth"
                                       class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">생년월일</label>
                                <input type="birth" id="birth" name="userBirth"
                                       class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 "
                                       value="${sessionScope.user.userBirth}" required>
                            </div>

                            <div class="flex justify-end">
                                <button type="submit"
                                        class="text-white bg-indigo-700  hover:bg-indigo-800 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">수정</button>
                            </div>
                        </form>
                        <a href="/mypage/deleteUser.do" class="text-white bg-red-500  hover:bg-indigo-800 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
                            회원 탈퇴
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
