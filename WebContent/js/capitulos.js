var img = $('imagen');
var aux = window.location.search
var id = getParameter('id');
var xhr = new wrapperXml();
var rol, idUser;
var Mname, Msinopsis, Mgenero;
var Cruta, Cpage = [], Cnum = [], Cid = [];
var Gname;
var page = 1;
var Clocation;
var j=0;
var numCap;
var cap = 0;
var rut;
verificar();
//llenarPag();

$('next').addEventListener("click", function (){
	console.log("Cpage[cap]: " + Cpage[cap] + " PAGE: " + page);
	if(page < Cpage[cap]){
		page ++;
		Clocation = "";
		Clocation = ".."+rut + "/"+Cid[cap] + "/" + page + ".jpg";
		alert("Location: " + Clocation);
		img.src = Clocation;
	}
});

function verificar(){
//	page =1;
	var json = {}
	xhr.get('../Login', json, function (data){
		
		if(data.status == "200"){
			var resp = [];
			resp[0] = JSON.stringify(data.rol);
			resp [1] = JSON.stringify(data.status);
			$('like').style.display="block";
			$('comment').style.display="block";
			$('comentar').style.display="block";
			idUser = data.idUser;
			rol = data.rol;
			//alert("VERIFICAR: " + idUser);
		}
		llenarPag();
	});
}

function getParameter(id){
	var parameter = aux.split("?");
	for(var i=0; i<parameter.length; i++){
		var parameter2 = parameter[i].split("=");
		if(parameter2[0] == id){
			return parameter2[1];
		}
	}
}

function $(id){
	return document.getElementById(id);
}

function Cap(){
	var json = {
			"page": "3",
			"mangaId": id
	}
	
	xhr.get("../CargarMangas", json, function (data){
		if(data.status == "200"){
			for(var i = 0; i < data.data.length; i++){
				Cruta = data.data[0].chapter_location;
				Cpage[i] = data.data[i].chapter_num_pages;
				Cnum[i] = data.data[i].chapter_number;
				Cid[i] = data.data[i].capitulo_id;
				console.log("Cid[" + i + "] " + Cid[i]);
			}
			numCap = data.capitulo;
			console.log("numCap: " + numCap);
			$('capitulo').innerText = "Capitulo " + Cnum[cap];
			subirCap(Cruta, Cpage[cap]);
			comentarios();
		}else{
			alert("Este manga no tiene Capitulos");
			document.location.href="shonen.html";
		}
	});
}

function manga(){
	json = {
			"page":"4",
			"mangaId": id
	}
	xhr.get("../CargarMangas", json, function (data){
		Mname = data[0].manga_nombre;
		Msinopsis = data[0].manga_sinopsis;
		Mgenero = data[0].genero_id;
		
		$('manga').innerText = Mname;
		$('texto').innerText = Msinopsis;
		
		if(idUser == data[0].usuario_id || rol == "1"){
			$('delete').style.display="block";
		}else{
			$('deleteManga').style.display="none";
		}
		genero();
	});
}

function genero(){
	json = {
			"page":"5",
			"GeneroId": Mgenero
	}
	xhr.get("../CargarMangas", json, function (data){
		Gname = data[0].genero_des;
		$('genero').innerText = Gname;
		obLikes();
		
	});
	
}


function llenarPag(){
	Cap();	
}

function subirCap(Cruta, Cpage){
	var rut2 = Cruta.split("WebContent");
	rut = rut2[1];
	for(var j=0; j < rut2.length; j++){
		console.log("idCap: " + Cid[cap] + " Capitulo: " + cap);
		Clocation = ".."+rut2[1] + "/"+Cid[cap];
	}
	
	console.log("PAGE: " + page);
	//if(page <= Cnum[cap]){
		img.src = Clocation + "/" + page + ".jpg";
		//page ++;
	//}
}

function comentarios(){
	var div1, divs = [];
	var json = {
			"capId": Cid[cap]
	}
	console.log("JSON: " + JSON.stringify(json));
	xhr.get("../comentarios", json, function (data){
		if(data.status == "200"){
			//console.log("CANTIDAD DE DATOS: " + data.data.length);
			div1 = $('comentarios');
			div1.innerHTML = "";
			for(var i = 0; i < data.data.length; i++){
				//LlenarCom(data.data[i].usuario_id, data.data[i].comment_content, data.data[i].comment_creation_time);
				//alert(LlenarCom(data.data[i].usuario_id));
				divs[i] = document.createElement('div');
				divs[i].innerHTML = data.data[i].comment_content + "<br>" + data.data[i].comment_creation_time + "<br><br>";
				//console.log("COMENTARIO "+i+": "+data.data[0].user_username + ": " + comment + "<br>" + creation +"<br><br>");
				div1.appendChild(divs[i]);
				//j++;
			}
		}
		manga();
	});
}


function comentar(){
	console.log($('comentar').value);
	if($('comentar').value !=""){
		var x = new Date();
	    var fecha;
	    var month = x.getMonth() +1;
	    
	    fecha = x.getDate()+" "+ month+" "+ x.getFullYear()+" /"+x.getHours()+ ":"+x.getMinutes();
		var jsonCom = {
				"comentario": $('comentar').value,
				"creation": fecha,
				"capId": Cid[cap],
				"UserId": idUser,
				"modo": "capitulo"
		}
		xhr.post("../comentarios", jsonCom, function (data){
			console.log(data);
			if(data.status == "200"){
				alert("Se envió exitosamente el comentario");
				location.reload();
			}else{
				alert("Error al enviar el comentario");
				location.reload();
			}
			
		})
	}
}

function LlenarCom(idUser, comment, creation){
	var json ={
			"userId": idUser
	}
	xhr.get("../BuscarUsuario", json, function (data){
		if(data.status == "200"){
			//$('comentarios').innerHTML = data.data[0].user_username + ": " + comment + "<br>" + creation +"<br><br>";
			div1 = $('comentarios');
			divs[j] = document.createElement('div');
			divs[j].innerHTML = data.data[0].user_username + ": " + comment + "<br>" + creation +"<br><br>";
			div1.appendChild(divs[j]);
			j++;
		}
	});
}


function like(){
	var json = {
			"userId": idUser,
			"CapId": Cid[cap]
	}
	
	xhr.post("../Likes", json, function (data){
		if(data.status == "200"){
			location.reload();
		}else{
			alert("Error al enviar el Like");
			location.reload();
		}
	})
}

function obLikes(){
	var json = {
			"idCap": Cid[cap]
	}
	
	xhr.get("../Likes", json, function (data){
		$('likes').innerHTML = "A: " + data.likes + " PERSONAS LES GUSTA";
	})
}

function capSiguiente(){
	console.log(cap < numCap);
//	var cap2 = 0;
//	cap2 = cap-1;
	console.log("cap {" + cap + "} < numCap {" + numCap + "}");
	if((cap+1) < numCap){
		alert(cap < numCap);
		cap ++;
		verificar();
	}
}

function capAnterior(){
	console.log(cap > 0);
//	var cap2 = 0;
//	cap2 = cap-1;
	if(cap > 0){
		cap --;
		verificar();
	}
}

function eliminar(){
	var json = {
			"Id" :Cid[cap],
			"options": "2"
	}
	console.log(Cid[cap]);
	xhr.post("../Eliminar", json, function (data){
		if(data.status == "200"){
			alert("Capitulo Eliminado Exitosamente");
			location.reload();
		}else{
			alert("Error al eliminar el capitulo");
			//location.reload();
		}
	});
}

function descargar(){
	//alert(idUser);
	var url = "C:/develop/tomcat/webapps/MangaReaderJ/WebContent" + rut + "/"+Cid[cap] + "/" + page + ".jpg";
	//alert(url);
	//var url = Clocation + "/" + page + ".jpg";
	var url2 = "../MangaDown?url="+ url;
	var downloadWindow = window.open(url2);
}
