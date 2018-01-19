package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTreeNode {
	public static void main(String[] args) {
		List<TreeNode> worldlist = new ArrayList<TreeNode>();
		List<TreeNode> orglist = new ArrayList<TreeNode>();
		TreeNode node1 = new TreeNode();
		TreeNode node2 = new TreeNode();
		TreeNode node3 = new TreeNode();
		TreeNode node4 = new TreeNode();
		TreeNode node5 = new TreeNode();
		TreeNode node6 = new TreeNode();
		node1.setId("1");
		node1.setName("世界");
		node2.setId("2");
		node2.setName("中国");
		node2.setPid("1");
		node3.setId("3");
		node3.setName("爱尔兰");
		node3.setPid("1");
		node4.setId("4");
		node4.setName("法国");
		node4.setPid("1");
		node5.setId("5");
		node5.setName("湖北");
		node5.setPid("2");
		node6.setId("6");
		node6.setName("山东");
		node6.setPid("2");
		
		worldlist.add(node1);
		worldlist.add(node2);
		worldlist.add(node3);
		worldlist.add(node4);
		worldlist.add(node5);
		worldlist.add(node6);
		
		orglist = worldlist;
		
		List<TreeNode> result = getTree(worldlist, null);
		
		System.out.println(result);
		
	}

	public static List<TreeNode> getTree(List<TreeNode> worldlist, List<TreeNode> orglist) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		try {
			TreeNode node1, node2;
			/*
			 *  国家
			 */
			Map<String, TreeNode> worldMap = new HashMap<String, TreeNode>();
			for (int i = 0; worldlist != null && i < worldlist.size(); i++) {
				node1 = worldlist.get(i);
				worldMap.put(node1.getId(), node1);
			}
			for (int j = 0; worldlist != null && j < worldlist.size(); j++) {
				node2 = worldlist.get(j);
				if (node2.getPid() == null) {
					result.add(node2);
				} else {
					node1 = worldMap.get(node2.getPid());
					if (node1 != null) {
						node1.setHasChildren(true);
						node1.getChildren().add(node2);
					}
				}
			}
			/*
			 *  组织
			 */
			List<TreeNode> list2 = new ArrayList<TreeNode>();
			for (int i = 0; orglist  != null && i < orglist.size(); i++) {
				node1 = orglist.get(i);
				list2.add(node1);
			}
			for (int j = 0; orglist  != null && j < orglist.size(); j++) {
				node1 = orglist.get(j);
				if (node1.getPid() == null) {
					result.add(node1);
				}
				for (int k = 0; k < list2.size(); k++) {
					node2 = list2.get(k);
					if (node1.getId().equals(node2.getPid())) {
						boolean isHaveThis = false;
						List<TreeNode> childrens = node1.getChildren();
						for (TreeNode c : childrens) {
							if (node2.getId().equals(c.getId())) {
								isHaveThis = true;
								break;
							}
						}
						if (!isHaveThis) {
							list2.remove(k);
							k--;
							node1.getChildren().add(node2);
							node1.setHasChildren(true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
