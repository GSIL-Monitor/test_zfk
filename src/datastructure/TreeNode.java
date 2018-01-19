package datastructure;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class TreeNode {
	String id;
	String name;
	String pid;
	
	boolean hasChildren = false;
	
	List<TreeNode> children = new ArrayList<TreeNode>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
	
}
