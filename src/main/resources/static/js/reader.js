var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const pointers = document.querySelectorAll("[point-to]");

for(let i = 0; i < pointers.length; i ++){
    pointers[i].addEventListener('click', () => {
        document.location.href = "/reader?url=" + pointers[i].getAttribute("point-to");
    });
}

function checkDevice() {
    if (navigator.userAgent.match(/Android/i)
        || navigator.userAgent.match(/webOS/i)
        || navigator.userAgent.match(/iPhone/i)
        || navigator.userAgent.match(/iPad/i)
        || navigator.userAgent.match(/iPod/i)
        || navigator.userAgent.match(/BlackBerry/i)
        || navigator.userAgent.match(/Windows Phone/i)
    ) {
        return true; // está utilizando celular
    }
    else {
        return false; // não é celular
    }
}

if(checkDevice()) {
    document.querySelector("#zoomout").classList.add("d-none");
    document.querySelector("#zoomin").classList.add("d-none");
}