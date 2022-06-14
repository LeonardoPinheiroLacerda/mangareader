var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});

var myModalEl = document.querySelector('#remove-modal');
var modal = bootstrap.Modal.getOrCreateInstance(myModalEl);

var removeUrl;

function read(source){
    const url = source.getAttribute("last-read-history");
    bootstrap.Tooltip.getInstance(source).hide();

    document.location = "/reader?url=" + url;
}

function remove(source){
    removeUrl = source.getAttribute("delete-history");
    bootstrap.Tooltip.getInstance(source).hide();

    modal.show();
}

function cancelRemove(){
    removeUrl = undefined;
    modal.hide();
}

function confirmRemove(){

    const remove = async () => {
        return fetch("api/history?url=" + removeUrl,
            {
                method: "DELETE"
            }
        );
    }

    (async () => {
        const req = await remove();

        if(req.status != 204){
            const json = await req.json();

            showDangerToast(json.message);
        }else{
            modal.hide();

            showSuccessToast("Manga removido de seu histórico com sucesso!");

            setTimeout(() => {
                document.location.href = document.location.href;
            }, 3000);
        }
    })();

}