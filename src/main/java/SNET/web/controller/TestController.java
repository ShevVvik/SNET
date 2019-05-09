package SNET.web.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import springweb.domain.entity.User;

@Controller
@RequestMapping("/test")
public class TestController {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(javax.sql.DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}
	
	@GetMapping("main")
	public String main(@RequestHeader("Cookie") String cookie, @CookieValue("JSESSIONID") String id) {
		
		System.out.println("Cookie: " + cookie);
		System.out.println("SESSION: " + id);
		
		return "test/main";
	}
	
	
	@GetMapping("jdbc")
	public String jdbc(Model model) {
		
		List<User> list = jdbcTemplate.query(
				"select id, email, password from user where email like ?",
				
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						
						ps.setString(1, "%test.tt%");
						
					}
					
				},
				
				new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User u = new User();
						
						u.setId(rs.getLong("id"));
						u.setEmail(rs.getString("email"));
						u.setPassword(rs.getString("password"));
						
						return u;
					}
			
		});
		
		model.addAttribute("users", list);
		
		return "user/list";
	}
}
