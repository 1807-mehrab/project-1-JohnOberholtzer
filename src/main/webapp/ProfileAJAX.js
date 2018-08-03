//Asynchronous JavaScript And XML
function loadProfile(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);	
			var USERN = result.username;
			var FIRSTN =result.firstname;
			var LASTN =result.lastname;
			var NUM =result.manager;
			var MANAGER = "";
			if(NUM==0){
				MANAGER = "Employee";
			} else{
				MANAGER = "Manager";
			}
			var PHON = result.phone;
			if(PHON===null){PHON="Not Listed"}
			var ADDR = result.address;
			if(ADDR===null){ADDR="Not Listed"}
			var EMAIL = result.email;
			if(EMAIL===null){EMAIL="Not Listed"}
			document.getElementById('mainbody').innerHTML = 
				`<h3>Username: `+USERN+`</h3>
				<h3>First Name: `+FIRSTN+`</h3>
				<h3>Last Name: `+LASTN+`</h3>
				<h3>Position: `+MANAGER+`</h3>
				<h3>Phone: `+PHON+`</h3>
				<h3>Address: `+ADDR+`</h3>
				<h3>Email: `+EMAIL+`</h3>
				<br>
				<button id="editprofile" type = "button" value = "editProfile" onclick ="editProfile();">Edit Profile</button>
				<br>
				`;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadprofile');
	xhr.send();
}

function editProfile(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);
			var USERN = result.username;
			var FIRSTN =`\"`+result.firstname + `\"`;
			var LASTN =`\"`+result.lastname + `\"`;
			var UPASS = ``+result.password;
			var NUM = result.isManager;
			var MANAGER = "";
			if(NUM==0){
				MANAGER = "Employee";
			} else{
				MANAGER = "Manager";
			}
			var PHON = result.phone;
			var ADDR = result.address;
			var EMAIL = result.email;
			document.getElementById('mainbody').innerHTML = 
				`<h3>Username: `+USERN+`</h3>
				<form id = "updateform" method = "post">
					<label>Edit First Name:</label>
					<input type="Text" name ="firstname" value = `+FIRSTN+` placeholder = `+FIRSTN+`>
					<br>
					<label>Edit Last Name:</label>
					<input type="Text" name ="lastname" value = `+LASTN+` placeholder = `+LASTN+`>
					<br>
					<label>Edit Password:</label>
					<input type="Password" name = "password" value =`+UPASS+` placeholder = `+UPASS+`>
					<br>
					<label>Edit Phone:</label>
					<input type="Text" name = "password" value =`+PHON+` placeholder = `+PHON+`>
					<br>
					<label>Edit Address:</label>
					<input type="Text" name = "password" value =`+ADDR+` placeholder = `+ADDR+`>
					<br>
					<label>Edit Email:</label>
					<input type="Text" name = "password" value =`+EMAIL+` placeholder = `+EMAIL+`>
					<br>
					<button id="submitedits" type = "button" value = "submitEdits" onclick ="submitEdits();">Submit Changes</button>
				</form>
				<br>
				`;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadprofile');
	xhr.send();
}

function submitEdits(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			loadProfile();
		}
	}
	var Nfirstname = document.getElementById('updateform').elements[0].value;
	var Nlastname = document.getElementById('updateform').elements[1].value;
	var Npassword = document.getElementById('updateform').elements[2].value;
	var Nphon = document.getElementById('updateform').elements[3].value;
	var Naddr = document.getElementById('updateform').elements[4].value;
	var Nemail = document.getElementById('updateform').elements[5].value;
	var json = JSON.stringify({
		e_ID: 0,
		manager: 0,
		username : "empty",
		firstname: Nfirstname,
		lastname: Nlastname,
		password : Npassword,
		phone: Nphon,
		address: Naddr,
		email: Nemail
	});
	xhr.open('POST', 'http://localhost:8080/RBSYS/updateprofile');
	xhr.send(json);
}

function getUsername(EID){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);	
			var USERN = result.username;
			console.log(USERN);
			return USERN;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadspecificprofile');
	xhr.send(JSON.stringify(EID));
}

function employeeMenu(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var employeelist = ``;
			var result = JSON.parse(xhr.responseText);
			var status = [];
			console.log(result);
			console.log('Length of object: ' + result.length)
			for(var i =0;i<result.length;i++){
				console.log(result[i].manager);
				if (result[i].manager == 0){
					status.push("Employee");
				} else  if (result[i].manager == 1){
					status.push("Manager");
				} else {
					status.push("Unspecified");
				}
				employeelist += `
				<li><button class = "viewemployee" type = "button" value = "viewEmployee" onclick ="viewEmployee(`+(result[i].e_ID)+`);">View Employee</button>
				Employee Username: `+result[i].username+` &nbsp;&nbsp; Account Type: `+status[i]+`
				<button id="viewtickets" type = "button" value = "viewTickets" onclick ="viewEmployeeTickets(`+(result[i].e_ID)+`);">View Employee's Tickets</button>
				</li><br>
				`;
			}
			document.getElementById('mainbody').innerHTML = 
			`<h3>Employees:</h3>
				<ul>
					`+employeelist+`
				</ul>
				<br>
			<br>
			`;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loademployees');
	xhr.send();
}

function viewEmployee(EID){
	var sendval = EID;
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);	
			var USERN = result.username;
			var FIRSTN =result.firstname;
			var LASTN =result.lastname;
			var NUM =result.manager;
			var MANAGER = "";
			if(NUM==0){
				MANAGER = "Employee";
			} else{
				MANAGER = "Manager";
			}
			var PHON = result.phone;
			if(PHON===null){PHON="Not Listed"}
			var ADDR = result.address;
			if(ADDR===null){ADDR="Not Listed"}
			var EMAIL = result.email;
			if(EMAIL===null){EMAIL="Not Listed"}
			document.getElementById('mainbody').innerHTML = 
				`<h2>EMPLOYEE INFORMATION</h2>
				<br>
				<h3>Username: `+USERN+`</h3>
				<h3>First Name: `+FIRSTN+`</h3>
				<h3>Last Name: `+LASTN+`</h3>
				<h3>Position: `+MANAGER+`</h3>
				<h3>Phone: `+PHON+`</h3>
				<h3>Address: `+ADDR+`</h3>
				<h3>Email: `+EMAIL+`</h3>
				<br>
				<button id="viewtickets" type = "button" value = "viewTickets" onclick ="viewEmployeeTickets(`+sendval+`);">View Employee's Tickets</button>
				<br>
				`;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadspecificprofile');
	xhr.send(JSON.stringify(EID));
}

