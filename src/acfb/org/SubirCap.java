package acfb.org;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class SubirCap
 */
@WebServlet("/SubirCap")
public class SubirCap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirCap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		
		DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
		
		if(session.isNew()) {
			System.out.println("Conexion Nueva");
			json.put("status", "403");
			session.invalidate();
		}else {
			System.out.println(request.getParameter("idUser"));
		//	System.out.println(tipo);

				// m = new Manga();
				String g = request.getParameter("mangaGenero");
				System.out.println("Genero: " + g);
				String n = request.getParameter("mangaName");
				String s = request.getParameter("mangaSynopsis");
				String c = request.getParameter("creacion");
				String iu = request.getParameter("idUser");
				
				System.out.println("Crear Manga");
				String query ="INSERT INTO manga (usuario_id, manga_nombre, manga_sinopsis, manga_status, manga_creation_time, genero_id) VALUES("+iu+",'"+n+"','"+s+"',true,'"+c+"',"+g+");";
				
				try {
				conn.Action(query);
				conn.Query("SELECT * FROM manga WHERE usuario_id="+iu.trim()+" AND manga_nombre='"+n+"';");
				
				//Creo la carpeta para almacenar el manga
				JSONObject resp = new JSONObject();
				resp = conn.getJsonResult().getJSONObject(0);
				Object idManga = resp.get("manga_id");
				System.out.println("IdUser:"+iu.trim());
				String ruta = "C:/develop/tomcat/webapps/MangaReaderJ/WebContent/MangaImages/u"+iu.trim();
				System.out.println("RUTA: "+ruta);
				File folder = new File(ruta);
				if(!folder.exists()) {
					folder.mkdir();
					System.out.println("La carpeta no existe");
				}else{
					System.out.println("La carpeta Existe");
					ruta = ruta+"/m"+idManga;
					folder = new File(ruta);
					System.out.println("RUTA MANGA: "+ruta);
					folder.mkdir();
				}
				
				System.out.println("El query: "+query);
				System.out.println("Insertó");
				json.put("status", "200");
				}catch(Exception e) {
					e.printStackTrace();
					json.put("status", "403");
				}	
			}
		out.print(json);
	}
}


