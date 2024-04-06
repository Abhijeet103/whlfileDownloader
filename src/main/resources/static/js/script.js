// script.js

document.getElementById("downloadForm").addEventListener("submit", function(event) {
    event.preventDefault();
    var formData = new FormData(this);

    // Construct the base URL
    var baseUrl = "http://localhost:8080/download?";

    // Append form data to the base URL
    for (var pair of formData.entries()) {
        baseUrl += encodeURIComponent(pair[0]) + "=" + encodeURIComponent(pair[1]) + "&";
    }

    // Remove the last '&' character
    baseUrl = baseUrl.slice(0, -1);

    console.log(baseUrl)

    // Fetch the GET API
    fetch(baseUrl)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.blob();
    })
    .then(blob => {
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = 'downloaded_file.zip';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        // Alert the user that download has started/finished
        alert("Your download has started. Please check your downloads folder.");
    })
    .catch(error => console.error('Error:', error));
});
