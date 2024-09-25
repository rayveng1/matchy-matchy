<%--
  Created by IntelliJ IDEA.
  User: Rayven
  Date: 9/17/2024
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
<!-- YOUR own local CSS -->
<link rel="stylesheet" href="/css/style.css"/>
<!-- For any Bootstrap that uses JS -->
<link href="/css/output.css" rel="stylesheet">
<script src="/webjars/bootstrap/js/bootstrap.min.js" defer></script>
<html>
<head>
    <title>Index</title>
</head>
<body onload="getCurrentLocation()">
<%--<div on="test()">test</div>--%>
<header class="px-2 py-2">
    <a href="https://ace.aaa.com" target="_blank"><img id="AAA_logo" src="/assets/AAA-logo.png" alt="AAA Logo"
                                                       style="max-width: 8rem; max-height: 8rem"></a>
<%--    <p>${jsObject}</p>--%>
</header>
<main class="d-flex">
    <div class="blue d-flex w-100" style="max-height: fit-content">
        <section class="container px-3 py-3" >
            <div class="flex-column mx-auto map-container">
                <h1 style="font-size: xx-large; color:white; width:fit-content; text-shadow: 2px 2px 1px black;">
                    Explore AAA Near You
                </h1>
                <c:if test="${latitude == null}">
                    <div style="margin: 8rem auto">
                        <img id="AAA_logo_middle" class="animate-pokePulse" src="/assets/acsc.svg" alt="AAA Logo"
                             style="max-width: 10rem; max-height: 8rem;" onclick="promptUserToChange()">
                        <p class="mx-auto text-warning fst-italic mt-3 hidden" style="width: fit-content">Please enable
                            your location sharing!</p>
                    </div>
                </c:if>
                <c:if test="${latitude != null}">
                    <div class="responsive-map" id="map" style="height: 400px"></div>

                </c:if>
            </div>
        </section>
        <c:if test="${mainPlace.googleMapsUrl != ' '}">
            <button class="btn btn-secondary btn-sm" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseWidthExample2" aria-expanded="false" aria-controls="collapseWidthExample2"
                    style="width:.1rem" onclick="toggleArrow(this)">
                <
            </button>
            <div style="min-height: 120px; max-height: 100%">
                <div class="collapse show collapse-horizontal" id="collapseWidthExample2" style="height: 100%">
                    <div class="card card-body" style="width: 300px; height: 100%; overflow-y:auto">
                        <div style="max-height:fit-content">
                            <c:if test="${mainPlace.imageGetRequest != ' '}">
                                <img src="${mainPlace.imageGetRequest}" alt="${mainPlace.placeName} logo!" style="object-fit: cover; width:100%; height: 35%; ">
                            </c:if>
                            <h3>${mainPlace.placeName}</h3>
                            <h5 style="font-weight: 450">Rating: ${mainPlace.rating}</h5>
                            <p>${mainPlace.summary}</p>
                            <h5 style="font-weight: 450">Address: ${mainPlace.address}</h5>
                            <h5 style="font-weight: 450">Phone: ${mainPlace.phone}</h5>
                            <h5 style="font-weight: 450">Website: <a href="">${mainPlace.websiteUrl}</a></h5>
                            <h5 style="height: fit-content; width: 100%; font-weight: 450">Google Maps: <a
                                    href="">${mainPlace.googleMapsUrl}</a>
                            </h5>
                            <button class="btn btn-sm btn-primary" onclick='panToMain(${mainPlace.lat}, ${mainPlace.lng})'>Show on Map</button>
                            <button class="btn btn-sm btn-secondary" onclick="resetMap(${latitude}, ${longitude})">Reset Map</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

    </div>
</main>
<%--<c:forEach var="item" items="${response.places}">--%>
<%--    <p class="lead">Name: ${item.displayName.text}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Latitude: ${item.location.latitude}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Longitude: ${item.location.longitude}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Rating: ${item.rating}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Number: ${item.internationalPhoneNumber}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Summary: ${item.editorialSummary.text}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">website URL: ${item.websiteUri}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Google Maps URL: ${item.googleMapsUri}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">hours: ${item.currentOpeningHours.weekdayDescriptions}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Types: ${item.types}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--    <p class="lead">Address: ${item.formattedAddress}</p>--%>
<%--    <p class="card-text" id="error"></p>--%>
<%--</c:forEach>--%>
<section>

    <div class="container p-5">
        <div class="mx-auto mb-5" style="width:fit-content">
            <h3>Categories</h3>
            <c:if test="${places.size() == 1}">
                <p class="mb-4 mx-auto" style="width:fit-content; color: dimgray">1 Place Found!</p>
            </c:if>
            <c:if test="${places.size() != 1}">
                <p class="mb-4 mx-auto" style="width:fit-content; color: dimgray">${places.size()} Places Found!</p>
            </c:if>
        </div>
        <c:set var="counter" value="0"/>
        <div class="accordion" id="accordionExample">

            <c:forEach var="category" items="${categories}">
            <div class="accordion-item">
                <h2 class="accordion-header">

                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapse${counter}" aria-expanded="true" aria-controls="collapse${counter}"
                            style="background-color:var(--aaa-red); color:white">
                            ${category.key}
                    </button>
                </h2>
                <c:forEach var="place" items="${category.value}">
                    <c:set var="summary" value=" "/>
                    <c:set var="rating" value=" "/>
                    <c:set var="summary" value=" "/>
                    <c:set var="address" value=" "/>
                    <c:set var="phone" value=" "/>
                    <c:set var="websiteUrl" value=" "/>
                    <c:set var="googleMapsUrl" value=" "/>
                    <c:set var="imageGetRequest" value=" "/>
                    <div id="collapse${counter}" class="accordion-collapse collapse show"
                         data-bs-parent="#accordionExample"
                         style="background-color:var(--aaa-blue); color:white">

                        <c:if test="${place.editorialSummary.text==' . '}">
                            <c:set var="summary" value="."/>
                        </c:if>
                        <c:if test="${place.editorialSummary.text != ' . '}">
                            <c:set var="summary" value="${place.editorialSummary.text}"/>
                        </c:if>
                        <c:if test="${place.rating ==null}">
                            <c:set var="rating" value="."/>
                        </c:if>
                        <c:if test="${place.rating != null}">
                            <c:set var="rating" value="${place.rating}"/>
                        </c:if>
                        <c:if test="${place.formattedAddress==' . '}">
                            <c:set var="address" value="."/>
                        </c:if>
                        <c:if test="${place.formattedAddress != ' . '}">
                            <c:set var="address" value="${place.formattedAddress}"/>
                        </c:if>
                        <c:if test="${place.internationalPhoneNumber==' . '}">
                            <c:set var="phone" value="."/>
                        </c:if>
                        <c:if test="${place.internationalPhoneNumber != ' . '}">
                            <c:set var="phone" value="${place.internationalPhoneNumber}"/>
                        </c:if>
                        <c:if test="${place.websiteUri=='.'}">
                            <c:set var="websiteUrl" value="."/>
                        </c:if>
                        <c:if test="${place.websiteUri != '.'}">
                            <c:set var="websiteUrl" value="${place.websiteUri}"/>
                        </c:if>
                        <c:if test="${place.imageGetRequest=='.'}">
                            <c:set var="imageGetRequest" value="."/>
                        </c:if>
                        <c:if test="${place.imageGetRequest != '.'}">
                            <c:set var="imageGetRequest" value="${place.imageGetRequest}"/>
                        </c:if>
                        <form:form action="/" id="${place.googleMapsUri}" method="get" modelAttribute="mainPlace" cssClass="mb-0">
                            <form:hidden path="placeName" value="${place.displayName.text}"/>
                            <form:hidden path="rating" value="${place.rating}"/>
                            <c:if test="${summary != '.'}">
                                <form:hidden path="summary" value="${summary}"/>
                            </c:if>
                            <c:if test="${address != '.'}">
                                <form:hidden path="address" value="${place.formattedAddress}"/>
                            </c:if>
                            <c:if test="${phone != '.'}">
                                <form:hidden path="phone" value="${place.internationalPhoneNumber}"/>
                            </c:if>
                            <c:if test="${websiteUrl != '.'}">
                                <form:hidden path="websiteUrl" value="${place.websiteUri}"/>
                            </c:if>
                            <c:if test="${googleMapsUrl != '.'}">
                                <form:hidden path="googleMapsUrl" value="${place.googleMapsUri}"/>
                            </c:if>
                            <c:if test="${imageGetRequest != '.'}">
                                <form:hidden path="imageGetRequest" value ="${place.imageGetRequest}"/>
                            </c:if>
                            <form:hidden path="lat" value = "${place.location.latitude}"/>
                            <form:hidden path="lng" value = "${place.location.longitude}"/>
                            <div class="accordion-body p-0" onclick="">
                                <button type="submit" class="bg-transparent w-100 p-3 d-flex justify-content-between border-0 text-white">
                                    <strong>${place.displayName.text}</strong>
                                    <span class="fw-light">${place.distance} miles away</span>
                                </button>

                            </div>
                        </form:form>
                    </div>
                </c:forEach>
                <c:set var="counter" value="${counter + 1}"/>
            </div>

            </c:forEach>

</section>
<script src="/javascript/script.js" ></script>
</body>
</html>