<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>장바구니</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<style>
    @layer utilities {
        input[type="number"]::-webkit-inner-spin-button,
        input[type="number"]::-webkit-outer-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
    }
</style>
<body class="bg-gray-100">
<div class="h-screen pt-20">
    <h1 class="mb-10 text-center text-2xl font-bold">장바구니 목록</h1>
    <div class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
        <div class="rounded-lg md:w-2/3">
            <c:forEach var="item" items="${shoppingCartList}">
                <div class="justify-between mb-6 rounded-lg bg-white p-6 shadow-md sm:flex sm:justify-start">
                    <img src="/resources/${item.productImage}" alt="${item.modelName}"
                         class="w-full rounded-lg sm:w-40"/>
                    <div class="sm:ml-4 sm:flex sm:w-full sm:justify-between">
                        <div class="mt-5 sm:mt-0">
                            <h2 class="text-lg font-bold text-gray-900">"${item.modelName}"</h2>
                            <p class="mt-1 text-xs text-gray-700">화장 용품</p>
                        </div>
                        <div class="mt-4 flex justify-between sm:space-y-6 sm:mt-0 sm:block sm:space-x-6">
                            <div class="flex items-center border-gray-100">
                                <form method="post" action="/update_quantity.do">
                                    <input type="hidden" name="recordId" value="${item.recordId}">
                                    <input type="hidden" name="quantity" value="${item.quantity - 1}">
                                    <input type="hidden" name="maxQuantity" value="${item.maxQuantity}">
                                    <button type="submit" class="text-blue-500 p-1 hover:underline"><span
                                            class="cursor-pointer rounded-l bg-gray-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50"> - </span>
                                    </button>
                                </form>
                                <input id="quantity_${item.recordId}" type="number" min="1" max="${item.maxQuantity}"
                                       class="h-8 w-8 border bg-white text-center text-xs outline-none"
                                       value="${item.quantity}" readonly>
                                <form method="post" action="/update_quantity.do">
                                    <input type="hidden" name="recordId" value="${item.recordId}">
                                    <input type="hidden" name="quantity" value="${item.quantity + 1}">
                                    <input type="hidden" name="maxQuantity" value="${item.maxQuantity}">
                                    <button type="submit" class="text-blue-500 p-1 hover:underline"><span
                                            class="cursor-pointer rounded-r bg-gray-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50"> + </span>
                                    </button>
                                </form>
                            </div>
                            <div class="flex items-center space-x-4">
                                <p class="text-sm">${item.quantity * item.unitCost}원</p>
                                <form action="/removeFromCart.do" method="post">
                                    <input type="hidden" name="recordId" value="${item.recordId}">
                                    <button type="submit" class="text-red-500 p-1 hover:underline">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                             stroke-width="1.5" stroke="currentColor"
                                             class="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                  d="M6 18L18 6M6 6l12 12"/>
                                        </svg>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="mt-6 h-full rounded-lg border bg-white p-6 shadow-md md:mt-0 md:w-1/3">
            <div class="mb-2 flex justify-between">
                <p class="text-gray-700">소계</p>
                <div id="subTotal">
                </div>
            </div>
            <div class="flex justify-between">
                <p class="text-gray-700">배송비</p>
                <p class="text-gray-700">0 ₩</p>
            </div>
            <hr class="my-4"/>
            <div class="flex justify-between">
                <p class="text-lg font-bold">총액</p>
                <div id="totalAmount">
                </div>
            </div>
            <div class="mt-6">
                <h2 class="text-xl font-bold mb-2">주소 선택</h2>
                <div class="grid grid-cols-1 gap-1">
                    <c:forEach var="address" items="${userAddressList}">
                        <div class="flex items-center mb-2 cursor-pointer address-item rounded-md p-2" data-address-id="${address.addressId}">
                            <div>
                                <p class="text-sm font-semibold">${address.name}</p>
                                <p class="text-xs">${address.address}, ${address.addressDetail}</p>
                                <p class="text-xs">${address.phoneNumber}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <style>
                .address-item:hover {
                    background-color: #edf2f7;
                }

                .address-item.selected {
                    background-color: #edf2f7;
                }
            </style>

            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    var addressItems = document.querySelectorAll('.address-item');

                    addressItems[0].classList.add('selected');

                    addressItems.forEach(function (item) {
                        item.addEventListener('click', function () {
                            addressItems.forEach(function (otherItem) {
                                otherItem.classList.remove('selected');
                            });
                            item.classList.add('selected');
                        });
                    });
                });
            </script>

            <form method="get" action="/payment.do">
                <button class="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">
                    결제하기
                </button>
            </form>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var itemPrices = [];

                <c:forEach var="item" items="${shoppingCartList}">
                itemPrices.push(${item.quantity * item.unitCost});
                </c:forEach>

                var totalPrice = itemPrices.reduce(function (acc, curr) {
                    return acc + curr;
                }, 0);

                document.getElementById("subTotal").innerHTML = '<p class="text-gray-700">' + totalPrice + ' ₩</p>';
                document.getElementById("totalAmount").innerHTML = '<p class="mb-1 text-lg font-bold">' + totalPrice + ' ₩</p>';
            });
        </script>
    </div>
</div>
</body>
</html>
