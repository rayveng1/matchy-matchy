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
    }


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

// Initialize and add the map
let map;

async function initMap() {
    // The location of Uluru
    const position = { lat: -25.344, lng: 131.031 };
    // Request needed libraries.
    //@ts-ignore
    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");

    // // A marker with a with a URL pointing to a PNG.
    // const beachFlagImg = document.createElement("img");
    //
    // beachFlagImg.src =
    //     "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png";
    //
    // const beachFlagMarkerView = new AdvancedMarkerElement({
    //     map,
    //     position: { lat: 37.434, lng: -122.082 },
    //     content: beachFlagImg,
    //     title: "A marker using a custom PNG Image",
    // });

    // The map, centered at Uluru
    map = new Map(document.getElementById("map"), {
        zoom: 4,
        center: position,
        mapId: "DEMO_MAP_ID",
    });

    // The marker, positioned at Uluru
    const marker = new AdvancedMarkerElement({
        map: map,
        position: position,
        title: "Uluru",
    });
}

initMap();

