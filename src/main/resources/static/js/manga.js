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