package mybatis.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>Description: 信息发布数据对象</p>
 *
 * @author zhufukun
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017</p>
 * @date 2017年10月30日
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
public class WayMessagePublishDO implements Serializable{
    private static final long serialVersionUID = -1398762932170349668L;
    /** 主键ID */
    private Long id;

    /** 信息ID */
    private String messageId;

    /** 信息标题 */
    private String messageTitle;

    /** 开始时间 */
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /** 结束时间 */
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 发布状态(0：未发布，1：发布中，2：已过期) */
    private Integer publishStatus;

    /** 是否删除(1 表示删除，0 表示未删除) */
    private Boolean isDeleted;

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

    /** 内容 */
    private String messageContent;

    List<WayRobotActionScopeDO> actionScopes;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle == null ? null : messageTitle.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    
    public List<WayRobotActionScopeDO> getActionScopes() {
		return actionScopes;
	}

	public void setActionScopes(List<WayRobotActionScopeDO> actionScopes) {
		this.actionScopes = actionScopes;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WayMessagePublishDO{");
        sb.append("id=").append(id);
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", messageTitle='").append(messageTitle).append('\'');
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", publishStatus=").append(publishStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", subsystemId='").append(subsystemId).append('\'');
        sb.append(", modifiedBy=").append(modifiedBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", actionScopes=").append(actionScopes);
        sb.append(", messageContent='").append(messageContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
	
	
}