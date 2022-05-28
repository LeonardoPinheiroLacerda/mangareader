const form = document.querySelector("#signin-form");
const alert = bootstrap.Toast.getOrCreateInstance(document.querySelector('#toast'));

function requestCreateUser(evt) {
    evt.preventDefault();
    
    (async () => {
        const req = await request(
            '/api/users?redirect=true', 
            'POST', 
            {
                'username':             document.querySelector("#username").value, 
                'email':                document.querySelector("#email").value,
                'password':             document.querySelector("#password").value,
                'passwordConfirmation': document.querySelector("#passwordConfirmation").value
            }
        );

        clearErrors(form);

        if(req.status == 200){
            showSuccessToast("Seu usuário foi criado, você será direcionado para a tela de login.");
            setTimeout(() => {
                document.location.href = req.url;
            }, 3000);
        }else if(req.status == 422) {
            handleErrors(form, req);
        }else {
            showDangerToast("Não foi possível criar seu usuário, verifique os dados informados.");
            if(await verifyIfEmailExists()){
                addError("email", "Esse e-mail já esta cadastrado.");
            }
            if(await verifyIfUsernameExists()){
                addError("username", "Esse nome de usuário já esta cadastrado.");
            }
        }
    })();

    
}

async function verifyIfUsernameExists(){
    const username = document.querySelector("#username").value;
    const req = await getRequest('/api/users/username/' + username);
    return req.status == 200;
} 
async function verifyIfEmailExists(){
    const email = document.querySelector("#email").value;
    const req = await getRequest('/api/users/email/' + email);
    return req.status == 200;
}   

window.addEventListener('load', () => {
    form.addEventListener('submit', requestCreateUser);

    constructToast();
});