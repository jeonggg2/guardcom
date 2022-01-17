package gcom.Model.statistic;

import lombok.Data;

@Data
public class UserAgentStatisticModel {
	private int totalUserCount;	//전체사용자 수
	private int connectAgentCount;	//접속자
	private int installedAgentCount;	//설치사용자수
	private int todayInstalledCount;	//오늘설치자
	
	
	private int requstEnrollCount;	//가입요청자

	
	private int contactCount;		//문의사항 수
	private int nonCommentContactCount;	//처리되지 않은 문의
	private int todayContactCount;	//금일 문의사항 수
	
	private float connectRate;	//접속 퍼센트
	private float installRate;	//설치 퍼센트
	private float commentRate;	//답변율
	
	//사용자 접속률/ 에이전트설치율, 문의답변율
	
	public void setConnectRate(){
		// connectAgentCount / totalUserCount		
		if(totalUserCount <= 0){
			connectRate = 100;
		}else
			connectRate = (float)((connectAgentCount*100) / totalUserCount);
	}
	
	public void setInstallRate(){
		// installedAgentCount / totalUserCont
		if(totalUserCount <= 0){
			installRate = 100;
		}else
			installRate = (float)((installedAgentCount*100) / totalUserCount);		
	}
	
	public void setCommentRate(){
		// contactCount - nonCommentContactCount / contactCount
		if(contactCount <= 0){
			commentRate = 100;
		}else
			commentRate = (float)(((contactCount - nonCommentContactCount)*100) / contactCount);				
	}
	
	
}
