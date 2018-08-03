//Asynchronous JavaScript And XML
function ticketMenu(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var ticketlist = ``;
			var result = JSON.parse(xhr.responseText);
			var status = [];
			for(var i =0;i<result.length;i++){
				if (result[i].r_BOOLEAN == 0){
					status.push("Unapproved");
				} else  if (result[i].r_BOOLEAN == 1){
					status.push("Approved");
				} else {
					status.push("Denied");
				}
				ticketlist += `
				<li><button class = "viewticket" type = "button" value = "viewTicket" onclick ="viewTicket(`+(result[i].t_ID)+`);">View Ticket</button>
				Ticket ID: `+result[i].t_ID+` &nbsp;&nbsp; Ticket Status: `+status[i]+` &nbsp;&nbsp; Context: `+result[i].t_TITLE+`</li><br>
				`;
			}
			console.log(result);
			document.getElementById('mainbody').innerHTML = 
			`<h3>Your Tickets:</h3>
				<ul>
					`+ticketlist+`
				</ul>
				<br>
				<button id = "maketicket" type = "button" value = "makeTicket" onclick ="makeTicket();">Create New Ticket</button>
			<br>
			`;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadtickets');
	xhr.send();
}

function viewTicket(ticketID){
	console.log(ticketID);
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);
			console.log(result);
			var TID = result.t_ID;
			var TITLE = result.t_TITLE;
			var DESC = result.t_DESC;
			var SOLVED = result.r_BOOLEAN;
			if (SOLVED == 0){
				document.getElementById('mainbody').innerHTML = 
	
				`<h3>TICKET #`+TID+`</h3>
				<h3>Title: `+TITLE+`</h3>
				<h3>Description: `+DESC+`</h3>
	
	
				<br>
				<button type = "button" onclick ="editTicket(`+TID+`);">Edit Ticket</button>
				<button type = "button" onclick ="loadPictureMenu(`+TID+`);">View/Edit Pictures</button>
				<br>
				`;
			} else {
				var TICKETSTATE = ``;
				if (SOLVED == 1){
					TICKETSTATE += "Approved";
				} else {
					TICKETSTATE += "Denied";
				}
				var MANAGERID = result.rm_ID;
				let xhr2 = new XMLHttpRequest();
				xhr2.onreadystatechange = function() {
					console.log(this.readyState);
					if (this.readyState == 4 && this.status == 200) {
						var result2 = JSON.parse(xhr2.responseText);
						var USRNAME = result2.username;
						console.log("Loading Username: " + USRNAME);
						document.getElementById('mainbody').innerHTML = 

							`<h3>TICKET #`+TID+`</h3>
							<h3>Title: `+TITLE+`</h3>
							<h3>Description: `+DESC+`</h3>
							<br>
							<h3>Status: `+TICKETSTATE+`</h3>
							<h3>Ticket was responded to by: `+USRNAME+`</h3>
							<br>
							<button type = "button" onclick ="loadPictureMenu(`+TID+`);">View Pictures</button>
							`;
					}
				
				}
				xhr2.open('POST', 'http://localhost:8080/RBSYS/loadspecificprofile');
				xhr2.send(JSON.stringify(MANAGERID));	
			}
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadticketpage');
	xhr.send(JSON.stringify(ticketID));
}

function viewTicket2(ticketID){
	console.log(ticketID);
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			
			var result = JSON.parse(xhr.responseText);
			var TID = result.t_ID;
			var EID = result.e_ID;
			var TITLE = result.t_TITLE;
			var DESC = result.t_DESC
			var SOLVED = result.r_BOOLEAN;
			var MID = result.rm_ID;
			
			var TICKETSTATE = ``;
			
			let xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				console.log(this.readyState);
				if (this.readyState == 4 && this.status == 200) {
					var result2 = JSON.parse(xhr2.responseText);
					var USRNAME = result2.username;
					
					if (SOLVED ==0){
						TICKETSTATE += "Unapproved";
						document.getElementById('mainbody').innerHTML = 

							`<h3>TICKET #`+TID+`</h3>
							<h3>Employee Username: `+USRNAME+`</h3>
							<h3>Title: `+TITLE+`</h3>
							<h3>Description: `+DESC+`</h3>
							<br>
							<h3>Status: `+TICKETSTATE+`</h3>

							<br>
							<button type = "button" onclick ="finishTicket(`+TID+`,`+1+`);">Approve Ticket</button>
							<button type = "button" onclick ="finishTicket(`+TID+`,`+2+`);">Deny Ticket</button>
							<br>
							<button type = "button" onclick ="loadPictureMenu(`+TID+`);">View Pictures</button>
							<button class = "viewemployee" type = "button" value = "viewEmployee" onclick ="viewEmployee(`+EID+`);">View Employee</button>
							`;
					} else {
						
						if (SOLVED == 1){
							TICKETSTATE += "Approved";
						} else {
							TICKETSTATE += "Denied";
						}
						
						let xhr3 = new XMLHttpRequest();
						xhr3.onreadystatechange = function() {
							console.log("new: "+this.readyState);
							if (this.readyState == 4 && this.status == 200) {
								var result3 = JSON.parse(xhr3.responseText);
								var MANAGERNAME = result3.username;
								
								document.getElementById('mainbody').innerHTML = 

									`<h3>TICKET #`+TID+`</h3>
									<h3>Employee Username: `+USRNAME+`</h3>
									<h3>Title: `+TITLE+`</h3>
									<h3>Description: `+DESC+`</h3>
									<br>
									<h3>Status: `+TICKETSTATE+`</h3>
									<h3>Ticket was responded to by: `+MANAGERNAME+`</h3>
									<br>
									<button class = "viewemployee" type = "button" value = "viewEmployee" onclick ="viewEmployee(`+EID+`);">View Employee</button>
									`;
						
							}
						}
						xhr3.open('POST', 'http://localhost:8080/RBSYS/loadspecificprofile');
						xhr3.send(JSON.stringify(MID));	
					}						
				}
			}
			xhr2.open('POST', 'http://localhost:8080/RBSYS/loadspecificprofile');
			xhr2.send(JSON.stringify(EID));	
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadticketpage');
	xhr.send(JSON.stringify(ticketID));
}

function makeTicket(){
	console.log("Ticket Creation")
	document.getElementById('mainbody').innerHTML = 
		`<h3>NEW REIMBURSAL REQUEST</h3>
		<form id = "ticketform" method = "post">
			<label>Title:</label>
			<input type="Text" name ="tickettitle"  style="width:300px;" placeholder = "What type of reimbursal is this?">
			<br>
			<label>Description:</label>
			<input type="Text" style="width:300px; height:50px;"  name ="description" placeholder = "Share any necessary details here." >
			<br>
			<button type = "button" onclick ="submitTicket();">Submit Request</button>
		</form>
		<br>
		`;
}

function submitTicket(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			ticketMenu();
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/createticket');
	var NTITLE = document.getElementById('ticketform').elements[0].value;
	var NDESC = document.getElementById('ticketform').elements[1].value;
	var json = JSON.stringify({
		t_ID : 0,
		e_ID : 0,
		t_TITLE: NTITLE,
		t_DESC : NDESC,
		r_BOOLEAN: 0,
		rm_ID: 0
	});
	xhr.send(json);
}

function editTicket(ticketID){
	console.log(ticketID);
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);
			var TITLE = `\"`+result.t_TITLE + `\"`;
			var DESC = `\"`+result.t_DESC + `\"`;
			document.getElementById('mainbody').innerHTML = 
				`<h3>EDIT REIMBURSAL REQUEST</h3>
				<form id = "edit_ticketform" method = "post">
					<label>Title:</label>
					<input type="Text" name ="tickettitle"  style="width:300px;" value = `+TITLE+` placeholder = `+TITLE+`>
					<br>
					<label>Description:</label>
					<input type="Text" style="width:300px; height:50px;"  name ="description" value =  `+DESC+` placeholder =  `+DESC+` >
					<br>
					<button type = "button" onclick ="submitTicketChanges(`+ticketID+`);">Submit Alterations</button>
				</form>
				<br>
				`;
			}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadticketpage');
	xhr.send(JSON.stringify(ticketID));
}

function submitTicketChanges(ticketID){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			ticketMenu();
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/updateticket');
	var NTITLE = document.getElementById('edit_ticketform').elements[0].value;
	var NDESC = document.getElementById('edit_ticketform').elements[1].value;
	var json = JSON.stringify({
		t_ID : ticketID,
		e_ID : 0,
		t_TITLE: NTITLE,
		t_DESC : NDESC,
		r_BOOLEAN: 0,
		rm_ID: 0
	});
	xhr.send(json);
}

function allTicketMenu(){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);
			var ticketlist = ``;
			var status = [];
			
			let xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				console.log(this.readyState);
				if (this.readyState == 4 && this.status == 200) {
					var employeelist = JSON.parse(xhr2.responseText);
					var empmap = new Object();
					for(var i =0;i<employeelist.length;i++){
						empmap[employeelist[i].e_ID] = employeelist[i].username;
					}
					function mapget(v){
						return empmap[v];
					}
					for(var i =0;i<result.length;i++){
						if (result[i].r_BOOLEAN == 0){
							status.push("Open");
						} else  if (result[i].r_BOOLEAN == 1){
							status.push("Approved");
						} else {
							status.push("Denied");
						}
						ticketlist += `
						<li><button class = "viewticket" type = "button" value = "viewTicket" onclick ="viewTicket2(`+(result[i].t_ID)+`);">View Ticket</button>
						Ticket ID: `+result[i].t_ID+` &nbsp;&nbsp; Ticket Status: `+status[i]+` &nbsp;&nbsp; Context: `+result[i].t_TITLE+` &nbsp;&nbsp; Owner: `+mapget(result[i].e_ID)+`</li><br>
						`;
					}
					document.getElementById('mainbody').innerHTML = 
					`<h3>All Employee Tickets:</h3>
						<button type="button" onclick="filteredTicketMenu(0)">View only Open Tickets</button>
						<button type="button" onclick="filteredTicketMenu(1)">View only Approved Tickets</button>
						<button type="button" onclick="filteredTicketMenu(2)">View only Denied Tickets</button>
						<ul>
							`+ticketlist+`
						</ul>
						<br>
						
					<br>
					`;
					
				}
			}
			xhr2.open('POST', 'http://localhost:8080/RBSYS/loademployees');
			xhr2.send();
			
			
			
			
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadalltickets');
	xhr.send();
}

function finishTicket(ticketID,ticketbool){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			allTicketMenu();
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/updateticketfinal');
	var json = JSON.stringify({
		t_ID : ticketID,
		e_ID : 0,
		t_TITLE: '',
		t_DESC : '',
		r_BOOLEAN: ticketbool,
		rm_ID: 0
	});
	xhr.send(json);
}

function viewEmployeeTickets(EID){
	console.log("EID Lookup is: " + EID);
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result2 = JSON.parse(xhr.responseText);
			var USERNAME = result2.username
			let xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				console.log(this.readyState);
				if (this.readyState == 4 && this.status == 200) {
					var ticketlist = ``;
					var result = JSON.parse(xhr2.responseText);
					var status = [];
					for(var i =0;i<result.length;i++){
						if (result[i].r_BOOLEAN == 0){
							status.push("Open");
						} else  if (result[i].r_BOOLEAN == 1){
							status.push("Approved");
						} else {
							status.push("Denied");
						}
						ticketlist += `
						<li><button class = "viewticket" type = "button" value = "viewTicket" onclick ="viewTicket2(`+(result[i].t_ID)+`);">View Ticket</button>
						Ticket ID: `+result[i].t_ID+` &nbsp;&nbsp; Ticket Status: `+status[i]+` &nbsp;&nbsp; Context: `+result[i].t_TITLE+`</li><br>
						`;
					}
					document.getElementById('mainbody').innerHTML = 
					`<h3>`+USERNAME+`'s Tickets:</h3>
						<ul>
							`+ticketlist+`
						</ul>
						<br>
					<br>
					`;
				}
			}
			xhr2.open('POST', 'http://localhost:8080/RBSYS/loadticketsbyemployee');
			xhr2.send(JSON.stringify(EID));
			
			
			
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadspecificprofile');
	xhr.send(JSON.stringify(EID));	
}




function filteredTicketMenu(selectorvalue){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);
			var ticketlist = ``;
			var status = [];
			
			let xhr2 = new XMLHttpRequest();
			xhr2.onreadystatechange = function() {
				console.log(this.readyState);
				if (this.readyState == 4 && this.status == 200) {
					var employeelist = JSON.parse(xhr2.responseText);
					var empmap = new Object();
					for(var i =0;i<employeelist.length;i++){
						empmap[employeelist[i].e_ID] = employeelist[i].username;
					}
					function mapget(v){
						return empmap[v];
					}
					var filteredtickets = [];
					for(var i =0;i<result.length;i++){
						if (result[i].r_BOOLEAN == selectorvalue){
							filteredtickets.push(result[i]);
						}
					}
					for(var i =0;i<filteredtickets.length;i++){
						if (filteredtickets[i].r_BOOLEAN == 0){
							status.push("Unapproved");
						} else  if (filteredtickets[i].r_BOOLEAN == 1){
							status.push("Approved");
						} else {
							status.push("Denied");
						}
						ticketlist += `
						<li><button class = "viewticket" type = "button" value = "viewTicket" onclick ="viewTicket2(`+(filteredtickets[i].t_ID)+`);">View Ticket</button>
						Ticket ID: `+filteredtickets[i].t_ID+` &nbsp;&nbsp; Context: `+filteredtickets[i].t_TITLE+` &nbsp;&nbsp; Owner: `+mapget(filteredtickets[i].e_ID)+`</li><br>
						`;
					}
					var headerstring = ""
					if (selectorvalue == 0){
						headerstring += "Open Tickets";
					} else if (selectorvalue == 1){
						headerstring += "Approved Tickets";
					} else {
						headerstring += "Denied Tickets"
					}
					
					document.getElementById('mainbody').innerHTML = 
					`<h3>`+headerstring+`</h3>
						<button type="button" onclick="filteredTicketMenu(0)">View only Open Tickets</button>
						<button type="button" onclick="filteredTicketMenu(1)">View only Approved Tickets</button>
						<button type="button" onclick="filteredTicketMenu(2)">View only Denied Tickets</button>
						<ul>
							`+ticketlist+`
						</ul>
						<br>
						
					<br>
					`;
					
				}
			}
			xhr2.open('POST', 'http://localhost:8080/RBSYS/loademployees');
			xhr2.send();
			
			
			
			
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadalltickets');
	xhr.send();
}