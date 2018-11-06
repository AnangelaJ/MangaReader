function wrapperXml(){
    var xhr = null;

    var json = function (params){    
        if(params == null){
            return "";
        }else{
            var cad = "";
            var name = Object.keys(params);
            for(var i=0; i< name.length; i++){
                if(cad==""){
                    cad += "&"+name[i]+"= "+params[name[i]];
                }else{
                    cad += " &"+name[i]+"= "+params[name[i]]; 
                }
            }
            return cad;
        }
    }
    

    var callback = function(metodo, url, cadena,cb){
        //var data = null;
        try{
             xhr  = new XMLHttpRequest();
             xhr.onreadystatechange = function(){
                if(xhr.status == 200 && xhr.readyState == 4){
                    var data = JSON.parse(xhr.responseText);
                    cb(data);
                }
            }
            xhr.open(metodo, url, true);
        }catch(err){
            cb(err);
        }
        
        if(metodo== "GET"){
            xhr.send();
        }else if(metodo== "POST"){
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send(cadena);
        }else if( metodo == "PUT"){
            xhr.send();
        }
    }
    

    this.get = function(url,parametros, cb){
        if(parametros != null){
           if(typeof parametros != "string"){
                parametros = json(parametros)+"&method=GET";
                url += "?"+parametros;
            }
        }
        callback("GET",url, parametros, cb);
    }
    

    this.post = function(url, parametros, cb){
        if(typeof parametros != "string"){
            parametros = json(parametros)+" &method= POST";
        }
        callback("POST", url, parametros, cb);
    }
    

    this.put = function(url, parametros, cb){
        if(typeof parametros != "string"){
            parametros = json(parametros);
        }
         callback("PUT",url, parametros, cb);
    }
}
