var toastElement;

function constructToast(){

    const toast = document.createElement('div');
    toast.classList.add('toast');
    toast.classList.add('align-items-center');
    toast.classList.add('text-white');
    toast.classList.add('bg-primary');
    toast.classList.add('border-0');
    toast.classList.add('position-fixed');
    toast.classList.add('end-0');
    toast.classList.add('top-0');
    toast.classList.add('m-3');
    toast.classList.add('shadow');

    toast.id = "toast";

    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');

    const content = document.createElement('div');
    content.classList.add('d-flex');

    const body = document.createElement('div');
    body.classList.add('toast-body');

    body.id = "toast-body";

    const closeBtn = document.createElement('button');
    closeBtn.type = 'button';

    closeBtn.id = 'toast-close'

    closeBtn.classList.add('btn-close');
    closeBtn.classList.add('btn-close-white');
    closeBtn.classList.add("me-2");
    closeBtn.classList.add("m-auto");

    content.appendChild(body);
    content.appendChild(closeBtn);

    toast.appendChild(content);

    document.body.appendChild(toast);

    toastElement = bootstrap.Toast.getOrCreateInstance(document.querySelector('#toast'));

}

function showToast(){
    toastElement.show();
}

function hideToast(){
    toastElement.hide();
}

function setToastBodyColor(color) {
    const header = document.querySelector('#toast');

    header.classList.remove('bg-danger');
    header.classList.remove('bg-success');
    header.classList.remove('bg-light');

    switch(color) {
        case "danger":
            header.classList.add('bg-danger');
            break;
        case "light":
            header.classList.add('bg-light');
            break;
        default:
            header.classList.add('bg-success');
            break;
    }
}

function setToastBodyText(text){
    const body = document.querySelector('#toast-body');
    body.innerHTML = text;
}

function showSuccessToast(text){
    setToastBodyColor('success');
    setToastBodyText(text);

    showToast();
}

function showDangerToast(text){
    setToastBodyColor('danger');
    setToastBodyText(text);
    
    showToast();
}