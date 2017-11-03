package mybatis.dto;

import java.util.List;

public class BaseDTO {

	// 是否分页(0-不分页、1-分页)
	private Integer isPaging = 1;
	
	// 页索引
	private Integer offset = 0;
	
	// 页大小，即每页显示的记录数量
	private Integer limit = 10;
	
	// 排序字段
	private String sort;
	
	// 排序类型
	private String order;

	// 是否需要统计记录数(0-不 统计，1-统计)，默认统计
	private Integer isCount = 1;
	
	// 统计记录数
	private Integer totalCounts = 0;
	
	private List<?> resultList;
	
	

	public Integer getIsPaging() {
		return isPaging;
	}

	public Integer getOffset() {
		return offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public String getSort() {
		return sort;
	}

	public String getOrder() {
		return order;
	}

	public Integer getIsCount() {
		return isCount;
	}

	public Integer getTotalCounts() {
		return totalCounts;
	}

	public List<?> getResultList() {
		return resultList;
	}

	

	public void setIsPaging(Integer isPaging) {
		this.isPaging = isPaging;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setIsCount(Integer isCount) {
		this.isCount = isCount;
	}

	public void setTotalCounts(Integer totalCounts) {
		this.totalCounts = totalCounts;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

}
