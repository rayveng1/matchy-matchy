function toggleArrow(element) {
    console.log(element.innerHTML)
    element.innerHTML = (element.innerHTML.trim() === "&lt;") ? "&gt;" : "&lt;"
}

const successCallback = (position) => {
    sendLocationToServer(position.coords.latitude, position.coords.longitude);
};

const errorCallback = (error) => {
    console.log(error);
};

function getCurrentLocation() {
    // Check if location has been retrieved already
    if (!localStorage.getItem("locationFetched")) {
        navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
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
            localStorage.setItem("locationFetched", "true");
            window.location.reload();
        }
    };

    var data = JSON.stringify({ Latitude: lat, Longitude: lon });
    xhr.send(data);
}

// Call this function on page load or based on user action
getCurrentLocation();
