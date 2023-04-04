const formulario = document.getElementById('formulario');
const inputs = document.querySelectorAll('#formulario input');

const expresiones = {
	usuario: /^[a-z0-9]{4,8}$/,
	password: /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[+-\/*])[A-Za-z0-9+-\/*]{6,12}$/,
	correo: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]$/,
    dni: /^[0-7][0-9]{7}[A-Z]$/,
}

const campos = {
	usuario: true,
	nombre: true,
	password: true,
	correo: true,
	dni: true
}

const validarFormulario = (e) => {
	switch (e.target.name) {
		case "clogin":
			if (expresiones.usuario.test(e.target.value)) {
				document.getElementById("grupo__usuario").classList.remove("formulario__grupo-incorrecto");
				document.getElementById("grupo__usuario").classList.add("formulario__grupo-correcto");
				document.querySelector("#grupo__usuario i").classList.add("fa-check-circle");
				document.querySelector("#grupo__usuario i").classList.remove("fa-times-circle");
				document.querySelector("#grupo__usuario .formulario__input-error").classList.remove("formulario__mensaje-activo")
				campos.usuario = true;
			}
			else {
				document.getElementById("grupo__usuario").classList.add("formulario__grupo-incorrecto");
				document.getElementById("grupo__usuario").classList.remove("formulario__grupo-correcto");
				document.querySelector("#grupo__usuario i").classList.add("fa-times-circle");
				document.querySelector("#grupo__usuario i").classList.remove("fa-check-circle");
				document.querySelector("#grupo__usuario .formulario__input-error").classList.add("formulario__mensaje-activo")
				campos.usuario = false;
			}
		break;
		case "cname":
			const patron_nombre = new RegExp(e.target.pattern);
			if (patron_nombre.test(e.target.value)) {
				document.getElementById("grupo__nombre").classList.remove("formulario__grupo-incorrecto");
				document.getElementById("grupo__nombre").classList.add("formulario__grupo-correcto");
				document.querySelector("#grupo__nombre i").classList.add("fa-check-circle");
				document.querySelector("#grupo__nombre i").classList.remove("fa-times-circle");
				document.querySelector("#grupo__nombre .formulario__input-error").classList.remove("formulario__mensaje-activo")
				campos.nombre = true;
			}
			else {
				document.getElementById("grupo__nombre").classList.add("formulario__grupo-incorrecto");
				document.getElementById("grupo__nombre").classList.remove("formulario__grupo-correcto");
				document.querySelector("#grupo__nombre i").classList.add("fa-times-circle");
				document.querySelector("#grupo__nombre i").classList.remove("fa-check-circle");
				document.querySelector("#grupo__nombre .formulario__input-error").classList.add("formulario__mensaje-activo")
				campos.nombre = false;
			}
		break;
		case "cpasswd":
			if (expresiones.password.test(e.target.value)) {
				document.getElementById("grupo__password").classList.remove("formulario__grupo-incorrecto");
				document.getElementById("grupo__password").classList.add("formulario__grupo-correcto");
				document.querySelector("#grupo__password i").classList.add("fa-check-circle");
				document.querySelector("#grupo__password i").classList.remove("fa-times-circle");
				document.querySelector("#grupo__password .formulario__input-error").classList.remove("formulario__mensaje-activo")
				campos.password = true;
			}
			else {
				document.getElementById("grupo__password").classList.add("formulario__grupo-incorrecto");
				document.getElementById("grupo__password").classList.remove("formulario__grupo-correcto");
				document.querySelector("#grupo__password i").classList.add("fa-times-circle");
				document.querySelector("#grupo__password i").classList.remove("fa-check-circle");
				document.querySelector("#grupo__password .formulario__input-error").classList.add("formulario__mensaje-activo")
				campos.password = false;
			}
		break;
		case "cemail":
			if (expresiones.correo.test(e.target.value)) {
				document.getElementById("grupo__correo").classList.remove("formulario__grupo-incorrecto");
				document.getElementById("grupo__correo").classList.add("formulario__grupo-correcto");
				document.querySelector("#grupo__correo i").classList.add("fa-check-circle");
				document.querySelector("#grupo__correo i").classList.remove("fa-times-circle");
				document.querySelector("#grupo__correo .formulario__input-error").classList.remove("formulario__mensaje-activo")
				campos.correo = true;
			}
			else {
				document.getElementById("grupo__correo").classList.add("formulario__grupo-incorrecto");
				document.getElementById("grupo__correo").classList.remove("formulario__grupo-correcto");
				document.querySelector("#grupo__correo i").classList.add("fa-times-circle");
				document.querySelector("#grupo__correo i").classList.remove("fa-check-circle");
				document.querySelector("#grupo__correo .formulario__input-error").classList.add("formulario__mensaje-activo")
				campos.correo = false;
			}
		break;
		case "cdni":
			if (expresiones.dni.test(e.target.value)) {
				document.getElementById("grupo__dni").classList.remove("formulario__grupo-incorrecto");
				document.getElementById("grupo__dni").classList.add("formulario__grupo-correcto");
				document.querySelector("#grupo__dni i").classList.add("fa-check-circle");
				document.querySelector("#grupo__dni i").classList.remove("fa-times-circle");
				document.querySelector("#grupo__dni .formulario__input-error").classList.remove("formulario__mensaje-activo")
				campos.dni = true;
			}
			else {
				document.getElementById("grupo__dni").classList.add("formulario__grupo-incorrecto");
				document.getElementById("grupo__dni").classList.remove("formulario__grupo-correcto");
				document.querySelector("#grupo__dni i").classList.add("fa-times-circle");
				document.querySelector("#grupo__dni i").classList.remove("fa-check-circle");
				document.querySelector("#grupo__dni .formulario__input-error").classList.add("formulario__mensaje-activo")
				campos.dni = false;
			}
		break;
	}
}

inputs.forEach((input) => {
	input.addEventListener("keyup", validarFormulario);
	input.addEventListener("blur", validarFormulario);
});

// Desmarcar marcar/desmarcar todas las opciones al marcar/desmarcar una de las otras
function check() {
	document.querySelectorAll(".marcar input[type=checkbox]").forEach(function(checkElement) {
		if (checkElement.checked) {
			checkElement.checked = false;
		}	
	});
}

function checkAll() {
	document.querySelectorAll(".marcar input[type=checkbox]").forEach(function(checkElement) {
		checkElement.checked = false;
	});
    document.querySelectorAll(".marcar__todas input[type=checkbox]").forEach(function(checkElement) {
        checkElement.checked = true;
    });
}

function uncheckAll() {
    document.querySelectorAll(".marcar__todas input[type=checkbox]").forEach(function(checkElement) {
        checkElement.checked = false;
    });
}

function send() {

	var today = new Date(); 
	var now = today.toLocaleString();
	document.querySelector("#cdate").setAttribute("value",now);

	var browser = "Navegador: " + navigator.appName + ", Versión: " + navigator.appVersion + ", Código: " + navigator.appCodeName;
	document.querySelector("#cbrowser").setAttribute("value",browser);

}


function select() {

	var met = document.getElementById("envio").value;
	var cod = document.getElementById("cod").value;

	document.querySelector("#formulario").setAttribute("method",met);
	document.querySelector("#formulario").setAttribute("enctype",cod);

}

function resetear() {
	
	formulario.reset();

	document.getElementById("grupo__usuario").classList.remove("formulario__grupo-incorrecto");
	document.querySelector("#grupo__usuario i").classList.remove("fa-times-circle");
	document.querySelector("#grupo__usuario i").classList.remove("fa-check-circle");
	document.querySelector("#grupo__usuario .formulario__input-error").classList.remove("formulario__mensaje-activo")

	document.getElementById("grupo__nombre").classList.remove("formulario__grupo-incorrecto");
	document.querySelector("#grupo__nombre i").classList.remove("fa-times-circle");
	document.querySelector("#grupo__nombre i").classList.remove("fa-check-circle");
	document.querySelector("#grupo__nombre .formulario__input-error").classList.remove("formulario__mensaje-activo")

	document.getElementById("grupo__password").classList.remove("formulario__grupo-incorrecto");
	document.querySelector("#grupo__password i").classList.remove("fa-times-circle");
	document.querySelector("#grupo__password i").classList.remove("fa-check-circle");
	document.querySelector("#grupo__password .formulario__input-error").classList.remove("formulario__mensaje-activo")

	document.getElementById("grupo__correo").classList.remove("formulario__grupo-incorrecto");
	document.querySelector("#grupo__correo i").classList.remove("fa-times-circle");
	document.querySelector("#grupo__correo i").classList.remove("fa-check-circle");
	document.querySelector("#grupo__correo .formulario__input-error").classList.remove("formulario__mensaje-activo")

	document.getElementById("grupo__dni").classList.remove("formulario__grupo-incorrecto");
	document.querySelector("#grupo__dni i").classList.remove("fa-times-circle");
	document.querySelector("#grupo__dni i").classList.remove("fa-check-circle");
	document.querySelector("#grupo__dni .formulario__input-error").classList.remove("formulario__mensaje-activo")

	document.getElementById("formulario__mensaje").classList.remove("formulario__mensaje_envio-activo");

	campos.usuario = true;
	campos.nombre = true;
	campos.password = true;
	campos.correo = true;
	campos.dni = true;
}

// mensajes de error y exito de envio
formulario.addEventListener("submit", (e) => {

	if(campos.usuario && campos.nombre && campos.password && campos.correo && campos.dni){

		document.getElementById("formulario__mensaje-exito").classList.add("formulario__mensaje-exito-activo");
		setTimeout(() => {
			document.getElementById("formulario__mensaje-exito").classList.remove("formulario__mensaje-exito-activo");
		}, 5000);

		document.querySelectorAll(".formulario__grupo-correcto").forEach((icono) => {
			icono.classList.remove("formulario__grupo-correcto");
		});
	} else {
		document.getElementById("formulario__mensaje").classList.add("formulario__mensaje_envio-activo");
		e.preventDefault();
	}
});

// resetear al refrescar la página
console.info(performance.navigation.type);
if (performance.navigation.type == performance.navigation.TYPE_RELOAD) {
	formulario.reset();
}
