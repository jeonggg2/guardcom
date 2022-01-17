package gcom.service.Statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import gcom.DAO.CommonStatistcDAO;
import gcom.DAO.PolicyStatistcDAO;
import gcom.Model.statistic.FlotChartDataModel;
import gcom.Model.statistic.UserAgentStatisticModel;

public class StatisticServiceImpl implements IStatisticService {
	
	CommonStatistcDAO coDao = new CommonStatistcDAO();
	PolicyStatistcDAO poDao = new PolicyStatistcDAO();
	
	public FlotChartDataModel getUSBChartDayData(Map<String, Object> map){
		return calcChartDate(map, poDao.getUSBChartDayData(map));
	}
	
	public FlotChartDataModel getUSBChartMonthData(Map<String, Object> map){
		return calcChartMonth(map, poDao.getUSBChartMonthData(map));		
	}

	public FlotChartDataModel getExportChartDayData(Map<String, Object> map){
		return calcChartDate(map, poDao.getExportChartDayData(map));
	}
	
	public FlotChartDataModel getExportChartMonthData(Map<String, Object> map){
		return calcChartMonth(map, poDao.getExportChartMonthData(map));		
	}

	public FlotChartDataModel getPrintChartDayData(Map<String, Object> map){
		return calcChartDate(map, poDao.getPrintChartDayData(map));
	}
	
	public FlotChartDataModel getPrintChartMonthData(Map<String, Object> map){
		return calcChartMonth(map, poDao.getPrintChartMonthData(map));		
	}

	public FlotChartDataModel getPatternChartDayData(Map<String, Object> map){
		return calcChartDate(map, poDao.getPatternChartDayData(map));
	}
	
	public FlotChartDataModel getPatternChartMonthData(Map<String, Object> map){
		return calcChartMonth(map, poDao.getPatternChartMonthData(map));		
	}
	
	public UserAgentStatisticModel getUserAgentStatisticData(Map<String, Object> map){
		return coDao.getUserAgentStatisticData(map);		
	}
	
	public FlotChartDataModel calcChartDate(Map<String, Object> map, FlotChartDataModel input){
	   List<List<Long>> data = input.getItem();
	   List<List<Long>> newData = new ArrayList<List<Long>>();
	   //1493564400000   5/1
	   int setRange = Integer.parseInt(map.get("setRange").toString());

	   long setValue = Long.parseLong(map.get("setValue").toString());
	   long oneDay = 86400000;
	   long startDay =  setValue - (oneDay * (setRange-1));
	   
	   /*
	    * 기준일로 부터 최초 데이터 구함
	    * 기준길이 만큼 반복문 수행
	    * 리스트의 첫번째 인덱스에 해당 값이 있는지 확인
	    * 	해당값이 있으면 넘어감
	    * 	해당값이 없으면 0기입
	    */
	   
	   for(int i = 0; i < setRange; i++){

		   boolean exist = false;

		   for(List<Long> item : data ){
			   if(item.get(0).longValue() == startDay){
				   exist = true;
				   List<Long> temp = new ArrayList<Long>();
				   temp.add(item.get(0));
				   temp.add(item.get(1));				   
				   newData.add(temp);
				   break;
			   }
		   }
		   //일자가 존재 하지 않는다면, 0 삽입해줌
		   if(exist == false){
			   List<Long> inputData = new ArrayList<Long>();
			   inputData.add(startDay);
			   inputData.add((long) 0);
			   newData.add(inputData);
		   }
		   
		   startDay += oneDay;
	   }
	   

	   FlotChartDataModel res = new FlotChartDataModel();
	   res.setItem(newData);
	   return res;
	}
	

	public FlotChartDataModel calcChartMonth(Map<String, Object> map, FlotChartDataModel input){
	   List<List<Long>> data = input.getItem();
	   List<List<Long>> newData = new ArrayList<List<Long>>();
	   long oneDay = 86400000;
	   int setRange = Integer.parseInt(map.get("setRange").toString());
	   long setValue = Long.parseLong(map.get("setValue").toString());
	   //long startDay =  setValue - (oneDay * (setRange-1));
	   Calendar startCal = Calendar.getInstance();
	   startCal.setTimeInMillis(setValue);
	   
	   startCal.add(Calendar.MONTH, (setRange * -1));
	   startCal.add(Calendar.MONTH, 1);
	   long start = startCal.getTimeInMillis();
	   
	   /*
	    * 기준일로 부터 최초 데이터 구함
	    * 기준길이 만큼 반복문 수행
	    * 리스트의 첫번째 인덱스에 해당 값이 있는지 확인
	    * 	해당값이 있으면 넘어감
	    * 	해당값이 없으면 0기입
	    */
	   
	   for(int i = 0; i < setRange; i++){

		   boolean exist = false;

		   for(List<Long> item : data ){
			   if(item.get(0).longValue() == start){
				   exist = true;
				   List<Long> temp = new ArrayList<Long>();
				   temp.add(item.get(0) + oneDay);
				   temp.add(item.get(1));				   
				   newData.add(temp);
				   break;
			   }
		   }
		   //일자가 존재 하지 않는다면, 0 삽입해줌
		   if(exist == false){
			   List<Long> inputData = new ArrayList<Long>();
			   inputData.add(start);
			   inputData.add((long) 0);
			   newData.add(inputData);
		   }
		   
		   startCal.add(Calendar.MONTH, 1);
		   start = startCal.getTimeInMillis();
	   }
	   

	   FlotChartDataModel res = new FlotChartDataModel();
	   res.setItem(newData);
	   return res;
	}
	
	
}
