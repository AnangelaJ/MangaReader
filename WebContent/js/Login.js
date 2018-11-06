var json;
var xhr = new wrapperXml();
function $(id){
    return document.getElementById(id);
}

function Login(){
    var pass = $('password').value;
    var user = $('username').value;
    if(pass != "" && user !== ""){
        json={
                "contrasena": pass,
                "usuario": user
            }   
            
            xhr.post('../Login', json, function (data){
            	resp = JSON.stringify(data.status);
            	console.log("Respuesta del Servidor: "+ resp);
            	if(data.status == "200"){
            		alert("Inicio de sesion exitoso");
            		document.location.href="../index.html";
            	}else{
            		alert("Error al iniciar sesion");
            		location.reload();
            	}
            }); 
    }else{
    	alert("Por favor llene todos los campos");
    }

}

function LogOut(){
	console.log("Entre en logout");
	xhr.get('./Logout', {}, function (data){
		resp = JSON.stringify(data.status);
		if(data.status == "200"){
			alert("Hasta Luego");
			location.reload();
		}else{
			alert("Error al cerrar sesion");
			location.reload();
		}
	});
	
}