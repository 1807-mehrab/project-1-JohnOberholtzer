//Asynchronous JavaScript And XML
function login() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);

		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText).result;
			console.log(result);
			if (result == 0) {
				console.log("successful login: Employee");
				document.getElementById('container').innerHTML = "<p>Login Success</p>";
				window.location = 'http://localhost:8080/RBSYS/ehome.html';
			} else if (result==1){
				console.log("successful login: Manager");
				document.getElementById('container').innerHTML = "<p>Login Success</p>";
				window.location = 'http://localhost:8080/RBSYS/mhome.html';
			} else if (result==2){
				console.log("loginfailed");
				document.getElementById('container').innerHTML = "<p style=\"color:#FF0000;\">Password Incorrect</p>";
			} else if (result==3){
				console.log("loginfailed");
				document.getElementById('container').innerHTML = "<p style=\"color:#FF0000;\">Username Not Found</p>";
			}

		}
	}
	var username = document.getElementById('loginform').elements[0].value;
	var password = document.getElementById('loginform').elements[1].value;
	xhr.open('POST', 'http://localhost:8080/RBSYS/login');
	var json = JSON.stringify({
		username : username,
		password : password
	});
	xhr.send(json);

}

function logout() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			window.location = 'http://localhost:8080/RBSYS/index.html';
		}

	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/logout');
	xhr.send();
}

function loadHome(){
	document.getElementById('mainbody').innerHTML = 
	`<h2>Employee Home</h2>
	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut nulla fermentum, vulputate risus at, vehicula velit. Donec et justo a libero aliquet sodales eget nec odio. In sed lorem elit. Duis eu condimentum augue. Duis tristique vitae urna ac sodales. Vestibulum tempus accumsan neque, nec molestie dui tincidunt in. Maecenas sit amet congue augue. Nulla pulvinar, orci sed luctus malesuada, nisl magna ornare ante, vel blandit neque ante vel quam. Phasellus faucibus nisl sit amet lorem hendrerit consectetur. Pellentesque scelerisque diam id nisl sodales, et auctor enim eleifend. Cras faucibus tortor quis ex finibus, non commodo eros iaculis. Phasellus cursus aliquet ipsum eu ultrices. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum laoreet vel lorem sed ultricies. Sed commodo, justo semper porttitor rutrum, quam felis facilisis nisl, id pharetra libero velit a lorem.</p>
	<p>
	</p>
	<br>
	`;
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }
    return true;
}
