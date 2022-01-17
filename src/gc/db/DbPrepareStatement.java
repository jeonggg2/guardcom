package gc.db;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class DbPrepareStatement {
	
	 // Data Types
    static final int FIELD_TYPE_DECIMAL = 0;
    static final int FIELD_TYPE_TINY = 1;
    static final int FIELD_TYPE_SHORT = 2;
    static final int FIELD_TYPE_LONG = 3;
    static final int FIELD_TYPE_FLOAT = 4;
    static final int FIELD_TYPE_DOUBLE = 5;
    static final int FIELD_TYPE_NULL = 6;
    static final int FIELD_TYPE_TIMESTAMP = 7;
    static final int FIELD_TYPE_LONGLONG = 8;
    static final int FIELD_TYPE_INT24 = 9;
    static final int FIELD_TYPE_DATE = 10;
    static final int FIELD_TYPE_TIME = 11;
    static final int FIELD_TYPE_DATETIME = 12;

    // Newer data types
    static final int FIELD_TYPE_YEAR = 13;
    static final int FIELD_TYPE_NEWDATE = 14;
    static final int FIELD_TYPE_VARCHAR = 15;
    static final int FIELD_TYPE_BIT	= 16;
    static final int FIELD_TYPE_NEW_DECIMAL = 246;
    static final int FIELD_TYPE_ENUM = 247;
    static final int FIELD_TYPE_SET = 248;

    // Older data types
    static final int FIELD_TYPE_TINY_BLOB = 249;
    static final int FIELD_TYPE_MEDIUM_BLOB = 250;
    static final int FIELD_TYPE_LONG_BLOB = 251;
    static final int FIELD_TYPE_BLOB = 252;
    static final int FIELD_TYPE_VAR_STRING = 253;
    static final int FIELD_TYPE_STRING = 254;
    static final int FIELD_TYPE_GEOMETRY = 255;
    
   static int mysqlToJavaType(int mysqlType) {
    	
        int jdbcType;
        switch (mysqlType) {
        case FIELD_TYPE_NEW_DECIMAL:
        case FIELD_TYPE_DECIMAL:
            jdbcType = Types.DECIMAL;
            break;
        case FIELD_TYPE_TINY:
            jdbcType = Types.TINYINT;
            break;
        case FIELD_TYPE_SHORT:
            jdbcType = Types.SMALLINT;
            break;
        case FIELD_TYPE_LONG:
            jdbcType = Types.INTEGER;
            break;
        case FIELD_TYPE_FLOAT:
            jdbcType = Types.REAL;
            break;
        case FIELD_TYPE_DOUBLE:
            jdbcType = Types.DOUBLE;
            break;
        case FIELD_TYPE_NULL:
            jdbcType = Types.NULL;
            break;
        case FIELD_TYPE_TIMESTAMP:
            jdbcType = Types.TIMESTAMP;
            break;
        case FIELD_TYPE_LONGLONG:
            jdbcType = Types.BIGINT;
            break;
        case FIELD_TYPE_INT24:
            jdbcType = Types.INTEGER;
            break;
        case FIELD_TYPE_DATE:
            jdbcType = Types.DATE;
            break;
        case FIELD_TYPE_TIME:
            jdbcType = Types.TIME;
            break;
        case FIELD_TYPE_DATETIME:
            jdbcType = Types.TIMESTAMP;
            break;
        case FIELD_TYPE_YEAR:
            jdbcType = Types.DATE;
            break;
        case FIELD_TYPE_NEWDATE:
            jdbcType = Types.DATE;
            break;
        case FIELD_TYPE_ENUM:
            jdbcType = Types.CHAR;
            break;
        case FIELD_TYPE_SET:
            jdbcType = Types.CHAR;
            break;
        case FIELD_TYPE_TINY_BLOB:
            jdbcType = Types.VARBINARY;
            break;
        case FIELD_TYPE_MEDIUM_BLOB:
            jdbcType = Types.LONGVARBINARY;
            break;
        case FIELD_TYPE_LONG_BLOB:
            jdbcType = Types.LONGVARBINARY;
            break;
        case FIELD_TYPE_BLOB:
            jdbcType = Types.LONGVARBINARY;
            break;
        case FIELD_TYPE_VAR_STRING:
        case FIELD_TYPE_VARCHAR:
            jdbcType = Types.VARCHAR;
            break;
        case FIELD_TYPE_STRING:
            jdbcType = Types.CHAR;
            break;
        case FIELD_TYPE_GEOMETRY:
        	jdbcType = Types.BINARY;        
        	break;
        case FIELD_TYPE_BIT:
        	jdbcType = Types.BIT;
        	break;        	
        default:
            jdbcType = Types.VARCHAR;
        }
        return jdbcType;
        
    }
	
	private Connection con;
	private PreparedStatement pstmt;
	private char sqlCmd;
	public boolean connect(int userNo, byte[] sessionId, String sql)  {
		
		try {
			
			con = DbCon.getConnection(userNo, sessionId);
			if (con == null) return false;
			
			sqlCmd = (char)(sql.charAt(0) | 0x20);
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (pstmt == null) {
				con.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
    public boolean bindParameter(int index, int type, int length, InputStream inputStream) throws Exception {
    	pstmt.setBinaryStream(index, inputStream, length);
    	synchronized (this) { this.wait(); }
    	return true;
    }
    
	public boolean bindParameter(int index, int type, int length, byte[] data) throws Exception {
		
		int jType = mysqlToJavaType(type);
		switch (jType) {
		case Types.FLOAT:
			if (length != 8) return false;
			pstmt.setFloat(index, ((((int)data[7]) & 0xFF) << 56) |
								  ((((int)data[6]) & 0xFF) << 48) |
								  ((((int)data[5]) & 0xFF) << 40) | 
								  ((((int)data[4]) & 0xFF) << 32) | 
								  ((((int)data[3]) & 0xFF) << 24) | 
								  ((((int)data[2]) & 0xFF) << 16) | 
								  ((((int)data[1]) & 0xFF) << 8) |
								  (((int)data[0]) & 0xFF));
			break;
		case Types.DOUBLE:
			if (length != 8) return false;
			pstmt.setDouble(index, ((((int)data[7]) & 0xFF) << 56) |
								   ((((int)data[6]) & 0xFF) << 48) |
								   ((((int)data[5]) & 0xFF) << 40) | 
								   ((((int)data[4]) & 0xFF) << 32) | 
								   ((((int)data[3]) & 0xFF) << 24) | 
								   ((((int)data[2]) & 0xFF) << 16) | 
								   ((((int)data[1]) & 0xFF) << 8) |
								   (((int)data[0]) & 0xFF));
			break;
		case Types.NULL:
			pstmt.setNull(index, jType);
			break;			
		case Types.TINYINT:
		case Types.BIT:
		case Types.BOOLEAN:
			if (length != 1) return false;
			pstmt.setByte(index, data[0]);
			break;
		case Types.SMALLINT:
			if (length != 2) return false;
			pstmt.setShort(index, (short)(((((int)data[1]) & 0xFF) << 8) |
										 (((int)data[0]) & 0xFF)));
			break;
		case Types.INTEGER:
			if (length != 4) return false;
			pstmt.setInt(index, ((((int)data[3]) & 0xFF) << 24) |
								((((int)data[2]) & 0xFF) << 16) |
								((((int)data[1]) & 0xFF) << 8) |
								(((int)data[0]) & 0xFF));
			break;
		case Types.BIGINT:
			if (length != 8) return false;
			pstmt.setLong(index, ((((int)data[7]) & 0xFF) << 56) |
								 ((((int)data[6]) & 0xFF) << 48) |
								 ((((int)data[5]) & 0xFF) << 40) | 
								 ((((int)data[4]) & 0xFF) << 32) | 
								 ((((int)data[3]) & 0xFF) << 24) | 
								 ((((int)data[2]) & 0xFF) << 16) | 
								 ((((int)data[1]) & 0xFF) << 8) |
								 (((int)data[0]) & 0xFF));
			break;
		case Types.LONGNVARCHAR:
		case Types.LONGVARCHAR:
		case Types.VARCHAR:
		case Types.CHAR:
			pstmt.setString(index, new String(data, "euc-kr"));//
			break;
		case Types.DECIMAL:
		case Types.TIMESTAMP:
		case Types.TIME:
		case Types.DATE:						
		case Types.BLOB:						
		case Types.BINARY:
		case Types.ARRAY:
		default:
			pstmt.setBytes(index, data);
			break;
		}
		
		return true;
		
	}
	
	public boolean execute(HttpServletResponse response) throws Exception {
		
		ServletOutputStream sos;
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		int columnCount;
		
		try {
			
			switch (sqlCmd) {
			case 'i':
			case 'u':
			case 'd':
				
				int i = pstmt.executeUpdate();
				synchronized (this) { this.notifyAll(); }

				ByteArrayOutputStream bos = new ByteArrayOutputStream(128);
				bos.write((byte)(i & 0x000000ff));
				bos.write((byte)((i & 0x0000ff00) >> 8));
				bos.write((byte)((i & 0x00ff0000) >> 16));
				bos.write((byte)((i & 0xff000000) >> 24));
				int bufferLength = 4;
				
				rs  =  pstmt.getGeneratedKeys();
				rsmd = rs.getMetaData();
				columnCount = rsmd.getColumnCount();
				bos.write((byte)(columnCount & 0x000000ff));
				bos.write((byte)((columnCount & 0x0000ff00) >> 8));
				bos.write((byte)((columnCount & 0x00ff0000) >> 16));
				bos.write((byte)((columnCount & 0xff000000) >> 24));
				bufferLength += 4;
				
				byte[] key;
				while (rs.next()) {
					
					for (i = 1; i <= columnCount; i++) {
						
						switch (rsmd.getColumnType(i)) {
						case Types.BLOB:						
						case Types.BINARY:
						case Types.ARRAY:
							key = rs.getBytes(i);
							break;
							
						case Types.INTEGER:
							int ik = rs.getInt(i);
							key = new byte[4];
							key[0] = (byte)(ik & 0x000000ff);
							key[1] = (byte)((ik & 0x0000ff00) >> 8);
							key[2] = (byte)((ik & 0x00ff0000) >> 16);
							key[3] = (byte)((ik & 0xff000000) >> 24);
							break;
							
						case Types.BIGINT:
							long lk = rs.getLong(i);
							key = new byte[8];
							key[0] = (byte)(lk & 0x00000000000000ffL);
							key[1] = (byte)((lk & 0x000000000000ff00L) >> 8);
							key[2] = (byte)((lk & 0x0000000000ff0000L) >> 16);
							key[3] = (byte)((lk & 0x00000000ff000000L) >> 24);
							key[4] = (byte)((lk & 0x000000ff00000000L) >> 32);
							key[5] = (byte)((lk & 0x0000ff0000000000L) >> 40);
							key[6] = (byte)((lk & 0x00ff000000000000L) >> 48);
							key[7] = (byte)((lk & 0xff00000000000000L) >> 56);
							break;
							
						default:
							key = null;
							break;
						}
						
						if (key != null) {
							bos.write((byte)(key.length & 0x000000ff));
							bos.write((byte)((key.length & 0x0000ff00) >> 8));
							bos.write((byte)((key.length & 0x00ff0000) >> 16));
							bos.write((byte)((key.length & 0xff000000) >> 24));
							bos.write(key);
							bufferLength += (4 + key.length);
						}
						
					}
					
				}
				
				response.setContentLength(bufferLength);
				
				sos = response.getOutputStream();
				if (sos == null) {
					bos.close();
					throw new Exception("failed to getOutputStream");
				}
				
				sos.write(bos.toByteArray());
				sos.flush();
				sos.close();
				bos.close();
				break;
				
			case 's':
				
				rs = pstmt.executeQuery();
				if (rs == null) {
					throw new Exception("failed to executeQuery");
				}
				
				sos = response.getOutputStream();
				if (sos == null) {
					throw new Exception("failed to getOutputStream");
				}
				
				rsmd = rs.getMetaData();
				columnCount = rsmd.getColumnCount();
				sos.write((byte)(columnCount & 0x000000ff));
				sos.write((byte)((columnCount & 0x0000ff00) >> 8));
				sos.write((byte)((columnCount & 0x00ff0000) >> 16));
				sos.write((byte)((columnCount & 0xff000000) >> 24));
				
				byte[] data;
				while (rs.next()) {
					for (i = 1; i <= columnCount; i++) {
						switch (rsmd.getColumnType(i)) {
						case Types.TINYINT:
						case Types.BIT:
						case Types.BOOLEAN:
							byte b =  rs.getByte(i);
							if (rs.wasNull()) {
								data = null;
							} else {
								data = new byte[1];
								data[0] = b;
							}
							break;
							
						case Types.SMALLINT:
							int is = rs.getShort(i);
							if (rs.wasNull()) {
								data = null;
							} else {
								data = new byte[2];
								data[0] = (byte)(is & 0x00ff);
								data[1] = (byte)((is & 0xff00) >> 8);
							}
							break;
							
						case Types.INTEGER:
							int ik = rs.getInt(i);
							if (rs.wasNull()) {
								data = null;
							} else {
								data = new byte[4];
								data[0] = (byte)(ik & 0x000000ff);
								data[1] = (byte)((ik & 0x0000ff00) >> 8);
								data[2] = (byte)((ik & 0x00ff0000) >> 16);
								data[3] = (byte)((ik & 0xff000000) >> 24);
							}
							break;
						
						case Types.BIGINT:
							long lk = rs.getLong(i);
							if (rs.wasNull()) {
								data = null;
							} else {
								data = new byte[8];
								data[0] = (byte)(lk & 0x00000000000000ffL);
								data[1] = (byte)((lk & 0x000000000000ff00L) >> 8);
								data[2] = (byte)((lk & 0x0000000000ff0000L) >> 16);
								data[3] = (byte)((lk & 0x00000000ff000000L) >> 24);
								data[4] = (byte)((lk & 0x000000ff00000000L) >> 32);
								data[5] = (byte)((lk & 0x0000ff0000000000L) >> 40);
								data[6] = (byte)((lk & 0x00ff000000000000L) >> 48);
								data[7] = (byte)((lk & 0xff00000000000000L) >> 56);
							}
							break;
							
						case Types.LONGNVARCHAR:
						case Types.LONGVARCHAR:
						case Types.VARCHAR:
						case Types.CHAR:
							String str = rs.getString(i);
							data = (str == null ? null : str.getBytes());
							break;
							
						case Types.DECIMAL:
						case Types.TIMESTAMP:
						case Types.TIME:
						case Types.DATE:						
						case Types.BLOB:						
						case Types.BINARY:
						case Types.ARRAY:
						default:
							data = rs.getBytes(i);
							break;
						}
						
						if (data == null) {
							sos.write(0);
							sos.write(0);
							sos.write(0);
							sos.write(0);
						} else {
							sos.write((byte)(data.length & 0x000000ff));
							sos.write((byte)((data.length & 0x0000ff00) >> 8));
							sos.write((byte)((data.length & 0x00ff0000) >> 16));
							sos.write((byte)((data.length & 0xff000000) >> 24));
							sos.write(data);
						}
						sos.write(0);
						
					}
					
				}
				
				sos.flush();
				sos.close();
				break;
				
			default:
				throw new Exception("failed to getOutputStream");
			}
			
		} finally {
			
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
			
		}
		
		return true;
		
	}
	
}

