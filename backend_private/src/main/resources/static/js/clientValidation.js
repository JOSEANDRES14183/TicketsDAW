// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                let isRepeatedPasswordValid = checkRepeatedPassword()

                if (!form.checkValidity() || !isRepeatedPasswordValid) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')

                if(!isRepeatedPasswordValid){
                    resetFormValidation()
                }
            }, false)
        })
})()

document.getElementById("passwordHashRepeat").addEventListener("keyup", checkRepeatedPassword)
document.getElementById("passwordHash").addEventListener("keyup", checkRepeatedPassword)

function checkRepeatedPassword(){
    let elementRepeat = document.getElementById("passwordHashRepeat");
    elementRepeat.classList.remove("is-valid")
    elementRepeat.classList.remove("is-invalid")

    if(document.getElementById("passwordHash").value === elementRepeat.value){
        elementRepeat.classList.add("is-valid");
        return true;
    }
    else{
        elementRepeat.classList.add("is-invalid");
        resetFormValidation();
        return false;
    }
}

function resetFormValidation(){
    let forms = document.getElementsByTagName("form")
    for (let i = 0; i < forms.length; i++) {
        forms[i].classList.remove("was-validated")
    }
}