
package com.king.nowedge.helper;

import com.king.nowedge.dto.ryx.crm.*;
import com.king.nowedge.query.ryx.crm.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class CrmHelper  extends BaseHelper {
	
	private static  CrmHelper crmHelper ;  
	
	public static CrmHelper getInstance() {
		return crmHelper;
	}
	

	@PostConstruct  
    public void init() {  		
		crmHelper = this;
    }  


	
	/**
	 * 联系人
	 * @param custId
	 * @return
	 */
	public List<RyxContactDTO> getContactByCustomer(Long custId ){	
	
		RyxContactQuery query = new RyxContactQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		return crmHelper.ryxCrmService.queryContact(query).getModule().getList();
			
	}
	
	public List<RyxContactDTO> getContactByCustomer(Long custId ,Integer pageSize){	
		
		RyxContactQuery query = new RyxContactQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(pageSize);
		return crmHelper.ryxCrmService.queryContact(query).getModule().getList();
			
	}
	
	
	public Integer getContactNbrByCustomer(Long custId ){	
		
		RyxContactQuery query = new RyxContactQuery();
		query.setCustId(custId);
		return crmHelper.ryxCrmService.countQueryContact(query).getModule();
			
	}
	
	
	public Integer getContractNbrByCustomer(Long custId ){	
		
		RyxContractQuery query = new RyxContractQuery();
		query.setCustId(custId);
		return crmHelper.ryxCrmService.countQueryContract(query).getModule();
			
	}
	
	
	public Integer getMoneyItemNbrByCustomer(Long custId ){	
		
		RyxMoneyItemQuery query = new RyxMoneyItemQuery();
		query.setCustId(custId);
		return crmHelper.ryxCrmService.countQueryMoneyItem(query).getModule();
			
	}
	
	
	public Integer getMoneyItemNbrByContract(Long contract ){	
		
		RyxMoneyItemQuery query = new RyxMoneyItemQuery();
		query.setContract(contract);
		return crmHelper.ryxCrmService.countQueryMoneyItem(query).getModule();
			
	}
	
	
	
	public Integer getMoneyPlanNbrByCustomer(Long custId ){	
		
		RyxMoneyPlanQuery query = new RyxMoneyPlanQuery();
		query.setCustId(custId);
		return crmHelper.ryxCrmService.countQueryMoneyPlan(query).getModule();
			
	}
	
	
	
	public RyxPresaleDTO getPresaleById(Long id ){	
		return crmHelper.ryxCrmService.getPresaleById(id).getModule();
			
	}
	
	
	/**
	 * 联系人
	 * @param custId
	 * @return
	 */
	public RyxContactDTO getDefaultContact(Long custId ){	
	
		RyxContactQuery query = new RyxContactQuery();
		query.setCustId(custId);
		query.setIdefault(1);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		List<RyxContactDTO> list = crmHelper.ryxCrmService.queryContact(query).getModule().getList();
		if(null == list || list.size() == 0){
			return null;
		}
		
		return list.get(0);
			
	}
	
	
	/**
	 * 往来账
	 * @param custId
	 * @return
	 */
	public List<RyxContactDTO> getMoneyItemByCustomer(Long custId ){	
		
		RyxMoneyItemQuery query = new RyxMoneyItemQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		return crmHelper.ryxCrmService.queryMoneyItem(query).getModule().getList();
			
	}
	
	
	
	
	public List<RyxContactDTO> getMoneyItemByCustomer(Long custId,Integer pageSize ){	
		
		RyxMoneyItemQuery query = new RyxMoneyItemQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(pageSize);
		return crmHelper.ryxCrmService.queryMoneyItem(query).getModule().getList();
			
	}
	
	
	
	
	
	
	public List<RyxContactDTO> getMoneyItemByContract(Long contract,Integer pageSize ){	
		
		RyxMoneyItemQuery query = new RyxMoneyItemQuery();
		query.setContract(contract);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(pageSize);
		return crmHelper.ryxCrmService.queryMoneyItem(query).getModule().getList();
			
	}
	
	
	
	public List<RyxContactDTO> getMoneyItemByContract(Long contract ){	
		
		RyxMoneyItemQuery query = new RyxMoneyItemQuery();
		query.setContract(contract);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		return crmHelper.ryxCrmService.queryMoneyItem(query).getModule().getList();
			
	}
	

	
	/**
	 * 往来账
	 * @param custId
	 * @return
	 */
	public List<RyxContactDTO> getMoneyPlanByCustomer(Long custId,Integer pageSize ){	
		
		RyxMoneyPlanQuery query = new RyxMoneyPlanQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(pageSize);
		return crmHelper.ryxCrmService.queryMoneyPlan(query).getModule().getList();
			
	}
	
	
	
	/**
	 * 往来账
	 * @param custId
	 * @return
	 */
	public List<RyxContactDTO> getMoneyPlanByContract(Long contract ){	
		
		RyxMoneyPlanQuery query = new RyxMoneyPlanQuery();
		query.setContract(contract);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		return crmHelper.ryxCrmService.queryMoneyPlan(query).getModule().getList();
			
	}
	
	
	public Integer getMoneyPlanNbrByContract(Long contract ){	
		
		RyxMoneyPlanQuery query = new RyxMoneyPlanQuery();
		query.setContract(contract);;
		return crmHelper.ryxCrmService.countQueryMoneyPlan(query).getModule();
			
	}
	
	
	public RyxMoneyPlanDTO getMoneyPlanById(Long id ){	
		return crmHelper.ryxCrmService.getMoneyPlanById(id).getModule();
	}
	
	

	
	
	
	public RyxContractDTO getContractById(Long id ){	
		return crmHelper.ryxCrmService.getContractById(id).getModule();
	}
	
	
	
	/**
	 * 往来账
	 * @param custId
	 * @return
	 */
	public List<RyxContactDTO> getContractByCustomer(Long custId ){	
		
		RyxContractQuery query = new RyxContractQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		return crmHelper.ryxCrmService.queryContract(query).getModule().getList();
			
	}
	
	
	
	/**
	 * 往来账
	 * @param custId
	 * @return
	 */
	public List<RyxContactDTO> getContractByCustomer(Long custId,Integer pageSize ){	
		
		RyxContractQuery query = new RyxContractQuery();
		query.setCustId(custId);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(pageSize);
		
		return crmHelper.ryxCrmService.queryContract(query).getModule().getList();
			
	}
	
	
	
	public List<RyxProjectDTO> getProjectByBizType(Integer bizType ){	
		
		RyxProjectQuery query = new RyxProjectQuery();
		query.setBizType(bizType);
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		return crmHelper.ryxCrmService.queryProject(query).getModule().getList();
			
	}
	

	
	public RyxProjectDTO getProjectById(Long id){	
		return crmHelper.ryxCrmService.getProjectById(id).getModule();
	}
	


}
