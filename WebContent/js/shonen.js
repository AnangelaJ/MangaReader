var Div;
var xhr = new wrapperXml();
var btn = [];

crearDiv();



function LogOut(){
	console.log("Entre en logout");
	xhr.get('../Logout', {}, function (data){
		resp = JSON.stringify(data.status);
		if(data.status == "200"){
			alert("Hasta Luego");
			document.location.href="../index.html";
		}else{
			alert("Error al cerrar sesion");
			location.reload();
		}
	});
}
	

function obtenerEl(id){
    return document.getElementById(id);
}

function crearEl(id){
    return document.createElement(id);
}

function ObtenerMangas(){
	var json ={
			"page": "2" 
	}
	
	var mangaId = [];
	var manga = [];
	
	xhr.get("../CargarMangas", json, function(data){
			console.log(data.length);
			for(var i = 0; i < data.length; i++){
				
				mangaId[i] = data[i].manga_id;
			    manga[i] = data[i].manga_nombre;
			    btn[i] = crearEl('button');
			    btn[i].setAttribute("class", "dinamicButton");
			    console.log("Id: "+data[i].manga_id+ "Nombre: "+data[i].manga_nombre);
			    btn[i].id = mangaId[i];	
				btn[i].innerText = manga[i];
				btn[i].addEventListener("click", enviarCap); 
			    Div.appendChild(btn[i]);
			 }
		});
	
}

function enviarCap(event){
	var id = event.target.id;
	console.log("IdManga: "+ id);
	document.location.href = "../modules/capitulos.html?id="+id;	
}

function crearDiv(){
	Div = crearEl('div');
	Div.style.top = "50px";
	Div.style.left = "20px";
	//Div.style.background = "rgba(199,176,143,0.5)";
	Div.style.width = "200px";
	Div.style.height = "100px";
	Div.style.display = "block";
	Div.style.position = "absolute";
	var div1 = obtenerEl('page-wrapper');
	console.log(div1);	
	div1.appendChild(Div);
	
	ObtenerMangas();
}