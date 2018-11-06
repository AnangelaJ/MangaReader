package acfb.org;

import org.json.JSONArray;

import acfb.org.json.DBConnect;


//Aplicaremos el patrón de diseño Singleton para manejar sólo una conexion en toda la aplicacion
public class Connection {
	private static DBConnect conn = null;
	private JSONArray result;
	private String [] columnName;
	private int numRows;
	private int numColumns;
	
	public static DBConnect getConnection(String port, String user, String key, String DB) {
		if(conn == null) {
			try {
				conn = new DBConnect(port, user, key, DB);
				System.out.println("Conexion "+conn);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public void Query(String sentence) {
		if(conn != null) {
			try {
				conn.Query(sentence);
				setResult(conn.getJsonResult());
				setColumnName(conn.getColumnName());
				setNumRows(conn.getNumRows());
				setNumColumns(conn.getNumColumn());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Debe crear una conexion");
		}
	}
	
	public void Actions(String sentence) {
		if(conn != null) {
			conn.Action(sentence);
		}else {
			System.out.println("Debe crear una conexion");
		}
	}
	
//-----------------------------------------GETTERS AND SETTERS
	
	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}

	public String[] getColumnName() {
		return columnName;
	}

	public void setColumnName(String[] columnName) {
		this.columnName = columnName;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
}
