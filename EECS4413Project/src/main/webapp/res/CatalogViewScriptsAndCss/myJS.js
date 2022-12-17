/****
CATALOG VIEW SCRIPTS (This includes Catalog.html and Product.html)
 ****/
 
/***	Catalog.html script	***/
function CallProductPage(name, id, price, description, quantity){
	var name = name;
	var id  = id;
	var price = price;
	var description = description;
	var quantity = quantity;
	var params = new URLSearchParams();
	params.append("name", name);
	params.append("id", id);
	params.append("price", price);
	params.append("description", description);
	params.append("quantity", quantity);
	var url = "Product.html?" + params.toString();
  	location.href = url;		
}
/***	Catalog.html script	***/
function CallLogoutAPI(username){
	var username = username;
	console.log("logout api function called username="+username);
	///Instantiate a headers object for API call
	var myHeaders = new Headers();
	//Add content type header to object for API call
	myHeaders.append("Content-Type", "application/json");
	//Using built in JSON utility package turn to object to string and store in a variable
	var task;
	task = JSON.stringify({"Service":"IdentityManager", "Method":"logOut", "Parameters":username} );
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
	.then(ans => { if(ans.statusCode == 200){
		sessionStorage.setItem("username", null);
		var url = "Catalog.html";
  		location.href = url;
		}
		else{alert("logout didn't work.");console.log(ans+ "  username was "+username);}})
	.catch(error => {alert(error);console.log(error);});	
}
/***	Catalog.html script	***/
function LoadCatalogPage(){
	/*/If user is logged-in add welcome message at to of page/*/
		//Get Parameters from url
		var username = sessionStorage.getItem("username");
		if(username != "null" & username != null){
			//remove login and register links
			document.getElementById("login-link").innerHTML="";
			document.getElementById("register-link").innerHTML="";
			//add Hi, username
			document.getElementById("login-link").removeAttribute("href");
			document.getElementById("login-link").innerHTML="Hi,"+username;
			document.getElementById("register-link").setAttribute("href", "#");
			document.getElementById("register-link").innerHTML="Log out";
			document.getElementById("register-link").setAttribute("onclick", "CallLogoutAPI("+"\'"+username+"\'"+")");
		}
		else{
			//show login and register links
			document.getElementById("login-link").innerHTML="LOGIN";
			document.getElementById("login-link").setAttribute("href", "Login.html");
			document.getElementById("register-link").innerHTML="REGISTER";
			document.getElementById("register-link").setAttribute("href", "Register.html");
			console.log(username);
		}
	//Clear page from previous products being displayed
	document.getElementsByClassName("products")[0].innerHTML = "";
	/*/Get which product filtering checkboxes were checked if any/*/
	var data = new FormData();
  	let checkedElements = document.querySelectorAll("#refine-form input:checked");
  	for (let field of checkedElements) {
		data.append(field.id, field.value); 
  	} 
  	let data_val = "";
  	for (const value of data.values()) {
  		data_val = data_val+value+",";
	}
	data_val = data_val.substring(0, data_val.length-1);
  	var num_s=0;
  	var num_b=0;
  	var num_t=0;
  	var data_length = 0;  
  	//Count up how many of each checkbox category was checked (How many brands, How many types, How many see-all)	  	
  	for (var key of data.keys()) {
		if (key.substring(0,7) == "see-all") {num_s = num_s + 1;}
		else if (key.substring(0,5) == "brand") {num_b = num_b + 1;}
		else if (key.substring(0,4) == "type") {num_t = num_t + 1;}		
		else {}
		data_length = data_length + 1;
	}
	/*/ Decide which method to use depending on the checkboxes checked /*/
	var method;
	if (data_length == num_s){
		method = "viewAll";
		//Keep correct checkboxes checked.
		document.getElementById("see-all-checkbox").checked = true;
		document.getElementById("refine-form").style.display = "none";
	}
	else if(data_length == num_b) {
		method = "viewByBrand";
		//Keep correct checkboxes checked.
		document.getElementById("refine-form").style.display = "none";
	}
	else if(data_length == num_t) {
		method = "viewByType";
		//Keep correct checkboxes checked.
		document.getElementById("refine-form").style.display = "none";
	}
	else {
		method = "viewAll";
		//Keep correct checkboxes checked.
		for (const id of data.keys()) {document.getElementById(id).checked = false;}
		document.getElementById("see-all-checkbox").checked = true;
		//Alert if checkboxes of different categories are selected together (Ex:See-all and Brand)
		alert ("Please select within one refining category at a time (See-all, byBrand, or byType).");
	}
	//Instantiate a headers object for API call
	var myHeaders = new Headers();
	//Add content type header to object for API call
	myHeaders.append("Content-Type", "application/json");
	//Using built in JSON utility package turn to object to string and store in a variable
	var task;
	task = JSON.stringify({"Service":"Catalog", "Method":method, "Parameters":data_val} );
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
	.then(result => { 
		/*/ Create product entries according to API response /*/
		var count = 0;
		var end;
		for (var i=1; i<result.body.length+1; i++){
			for (var j=1; j<4; j++){
				if(count >= result.body.length){end=1; break;}
				else{
					//Initialize variables
					var prod_id = "p_"+i+"_"+j; 			
					var outter_div = document.createElement('div');
					var inner_div = document.createElement('div');
					var inner_div_1 = document.createElement('div');
					var inner_div_2 = document.createElement('div');
					var prod_img =  document.createElement('a');
					var prod_img_img =  document.createElement('img');
					var prod_name = document.createElement('a');
					var prod_price = document.createElement('div');
					//Get product attributes from API response
					let name = JSON.parse(JSON.stringify(result.body[count])).name;
					let id = JSON.parse(JSON.stringify(result.body[count])).ID;
					let price= JSON.parse(JSON.stringify(result.body[count])).price;
					let description = JSON.parse(JSON.stringify(result.body[count])).description;
					let quantity = JSON.parse(JSON.stringify(result.body[count])).quantity;
					//Set html element attributes product		
					outter_div.className = "product";
					outter_div.id = prod_id;
					inner_div.className = "s-product";
					inner_div_1.className = "s-product-image";
					prod_img.className = "s-product-image";
					prod_img_img.id = "product#"+(count+1);
					prod_img_img.width = "370";
					prod_img_img.src = "res/product-images/"+prod_id+".jpg";
					inner_div_2.className = "s-product-description";
					prod_name.className = "s-product-name";
					prod_name.innerHTML = name;
					prod_price.className = "s-product-price";
					prod_price.innerHTML = "C$ "+price;
					//Build the html element for product
					outter_div.appendChild(inner_div);
					inner_div.appendChild(inner_div_2);
					inner_div_2.appendChild(prod_price);
					inner_div_2.insertBefore(prod_name, prod_price);
					inner_div.insertBefore(inner_div_1, inner_div_2);
					inner_div_1.appendChild(prod_img);
					prod_img.appendChild(prod_img_img);
					document.getElementsByClassName("products")[0].appendChild(outter_div);
					document.getElementById("product#"+(count+1)).setAttribute("onclick", "CallProductPage("+"\'"+name+"\'"+","+"\'"+id+"\'"+","+price+","+"\'"+description+"\'"+","+quantity+")");
					count = count + 1;
					end=0;					
				}
			}
			if(end == 1){break;}
		}		
	}) 
	.catch(error => {alert(error);});
}
/***	Product.html script	***/
function CallAddToCartAPI(/*add user name*/id){
	var user_ip_address = "12.237.119.124";
	var userID = "default_user";
	var itemID = id;
	var quantity_wanted = document.getElementById("quantity-wanted-dropdown").value;
	///Instantiate a headers object for API call
	var myHeaders = new Headers();
	//Add content type header to object for API call
	myHeaders.append("Content-Type", "application/json");
	//Using built in JSON utility package turn to object to string and store in a variable
	var params;
	params = JSON.stringify({"ip": user_ip_address, "userID":userID, "Item":itemID, "quantity_wanted":quantity_wanted} );
	var task;
	task = JSON.stringify({"Service":"ShoppingCart", "Method":"addToCart", "Parameters":params} );
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
	.then(ans => { if(ans.statusCode == 200){
		alert("Item(s) added to cart!");
		document.getElementById("quantity-wanted-dropdown").value = 1;
		}
		else{console.log("api says not added to cart"+ ans.statusCode);}})
	.catch(error => {alert(error);});
}
/***	Product.html script	***/
function CallReviewAPI(fullname, review){
	var name = fullname.split(" ")[0];
	var surName;
	if (fullname.split(" ")[1] == undefined){
		surName = "";
	}else{surName = fullname.split(" ")[1];}
	var userID = "default_user";
	var item_params = new URLSearchParams(window.location.search),
	itemID = item_params.get("id");
	var comment = review;
	///Instantiate a headers object for API call
	var myHeaders = new Headers();
	// add content type header to object for API call
	myHeaders.append("Content-Type", "application/json");
	//Using built in JSON utility package turn to object to string and store in a variable
	var params;
	params = JSON.stringify({"itemID": itemID, "userID":userID, "name":name, "surName":surName, "comment":comment} );
	var task;
	task = JSON.stringify({"Service":"Catalog", "Method":"addReview", "Parameters":params} );
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
			alert("Your review has been added!"); 
			document.getElementById('fullname').value = "";
			document.getElementById('review').value = "";	
		}
		else{alert("Please do not use special characters in your review or name.");}
		})
	.catch(error => {alert(error);});
}
/***	Product.html script	***/
function LoadProductPage(){
	/*/If user is logged-in add welcome message at to of page/*/
		//Get Parameters from url
		var username = sessionStorage.getItem("username");
console.log("username= "+username);
		if( username != "null" & username != null){
			//remove login and register links
			document.getElementById("login-link").innerHTML="";
			document.getElementById("register-link").innerHTML="";
			//add Hi, username
			document.getElementById("login-link").removeAttribute("href");
			document.getElementById("login-link").innerHTML="Hi,"+username;
			document.getElementById("register-link").setAttribute("href", "#");
			document.getElementById("register-link").innerHTML="Log out";
			document.getElementById("register-link").setAttribute("onclick", "CallLogoutAPI("+"\'"+username+"\'"+")");
		}
		else{
			//show login and register links
			document.getElementById("login-link").innerHTML="LOGIN";
			document.getElementById("login-link").setAttribute("href", "Login.html");
			document.getElementById("register-link").innerHTML="REGISTER";
			document.getElementById("register-link").setAttribute("href", "Register.html");
		}
	//Get Parameters from url
	var item_params = new URLSearchParams(window.location.search),
	name = item_params.get("name"),
	id = item_params.get("id"),
	price = item_params.get("price"),
	description = item_params.get("description");
	stock_quantity = item_params.get("quantity");
	/*/ Create product page according to product attributes /*/
	//Initialize variables
	var prod_id = "p_1_1";	//Pls Chanage to id 			
	var prod_img_div = document.createElement('div');
	var prod_img_img =  document.createElement('img');
	var prod_info_div = document.createElement('div');
	var prod_info_descripton_div = document.createElement('div');
	var prod_info_descripton_name =  document.createElement('a');
	var prod_info_descripton_price = document.createElement('div');
	var prod_action_div = document.createElement('div');
	var prod_action_AddToCart_button = document.createElement('button');
	var prod_action_LeaveAReview_button = document.createElement('button');
	var prod_action_quantity_dropdown_label = document.createElement('label');
	var prod_action_quantity_dropdown = document.createElement('select');
	for(i=0;i<(stock_quantity);i++){
		var prod_action_quantity_dropdown_option = document.createElement("option");
        prod_action_quantity_dropdown_option.value = i+1;
        prod_action_quantity_dropdown_option.text = i+1;
        prod_action_quantity_dropdown.appendChild(prod_action_quantity_dropdown_option);
	}
	var prod_info_descripton_description_div = document.createElement('div');
	var prod_info_descripton_description = document.createElement('div');
	var prod_info_descripton_description_title = document.createElement('a');
	//Set html element attributes for product		
	prod_img_div.className = "product-image";
	prod_info_div.className = "product-info";
	prod_img_img.width = "400";
	prod_img_img.src = "res/product-images/"+prod_id+".jpg";
	prod_info_descripton_div.className = "product-description";
	prod_info_descripton_name.className = "s-product-name";
	prod_info_descripton_name.innerHTML = name;
	prod_info_descripton_price.className = "s-product-price";
	prod_info_descripton_price.innerHTML = "C$ "+price;
	prod_action_div.className = "product-action";
	prod_action_AddToCart_button.id = "add-to-cart-button";
	prod_action_AddToCart_button.type = "button";
	prod_action_AddToCart_button.innerHTML = "Add to cart";
	prod_action_LeaveAReview_button.id = "leave-a-review-button";
	prod_action_LeaveAReview_button.type = "button";
	prod_action_LeaveAReview_button.innerHTML = "Leave a review";
	prod_action_quantity_dropdown.id = "quantity-wanted-dropdown";
	prod_action_quantity_dropdown.name = "quantity-wanted-dropdown";
	prod_action_quantity_dropdown_label.for = prod_action_quantity_dropdown.name;
	prod_action_quantity_dropdown_label.innerHTML = "Quantity : "
	prod_info_descripton_description_div.className = "product-description";
	prod_info_descripton_description.className = "s-product-description-description";
	prod_info_descripton_description.innerHTML = description;
	prod_info_descripton_description_title.className = "s-product-description-title";
	prod_info_descripton_description_title.innerHTML = "PRODUCT DETAILS";
	//Build the html element for product
	prod_img_div.appendChild(prod_img_img);
	prod_action_div.appendChild(prod_action_quantity_dropdown);
	prod_action_div.insertBefore(prod_action_quantity_dropdown_label, prod_action_quantity_dropdown);
	prod_action_div.insertBefore(prod_action_LeaveAReview_button, prod_action_quantity_dropdown_label);
	prod_action_div.insertBefore(prod_action_AddToCart_button, prod_action_LeaveAReview_button);	
	prod_info_descripton_div.appendChild(prod_info_descripton_price);
	prod_info_descripton_div.insertBefore(prod_info_descripton_name, prod_info_descripton_price);
	prod_info_descripton_description_div.appendChild(prod_info_descripton_description);
	prod_info_descripton_description_div.insertBefore(prod_info_descripton_description_title, prod_info_descripton_description);
	prod_info_div.appendChild(prod_info_descripton_description_div);
	prod_info_div.insertBefore(prod_action_div, prod_info_descripton_description_div);
	prod_info_div.insertBefore(prod_info_descripton_div, prod_action_div);
	document.getElementsByClassName("product")[0].appendChild(prod_info_div);
	document.getElementsByClassName("product")[0].insertBefore(prod_img_div, prod_info_div);
//					var quantity_wanted = document.getElementById("quantity-wanted-dropdown").value;					
					document.getElementById("add-to-cart-button").setAttribute("onclick", "CallAddToCartAPI("+"\'"+id+"\'"/*+","+quantity_wanted*/+")");

	document.getElementById("leave-a-review-button").setAttribute("onclick", "document.getElementById("+"\'"+"fullname"+"\'"+").focus()");
	
	
}	

/*

<div class="testimonial-box">
	        	<div class="user-name-2">
	            	<strong>Touseeq Ijaz</strong>
	            	<span>@touseeqijazweb</span>
				</div>
				<div class="client-comment-2">
	        		<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Exercitationem, quaerat quis? Provident temporibus architecto asperiores nobis maiores nisi a. Quae doloribus ipsum aliquam tenetur voluptates incidunt blanditiis sed atque cumque.</p>
				</div>                
	        </div>
	        */



