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

        if(req.status == 200){
            showSuccessToast("Seu usuário foi criado, você será direcionado para a tela de login.");
            setTimeout(() => {
                document.location.href = req.url;
            }, 3000);
        }else {
            showDangerToast("Não foi possível criar seu usuário, verifique os dados informados.");
        }
    })();

    
}

window.addEventListener('load', () => {
    form.addEventListener('submit', requestCreateUser);

    constructToast();
})