<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>검색 페이지</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body>
<div class="flex flex-col items-center h-screen shadow-md">
    <div class="w-full max-w-full">
        <div class="rounded-md p-4">
            <form onsubmit="handleSearch(event)">
                <input type="text" name="keyword" id="simple-search"
                       class="w-full border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                       placeholder="검색어를 입력하세요" required>
            </form>
        </div>

        <div class="p-4">
            <h2 class="text-xl font-bold text-gray-800">최근 검색 기록</h2>
            <ul class="mt-2 search-history" onclick="handleHistoryClick(event)"></ul>
        </div>
    </div>

    <script>
        let searchHistory = [];

        function addSearchHistory(item) {
            // 중복된 검색어 확인 및 제거
            const index = searchHistory.indexOf(item);
            if (index !== -1) {
                searchHistory.splice(index, 1);
            }

            // 맨 위로 추가
            searchHistory.unshift(item);

            localStorage.setItem("searchHistory", JSON.stringify(searchHistory));
            renderHistory();
        }

        function clearHistory() {
            searchHistory = [];
            localStorage.removeItem("searchHistory");
            renderHistory();
        }

        function deleteHistoryItem(item) {
            const index = searchHistory.indexOf(item);
            searchHistory.splice(index, 1);
            localStorage.setItem("searchHistory", JSON.stringify(searchHistory));
            renderHistory();
        }

        function renderHistory() {
            const ul = document.querySelector(".search-history");
            ul.innerHTML = "";

            for (const item of searchHistory) {
                const li = document.createElement("li");
                li.classList.add("flex", "items-center", "justify-between", "py-2", "px-4", "border-b", "border-gray-300", "hover:bg-gray-200");

                const span = document.createElement("span");
                span.textContent = item;
                span.classList.add("text-gray-900", "w-full");

                const button = document.createElement("button");
                button.classList.add("text-gray-500", "hover:text-gray-700");
                button.textContent = "X";
                button.addEventListener("click", () => deleteHistoryItem(item));

                li.appendChild(span);
                li.appendChild(button);

                ul.appendChild(li);
            }
        }

        function handleSearch(event) {
            event.preventDefault(); // 기본 제출 동작 방지
            const keywordInput = document.getElementById('simple-search');
            const keyword = keywordInput.value.trim();

            if (keyword !== "") {
                addSearchHistory(keyword);
                window.location.href = "/search.do?keyword=" + encodeURIComponent(keyword);
            }
        }

        function handleHistoryClick(event) {
            if (event.target.tagName === 'SPAN') {
                const keyword = event.target.textContent;
                window.location.href = "/search.do?keyword=" + encodeURIComponent(keyword);
            }
        }

        window.addEventListener("load", () => {
            const history = localStorage.getItem("searchHistory");
            if (history) {
                searchHistory = JSON.parse(history);
            }

            renderHistory();
        });
    </script>
</div>
</body>
</html>

