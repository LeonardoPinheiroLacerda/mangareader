var toastElement;

function constructToast(){
    const toast = document.createElement('div');

    toast.classList.add('toast');
    toast.classList.add('position-fixed');
    toast.classList.add('end-0');
    toast.classList.add('bottom-0');
    toast.classList.add('m-3');

    toast.id = "toast";

    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');

    const header = document.createElement('div');

    header.classList.add('toast-header');
    header.id = 'toast-header';

    const icon = document.createElement('img');

    icon.id = 'toast-icon';

    icon.classList.add('me-2');

    icon.style.width="25px";
    icon.style.height="25px";

    const headerText = document.createElement('strong');
    headerText.classList.add('me-auto');
    headerText.classList.add('text-light');
    
    headerText.innerHTML = 'Manga Reader';

    const closeBtn = document.createElement('button');
    closeBtn.type = 'button';

    closeBtn.id = 'toast-close'

    closeBtn.classList.add('btn-close');
    closeBtn.classList.add('btn-close-white');

    toast.setAttribute('data-bs-dismiss', 'toast');
    toast.setAttribute('aria-label', 'Close');
    
    const body = document.createElement('div');

    body.id = 'toast-body';

    body.classList.add('toast-body');

    header.appendChild(icon);
    header.appendChild(headerText);
    header.appendChild(closeBtn);

    toast.appendChild(header);
    toast.appendChild(body);

    document.body.appendChild(toast);

    toastElement = bootstrap.Toast.getOrCreateInstance(document.querySelector('#toast'));

}

function showToast(){
    toastElement.show();
}

function hideToast(){
    toastElement.hide();
}

function setToastIconColor(color) {
    const icon = document.querySelector('#toast-icon');

    switch(color) {
        case "danger":
            icon.src = "/assets/icon-red-small.png";
            break;
        case "light":
            icon.src = "/assets/icon-white-small.png";
            break;
        default:
            icon.src = "/assets/icon-blue-small.png";
            break;
    }
}

function setToastHeaderColor(color) {
    const header = document.querySelector('#toast-header');

    header.classList.remove('bg-danger');
    header.classList.remove('bg-primary');
    header.classList.remove('bg-light');

    switch(color) {
        case "danger":
            header.classList.add('bg-danger');
            break;
        case "light":
            header.classList.add('bg-light');
            break;
        default:
            header.classList.add('bg-primary');
            break;
    }
}

function setToastBodyText(text){
    const body = document.querySelector('#toast-body');
    body.innerHTML = text;
}

function showSuccessToast(text){
    setToastIconColor('light');
    setToastHeaderColor('primary');
    setToastBodyText(text);
    
    showToast();
}

function showDangerToast(text){
    setToastIconColor('light');
    setToastHeaderColor('danger');
    setToastBodyText(text);
    
    showToast();
}