package mybatis.dao;

import java.util.List;
import java.util.Map;

import mybatis.entity.WayRobotActionScopeDO;

/**
 * <p>
 * Description: 机器人作用域中间表DAO对象
 * </p>
 *
 * @author evan
 * @version 1.0.0
 *          <p>
 *          Company:workway
 *          </p>
 *          <p>
 *          Copyright:Copyright(c)2017
 *          </p>
 * @date 2017年10月30日
 */
public interface WayRobotActionScopeMapper{

	/**
	 * 作用域批量插入
	 * 
	 * @param list
	 * @return
	 */
	int insertBatch(List<WayRobotActionScopeDO> list);

	/**
	 * 通过使用id删除作用域，可删除多条数据
	 * 
	 * @param useId
	 *            作用域使用者id
	 * @return
	 */
	int deleteByUseId(String useId);

	/**
	 * 
	 * 作用范围查询
	 * 
	 * @param map
	 *
	 * @return
	 */
	List<WayRobotActionScopeDO> listActionScopes(Map map);
}