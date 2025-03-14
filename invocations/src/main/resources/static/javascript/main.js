const urls = {
	authentication: 'http://localhost:8080',
	invocations: 'http://localhost:7070',
	players: 'http://localhost:9090',
	monsters: 'http://localhost:6060',
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
	invocations: {
		currentId: document.querySelector('#current-invocation-id'),
		currentMonster: document.querySelector('#current-monster'),
	},
};

const http = {
	post: {
		register: async () => await authenticationAction('/register'),
		login: async () => await authenticationAction('/login'),
		players: async (login) =>
			await fetch(`${urls.players}/players`, {
				method: 'POST',
				headers: {
					'Content-type': 'application/json',
				},
				body: JSON.stringify({
					login,
				}),
			}).then((response) => response.json()),
		invocations: async (me, token) =>
			await fetch(`${urls.invocations}/invocations/new/${me['id']}`, {
				method: 'POST',
				headers: {
					token,
				},
			}).then((response) => response.json()),
	},
	get: {
		me: async (token) =>
			await fetch(`${urls.players}/players/by-login/${dom.login.value}`, {
				headers: {
					token,
				},
			}).then((response) => response.json()),
		monster: async (id, token) =>
			await fetch(`${urls.monsters}/monsters/${id}`, {
				headers: {
					token,
				},
			}).then((response) => response.json()),
	},
};

// start utils
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
// end utils

// start dom
dom.buttons.register.addEventListener('click', async (event) => {
	event.preventDefault();
	const login = await http.post.register();
	const player = await http.post.players(login);

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
	token = await http.post.login();
	me = await http.get.me(token);

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

	const invocation = await http.post.invocations(me, token);
	console.log(invocation);

	dom.invocations.currentId.innerHTML = `Invocation number : ${invocation['id']}`;

	const monster = await http.get.monster(invocation['idMonstre'], token);
	console.log(monster);

	const properties = Object.entries(monster)
		.filter(([key, value]) => key !== 'skills')
		.map(
			([key, value]) =>
				`<li class="list-group-item">${key} : ${value}</li>`
		)
		.join('');

	const skills = `
	<li class="list-group-item">
		skills : 
		${monster['skills']
			.map(
				(skill) =>
					`<ul id="current-monster" class="list-group">${Object.entries(
						skill
					)
						.map(
							([key, value]) =>
								`<li class="list-group-item">${key} : ${value}</li>`
						)
						.join('')}
					</ul>
				`
			)
			.join('<hr>')}
	</li>`;

	dom.invocations.currentMonster.innerHTML = properties + skills;
});
// end dom
