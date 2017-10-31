package jackson;

import java.util.ArrayList;
//JSON序列化和反序列化使用的User类
import java.util.Date;
import java.util.List;

public class User {
	private String name;
	private Integer age;
	private Date birthday;
	private String email;
	private List<User> friends = new ArrayList<User>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", birthday=" + birthday + ", email=" + email + ", friends="
				+ friends + "]";
	}

	

}