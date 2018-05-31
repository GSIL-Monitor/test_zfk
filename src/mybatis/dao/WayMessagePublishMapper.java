package mybatis.dao;

import java.util.List;
import java.util.Map;

import mybatis.dto.PageSearchDTO;
import mybatis.entity.WayMessagePublishDO;


/**
 * <p>Description: 信息发布DAO对象</p>
 *
 * @author zhufukun
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017</p>
 * @date 2017年10月30日
 */
public interface WayMessagePublishMapper {
	
	WayMessagePublishDO getById(Long id);
	
	Map<String, Object> getByIdMap(Long id);
	
	List<WayMessagePublishDO> listByPage(PageSearchDTO searchDTO);
	
	List<Map<String, Object>> listByPageMap(Map<String, Object> map);
	
	Long countAll();
	
	List<WayMessagePublishDO> listAll();
}