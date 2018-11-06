package acfb.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class CargarMangas
 */
@WebServlet("/CargarMangas")
public class CargarMangas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarMangas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
		String page = request.getParameter("page");
		String id = request.getParameter("mangaId");
		String Gid = request.getParameter("GeneroId");
		System.out.println("PAGE: "+page.trim());
		JSONArray json = new JSONArray();
		
		if(page.trim().equals("1")){
			System.out.println("Entre en la primera comparacion");
			conn.Query("SELECT * FROM manga WHERE usuario_id= "+request.getParameter("userId") + " AND manga_status = true ORDER BY manga_id");
			json = conn.getJsonResult();
			System.out.println("id Usuario: "+(String)session.getAttribute("idUser"));
			System.out.println("JSON: "+json);
			out.print(json);
		}else if(page.trim().equals("3")){
			System.out.println("Entre en la segunda comparacion");
			System.out.println("MangaId: "+id);
			conn.Query("SELECT * FROM capitulo WHERE manga_id= " + id + " AND status = true ORDER BY capitulo_id");
			JSONObject json2 = new JSONObject();
			if(conn.getNumRows() > 0){
				json2.put("status", "200");
			}
			json2.put("data", conn.getJsonResult()).put("capitulo", conn.getNumRows());
			out.print(json2);
			//json = conn.getJsonResult();
			System.out.println("JSON: "+json);
		}else if(page.trim().equals("4")){
			System.out.println("Entre en la tercera comparacion");
			conn.Query("SELECT * FROM manga WHERE manga_id= "+id + " AND manga_status = true ORDER BY manga_id");
			json = conn.getJsonResult();
			System.out.println("JSON: "+json);
			out.print(json);
		}else if(page.trim().equals("5")){
			System.out.println("Entre en la tercera comparacion");
			conn.Query("SELECT * FROM genero WHERE genero_id= "+Gid);
			json = conn.getJsonResult();
			System.out.println("JSON: "+json);
			out.print(json);
		}else{
			System.out.println("Entre en la tercera comparacion");
			conn.Query("SELECT * FROM manga WHERE manga_status = true ORDER BY manga_id");
			json = conn.getJsonResult();
			System.out.println("JSON: "+json);
			out.print(json);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
