let loadingGif;

function constructLoadingAnimation(){

    const container = document.createElement('div');

    container.classList.add('position-fixed');
    container.classList.add('top-0');
    container.classList.add('start-0');
    container.classList.add('w-100');
    container.classList.add('h-100');
    
    container.classList.add('d-flex');
    container.classList.add('justify-content-center');
    container.classList.add('align-items-center');

    container.classList.add('bg-dark');
    container.classList.add('opacity-75');

    container.style.zIndex = "9999";

    const div = document.createElement('div');

    div.classList.add('spinner-border');
    div.classList.add('text-light');
    div.classList.add('opacity-100');

    div.setAttribute('role', 'status');

    const span = document.createElement('span');

    span.classList.add('visually-hidden');

    
    div.appendChild(span);

    container.appendChild(div);

    loadingGif = container;
}

function showLoadingAnimation(){
    console.log("showing loading animation");
    document.body.appendChild(loadingGif);
}

function hideLoadingAnimation(){
    console.log("hiding loading animation");
    document.body.removeChild(loadingGif);
}

constructLoadingAnimation();