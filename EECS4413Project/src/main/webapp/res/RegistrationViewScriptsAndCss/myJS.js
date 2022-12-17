/****
REGISTRATION VIEW SCRIPTS (This includes Login.html and Register.html)
 ****/
 
/***	Login.html script	***/
function CallLoginAPI(email, password){
    var id = email;
	var pass = password;
	///Instantiate a headers object for API call
	var myHeaders = new Headers();
	//Add content type header to object for API call
	myHeaders.append("Content-Type", "application/json");
	//Using built in JSON utility package turn to object to string and store in a variable
	var params;
	params = JSON.stringify({"id": id, "pass":pass} );
	var task;
	task = JSON.stringify({"Service":"IdentityManager", "Method":"logIn", "Parameters":params} );
	//Create a JSON object with parameters for API call and store in a variable
	var requestOptions = {
		method: 'POST',
		headers: myHeaders,
		body: task,
		redirect: 'follow'
	};		
    /*/ Call API /*/
	fetch("https://bj837ys678.execute-api.us-east-1.amazonaws.com/test", requestOptions)
    .then(response => response.text())
	.then(test => JSON.parse(test))
	.then(test1 => JSON.parse(test1))
	.then(ans => { 
		if(ans.statusCode == 200){
			sessionStorage.setItem("username", id);
			var url = "Catalog.html";
 	 		location.href = url;
		}
		else{alert(ans.body);}})
	.catch(error => {alert(error);});
}
/***	Login.html script	***/
function CallRegisterAPI(username, password, fname, sname, pcode, addrs){
    var id = username;
	var pass = password;
    var nomi = fname;
	var aile = sname;
    var postal = pcode;
	var address = addrs;	
	///Instantiate a headers object for API call
	var myHeaders = new Headers();
	//Add content type header to object for API call
	myHeaders.append("Content-Type", "application/json");
	//Using built in JSON utility package turn to object to string and store in a variable
	var params;
	params = JSON.stringify({"id": id, "pass":pass, "nomi": nomi, "aile":aile, "postal":postal, "address":address} );
	var task;
	task = JSON.stringify({"Service":"IdentityManager", "Method":"register", "Parameters":params} );
	//Create a JSON object with parameters for API call and store in a variable
	var requestOptions = {
		method: 'POST',
		headers: myHeaders,
		body: task,
		redirect: 'follow'
	};		
    /*/ Call API /*/
	fetch("https://bj837ys678.execute-api.us-east-1.amazonaws.com/test", requestOptions)
    .then(response => response.text())
	.then(test => JSON.parse(test))
	.then(test1 => JSON.parse(test1))
	.then(ans => { 
		if(ans.statusCode == 200){
			sessionStorage.setItem("username", id);
			var url = "Catalog.html";
 	 		location.href = url;
		}
			else{alert(ans.body);}})
	.catch(error => {alert(error);});
}
