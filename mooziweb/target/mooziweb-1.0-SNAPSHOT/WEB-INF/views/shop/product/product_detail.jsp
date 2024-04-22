<%--
  Created by IntelliJ IDEA.
  User: parksangwon
  Date: 24. 1. 18.
  Time: 오후 3:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>제품 상세 페이지</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;1,200;1,300;1,400;1,500;1,600;1,700;1,800&display=swap"
            rel="stylesheet">
    <style>
        body {
            font-family: "Plus Jakarta Sans", sans-serif;
        }
        #starRating {
            font-size: 24px; /* 별의 크기 조절 */
        }

        .star {
            cursor: pointer;
        }

        .star:hover,
        .star.active {
            color: gold; /* 클릭 또는 호버시 별의 색상 변경 */
        }
    </style>
</head>
<body class="bg-gray-100">

<div class="container mx-auto p-8 bg-white shadow-lg rounded-lg">
    <section class="py-12 sm:py-16">
        <div class="container px-4 mx-auto">
            <div class="grid grid-cols-1 gap-12 mt-8 lg:col-gap-12 xl:col-gap-16 lg:mt-12 lg:grid-cols-5 lg:gap-16">
                <div class="lg:col-span-3 lg:row-end-1">
                    <div class="">
                        <div class="lg:order-2 lg:ml-5 ">
                            <div class="max-w-2xl mx-auto overflow-hidden rounded-lg">
                                <img class="object-cover w-full h-full max-w-full"
                                     src="../resources/${product.productImage}"
                                     alt="${product.modelName}"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="lg:col-span-3 lg:row-span-2 lg:row-end-2">
                    <h1 class="font-bold leading-9 text-gray-900 sm:text-3xl">${product.modelName}</h1>

                    <p class="mt-2 text-sm font-medium text-gray-900 uppercase">by <span
                            class="font-semibold text-pink-500">A Company</span></p>

                    <div class="flex items-center mt-8">
                        <p class="p-4 mr-4 text-4xl font-bold text-pink-900 rounded-lg bg-pink-50">${product.unitCost}₩</p>
                        <p class="flex flex-col">
                            <span class="text-xl font-semibold text-green-500">Sale 10%</span>
                        </p>
                    </div>
                    <p class="mt-8 text-base text-gray-600">
                        ${product.description}
                    </p>
                    <div class="flex space-x-4 mt-8">
                        <div class="flex items-center">
                            <button id="decrementBtn2" type="button"
                                    class="bg-pink-300 hover:bg-pink-400 text-pink-800 font-bold py-2 px-4 rounded-l">
                                -
                            </button>
                            <input id="quantityInput2" type="text"
                                   class="text-center w-16 bg-pink-100 border-t border-b border-pink-300 py-2" value="1"
                                   name="quantity" readonly>
                            <button id="incrementBtn2" type="button"
                                    class="bg-pink-300 hover:bg-pink-400 text-pink-800 font-bold py-2 px-4 rounded-r">
                                +
                            </button>
                        </div>

                        <form id="buyNowForm2" action="/product/buyNow.do" method="post">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <input id="buyNowQuantity2" type="hidden" name="quantity" value="1">
                            <button type="submit"
                                    class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-md">
                                즉시 구매
                            </button>
                        </form>

                        <form id="addToCartForm2" action="/product/addToCart.do" method="post">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <input id="addToCartQuantity2" type="hidden" name="quantity" value="1">
                            <button type="submit"
                                    class="bg-pink-400 hover:bg-pink-700 text-white font-bold py-2 px-4 rounded-md">
                                장바구니 추가
                            </button>
                        </form>
                    </div>

                    <script>
                        const decrementBtn = document.getElementById('decrementBtn2');
                        const incrementBtn = document.getElementById('incrementBtn2');
                        const quantityInput = document.getElementById('quantityInput2');
                        const buyNowQuantityInput = document.getElementById('buyNowQuantity2');
                        const addToCartQuantityInput = document.getElementById('addToCartQuantity2');

                        decrementBtn.addEventListener('click', () => {
                            let quantity = parseInt(quantityInput.value, 10);
                            quantity = Math.max(1, quantity - 1);
                            quantityInput.value = quantity;
                            buyNowQuantityInput.value = quantity;
                            addToCartQuantityInput.value = quantity;
                        });

                        incrementBtn.addEventListener('click', () => {
                            let quantity = parseInt(quantityInput.value, 10);
                            quantity = quantity + 1;
                            quantityInput.value = quantity;
                            buyNowQuantityInput.value = quantity;
                            addToCartQuantityInput.value = quantity;
                        });
                    </script>
                </div>
            </div>
        </div>
    </section>

    <div class="mb-8">
        <div class="mb-4">
            <form action="/review/submitReview.do" method="post">
                <input type="hidden" name="productId" value="${product.productId}">
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        const stars = document.querySelectorAll('.star');
                        const ratingInput = document.getElementById('rating');

                        stars.forEach(star => {
                            star.addEventListener('click', () => {
                                const value = star.getAttribute('data-value');
                                ratingInput.value = value;

                                stars.forEach(s => s.classList.remove('active'));
                                for (let i = 0; i < value; i++) {
                                    stars[i].classList.add('active');
                                }
                            });
                        });
                    });
                </script>
                <div class="mb-4" id="starRating">
                    <div class="flex">
                        <input type="hidden" name="rating" id="rating" value="0">
                        <svg class="w-4 h-4 star text-gray-300 dark:text-gray-500"
                             aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20" data-value="1">
                            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                        </svg>
                        <svg class="w-4 h-4 star text-gray-300 dark:text-gray-500"
                             aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20" data-value="2">
                            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                        </svg>
                        <svg class="w-4 h-4 star text-gray-300 dark:text-gray-500"
                             aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20" data-value="3">
                            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                        </svg>
                        <svg class="w-4 h-4 star text-gray-300 dark:text-gray-500"
                             aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20" data-value="4">
                            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                        </svg>
                        <svg class="w-4 h-4 star text-gray-300 dark:text-gray-500"
                             aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20" data-value="5">
                            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                        </svg>
                    </div>
                </div>

                <div class="mb-4">
                    <textarea class="w-full px-3 py-2 border rounded-md appearance-none text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                              name="comments" id="comments" placeholder="리뷰를 작성해주세요"></textarea>
                </div>

                <button type="submit" class="bg-pink-500 hover:bg-pink-700 text-white font-bold py-2 px-4 rounded-md">
                    작성
                </button>
            </form>
        </div>

        <c:forEach var="review" items="${reviewList}">
            <article>
                <div class="flex items-center mb-4">
                    <img class="w-10 h-10 me-4 rounded-full" src="https://png.pngtree.com/png-clipart/20191122/original/pngtree-user-icon-isolated-on-abstract-background-png-image_5192004.jpg" alt="">
                    <div class="font-medium dark:text-white">
                        <p>${review.userId}</p>
                    </div>
                </div>
                <div class="flex items-center mb-1 space-x-1 rtl:space-x-reverse">
                    <svg class="w-4 h-4 ${review.rating >= 1 ? 'text-yellow-300' : 'text-gray-300 dark:text-gray-500'}"
                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
                        <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                    <svg class="w-4 h-4 ${review.rating >= 2 ? 'text-yellow-300' : 'text-gray-300 dark:text-gray-500'}"
                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
                        <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                    <svg class="w-4 h-4 ${review.rating >= 3 ? 'text-yellow-300' : 'text-gray-300 dark:text-gray-500'}"
                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
                        <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                    <svg class="w-4 h-4 ${review.rating >= 4 ? 'text-yellow-300' : 'text-gray-300 dark:text-gray-500'}"
                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
                        <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                    <svg class="w-4 h-4 ${review.rating >= 5 ? 'text-yellow-300' : 'text-gray-300 dark:text-gray-500'}"
                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
                        <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                    <h3 class="ms-2 text-sm font-semibold text-gray-900 dark:text-white">또 구매할 생각 있습니다!</h3>
                </div>
                <footer class="mb-5 text-sm text-gray-500 dark:text-gray-400">
                    <p>작성일: 한국
                        <time datetime="2024-01-23 19:00">1월 23일, 2024</time>
                    </p>
                </footer>
                <p class="mb-2 text-gray-500 dark:text-gray-400">${review.comments}</p>
            </article>
        </c:forEach>

        <div class="mt-4">
            <nav class="flex justify-center">
                <ul class="pagination">
                    <li class="page-item">
                        <a href="/product/detail.do?page=${currentPage - 1}&productId=${product.productId}" class="page-link text-dark" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>

                    <c:forEach var="i" begin="1" end="${pageCount}">
                        <li class="page-item">
                            <a href="/product/detail.do?page=${i}&productId=${product.productId}"
                               class="page-link text-dark ${currentPage eq i ? 'bg-pink-500 text-white' : 'hover:bg-pink-500 hover:text-white'}">${i}</a>
                        </li>
                    </c:forEach>

                    <li class="page-item">
                        <a href="/product/detail.do?page=${currentPage + 1}&productId=${product.productId}" class="page-link text-dark" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>

</body>
</html>