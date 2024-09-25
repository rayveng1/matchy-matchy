console.log("testing here")

function toggleArrow(element) {
    element.innerHTML = (element.innerHTML.trim() === "&lt;") ? "&gt;" : "&lt;"
}

const successCallback = (position) => {
    sendLocationToServer(position.coords.latitude, position.coords.longitude);
    localStorage.setItem("locationCookie", "true");
};

const errorCallback = (error) => {
    localStorage.setItem("locationCookie", "false")
    sendLocationToServer(null, null)
};

async function getCurrentLocation() {
    console.log((new Error()).stack?.split("\n")[2]?.trim().split(" ")[1])

    // If the locationCookie isn't set yet.
    if (!localStorage.getItem("locationCookie")) {
        console.log("2")
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
        if (localStorage.getItem("locationCookie") === "true") {
            document.getElementById("AAA_logo_middle").classList.add("animate-spin")
            window.location.reload();
        }
    } else {// If the locationCookie is set.
        if (localStorage.getItem("locationCookie") === "true"){
            console.log("3")
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
                fetch("/resetMainPlace")
                window.location.reload();
            }
        } else {
            console.log("4")
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
                document.getElementById("AAA_logo_middle").classList.add("animate-spin")
                window.location.reload();
            }
        }
    }
    await initMap();



}


function sendLocationToServer(lat, lon) {
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

const intersectionObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add("drop");
            intersectionObserver.unobserve(entry.target);  // Animate once
        }
    });
});

async function initMap() {

    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement, PinElement } = await google.maps.importLibrary("marker");
    // const { Place } = await google.maps.importLibrary("places");

    const position = await getUserLocation();
    const userLat = position.coords.latitude;
    const userLng = position.coords.longitude;

    map = new Map(document.getElementById("map"), {
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



        const icon = categoryIcons[category];

        // Create an image element dynamically based on the category
        const iconElement = document.createElement('img');
        iconElement.src = categoryIcons[category] // || "/assets/default-icon.png";
        iconElement.style.width = "30px";
        iconElement.style.height = "30px";
        iconElement.style.opacity = "0";  // Start with opacity 0

        // Create a new marker with the custom icon
        const customMarker = new AdvancedMarkerElement({
            map,
            position: { lat: lat, lng: lng },
            content: iconElement,  // Use the dynamically created image element
            title: category  // Optionally, set the title to the category
        });
        // Add animation using IntersectionObserver
        intersectionObserver.observe(iconElement);
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
    console.log("INTMAP")
    // Add a button to pan to the current user location
    const locationButton = document.createElement("button");
    locationButton.id = "locationButton";
    locationButton.textContent = "Pan to Current Location";
    locationButton.classList.add("custom-map-control-button");
    locationButton.style.margin = "10px";
    map.controls[google.maps.ControlPosition.BOTTOM_LEFT].push(locationButton);

    locationButton.addEventListener("click", async () => {
        try {
            const position = await getUserLocation();
            const userLat = position.coords.latitude;
            const userLng = position.coords.longitude;

            // Pan the map to the current user's location
            map.panTo({ lat: userLat, lng: userLng });
            map.setZoom(15); // Optionally zoom in closer
        } catch (error) {
            console.error("Error getting the current location", error);
        }
    });
}

async function panToMain(lat, lng){
    // Ensure that 'map' has been initialized before calling panTo
    if (map) {
        map.panTo({lat: lat, lng: lng});
        map.setZoom(15)// Pan to a new location
    } else {
        console.error("Map is not initialized yet!");
    }
}

async function resetMap(userLat, userLng){
    // Ensure that 'map' has been initialized before calling panTo
    if (map) {
        map.panTo({lat: userLat, lng: userLng});
        map.setZoom(13)// Pan to a new location
    } else {
        console.error("Map is not initialized yet!");
    }
}


