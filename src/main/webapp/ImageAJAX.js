//Asynchronous JavaScript And XML
function loadPictureMenu(TID){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(xhr.responseText);	
			var imglist = ``;
			
			for(var i =0;i<result.length;i++){
				var imageblob = result[i].iMAGE;
				var imageID = result.i_ID;
				var objectURL = window.URL.createObjectURL(imageblob);
				var imageObj = new Image();
				imageObj.src = objectURL;
				
				
				imglist += `
				<li><img>imageObj</img>
				Image ID: `+result[i].i_ID+`
				<button type = "button"  onclick ="delImage(`+TID+`,`+(result[i].i_ID)+`);">Remove Image</button>
				</li><br>
				`;
			
			}
			
			document.getElementById('mainbody').innerHTML = 
			`<h3>Ticket #`+TID+`'s Images:</h3>
				<ul>
					`+imglist+`
				</ul>
				<br>
				<form id = "add_image" method = "post">
					<label>Upload New Image:</label>
					<input type="image"></input>
					<button type = "button" onclick ="addImage(`+TID+`);">Upload Image</button>
				</form>
				
			<br>
			`;
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/loadimagemenu');
	xhr.send(JSON.stringify(TID));
}

function addImage(TID){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			loadPictureMenu(TID);
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/addimage');
	var IMAGEBLOB = document.getElementById('add_image').elements[0].value;
	var json = JSON.stringify({
		i_ID : ticketID,
		t_ID : 0,
		iMAGE: IMAGEBLOB
	});
	xhr.send(json);
}

function delImage(TID,IID){
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(this.readyState);
		if (this.readyState == 4 && this.status == 200) {
			loadPictureMenu(TID);
		}
	}
	xhr.open('POST', 'http://localhost:8080/RBSYS/delimage');
	xhr.send(JSON.stringify(IID));
}
