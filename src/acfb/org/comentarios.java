package acfb.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class comentarios
 */
@WebServlet("/comentarios")
public class comentarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public comentarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String id = request.getParameter("capId");
		System.out.println("capId: " + id);
		try{
			DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
			conn.Query("SELECT * FROM comments_chapter WHERE capitulo_id="+id);
			System.out.println("FILAS: " + conn.getNumRows());
			if(conn.getNumRows() <= 0){
				json.put("status", "403");
				//out.print(json);
			}else{
				JSONArray jsonA = new JSONArray();
				json.put("data", conn.getJsonResult()).put("status", "200");
				jsonA = conn.getJsonResult();
				//out.print(jsonA);
			}
			out.print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String userId = request.getParameter("UserId");
		String comentario = request.getParameter("comentario");
		String creation = request.getParameter("creation");
		String capId = request.getParameter("capId");
		String modo = request.getParameter("modo");
		
		if(modo.trim().equals("capitulo")){
			String campos = "usuario_id, capitulo_id, comment_content, comment_creation_time";
			String valores = userId + ", " + capId + ", '" + comentario + "', '" + creation +"'";
			System.out.println("CAMPOS: " + campos);
			System.out.println("VALORES: " + valores);
			
			try{
				DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
				conn.Action("INSERT into comments_chapter ("+campos+") VALUES ("+valores+");");
			}catch(Exception e){
				e.printStackTrace();
			}
			json.put("status", "200");
			out.print(json);
		}
	}

}
