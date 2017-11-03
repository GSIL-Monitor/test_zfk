package mybatis.dto;

import java.util.Map;

/**
 * 
 * @author workway
 *
 */
public class PageSearchDTO extends BaseDTO {

	String key;

	Map<String, Object> searchContents;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getSearchContents() {
		return searchContents;
	}

	public void setSearchContents(Map<String, Object> searchContents) {
		this.searchContents = searchContents;
	}

	@Override
	public String toString() {
		return "PageSearchDTO [key=" + key + ", searchContents=" + searchContents + "]";
	}

	
}
