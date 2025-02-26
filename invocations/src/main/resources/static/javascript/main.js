const urls = {
	authentication: 'http://localhost:8080',
	invocations: 'http://localhost:7070',
};

const authenticationAction = async (event, endpoint, callback) => {
	event.preventDefault();

	const login = document.querySelector('#form-login').value;
	const password = document.querySelector('#form-password').value;

	console.log(
		JSON.stringify({
			password,
			login,
		})
	);

	const response = await fetch(`${urls.authentication}/${endpoint}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			accept: 'text/plain',
		},
		body: JSON.stringify({
			login,
			password,
		}),
	});
	callback(response);
};

document
	.querySelector('#form-register-submit')
	.addEventListener('click', (event) =>
		authenticationAction(event, 'register', async (response) =>
			console.log(await response.json())
		)
	);

document
	.querySelector('#form-login-submit')
	.addEventListener('click', (event) => authenticationAction(event, 'login'));
