async function request(url, method, body = {}) {
    return fetch(url, {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}

async function getRequest(url) {
    return fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
}

async function handleErrors(form, req){
    const json = await req.json();

    const errors = json.error;
   
    clearErrors(form);

    for(let fieldName in errors){
        const err = errors[fieldName];

        const field = document.querySelector(`#${fieldName}-err`);
        field.classList.remove('d-none');
        field.innerHTML = err;
    }
}

function clearErrors(form){
    const fields = document.querySelectorAll('.err');

    for(let i = 0; i < fields.length; i ++){
        const field = fields[i];
        field.classList.add('d-none');
    }
}

function addError(fieldName, err){
    const field = document.querySelector(`#${fieldName}-err`);
    field.classList.remove('d-none');
    field.innerHTML = err;
}