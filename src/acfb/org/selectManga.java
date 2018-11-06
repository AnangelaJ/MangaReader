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
 * Servlet implementation class selectManga
 */
@WebServlet("/selectManga")
public class selectManga extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static int[] mangaId;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectManga() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("selectManga");
		PrintWriter out = response.getWriter();
		DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
		conn.Query("SELECT * FROM genero");
		JSONArray json = new JSONArray();
		JSONObject jsonE = new JSONObject();
		json = conn.getJsonResult();
		System.out.println("JSON: "+json);
		out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
