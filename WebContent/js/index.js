var xhr = new wrapperXml();
var mangaId=[];
var manga = [];

function $(id){
	return document.getElementById(id);
}

function cargar(){
	xhr.get('./Login', {}, function (data){
		var resp = [];
		resp[0] = JSON.stringify(data.rol);
		console.log("rol: "+resp[0]);
		resp [1] = JSON.stringify(data.status);
		console.log("status: "+resp[1]);
		if(data.status == "200"){
			console.log("Hay sesion");
			document.getElementById('registro').style.display="none";
			document.getElementById('inicio2').style.display="none";
		}else{
			document.getElementById('logOut').style.display="none";
			document.getElementById('MangaUp').style.display="none";
			document.getElementById('ChapterUp').style.display="none";
		}
	});
}

