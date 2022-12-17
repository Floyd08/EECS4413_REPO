
/****
 * SHOPPING CART
 * 
 */

/**
 * FUNCTIONALITIES
 * all items
 * remove values
 * can increase or reduce quantity 
 * and get total
 */

var cartitems;
var numitems;

function LoadPage(){

    // instantiate a headers object 
    var myHeaders = new Headers();
    // add content type header to object
    myHeaders.append("Content-Type", "application/json");

    //using built-in JSON utility package turn object to string and store in a variable
    var task = JSON.stringify({"Service":"ShoppingCart", "Method":"addToCart", "Parameters":"{\"ip\":\"u999ip\","
				+ "\"userID\":\"u999\","
				+ "\"Item\":{"
				+ "\"quantity\":\"100\","
				+ "\"price\":\"100.00\","
				+ "\"name\":\"testItem\","
				+ "\"description\":\"This is a test item\","
				+ "\"ID\":\"t001\","
				+ "\"type\":\"food\","
				+ "\"brand\":\"burnbrae\"}}"});
    // create a JSON object with parameters for API call and store in a variable
    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: task,
        redirect: 'follow'
    };


    
    task = JSON.stringify({"Service":"ShoppingCart", "Method":"getCart", "Parameters":"u999"});
    // create a JSON object with parameters for API call and store in a variable
    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: task,
        redirect: 'follow'
    };


    fetch("https://bj837ys678.execute-api.us-east-1.amazonaws.com/test", requestOptions)
    .then(response => response.text())
    .then(test => JSON.parse(test))
    .then(test1 => JSON.parse(test1))
    .then(result =>{
        cartitems = JSON.parse(JSON.stringify(result.body));
        sayaazheng();
        alert( JSON.parse(JSON.stringify(result.body)) );
    })
    .catch(error => alert(error));
    calculateTotal();

}


function sayaazheng(){
    for (laaye in cartitems){
        azhutaaye(laaye.getAttribute("name"), laaye.getAttribute("price"), laaye.getAttribute("quantity"))
        numitems[i] = laaye.getAttribute("quantity");
    }
    
}
       
function subtract(){
    var product = document.getElementById("remove").getAttribute('class');
    
    var id = cartitems.get(product);
    numitems[id] = numitems[id] - 1;
    sayaazheng();
    calculateTotal();

}

function remove(){ 
    var product = document.getElementById("remove").getAttribute('class');
    
    cartitems.remove(product);


    var ul = document.getElementById("dynamic-list");
    var li = document.getElementById(product);
    

    sayaazheng();
    calculateTotal();
}

function add(){

    var product = document.getElementById("add").getAttribute('class');
    
    var id = cartitems.get(product);
    numitems[id] = numitems[id] + 1;
    sayaazheng();
    calculateTotal();

}

function calculateTotal(){
    var total = 0;
    for(item in cartitems){
        
        var itemPrice;
        var times = document.getElementById(item).innerHTML(); 
        total = total + (itemPrice * times);

    }

    
    document.getElementById("totalvalue").innerHTML = total;


}


function azhutaaye(nomi, baalor, kaantidaad){
    var ul = document.getElementById("dynamic-list");
    var li = document.createElement("li");
    li.setAttribute('id', nomi);

    var ah = document.createElement("a");
    ah.setAttribute('href', "Product.html");
    ah.setAttribute('id', nomi);
    var price = document.createElement("div");
    price.innerHTML = baalor;
    var baalori = document.createElement("div");


    var addb = document.createElement("button");
    addb.setAttribute('id', "add");
    addb.setAttribute('onclick', "add();");
    addb.setAttribute('class', nomi);
    var idtimes = document.createElement("span");
    idtimes.setAttribute('id', "times");
    var subtractb = document.createElement("button");
    subtractb.setAttribute('id', "subtract");
    subtractb.setAttribute('onclick', "subtract();");
    subtractb.setAttribute('class', nomi);
    var removeb = document.createElement("button");
    removeb.setAttribute('id', "remove");
    removeb.setAttribute('onclick', "remove();");
    removeb.setAttribute('class', nomi);

    baalori.appendChild(addb);
    baalori.appendChild(idtimes);
    baalori.appendChild(subtractb);
    baalori.appendChild(removeb);



    ah.appendChild(document.createTextNode(nomi));
    li.appendChild(ah);
    li.appendChild(baalori);
    ul.appendChild(li);

}

function CallCheckout(name, price, description, quantity){
    var name = name;
    var price = price;
    var description = description;
    var quantity = quantity;
    location.replace("./checkout.html");
}