package mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * Description: 机器人作用域中间表数据对象
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
public class WayRobotActionScopeDO implements Serializable {
	private static final long serialVersionUID = -4811406555105124939L;
	/** 主键ID */
	private Long id;

	/** 应用类型(1：流程库、2：问答库、3：技能库、4：信息发布) */
	private Integer useType;

	/** 应用ID */
	private String useId;

	/** 作用域类型(1：单个机器人，2：机器人类型，3：安装位置) */
	private Integer scopeType;

	/** 作用域ID，eg：机器人类型ID */
	private String scopeId;

	/** 作用域名称，eg：机器人类型名称 */
	private String scopeName;

	/** 所属子系统ID */
	private String subsystemId;

	/** 修改人 */
	private Long modifiedBy;

	/** 创建时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/** 修改时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId == null ? null : useId.trim();
	}

	public Integer getScopeType() {
		return scopeType;
	}

	public void setScopeType(Integer scopeType) {
		this.scopeType = scopeType;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId == null ? null : scopeId.trim();
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId == null ? null : subsystemId.trim();
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("WayRobotActionScopeDO{");
		sb.append("id=").append(id);
		sb.append(", useType=").append(useType);
		sb.append(", useId='").append(useId).append('\'');
		sb.append(", scopeType=").append(scopeType);
		sb.append(", scopeId='").append(scopeId).append('\'');
		sb.append(", scopeName='").append(scopeName).append('\'');
		sb.append(", subsystemId='").append(subsystemId).append('\'');
		sb.append(", modifiedBy=").append(modifiedBy);
		sb.append(", createTime=").append(createTime);
		sb.append(", modifiedTime=").append(modifiedTime);
		sb.append('}');
		return sb.toString();
	}
}