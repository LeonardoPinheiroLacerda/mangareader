var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});

const pointers = document.querySelectorAll("[point-to]");

for(let i = 0; i < pointers.length; i ++){
    pointers[i].addEventListener('click', () => {
        document.location.href = "/reader?url=" + pointers[i].getAttribute("point-to");
    });
}