package gcom.common.util;

public class ConfigInfo {
	/* file path */
//	public final static String FILE_UPLOAD_PATH = "/uploads";
	public final static String FILE_UPLOAD_PATH = "/Users/dwkang/Documents/uploads";
	public final static String AGENT_FILE_PATH = "/agent";
	public final static String AGENT_FILE_REAL_NAME = "/GcInstaller.exe";
	public final static String AGENT_FILE_VIEW_NAME = "GcInstaller.exe";
	
	/* Result Code */
	public final static String RETURN_CODE_SUCCESS = "S";	// �꽦怨�
	public final static String RETURN_CODE_ERROR = "E";		// �떎�뙣
	public final static String DUP_USER_NUMBER = "DUN";		// 以묐났�맂 �궗踰� 議댁옱
	public final static String DUP_USER_ID = "DUI";			// 以묐났�맂 �븘�씠�뵒 議댁옱
	public final static String EXIST_CHILD_DEPT = "ECD";	// �븯�쐞遺��꽌 議댁옱
	public final static String EXIST_DEPT_AGENT = "EDA";	// �뿉�씠�쟾�듃 議댁옱
	public final static String EXIST_DEPT_USER= "EDU";		// 
	public final static String EXIST_USER_NUMBER= "EUN";	// 以묐났�맂 �궗踰� 議댁옱
	
	public final static String NOT_EXIST_USER= "NEU";		//�븘�씠�뵒 �뾾�쓬
	public final static String NOT_CORRECT_PASSWORD = "NCP";		//�뙣�뒪�썙�뱶 ��由�
	public final static String NOT_CORRECT_PASSWORD_ID = "NCPI";		//�뙣�뒪�썙�뱶�굹 �븘�씠�뵒 ��由�
	
	public final static String EXIST_OVER_FILE_SIZE = "M";	// �뙆�씪�궗�씠利덉큹怨�
	
	public final static String EXIST_NOT_AGENT = "ENA";		// Agent �젙蹂� �뾾�쓬. 
	public final static String EXIST_NOT_PARAM = "ENP"; 	// �븘�슂�젙蹂닿� 議댁옱�븯吏� �븡�쓬. 
	public final static String EXIST_FAIL_VERIFY = "EFV";	// �뜲�씠�꽣 寃�利� �떎�뙣 
	
	public final static String NOT_CORRECT_IP = "NCI";		//�븘�씠�뵾 嫄곕�
	
	public final static String RETURN_CODE_SUCCESS_OVERLAP = "SO"; //�씪遺��꽦怨� 以묐났議댁옱
	public final static String PW_PATTERN = "PP"; // 패스워드 패턴 이상 
	public final static String DUP_USER = "DUP"; // 아이디 혹은 사번 둘중 하나 중복
	public final static String PW_USED = "PU"; // 최근에 사용된 비밀번호

	public final static String PW_COUNT_ERROR = "PCE";
}
