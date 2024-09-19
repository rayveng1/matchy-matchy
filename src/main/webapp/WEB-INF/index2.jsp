<%--
  Created by IntelliJ IDEA.
  User: Rayven
  Date: 9/17/2024
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
<!-- YOUR own local CSS -->
<link rel="stylesheet" href="/css/style.css"/>
<!-- For any Bootstrap that uses JS -->
<link href="/css/output.css" rel="stylesheet">
<script src="/webjars/bootstrap/js/bootstrap.min.js" defer></script>
<script src="/javascript/script.js" defer></script>
<html>
<head>
    <title>Index</title>
</head>
<script>

</script>
<body onload="getCurrentLocation()">
<header class="px-2 py-2">
    <a href="https://ace.aaa.com" target="_blank"><img id="AAA_logo" src="/assets/AAA-logo.png" alt="AAA Logo"
                                                       style="max-width: 8rem; max-height: 8rem"></a>
</header>
<main class="d-flex">
    <div class="blue d-flex w-100" style="max-height: fit-content">
        <section class="container px-3 py-3">
            <div class="flex-column mx-auto" style="width:fit-content">
                <h1 style="font-size: xx-large; color:white; width:fit-content; text-shadow: 2px 2px 1px black;">Explore AAA Near You</h1>
                <c:if test="${latitude == null}">
                    <div style="margin: 8rem auto">

                    <img id="AAA_logo_middle" class="animate-pokePulse" src="/assets/acsc.svg" alt="AAA Logo"
                         style="max-width: 10rem; max-height: 8rem;" onclick="promptUserToChange()">
                        <p class="mx-auto text-warning fst-italic mt-3 hidden" style="width: fit-content">Please enable your location sharing!</p>
                    </div>
                </c:if>
                <c:if test="${latitude != null}">
                <img class="mb-5" src="/assets/map.png" alt="Map Logo"
                     style="max-width: 100%; max-height: 100%;  border-radius:1%">
                </c:if>
            </div>
        </section>
        <c:if test="${latitude != null}">

<button class="btn btn-secondary btn-sm" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample"
                style="width:.1rem" onclick="toggleArrow(this)">
            <
        </button>
</c:if>

        <div style="min-height: 120px; max-height: 100%">
            <div class="collapse collapse-horizontal" id="collapseWidthExample" style="height: 100%">
                <div class="card card-body" style="width: 300px; height: 100%">
                    <div style="height:fit-content">
                        <img src="/assets/canes.png" alt="Canes logo!" style="object-fit: contain; width:100%">
                        <h3>Raising Canes</h3>
                        <h5 style="font-weight: 450">Rating: 4.6</h5>
                        <p>Fast-food chain specializing in fried chicken fingers, crinkle-cut fries & Texas toast.</p>
                        <h5 style="font-weight: 450">Address: 7345 Gaston Ave, Dallas, TX, 75214</h5>
                        <h5 style="font-weight: 450">Phone: (214) 321-3220</h5>
                        <h5 style="font-weight: 450">Website: <a href="">raisingcanes.com</a></h5>
                        <h5 style="height: fit-content; width: 100%; font-weight: 450">Google Maps: <a href="">https://maps.google.com/?cid=7851923780944608967</a>
                        </h5>
                    </div>
                </div>
            </div>
        </div>

    </div>
</main>
<c:forEach var="item" items="${response.places}">
    <p class="lead">Name: ${item.displayName.text}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Latitude: ${item.location.latitude}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Longitude: ${item.location.longitude}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Rating: ${item.rating}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Number: ${item.internationalPhoneNumber}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Summary: ${item.editorialSummary.text}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">website URL: ${item.websiteUri}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Google Maps URL: ${item.googleMapsUri}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">hours: ${item.currentOpeningHours.weekdayDescriptions}</p>
    <p class="card-text" id="error"></p>
    <p class="lead">Types: ${item.types}</p>
    <p class="card-text" id="error"></p>
</c:forEach>
<section>
    <div class="container p-5">
        <div class="mx-auto mb-5" style="width:fit-content">
            <h3>Categories</h3>
            <p class="mb-4 mx-auto" style="width:fit-content; color: dimgray">11 Places Found!</p>
        </div>
        <div class="accordion" id="accordionExample">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne"
                            style="background-color:var(--aaa-red); color:white">
                        Car
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample"
                     style="background-color:var(--aaa-blue); color:white">
                    <div class="accordion-body">
                        <strong class="text-start">Bob the Mechanic</strong>
                        <span class="text-end">2.4 miles away</span>
                    </div>
                    <div id="collapseTwo" class="accordion-collapse collapse show" data-bs-parent="#accordionExample"
                         style="background-color:var(--aaa-blue); color:white">
                        <div class="accordion-body">
                            <strong class="text-start">AAA Locksmith</strong>
                            <span class="text-end">1.2 miles away</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapseThree" aria-expanded="true" aria-controls="collapseOne"
                            style="background-color:var(--aaa-red); color:white">
                        Travel
                    </button>
                </h2>
                <div id="collapseThree" class="accordion-collapse collapse show" data-bs-parent="#accordionExample"
                     style="background-color:var(--aaa-blue); color:white">
                    <div class="accordion-body">
                        <strong class="text-start">AAA Travel Agent</strong>
                        <span class="text-end">8 miles away</span>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapseFour" aria-expanded="true" aria-controls="collapseOne"
                            style="background-color:var(--aaa-red); color:white">
                        Finance
                    </button>
                </h2>
            </div>
        </div>
    </div>
</section>
</body>
</html>
