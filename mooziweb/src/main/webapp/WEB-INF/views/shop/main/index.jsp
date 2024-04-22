<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>nhn 아카데미 shopping mall</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
    <style>
        .slide-content {
            position: relative;
        }

        .slide-content p {
            position: absolute;
            top: 90%;
            left: 20%;
            transform: translate(-50%, -50%);
            color: white;
            font-size: 24px;
            font-weight: bold;
            text-align: center;
        }
    </style>
</head>
<body class="bg-gray-100">

<c:if test="${not empty categoryList}">
    <div class="mb-1 bg-white p-1 text-center overflow-x-auto">
        <form method="get" action="/index.do">
            <div class="flex space-x-4">
                <c:forEach var="category" items="${categoryList}">
                    <button type="submit"
                            class="text-dark font-bold py-2 px-4 rounded-full whitespace-nowrap ${categoryId eq category.categoryId ? 'bg-gray-200 text-gray-900' : 'hover:bg-gray-200 hover:text-gray-900'} focus:outline-none"
                            name="categoryId" value="${category.categoryId}">
                            ${category.categoryName}
                    </button>
                </c:forEach>
            </div>
        </form>
    </div>
</c:if>

<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<div class="mb-6 bg-white p-4">
    <div class="swiper-container" style="width: 750px; height: 100%">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <a href="https://www.amoremall.com/kr/ko/display/event_detail?planDisplaySn=10410">
                    <div class="slide-content">
                        <p>지금부터 설 선물 준비</p>
                        <img src="https://images-kr.amoremall.com/fileupload/mainContents/2024/01/19/01_Mainbanner_750x570_10410_2401_4w22.jpg"
                             alt="Slide 1">
                    </div>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="https://www.amoremall.com/kr/ko/display/event_detail?planDisplaySn=10369">
                    <div class="slide-content">
                        <p>정성 가득 설화수 지함보포</p>
                        <img src="https://images-kr.amoremall.com/fileupload/mainContents/2024/01/19/01_Mainbanner_750x570_10369_2401_4w.jpg"
                             alt="Slide 2">
                    </div>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="https://www.amoremall.com/kr/ko/display/event_detail?planDisplaySn=10406">
                    <div class="slide-content">
                        <p>값진 분을 위한 한율 세트</p>
                        <img src="https://images-kr.amoremall.com/fileupload/mainContents/2024/01/19/01_Mainbanner_750x570_10406_2401_4w.jpg"
                             alt="Slide 3">
                    </div>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="https://www.amoremall.com/kr/ko/display/event_detail?planDisplaySn=10404">
                    <div class="slide-content">
                        <p>온가족 보습템, 아토베리어</p>
                        <img src="https://images-kr.amoremall.com/fileupload/mainContents/2024/01/22/01_Mainbanner_750x570_10404_2401_4w.jpg"
                             alt="Slide 4">
                    </div>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="https://www.amoremall.com/kr/ko/display/event_detail?planDisplaySn=10411">
                    <div class="slide-content">
                        <p>아모레몰 선물하기 런칭</p>
                        <img src="https://images-kr.amoremall.com/fileupload/mainContents/2024/01/22/01_Mainbanner_750x570_10411_2401_4w_v1.jpg"
                             alt="Slide 5">
                    </div>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="https://www.amoremall.com/kr/ko/display/event_detail?planDisplaySn=10266">
                    <div class="slide-content">
                        <p>헤라, 스킨케어의 처음과 끝</p>
                        <img src="https://images-kr.amoremall.com/fileupload/mainContents/2024/01/19/01_Mainbanner_750x570_10266_2401_4w.jpg"
                             alt="Slide 6">
                    </div>
                </a>
            </div>
        </div>
        <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div>
    </div>

    <script>
        var swiper = new Swiper('.swiper-container', {
            slidesPerView: 1,
            centeredSlides: true,
            spaceBetween: 6,
            loop: true,
            loopAdditionalSlides: 2,
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            autoplay: {
                delay: 5000,
            },
        });
    </script>
</div>

<c:if test="${not empty productQueue}">
    <div class="mb-6 bg-white p-4">
        <h2 class="text-xl font-bold mb-2">최근에 본 상품들</h2>
        <div class="flex space-x-4">
            <c:forEach var="recentProduct" items="${productQueue}">
                <div class="w-72 bg-white shadow-md rounded-xl duration-500 hover:scale-105 hover:shadow-xl">
                    <a href="/product/detail.do?productId=${recentProduct.productId}">
                        <img src="resources/${recentProduct.productImage}"
                             alt="${recentProduct.modelName}" class="h-80 w-72 object-cover rounded-t-xl"/>
                        <div class="px-4 py-3 w-72">
                            <span class="text-gray-400 mr-3 uppercase text-xs">Brand</span>
                            <p class="text-lg font-bold text-black truncate block capitalize">${recentProduct.modelName}</p>
                            <div class="flex items-center">
                                <p class="text-lg font-semibold text-black cursor-auto my-3">${recentProduct.unitCost}
                                    ₩</p>
                                <del>
                                    <p class="text-sm text-gray-600 cursor-auto ml-2">${recentProduct.unitCost * 2}
                                        ₩</p>
                                </del>
                                <div class="ml-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                         fill="currentColor" class="bi bi-bag-plus" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd"
                                              d="M8 7.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V12a.5.5 0 0 1-1 0v-1.5H6a.5.5 0 0 1 0-1h1.5V8a.5.5 0 0 1 .5-.5z"/>
                                        <path
                                                d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5z"/>
                                    </svg>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>

<div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
    <c:forEach var="product" items="${productList}" varStatus="loop">
        <div class="bg-white rounded-lg overflow-hidden shadow-md duration-500 hover:scale-105 hover:shadow-xl relative">
            <a href="/product/detail.do?productId=${product.productId}">
                <div class="absolute bg-gray-800 text-white px-2 py-1 rounded-tl-md rounded-br-md"><p class="text-lg font-semibold text-white cursor-auto">0${loop.index + 1}</p></div>
                <img class="w-full h-56 object-cover object-center" src="resources/${product.productImage}" alt="${product.modelName}">
                <div class="p-6">
                    <div class="font-bold text-xl mb-2">${product.modelName}</div>
                    <div class="flex items-center">
                        <p class="text-lg font-semibold text-black cursor-auto my-3">${product.unitCost} ₩</p>
                        <del>
                            <p class="text-sm text-gray-600 cursor-auto ml-2">${product.unitCost * 2} ₩</p>
                        </del>
                        <div class="ml-auto">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-bag-plus" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M8 7.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V12a.5.5 0 0 1-1 0v-1.5H6a.5.5 0 0 1 0-1h1.5V8a.5.5 0 0 1 .5-.5z"/>
                                <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1zm3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4h-3.5zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5z"/>
                            </svg>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>
</div>

<div class="mt-4">
    <nav class="flex justify-center">
        <ul class="pagination">
            <li class="page-item">
                <a href="/index.do?page=${currentPage - 1}&categoryId=${categoryId}" class="page-link text-dark"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach var="i" begin="1" end="${pageCount}">
                <li class="page-item">
                    <a href="/index.do?page=${i}&categoryId=${categoryId}"
                       class="page-link text-black ${currentPage eq i ? 'bg-gray-400 text-white' : 'hover:bg-gray-400 hover:text-white'}">${i}</a>
                </li>
            </c:forEach>

            <li class="page-item">
                <a href="/index.do?page=${currentPage + 1}&categoryId=${categoryId}" class="page-link text-dark"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>

</body>
</html>
