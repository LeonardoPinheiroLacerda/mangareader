var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const pointers = document.querySelectorAll("[point-to]");

const modes = ["scroll", "webtoon"];
var actualMode = 0;

var actualPage = 0;

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

function next(){
console.log(document.querySelector("#page-" + (parseInt(actualPage) + 1)));


    if(document.querySelector("#page-" + (parseInt(actualPage) + 1)) != undefined){
        actualPage += 1;
        document.location.href = getUrlWithFragment(actualPage);
    }    
}

function previous(){
    if(actualPage != 0){
        actualPage -= 1;
        document.location.href = getUrlWithFragment(actualPage);
    }
}

function getUrlWithFragment(fragment){
    var url = document.location.href;
    if(url.includes("#")) {
        return url.substring(0, url.indexOf("#")) + "#page-" + fragment;
    }
    return url + "#page-" + fragment;
}





if(checkDevice()) {
    document.querySelector("#zoomout").classList.add("d-none");
    document.querySelector("#zoomin").classList.add("d-none");
}

function changeMode(){
    const newMode = (actualMode + 1 < modes.length) ? actualMode + 1 : 0;

    const pages = document.querySelectorAll("." + modes[actualMode]);

    for(let i = 0; i < pages.length; i ++){
        pages[i].classList.remove(modes[actualMode]);
        pages[i].classList.add(modes[newMode]);
    }

    actualMode = newMode;
} 

onload = () => {
    document.querySelector("#previous").disabled = false;
    document.querySelector("#next").disabled = false;
}