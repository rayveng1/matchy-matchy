function toggleArrow(element) {
    console.log(element.innerHTML)
    element.innerHTML = (element.innerHTML.trim() === "&lt;") ? "&gt;" : "&lt;"
}

const successCallback = (position) => {
    sendLocationToServer(position.coords.latitude, position.coords.longitude);
    localStorage.setItem("locationCookie", "true");
};

const errorCallback = (error) => {
    localStorage.setItem("locationCookie", "false")
    sendLocationToServer(null, null)
    console.log(error);
};

async function getCurrentLocation() {

    // If the locationCookie isn't set yet.
    if (!localStorage.getItem("locationCookie")) {
        const positionPromise = new Promise((resolve, reject) => {
            navigator.geolocation.getCurrentPosition(resolve, reject);
        });

        try {
            const position = await positionPromise;
            successCallback(position); // Call the success callback with the position
        } catch (error) {
            errorCallback(error); // Call the error callback with the error
        }

        // User accepted to share location
        console.log("bye - " + localStorage.getItem("locationCookie"));
        if (localStorage.getItem("locationCookie") === "true") {
            console.log("hello");
            document.getElementById("AAA_logo_middle").classList.add("animate-spin")
            window.location.reload();
        }
    } else {// If the locationCookie is set.
        if (localStorage.getItem("locationCookie") === "true"){
            const positionPromise = new Promise((resolve, reject) => {
                navigator.geolocation.getCurrentPosition(resolve, reject);
            });

            try {
                const position = await positionPromise;
                successCallback(position); // Call the success callback with the position
            } catch (error) {
                errorCallback(error); // Call the error callback with the error
            }

            if (localStorage.getItem("locationCookie") === "false"){
                window.location.reload();
            }
        } else {
            const positionPromise = new Promise((resolve, reject) => {
                navigator.geolocation.getCurrentPosition(resolve, reject);
            });

            try {
                const position = await positionPromise;
                successCallback(position); // Call the success callback with the position
            } catch (error) {
                errorCallback(error); // Call the error callback with the error
            }

            if (localStorage.getItem("locationCookie") === "true"){
                window.location.reload();
            }
        }
    }await initMap();



}


function sendLocationToServer(lat, lon) {
    console.log(`lat: ${lat}`);
    console.log(`lon: ${lon}`);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/savelocation", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Mark that location has been fetched and prevent reload loop
        }
    };

    var data = JSON.stringify({ Latitude: lat, Longitude: lon });
    xhr.send(data);
}

// Call this function on page load or based on user action
getCurrentLocation();

function promptUserToChange(){
    let el = document.getElementsByClassName("hidden");
    if (el !== null){
        el[0].classList.remove("hidden");
        // el[0].classList.add("unhidden")
    }
}


(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})
({key: "AIzaSyCMsvx7BJpL7LnTmla3mgcFZF78s7TUm7g", v: "weekly"});



let map;

async function getUserLocation() {
    return new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
    });
}

async function initMap() {    console.log("hello!")

    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement, PinElement } = await google.maps.importLibrary("marker");
    // const { Place } = await google.maps.importLibrary("places");

    const position = await getUserLocation();
    const userLat = position.coords.latitude;
    const userLng = position.coords.longitude;

    const map = new Map(document.getElementById("map"), {
        center: { lat: userLat, lng: userLng },
        zoom: 13,
        mapId: "4504f8b37365c3d0",
    });

    const categoryIcons = {
        "Food": "/assets/food_icon2.png",
        "Finance": "/assets/money2.png",
        "Automotive": "/assets/car-icon2.png",
        "Insurance": "/assets/insurance_icon2.png",
        "Entertainment": "/assets/movie_icon.png",
        "Travel": "/assets/travel_icon2.png",
        "Hotel/Lodging": "/assets/lodging_icon2.png",
        "Fitness Centers": "/assets/gym_icon2.png",
        "Theme Parks": "/assets/theme-park_icon2.png",
        "Department Store": "/assets/retail_icon2.png"
    };


    // Fetch the places data from the server
    const response = await fetch('/places');
    const places = await response.json();

    // Create a marker for each place
    places.forEach(place => {
        const lat = place.latitude;
        const lng = place.longitude;
        const category = place.category;

        console.log("Category is the javascriopt : ", category);


        const icon = categoryIcons[category];

        // Create an image element dynamically based on the category
        const iconElement = document.createElement('img');
        iconElement.src = categoryIcons[category] // || "/assets/default-icon.png";
        iconElement.style.width = "30px";
        iconElement.style.height = "30px";

        // Create a new marker with the custom icon
        const customMarker = new AdvancedMarkerElement({
            map,
            position: { lat: lat, lng: lng },
            content: iconElement,  // Use the dynamically created image element
            title: category  // Optionally, set the title to the category
        });
    });

    const carIcon = document.createElement('img');
    carIcon.src = "/assets/crane.png";
    carIcon.style.width = "30px";
    carIcon.style.height = "30px";

    const carIconMarkerView = new AdvancedMarkerElement({
        map,
        position: { lat: userLat, lng: userLng },
        content: carIcon,
        title: "A marker using a custom PNG Image",
    });
}


// function test(obj){
//     console.log("test");
//     console.log(obj);
//     console.log("test2");
// }

function test(){
}
