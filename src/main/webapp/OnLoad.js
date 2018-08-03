//Asynchronous JavaScript And XML
window.onload = function() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var txt = xhr.responseText;
			if (isEmpty(txt)){
				window.location = 'http://localhost:8080/RBSYS/index.html';
			}
			var result = JSON.parse(txt).username;
			document.getElementById('profilebutton').innerHTML = "Profile: [ "+result +" ]"; 
		}

	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loader');
	xhr.send();
}
