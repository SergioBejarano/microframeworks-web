function sendGreeting() {
    const name = document.getElementById("name").value || "World";

    fetch(`/app/hello?name=${encodeURIComponent(name)}`)
        .then(response => response.text()) 
        .then(data => {
            document.getElementById("response").innerText = data;
        })
        .catch(error => {
            document.getElementById("response").innerText = "Error de conexión";
            console.error(error);
        });
}

function getPi() {
    fetch("/app/pi")
        .then(response => response.text())
        .then(data => {
            document.getElementById("piResponse").innerText = "π = " + data;
        })
        .catch(error => {
            document.getElementById("piResponse").innerText = "Error de conexión";
            console.error(error);
        });
}

function sendEcho() {
    const message = document.getElementById("echo").value || "";

    fetch("/app/echo", {
        method: "POST",
        headers: {
            "Content-Type": "text/plain"
        },
        body: message
    })
    .then(response => response.text()) 
    .then(data => {
        document.getElementById("echoResponse").innerHTML = data;
    })
    .catch(error => {
        document.getElementById("echoResponse").innerText = "Error de conexión";
        console.error(error);
    });
}
