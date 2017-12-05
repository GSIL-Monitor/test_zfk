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
}
