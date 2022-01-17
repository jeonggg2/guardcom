package gcom.policy.service;

import java.util.HashMap;

import gcom.policy.model.PolicyModel;

public interface IPolicyService {
	
	PolicyModel getPolicyDataByNo(int no);
	HashMap<String, Object> savePolicyData(HashMap<String, Object> map);
	
}
