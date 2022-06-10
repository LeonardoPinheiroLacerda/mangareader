var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const chaptersElements = document.querySelectorAll('[chapter-url]');

for(let i = 0; i < chaptersElements.length; i ++){
    const chaptersElement = chaptersElements[i];

    chaptersElement.addEventListener("click", () => {
        document.location = "/reader?url=" + chaptersElement.getAttribute('chapter-url');
    })
}

const downloadElements = document.querySelectorAll('[pdf-download-url]');

for(let i = 0; i < downloadElements.length; i ++){
    const downloadElement = downloadElements[i];

    downloadElement.addEventListener("click", () => {
        
        const downloadRequest = async () => {
            return fetch("api/downloads/chapter?url=" + downloadElement.getAttribute('pdf-download-url'));
        }

        (async() => {

            disablePdfDownloadButton(downloadElement);

            const req = await downloadRequest();

            if(req.status == 200){
                const blob = await req.blob();

                const fileName = () => {
                    let name = req.headers.get('Content-Disposition');
                    name = name.substring(22, name.length -1);
                    return name;
                }
    
                var url = window.URL.createObjectURL(blob);
    
                var a = document.createElement('a');
                a.href = url;
                a.download = fileName();
                document.body.appendChild(a);
                a.click();    
                a.remove();

            }else{
                const json = await req.json();
                showDangerToast(json.message);
            }

            enablePdfDownloadButton(downloadElement);

        })();

    });
}

document.querySelector("#cover-field").addEventListener('keyup', (e) => {
    if(e.keyCode == 13){
        setCover();
    }
});

async function setCover(){
    var myModalEl = document.querySelector('#set-cover-modal');
    var modal = bootstrap.Modal.getOrCreateInstance(myModalEl);

    const input = document.querySelector("#cover-field");

    const sourceUrl = document.querySelector("meta[name='source-url']").getAttribute('content');

    const req = await request("/api/mangas/cover", 
        'POST', 
        {
            'url': sourceUrl,
            'cover': input.value
        }
    );

    modal.hide();

    if(req.status == 204){
        document.location.href = document.location.href;
    }else{
        const json = await req.json();
        showDangerToast(json.message);
    }
    
}

function disablePdfDownloadButton(button){
    
    const tooltip = bootstrap.Tooltip.getInstance(button);
    tooltip.hide();

    button.disabled =  true;
    togglePdfDownloadButtonIcon(button);
}

function enablePdfDownloadButton(button){
    button.disabled =  false;
    togglePdfDownloadButtonIcon(button);
}

function togglePdfDownloadButtonIcon(button){
    const children = button.children;
    children[0].classList.toggle("d-none");
    children[1].classList.toggle("d-none");
}