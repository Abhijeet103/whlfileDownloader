// script.js

document.getElementById("downloadForm").addEventListener("submit", function(event) {
    event.preventDefault();
    var formData = new FormData(this);

    var queryString = new URLSearchParams(formData).toString();
    console.log(queryString)
    var url = "http://localhost:8080/download?" + queryString;


            fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                document.getElementById('result').innerHTML = `<p>Download Link: <a href="${data.downloadLink}">${data.downloadLink}</a></p>`;
            })
            .catch(error => console.error('Error:', error));
        });