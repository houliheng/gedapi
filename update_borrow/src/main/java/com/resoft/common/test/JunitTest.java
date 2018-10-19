package com.resoft.common.test;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.utils.Encodes;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-context.xml", "/spring-context-shiro.xml" })
public class JunitTest {

	public static void main(String[] args) {
		System.out.println(testPass());
	}

	public static String testPass(){
	/*	byte[] salt = Encodes.decodeHex("85c345441f877cf4fb929ef862f7239913ea1aac69a9c045aeb8a330".substring(0, 16));
		byte[] hashPassword = Digests.sha1("111111".getBytes(), salt, 1024);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
*/
		byte[] salt = Digests.generateSalt(8);
		byte[] hashPassword = Digests.sha1("111111".getBytes(), salt, 1024);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}
	
	private static void genDictTypeConstans(){
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:mysql://192.168.8.237:3306/cre", "cre","cre");
		ds.setDriverClassName("org.gjt.mm.mysql.Driver"); 
		JdbcTemplate jdbc = new JdbcTemplate(ds);
		String sql = "select distinct type,description from sys_dict order by type";
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		StringBuffer buffer = new StringBuffer();
		buffer.append("package com.resoft.common.utils;\n\n");
		buffer.append("public class Constants {\n");
		for (int i = 0; i < list.size(); i++) {
			buffer.append("\t/**").append(list.get(i).get("description")).append("*/\n");
			buffer.append("\tpublic static final String ").append(StringUtils.upperCase((String) list.get(i).get("type"))).append(" = \"").append(list.get(i).get("type")).append("\";\n");
		}
		buffer.append("}");
		FileUtils.writeToFile("C:/temp/Constants.java", buffer.toString(), "utf-8",false);
	}
	private static String genDictValueConstans(){
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:mysql://192.168.8.237:3306/cre", "cre","cre");
		ds.setDriverClassName("org.gjt.mm.mysql.Driver"); 
		JdbcTemplate jdbc = new JdbcTemplate(ds);
		String sql = "select type,value,label from sys_dict order by type";
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		StringBuffer buffer = new StringBuffer();
		buffer.append("package com.resoft.common.utils;\n\n");
		buffer.append("public class Constants {\n");
		for (int i = 0; i < list.size(); i++) {
			buffer.append("\t/**").append(list.get(i).get("label")).append("*/\n");
			buffer.append("\tpublic static final String ").append(StringUtils.upperCase((String) list.get(i).get("type"))).append("_")
			.append(list.get(i).get("label")).append(" = \"").append(list.get(i).get("value")).append("\";\n");
		}
		buffer.append("}");
//		FileUtils.writeToFile("C:/temp/Constants.java", buffer.toString(), "utf-8",false);
		return buffer.toString();
	}
}
