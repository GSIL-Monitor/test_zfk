package hadoop.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;

public class HDFSFileSystemLoader {
	private DistributedFileSystem dfs = null;
	private String user = "user";
	private String superuser = "hadoop";
	private String supergroup = "supergroup";
	private static HDFSFileSystemLoader hsl = new HDFSFileSystemLoader();

	public static HDFSFileSystemLoader getInstance() {
		if (null == hsl) {
			hsl = new HDFSFileSystemLoader();
		}
		return hsl;
	}

	private void hdfsInit() throws IOException {
		try {
			Configuration conf = new Configuration();
			UserGroupInformation.setLoginUser(UserGroupInformation
					.createRemoteUser(this.superuser));
			// conf.set("dfs.web.ugi", this.superuser + "," + this.supergroup);
			// conf.set("fs.default.name", "hdfs://192.168.31.182:9000");
			FileSystem fs = FileSystem.get(conf);
			this.dfs = (DistributedFileSystem) fs;

			this.dfs.initialize(new URI(conf.get("fs.default.name") + "/"
					+ this.user + "/" + this.superuser), conf);
		} catch (URISyntaxException e) {
		}
	}

	public FileSystem getFs() throws IOException {
		Configuration conf = new Configuration();
		// 设置访问用户是hadoop，不然没有权限操作
		UserGroupInformation.setLoginUser(UserGroupInformation
				.createRemoteUser(this.superuser));
		FileSystem fs = FileSystem.get(conf);
		return fs;
	}

	public DistributedFileSystem getDfs() throws IOException {
		if (this.dfs == null) {
			hdfsInit();
		}
		return this.dfs;
	}

	public DistributedFileSystem getDfs(Configuration conf) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		UserGroupInformation.setLoginUser(UserGroupInformation
				.createRemoteUser(this.superuser));
		DistributedFileSystem dfs = (DistributedFileSystem) fs;
		return dfs;
	}

}

/*
 * Location: D:\WorkSpacesForUap2.8\lib\component.hdfs-1.0.0.jar Qualified Name:
 * component.hdfs.util.HDFSFileSystemLoader JD-Core Version: 0.6.0
 */