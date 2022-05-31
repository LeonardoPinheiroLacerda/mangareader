const offcanvas = new bootstrap.Offcanvas(
    document.querySelector('.offcanvas')
);

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const logoutModal = bootstrap.Modal
    .getOrCreateInstance(document.querySelector('#logout-modal'));

constructToast();

function showLogoutModal(){
    logoutModal.show();
}

async function logout(){
    logoutModal.hide();
    tooltip.hide();
    const req = await request("/logout", "POST");

    if(req.status == 200){
        showSuccessToast("Aguarde, estamos removendo sua sessão.");
    }else{
        showDangerToast("Algo deu errado, tente novamente.");
    }

    setTimeout(() => {
        document.location.href = document.location.href;
    }, 3000);

}

function openSearch(){
    offcanvas.show();

    const tooltip = bootstrap.Tooltip.getInstance(document.getElementById('search-sidebar'))
    tooltip.hide();

    const input = document.querySelector("#search-input-offcanvas");
    input.value = "";

    setTimeout(() => {
        input.focus();
    }, 400);
}