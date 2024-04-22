<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
</head>
<body class="bg-gray-100 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded shadow-md w-96">
    <h1 class="text-2xl font-bold mb-4">Error Details</h1>
    <table class="w-full">
        <tbody>
        <tr>
            <th class="py-2">Status Code</th>
            <td class="py-2">${requestScope.get("status_code")}</td>
        </tr>
        <tr>
            <th class="py-2">Exception Type</th>
            <td class="py-2">${requestScope.get("exception_type")}</td>
        </tr>
        <tr>
            <th class="py-2">Message</th>
            <td class="py-2">${requestScope.get("message")}</td>
        </tr>
        <tr>
            <th class="py-2">Exception</th>
            <td class="py-2">${requestScope.get("exception")}</td>
        </tr>
        <tr>
            <th class="py-2">Request URI</th>
            <td class="py-2">${requestScope.get("request_uri")}</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
