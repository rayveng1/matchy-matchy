<%--
  Created by IntelliJ IDEA.
  User: Rayven
  Date: 9/17/2024
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<!-- YOUR own local CSS -->
<link rel="stylesheet" href="/css/style.css"/>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<html>
<head>
    <title>Index</title>
</head>
<body>
<header class="px-2 py-4">
    <img src="/assets/AAA-logo.png" alt="AAA Logo" style="max-width: 8rem; max-height: 8rem">
</header>
<main class="px-5 py-2">
    <section class="container mx-auto">
        <h1 style="font-size: xx-large; color:white;">Explore <span style="color:var(--aaa-red)">AAA</span> Near You</h1>
        <img class="mb-5" src="/assets/map.png" alt="Map Logo" style="max-width: 60rem; max-height: 45rem">
    </section>
</main>
<section>
    <div class="container p-5">
        <div class="mx-auto mb-5" style="width:fit-content">
            <h3>Categories</h3>
            <p class="mb-4 mx-auto" style="width:fit-content; color: dimgray">11 Places Found!</p>
        </div>
        <div class="accordion" id="accordionExample">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne" style="background-color:var(--aaa-red); color:white">
                        Car
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample" style="background-color:var(--aaa-blue); color:white">
                    <div class="accordion-body">
                        <strong class="text-start">Bob the Mechanic</strong>
                        <span class="text-end">2.4 miles away</span>
                    </div>
                    <div id="collapseTwo" class="accordion-collapse collapse show" data-bs-parent="#accordionExample" style="background-color:var(--aaa-blue); color:white">
                        <div class="accordion-body">
                            <strong class="text-start">AAA Locksmith</strong>
                            <span class="text-end">1.2 miles away</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="true" aria-controls="collapseOne" style="background-color:var(--aaa-red); color:white">
                        Travel
                    </button>
                </h2>
                <div id="collapseThree" class="accordion-collapse collapse show" data-bs-parent="#accordionExample" style="background-color:var(--aaa-blue); color:white">
                        <div class="accordion-body">
                            <strong class="text-start">AAA Travel Agent</strong>
                            <span class="text-end">8 miles away</span>
                        </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="true" aria-controls="collapseOne" style="background-color:var(--aaa-red); color:white">
                        Finance
                    </button>
                </h2>
            </div>
        </div>
    </div>
</section>
</body>
</html>
