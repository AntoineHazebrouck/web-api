const urls = {
	authentication: 'http://localhost:8080',
	invocations: 'http://localhost:7070',
};

const renderAlert = (text) => {
	document.querySelector(
		'#form-alert'
	).innerHTML = `<div class="alert alert-success" role="alert">${text}</div>`;
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
			'Content-type': 'application/json',
		},
		body: JSON.stringify({
			login,
			password,
		}),
	}).then((response) => response.text());
	console.log(response);
	callback(response);
};

document
	.querySelector('#form-register-submit')
	.addEventListener('click', (event) =>
		authenticationAction(event, 'register', (response) =>
			renderAlert(
				`User was registered with the following login : ${response}`
			)
		)
	);

let token;
document
	.querySelector('#form-login-submit')
	.addEventListener('click', (event) =>
		authenticationAction(event, 'login', (response) => {
			renderAlert(
				`User was logged in providing the following token : ${response}`
			);
		})
	);

document
	.querySelector('#invocation-button')
	.addEventListener('click', async (event) => {
		event.preventDefault();

		const response = await fetch(`${urls.invocations}/TODO`, {
			method: 'POST',
			headers: {
				'Content-type': 'application/json',
			},
			body: JSON.stringify({
				TODOOOOOOO,
			}),
		}).then((response) => response.text());
	});
