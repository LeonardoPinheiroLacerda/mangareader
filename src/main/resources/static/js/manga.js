var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const chaptersElements = document.querySelectorAll('[chapter-url]');

for(let i = 0; i < chaptersElements.length; i ++){
    const chaptersElement = chaptersElements[i];

    chaptersElement.addEventListener("click", () => {
        document.location = chaptersElement.getAttribute('chapter-url');
    })
}

const downloadElements = document.querySelectorAll('[pdf-download-url]');

for(let i = 0; i < downloadElements.length; i ++){
    const downloadElement = downloadElements[i];

    downloadElement.addEventListener("click", () => {
        
        const downloadRequest = async () => {
            return fetch(downloadElement.getAttribute('pdf-download-url'));
        }

        (async() => {

            disablePdfDownloadButton(downloadElement);

            const req = await downloadRequest();

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

            enablePdfDownloadButton(downloadElement);

        })();

    });
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