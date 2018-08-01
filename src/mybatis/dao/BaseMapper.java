package mybatis.dao;

import java.util.List;
import java.util.Map;

public interface BaseMapper {
	
	/**
	 * 匹配度分组分析
	 * @param map
	 * @return
	 */
	List<Map> scoreGroup(Map map);
	
	/**
	 * 类型匹配度分组分析
	 * @param map
	 * @return
	 */
	List<Map> typeScoreGroupAvg(Map map);
	
	
	/**
	 * 查询作用域
	 * @param map
	 * @return
	 */
	List<Map> findActionScope(Map map);
}
