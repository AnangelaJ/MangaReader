var xhr = new wrapperXml();
var manga = [];
var mangaId = [];
var idUser;
var x = new Date();
var fecha;
var month = x.getMonth() +1;

fecha = x.getDate()+" "+ month+" "+ x.getFullYear()+" /"+x.getHours()+ ":"+x.getMinutes();

function cargar(){
	console.log("Entro en cargar");
	xhr.get('../Login', {}, function (data){
		console.log("statusLogin: "+data.status);
		if(data.status == "200"){
			idUser = data.idUser;
			//alert("Tamaño del Id: "+idUser.length)
			crear();
			console.log("usuario: "+idUser);
		}else{
			console.log("No entre");
		}
	});	
}


function $(id){
	return document.getElementById(id);
}

function obName(nombre){
	console.log("Tamaño del arreglo: "+manga.length);
	for(var i = 0; i < manga.length; i++){
		//alert("nombre: "+nombre+" NameDB: "+manga[i]);
		if(nombre == manga[i].trim()){
			//alert("MangaID: "+mangaId[i]);
			return mangaId[i];
			break;
		}
	}
}

function crear(){
	console.log("id User: "+idUser);
	var id= obName($('mangaGenero').value);
	console.log("IdGenero: "+id);
	if($('mangaName').value != "" && $('mangaSinopsis').value != ""){
		var json={
				"mangaName": $('mangaName').value,
				"mangaSynopsis": $('mangaSinopsis').value,
				"mangaGenero": id,
				"idUser": idUser,
				"creacion": fecha
		}
		
		console.log(json);
		
		xhr.post('../SubirCap', json, function (data){
			resp = JSON.stringify(data.status);
	    	console.log("Respuesta del Servidor: "+ resp);
	    	if(data.status == "200"){
	    		alert("El Manga se creó exitosamente");
	    		document.location.href="../modules/subirCap.html";
	    	}else{
	    		alert("Lo siento, no se pudo guardar el Manga");
	    		location.reload();
	    	}
		});
	}else{
		alert("Por favor llene todos los campos");
	}
}

function subir(){
	var form = new FormData();
	var xml = new XMLHttpRequest();
	var files = $('files').files;
	console.log("numero de archivos: "+ files.length);
	if($('chapterTitle').value != "" && $('chapterNum').value != "" && $('numPages').value != ""){
		if(files.length == $('numPages').value){
			var t = $('chapterTitle').value;
			if(t.length <= 50){
				for(var i=0; i< files.length; i++){
					var file = files[i];
					form.append('pages[]', file);
				}
				
				form.append("UserId", idUser);
				//alert("UserId: "+idUser);
				form.append("chapterTitle", $('chapterTitle').value);
				form.append("chapterNum", $('chapterNum').value);
				form.append("numPages", $('numPages').value);
				form.append("creationTime", fecha);
				var mangaName = $('CargarManga').value;
				//alert(mangaName);
				form.append("mangaId", obName(mangaName));
				//alert("IdManga: "+obName($('CargarManga').value));

				var status; 
				
				xml.onreadystatechange = function(){
					if(xml.status == 200 && xml.readyState == 4){
						status = 200;
						console.log(status)
						console.log("Respuesta: "+xml.status);
						if(status == "200"){
							alert("Subida de Capitulos exitosa");
							document.location.href = "../modules/capitulos.html"
						}
					}
				}
			
				xml.open("post", "../MangaUp", true);
				xml.send(form);
			}else{
				alert("El titulo no debe pasar los 50 caracteres");
			}
		}else{
			alert("Los archivos subidos deben coincidir con el numero de paginas");
		}
	}else{
		alert("Por favor llene todos los campos");
	}
	
	
	
	/*xhr.post('../MangaUp', form, function (data){
		resp = JSON.stringify(data.status);
    	console.log("Respuesta del Servidor: "+ resp);
    	if(data.status == "200"){
    		document.location.href="#";
    	}else{
    		alert("Lo siento, no se pudo guardar el Capitulo");
    		location.reload();
    	}
	});*/
	
}

function cargarSelect(){
	console.log("cargarSelect");
	xhr.get("../selectManga", {}, function(data){
		
		var genero = $('mangaGenero');
		
		for(var i = 0; i < data.length; i++){
			mangaId[i] = data[i].genero_id;
		    genero.options[i] = new Option(data[i].genero_des);
		    manga[i] = data[i].genero_des;
		}
	});
}

function cManga(){
	xhr.get('../Login', {}, function (data){
		console.log("statusLogin: "+data.status);
		if(data.status == "200"){
			idUser = data.idUser;
			cargarManga();
			console.log("usuario: "+idUser);
		}else{
			console.log("No entre");
		}
	});	
}

function cargarManga(){
	console.log("cargarSelect");
	
	var json ={
			"userId":idUser,
			"page":1
	}
	console.log(json);
	
	xhr.get("../CargarMangas", json, function(data){
		
		var mangas = $('CargarManga');
		
		for(var i = 0; i < data.length; i++){
			
			mangaId[i] = data[i].manga_id;
		    mangas.options[i] = new Option(data[i].manga_nombre);
		    manga[i] = data[i].manga_nombre;
		 }
	});
}
