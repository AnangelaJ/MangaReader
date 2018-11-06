package acfb.org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class MangaUp
 */
@WebServlet("/MangaUp")
@MultipartConfig
public class MangaUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MangaUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Entro en el doPost");
		Collection<Part> files = request.getParts();
		InputStream fileContent = null;
		OutputStream os = null;
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		//Declaro las variables para almacenarlas en la BD
		String user = request.getParameter("UserId");
		String chapterTitle = request.getParameter("chapterTitle");
		String chapterNum = request.getParameter("chapterNum");
		String numPages = request.getParameter("numPages");
		String creation = request.getParameter("creationTime");
		String idManga = request.getParameter("mangaId");
		
		System.out.println("USER: "+user+" idManga: "+idManga);
		//Creo la carpeta donde almaceno las imagenes de los capitulos
		String ruta = "C:/develop/tomcat/webapps/MangaReaderJ/WebContent/MangaImages/u" + user+"/m"+ idManga;
		//System.out.println("RUTA: "+ruta);
	
		//System.out.println("RUTA: "+ruta);
		
		System.out.println(System.getProperty("user.dir"));
		try{
			//Hago la conexion a la BD y las Sentencias SQL
			DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
			String campos= "manga_id, chapter_number, chapter_title, chapter_creation_time, chapter_location, chapter_num_pages";
			String valores = idManga+"," + chapterNum + ",'" + chapterTitle+"','" +creation +"','" + ruta + "'," + numPages;
			
			conn.Action("INSERT INTO capitulo (" + campos + ") VALUES (" + valores + ");");
			conn.Query("SELECT * FROM capitulo WHERE chapter_number="+chapterNum+" AND chapter_creation_time='"+creation+"';");
			JSONObject jsonid = new JSONObject();
			jsonid = conn.getJsonResult().getJSONObject(0);
			Object id = jsonid.get("capitulo_id");
			System.out.println("LO QUE ME DEVUELVE: "+id);
			
			File folder = new File(ruta);
			if(!folder.exists()) {
				ruta = ruta+"/"+id;
				folder = new File(ruta);
				folder.mkdir();
				System.out.println("La carpeta no existe");
			}else{
				System.out.println("La carpeta Existe");
				ruta = ruta+"/"+id;
				folder = new File(ruta);
				System.out.println("RUTA MANGA: "+ruta);
				folder.mkdir();
			}
			
			//Guardo las imagenes en la carpeta
			System.out.println("Tamaño de la coleccion: "+files.size());
			int i=1;
			//ruta1 = ruta1 + "\\p" + i;
			for(Part file : files){
				System.out.println(file.getName());
				System.out.println("tamaño: "+files.size());
				System.out.println((6-i));
				System.out.println("Tamaño de la coleccion: "+file.getSize());
				System.out.println("i: " + i);
				System.out.println(files.size() <= i);
				if(i <= file.getSize()){
					System.out.println("Hola");
				//if(Integer.parseInt(numPages) != (5-i)){
					fileContent = file.getInputStream();
					os = new FileOutputStream(ruta + "/" + i+".jpg");
					int read = 0;
					byte [] bytes = new byte[1024];
					while((read = fileContent.read(bytes)) != -1){
						os.write(bytes, 0, read);
					}
					
					if(fileContent != null){
						fileContent.close();
					}
					
					if(os != null){
						os.close();
					}
					i++;

				}
				else{
					break;
				}
			}
			
			System.out.println("A");
			json.put("status", 200);
			out.print(json);
		}catch(Exception e){
			json.put("status", 403);
			e.printStackTrace();
		}
		out.print(json);
	}
}


