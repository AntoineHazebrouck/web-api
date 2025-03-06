const urls = {
	authentication: 'http://localhost:8080',
	invocations: 'http://localhost:7070',
	players: 'http://localhost:9090',
};

const dom = {
	login: document.querySelector('#form-login'),
	password: document.querySelector('#form-password'),
	alerts: document.querySelector('#form-alert'),
	buttons: {
		register: document.querySelector('#form-register-submit'),
		login: document.querySelector('#form-login-submit'),
		invocations: document.querySelector('#invocation-button'),
	},
};

const renderAlerts = (successes, errors) => {
	dom.alerts.innerHTML = '';
	const alerts = errors
		.map(
			(text) =>
				`<div class="alert alert-danger" role="alert">${text}</div>`
		)
		.concat(
			successes.map(
				(text) =>
					`<div class="alert alert-success" role="alert">${text}</div>`
			)
		);
	dom.alerts.innerHTML = alerts.join('');
};

const authenticationAction = async (endpoint) => {
	const login = dom.login.value;
	const password = dom.password.value;

	console.log(
		JSON.stringify({
			password,
			login,
		})
	);

	let error = false;
	const response = await fetch(`${urls.authentication}${endpoint}`, {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: JSON.stringify({
			login,
			password,
		}),
	}).then(async (response) => {
		const content = await response.text();
		if (!response.ok) {
			renderAlerts([], [content, `Try to register first`]);
			error = true;
		}
		return content;
	});
	console.log(response);

	return error ? null : response;
};

dom.buttons.register.addEventListener('click', async (event) => {
	event.preventDefault();
	const login = await authenticationAction('/register');

	const player = await fetch(`${urls.players}/players`, {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: JSON.stringify({
			login,
		}),
	}).then((response) => response.json());

	renderAlerts(
		[
			`User was registered with the following login : ${login}`,
			`New player was created : ${JSON.stringify(player)}`,
		],
		[]
	);
});

let me;
let token;
dom.buttons.login.addEventListener('click', async (event) => {
	event.preventDefault();
	token = await authenticationAction('/login');
	me = await fetch(`${urls.players}/players/by-login/${dom.login.value}`, {
		headers: {
			token,
		},
	}).then((response) => response.json());

	if (token) {
		renderAlerts(
			[
				`User was logged with the following token : ${token}`,
				`Attached player is : ${JSON.stringify(me)}`,
			],
			[]
		);
	}
});

dom.buttons.invocations.addEventListener('click', async (event) => {
	event.preventDefault();

	const response = await fetch(
		`${urls.invocations}/invocations/new/${me['id']}`,
		{
			method: 'POST',
			headers: {
				token,
			},
		}
	).then((response) => response.text());
	
	console.log(response);
	
});
