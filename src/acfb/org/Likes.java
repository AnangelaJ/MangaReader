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
 * Servlet implementation class Likes
 */
@WebServlet("/Likes")
public class Likes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Likes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String id = request.getParameter("idCap");
		JSONObject json = new JSONObject();
		
		try{
			DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
			conn.Query("SELECT * FROM likes_chapter WHERE capitulo_id = " + id);
			json.put("status", "200").put("likes", conn.getNumRows());
		}catch(Exception e){
			json.put("status", "403");
			e.printStackTrace();
		}
		
		out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		String userId = request.getParameter("userId");
		String capId = request.getParameter("CapId");
		try{
			DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
			conn.Query("SELECT * FROM likes_chapter WHERE usuario_id= " + userId + " AND capitulo_id= " + capId);
			if(conn.getNumRows() == 0){
				try{

					conn.Action("INSERT INTO likes_chapter (usuario_id, capitulo_id) VALUES (" + userId + ", "+ capId+");");
					json.put("status", "200");
				}catch(Exception e){
					json.put("status", "403");
					e.printStackTrace();
				}
			}else{
				json.put("status", "403");
			}
		}catch(Exception e){
			json.put("status", "403");
			e.printStackTrace();
		}
		
		
		out.print(json);
	}

}
