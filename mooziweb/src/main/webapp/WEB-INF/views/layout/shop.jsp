<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <title>nhn아카데미 shopping mall</title>

</head>
<body>

<div class="mainContainer">
    <header class="p-1 bg-white text-dark">
        <div class="mx-auto shadow py-1">
            <div class="flex flex-wrap items-center justify-center lg:justify-start">
                <a href="/" class="flex items-center mb-2 mb-lg-0 text-decoration-none text-dark">
                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                        <use xlink:href="#bootstrap"></use>
                    </svg>
                </a>

                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-center mb-md-0">
                    <li><a href="/index.do" class="nav-link px-2 text-secondary"><img
                            src="https://images-kr.amoremall.com/logo/ic_logo_amoreMall_s158x28_000.svg"
                            alt="AMORE MALL"></a></li>
                </ul>

                <a href="/searchAction.do">
                    <svg class="w-7 h-7" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                         viewBox="0 0 20 20">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                    </svg>
                    <span class="sr-only">Search</span>
                </a>

                <div class="text-end">
                    <div class="dropdown">
                        <button class="btn" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="data:image/png;base64,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"
                                 width="40px" height="40px">
                        </button>
                        <ul class="dropdown-menu">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user and sessionScope.user.userAuth eq 'ROLE_ADMIN'}">
                                            <li>
                                                <a href="/admin/index.do" class="dropdown-item">제품 처리 페이지</a>
                                            </li>
                                            <li>
                                                <a href="/admin/category_processing.do" class="dropdown-item">카테고리
                                                    처리 페이지</a>
                                            </li>
                                            <li>
                                                <a href="/admin/user_processing.do" class="dropdown-item">고객
                                                    처리
                                                    페이지</a>
                                            </li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li>
                                                <a class="dropdown-item" style="color: orangered" href="/logout.do">로그아웃</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a class="dropdown-item" href="/shoppingCart.do">장바구니</a>
                                            </li>
                                            <li>
                                                <a href="/mypage/index.do" class="dropdown-item">마이페이지</a>
                                            </li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li>
                                                <a class="dropdown-item" style="color: orangered" href="/logout.do">로그아웃</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a class="dropdown-item" href="/login.do">로그인</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="/signup.do">회원가입</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <main>
        <div class="album py-1 bg-light">
            <div class="container">
                <jsp:include page="${layout_content_holder}"/>
            </div>
        </div>
    </main>

    <footer class="bg-neutral-200 text-center text-neutral-600 dark:bg-neutral-600 dark:text-neutral-200 lg:text-left">
        <div
                class="flex items-center justify-center border-b-2 border-neutral-200 p-6 dark:border-neutral-500 lg:justify-between">
            <div class="mr-12 hidden lg:block">
                <span>Get connected with us on social networks:</span>
            </div>
            <div class="flex justify-center">
                <a href="#!" class="mr-6 text-neutral-600 dark:text-neutral-200">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-4 w-4"
                            fill="currentColor"
                            viewBox="0 0 24 24">
                        <path
                                d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z" />
                    </svg>
                </a>
                <a href="#!" class="mr-6 text-neutral-600 dark:text-neutral-200">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-4 w-4"
                            fill="currentColor"
                            viewBox="0 0 24 24">
                        <path
                                d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z" />
                    </svg>
                </a>
                <a href="#!" class="mr-6 text-neutral-600 dark:text-neutral-200">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-5 w-5"
                            fill="currentColor"
                            viewBox="0 0 24 24">
                        <path
                                d="M7 11v2.4h3.97c-.16 1.029-1.2 3.02-3.97 3.02-2.39 0-4.34-1.979-4.34-4.42 0-2.44 1.95-4.42 4.34-4.42 1.36 0 2.27.58 2.79 1.08l1.9-1.83c-1.22-1.14-2.8-1.83-4.69-1.83-3.87 0-7 3.13-7 7s3.13 7 7 7c4.04 0 6.721-2.84 6.721-6.84 0-.46-.051-.81-.111-1.16h-6.61zm0 0 17 2h-3v3h-2v-3h-3v-2h3v-3h2v3h3v2z"
                                fill-rule="evenodd"
                                clip-rule="evenodd" />
                    </svg>
                </a>
                <a href="#!" class="mr-6 text-neutral-600 dark:text-neutral-200">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-4 w-4"
                            fill="currentColor"
                            viewBox="0 0 24 24">
                        <path
                                d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zm0-2.163c-3.259 0-3.667.014-4.947.072-4.358.2-6.78 2.618-6.98 6.98-.059 1.281-.073 1.689-.073 4.948 0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98-1.281-.059-1.69-.073-4.949-.073zm0 5.838c-3.403 0-6.162 2.759-6.162 6.162s2.759 6.163 6.162 6.163 6.162-2.759 6.162-6.163c0-3.403-2.759-6.162-6.162-6.162zm0 10.162c-2.209 0-4-1.79-4-4 0-2.209 1.791-4 4-4s4 1.791 4 4c0 2.21-1.791 4-4 4zm6.406-11.845c-.796 0-1.441.645-1.441 1.44s.645 1.44 1.441 1.44c.795 0 1.439-.645 1.439-1.44s-.644-1.44-1.439-1.44z" />
                    </svg>
                </a>
                <a href="#!" class="mr-6 text-neutral-600 dark:text-neutral-200">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-4 w-4"
                            fill="currentColor"
                            viewBox="0 0 24 24">
                        <path
                                d="M4.98 3.5c0 1.381-1.11 2.5-2.48 2.5s-2.48-1.119-2.48-2.5c0-1.38 1.11-2.5 2.48-2.5s2.48 1.12 2.48 2.5zm.02 4.5h-5v16h5v-16zm7.982 0h-4.968v16h4.969v-8.399c0-4.67 6.029-5.052 6.029 0v8.399h4.988v-10.131c0-7.88-8.922-7.593-11.018-3.714v-2.155z" />
                    </svg>
                </a>
                <a href="#!" class="text-neutral-600 dark:text-neutral-200">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-4 w-4"
                            fill="currentColor"
                            viewBox="0 0 24 24">
                        <path
                                d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
                    </svg>
                </a>
            </div>
        </div>
        <div class="bg-neutral-300 p-6 text-center dark:bg-neutral-700">
            <p class="mb-1">shoppingmall example is © nhnacademy.com</p>
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
        </div>
    </footer>
</div>
</body>
</html>