var json;
var xhr = new wrapperXml();
function $(id){
    return document.getElementById(id).value;
}

function Register(){
    var name = $('name');
    var pass = $('password');
    var user = $('username');
    var email = $('email');
    var Rpass = $('Rpass');
    
    var x = new Date();
    var fecha;
    var month = x.getMonth() +1;
    
    fecha = x.getDate()+"-"+ month+"-"+ x.getFullYear()+" /"+x.getHours()+ ":"+x.getMinutes();
    
    if(pass != Rpass){
        alert("Las Contraseñas no coinciden");
        location.reload(true);
    }else{
    	if(name != "" && pass != "" && user != "" && email != ""){
    		var resp;
            json={
                "nombre": name,
                "contrasena": pass,
                "usuario": user,
                "correo": email,
                "fecha": fecha
            }
            
            xhr.post('../Register', json, function (data){
            	resp = JSON.stringify(data.status);
            	console.log("Respuesta del Servidor: "+ resp);
            	if(data.status == "200"){
            		alert("Se registró exitosamente");
            		document.location.href="../modules/Login.html";
            	}else{
            		alert("Error al registrar");
            		location.reload();
            	}
            });
     	
    	}else{
    		alert("Debe llenar todos los campos");
    	}
    }
}