package mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongodbTest {
	public static void main(String[] args) throws Exception {
		//insert();
		//selectAll();
		selectPart();
		//update();
		//delete();
	}

	/**
	 * 保存实体对象
	 * 
	 * @throws Exception
	 */
	public static void insert() throws Exception {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("192.168.1.28", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("zfk");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("user");
			System.out.println("集合 user 选择成功");
			// 插入文档
			/**
			 * 1. 创建文档 org.bson.Document 参数为key-value的格式 2. 创建文档集合List
			 * <Document> 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List
			 * <Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
			 */
			Document document = new Document();
			document.put("id", 1);
			document.put("name", "小明");
			document.put("age", 12);
			// //然后保存到集合中
			// // mongoCollection.insertOne(document);
			// 实现上述json串思路如下：
			// 第一种：类似xml时，不断添加
			Document addressDocument = new Document();
			addressDocument.put("city", "beijing");
			addressDocument.put("code", "065000");
			document.put("address", addressDocument);
			List<Document> documents = new ArrayList<Document>();
			documents.add(document);
			collection.insertMany(documents);
			System.out.println("文档插入成功");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 遍历所有的
	 * 
	 * @throws Exception
	 */
	public static void selectAll() throws Exception {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("192.168.1.28", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("zfk");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("user");
			System.out.println("集合 user 选择成功");

			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			FindIterable<Document> findIterable = collection.find().limit(2);
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 根据条件查询
	 * 
	 * @throws Exception
	 */
	public static void selectPart() throws Exception {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("192.168.1.28", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("zfk");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("user");
			System.out.println("集合 user 选择成功");

			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			// 可以直接put
			Document document = new Document();
			document.put("id", 1);
//			FindIterable<Document> findIterable = collection.find(document);
			FindIterable<Document> findIterable = collection.find(Filters.gte("age", 20));
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

	/**
	 * 更新操作 更新一条记录
	 * 
	 * @throws Exception
	 */
	public static void update() throws Exception {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("192.168.1.28", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("zfk");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("user");
			System.out.println("集合 user 选择成功");

			//更新一个
			collection.updateOne(Filters.eq("id", 1), new Document("$set", new Document("id", 2)));
			// 更新所有
			//collection.updateMany(Filters.eq("id", 1), new Document("$set", new Document("id", 2)));
			// 检索查看结果
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 删除文档，其中包括删除全部或删除部分
	 * 
	 * @throws Exception
	 */
	public static void delete() throws Exception {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("192.168.1.28", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("zfk");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("user");
			System.out.println("集合 user 选择成功");

			// 删除符合条件的第一个文档
			collection.deleteOne(Filters.eq("id", 2));
			// 删除所有符合条件的文档
			//collection.deleteMany(Filters.eq("id", 2));
			// 检索查看结果
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
