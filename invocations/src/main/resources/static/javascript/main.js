const urls = {
	authentication: 'http://localhost:8080',
	invocations: 'http://localhost:7070',
}

const authenticationAction = (event, endpoint) => {
	event.preventDefault();

	const login = document.querySelector('#form-login').value
	const password = document.querySelector('#form-password').value

	console.log(JSON.stringify({
		password, login
	}));

	fetch(`${urls.authentication}/${endpoint}`, {
		method: "POST",
		headers: {
			"Content-type": "application/json"
		},
		body: JSON.stringify({
			login, password
		}),
	});
}

document.querySelector('#form-register-submit').addEventListener("click", event => authenticationAction(event, 'register'))

document.querySelector('#form-login-submit').addEventListener("click", event => authenticationAction(event, 'login'))