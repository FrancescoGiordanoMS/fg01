package francesco.giordano.fg01.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;

public class GetAutomaticField {

	public GetAutomaticField() {
		
	}
	
	public Object getField(Object sig, ResultSet res2) {
		Class<?> c = sig.getClass();
		ResultSetMetaData rsmd;
		Object rv = null;

		try {
			Method m = null;
			rsmd = res2.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String nameCol, setMethod="", typeField;
			Class[] cArg = new Class[1];

			for(int ix=1; ix <= columnsNumber; ix++) {
				nameCol=res2.getMetaData().getColumnName(ix);
				typeField=res2.getMetaData().getColumnTypeName(ix);
				setMethod = "set" + nameCol.substring(0, 1).toUpperCase() + nameCol.substring(1, nameCol.length());		

				switch(typeField) {
				case "VARCHAR":
					cArg[0] = String.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getString(ix));
					break;
				case "INTEGER":
					cArg[0] = int.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getInt(ix));
					break;
				case "DATETIME":
					cArg[0] = LocalDate.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getDate(ix).toLocalDate());
					break;
				case "BLOB", "LONGBLOB":
					cArg[0] = Blob.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getBlob(ix));
				}
 			} 
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return sig;
	}
	
	
}
