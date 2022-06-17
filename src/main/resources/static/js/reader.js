var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const pointers = document.querySelectorAll("[point-to]");

const modes = ["scroll", "webtoon"];
var actualMode = 0;

var actualPage = 0;

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

function next(btn){
    bootstrap.Tooltip.getInstance(btn).hide();

    if(document.querySelector("#page-" + (parseInt(actualPage) + 1)) != undefined){
        actualPage += 1;
        document.location.href = getUrlWithFragment(actualPage);
    }    
}

function previous(btn){
    bootstrap.Tooltip.getInstance(btn).hide();

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

function changeMode(btn){
    bootstrap.Tooltip.getInstance(btn).hide();

    actualMode = (actualMode + 1 < modes.length) ? actualMode + 1 : 0;

    scrollTo(0, 0);
    actualPage = 0;
    document.location.href = getUrlWithFragment(actualPage);

    if(modes[actualMode] == 'webtoon'){
        document.querySelector("#previous").classList.add("d-none");
        document.querySelector("#next").classList.add("d-none");
    }else{
        document.querySelector("#previous").classList.remove("d-none");
        document.querySelector("#next").classList.remove("d-none");
    }

    if(actualMode < 2){
        buildGenericMode(modes[actualMode]);
    }
} 

function zoomin(btn){
    bootstrap.Tooltip.getInstance(btn).hide();
}

function zoomout(btn){
    bootstrap.Tooltip.getInstance(btn).hide();
}

function clearPages(){
    const container = document.querySelector("#pages-container");
    container.innerHTML = "";
}

function buildGenericMode(mode){
    clearPages();

    const loading = document.querySelector("#loading");
    loading.classList.remove("d-none");

    var index = 0;

    const container = document.querySelector("#pages-container");
    
    const buildImageDiv = () => {
        const div = document.createElement("div");
        div.classList.add("image-placeholder", "d-flex", "justify-content-center", "align-items-center");

        return div;
    }

    loadImages(container, pages, index, mode, buildImageDiv);
}


function loadImages(container, pages, index, mode, imageDivBuilder = null) {      
    const image = new Image();
    image.src = pages[index];
    image.id = "page-" + index;
    image.classList.add("page", mode);

    image.onload = () => {
        if(imageDivBuilder != null){
            const div = imageDivBuilder();
            div.appendChild(image);
            container.appendChild(div);
        }else{
            container.appendChild(image);
        }
        
        index += 1;
        loadImages(container, pages, index, mode, imageDivBuilder);
    }

    image.onerror = () => {
        const loading = document.querySelector("#loading");
        loading.classList.add("d-none");
    }
}

if(checkDevice()) {
    document.querySelector("#zoomout").classList.add("d-none");
    document.querySelector("#zoomin").classList.add("d-none");
}

for(let i = 0; i < pointers.length; i ++){
    pointers[i].addEventListener('click', () => {
        document.location.href = "/reader?url=" + pointers[i].getAttribute("point-to");
    });
}

onload = () => {
    buildGenericMode(modes[actualMode]);

    document.querySelector("#previous").disabled = false;
    document.querySelector("#next").disabled = false;
}